// 
// 

package com.onyx.android.sdk.common.request;

import java.util.Iterator;
import com.onyx.android.sdk.utils.Debug;
import java.util.Map;
import java.util.function.Function;
import android.content.Context;
import kotlin.jvm.internal.Intrinsics;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0005J\u0006\u0010\f\u001a\u00020\bJ\u000e\u0010\r\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u0005R\u001a\u0010\u0003\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e" }, d2 = { "Lcom/onyx/android/sdk/common/request/WakeLockManager;", "", "()V", "wakeLockHolderMap", "Ljava/util/concurrent/ConcurrentHashMap;", "", "Lcom/onyx/android/sdk/common/request/WakeLockHolder;", "acquireWakeLock", "", "context", "Landroid/content/Context;", "tag", "print", "releaseWakeLock", "onyxsdk-base_release" })
public final class WakeLockManager
{
    @NotNull
    public static final WakeLockManager INSTANCE;
    @NotNull
    private static final ConcurrentHashMap<String, WakeLockHolder> a;
    
    private WakeLockManager() {
    }
    
    private static final WakeLockHolder a(final String it) {
        Intrinsics.checkNotNullParameter((Object)it, "it");
        return new WakeLockHolder();
    }
    
    static {
        INSTANCE = new WakeLockManager();
        a = new ConcurrentHashMap<String, WakeLockHolder>();
    }
    
    public final void acquireWakeLock(@NotNull final Context context, @NotNull final String tag) {
        Intrinsics.checkNotNullParameter((Object)context, "context");
        Intrinsics.checkNotNullParameter((Object)tag, "tag");
        final WakeLockHolder computeIfAbsent = WakeLockManager.a.computeIfAbsent(tag, WakeLockManager::a);
        Intrinsics.checkNotNullExpressionValue((Object)computeIfAbsent, "wakeLockHolderMap.comput\u2026tag) { WakeLockHolder() }");
        computeIfAbsent.acquireWakeLock(context, tag);
    }
    
    public final void releaseWakeLock(@NotNull final String tag) {
        Intrinsics.checkNotNullParameter((Object)tag, "tag");
        final ConcurrentHashMap<String, WakeLockHolder> a;
        final WakeLockHolder wakeLockHolder;
        if ((wakeLockHolder = (a = WakeLockManager.a).get(tag)) != null && wakeLockHolder.releaseWhenNoneRef()) {
            a.remove(tag);
        }
    }
    
    public final void print() {
        final ConcurrentHashMap<String, WakeLockHolder> a;
        if ((a = WakeLockManager.a).isEmpty()) {
            return;
        }
        final Iterator<Map.Entry<String, WakeLockHolder>> iterator = a.entrySet().iterator();
        while (iterator.hasNext()) {
            Debug.e((Class)WakeLockManager.INSTANCE.getClass(), "tag: " + iterator.next().getKey() + " not release", new Object[0]);
        }
    }
}
