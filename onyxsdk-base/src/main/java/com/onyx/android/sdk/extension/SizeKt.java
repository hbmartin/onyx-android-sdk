// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.extension;

import android.graphics.Rect;
import kotlin.jvm.internal.Intrinsics;
import android.graphics.RectF;
import org.jetbrains.annotations.NotNull;
import android.util.Size;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0004*\u00020\u0002¨\u0006\u0005" }, d2 = { "toRect", "Landroid/graphics/Rect;", "Landroid/util/Size;", "toRectF", "Landroid/graphics/RectF;", "onyxsdk-base_release" })
public final class SizeKt
{
    private SizeKt() {
    }

    @NotNull
    public static final RectF toRectF(@NotNull final Size $this$toRectF) {
        Intrinsics.checkNotNullParameter((Object)$this$toRectF, "<this>");
        return new RectF(0.0f, 0.0f, (float)$this$toRectF.getWidth(), (float)$this$toRectF.getHeight());
    }
    
    @NotNull
    public static final Rect toRect(@NotNull final Size $this$toRect) {
        Intrinsics.checkNotNullParameter((Object)$this$toRect, "<this>");
        return new Rect(0, 0, $this$toRect.getWidth(), $this$toRect.getHeight());
    }
}
