// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.data.note;

import java.util.Iterator;
import kotlin.collections.CollectionsKt;
import org.jetbrains.annotations.Nullable;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.DefaultConstructorMarker;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u0000 \r2\u00020\u0001:\u0001\rB\u0005¢\u0006\u0002\u0010\u0002J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001H\u0096\u0002R \u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t¨\u0006\u000e" }, d2 = { "Lcom/onyx/android/sdk/data/note/LayerList;", "", "()V", "layerList", "", "Lcom/onyx/android/sdk/data/note/LayerInfo;", "getLayerList", "()Ljava/util/List;", "setLayerList", "(Ljava/util/List;)V", "equals", "", "other", "Companion", "onyxsdk-base_release" })
public final class LayerList
{
    @NotNull
    public static final Companion Companion;
    @NotNull
    private List<LayerInfo> a;
    
    public LayerList() {
        this.a = new ArrayList<LayerInfo>();
    }
    
    static {
        Companion = new Companion(null);
    }
    
    @NotNull
    public final List<LayerInfo> getLayerList() {
        return this.a;
    }
    
    public final void setLayerList(@NotNull final List<LayerInfo> value) {
        Intrinsics.checkNotNullParameter((Object)value, "<set-?>");
        this.a = value;
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (!(other instanceof LayerList)) {
            return false;
        }
        final LayerList list;
        if (this.a.size() != (list = (LayerList)other).a.size()) {
            return false;
        }
        final List<LayerInfo> a = this.a;
        int n = 0;
        final Iterator<LayerInfo> iterator = a.iterator();
        while (iterator.hasNext()) {
            final int n2 = n;
            final LayerInfo next = iterator.next();
            final int n3 = n + 1;
            if (n2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            if (!next.equals(list.getLayerList().get(n))) {
                return false;
            }
            n = n3;
        }
        return true;
    }
    
    @Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¨\u0006\u0007" }, d2 = { "Lcom/onyx/android/sdk/data/note/LayerList$Companion;", "", "()V", "cloneList", "", "Lcom/onyx/android/sdk/data/note/LayerInfo;", "srcList", "onyxsdk-base_release" })
    public static final class Companion
    {
        private Companion() {
        }
        
        public Companion(DefaultConstructorMarker marker) {
            this();
        }

        @NotNull
        public final List<LayerInfo> cloneList(@NotNull final List<? extends LayerInfo> srcList) {
            Intrinsics.checkNotNullParameter((Object)srcList, "srcList");
            if (srcList.isEmpty()) {
                return CollectionsKt.emptyList();
            }
            final ArrayList<LayerInfo> list = new ArrayList<LayerInfo>();
            final Iterator<? extends LayerInfo> iterator = srcList.iterator();
            while (iterator.hasNext()) {
                final ArrayList<LayerInfo> list2 = list;
                try {
                    final LayerInfo clone = iterator.next().clone();
                    Intrinsics.checkNotNullExpressionValue((Object)clone, "it.clone()");
                    list2.add(clone);
                } catch (CloneNotSupportedException exception) {
                    // LayerInfo implements Cloneable, so this indicates a broken runtime contract.
                    throw new AssertionError(exception);
                }
            }
            return list;
        }
    }
}
