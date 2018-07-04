package com.miquido.revoltsdk.internal.model

import android.os.Bundle
import com.google.gson.JsonObject
import com.miquido.revoltsdk.internal.generateJson


/** Created by MiQUiDO on 29.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class RevoltEvent {
    private val revoltEvent: Event
    private val metadataModel: MetaDataModel

    constructor(bundle: Bundle) {
        revoltEvent = CustomEvent(bundle)
        metadataModel = MetaDataModel(revoltEvent.getType())
    }

    constructor(key: String, value: String) {
        val bundle = Bundle()
        bundle.putString(key, value)
        revoltEvent = CustomEvent(bundle)
        metadataModel = MetaDataModel(revoltEvent.getType())
    }

    constructor(revoltEvent: Event) {
        this.revoltEvent = revoltEvent
        metadataModel = MetaDataModel(revoltEvent.getType())
    }

    fun createJson(): JsonObject {
        return generateJson(revoltEvent.getJson(), metadataModel.getJson())
    }

    enum class Type {
        CUSTOM,
        USER,
        SYSTEM
    }
}
