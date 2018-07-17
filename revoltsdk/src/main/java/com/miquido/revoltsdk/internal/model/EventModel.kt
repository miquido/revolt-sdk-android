package com.miquido.revoltsdk.internal.model

import com.google.gson.JsonObject
import com.miquido.revoltsdk.Event

/** Created by MiQUiDO on 09.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal data class EventModel(private val event: Event) {

    private val metaDataModel: MetaDataModel = MetaDataModel(event.getType())

    internal fun createJson(): JsonObject {
        val json = JsonObject()
        json.add(EVENT, event.getJson())
        json.add(METADATA, metaDataModel.getJson())
        return json
    }

    fun getTimestamp(): Long {
        return metaDataModel.getTimestamp()
    }

    companion object {
        const val METADATA = "meta"
        const val EVENT = "data"
    }
}
