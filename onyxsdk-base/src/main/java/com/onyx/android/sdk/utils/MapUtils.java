package com.onyx.android.sdk.utils;

import androidx.annotation.Nullable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/MapUtils.class */
public class MapUtils {

    /* JADX INFO: Add missing generic type declarations: [V, K] */
    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/MapUtils$a.class */
    static class a<K, V> implements Comparator<Map.Entry<K, V>> {
        a() {
        }

        @Override // java.util.Comparator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
            return ((Comparable) o1.getValue()).compareTo(o2.getValue());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        LinkedList<Map.Entry> linkedList = new LinkedList(map.entrySet());
        Collections.sort(linkedList, new a());
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry entry : linkedList) {
            linkedHashMap.put(entry.getKey(), entry.getValue());
        }
        return linkedHashMap;
    }

    public static <K, V> Map.Entry<K, V> getFirst(Map<K, V> map) {
        return map.entrySet().iterator().next();
    }

    public static <K, V> Map.Entry<K, V> getLast(Map<K, V> map) {
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        Map.Entry<K, V> next = null;
        while (true) {
            Map.Entry<K, V> entry = next;
            if (!it.hasNext()) {
                return entry;
            }
            next = it.next();
        }
    }

    @Nullable
    public static <K, V> K getKeyByValue(Map<K, V> map, V v) {
        Comparator comparator = null;
        if (v instanceof Integer) {
            comparator = (o1, o2) -> {
                return ((Integer) o1).compareTo((Integer) o2);
            };
        }
        return (K) getKeyByValue(map, v, comparator);
    }

    @Nullable
    public static <K, V> K getKeyByValue(Map<K, V> map, V value, @Nullable Comparator<V> comparator) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue() == value) {
                return entry.getKey();
            }
            if (comparator != null && comparator.compare(entry.getValue(), value) == 0) {
                return entry.getKey();
            }
        }
        return null;
    }
}
