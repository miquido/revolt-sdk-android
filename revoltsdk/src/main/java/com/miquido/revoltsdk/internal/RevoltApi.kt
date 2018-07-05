package com.miquido.revoltsdk.internal

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.miquido.revoltsdk.internal.model.RevoltEvent
import com.miquido.revoltsdk.internal.model.ResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/** Created by MiQUiDO on 28.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
interface RevoltApi {

    @POST("events")
    fun send(@Body event: JsonArray): Call<ResponseModel>
}
