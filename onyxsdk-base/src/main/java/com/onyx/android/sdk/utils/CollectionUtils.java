package com.onyx.android.sdk.utils;

import android.util.SparseArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.onyx.android.sdk.data.GAdapter;
import com.onyx.android.sdk.rx.RxUtils;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionUtils {

    static class a<T> implements Comparable<T> {
        final /* synthetic */ Collection a;
        final /* synthetic */ Comparator b;

        a(Collection collection, Comparator comparator) {
            this.a = collection;
            this.b = comparator;
        }

        @Override // java.lang.Comparable
        public int compareTo(@NonNull T item) {
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                if (this.b.compare(it.next(), item) == 0) {
                    return 0;
                }
            }
            return 1;
        }
    }

    static class b implements Comparable<String> {
        final /* synthetic */ String a;

        b(String str) {
            this.a = str;
        }

        @Override // java.lang.Comparable
        public int compareTo(@NonNull String o) {
            return o.compareTo(this.a);
        }
    }

    public static boolean isNullOrEmpty(Collection list) {
        return list == null || list.size() <= 0;
    }

    public static boolean isNonBlank(Collection list) {
        return list != null && list.size() > 0;
    }

    public static boolean contains(Set<String> set, String value) {
        if (set == null || set.size() <= 0) {
            return true;
        }
        return set.contains(value);
    }

    public static <T> boolean containsAll(Collection<T> source, Collection<T> target, Comparator<T> comparator) {
        if (isNullOrEmpty(target)) {
            return true;
        }
        if (isNullOrEmpty(source)) {
            return false;
        }
        HashSet hashSet = new HashSet();
        for (T t : target) {
            if (!hashSet.contains(t)) {
                boolean z = false;
                for (T t2 : source) {
                    hashSet.add(t2);
                    if (t != null) {
                        if (comparator.compare(t2, t) == 0) {
                            z = true;
                            break;
                        }
                    } else {
                        if (t2 == null) {
                            z = true;
                            break;
                        }
                    }
                }
                if (!z) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean equals(Collection firstSet, Collection secondSet) {
        if (firstSet == null || secondSet == null) {
            return false;
        }
        return firstSet.equals(secondSet);
    }

    public static <T> boolean safelyContains(Collection<T> collection, T t) {
        if (collection == null) {
            return false;
        }
        return collection.contains(t);
    }

    public static int getSize(Collection collection) {
        if (collection == null) {
            return 0;
        }
        return collection.size();
    }

    public static <K, V extends Collection> int getMapValueSize(Map<K, V> map) {
        if (map == null) {
            return 0;
        }
        int size = 0;
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            size += getSize(it.next().getValue());
        }
        return size;
    }

    public static boolean safelyReverseContains(List<String> list, String string) {
        if (StringUtils.isNullOrEmpty(string) || isNullOrEmpty(list)) {
            return false;
        }
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            if (string.contains(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static <T> void diff(Collection<T> origin, Collection<T> target, Collection<T> diff) {
        for (T t : target) {
            if (!origin.contains(t)) {
                diff.add(t);
            }
        }
    }

    public static <T> void safeSet(List<T> originList, int index, T target) {
        if (isOutOfRange(originList, index) || target == null) {
            return;
        }
        originList.set(index, target);
    }

    public static <T> void safeAddAll(Collection<T> originList, Collection<T> collection) {
        if (originList == null || isNullOrEmpty(collection)) {
            return;
        }
        originList.addAll(collection);
    }

    public static <T> void safeAdd(Collection<T> originList, T target) {
        if (originList == null || target == null) {
            return;
        }
        originList.add(target);
    }

    public static <K, V> void safeAddAllMap(Map<K, V> originMap, Map<K, V> map) {
        if (originMap == null || isNullOrEmpty(map)) {
            return;
        }
        originMap.putAll(map);
    }

    public static void clear(Collection list) {
        if (list != null) {
            list.clear();
        }
    }

    public static <T> void ensureAddAll(Collection<T> originList, Collection<T> collection) {
        safeAddAll(originList, collection);
    }

    public static <T extends Comparable<T>> boolean compare(@NonNull List<T> a2, @NonNull List<T> b2) {
        if (a2.size() != b2.size()) {
            return false;
        }
        Collections.sort(a2);
        Collections.sort(b2);
        for (int i = 0; i < a2.size(); i++) {
            if (!a2.get(i).equals(b2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static <T extends Comparable<T>> boolean safelyCompareWithNotSort(@NonNull List<T> a2, @NonNull List<T> b2) {
        if (a2.size() != b2.size()) {
            return false;
        }
        for (int i = 0; i < a2.size(); i++) {
            T t = a2.get(i);
            T t2 = b2.get(i);
            if (!(t == null && t2 == null) && (t == null || t2 == null || !t.equals(t2))) {
                return false;
            }
        }
        return true;
    }

    @NonNull
    public static <K, V> Collection<V> getValuesFromKeySet(Map<K, V> map, Collection<K> keySet) {
        if (isNullOrEmpty(keySet) || isNullOrEmpty(map)) {
            return new HashSet();
        }
        HashSet hashSet = new HashSet();
        for (K k : keySet) {
            if (map.containsKey(k)) {
                hashSet.add(map.get(k));
            }
        }
        return hashSet;
    }

    public static int getClosetValueFromCollection(Collection<Integer> collection, int validateValue) {
        ArrayList arrayList = new ArrayList(collection);
        Collections.sort(arrayList);
        int[] iArr = new int[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            iArr[i] = ((Integer) arrayList.get(i)).intValue();
        }
        int iBinarySearch = Arrays.binarySearch(iArr, validateValue);
        return iBinarySearch < 0 ? iArr[Math.abs(iBinarySearch) - 1] : validateValue;
    }

    public static <T> List<T> transformData(List<T> data, int col) {
        int i;
        ArrayList arrayList = new ArrayList(data.size());
        int iCeil = (int) Math.ceil(((double) data.size()) / ((double) col));
        for (int i2 = 0; i2 < iCeil; i2++) {
            for (int i3 = 0; i3 < col && (i = i2 + (i3 * iCeil)) < data.size(); i3++) {
                arrayList.add(data.get(i));
            }
        }
        return arrayList;
    }

    public static <T> List<T> safelySubList(List<T> list, int fromIndex, int toIndex) {
        if (isNullOrEmpty(list)) {
            return Collections.emptyList();
        }
        if (fromIndex < 0) {
            fromIndex = 0;
        }
        if (fromIndex > getSize(list)) {
            fromIndex = getSize(list);
        }
        if (toIndex > getSize(list)) {
            toIndex = getSize(list);
        }
        return new ArrayList(list.subList(fromIndex, toIndex));
    }

    public static <T> List<T> safelyDetachList(List<T> list, int fromIndex, int toIndex) {
        List<T> listSafelySubList = safelySubList(list, fromIndex, toIndex);
        if (list != null) {
            list.removeAll(listSafelySubList);
        }
        return listSafelySubList;
    }

    @NonNull
    public static <K, V> List<V> ensureList(Map<K, List<V>> map, K key) {
        List<V> list = map.get(key);
        List<V> list2 = list;
        if (list == null) {
            list2 = new ArrayList<>();
            map.put(key, list2);
        }
        return list2;
    }

    @NonNull
    public static <K, V> Set<V> ensureSet(Map<K, Set<V>> map, K key) {
        Set<V> set = map.get(key);
        Set<V> set2 = set;
        if (set == null) {
            set2 = new HashSet<>();
            map.put(key, set2);
        }
        return set2;
    }

    public static <T> T getLast(List<T> list) {
        if (isNullOrEmpty(list)) {
            return null;
        }
        return list.get(getSize(list) - 1);
    }

    @Nullable
    public static <T> T getFirst(Collection<T> list) {
        if (isNullOrEmpty(list)) {
            return null;
        }
        return list.iterator().next();
    }

    public static boolean isLastElement(List list, int index) {
        return index + 1 == getSize(list);
    }

    public static boolean isFirstElement(List list, int index) {
        return !isNullOrEmpty(list) && index == 0;
    }

    public static <T> T getIndexElementOrFirst(List<T> list, int i) {
        Object element = getElement(list, i);
        Object first = element;
        if (element == null) {
            first = getFirst(list);
        }
        return (T) first;
    }

    public static <T> T getElement(List<T> list, int i, boolean z) {
        return (T) getElement(list, z ? i - 1 : i + 1);
    }

    public static boolean isOutOfRange(Collection list, int index) {
        return index < 0 || index >= getSize(list);
    }

    @Nullable
    public static <T> T safelyRemove(List<T> list, int position) {
        if (isOutOfRange(list, position)) {
            return null;
        }
        return list.remove(position);
    }

    public static <T> boolean safelyRemoveAll(Collection<T> originList, Collection<T> targetList) {
        if (isNullOrEmpty(originList) || isNullOrEmpty(targetList)) {
            return false;
        }
        return originList.removeAll(targetList);
    }

    public static <T> boolean safelyEquals(Collection<T> collection, Comparable<T> comparable) {
        if (isNullOrEmpty(collection)) {
            return false;
        }
        boolean z = false;
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            if (comparable.compareTo(it.next()) == 0) {
                z = true;
                break;
            }
        }
        return z;
    }

    public static boolean safelyStringEquals(Collection<String> collection, String exceptString) {
        return safelyEquals(collection, new b(exceptString));
    }

    public static void swapList(List list, int fromPosition, int toPosition) {
        int i;
        int i2;
        int size = getSize(list);
        if (fromPosition < toPosition) {
            while (fromPosition < toPosition && (i2 = fromPosition + 1) < size) {
                Collections.swap(list, fromPosition, i2);
                fromPosition = i2;
            }
            return;
        }
        while (fromPosition > toPosition && (i = fromPosition - 1) >= 0) {
            Collections.swap(list, fromPosition, i);
            fromPosition--;
        }
    }

    public static void swap(List list, int fromPosition, int toPosition) {
        Collections.swap(list, fromPosition, toPosition);
    }

    public static boolean isIntersect(Collection c1, Collection c2) {
        if (isNullOrEmpty(c1) || isNullOrEmpty(c2)) {
            return false;
        }
        Iterator it = c1.iterator();
        while (it.hasNext()) {
            if (c2.contains(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static <T> void toggleItem(Collection<T> originList, T t) {
        if (originList == null || t == null || originList.remove(t)) {
            return;
        }
        originList.add(t);
    }

    public static <T> List<T> filterNonNullItems(List<T> list) {
        return filterItems(list, t -> {
            return t != null;
        });
    }

    public static <T> List<T> filterItems(List<T> list, Predicate<T> predicate) {
        if (isNullOrEmpty(list)) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        for (T t : list) {
            if (RxUtils.testSafety(predicate, t)) {
                arrayList.add(t);
            }
        }
        return arrayList;
    }

    public static <T> int findItemIndex(List<T> list, Predicate<T> predicate) {
        if (isNullOrEmpty(list)) {
            return -1;
        }
        for (int i = 0; i < list.size(); i++) {
            if (RxUtils.testSafety(predicate, list.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public static <T> List<T> moveTo(List<T> list, int from, int to) {
        if (isNullOrEmpty(list)) {
            return list;
        }
        if (isOutOfRange(list, from) || isOutOfRange(list, to) || from == to) {
            return list;
        }
        list.add(to, list.remove(from));
        return list;
    }

    @Nullable
    public static <T> T findItem(List<T> list, Predicate<T> predicate) {
        for (T t : ensureList(list)) {
            if (RxUtils.testSafety(predicate, t)) {
                return t;
            }
        }
        return null;
    }

    public static <T> List<T> findItems(List<T> list, Predicate<T> test) {
        List<T> listEnsureList = ensureList(null);
        for (T obj : ensureList(list)) {
            if (RxUtils.testSafety(test, obj)) {
                safeAdd(listEnsureList, obj);
            }
        }
        return listEnsureList;
    }

    public static <K, V> Map<K, V> snapshot(Map<K, V> map) {
        return new HashMap(map);
    }

    public static boolean safelyStringEqualsIgnoreCase(Collection<String> collection, String exceptString) {
        return safelyEquals(collection, item -> {
            return item.compareToIgnoreCase(exceptString);
        });
    }

    public static <K, V> Map<K, V> toMap(Collection<V> collection, Function<V, K> function) {
        HashMap map = new HashMap();
        if (isNonBlank(collection)) {
            for (V v : collection) {
                map.put(RxUtils.applyItemSafety(function, v), v);
            }
        }
        return map;
    }

    public static <K, V> Map<K, V> reverse(Map<K, V> map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap(map.size());
        ArrayList arrayList = new ArrayList(map.keySet());
        Collections.reverse(arrayList);
        for (Object obj : arrayList) {
            linkedHashMap.put(obj, map.get(obj));
        }
        return linkedHashMap;
    }

    public static <T> List<T> createNewList(List<T> originList, boolean clear) {
        ArrayList arrayList = new ArrayList(ensureList(originList));
        if (clear) {
            clear(originList);
        }
        return arrayList;
    }

    public static <T> List<T> createMutableList(T[] array) {
        return array == null ? new ArrayList() : new ArrayList(Arrays.asList(array));
    }

    public static <T> List<List<T>> split(List<T> originList, int batch) {
        ArrayList arrayList = new ArrayList();
        int size = originList.size() / batch;
        if (originList.size() % batch != 0) {
            size++;
        }
        for (int i = 0; i < size; i++) {
            int i2 = i * batch;
            arrayList.add(originList.subList(i2, Math.min(i2 + batch, originList.size())));
        }
        return arrayList;
    }

    @SafeVarargs
    public static <T> Collection<T> createCombinations(Collection<T>... collectionArray) {
        if (ArraysUtils.isNullOrEmpty(collectionArray)) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        for (Collection<T> collection : collectionArray) {
            safeAddAll(arrayList, collection);
        }
        return arrayList;
    }

    public static boolean isNullOrEmpty(SparseArray array) {
        return array == null || array.size() <= 0;
    }

    public static boolean isNonBlank(Map map) {
        return map != null && map.size() > 0;
    }

    public static <T> boolean safelyContains(Collection<T> srcList, Collection<T> dstList) {
        if (isNullOrEmpty(srcList) || isNullOrEmpty(dstList)) {
            return false;
        }
        Iterator<T> it = dstList.iterator();
        while (it.hasNext()) {
            if (srcList.contains(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static int getSize(Map map) {
        if (map == null) {
            return 0;
        }
        return map.size();
    }

    public static <T> boolean isLastElement(List<T> list, T t) {
        int iIndexOf;
        return !isNullOrEmpty(list) && (iIndexOf = list.indexOf(t)) >= 0 && iIndexOf + 1 == getSize(list);
    }

    public static <T> boolean isFirstElement(List<T> list, T t) {
        return !isNullOrEmpty(list) && list.indexOf(t) == 0;
    }

    public static <T> T getElement(List<T> list, int currentIndex) {
        if (isOutOfRange(list, currentIndex)) {
            return null;
        }
        return list.get(currentIndex);
    }

    public static boolean isNullOrEmpty(Map map) {
        return map == null || map.size() <= 0;
    }

    public static boolean contains(Collection<String> source, Collection<String> target) {
        if (source == null && target == null) {
            return true;
        }
        if (source == null || target == null) {
            return false;
        }
        Iterator<String> it = target.iterator();
        while (it.hasNext()) {
            if (source.contains(it.next())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNullOrEmpty(GAdapter adapter) {
        return adapter == null || isNullOrEmpty(adapter.getList());
    }

    public static <T> void diff(Collection<T> origin, Collection<T> target, Collection<T> diff, Comparator<T> comparator) {
        if (isNullOrEmpty(origin)) {
            safeAddAll(diff, target);
            return;
        }
        if (isNullOrEmpty(target)) {
            safeAddAll(diff, origin);
            return;
        }
        for (T t : target) {
            if (!contains(origin, t, comparator)) {
                diff.add(t);
            }
        }
    }

    public static <T> boolean contains(Collection<T> source, T target, Comparator<T> comparator) {
        if (source == null && target == null) {
            return true;
        }
        if (source == null || target == null) {
            return false;
        }
        Iterator<T> it = source.iterator();
        while (it.hasNext()) {
            if (comparator.compare(it.next(), target) == 0) {
                return true;
            }
        }
        return false;
    }

    public static <T> void safeAddAll(Collection<T> originList, Collection<T> targetList, Comparator<T> uniqueComparator) {
        if (originList == null) {
            return;
        }
        safelyRemove((Collection) originList, (Comparable) new a(targetList, uniqueComparator), false);
        safeAddAll(originList, targetList);
    }

    @NonNull
    public static <T> List<T> ensureList(SparseArray<List<T>> array, int key) {
        List<T> list = array.get(key);
        List<T> list2 = list;
        if (list == null) {
            list2 = new ArrayList<>();
            array.put(key, list2);
        }
        return list2;
    }

    public static <T> boolean safelyRemove(Collection<T> collection, T t) {
        if (isNullOrEmpty(collection)) {
            return false;
        }
        return collection.remove(t);
    }

    public static <T> List<T> reverse(List<T> list) {
        if (isNullOrEmpty(list)) {
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList(list.size());
        safeAddAll(arrayList, list);
        Collections.reverse(arrayList);
        return arrayList;
    }

    public static boolean contains(List<String> list, String string) {
        return list == null || list.contains(string);
    }

    public static <T> boolean safelyContains(T[] srcList, T dst) {
        if (srcList == null) {
            return false;
        }
        for (T t : srcList) {
            if (t.equals(dst)) {
                return true;
            }
        }
        return false;
    }

    public static boolean safelyContains(Set<String> set, String string) {
        return set != null && set.contains(string);
    }

    public static <T> boolean safelyRemove(Collection<T> collection, Comparable<T> comparable, boolean abortFirstMatched) {
        boolean z = false;
        Iterator<T> it = collection.iterator();
        while (it.hasNext()) {
            if (comparable.compareTo(it.next()) == 0) {
                z = true;
                it.remove();
                if (abortFirstMatched) {
                    break;
                }
            }
        }
        return z;
    }

    public static <T> List<T> ensureList(List<T> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public static boolean safelyContains(List<String> list, String string) {
        return list != null && list.contains(string);
    }

    public static <T> List<T> transformData(List<T> data, int col, int row) {
        int i;
        ArrayList arrayList = new ArrayList(data.size());
        int i2 = col * row;
        int iCeil = (int) Math.ceil(((double) data.size()) / ((double) i2));
        for (int i3 = 0; i3 < iCeil; i3++) {
            int i4 = i2 * i3;
            for (int i5 = 0; i5 < row; i5++) {
                for (int i6 = 0; i6 < col && (i = i5 + (i6 * row) + i4) < data.size(); i6++) {
                    arrayList.add(data.get(i));
                }
            }
        }
        return arrayList;
    }

    public static <K, V> boolean safelyRemove(Map<K, V> map, Comparable<Map.Entry<K, V>> comparable, boolean abortFirstMatched) {
        if (isNullOrEmpty(map)) {
            return false;
        }
        boolean z = false;
        Iterator<Map.Entry<K, V>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            if (comparable.compareTo(it.next()) == 0) {
                z = true;
                it.remove();
                if (abortFirstMatched) {
                    break;
                }
            }
        }
        return z;
    }

    public static <T> void diff(Set<T> first, Set<T> second, Set<T> firstOnly, Set<T> secondOnly) {
        for (T t : second) {
            if (!first.contains(t)) {
                secondOnly.add(t);
            }
        }
        for (T t2 : first) {
            if (!second.contains(t2)) {
                firstOnly.add(t2);
            }
        }
    }

    public static <T> void safeAddAll(Collection<T> originList, Collection<T> targetList, boolean clear) {
        if (originList == null) {
            return;
        }
        if (clear) {
            originList.clear();
        }
        safeAddAll(originList, targetList);
    }

    public static <T, R> List<R> transformData(Collection<T> originList, Function<T, R> function) {
        return (List) transformData(originList, new ArrayList(), function);
    }

    public static <T, R, E extends Collection<R>> E transformData(Collection<T> originList, E destList, Function<T, R> function) {
        if (isNullOrEmpty(originList)) {
            return destList;
        }
        Iterator<T> it = originList.iterator();
        while (it.hasNext()) {
            safeAdd(destList, RxUtils.applyItemSafety(function, it.next()));
        }
        return destList;
    }
}
