package com.onyx.android.sdk.pen;

import androidx.annotation.Nullable;

/** Immutable raw-input device metadata. Missing axes are represented by {@code null}. */
public final class RawInputDeviceInfo {
    private final RawInputAxisRange x;
    private final RawInputAxisRange y;
    private final RawInputAxisRange pressure;
    private final RawInputAxisRange tiltX;
    private final RawInputAxisRange tiltY;
    private final boolean monotonicClock;

    public RawInputDeviceInfo(
            @Nullable RawInputAxisRange x,
            @Nullable RawInputAxisRange y,
            @Nullable RawInputAxisRange pressure,
            @Nullable RawInputAxisRange tiltX,
            @Nullable RawInputAxisRange tiltY,
            boolean monotonicClock) {
        this.x = x;
        this.y = y;
        this.pressure = pressure;
        this.tiltX = tiltX;
        this.tiltY = tiltY;
        this.monotonicClock = monotonicClock;
    }

    @Nullable public RawInputAxisRange getX() { return x; }
    @Nullable public RawInputAxisRange getY() { return y; }
    @Nullable public RawInputAxisRange getPressure() { return pressure; }
    @Nullable public RawInputAxisRange getTiltX() { return tiltX; }
    @Nullable public RawInputAxisRange getTiltY() { return tiltY; }
    public boolean isMonotonicClock() { return monotonicClock; }
}
