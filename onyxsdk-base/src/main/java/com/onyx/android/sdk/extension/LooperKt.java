// 
// 

package com.onyx.android.sdk.extension;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import android.os.Looper;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000\f\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\u001a\u0006\u0010\u0000\u001a\u00020\u0001\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003" }, d2 = { "isMainThread", "", "Landroid/os/Looper;", "onyxsdk-base_release" })
public final class LooperKt
{
    private LooperKt() {
    }

    public static final boolean isMainThread(@NotNull final Looper $this$isMainThread) {
        Intrinsics.checkNotNullParameter((Object)$this$isMainThread, "<this>");
        return Intrinsics.areEqual((Object)$this$isMainThread, (Object)Looper.getMainLooper());
    }
    
    public static final boolean isMainThread() {
        final Looper myLooper;
        return (myLooper = Looper.myLooper()) != null && isMainThread(myLooper);
    }
}
