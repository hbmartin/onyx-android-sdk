package com.onyx.android.sdk.pen;

/**
 * Immutable v2 raw-input event. Coordinates are physical pixels local to the bound host view;
 * timestamps use the kernel input clock and are expressed as monotonic nanoseconds.
 */
public final class RawInputEventV2 {
    private final RawInputPhase phase;
    private final RawInputTool tool;
    private final float x;
    private final float y;
    private final int rawPressure;
    private final float normalizedPressure;
    private final int pressureMaximum;
    private final int tiltX;
    private final int tiltY;
    private final long timestampNanos;
    private final long sequence;
    private final boolean outsideLimitRegion;
    private final boolean forced;

    public RawInputEventV2(
            RawInputPhase phase,
            RawInputTool tool,
            float x,
            float y,
            int rawPressure,
            float normalizedPressure,
            int pressureMaximum,
            int tiltX,
            int tiltY,
            long timestampNanos,
            long sequence,
            boolean outsideLimitRegion,
            boolean forced) {
        this.phase = phase;
        this.tool = tool;
        this.x = x;
        this.y = y;
        this.rawPressure = rawPressure;
        this.normalizedPressure = normalizedPressure;
        this.pressureMaximum = pressureMaximum;
        this.tiltX = tiltX;
        this.tiltY = tiltY;
        this.timestampNanos = timestampNanos;
        this.sequence = sequence;
        this.outsideLimitRegion = outsideLimitRegion;
        this.forced = forced;
    }

    public RawInputPhase getPhase() { return phase; }
    public RawInputTool getTool() { return tool; }
    public float getX() { return x; }
    public float getY() { return y; }
    public int getRawPressure() { return rawPressure; }
    public float getNormalizedPressure() { return normalizedPressure; }
    public int getPressureMaximum() { return pressureMaximum; }
    public int getTiltX() { return tiltX; }
    public int getTiltY() { return tiltY; }
    public long getTimestampNanos() { return timestampNanos; }
    public long getSequence() { return sequence; }
    public boolean isOutsideLimitRegion() { return outsideLimitRegion; }
    public boolean isForced() { return forced; }
}
