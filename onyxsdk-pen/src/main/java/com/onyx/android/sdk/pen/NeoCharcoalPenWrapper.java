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

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/NeoCharcoalPenWrapper.class */
public class NeoCharcoalPenWrapper {
    private static final int a = 5000;
    private static final int b = 1000;
    private static int c;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.onyx.android.sdk.pen.NeoCharcoalPen, com.onyx.android.sdk.pen.NeoPen, java.lang.Throwable] */
    public static NeoRenderPoint[] computeStrokeRenderPoints(PenRenderArgs renderArgs, List<Bitmap> indexedPixelBitmapsResult) {
        Matrix screenMatrix = renderArgs.getScreenMatrix();
        List<TouchPoint> points = renderArgs.getPoints();
        int matrixRotateAngel = (int) MatrixUtils.getMatrixRotateAngel(screenMatrix);
        NeoPenConfig neoPenConfig = new NeoPenConfig();
        neoPenConfig.setColor(renderArgs.getColor());
        neoPenConfig.setWidth(renderArgs.getStrokeWidth());
        neoPenConfig.setTiltEnabled(renderArgs.isTiltEnabled());
        neoPenConfig.setRotateAngle(matrixRotateAngel);
        neoPenConfig.setMaxTouchPressure(renderArgs.getCreateArgs().getMaxPressure());
        NeoPen pen = NeoCharcoalPen.Companion.create(neoPenConfig);
        if (pen == null) {
            return new NeoRenderPoint[0];
        }
        try {
            ArrayList<TouchPoint> arrayListMapToPenCanvas = NeoPenUtils.mapToPenCanvas(points, screenMatrix);
            ArrayList arrayList = new ArrayList();
            if (arrayListMapToPenCanvas.size() < 2) {
                NeoRenderPoint[] neoRenderPointArr = new NeoRenderPoint[0];
                pen.destroy();
                return neoRenderPointArr;
            }
            for (int i = 0; i < arrayListMapToPenCanvas.size(); i++) {
                arrayListMapToPenCanvas.get(i).pressure /= renderArgs.getCreateArgs().getMaxPressure();
            }
            NeoPenUtils.readTextureResult(pen.onPenDown(arrayListMapToPenCanvas.get(0), true), indexedPixelBitmapsResult, arrayList);
            if (arrayListMapToPenCanvas.size() > 2) {
                NeoPenUtils.readTextureResult(pen.onPenMove(arrayListMapToPenCanvas.subList(1, arrayListMapToPenCanvas.size() - 1), null, true), indexedPixelBitmapsResult, arrayList);
            }
            NeoPenUtils.readTextureResult(pen.onPenUp(arrayListMapToPenCanvas.get(arrayListMapToPenCanvas.size() - 1), true), indexedPixelBitmapsResult, arrayList);
            NeoRenderPoint[] neoRenderPointArr2 = (NeoRenderPoint[]) arrayList.toArray(new NeoRenderPoint[0]);
            Matrix matrix = new Matrix();
            if (screenMatrix.invert(matrix)) {
                NeoRenderPoint[] neoRenderPointArrMapFromPenCanvas = NeoPenUtils.mapFromPenCanvas(neoRenderPointArr2, indexedPixelBitmapsResult, matrix);
                pen.destroy();
                return neoRenderPointArrMapFromPenCanvas;
            }
            NeoRenderPoint[] neoRenderPointArr3 = new NeoRenderPoint[0];
            pen.destroy();
            return neoRenderPointArr3;
        } catch (Throwable th) {
            pen.destroy();
            throw th;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static float[] computeStrokePoints(PenRenderArgs renderArgs, List<Bitmap> indexedPixelBitmapsResult) {
        NeoRenderPoint[] neoRenderPointArrComputeStrokeRenderPoints = computeStrokeRenderPoints(renderArgs, indexedPixelBitmapsResult);
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

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void drawNormalStroke(PenRenderArgs renderArgs) {
        List<TouchPoint> points = renderArgs.getPoints();
        int size = CollectionUtils.getSize(points);
        int i = 0;
        do {
            int i2 = i;
            int i3 = i2 + 1000;
            i = i3;
            List<TouchPoint> listSafelySubList = CollectionUtils.safelySubList(points, i2, i3);
            if (CollectionUtils.isNullOrEmpty(listSafelySubList)) {
                return;
            }
            renderArgs.setPoints(listSafelySubList);
            drawNormalStrokeImpl(renderArgs);
        } while (i < size);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void drawNormalStrokeImpl(PenRenderArgs renderArgs) {
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
        for (NeoRenderPoint neoRenderPoint : neoRenderPointArrComputeStrokeRenderPoints) {
            renderArgs.getCanvas().drawBitmap((Bitmap) arrayList.get(neoRenderPoint.bitmapIndex), neoRenderPoint.x, neoRenderPoint.y, paint);
        }
        BitmapUtils.recycle(arrayList);
        arrayList.clear();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void drawBigStroke(PenRenderArgs renderArgs) {
        List<TouchPoint> points = renderArgs.getPoints();
        int size = CollectionUtils.getSize(points);
        int i = 0;
        do {
            int i2 = i;
            int i3 = i2 + 1000;
            i = i3;
            List<TouchPoint> listSafelySubList = CollectionUtils.safelySubList(points, i2, i3);
            if (CollectionUtils.isNullOrEmpty(listSafelySubList)) {
                return;
            }
            renderArgs.setPoints(listSafelySubList);
            drawBigStrokeImpl(renderArgs);
        } while (i < size);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void drawBigStrokeImpl(PenRenderArgs renderArgs) {
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
    }
}
