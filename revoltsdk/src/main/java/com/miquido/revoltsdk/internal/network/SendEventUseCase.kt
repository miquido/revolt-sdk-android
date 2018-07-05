package com.miquido.revoltsdk.internal.network

import com.miquido.revoltsdk.internal.RevoltRepository
import com.miquido.revoltsdk.internal.model.RevoltEvent

/** Created by MiQUiDO on 30.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */

class SendEventUseCase(private val revoltRepository: RevoltRepository) {
    fun send(event: RevoltEvent) {
        revoltRepository.sendEvent(event)
    }
}
