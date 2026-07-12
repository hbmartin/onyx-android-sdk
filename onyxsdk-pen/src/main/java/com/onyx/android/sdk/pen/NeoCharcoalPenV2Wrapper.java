/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Matrix
 *  android.graphics.Paint
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffXfermode
 *  android.graphics.Xfermode
 *  com.onyx.android.sdk.base.data.TouchPoint
 *  com.onyx.android.sdk.data.note.TouchPoint
 *  com.onyx.android.sdk.pen.NeoCharcoalPenV2
 *  com.onyx.android.sdk.pen.NeoPenConfig
 *  com.onyx.android.sdk.pen.PenResult
 *  com.onyx.android.sdk.utils.BitmapUtils
 *  com.onyx.android.sdk.utils.CollectionUtils
 *  com.onyx.android.sdk.utils.Debug
 *  com.onyx.android.sdk.utils.MatrixUtils
 *  kotlin.Pair
 */
package com.onyx.android.sdk.pen;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import com.onyx.android.sdk.base.data.TouchPoint;
import com.onyx.android.sdk.pen.NeoCharcoalPenV2;
import com.onyx.android.sdk.pen.NeoCharcoalPenWrapper;
import com.onyx.android.sdk.pen.NeoPenConfig;
import com.onyx.android.sdk.pen.NeoPenUtils;
import com.onyx.android.sdk.pen.NeoRenderPoint;
import com.onyx.android.sdk.pen.PenRenderArgs;
import com.onyx.android.sdk.pen.PenResult;
import com.onyx.android.sdk.utils.BitmapUtils;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.MatrixUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Pair;

public class NeoCharcoalPenV2Wrapper {
    private static final int a = 5000;
    private static final int b = 500;
    private static int c;

    /*
     * Unable to fully structure code
     */
    public static NeoRenderPoint[] computeStrokeRenderPoints(PenRenderArgs renderArgs, List<Bitmap> pixelBitmapPool) {
        block19: {
            block18: {
                var2_2 = (int)MatrixUtils.getMatrixRotateAngel((Matrix)renderArgs.getScreenMatrix());
                var3_4 = v0;
                var3_4();
                var3_4.setColor(var0.getColor());
                var3_4.setWidth(var0.getStrokeWidth());
                var3_4.setTiltEnabled(var0.isTiltEnabled());
                var3_4.setMaxTouchPressure(var0.getCreateArgs().getMaxPressure());
                v0.setRotateAngle(var2_2);
                var2_3 = NeoCharcoalPenV2.Companion.create((NeoPenConfig)var3_4);
                if (var2_3 == null) {
                    return new NeoRenderPoint[0];
                }
                var3_4 = NeoCharcoalPenV2Wrapper.a(var0.getPoints(), var0.getScreenMatrix());
                var4_5 = v1;
                v1 = new ArrayList<NeoRenderPoint>();
                if (var3_4.size() >= 2) break block18;
                v2 = new NeoRenderPoint[]{};
                var2_3.destroy();
                return v2;
            }
            var5_6 = 0;
            while (true) {
                if (var5_6 >= var3_4.size()) break;
                v3 = (com.onyx.android.sdk.data.note.TouchPoint)var3_4.get(var5_6);
                v4 = v3;
                v5 = v3.pressure / var0.getCreateArgs().getMaxPressure();
                v4.pressure = v5;
                ++var5_6;
                continue;
                break;
            }
            try {
                NeoPenUtils.readTextureResult((Pair<PenResult, PenResult>)var2_3.onPenDown((TouchPoint)var3_4.get(0), true), (List<Bitmap>)var1_1, var4_5);
                if (var3_4.size() <= 2) ** GOTO lbl50
            }
            catch (Throwable v6) {
                var2_3.destroy();
                throw v6;
            }
            v7 = var3_4.size();
            NeoPenUtils.readTextureResult((Pair<PenResult, PenResult>)var2_3.onPenMove(var3_4.subList(1, v7 - 1), null, true), (List<Bitmap>)var1_1, var4_5);
lbl50:
            // 2 sources

            v8 = var0;
            v9 = var4_5;
            v10 = var2_3;
            v11 = var3_4;
            v12 = v11;
            v13 = v11.size();
            NeoPenUtils.readTextureResult((Pair<PenResult, PenResult>)v10.onPenUp((TouchPoint)v12.get(v13 - 1), true), (List<Bitmap>)var1_1, var4_5);
            var0 = v9.toArray(new NeoRenderPoint[0]);
            var3_4 = v14;
            v14 = new Matrix();
            if (v8.getScreenMatrix().invert((Matrix)var3_4)) break block19;
            v15 = new NeoRenderPoint[]{};
            var2_3.destroy();
            return v15;
        }
        v16 = NeoCharcoalPenV2Wrapper.a(var0, (List<Bitmap>)var1_1, (Matrix)var3_4);
        var2_3.destroy();
        return v16;
    }

    public static float[] computeStrokePoints(PenRenderArgs renderArgs, ArrayList<Bitmap> bitmaps) {
        float[] fArray;
        NeoRenderPoint[] neoRenderPointArray = NeoCharcoalPenV2Wrapper.computeStrokeRenderPoints(renderArgs, (List<Bitmap>)fArray);
        if (neoRenderPointArray == null) {
            return new float[0];
        }
        fArray = new float[neoRenderPointArray.length * 3];
        for (int i = 0; i < neoRenderPointArray.length; ++i) {
            int n = i;
            int n2 = n * 3;
            fArray[n2] = neoRenderPointArray[n].x;
            int n3 = n2 + 1;
            fArray[n3] = neoRenderPointArray[i].y;
            fArray[n2 += 2] = neoRenderPointArray[i].bitmapIndex;
        }
        return fArray;
    }

