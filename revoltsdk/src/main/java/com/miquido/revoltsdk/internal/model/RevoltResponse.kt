package com.miquido.revoltsdk.internal.model

/** Created by MiQUiDO on 17.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal data class RevoltResponse(val eventResponseModel: EventResponseModel? = null, val responseStatus: ResponseStatus) {
    enum class ResponseStatus {
        RETRYABLE_GLOBAL, RETRYABLE_EVENT, PERMANENT_EVENT, OK, ERROR
    }
}
