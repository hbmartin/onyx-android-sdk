package com.onyx.android.sdk.pen;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lcom/onyx/android/sdk/pen/CharcoalNeoPenRender;", "Lcom/onyx/android/sdk/pen/NeoPenRender;", "neoPen", "Lcom/onyx/android/sdk/pen/NeoPen;", "(Lcom/onyx/android/sdk/pen/NeoPen;)V", "onyxsdk-pen_release"})
public final class CharcoalNeoPenRender extends NeoPenRender {
    public CharcoalNeoPenRender(@NotNull NeoPen neoPen) {
        super(neoPen);
        Intrinsics.checkNotNullParameter(neoPen, "neoPen");
    }
}

