package com.onyx.android.sdk.utils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.onyx.android.sdk.api.ReaderPosition;
import com.onyx.android.sdk.data.PageInfo;
import com.onyx.android.sdk.data.PageRange;
import com.onyx.android.sdk.data.ReaderPositionImpl;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/PageInfoUtils.class */
public class PageInfoUtils {
    public static final int INVALID_PAGE_NUMBER = -1;
    public static final int INVALID_POSITION_INT = -1;
    public static final int INVALID_PAGE_ID_JSON_HASH_CODE = -1;
    public static final int INVALID_LINE_NUMBER = -1;
    public static final int INVALID_PARAGRAPH_NUMBER = -1;
    public static final ReaderPosition INVALID_POSITION = ReaderPosition.dummy;
    public static final ReaderPosition FIRST_PAGE_POSITION = getPositionByPageNumber(0);
    public static final String INVALID_POSITION_STR = null;

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/PageInfoUtils$a.class */
    static class a implements Comparator<PageInfo> {
        final /* synthetic */ boolean a;

        a(boolean z) {
            this.a = z;
        }

        @Override // java.util.Comparator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(PageInfo o1, PageInfo o2) {
            int pageNumber = this.a ? o2.getPageNumber() : o1.getPageNumber();
            int pageNumber2 = this.a ? o1.getPageNumber() : o2.getPageNumber();
            return pageNumber == pageNumber2 ? (this.a ? o2.getSubPage() : o1.getSubPage()) - (this.a ? o1.getSubPage() : o2.getSubPage()) : pageNumber - pageNumber2;
        }
    }

    public static boolean isValidPosition(String position) {
        return StringUtils.isNotBlank(position);
    }

    public static boolean isInvalidPosition(ReaderPosition position) {
        return position == null || ReaderPosition.dummy.equals(position);
    }

    public static boolean isValidPage(int page) {
        return page >= 0;
    }

    public static boolean isValidPageInfo(PageInfo pageInfo) {
        return pageInfo != null && isValidPage(pageInfo.getPageNumber());
    }

    public static boolean isValidPositionInt(int positionInt) {
        return positionInt >= 0;
    }

    public static boolean equal(PageInfo pageInfo, PageInfo other) {
        if (pageInfo == null && other == null) {
            return true;
        }
        if (other == null || pageInfo == null) {
            return false;
        }
        if (pageInfo == other) {
            return true;
        }
        return pageInfo.getSubPage() == other.getSubPage() && pageInfo.getRange().equals(other.getRange()) && pageInfo.getPageNumber() == other.getPageNumber();
    }

    public static boolean equalPageRange(PageInfo pageInfo, PageInfo other) {
        if (pageInfo == null && other == null) {
            return true;
        }
        if (other == null || pageInfo == null) {
            return false;
        }
        if (pageInfo == other) {
            return true;
        }
        return pageInfo.getRange().equals(other.getRange());
    }

    public static boolean intersect(List<PageInfo> listFirst, List<PageInfo> listSecond) {
        if (CollectionUtils.isNullOrEmpty(listFirst) || CollectionUtils.isNullOrEmpty(listSecond)) {
            return false;
        }
        for (PageInfo pageInfo : listFirst) {
            Iterator<PageInfo> it = listSecond.iterator();
            while (it.hasNext()) {
                if (equal(pageInfo, it.next())) {
                    return true;
                }
            }
        }
        return false;
    }

    @NonNull
    private static Comparator<PageInfo> a() {
        return a(false);
    }

    public static boolean isReaderNotePage(PageInfo pageInfo) {
        return pageInfo != null && pageInfo.getSubPage() == 0;
    }

    public static void sort(List<PageInfo> list) {
        sort(list, false);
    }

    public static boolean isValidPageRange(PageRange pageRange) {
        return pageRange != null && isValidPosition(pageRange.getStartPosition()) && isValidPosition(pageRange.getEndPosition());
    }

    public static boolean intersect(int startPosition1, int endPosition1, int startPosition2, int endPosition2) {
        return startPosition1 <= endPosition2 && startPosition2 <= endPosition1;
    }

    public static boolean isContained(int childStart, int childEnd, int parentStart, int parentEnd) {
        return parentStart <= childStart && parentEnd >= childEnd;
    }

    public static String toShortString(PageInfo pageInfo) {
        return pageInfo == null ? "invalid pageInfo" : "PageNumber=" + pageInfo.getPageNumber() + ", " + pageInfo.getRange();
    }

    public static boolean isPointInDocumentRect(float xInDocument, float yInDocument, PageInfo pageInfo) {
        return pageInfo.getOriginRect().contains(xInDocument, yInDocument);
    }

    public static String getPageNumberForDisplay(int pageNumber) {
        return !isValidPage(pageNumber) ? TTFFont.UNKNOWN_FONT_NAME : String.valueOf(pageNumber + 1);
    }

    public static String getPositionString(int positionInt) {
        return String.valueOf(positionInt);
    }

    public static int getCompatiblePositionInt(ReaderPosition position) {
        if (position == null) {
            return -1;
        }
        return position.asInteger() >= 0 ? position.asInteger() : NumberUtils.parseInt(position.asString());
    }

    public static boolean isNumberPosition(String position) {
        return StringUtils.isNumberStr(position);
    }

    @Nullable
    public static PageInfo hitPage(List<PageInfo> pageInfoList, float x, float y) {
        for (PageInfo pageInfo : pageInfoList) {
            if (pageInfo.getVisibleRect().contains(x, y)) {
                return pageInfo;
            }
        }
        return null;
    }

    public static int getPositionInt(ReaderPosition position) {
        if (position == null) {
            return -1;
        }
        return position.asInteger();
    }

    public static ReaderPosition getPosition(String position) {
        return position == null ? INVALID_POSITION : new ReaderPositionImpl().fromString(position);
    }

    public static ReaderPosition getPositionByPageNumber(int pageNumber) {
        return new ReaderPositionImpl().fromPageIndex(pageNumber).fromInteger(pageNumber).fromString(String.valueOf(pageNumber));
    }

    public static ReaderPosition getCompatiblePositionByPageNumber(String pageNumber) {
        ReaderPosition readerPositionFromString = new ReaderPositionImpl().fromString(pageNumber);
        if (isNumberPosition(pageNumber)) {
            int i = NumberUtils.parseInt(pageNumber);
            readerPositionFromString.fromInteger(i).fromPageIndex(i);
        }
        return readerPositionFromString;
    }

    public static ReaderPosition getCompatiblePosition(String position) {
        ReaderPosition readerPositionFromString = new ReaderPositionImpl().fromString(position);
        if (isNumberPosition(position)) {
            readerPositionFromString.fromInteger(NumberUtils.parseInt(position));
        }
        return readerPositionFromString;
    }

    public static int getPageNumberByPosition(ReaderPosition position) {
        if (position == null) {
            return -1;
        }
        return position.getPageIndex();
    }

    public static boolean positionStringEquals(PageInfo pageInfo, ReaderPosition position) {
        if (pageInfo == null || position == null) {
            return false;
        }
        return StringUtils.safelyNotNullEqual(getPositionString(pageInfo.getPositionSafely()), getPositionString(position));
    }

    public static boolean equals(ReaderPosition position1, ReaderPosition position2) {
        return position1 != null && position1.equals(position2);
    }

    public static boolean compatEquals(ReaderPosition position1, ReaderPosition position2) {
        return position1 != null && position1.compatEquals(position2);
    }

    public static List<PageInfo> createPageInfoCopy(List<PageInfo> source) {
        return source == null ? Collections.emptyList() : (List) source.stream().map(PageInfo::new).collect(Collectors.toList());
    }

    public static boolean isValidPosition(ReaderPosition position) {
        return (position == null || ReaderPosition.dummy.equals(position)) ? false : true;
    }

    @NonNull
    private static Comparator<PageInfo> a(boolean desc) {
        return new a(desc);
    }

    public static void sort(List<PageInfo> list, boolean desc) {
        Collections.sort(list, a(desc));
    }

    public static String toShortString(List<PageInfo> pageInfoList) {
        if (CollectionUtils.isNullOrEmpty(pageInfoList)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Iterator<PageInfo> it = pageInfoList.iterator();
        while (it.hasNext()) {
            sb.append(toShortString(it.next())).append(LogUtils.ATTRIBUTE_SEPARATOR);
        }
        return sb.toString();
    }

    public static String getPositionString(ReaderPosition position) {
        return position == null ? INVALID_POSITION_STR : position.asString();
    }

    public static ReaderPosition getCompatiblePosition(int positionInt) {
        return new ReaderPositionImpl().fromInteger(positionInt).fromString(String.valueOf(positionInt));
    }

    public static ReaderPosition getPosition(ReaderPosition position, int positionInt) {
        if (isInvalidPosition(position)) {
            return new ReaderPositionImpl().fromInteger(positionInt).fromString(String.valueOf(positionInt));
        }
        return position.fromInteger(positionInt);
    }

    public static int getCompatiblePositionInt(String position) {
        return NumberUtils.parseInt(position);
    }

    public static boolean intersect(PageRange pageRange1, PageRange pageRange2) {
        if (isValidPageRange(pageRange1) && isValidPageRange(pageRange2)) {
            return intersect(pageRange1.getStartPosition().asInteger(), pageRange1.getEndPosition().asInteger(), pageRange2.getStartPosition().asInteger(), pageRange2.getEndPosition().asInteger());
        }
        return false;
    }

    public static boolean equal(List<PageInfo> listFirst, List<PageInfo> listSecond) {
        int size = CollectionUtils.getSize(listFirst);
        if (size != CollectionUtils.getSize(listSecond) || size == 0) {
            return false;
        }
        Comparator<PageInfo> comparatorA = a();
        Collections.sort(listFirst, comparatorA);
        Collections.sort(listSecond, comparatorA);
        for (int i = 0; i < size; i++) {
            if (!equal(listFirst.get(i), listSecond.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static ReaderPosition getPosition(ReaderPosition position, String strPosition) {
        if (isInvalidPosition(position)) {
            ReaderPosition readerPositionFromString = new ReaderPositionImpl().fromString(strPosition);
            if (isNumberPosition(strPosition)) {
                readerPositionFromString.fromInteger(NumberUtils.parseInt(strPosition));
            }
            return readerPositionFromString;
        }
        return position.fromString(strPosition);
    }

    public static ReaderPosition getPosition(String position, int positionInt) {
        return new ReaderPositionImpl().fromInteger(positionInt).fromString(position);
    }
}
