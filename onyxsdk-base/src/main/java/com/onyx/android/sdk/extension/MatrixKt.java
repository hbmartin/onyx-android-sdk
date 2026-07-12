package com.onyx.android.sdk.extension;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.annotation.FloatRange;
import com.onyx.android.sdk.data.ReaderTextStyle;
import com.onyx.android.sdk.data.note.MatrixValues;
import com.onyx.android.sdk.utils.MathUtils;
import com.onyx.android.sdk.utils.MathUtilsKt;
import com.onyx.android.sdk.utils.RectUtils;
import com.onyx.android.sdk.utils.TTFFont;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.ranges.RangesKt;
import org.apache.commons.lang3.math.NumberUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/extension/MatrixKt.class */
@Metadata(mv = {1, 6, 0}, k = 2, xi = 48, d1 = {"\u0000R\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0014\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\u001a#\u0010\u0002\u001a\u00020\u00012\u0016\u0010\u0003\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u0004\"\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0005\u001a\u0006\u0010\u0006\u001a\u00020\u0007\u001a\f\u0010\b\u001a\u00020\u0001*\u0004\u0018\u00010\u0001\u001a\f\u0010\t\u001a\u00020\n*\u0004\u0018\u00010\u0001\u001a\u000e\u0010\u000b\u001a\u00020\n*\u0004\u0018\u00010\u0001H\u0007\u001a\f\u0010\u000b\u001a\u00020\n*\u00020\u0007H\u0007\u001a\f\u0010\f\u001a\u00020\u0001*\u0004\u0018\u00010\r\u001a\f\u0010\f\u001a\u00020\u0001*\u0004\u0018\u00010\u0007\u001a\u000e\u0010\u000e\u001a\u00020\n*\u0004\u0018\u00010\u0001H\u0007\u001a\f\u0010\u000e\u001a\u00020\n*\u00020\u0007H\u0007\u001a\f\u0010\u000f\u001a\u00020\n*\u0004\u0018\u00010\u0007\u001a\u0012\u0010\u0010\u001a\u00020\n*\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u0012\u001a\f\u0010\u0010\u001a\u00020\n*\u0004\u0018\u00010\u0001\u001a\n\u0010\u0010\u001a\u00020\n*\u00020\u0007\u001a\u0014\u0010\u0010\u001a\u00020\n*\u0004\u0018\u00010\u00072\u0006\u0010\u0011\u001a\u00020\u0012\u001a\n\u0010\u0013\u001a\u00020\n*\u00020\u0007\u001a\n\u0010\u0014\u001a\u00020\n*\u00020\u0007\u001a\n\u0010\u0015\u001a\u00020\n*\u00020\u0007\u001a\n\u0010\u0016\u001a\u00020\n*\u00020\u0007\u001a\f\u0010\u0017\u001a\u00020\u0007*\u0004\u0018\u00010\u0001\u001a\f\u0010\u0017\u001a\u00020\u0007*\u0004\u0018\u00010\r\u001a\f\u0010\u0018\u001a\u00020\n*\u0004\u0018\u00010\u0001\u001a\f\u0010\u0019\u001a\u00020\n*\u0004\u0018\u00010\u0001\u001a\f\u0010\u001a\u001a\u00020\n*\u0004\u0018\u00010\u0001\u001a\f\u0010\u001b\u001a\u00020\n*\u0004\u0018\u00010\u0001\u001a\f\u0010\u001c\u001a\u00020\n*\u0004\u0018\u00010\u0001\u001a\f\u0010\u001d\u001a\u00020\n*\u0004\u0018\u00010\u0001\u001a\f\u0010\u001e\u001a\u00020\u0001*\u0004\u0018\u00010\u0001\u001a\u001d\u0010\u001f\u001a\u00020 *\u0004\u0018\u00010\u0001\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\b\u0002\u0010\u0000\u001a\f\u0010!\u001a\u00020 *\u0004\u0018\u00010\u0007\u001a\f\u0010\"\u001a\u00020 *\u0004\u0018\u00010\u0001\u001a\u001d\u0010#\u001a\u00020 *\u0004\u0018\u00010\u0001\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\b\u0002\u0010\u0000\u001a\u0012\u0010$\u001a\u00020%*\u00020\u00012\u0006\u0010&\u001a\u00020%\u001a\u001a\u0010$\u001a\u00020%*\u00020\u00012\u0006\u0010'\u001a\u00020\n2\u0006\u0010(\u001a\u00020\n\u001a\u0018\u0010)\u001a\u0004\u0018\u00010**\u0004\u0018\u00010\u00012\b\u0010+\u001a\u0004\u0018\u00010*\u001a\u0012\u0010,\u001a\u00020-*\u00020\u00012\u0006\u0010&\u001a\u00020%\u001a\u0016\u0010.\u001a\u00020 *\u0004\u0018\u00010\u00072\b\u0010/\u001a\u0004\u0018\u00010\u0007\u001a\f\u00100\u001a\u00020\r*\u0004\u0018\u00010\u0001\u001a\n\u00101\u001a\u00020\u0007*\u00020\u0001\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00062"}, d2 = {"EMPTY", "Landroid/graphics/Matrix;", "concat", "matrices", TTFFont.UNKNOWN_FONT_NAME, "([Landroid/graphics/Matrix;)Landroid/graphics/Matrix;", "getDefaultMatrixValue", TTFFont.UNKNOWN_FONT_NAME, "copy", "getAverageScale", TTFFont.UNKNOWN_FONT_NAME, "getHorizontalAngle", "getMatrixByValues", "Lcom/onyx/android/sdk/data/note/MatrixValues;", "getMatrixFloatRotate", "getMatrixMinScale", "getMatrixRotateAngel", "rectF", "Landroid/graphics/RectF;", "getMatrixScaleX", "getMatrixScaleY", "getMatrixTransX", "getMatrixTransY", "getMatrixValue", "getMaxScale", "getMinScale", "getScaleX", "getScaleY", "getTranslateX", "getTranslateY", "invertMatrix", "isEmpty", TTFFont.UNKNOWN_FONT_NAME, "isEmptyMatrix", "isNotBlank", "isNullOrEmpty", "mapPoint", "Landroid/graphics/PointF;", "pointF", "x", "y", "mapRect", "Landroid/graphics/Rect;", "rect", "mapSelfPoint", TTFFont.UNKNOWN_FONT_NAME, "safelyEquals", "m2", "toMatrixValues", "values", "onyxsdk-base_release"})
public final class MatrixKt {
    private MatrixKt() {
    }


