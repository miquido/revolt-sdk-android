package com.miquido.revoltsdk.internal

import com.miquido.revoltsdk.RevoltEvent

/** Created by MiQUiDO on 09.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
interface EventsRepository {
    fun addEvent(revoltEvent: RevoltEvent)
}
