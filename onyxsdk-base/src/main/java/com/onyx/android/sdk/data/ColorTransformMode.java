// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.data;

import android.graphics.ColorMatrixColorFilter;
import android.graphics.ColorFilter;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0017\u0010\u0006\u001a\u0004\u0018\u00010\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\tR\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000¨\u0006\n" }, d2 = { "Lcom/onyx/android/sdk/data/ColorTransformMode;", "", "()V", "inverseNoWithAlpha", "", "origin", "loadColorFilter", "Landroid/graphics/ColorFilter;", "colorTransformMode", "(Ljava/lang/Integer;)Landroid/graphics/ColorFilter;", "onyxsdk-base_release" })
public final class ColorTransformMode
{
    @NotNull
    public static final ColorTransformMode INSTANCE;
    public static final int origin = 0;
    public static final int inverseNoWithAlpha = 1;
    
    private ColorTransformMode() {
    }
    
    static {
        INSTANCE = new ColorTransformMode();
    }
    
    @Nullable
    public final ColorFilter loadColorFilter(@Nullable final Integer colorTransformMode) {
        if (colorTransformMode == null) {
            return null;
        }
        if (colorTransformMode.intValue() != inverseNoWithAlpha) {
            return null;
        }
        return new ColorMatrixColorFilter(new float[] {
            -1.0f, 0.0f, 0.0f, 0.0f, 255.0f,
            0.0f, -1.0f, 0.0f, 0.0f, 255.0f,
            0.0f, 0.0f, -1.0f, 0.0f, 255.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f
        });
    }
}
