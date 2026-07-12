/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.onyx.android.sdk.utils.EventBusHolder
 */
package com.onyx.android.sdk.pen;

import com.onyx.android.sdk.utils.EventBusHolder;

public class TouchEventBus {
    private static final TouchEventBus b = new TouchEventBus();
    private EventBusHolder a;

    public static TouchEventBus getInstance() {
        return b;
    }

    private TouchEventBus() {
        EventBusHolder eventBusHolder;
        TouchEventBus this_ = eventBusHolder;
        eventBusHolder = new EventBusHolder();
        v1.a = this_;
    }

    public EventBusHolder getEventBusHolder() {
        return this.a;
    }
}

