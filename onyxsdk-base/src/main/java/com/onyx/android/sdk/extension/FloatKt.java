// 
// 

package com.onyx.android.sdk.extension;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import kotlin.math.MathKt;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\u0016\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0004\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0001\u001a\n\u0010\u0002\u001a\u00020\u0003*\u00020\u0001\u001a\n\u0010\u0004\u001a\u00020\u0001*\u00020\u0001\u001a\u0012\u0010\u0005\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0003\u001a\n\u0010\u0007\u001a\u00020\u0001*\u00020\u0001\u001a\u0012\u0010\b\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\t\u001a\u00020\n¨\u0006\u000b" }, d2 = { "ceil", "", "ceilToInt", "", "floor", "keepFloat", "bits", "roundToIntFloat", "safeDivide", "divisor", "", "onyxsdk-base_release" })
public final class FloatKt
{
    private FloatKt() {
    }

    public static final float roundToIntFloat(final float $this$roundToIntFloat) {
        return (float)MathKt.roundToInt($this$roundToIntFloat);
    }
    
    public static final float ceil(final float $this$ceil) {
        return (float)Math.ceil($this$ceil);
    }
    
    public static final float floor(final float $this$floor) {
        return (float)Math.floor($this$floor);
    }
    
    public static final int ceilToInt(final float $this$ceilToInt) {
        return (int)(float)Math.ceil($this$ceilToInt);
    }
    
    public static final float keepFloat(float $this$keepFloat, final int bits) {
        return MathKt.roundToInt($this$keepFloat * ($this$keepFloat = (float)Math.pow(10.0f, bits))) / $this$keepFloat;
    }
    
    public static final float safeDivide(float $this$safeDivide, @NotNull final Number divisor) {
        Intrinsics.checkNotNullParameter((Object)divisor, "divisor");
        final float floatValue;
        if ((floatValue = divisor.floatValue()) != 0.0f) {
            $this$safeDivide /= floatValue;
        }
        return $this$safeDivide;
    }
}
