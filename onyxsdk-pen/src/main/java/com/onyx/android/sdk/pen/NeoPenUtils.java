package com.onyx.android.sdk.pen;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.Debug;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;

public class NeoPenUtils {
    public static List<TouchPoint> computeStrokePoints(int type, List<TouchPoint> points, float strokeWidth, float maxTouchPressure) {
        NeoPenConfig neoPenConfig = new NeoPenConfig();
        neoPenConfig.setWidth(strokeWidth);
        return computeStrokePoints(type, points, neoPenConfig, maxTouchPressure);
    }

    /**
     * Computes a stroke with the supplied native configuration. Existing
     * callers continue to use the recovered-v1 defaults through the overload
     * above; this overload makes newer renderer features additive.
     */
    public static List<TouchPoint> computeStrokePoints(int type, List<TouchPoint> points, NeoPenConfig config, float maxTouchPressure) {
        if (points.size() < 2) {
            return new ArrayList();
        }
        NeoPenConfig neoPenConfig = config == null ? new NeoPenConfig() : config;
        NeoPen neoPenCreate = null;
        if (type == 1) {
            neoPenCreate = NeoBrushPen.Companion.create(neoPenConfig);
        } else if (type == 2) {
            neoPenCreate = NeoFountainPen.Companion.create(neoPenConfig);
        } else if (type == 3) {
            neoPenCreate = NeoMarkerPen.Companion.create(neoPenConfig);
        }
        if (neoPenCreate == null) {
            Debug.e(NeoPenUtils.class, "invalid pen type: " + type, new Object[0]);
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        try {
            // The native APIs normalize against NeoPenConfig.maxTouchPressure.
            // Keep the caller's point list unchanged so rendering is repeatable.
            ArrayList<TouchPoint> normalizedPoints = new ArrayList<>(points.size());
            for (TouchPoint point : points) {
                TouchPoint copy = new TouchPoint(point);
                copy.pressure = maxTouchPressure > 0.0f ? copy.pressure / maxTouchPressure : 0.0f;
                normalizedPoints.add(copy);
            }
            readPointResult(neoPenCreate.onPenDown(normalizedPoints.get(0), true), arrayList);
            if (normalizedPoints.size() > 2) {
                readPointResult(neoPenCreate.onPenMove(normalizedPoints.subList(1, normalizedPoints.size() - 1), null, true), arrayList);
            }
            readPointResult(neoPenCreate.onPenUp(normalizedPoints.get(normalizedPoints.size() - 1), true), arrayList);
            return arrayList;
        } finally {
            neoPenCreate.destroy();
        }
    }

    public static ArrayList<TouchPoint> mapToPenCanvas(List<TouchPoint> points, Matrix screenMatrix) {
        float[] fArr = new float[points.size() * 2];
        for (int i = 0; i < points.size(); i++) {
            int i2 = i;
            int i3 = i2 * 2;
            fArr[i3] = points.get(i2).x;
            fArr[i3 + 1] = points.get(i).y;
        }
        screenMatrix.mapPoints(fArr);
        ArrayList<TouchPoint> arrayList = new ArrayList<>();
        for (int i4 = 0; i4 < points.size(); i4++) {
            TouchPoint touchPoint = new TouchPoint(points.get(i4));
            int i5 = i4 * 2;
            touchPoint.x = fArr[i5];
            touchPoint.y = fArr[i5 + 1];
            arrayList.add(touchPoint);
        }
        return arrayList;
    }

    public static NeoRenderPoint[] mapFromPenCanvas(NeoRenderPoint[] points, List<Bitmap> pixelBitmapPool, Matrix matrix) {
        float[] fArr = new float[points.length * 2];
        for (int i = 0; i < points.length; i++) {
            NeoRenderPoint neoRenderPoint = points[i];
            int i2 = i * 2;
            fArr[i2] = neoRenderPoint.x + (pixelBitmapPool.get(neoRenderPoint.bitmapIndex).getWidth() / 2.0f);
            fArr[i2 + 1] = neoRenderPoint.y + (pixelBitmapPool.get(neoRenderPoint.bitmapIndex).getHeight() / 2.0f);
        }
        matrix.mapPoints(fArr);
        NeoRenderPoint[] neoRenderPointArr = new NeoRenderPoint[points.length];
        for (int i3 = 0; i3 < points.length; i3++) {
            NeoRenderPoint neoRenderPointCreate = NeoRenderPoint.create(points[i3]);
            int i4 = i3 * 2;
            neoRenderPointCreate.x = fArr[i4] - (pixelBitmapPool.get(neoRenderPointCreate.bitmapIndex).getWidth() / 2.0f);
            neoRenderPointCreate.y = fArr[i4 + 1] - (pixelBitmapPool.get(neoRenderPointCreate.bitmapIndex).getHeight() / 2.0f);
            neoRenderPointArr[i3] = neoRenderPointCreate;
        }
        return neoRenderPointArr;
    }

    public static void readPointResult(Pair<PenResult, PenResult> result, List<TouchPoint> points) {
        if (result == null || result.getFirst() == null || !(result.getFirst() instanceof PenPointResult)) {
            return;
        }
        for (PenPointInk penPointInk : ((PenPointResult) result.getFirst()).getPoints()) {
            TouchPoint touchPoint = new TouchPoint(penPointInk.getX(), penPointInk.getY());
            touchPoint.size = penPointInk.getSize();
            points.add(touchPoint);
        }
    }

    public static void readPointResults(List<Pair<PenResult, PenResult>> results, List<TouchPoint> points) {
        if (CollectionUtils.isNullOrEmpty(results)) {
            return;
        }
        Iterator<Pair<PenResult, PenResult>> it = results.iterator();
        while (it.hasNext()) {
            readPointResult(it.next(), points);
        }
    }

    public static void readTextureResult(Pair<PenResult, PenResult> result, List<Bitmap> indexedPixelBitmapsResult, ArrayList<NeoRenderPoint> resultPoints) {
        if (result == null || result.getFirst() == null) {
            return;
        }
        for (PenTextureInk penTextureInk : ((PenTextureResult) result.getFirst()).getTextures()) {
            indexedPixelBitmapsResult.add(penTextureInk.getBitmap());
            NeoRenderPoint neoRenderPoint = new NeoRenderPoint();
            neoRenderPoint.x = penTextureInk.getX();
            neoRenderPoint.y = penTextureInk.getY();
            neoRenderPoint.bitmapIndex = indexedPixelBitmapsResult.size() - 1;
            resultPoints.add(neoRenderPoint);
        }
    }
}
