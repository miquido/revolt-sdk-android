package com.miquido.revoltsdk.internal.model

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import com.miquido.revoltsdk.Event

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal data class SystemEvent(var deviceBrand: String? = null,
                       var deviceScreenSize: Double? = null,
                       var deviceScreenResolutionWidth: Int? = null,
                       var deviceScreenResolutionHeight: Int? = null,
                       var deviceModel: String? = null,
                       var appVersion: String? = null,
                       var sdkVersion: String? = null,
                       var operatingSystem: String? = null,
                       var operatingSystemVersion: String? = null,
                       var location: String? = null,
                       var language: String? = null) : Event {


    override fun getJson(): JsonObject {
        val json = JsonObject()
        json.add("deviceBrand", JsonPrimitive(deviceBrand))
        json.add("deviceScreenSize", JsonPrimitive(deviceScreenSize))
        json.add("deviceScreenResolutionWidth", JsonPrimitive(deviceScreenResolutionWidth))
        json.add("deviceScreenResolutionHeight", JsonPrimitive(deviceScreenResolutionHeight))
        json.add("deviceModel", JsonPrimitive(deviceModel))
        json.add("appVersion", JsonPrimitive(appVersion))
        json.add("sdkVersion", JsonPrimitive(sdkVersion))
        json.add("operatingSystem", JsonPrimitive(operatingSystem))
        json.add("operatingSystemVersion", JsonPrimitive(operatingSystemVersion))
        json.add("location", JsonPrimitive(location))
        json.add("language", JsonPrimitive(language))
        return json
    }

    override fun getType(): String {
        return "System"
    }
}

