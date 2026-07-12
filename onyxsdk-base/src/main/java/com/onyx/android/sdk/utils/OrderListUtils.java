// 
// 

package com.onyx.android.sdk.utils;

import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.functions.Function1;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.collections.CollectionsKt;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002Jb\u0010\u0007\u001a\u00020\b\"\u0004\b\u0000\u0010\t2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00062\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\t0\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\t0\r2\u0018\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\b0\u0011H\u0002J\u001e\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00062\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\rH\u0002J\u001e\u0010\u0013\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00062\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\rH\u0002JN\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\t0\r\"\u0004\b\u0000\u0010\t2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\t0\r2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\u00040\u00162\u0018\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\b0\u0011JN\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\t0\r\"\u0004\b\u0000\u0010\t2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\t0\r2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\u00040\u00162\u0018\u0010\u0010\u001a\u0014\u0012\u0004\u0012\u0002H\t\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\b0\u0011R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0018" }, d2 = { "Lcom/onyx/android/sdk/utils/OrderListUtils;", "", "()V", "ORDER_INDEX_INTERVAL", "", "REORDER_COUNT", "", "adjust", "", "T", "start", "end", "orderList", "", "reOrderList", "list", "setter", "Lkotlin/Function2;", "findEnd", "findStart", "reorderIndex", "getter", "Lkotlin/Function1;", "reorderIndexChecked", "onyxsdk-base_release" })
public final class OrderListUtils
{
    @NotNull
    public static final OrderListUtils INSTANCE;
    private static final float a = 1.0f;
    private static final int b = 30;
    
    private OrderListUtils() {
    }
    
    private final int b(int start, final List<Float> orderList) {
        final int lastIndex = CollectionsKt.getLastIndex((List)orderList);
        int n = start;
        while (start < lastIndex) {
            final int n2 = start;
            final int n3 = n2 + 1;
            if (orderList.get(n2).floatValue() >= 0.0f && orderList.get(n3).floatValue() < 0.0f) {
                n = start;
                break;
            }
            final int n4 = n3;
            n = start;
            start = n4;
        }
        return n;
    }
    
    private final int a(int end, final List<Float> orderList) {
        int lastIndex;
        int n;
        for (lastIndex = CollectionsKt.getLastIndex((List)orderList), n = end; end < lastIndex; end = n) {
            final int n2 = end;
            n = n2 + 1;
            if (orderList.get(n2).floatValue() < 0.0f && orderList.get(n).floatValue() > 0.0f) {
                break;
            }
        }
        return n;
    }
    
    private final <T> void a(int start, final int end, final List<Float> orderList, final List<T> reOrderList, final List<T> list, final Function2<? super T, ? super Float, Unit> setter) {
        final float n = (orderList.get(end).floatValue() - orderList.get(start).floatValue()) / (end - start);
        ++start;
        while (start < end) {
            final int n2 = start;
            final int n3;
            orderList.set(start, orderList.get(n3 = start++ - 1).floatValue() + n);
            setter.invoke(list.get(n3), orderList.get(n2));
            reOrderList.add(list.get(n3));
        }
    }
    
    static {
        INSTANCE = new OrderListUtils();
    }
    
