package com.miquido.revoltsdk.internal.network

import com.google.gson.Gson
import com.miquido.revoltsdk.internal.RevoltApi
import com.miquido.revoltsdk.internal.model.EventRequestModel
import com.miquido.revoltsdk.internal.model.EventResponseModel
import com.miquido.revoltsdk.internal.model.RevoltResponse
import java.io.IOException

/** Created by MiQUiDO on 09.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class BackendRepository(private var revoltApi: RevoltApi) {

    private var retryCounter = 0

    companion object {
        const val MAX_RETRY_400_ERROR_NUMBER = 100
        const val STATUS_CODE_RETRY = 500
    }

    fun addEvents(events: ArrayList<EventRequestModel>): RevoltResponse {
        val array = Gson().toJsonTree(events.map { it.createJson() }).asJsonArray
        return try {
            val response = revoltApi.send(array).execute()
            when {
                response.isSuccessful -> {
                    val responseModel = response.body()
                    when {
                        responseModel != null -> successfulResponse(responseModel)
                        else -> RevoltResponse(responseStatus = RevoltResponse.ResponseStatus.RETRYABLE_GLOBAL)
                    }
                }
                else -> RevoltResponse(responseStatus = RevoltResponse.ResponseStatus.RETRYABLE_GLOBAL)
            }
        } catch (exception: IOException) {
            RevoltResponse(responseStatus = RevoltResponse.ResponseStatus.RETRYABLE_GLOBAL)
        }
    }

    private fun successfulResponse(eventResponseModel: EventResponseModel): RevoltResponse {
        val eventError = eventResponseModel.eventError
        return if (eventError != null) {
            return if (eventError.errorCode == STATUS_CODE_RETRY) {
                RevoltResponse(responseStatus = RevoltResponse.ResponseStatus.PERMANENT_EVENT)
            } else {
                ++retryCounter
                if (retryCounter >= MAX_RETRY_400_ERROR_NUMBER) {
                    RevoltResponse(responseStatus = RevoltResponse.ResponseStatus.RETRYABLE_EVENT)
                } else {
                    RevoltResponse(responseStatus = RevoltResponse.ResponseStatus.PERMANENT_EVENT)
                }
            }
        } else {
            RevoltResponse(eventResponseModel, RevoltResponse.ResponseStatus.OK)
        }
    }

}
