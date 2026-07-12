// 
// 

package com.onyx.android.sdk.data.render;

import org.jetbrains.annotations.Nullable;
import android.graphics.Rect;
import android.graphics.Canvas;
import kotlin.jvm.internal.Intrinsics;
import android.graphics.RenderNode;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\n\u0010\t\u001a\u0004\u0018\u00010\nH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0016J\u0010\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\nH\u0016J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\nH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\b\u0010\u0014\u001a\u00020\u0013H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0011H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u0010\u0010\u001a\u001a\u00020\f2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016J\u0010\u0010\u001d\u001a\u00020\f2\u0006\u0010\u001e\u001a\u00020\u0013H\u0016J\u0010\u0010\u001f\u001a\u00020\f2\u0006\u0010 \u001a\u00020\u0013H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006!" }, d2 = { "Lcom/onyx/android/sdk/data/render/GraphicsRenderNode;", "Lcom/onyx/android/sdk/data/render/BaseRenderNode;", "name", "", "(Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "renderNode", "Landroid/graphics/RenderNode;", "beginRecording", "Landroid/graphics/Canvas;", "discardDisplayList", "", "drawRenderNode", "canvas", "endRecording", "getHeight", "", "getTranslationX", "", "getTranslationY", "getUniqueId", "", "getWidth", "hasDisplayList", "", "setPosition", "rect", "Landroid/graphics/Rect;", "setTranslationX", "x", "setTranslationY", "y", "onyxsdk-base_release" })
public final class GraphicsRenderNode implements BaseRenderNode
{
    @NotNull
    private final String a;
    @NotNull
    private RenderNode b;
    
    public GraphicsRenderNode(@NotNull final String name) {
        Intrinsics.checkNotNullParameter((Object)name, "name");
        this.a = name;
        this.b = new RenderNode(name);
    }
    
    @NotNull
    public final String getName() {
        return this.a;
    }
    
    @Override
    public boolean hasDisplayList() {
        return this.b.hasDisplayList();
    }
    
    @Override
    public long getUniqueId() {
        return this.b.getUniqueId();
    }
    
    @Override
    public int getWidth() {
        return this.b.getWidth();
    }
    
    @Override
    public int getHeight() {
        return this.b.getHeight();
    }
    
    @Override
    public float getTranslationX() {
        return this.b.getTranslationX();
    }
    
    @Override
    public float getTranslationY() {
        return this.b.getTranslationY();
    }
    
    @Override
    public void setTranslationX(final float x) {
        this.b.setTranslationX(x);
    }
    
    @Override
    public void setTranslationY(final float y) {
        this.b.setTranslationY(y);
    }
    
    @Override
    public void discardDisplayList() {
        this.b.discardDisplayList();
    }
    
    @Override
    public void drawRenderNode(@NotNull final Canvas canvas) {
        Intrinsics.checkNotNullParameter((Object)canvas, "canvas");
        canvas.drawRenderNode(this.b);
    }
    
    @Override
    public void setPosition(@NotNull final Rect rect) {
        Intrinsics.checkNotNullParameter((Object)rect, "rect");
        this.b.setPosition(rect);
    }
    
    @Nullable
    @Override
    public Canvas beginRecording() {
        return (Canvas)this.b.beginRecording();
    }
    
    @Override
    public void endRecording(@NotNull final Canvas canvas) {
        Intrinsics.checkNotNullParameter((Object)canvas, "canvas");
        this.b.endRecording();
    }
}
