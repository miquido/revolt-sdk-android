package com.miquido.revoltsdk.internal.network

import com.google.gson.Gson
import com.miquido.revoltsdk.internal.RevoltApi
import com.miquido.revoltsdk.internal.model.RevoltModel

/** Created by MiQUiDO on 09.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class BackendRepository(private var revoltApi: RevoltApi) {

    fun addEvents(events: ArrayList<RevoltModel>): ResponseModel? {
        val array = Gson().toJsonTree(events.map { it.createJson() }).asJsonArray
        val response = revoltApi.send(array).execute()
        val responseModel = response.body()
        when {
            responseModel?.eventError?.errorCode == STATUS_CODE_WRONG_EVENT -> responseModel.responseStatus = ResponseModel.ResponseStatus.RETRY
            response.code() == STATUS_CODE_OK -> responseModel?.responseStatus = ResponseModel.ResponseStatus.OK
            else -> responseModel?.responseStatus = ResponseModel.ResponseStatus.ERROR
        }

        return response.body()
    }

    companion object {
        const val STATUS_CODE_OK = 200
        const val STATUS_CODE_SERVER_ERROR = 500
        const val STATUS_CODE_WRONG_EVENT = 400
    }
}
