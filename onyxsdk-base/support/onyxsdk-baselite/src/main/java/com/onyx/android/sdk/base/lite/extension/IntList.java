package com.onyx.android.sdk.base.lite.extension;

import java.util.ArrayList;
import kotlin.Metadata;

@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003B\u0005¢\u0006\u0002\u0010\u0004¨\u0006\u0005"}, d2 = {"Lcom/onyx/android/sdk/base/lite/extension/IntList;", "Ljava/util/ArrayList;", "", "Lkotlin/collections/ArrayList;", "()V", "sdk-baselite_release"})
public final class IntList extends ArrayList<Integer> {
    public /* bridge */ boolean remove(Integer element) {
        return super.remove((Object) element);
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ boolean remove(Object element) {
        if (element == null ? true : element instanceof Integer) {
            return remove((Integer) element);
        }
        return false;
    }

    public /* bridge */ boolean contains(Integer element) {
        return super.contains((Object) element);
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ boolean contains(Object element) {
        if (element instanceof Integer) {
            return contains((Integer) element);
        }
        return false;
    }

    public /* bridge */ Integer removeAt(int p0) {
        return (Integer) super.remove(p0);
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public final /* bridge */ Integer remove(int index) {
        return removeAt(index);
    }

    public /* bridge */ int indexOf(Integer element) {
        return super.indexOf((Object) element);
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public final /* bridge */ int indexOf(Object element) {
        if (element instanceof Integer) {
            return indexOf((Integer) element);
        }
        return -1;
    }

    public /* bridge */ int lastIndexOf(Integer element) {
        return super.lastIndexOf((Object) element);
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public final /* bridge */ int lastIndexOf(Object element) {
        if (element instanceof Integer) {
            return lastIndexOf((Integer) element);
        }
        return -1;
    }

    public /* bridge */ int getSize() {
        return super.size();
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ int size() {
        return getSize();
    }
}
