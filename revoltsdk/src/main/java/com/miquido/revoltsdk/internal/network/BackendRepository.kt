package com.miquido.revoltsdk.internal.network

import com.google.gson.Gson
import com.miquido.revoltsdk.internal.model.EventModel
import java.io.IOException

/** Created by MiQUiDO on 09.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class BackendRepository(private val revoltApi: RevoltApi) {

    companion object {
        const val STATUS_CODE_SERVER_ERROR = 500
    }

    fun sendEvents(events: ArrayList<EventModel>): SendEventsResult {
        val array = Gson().toJsonTree(events.map { it.createJson() }).asJsonArray
        return try {
            val response = revoltApi.send(array).execute()
            when {
                response.isSuccessful -> {
                    val responseModel = response.body()
                    when {
                        responseModel != null -> successfulResponse(responseModel)
                        else -> SendEventsResult(SendEventsResult.Status.SERVER_ERROR)
                    }
                }
                response.code() in 400..499 -> SendEventsResult(SendEventsResult.Status.REQUEST_ERROR)
                else -> SendEventsResult(SendEventsResult.Status.SERVER_ERROR)
            }
        } catch (exception: IOException) {
            SendEventsResult(SendEventsResult.Status.SERVER_ERROR)
        }
    }

    private fun successfulResponse(sendEventResponse: SendEventResponse): SendEventsResult {
        val eventError = sendEventResponse.eventError
        val status = if (eventError != null) {
            when (eventError.errorCode) {
                STATUS_CODE_SERVER_ERROR -> SendEventsResult.Status.SERVER_EVENT_ERROR
                else -> SendEventsResult.Status.REQUEST_EVENT_ERROR
            }
        } else {
            SendEventsResult.Status.OK
        }
        return SendEventsResult(status, sendEventResponse.eventsAccepted)
    }

}
