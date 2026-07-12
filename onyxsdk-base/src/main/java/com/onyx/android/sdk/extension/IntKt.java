// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.extension;

import org.jetbrains.annotations.NotNull;
import kotlin.jvm.internal.Intrinsics;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\u001e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u0001\u001a\u0012\u0010\u0004\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0001\u001a\n\u0010\u0006\u001a\u00020\u0007*\u00020\u0001\u001a\u0012\u0010\b\u001a\u00020\t*\u00020\u00012\u0006\u0010\n\u001a\u00020\u0001\u001a\u0012\u0010\u000b\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\f\u001a\u00020\u0001\u001a\n\u0010\r\u001a\u00020\u0003*\u00020\u0001\u001a\n\u0010\u000e\u001a\u00020\u0003*\u00020\u0001\u001a\n\u0010\u000f\u001a\u00020\u0003*\u00020\u0001\u001a\n\u0010\u0010\u001a\u00020\u0003*\u00020\u0001¨\u0006\u0011" }, d2 = { "calculatePages", "", "pageSize", "", "defaultIfZero", "placeholder", "formatNumber", "", "hasFlag", "", "flag", "safeDivide", "divisor", "toBytes", "toGb", "toKb", "toMb", "onyxsdk-base_release" })
public final class IntKt
{
    private IntKt() {
    }

    public static final boolean hasFlag(final int $this$hasFlag, final int flag) {
        return ($this$hasFlag & flag) == flag;
    }
    
    @NotNull
    public static final String formatNumber(final int $this$formatNumber) {
        return ($this$formatNumber > 9) ? String.valueOf($this$formatNumber) : Intrinsics.stringPlus("0", (Object)$this$formatNumber);
    }
    
    public static final int safeDivide(int $this$safeDivide, final int divisor) {
        if (divisor != 0) {
            $this$safeDivide /= divisor;
        }
        return $this$safeDivide;
    }
    
    public static final int defaultIfZero(final int $this$defaultIfZero, int placeholder) {
        Integer value;
        if ((value = $this$defaultIfZero).intValue() == 0) {
            value = null;
        }
        if (value != null) {
            placeholder = value;
        }
        return placeholder;
    }
    
    public static final long toBytes(final int $this$toBytes) {
        return $this$toBytes;
    }
    
    public static final long toKb(final int $this$toKb) {
        return $this$toKb * 1024L;
    }
    
    public static final long toMb(final int $this$toMb) {
        return $this$toMb * 1024L * 1024;
    }
    
    public static final long toGb(final int $this$toGb) {
        final long n = $this$toGb * 1024L;
        final long n2 = 1024;
        return n * n2 * n2;
    }
    
    public static final int calculatePages(final int $this$calculatePages, final int pageSize) {
        return calculatePages((long)$this$calculatePages, pageSize);
    }
    
    public static final int calculatePages(final long $this$calculatePages, final int pageSize) {
        long n;
        return ($this$calculatePages <= 0L) ? 0 : (($this$calculatePages <= (n = pageSize)) ? 1 : ((int)(($this$calculatePages + n - 1L) / n)));
    }
}
