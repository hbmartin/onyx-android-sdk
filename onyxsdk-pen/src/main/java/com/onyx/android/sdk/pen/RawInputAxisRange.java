package com.onyx.android.sdk.pen;

/** Immutable Linux input-axis metadata captured from {@code EVIOCGABS}. */
public final class RawInputAxisRange {
    private final int minimum;
    private final int maximum;
    private final int fuzz;
    private final int flat;
    private final int resolution;

    public RawInputAxisRange(int minimum, int maximum, int fuzz, int flat, int resolution) {
        this.minimum = minimum;
        this.maximum = maximum;
        this.fuzz = fuzz;
        this.flat = flat;
        this.resolution = resolution;
    }

    public int getMinimum() { return minimum; }
    public int getMaximum() { return maximum; }
    public int getFuzz() { return fuzz; }
    public int getFlat() { return flat; }
    public int getResolution() { return resolution; }
}
