package com.miquido.revoltsdk.internal.network

import com.miquido.revoltsdk.RevoltEvent
import com.miquido.revoltsdk.internal.EventsRepository
import com.miquido.revoltsdk.internal.RevoltApi
import com.miquido.revoltsdk.internal.model.ResponseModel
import com.miquido.revoltsdk.internal.packWithArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/** Created by MiQUiDO on 09.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class BackendRepository(private val revoltApi: RevoltApi) : EventsRepository {

    override fun addEvent(revoltEvent: RevoltEvent) {
        revoltApi.send(revoltEvent.createJson().packWithArray()).enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>?, t: Throwable?) {
                Timber.e(t)
            }

            override fun onResponse(call: Call<ResponseModel>?, response: Response<ResponseModel>?) {

            }
        })
    }
}
