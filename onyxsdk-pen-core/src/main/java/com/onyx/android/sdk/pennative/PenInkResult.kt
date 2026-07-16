package com.onyx.android.sdk.pennative

/** Real and predicted native ink channels compatible with `onyxsdk-pennative:1.0.4`. */
class PenInkResult(
    /** Geometry committed to the active stroke by the current call. */
    val realInk: PenInk,

    /** Ephemeral geometry derived from the optional prediction sample. */
    val predictionInk: PenInk,
) {
    /** Java-friendly factory matching the vendor API. */
    companion object {
        /** Creates a result with distinct committed [realInk] and predicted [predictionInk]. */
        @JvmStatic
        fun create(realInk: PenInk, predictionInk: PenInk): PenInkResult =
            PenInkResult(realInk, predictionInk)
    }
}
