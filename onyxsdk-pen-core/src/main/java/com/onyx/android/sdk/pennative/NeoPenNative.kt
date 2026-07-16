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

    /**
     * Creates a native logger handle and selects the SDK default log level.
     *
     * @return an opaque handle that can be passed to [registerLogger]
     */
    fun createLogger(): Long = nativeCreateLogger().also { setLogLevel(DEFAULT_LOG_LEVEL) }

    /** Makes [logger] the process-wide logger used by the native renderer. */
    fun registerLogger(logger: Long) = nativeRegisterLogger(logger)

    /** Sets the vendor-compatible integer logging [level] for native renderer messages. */
    fun setLogLevel(level: Int) = nativeSetLogLevel(level)

    /**
     * Creates a renderer for [penType] using a snapshot of [config].
     *
     * @return an opaque pen handle used by the stroke lifecycle methods
     */
    fun createPen(penType: Int, config: PenConfig): Long = nativeCreatePen(penType, config)

    /** Releases [pen]. Destroying an unknown or already released handle is a no-op. */
    fun destroyPen(pen: Long) = nativeDestroyPen(pen)

    /**
     * Starts a stroke for [pen].
     *
     * [pointArray] contains one or more seven-value records in the order x, y, pressure, size,
     * tilt-x, tilt-y, and timestamp. [repaint] is retained for vendor API compatibility.
     *
     * @return generated ink, or `null` when [pen] is not registered
     */
    fun onPenDown(pen: Long, pointArray: DoubleArray, repaint: Boolean): PenInkResult? =
        nativeOnPenDown(pen, pointArray, repaint)

    /**
     * Extends the active stroke for [pen].
     *
     * [pointArray] contains committed seven-value touch records. [predictionArray] may contain a
     * single uncommitted record; predicted geometry is returned separately and never advances the
     * committed renderer state. [repaint] is retained for vendor API compatibility.
     *
     * @return generated ink, or `null` when [pen] is not registered
     */
    fun onPenMove(
        pen: Long,
        pointArray: DoubleArray,
        predictionArray: DoubleArray?,
        repaint: Boolean,
    ): PenInkResult? = nativeOnPenMove(pen, pointArray, predictionArray, repaint)

    /**
     * Finishes the active stroke for [pen] and resets its per-stroke state.
     *
     * [pointArray] contains the final seven-value touch record and [repaint] is retained for vendor
     * API compatibility.
     *
     * @return final ink, or `null` when [pen] is not registered
     */
    fun onPenUp(pen: Long, pointArray: DoubleArray, repaint: Boolean): PenInkResult? =
        nativeOnPenUp(pen, pointArray, repaint)

    private const val DEFAULT_LOG_LEVEL = 2
}
