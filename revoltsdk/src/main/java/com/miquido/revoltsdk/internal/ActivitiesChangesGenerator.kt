package com.miquido.revoltsdk.internal

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle

/** Created by MiQUiDO on 24.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class ActivitiesChangesGenerator(val context: Context) : Application.ActivityLifecycleCallbacks {

    private var callback: ((className: String, action: String) -> Unit)? = null

    fun start() {
        (context as Application).registerActivityLifecycleCallbacks(this)
    }

    fun registerCallback(callback: (String, String) -> Unit) {
        this.callback = callback
    }

    override fun onActivityPaused(p0: Activity) {
        callback?.invoke(p0.localClassName, "paused")
    }

    override fun onActivityResumed(p0: Activity) {
        callback?.invoke(p0.localClassName, "resumed")
    }

    override fun onActivityStarted(p0: Activity) {
        callback?.invoke(p0.localClassName, "started")
    }

    override fun onActivityDestroyed(p0: Activity) {
        callback?.invoke(p0.localClassName, "destroyed")
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle?) {
        callback?.invoke(p0.localClassName, "saveInstanceState")
    }

    override fun onActivityStopped(p0: Activity) {
        callback?.invoke(p0.localClassName, "stopped")
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        callback?.invoke(p0.localClassName, "created")
    }


}