    @NotNull
    public final <T> List<T> reorderIndexChecked(@NotNull final List<T> list, @NotNull final Function1<? super T, Float> getter, @NotNull final Function2<? super T, ? super Float, Unit> setter) {
        Intrinsics.checkNotNullParameter((Object)list, "list");
        Intrinsics.checkNotNullParameter((Object)getter, "getter");
        Intrinsics.checkNotNullParameter((Object)setter, "setter");
        final List<T> reorderIndex = this.reorderIndex((List<T>)list, (kotlin.jvm.functions.Function1<? super T, Float>)getter, (kotlin.jvm.functions.Function2<? super T, ? super Float, Unit>)setter);
        int n2 = 0;
        for (int i = 1; i < list.size(); i = n2) {
            final int n = i;
            n2 = n + 1;
            final float floatValue = ((Number)getter.invoke(list.get(n))).floatValue();
            final int start;
            final float n3;
            if ((n3 = Float.compare(floatValue, getter.invoke(list.get(start = i - 1)))) == 0) {
                final List<T> list2 = reorderIndex;
                final int n4 = i;
                setter.invoke(list.get(i), floatValue + 1);
                list2.add((T)list.get(n4));
            }
            else if (n3 < 0) {
                final int min = Math.min(start + 30, list.size() - 1);
                final List<Float> mutableList = CollectionsKt.mutableListOf(new Float[] { 0.0f });
                final ArrayList<Float> list3 = new ArrayList<>(list.size());
                final Iterator<T> iterator = list.iterator();
                while (iterator.hasNext()) {
                    list3.add(((Number)getter.invoke(iterator.next())).floatValue());
                }
                mutableList.addAll(list3);
                this.a(start, min, mutableList, (List<T>)reorderIndex, list, setter);
            }
        }
        return CollectionsKt.toMutableList((Collection)CollectionsKt.toSet((Iterable)reorderIndex));
    }
    
    @NotNull
    public final <T> List<T> reorderIndex(@NotNull final List<T> list, @NotNull final Function1<? super T, Float> getter, @NotNull final Function2<? super T, ? super Float, Unit> setter) {
        Intrinsics.checkNotNullParameter((Object)list, "list");
        Intrinsics.checkNotNullParameter((Object)getter, "getter");
        Intrinsics.checkNotNullParameter((Object)setter, "setter");
        final ArrayList<T> reOrderList = new ArrayList<T>();
        if (list.isEmpty()) {
            return reOrderList;
        }
        if (list.size() == 1) {
            if (getter.invoke(list.get(0)) < 0.0f) {
                final ArrayList<T> list2 = reOrderList;
                setter.invoke(list.get(0), 1.0f);
                list2.add(list.get(0));
            }
            return reOrderList;
        }
        final List<Float> mutableList = CollectionsKt.mutableListOf(new Float[] { 0.0f });
        final ArrayList<Float> list3 = new ArrayList<>(list.size());
        final Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            list3.add(((Number)getter.invoke(iterator.next())).floatValue());
        }
        mutableList.addAll(list3);
        int b;
        int a;
        for (int start = 0; (b = this.b(start, mutableList)) != CollectionsKt.getLastIndex(mutableList) - 1; start = a) {
            int n;
            if ((a = this.a(n = b + 1, mutableList)) == CollectionsKt.getLastIndex(mutableList) && ((Number)CollectionsKt.last(mutableList)).floatValue() < 0.0f) {
                final int lastIndex;
                if (n <= (lastIndex = CollectionsKt.getLastIndex(mutableList))) {
                    while (true) {
                        final int n2 = n;
                        final int n3 = lastIndex;
                        final ArrayList<T> list4 = reOrderList;
                        final int n4 = n;
                        final int n5 = n;
                        final int n6 = n;
                        final int n7 = b;
                        final int n8 = n + 1;
                        mutableList.set(n6, mutableList.get(n7) + (n - b) * 1.0f);
                        final int n9;
                        setter.invoke(list.get(n9 = n5 - 1), mutableList.get(n4));
                        list4.add((T)list.get(n9));
                        if (n2 == n3) {
                            break;
                        }
                        n = n8;
                    }
                }
                return reOrderList;
            }
            final int n10 = a;
            final List list8 = mutableList;
            this.a(b, a, mutableList, reOrderList, list, setter);
            if (n10 >= CollectionsKt.getLastIndex(list8)) {
                return reOrderList;
            }
        }
        if (((Number)CollectionsKt.last(mutableList)).floatValue() <= 0.0f) {
            final ArrayList<T> list9 = reOrderList;
            mutableList.set(CollectionsKt.getLastIndex(mutableList), mutableList.get(CollectionsKt.getLastIndex(mutableList) - 1) + 1.0f);
            setter.invoke(list.get(CollectionsKt.getLastIndex((List)list)), mutableList.get(CollectionsKt.getLastIndex(mutableList)));
            list9.add(list.get(CollectionsKt.getLastIndex((List)list)));
        }
        return reOrderList;
    }
}
