package com.onyx.android.sdk.base.data

import android.graphics.Rect
import android.graphics.RectF

data class Size(
    var width: Int = 0,
    var height: Int = 0,
) {
    fun setWidth(width: Int): Size {
        this.width = width
        return this
    }

    fun setHeight(height: Int): Size {
        this.height = height
        return this
    }

    fun isEmpty(): Boolean = width <= 0 || height <= 0

    fun equals(w: Int, h: Int): Boolean = width == w && height == h

    fun toRectF(): RectF = RectF(0.0f, 0.0f, width.toFloat(), height.toFloat())

    fun toRect(): Rect = Rect(0, 0, width, height)

    fun key(): String = "w=$width-h=$height"

    fun isVertical(): Boolean = height > width

    fun ratioPageSize(maxPageSize: Size): RectF {
        val maxSizeValue = maxOf(maxPageSize.width, maxPageSize.height)
        val bitmapMaxSize = maxOf(width, height)
        if (maxSizeValue > bitmapMaxSize) {
            return RectF(0.0f, 0.0f, width.toFloat(), height.toFloat())
        }
        val (maxWidth, maxHeight) = if (isVertical()) {
            maxPageSize.width to maxPageSize.height
        } else {
            maxPageSize.height to maxPageSize.width
        }
        val ratio = maxOf(width / maxWidth, height / maxHeight).toFloat()
        return RectF(0.0f, 0.0f, width / ratio, height / ratio)
    }

    fun getOrElse(defaultValue: () -> Size): Size = if (isEmpty()) defaultValue() else this

    companion object {
        val empty = Size()

        fun android.util.Size.toDataSize(): Size = Size(width, height)
    }
}
