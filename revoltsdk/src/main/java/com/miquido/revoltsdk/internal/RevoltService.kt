package com.miquido.revoltsdk.internal

import android.os.Handler
import android.os.HandlerThread
import android.os.Process
import com.miquido.revoltsdk.internal.database.DatabaseRepository
import com.miquido.revoltsdk.Event
import com.miquido.revoltsdk.internal.log.RevoltLogger
import com.miquido.revoltsdk.internal.model.EventModel
import com.miquido.revoltsdk.internal.network.BackendRepository
import com.miquido.revoltsdk.internal.network.SendEventsResult
import kotlin.math.log2

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class RevoltService(private val eventDelayMillis: Long,
                             private val batchSize: Int,
                             private val backendRepository: BackendRepository,
                             private val databaseRepository: DatabaseRepository,
                             private val firstSendingRetryTimeSeconds: Int,
                             private val maxSendingRetryTimeSeconds: Int) {

    private val handler: Handler
    private val sendEventTask = ::sendEvent
    private var sendingAttempts = 0
    private var lastAttemptTimeMillis = 0L
    private var requestEventErrorRetryCounter = 0

    init {
        val thread = HandlerThread("RevoltThread", Process.THREAD_PRIORITY_BACKGROUND)
        thread.start()
        handler = Handler(thread.looper)
    }

    companion object {
        private const val MAX_REQUEST_ERROR_RETRY_ATTEMPTS = 100
    }

    fun addEvent(event: Event) = postTask(saveEventInDatabaseTask(EventModel(event)))

    private fun postTask(task: () -> Unit) = handler.post(task)

    private fun removeTask(task: () -> Unit) = handler.removeCallbacks(task)

    private fun postTaskWithDelay(task: () -> Unit, millis: Long) = handler.postDelayed(task, millis)

    private fun saveEventInDatabaseTask(eventModel: EventModel): () -> Unit = {
        databaseRepository.addEvent(eventModel)
        createNextSendingEventTask()
    }

    private fun sendEvent() {
        val millisToSend = getTimeToSendEvent() ?: return

        RevoltLogger.d("Sending events to backend in $millisToSend milliseconds")

        if (millisToSend > 0) {
            removeTask(sendEventTask)
            postTaskWithDelay(sendEventTask, millisToSend)
            return
        }

        val eventsToSend = databaseRepository.getFirstEvents(batchSize)
        RevoltLogger.d("Events number to be send: ${eventsToSend.size}")

        val response = backendRepository.sendEvents(eventsToSend)
        handleResponse(response)

        createNextSendingEventTask()
    }

    private fun handleResponse(result: SendEventsResult) {
        RevoltLogger.d("Response status: ${result.responseStatus}")
        when (result.responseStatus) {
            SendEventsResult.Status.OK -> {
                databaseRepository.removeEvents(result.eventsAccepted)
                clearRetryData()
            }
            SendEventsResult.Status.SERVER_ERROR -> retryEvent()
            SendEventsResult.Status.SERVER_EVENT_ERROR -> {
                databaseRepository.removeEvents(result.eventsAccepted)
                clearRetryData()
            }
            SendEventsResult.Status.REQUEST_ERROR -> retryEvent()
            SendEventsResult.Status.REQUEST_EVENT_ERROR -> {
                databaseRepository.removeEvents(result.eventsAccepted + 1)
                retryEventError()
            }
        }
    }

    private fun clearRetryData() {
        sendingAttempts = 0
        requestEventErrorRetryCounter = 0
        lastAttemptTimeMillis = 0
    }

    private fun retryEventError() {
        ++requestEventErrorRetryCounter
        lastAttemptTimeMillis = System.currentTimeMillis()
    }

    private fun retryEvent() {
        ++sendingAttempts
        RevoltLogger.d("Retrying sending event - attempts number: $sendingAttempts")
        lastAttemptTimeMillis = System.currentTimeMillis()
    }

    private fun createNextSendingEventTask() {
        removeTask(sendEventTask)

        val timeMillisToSendEvents = when {
            isRetryRequired() -> getTimeToRetrySendingEvent()
            else -> getTimeToSendEvent()
        }

        RevoltLogger.d("Sending next event in: $timeMillisToSendEvents")

        timeMillisToSendEvents?.let {
            if (it > 0) {
                postTaskWithDelay(sendEventTask, it)
            } else {
                postTask(sendEventTask)
            }
        }
    }

    private fun isRetryRequired() = sendingAttempts > 0 || requestEventErrorRetryCounter > MAX_REQUEST_ERROR_RETRY_ATTEMPTS

    private fun getTimeToRetrySendingEvent(): Long {
        val intervalTimeMillis = when {
            requestEventErrorRetryCounter > MAX_REQUEST_ERROR_RETRY_ATTEMPTS ->
                getRetryInterval(MAX_REQUEST_ERROR_RETRY_ATTEMPTS - requestEventErrorRetryCounter)
            else -> getRetryInterval(sendingAttempts)
        }
        val retryTime = lastAttemptTimeMillis + intervalTimeMillis - System.currentTimeMillis()
        RevoltLogger.d("Retrying in $retryTime")
        return Math.max(retryTime, 0)
    }

    private fun getRetryInterval(attempts: Int): Long {
        val maxAttempts = log2(maxSendingRetryTimeSeconds.toDouble() / firstSendingRetryTimeSeconds) + 1
        if (attempts > maxAttempts) {
            return maxSendingRetryTimeSeconds.secondsToMillis()
        }
        return Math.min(powOf2(attempts - 1) * firstSendingRetryTimeSeconds, maxSendingRetryTimeSeconds).secondsToMillis()
    }

    private fun powOf2(n: Int) = 1 shl n

    private fun getTimeToSendEvent(): Long? {
        if (databaseRepository.getEventsNumber() >= batchSize) {
            return 0L
        }

        val firstEventTime = databaseRepository.getFirstEventTimestamp() ?: return null

        val timeToSend = firstEventTime + eventDelayMillis - System.currentTimeMillis()
        return Math.max(timeToSend, 0)
    }
}

