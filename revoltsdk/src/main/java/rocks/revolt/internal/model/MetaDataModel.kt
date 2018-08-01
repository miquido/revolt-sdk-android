package rocks.revolt.internal.model

import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal data class MetaDataModel(val id: String, val type: String, val timestamp: Long) {

    fun getJson(): JsonObject =
            JsonObject().apply {
                add("id", JsonPrimitive(id))
                add("timestamp", JsonPrimitive(timestamp))
                add("type", JsonPrimitive(type))
            }
}
