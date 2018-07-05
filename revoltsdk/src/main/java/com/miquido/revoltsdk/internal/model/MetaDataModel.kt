package com.miquido.revoltsdk.internal.model

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import java.util.*

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
data class MetaDataModel(var type: RevoltEvent.Type) {
    var id: String = UUID.randomUUID().toString()
    var timestamp: Long = System.currentTimeMillis()


    fun getJson(): JsonObject {
        val json = JsonObject()
        json.add(ID, JsonPrimitive(id))
        json.add(TYPE, JsonPrimitive(type.toString()))
        json.add(TIME_STAMP, JsonPrimitive(timestamp))
        return json
    }

    companion object {
        const val ID: String = "id"
        const val TIME_STAMP: String = "timestamp"
        const val TYPE: String = "type"
        const val METADATA_MODEL = "meta"
    }
}
