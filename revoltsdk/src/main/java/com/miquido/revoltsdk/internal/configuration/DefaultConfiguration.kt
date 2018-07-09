package com.miquido.revoltsdk.internal.configuration

import com.miquido.revoltsdk.RevoltLogLevel

/** Created by MiQUiDO on 28.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class DefaultConfiguration {
    companion object {
        const val URL: String = "http://revoltapi.eu-central-1.elasticbeanstalk.com"
        const val MAX_BATCH_SIZE: Int = 20
        const val EVENT_DELAY: Int = 500
        const val OFFLINE_MAX_SIZE: Int = 10_000
        val LOG_LEVEL = RevoltLogLevel.WARN
    }
}
