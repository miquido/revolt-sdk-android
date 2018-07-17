package com.miquido.revoltsdk.internal.model

/** Created by MiQUiDO on 17.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal data class RevoltResponse(val eventResponseModel: EventResponseModel? = null, val responseStatus: ResponseStatus) {
    enum class ResponseStatus {
        SERVER_ERROR, SERVER_EVENT_ERROR, REQUEST_ERROR, REQUEST_EVENT_ERROR, OK
    }
}
