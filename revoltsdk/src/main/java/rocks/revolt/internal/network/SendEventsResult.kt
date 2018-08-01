package rocks.revolt.internal.network

/** Created by MiQUiDO on 17.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal data class SendEventsResult(val responseStatus: Status, val eventsAccepted: Int = 0) {
    enum class Status {
        SERVER_ERROR, SERVER_EVENT_ERROR, REQUEST_ERROR, REQUEST_EVENT_ERROR, OK
    }
}
