package com.miquido.revoltsdk

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** Created by MiQUiDO on 28.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class NetworkConfiguration(endpointUrl: String) {
    val revoltApi: RevoltApi

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(endpointUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        revoltApi = retrofit.create(RevoltApi::class.java)
    }
}
