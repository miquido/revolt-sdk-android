package com.miquido.revoltsdk.internal.network

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.miquido.revoltsdk.RevoltEvent
import com.miquido.revoltsdk.internal.model.Event
import com.miquido.revoltsdk.internal.model.MetaDataModel

/** Created by MiQUiDO on 09.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
data class RevoltRequestModel(private val event: Event) {

    private val metaDataModel: MetaDataModel = MetaDataModel(event.getType())

    internal fun createJson(): JsonObject {
        val json = JsonObject()
        json.add(Event.EVENT_MODEL, event.getJson())
        json.add(MetaDataModel.METADATA_MODEL, metaDataModel.getJson())
        return json
    }

}
