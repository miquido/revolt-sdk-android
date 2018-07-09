package com.miquido.revoltsdk.internal

import com.miquido.revoltsdk.internal.network.RevoltRequestModel

/** Created by MiQUiDO on 09.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal interface EventsRepository {
    fun addEvent(event: RevoltRequestModel)
}
