package com.onyx.android.sdk.pen;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/PenResult.class */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u0000 \u00102\u00020\u0001:\u0001\u0010B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0014\u0010\u0007\u001a\u0004\u0018\u00010\u00002\b\u0010\b\u001a\u0004\u0018\u00010\u0000H&J\b\u0010\t\u001a\u00020\nH\u0016J\u0018\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH&R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lcom/onyx/android/sdk/pen/PenResult;", "", "rect", "Landroid/graphics/RectF;", "(Landroid/graphics/RectF;)V", "getRect", "()Landroid/graphics/RectF;", "append", "add", "clearCache", "", "draw", "canvas", "Landroid/graphics/Canvas;", "paint", "Landroid/graphics/Paint;", "Companion", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public abstract class PenResult {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private final RectF a;

    /* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/PenResult$Companion.class */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u0004*\u001a\u0012\u0014\u0012\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006\u0018\u00010\u0005J\u001c\u0010\b\u001a\u00020\u0004*\u0004\u0018\u00010\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fJ2\u0010\b\u001a\u00020\u0004*\u001a\u0012\u0014\u0012\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006\u0018\u00010\u00052\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f¨\u0006\r"}, d2 = {"Lcom/onyx/android/sdk/pen/PenResult$Companion;", "", "()V", "clearCache", "", "", "Lkotlin/Pair;", "Lcom/onyx/android/sdk/pen/PenResult;", "render", "canvas", "Landroid/graphics/Canvas;", "paint", "Landroid/graphics/Paint;", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0001: CONSTRUCTOR  A[MD:():void (m)] call: com.onyx.android.sdk.pen.PenResult.Companion.<init>():void type: THIS */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final void clearCache(@Nullable List<? extends Pair<? extends PenResult, ? extends PenResult>> list) {
            if (list == null) {
                return;
            }
            Iterator<?> it = list.iterator();
            while (it.hasNext()) {
                PenResult penResult = (PenResult) ((Pair) it.next()).getFirst();
                if (penResult != null) {
                    penResult.clearCache();
                }
            }
        }

        public final void render(@Nullable PenResult penResult, @NotNull Canvas canvas, @NotNull Paint paint) {
            Intrinsics.checkNotNullParameter(canvas, "canvas");
            Intrinsics.checkNotNullParameter(paint, "paint");
            if (penResult == null) {
                return;
            }
            penResult.draw(canvas, paint);
        }

        public final void render(@Nullable List<? extends Pair<? extends PenResult, ? extends PenResult>> list, @NotNull Canvas canvas, @NotNull Paint paint) {
            PenResult penResult;
            Intrinsics.checkNotNullParameter(canvas, "canvas");
            Intrinsics.checkNotNullParameter(paint, "paint");
            if (list == null) {
                return;
            }
            Iterator<?> it = list.iterator();
            while (it.hasNext()) {
                PenResult.Companion.render((PenResult) ((Pair) it.next()).getFirst(), canvas, paint);
            }
            Pair pair = (Pair) CollectionsKt.lastOrNull(list);
            if (pair == null || (penResult = (PenResult) pair.getSecond()) == null) {
                return;
            }
            render(penResult, canvas, paint);
        }
    }

    public PenResult(@NotNull RectF rectF) {
        Intrinsics.checkNotNullParameter(rectF, "rect");
        this.a = rectF;
    }

    @Nullable
    public abstract PenResult append(@Nullable PenResult add);

    public void clearCache() {
    }

    public abstract void draw(@NotNull Canvas canvas, @NotNull Paint paint);

    @NotNull
    public final RectF getRect() {
        return this.a;
    }
}

