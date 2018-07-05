package com.miquido.revoltsdk.internal.configuration

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
data class RevoltConfiguration(
        var trackingId: String,
        var endpoint: String,
        var maxBatchSize: Int,
        var eventDelay: Int,
        var offlineMaxSize: Int,
        var secretKey: String)
