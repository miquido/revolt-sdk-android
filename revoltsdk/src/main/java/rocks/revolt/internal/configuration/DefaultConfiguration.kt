package rocks.revolt.internal.configuration

import rocks.revolt.RevoltLogLevel
import java.util.concurrent.TimeUnit

/** Created by MiQUiDO on 28.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal object DefaultConfiguration {
    const val MAX_BATCH_SIZE: Int = 20
    const val EVENT_DELAY_MILLIS = 5_000L
    const val OFFLINE_MAX_SIZE: Int = 10_000
    val LOG_LEVEL = RevoltLogLevel.WARN
    const val FIRST_RETRY_TIME_SECONDS = 5
    const val MAX_RETRY_TIME_SECONDS = 600
}
