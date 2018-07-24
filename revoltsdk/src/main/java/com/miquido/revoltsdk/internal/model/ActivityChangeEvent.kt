package com.miquido.revoltsdk.internal.model

import com.google.gson.JsonObject
import com.miquido.revoltsdk.Event

/** Created by MiQUiDO on 24.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
data class ActivityChangeEvent(val className: String,
                               val action: String) : Event {
    override fun getJson(): JsonObject {
        return JsonObject().apply {
            addProperty("className", className)
            addProperty("action", action)
        }
    }

    override fun getType(): String {
        return "app.activitiesChanges"
    }
}
