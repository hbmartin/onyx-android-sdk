// 
// 

package com.onyx.android.sdk.common.collections;

import com.onyx.android.sdk.extension.AnyKt;
import java.util.Map;
import kotlin.TuplesKt;
import kotlin.Pair;
import org.jetbrains.annotations.Nullable;
import java.util.function.Predicate;
import java.util.Collection;
import java.util.Iterator;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import java.util.HashMap;
import org.jetbrains.annotations.NotNull;
import kotlin.jvm.functions.Function1;
import kotlin.Metadata;
import java.util.ArrayList;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000^\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000*\u0004\b\u0000\u0010\u00012\u0012\u0012\u0004\u0012\u0002H\u00010\u0002j\b\u0012\u0004\u0012\u0002H\u0001`\u0003B2\u0012!\u0010\u0004\u001a\u001d\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\t0\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0015\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0016J\u001d\u0010\u0013\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0019J\u001e\u0010\u001a\u001a\u00020\u00142\u0006\u0010\u0018\u001a\u00020\u000b2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00000\u001cH\u0016J\u0016\u0010\u001a\u001a\u00020\u00142\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00000\u001cH\u0016J\u0006\u0010\u001d\u001a\u00020\u0014J\b\u0010\u001e\u001a\u00020\u0017H\u0016J\b\u0010\u001f\u001a\u00020\u0017H\u0002J\f\u0010 \u001a\b\u0012\u0004\u0012\u00028\u00000\u0000J\u0006\u0010!\u001a\u00020\tJ\u001c\u0010\"\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00028\u0000\u0018\u00010#2\u0006\u0010$\u001a\u00020\tJ\u0015\u0010%\u001a\u0004\u0018\u00018\u00002\u0006\u0010$\u001a\u00020\t¢\u0006\u0002\u0010&J\u000e\u0010'\u001a\u00020\u000b2\u0006\u0010$\u001a\u00020\tJ\u001f\u0010(\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000b2\b\u0010\u0015\u001a\u0004\u0018\u00018\u0000H\u0002¢\u0006\u0002\u0010\u0019J\b\u0010)\u001a\u00020\u0017H\u0002J\u001a\u0010*\u001a\u00020\u00172\u0006\u0010+\u001a\u00020\u000b2\b\b\u0002\u0010,\u001a\u00020\u000bH\u0002J\u0015\u0010-\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0016J\u0016\u0010.\u001a\u00020\u00142\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00000\u001cH\u0016J\u0015\u0010/\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u000bH\u0016¢\u0006\u0002\u00100J\u0018\u00101\u001a\u00020\u00142\u000e\u00102\u001a\n\u0012\u0006\b\u0000\u0012\u00028\u000003H\u0016J\u0017\u00104\u001a\u00020\u00172\b\u0010\u0015\u001a\u0004\u0018\u00018\u0000H\u0002¢\u0006\u0002\u00105J\u001f\u00104\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u000b2\b\u0010\u0015\u001a\u0004\u0018\u00018\u0000H\u0002¢\u0006\u0002\u0010\u0019J\u0018\u00106\u001a\u00020\u00172\u0006\u0010+\u001a\u00020\u000b2\u0006\u0010,\u001a\u00020\u000bH\u0002J\u0018\u00107\u001a\u00020\u00172\u0006\u00108\u001a\u00020\u000b2\u0006\u00109\u001a\u00020\u000bH\u0014J\u001c\u0010:\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00028\u0000\u0018\u00010#2\u0006\u0010$\u001a\u00020\tJ\u001e\u0010;\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00028\u0000H\u0096\u0002¢\u0006\u0002\u0010<J\u0016\u0010=\u001a\u00020\u00142\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00000\u001cH\u0002R*\u0010\r\u001a\u001e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00028\u00000\u000ej\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00028\u0000`\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R*\u0010\u0010\u001a\u001e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u000b0\u000ej\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u000b`\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R,\u0010\u0004\u001a\u001d\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b\u0006\u0012\b\b\u0007\u0012\u0004\b\b(\b\u0012\u0004\u0012\u00020\t0\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006>" }, d2 = { "Lcom/onyx/android/sdk/common/collections/StringIndexedArrayList;", "E", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "stringIndexGenerator", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "e", "", "initialCapacity", "", "(Lkotlin/jvm/functions/Function1;I)V", "idToElementMap", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "idToIndexMap", "getStringIndexGenerator", "()Lkotlin/jvm/functions/Function1;", "add", "", "element", "(Ljava/lang/Object;)Z", "", "index", "(ILjava/lang/Object;)V", "addAll", "elements", "", "checkIndexSize", "clear", "clearAllIndex", "copy", "getDebugString", "getIndexElementPairWithId", "Lkotlin/Pair;", "id", "getWithId", "(Ljava/lang/String;)Ljava/lang/Object;", "indexOfId", "putIndex", "rebuildAllIndex", "rebuildRangeIndex", "from", "to", "remove", "removeAll", "removeAt", "(I)Ljava/lang/Object;", "removeIf", "filter", "Ljava/util/function/Predicate;", "removeIndex", "(Ljava/lang/Object;)V", "removeIndexRange", "removeRange", "fromIndex", "toIndex", "removeWithId", "set", "(ILjava/lang/Object;)Ljava/lang/Object;", "superAddAll", "onyxsdk-base_release" })
public final class StringIndexedArrayList<E> extends ArrayList<E>
{
    @NotNull
    private final Function1<E, String> a;
    @NotNull
    private final HashMap<String, E> b;
    @NotNull
    private final HashMap<String, Integer> c;
    
    public StringIndexedArrayList(@NotNull final Function1<? super E, String> stringIndexGenerator, final int initialCapacity) {
        super(initialCapacity);
        Intrinsics.checkNotNullParameter((Object)stringIndexGenerator, "stringIndexGenerator");
        this.a = (Function1<E, String>)stringIndexGenerator;
        this.b = new HashMap<String, E>(initialCapacity);
        this.c = new HashMap<String, Integer>(initialCapacity);
    }

    public StringIndexedArrayList(Function1 stringIndexGenerator, int initialCapacity, int mask,
                                  DefaultConstructorMarker marker) {
        this((Function1<? super E, String>) stringIndexGenerator,
                (mask & 2) != 0 ? 10 : initialCapacity);
    }
    
    private final void a(final int index, final E element) {
        if (element == null) {
            return;
        }
        final String s = this.a.invoke(element);
        this.b.put(s, element);
        this.c.put(s, index);
    }
    
    private final void b(final int index, final E element) {
        if (element == null) {
            return;
        }
        final String s = this.a.invoke(element);
        this.b.remove(s);
        this.c.remove(s);
        a(this, index, 0, 2, null);
    }
    
    private final void a(final E element) {
        if (element == null) {
            return;
        }
        final Integer n;
        if ((n = this.c.get(this.a.invoke(element))) != null) {
            this.b(n, element);
        }
    }
    
    private final void b(final int from, final int to) {
        int i = from;
        while (i < to) {
            final String s = (String)this.a.invoke(this.get(i++));
            this.b.remove(s);
            this.c.remove(s);
        }
        a(this, from, 0, 2, null);
    }
    
    private final void b() {
        this.a();
        int index = 0;
        final Iterator<E> iterator = this.iterator();
        while (iterator.hasNext()) {
            final int n = index;
            final E next = iterator.next();
            final int n2 = index + 1;
            if (n < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            this.a(index, next);
            index = n2;
        }
    }
    
    private final void a(int from, final int to) {
        if (from >= this.size()) {
            return;
        }
        while (from < to) {
            final int n = from + 1;
            this.a(from, this.get(from));
            from = n;
        }
    }
    
    static /* synthetic */ void a(final StringIndexedArrayList list, final int from, int size, final int n, final Object o) {
        if ((n & 0x2) != 0x0) {
            size = list.size();
        }
        list.a(from, size);
    }
    
    private final void a() {
        this.b.clear();
        this.c.clear();
    }
    
    private final boolean a(final Collection<? extends E> elements) {
        return super.addAll(elements);
    }
    
    @NotNull
    public final Function1<E, String> getStringIndexGenerator() {
        return this.a;
    }
    
    @Override
    public boolean add(final E element) {
        final int size = this.size();
        final boolean add;
        if (add = super.add(element)) {
            this.a(size, element);
        }
        return add;
    }
    
    @Override
    public boolean addAll(@NotNull final Collection<? extends E> elements) {
        Intrinsics.checkNotNullParameter((Object)elements, "elements");
        final int size = this.size();
        final boolean addAll;
        if (addAll = super.addAll(elements)) {
            a(this, size, 0, 2, null);
        }
        return addAll;
    }
    
    @Override
    public void add(final int index, final E element) {
        super.add(index, element);
        a(this, index, 0, 2, null);
    }
    
    @Override
    public boolean addAll(final int index, @NotNull final Collection<? extends E> elements) {
        Intrinsics.checkNotNullParameter((Object)elements, "elements");
        final boolean addAll;
        if (addAll = super.addAll(index, elements)) {
            a(this, index, 0, 2, null);
        }
        return addAll;
    }
    
    @Override
    public E set(final int index, final E element) {
        this.a(index, element);
        final E set = super.set(index, element);
        this.a(set);
        return set;
    }
    
    @Override
    public boolean remove(final Object element) {
        final boolean remove;
        if (remove = super.remove(element)) {
            this.b();
        }
        return remove;
    }
    
    @Override
    public boolean removeAll(@NotNull final Collection<? extends Object> elements) {
        Intrinsics.checkNotNullParameter((Object)elements, "elements");
        final boolean removeAll;
        if (removeAll = super.removeAll(elements)) {
            this.b();
        }
        return removeAll;
    }
    
    public E removeAt(final int index) {
        final E remove = super.remove(index);
        this.b(index, remove);
        return remove;
    }
    
    @Override
    protected void removeRange(final int fromIndex, final int toIndex) {
        super.removeRange(fromIndex, toIndex);
        this.b(fromIndex, toIndex);
    }
    
    @Override
    public boolean removeIf(@NotNull final Predicate<? super E> filter) {
        Intrinsics.checkNotNullParameter((Object)filter, "filter");
        final boolean removeIf;
        if (removeIf = super.removeIf(filter)) {
            this.b();
        }
        return removeIf;
    }
    
    @Override
    public void clear() {
        this.a();
        super.clear();
    }
    
    @Nullable
    public final E getWithId(@NotNull final String id) {
        Intrinsics.checkNotNullParameter((Object)id, "id");
        return this.b.get(id);
    }
    
    public final int indexOfId(@NotNull final String id) {
        Intrinsics.checkNotNullParameter((Object)id, "id");
        Integer value;
        if ((value = this.c.get(id)) == null) {
            value = -1;
        }
        return value.intValue();
    }
    
    @Nullable
    public final Pair<Integer, E> getIndexElementPairWithId(@NotNull final String id) {
        Intrinsics.checkNotNullParameter((Object)id, "id");
        final E value;
        if ((value = this.b.get(id)) == null) {
            return null;
        }
        final Integer n;
        if ((n = this.c.get(id)) == null) {
            return null;
        }
        return new Pair<>(n, value);
    }
    
    @Nullable
    public final Pair<Integer, E> removeWithId(@NotNull final String id) {
        Intrinsics.checkNotNullParameter((Object)id, "id");
        final E remove;
        if ((remove = this.b.remove(id)) == null) {
            return null;
        }
        final Integer n;
        if ((n = this.c.get(id)) == null) {
            return null;
        }
        final int intValue;
        this.remove(intValue = n.intValue());
        return new Pair<>(intValue, remove);
    }
    
    @NotNull
    public final StringIndexedArrayList<E> copy() {
        final StringIndexedArrayList list = new StringIndexedArrayList((kotlin.jvm.functions.Function1<? super Object, String>)this.a, this.size());
        list.a((Collection<? extends E>)this);
        list.b.putAll((Map<? extends String, ? extends E>)this.b);
        list.c.putAll(this.c);
        return list;
    }
    
    public final boolean checkIndexSize() {
        return this.size() == this.b.size() && this.size() == this.c.size();
    }
    
    @NotNull
    public final String getDebugString() {
        return AnyKt.toSimpleNameString(this) + ", list.size = " + this.size() + ", idToElementMap.size = " + this.b.size() + ", idToIndexMap.size = " + this.c.size();
    }
    
    @Override
    public final /* bridge */ E remove(final int index) {
        return this.removeAt(index);
    }
    
    public /* bridge */ int getSize() {
        return super.size();
    }
    
    @Override
    public final /* bridge */ int size() {
        return this.getSize();
    }
}
