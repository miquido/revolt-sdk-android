package com.miquido.revoltsdk.internal.connection

import android.content.Context
import android.net.ConnectivityManager

/** Created by MiQUiDO on 23.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
abstract class NetworkStateService(context: Context) {
    protected val manager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    protected var networkStateCallback: ((Boolean) -> Unit)? = null

    abstract fun start()

    abstract fun isConnected(): Boolean

    fun registerCallback(callback: (isConnected: Boolean) -> Unit) {
        this.networkStateCallback = callback
    }
}
