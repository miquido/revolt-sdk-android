package com.miquido.revoltsdk.internal

import android.content.Context
import android.support.v4.content.PermissionChecker.PERMISSION_GRANTED
import android.support.v4.content.PermissionChecker.checkCallingOrSelfPermission
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.miquido.revoltsdk.internal.model.Event
import com.miquido.revoltsdk.internal.model.MetaDataModel
import kotlin.experimental.and


/** Created by MiQUiDO on 28.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
fun hasPermission(context: Context, permission: String): Boolean {
    return context.checkCallingOrSelfPermission(permission) == PERMISSION_GRANTED
}

fun generateJson(dataObject: JsonObject, metadataObject: JsonObject): JsonObject {
    val json = JsonObject()
    json.add(Event.EVENT_MODEL, dataObject)
    json.add(MetaDataModel.METADATA_MODEL, metadataObject)
    return json
}

fun JsonObject.packWithArray(): JsonArray {
    val jsonArray = JsonArray()
    jsonArray.add(this)
    return jsonArray
}

fun ByteArray.toHex() = this.joinToString(separator = "") { it.toInt().and(0xff).toString(16).padStart(2, '0') }
