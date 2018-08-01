@file:JvmName("UIActivityEvents")
package com.miquido.revoltsdk

import android.app.Activity

/** Created by MiQUiDO on 19.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */

fun buildUIActivityStartedEvent(activity: Activity) : Event =
        buildUIActivityEvent(activity, "started")

fun buildUIActivityStoppedEvent(activity: Activity) : Event =
        buildUIActivityEvent(activity, "stopped")

fun buildUIActivityCreatedEvent(activity: Activity) : Event =
        buildUIActivityEvent(activity, "created")

fun buildUIActivityDestroyedEvent(activity: Activity) : Event =
        buildUIActivityEvent(activity, "destroyed")

fun buildUIActivityPausedEvent(activity: Activity) : Event =
        buildUIActivityEvent(activity, "paused")

fun buildUIActivityResumedEvent(activity: Activity) : Event =
        buildUIActivityEvent(activity, "resumed")


internal fun buildUIActivityEvent(activity: Activity, action: String) : Event =
        RevoltEvent("ui.activity.$action", "activity", activity.localClassName)

