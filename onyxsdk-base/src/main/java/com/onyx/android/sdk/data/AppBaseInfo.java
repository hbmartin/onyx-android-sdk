package com.onyx.android.sdk.data;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import androidx.databinding.BaseObservable;
import com.alibaba.fastjson2.annotation.JSONField;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/AppBaseInfo.class */
public class AppBaseInfo extends BaseObservable implements Serializable {
    public String name;

    @JSONField(deserialize = false, serialize = false)
    public Drawable iconDrawable;

    @JSONField(deserialize = false, serialize = false)
    public Bitmap iconImage;
    private Type a;
    public int rowSpan = 1;
    public int colSpan = 1;
    public int x = 0;
    public int y = 0;
    public int page = 0;
    public Source source = Source.NONE;

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/AppBaseInfo$Source.class */
    public enum Source {
        NONE,
        CLUSTER
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/AppBaseInfo$Type.class */
    public enum Type {
        APP,
        GROUP,
        WIDGET,
        SHORTCUT;

        public static boolean isAppType(Type type) {
            return type == APP;
        }

        public static boolean isShortcutType(Type type) {
            return type == SHORTCUT;
        }
    }

    public void setType(Type type) {
        this.a = type;
    }

    public Type getType() {
        return this.a;
    }

    public Source getSource() {
        return this.source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public int getRowSpan() {
        return this.rowSpan;
    }

    public int getColSpan() {
        return this.colSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = Math.max(rowSpan, 1);
    }

    public void setColSpan(int colSpan) {
        this.colSpan = Math.max(colSpan, 1);
    }

    public int getSpanSize() {
        return this.colSpan * this.rowSpan;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return this.x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return this.y;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return this.page;
    }

    public Bitmap getIconImage() {
        return this.iconImage;
    }

    public void setIconImage(Bitmap iconImage) {
        this.iconImage = iconImage;
    }

    public Drawable getIconDrawable() {
        return this.iconDrawable;
    }

    public void setIconDrawable(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JSONField(serialize = false, deserialize = false)
    public boolean isGroupType() {
        return getType() == Type.GROUP;
    }

    @JSONField(serialize = false, deserialize = false)
    public boolean isAppType() {
        return Type.isAppType(getType());
    }

    @JSONField(serialize = false, deserialize = false)
    public boolean isWidgetType() {
        return getType() == Type.WIDGET;
    }

    @JSONField(serialize = false, deserialize = false)
    public boolean isShortcutType() {
        return Type.isShortcutType(getType());
    }

    public boolean isPagePositionMatch(AppBaseInfo appBaseInfo) {
        return appBaseInfo.page == getPage() && appBaseInfo.getX() == getX() && appBaseInfo.getY() == getY();
    }

    public String getLaunchName() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

    @JSONField(serialize = false, deserialize = false)
    public String getPackageName() {
        return getLaunchName();
    }

    public void open(Activity activity) {
    }

    public void parseInfo(Context context) {
    }
}
