package com.miquido.revoltsdk.internal

import com.miquido.revoltsdk.internal.model.RevoltEvent
import com.miquido.revoltsdk.internal.model.ResponseModel
import retrofit2.Call
import retrofit2.http.GET

/** Created by MiQUiDO on 28.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
interface RevoltApi {

    @GET("")
    fun send(event: RevoltEvent): Call<ResponseModel>
}
