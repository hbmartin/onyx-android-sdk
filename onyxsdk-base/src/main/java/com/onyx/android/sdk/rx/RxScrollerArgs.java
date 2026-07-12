// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.rx;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import android.graphics.RectF;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u00011B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010/\u001a\u000200R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001a\u0010\u0012\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000eR\u001a\u0010\u0015\u001a\u00020\u0016X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u001bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010 \u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\f\"\u0004\b\"\u0010\u000eR\u001a\u0010#\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\f\"\u0004\b%\u0010\u000eR\u001a\u0010&\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b'\u0010\f\"\u0004\b(\u0010\u000eR\u001a\u0010)\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\f\"\u0004\b+\u0010\u000eR\u001a\u0010,\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\f\"\u0004\b.\u0010\u000e¨\u00062" }, d2 = { "Lcom/onyx/android/sdk/rx/RxScrollerArgs;", "", "()V", "flingActionType", "Lcom/onyx/android/sdk/rx/RxScrollerArgs$FlingActionType;", "getFlingActionType", "()Lcom/onyx/android/sdk/rx/RxScrollerArgs$FlingActionType;", "setFlingActionType", "(Lcom/onyx/android/sdk/rx/RxScrollerArgs$FlingActionType;)V", "frictionFactor", "", "getFrictionFactor", "()F", "setFrictionFactor", "(F)V", "frictionX", "getFrictionX", "setFrictionX", "frictionY", "getFrictionY", "setFrictionY", "isEnabledAbortFlingAnimation", "", "()Z", "setEnabledAbortFlingAnimation", "(Z)V", "limitRect", "Landroid/graphics/RectF;", "getLimitRect", "()Landroid/graphics/RectF;", "setLimitRect", "(Landroid/graphics/RectF;)V", "minimumVisibleChange", "getMinimumVisibleChange", "setMinimumVisibleChange", "startX", "getStartX", "setStartX", "startY", "getStartY", "setStartY", "velocityX", "getVelocityX", "setVelocityX", "velocityY", "getVelocityY", "setVelocityY", "getDebugString", "", "FlingActionType", "onyxsdk-base_release" })
public final class RxScrollerArgs
{
    private float a;
    private float b;
    private float c;
    private float d;
    @NotNull
    private RectF e;
    private float f;
    private float g;
    private float h;
    private float i;
    private boolean j;
    @NotNull
    private FlingActionType k;
    
    public RxScrollerArgs() {
        this.e = new RectF();
        this.f = 0.5f;
        this.g = 0.5f;
        this.h = 1.0f;
        this.i = 1.0f;
        this.j = true;
        this.k = FlingActionType.CONTINUOUS_SCROLL;
    }
    
    public final float getStartX() {
        return this.a;
    }
    
    public final void setStartX(final float value) {
        this.a = value;
    }
    
    public final float getStartY() {
        return this.b;
    }
    
    public final void setStartY(final float value) {
        this.b = value;
    }
    
    public final float getVelocityX() {
        return this.c;
    }
    
    public final void setVelocityX(final float value) {
        this.c = value;
    }
    
    public final float getVelocityY() {
        return this.d;
    }
    
    public final void setVelocityY(final float value) {
        this.d = value;
    }
    
    @NotNull
    public final RectF getLimitRect() {
        return this.e;
    }
    
    public final void setLimitRect(@NotNull final RectF value) {
        Intrinsics.checkNotNullParameter((Object)value, "<set-?>");
        this.e = value;
    }
    
    public final float getFrictionX() {
        return this.f;
    }
    
    public final void setFrictionX(final float value) {
        this.f = value;
    }
    
    public final float getFrictionY() {
        return this.g;
    }
    
    public final void setFrictionY(final float value) {
        this.g = value;
    }
    
    public final float getMinimumVisibleChange() {
        return this.h;
    }
    
    public final void setMinimumVisibleChange(final float value) {
        this.h = value;
    }
    
    public final float getFrictionFactor() {
        return this.i;
    }
    
    public final void setFrictionFactor(final float value) {
        this.i = value;
    }
    
    public final boolean isEnabledAbortFlingAnimation() {
        return this.j;
    }
    
    public final void setEnabledAbortFlingAnimation(final boolean value) {
        this.j = value;
    }
    
    @NotNull
    public final FlingActionType getFlingActionType() {
        return this.k;
    }
    
    public final void setFlingActionType(@NotNull final FlingActionType value) {
        Intrinsics.checkNotNullParameter((Object)value, "<set-?>");
        this.k = value;
    }
    
    @NotNull
    public final String getDebugString() {
        return "RxScrollerArgs(\nstartX=" + this.a + ", startY=" + this.b + "\n, velocityX=" + this.c + ", velocityY=" + this.d + "\n, limitRect=" + this.e + "\n, isEnabledAbortFlingAnimation=" + this.j + "\n, flingActionType = " + this.k + "\n, frictionX=" + this.f + ", frictionY=" + this.g + ')';
    }
    
    @Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0007\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006j\u0002\b\u0007¨\u0006\b" }, d2 = { "Lcom/onyx/android/sdk/rx/RxScrollerArgs$FlingActionType;", "", "(Ljava/lang/String;I)V", "CONTINUOUS_SCROLL", "NEXT_SCREEN", "PREV_SCREEN", "REBOUND_CURRENT_SCREEN", "CONTINUOUS_SCROLL_IN_CURRENT_SCREEN", "onyxsdk-base_release" })
    public enum FlingActionType
    {
        CONTINUOUS_SCROLL, 
        NEXT_SCREEN, 
        PREV_SCREEN, 
        REBOUND_CURRENT_SCREEN, 
        CONTINUOUS_SCROLL_IN_CURRENT_SCREEN;
    }
}
