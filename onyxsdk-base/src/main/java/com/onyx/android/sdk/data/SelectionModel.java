package com.onyx.android.sdk.data;

import androidx.databinding.BaseObservable;
import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.utils.CollectionUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/SelectionModel.class */
public class SelectionModel<T> extends BaseObservable implements Serializable {
    private boolean a;
    private List<T> b;
    private long c;
    private Comparator<T> d;

    public SelectionModel() {
        this.b = new ArrayList();
    }

    private boolean a(List<T> list, T t) {
        if (this.d == null) {
            return list.contains(t);
        }
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (this.d.compare(it.next(), t) == 0) {
                return true;
            }
        }
        return false;
    }

    private boolean b(List<T> list, T t) {
        if (this.d == null) {
            return list.remove(t);
        }
        for (T t2 : list) {
            if (this.d.compare(t2, t) == 0) {
                return list.remove(t2);
            }
        }
        return false;
    }

    public boolean isSelectedAllMode() {
        return this.a;
    }

    public SelectionModel<T> setSelectedAllMode(boolean selectedAllMode) {
        this.a = selectedAllMode;
        this.b.clear();
        notifyChange();
        return this;
    }

    @JSONField(serialize = false, deserialize = false)
    public boolean isAllDataSelected() {
        if (isSelectedAllMode()) {
            return CollectionUtils.isNullOrEmpty(this.b);
        }
        return !CollectionUtils.isNullOrEmpty(this.b) && ((long) CollectionUtils.getSize(this.b)) >= this.c;
    }

    public List<T> getSelectedList() {
        return this.b;
    }

    public SelectionModel<T> setSelectedList(List<T> selectedList) {
        this.b = selectedList;
        return this;
    }

    public long getCount() {
        return this.c;
    }

    public SelectionModel<T> setCount(long count) {
        this.c = count;
        return this;
    }

    public SelectionModel<T> clear() {
        return clearSelectedStatus().setCount(0L);
    }

    public SelectionModel<T> clearSelectedStatus() {
        getSelectedList().clear();
        setSelectedAllMode(false);
        return this;
    }

    public boolean hasSelectedItems() {
        return (this.a && this.c > ((long) this.b.size())) || !(this.a || this.b.size() == 0);
    }

    public SelectionModel<T> toggleSelectData(T t) {
        if (!b(this.b, t)) {
            this.b.add(t);
        }
        notifyChange();
        return this;
    }

    public void toggleAllSection() {
        if (isAllDataSelected()) {
            this.a = false;
        } else {
            this.a = true;
        }
        this.b.clear();
    }

    @JSONField(serialize = false, deserialize = false)
    public boolean isSelected(T t) {
        return this.a != a(this.b, t);
    }

    @JSONField(serialize = false, deserialize = false)
    public long getSelectedCount() {
        return this.a ? this.c - ((long) this.b.size()) : this.b.size();
    }

    @JSONField(serialize = false, deserialize = false)
    public SelectionModel<T> setComparator(Comparator<T> comparator) {
        this.d = comparator;
        return this;
    }

    @JSONField(serialize = false, deserialize = false)
    public Comparator<T> getComparator() {
        return this.d;
    }

    public SelectionModel(long count) {
        this.b = new ArrayList();
        this.a = false;
        this.b = new ArrayList();
        this.c = count;
    }
}
