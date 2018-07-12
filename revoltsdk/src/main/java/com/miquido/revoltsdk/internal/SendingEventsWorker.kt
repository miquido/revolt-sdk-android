package com.miquido.revoltsdk.internal

import androidx.work.Worker
import com.miquido.revoltsdk.internal.configuration.DefaultConfiguration
import com.miquido.revoltsdk.internal.database.DatabaseRepository
import com.miquido.revoltsdk.internal.log.RevoltLogger
import com.miquido.revoltsdk.internal.network.BackendRepository

/** Created by MiQUiDO on 10.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class SendingEventsWorker : Worker() {

    override fun doWork(): Result {
        RevoltLogger.d("Worker started ($id) - ${Thread.currentThread().name}")
        var batchSize = inputData.getInt(BATCH_SIZE, DefaultConfiguration.MAX_BATCH_SIZE)
        if (batchSize > DefaultConfiguration.MAX_BATCH_SIZE) {
            batchSize = DefaultConfiguration.MAX_BATCH_SIZE
        }
        val events = DatabaseRepository.getFirstEvents(batchSize)
        val response = BackendRepository.addEvents(events).execute()
        return if (response.isSuccessful) {
            DatabaseRepository.removeElements(events.size)
            Result.SUCCESS
        } else {
            Result.FAILURE
        }
    }

    companion object {
        const val BATCH_SIZE = "BATCH_SIZE"
    }
}
