package rocks.revolt.internal.connection

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import rocks.revolt.internal.log.RevoltLogger

/** Created by MiQUiDO on 23.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
@Suppress("DEPRECATION")
@SuppressLint("MissingPermission")
class PreLollipopNetworkStateService(private val context: Context) : NetworkStateService(context) {

    override fun start() {
        context.registerReceiver(createNetworkStatesBroadcast(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun isConnected(): Boolean {
        val netInfo = manager?.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    private fun createNetworkStatesBroadcast() = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val hasConnection = isConnected()
            RevoltLogger.d("NetworkStateService Receive network change broadcast, hasConnection: $hasConnection ")
            networkStateCallback?.invoke(hasConnection)
        }
    }
}
