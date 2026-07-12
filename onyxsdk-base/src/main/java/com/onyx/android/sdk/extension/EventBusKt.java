// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.extension;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.greenrobot.eventbus.EventBus;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\u0014\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\u001a\u0012\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u0012\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\u0006" }, d2 = { "ensureRegister", "", "Lorg/greenrobot/eventbus/EventBus;", "subscriber", "", "ensureUnRegister", "onyxsdk-base_release" })
public final class EventBusKt
{
    public static final void ensureRegister(@NotNull final EventBus $this$ensureRegister, @NotNull final Object subscriber) {
        Intrinsics.checkNotNullParameter((Object)$this$ensureRegister, "<this>");
        Intrinsics.checkNotNullParameter(subscriber, "subscriber");
        if (!$this$ensureRegister.isRegistered(subscriber)) {
            $this$ensureRegister.register(subscriber);
        }
    }
    
    public static final void ensureUnRegister(@NotNull final EventBus $this$ensureUnRegister, @NotNull final Object subscriber) {
        Intrinsics.checkNotNullParameter((Object)$this$ensureUnRegister, "<this>");
        Intrinsics.checkNotNullParameter(subscriber, "subscriber");
        if ($this$ensureUnRegister.isRegistered(subscriber)) {
            $this$ensureUnRegister.unregister(subscriber);
        }
    }
}
