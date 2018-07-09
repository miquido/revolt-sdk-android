package com.miquido.revoltsdk.internal.configuration

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings
import com.miquido.revoltsdk.BuildConfig
import com.miquido.revoltsdk.internal.toHex
import java.security.MessageDigest

/** Created by MiQUiDO on 05.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
@SuppressLint("HardwareIds")
internal class ConfigurationRepository(private val context: Context) {

    private val sharedPref: SharedPreferences = context.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE)

    init {
        initAppInstanceId()
    }

    fun getAppInstanceId(): String {
        return sharedPref.getString(APP_ID, null)
    }


    private fun initAppInstanceId() {
        if (sharedPref.getString(APP_ID, null) == null) {
            val deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID) + BuildConfig.APPLICATION_ID
            val digest = MessageDigest.getInstance("SHA-256")
            digest.update(deviceId.toByteArray())
            val byteData = digest.digest()

            sharedPref.edit()
                    .putString(APP_ID, byteData.toHex())
                    .apply()
        }
    }

    companion object {
        private const val PREFERENCES_FILE = "revolt_config"
        private const val APP_ID = "APP_ID"
    }


}
