package com.onyx.android.sdk.data;

import android.graphics.Matrix;
import android.graphics.RectF;
import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.api.ReaderPosition;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.PageInfoUtils;

public class PageInfo {
    static final /* synthetic */ boolean t = !PageInfo.class.desiredAssertionStatus();
    private int a;
    private PageRange b;
    private int c;
    private int d;
    private int e;
    private float f;
    private float g;
    private boolean h;
    private boolean i;
    private boolean j;
    private RectF k;
    private RectF l;
    private RectF m;
    private RectF n;
    private RectF o;
    private RectF p;
    private float q;
    private int r;
    private String s;

    public static PageInfo copyWithSubPage(PageInfo pageInfo) {
        PageInfo pageInfo2 = new PageInfo(pageInfo);
        pageInfo2.setSubPage(pageInfo.getSubPage());
        return pageInfo2;
    }

    public PageInfo() {
        this.c = 0;
        this.e = 0;
        this.j = true;
        this.l = new RectF();
        this.m = new RectF();
        this.n = new RectF();
        this.o = new RectF();
        this.p = new RectF();
        this.q = 1.0f;
        this.r = 0;
    }

    public final float getOriginWidth() {
        return this.f;
    }

    public final float getOriginHeight() {
        return this.g;
    }

    public boolean isTextPage() {
        return this.h;
    }

    public void setIsTextPage(boolean value) {
        this.h = value;
    }

    public boolean isOnyxPage() {
        return this.i;
    }

    public PageInfo setOnyxPage(boolean onyxPage) {
        this.i = onyxPage;
        return this;
    }

    public boolean isValidPage() {
        return this.j;
    }

    public PageInfo setValidPage(boolean validPage) {
        this.j = validPage;
        return this;
    }

    public final RectF getAutoCropContentRegion() {
        return this.k;
    }

    public void setAutoCropContentRegion(RectF region) {
        this.k = region;
    }

    public final RectF getPositionRect() {
        return this.l;
    }

    public final float getScaledHeight() {
        return this.l.height();
    }

    public final float getScaledWidth() {
        return this.l.width();
    }

    public final float getActualScale() {
        return this.q;
    }

    public void update(float newScale, float x, float y) {
        setScale(newScale);
        setPosition(x, y);
    }

    public void setPosition(float x, float y) {
        this.l.offsetTo(x, y);
    }

    public float getX() {
        return this.l.left;
    }

    public float getY() {
        return this.l.top;
    }

    public void setX(float x) {
        RectF rectF = this.l;
        rectF.offsetTo(x, rectF.top);
    }

    public void setY(float y) {
        RectF rectF = this.l;
        rectF.offsetTo(rectF.left, y);
    }

    public void setScale(float newScale) {
        this.q = newScale;
        RectF rectF = this.l;
        float f = rectF.left;
        float f2 = rectF.top;
        rectF.set(f, f2, f + (this.f * newScale), f2 + (this.g * newScale));
    }

    public RectF updateDisplayRect(RectF rect) {
        RectF rectF = new RectF(rect);
        this.m = rectF;
        return rectF;
    }

    public RectF getDisplayRect() {
        return this.m;
    }

    public RectF updateVisibleRect(RectF rect) {
        RectF rectF = new RectF(rect);
        this.n = rectF;
        return rectF;
    }

    public RectF getVisibleRect() {
        return this.n;
    }

    public int getPageDisplayOrientation() {
        return this.e;
    }

    public void setPageNumber(int pageNumber) {
        this.a = pageNumber;
    }

    public int getPageNumber() {
        return this.a;
    }

    @JSONField(serialize = false, deserialize = false)
    public String getPageNumberString() {
        return String.valueOf(this.a);
    }

    @JSONField(serialize = false, deserialize = false)
    public ReaderPosition getPositionSafely() {
        if (!PageInfoUtils.isInvalidPosition(this.b.getStartPosition())) {
            return this.b.getStartPosition();
        }
        Debug.w(getClass(), "expect pagePosition, actual pageNumber = " + getPageNumberString(), new Object[0]);
        return PageInfoUtils.getCompatiblePosition(this.a);
    }

    @JSONField(serialize = false, deserialize = false)
    public int getPositionIntSafely() {
        if (!PageInfoUtils.isInvalidPosition(this.b.getStartPosition())) {
            return this.b.getStartPositionInt();
        }
        Debug.w(getClass(), "expect pagePositionInt, actual pageNumber = " + getPageNumber(), new Object[0]);
        return getPageNumber();
    }

    public ReaderPosition getPosition() {
        if (t || this.b != null) {
            return this.b.getStartPosition();
        }
        throw new AssertionError();
    }

