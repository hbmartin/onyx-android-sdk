package com.onyx.android.sdk.pen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import com.onyx.android.sdk.pen.utils.ColorUtils;
import com.onyx.android.sdk.pen.NeoPencilPen.Companion.BitmapHolder;
import com.onyx.android.sdk.pen.NeoPencilPen.Companion.BrushMaskGenerator;
import com.onyx.android.sdk.pen.NeoPencilPen.Companion.MaskKey;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0006\u0018\u0000 42\u00020\u0001:\u000245B#\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0014\u0010!\u001a\u0004\u0018\u00010\u00012\b\u0010\"\u001a\u0004\u0018\u00010\u0001H\u0016J\u0018\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020(H\u0016J(\u0010)\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020+2\u0006\u0010-\u001a\u00020\u0004H\u0002J\u0013\u0010.\u001a\u00020\u000e2\b\u0010/\u001a\u0004\u0018\u000100H\u0096\u0002J\u0010\u00101\u001a\u00020\u000e2\u0006\u0010-\u001a\u00020\u0004H\u0002J\u0010\u00102\u001a\u00020$2\u0006\u0010%\u001a\u00020&H\u0002J\b\u00103\u001a\u00020$H\u0002R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0019\u001a\u00020\u001aX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u000e\u0010\u001f\u001a\u00020 X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00066"}, d2 = {"Lcom/onyx/android/sdk/pen/PenBrushResult;", "Lcom/onyx/android/sdk/pen/PenResult;", "brushPoints", "", "Lcom/onyx/android/sdk/pen/PenBrushInk;", "maskGenerator", "Lcom/onyx/android/sdk/pen/NeoPencilPen$Companion$BrushMaskGenerator;", "rect", "Landroid/graphics/RectF;", "(Ljava/util/List;Lcom/onyx/android/sdk/pen/NeoPencilPen$Companion$BrushMaskGenerator;Landroid/graphics/RectF;)V", "canvasBoundingRectF", "invertMatrix", "Landroid/graphics/Matrix;", "isEnabledClipRect", "", "()Z", "setEnabledClipRect", "(Z)V", "matrix", "getMatrix", "()Landroid/graphics/Matrix;", "setMatrix", "(Landroid/graphics/Matrix;)V", "pointBitmapRenderMatrix", "pointScaleRenderMatrix", "pointSizeScale", "", "getPointSizeScale", "()F", "setPointSizeScale", "(F)V", "tempMaskKey", "Lcom/onyx/android/sdk/pen/NeoPencilPen$Companion$MaskKey;", "append", "add", "draw", "", "canvas", "Landroid/graphics/Canvas;", "paint", "Landroid/graphics/Paint;", "drawMask", "shapeColor", "", "shapeAlpha", "point", "equals", "other", "", "isEnabledDrawMask", "normalizeCanvasBoundingRectF", "updateMatrixWithPointSizeScale", "Companion", "PaintHolder", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class PenBrushResult extends PenResult {
    public static final float POINT_SIZE_FACTOR = 255.0f;
    private static final int l = 255;
    private static final int m = 4;
    private static final int n = 2;

    @NotNull
    private final List<PenBrushInk> b;

    @NotNull
    private final BrushMaskGenerator c;

    @NotNull
    private Matrix d;
    private float e;
    private boolean f;

    @NotNull
    private final RectF g;

    @NotNull
    private final Matrix h;

    @NotNull
    private final Matrix i;

    @NotNull
    private final Matrix j;

    @NotNull
    private final MaskKey k;

    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private static final Map<Integer, a> o = new LinkedHashMap();

    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\f\u0010\f\u001a\u00020\u0004*\u0004\u0018\u00010\rR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0086T¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lcom/onyx/android/sdk/pen/PenBrushResult$Companion;", "", "()V", "MAX_ALPHA_INT", "", "MIN_ALPHA_INT", "MIN_POINT_SIZE", "POINT_SIZE_FACTOR", "", "bitmapPaintMap", "", "Lcom/onyx/android/sdk/pen/PenBrushResult$PaintHolder;", "getPointsCount", "Lcom/onyx/android/sdk/pen/PenBrushResult;", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final int getPointsCount(@Nullable PenBrushResult penBrushResult) {
            List list;
            int size = 0;
            if (penBrushResult != null && (list = penBrushResult.b) != null) {
                size = list.size();
            }
            return size;
        }
    }

    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0017\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\u0016\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u0010"}, d2 = {"Lcom/onyx/android/sdk/pen/PenBrushResult$PaintHolder;", "", "shapeColor", "", "alpha", "(II)V", "paint", "Landroid/graphics/Paint;", "getPaint", "()Landroid/graphics/Paint;", "getShapeColor", "()I", "setShapeColor", "(I)V", "updatePaintColor", "", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    static final class a {
        private int a;

        @NotNull
        private final Paint b;

        public a(int i, int i2) {
            this.a = i;
            Paint paint = new Paint();
            paint.setAntiAlias(false);
            paint.setDither(false);
            paint.setFilterBitmap(false);
            paint.setColor(ColorUtils.INSTANCE.replaceAlphaWithBitwise(b(), i2));
            this.b = paint;
        }

        public /* synthetic */ a(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this((i3 & 1) != 0 ? -16777216 : i, i2);
        }

        @NotNull
        public final Paint a() {
            return this.b;
        }

        public final int b() {
            return this.a;
        }

        public final void c(int i) {
            this.a = i;
        }

        public final void d(int i, int i2) {
            this.a = i;
            this.b.setColor(ColorUtils.INSTANCE.replaceAlphaWithBitwise(i, i2));
        }
    }

    public PenBrushResult(@NotNull List<PenBrushInk> list, @NotNull BrushMaskGenerator brushMaskGenerator, @NotNull RectF rectF) {
        super(rectF);
        Intrinsics.checkNotNullParameter(list, "brushPoints");
        Intrinsics.checkNotNullParameter(brushMaskGenerator, "maskGenerator");
        Intrinsics.checkNotNullParameter(rectF, "rect");
        this.b = list;
        this.c = brushMaskGenerator;
        this.d = new Matrix();
        this.e = 1.0f;
        this.g = new RectF();
        this.h = new Matrix();
        this.i = new Matrix();
        this.j = new Matrix();
        this.k = new MaskKey(2, 0);
    }

    private final void a(Canvas canvas, final int i, int i2, PenBrushInk penBrushInk) {
        this.k.setSize(Math.max(2, (int) (((PenBrushInkAccess.size(penBrushInk) & l) / 255.0f) * this.c.getConfig().width * this.e)));
        this.k.setAngle(PenBrushInkAccess.angle(penBrushInk) & l);
        BitmapHolder maskBitmap = this.c.getMaskBitmap(this.k);
        Bitmap bitmap = maskBitmap.getBitmap();
        float x = penBrushInk.getX();
        float f = this.e;
        float halfWidth = maskBitmap.getHalfWidth();
        float y = penBrushInk.getY();
        float f2 = this.e;
        float halfWidth2 = maskBitmap.getHalfWidth();
        final int iCoerceIn = RangesKt.coerceIn((int) (((PenBrushInkAccess.alpha(penBrushInk) & l) / (float) l) * i2 * this.c.getConfig().getAlphaFactor()), 4, l);
        a aVarComputeIfAbsent = o.computeIfAbsent(Integer.valueOf(iCoerceIn), ignored -> PenBrushResult.b(i, iCoerceIn, ignored));
        Intrinsics.checkNotNullExpressionValue(aVarComputeIfAbsent, "bitmapPaintMap.computeIf…peColor, pointAlphaInt) }");
        a aVar = aVarComputeIfAbsent;
        if (i != aVar.b()) {
            aVar.d(i, iCoerceIn);
        }
        this.j.setTranslate((x * f) - halfWidth, (y * f2) - halfWidth2);
        this.j.postConcat(this.i);
        canvas.drawBitmap(bitmap, this.j, aVar.a());
    }

    private static final a b(int i, int i2, Integer num) {
        Intrinsics.checkNotNullParameter(num, "it");
        return new a(i, i2);
    }

    public static final List access$getBrushPoints$p(PenBrushResult result) {
        return result.b;
    }

    public static a d(int color, int alpha, Integer ignored) {
        return b(color, alpha, ignored);
    }

    private final boolean c(PenBrushInk penBrushInk) {
        if (this.f) {
            return this.g.contains(penBrushInk.getX(), penBrushInk.getY());
        }
        return true;
    }

    private final void e(Canvas canvas) {
        if (this.f) {
            this.g.set(com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY, com.onyx.android.sdk.pen.utils.PenUtils.KEPLER_MIN_PRESSURE_SENSITIVITY, canvas.getWidth(), canvas.getHeight());
            float f = -(this.c.getConfig().width * this.e);
            this.g.inset(f, f);
            this.h.reset();
            this.d.invert(this.h);
            this.h.mapRect(this.g);
        }
    }

    private final void f() {
        Matrix matrix = this.i;
        matrix.reset();
        matrix.set(getMatrix());
        float f = 1;
        matrix.postScale(f / getPointSizeScale(), f / getPointSizeScale());
        com.onyx.android.sdk.pen.utils.PenUtils.INSTANCE.scaleTranslate(matrix, getPointSizeScale(), getPointSizeScale());
    }

    @Override // com.onyx.android.sdk.pen.PenResult
    @Nullable
    public PenResult append(@Nullable PenResult add) {
        return null;
    }

    @Override // com.onyx.android.sdk.pen.PenResult
    public void draw(@NotNull Canvas canvas, @NotNull Paint paint) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(paint, "paint");
        f();
        e(canvas);
        int alpha = paint.getAlpha();
        int color = paint.getColor();
        for (PenBrushInk penBrushInk : this.b) {
            if (c(penBrushInk)) {
                a(canvas, color, alpha, penBrushInk);
            }
        }
    }

    public boolean equals(@Nullable Object other) {
        if (other == null || !(other instanceof PenBrushResult)) {
            return false;
        }
        PenBrushResult penBrushResult = (PenBrushResult) other;
        if (this.b.size() != penBrushResult.b.size()) {
            return false;
        }
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            if (!Intrinsics.areEqual(this.b.get(i), penBrushResult.b.get(i))) {
                return false;
            }
        }
        return true;
    }

    @NotNull
    public final Matrix getMatrix() {
        return this.d;
    }

    public final float getPointSizeScale() {
        return this.e;
    }

    public final boolean isEnabledClipRect() {
        return this.f;
    }

    public final void setEnabledClipRect(boolean z) {
        this.f = z;
    }

    public final void setMatrix(@NotNull Matrix matrix) {
        Intrinsics.checkNotNullParameter(matrix, "<set-?>");
        this.d = matrix;
    }

    public final void setPointSizeScale(float f) {
        this.e = f;
    }
}
