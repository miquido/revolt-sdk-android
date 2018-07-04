package com.miquido.revoltsdk.internal

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import android.view.WindowManager

/** Created by MiQUiDO on 03.07.2018.
 * <p>
 * Copyright 2018 MiQUiDO <http://www.miquido.com/>. All rights reserved.
 */
class ScreenSizeProvider(val context: Context) {

    var sizePx: Point
    var sizeIn: Double

    init {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val defaultDisplay = windowManager.defaultDisplay

        sizePx = getSizePx(defaultDisplay)
        sizeIn = evaluateSizeIn(defaultDisplay, sizePx)
    }

    private fun getSizePx(display: Display): Point {
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)


        try {
            val realSize = Point()
            Display::class.java.getMethod("getRealSize", Point::class.java).invoke(display, realSize)
            return realSize
        } catch (ignored: Exception) {
        }

        return Point(metrics.widthPixels, metrics.heightPixels)
    }

    private fun evaluateSizeIn(display: Display, sizePx: Point): Double {
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)

        val x = Math.pow((sizePx.x / metrics.xdpi).toDouble(), 2.0)
        val y = Math.pow((sizePx.y / metrics.ydpi).toDouble(), 2.0)
        return Math.sqrt(x + y)
    }
}
