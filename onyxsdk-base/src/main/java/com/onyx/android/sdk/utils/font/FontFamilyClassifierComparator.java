package com.onyx.android.sdk.utils.font;

import com.onyx.android.sdk.data.FontInfo;
import java.util.Comparator;
import kotlin.comparisons.ComparisonsKt;

/** Kotlin's original inlined sortedBy comparator, retained under its binary name. */
final class FontFamilyClassifier$groupFonts$$inlined$sortedBy$1<T> implements Comparator<T> {
    @Override
    public int compare(T first, T second) {
        FontInfo left = (FontInfo) first;
        FontInfo right = (FontInfo) second;
        return ComparisonsKt.compareValues(left.getFontUniqueName(), right.getFontUniqueName());
    }
}
