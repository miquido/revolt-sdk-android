package com.miquido.revoltsdk.internal.model

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
data class SystemEvent(var deviceBrand: String? = null,
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
        json.add(DEVICE_BRAND, JsonPrimitive(deviceBrand))
        json.add(DEVICE_SCREEN_SIZE, JsonPrimitive(deviceScreenSize))
        json.add(DEVICE_SCREEN_RESOLUTION_WIDTH, JsonPrimitive(deviceScreenResolutionWidth))
        json.add(DEVICE_SCREEN_RESOLUTION_HEIGHT, JsonPrimitive(deviceScreenResolutionHeight))
        json.add(DEVICE_MODEL, JsonPrimitive(deviceModel))
        json.add(APP_VERSION, JsonPrimitive(appVersion))
        json.add(SDK_VERSION, JsonPrimitive(sdkVersion))
        json.add(OPERATING_SYSTEM, JsonPrimitive(operatingSystem))
        json.add(OPERATING_SYSTEM_VERSION, JsonPrimitive(operatingSystemVersion))
        json.add(LOCATION, JsonPrimitive(location))
        json.add(LANGUAGE, JsonPrimitive(language))
        return json
    }

    override fun getType(): RevoltEvent.Type {
        return RevoltEvent.Type.SYSTEM
    }

    companion object {
        const val DEVICE_BRAND = "deviceBrand"
        const val DEVICE_SCREEN_SIZE = "deviceScreenSize"
        const val DEVICE_SCREEN_RESOLUTION_WIDTH = "deviceScreenResolutionWidth"
        const val DEVICE_SCREEN_RESOLUTION_HEIGHT = "deviceScreenResolutionHeight"
        const val DEVICE_MODEL = "deviceModel"
        const val APP_VERSION = "appVersion"
        const val SDK_VERSION = "sdkVersion"
        const val OPERATING_SYSTEM = "operatingSystem"
        const val OPERATING_SYSTEM_VERSION = "operatingSystemVersion"
        const val LOCATION = "location"
        const val LANGUAGE = "language"
    }
}

