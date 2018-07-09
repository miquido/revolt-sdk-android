package com.miquido.revoltsdk.internal.configuration

import com.miquido.revoltsdk.RevoltLogLevel

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal data class RevoltConfiguration(
        var trackingId: String,
        var endpoint: String,
        var maxBatchSize: Int,
        var eventDelay: Int,
        var offlineMaxSize: Int,
        var secretKey: String,
        var logLevel: RevoltLogLevel)
