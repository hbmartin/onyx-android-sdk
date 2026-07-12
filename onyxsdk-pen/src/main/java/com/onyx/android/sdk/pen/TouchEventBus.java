package com.onyx.android.sdk.pen;

import com.onyx.android.sdk.utils.EventBusHolder;

public class TouchEventBus {
    private static final TouchEventBus b = new TouchEventBus();
    private EventBusHolder a = new EventBusHolder();

    public static TouchEventBus getInstance() {
        return b;
    }

    private TouchEventBus() {
    }

    public EventBusHolder getEventBusHolder() {
        return this.a;
    }
}

