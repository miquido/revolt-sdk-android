package com.miquido.revoltsdk.internal.configuration

import com.miquido.revoltsdk.RevoltLogLevel
import java.util.concurrent.TimeUnit

/** Created by MiQUiDO on 28.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class DefaultConfiguration {
    companion object {
        const val URL: String = "https://api.revolt.rocks/api/v1"
        const val MAX_BATCH_SIZE: Int = 20
        val EVENT_DELAY: EventDelay = EventDelay(5, TimeUnit.SECONDS)
        const val OFFLINE_MAX_SIZE: Int = 10_000
        val LOG_LEVEL = RevoltLogLevel.WARN
    }
}
