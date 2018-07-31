package com.miquido.revoltsdk.internal

import android.content.Context
import com.miquido.revoltsdk.LifecycleEvent
import com.miquido.revoltsdk.Revolt

/** Created by MiQUiDO on 31.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class RevoltLifecycleEventsManager(private val revolt: Revolt,
                                   context: Context) {
    private val activitiesChangesGenerator: ActivitiesChangesGenerator = ActivitiesChangesGenerator(context)

    fun start() {
        activitiesChangesGenerator.registerCallback(::activityChanges)
        activitiesChangesGenerator.start()
    }

    private fun activityChanges(name: String, action: String) {
        revolt.sendEvent(LifecycleEvent(name, action))
    }
}
