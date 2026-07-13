@file:JvmName("SizeExtensionsKt")

package com.onyx.android.sdk.ktx

import android.util.Size as AndroidSize
import com.onyx.android.sdk.base.data.Size as OnyxSize
import kotlin.math.max

/**
 * Immutable pixel dimensions for Kotlin callers that do not need the recovered mutable API.
 *
 * @property width nonnegative horizontal dimension in pixels
 * @property height nonnegative vertical dimension in pixels
 * @throws IllegalArgumentException when either dimension is negative
 */
data class PixelSize(
    val width: Int,
    val height: Int,
) {
    init {
        require(width >= 0 && height >= 0) { "Dimensions must not be negative: $width x $height" }
    }

    /** Returns `true` when either dimension is zero. */
    val isEmpty: Boolean
        get() = width == 0 || height == 0

    /**
     * Scales this size down to fit [maximum] without changing its aspect ratio.
     *
     * The result never enlarges this size. Fractional dimensions are truncated and each result
     * dimension is kept at one pixel or greater.
     *
     * @throws IllegalArgumentException when this size or [maximum] is empty
     */
    fun fitWithin(maximum: PixelSize): PixelSize {
        require(!isEmpty) { "Size must have positive dimensions: $width x $height" }
        require(!maximum.isEmpty) {
            "Maximum size must have positive dimensions: ${maximum.width} x ${maximum.height}"
        }
        val ratio = max(
            1.0,
            max(
                width.toDouble() / maximum.width.toDouble(),
                height.toDouble() / maximum.height.toDouble(),
            ),
        )
        return PixelSize(
            width = (width / ratio).toInt().coerceAtLeast(1),
            height = (height / ratio).toInt().coerceAtLeast(1),
        )
    }

    /** Shared constants for [PixelSize]. */
    companion object {
        /** Empty immutable dimensions. */
        val Zero = PixelSize(0, 0)
    }
}

/** Copies this recovered mutable size into an immutable [PixelSize]. */
fun OnyxSize.toPixelSize(): PixelSize = PixelSize(width, height)

/** Copies this immutable size into a new mutable [OnyxSize]. */
fun PixelSize.toOnyxSize(): OnyxSize = OnyxSize(width, height)

/** Copies this Android size into a new mutable [OnyxSize]. */
fun AndroidSize.toOnyxSize(): OnyxSize = OnyxSize(width, height)

/** Copies this recovered mutable size into an Android size. */
fun OnyxSize.toAndroidSize(): AndroidSize = AndroidSize(width, height)

/**
 * Fits this recovered mutable size within [maximum] and returns the result as an immutable value.
 *
 * @throws IllegalArgumentException when this size or [maximum] is empty
 */
fun OnyxSize.fitWithin(maximum: OnyxSize): PixelSize =
    toPixelSize().fitWithin(maximum.toPixelSize())

/** Returns a fresh mutable empty value instead of sharing `Size.empty`. */
fun emptyOnyxSize(): OnyxSize = OnyxSize()
