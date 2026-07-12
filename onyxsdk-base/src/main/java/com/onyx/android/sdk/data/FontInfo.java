package com.onyx.android.sdk.data;

import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.commons.io.FilenameUtils;
import com.onyx.android.sdk.utils.DeviceUtils;
import com.onyx.android.sdk.utils.FileUtils;
import com.onyx.android.sdk.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/FontInfo.class */
public class FontInfo {
    public static final String DEFAULT_ID = "serif";
    private String a;
    private String b;
    private String c;
    private String d;
    private DeviceUtils.FontType g;
    private boolean e = false;
    private boolean f = false;
    private boolean h = false;
    private List<FontInfo> i = new ArrayList();

    public String getId() {
        return this.a;
    }

    public void setId(String id) {
        this.a = id;
    }

    @JSONField(serialize = false, deserialize = false)
    public String getName() {
        return this.b;
    }

    public String getFontDisplayName() {
        return this.b;
    }

    public void setFontDisplayName(String fontDisplayName) {
        this.b = fontDisplayName;
    }

    public String getFontUniqueName() {
        return StringUtils.isNotBlank(this.c) ? this.c : this.b;
    }

    public void setFontUniqueName(String fontUniqueName) {
        this.c = fontUniqueName;
    }

    public boolean isSupportCurrentLang() {
        return this.h;
    }

    public FontInfo setSupportCurrentLang(boolean supportCurrentLang) {
        this.h = supportCurrentLang;
        return this;
    }

    public boolean isFamily() {
        return this.f;
    }

    public FontInfo setFamily(boolean family) {
        this.f = family;
        return this;
    }

    public List<FontInfo> getChildFontList() {
        return this.i;
    }

    public FontInfo setChildFontList(List<FontInfo> childFontList) {
        this.i = childFontList;
        return this;
    }

    public String getFilePath() {
        return this.d;
    }

    public FontInfo setFilePath(String filePath) {
        this.d = filePath;
        return this;
    }

    @JSONField(serialize = false, deserialize = false)
    public String getFileBaseName() {
        return FilenameUtils.getBaseName(this.d);
    }

    public void setFontType(DeviceUtils.FontType fontType) {
        this.g = fontType;
    }

    @JSONField(serialize = false, deserialize = false)
    public boolean isSystemFont() {
        return this.g == DeviceUtils.FontType.SYSTEM || StringUtils.startsWith(this.d, "/system");
    }

    public boolean isImportFont() {
        return this.e;
    }

    public void setImportFont(boolean importFont) {
        this.e = importFont;
    }

    public DeviceUtils.FontType getFontType() {
        return this.g;
    }

    @JSONField(serialize = false, deserialize = false)
    public String getFontTypeName() {
        String strName = null;
        if (getFontType() != null) {
            strName = getFontType().name();
        }
        return strName;
    }

    public void useDefaultId() {
        setId(DEFAULT_ID);
    }

    @JSONField(serialize = false, deserialize = false)
    public boolean isExist() {
        return FileUtils.fileExist(getId()) || StringUtils.safelyEquals(this.a, DEFAULT_ID);
    }

    public String toString() {
        return "FontInfo{id='" + this.a + "', fontDisplayName='" + this.b + "', filePath='" + this.d + "', importFont=" + this.e + ", fontType=" + this.g + '}';
    }
}
