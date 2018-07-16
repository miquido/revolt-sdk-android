package com.miquido.revoltsdk.internal.network

import com.miquido.revoltsdk.internal.RevoltApi
import okhttp3.Credentials
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/** Created by MiQUiDO on 28.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class RevoltApiBuilder(private val endpointUrl: String,
                                private val appInstanceId: String,
                                private val trackingId: String,
                                private val secretKey: String) {

    fun getRevoltApi(): RevoltApi {
        val client = OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor {
                    val request = it.request()
                            .newBuilder()
                            .addHeader(AUTHORIZATION_HEADER, Credentials.basic(trackingId, secretKey))
                            .build()
                    it.proceed(request)
                }


        val retrofit = Retrofit.Builder()
                .baseUrl("$endpointUrl/$trackingId/$appInstanceId/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build()
        return retrofit.create(RevoltApi::class.java)
    }

    companion object {
        const val AUTHORIZATION_HEADER: String = "Authorization"
    }
}
