package com.miquido.revoltsdk

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.miquido.revoltsdk.internal.model.EventImpl

/** Created by MiQUiDO on 09.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class RevoltEvents {
    companion object {
        fun fromJson(type: String, jsonObject: JsonObject): Event {
            return EventImpl(type, jsonObject)
        }

        fun fromKeyValue(type: String, key: String, value: String): Event {
            return EventImpl(type, key, value)
        }

        fun fromMap(type: String, map: Map<String, Any>): Event {
            return EventImpl(type, Gson().toJsonTree(map).asJsonObject)
        }

        fun fromPairs(type: String, vararg pairs: Pair<String, Any>): Event {
            return EventImpl(type, Gson().toJsonTree(pairs).asJsonObject)
        }
    }
}
