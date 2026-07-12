/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Matrix
 *  com.onyx.android.sdk.base.data.TouchPoint
 *  com.onyx.android.sdk.data.note.TouchPoint
 *  com.onyx.android.sdk.pen.NeoBrushPen
 *  com.onyx.android.sdk.pen.NeoFountainPen
 *  com.onyx.android.sdk.pen.NeoMarkerPen
 *  com.onyx.android.sdk.pen.NeoPen
 *  com.onyx.android.sdk.pen.NeoPenConfig
 *  com.onyx.android.sdk.pen.PenPointInk
 *  com.onyx.android.sdk.pen.PenPointResult
 *  com.onyx.android.sdk.pen.PenResult
 *  com.onyx.android.sdk.pen.PenTextureInk
 *  com.onyx.android.sdk.pen.PenTextureResult
 *  com.onyx.android.sdk.utils.CollectionUtils
 *  com.onyx.android.sdk.utils.Debug
 *  kotlin.Pair
 */
package com.onyx.android.sdk.pen;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import com.onyx.android.sdk.base.data.TouchPoint;
import com.onyx.android.sdk.pen.NeoBrushPen;
import com.onyx.android.sdk.pen.NeoFountainPen;
import com.onyx.android.sdk.pen.NeoMarkerPen;
import com.onyx.android.sdk.pen.NeoPen;
import com.onyx.android.sdk.pen.NeoPenConfig;
import com.onyx.android.sdk.pen.NeoRenderPoint;
import com.onyx.android.sdk.pen.PenPointInk;
import com.onyx.android.sdk.pen.PenPointResult;
import com.onyx.android.sdk.pen.PenResult;
import com.onyx.android.sdk.pen.PenTextureInk;
import com.onyx.android.sdk.pen.PenTextureResult;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.Debug;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Pair;

public class NeoPenUtils {
    /*
     * WARNING - void declaration
     */
    public static List<com.onyx.android.sdk.data.note.TouchPoint> computeStrokePoints(int type, List<com.onyx.android.sdk.data.note.TouchPoint> points, float strokeWidth, float maxTouchPressure) {
        void var1_3;
        ArrayList<com.onyx.android.sdk.data.note.TouchPoint> arrayList;
        int n;
        void var2_4;
        NeoPenConfig neoPenConfig;
        if (points.size() < 2) {
            return new ArrayList<com.onyx.android.sdk.data.note.TouchPoint>();
        }
        NeoPenConfig neoPenConfig2 = neoPenConfig;
        neoPenConfig2();
        neoPenConfig.setWidth((float)var2_4);
        NeoPen neoPen = null;
        if (n != 1) {
            if (n != 2) {
                if (n == 3) {
                    neoPen = NeoMarkerPen.Companion.create(neoPenConfig2);
                }
            } else {
                neoPen = NeoFountainPen.Companion.create(neoPenConfig2);
            }
        } else {
            neoPen = NeoBrushPen.Companion.create(neoPenConfig2);
        }
        if (neoPen == null) {
            Object[] objectArray = new Object[]{};
            Debug.e(NeoPenUtils.class, (String)("invalid pen type: " + n), (Object[])objectArray);
            return new ArrayList<com.onyx.android.sdk.data.note.TouchPoint>();
        }
        ArrayList<com.onyx.android.sdk.data.note.TouchPoint> arrayList2 = arrayList;
        arrayList = new ArrayList<com.onyx.android.sdk.data.note.TouchPoint>();
        for (int i = 0; i < var1_3.size(); ++i) {
            void var3_6;
            ((com.onyx.android.sdk.data.note.TouchPoint)var1_3.get((int)i)).pressure /= var3_6;
        }
        NeoPenUtils.readPointResult((Pair<PenResult, PenResult>)neoPen.onPenDown((TouchPoint)var1_3.get(0), true), arrayList2);
        if (var1_3.size() > 2) {
            void v2 = var1_3;
            NeoPenUtils.readPointResult((Pair<PenResult, PenResult>)neoPen.onPenMove(v2.subList(1, v2.size() - 1), null, true), arrayList2);
        }
        NeoPen neoPen2 = neoPen;
        void v4 = var1_3;
        NeoPenUtils.readPointResult((Pair<PenResult, PenResult>)neoPen2.onPenUp((TouchPoint)v4.get(v4.size() - 1), true), arrayList2);
        neoPen2.destroy();
        return arrayList2;
    }

