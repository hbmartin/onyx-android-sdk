package com.onyx.android.sdk.wifi;

import androidx.annotation.NonNull;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.StringUtils;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/ExceptionUtils.class */
public class ExceptionUtils {

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/ExceptionUtils$a.class */
    static class a implements Thread.UncaughtExceptionHandler {
        final /* synthetic */ Thread.UncaughtExceptionHandler a;

        a(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
            this.a = uncaughtExceptionHandler;
        }

        @Override // java.lang.Thread.UncaughtExceptionHandler
        public void uncaughtException(@NonNull Thread thread, @NonNull Throwable throwable) {
            if (StringUtils.safelyEquals(thread.getName(), "FinalizerWatchdogDaemon") && (throwable instanceof TimeoutException)) {
                Debug.e(a.class, "ignore " + thread.getName() + " exception: " + throwable.toString(), new Object[0]);
            } else {
                this.a.uncaughtException(thread, throwable);
            }
        }
    }

    public static void ignoreFinalizerTimeOutExceptionHandler() {
        Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (defaultUncaughtExceptionHandler != null) {
            Thread.setDefaultUncaughtExceptionHandler(new a(defaultUncaughtExceptionHandler));
        }
    }
}
