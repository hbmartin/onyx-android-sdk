package com.onyx.android.sdk.extension;

import android.graphics.RectF;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.LineUtils;
import com.onyx.android.sdk.utils.RectUtils;
import com.onyx.android.sdk.utils.TTFFont;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010 \n\u0002\b\b\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\n\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fJ\u0016\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\fJ\u001a\u0010\u000f\u001a\u00020\b2\b\u0010\u0010\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0011\u001a\u00020\fH\u0002J\u0016\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\fJ\u0016\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\fJ\u0016\u0010\u0017\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\fJ\u0016\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\fJ\u0014\u0010\u001a\u001a\u00020\u0013*\u00020\f2\b\b\u0002\u0010\u001b\u001a\u00020\bJ\u0018\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\f0\u001d*\b\u0012\u0004\u0012\u00020\f0\u001dH\u0007J\u0018\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\f0\u001d*\b\u0012\u0004\u0012\u00020\f0\u001dH\u0007J \u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\f0\u001d*\b\u0012\u0004\u0012\u00020\f0\u001d2\u0006\u0010 \u001a\u00020\u0013H\u0007J\u0018\u0010!\u001a\b\u0012\u0004\u0012\u00020\f0\u001d*\b\u0012\u0004\u0012\u00020\f0\u001dH\u0007J\u0016\u0010\"\u001a\b\u0012\u0004\u0012\u00020\f0\u001d*\b\u0012\u0004\u0012\u00020\f0\u001dJ\u0016\u0010#\u001a\b\u0012\u0004\u0012\u00020\f0\u001d*\b\u0012\u0004\u0012\u00020\f0\u001dJ\u0016\u0010$\u001a\b\u0012\u0004\u0012\u00020\f0\u001d*\b\u0012\u0004\u0012\u00020\f0\u001dR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/onyx/android/sdk/extension/CharRectList;", TTFFont.UNKNOWN_FONT_NAME, "()V", "PUNCTUATION_DIFF_RATIO", TTFFont.UNKNOWN_FONT_NAME, "PUNCTUATION_WORD_VERTICAL_SPACING", "TOLERANCE", "WORD_EXPAND", TTFFont.UNKNOWN_FONT_NAME, "WORD_SPACING", "compareBaseLine", "rect1", "Landroid/graphics/RectF;", "rect2", "compareBaseLineInVertical", "computeWordSpace", "lastWordRect", "wordRect", "isSameBaseLine", TTFFont.UNKNOWN_FONT_NAME, "o1", "o2", "isSameBaseLineInVertical", "isVerticalWordInBlock", "blockRect", "isWordInBlock", "isEmptyOnScreen", "epsilon", "mergeBlockRectanglesByVerticalWord", TTFFont.UNKNOWN_FONT_NAME, "mergeBlockRectanglesByWord", "mergeLineRectanglesByWord", "isHorizontalChar", "mergeRectanglesByBaseLine", "mergeRectanglesByMostHorizontalBaseLine", "mergeRectanglesByMostVerticalBaseLine", "mergeRectanglesByVerticalBaseLine", "onyxsdk-base_release"})
public final class CharRectList {

    @NotNull
    public static final CharRectList INSTANCE = new CharRectList();
    private static final int a = 20;
    private static final float b = 1.5f;
    private static final int c = -3;
    private static final int d = 2;
    private static final int e = 10;

    private CharRectList() {
    }

