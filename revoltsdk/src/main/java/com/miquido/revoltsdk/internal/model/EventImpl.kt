package com.miquido.revoltsdk.internal.model

import com.google.gson.JsonObject
import com.miquido.revoltsdk.Event

/** Created by MiQUiDO on 29.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class EventImpl : Event {
    private val revoltData: JsonObject
    private val type: String

    constructor(type: String, jsonObject: JsonObject) {
        this.revoltData = jsonObject
        this.type = type
    }

    constructor(type: String, key: String, value: String) {
        val jsonObject = JsonObject()
        jsonObject.addProperty(key, value)
        this.revoltData = jsonObject
        this.type = type
    }

    override fun getJson(): JsonObject {
        return revoltData
    }

    override fun getType(): String {
        return type
    }
}
