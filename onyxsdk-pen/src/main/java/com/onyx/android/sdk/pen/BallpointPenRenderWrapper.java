/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.onyx.android.sdk.pen.NeoBallpointInkPen
 *  com.onyx.android.sdk.pen.NeoPen
 *  com.onyx.android.sdk.pen.NeoPenConfig
 *  com.onyx.android.sdk.utils.Colors
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.onyx.android.sdk.pen;

import com.onyx.android.sdk.pen.NeoBallpointInkPen;
import com.onyx.android.sdk.pen.NeoPen;
import com.onyx.android.sdk.pen.NeoPenConfig;
import com.onyx.android.sdk.pen.NeoPenRenderWrapper;
import com.onyx.android.sdk.utils.Colors;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0017\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\b"}, d2={"Lcom/onyx/android/sdk/pen/BallpointPenRenderWrapper;", "Lcom/onyx/android/sdk/pen/NeoPenRenderWrapper;", "neoPen", "Lcom/onyx/android/sdk/pen/NeoPen;", "segment", "", "(Lcom/onyx/android/sdk/pen/NeoPen;Z)V", "Companion", "onyxsdk-pen_release"})
public final class BallpointPenRenderWrapper
extends NeoPenRenderWrapper {
    @NotNull
    public static final Companion Companion = new Companion(null);

    /*
     * WARNING - void declaration
     */
    private BallpointPenRenderWrapper(NeoPen neoPen, boolean segment) {
        super((NeoPen)var1_1, (boolean)var2_2);
        void var2_2;
        void var1_1;
    }

    /*
     * WARNING - void declaration
     */
    public /* synthetic */ BallpointPenRenderWrapper(NeoPen neoPen, boolean segment, DefaultConstructorMarker $constructor_marker) {
        this((NeoPen)var1_1, (boolean)var2_2);
        void var2_2;
        void var1_1;
    }

    @Metadata(mv={1, 6, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2={"Lcom/onyx/android/sdk/pen/BallpointPenRenderWrapper$Companion;", "", "()V", "create", "Lcom/onyx/android/sdk/pen/BallpointPenRenderWrapper;", "penConfig", "Lcom/onyx/android/sdk/pen/NeoPenConfig;", "onyxsdk-pen_release"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        /*
         * WARNING - void declaration
         */
        @Nullable
        public final BallpointPenRenderWrapper create(@NotNull NeoPenConfig penConfig) {
            void var1_1;
            Intrinsics.checkNotNullParameter((Object)penConfig, (String)"penConfig");
            Companion companion = NeoBallpointInkPen.Companion.create((NeoPenConfig)var1_1);
            if (companion == null) {
                return null;
            }
            boolean bl = Colors.INSTANCE.isColorOpaque(var1_1.color);
            return new BallpointPenRenderWrapper((NeoPen)companion, bl, null);
        }
    }
}

