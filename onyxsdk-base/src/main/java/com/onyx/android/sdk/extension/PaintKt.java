// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.extension;

import android.graphics.Paint.Join;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import android.graphics.Paint;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0001\u001a\n\u0010\u0002\u001a\u00020\u0001*\u00020\u0001¨\u0006\u0003" }, d2 = { "copy", "Landroid/graphics/Paint;", "updateShapeDrawPaintStyle", "onyxsdk-base_release" })
public final class PaintKt
{
    private PaintKt() {
    }

    @NotNull
    public static final Paint updateShapeDrawPaintStyle(@NotNull final Paint $this$updateShapeDrawPaintStyle) {
        Intrinsics.checkNotNullParameter((Object)$this$updateShapeDrawPaintStyle, "<this>");
        $this$updateShapeDrawPaintStyle.setColor(-16777216);
        $this$updateShapeDrawPaintStyle.setStyle(Paint.Style.STROKE);
        $this$updateShapeDrawPaintStyle.setAntiAlias(true);
        $this$updateShapeDrawPaintStyle.setDither(true);
        $this$updateShapeDrawPaintStyle.setStrokeCap(Paint.Cap.ROUND);
        $this$updateShapeDrawPaintStyle.setStrokeJoin(Paint.Join.ROUND);
        $this$updateShapeDrawPaintStyle.setStrokeMiter(4.0f);
        return $this$updateShapeDrawPaintStyle;
    }
    
    @NotNull
    public static final Paint copy(@NotNull final Paint $this$copy) {
        Intrinsics.checkNotNullParameter((Object)$this$copy, "<this>");
        return new Paint($this$copy);
    }
}
