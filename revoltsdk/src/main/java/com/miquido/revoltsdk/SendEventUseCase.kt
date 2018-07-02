package com.miquido.revoltsdk

/** Created by MiQUiDO on 30.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */

class SendEventUseCase(private val revoltApi: RevoltApi) {
    fun send(event: RevoltEvent) {
        revoltApi.send(event)
    }
}
