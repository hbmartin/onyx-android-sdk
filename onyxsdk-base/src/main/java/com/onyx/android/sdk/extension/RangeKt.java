// 
// 

package com.onyx.android.sdk.extension;

import com.onyx.android.sdk.utils.MathUtils;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.apache.commons.lang3.Range;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\u0010\n\u0002\b\u0003\n\u0002\u0010\u000f\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a5\u0010\u0000\u001a\n \u0002*\u0004\u0018\u0001H\u0001H\u0001\"\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u0003*\b\u0012\u0004\u0012\u0002H\u00010\u00042\u0006\u0010\u0005\u001a\u0002H\u0001¢\u0006\u0002\u0010\u0006¨\u0006\u0007" }, d2 = { "clamp", "T", "kotlin.jvm.PlatformType", "", "Lorg/apache/commons/lang3/Range;", "value", "(Lorg/apache/commons/lang3/Range;Ljava/lang/Comparable;)Ljava/lang/Comparable;", "onyxsdk-base_release" })
public final class RangeKt
{
    private RangeKt() {
    }

    public static final <T extends Comparable<? super T>> T clamp(@NotNull final Range<T> $this$clamp, @NotNull final T value) {
        Intrinsics.checkNotNullParameter((Object)$this$clamp, "<this>");
        Intrinsics.checkNotNullParameter((Object)value, "value");
        if (value.compareTo($this$clamp.getMinimum()) < 0) {
            return $this$clamp.getMinimum();
        }
        if (value.compareTo($this$clamp.getMaximum()) > 0) {
            return $this$clamp.getMaximum();
        }
        return value;
    }
}
