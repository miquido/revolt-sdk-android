package com.miquido.revoltsdk

import android.Manifest
import android.content.Context
import com.miquido.revoltsdk.internal.RevoltRepository
import com.miquido.revoltsdk.internal.ScreenSizeProvider
import com.miquido.revoltsdk.internal.SystemEventGenerator
import com.miquido.revoltsdk.internal.configuration.ConfigurationRepository
import com.miquido.revoltsdk.internal.configuration.DefaultConfiguration
import com.miquido.revoltsdk.internal.configuration.RevoltConfiguration
import com.miquido.revoltsdk.internal.database.DatabaseRepository
import com.miquido.revoltsdk.internal.hasPermission
import com.miquido.revoltsdk.internal.log.RevoltLogger
import com.miquido.revoltsdk.internal.network.BackendRepository
import com.miquido.revoltsdk.internal.network.RevoltApiBuilder
import com.miquido.revoltsdk.internal.network.RevoltRequestModel

/** Created by MiQUiDO on 28.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class Revolt private constructor(revoltConfiguration: RevoltConfiguration,
                                 context: Context) {

    private val systemEventGenerator: SystemEventGenerator
    private val revoltRepository: RevoltRepository
    private val configurationRepository: ConfigurationRepository = ConfigurationRepository(context)

    companion object {
        fun builder(): BuilderContext {
            return BuilderContext()
        }
    }

    init {
        val revoltApiBuilder = RevoltApiBuilder(revoltConfiguration.endpoint,
                configurationRepository.getAppInstanceId(),
                revoltConfiguration.trackingId,
                revoltConfiguration.secretKey
        )
        val backendRepository = BackendRepository(revoltApiBuilder.getRevoltApi())
        val databaseRepository = DatabaseRepository()
        revoltRepository = RevoltRepository(backendRepository, databaseRepository)
        systemEventGenerator = SystemEventGenerator(ScreenSizeProvider(context))
        RevoltLogger.init(revoltConfiguration.logLevel)

        startSession()
    }

    /**
     * Send an event to the Revolt backend.
     * Method is asynchronous. Events are sent in configurable batches or buffered for later in case of lack of internet connection.
     * @param event Event to send.
     */
    fun sendEvent(event: Event) {
        val revoltRequestModel = RevoltRequestModel(event)
        revoltRepository.addEvent(revoltRequestModel)
    }

    private fun startSession() {
        val revoltRequestModel = RevoltRequestModel(systemEventGenerator.generateEvent())
        revoltRepository.addEvent(revoltRequestModel)
    }

    class BuilderContext {
        fun with(context: Context): BuilderTrackingId {
            return BuilderTrackingId(context)
        }
    }

    class BuilderTrackingId(private val context: Context) {
        fun trackingId(trackingId: String): BuilderSecretKey {
            return BuilderSecretKey(context, trackingId)
        }
    }

    class BuilderSecretKey(private val context: Context,
                           private val trackingId: String) {
        fun secretKey(secretKey: String): Builder {
            return Builder(context, trackingId, secretKey)
        }
    }


    class Builder(private var context: Context,
                  private var trackingId: String,
                  private var secretKey: String) {
        private var maxBatchSize: Int = DefaultConfiguration.MAX_BATCH_SIZE
        private var eventDelay: Int = DefaultConfiguration.EVENT_DELAY
        private var offlineMaxSize: Int = DefaultConfiguration.OFFLINE_MAX_SIZE
        private var endpoint: String = DefaultConfiguration.URL
        private var revoltLogLevel = DefaultConfiguration.LOG_LEVEL

        fun logLevel(revoltLogLevel: RevoltLogLevel) {
            this.revoltLogLevel = revoltLogLevel
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
            if (!hasPermission(context, Manifest.permission.INTERNET)) {
                throw IllegalArgumentException("INTERNET permission is required.")
            }

            return Revolt(createConfiguration(), context)
        }

        private fun createConfiguration(): RevoltConfiguration {
            return RevoltConfiguration(trackingId,
                    endpoint,
                    maxBatchSize,
                    eventDelay,
                    offlineMaxSize,
                    secretKey,
                    revoltLogLevel)
        }
    }
}