    @JvmStatic
    @NotNull
    public static final List<RectF> mergeLineRectanglesByWord(@NotNull List<? extends RectF> list, boolean isHorizontalChar) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (!((RectF) obj).isEmpty()) {
                arrayList.add((RectF) obj);
            }
        }
        List<RectF> listMergeBlockRectanglesByWord = isHorizontalChar ? mergeBlockRectanglesByWord(arrayList) : mergeBlockRectanglesByVerticalWord(arrayList);
        return isHorizontalChar ? INSTANCE.mergeRectanglesByMostHorizontalBaseLine(listMergeBlockRectanglesByWord) : INSTANCE.mergeRectanglesByMostVerticalBaseLine(listMergeBlockRectanglesByWord);
    }

    @JvmStatic
    @NotNull
    public static final List<RectF> mergeRectanglesByBaseLine(@NotNull List<? extends RectF> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        ArrayList<RectF> arrayList = new ArrayList();
        for (Object obj : list) {
            if (!((RectF) obj).isEmpty()) {
                arrayList.add((RectF) obj);
            }
        }
        if (arrayList.isEmpty()) {
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList();
        for (RectF rectF : arrayList) {
            if (arrayList2.isEmpty()) {
                arrayList2.add(new RectF(rectF));
            } else {
                RectF rectF2 = (RectF) arrayList2.get(arrayList2.size() - 1);
                if (INSTANCE.compareBaseLine(rectF, rectF2) == 0) {
                    rectF2.union(rectF);
                } else {
                    arrayList2.add(new RectF(rectF));
                }
            }
        }
        return arrayList2;
    }

    @JvmStatic
    @NotNull
    public static final List<RectF> mergeBlockRectanglesByVerticalWord(@NotNull List<? extends RectF> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        ArrayList<RectF> arrayList = new ArrayList();
        for (Object obj : list) {
            if (!((RectF) obj).isEmpty()) {
                arrayList.add((RectF) obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        RectF rectF = null;
        RectF rectF2 = null;
        for (RectF rectF3 : arrayList) {
            RectF rectF4 = rectF2;
            if (rectF4 == null) {
                rectF2 = rectF;
                RectF rectF5 = new RectF(rectF3);
                arrayList2.add(rectF2);
                rectF = rectF3;
            } else {
                CharRectList charRectList = INSTANCE;
                if (RectUtils.getDistance(rectF, rectF3) >= charRectList.a(rectF, rectF3) || !charRectList.isVerticalWordInBlock(rectF2, rectF3)) {
                    rectF2 = rectF;
                    RectF rectF6 = new RectF(rectF3);
                    arrayList2.add(rectF2);
                } else {
                    rectF2.union(rectF3);
                }
                rectF = rectF3;
            }
        }
        return arrayList2;
    }

    private final float a(RectF lastWordRect, RectF wordRect) {
        Intrinsics.checkNotNull(lastWordRect);
        return Float.max((Float.max(lastWordRect.width(), lastWordRect.height()) + Float.max(wordRect.width(), wordRect.height())) * b, 20.0f);
    }

    @JvmStatic
    @NotNull
    public static final List<RectF> mergeBlockRectanglesByWord(@NotNull List<? extends RectF> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        ArrayList<RectF> arrayList = new ArrayList();
        for (Object obj : list) {
            if (!((RectF) obj).isEmpty()) {
                arrayList.add((RectF) obj);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        RectF rectF = null;
        RectF rectF2 = null;
        for (RectF rectF3 : arrayList) {
            RectF rectF4 = rectF2;
            if (rectF4 == null) {
                rectF2 = rectF;
                RectF rectF5 = new RectF(rectF3);
                arrayList2.add(rectF2);
                rectF = rectF3;
            } else {
                CharRectList charRectList = INSTANCE;
                if (RectUtils.getDistance(rectF, rectF3) >= charRectList.a(rectF, rectF3) || !charRectList.isWordInBlock(rectF2, rectF3)) {
                    rectF2 = rectF;
                    RectF rectF6 = new RectF(rectF3);
                    arrayList2.add(rectF2);
                } else {
                    rectF2.union(rectF3);
                }
                rectF = rectF3;
            }
        }
        return arrayList2;
    }

    public static /* synthetic */ boolean isEmptyOnScreen$default(CharRectList charRectList, RectF rectF, float f, int i, Object obj) {
        if ((i & 1) != 0) {
            f = 1.0f;
        }
        return charRectList.isEmptyOnScreen(rectF, f);
    }

    @NotNull
    public final List<RectF> mergeRectanglesByMostHorizontalBaseLine(@NotNull List<? extends RectF> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            RectF rectF = (RectF) obj;
            if (!rectF.isEmpty() && LineUtils.isVerticalLine(rectF.width(), rectF.height())) {
                arrayList.add((RectF) obj);
            }
        }
        List mutableList = CollectionsKt.toMutableList(list);
        mutableList.removeAll(arrayList);
        Debug.d(AnyKt.toSimpleNameString(list), "horizontal count = " + mutableList.size() + ", vertical count = " + arrayList.size(), new Object[0]);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.addAll(mergeRectanglesByBaseLine(mutableList));
        arrayList2.addAll(mergeRectanglesByVerticalBaseLine(arrayList));
        return arrayList2;
    }

    @NotNull
    public final List<RectF> mergeRectanglesByVerticalBaseLine(@NotNull List<? extends RectF> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        ArrayList<RectF> arrayList = new ArrayList();
        for (Object obj : list) {
            if (!((RectF) obj).isEmpty()) {
                arrayList.add((RectF) obj);
            }
        }
        if (arrayList.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList2 = new ArrayList();
        for (RectF rectF : arrayList) {
            if (arrayList2.isEmpty()) {
                arrayList2.add(new RectF(rectF));
            } else {
                RectF rectF2 = (RectF) arrayList2.get(arrayList2.size() - 1);
                if (compareBaseLineInVertical(rectF, rectF2) == 0) {
                    rectF2.union(rectF);
                } else {
                    arrayList2.add(new RectF(rectF));
                }
            }
        }
        return arrayList2;
    }

    @NotNull
    public final List<RectF> mergeRectanglesByMostVerticalBaseLine(@NotNull List<? extends RectF> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            RectF rectF = (RectF) obj;
            if (!rectF.isEmpty() && LineUtils.isHorizontalLine(rectF.width(), rectF.height())) {
                arrayList.add(obj);
            }
        }
        List<? extends RectF> mutableList = CollectionsKt.toMutableList(list);
        mutableList.removeAll(arrayList);
        Debug.d(AnyKt.toSimpleNameString(list), "vertical count = " + mutableList.size() + ", horizontal count = " + arrayList.size(), new Object[0]);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.addAll(mergeRectanglesByVerticalBaseLine(mutableList));
        arrayList2.addAll(mergeRectanglesByBaseLine(arrayList));
        return arrayList2;
    }

    public final boolean isWordInBlock(@NotNull RectF blockRect, @NotNull RectF wordRect) {
        Intrinsics.checkNotNullParameter(blockRect, "blockRect");
        Intrinsics.checkNotNullParameter(wordRect, "wordRect");
        return compareBaseLine(blockRect, wordRect) == 0 || ((((wordRect.height() * ((float) 2)) > blockRect.height() ? 1 : ((wordRect.height() * ((float) 2)) == blockRect.height() ? 0 : -1)) < 0) && blockRect.bottom - wordRect.top > -3.0f);
    }

    public final boolean isVerticalWordInBlock(@NotNull RectF blockRect, @NotNull RectF wordRect) {
        Intrinsics.checkNotNullParameter(blockRect, "blockRect");
        Intrinsics.checkNotNullParameter(wordRect, "wordRect");
        return compareBaseLineInVertical(blockRect, wordRect) == 0;
    }

    public final int compareBaseLineInVertical(@NotNull RectF rect1, @NotNull RectF rect2) {
        Intrinsics.checkNotNullParameter(rect1, "rect1");
        Intrinsics.checkNotNullParameter(rect2, "rect2");
        int i = (int) (rect1.left - rect2.left);
        int i2 = i;
        if (Math.abs(i) < 10 && rect1.left < rect2.right && rect2.left < rect1.right) {
            return 0;
        }
        float f = rect1.right;
        float f2 = rect2.right;
        if (f <= f2 && rect1.left >= rect2.left) {
            return 0;
        }
        if (f2 <= f && rect2.left >= rect1.left) {
            return 0;
        }
        float fMin = Math.min(f, f2) - Float.max(rect1.left, rect2.left);
        float f3 = 2;
        if (fMin > rect1.width() / f3 || fMin > rect2.width() / f3) {
            i2 = 0;
        }
        return i2;
    }

    public final boolean isSameBaseLineInVertical(@NotNull RectF o1, @NotNull RectF o2) {
        Intrinsics.checkNotNullParameter(o1, "o1");
        Intrinsics.checkNotNullParameter(o2, "o2");
        return compareBaseLineInVertical(o1, o2) == 0;
    }

    public final int compareBaseLine(@NotNull RectF rect1, @NotNull RectF rect2) {
        Intrinsics.checkNotNullParameter(rect1, "rect1");
        Intrinsics.checkNotNullParameter(rect2, "rect2");
        int i = (int) (rect1.bottom - rect2.bottom);
        int i2 = i;
        if (Math.abs(i) < 10 && rect1.top < rect2.bottom && rect2.top < rect1.bottom) {
            return 0;
        }
        float f = rect1.top;
        float f2 = rect2.top;
        if (f <= f2 && rect1.bottom >= rect2.bottom) {
            return 0;
        }
        if (f2 <= f && rect2.bottom >= rect1.bottom) {
            return 0;
        }
        float fMin = Math.min(rect1.bottom, rect2.bottom) - Float.max(rect1.top, rect2.top);
        float f3 = 2;
        if (fMin > rect1.height() / f3 || fMin > rect2.height() / f3) {
            i2 = 0;
        }
        return i2;
    }

    public final boolean isSameBaseLine(@NotNull RectF o1, @NotNull RectF o2) {
        Intrinsics.checkNotNullParameter(o1, "o1");
        Intrinsics.checkNotNullParameter(o2, "o2");
        return compareBaseLine(o1, o2) == 0;
    }

    public final boolean isEmptyOnScreen(@NotNull RectF $this$isEmptyOnScreen, float epsilon) {
        Intrinsics.checkNotNullParameter($this$isEmptyOnScreen, "<this>");
        return $this$isEmptyOnScreen.width() < epsilon || $this$isEmptyOnScreen.height() < epsilon;
    }
}
