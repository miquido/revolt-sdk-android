package com.miquido.revoltsdk

import android.os.Bundle
import com.google.gson.JsonObject
import com.miquido.revoltsdk.internal.model.CustomEvent
import com.miquido.revoltsdk.internal.model.Event
import com.miquido.revoltsdk.internal.model.MetaDataModel


/** Created by MiQUiDO on 29.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class RevoltEvent {
    private val revoltEvent: Event
    private lateinit var metadataModel: MetaDataModel

    constructor(any: Any) {
        revoltEvent = CustomEvent(any)
    }

    constructor(key: String, value: String) {
        val bundle = Bundle()
        bundle.putString(key, value)
        revoltEvent = CustomEvent(bundle)
    }

    constructor(revoltEvent: Event) {
        this.revoltEvent = revoltEvent
    }

    fun generateMetadata() {
        metadataModel = MetaDataModel(revoltEvent.getType())
    }

    fun createJson(): JsonObject {
        return generateJson(revoltEvent.getJson(), metadataModel.getJson())
    }

    private fun generateJson(dataObject: JsonObject, metadataObject: JsonObject): JsonObject {
        val json = JsonObject()
        json.add(Event.EVENT_MODEL, dataObject)
        json.add(MetaDataModel.METADATA_MODEL, metadataObject)
        return json
    }
}
