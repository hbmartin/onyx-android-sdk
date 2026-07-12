// 
// 

package com.onyx.android.sdk.rx;

import android.content.Context;
import kotlin.jvm.internal.Intrinsics;
import com.onyx.android.sdk.common.request.WakeLockManager;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u0016\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0005¢\u0006\u0002\u0010\u0003J\b\u0010\u0004\u001a\u00020\u0005H\u0014J\b\u0010\u0006\u001a\u00020\u0005H\u0014¨\u0006\u0007" }, d2 = { "Lcom/onyx/android/sdk/rx/RxBaseGlobalWackLockAction;", "T", "Lcom/onyx/android/sdk/rx/RxBaseAction;", "()V", "acquireActionWakeLock", "", "releaseActionWakeLock", "onyxsdk-base_release" })
public abstract class RxBaseGlobalWackLockAction<T> extends RxBaseAction<T>
{
    @Override
    protected void acquireActionWakeLock() {
        if (!super.useWakelock) {
            return;
        }
        final WakeLockManager instance = WakeLockManager.INSTANCE;
        final Context appContext;
        Intrinsics.checkNotNullExpressionValue((Object)(appContext = RxBaseAction.getAppContext()), "getAppContext()");
        final String wakeLockTag = this.getWakeLockTag();
        final Context context = appContext;
        Intrinsics.checkNotNullExpressionValue((Object)wakeLockTag, "wakeLockTag");
        instance.acquireWakeLock(context, wakeLockTag);
    }
    
    @Override
    protected void releaseActionWakeLock() {
        if (!super.useWakelock) {
            return;
        }
        final WakeLockManager instance = WakeLockManager.INSTANCE;
        final String wakeLockTag = this.getWakeLockTag();
        Intrinsics.checkNotNullExpressionValue((Object)wakeLockTag, "wakeLockTag");
        instance.releaseWakeLock(wakeLockTag);
    }
}
