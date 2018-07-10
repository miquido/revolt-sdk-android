package com.miquido.revoltsdk.internal

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import com.google.gson.JsonObject
import com.miquido.revoltsdk.BuildConfig
import com.miquido.revoltsdk.Event
import com.miquido.revoltsdk.RevoltEvent
import java.util.Locale

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class AppInstanceDataEventGenerator(private val screenSizeProvider: ScreenSizeProvider,
                                             private val context: Context) {
    @SuppressLint("HardwareIds")
    fun generateEvent(): Event {
        val jsonObject = JsonObject().apply {
            add("app", JsonObject().apply {
                addProperty("type", "mobile")
                addProperty("version", context.packageManager.getPackageInfo(context.packageName, 0).versionName)
                addProperty("sdkVersion", BuildConfig.VERSION_CODE.toString())
                addProperty("code", context.javaClass.name)
            })
            add("device", JsonObject().apply {
                addProperty("brand", Build.MANUFACTURER)
                addProperty("model", Build.MODEL)
                addProperty("screenSizeIn", screenSizeProvider.sizeIn)
                addProperty("screenResWidthPx", screenSizeProvider.sizePx.x)
                addProperty("screenResHeightPx", screenSizeProvider.sizePx.y)
                addProperty("os", "android")
                addProperty("osVersion", Build.VERSION.RELEASE)
                addProperty("language", Locale.getDefault().toString())
            })
            add("mobileDevice", JsonObject().apply {
                addProperty("deviceId", Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID))
            })
        }
        return RevoltEvent("system.appInstanceData", jsonObject)
    }
}
