package com.onyx.android.sdk.pen.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import com.onyx.android.sdk.base.data.TouchPoint;
import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/utils/PenUtils.class */
@Metadata(d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0014\n\u0002\b\u0002\n\u0002\u0010\u0013\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J,\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f2\u0006\u0010!\u001a\u00020\"J\u000e\u0010#\u001a\u00020\u00172\u0006\u0010$\u001a\u00020%J\u001c\u0010&\u001a\u00020'2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f2\u0006\u0010(\u001a\u00020\u0004J\u0018\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020 2\b\b\u0002\u0010(\u001a\u00020\u0004J\u001e\u0010)\u001a\u00020*2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020 0\u001f2\b\b\u0002\u0010(\u001a\u00020\u0004J\u001c\u0010,\u001a\u0004\u0018\u00010*2\b\u0010+\u001a\u0004\u0018\u00010 2\b\b\u0002\u0010(\u001a\u00020\u0004J\u000e\u0010-\u001a\u00020\u00042\u0006\u0010.\u001a\u00020\u0004J\u000e\u0010/\u001a\u00020\u00042\u0006\u00100\u001a\u00020\u0004J\n\u00101\u001a\u00020\u0004*\u00020'J\n\u00102\u001a\u00020\u0004*\u00020'J\f\u00103\u001a\u00020'*\u0004\u0018\u000104J\u001e\u00105\u001a\u0004\u0018\u000104*\u0004\u0018\u0001042\u0006\u00106\u001a\u00020\u00042\u0006\u00107\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0010\u0010\u0016\u001a\u0004\u0018\u00010\u0017X\u0082\u000e¢\u0006\u0002\n\u0000¨\u00068"}, d2 = {"Lcom/onyx/android/sdk/pen/utils/PenUtils;", "", "()V", "DEFAULT_ALPHA_FACTOR", "", "DEFAULT_BRUSH_SPACING", "DEFAULT_DPI", "DEFAULT_PRESSURE_SENSITIVITY", "DEFAULT_SMOOTH_LEVEL", "DEFAULT_VELOCITY_SENSITIVITY", "ERASE_EXTRA_STROKE_WIDTH", "FLOAT_ONE", "KEPLER_DEFAULT_PRESSURE_SENSITIVITY", "KEPLER_MAX_PRESSURE_SENSITIVITY", "KEPLER_MIN_PRESSURE_SENSITIVITY", "MAX_PRECISION", "MAX_PRESSURE_SENSITIVITY", "MIDDLE_PRECISION", "MIN_PRECISION", "MIN_PRESSURE", "MIN_PRESSURE_SENSITIVITY", "RANGE_PRESSURE_SENSITIVITY", "penBitmap", "Landroid/graphics/Bitmap;", "drawStrokeByPointSize", "", "canvas", "Landroid/graphics/Canvas;", "paint", "Landroid/graphics/Paint;", "points", "", "Lcom/onyx/android/sdk/base/data/TouchPoint;", "erase", "", "ensurePenBitmapCreated", "drawRect", "Landroid/graphics/Rect;", "getPointArray", "", "maxTouchPressure", "getPointDoubleArray", "", "point", "getPointDoubleArrayNullable", "toNormalizedPressureSensitivity", "percentValue", "toPercentPressureSensitivity", "normalizedValue", "getMatrixTransX", "getMatrixTransY", "getMatrixValue", "Landroid/graphics/Matrix;", "scaleTranslate", "scaleX", "scaleY", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class PenUtils {
    public static final float DEFAULT_ALPHA_FACTOR = 1.0f;
    public static final float DEFAULT_BRUSH_SPACING = 0.25f;
    public static final float DEFAULT_DPI = 320.0f;
    public static final float DEFAULT_PRESSURE_SENSITIVITY = 0.375f;
    public static final float DEFAULT_SMOOTH_LEVEL = 0.2f;
    public static final float DEFAULT_VELOCITY_SENSITIVITY = 0.5f;
    public static final float FLOAT_ONE = 1.0f;
    public static final float KEPLER_DEFAULT_PRESSURE_SENSITIVITY = 0.3f;
    public static final float KEPLER_MAX_PRESSURE_SENSITIVITY = 1.0f;
    public static final float KEPLER_MIN_PRESSURE_SENSITIVITY = 0.0f;
    public static final float MAX_PRECISION = 8.0f;
    public static final float MIDDLE_PRECISION = 4.0f;
    public static final float MIN_PRECISION = 1.0f;
    public static final float MIN_PRESSURE = 0.001f;

    @Nullable
    private static Bitmap b;
    private static final float c = 0.15f;
    private static final float d = 0.6f;
    private static final float e = 0.45000002f;

    @NotNull
    public static final PenUtils INSTANCE = new PenUtils();
    private static final float a = 1.0f;

    private PenUtils() {
    }

    public static /* synthetic */ double[] getPointDoubleArray$default(PenUtils penUtils, TouchPoint touchPoint, float f, int i, Object obj) {
        if ((i & 2) != 0) {
            f = 1.0f;
        }
        return penUtils.getPointDoubleArray(touchPoint, f);
    }

    public static /* synthetic */ double[] getPointDoubleArray$default(PenUtils penUtils, List list, float f, int i, Object obj) {
        if ((i & 2) != 0) {
            f = 1.0f;
        }
        return penUtils.getPointDoubleArray((List<? extends TouchPoint>) list, f);
    }

    public static /* synthetic */ double[] getPointDoubleArrayNullable$default(PenUtils penUtils, TouchPoint touchPoint, float f, int i, Object obj) {
        if ((i & 2) != 0) {
            f = 1.0f;
        }
        return penUtils.getPointDoubleArrayNullable(touchPoint, f);
    }

    /* JADX DEBUG: Move duplicate insns, count: 1 to block B:3:0x0040 */
    public final void drawStrokeByPointSize(@NotNull Canvas canvas, @NotNull Paint paint, @NotNull List<? extends TouchPoint> points, boolean erase) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(paint, "paint");
        Intrinsics.checkNotNullParameter(points, "points");
        Paint paint2 = new Paint(paint);
        paint2.setStyle(Paint.Style.FILL_AND_STROKE);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        paint2.setStrokeJoin(Paint.Join.ROUND);
        paint2.setAntiAlias(true);
        int size = points.size();
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 >= size - 1) {
                return;
            }
            int i3 = i2 + 1;
            float f = points.get(i3).size;
            float f2 = f;
            if (erase) {
                f2 = f + a;
            }
            paint2.setStrokeWidth(f2);
            canvas.drawLine(points.get(i2).x, points.get(i2).y, points.get(i3).x, points.get(i3).y, paint2);
            i = i3;
        }
    }

    /* JADX WARN: Code duplicated, block: B:8:0x0030  */
    @NotNull
    public final Bitmap ensurePenBitmapCreated(@NotNull Rect drawRect) {
        Intrinsics.checkNotNullParameter(drawRect, "drawRect");
        Bitmap bitmap = b;
        if (bitmap != null) {
            Intrinsics.checkNotNull(bitmap);
            if (bitmap.getWidth() == drawRect.width()) {
                Bitmap bitmap2 = b;
                Intrinsics.checkNotNull(bitmap2);
                if (bitmap2.getHeight() != drawRect.height()) {
                    b = Bitmap.createBitmap(drawRect.width(), drawRect.height(), Bitmap.Config.ARGB_8888);
                }
            } else {
                b = Bitmap.createBitmap(drawRect.width(), drawRect.height(), Bitmap.Config.ARGB_8888);
            }
        } else {
            b = Bitmap.createBitmap(drawRect.width(), drawRect.height(), Bitmap.Config.ARGB_8888);
        }
        Bitmap bitmap3 = b;
        if (bitmap3 != null) {
            bitmap3.eraseColor(0);
        }
        Bitmap bitmap4 = b;
        Objects.requireNonNull(bitmap4, "null cannot be cast to non-null type android.graphics.Bitmap");
        return bitmap4;
    }

    public final float getMatrixTransX(@NotNull float[] fArr) {
        Intrinsics.checkNotNullParameter(fArr, "<this>");
        return fArr[2];
    }

    public final float getMatrixTransY(@NotNull float[] fArr) {
        Intrinsics.checkNotNullParameter(fArr, "<this>");
        return fArr[5];
    }

    @NotNull
    public final float[] getMatrixValue(@Nullable Matrix matrix) {
        float[] fArr = new float[9];
        if (matrix != null) {
            matrix.getValues(fArr);
        }
        return fArr;
    }

    @NotNull
    public final float[] getPointArray(@NotNull List<? extends TouchPoint> points, float maxTouchPressure) {
        Intrinsics.checkNotNullParameter(points, "points");
        float[] fArr = new float[points.size() * 5];
        int size = points.size();
        for (int i = 0; i < size; i++) {
            int i2 = i * 5;
            fArr[i2] = points.get(i).x;
            fArr[i2 + 1] = points.get(i).y;
            fArr[i2 + 2] = points.get(i).pressure / maxTouchPressure;
            fArr[i2 + 3] = points.get(i).size;
            fArr[i2 + 4] = points.get(i).timestamp;
        }
        return fArr;
    }

    @NotNull
    public final double[] getPointDoubleArray(@NotNull TouchPoint point, float maxTouchPressure) {
        Intrinsics.checkNotNullParameter(point, "point");
        return new double[]{point.x, point.y, point.pressure / maxTouchPressure, point.size, point.tiltX, point.tiltY, point.timestamp};
    }

    @NotNull
    public final double[] getPointDoubleArray(@NotNull List<? extends TouchPoint> points, float maxTouchPressure) {
        Intrinsics.checkNotNullParameter(points, "points");
        double[] dArr = new double[points.size() * 7];
        int size = points.size();
        for (int i = 0; i < size; i++) {
            int i2 = i * 7;
            dArr[i2] = points.get(i).x;
            dArr[i2 + 1] = points.get(i).y;
            dArr[i2 + 2] = Math.min(1.0f, points.get(i).pressure / maxTouchPressure);
            dArr[i2 + 3] = points.get(i).size;
            dArr[i2 + 4] = points.get(i).tiltX;
            dArr[i2 + 5] = points.get(i).tiltY;
            dArr[i2 + 6] = points.get(i).timestamp;
        }
        return dArr;
    }

    @Nullable
    public final double[] getPointDoubleArrayNullable(@Nullable TouchPoint point, float maxTouchPressure) {
        if (point == null) {
            return null;
        }
        return getPointDoubleArray(point, maxTouchPressure);
    }

    @Nullable
    public final Matrix scaleTranslate(@Nullable Matrix matrix, float f, float f2) {
        if (matrix == null) {
            return null;
        }
        float[] matrixValue = getMatrixValue(matrix);
        float matrixTransX = getMatrixTransX(matrixValue);
        float matrixTransY = getMatrixTransY(matrixValue);
        matrix.postTranslate(-matrixTransX, -matrixTransY);
        matrix.postTranslate(matrixTransX * f, matrixTransY * f2);
        return matrix;
    }

    public final float toNormalizedPressureSensitivity(float percentValue) {
        return Math.min(d, (percentValue * e) + c);
    }

    public final float toPercentPressureSensitivity(float normalizedValue) {
        return Math.max(normalizedValue - c, KEPLER_MIN_PRESSURE_SENSITIVITY) / e;
    }
}

