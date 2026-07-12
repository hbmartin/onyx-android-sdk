/*
 * Decompiled with CFR 0.152.
 */
package com.onyx.android.sdk.pen;

import com.onyx.android.sdk.pen.NeoPencilPen;
import java.util.function.Function;

public final class b
implements Function {
    public static final b a = new b();

    private /* synthetic */ b() {
    }

    public final Object apply(Object object) {
        return NeoPencilPen.Companion.f((Integer)object);
    }
}
