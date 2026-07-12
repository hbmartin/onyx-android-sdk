package com.onyx.android.sdk.pen;

import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/NeoPenResult.class */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\n"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoPenResult;", "", "realInk", "Lcom/onyx/android/sdk/pen/PenInk;", "predictionInk", "(Lcom/onyx/android/sdk/pen/PenInk;Lcom/onyx/android/sdk/pen/PenInk;)V", "getPredictionInk", "()Lcom/onyx/android/sdk/pen/PenInk;", "getRealInk", "Companion", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class NeoPenResult {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private final PenInk a;

    @NotNull
    private final PenInk b;

    /* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/NeoPenResult$Companion.class */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\u0007¨\u0006\b"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoPenResult$Companion;", "", "()V", "create", "Lcom/onyx/android/sdk/pen/NeoPenResult;", "realInk", "Lcom/onyx/android/sdk/pen/PenInk;", "predictionInk", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0001: CONSTRUCTOR  A[MD:():void (m)] call: com.onyx.android.sdk.pen.NeoPenResult.Companion.<init>():void type: THIS */
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @JvmStatic
        @NotNull
        public final NeoPenResult create(@NotNull PenInk realInk, @NotNull PenInk predictionInk) {
            Intrinsics.checkNotNullParameter(realInk, "realInk");
            Intrinsics.checkNotNullParameter(predictionInk, "predictionInk");
            return new NeoPenResult(realInk, predictionInk);
        }
    }

    public NeoPenResult(@NotNull PenInk penInk, @NotNull PenInk penInk2) {
        Intrinsics.checkNotNullParameter(penInk, "realInk");
        Intrinsics.checkNotNullParameter(penInk2, "predictionInk");
        this.a = penInk;
        this.b = penInk2;
    }

    @JvmStatic
    @NotNull
    public static final NeoPenResult create(@NotNull PenInk penInk, @NotNull PenInk penInk2) {
        return Companion.create(penInk, penInk2);
    }

    @NotNull
    public final PenInk getPredictionInk() {
        return this.b;
    }

    @NotNull
    public final PenInk getRealInk() {
        return this.a;
    }
}

