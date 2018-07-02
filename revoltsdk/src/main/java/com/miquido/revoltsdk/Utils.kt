package com.miquido.revoltsdk

import android.content.Context
import android.support.v4.content.PermissionChecker.PERMISSION_GRANTED
import android.support.v4.content.PermissionChecker.checkCallingOrSelfPermission


/** Created by MiQUiDO on 28.06.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
fun hasPermission(context: Context, permission: String): Boolean {
    return context.checkCallingOrSelfPermission(permission) == PERMISSION_GRANTED
}
