/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  androidx.annotation.Nullable
 */
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
    private List<WeakReference<T>> c;

    public BaseViewWatcher() {
        ArrayList arrayList;
        ArrayList this_ = arrayList;
        arrayList = new ArrayList();
        v1.c = this_;
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    private WeakReference<T> a(T object) {
        if (object == null) {
            return null;
        }
        for (WeakReference weakReference : ((BaseViewWatcher)this).c) {
            void var1_1;
            Object t = weakReference.get();
            if (t == null || t != var1_1) continue;
            return weakReference;
        }
        return null;
    }

    public List<WeakReference<T>> getWatchedObjects() {
        return this.c;
    }

    /*
     * WARNING - void declaration
     */
    public boolean add(T object) {
        void var1_1;
        if (this.contains(var1_1)) {
            return false;
        }
        this.c.add(new WeakReference<void>(var1_1));
        return true;
    }

    public boolean add(List<T> objectList) {
        boolean bl = false;
        Iterator<T> iterator = objectList.iterator();
        while (iterator.hasNext()) {
            bl |= this.add(iterator.next());
        }
        return bl;
    }

    public boolean remove(T object) {
        WeakReference<void> weakReference;
        if ((weakReference = this.a(weakReference)) != null) {
            return this.c.remove(weakReference);
        }
        return false;
    }

    public boolean remove(List<T> objectList) {
        boolean bl = false;
        Iterator<T> iterator = objectList.iterator();
        while (iterator.hasNext()) {
            bl |= this.remove(iterator.next());
        }
        return bl;
    }

    /*
     * WARNING - void declaration
     */
    public boolean contains(T object) {
        void var1_1;
        return this.a(var1_1) != null;
    }

    public void clear() {
        this.c.clear();
    }

    public abstract List<Rect> getRects();

    public abstract boolean hasVisibleObject();

    public boolean isAllInvisible() {
        return this.hasVisibleObject() ^ true;
    }

    public int getContainerViewScreenX() {
        return this.a;
    }

    public int getContainerViewScreenY() {
        return this.b;
    }

    /*
     * WARNING - void declaration
     */
    public BaseViewWatcher<T> setContainerViewScreenX(int containerViewScreenX) {
        void var1_1;
        this.a = var1_1;
        return this;
    }

    /*
     * WARNING - void declaration
     */
    public BaseViewWatcher<T> setContainerViewScreenY(int containerViewScreenY) {
        void var1_1;
        this.b = var1_1;
        return this;
    }
}

