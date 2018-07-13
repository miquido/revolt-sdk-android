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
import com.miquido.revoltsdk.internal.model.RevoltModel
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class RevoltRepository(private val eventDelay: EventDelay,
                                private val batchSize: Int) {

    private var executionTime: Long? = null

    fun addEvent(event: Event) {
        launch {
            DatabaseRepository.addEvent(RevoltModel(event))
            sendEvent()
        }
    }

    private fun sendEvent() {
        //Starting new worker only if current worker hasn't started, otherwise wait until worker succeeded
        if (executionTime == null || System.currentTimeMillis() < executionTime!!) {
            RevoltLogger.d("Work hasn't started, replacing existing")

            //Worker will be executed with `eventDelay` delay so we're
            //setting `executionTime` to count delay in `setInitialDelay` method in Worker builder
            if (executionTime == null) {
                executionTime = System.currentTimeMillis() + eventDelay.timeUnit.toMillis(eventDelay.delay)
            }

            RevoltLogger.d("Starting with delay: ${executionTime!! - System.currentTimeMillis()}")

            val worker = createNewWorker()
            WorkManager.getInstance()!!.beginUniqueWork("REVOLT_WORKER", ExistingWorkPolicy.REPLACE, worker).enqueue()
            WorkManager.getInstance()!!.getStatusById(worker.id).observeForever { t: WorkStatus? ->
                launch {
                    if (t?.state == State.SUCCEEDED) {
                        executionTime = null
                        //starting new worker only if there are any events left in the database
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
                    .setInitialDelay(executionTime!! - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .setInputData(data)
                    .setConstraints(constraints)
                    .build()
        }
    }
}
