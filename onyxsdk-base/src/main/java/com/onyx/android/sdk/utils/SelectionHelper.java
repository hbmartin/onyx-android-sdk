package com.onyx.android.sdk.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.data.SelectionModel;
import com.onyx.android.sdk.rx.RxUtils;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SelectionHelper<T> implements Serializable {
    private Map<String, SelectionModel<T>> a;
    private Comparator<T> b;

    public SelectionHelper() {
        this.a = new HashMap();
    }

    private SelectionModel<T> a() {
        return new SelectionModel().setComparator(this.b);
    }

    public static <T> Map<String, Map<String, Object>> transformToObjectMap(Map<String, SelectionModel<T>> selectedItemMap, Consumer<Map<String, Object>> mapConsumer) {
        Map<String, Map<String, Object>> map = new HashMap<>();
        for (Map.Entry<String, SelectionModel<T>> entry : selectedItemMap.entrySet()) {
            Map<String, Object> value = JSONUtils.toJSONObject(entry.getValue());
            map.put(entry.getKey(), value);
            RxUtils.acceptItemSafety(mapConsumer, value);
        }
        return map;
    }

    public SelectionHelper<T> setComparator(Comparator<T> comparator) {
        this.b = comparator;
        return this;
    }

    public Comparator<T> getComparator() {
        return this.b;
    }

    public Map<String, SelectionModel<T>> getSelectedMap() {
        return this.a;
    }

    @NonNull
    public SelectionModel<T> getSelectedModel(@Nullable String parent, long childCount) {
        a(parent, childCount);
        return getSelectedModel(parent);
    }

    @NonNull
    public SelectionModel<T> getEnsureSelectedModel(@Nullable String parent) {
        if (!this.a.containsKey(parent)) {
            this.a.put(parent, a());
        }
        return this.a.get(parent);
    }

    public SelectionModel<T> toggleSelectData(@Nullable String parent, T t) {
        return getEnsureSelectedModel(parent).toggleSelectData(t);
    }

    public boolean isSelected(@Nullable String parent, T t) {
        SelectionModel<T> selectedModel = getSelectedModel(parent);
        return selectedModel != null && selectedModel.isSelected(t);
    }

    public void clearAll() {
        Iterator<SelectionModel<T>> it = this.a.values().iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
        clearSelectedMap();
    }

    public void clearSelectedMap() {
        this.a.clear();
    }

    public void clearAllSelectedStatus() {
        Iterator<SelectionModel<T>> it = this.a.values().iterator();
        while (it.hasNext()) {
            it.next().clearSelectedStatus();
        }
    }

    public boolean hasSelectedItems() {
        Iterator<SelectionModel<T>> it = this.a.values().iterator();
        while (it.hasNext()) {
            if (it.next().hasSelectedItems()) {
                return true;
            }
        }
        return false;
    }

    @JSONField(serialize = false, deserialize = false)
    public T getFirstSelectedItem() {
        for (Map.Entry<String, SelectionModel<T>> entry : getSelectedMap().entrySet()) {
            SelectionModel<T> value = entry.getValue();
            if (entry.getKey() != null && value != null && CollectionUtils.isNonBlank(value.getSelectedList())) {
                return (T) CollectionUtils.getFirst(value.getSelectedList());
            }
        }
        return null;
    }

    @JSONField(serialize = false, deserialize = false)
    public Collection<String> getSelectedParentSet() {
        HashSet hashSet = new HashSet();
        for (String str : this.a.keySet()) {
            if (this.a.get(str).hasSelectedItems()) {
                hashSet.add(str);
            }
        }
        return hashSet;
    }

    public <R> Map<String, SelectionModel<R>> getSelectedItemMap(Function<T, R> transform) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (String str : getSelectedMap().keySet()) {
            SelectionModel<T> selectedModel = getSelectedModel(str);
            if (selectedModel != null && (selectedModel.isSelectedAllMode() || !CollectionUtils.isNullOrEmpty(selectedModel.getSelectedList()))) {
                SelectionModel selectionModel = new SelectionModel();
                selectionModel.setSelectedAllMode(selectedModel.isSelectedAllMode());
                Iterator<T> it = selectedModel.getSelectedList().iterator();
                while (it.hasNext()) {
                    CollectionUtils.safeAdd(selectionModel.getSelectedList(), RxUtils.applyItemSafety(transform, it.next()));
                }
                linkedHashMap.put(str, selectionModel);
            }
        }
        return linkedHashMap;
    }

    private void a(String parent, long childCount) {
        if (this.a.containsKey(parent)) {
            this.a.get(parent).setCount(childCount);
        } else {
            this.a.put(parent, a().setCount(childCount));
        }
    }

    public SelectionHelper(T t) {
        this(null, t);
    }

    @Nullable
    public SelectionModel<T> getSelectedModel(@Nullable String parent) {
        return this.a.get(parent);
    }

    public SelectionHelper(String parent, T t) {
        this.a = new HashMap();
        getEnsureSelectedModel(parent).toggleSelectData(t);
    }

    public SelectionHelper(List<T> list) {
        this.a = new HashMap();
        getEnsureSelectedModel(null).setSelectedList(list);
    }

    public SelectionHelper(SelectionHelper<T> selectionHelper) {
        this.a = new HashMap();
        this.a = new HashMap(selectionHelper.getSelectedMap());
        setComparator(selectionHelper.getComparator());
    }

    public SelectionHelper(Map<String, SelectionModel<T>> map) {
        HashMap map2 = new HashMap();
        this.a = map2;
        map2.putAll(map);
    }
}
