package com.onyx.android.sdk.pennative

import android.graphics.Bitmap

/** Immutable native ink payload compatible with `onyxsdk-pennative:1.0.4`. */
class PenInk(
    val points: FloatArray,
    val pointSizeArray: IntArray,
    val bitmaps: Array<Bitmap>,
) {
    override fun toString(): String = buildString {
        append("PenInk: ${points.size} -> [")
        if (points.isNotEmpty()) append(points.first())
        for (index in 1 until points.size) append(",${points[index]}")
        append(']')
    }

    companion object {
        @JvmStatic
        fun create(
            points: FloatArray,
            pointSizeArray: IntArray,
            bitmaps: Array<Bitmap>,
        ): PenInk = PenInk(points, pointSizeArray, bitmaps)
    }
}
