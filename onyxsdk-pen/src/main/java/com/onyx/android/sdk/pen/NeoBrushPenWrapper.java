package com.onyx.android.sdk.pen;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.onyx.android.sdk.data.note.TouchPoint;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/NeoBrushPenWrapper.class */
public class NeoBrushPenWrapper {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static List<TouchPoint> computeStrokePoints(List<TouchPoint> points, float strokeWidth, float maxTouchPressure) {
        return computeStrokePoints(strokeWidth, points, maxTouchPressure);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void drawStroke(Canvas canvas, Paint paint, List<TouchPoint> points, float strokeWidth, float maxTouchPressure, boolean erase) {
        List<TouchPoint> listComputeStrokePoints = computeStrokePoints(points, strokeWidth, maxTouchPressure);
        if (listComputeStrokePoints == null) {
            return;
        }
        PenUtils.drawStrokeByPointSize(canvas, paint, listComputeStrokePoints, erase);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public static List<TouchPoint> computeStrokePoints(float strokeWidth, List<TouchPoint> points, float maxTouchPressure) {
        return NeoPenUtils.computeStrokePoints(1, points, strokeWidth, maxTouchPressure);
    }
}

