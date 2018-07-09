package com.miquido.revoltsdk.internal.log

import android.util.Log
import com.miquido.revoltsdk.RevoltLogLevel
import com.miquido.revoltsdk.RevoltLogLevel.*

/** Created by MiQUiDO on 09.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
object RevoltLogger {
    private var logLevel = RevoltLogLevel.WARN
    private const val TAG = "Revolt-SDK"

    fun init(logLevel: RevoltLogLevel) {
        this.logLevel = logLevel
    }

    fun e(message: String) {
        if (shouldShowLog(ERROR)) {
            Log.e(TAG, message)
        }
    }

    fun e(message: String, throwable: Throwable) {
        if (shouldShowLog(ERROR)) {
            Log.e(TAG, message, throwable)
        }
    }

    fun w(message: String) {
        if (shouldShowLog(WARN)) {
            Log.w(TAG, message)
        }
    }

    fun w(message: String, throwable: Throwable) {
        if (shouldShowLog(WARN)) {
            Log.w(TAG, message, throwable)
        }
    }

    fun i(message: String) {
        if (shouldShowLog(INFO)) {
            Log.i(TAG, message)
        }
    }

    fun i(message: String, throwable: Throwable) {
        if (shouldShowLog(INFO)) {
            Log.i(TAG, message, throwable)
        }
    }

    fun d(message: String) {
        if (shouldShowLog(DEBUG)) {
            Log.d(TAG, message)
        }
    }

    fun d(message: String, throwable: Throwable) {
        if (shouldShowLog(DEBUG)) {
            Log.d(TAG, message, throwable)
        }
    }

    fun v(message: String) {
        if (shouldShowLog(VERBOSE)) {
            Log.v(TAG, message)
        }
    }

    fun v(message: String, throwable: Throwable) {
        if (shouldShowLog(VERBOSE)) {
            Log.v(TAG, message, throwable)
        }
    }

    private fun shouldShowLog(revoltLogLevel: RevoltLogLevel): Boolean {
        return logLevel >= revoltLogLevel
    }
}
