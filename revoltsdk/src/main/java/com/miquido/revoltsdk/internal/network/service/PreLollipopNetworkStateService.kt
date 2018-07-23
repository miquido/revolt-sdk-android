package com.miquido.revoltsdk.internal.network.service

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.miquido.revoltsdk.internal.log.RevoltLogger

/** Created by MiQUiDO on 23.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
@Suppress("DEPRECATION")
@SuppressLint("MissingPermission")
class PreLollipopNetworkStateService(private val context: Context) : NetworkStateService(context) {
    override fun setNetworkStatesListener() {
        context.registerReceiver(createNetworkStatesBroadcast(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun createNetworkStatesBroadcast() = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val netInfo = manager.activeNetworkInfo
            val hasConnection = netInfo != null && netInfo.isConnected
            RevoltLogger.d("NetworkStateService Receive network change broadcast, hasConnection: $hasConnection ")
            networkStateListener?.onNetworkStateChange(hasConnection)
        }
    }
}
