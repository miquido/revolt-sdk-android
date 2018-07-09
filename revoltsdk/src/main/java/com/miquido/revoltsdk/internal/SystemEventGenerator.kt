package com.miquido.revoltsdk.internal

import android.content.Context
import android.os.Build
import com.miquido.revoltsdk.BuildConfig
import com.miquido.revoltsdk.Event
import com.miquido.revoltsdk.internal.model.SystemEvent
import java.util.Locale

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class SystemEventGenerator(private val screenSizeProvider: ScreenSizeProvider,
                                    private val context: Context) {
    fun generateEvent(): Event {
        val sessionEvent = SystemEvent()
        sessionEvent.deviceBrand = Build.MANUFACTURER
        sessionEvent.deviceModel = Build.MODEL
        sessionEvent.operatingSystem = "Android"
        sessionEvent.operatingSystemVersion = Build.VERSION.RELEASE
        sessionEvent.deviceScreenSize = screenSizeProvider.sizeIn
        sessionEvent.deviceScreenResolutionHeight = screenSizeProvider.sizePx.y
        sessionEvent.deviceScreenResolutionWidth = screenSizeProvider.sizePx.x
        sessionEvent.appVersion = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        sessionEvent.sdkVersion = BuildConfig.VERSION_CODE.toString()
        sessionEvent.language = Locale.getDefault().toString()
        sessionEvent.location = "Location"
        sessionEvent.code = "${context.packageName}.${context.applicationInfo.loadLabel(context.packageManager)}"
        return sessionEvent
    }
}
