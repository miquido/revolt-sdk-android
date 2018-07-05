package com.miquido.revoltsdk.internal.model

import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive


/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class CustomEvent(private val obj: Any) : Event {

    override fun getJson(): JsonObject {
        return Gson().toJsonTree(obj).asJsonObject
    }

    override fun getType(): RevoltEvent.Type {
        return RevoltEvent.Type.CUSTOM
    }
}