    @NotNull
    private static final Matrix a = new Matrix();

    @NotNull
    public static final Matrix copy(@Nullable Matrix $this$copy) {
        return $this$copy == null ? new Matrix() : new Matrix($this$copy);
    }

    public static final boolean isEmpty(@Nullable Matrix $this$isEmpty) {
        return isNullOrEmpty($this$isEmpty);
    }

    public static final boolean isNullOrEmpty(@Nullable Matrix $this$isNullOrEmpty) {
        return $this$isNullOrEmpty == null || Intrinsics.areEqual($this$isNullOrEmpty, a);
    }

    @NotNull
    public static final Matrix concat(@NotNull Matrix... matrices) {
        Intrinsics.checkNotNullParameter(matrices, "matrices");
        Matrix matrix = new Matrix();
        for (Matrix matrix2 : matrices) {
            matrix.postConcat(matrix2);
        }
        return matrix;
    }

    public static final boolean isNotBlank(@Nullable Matrix $this$isNotBlank) {
        return !isNullOrEmpty($this$isNotBlank);
    }

    public static final float getMaxScale(@Nullable Matrix $this$getMaxScale) {
        if ($this$getMaxScale != null) {
            float[] matrixValue = getMatrixValue($this$getMaxScale);
            return RangesKt.coerceAtLeast(getMatrixScaleX(matrixValue), getMatrixScaleY(matrixValue));
        }
        Float f = NumberUtils.FLOAT_ONE;
        Intrinsics.checkNotNullExpressionValue(f, "FLOAT_ONE");
        return f.floatValue();
    }

    public static final float getAverageScale(@Nullable Matrix $this$getAverageScale) {
        return (getMinScale($this$getAverageScale) + getMaxScale($this$getAverageScale)) / 2.0f;
    }

    public static final float getTranslateX(@Nullable Matrix $this$getTranslateX) {
        return getMatrixValue($this$getTranslateX)[2];
    }

    public static final float getTranslateY(@Nullable Matrix $this$getTranslateY) {
        return getMatrixValue($this$getTranslateY)[5];
    }

    public static final float getMinScale(@Nullable Matrix $this$getMinScale) {
        if ($this$getMinScale != null) {
            return getMatrixMinScale(getMatrixValue($this$getMinScale));
        }
        Float f = NumberUtils.FLOAT_ONE;
        Intrinsics.checkNotNullExpressionValue(f, "FLOAT_ONE");
        return f.floatValue();
    }

    @NotNull
    public static final float[] getMatrixValue(@Nullable Matrix $this$getMatrixValue) {
        float[] fArr = new float[9];
        if ($this$getMatrixValue != null) {
            $this$getMatrixValue.getValues(fArr);
        }
        return fArr;
    }

    public static final float getMatrixTransX(@NotNull float[] $this$getMatrixTransX) {
        Intrinsics.checkNotNullParameter($this$getMatrixTransX, "<this>");
        return $this$getMatrixTransX[2];
    }

