package com.miquido.revoltsdk.internal.model

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import java.util.UUID

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class MetaDataModel {
    private var id: String = UUID.randomUUID().toString()
    private var timestamp: Long
    private var type: String

    constructor(type: String) : this(type, System.currentTimeMillis())

    constructor(type: String, timestamp: Long) {
        this.type = type
        this.timestamp = timestamp
    }

    fun getJson(): JsonObject {
        val json = JsonObject()
        json.add(ID, JsonPrimitive(id))
        json.add(TYPE, JsonPrimitive(type))
        json.add(TIMESTAMP, JsonPrimitive(timestamp))
        return json
    }

    fun getTimestamp(): Long {
        return timestamp
    }

    fun getType(): String {
        return type
    }

    companion object {
        const val ID: String = "id"
        const val TIMESTAMP: String = "timestamp"
        const val TYPE: String = "type"
    }
}
