package com.onyx.android.sdk.pen;

import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u000b2\u00020\u0001:\u0001\u000bB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J'\u0010\u0005\u001a\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u00062\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0010¢\u0006\u0002\b\n¨\u0006\f"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoCharcoalPen;", "Lcom/onyx/android/sdk/pen/NeoNativePen;", "penHandle", "", "(J)V", "buildPenResult", "Lkotlin/Pair;", "Lcom/onyx/android/sdk/pen/PenResult;", "result", "Lcom/onyx/android/sdk/pen/NeoPenResult;", "buildPenResult$sdk_pen_release", "Companion", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class NeoCharcoalPen extends NeoNativePen {

    @NotNull
    public static final Companion Companion = new Companion(null);

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoCharcoalPen$Companion;", "", "()V", "create", "Lcom/onyx/android/sdk/pen/NeoCharcoalPen;", "config", "Lcom/onyx/android/sdk/pen/NeoPenConfig;", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Nullable
        public final NeoCharcoalPen create(@NotNull NeoPenConfig config) {
            Intrinsics.checkNotNullParameter(config, "config");
            long jCreatePen = NeoPenNative.INSTANCE.createPen(4, config);
            if (jCreatePen == 0) {
                return null;
            }
            return new NeoCharcoalPen(jCreatePen, null);
        }
    }

    private NeoCharcoalPen(long j) {
        super(j);
    }

    public /* synthetic */ NeoCharcoalPen(long j, DefaultConstructorMarker defaultConstructorMarker) {
        this(j);
    }

    @Override // com.onyx.android.sdk.pen.NeoNativePen
    @NotNull
    public Pair<PenResult, PenResult> buildPenResult$sdk_pen_release(@Nullable NeoPenResult result) {
        PenTextureResult.Companion companion = PenTextureResult.Companion;
        return new Pair<>(companion.buildFromInkArray(result == null ? null : result.getRealInk()), companion.buildFromInkArray(result == null ? null : result.getPredictionInk()));
    }
}


