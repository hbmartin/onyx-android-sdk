package com.onyx.android.sdk.pen

/** One textured-brush stamp returned by the native pen renderer. */
class PenBrushInk(
    var x: Float,
    var y: Float,
    var size: UByte,
    var angle36: UByte,
    var alpha: UByte,
) {
    override fun equals(other: Any?): Boolean {
        return other is PenBrushInk &&
            x == other.x &&
            y == other.y &&
            size == other.size &&
            angle36 == other.angle36 &&
            alpha == other.alpha
    }
}
