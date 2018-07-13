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

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class RevoltService(eventDelay: EventDelay,
                             private val batchSize: Int) {

    private val handler: Handler
    private var isStarted = false
    private val sendingEventTask = SendingEventTask(batchSize)
    private val delayMillis = eventDelay.timeUnit.toMillis(eventDelay.delay)

    init {
        val thread = HandlerThread("RevoltThread", Process.THREAD_PRIORITY_BACKGROUND)
        thread.start()
        handler = Handler(thread.looper)
        isStarted = true
    }

    fun addEvent(event: Event) {
        postTask(SaveEventInDatabase(event))
        postTaskWithDelay(sendingEventTask, delayMillis)
    }

    private fun postTask(task: Runnable) {
        if (isStarted) {
            handler.post(task)
        }
    }

    fun removeTask(task: Runnable) {
        handler.removeCallbacks(task)
    }

    fun postTaskWithDelay(task: Runnable, millis: Long) {
        if (isStarted) {
            handler.postDelayed(task, millis)
        }
    }

    internal class SaveEventInDatabase(private val event: Event) : Runnable {
        override fun run() {
            RevoltLogger.d("Adding events to database")
            DatabaseRepository.addEvent(RevoltModel(event))
        }
    }

    inner class SendingEventTask(private val batchSize: Int) : Runnable {
        override fun run() {
            RevoltLogger.d("Sending events to backend")

            val millisToSend = getTimeToSendEvent()
            val eventsToSend = DatabaseRepository.getFirstEvents(batchSize).size

            RevoltLogger.d("Events number to be send: $eventsToSend")

            if (millisToSend > 0) {
                postTaskWithDelay(sendingEventTask, millisToSend)
                return
            }
            val response = BackendRepository.addEvents(DatabaseRepository.getFirstEvents(eventsToSend)).execute()
            if (response.isSuccessful) {
                response.body()?.let {
                    DatabaseRepository.removeElements(it.eventsAccepted)
                }
            }

            if (DatabaseRepository.getFirstEvents(batchSize).size > 0) {
                postTask(sendingEventTask)
                return
            }

            removeTask(sendingEventTask)
        }

    }

    fun getTimeToSendEvent(): Long {
        if (DatabaseRepository.getEventsNumber() >= batchSize) {
            return 0L
        }

        val firstEventTime = DatabaseRepository.getFirstEvent().getTimestamp()

        val timeToSend = firstEventTime + delayMillis - System.currentTimeMillis()
        RevoltLogger.d("Time to send: $timeToSend")
        return timeToSend
    }
}
