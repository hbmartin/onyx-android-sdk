package com.onyx.android.sdk.utils;

import com.onyx.android.sdk.data.SortBy;

final class FileUtils$h {
    static final int[] a = new int[SortBy.values().length];
    static {
        try { a[SortBy.Name.ordinal()] = 1; } catch (NoSuchFieldError ignored) {}
        try { a[SortBy.CreationTime.ordinal()] = 2; } catch (NoSuchFieldError ignored) {}
        try { a[SortBy.FileType.ordinal()] = 3; } catch (NoSuchFieldError ignored) {}
        try { a[SortBy.Size.ordinal()] = 4; } catch (NoSuchFieldError ignored) {}
    }
}
