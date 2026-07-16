package com.onyx.android.sdk.ktx.render

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import com.onyx.android.sdk.ktx.PixelSize
import com.onyx.android.sdk.utils.BitmapUtils

/**
 * Returns a copy of this bitmap clipped to rounded corners and optionally resized to [targetSize].
 *
 * The default keeps the original Onyx SDK's non-antialiased mask. Set [antiAlias] to `true` to
 * opt into smoother, partially transparent edge pixels.
 *
 * @throws IllegalArgumentException when [radiusPx] is negative or non-finite, or when
 * [targetSize] is empty
 */
fun Bitmap.roundedCorners(
    radiusPx: Float,
    targetSize: PixelSize = PixelSize(width, height),
    antiAlias: Boolean = false,
): Bitmap {
    require(radiusPx.isFinite() && radiusPx >= 0f) {
        "Corner radius must be finite and nonnegative: $radiusPx"
    }
    require(!targetSize.isEmpty) {
        "Target size must have positive dimensions: ${targetSize.width} x ${targetSize.height}"
    }
    if (!antiAlias) {
        return BitmapUtils.roundCornerBitmap(
            this,
            targetSize.width,
            targetSize.height,
            radiusPx,
        )
    }

    val source = if (width == targetSize.width && height == targetSize.height) {
        this
    } else {
        BitmapUtils.createScaledBitmap(this, targetSize.width, targetSize.height)
    }
    try {
        val output = Bitmap.createBitmap(
            targetSize.width,
            targetSize.height,
            Bitmap.Config.ARGB_8888,
        )
        val canvas = Canvas(output)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val bounds = Rect(0, 0, targetSize.width, targetSize.height)
        canvas.drawRoundRect(RectF(bounds), radiusPx, radiusPx, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(source, bounds, bounds, paint)
        return output
    } finally {
        if (source !== this) {
            source.recycle()
        }
    }
}
