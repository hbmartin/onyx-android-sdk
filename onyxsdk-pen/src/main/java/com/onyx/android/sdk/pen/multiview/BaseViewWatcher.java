package com.onyx.android.sdk.pen.multiview;

import android.graphics.Rect;
import androidx.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BaseViewWatcher<T> {
    private int a;
    private int b;
    private List<WeakReference<T>> c = new ArrayList();

    @Nullable
    private WeakReference<T> a(T object) {
        if (object == null) {
            return null;
        }
        for (WeakReference<T> weakReference : this.c) {
            T t = weakReference.get();
            if (t != null && t == object) {
                return weakReference;
            }
        }
        return null;
    }

    public List<WeakReference<T>> getWatchedObjects() {
        return this.c;
    }

    public boolean add(T object) {
        if (contains(object)) {
            return false;
        }
        this.c.add(new WeakReference<>(object));
        return true;
    }

    public boolean remove(T object) {
        WeakReference<T> weakReferenceA = a(object);
        if (weakReferenceA != null) {
            return this.c.remove(weakReferenceA);
        }
        return false;
    }

    public boolean contains(T object) {
        return a(object) != null;
    }

    public void clear() {
        this.c.clear();
    }

    public abstract List<Rect> getRects();

    public abstract boolean hasVisibleObject();

    public boolean isAllInvisible() {
        return !hasVisibleObject();
    }

    public int getContainerViewScreenX() {
        return this.a;
    }

    public int getContainerViewScreenY() {
        return this.b;
    }

    public BaseViewWatcher<T> setContainerViewScreenX(int containerViewScreenX) {
        this.a = containerViewScreenX;
        return this;
    }

    public BaseViewWatcher<T> setContainerViewScreenY(int containerViewScreenY) {
        this.b = containerViewScreenY;
        return this;
    }

    public boolean remove(List<T> objectList) {
        boolean zRemove = false;
        Iterator<T> it = objectList.iterator();
        while (it.hasNext()) {
            zRemove |= remove(it.next());
        }
        return zRemove;
    }

    public boolean add(List<T> objectList) {
        boolean zAdd = false;
        Iterator<T> it = objectList.iterator();
        while (it.hasNext()) {
            zAdd |= add(it.next());
        }
        return zAdd;
    }
}

