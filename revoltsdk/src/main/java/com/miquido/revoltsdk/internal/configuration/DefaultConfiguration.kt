package com.miquido.revoltsdk.internal.configuration

import com.miquido.revoltsdk.RevoltLogLevel
import java.util.concurrent.TimeUnit

/** Created by MiQUiDO on 28.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal object DefaultConfiguration {
    const val URL: String = "https://api.revolt.rocks/api/v1"
    const val MAX_BATCH_SIZE: Int = 20
    const val EVENT_DELAY = 5_000L
    const val OFFLINE_MAX_SIZE: Int = 10_000
    val LOG_LEVEL = RevoltLogLevel.WARN
    const val FIRST_RETRY_TIME = 5
    const val MAX_RETRY_TIME = 600
}
