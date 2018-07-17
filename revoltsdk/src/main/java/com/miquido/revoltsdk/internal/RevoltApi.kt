package com.miquido.revoltsdk.internal

import com.google.gson.JsonArray
import com.miquido.revoltsdk.internal.network.SendEventResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/** Created by MiQUiDO on 28.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal interface RevoltApi {

    @POST("events")
    fun send(@Body event: JsonArray): Call<SendEventResponse>
}
