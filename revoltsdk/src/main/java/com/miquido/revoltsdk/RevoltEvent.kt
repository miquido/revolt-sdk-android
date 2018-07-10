package com.miquido.revoltsdk

import com.google.gson.Gson
import com.google.gson.JsonObject

/** Created by MiQUiDO on 09.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class RevoltEvent : Event {
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

    constructor(type: String, map: Map<String, Any>) {
        this.revoltData = Gson().toJsonTree(map).asJsonObject
        this.type = type
    }

    constructor(type: String, vararg pairs: Pair<String, Any>) {
        this.revoltData = Gson().toJsonTree(pairs).asJsonObject
        this.type = type
    }

    override fun getJson(): JsonObject {
        return revoltData
    }

    override fun getType(): String {
        return type
    }
}
