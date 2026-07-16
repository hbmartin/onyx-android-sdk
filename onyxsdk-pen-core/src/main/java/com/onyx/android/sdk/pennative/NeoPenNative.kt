@file:Suppress(
    // The method count and private bitmap export are fixed by the vendor JNI contract.
    "TooManyFunctions",
    "UnusedPrivateMember",
)

package com.onyx.android.sdk.pennative

import android.graphics.Bitmap

/**
 * JNI facade compatible with `onyxsdk-pennative:1.0.4`.
 *
 * The native implementation is owned by this project and also exports the legacy
 * `com.onyx.android.sdk.pen` entry points from the same renderer core.
 */
object NeoPenNative {
    init {
        System.loadLibrary("neopen_jni")
    }

    private external fun nativeCreateLogger(): Long
    private external fun nativeRegisterLogger(logger: Long)
    private external fun nativeSetLogLevel(level: Int)
    private external fun nativeCreatePen(penType: Int, config: PenConfig): Long
    private external fun nativeDestroyPen(pen: Long)
    private external fun nativeOnPenDown(
        pen: Long,
        pointArray: DoubleArray,
        repaint: Boolean,
    ): PenInkResult?

    private external fun nativeOnPenMove(
        pen: Long,
        pointArray: DoubleArray,
        predictionArray: DoubleArray?,
        repaint: Boolean,
    ): PenInkResult?

    private external fun nativeOnPenUp(
        pen: Long,
        pointArray: DoubleArray,
        repaint: Boolean,
    ): PenInkResult?

    private external fun nativeSetBitmapColor(bitmap: Bitmap): Boolean

    fun createLogger(): Long = nativeCreateLogger().also { setLogLevel(DEFAULT_LOG_LEVEL) }

    fun registerLogger(logger: Long) = nativeRegisterLogger(logger)

    fun setLogLevel(level: Int) = nativeSetLogLevel(level)

    fun createPen(penType: Int, config: PenConfig): Long = nativeCreatePen(penType, config)

    fun destroyPen(pen: Long) = nativeDestroyPen(pen)

    fun onPenDown(pen: Long, pointArray: DoubleArray, repaint: Boolean): PenInkResult? =
        nativeOnPenDown(pen, pointArray, repaint)

    fun onPenMove(
        pen: Long,
        pointArray: DoubleArray,
        predictionArray: DoubleArray?,
        repaint: Boolean,
    ): PenInkResult? = nativeOnPenMove(pen, pointArray, predictionArray, repaint)

    fun onPenUp(pen: Long, pointArray: DoubleArray, repaint: Boolean): PenInkResult? =
        nativeOnPenUp(pen, pointArray, repaint)

    private const val DEFAULT_LOG_LEVEL = 2
}
