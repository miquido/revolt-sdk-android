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
import com.miquido.revoltsdk.internal.network.BackendRepository
import com.miquido.revoltsdk.internal.network.RevoltApiBuilder
import timber.log.Timber

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
        revoltRepository.addEvent(revoltEvent)
    }

    private fun startSession() {
        revoltRepository.addEvent(systemEventGenerator.generateEvent())
    }

    class BuilderContext {
        fun with(context: Context): BuilderSecretKey {
            return BuilderSecretKey(context)
        }
    }

    class BuilderSecretKey(private val context: Context) {
        fun secretKey(secretKey: String): BuilderTrackingId {
            return BuilderTrackingId(context, secretKey)
        }
    }

    class BuilderTrackingId(private val context: Context,
                            private val secretKey: String) {
        fun trackingId(trackingId: String): BuilderEndpoint {
            return BuilderEndpoint(context, secretKey, trackingId)
        }
    }

    class BuilderEndpoint(private val context: Context,
                          private val secretKey: String,
                          private val trackingId: String) {
        fun endpoint(endpoint: String): Builder {
            return Builder(context, secretKey, trackingId, endpoint)
        }
    }

    class Builder(private var context: Context,
                  private var secretKey: String,
                  private var trackingId: String,
                  private var endpoint: String) {
        private var maxBatchSize: Int = DefaultConfiguration.MAX_BATCH_SIZE
        private var eventDelay: Int = DefaultConfiguration.EVENT_DELAY
        private var offlineMaxSize: Int = DefaultConfiguration.OFFLINE_MAX_SIZE

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
                    secretKey)
        }
    }
}