    public static final float getMatrixTransY(@NotNull float[] $this$getMatrixTransY) {
        Intrinsics.checkNotNullParameter($this$getMatrixTransY, "<this>");
        return $this$getMatrixTransY[5];
    }

    @NotNull
    public static final MatrixValues toMatrixValues(@Nullable Matrix $this$toMatrixValues) {
        return new MatrixValues(getMatrixValue($this$toMatrixValues));
    }

    @NotNull
    public static final Matrix invertMatrix(@Nullable Matrix $this$invertMatrix) {
        Matrix matrix = new Matrix();
        if ($this$invertMatrix == null) {
            return matrix;
        }
        $this$invertMatrix.invert(matrix);
        return matrix;
    }

    public static final float getScaleX(@Nullable Matrix $this$getScaleX) {
        if ($this$getScaleX == null) {
            return 1.0f;
        }
        return getMatrixScaleX(getMatrixValue($this$getScaleX));
    }

    public static final float getScaleY(@Nullable Matrix $this$getScaleY) {
        if ($this$getScaleY == null) {
            return 1.0f;
        }
        return getMatrixScaleY(getMatrixValue($this$getScaleY));
    }

    @NotNull
    public static final float[] getDefaultMatrixValue() {
        return getMatrixValue(a);
    }

    public static final float getMatrixRotateAngel(@Nullable Matrix $this$getMatrixRotateAngel) {
        return getMatrixRotateAngel(getMatrixValue($this$getMatrixRotateAngel));
    }

    @FloatRange(from = -180.0d, to = 180.0d)
    public static final float getMatrixFloatRotate(@Nullable Matrix $this$getMatrixFloatRotate) {
        return getMatrixFloatRotate(getMatrixValue($this$getMatrixFloatRotate));
    }

    @FloatRange(from = -90.0d, to = 90.0d)
    public static final float getHorizontalAngle(@Nullable Matrix $this$getHorizontalAngle) {
        return getHorizontalAngle(getMatrixValue($this$getHorizontalAngle));
    }

    @NotNull
    public static final float[] values(@NotNull Matrix $this$values) {
        Intrinsics.checkNotNullParameter($this$values, "<this>");
        float[] fArr = new float[9];
        $this$values.getValues(fArr);
        return fArr;
    }

    @Nullable
    public static final Rect mapRect(@Nullable Matrix $this$mapRect, @Nullable Rect rect) {
        if ($this$mapRect == null || rect == null) {
            return rect;
        }
        RectF rectF = RectUtils.toRectF(rect);
        $this$mapRect.mapRect(rectF);
        return RectUtils.toRect(rectF);
    }

    @NotNull
    public static final Matrix getMatrixByValues(@Nullable MatrixValues $this$getMatrixByValues) {
        float[] fArr;
        Matrix matrix = new Matrix();
        if ($this$getMatrixByValues != null && (fArr = $this$getMatrixByValues.values) != null) {
            matrix.setValues(fArr);
        }
        return matrix;
    }

    public static final float getMatrixScaleX(@NotNull float[] $this$getMatrixScaleX) {
        Intrinsics.checkNotNullParameter($this$getMatrixScaleX, "<this>");
        float f = $this$getMatrixScaleX[0];
        float f2 = $this$getMatrixScaleX[3];
        return (float) Math.sqrt((f * f) + (f2 * f2));
    }

    public static final float getMatrixScaleY(@NotNull float[] $this$getMatrixScaleY) {
        Intrinsics.checkNotNullParameter($this$getMatrixScaleY, "<this>");
        float f = $this$getMatrixScaleY[4];
        float f2 = $this$getMatrixScaleY[1];
        return (float) Math.sqrt((f * f) + (f2 * f2));
    }

    public static final float getMatrixMinScale(@Nullable float[] $this$getMatrixMinScale) {
        if ($this$getMatrixMinScale != null) {
            if (!($this$getMatrixMinScale.length == 0)) {
                return RangesKt.coerceAtMost(getMatrixScaleX($this$getMatrixMinScale), getMatrixScaleY($this$getMatrixMinScale));
            }
        }
        Float f = NumberUtils.FLOAT_ONE;
        Intrinsics.checkNotNullExpressionValue(f, "FLOAT_ONE");
        return f.floatValue();
    }

    public static final boolean isEmptyMatrix(@Nullable float[] $this$isEmptyMatrix) {
        return Intrinsics.areEqual(getMatrixByValues($this$isEmptyMatrix), a);
    }

