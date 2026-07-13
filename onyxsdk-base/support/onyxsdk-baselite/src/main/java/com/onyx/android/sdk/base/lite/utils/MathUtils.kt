package com.onyx.android.sdk.base.lite.utils

import android.graphics.Matrix
import kotlin.math.atan2
import kotlin.math.hypot

object MathUtils {
    const val FLOAT_ONE: Float = 1.0f

    fun isEmptyMatrix(matrix: Matrix?): Boolean = matrix == null || matrix.isIdentity

    fun parseFloat(str: String?, defaultValue: Float): Float =
        str?.toFloatOrNull() ?: defaultValue

    fun parseFloat(str: String): Float = parseFloat(str, 0.0f)

    fun clamp(value: Int, min: Int, max: Int): Int = when {
        value < min -> min
        value > max -> max
        else -> value
    }

    fun distance(x1: Float, y1: Float, x2: Float, y2: Float): Double =
        hypot((x1 - x2).toDouble(), (y1 - y2).toDouble())

    fun lineNearAngle(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        val angle = kotlin.math.abs(
            Math.toDegrees(atan2((y2 - y1).toDouble(), (x2 - x1).toDouble())).toFloat(),
        ) % 90
        return if (angle > 45.0f) 90 - angle else angle
    }

    fun Float.limitIn(
        start: Float,
        end: Float,
        startPadding: Float = 0.0f,
        endPadding: Float = 0.0f,
    ): Float {
        val minimum = minOf(start, end) + startPadding
        val maximum = maxOf(start, end) - endPadding
        require(minimum <= maximum) {
            "Padding produces an empty range: minimum=$minimum, maximum=$maximum"
        }
        return coerceIn(minimum, maximum)
    }

    fun normalizeAngleTo36(angle: Double): UByte {
        require(angle.isFinite()) { "Angle must be finite: $angle" }
        val normalizedDegrees = ((angle % 360.0) + 360.0) % 360.0
        return (normalizedDegrees / 10.0).toInt().toUByte()
    }

    fun <T : Comparable<T>> T.atMost(max: T): T = coerceAtMost(max)

    fun <T : Comparable<T>> T.atLeast(min: T): T = coerceAtLeast(min)
}
