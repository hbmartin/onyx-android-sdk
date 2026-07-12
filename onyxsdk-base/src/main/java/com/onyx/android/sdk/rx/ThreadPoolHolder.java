package com.onyx.android.sdk.rx;

import android.content.Context;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/rx/ThreadPoolHolder.class */
public class ThreadPoolHolder {
    private ConcurrentHashMap<String, RxManager> a = new ConcurrentHashMap<>();

    public RxManager getRxManager(Context context, String identifier, boolean multi) {
        RxManager rxManagerNewMultiThreadManager;
        String str = identifier + (multi ? "-multi" : "-single");
        RxManager rxManager = this.a.get(str);
        if (rxManager == null) {
            RxManager.Builder.initAppContext(context.getApplicationContext());
            rxManagerNewMultiThreadManager = multi ? RxManager.Builder.newMultiThreadManager() : RxManager.Builder.newSingleThreadManager(identifier);
            this.a.put(str, rxManagerNewMultiThreadManager);
        } else {
            rxManagerNewMultiThreadManager = rxManager;
        }
        return rxManagerNewMultiThreadManager;
    }
}
