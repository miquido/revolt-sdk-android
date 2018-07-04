package com.miquido.revoltsdk.internal

import com.google.gson.JsonObject
import com.miquido.revoltsdk.internal.model.RevoltEvent
import com.miquido.revoltsdk.internal.model.ResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/** Created by MiQUiDO on 28.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
interface RevoltApi {

    @POST("")
    fun send(@Body event: JsonObject): Call<ResponseModel>
}
