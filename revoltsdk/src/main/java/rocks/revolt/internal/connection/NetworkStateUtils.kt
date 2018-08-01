package rocks.revolt.internal.connection

import android.content.Context

/** Created by MiQUiDO on 23.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal fun createNetworkStateService(context: Context): NetworkStateService {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        LollipopNetworkStateService(context)
    } else {
        PreLollipopNetworkStateService(context)
    }
}
