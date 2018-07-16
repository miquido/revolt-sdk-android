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

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class RevoltService(eventDelay: EventDelay,
                             private val batchSize: Int,
                             private val backendRepository: BackendRepository,
                             private val databaseRepository: DatabaseRepository) {

    private val handler: Handler
    private val sendingEventTask = Runnable(sendingEventTask())
    private val delayMillis = eventDelay.timeUnit.toMillis(eventDelay.delay)

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
            if (it.responseStatus == ResponseModel.ResponseStatus.OK) {
                databaseRepository.removeElements(it.eventsAccepted)
            }
        }


        removeTask(sendingEventTask)

        createNextEventToSend()
    }


    private fun sendingEventTask(): () -> Unit = {
        sendEvent()
    }


    private fun createNextEventToSend() {
        getTimeToSendEvent()?.let {
            if (it > 0) {
                postTaskWithDelay(sendingEventTask, it)
            } else {
                postTask(sendingEventTask)
            }
        }
    }

    private fun getTimeToSendEvent(): Long? {
        if (databaseRepository.getEventsNumber() >= batchSize) {
            return 0L
        }

        val firstEvent = databaseRepository.getFirstEvent() ?: return null

        val firstEventTime = firstEvent.getTimestamp()

        val timeToSend = firstEventTime + delayMillis - System.currentTimeMillis()
        return if (timeToSend >= 0) timeToSend else 0
    }
}
