package com.onyx.android.sdk.pen;

import com.onyx.android.sdk.base.data.TouchPoint;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/NeoFountainPen.class */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u0000 \u00122\u00020\u0001:\u0001\u0012B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J'\u0010\u0007\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0006\u0012\u0004\u0018\u00010\t0\b2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0010¢\u0006\u0002\b\fJ(\u0010\r\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0006\u0012\u0004\u0018\u00010\t0\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoFountainPen;", "Lcom/onyx/android/sdk/pen/NeoNativePen;", "handle", "", "(J)V", "lastResult", "Lcom/onyx/android/sdk/pen/PenPointResult;", "buildPenResult", "Lkotlin/Pair;", "Lcom/onyx/android/sdk/pen/PenResult;", "result", "Lcom/onyx/android/sdk/pen/NeoPenResult;", "buildPenResult$sdk_pen_release", "onPenDown", "point", "Lcom/onyx/android/sdk/base/data/TouchPoint;", "repaint", "", "Companion", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class NeoFountainPen extends NeoNativePen {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion Companion = new Companion(null);

    @Nullable
    private PenPointResult b;

    /* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/NeoFountainPen$Companion.class */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoFountainPen$Companion;", "", "()V", "create", "Lcom/onyx/android/sdk/pen/NeoPen;", "config", "Lcom/onyx/android/sdk/pen/NeoPenConfig;", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0001: CONSTRUCTOR  A[MD:():void (m)] call: com.onyx.android.sdk.pen.NeoFountainPen.Companion.<init>():void type: THIS */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Nullable
        public final NeoPen create(@NotNull NeoPenConfig config) {
            Intrinsics.checkNotNullParameter(config, "config");
            long jCreatePen = NeoPenNative.INSTANCE.createPen(2, config);
            if (jCreatePen == 0) {
                return null;
            }
            return new NeoFountainPen(jCreatePen, null);
        }
    }

    private NeoFountainPen(long j) {
        super(j);
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0002: CONSTRUCTOR (r5v0 long) A[MD:(long):void (m)] call: com.onyx.android.sdk.pen.NeoFountainPen.<init>(long):void type: THIS */
    public /* synthetic */ NeoFountainPen(long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(j);
    }

    @Override // com.onyx.android.sdk.pen.NeoNativePen
    @NotNull
    public Pair<PenResult, PenResult> buildPenResult$sdk_pen_release(@Nullable NeoPenResult result) {
        PenPointResult.Companion companion = PenPointResult.Companion;
        PenPointResult penPointResultBuildFromInkArray = companion.buildFromInkArray(result == null ? null : result.getRealInk(), this.b);
        PenPointResult penPointResultBuildFromInkArray2 = companion.buildFromInkArray(result == null ? null : result.getPredictionInk(), penPointResultBuildFromInkArray);
        if (penPointResultBuildFromInkArray != null) {
            this.b = penPointResultBuildFromInkArray;
        }
        return new Pair<>(penPointResultBuildFromInkArray, penPointResultBuildFromInkArray2);
    }

    @Override // com.onyx.android.sdk.pen.NeoNativePen, com.onyx.android.sdk.pen.NeoPen
    @NotNull
    public Pair<PenResult, PenResult> onPenDown(@NotNull TouchPoint point, boolean repaint) {
        Intrinsics.checkNotNullParameter(point, "point");
        this.b = null;
        return super.onPenDown(point, repaint);
    }
}


