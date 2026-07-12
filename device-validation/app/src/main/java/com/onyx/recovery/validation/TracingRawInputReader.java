package com.onyx.recovery.validation;

import com.onyx.android.sdk.pen.RawInputReader;

final class TracingRawInputReader extends RawInputReader {
    private final ResultRecorder recorder;

    TracingRawInputReader(ResultRecorder recorder) {
        this.recorder = recorder;
    }

    @Override
    public void onTouchPointReceived(float x, float y, int pressure, int tiltX, int tiltY,
                                     boolean erasing, boolean shortcutDrawing,
                                     boolean shortcutErasing, int state, long timestamp) {
        recorder.event("raw_jni", ResultRecorder.map(
                "x", x, "y", y, "pressure", pressure,
                "tiltX", tiltX, "tiltY", tiltY,
                "erasing", erasing,
                "shortcutDrawing", shortcutDrawing,
                "shortcutErasing", shortcutErasing,
                "state", state, "timestamp", timestamp
        ));
        super.onTouchPointReceived(x, y, pressure, tiltX, tiltY, erasing,
                shortcutDrawing, shortcutErasing, state, timestamp);
    }
}
