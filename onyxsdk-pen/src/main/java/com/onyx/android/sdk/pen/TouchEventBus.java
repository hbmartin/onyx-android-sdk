package com.onyx.android.sdk.pen;

import com.onyx.android.sdk.utils.EventBusHolder;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/TouchEventBus.class */
public class TouchEventBus {
    private static final TouchEventBus b = new TouchEventBus();
    private EventBusHolder a = new EventBusHolder();

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static TouchEventBus getInstance() {
        return b;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private TouchEventBus() {
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public EventBusHolder getEventBusHolder() {
        return this.a;
    }
}

