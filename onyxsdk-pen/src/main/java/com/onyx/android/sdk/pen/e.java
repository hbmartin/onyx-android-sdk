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

public final class e
implements Function {
    public final Bitmap a;

    public /* synthetic */ e(Bitmap bitmap) {
        this.a = bitmap;
    }

    public final Object apply(Object object) {
        return NeoPencilPen.Companion.h(this.a, (Integer)object);
    }
}
