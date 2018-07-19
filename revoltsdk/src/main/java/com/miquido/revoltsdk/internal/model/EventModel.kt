package com.miquido.revoltsdk.internal.model

import com.google.gson.JsonObject
import com.miquido.revoltsdk.Event
import java.util.UUID

/** Created by MiQUiDO on 09.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal data class EventModel(val metaData: MetaDataModel,
                               val data: JsonObject) {

    internal fun toJson(): JsonObject =
            JsonObject().apply {
                add("meta", metaData.getJson())
                add("data", data)
            }
}

internal fun createNewEventModel(event: Event) : EventModel {
    return EventModel(
            MetaDataModel(UUID.randomUUID().toString(),
                    event.getType(),
                    System.currentTimeMillis()),
            event.getJson())
}
