package com.onyx.android.sdk.pen;

import android.graphics.Bitmap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/PenTextureInk.class */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000b¨\u0006\r"}, d2 = {"Lcom/onyx/android/sdk/pen/PenTextureInk;", "", "x", "", "y", "bitmap", "Landroid/graphics/Bitmap;", "(FFLandroid/graphics/Bitmap;)V", "getBitmap", "()Landroid/graphics/Bitmap;", "getX", "()F", "getY", "sdk-pen_release"}, k = 1, mv = {1, 6, 0}, xi = 48)
public final class PenTextureInk {
    private final float a;
    private final float b;

    @NotNull
    private final Bitmap c;

    public PenTextureInk(float f, float f2, @NotNull Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        this.a = f;
        this.b = f2;
        this.c = bitmap;
    }

    @NotNull
    public final Bitmap getBitmap() {
        return this.c;
    }

    public final float getX() {
        return this.a;
    }

    public final float getY() {
        return this.b;
    }
}