    public static ArrayList<com.onyx.android.sdk.data.note.TouchPoint> mapToPenCanvas(List<com.onyx.android.sdk.data.note.TouchPoint> points, Matrix screenMatrix) {
        ArrayList<com.onyx.android.sdk.data.note.TouchPoint> arrayList;
        ArrayList<com.onyx.android.sdk.data.note.TouchPoint> arrayList2;
        List<com.onyx.android.sdk.data.note.TouchPoint> list;
        int n;
        float[] fArray = new float[points.size() * 2];
        for (n = 0; n < list.size(); ++n) {
            int n2 = n;
            int n3 = n2 * 2;
            fArray[n3] = list.get((int)n2).x;
            fArray[++n3] = list.get((int)n).y;
        }
        arrayList2.mapPoints(fArray);
        arrayList2 = arrayList;
        arrayList = new ArrayList<com.onyx.android.sdk.data.note.TouchPoint>();
        for (n = 0; n < list.size(); ++n) {
            com.onyx.android.sdk.data.note.TouchPoint touchPoint;
            com.onyx.android.sdk.data.note.TouchPoint touchPoint2 = touchPoint;
            touchPoint2(list.get(n));
            int n4 = n * 2;
            touchPoint2.x = fArray[n4];
            touchPoint.y = fArray[n4 + 1];
            arrayList2.add(touchPoint);
        }
        return arrayList2;
    }

    /*
     * WARNING - void declaration
     */
    public static NeoRenderPoint[] mapFromPenCanvas(NeoRenderPoint[] points, List<Bitmap> pixelBitmapPool, Matrix matrix) {
        NeoRenderPoint[] neoRenderPointArray;
        void var1_1;
        int n;
        NeoRenderPoint neoRenderPoint;
        NeoRenderPoint[] neoRenderPointArray2;
        int n2;
        float[] fArray = new float[points.length * 2];
        for (n2 = 0; n2 < neoRenderPointArray2.length; ++n2) {
            neoRenderPoint = neoRenderPointArray2[n2];
            n = n2 * 2;
            fArray[n] = neoRenderPoint.x + (float)((Bitmap)var1_1.get(neoRenderPoint.bitmapIndex)).getWidth() / 2.0f;
            fArray[n + 1] = neoRenderPoint.y + (float)((Bitmap)var1_1.get(neoRenderPoint.bitmapIndex)).getHeight() / 2.0f;
        }
        neoRenderPointArray.mapPoints(fArray);
        neoRenderPointArray = new NeoRenderPoint[neoRenderPointArray2.length];
        for (n2 = 0; n2 < neoRenderPointArray2.length; ++n2) {
            neoRenderPoint = NeoRenderPoint.create(neoRenderPointArray2[n2]);
            n = n2 * 2;
            neoRenderPoint.x = fArray[n] - (float)((Bitmap)var1_1.get(neoRenderPoint.bitmapIndex)).getWidth() / 2.0f;
            NeoRenderPoint.create(neoRenderPointArray2[n2]).y = fArray[n + 1] - (float)((Bitmap)var1_1.get(neoRenderPoint.bitmapIndex)).getHeight() / 2.0f;
            neoRenderPointArray[n2] = neoRenderPoint;
        }
        return neoRenderPointArray;
    }

    /*
     * WARNING - void declaration
     */
    public static void readPointResult(Pair<PenResult, PenResult> result, List<com.onyx.android.sdk.data.note.TouchPoint> points) {
        Iterator iterator;
        if (result != null && iterator.getFirst() != null && iterator.getFirst() instanceof PenPointResult) {
            iterator = ((PenPointResult)iterator.getFirst()).getPoints().iterator();
            while (iterator.hasNext()) {
                void var1_1;
                PenPointInk penPointInk;
                PenPointInk penPointInk2 = penPointInk = (PenPointInk)iterator.next();
                float f = penPointInk2.getX();
                new com.onyx.android.sdk.data.note.TouchPoint(f, penPointInk2.getY()).size = penPointInk.getSize();
                var1_1.add(new com.onyx.android.sdk.data.note.TouchPoint(f, penPointInk2.getY()));
            }
        }
    }

    /*
     * WARNING - void declaration
     */
    public static void readPointResults(List<Pair<PenResult, PenResult>> results, List<com.onyx.android.sdk.data.note.TouchPoint> points) {
        Iterator<Pair<PenResult, PenResult>> iterator;
        if (CollectionUtils.isNullOrEmpty(results)) {
            return;
        }
        iterator = iterator.iterator();
        while (iterator.hasNext()) {
            void var1_1;
            NeoPenUtils.readPointResult((Pair<PenResult, PenResult>)((Pair)iterator.next()), (List<com.onyx.android.sdk.data.note.TouchPoint>)var1_1);
        }
    }

    /*
     * WARNING - void declaration
     */
    public static void readTextureResult(Pair<PenResult, PenResult> result, List<Bitmap> indexedPixelBitmapsResult, ArrayList<NeoRenderPoint> resultPoints) {
        Object object;
        if (result != null && object.getFirst() != null) {
            for (PenTextureInk penTextureInk : ((PenTextureResult)object.getFirst()).getTextures()) {
                void var2_2;
                NeoRenderPoint neoRenderPoint;
                void var1_1;
                var1_1.add(penTextureInk.getBitmap());
                NeoRenderPoint neoRenderPoint2 = neoRenderPoint;
                neoRenderPoint2();
                neoRenderPoint2.x = penTextureInk.getX();
                neoRenderPoint2.y = penTextureInk.getY();
                neoRenderPoint.bitmapIndex = var1_1.size() - 1;
                var2_2.add(neoRenderPoint);
            }
        }
    }
}

