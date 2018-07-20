package com.miquido.revoltsdk.internal.network

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import com.miquido.revoltsdk.internal.log.RevoltLogger
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.support.annotation.RequiresApi


/** Created by MiQUiDO on 20.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
@Suppress("DEPRECATION")
@SuppressLint("MissingPermission")
internal class NetworkStateService(private val context: Context,
                                   private val networkStateListener: NetworkStateListener) {

    private val manager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var isConnected = true

    fun start() {
        setNetworkStatesListener()
    }

    private fun setNetworkStatesListener() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            val networkRequest = NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .build()

            manager.registerNetworkCallback(networkRequest, object : ConnectivityManager.NetworkCallback() {
                override fun onLost(network: Network?) {
                    super.onLost(network)

                    var connectionStatus = false
                    manager.allNetworks.forEach { connectionStatus = connectionStatus || manager.getNetworkInfo(it).isConnected }

                    if (isConnected != connectionStatus) {
                        isConnected = connectionStatus
                        networkStateListener.onNetworkStateChange(connectionStatus)
                    }
                    RevoltLogger.d("NetworkStateService: onLost: state: $isConnected")
                }


                override fun onAvailable(network: Network?) {
                    super.onAvailable(network)

                    var connectionStatus = false
                    manager.allNetworks.forEach { connectionStatus = connectionStatus || manager.getNetworkInfo(it).isConnected }

                    if (isConnected != connectionStatus) {
                        isConnected = connectionStatus
                        networkStateListener.onNetworkStateChange(connectionStatus)
                    }
                    RevoltLogger.d("NetworkStateService: onAvailable: state: $isConnected")
                }
            })
        } else {
            context.registerReceiver(createNetworkStatesBroadcast(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        }
    }

    private fun createNetworkStatesBroadcast() = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val netInfo = manager.activeNetworkInfo
            val hasConnection = netInfo != null && netInfo.isConnected
            RevoltLogger.d("NetworkStateService Receive network change broadcast, hasConnection: $hasConnection ")
            networkStateListener.onNetworkStateChange(hasConnection)
        }
    }

    interface NetworkStateListener {
        fun onNetworkStateChange(isConnected: Boolean)
    }
}
