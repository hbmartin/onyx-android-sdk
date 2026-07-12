package com.onyx.android.sdk.pen;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.utils.BitmapUtils;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.MatrixUtils;
import java.util.ArrayList;
import java.util.List;

public class NeoCharcoalPenV2Wrapper {
    private static final int a = 5000;
    private static final int b = 500;
    private static int c;

    public static NeoRenderPoint[] computeStrokeRenderPoints(PenRenderArgs renderArgs, List<Bitmap> pixelBitmapPool) {
        int matrixRotateAngel = (int) MatrixUtils.getMatrixRotateAngel(renderArgs.getScreenMatrix());
        NeoPenConfig neoPenConfig = new NeoPenConfig();
        neoPenConfig.setColor(renderArgs.getColor());
        neoPenConfig.setWidth(renderArgs.getStrokeWidth());
        neoPenConfig.setTiltEnabled(renderArgs.isTiltEnabled());
        neoPenConfig.setMaxTouchPressure(renderArgs.getCreateArgs().getMaxPressure());
        neoPenConfig.setRotateAngle(matrixRotateAngel);
        NeoPen pen = NeoCharcoalPenV2.Companion.create(neoPenConfig);
        if (pen == null) {
            return new NeoRenderPoint[0];
        }
        try {
            ArrayList<TouchPoint> arrayListA = a(renderArgs.getPoints(), renderArgs.getScreenMatrix());
            ArrayList arrayList = new ArrayList();
            if (arrayListA.size() < 2) {
                NeoRenderPoint[] neoRenderPointArr = new NeoRenderPoint[0];
                pen.destroy();
                return neoRenderPointArr;
            }
            for (int i = 0; i < arrayListA.size(); i++) {
                arrayListA.get(i).pressure /= renderArgs.getCreateArgs().getMaxPressure();
            }
            NeoPenUtils.readTextureResult(pen.onPenDown(arrayListA.get(0), true), pixelBitmapPool, arrayList);
            if (arrayListA.size() > 2) {
                NeoPenUtils.readTextureResult(pen.onPenMove(arrayListA.subList(1, arrayListA.size() - 1), null, true), pixelBitmapPool, arrayList);
            }
            NeoPenUtils.readTextureResult(pen.onPenUp(arrayListA.get(arrayListA.size() - 1), true), pixelBitmapPool, arrayList);
            NeoRenderPoint[] neoRenderPointArr2 = (NeoRenderPoint[]) arrayList.toArray(new NeoRenderPoint[0]);
            Matrix matrix = new Matrix();
            if (renderArgs.getScreenMatrix().invert(matrix)) {
                NeoRenderPoint[] neoRenderPointArrA = a(neoRenderPointArr2, pixelBitmapPool, matrix);
                pen.destroy();
                return neoRenderPointArrA;
            }
            NeoRenderPoint[] neoRenderPointArr3 = new NeoRenderPoint[0];
            pen.destroy();
            return neoRenderPointArr3;
        } catch (Throwable th) {
            pen.destroy();
            throw th;
        }
    }

    public static float[] computeStrokePoints(PenRenderArgs renderArgs, ArrayList<Bitmap> bitmaps) {
        NeoRenderPoint[] neoRenderPointArrComputeStrokeRenderPoints = computeStrokeRenderPoints(renderArgs, bitmaps);
        if (neoRenderPointArrComputeStrokeRenderPoints == null) {
            return new float[0];
        }
        float[] fArr = new float[neoRenderPointArrComputeStrokeRenderPoints.length * 3];
        for (int i = 0; i < neoRenderPointArrComputeStrokeRenderPoints.length; i++) {
            int i2 = i;
            int i3 = i2 * 3;
            fArr[i3] = neoRenderPointArrComputeStrokeRenderPoints[i2].x;
            fArr[i3 + 1] = neoRenderPointArrComputeStrokeRenderPoints[i].y;
            fArr[i3 + 2] = neoRenderPointArrComputeStrokeRenderPoints[i].bitmapIndex;
        }
        return fArr;
    }

