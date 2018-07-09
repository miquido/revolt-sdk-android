package com.miquido.revoltsdk.internal.model

import android.provider.Settings
import com.google.gson.JsonObject
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
                                var language: String? = null,
                                var code: String? = null) : Event {


    override fun getJson(): JsonObject {
        val json = JsonObject()
        val appObject = JsonObject()
        val deviceObject = JsonObject()
        val mobileDeviceObject = JsonObject()

        appObject.addProperty("type", "mobile")
        appObject.addProperty("appVersion", appVersion)
        appObject.addProperty("sdkVersion", sdkVersion)
        appObject.addProperty("code", code)

        mobileDeviceObject.addProperty("deviceId", Settings.Secure.ANDROID_ID)

        deviceObject.addProperty("deviceBrand", deviceBrand)
        deviceObject.addProperty("deviceScreenSize", deviceScreenSize)
        deviceObject.addProperty("deviceScreenResolutionWidth", deviceScreenResolutionWidth)
        deviceObject.addProperty("deviceScreenResolutionHeight", deviceScreenResolutionHeight)
        deviceObject.addProperty("deviceModel", deviceModel)
        deviceObject.addProperty("operatingSystem", operatingSystem)
        deviceObject.addProperty("operatingSystemVersion", operatingSystemVersion)
        deviceObject.addProperty("location", location)
        deviceObject.addProperty("language", language)

        json.add("app", appObject)
        json.add("device", deviceObject)
        json.add("mobileDevice", mobileDeviceObject)

        return json
    }

    override fun getType(): String {
        return "System"
    }
}

