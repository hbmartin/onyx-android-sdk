// 
// 

package com.onyx.android.sdk.data.note.line;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\u0012\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\u001a\u0014\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004¨\u0006\u0005" }, d2 = { "isHorizontalLine", "", "Lcom/onyx/android/sdk/data/note/line/Line;", "customTolerance", "", "onyxsdk-base_release" })
public final class LineKt
{
    private LineKt() {
    }

    public static final boolean isHorizontalLine(@NotNull final Line $this$isHorizontalLine, final float customTolerance) {
        Intrinsics.checkNotNullParameter((Object)$this$isHorizontalLine, "<this>");
        final float abs = Math.abs($this$isHorizontalLine.getEndY() - $this$isHorizontalLine.getStartY());
        final float abs2 = Math.abs($this$isHorizontalLine.getEndX() - $this$isHorizontalLine.getStartX());
        return abs <= customTolerance && abs2 > 0.0f;
    }

    public static /* synthetic */ boolean isHorizontalLine$default(final Line line, float customTolerance, final int i, final Object obj) {
        if ((i & 1) != 0) {
            customTolerance = 1.0f;
        }
        return isHorizontalLine(line, customTolerance);
    }
}
