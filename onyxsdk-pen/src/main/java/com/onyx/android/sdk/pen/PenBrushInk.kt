package com.onyx.android.sdk.pen

/** One textured-brush stamp returned by the native pen renderer. */
class PenBrushInk(
    var x: Float,
    var y: Float,
    var size: UByte,
    var angle36: UByte,
    var alpha: UByte,
) {
    /** Java-friendly constructor that preserves the unsigned 0..255 values. */
    constructor(x: Float, y: Float, size: Int, angle36: Int, alpha: Int) : this(
        x = x,
        y = y,
        size = size.also { require(it in 0..255) { "size must be in 0..255: $it" } }.toUByte(),
        angle36 = angle36.also { require(it in 0..255) { "angle36 must be in 0..255: $it" } }.toUByte(),
        alpha = alpha.also { require(it in 0..255) { "alpha must be in 0..255: $it" } }.toUByte(),
    )

    /** Returns [size] without Kotlin's unsigned JVM name mangling. */
    fun getSizeAsInt(): Int = size.toInt()

    /** Returns [angle36] without Kotlin's unsigned JVM name mangling. */
    fun getAngle36AsInt(): Int = angle36.toInt()

    /** Returns [alpha] without Kotlin's unsigned JVM name mangling. */
    fun getAlphaAsInt(): Int = alpha.toInt()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        return other is PenBrushInk &&
            x == other.x &&
            y == other.y &&
            size == other.size &&
            angle36 == other.angle36 &&
            alpha == other.alpha
    }

    override fun hashCode(): Int {
        var result = if (x == 0.0f) 0 else x.toBits()
        result = 31 * result + if (y == 0.0f) 0 else y.toBits()
        result = 31 * result + size.toInt()
        result = 31 * result + angle36.toInt()
        result = 31 * result + alpha.toInt()
        return result
    }
}
