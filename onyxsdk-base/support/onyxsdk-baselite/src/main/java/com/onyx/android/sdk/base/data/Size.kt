package com.onyx.android.sdk.base.data

import android.graphics.Rect
import android.graphics.RectF

/**
 * Mutable pixel dimensions used by the recovered SDK APIs.
 *
 * A dimension is considered empty when either value is zero or negative. The default instance is
 * therefore empty.
 *
 * @property width horizontal dimension in pixels
 * @property height vertical dimension in pixels
 */
data class Size(
    var width: Int = 0,
    var height: Int = 0,
) {
    /** Sets [width] and returns this mutable instance for call chaining. */
    fun setWidth(width: Int): Size {
        this.width = width
        return this
    }

    /** Sets [height] and returns this mutable instance for call chaining. */
    fun setHeight(height: Int): Size {
        this.height = height
        return this
    }

    /** Returns `true` when [width] or [height] is not positive. */
    fun isEmpty(): Boolean = width <= 0 || height <= 0

    /** Returns whether this instance has exactly the supplied [w] and [h] dimensions. */
    fun equals(w: Int, h: Int): Boolean = width == w && height == h

    /** Returns a floating-point rectangle from the origin to this size. */
    fun toRectF(): RectF = RectF(0.0f, 0.0f, width.toFloat(), height.toFloat())

    /** Returns an integer rectangle from the origin to this size. */
    fun toRect(): Rect = Rect(0, 0, width, height)

    /** Returns a stable text key containing the current dimensions. */
    fun key(): String = "w=$width-h=$height"

    /** Returns `true` when the height is greater than the width. */
    fun isVertical(): Boolean = height > width

    /**
     * Returns a rectangle whose dimensions fit within [maxPageSize] without changing orientation
     * or aspect ratio. Sizes that already fit are returned at their original dimensions.
     *
     * @throws IllegalArgumentException if this size or [maxPageSize] is empty
     */
    fun ratioPageSize(maxPageSize: Size): RectF {
        require(!isEmpty()) { "Size must have positive dimensions: $width x $height" }
        require(!maxPageSize.isEmpty()) {
            "Maximum page size must have positive dimensions: ${maxPageSize.width} x ${maxPageSize.height}"
        }
        val (maxWidth, maxHeight) = if (isVertical()) {
            maxPageSize.width to maxPageSize.height
        } else {
            maxPageSize.height to maxPageSize.width
        }
        val ratio = maxOf(
            1.0f,
            width.toFloat() / maxWidth.toFloat(),
            height.toFloat() / maxHeight.toFloat(),
        )
        return RectF(0.0f, 0.0f, width / ratio, height / ratio)
    }

    /** Returns this non-empty instance, or a value produced by [defaultValue] when it is empty. */
    fun getOrElse(defaultValue: () -> Size): Size = if (isEmpty()) defaultValue() else this

    /** Factory and conversion helpers for [Size]. */
    companion object {
        /**
         * Shared mutable empty value retained for compatibility with the recovered API.
         *
         * Callers that need ownership of the returned value should create a new [Size] instead of
         * mutating this instance.
         */
        val empty = Size()

        /** Converts an Android size to the mutable recovered SDK representation. */
        fun android.util.Size.toDataSize(): Size = Size(width, height)
    }
}
