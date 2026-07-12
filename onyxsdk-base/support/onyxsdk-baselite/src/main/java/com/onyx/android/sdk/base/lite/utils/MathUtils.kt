package com.onyx.android.sdk.base.lite.utils

import android.graphics.Matrix
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.hypot

object MathUtils {
    const val FLOAT_ONE: Float = 1.0f

    private val EMPTY_MATRIX = Matrix()

    fun isEmptyMatrix(matrix: Matrix?): Boolean = matrix == null || matrix == EMPTY_MATRIX

    fun parseFloat(str: String?, defaultValue: Float): Float =
        if (str == null) defaultValue else try {
            str.toFloat()
        } catch (_: NumberFormatException) {
            defaultValue
        }

    fun parseFloat(str: String): Float = parseFloat(str, 0.0f)

    fun clamp(value: Int, min: Int, max: Int): Int = when {
        value < min -> min
        value > max -> max
        else -> value
    }

    fun distance(x1: Float, y1: Float, x2: Float, y2: Float): Double =
        hypot(abs(x1 - x2).toDouble(), abs(y1 - y2).toDouble())

    fun lineNearAngle(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        val angle = abs(Math.toDegrees(atan2((y2 - y1).toDouble(), (x2 - x1).toDouble())).toFloat()) % 90
        return if (angle > 45.0f) 90 - angle else angle
    }

    fun Float.limitIn(
        start: Float,
        end: Float,
        startPadding: Float = 0.0f,
        endPadding: Float = 0.0f,
    ): Float = coerceIn(
        minOf(start, end) + startPadding,
        maxOf(start, end) - endPadding,
    )

    fun normalizeAngleTo36(angle: Double): UByte {
        val angleInt = (angle * 0.1).toInt().coerceIn(0, 36)
        return if (angleInt == 36) 0u else angleInt.toUByte()
    }

    fun <T : Comparable<T>> T.atMost(max: T): T = coerceAtMost(max)

    fun <T : Comparable<T>> T.atLeast(min: T): T = coerceAtLeast(min)
}
