package com.onyx.android.sdk.data;

public enum SortOrder {
    Asc,
    Desc;

    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[SortOrder.values().length];
            a = iArr;
            try {
                iArr[SortOrder.Desc.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[SortOrder.Asc.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public SortOrder reverse() {
        return a.a[ordinal()] != 1 ? Desc : Asc;
    }

    public boolean isAsc() {
        return equals(Asc);
    }

    public boolean isDesc() {
        return equals(Desc);
    }
}
