// 
// 

package com.onyx.android.sdk.utils;

import kotlin.ranges.RangesKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u00020\u00042\n\u0010\u0005\u001a\u0006\u0012\u0002\b\u00030\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b¨\u0006\t" }, d2 = { "Lcom/onyx/android/sdk/utils/CallStackUtils;", "", "()V", "printCallStack", "", "cls", "Ljava/lang/Class;", "maxDepth", "", "onyxsdk-base_release" })
public final class CallStackUtils
{
    @NotNull
    public static final CallStackUtils INSTANCE;
    
    private CallStackUtils() {
    }
    
    static {
        INSTANCE = new CallStackUtils();
    }
    
    public final void printCallStack(@NotNull final Class<?> cls, int maxDepth) {
        Intrinsics.checkNotNullParameter((Object)cls, "cls");
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        final StringBuilder sb;
        (sb = new StringBuilder()).append("current function call path\uff08max depth\uff1a" + maxDepth + "\uff09\uff1a\n");
        int i;
        StringBuilder sb2;
        StackTraceElement[] array;
        int n;
        int n2;
        StackTraceElement stackTraceElement;
        for (i = 2, maxDepth = RangesKt.coerceAtMost(stackTrace.length, maxDepth + 2); i < maxDepth; i = n2) {
            sb2 = sb;
            array = stackTrace;
            n = i;
            n2 = n + 1;
            stackTraceElement = array[n];
            sb2.append(new StringBuilder().append('#').append(i - 2).append(": ").append((Object)stackTraceElement.getClassName()).append('.').append((Object)stackTraceElement.getMethodName()).append('(').append((Object)stackTraceElement.getFileName()).append(':').append(stackTraceElement.getLineNumber()).append(")\n").toString());
        }
        Debug.i((Class)cls, sb.toString(), new Object[0]);
    }

    public static /* synthetic */ void printCallStack$default(final CallStackUtils callStackUtils, final Class cls, int maxDepth, final int i, final Object obj) {
        if ((i & 2) != 0) {
            maxDepth = 5;
        }
        callStackUtils.printCallStack(cls, maxDepth);
    }
}
