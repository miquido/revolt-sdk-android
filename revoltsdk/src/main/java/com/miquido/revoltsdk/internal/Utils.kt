package com.miquido.revoltsdk.internal

import android.content.Context
import android.support.v4.content.PermissionChecker.PERMISSION_GRANTED
import android.support.v4.content.PermissionChecker.checkCallingOrSelfPermission
import com.google.gson.JsonObject
import com.miquido.revoltsdk.internal.model.Event
import com.miquido.revoltsdk.internal.model.MetaDataModel


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
