package com.miquido.revoltsdk.internal.network

/** Created by MiQUiDO on 29.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal data class ResponseModel(var eventsAccepted: Int,
                                  val eventError: EventError?) {

    class EventError {
        var eventOffset: Int? = null
        var eventId: String? = null
        var errorCode: Int? = null
        var errorMessage: String? = null
    }
}
