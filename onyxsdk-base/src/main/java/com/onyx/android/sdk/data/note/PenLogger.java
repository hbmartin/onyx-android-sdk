package com.onyx.android.sdk.data.note;

import com.onyx.android.sdk.data.sync.KSyncConstant;
import com.onyx.android.sdk.utils.CallStackUtils;
import com.onyx.android.sdk.utils.Debug;
import java.util.LinkedHashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/note/PenLogger.class */
public class PenLogger {
    public static int PRINT_INTERVAL_TIME_MS = 2000;
    public static PenLogger instance = new PenLogger();
    private final Map<String, Long> a = new LinkedHashMap();
    private boolean b = true;
    private boolean c = false;
    int d = 6;

    public static PenLogger getInstance() {
        return instance;
    }

    public PenLogger setEnabled(boolean enabled) {
        this.b = enabled;
        return this;
    }

    public void setPrintPauseCallStack(boolean printPauseCallStack) {
        this.c = printPauseCallStack;
    }

    public void printPauseCallStack(Class<?> cls) {
        if (this.c) {
            CallStackUtils.INSTANCE.printCallStack(cls, this.d);
        }
    }

    public void print(Class<?> cls, String message) {
        if (this.b) {
            if (!this.a.containsKey(message)) {
                this.a.put(message, Long.valueOf(System.currentTimeMillis()));
                return;
            }
            long jCurrentTimeMillis = System.currentTimeMillis() - this.a.get(message).longValue();
            if (jCurrentTimeMillis < PRINT_INTERVAL_TIME_MS) {
                Debug.i(PenLogger.class.getSimpleName() + KSyncConstant.COUCH_DB_FULL_NAME_SPLIT_CHAR + cls, message + "， interval time " + jCurrentTimeMillis + "ms", new Object[0]);
            }
            this.a.put(message, Long.valueOf(System.currentTimeMillis()));
        }
    }

    public void reset() {
        this.a.clear();
    }
}
