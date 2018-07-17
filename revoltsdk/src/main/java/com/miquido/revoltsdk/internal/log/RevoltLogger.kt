package com.miquido.revoltsdk.internal.log

import android.os.Looper
import android.util.Log
import com.miquido.revoltsdk.RevoltLogLevel
import com.miquido.revoltsdk.RevoltLogLevel.*

/** Created by MiQUiDO on 09.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
internal object RevoltLogger {
    private var logLevel = RevoltLogLevel.WARN
    private const val TAG = "Revolt-SDK"

    fun init(logLevel: RevoltLogLevel) {
        this.logLevel = logLevel
    }

    fun e(message: String) {
        if (shouldShowLog(ERROR)) {
            Log.e(TAG, "$message, currentThread: ${Thread.currentThread().name}")
        }
    }

    fun e(message: String, throwable: Throwable) {
        if (shouldShowLog(ERROR)) {
            Log.e(TAG, "$message, currentThread: ${Thread.currentThread().name}", throwable)
        }
    }

    fun w(message: String) {
        if (shouldShowLog(WARN)) {
            Log.w(TAG, "$message, currentThread: ${Thread.currentThread().name}")
        }
    }

    fun w(message: String, throwable: Throwable) {
        if (shouldShowLog(WARN)) {
            Log.w(TAG, "$message, currentThread: ${Thread.currentThread().name}", throwable)
        }
    }

    fun i(message: String) {
        if (shouldShowLog(INFO)) {
            Log.i(TAG, "$message, currentThread: ${Thread.currentThread().name}")
        }
    }

    fun i(message: String, throwable: Throwable) {
        if (shouldShowLog(INFO)) {
            Log.i(TAG, "$message, currentThread: ${Thread.currentThread().name}", throwable)
        }
    }

    fun d(message: String) {
        if (shouldShowLog(DEBUG)) {
            Log.d(TAG, "$message, currentThread: ${Thread.currentThread().name}")
        }
    }

    fun d(message: String, throwable: Throwable) {
        if (shouldShowLog(DEBUG)) {
            Log.d(TAG, "$message, currentThread: ${Thread.currentThread().name}", throwable)
        }
    }

    fun v(message: String) {
        if (shouldShowLog(VERBOSE)) {
            Log.v(TAG, "$message, currentThread: ${Thread.currentThread().name}")
        }
    }

    fun v(message: String, throwable: Throwable) {
        if (shouldShowLog(VERBOSE)) {
            Log.v(TAG, "$message, currentThread: ${Thread.currentThread().name}", throwable)
        }
    }

    private fun shouldShowLog(revoltLogLevel: RevoltLogLevel): Boolean {
        return logLevel >= revoltLogLevel
    }
}
