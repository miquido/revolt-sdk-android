package com.miquido.revoltsdk.internal.network

import com.miquido.revoltsdk.internal.RevoltApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** Created by MiQUiDO on 28.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class NetworkConfiguration(endpointUrl: String, secretKey: String, appInstanceId: String) {
    val revoltApi: RevoltApi

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("$endpointUrl/api/v1/$secretKey/$appInstanceId/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        revoltApi = retrofit.create(RevoltApi::class.java)
    }
}
