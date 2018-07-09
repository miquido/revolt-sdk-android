package com.miquido.revoltsdk.internal.network

import com.google.gson.JsonObject
import com.miquido.revoltsdk.Event
import com.miquido.revoltsdk.internal.model.MetaDataModel

/** Created by MiQUiDO on 09.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal data class RevoltRequestModel(private val event: Event) {

    private val metaDataModel: MetaDataModel = MetaDataModel(event.getType())

    internal fun createJson(): JsonObject {
        val json = JsonObject()
        json.add(EVENT, event.getJson())
        json.add(METADATA, metaDataModel.getJson())
        return json
    }

    companion object {
        const val METADATA = "meta"
        const val EVENT = "data"
    }
}
