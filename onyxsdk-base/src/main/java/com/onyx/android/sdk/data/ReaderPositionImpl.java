package com.onyx.android.sdk.data;

import com.onyx.android.sdk.api.ReaderPosition;
import com.onyx.android.sdk.utils.StringUtils;
import com.onyx.android.sdk.utils.TTFFont;
import java.util.Objects;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/ReaderPositionImpl.class */
public class ReaderPositionImpl implements ReaderPosition {
    public static final int POSITION_TYPE_UNKNOWN = 0;
    public static final int POSITION_TYPE_INT = 1;
    public static final int POSITION_TYPE_STRING = 2;
    public static final int POSITION_TYPE_PAGE_INDEX = 4;
    private int pageIndex = -1;
    private int intPosition = -1;
    private String strPosition = TTFFont.UNKNOWN_FONT_NAME;
    private int positionType = 0;

    private int compareIntPosition(ReaderPosition otherPosition) {
        int iCompare = 0;
        if (isIntPositionType(this.positionType) && isIntPositionType(otherPosition.positionType())) {
            iCompare = Integer.compare(this.intPosition, otherPosition.asInteger());
        }
        return iCompare;
    }

    private int comparePageIndexPosition(ReaderPosition otherPosition) {
        int iCompare = 0;
        if (isPageIndexPositionType(this.positionType) && isPageIndexPositionType(otherPosition.positionType())) {
            iCompare = Integer.compare(this.pageIndex, otherPosition.getPageIndex());
        }
        return iCompare;
    }

    private int compareStrPosition(ReaderPosition otherPosition) {
        int iCompareTo = 0;
        if (isStringPositionType(this.positionType) && isStringPositionType(otherPosition.positionType())) {
            iCompareTo = this.strPosition.compareTo(otherPosition.asString());
        }
        return iCompareTo;
    }

    private boolean isPageIndexPositionType(int positionType) {
        return (positionType & 4) != 0;
    }

    private boolean isIntPositionType(int positionType) {
        return (positionType & 1) != 0;
    }

    private boolean isStringPositionType(int positionType) {
        return (positionType & 2) != 0;
    }

    @Override // com.onyx.android.sdk.api.ReaderPosition
    public int getPageIndex() {
        return this.pageIndex;
    }

    @Override // com.onyx.android.sdk.api.ReaderPosition
    public ReaderPosition fromPageIndex(int pageIndex) {
        this.positionType |= 4;
        this.pageIndex = pageIndex;
        return this;
    }

    @Override // com.onyx.android.sdk.api.ReaderPosition
    public int asInteger() {
        return this.intPosition;
    }

    @Override // com.onyx.android.sdk.api.ReaderPosition
    public ReaderPosition fromInteger(int value) {
        this.positionType |= 1;
        this.intPosition = value;
        return this;
    }

    @Override // com.onyx.android.sdk.api.ReaderPosition
    public String asString() {
        return this.strPosition;
    }

    @Override // com.onyx.android.sdk.api.ReaderPosition
    public ReaderPosition fromString(String string) {
        this.positionType |= 2;
        this.strPosition = string;
        return this;
    }

    @Override // com.onyx.android.sdk.api.ReaderPosition
    public int compare(ReaderPosition otherPosition) {
        int i = this.positionType;
        if ((i & 4) != 0) {
            int iCompare = Integer.compare(this.pageIndex, otherPosition.getPageIndex());
            return (iCompare == 0 || (this.positionType & 1) != 0) ? Integer.compare(this.intPosition, otherPosition.asInteger()) : iCompare;
        }
        if ((i & 1) != 0) {
            return Integer.compare(this.intPosition, otherPosition.asInteger());
        }
        if ((i & 2) != 0) {
            return this.strPosition.compareTo(otherPosition.asString());
        }
        return 0;
    }

    @Override // com.onyx.android.sdk.api.ReaderPosition
    public boolean compatEquals(ReaderPosition otherPosition) {
        return compareIntPosition(otherPosition) == 0 && comparePageIndexPosition(otherPosition) == 0 && compareStrPosition(otherPosition) == 0;
    }

    @Override // com.onyx.android.sdk.api.ReaderPosition
    public int positionType() {
        return this.positionType;
    }

    @Override // com.onyx.android.sdk.api.ReaderPosition
    public ReaderPosition copy() {
        ReaderPositionImpl readerPositionImpl = new ReaderPositionImpl();
        readerPositionImpl.pageIndex = this.pageIndex;
        readerPositionImpl.intPosition = this.intPosition;
        readerPositionImpl.strPosition = this.strPosition;
        readerPositionImpl.positionType = this.positionType;
        return readerPositionImpl;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReaderPosition)) {
            return false;
        }
        ReaderPosition readerPosition = (ReaderPosition) o;
        return this.pageIndex == readerPosition.getPageIndex() && this.intPosition == readerPosition.asInteger() && ((StringUtils.isNullOrEmpty(this.strPosition) && StringUtils.isNullOrEmpty(readerPosition.asString())) || Objects.equals(this.strPosition, readerPosition.asString()));
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.pageIndex), Integer.valueOf(this.intPosition), this.strPosition, Integer.valueOf(this.positionType));
    }

    public String toString() {
        return "ReaderPositionImpl{pageIndex=" + this.pageIndex + ", intPosition=" + this.intPosition + ", strPosition='" + this.strPosition + "', positionType=" + this.positionType + '}';
    }
}
