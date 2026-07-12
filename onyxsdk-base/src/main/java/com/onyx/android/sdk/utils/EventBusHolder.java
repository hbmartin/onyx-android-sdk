package com.onyx.android.sdk.utils;

import android.util.Log;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/EventBusHolder.class */
public class EventBusHolder {
    private EventBus a;
    private final List<a> b = new ArrayList();

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/EventBusHolder$a.class */
    private static class a extends WeakReference<Object> {
        private final String a;

        public a(Object referent) {
            super(referent);
            this.a = referent.getClass().getSimpleName();
        }

        public String a() {
            return StringUtils.safelyGetStr(this.a);
        }
    }

    public EventBusHolder() {
    }

    private void a(Object subscriber) {
        if (getEventBus().isRegistered(subscriber)) {
            this.b.add(new a(subscriber));
        }
    }

    private void b(Object subscriber) {
        if (getEventBus().isRegistered(subscriber)) {
            return;
        }
        for (a aVar : this.b) {
            if (aVar != null && aVar.get() != null && aVar.get().equals(subscriber)) {
                this.b.remove(aVar);
                return;
            }
        }
    }

    public EventBus getEventBus() {
        if (this.a == null) {
            this.a = new EventBus();
        }
        return this.a;
    }

    public void post(Object event) {
        getEventBus().post(event);
    }

    public void register(Object subscriber) {
        if (getEventBus().isRegistered(subscriber)) {
            return;
        }
        getEventBus().register(subscriber);
        a(subscriber);
    }

    public Boolean unregister(Object subscriber) {
        if (!getEventBus().isRegistered(subscriber)) {
            return Boolean.FALSE;
        }
        getEventBus().unregister(subscriber);
        b(subscriber);
        return Boolean.TRUE;
    }

    public int dumpEventBus(String tag) {
        for (a aVar : this.b) {
        }
        long jA = a();
        if (jA > 0) {
            Log.e(tag, "EventBus unregister class counting: " + this.b.size());
            for (a aVar2 : this.b) {
                if (aVar2 != null && aVar2.get() != null) {
                    Log.e(tag, "EventBus unregister class: " + ClassUtils.getSimpleName(aVar2.get().getClass()));
                }
            }
        } else {
            Log.e(tag, "EventBus all unregister.");
        }
        return (int) jA;
    }

    public EventBusHolder(EventBus eventBus) {
        this.a = eventBus;
    }

    private long a() {
        return this.b.stream().filter(o -> {
            return (o == null || o.get() == null) ? false : true;
        }).count();
    }
}
