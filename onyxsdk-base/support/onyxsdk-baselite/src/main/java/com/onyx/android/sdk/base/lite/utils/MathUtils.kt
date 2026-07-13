package com.onyx.android.sdk.base.lite.utils

import android.graphics.Matrix
import kotlin.math.atan2
import kotlin.math.hypot

/** Numeric and geometry helpers retained by the recovered baselite API. */
object MathUtils {
    /** Floating-point representation of one. */
    const val FLOAT_ONE: Float = 1.0f

    /** Returns `true` when [matrix] is `null` or represents the identity transform. */
    fun isEmptyMatrix(matrix: Matrix?): Boolean = matrix == null || matrix.isIdentity

    /** Returns [str] as a float, or [defaultValue] when it is `null` or malformed. */
    fun parseFloat(str: String?, defaultValue: Float): Float =
        str?.toFloatOrNull() ?: defaultValue

    /** Returns [str] as a float, or `0.0` when it is malformed. */
    fun parseFloat(str: String): Float = parseFloat(str, 0.0f)

    /** Returns [value] constrained to the inclusive range from [min] to [max]. */
    fun clamp(value: Int, min: Int, max: Int): Int = when {
        value < min -> min
        value > max -> max
        else -> value
    }

    /** Returns the Euclidean distance between ([x1], [y1]) and ([x2], [y2]). */
    fun distance(x1: Float, y1: Float, x2: Float, y2: Float): Double =
        hypot((x1 - x2).toDouble(), (y1 - y2).toDouble())

    /**
     * Returns how far the line from ([x1], [y1]) to ([x2], [y2]) is from its nearest horizontal or
     * vertical axis, in degrees from `0` through `45`.
     */
    fun lineNearAngle(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        val angle = kotlin.math.abs(
            Math.toDegrees(atan2((y2 - y1).toDouble(), (x2 - x1).toDouble())).toFloat(),
        ) % 90
        return if (angle > 45.0f) 90 - angle else angle
    }

    /**
     * Constrains this value to the inclusive interval between [start] and [end], inset by the
     * supplied paddings.
     *
     * @receiver value to constrain
     * @throws IllegalArgumentException when the paddings produce an empty interval
     */
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

    /**
     * Normalizes [angle] to `[0, 360)` and returns the zero-based ten-degree bucket from `0` to
     * `35`.
     *
     * @throws IllegalArgumentException when [angle] is not finite
     */
    fun normalizeAngleTo36(angle: Double): UByte {
        require(angle.isFinite()) { "Angle must be finite: $angle" }
        val normalizedDegrees = ((angle % 360.0) + 360.0) % 360.0
        return (normalizedDegrees / 10.0).toInt().toUByte()
    }

    /** Returns this value or [max], whichever is smaller. */
    fun <T : Comparable<T>> T.atMost(max: T): T = coerceAtMost(max)

    /** Returns this value or [min], whichever is larger. */
    fun <T : Comparable<T>> T.atLeast(min: T): T = coerceAtLeast(min)
}
