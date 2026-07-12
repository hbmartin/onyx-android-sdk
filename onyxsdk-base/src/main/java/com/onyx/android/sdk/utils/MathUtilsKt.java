// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import kotlin.jvm.functions.Function0;
import com.onyx.android.sdk.extension.AnyKt;
import kotlin.ranges.RangesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0004\n\u0002\b\u0005\n\u0002\u0010\u000f\n\u0002\b\u0010\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00042\u0006\u0010\u0016\u001a\u00020\u00042\b\b\u0002\u0010\u0017\u001a\u00020\u0004J%\u0010\u0018\u001a\u0002H\u0019\"\b\b\u0000\u0010\u0019*\u00020\u001a2\u0006\u0010\u001b\u001a\u0002H\u00192\u0006\u0010\u001c\u001a\u0002H\u0019¢\u0006\u0002\u0010\u001dJ\u0016\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u001a2\u0006\u0010\u0016\u001a\u00020\u001aJ'\u0010\u001f\u001a\u0002H\u0019\"\u000e\b\u0000\u0010\u0019*\b\u0012\u0004\u0012\u0002H\u00190 *\u0002H\u00192\u0006\u0010!\u001a\u0002H\u0019¢\u0006\u0002\u0010\"J'\u0010#\u001a\u0002H\u0019\"\u000e\b\u0000\u0010\u0019*\b\u0012\u0004\u0012\u0002H\u00190 *\u0002H\u00192\u0006\u0010$\u001a\u0002H\u0019¢\u0006\u0002\u0010\"J\u0012\u0010%\u001a\u00020\u0004*\u00020\u001a2\u0006\u0010&\u001a\u00020\u001aJ8\u0010'\u001a\u00020\u0014*\u00020\u001a2\u0006\u0010(\u001a\u00020\u001a2\u0006\u0010)\u001a\u00020\u001a2\b\b\u0002\u0010*\u001a\u00020\u00142\b\b\u0002\u0010+\u001a\u00020\u00142\b\b\u0002\u0010,\u001a\u00020\u0014J\u0012\u0010-\u001a\u00020\u0014*\u00020\u001a2\u0006\u0010&\u001a\u00020\u001aJ\n\u0010.\u001a\u00020\u0006*\u00020\u001aJ\n\u0010/\u001a\u00020\u0006*\u00020\u001aR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u00060" }, d2 = { "Lcom/onyx/android/sdk/utils/MathUtilsKt;", "", "()V", "ARE_APPROXIMATELY_EQUAL_EPSILON", "", "DEGREE_0", "", "DEGREE_180", "DEGREE_30", "DEGREE_360", "DEGREE_45", "DEGREE_60", "DEGREE_90", "FLOAT_PI", "ONE_HALF", "PI_FLOAT", "PI_HALF_FLOAT", "PI_TWICE_FLOAT", "RAD_1_DEG", "areApproximatelyEqual", "", "num1", "num2", "epsilon", "average", "T", "", "a", "b", "(Ljava/lang/Number;Ljava/lang/Number;)Ljava/lang/Number;", "differRateToShorter", "atLeast", "", "min", "(Ljava/lang/Comparable;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "atMost", "max", "differRateTo", "another", "inRange", "start", "end", "includeStart", "includeEnd", "autoSwap", "sameSignWith", "signFactor", "signOneFactor", "onyxsdk-base_release" })
public final class MathUtilsKt
{
    @NotNull
    public static final MathUtilsKt INSTANCE;
    public static final float ONE_HALF = 0.5f;
    public static final int DEGREE_0 = 0;
    public static final int DEGREE_30 = 30;
    public static final int DEGREE_45 = 45;
    public static final int DEGREE_60 = 60;
    public static final int DEGREE_90 = 90;
    public static final int DEGREE_180 = 180;
    public static final int DEGREE_360 = 360;
    public static final float FLOAT_PI = 3.1415927f;
    public static final float ARE_APPROXIMATELY_EQUAL_EPSILON = 0.001f;
    public static final float PI_FLOAT = 3.1415927f;
    public static final float PI_HALF_FLOAT = 1.5707964f;
    public static final float PI_TWICE_FLOAT = 6.2831855f;
    public static final float RAD_1_DEG = 0.017453292f;
    
    private MathUtilsKt() {
    }
    
    static {
        INSTANCE = new MathUtilsKt();
    }
    
    @NotNull
    public final <T extends Comparable<? super T>> T atMost(@NotNull final T $this$atMost, @NotNull final T max) {
        Intrinsics.checkNotNullParameter((Object)$this$atMost, "<this>");
        Intrinsics.checkNotNullParameter((Object)max, "max");
        return (T)RangesKt.coerceAtMost((Comparable)$this$atMost, (Comparable)max);
    }
    
    @NotNull
    public final <T extends Comparable<? super T>> T atLeast(@NotNull final T $this$atLeast, @NotNull final T min) {
        Intrinsics.checkNotNullParameter((Object)$this$atLeast, "<this>");
        Intrinsics.checkNotNullParameter((Object)min, "min");
        return (T)RangesKt.coerceAtLeast((Comparable)$this$atLeast, (Comparable)min);
    }
    
    public final int signFactor(@NotNull final Number $this$signFactor) {
        Intrinsics.checkNotNullParameter((Object)$this$signFactor, "<this>");
        if ($this$signFactor.doubleValue() > 0.0) {
            return 1;
        }
        if ($this$signFactor.doubleValue() < 0.0) {
            return -1;
        }
        return 0;
    }
    
    public final boolean sameSignWith(@NotNull final Number $this$sameSignWith, @NotNull final Number another) {
        Intrinsics.checkNotNullParameter((Object)$this$sameSignWith, "<this>");
        Intrinsics.checkNotNullParameter((Object)another, "another");
        return this.signFactor($this$sameSignWith) == this.signFactor(another);
    }
    
    public final int signOneFactor(@NotNull final Number $this$signOneFactor) {
        Intrinsics.checkNotNullParameter((Object)$this$signOneFactor, "<this>");
        Double value;
        if (!((value = Math.signum($this$signOneFactor.doubleValue())).doubleValue() == 0.0 ^ true)) {
            value = null;
        }
        return value == null ? 1 : value.intValue();
    }
    
    public final boolean areApproximatelyEqual(final float num1, final float num2, final float epsilon) {
        return Math.abs(num1 - num2) <= epsilon;
    }
    
    @NotNull
    public final <T extends Number> T average(@NotNull final T a, @NotNull final T b) {
        Intrinsics.checkNotNullParameter((Object)a, "a");
        Intrinsics.checkNotNullParameter((Object)b, "b");
        return (T)Double.valueOf((a.doubleValue() + b.doubleValue()) / 2);
    }
    
    public final float differRateTo(@NotNull final Number $this$differRateTo, @NotNull final Number another) {
        Intrinsics.checkNotNullParameter((Object)$this$differRateTo, "<this>");
        Intrinsics.checkNotNullParameter((Object)another, "another");
        return this.differRateToShorter($this$differRateTo, another);
    }
    
    public final float differRateToShorter(@NotNull final Number num1, @NotNull final Number num2) {
        Intrinsics.checkNotNullParameter((Object)num1, "num1");
        Intrinsics.checkNotNullParameter((Object)num2, "num2");
        try {
            final double abs = Math.abs(num1.doubleValue());
            final double abs2 = Math.abs(num2.doubleValue());
            final double v;
            if (!Double.isNaN(v = Math.abs(abs - abs2) / Math.min(abs, abs2)) && !Double.isInfinite(v)) {
                return (float)v;
            }
        }
        finally {
            return Float.MAX_VALUE;
        }
    }
    
    public final boolean inRange(@NotNull final Number $this$inRange, @NotNull final Number start, @NotNull final Number end, final boolean includeStart, final boolean includeEnd, final boolean autoSwap) {
        Intrinsics.checkNotNullParameter((Object)$this$inRange, "<this>");
        Intrinsics.checkNotNullParameter((Object)start, "start");
        Intrinsics.checkNotNullParameter((Object)end, "end");
        double doubleValue = start.doubleValue();
        double doubleValue2 = end.doubleValue();
        if (!autoSwap || doubleValue2 >= doubleValue) {
            final double n = doubleValue;
            doubleValue = doubleValue2;
            doubleValue2 = n;
        }
        final boolean b = includeStart ? ($this$inRange.doubleValue() >= doubleValue2) : ($this$inRange.doubleValue() > doubleValue2);
        final boolean b2 = includeEnd ? ($this$inRange.doubleValue() <= doubleValue) : ($this$inRange.doubleValue() < doubleValue);
        return b && b2;
    }
}
