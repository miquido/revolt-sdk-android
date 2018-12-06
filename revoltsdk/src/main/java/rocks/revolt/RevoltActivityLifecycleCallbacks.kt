package rocks.revolt

import android.app.Activity
import android.app.Application
import android.os.Bundle

/** Created by MiQUiDO on 24.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class RevoltActivityLifecycleCallbacks(val revolt: Revolt) : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, bundle: Bundle?) =
            sendEventToRevolt { buildUIActivityCreatedEvent(activity) }

    override fun onActivityDestroyed(activity: Activity) {
        sendEventToRevolt { buildUIActivityDestroyedEvent(activity) }
    }

    override fun onActivityStarted(activity: Activity) =
            sendEventToRevolt { buildUIActivityStartedEvent(activity) }

    override fun onActivityStopped(activity: Activity) =
            sendEventToRevolt { buildUIActivityStoppedEvent(activity) }

    override fun onActivityPaused(activity: Activity) =
            sendEventToRevolt { buildUIActivityPausedEvent(activity) }

    override fun onActivityResumed(activity: Activity) =
            sendEventToRevolt { buildUIActivityResumedEvent(activity) }

    override fun onActivitySaveInstanceState(activity: Activity?, bundle: Bundle?) {
    }

    private fun sendEventToRevolt(eventBuilder: () -> Event) {
        revolt.sendEvent(eventBuilder.invoke())
    }
}
