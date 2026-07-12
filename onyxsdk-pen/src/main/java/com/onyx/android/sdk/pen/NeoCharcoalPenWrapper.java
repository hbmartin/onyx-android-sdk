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
 *  com.onyx.android.sdk.pen.NeoCharcoalPen
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
import com.onyx.android.sdk.pen.NeoCharcoalPen;
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

public class NeoCharcoalPenWrapper {
    private static final int a = 5000;
    private static final int b = 1000;
    private static int c;

    /*
     * Unable to fully structure code
     */
    public static NeoRenderPoint[] computeStrokeRenderPoints(PenRenderArgs renderArgs, List<Bitmap> indexedPixelBitmapsResult) {
        block19: {
            block18: {
                v0 = renderArgs;
                var2_2 = v0.getScreenMatrix();
                var3_3 = v0.getPoints();
                var4_4 = (int)MatrixUtils.getMatrixRotateAngel((Matrix)var2_2);
                var5_6 = v1;
                var5_6();
                var5_6.setColor(var0.getColor());
                var5_6.setWidth(var0.getStrokeWidth());
                var5_6.setTiltEnabled(var0.isTiltEnabled());
                var5_6.setRotateAngle(var4_4);
                v1.setMaxTouchPressure(var0.getCreateArgs().getMaxPressure());
                var4_5 = NeoCharcoalPen.Companion.create((NeoPenConfig)var5_6);
                if (var4_5 == null) {
                    return new NeoRenderPoint[0];
                }
                var3_3 = NeoPenUtils.mapToPenCanvas(var3_3, var2_2);
                var5_6 = v2;
                v2 = new ArrayList<E>();
                if (var3_3.size() >= 2) break block18;
                v3 = new NeoRenderPoint[]{};
                var4_5.destroy();
                return v3;
            }
            var6_7 = 0;
            while (true) {
                if (var6_7 >= var3_3.size()) break;
                v4 = (com.onyx.android.sdk.data.note.TouchPoint)var3_3.get(var6_7);
                v5 = v4;
                v6 = v4.pressure / var0.getCreateArgs().getMaxPressure();
                v5.pressure = v6;
                ++var6_7;
                continue;
                break;
            }
            try {
                NeoPenUtils.readTextureResult((Pair<PenResult, PenResult>)var4_5.onPenDown((TouchPoint)var3_3.get(0), true), (List<Bitmap>)var1_1, (ArrayList<NeoRenderPoint>)var5_6);
                if (var3_3.size() <= 2) ** GOTO lbl53
            }
            catch (Throwable v7) {
                var4_5.destroy();
                throw v7;
            }
            v8 = var3_3.size();
            NeoPenUtils.readTextureResult((Pair<PenResult, PenResult>)var4_5.onPenMove(var3_3.subList(1, v8 - 1), null, true), (List<Bitmap>)var1_1, (ArrayList<NeoRenderPoint>)var5_6);
lbl53:
            // 2 sources

            v9 = var2_2;
            v10 = var5_6;
            v11 = var4_5;
            v12 = var3_3;
            v13 = v12;
            v14 = v12.size();
            NeoPenUtils.readTextureResult((Pair<PenResult, PenResult>)v11.onPenUp((TouchPoint)v13.get(v14 - 1), true), (List<Bitmap>)var1_1, (ArrayList<NeoRenderPoint>)var5_6);
            var0 = v10.toArray(new NeoRenderPoint[0]);
            var2_2 = v15;
            if (v9.invert(new Matrix())) break block19;
            v16 = new NeoRenderPoint[]{};
            var4_5.destroy();
            return v16;
        }
        v17 = NeoPenUtils.mapFromPenCanvas(var0, (List<Bitmap>)var1_1, var2_2);
        var4_5.destroy();
        return v17;
    }

    public static float[] computeStrokePoints(PenRenderArgs renderArgs, List<Bitmap> indexedPixelBitmapsResult) {
        float[] fArray;
        NeoRenderPoint[] neoRenderPointArray = NeoCharcoalPenWrapper.computeStrokeRenderPoints(renderArgs, (List<Bitmap>)fArray);
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
        int n2 = 1000;
        int n3 = 0;
        do {
            PenRenderArgs penRenderArgs;
            int n4 = n3;
            n3 = n4 + n2;
            List list2 = CollectionUtils.safelySubList(list, (int)n4, (int)n3);
            if (CollectionUtils.isNullOrEmpty((Collection)list2)) break;
            PenRenderArgs penRenderArgs2 = penRenderArgs;
            penRenderArgs2.setPoints(list2);
            NeoCharcoalPenWrapper.drawNormalStrokeImpl(penRenderArgs2);
        } while (n3 < n);
    }

    public static void drawNormalStrokeImpl(PenRenderArgs renderArgs) {
        PenRenderArgs penRenderArgs;
        ArrayList<Bitmap> arrayList;
        ArrayList<Bitmap> arrayList2 = arrayList;
        arrayList = new ArrayList<Bitmap>();
        NeoRenderPoint[] neoRenderPointArray = NeoCharcoalPenWrapper.computeStrokeRenderPoints(renderArgs, arrayList2);
        if (neoRenderPointArray == null) {
            return;
        }
        Paint paint = null;
        if (penRenderArgs.isErase()) {
            Paint paint2;
            paint = paint2;
            new Paint().setXfermode((Xfermode)new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }
        int n = neoRenderPointArray.length;
        for (int i = 0; i < n; ++i) {
            NeoRenderPoint neoRenderPoint;
            NeoRenderPoint neoRenderPoint2 = neoRenderPoint = neoRenderPointArray[i];
            neoRenderPoint = arrayList2.get(neoRenderPoint.bitmapIndex);
            float f = neoRenderPoint2.x;
            float f2 = neoRenderPoint2.y;
            penRenderArgs.getCanvas().drawBitmap((Bitmap)neoRenderPoint, f, f2, paint);
        }
        ArrayList<Bitmap> arrayList3 = arrayList2;
        BitmapUtils.recycle(arrayList3);
        arrayList3.clear();
    }

    public static void drawBigStroke(PenRenderArgs renderArgs) {
        List<com.onyx.android.sdk.data.note.TouchPoint> list = renderArgs.getPoints();
        int n = CollectionUtils.getSize(list);
        int n2 = 1000;
        int n3 = 0;
        do {
            PenRenderArgs penRenderArgs;
            int n4 = n3;
            n3 = n4 + n2;
            List list2 = CollectionUtils.safelySubList(list, (int)n4, (int)n3);
            if (CollectionUtils.isNullOrEmpty((Collection)list2)) break;
            PenRenderArgs penRenderArgs2 = penRenderArgs;
            penRenderArgs2.setPoints(list2);
            NeoCharcoalPenWrapper.drawBigStrokeImpl(penRenderArgs2);
        } while (n3 < n);
    }

    public static void drawBigStrokeImpl(PenRenderArgs renderArgs) {
        Object[] objectArray;
        if (renderArgs.getContentRect() != null && !objectArray.getContentRect().isEmpty()) {
            ArrayList<Bitmap> arrayList;
            ArrayList<Bitmap> arrayList2 = arrayList;
            arrayList = new ArrayList<Bitmap>();
            NeoRenderPoint[] neoRenderPointArray = NeoCharcoalPenWrapper.computeStrokeRenderPoints((PenRenderArgs)objectArray, arrayList2);
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
            return;
        }
        objectArray = new Object[]{};
        Debug.e(NeoCharcoalPenWrapper.class, (String)"empty view port", (Object[])objectArray);
    }
}

