package com.miquido.revoltsdk.internal

import com.miquido.revoltsdk.internal.model.RevoltEvent

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class RevoltRepository(private val revoltApi: RevoltApi) {
    fun sendEvent(event: RevoltEvent) {
        //TODO store in the database
        revoltApi.send(event)
    }
}
