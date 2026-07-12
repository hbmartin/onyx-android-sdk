// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import android.util.LruCache;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\f\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0002¨\u0006\u0003" }, d2 = { "sizeMBString", "", "Landroid/util/LruCache;", "onyxsdk-base_release" })
public final class LruCacheKt
{
    private LruCacheKt() {
    }

    @NotNull
    public static final String sizeMBString(@NotNull final LruCache<?, ?> $this$sizeMBString) {
        Intrinsics.checkNotNullParameter((Object)$this$sizeMBString, "<this>");
        return $this$sizeMBString.size() / 1048576 + " MB";
    }
}
