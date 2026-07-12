package com.onyx.android.sdk.pen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.utils.CollectionUtils;
import java.util.List;

public class NeoMarkerPenWrapper {
    public static List<TouchPoint> computeStrokePoints(List<TouchPoint> points, float strokeWidth, float maxTouchPressure) {
        return computeStrokePoints(strokeWidth, points, maxTouchPressure);
    }

    public static void drawStroke(Canvas canvas, Paint paint, List<TouchPoint> list, float strokeWidth, boolean erase) {
        if (CollectionUtils.isNullOrEmpty(list)) {
            return;
        }
        Bitmap bitmapEnsurePenBitmapCreated = PenUtils.ensurePenBitmapCreated(new Rect(0, 0, canvas.getWidth(), canvas.getHeight()));
        Canvas canvas2 = new Canvas(bitmapEnsurePenBitmapCreated);
        if (erase) {
            canvas2 = canvas;
        }
        PenUtils.drawStrokeByPointSize(canvas2, paint, list, erase);
        if (erase) {
            return;
        }
        Rect rect = null;
        for (TouchPoint touchPoint : list) {
            Rect rect2 = rect;
            if (rect2 == null) {
                float f = touchPoint.x;
                float f2 = touchPoint.y;
                rect = new Rect((int) f, (int) f2, (int) f, (int) f2);
            } else {
                rect.union((int) touchPoint.x, (int) touchPoint.y);
            }
        }
        int i = -((int) (strokeWidth / 2.0f));
        rect.inset(i, i);
        int alpha = paint.getAlpha();
        paint.setAlpha(128);
        Rect rect4 = rect;
        canvas.drawBitmap(bitmapEnsurePenBitmapCreated, rect4, rect4, paint);
        paint.setAlpha(alpha);
    }

    public static List<TouchPoint> computeStrokePoints(float strokeWidth, List<TouchPoint> points, float maxTouchPressure) {
        return NeoPenUtils.computeStrokePoints(3, points, strokeWidth, maxTouchPressure);
    }
}

