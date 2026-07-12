package com.onyx.android.sdk.pen;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lcom/onyx/android/sdk/pen/NeoBallpointPen;", "", "()V", "create", "Lcom/onyx/android/sdk/pen/NeoPen;", "segment", "", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class NeoBallpointPen {

    @NotNull
    public static final NeoBallpointPen INSTANCE = new NeoBallpointPen();

    private NeoBallpointPen() {
    }

    public static /* synthetic */ NeoPen create$default(NeoBallpointPen neoBallpointPen, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = true;
        }
        return neoBallpointPen.create(z);
    }

    @NotNull
    public final NeoPen create(boolean segment) {
        return segment ? new NeoSegmentPathResultPen(0L, 1, null) : new NeoSinglePathResultPen(0L, 1, null);
    }
}

