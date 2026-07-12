package com.onyx.android.sdk.api.device.epd;

import java.util.Arrays;

/** Immutable group of vendor stroke-style properties. */
public final class StrokeConfiguration {
    private final int color;
    private final float width;
    private final int style;
    private final float[] extraParameters;

    public StrokeConfiguration(int color, float width, int style, float[] extraParameters) {
        if (Float.isNaN(width) || Float.isInfinite(width) || width < 0.0f) {
            throw new IllegalArgumentException("width must be finite and non-negative");
        }
        this.color = color;
        this.width = width;
        this.style = style;
        this.extraParameters = extraParameters == null
                ? new float[0]
                : Arrays.copyOf(extraParameters, extraParameters.length);
    }

    public int getColor() {
        return color;
    }

    public float getWidth() {
        return width;
    }

    public int getStyle() {
        return style;
    }

    public float[] getExtraParameters() {
        return Arrays.copyOf(extraParameters, extraParameters.length);
    }
}
