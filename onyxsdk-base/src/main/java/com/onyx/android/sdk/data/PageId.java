package com.onyx.android.sdk.data;

import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.utils.StringUtils;
import com.onyx.android.sdk.utils.TTFFont;
import com.onyx.android.sdk.utils.UUIDUtils;

public class PageId {
    private static final boolean d = false;
    private String a;
    private String b;
    private int c;

    public static PageId create(String pageUUID, String pageReferenceId) {
        PageId pageId = new PageId();
        if (StringUtils.isNotBlank(pageUUID)) {
            pageId.setPageUUID(pageUUID);
        }
        if (StringUtils.isNotBlank(pageReferenceId)) {
            pageId.setPageReferenceId(pageReferenceId);
        }
        return pageId;
    }

    public static boolean isValidId(PageId pageId) {
        if (pageId == null) {
            return false;
        }
        return StringUtils.isNotBlank(pageId.getPageReferenceId()) || StringUtils.isNotBlank(pageId.getPageUUID());
    }

    public static String generateUUID() {
        return isEnabledUUID() ? UUIDUtils.randomUUID() : TTFFont.UNKNOWN_FONT_NAME;
    }

    public static boolean isEnabledUUID() {
        return false;
    }

    public String getPageUUID() {
        return this.a;
    }

    public PageId setPageUUID(String pageUUID) {
        this.a = pageUUID;
        return this;
    }

    public String getPageReferenceId() {
        return this.b;
    }

    public PageId setPageReferenceId(String pageReferenceId) {
        this.b = pageReferenceId;
        return this;
    }

    @JSONField(serialize = false, deserialize = false)
    public int getPageNumber() {
        return this.c;
    }

    @JSONField(serialize = false, deserialize = false)
    public PageId setPageNumber(int pageNumber) {
        this.c = pageNumber;
        return this;
    }

    public String toInfoString() {
        return "PageId{pageUUID='" + this.a + "', pageReferenceId='" + this.b + "'}";
    }
}
