package com.onyx.android.sdk.utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/ObjectHolder.class */
public class ObjectHolder<T> {
    private List<WeakReference<T>> a = new ArrayList();

    public void add(WeakReference<T> weakReference) {
        this.a.add(weakReference);
    }

    public void remove(WeakReference<T> weakReference) {
        this.a.remove(weakReference);
    }

    public void clear() {
        this.a.clear();
    }

    public List<WeakReference<T>> getObjectList() {
        return this.a;
    }

    public List<WeakReference<T>> getCopyOfObjectList() {
        return new ArrayList(this.a);
    }

    public void remove(T object) {
        for (WeakReference<T> weakReference : this.a) {
            if (weakReference.get() == object) {
                remove((WeakReference) weakReference);
                return;
            }
        }
    }
}
