package rocks.revolt

import android.Manifest
import android.content.Context
import rocks.revolt.internal.RevoltService
import rocks.revolt.internal.ScreenSizeProvider
import rocks.revolt.internal.AppInstanceDataEventGenerator
import rocks.revolt.internal.configuration.ConfigurationRepository
import rocks.revolt.internal.configuration.DefaultConfiguration
import rocks.revolt.internal.configuration.RevoltConfiguration
import rocks.revolt.internal.database.DatabaseBuilder
import rocks.revolt.internal.database.DatabaseRepository
import rocks.revolt.internal.hasPermission
import rocks.revolt.internal.log.RevoltLogger
import rocks.revolt.internal.network.BackendRepository
import rocks.revolt.internal.network.RevoltApiBuilder
import rocks.revolt.internal.connection.createNetworkStateService
import java.util.concurrent.TimeUnit

/** Created by MiQUiDO on 28.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class Revolt private constructor(revoltConfiguration: RevoltConfiguration,
                                 context: Context) {

    private val appInstanceDataEventGenerator: AppInstanceDataEventGenerator
    private val revoltService: RevoltService
    private val configurationRepository: ConfigurationRepository = ConfigurationRepository(context)

    companion object {
        @JvmStatic
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
        val databaseRepository = DatabaseRepository(DatabaseBuilder(context).getEventsDao())

        revoltService = RevoltService(revoltConfiguration.eventDelayMillis,
                revoltConfiguration.maxBatchSize,
                backendRepository,
                databaseRepository,
                revoltConfiguration.firstRetryTimeSeconds,
                revoltConfiguration.maxRetryTimeSeconds,
                revoltConfiguration.offlineMaxSize,
                createNetworkStateService(context))
        appInstanceDataEventGenerator = AppInstanceDataEventGenerator(ScreenSizeProvider(context), context)
        RevoltLogger.init(revoltConfiguration.logLevel)

        startSession()
        RevoltLogger.d("Revolt has been initialized")
    }

    /**
     * Send an event to the Revolt backend.
     * Method is asynchronous. Events are sent in configurable batches or buffered for later in case of lack of internet connection.
     * @param event Event to send.
     */
    fun sendEvent(event: Event) {
        revoltService.addEvent(event)
    }

    private fun startSession() {
        revoltService.addEvent(appInstanceDataEventGenerator.generateEvent())
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
        fun secretKey(secretKey: String): BuilderEndpoint {
            return BuilderEndpoint(context, trackingId, secretKey)
        }
    }

    class BuilderEndpoint(private val context: Context,
                          private val trackingId: String,
                          private val secretKey: String) {
        fun endpoint(endpoint: String): Builder {
            return Builder(context, trackingId, secretKey, endpoint)
        }
    }

    class Builder(private val context: Context,
                  private val trackingId: String,
                  private val secretKey: String,
                  private val endpoint: String) {
        private var maxBatchSize: Int = DefaultConfiguration.MAX_BATCH_SIZE
        private var eventDelayMillis = DefaultConfiguration.EVENT_DELAY_MILLIS
        private var offlineMaxSize: Int = DefaultConfiguration.OFFLINE_MAX_SIZE
        private var revoltLogLevel = DefaultConfiguration.LOG_LEVEL
        private var firstRetryTimeSeconds = DefaultConfiguration.FIRST_RETRY_TIME_SECONDS
        private var maxRetryTimeSeconds = DefaultConfiguration.MAX_RETRY_TIME_SECONDS

        fun logLevel(revoltLogLevel: RevoltLogLevel): Revolt.Builder {
            this.revoltLogLevel = revoltLogLevel
            return this
        }

        fun maxBatchSize(size: Int): Revolt.Builder {
            this.maxBatchSize = size
            return this
        }

        fun eventDelay(delay: Long, timeUnit: TimeUnit): Revolt.Builder {
            this.eventDelayMillis = timeUnit.toMillis(delay)
            return this
        }

        fun firstRetryIntervalSeconds(time: Int): Revolt.Builder {
            this.firstRetryTimeSeconds = time
            return this
        }

        fun maxRetryIntervalSeconds(time: Int): Revolt.Builder {
            this.maxRetryTimeSeconds = time
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
            if (!hasPermission(context, Manifest.permission.ACCESS_NETWORK_STATE)) {
                throw IllegalArgumentException("ACCESS_NETWORK_STATE permission is required.")
            }
            return Revolt(createConfiguration(), context)
        }

        private fun createConfiguration(): RevoltConfiguration {
            return RevoltConfiguration(trackingId,
                    endpoint,
                    maxBatchSize,
                    eventDelayMillis,
                    offlineMaxSize,
                    secretKey,
                    revoltLogLevel,
                    firstRetryTimeSeconds,
                    maxRetryTimeSeconds)
        }
    }
}
