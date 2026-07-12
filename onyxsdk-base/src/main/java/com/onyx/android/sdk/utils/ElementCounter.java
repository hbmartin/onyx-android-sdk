package com.onyx.android.sdk.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0008\n\u0002\u0018\u0002\n\u0002\u0008\u0005\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0008\u0008\n\u0002\u0010\u0002\n\u0002\u0008\u0006\u0018\u0000*\u0004\u0008\u0000\u0010\u00012\u001e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u00030\u0002j\u000e\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u00020\u0003`\u0004B\u0005\u00a2\u0006\u0002\u0010\u0005J\u0016\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00028\u0000H\u0096\u0002\u00a2\u0006\u0002\u0010\u0008J\u0016\u0010\t\u001a\u00020\n2\u000c\u0010\u000b\u001a\u0008\u0012\u0004\u0012\u00028\u00000\u000cH\u0002J\u0006\u0010\r\u001a\u00020\nJ\u0006\u0010\u000e\u001a\u00020\nJ\u0013\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u0010J\u001d\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00028\u00002\u0006\u0010\u0011\u001a\u00020\u0003H\u0016\u00a2\u0006\u0002\u0010\u0012J\u0015\u0010\u0013\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00028\u0000H\u0016\u00a2\u0006\u0002\u0010\u0008J\u0013\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0007\u001a\u00028\u0000\u00a2\u0006\u0002\u0010\u0016J\u0014\u0010\u0017\u001a\u00020\u00152\u000c\u0010\u0018\u001a\u0008\u0012\u0004\u0012\u00028\u00000\u000cJ\u000c\u0010\u0019\u001a\u0008\u0012\u0004\u0012\u00028\u00000\u000cJ\u000c\u0010\u001a\u001a\u0008\u0012\u0004\u0012\u00028\u00000\u000c\u00a8\u0006\u001b" }, d2 = { "Lcom/onyx/android/sdk/utils/ElementCounter;", "T", "Ljava/util/HashMap;", "", "Lkotlin/collections/HashMap;", "()V", "get", "key", "(Ljava/lang/Object;)Ljava/lang/Integer;", "listInfo", "", "list", "", "printAscList", "printDescList", "put", "(Ljava/lang/Object;)I", "appendCount", "(Ljava/lang/Object;I)Ljava/lang/Integer;", "remove", "reset", "", "(Ljava/lang/Object;)V", "resetAll", "keys", "toAscList", "toDescList", "onyxsdk-base_release" })
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

    public final int put(T key) { return put(key, 1); }

    public Integer put(T key, int appendCount) {
        int next = get(key) + appendCount;
        Integer previous = super.put(key, next);
        return previous == null ? next : previous;
    }

    @Override public Integer remove(Object key) {
        Integer previous = super.remove(key);
        return previous == null ? 0 : previous;
    }

    public final void reset(T key) { super.put(key, 0); }

    public final void resetAll(List<? extends T> keys) {
        for (T key : keys) super.put(key, 0);
    }

    public final List<T> toAscList() {
        List<T> result = new ArrayList<>(super.keySet());
        result.sort((left, right) -> a(this, left, right));
        return result;
    }

    public final List<T> toDescList() {
        List<T> result = new ArrayList<>(super.keySet());
        result.sort((left, right) -> b(this, left, right));
        return result;
    }

    public final String printAscList() { return a(toAscList()); }
    public final String printDescList() { return a(toDescList()); }

    public boolean remove(Object key, Integer value) { return super.remove(key, value); }
    @Override public final boolean remove(Object key, Object value) { return value instanceof Integer && remove(key, (Integer) value); }
    public boolean containsValue(Integer value) { return super.containsValue(value); }
    @Override public final boolean containsValue(Object value) { return value instanceof Integer && containsValue((Integer) value); }
    @SuppressWarnings("unchecked") public Set<Map.Entry<Object, Integer>> getEntries() { return (Set) super.entrySet(); }
    @Override public final Set<Map.Entry<T, Integer>> entrySet() { return super.entrySet(); }
    @SuppressWarnings("unchecked") public Set<Object> getKeys() { return (Set) super.keySet(); }
    @Override public final Set<T> keySet() { return super.keySet(); }
    public Collection<Integer> getValues() { return super.values(); }
    @Override public final Collection<Integer> values() { return super.values(); }
    public int getSize() { return super.size(); }
    @Override public final int size() { return super.size(); }
}
