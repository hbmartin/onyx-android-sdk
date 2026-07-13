package com.onyx.android.sdk.ktx

import android.util.Size as AndroidSize
import com.onyx.android.sdk.base.data.Size as OnyxSize
import kotlin.math.max

/** Immutable dimensions for Kotlin callers that do not need the recovered mutable API. */
data class PixelSize(
    val width: Int,
    val height: Int,
) {
    init {
        require(width >= 0 && height >= 0) { "Dimensions must not be negative: $width x $height" }
    }

    val isEmpty: Boolean
        get() = width == 0 || height == 0

    /** Scales this size down to fit [maximum] without changing its aspect ratio. */
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

    companion object {
        val Zero = PixelSize(0, 0)
    }
}

fun OnyxSize.toPixelSize(): PixelSize = PixelSize(width, height)

fun PixelSize.toOnyxSize(): OnyxSize = OnyxSize(width, height)

fun AndroidSize.toOnyxSize(): OnyxSize = OnyxSize(width, height)

fun OnyxSize.toAndroidSize(): AndroidSize = AndroidSize(width, height)

fun OnyxSize.fitWithin(maximum: OnyxSize): PixelSize =
    toPixelSize().fitWithin(maximum.toPixelSize())

/** Returns a fresh mutable empty value instead of sharing `Size.empty`. */
fun emptyOnyxSize(): OnyxSize = OnyxSize()
