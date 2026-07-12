package com.onyx.android.sdk.common.request;

import android.content.Context;
import android.os.PowerManager;
import android.util.Log;
import com.onyx.android.sdk.device.Device;
import java.util.concurrent.atomic.AtomicInteger;

public class WakeLockHolder {
    private static final String d = "onyx-framework";
    public static final int FULL_FLAGS = 26;
    public static final int ON_AFTER_RELEASE = 536870912;
    public static final int WAKEUP_FLAGS = 805306394;
    private volatile PowerManager.WakeLock a;
    private AtomicInteger b;
    private volatile boolean c;

    public WakeLockHolder() {
        this.b = new AtomicInteger(0);
        this.c = true;
    }

    public static WakeLockHolder onAcquireInfinite(Context context) {
        WakeLockHolder wakeLockHolder = new WakeLockHolder();
        wakeLockHolder.acquireInfiniteWakeLock(context);
        return wakeLockHolder;
    }

    public static void onRelease(WakeLockHolder wakeLockHolder) {
        if (wakeLockHolder == null) {
            return;
        }
        wakeLockHolder.releaseWakeLock();
    }

    public synchronized void acquireInfiniteWakeLock(Context context) {
        acquireWakeLock(context, d);
    }

    public synchronized void acquireWakeLock(Context context, String tag) {
        acquireWakeLock(context, 26, tag, -1);
    }

    public boolean isHeld() {
        return this.a != null && this.a.isHeld();
    }

    public synchronized void releaseWakeLock() {
        try {
            if (this.a != null) {
                if (this.a.isHeld()) {
                    this.a.release();
                }
                this.b.decrementAndGet();
                if (this.b.get() <= 0 || !this.c) {
                    this.b.set(0);
                    this.a = null;
                }
            }
        } catch (Exception failure) {
            failure.printStackTrace();
        }
    }

    public synchronized boolean releaseWhenNoneRef() {
        if (this.b.decrementAndGet() > 0) {
            return false;
        }
        forceReleaseWakeLock();
        return true;
    }

    public synchronized void forceReleaseWakeLock() {
        if (this.a != null) {
            if (this.a.isHeld()) {
                this.a.setReferenceCounted(false);
                this.a.release();
            }
            this.a = null;
            this.b.set(0);
        }
    }

    public synchronized void dumpWakelocks(String tag) {
        if ((this.a != null && this.b.get() <= 0) || (this.a == null && this.b.get() > 0)) {
            Log.e(tag, " Counting unmatched!");
        } else if (this.a != null || this.b.get() > 0) {
            Log.e(tag, "Wake lock in using: " + this.a.toString() + " counting: " + this.b.get());
        } else {
            Log.e(tag, "Wake lock released.");
        }
    }

    public synchronized void acquireInfiniteWakeLock(Context context, int flags) {
        acquireWakeLock(context, flags, d);
    }

    public synchronized void acquireWakeLock(Context context, int flags, String tag) {
        acquireWakeLock(context, flags, tag, -1);
    }

    public synchronized void acquireWakeLock(Context context, int flags, String tag, int ms) {
        try {
            if (this.a == null) {
                this.a = Device.currentDevice().newWakeLockWithFlags(context, flags, tag);
                this.a.setReferenceCounted(this.c);
            }
            if (this.a != null) {
                if (ms > 0) {
                    this.a.acquire(ms);
                } else {
                    this.a.acquire();
                }
                if (this.c) {
                    this.b.incrementAndGet();
                } else {
                    this.b.set(1);
                }
            }
        } catch (Exception failure) {
            failure.printStackTrace();
        }
    }

    public WakeLockHolder(boolean ref) {
        this.b = new AtomicInteger(0);
        this.c = true;
        this.c = ref;
    }
}
