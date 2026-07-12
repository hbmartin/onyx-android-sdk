/*
 * Decompiled with CFR 0.152.
 */
package com.onyx.android.sdk.pen;

import com.onyx.android.sdk.pen.NeoPencilPen;
import com.onyx.android.sdk.pen.NeoPencilPen.Companion.BrushMaskGenerator;
import com.onyx.android.sdk.pen.NeoPencilPen.Companion.MaskKey;
import java.util.function.Function;

public final class d
implements Function {
    public final MaskKey a;

    public /* synthetic */ d(MaskKey maskKey) {
        this.a = maskKey;
    }

    public final Object apply(Object object) {
        return BrushMaskGenerator.b(this.a, (MaskKey)object);
    }
}
