// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import java.util.Iterator;
import io.reactivex.functions.Consumer;
import java.util.Map;

public class ThreadUtils
{
    public static void mySleep(final int ms) {
        final long millis = ms;
        try {
            Thread.sleep(millis, 0);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void printAllThread() {
        final Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        Debug.i((Class)ThreadUtils.class, "----------------------", new Object[0]);
        Debug.i((Class)ThreadUtils.class, "thread count\uff1a" + allStackTraces.size(), new Object[0]);
        for (final Map.Entry entry : allStackTraces.entrySet()) {
            final Thread thread = (Thread)entry.getKey();
            Debug.i((Class)ThreadUtils.class, "thread name\uff1a" + thread.getName() + ",id=" + thread.getId() + ",state=" + thread.getState(), new Object[0]);
            final StringBuilder obj = new StringBuilder();
            ArraysUtils.foreach((Object[])entry.getValue(), (io.reactivex.functions.Consumer<Object>)(item -> obj.append(item).append("\r\n")));
            Debug.i((Class)ThreadUtils.class, "thread stackElements\uff1a" + (Object)obj, new Object[0]);
        }
        Debug.i((Class)ThreadUtils.class, "----------------------", new Object[0]);
    }
}
