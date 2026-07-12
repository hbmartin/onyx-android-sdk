package com.onyx.android.sdk.pen;

import com.onyx.android.sdk.base.data.TouchPoint;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/NeoFountainPenV2.class */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J'\u0010\r\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000e2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0010¢\u0006\u0002\b\u0012J*\u0010\u0013\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000e2\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0015H\u0016J(\u0010\u0013\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u000f\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u000e2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0018H\u0016R\u001a\u0010\u0004\u001a\u00020\u0005X\u0080\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001a"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoFountainPenV2;", "Lcom/onyx/android/sdk/pen/NeoNativePen;", "handle", "", "config", "Lcom/onyx/android/sdk/pen/NeoPenConfig;", "(JLcom/onyx/android/sdk/pen/NeoPenConfig;)V", "getConfig$sdk_pen_release", "()Lcom/onyx/android/sdk/pen/NeoPenConfig;", "setConfig$sdk_pen_release", "(Lcom/onyx/android/sdk/pen/NeoPenConfig;)V", "lastPointResult", "Lcom/onyx/android/sdk/pen/PenPointResult;", "buildPenResult", "Lkotlin/Pair;", "Lcom/onyx/android/sdk/pen/PenResult;", "result", "Lcom/onyx/android/sdk/pen/NeoPenResult;", "buildPenResult$sdk_pen_release", "onPenDown", "point", "Lcom/onyx/android/sdk/base/data/TouchPoint;", "prediction", "repaint", "", "Companion", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class NeoFountainPenV2 extends NeoNativePen {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private NeoPenConfig b;

    @Nullable
    private PenPointResult c;

    /* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/NeoFountainPenV2$Companion.class */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoFountainPenV2$Companion;", "", "()V", "create", "Lcom/onyx/android/sdk/pen/NeoPen;", "config", "Lcom/onyx/android/sdk/pen/NeoPenConfig;", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0001: CONSTRUCTOR  A[MD:():void (m)] call: com.onyx.android.sdk.pen.NeoFountainPenV2.Companion.<init>():void type: THIS */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Nullable
        public final NeoPen create(@NotNull NeoPenConfig config) {
            Intrinsics.checkNotNullParameter(config, "config");
            long jCreatePen = NeoPenNative.INSTANCE.createPen(6, config);
            if (jCreatePen == 0) {
                return null;
            }
            return new NeoFountainPenV2(jCreatePen, config, null);
        }
    }

    private NeoFountainPenV2(long j, NeoPenConfig neoPenConfig) {
        super(j);
        this.b = neoPenConfig;
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0003: CONSTRUCTOR (r6v0 long), (r8v0 com.onyx.android.sdk.pen.NeoPenConfig) A[MD:(long, com.onyx.android.sdk.pen.NeoPenConfig):void (m)] call: com.onyx.android.sdk.pen.NeoFountainPenV2.<init>(long, com.onyx.android.sdk.pen.NeoPenConfig):void type: THIS */
    public /* synthetic */ NeoFountainPenV2(long j, NeoPenConfig neoPenConfig, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, neoPenConfig);
    }

    @Override // com.onyx.android.sdk.pen.NeoNativePen
    @NotNull
    public Pair<PenResult, PenResult> buildPenResult$sdk_pen_release(@Nullable NeoPenResult result) {
        if (!this.b.fastMode) {
            PenPathResult.Companion companion = PenPathResult.Companion;
            return new Pair<>(companion.buildFromInkArray(result == null ? null : result.getRealInk()), companion.buildFromInkArray(result == null ? null : result.getPredictionInk()));
        }
        PenPointResult.Companion companion2 = PenPointResult.Companion;
        PenPointResult penPointResultBuildFromInkArray = companion2.buildFromInkArray(result == null ? null : result.getRealInk(), this.c);
        PenPointResult penPointResultBuildFromInkArray2 = companion2.buildFromInkArray(result == null ? null : result.getPredictionInk(), penPointResultBuildFromInkArray);
        if (penPointResultBuildFromInkArray != null) {
            this.c = penPointResultBuildFromInkArray;
        }
        return new Pair<>(penPointResultBuildFromInkArray, penPointResultBuildFromInkArray2);
    }

    @NotNull
    public final NeoPenConfig getConfig$sdk_pen_release() {
        return this.b;
    }

    @Override // com.onyx.android.sdk.pen.NeoNativePen, com.onyx.android.sdk.pen.NeoPen
    @NotNull
    public Pair<PenResult, PenResult> onPenDown(@NotNull TouchPoint point, @Nullable TouchPoint prediction) {
        Intrinsics.checkNotNullParameter(point, "point");
        this.c = null;
        return super.onPenDown(point, prediction);
    }

    @Override // com.onyx.android.sdk.pen.NeoNativePen, com.onyx.android.sdk.pen.NeoPen
    @NotNull
    public Pair<PenResult, PenResult> onPenDown(@NotNull TouchPoint point, boolean repaint) {
        Intrinsics.checkNotNullParameter(point, "point");
        this.c = null;
        return super.onPenDown(point, repaint);
    }

    public final void setConfig$sdk_pen_release(@NotNull NeoPenConfig neoPenConfig) {
        Intrinsics.checkNotNullParameter(neoPenConfig, "<set-?>");
        this.b = neoPenConfig;
    }
}


