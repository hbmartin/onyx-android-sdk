package com.onyx.android.sdk.pen;

import android.graphics.Rect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Immutable SDK-observed raw drawing configuration.
 *
 * <p>The firmware exposes setters but no complete getter transaction, so this is an honest
 * process-local snapshot of values applied through {@link TouchHelper}, seeded with the recovered
 * firmware defaults. It must not be interpreted as an independent firmware round trip.</p>
 */
public final class RawDrawingConfigurationSnapshot {
    public enum Evidence { SDK_OBSERVED, RECOVERED_DEFAULTS }

    private final int strokeStyle;
    private final int strokeColor;
    private final float strokeWidth;
    private final boolean brushEnabled;
    private final boolean eraserEnabled;
    private final int eraserStyle;
    private final boolean sideButtonEraserEnabled;
    private final boolean singleRegionMode;
    private final List<Rect> limitRegions;
    private final List<Rect> excludeRegions;
    private final Evidence evidence;

    public RawDrawingConfigurationSnapshot(
            int strokeStyle,
            int strokeColor,
            float strokeWidth,
            boolean brushEnabled,
            boolean eraserEnabled,
            int eraserStyle,
            boolean sideButtonEraserEnabled,
            boolean singleRegionMode,
            List<Rect> limitRegions,
            List<Rect> excludeRegions,
            Evidence evidence) {
        if (!Float.isFinite(strokeWidth) || strokeWidth <= 0.0f) {
            throw new IllegalArgumentException("strokeWidth must be finite and positive");
        }
        this.strokeStyle = strokeStyle;
        this.strokeColor = strokeColor;
        this.strokeWidth = strokeWidth;
        this.brushEnabled = brushEnabled;
        this.eraserEnabled = eraserEnabled;
        this.eraserStyle = eraserStyle;
        this.sideButtonEraserEnabled = sideButtonEraserEnabled;
        this.singleRegionMode = singleRegionMode;
        this.limitRegions = immutableRects(limitRegions);
        this.excludeRegions = immutableRects(excludeRegions);
        this.evidence = evidence == null ? Evidence.SDK_OBSERVED : evidence;
    }

    static RawDrawingConfigurationSnapshot defaults() {
        return new RawDrawingConfigurationSnapshot(
                TouchHelper.STROKE_STYLE_FOUNTAIN,
                0xff000000,
                3.0f,
                true,
                false,
                5,
                true,
                false,
                Collections.emptyList(),
                Collections.emptyList(),
                Evidence.RECOVERED_DEFAULTS);
    }

    RawDrawingConfigurationSnapshot observed(
            Integer style,
            Integer color,
            Float width,
            Boolean brush,
            Boolean eraser,
            Integer eraserStyleValue,
            Boolean sideButton,
            Boolean singleRegion,
            List<Rect> limits,
            List<Rect> excludes) {
        return new RawDrawingConfigurationSnapshot(
                style == null ? strokeStyle : style,
                color == null ? strokeColor : color,
                width == null ? strokeWidth : width,
                brush == null ? brushEnabled : brush,
                eraser == null ? eraserEnabled : eraser,
                eraserStyleValue == null ? eraserStyle : eraserStyleValue,
                sideButton == null ? sideButtonEraserEnabled : sideButton,
                singleRegion == null ? singleRegionMode : singleRegion,
                limits == null ? limitRegions : limits,
                excludes == null ? excludeRegions : excludes,
                Evidence.SDK_OBSERVED);
    }

    public int getStrokeStyle() { return strokeStyle; }
    public int getStrokeColor() { return strokeColor; }
    public float getStrokeWidth() { return strokeWidth; }
    public boolean isBrushEnabled() { return brushEnabled; }
    public boolean isEraserEnabled() { return eraserEnabled; }
    public int getEraserStyle() { return eraserStyle; }
    public boolean isSideButtonEraserEnabled() { return sideButtonEraserEnabled; }
    public boolean isSingleRegionMode() { return singleRegionMode; }
    public List<Rect> getLimitRegions() { return mutableRectCopy(limitRegions); }
    public List<Rect> getExcludeRegions() { return mutableRectCopy(excludeRegions); }
    public Evidence getEvidence() { return evidence; }

    private static List<Rect> immutableRects(List<Rect> source) {
        return Collections.unmodifiableList(mutableRectCopy(source));
    }

    private static List<Rect> mutableRectCopy(List<Rect> source) {
        ArrayList<Rect> copy = new ArrayList<>();
        if (source != null) {
            for (Rect rect : source) {
                if (rect != null) copy.add(new Rect(rect));
            }
        }
        return copy;
    }
}
