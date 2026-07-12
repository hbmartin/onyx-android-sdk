// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import kotlin.jvm.functions.Function0;
import kotlin.LazyKt;
import kotlin.Lazy;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\u00042\b\b\u0002\u0010\r\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\u000b\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u000e" }, d2 = { "Lcom/onyx/android/sdk/utils/BitmapLruCacheConfig;", "", "()V", "BYTE_PER_PIXEL", "", "BYTE_PER_SCREEN", "", "getBYTE_PER_SCREEN", "()F", "BYTE_PER_SCREEN$delegate", "Lkotlin/Lazy;", "DEFAULT_SCREEN_SIZE", "bitmapCacheMaxByteCount", "screenSize", "onyxsdk-base_release" })
public final class BitmapLruCacheConfig
{
    @NotNull
    public static final BitmapLruCacheConfig INSTANCE;
    private static final int a = 4;
    private static final int b = 3;
    @NotNull
    private static final Lazy c;
    
    private BitmapLruCacheConfig() {
    }
    
    static {
        INSTANCE = new BitmapLruCacheConfig();
        c = LazyKt.lazy((Function0)BitmapLruCacheConfig$a.a);
    }
    
    public final float getBYTE_PER_SCREEN() {
        return ((Number)BitmapLruCacheConfig.c.getValue()).floatValue();
    }
    
    public final int bitmapCacheMaxByteCount(final int screenSize) {
        return (int)(screenSize * this.getBYTE_PER_SCREEN());
    }

    public static /* synthetic */ int bitmapCacheMaxByteCount$default(final BitmapLruCacheConfig bitmapLruCacheConfig, int screenSize, final int i, final Object obj) {
        if ((i & 1) != 0) {
            screenSize = 3;
        }
        return bitmapLruCacheConfig.bitmapCacheMaxByteCount(screenSize);
    }
}
