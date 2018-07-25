package com.miquido.revoltsdk.internal

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle

/** Created by MiQUiDO on 24.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal class ActivitiesChangesManager(val context: Context) : Application.ActivityLifecycleCallbacks {

    private var callback: ((className: String, action: String) -> Unit)? = null

    init {
        (context as Application).registerActivityLifecycleCallbacks(this)
    }

    fun registerCallback(callback: (String, String) -> Unit) {
        this.callback = callback
    }

    override fun onActivityPaused(p0: Activity) {
        callback?.invoke(p0.localClassName, "onActivityPaused")
    }

    override fun onActivityResumed(p0: Activity) {
        callback?.invoke(p0.localClassName, "onActivityResumed")
    }

    override fun onActivityStarted(p0: Activity) {
        callback?.invoke(p0.localClassName, "onActivityStarted")
    }

    override fun onActivityDestroyed(p0: Activity) {
        callback?.invoke(p0.localClassName, "onActivityDestroyed")
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle?) {
        callback?.invoke(p0.localClassName, "onActivitySaveInstanceState")
    }

    override fun onActivityStopped(p0: Activity) {
        callback?.invoke(p0.localClassName, "onActivityStopped")
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        callback?.invoke(p0.localClassName, "onActivityCreated")
    }


}