    public PageInfo setPageRange(PageRange pageRange) {
        this.b = pageRange;
        return this;
    }

    public PageRange getPageRange() {
        return this.b;
    }

    @JSONField(deserialize = false, serialize = false)
    public PageRange getRange() {
        return this.b;
    }

    public int getSubPage() {
        return this.c;
    }

    public PageInfo setSubPage(int subPage) {
        this.c = subPage;
        return this;
    }

    public Matrix normalizeMatrix() {
        Matrix matrix = new Matrix();
        float fWidth = (1.0f / getDisplayRect().width()) / getActualScale();
        float fHeight = (1.0f / getDisplayRect().height()) / getActualScale();
        matrix.postTranslate(-getDisplayRect().left, -getDisplayRect().top);
        matrix.postScale(fWidth, fHeight);
        return matrix;
    }

    public String getExtraKey() {
        return this.s;
    }

    public PageInfo setExtraKey(String extraKey) {
        this.s = extraKey;
        return this;
    }

    public PageInfo setOriginWidth(float originWidth) {
        this.f = originWidth;
        return this;
    }

    public PageInfo setOriginHeight(float originHeight) {
        this.g = originHeight;
        return this;
    }

    public int getSpecialScale() {
        return this.r;
    }

    public PageInfo setSpecialScale(int specialScale) {
        this.r = specialScale;
        return this;
    }

    public RectF getContentRect() {
        return this.o;
    }

    public PageInfo setContentRect(RectF contentRect) {
        this.o = contentRect;
        return this;
    }

    public RectF getVisibleRectInContent() {
        return this.p;
    }

    public PageInfo setVisibleRectInContent(RectF visibleRectInContent) {
        this.p = visibleRectInContent;
        return this;
    }

    @JSONField(serialize = false, deserialize = false)
    public RectF getOriginRect() {
        return new RectF(ReaderTextStyle.FONT_EMBOLDEN_NORMAL, ReaderTextStyle.FONT_EMBOLDEN_NORMAL, getOriginWidth(), getOriginHeight());
    }

    public String toString() {
        return "PageInfo{pageNumber='" + this.a + "', subPage=" + this.c + '}';
    }

    public PageInfo(int pageNumber, float nw, float nh) {
        this.c = 0;
        this.e = 0;
        this.j = true;
        this.l = new RectF();
        this.m = new RectF();
        this.n = new RectF();
        this.o = new RectF();
        this.p = new RectF();
        this.q = 1.0f;
        this.r = 0;
        ReaderPosition positionByPageNumber = PageInfoUtils.getPositionByPageNumber(pageNumber);
        this.b = PageRange.create(positionByPageNumber, positionByPageNumber.copy());
        this.a = pageNumber;
        this.f = nw;
        this.g = nh;
        this.l.set(ReaderTextStyle.FONT_EMBOLDEN_NORMAL, ReaderTextStyle.FONT_EMBOLDEN_NORMAL, nw, nh);
    }

    public PageInfo(int pageNumber, PageRange pageRange, float nw, float nh) {
        this.c = 0;
        this.e = 0;
        this.j = true;
        this.l = new RectF();
        this.m = new RectF();
        this.n = new RectF();
        this.o = new RectF();
        this.p = new RectF();
        this.q = 1.0f;
        this.r = 0;
        this.a = pageNumber;
        this.b = pageRange;
        this.f = nw;
        this.g = nh;
        this.l.set(ReaderTextStyle.FONT_EMBOLDEN_NORMAL, ReaderTextStyle.FONT_EMBOLDEN_NORMAL, nw, nh);
    }

    public PageInfo(PageInfo pageInfo) {
        this.c = 0;
        this.e = 0;
        this.j = true;
        this.l = new RectF();
        this.m = new RectF();
        this.n = new RectF();
        this.o = new RectF();
        this.p = new RectF();
        this.q = 1.0f;
        this.r = 0;
        this.a = pageInfo.getPageNumber();
        this.b = PageRange.copy(pageInfo.getRange());
        this.f = pageInfo.getOriginWidth();
        this.g = pageInfo.getOriginHeight();
        this.l.set(pageInfo.l);
        this.m.set(pageInfo.m);
        this.n.set(pageInfo.n);
        this.o.set(pageInfo.o);
        this.p.set(pageInfo.p);
        this.q = pageInfo.q;
        this.r = pageInfo.r;
        this.h = pageInfo.h;
        this.i = pageInfo.i;
        this.j = pageInfo.j;
        this.s = pageInfo.s;
    }
}
