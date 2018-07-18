package com.miquido.revoltsdk.internal.model

import com.google.gson.JsonObject
import com.miquido.revoltsdk.Event

/** Created by MiQUiDO on 09.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class EventModel {

    private val metaDataModel: MetaDataModel
    private val event: Event

    constructor(event: Event) : this(event, MetaDataModel(event.getType()))

    constructor(event: Event, metaDataModel: MetaDataModel) {
        this.metaDataModel = metaDataModel
        this.event = event
    }

    internal fun createJson(): JsonObject {
        val json = JsonObject()
        json.add(EVENT, event.getJson())
        json.add(METADATA, metaDataModel.getJson())
        return json
    }

    fun getTimestamp(): Long {
        return metaDataModel.getTimestamp()
    }

    fun getType(): String {
        return metaDataModel.getType()
    }

    companion object {
        const val METADATA = "meta"
        const val EVENT = "data"
    }
}
