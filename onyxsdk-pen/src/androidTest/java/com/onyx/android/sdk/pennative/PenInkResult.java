package com.onyx.android.sdk.pennative;

/** Test-only result pair constructed by the installed Notable JNI library. */
public final class PenInkResult {
    private final PenInk realInk;
    private final PenInk predictionInk;

    public PenInkResult(PenInk realInk, PenInk predictionInk) {
        this.realInk = realInk;
        this.predictionInk = predictionInk;
    }

    public static PenInkResult create(PenInk realInk, PenInk predictionInk) {
        return new PenInkResult(realInk, predictionInk);
    }

    public PenInk getRealInk() { return realInk; }
    public PenInk getPredictionInk() { return predictionInk; }
}
