package com.miquido.revoltsdk.internal

import android.os.Handler
import android.os.HandlerThread
import android.os.Process
import com.miquido.revoltsdk.internal.database.DatabaseRepository
import com.miquido.revoltsdk.Event
import com.miquido.revoltsdk.internal.configuration.EventDelay
import com.miquido.revoltsdk.internal.log.RevoltLogger
import com.miquido.revoltsdk.internal.model.EventModel
import com.miquido.revoltsdk.internal.network.BackendRepository
import com.miquido.revoltsdk.internal.network.SendEventsResult

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class RevoltService(eventDelay: EventDelay,
                             private val batchSize: Int,
                             private val backendRepository: BackendRepository,
                             private val databaseRepository: DatabaseRepository) {

    private val handler: Handler
    private val sendEventTask = ::sendEvent
    private val delayMillis = eventDelay.timeUnit.toMillis(eventDelay.delay)

    init {
        val thread = HandlerThread("RevoltThread", Process.THREAD_PRIORITY_BACKGROUND)
        thread.start()
        handler = Handler(thread.looper)
    }

    fun addEvent(event: Event) {
        postTask(saveEventInDatabaseTask(EventModel(event)))
    }

    private fun postTask(task: () -> Unit) {
        handler.post(task)
    }

    private fun removeTask(task: () -> Unit) {
        handler.removeCallbacks(task)
    }

    private fun postTaskWithDelay(task: () -> Unit, millis: Long) {
        handler.postDelayed(task, millis)
    }

    private fun saveEventInDatabaseTask(eventModel: EventModel): () -> Unit = {
        RevoltLogger.d("Adding events to database")

        databaseRepository.addEvent(eventModel)
        createNextSendingEventTask()
    }

    private fun sendEvent() {
        RevoltLogger.d("Sending events to backend")

        val millisToSend = getTimeToSendEvent() ?: return

        if (millisToSend > 0) {
            removeTask(sendEventTask)
            postTaskWithDelay(sendEventTask, millisToSend)
            return
        }

        val eventsToSend = ArrayList(databaseRepository.getFirstEvents(batchSize))
        RevoltLogger.d("Events number to be send: ${eventsToSend.size}")

        val response = backendRepository.sendEvents(eventsToSend)
        if (response.responseStatus == SendEventsResult.Status.OK) {
            databaseRepository.removeEvents(response.eventsAccepted)
        }

        createNextSendingEventTask()
    }


    private fun createNextSendingEventTask() {
        removeTask(sendEventTask)

        getTimeToSendEvent()?.let {
            if (it > 0) {
                postTaskWithDelay(sendEventTask, it)
            } else {
                postTask(sendEventTask)
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
