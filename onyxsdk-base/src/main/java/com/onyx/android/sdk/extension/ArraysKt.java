// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.extension;

import org.jetbrains.annotations.NotNull;
import android.util.Log;
import kotlin.jvm.internal.Intrinsics;
import java.nio.charset.Charset;
import org.jetbrains.annotations.Nullable;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000 \n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\u0010\u0015\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a0\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u0003\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\b\u0003\u0010\u0000¢\u0006\u0002\u0010\u0004\u001a\f\u0010\u0005\u001a\u00020\u0001*\u0004\u0018\u00010\u0006\u001a\f\u0010\u0005\u001a\u00020\u0001*\u0004\u0018\u00010\u0007\u001a\f\u0010\b\u001a\u00020\t*\u0004\u0018\u00010\u0006¨\u0006\n" }, d2 = { "isNotEmpty", "", "T", "", "([Ljava/lang/Object;)Z", "isNullOrEmpty", "", "", "utf16le", "", "onyxsdk-base_release" })
public final class ArraysKt
{
    public static final boolean isNullOrEmpty(@Nullable final int[] $this$isNullOrEmpty) {
        return $this$isNullOrEmpty == null || $this$isNullOrEmpty.length == 0;
    }
    
    public static final <T> boolean isNotEmpty(@Nullable final T[] $this$isNotEmpty) {
        if ($this$isNotEmpty != null) {
            if ($this$isNotEmpty.length != 0) {
                final int n = 0;
                return (n ^ 0x1) != 0x0;
            }
        }
        final int n = 1;
        return (n ^ 0x1) != 0x0;
    }
    
    @NotNull
    public static final String utf16le(@Nullable final byte[] $this$utf16le) {
        final String s = "";
        if ($this$utf16le == null) {
            return s;
        }
        String s2;
        try {
            final Charset forName;
            Intrinsics.checkNotNullExpressionValue((Object)(forName = Charset.forName("UTF-16LE")), "forName(UTF16LE)");
            s2 = new String($this$utf16le, forName);
        }
        catch (final Exception ex) {
            Log.w("", (Throwable)ex);
            s2 = s;
        }
        return s2;
    }
    
    public static final boolean isNullOrEmpty(@Nullable final byte[] $this$isNullOrEmpty) {
        return $this$isNullOrEmpty == null || $this$isNullOrEmpty.length == 0;
    }
}
