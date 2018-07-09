package com.miquido.revoltsdk

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.miquido.revoltsdk.internal.model.Event

/** Created by MiQUiDO on 29.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class RevoltEvent : Event {
    private val revoltData: JsonObject
    private val type: String

    constructor(jsonObject: JsonObject, type: String) {
        this.revoltData = jsonObject
        this.type = type
    }

    constructor(key: String, value: String, type: String) {
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