    public static void drawNormalStroke(PenRenderArgs renderArgs) {
        List<com.onyx.android.sdk.data.note.TouchPoint> list = renderArgs.getPoints();
        int n = CollectionUtils.getSize(list);
        int n2 = 500;
        int n3 = 0;
        do {
            PenRenderArgs penRenderArgs;
            int n4 = n3;
            n3 = n4 + n2;
            List list2 = CollectionUtils.safelySubList(list, (int)n4, (int)n3);
            if (CollectionUtils.isNullOrEmpty((Collection)list2)) break;
            PenRenderArgs penRenderArgs2 = penRenderArgs;
            penRenderArgs2.setPoints(list2);
            NeoCharcoalPenV2Wrapper.b(penRenderArgs2);
        } while (n3 < n);
    }

    private static void b(PenRenderArgs renderArgs) {
        ArrayList<Bitmap> arrayList;
        PenRenderArgs penRenderArgs = renderArgs;
        ArrayList<Bitmap> arrayList2 = arrayList;
        NeoRenderPoint[] neoRenderPointArray = NeoCharcoalPenV2Wrapper.computeStrokeRenderPoints(penRenderArgs, new ArrayList<Bitmap>());
        Paint paint = null;
        if (penRenderArgs.isErase()) {
            Paint paint2;
            paint = paint2;
            new Paint().setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }
        int n = neoRenderPointArray.length;
        for (int i = 0; i < n; ++i) {
            PenRenderArgs penRenderArgs2;
            NeoRenderPoint neoRenderPoint;
            NeoRenderPoint neoRenderPoint2 = neoRenderPoint = neoRenderPointArray[i];
            neoRenderPoint = arrayList2.get(neoRenderPoint.bitmapIndex);
            float f = neoRenderPoint2.x;
            float f2 = neoRenderPoint2.y;
            penRenderArgs2.getCanvas().drawBitmap((Bitmap)neoRenderPoint, f, f2, paint);
        }
        ArrayList<Bitmap> arrayList3 = arrayList2;
        BitmapUtils.recycle(arrayList3);
        arrayList3.clear();
    }

    public static void drawBigStroke(PenRenderArgs renderArgs) {
        List<com.onyx.android.sdk.data.note.TouchPoint> list = renderArgs.getPoints();
        int n = CollectionUtils.getSize(list);
        int n2 = 500;
        int n3 = 0;
        do {
            PenRenderArgs penRenderArgs;
            int n4 = n3;
            n3 = n4 + n2;
            List list2 = CollectionUtils.safelySubList(list, (int)n4, (int)n3);
            if (CollectionUtils.isNullOrEmpty((Collection)list2)) break;
            PenRenderArgs penRenderArgs2 = penRenderArgs;
            penRenderArgs2.setPoints(list2);
            NeoCharcoalPenV2Wrapper.a(penRenderArgs2);
        } while (n3 < n);
    }

    private static void a(PenRenderArgs renderArgs) {
        Object[] objectArray;
        if (renderArgs.getContentRect() != null && !objectArray.getContentRect().isEmpty()) {
            ArrayList<Bitmap> arrayList;
            ArrayList<Bitmap> arrayList2 = arrayList;
            arrayList = new ArrayList<Bitmap>();
            NeoRenderPoint[] neoRenderPointArray = NeoCharcoalPenV2Wrapper.computeStrokeRenderPoints((PenRenderArgs)objectArray, arrayList2);
            if (neoRenderPointArray == null) {
                return;
            }
            Paint paint = null;
            if (objectArray.isErase()) {
                Paint paint2;
                paint = paint2;
                new Paint().setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            }
            if ((c += CollectionUtils.getSize(objectArray.getPoints())) > 5000) {
                System.gc();
                c = 0;
            }
            for (NeoRenderPoint neoRenderPoint : neoRenderPointArray) {
                Matrix matrix;
                Matrix matrix2 = matrix;
                NeoRenderPoint neoRenderPoint2 = neoRenderPoint;
                matrix2();
                float f = neoRenderPoint2.x;
                matrix2.postTranslate(f, neoRenderPoint2.y);
                matrix.postConcat(objectArray.getRenderMatrix());
                objectArray.getCanvas().drawBitmap(arrayList2.get(neoRenderPoint.bitmapIndex), matrix2, paint);
            }
            ArrayList<Bitmap> arrayList3 = arrayList2;
            BitmapUtils.recycle(arrayList3);
            arrayList3.clear();
            return;
        }
        objectArray = new Object[]{};
        Debug.e(NeoCharcoalPenWrapper.class, (String)"empty view port", (Object[])objectArray);
    }

    private static ArrayList<com.onyx.android.sdk.data.note.TouchPoint> a(List<com.onyx.android.sdk.data.note.TouchPoint> points, Matrix matrix) {
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
    private static NeoRenderPoint[] a(NeoRenderPoint[] points, List<Bitmap> pixelBitmapPool, Matrix matrix) {
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
}

