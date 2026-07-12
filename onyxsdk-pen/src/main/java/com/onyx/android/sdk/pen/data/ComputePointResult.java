/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  kotlin.Metadata
 *  org.jetbrains.annotations.Nullable
 */
package com.onyx.android.sdk.pen.data;

import android.graphics.Bitmap;
import com.onyx.android.sdk.pen.NeoRenderPoint;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e\u00a8\u0006\u000f"}, d2={"Lcom/onyx/android/sdk/pen/data/ComputePointResult;", "", "()V", "bitmap", "Landroid/graphics/Bitmap;", "getBitmap", "()Landroid/graphics/Bitmap;", "setBitmap", "(Landroid/graphics/Bitmap;)V", "neoRenderPoint", "Lcom/onyx/android/sdk/pen/NeoRenderPoint;", "getNeoRenderPoint", "()Lcom/onyx/android/sdk/pen/NeoRenderPoint;", "setNeoRenderPoint", "(Lcom/onyx/android/sdk/pen/NeoRenderPoint;)V", "onyxsdk-pen_release"})
public final class ComputePointResult {
    @Nullable
    private NeoRenderPoint a;
    @Nullable
    private Bitmap b;

    @Nullable
    public final NeoRenderPoint getNeoRenderPoint() {
        return this.a;
    }

    /*
     * WARNING - void declaration
     */
    public final void setNeoRenderPoint(@Nullable NeoRenderPoint neoRenderPoint) {
        void var1_1;
        this.a = var1_1;
    }

    @Nullable
    public final Bitmap getBitmap() {
        return this.b;
    }

    /*
     * WARNING - void declaration
     */
    public final void setBitmap(@Nullable Bitmap bitmap) {
        void var1_1;
        this.b = var1_1;
    }
}

