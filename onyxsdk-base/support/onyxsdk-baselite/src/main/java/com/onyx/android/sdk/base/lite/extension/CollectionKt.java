package com.onyx.android.sdk.base.lite.extension;

import com.onyx.android.sdk.base.lite.utils.MathUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: compiled from: Collection.kt */
/* JADX INFO: loaded from: baselite.jar:com/onyx/android/sdk/base/lite/extension/CollectionKt.class */
@Metadata(mv = {1, 6, 0}, k = 2, xi = 48, d1 = {"\u0000F\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001f\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\u0010\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\u0007\n\u0002\b\t\u001a'\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u0001H\u0002¢\u0006\u0002\u0010\u0005\u001a*\u0010\u0006\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\b2\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u00070\n\u001a\u001c\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00020\f\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\r\u001a\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\f\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\f\u001a)\u0010\u000f\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0011¢\u0006\u0002\u0010\u0012\u001a\u0010\u0010\u0013\u001a\u00020\u0014*\b\u0012\u0002\b\u0003\u0018\u00010\u0015\u001a\u0017\u0010\u0016\u001a\u0004\u0018\u00010\u0017*\b\u0012\u0004\u0012\u00020\u00170\b¢\u0006\u0002\u0010\u0018\u001a&\u0010\u0019\u001a\u00020\u0007\"\u0004\b\u0000\u0010\u0002*\u0002H\u00022\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0015H\u0086\f¢\u0006\u0002\u0010\u001b\u001a'\u0010\u001c\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u0001H\u0002¢\u0006\u0002\u0010\u0005\u001a,\u0010\u001d\u001a\b\u0012\u0004\u0012\u0002H\u00020\f\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\r2\u0006\u0010\u001e\u001a\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u0014¨\u0006 "}, d2 = {"add", "", "T", "", "target", "(Ljava/util/Collection;Ljava/lang/Object;)V", "contains", "", "", "predicate", "Lkotlin/Function1;", "copy", "", "", "ensureMutableList", "firstOrPut", "defaultValue", "Lkotlin/Function0;", "(Ljava/util/List;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "getSize", "", "", "min", "", "(Ljava/lang/Iterable;)Ljava/lang/Float;", "outside", "collection", "(Ljava/lang/Object;Ljava/util/Collection;)Z", "safeAdd", "safelySubList", "fromIndex", "toIndex", "sdk-baselite_release"})
public final class CollectionKt {
    public static final <T> void add(@Nullable Collection<T> collection, @Nullable T t) {
        if (collection != null && t != null) {
            collection.add(t);
        }
    }

    @NotNull
    public static final <T> List<T> ensureMutableList(@Nullable List<T> list) {
        return list == null ? new ArrayList() : list;
    }

    @Nullable
    public static final Float min(@NotNull Iterable<Float> iterable) {
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        return CollectionsKt.minOrNull(iterable);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0045 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:11:0x0049 A[ORIG_RETURN, RETURN] */
    public static final <T> boolean contains(@NotNull Iterable<? extends T> iterable, @NotNull Function1<? super T, Boolean> function1) {
        Object obj;
        Intrinsics.checkNotNullParameter(iterable, "<this>");
        Intrinsics.checkNotNullParameter(function1, "predicate");
        for (T element$iv : iterable) {
            if (((Boolean) function1.invoke(element$iv)).booleanValue()) {
                obj = element$iv;
                if (obj != null) {
                    return true;
                }
                return false;
            }
        }
        obj = null;
        if (obj != null) {
            return true;
        }
        return false;
    }

    public static final <T> T firstOrPut(@NotNull List<T> list, @NotNull Function0<? extends T> function0) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(function0, "defaultValue");
        if (list.isEmpty()) {
            safeAdd(list, function0.invoke());
        }
        return (T) CollectionsKt.first(list);
    }

    public static final int getSize(@Nullable Collection<?> collection) {
        if (collection == null) {
            return 0;
        }
        return collection.size();
    }

    public static final <T> void safeAdd(@Nullable Collection<T> collection, @Nullable T t) {
        add(collection, t);
    }

    @NotNull
    public static final <T> List<T> safelySubList(@NotNull List<? extends T> list, int fromIndex, int toIndex) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        if (list.isEmpty()) {
            return new ArrayList();
        }
        if (fromIndex > toIndex) {
            return new ArrayList();
        }
        int size = getSize(list);
        return CollectionsKt.toMutableList(list.subList(MathUtils.INSTANCE.clamp(fromIndex, 0, size), MathUtils.INSTANCE.clamp(toIndex, 0, size)));
    }

    public static final <T> boolean outside(T t, @NotNull Collection<? extends T> collection) {
        Intrinsics.checkNotNullParameter(collection, "collection");
        return !collection.contains(t);
    }

    @NotNull
    public static final <T> List<T> copy(@NotNull List<? extends T> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        return CollectionsKt.toMutableList(list);
    }
}
