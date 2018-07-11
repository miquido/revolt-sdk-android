package com.miquido.revoltsdk.internal

import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import com.miquido.revoltsdk.internal.database.DatabaseRepository
import androidx.work.WorkManager
import androidx.work.OneTimeWorkRequest
import androidx.work.State
import androidx.work.WorkStatus
import com.miquido.revoltsdk.Event
import com.miquido.revoltsdk.internal.configuration.EventDelay
import com.miquido.revoltsdk.internal.log.RevoltLogger
import java.util.*
import java.util.concurrent.TimeUnit

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class RevoltRepository(private val eventDelay: EventDelay,
                                private val batchSize: Int) {

    private var workId: UUID? = null
    private var eventsNumber: Int = 0

    fun addEvent(event: Event) {
        DatabaseRepository.addEvent(event)

        sendEvent()
    }

    private fun sendEvent() {
        eventsNumber = DatabaseRepository.getEventsNumber()

        WorkManager.getInstance()!!.beginUniqueWork("REVOLT_WORKER", ExistingWorkPolicy.REPLACE, createNewWorker()).enqueue()
        WorkManager.getInstance()!!.getStatusById(workId!!).observeForever { t: WorkStatus? ->
            RevoltLogger.d("Work status: ${t.toString()}")
            if (t?.state == State.SUCCEEDED) {
                RevoltLogger.d("Send succeeded, removing $eventsNumber")
                if (eventsNumber >= batchSize) {
                    DatabaseRepository.removeElements(batchSize)
                    eventsNumber -= batchSize
                } else {
                    DatabaseRepository.removeElements(eventsNumber)
                    eventsNumber = 0
                }
                RevoltLogger.d("Current events number: $eventsNumber")
                RevoltLogger.d("Current events number from db: ${DatabaseRepository.getEventsNumber()}")
                if (eventsNumber > 0) {
                    sendEvent()
                }
            }
        }
    }


    private fun createNewWorker(): OneTimeWorkRequest {
        val data = Data.Builder()
                .putInt(SendingEventsWorker.BATCH_SIZE, batchSize).build()

        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val sendingEventWork = if (eventsNumber >= batchSize) {
            OneTimeWorkRequest.Builder(SendingEventsWorker::class.java)
                    .setInputData(data)
                    .setConstraints(constraints)
                    .build()
        } else {
            OneTimeWorkRequest.Builder(SendingEventsWorker::class.java)
                    .setInitialDelay(eventDelay.delay, eventDelay.timeUnit)
                    .setInputData(data)
                    .setConstraints(constraints)
                    .build()
        }
        workId = sendingEventWork.id
        return sendingEventWork
    }
}
