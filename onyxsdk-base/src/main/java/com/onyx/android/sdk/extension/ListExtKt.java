// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.extension;

import java.util.Collections;
import java.util.Collection;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\u0016\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u001a&\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005¨\u0006\u0007" }, d2 = { "swap", "", "T", "", "i", "", "j", "onyxsdk-base_release" })
public final class ListExtKt
{
    private ListExtKt() {
    }

    public static final <T> void swap(@NotNull final List<? extends T> $this$swap, final int i, final int j) {
        Intrinsics.checkNotNullParameter((Object)$this$swap, "<this>");
        if (!CollectionKt.isOutOfRange($this$swap, i) && !CollectionKt.isOutOfRange($this$swap, j)) {
            Collections.swap($this$swap, i, j);
        }
    }
}
