package com.onyx.android.sdk.data;

import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.drawable.Drawable;
import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.PackageUtils;
import com.onyx.android.sdk.utils.ShellUtils;
import com.onyx.android.sdk.utils.StringUtils;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/AppWidgetInfo.class */
public class AppWidgetInfo extends AppBaseInfo {
    public String appLabelName;
    public String idString;
    public int widgetId;
    public long time;

    @JSONField(deserialize = false, serialize = false)
    public Drawable previewDrawable;

    @JSONField(deserialize = false, serialize = false)
    public AppWidgetProviderInfo providerInfo;
    private String b;
    private String c;

    public AppWidgetInfo() {
        setType(AppBaseInfo.Type.WIDGET);
    }

    public String getLabelName() {
        return StringUtils.isNotBlank(this.appLabelName) ? this.appLabelName : this.name;
    }

    public void setProviderInfo(Context context, AppWidgetProviderInfo providerInfo) {
        this.providerInfo = providerInfo;
        if (providerInfo != null) {
            this.b = providerInfo.provider.getPackageName();
            this.c = providerInfo.provider.getClassName();
            this.name = providerInfo.loadLabel(context.getPackageManager());
            this.appLabelName = PackageUtils.getAppDisplayName(context, this.b);
        }
    }

    @Override // com.onyx.android.sdk.data.AppBaseInfo
    public String getPackageName() {
        AppWidgetProviderInfo appWidgetProviderInfo;
        if (StringUtils.isNullOrEmpty(this.b) && (appWidgetProviderInfo = this.providerInfo) != null) {
            this.b = appWidgetProviderInfo.provider.getPackageName();
        }
        return this.b;
    }

    public String getProviderClsName() {
        AppWidgetProviderInfo appWidgetProviderInfo;
        if (StringUtils.isNullOrEmpty(this.c) && (appWidgetProviderInfo = this.providerInfo) != null) {
            this.c = appWidgetProviderInfo.provider.getClassName();
        }
        return this.c;
    }

    public void setProviderClsName(String providerClsName) {
        this.c = providerClsName;
    }

    public void setPackageName(String packageName) {
        this.b = packageName;
    }

    public String flattenComponentName() {
        return getPackageName() + "/" + getProviderClsName();
    }

    @JSONField(deserialize = false, serialize = false)
    public int getResizeMode() {
        AppWidgetProviderInfo appWidgetProviderInfo = this.providerInfo;
        if (appWidgetProviderInfo == null) {
            return 0;
        }
        return appWidgetProviderInfo.resizeMode;
    }

    public boolean canResize() {
        return getResizeMode() != 0;
    }

    @JSONField(deserialize = false, serialize = false)
    public int getMinWidth() {
        AppWidgetProviderInfo appWidgetProviderInfo = this.providerInfo;
        if (appWidgetProviderInfo == null) {
            return 0;
        }
        return appWidgetProviderInfo.minWidth;
    }

    @JSONField(deserialize = false, serialize = false)
    public int getMinHeight() {
        AppWidgetProviderInfo appWidgetProviderInfo = this.providerInfo;
        if (appWidgetProviderInfo == null) {
            return 0;
        }
        return appWidgetProviderInfo.minHeight;
    }

    @JSONField(deserialize = false, serialize = false)
    public int getMinResizeHeight() {
        AppWidgetProviderInfo appWidgetProviderInfo = this.providerInfo;
        if (appWidgetProviderInfo == null) {
            return 0;
        }
        return appWidgetProviderInfo.minResizeHeight;
    }

    @JSONField(deserialize = false, serialize = false)
    public int getMinResizeWidth() {
        AppWidgetProviderInfo appWidgetProviderInfo = this.providerInfo;
        if (appWidgetProviderInfo == null) {
            return 0;
        }
        return appWidgetProviderInfo.minResizeWidth;
    }

    @JSONField(deserialize = false, serialize = false)
    public ComponentName getConfigureComponentName() {
        AppWidgetProviderInfo appWidgetProviderInfo = this.providerInfo;
        if (appWidgetProviderInfo == null) {
            return null;
        }
        return appWidgetProviderInfo.configure;
    }

    @Override // com.onyx.android.sdk.data.AppBaseInfo
    public String getLaunchName() {
        return this.idString;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Appwidget.Name:").append(this.name).append(ShellUtils.COMMAND_LINE_END).append("widgetId:" + this.widgetId).append(ShellUtils.COMMAND_LINE_END).append("x/y:" + this.x + "/" + this.y).append(ShellUtils.COMMAND_LINE_END).append("col/row:" + this.colSpan + "/" + this.rowSpan).append(ShellUtils.COMMAND_LINE_END);
        if (this.providerInfo != null) {
            sb.append("Provider Name: ").append(this.providerInfo.provider).append(ShellUtils.COMMAND_LINE_END).append("Configure Name: ").append(this.providerInfo.configure).append(ShellUtils.COMMAND_LINE_END).append("min width/height: " + this.providerInfo.minWidth + " " + this.providerInfo.minHeight).append(ShellUtils.COMMAND_LINE_END).append("resize width/height: " + this.providerInfo.minResizeWidth + " " + this.providerInfo.minResizeHeight).append(ShellUtils.COMMAND_LINE_END).append("resizeMode: ").append(this.providerInfo.resizeMode).append(ShellUtils.COMMAND_LINE_END);
        }
        if (this.previewDrawable != null) {
            sb.append("previewDrawable width/height:" + this.previewDrawable.getIntrinsicWidth() + " " + this.previewDrawable.getIntrinsicHeight()).append(ShellUtils.COMMAND_LINE_END);
        }
        if (this.iconDrawable != null) {
            sb.append("iconDrawable width/height:" + this.iconDrawable.getIntrinsicWidth() + " " + this.iconDrawable.getIntrinsicHeight()).append(ShellUtils.COMMAND_LINE_END);
        }
        return sb.toString();
    }

    public void printInfo() {
        Debug.i(getClass(), toString(), new Object[0]);
        Debug.i(getClass(), "--------------------", new Object[0]);
    }
}
