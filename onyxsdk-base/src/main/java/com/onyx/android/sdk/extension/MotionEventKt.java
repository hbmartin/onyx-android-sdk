// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.extension;

import com.onyx.android.sdk.utils.Debug;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import android.view.MotionEvent;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\u001c\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0004\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0007\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\b\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\t\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\n\u001a\u00020\u0001*\u00020\u0002\u001a\u0012\u0010\u000b\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\f\u001a\u00020\r\u001a\n\u0010\u000e\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u000f\u001a\u00020\u0010*\u00020\u0002¨\u0006\u0011" }, d2 = { "isCancelAction", "", "Landroid/view/MotionEvent;", "isDownAction", "isEraserTouchType", "isFingerTouch", "isMouseTouchType", "isMultiTouch", "isPenTouchType", "isPointerDownAction", "isStylusTouchType", "isTouchType", "touchType", "", "isUpAction", "reportMotionEvent", "", "onyxsdk-base_release" })
public final class MotionEventKt
{
    public static final boolean isPenTouchType(@NotNull final MotionEvent $this$isPenTouchType) {
        Intrinsics.checkNotNullParameter((Object)$this$isPenTouchType, "<this>");
        return isTouchType($this$isPenTouchType, 2) || isTouchType($this$isPenTouchType, 4);
    }
    
    public static final boolean isStylusTouchType(@NotNull final MotionEvent $this$isStylusTouchType) {
        Intrinsics.checkNotNullParameter((Object)$this$isStylusTouchType, "<this>");
        return isTouchType($this$isStylusTouchType, 2);
    }
    
    public static final boolean isEraserTouchType(@NotNull final MotionEvent $this$isEraserTouchType) {
        Intrinsics.checkNotNullParameter((Object)$this$isEraserTouchType, "<this>");
        return isTouchType($this$isEraserTouchType, 4);
    }
    
    public static final boolean isMouseTouchType(@NotNull final MotionEvent $this$isMouseTouchType) {
        Intrinsics.checkNotNullParameter((Object)$this$isMouseTouchType, "<this>");
        return isTouchType($this$isMouseTouchType, 3);
    }
    
    public static final boolean isTouchType(@NotNull final MotionEvent $this$isTouchType, final int touchType) {
        Intrinsics.checkNotNullParameter((Object)$this$isTouchType, "<this>");
        return $this$isTouchType.getToolType($this$isTouchType.getActionIndex()) == touchType;
    }
    
    public static final boolean isFingerTouch(@NotNull final MotionEvent $this$isFingerTouch) {
        Intrinsics.checkNotNullParameter((Object)$this$isFingerTouch, "<this>");
        final int toolType;
        return (toolType = $this$isFingerTouch.getToolType($this$isFingerTouch.getActionIndex())) == 1 || toolType == 0;
    }
    
    public static final boolean isMultiTouch(@NotNull final MotionEvent $this$isMultiTouch) {
        Intrinsics.checkNotNullParameter((Object)$this$isMultiTouch, "<this>");
        return $this$isMultiTouch.getPointerCount() > 1;
    }
    
    public static final boolean isDownAction(@NotNull final MotionEvent $this$isDownAction) {
        Intrinsics.checkNotNullParameter((Object)$this$isDownAction, "<this>");
        return $this$isDownAction.getAction() == 0;
    }
    
    public static final boolean isPointerDownAction(@NotNull final MotionEvent $this$isPointerDownAction) {
        Intrinsics.checkNotNullParameter((Object)$this$isPointerDownAction, "<this>");
        return $this$isPointerDownAction.getActionMasked() == 5;
    }
    
    public static final boolean isUpAction(@NotNull final MotionEvent $this$isUpAction) {
        Intrinsics.checkNotNullParameter((Object)$this$isUpAction, "<this>");
        return $this$isUpAction.getAction() == 1;
    }
    
    public static final boolean isCancelAction(@NotNull final MotionEvent $this$isCancelAction) {
        Intrinsics.checkNotNullParameter((Object)$this$isCancelAction, "<this>");
        return $this$isCancelAction.getAction() == 3;
    }
    
    public static final void reportMotionEvent(@NotNull final MotionEvent $this$reportMotionEvent) {
        Intrinsics.checkNotNullParameter((Object)$this$reportMotionEvent, "<this>");
        if (!Debug.getDebug()) {
            return;
        }
        final int actionMasked;
        if ((actionMasked = $this$reportMotionEvent.getActionMasked()) == 0 || actionMasked == 1 || actionMasked == 3) {
            Debug.v((Class)$this$reportMotionEvent.getClass(), Intrinsics.stringPlus("onTouch, ", (Object)$this$reportMotionEvent), new Object[0]);
        }
    }
}
