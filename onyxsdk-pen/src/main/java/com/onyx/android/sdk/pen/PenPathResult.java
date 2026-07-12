package com.onyx.android.sdk.pen;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import com.onyx.android.sdk.base.lite.extension.PathKt;
import com.onyx.android.sdk.base.lite.extension.RectFKt;
import com.onyx.android.sdk.base.utils.Debug;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0015\n\u0002\b\u0005\n\u0002\u0010\u0014\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\u0018\u0000 \"2\u00020\u0001:\u0001\"B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0015\u001a\u00020\u00012\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0018\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!H\u0096\u0002R\u0014\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006#"}, d2 = {"Lcom/onyx/android/sdk/pen/PenPathResult;", "Lcom/onyx/android/sdk/pen/PenResult;", "path", "Landroid/graphics/Path;", "rect", "Landroid/graphics/RectF;", "(Landroid/graphics/Path;Landroid/graphics/RectF;)V", "getPath$sdk_pen_release", "()Landroid/graphics/Path;", "pointSizeArray", "", "getPointSizeArray", "()[I", "setPointSizeArray", "([I)V", "points", "", "getPoints", "()[F", "setPoints", "([F)V", "append", "add", "clearCache", "", "draw", "canvas", "Landroid/graphics/Canvas;", "paint", "Landroid/graphics/Paint;", "equals", "", "other", "", "Companion", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class PenPathResult extends PenResult {

    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final boolean e = false;
    private static int f;
    private static int g;

    @NotNull
    private final Path b;

    @NotNull
    private float[] c;

    @NotNull
    private int[] d;

    public static final boolean access$getDUMP_POINTS$cp() {
        return e;
    }

    public static final int access$getPathCount$cp() {
        return f;
    }

    public static final int access$getPointCount$cp() {
        return g;
    }

    public static final void access$setPathCount$cp(int value) {
        f = value;
    }

    public static final void access$setPointCount$cp(int value) {
        g = value;
    }

    @Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0014\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J:\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\t2\u0006\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0006H\u0002J\u0012\u0010\u0011\u001a\u0004\u0018\u00010\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014J\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lcom/onyx/android/sdk/pen/PenPathResult$Companion;", "", "()V", "DUMP_POINTS", "", "pathCount", "", "pointCount", "buildFromInk", "Lkotlin/Triple;", "Landroid/graphics/Path;", "Landroid/graphics/RectF;", "", "path", "points", "start", "end", "buildFromInkArray", "Lcom/onyx/android/sdk/pen/PenPathResult;", "ink", "Lcom/onyx/android/sdk/pen/PenInk;", "dumpCount", "", "resetCount", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final Triple<Path, RectF, float[]> a(Path path, float[] fArr, int i, int i2) {
            RectF rectF;
            int i3;
            RectF rectF2;
            float f;
            float f2;
            RectF rectF3;
            RectF rectF4 = new RectF();
            ArrayList arrayList = new ArrayList();
            IntProgression intProgressionStep = RangesKt.step(RangesKt.until(i, i2), 2);
            int first = intProgressionStep.getFirst();
            int last = intProgressionStep.getLast();
            int step = intProgressionStep.getStep();
            if (step > 0) {
                rectF2 = rectF4;
                i3 = first;
                if (first > last) {
                    rectF = rectF4;
                    if (step < 0) {
                        rectF = rectF4;
                        if (last <= first) {
                            i3 = first;
                            rectF2 = rectF4;
                            while (true) {
                                if (PenPathResult.e) {
                                    PenPathResult.g++;
                                }
                                f = fArr[i3];
                                f2 = fArr[i3 + 1];
                                if (i3 == i) {
                                    path.moveTo(f, f2);
                                    rectF3 = new RectF(f, f2, f, f2);
                                } else {
                                    path.lineTo(f, f2);
                                    rectF2.union(f, f2);
                                    rectF3 = rectF2;
                                }
                                arrayList.add(Float.valueOf(f));
                                arrayList.add(Float.valueOf(f2));
                                if (i3 == last) {
                                    break;
                                }
                                i3 += step;
                                rectF2 = rectF3;
                            }
                            rectF = rectF3;
                        }
                    }
                } else {
                    while (true) {
                        if (PenPathResult.e) {
                            PenPathResult.g++;
                        }
                        f = fArr[i3];
                        f2 = fArr[i3 + 1];
                        if (i3 == i) {
                            path.moveTo(f, f2);
                            rectF3 = new RectF(f, f2, f, f2);
                        } else {
                            path.lineTo(f, f2);
                            rectF2.union(f, f2);
                            rectF3 = rectF2;
                        }
                        arrayList.add(Float.valueOf(f));
                        arrayList.add(Float.valueOf(f2));
                        if (i3 == last) {
                            break;
                        }
                        i3 += step;
                        rectF2 = rectF3;
                    }
                    rectF = rectF3;
                }
            } else {
                rectF = rectF4;
                if (step < 0) {
                    rectF = rectF4;
                    if (last <= first) {
                        i3 = first;
                        rectF2 = rectF4;
                        while (true) {
                            if (PenPathResult.e) {
                                PenPathResult.g++;
                            }
                            f = fArr[i3];
                            f2 = fArr[i3 + 1];
                            if (i3 == i) {
                                path.moveTo(f, f2);
                                rectF3 = new RectF(f, f2, f, f2);
                            } else {
                                path.lineTo(f, f2);
                                rectF2.union(f, f2);
                                rectF3 = rectF2;
                            }
                            arrayList.add(Float.valueOf(f));
                            arrayList.add(Float.valueOf(f2));
                            if (i3 == last) {
                                break;
                            }
                            i3 += step;
                            rectF2 = rectF3;
                        }
                        rectF = rectF3;
                    }
                }
            }
            path.close();
            return new Triple<>(path, rectF, CollectionsKt.toFloatArray(arrayList));
        }

        @Nullable
        public final PenPathResult buildFromInkArray(@Nullable PenInk ink) {
            if (ink == null) {
                return null;
            }
            Path path = new Path();
            ArrayList arrayList = new ArrayList();
            path.incReserve(ink.getPoints().length / 2);
            int length = ink.getPointSizeArray().length;
            int i = 0;
            RectF rectF = null;
            int i2 = 0;
            int i3 = 0;
            while (i < length) {
                if (PenPathResult.e) {
                    PenPathResult.f++;
                }
                i3 += ink.getPointSizeArray()[i];
                Triple<Path, RectF, float[]> tripleA = a(path, ink.getPoints(), i2, i3);
                arrayList.add(tripleA.getThird());
                RectF rectF2 = rectF;
                if (rectF == null) {
                    rectF2 = new RectF((RectF) tripleA.getSecond());
                }
                rectF2.union((RectF) tripleA.getSecond());
                i2 = i3;
                i++;
                rectF = rectF2;
            }
            if (rectF == null) {
                return null;
            }
            PenPathResult penPathResult = new PenPathResult(path, rectF);
            penPathResult.setPoints(ink.getPoints());
            penPathResult.setPointSizeArray(ink.getPointSizeArray());
            return penPathResult;
        }

        public final void dumpCount() {
            Debug.INSTANCE.i(Companion.class, "path count: " + PenPathResult.f + ", point count: " + PenPathResult.g, new Object[0]);
        }

        public final void resetCount() {
            PenPathResult.f = 0;
            PenPathResult.g = 0;
        }
    }

    public PenPathResult(@NotNull Path path, @NotNull RectF rectF) {
        super(rectF);
        Intrinsics.checkNotNullParameter(path, "path");
        Intrinsics.checkNotNullParameter(rectF, "rect");
        this.b = path;
        this.c = new float[0];
        this.d = new int[0];
    }

    @Override // com.onyx.android.sdk.pen.PenResult
    @NotNull
    public PenResult append(@Nullable PenResult add) {
        if (add == null) {
            return this;
        }
        PenPathResult penPathResult = (PenPathResult) add;
        Path pathCopy = PathKt.copy(this.b);
        pathCopy.addPath(penPathResult.b);
        RectF rectFCopy = RectFKt.copy(getRect());
        rectFCopy.union(penPathResult.getRect());
        return new PenPathResult(pathCopy, rectFCopy);
    }

    @Override // com.onyx.android.sdk.pen.PenResult
    public void clearCache() {
        this.c = new float[0];
        this.d = new int[0];
    }

    @Override // com.onyx.android.sdk.pen.PenResult
    public void draw(@NotNull Canvas canvas, @NotNull Paint paint) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Intrinsics.checkNotNullParameter(paint, "paint");
        canvas.drawPath(this.b, paint);
    }

    public boolean equals(@Nullable Object other) {
        if (other == null || !(other instanceof PenPathResult)) {
            return false;
        }
        float[] fArr = this.c;
        int length = fArr.length;
        PenPathResult penPathResult = (PenPathResult) other;
        if (length != penPathResult.c.length) {
            return false;
        }
        int length2 = fArr.length;
        int i = 0;
        while (true) {
            boolean z = true;
            if (i >= length2) {
                return true;
            }
            if (this.c[i] != penPathResult.c[i]) {
                z = false;
            }
            if (!z) {
                return false;
            }
            i++;
        }
    }

    @NotNull
    public final Path getPath$sdk_pen_release() {
        return this.b;
    }

    @NotNull
    public final int[] getPointSizeArray() {
        return this.d;
    }

    @NotNull
    public final float[] getPoints() {
        return this.c;
    }

    public final void setPointSizeArray(@NotNull int[] iArr) {
        Intrinsics.checkNotNullParameter(iArr, "<set-?>");
        this.d = iArr;
    }

    public final void setPoints(@NotNull float[] fArr) {
        Intrinsics.checkNotNullParameter(fArr, "<set-?>");
        this.c = fArr;
    }
}
