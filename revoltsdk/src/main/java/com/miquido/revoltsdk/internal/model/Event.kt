package com.miquido.revoltsdk.internal.model

import com.google.gson.JsonObject

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
interface Event {
    fun getJson(): JsonObject
    fun getType(): String

    companion object {
        const val EVENT_MODEL = "data"
    }
}
