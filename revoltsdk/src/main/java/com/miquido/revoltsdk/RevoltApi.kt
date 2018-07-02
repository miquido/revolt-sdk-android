package com.miquido.revoltsdk

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/** Created by MiQUiDO on 28.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
interface RevoltApi {

    @GET("")
    fun send(event: RevoltEvent): Call<ResponseModel>
}
