package com.miquido.revoltsdk.internal

import com.miquido.revoltsdk.internal.model.ResponseModel
import com.miquido.revoltsdk.internal.model.RevoltEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class RevoltRepository(private val revoltApi: RevoltApi) {
    fun sendEvent(event: RevoltEvent) {
        //TODO store in the database
        revoltApi.send(event.createJson().packWithArray()).enqueue(object : Callback<ResponseModel> {
            override fun onFailure(call: Call<ResponseModel>?, t: Throwable?) {
                Timber.e(t)
            }

            override fun onResponse(call: Call<ResponseModel>?, response: Response<ResponseModel>?) {

            }
        })
    }
}
