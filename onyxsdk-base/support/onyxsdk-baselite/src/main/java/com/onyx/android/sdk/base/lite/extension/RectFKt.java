package com.onyx.android.sdk.base.lite.extension;

import android.graphics.RectF;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv = {1, 6, 0}, k = 2, xi = 48, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0001¨\u0006\u0002"}, d2 = {"copy", "Landroid/graphics/RectF;", "sdk-baselite_release"})
public final class RectFKt {
    @NotNull
    public static final RectF copy(@NotNull RectF $this$copy) {
        Intrinsics.checkNotNullParameter($this$copy, "<this>");
        return new RectF($this$copy);
    }
}