    public static final boolean safelyEquals(@Nullable float[] $this$safelyEquals, @Nullable float[] m2) {
        if ($this$safelyEquals == null && m2 == null) {
            return true;
        }
        if ($this$safelyEquals == null || m2 == null || $this$safelyEquals.length != m2.length) {
            return false;
        }
        int i = 0;
        int length = $this$safelyEquals.length;
        while (i < length) {
            int i2 = i;
            int i3 = i2 + 1;
            if (Float.compare($this$safelyEquals[i2], m2[i]) != 0) {
                return false;
            }
            i = i3;
        }
        return true;
    }

    @NotNull
    public static final PointF mapPoint(@NotNull Matrix $this$mapPoint, @NotNull PointF pointF) {
        Intrinsics.checkNotNullParameter($this$mapPoint, "<this>");
        Intrinsics.checkNotNullParameter(pointF, "pointF");
        float[] fArr = {pointF.x, pointF.y};
        $this$mapPoint.mapPoints(fArr);
        return new PointF(fArr[0], fArr[1]);
    }

    public static final void mapSelfPoint(@NotNull Matrix $this$mapSelfPoint, @NotNull PointF pointF) {
        Intrinsics.checkNotNullParameter($this$mapSelfPoint, "<this>");
        Intrinsics.checkNotNullParameter(pointF, "pointF");
        float[] fArr = {pointF.x, pointF.y};
        $this$mapSelfPoint.mapPoints(fArr);
        pointF.set(fArr[0], fArr[1]);
    }

    public static final float getMatrixRotateAngel(@NotNull Matrix $this$getMatrixRotateAngel, @NotNull RectF rectF) {
        Intrinsics.checkNotNullParameter($this$getMatrixRotateAngel, "<this>");
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        float f = rectF.top;
        float[] fArr = {rectF.left, f};
        float[] fArr2 = {rectF.right, f};
        $this$getMatrixRotateAngel.mapPoints(fArr);
        $this$getMatrixRotateAngel.mapPoints(fArr2);
        return -MathUtils.calculateAngle(fArr[0], fArr[1], fArr2[0], fArr2[1]);
    }

    @FloatRange(from = -180.0d, to = 180.0d)
    public static final float getMatrixFloatRotate(@NotNull float[] $this$getMatrixFloatRotate) {
        Intrinsics.checkNotNullParameter($this$getMatrixFloatRotate, "<this>");
        return (float) Math.toDegrees(Math.atan2($this$getMatrixFloatRotate[1], $this$getMatrixFloatRotate[0]));
    }

    @FloatRange(from = -90.0d, to = 90.0d)
    public static final float getHorizontalAngle(@NotNull float[] $this$getHorizontalAngle) {
        Intrinsics.checkNotNullParameter($this$getHorizontalAngle, "<this>");
        float f = -getMatrixFloatRotate($this$getHorizontalAngle);
        if (Float.compare(f, ReaderTextStyle.FONT_EMBOLDEN_NORMAL) == 0) {
            return ReaderTextStyle.FONT_EMBOLDEN_NORMAL;
        }
        if (90.0f >= f || f > 180.0f) {
            return (-180.0f > f || f >= -90.0f) ? f : MathUtilsKt.DEGREE_180 + f;
        }
        return f - MathUtilsKt.DEGREE_180;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0015  */
    @NotNull
    public static final float[] getMatrixValue(@Nullable MatrixValues $this$getMatrixValue) {
        float[] fArr;
        float[] fArr2 = new float[9];
        if ($this$getMatrixValue == null) {
            fArr = fArr2;
        } else {
            float[] fArr3 = $this$getMatrixValue.values;
            fArr = fArr3;
            if (fArr3 == null) {
                fArr = fArr2;
            }
        }
        return fArr;
    }

    @NotNull
    public static final Matrix getMatrixByValues(@Nullable float[] $this$getMatrixByValues) {
        Matrix matrix = new Matrix();
        if ($this$getMatrixByValues != null) {
            matrix.setValues($this$getMatrixByValues);
        }
        return matrix;
    }

    @NotNull
    public static final PointF mapPoint(@NotNull Matrix $this$mapPoint, float x, float y) {
        Intrinsics.checkNotNullParameter($this$mapPoint, "<this>");
        PointF pointF = new PointF(x, y);
        mapSelfPoint($this$mapPoint, pointF);
        return pointF;
    }

    public static final float getMatrixRotateAngel(@NotNull float[] $this$getMatrixRotateAngel) {
        Intrinsics.checkNotNullParameter($this$getMatrixRotateAngel, "<this>");
        return MathKt.roundToInt(Math.toDegrees(Math.atan2($this$getMatrixRotateAngel[1], $this$getMatrixRotateAngel[0])));
    }

    public static final float getMatrixRotateAngel(@Nullable float[] $this$getMatrixRotateAngel, @NotNull RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "rectF");
        return getMatrixRotateAngel(getMatrixByValues($this$getMatrixRotateAngel), rectF);
    }
}
