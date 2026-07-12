// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import org.jetbrains.annotations.Nullable;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\f\u0010\u0000\u001a\u00020\u0001*\u0004\u0018\u00010\u0002¨\u0006\u0003" }, d2 = { "isSafelyValid", "", "Lcom/onyx/android/sdk/utils/SimpleBitmapReference;", "onyxsdk-base_release" })
public final class BitmapLruCacheKt
{
    public static final boolean isSafelyValid(@Nullable final SimpleBitmapReference $this$isSafelyValid) {
        return $this$isSafelyValid != null && $this$isSafelyValid.isValid();
    }
}
