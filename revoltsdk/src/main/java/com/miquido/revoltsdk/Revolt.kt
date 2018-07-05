package com.miquido.revoltsdk

import android.Manifest
import android.content.Context
import com.miquido.revoltsdk.internal.*
import com.miquido.revoltsdk.internal.configuration.ConfigurationRepository
import com.miquido.revoltsdk.internal.configuration.DefaultConfiguration
import com.miquido.revoltsdk.internal.configuration.RevoltConfiguration
import com.miquido.revoltsdk.internal.model.RevoltEvent
import com.miquido.revoltsdk.internal.network.NetworkConfiguration
import com.miquido.revoltsdk.internal.network.SendEventUseCase
import timber.log.Timber

/** Created by MiQUiDO on 28.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class Revolt private constructor(revoltConfiguration: RevoltConfiguration,
                                 context: Context) {

    private val sendEventUseCase: SendEventUseCase
    private val systemEventGenerator: SystemEventGenerator
    private val revoltRepository: RevoltRepository
    private val configurationRepository: ConfigurationRepository = ConfigurationRepository(context)

    init {
        val networkConfiguration = NetworkConfiguration(revoltConfiguration.endpoint,
                revoltConfiguration.secretKey,
                configurationRepository.getAppInstanceId())
        revoltRepository = RevoltRepository(networkConfiguration.revoltApi)
        sendEventUseCase = SendEventUseCase(revoltRepository)
        systemEventGenerator = SystemEventGenerator(ScreenSizeProvider(context))
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        startSession()
    }

    /**
     * Send an event to the Revolt backend.
     * Method is asynchronous. Events are sent in configurable batches or buffered for later in case of lack of internet connection.
     * @param revoltEvent RevoltEvent to send.
     */
    fun sendEvent(revoltEvent: RevoltEvent) {
        sendEventUseCase.send(revoltEvent)
    }

    private fun startSession() {
        sendEventUseCase.send(systemEventGenerator.generateEvent())
    }

    class Builder {
        private var secretKey: String? = null
        private var endpoint: String = DefaultConfiguration.URL
        private var maxBatchSize: Int = DefaultConfiguration.MAX_BATCH_SIZE
        private var eventDelay: Int = DefaultConfiguration.EVENT_DELAY
        private var offlineMaxSize: Int = DefaultConfiguration.OFFLINE_MAX_SIZE
        private var context: Context? = null

        fun with(context: Context): Revolt.Builder {
            this.context = context
            return this
        }

        fun secretKey(secretKey: String): Revolt.Builder {
            this.secretKey = secretKey
            return this
        }

        fun maxBatchSize(size: Int): Revolt.Builder {
            this.maxBatchSize = size
            return this
        }

        fun eventDelay(delay: Int): Revolt.Builder {
            this.eventDelay = delay
            return this
        }

        fun offlineQueueMaxSize(size: Int): Revolt.Builder {
            this.offlineMaxSize = size
            return this
        }

        fun endpoint(endpoint: String): Revolt.Builder {
            this.endpoint = endpoint
            return this
        }

        fun build(): Revolt {
            if (context == null) {
                throw IllegalArgumentException("Context must not be null")
            }
            if (secretKey == null) {
                throw IllegalStateException("Secret key must be initialized")
            }
            if (!hasPermission(context!!, Manifest.permission.INTERNET)) {
                throw IllegalArgumentException("INTERNET permission is required.")
            }

            return Revolt(createConfiguration(), context!!)
        }

        private fun createConfiguration(): RevoltConfiguration {
            return RevoltConfiguration(secretKey!!,
                    endpoint,
                    maxBatchSize,
                    eventDelay,
                    offlineMaxSize)
        }
    }
}
