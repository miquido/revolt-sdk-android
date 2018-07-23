package com.miquido.revoltsdk

import com.google.gson.JsonObject
import com.miquido.revoltsdk.internal.asJsonObject

/** Created by MiQUiDO on 19.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
data class UserSignOutEvent(private val appUserId: String) : Event {

    override fun getJson(): JsonObject = appUserId.asJsonObject()

    override fun getType() = "user.signedOut"
}
