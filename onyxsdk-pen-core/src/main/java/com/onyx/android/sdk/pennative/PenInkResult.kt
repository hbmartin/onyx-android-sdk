package com.onyx.android.sdk.pennative

/** Real and predicted native ink channels compatible with `onyxsdk-pennative:1.0.4`. */
class PenInkResult(
    val realInk: PenInk,
    val predictionInk: PenInk,
) {
    companion object {
        @JvmStatic
        fun create(realInk: PenInk, predictionInk: PenInk): PenInkResult =
            PenInkResult(realInk, predictionInk)
    }
}
