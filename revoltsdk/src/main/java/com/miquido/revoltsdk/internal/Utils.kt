package com.miquido.revoltsdk.internal

import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import com.google.gson.JsonParser


/** Created by MiQUiDO on 28.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal fun hasPermission(context: Context, permission: String): Boolean {
    return context.checkCallingOrSelfPermission(permission) == PERMISSION_GRANTED
}

internal fun ByteArray.toHex() = this.joinToString(separator = "") { it.toInt().and(0xff).toString(16).padStart(2, '0') }

internal fun Int.secondsToMillis() = this * 1000L

internal fun String.asJsonObject() = JsonParser().parse(this).asJsonObject
