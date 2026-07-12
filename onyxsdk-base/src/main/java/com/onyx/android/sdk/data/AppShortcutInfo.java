// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.data;

import androidx.annotation.NonNull;
import com.onyx.android.sdk.utils.MathUtils;
import com.onyx.android.sdk.utils.Debug;
import java.util.Arrays;
import android.os.UserHandle;
import java.io.Serializable;
import android.app.Activity;
import com.onyx.android.sdk.utils.LauncherAppsManager;
import android.content.Context;
import android.annotation.TargetApi;
import android.content.ComponentName;
import androidx.annotation.RequiresApi;
import androidx.annotation.Nullable;
import com.onyx.android.sdk.utils.StringUtils;
import com.alibaba.fastjson2.annotation.JSONField;
import android.content.pm.ShortcutInfo;

public class AppShortcutInfo extends AppDataInfo
{
    @JSONField(serialize = false, deserialize = false)
    public ShortcutInfo shortcutInfo;
    public String shortcutKey;
    
    public AppShortcutInfo() {
        this.setType(Type.SHORTCUT);
    }
    
    @Nullable
    public static String getPackageName(final String shortcutKey) {
        if (StringUtils.isNotBlank(shortcutKey)) {
            return shortcutKey.split("/")[0];
        }
        return null;
    }
    
    @RequiresApi(api = 25)
    public static AppShortcutInfo create(final ShortcutInfo shortcutInfo) {
        final AppShortcutInfo appShortcutInfo = new com.onyx.android.sdk.data.AppShortcutInfo();
        final AppShortcutInfo appShortcutInfo3;
        final AppShortcutInfo appShortcutInfo2 = appShortcutInfo3 = appShortcutInfo;
        new AppShortcutInfo();
        appShortcutInfo3.setType(Type.SHORTCUT);
        appShortcutInfo2.setShortcutInfo(shortcutInfo);
        appShortcutInfo.packageName = (appShortcutInfo.shortcutKey = makeShortcutKey(shortcutInfo));
        appShortcutInfo.lastUpdatedTime = System.currentTimeMillis();
        return appShortcutInfo;
    }
    
    @RequiresApi(api = 25)
    public static String makeShortcutKey(final ShortcutInfo shortcutInfo) {
        return new ShortcutKey(shortcutInfo.getPackage(), shortcutInfo.getUserHandle(), shortcutInfo.getId()).toString();
    }
    
    @Override
    public AppShortcutInfo clone() {
        try {
            return (AppShortcutInfo)super.clone();
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    @Nullable
    @TargetApi(25)
    @Override
    public ComponentName getComponentName() {
        final ShortcutInfo shortcutInfo;
        if ((shortcutInfo = this.shortcutInfo) != null) {
            return shortcutInfo.getActivity();
        }
        if (StringUtils.isNotBlank(this.shortcutKey)) {
            final ShortcutKey fromString;
            return ((fromString = ShortcutKey.fromString(this.shortcutKey)) == null) ? null : fromString.componentName;
        }
        return null;
    }
    
    @Override
    public String getPackageName() {
        String s;
        if ((s = getPackageName(this.shortcutKey)) == null) {
            s = super.packageName;
        }
        return s;
    }
    
    @Override
    public String getActivityClassName() {
        final ComponentName componentName;
        return ((componentName = this.getComponentName()) != null) ? componentName.getClassName() : super.activityClassName;
    }
    
    @Override
    public String getLaunchName() {
        return this.shortcutKey;
    }
    
    @Override
    public String getName() {
        final String name;
        if ((name = super.name) != null && super.labelName != null) {
            return name;
        }
        return this.shortcutKey;
    }
    
    @RequiresApi(api = 25)
    public void setShortcutInfo(final ShortcutInfo shortcutInfo) {
        if (shortcutInfo == null) {
            return;
        }
        this.shortcutInfo = shortcutInfo;
        super.labelName = (super.name = StringUtils.safelyGetStr(shortcutInfo.getShortLabel()));
    }
    
    public ShortcutInfo getShortcutInfo() {
        return this.shortcutInfo;
    }
    
    @TargetApi(25)
    @Override
    public void parseInfo(final Context context) {
        if (StringUtils.isNullOrEmpty(this.shortcutKey)) {
            return;
        }
        LauncherAppsManager.getInstance().updateShortcutInfo(this);
    }
    
    @Nullable
    @RequiresApi(api = 25)
    public ShortcutKey fromShortcutKey() {
        return ShortcutKey.fromString(this.shortcutKey);
    }
    
    @TargetApi(25)
    @Override
    public void open(final Activity activity) {
        if (this.shortcutInfo != null) {
            LauncherAppsManager.getInstance().startShortcut(this.shortcutInfo);
        }
    }
    
    @RequiresApi(api = 25)
    public static class ShortcutKey implements Serializable, Cloneable
    {
        public final ComponentName componentName;
        public final UserHandle user;
        public final int hashCode;
        
        public ShortcutKey(final String packageName, final UserHandle user, final String id) {
            this(new ComponentName(packageName, id), user);
        }
        
        public ShortcutKey(final ComponentName componentName, final UserHandle user) {
            this.componentName = componentName;
            this.user = user;
            this.hashCode = Arrays.hashCode(new Object[] { componentName, user });
        }
        
        public static ShortcutKey fromString(String key) {
            Debug.d((Class)ShortcutKey.class, "shortcutKey:" + key, new Object[0]);
            if (StringUtils.isNullOrEmpty(key)) {
                return null;
            }
            final int index;
            final int n = index = key.indexOf("/");
            final int lastIndex = key.lastIndexOf("#");
            final int beginIndex;
            if (n >= 0 && lastIndex >= 0 && (beginIndex = index + 1) <= lastIndex) {
                final String s = key;
                final int n2 = lastIndex;
                final String substring = key.substring(0, index);
                final String substring2 = s.substring(n2 + 1);
                key = key.substring(beginIndex, lastIndex);
                return new ShortcutKey(substring, UserHandle.getUserHandleForUid(MathUtils.parseInt(substring2.replaceAll(UserHandle.class.getSimpleName(), "").replaceAll("[{|}]", ""))), key);
            }
            Debug.d("AppShortcutInfo", "fromString() string index out of bounds", new Object[0]);
            return null;
        }
        
        public String getPackageName() {
            return this.componentName.getPackageName();
        }
        
        public String getId() {
            return this.componentName.getClassName();
        }
        
        @Override
        public int hashCode() {
            return this.hashCode;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof ShortcutKey)) {
                return false;
            }
            return StringUtils.safelyEquals(this.toString(), ((ShortcutKey)o).toString());
        }
        
        @NonNull
        @Override
        public String toString() {
            return this.componentName.flattenToString() + "#" + this.user;
        }
    }
}
