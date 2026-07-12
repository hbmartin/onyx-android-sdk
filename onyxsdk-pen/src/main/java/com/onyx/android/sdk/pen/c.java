/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 */
package com.onyx.android.sdk.pen;

import android.graphics.Bitmap;
import com.onyx.android.sdk.pen.NeoPencilPen;
import java.util.function.Function;

public final class c
implements Function {
    public final Bitmap a;
    public final int b;

    public /* synthetic */ c(Bitmap bitmap, int n2) {
        this.a = bitmap;
        this.b = n2;
    }

    public final Object apply(Object object) {
        return NeoPencilPen.Companion.g(this.a, this.b, (Integer)object);
    }
}