    public static void drawNormalStroke(PenRenderArgs renderArgs) {
        List<TouchPoint> points = renderArgs.getPoints();
        int size = CollectionUtils.getSize(points);
        int i = 0;
        do {
            int i2 = i;
            int i3 = i2 + b;
            i = i3;
            List<TouchPoint> listSafelySubList = CollectionUtils.safelySubList(points, i2, i3);
            if (CollectionUtils.isNullOrEmpty(listSafelySubList)) {
                return;
            }
            renderArgs.setPoints(listSafelySubList);
            b(renderArgs);
        } while (i < size);
    }

    private static void b(PenRenderArgs renderArgs) {
        ArrayList arrayList = new ArrayList();
        NeoRenderPoint[] neoRenderPointArrComputeStrokeRenderPoints = computeStrokeRenderPoints(renderArgs, arrayList);
        Paint paint = null;
        if (renderArgs.isErase()) {
            Paint paint2 = new Paint();
            paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            paint = paint2;
        }
        for (NeoRenderPoint neoRenderPoint : neoRenderPointArrComputeStrokeRenderPoints) {
            renderArgs.getCanvas().drawBitmap((Bitmap) arrayList.get(neoRenderPoint.bitmapIndex), neoRenderPoint.x, neoRenderPoint.y, paint);
        }
        BitmapUtils.recycle(arrayList);
        arrayList.clear();
    }

    public static void drawBigStroke(PenRenderArgs renderArgs) {
        List<TouchPoint> points = renderArgs.getPoints();
        int size = CollectionUtils.getSize(points);
        int i = 0;
        do {
            int i2 = i;
            int i3 = i2 + b;
            i = i3;
            List<TouchPoint> listSafelySubList = CollectionUtils.safelySubList(points, i2, i3);
            if (CollectionUtils.isNullOrEmpty(listSafelySubList)) {
                return;
            }
            renderArgs.setPoints(listSafelySubList);
            a(renderArgs);
        } while (i < size);
    }

    private static void a(PenRenderArgs renderArgs) {
        if (renderArgs.getContentRect() == null || renderArgs.getContentRect().isEmpty()) {
            Debug.e(NeoCharcoalPenWrapper.class, "empty view port", new Object[0]);
            return;
        }
        ArrayList arrayList = new ArrayList();
        NeoRenderPoint[] neoRenderPointArrComputeStrokeRenderPoints = computeStrokeRenderPoints(renderArgs, arrayList);
        if (neoRenderPointArrComputeStrokeRenderPoints == null) {
            return;
        }
        Paint paint = null;
        if (renderArgs.isErase()) {
            Paint paint2 = new Paint();
            paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            paint = paint2;
        }
        int size = c + CollectionUtils.getSize(renderArgs.getPoints());
        c = size;
        if (size > a) {
            System.gc();
            c = 0;
        }
        for (NeoRenderPoint neoRenderPoint : neoRenderPointArrComputeStrokeRenderPoints) {
            Matrix matrix = new Matrix();
            matrix.postTranslate(neoRenderPoint.x, neoRenderPoint.y);
            matrix.postConcat(renderArgs.getRenderMatrix());
            renderArgs.getCanvas().drawBitmap((Bitmap) arrayList.get(neoRenderPoint.bitmapIndex), matrix, paint);
        }
        BitmapUtils.recycle(arrayList);
        arrayList.clear();
    }

    private static ArrayList<TouchPoint> a(List<TouchPoint> points, Matrix matrix) {
        float[] fArr = new float[points.size() * 2];
        for (int i = 0; i < points.size(); i++) {
            int i2 = i;
            int i3 = i2 * 2;
            fArr[i3] = points.get(i2).x;
            fArr[i3 + 1] = points.get(i).y;
        }
        matrix.mapPoints(fArr);
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

    private static NeoRenderPoint[] a(NeoRenderPoint[] points, List<Bitmap> pixelBitmapPool, Matrix matrix) {
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
}
