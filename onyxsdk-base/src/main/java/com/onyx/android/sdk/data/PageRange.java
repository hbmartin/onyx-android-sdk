package com.onyx.android.sdk.data;

import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.api.ReaderPosition;
import com.onyx.android.sdk.utils.PageInfoUtils;

public class PageRange {
    private ReaderPosition a;
    private ReaderPosition b;

    public PageRange() {
        ReaderPosition readerPosition = ReaderPosition.dummy;
        this.a = readerPosition;
        this.b = readerPosition;
    }

    public static PageRange create(int startPosition, int endPosition) {
        return new PageRange().setPosition(PageInfoUtils.getCompatiblePosition(startPosition), PageInfoUtils.getCompatiblePosition(endPosition));
    }

    public static PageRange copy(PageRange pageRange) {
        return new PageRange().copyPageRange(pageRange);
    }

    public PageRange copyPageRange(PageRange pageRange) {
        setPosition(pageRange.getStartPosition().copy(), pageRange.getEndPosition().copy());
        return this;
    }

    @JSONField(serialize = false, deserialize = false)
    public ReaderPosition getStartPosition() {
        return this.a;
    }

    @JSONField(serialize = false, deserialize = false)
    public PageRange setStartPosition(ReaderPosition startPosition) {
        this.a = startPosition;
        return this;
    }

    @JSONField(serialize = false, deserialize = false)
    public ReaderPosition getEndPosition() {
        return this.b;
    }

    @JSONField(serialize = false, deserialize = false)
    public PageRange setEndPosition(ReaderPosition endPosition) {
        this.b = endPosition;
        return this;
    }

    @JSONField(serialize = false, deserialize = false)
    public int getStartPositionInt() {
        return this.a.asInteger();
    }

    @JSONField(serialize = false, deserialize = false)
    public PageRange setStartPositionInt(int startPositionInt) {
        this.a = PageInfoUtils.getPosition(this.a, startPositionInt);
        return this;
    }

    @JSONField(serialize = false, deserialize = false)
    public int getEndPositionInt() {
        return this.b.asInteger();
    }

    @JSONField(serialize = false, deserialize = false)
    public PageRange setEndPositionInt(int endPositionInt) {
        this.b = PageInfoUtils.getPosition(this.b, endPositionInt);
        return this;
    }

    @JSONField(serialize = false, deserialize = false)
    public PageRange setPosition(ReaderPosition startPosition, ReaderPosition endPosition) {
        this.a = startPosition;
        this.b = endPosition;
        return this;
    }

    @JSONField(name = "startPosition")
    public String getStartPositionString() {
        return PageInfoUtils.getPositionString(this.a);
    }

    @JSONField(name = "endPosition")
    public String getEndPositionString() {
        return PageInfoUtils.getPositionString(this.b);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PageRange pageRange = (PageRange) o;
        return PageInfoUtils.isValidPageRange(pageRange) && this.a.compatEquals(pageRange.a) && this.b.compatEquals(pageRange.b);
    }

    public int hashCode() {
        ReaderPosition readerPosition = this.a;
        int iHashCode = (readerPosition != null ? readerPosition.hashCode() : 0) * 31;
        ReaderPosition readerPosition2 = this.b;
        return iHashCode + (readerPosition2 != null ? readerPosition2.hashCode() : 0);
    }

    public String toString() {
        return "PageRange{startPosition='" + this.a + "', endPosition='" + this.b + "'}";
    }

    public PageRange setStartPosition(String position) {
        this.a = PageInfoUtils.getPosition(this.a, position);
        return this;
    }

    public PageRange setEndPosition(String position) {
        this.b = PageInfoUtils.getPosition(this.b, position);
        return this;
    }

    public static PageRange create(String startPosition, String endPosition) {
        return new PageRange().setPosition(PageInfoUtils.getCompatiblePosition(startPosition), PageInfoUtils.getCompatiblePosition(endPosition));
    }

    public static PageRange create(ReaderPosition startPosition, ReaderPosition endPosition) {
        return new PageRange().setPosition(startPosition, endPosition);
    }
}
