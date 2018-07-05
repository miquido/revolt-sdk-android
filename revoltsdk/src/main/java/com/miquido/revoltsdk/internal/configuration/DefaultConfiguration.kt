package com.miquido.revoltsdk.internal.configuration

/** Created by MiQUiDO on 28.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class DefaultConfiguration {
    companion object {
        const val URL: String = "https://www.miquido.com"
        const val MAX_BATCH_SIZE: Int = 20
        const val EVENT_DELAY: Int = 500
        const val OFFLINE_MAX_SIZE: Int = 10_000
    }
}
