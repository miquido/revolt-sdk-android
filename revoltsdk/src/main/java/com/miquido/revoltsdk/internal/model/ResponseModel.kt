package com.miquido.revoltsdk.internal.model

/** Created by MiQUiDO on 29.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class ResponseModel {
    var eventsAccepted: List<Int>? = null
    val eventError: EventError? = null

    class EventError {
        var eventOffset: Int? = null
        var eventId: String? = null
        var errorCode: Int? = null
        var errorMessage: String? = null
    }
}
