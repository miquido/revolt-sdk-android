package com.miquido.revoltsdk.internal.network.service

import android.content.Context
import android.net.ConnectivityManager

/** Created by MiQUiDO on 23.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
abstract class NetworkStateService(context: Context) {
    protected var manager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    protected var networkStateListener: NetworkStateListener? = null

    abstract fun setNetworkStatesListener()

    fun start() {
        setNetworkStatesListener()
    }

    fun registerCallback(listener: NetworkStateListener) {
        this.networkStateListener = listener
    }

    interface NetworkStateListener {
        fun onNetworkStateChange(isConnected: Boolean)
    }
}
