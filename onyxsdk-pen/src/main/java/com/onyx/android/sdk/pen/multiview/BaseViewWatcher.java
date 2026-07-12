package com.onyx.android.sdk.pen.multiview;

import android.graphics.Rect;
import androidx.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/multiview/BaseViewWatcher.class */
public abstract class BaseViewWatcher<T> {
    private int a;
    private int b;
    private List<WeakReference<T>> c = new ArrayList();

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
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

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public List<WeakReference<T>> getWatchedObjects() {
        return this.c;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public boolean add(T object) {
        if (contains(object)) {
            return false;
        }
        this.c.add(new WeakReference<>(object));
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public boolean remove(T object) {
        WeakReference<T> weakReferenceA = a(object);
        if (weakReferenceA != null) {
            return this.c.remove(weakReferenceA);
        }
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public boolean contains(T object) {
        return a(object) != null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void clear() {
        this.c.clear();
    }

    public abstract List<Rect> getRects();

    public abstract boolean hasVisibleObject();

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public boolean isAllInvisible() {
        return !hasVisibleObject();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public int getContainerViewScreenX() {
        return this.a;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public int getContainerViewScreenY() {
        return this.b;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public BaseViewWatcher<T> setContainerViewScreenX(int containerViewScreenX) {
        this.a = containerViewScreenX;
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
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

