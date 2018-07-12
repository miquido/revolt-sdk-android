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
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class RevoltRepository(private val eventDelay: EventDelay,
                                private val batchSize: Int) {

    private var workStatus: State = State.ENQUEUED
    private var delay: Long = eventDelay.timeUnit.toMillis(eventDelay.delay)
    private var executionTime: Long? = null

    fun addEvent(event: Event) {
        launch {
            DatabaseRepository.addEvent(event)
            sendEvent()
        }
    }

    private fun sendEvent() {
        RevoltLogger.d("Sending event, workstatus: $workStatus")
        if (workStatus == State.ENQUEUED) {
            RevoltLogger.d("Work is enqueued, replacing existing")
            if (executionTime == null) {
                executionTime = System.currentTimeMillis() + delay
            } else {
                delay = executionTime!! - System.currentTimeMillis()
            }
            RevoltLogger.d("Starting with delay: $delay")
            val worker = createNewWorker()
            WorkManager.getInstance()!!.beginUniqueWork("REVOLT_WORKER", ExistingWorkPolicy.REPLACE, worker).enqueue()
            WorkManager.getInstance()!!.getStatusById(worker.id).observeForever { t: WorkStatus? ->
                launch {
                    if (t?.state == State.SUCCEEDED) {
                        RevoltLogger.d("Work succeeded ${t.id}")
                        workStatus = State.ENQUEUED
                        delay = eventDelay.timeUnit.toMillis(eventDelay.delay)
                        executionTime = null
                        if (DatabaseRepository.getEventsNumber() > 0) {
                            sendEvent()
                        }
                    }
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

        return if (DatabaseRepository.getEventsNumber() >= batchSize) {
            OneTimeWorkRequest.Builder(SendingEventsWorker::class.java)
                    .setInputData(data)
                    .setConstraints(constraints)
                    .build()
        } else {
            OneTimeWorkRequest.Builder(SendingEventsWorker::class.java)
                    .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                    .setInputData(data)
                    .setConstraints(constraints)
                    .build()
        }
    }
}
