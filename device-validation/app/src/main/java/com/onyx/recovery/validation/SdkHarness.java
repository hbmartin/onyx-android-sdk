package com.onyx.recovery.validation;

import android.view.SurfaceView;
import android.widget.TextView;

/** Flavor-neutral harness; reference uses legacy SDK calls and recovered uses KTX. */
interface SdkHarness extends AutoCloseable {
    void runAutomated(SurfaceView surface, TextView status, Runnable finished);

    void startInk(SurfaceView surface, TextView status, long durationMs, Runnable finished);

    @Override
    void close();
}
