package com.miquido.revoltsdk.internal.connection

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.support.annotation.RequiresApi

/** Created by MiQUiDO on 23.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
@SuppressLint("MissingPermission")
class LollipopNetworkStateService(context: Context) : NetworkStateService(context) {
    private var isConnected: Boolean? = null

    override fun start() {
        val networkRequest = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .build()

        manager.registerNetworkCallback(networkRequest, object : ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network?) {
                onNetworkStateChanged()
            }

            override fun onAvailable(network: Network?) {
                onNetworkStateChanged()
            }
        })
        isConnected = isConnected()
    }

    override fun isConnected(): Boolean {
        return manager.allNetworks.any { manager.getNetworkInfo(it).isConnected }
    }

    private fun onNetworkStateChanged() {
        val connectionStatus = isConnected()

        if (isConnected != connectionStatus) {
            isConnected = connectionStatus
            networkStateCallback?.invoke(connectionStatus)
        }
    }
}
