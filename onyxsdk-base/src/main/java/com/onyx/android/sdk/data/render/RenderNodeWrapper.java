// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.data.render;

import org.jetbrains.annotations.Nullable;
import android.graphics.Rect;
import android.graphics.Canvas;
import com.onyx.android.sdk.api.utils.CompatibilityUtil;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000L\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\t\u001a\u0004\u0018\u00010\nJ\u0006\u0010\u000b\u001a\u00020\fJ\u000e\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\nJ\u000e\u0010\u000f\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\nJ\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\u0012\u001a\u00020\u0013J\u0006\u0010\u0014\u001a\u00020\u0013J\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\u0011J\u0006\u0010\u0018\u001a\u00020\u0019J\u000e\u0010\u001a\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001cJ\u000e\u0010\u001d\u001a\u00020\f2\u0006\u0010\u001e\u001a\u00020\u0013J\u000e\u0010\u001f\u001a\u00020\f2\u0006\u0010 \u001a\u00020\u0013R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!" }, d2 = { "Lcom/onyx/android/sdk/data/render/RenderNodeWrapper;", "", "name", "", "(Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "renderNode", "Lcom/onyx/android/sdk/data/render/BaseRenderNode;", "beginRecording", "Landroid/graphics/Canvas;", "discardDisplayList", "", "drawRenderNode", "canvas", "endRecording", "getHeight", "", "getTranslationX", "", "getTranslationY", "getUniqueId", "", "getWidth", "hasDisplayList", "", "setPosition", "rect", "Landroid/graphics/Rect;", "setTranslationX", "x", "setTranslationY", "y", "onyxsdk-base_release" })
public final class RenderNodeWrapper
{
    @NotNull
    private final String a;
    @NotNull
    private BaseRenderNode b;
    
    public RenderNodeWrapper(@NotNull final String name) {
        Intrinsics.checkNotNullParameter((Object)name, "name");
        this.a = name;
        BaseRenderNode b;
        if (CompatibilityUtil.isApiLevelAbove(28)) {
            b = new GraphicsRenderNode(name);
        }
        else {
            b = new ViewRenderNode(name);
        }
        this.b = b;
    }
    
    @NotNull
    public final String getName() {
        return this.a;
    }
    
    public final boolean hasDisplayList() {
        return this.b.hasDisplayList();
    }
    
    public final long getUniqueId() {
        return this.b.getUniqueId();
    }
    
    public final int getWidth() {
        return this.b.getWidth();
    }
    
    public final int getHeight() {
        return this.b.getHeight();
    }
    
    public final float getTranslationX() {
        return this.b.getTranslationX();
    }
    
    public final float getTranslationY() {
        return this.b.getTranslationY();
    }
    
    public final void setTranslationX(final float x) {
        this.b.setTranslationX(x);
    }
    
    public final void setTranslationY(final float y) {
        this.b.setTranslationY(y);
    }
    
    public final void discardDisplayList() {
        this.b.discardDisplayList();
    }
    
    public final void drawRenderNode(@NotNull final Canvas canvas) {
        Intrinsics.checkNotNullParameter((Object)canvas, "canvas");
        this.b.drawRenderNode(canvas);
    }
    
    public final void setPosition(@NotNull final Rect rect) {
        Intrinsics.checkNotNullParameter((Object)rect, "rect");
        this.b.setPosition(rect);
    }
    
    @Nullable
    public final Canvas beginRecording() {
        return this.b.beginRecording();
    }
    
    public final void endRecording(@NotNull final Canvas canvas) {
        Intrinsics.checkNotNullParameter((Object)canvas, "canvas");
        this.b.endRecording(canvas);
    }
}
