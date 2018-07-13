package com.miquido.revoltsdk.internal.network

import com.google.gson.Gson
import com.miquido.revoltsdk.Event
import com.miquido.revoltsdk.internal.RevoltApi
import com.miquido.revoltsdk.internal.model.RevoltModel
import retrofit2.Call

/** Created by MiQUiDO on 09.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal object BackendRepository {
    private lateinit var revoltApi: RevoltApi

    fun init(revoltApi: RevoltApi) {
        this.revoltApi = revoltApi
    }

    fun addEvents(events: ArrayList<RevoltModel>): Call<ResponseModel> {
        val array = Gson().toJsonTree(events.map { it.createJson() }).asJsonArray
        return revoltApi.send(array)
    }
}
