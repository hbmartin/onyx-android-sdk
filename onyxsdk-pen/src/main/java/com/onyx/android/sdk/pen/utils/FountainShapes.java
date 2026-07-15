package com.onyx.android.sdk.pen.utils;

import com.onyx.android.sdk.pen.NeoFountainPenV2;
import com.onyx.android.sdk.pen.NeoPen;
import com.onyx.android.sdk.pen.NeoPenConfig;
import org.jetbrains.annotations.Nullable;

/** Fountain-pen factory compatible with the 1.5.4 facade and current Notable callers. */
public final class FountainShapes {
    public static final FountainShapes INSTANCE = new FountainShapes();

    private static final float FOUNTAIN_PEN_V1_COMPENSATION = 3f;
    private static final float DEFAULT_PRESSURE_SENSITIVITY = 0.3f;
    private static final float LEGACY_SMOOTH_LEVEL = 0.2f;
    private static final float DEFAULT_SMOOTH_LEVEL = 0.6f;

    private FountainShapes() {}

    /** Preserves the original 1.5.4 instance-method descriptor. */
    @Nullable
    public NeoPen createNeoPenV2(
            float width,
            float minWidth,
            float displayScaleX,
            float displayScaleY,
            float scalePrecision,
            float createScale,
            @Nullable Float pressureSensitivity,
            boolean fastMode) {
        return create(
                width,
                minWidth,
                displayScaleX,
                displayScaleY,
                scalePrecision,
                createScale,
                pressureSensitivity,
                fastMode,
                LEGACY_SMOOTH_LEVEL);
    }

    /** Preserves the original Kotlin default-argument bridge for compiled callers. */
    @Nullable
    public static NeoPen createNeoPenV2$default(
            FountainShapes receiver,
            float width,
            float minWidth,
            float displayScaleX,
            float displayScaleY,
            float scalePrecision,
            float createScale,
            @Nullable Float pressureSensitivity,
            boolean fastMode,
            int mask,
            Object marker) {
        if ((mask & 64) != 0) pressureSensitivity = null;
        if ((mask & 128) != 0) fastMode = true;
        return receiver.createNeoPenV2(
                width,
                minWidth,
                displayScaleX,
                displayScaleY,
                scalePrecision,
                createScale,
                pressureSensitivity,
                fastMode);
    }

    /** Newer facade overload used by Notable to align offline and live stroke smoothing. */
    @Nullable
    public static NeoPen createNeoPenV2(
            float width,
            float minWidth,
            float displayScaleX,
            float displayScaleY,
            float scalePrecision,
            float createScale,
            @Nullable Float pressureSensitivity,
            boolean fastMode,
            @Nullable Float smoothLevel) {
        return create(
                width,
                minWidth,
                displayScaleX,
                displayScaleY,
                scalePrecision,
                createScale,
                pressureSensitivity,
                fastMode,
                smoothLevel == null ? DEFAULT_SMOOTH_LEVEL : smoothLevel);
    }

    private static NeoPen create(
            float width,
            float minWidth,
            float displayScaleX,
            float displayScaleY,
            float scalePrecision,
            float createScale,
            @Nullable Float pressureSensitivity,
            boolean fastMode,
            float smoothLevel) {
        NeoPenConfig config = new NeoPenConfig();
        config.width = width + FOUNTAIN_PEN_V1_COMPENSATION / createScale;
        config.minWidth = minWidth / createScale;
        config.pressureSensitivity = pressureSensitivity == null
                ? DEFAULT_PRESSURE_SENSITIVITY : pressureSensitivity;
        config.scalePrecision = scalePrecision;
        config.smoothLevel = smoothLevel;
        config.tiltEnabled = false;
        config.fastMode = fastMode;
        config.displayScaleX = displayScaleX;
        config.displayScaleY = displayScaleY;
        return NeoFountainPenV2.Companion.create(config);
    }
}
