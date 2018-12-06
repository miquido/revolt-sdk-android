package rocks.revolt.internal.log

import android.os.Looper
import android.util.Log
import rocks.revolt.RevoltLogLevel
import rocks.revolt.RevoltLogLevel.*

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
            Log.e(TAG, formatLog(message))
        }
    }

    fun e(message: String, throwable: Throwable) {
        if (shouldShowLog(ERROR)) {
            Log.e(TAG, formatLog(message), throwable)
        }
    }

    fun w(message: String) {
        if (shouldShowLog(WARN)) {
            Log.w(TAG, formatLog(message))
        }
    }

    fun w(message: String, throwable: Throwable) {
        if (shouldShowLog(WARN)) {
            Log.w(TAG, formatLog(message), throwable)
        }
    }

    fun i(message: String) {
        if (shouldShowLog(INFO)) {
            Log.i(TAG, formatLog(message))
        }
    }

    fun i(message: String, throwable: Throwable) {
        if (shouldShowLog(INFO)) {
            Log.i(TAG, formatLog(message), throwable)
        }
    }

    fun d(message: String) {
        if (shouldShowLog(DEBUG)) {
            Log.d(TAG, formatLog(message))
        }
    }

    fun d(message: String, throwable: Throwable) {
        if (shouldShowLog(DEBUG)) {
            Log.d(TAG, formatLog(message), throwable)
        }
    }

    fun v(message: String) {
        if (shouldShowLog(VERBOSE)) {
            Log.v(TAG, formatLog(message))
        }
    }

    fun v(message: String, throwable: Throwable) {
        if (shouldShowLog(VERBOSE)) {
            Log.v(TAG, formatLog(message), throwable)
        }
    }

    private fun shouldShowLog(revoltLogLevel: RevoltLogLevel): Boolean {
        return logLevel >= revoltLogLevel
    }

    private fun formatLog(message: String) = "[${threadName()},ms: ${System.currentTimeMillis()}] $message"

    private fun threadName() = Thread.currentThread().name.padEnd(12).substring(0, 12)
}
