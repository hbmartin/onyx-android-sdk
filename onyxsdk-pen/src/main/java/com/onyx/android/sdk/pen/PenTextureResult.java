package com.onyx.android.sdk.pen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/PenTextureResult.class */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0014\u0010\n\u001a\u0004\u0018\u00010\u00012\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001H\u0016J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\u0013"}, d2 = {"Lcom/onyx/android/sdk/pen/PenTextureResult;", "Lcom/onyx/android/sdk/pen/PenResult;", "textures", "", "Lcom/onyx/android/sdk/pen/PenTextureInk;", "rect", "Landroid/graphics/RectF;", "(Ljava/util/List;Landroid/graphics/RectF;)V", "getTextures", "()Ljava/util/List;", "append", "add", "draw", "", "canvas", "Landroid/graphics/Canvas;", "paint", "Landroid/graphics/Paint;", "Companion", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class PenTextureResult extends PenResult {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private final List<PenTextureInk> b;

    /* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/PenTextureResult$Companion.class */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006¨\u0006\u0007"}, d2 = {"Lcom/onyx/android/sdk/pen/PenTextureResult$Companion;", "", "()V", "buildFromInkArray", "Lcom/onyx/android/sdk/pen/PenTextureResult;", "ink", "Lcom/onyx/android/sdk/pen/PenInk;", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0001: CONSTRUCTOR  A[MD:():void (m)] call: com.onyx.android.sdk.pen.PenTextureResult.Companion.<init>():void type: THIS */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX DEBUG: Move duplicate insns, count: 1 to block B:15:0x004e */
        @Nullable
        public final PenTextureResult buildFromInkArray(@Nullable PenInk ink) {
            RectF rectF;
            if (ink == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            IntProgression intProgressionStep = RangesKt.step(RangesKt.until(0, ink.getPoints().length), 2);
            int first = intProgressionStep.getFirst();
            int last = intProgressionStep.getLast();
            int step = intProgressionStep.getStep();
            if ((step <= 0 || first > last) && (step >= 0 || last > first)) {
                rectF = null;
            } else {
                RectF rectF2 = null;
                while (true) {
                    RectF rectF3 = rectF2;
                    int i = first / 2;
                    rectF = rectF3;
                    if (i < ink.getBitmaps().length) {
                        Bitmap bitmap = ink.getBitmaps()[i];
                        float f = ink.getPoints()[first];
                        float f2 = ink.getPoints()[first + 1];
                        arrayList.add(new PenTextureInk(f, f2, bitmap));
                        rectF = rectF3;
                        if (rectF3 == null) {
                            rectF = new RectF(f, f2, bitmap.getWidth() + f, bitmap.getHeight() + f2);
                        }
                        rectF.union(f, f2);
                        rectF.union(f + bitmap.getWidth(), f2 + bitmap.getHeight());
                    }
                    if (first == last) {
                        break;
                    }
                    first += step;
                    rectF2 = rectF;
                }
            }
            if (rectF == null) {
                return null;
            }
            return new PenTextureResult(arrayList, rectF);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PenTextureResult(@NotNull List<PenTextureInk> list, @NotNull RectF rectF) {
        super(rectF);
        Intrinsics.checkNotNullParameter(list, "textures");
        Intrinsics.checkNotNullParameter(rectF, "rect");
        this.b = list;
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
        for (PenTextureInk penTextureInk : this.b) {
            canvas.drawBitmap(penTextureInk.getBitmap(), penTextureInk.getX(), penTextureInk.getY(), paint);
        }
    }

    @NotNull
    public final List<PenTextureInk> getTextures() {
        return this.b;
    }
}


