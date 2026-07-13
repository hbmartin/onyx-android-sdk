package com.onyx.android.sdk.pen;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.onyx.android.sdk.data.PenConstant;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.utils.CollectionUtils;
import java.util.ArrayList;
import java.util.List;

public class NeoFountainPenWrapper {
    public static final float MIN_FOUNTAIN_PEN_WIDTH = 1.0f;
    private static final int a = 10;

    public static void drawStroke(Canvas canvas, Paint paint, List<TouchPoint> points, float displayScale, float strokeWidth, float maxTouchPressure, boolean erase) {
        List<TouchPoint> listComputeStrokePoints = computeStrokePoints(points, displayScale, strokeWidth, maxTouchPressure);
        if (listComputeStrokePoints == null) {
            return;
        }
        PenUtils.drawStrokeByPointSize(canvas, paint, listComputeStrokePoints, erase);
    }

    public static void drawStroke(Canvas canvas, Paint paint, List<TouchPoint> points,
                                  float displayScale, float strokeWidth, float maxTouchPressure,
                                  boolean erase, int rendererVersion) {
        List<TouchPoint> computed = computeStrokePoints(
                points, displayScale, strokeWidth, maxTouchPressure, rendererVersion);
        if (computed != null) {
            PenUtils.drawStrokeByPointSize(canvas, paint, computed, erase);
        }
    }

    public static List<TouchPoint> computeStrokePoints(List<TouchPoint> points, float displayScale, float strokeWidth, float maxTouchPressure) {
        return hasPressure(points) ? computeStrokePoints(points, strokeWidth, maxTouchPressure) : a(points, displayScale);
    }

    public static List<TouchPoint> computeStrokePoints(List<TouchPoint> points, float displayScale,
                                                        float strokeWidth, float maxTouchPressure,
                                                        int rendererVersion) {
        return hasPressure(points)
                ? computeStrokePoints(
                        points,
                        new NeoPenConfig()
                                .setWidth(strokeWidth)
                                .setRendererVersion(rendererVersion),
                        maxTouchPressure)
                : a(points, displayScale);
    }

    private static List<TouchPoint> a(List<TouchPoint> points, float scale) {
        ArrayList arrayList = new ArrayList();
        for (TouchPoint touchPoint : points) {
            TouchPoint touchPoint2 = new TouchPoint(touchPoint);
            touchPoint2.size = PenConstant.checkPenWidth(touchPoint.size * scale);
            arrayList.add(touchPoint2);
        }
        return arrayList;
    }

    public static boolean hasPressure(List<TouchPoint> points) {
        if (CollectionUtils.isNullOrEmpty(points)) {
            return false;
        }
        return points.stream().limit(10L).anyMatch(o -> {
            return o.getPressure() > com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY;
        });
    }

    public static List<TouchPoint> computeStrokePoints(List<TouchPoint> points, float strokeWidth, float maxTouchPressure) {
        return NeoPenUtils.computeStrokePoints(2, points, strokeWidth, maxTouchPressure);
    }

    public static List<TouchPoint> computeStrokePoints(List<TouchPoint> points,
                                                        NeoPenConfig config,
                                                        float maxTouchPressure) {
        return NeoPenUtils.computeStrokePoints(2, points, config, maxTouchPressure);
    }
}
