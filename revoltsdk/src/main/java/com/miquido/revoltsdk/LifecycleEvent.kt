package com.miquido.revoltsdk

import com.google.gson.JsonObject

/** Created by MiQUiDO on 31.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class LifecycleEvent(private val className: String,
                     private val action: String) : Event {

    override fun getJson(): JsonObject {
        return JsonObject().apply {
            addProperty("name", className)
        }
    }

    override fun getType(): String {
        return "ui.activity.$action"
    }
}
