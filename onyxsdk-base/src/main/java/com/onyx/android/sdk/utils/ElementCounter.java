package com.onyx.android.sdk.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class ElementCounter<T> extends HashMap<T, Integer> {
    public ElementCounter() {}

    private String a(List<? extends T> list) {
        StringBuilder result = new StringBuilder();
        for (T item : list) {
            if (result.length() > 0) result.append(',');
            result.append('"').append(item).append('"');
        }
        return result.toString();
    }

    private static int a(ElementCounter counter, Object left, Object right) {
        return counter.get(left) - counter.get(right);
    }

    private static int b(ElementCounter counter, Object left, Object right) {
        return counter.get(right) - counter.get(left);
    }

    @Override public Integer get(Object key) {
        Integer count = super.get(key);
        return count == null ? 0 : count;
    }

    public int put(T key) { return put(key, 1); }

    public Integer put(T key, int appendCount) {
        int next = get(key) + appendCount;
        Integer previous = super.put(key, next);
        return previous == null ? next : previous;
    }

    @Override public Integer remove(Object key) {
        Integer previous = super.remove(key);
        return previous == null ? 0 : previous;
    }

    public void reset(T key) { super.put(key, 0); }

    public void resetAll(List<? extends T> keys) {
        for (T key : keys) super.put(key, 0);
    }

    public List<T> toAscList() {
        List<T> result = new ArrayList<>(super.keySet());
        result.sort((left, right) -> a(this, left, right));
        return result;
    }

    public List<T> toDescList() {
        List<T> result = new ArrayList<>(super.keySet());
        result.sort((left, right) -> b(this, left, right));
        return result;
    }

    public String printAscList() { return a(toAscList()); }
    public String printDescList() { return a(toDescList()); }

    public boolean remove(Object key, Integer value) { return super.remove(key, value); }
    @Override public boolean remove(Object key, Object value) { return value instanceof Integer && remove(key, (Integer) value); }
    public boolean containsValue(Integer value) { return super.containsValue(value); }
    @Override public boolean containsValue(Object value) { return value instanceof Integer && containsValue((Integer) value); }
    @SuppressWarnings("unchecked") public Set<Map.Entry<Object, Integer>> getEntries() { return (Set) super.entrySet(); }
    @Override public Set<Map.Entry<T, Integer>> entrySet() { return super.entrySet(); }
    @SuppressWarnings("unchecked") public Set<Object> getKeys() { return (Set) super.keySet(); }
    @Override public Set<T> keySet() { return super.keySet(); }
    public Collection<Integer> getValues() { return super.values(); }
    @Override public Collection<Integer> values() { return super.values(); }
    public int getSize() { return super.size(); }
    @Override public int size() { return super.size(); }
}
