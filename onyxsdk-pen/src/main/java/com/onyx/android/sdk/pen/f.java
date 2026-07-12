/*
 * Decompiled with CFR 0.152.
 */
package com.onyx.android.sdk.pen;

import com.onyx.android.sdk.pen.PenBrushResult;
import java.util.function.Function;

public final class f
implements Function {
    public final int a;
    public final int b;

    public /* synthetic */ f(int n2, int n3) {
        this.a = n2;
        this.b = n3;
    }

    public final Object apply(Object object) {
        return PenBrushResult.d(this.a, this.b, (Integer)object);
    }
}
