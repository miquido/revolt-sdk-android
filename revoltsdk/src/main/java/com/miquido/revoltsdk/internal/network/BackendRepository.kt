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
internal class BackendRepository(private val revoltApi: RevoltApi) {

    private var retryCounter = 0

    companion object {
        const val MAX_RETRY_400_ERROR_NUMBER = 100
        const val STATUS_CODE_REQUEST_ERROR = 400
        const val STATUS_CODE_SERVER_ERROR = 500
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
                        else -> RevoltResponse(responseStatus = RevoltResponse.ResponseStatus.SERVER_ERROR)
                    }
                }
                response.code() in STATUS_CODE_REQUEST_ERROR..(STATUS_CODE_SERVER_ERROR - 1) -> RevoltResponse(responseStatus = RevoltResponse.ResponseStatus.REQUEST_ERROR)
                else -> RevoltResponse(responseStatus = RevoltResponse.ResponseStatus.SERVER_ERROR)
            }
        } catch (exception: IOException) {
            RevoltResponse(responseStatus = RevoltResponse.ResponseStatus.SERVER_ERROR)
        }
    }

    private fun successfulResponse(eventResponseModel: EventResponseModel): RevoltResponse {
        val eventError = eventResponseModel.eventError
        return if (eventError != null) {
            return if (eventError.errorCode == STATUS_CODE_SERVER_ERROR) {
                RevoltResponse(responseStatus = RevoltResponse.ResponseStatus.SERVER_EVENT_ERROR)
            } else {
                ++retryCounter
                if (retryCounter >= MAX_RETRY_400_ERROR_NUMBER) {
                    RevoltResponse(responseStatus = RevoltResponse.ResponseStatus.REQUEST_ERROR)
                } else {
                    RevoltResponse(responseStatus = RevoltResponse.ResponseStatus.REQUEST_EVENT_ERROR)
                }
            }
        } else {
            retryCounter = 0
            RevoltResponse(eventResponseModel, RevoltResponse.ResponseStatus.OK)
        }
    }

}
