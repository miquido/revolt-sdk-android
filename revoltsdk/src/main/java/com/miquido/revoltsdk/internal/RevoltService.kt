package com.miquido.revoltsdk.internal

import android.os.Handler
import android.os.HandlerThread
import android.os.Process
import com.miquido.revoltsdk.internal.database.DatabaseRepository
import com.miquido.revoltsdk.Event
import com.miquido.revoltsdk.internal.configuration.EventDelay
import com.miquido.revoltsdk.internal.log.RevoltLogger
import com.miquido.revoltsdk.internal.model.RevoltModel
import com.miquido.revoltsdk.internal.network.BackendRepository
import com.miquido.revoltsdk.internal.network.ResponseModel
import kotlin.math.pow
import kotlin.math.roundToLong

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class RevoltService(private val eventDelay: Long,
                             private val batchSize: Int,
                             private val backendRepository: BackendRepository,
                             private val databaseRepository: DatabaseRepository,
                             private val firstRetryTimeSeconds: Int,
                             private val maxRetryTimeSeconds: Int) {

    private val handler: Handler
    private val sendingEventTask = Runnable(sendingEventTask())
    private var sendingAttempts: Int = 0

    init {
        val thread = HandlerThread("RevoltThread", Process.THREAD_PRIORITY_BACKGROUND)
        thread.start()
        handler = Handler(thread.looper)
    }

    fun addEvent(event: Event) {
        postTask(Runnable(saveEventInDatabaseTask(RevoltModel(event))))
    }

    private fun postTask(task: Runnable) {
        handler.post(task)
    }

    private fun removeTask(task: Runnable) {
        handler.removeCallbacks(task)
    }

    private fun postTaskWithDelay(task: Runnable, millis: Long) {
        handler.postDelayed(task, millis)
    }

    private fun saveEventInDatabaseTask(revoltModel: RevoltModel): () -> Unit = {
        RevoltLogger.d("Adding events to database")

        databaseRepository.addEvent(revoltModel)
        createNextEventToSend()
    }


    private fun sendEvent() {
        RevoltLogger.d("Sending events to backend")

        val millisToSend = getTimeToSendEvent() ?: return

        val eventsToSend = databaseRepository.getFirstEvents(batchSize)

        RevoltLogger.d("Events number to be send: ${eventsToSend.size}")

        if (millisToSend > 0) {
            postTaskWithDelay(sendingEventTask, millisToSend)
            return
        }
        val response = backendRepository.addEvents(eventsToSend)
        response?.let {
            when {
                it.responseStatus == ResponseModel.ResponseStatus.OK -> {
                    clearRetryData()
                    databaseRepository.removeElements(it.eventsAccepted)
                }
                it.responseStatus == ResponseModel.ResponseStatus.RETRY -> {
                    retryEvent()
                }
                else -> {
                    clearRetryData()
                    databaseRepository.removeElements(it.eventsAccepted + 1)
                }

            }
        }


        removeTask(sendingEventTask)

        createNextEventToSend()
    }

    private fun clearRetryData() {
        sendingAttempts = 0
    }

    private fun retryEvent() {
        RevoltLogger.d("Retrying sending event")
        ++sendingAttempts
    }

    private fun sendingEventTask(): () -> Unit = {
        sendEvent()
    }


    private fun createNextEventToSend() {
        val timeMillisToSendEvents = if (sendingAttempts > 0)
            getTimeToRetrySendingEvent()
        else
            getTimeToSendEvent()

        RevoltLogger.d("Sending next event in: $timeMillisToSendEvents")

        timeMillisToSendEvents?.let {
            if (it > 0) {
                postTaskWithDelay(sendingEventTask, it)
            } else {
                postTask(sendingEventTask)
            }
        }
    }

    private fun getTimeToRetrySendingEvent(): Long? {
        val timeToRetry = Math.min(2f.pow(sendingAttempts - 1) * firstRetryTimeSeconds, maxRetryTimeSeconds.toFloat()) * 1000L
        RevoltLogger.d("Retrying in $timeToRetry")
        return timeToRetry.roundToLong()
    }

    private fun getTimeToSendEvent(): Long? {
        if (databaseRepository.getEventsNumber() >= batchSize) {
            return 0L
        }

        val firstEvent = databaseRepository.getFirstEvent() ?: return null

        val firstEventTime = firstEvent.getTimestamp()

        val timeToSend = firstEventTime + eventDelay - System.currentTimeMillis()

        return if (timeToSend >= 0) timeToSend else 0
    }
}
