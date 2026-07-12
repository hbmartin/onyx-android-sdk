package com.onyx.android.sdk.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Process;
import android.os.UserHandle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.WorkerThread;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.content.pm.ShortcutManagerCompat;
import androidx.core.graphics.drawable.IconCompat;
import com.onyx.android.sdk.data.AppDataInfo;
import com.onyx.android.sdk.data.AppShortcutInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LauncherAppsManager {
    private static final String d = "LauncherAppsManager";
    private static LauncherAppsManager e;
    private final LauncherApps a;
    private List<ShortcutInfo> b = Collections.emptyList();
    private List<ResolveInfo> c = Collections.emptyList();

    private LauncherAppsManager(Context context) {
        this.a = (LauncherApps) context.getSystemService("launcherapps");
    }

    public static LauncherAppsManager getInstance() {
        if (e == null) {
            e = new LauncherAppsManager(ResManager.getAppContext());
        }
        return e;
    }

    @RequiresApi(api = 25)
    public static int getShortcutQueryFlag() {
        return 11;
    }

    @RequiresApi(api = 25)
    private static List<String> a(List<ShortcutInfo> shortcuts) {
        return CollectionUtils.transformData(shortcuts, (v0) -> {
            return v0.getId();
        });
    }

    @RequiresApi(api = 25)
    public QueryResult queryForFullDetails(String packageName, List<String> shortcutIds, UserHandle user) {
        return a(getShortcutQueryFlag(), packageName, null, shortcutIds, user);
    }

    @RequiresApi(api = 25)
    public QueryResult queryForShortcutsContainer(String packageName, UserHandle user) {
        return a(9, packageName, null, null, user);
    }

    @RequiresApi(api = 25)
    public void unpinShortcut(AppShortcutInfo.ShortcutKey key) {
        if (key == null) {
            Debug.d(d, "ShortcutKey is null, can't unpin", new Object[0]);
            return;
        }
        String packageName = key.componentName.getPackageName();
        String id = key.getId();
        UserHandle userHandle = key.user;
        List<String> listA = a(getShortcutInfoList(userHandle, key.getPackageName()));
        listA.remove(id);
        try {
            this.a.pinShortcuts(packageName, listA, userHandle);
        } catch (Exception e2) {
            Log.w(d, "Failed to unpin shortcut", e2);
        }
    }

    @RequiresApi(api = 25)
    public void pinShortcut(AppShortcutInfo.ShortcutKey key) {
        String packageName = key.componentName.getPackageName();
        String id = key.getId();
        UserHandle userHandle = key.user;
        List<String> listA = a(queryForPinnedShortcuts(packageName, userHandle));
        listA.add(id);
        try {
            this.a.pinShortcuts(packageName, listA, userHandle);
        } catch (Exception e2) {
            Log.w(d, "Failed to pin shortcut", e2);
        }
    }

    @RequiresApi(api = 25)
    public boolean startShortcut(ShortcutInfo shortcutInfo) {
        return startShortcut(shortcutInfo.getPackage(), shortcutInfo.getId(), null, null, shortcutInfo.getUserHandle());
    }

    @RequiresApi(api = 25)
    public Drawable getShortcutIconDrawable(ShortcutInfo shortcutInfo, int density) {
        try {
            return this.a.getShortcutIconDrawable(shortcutInfo, density);
        } catch (Exception e2) {
            Log.e(d, "Failed to get shortcut icon", e2);
            return null;
        }
    }

    @RequiresApi(api = 25)
    @WorkerThread
    public QueryResult queryForPinnedShortcuts(String packageName, UserHandle user) {
        return queryForPinnedShortcuts(packageName, null, user);
    }

    @RequiresApi(api = 25)
    @WorkerThread
    public QueryResult queryForAllShortcuts(UserHandle user) {
        return a(getShortcutQueryFlag(), null, null, null, user);
    }

    public void requestPinShortcut(Context context, Class activityClass, String label, int icon, IntentSender callback) {
        Intent intent = new Intent(context, (Class<?>) activityClass);
        intent.setAction("android.intent.action.VIEW");
        ShortcutManagerCompat.requestPinShortcut(context, new ShortcutInfoCompat.Builder(context, activityClass.getName()).setIcon(IconCompat.createWithResource(context, icon)).setActivity(intent.getComponent()).setShortLabel(label).setIntent(intent).build(), callback);
    }

    public void setLaunchResolveInfoList(List<ResolveInfo> launchResolveInfoList) {
        this.c = launchResolveInfoList;
    }

    public List<ResolveInfo> getLaunchResolveInfoList() {
        List<ResolveInfo> launchResolveInfoList = ApplicationUtil.getLaunchResolveInfoList(ResManager.getAppContext().getPackageManager());
        this.c = launchResolveInfoList;
        return launchResolveInfoList;
    }

    @TargetApi(24)
    public List<PackageInfo> getLaunchPackageInfoList(Context context) {
        List listTransformData = CollectionUtils.transformData(getLaunchResolveInfoList(), resolveInfo -> {
            return resolveInfo.activityInfo.applicationInfo.packageName;
        });
        return (List) PackageUtils.getInstalledPackages(context).stream().filter(packageInfo -> {
            return listTransformData.contains(packageInfo.packageName);
        }).collect(Collectors.toList());
    }

    @RequiresApi(api = 25)
    public List<ShortcutInfo> initUserPinnedShorts(UserHandle userHandle) {
        setShortcutInfoList(queryForPinnedShortcuts(null, userHandle));
        return getShortcutInfoList();
    }

    public void setShortcutInfoList(List<ShortcutInfo> shortcutInfoList) {
        this.b = shortcutInfoList;
    }

    public List<ShortcutInfo> getShortcutInfoList() {
        return this.b;
    }

    public void updateShortcutInfo(AppShortcutInfo appShortcutInfo) {
        if (CompatibilityUtil.apiLevelCheck(25) && !StringUtils.isNullOrEmpty(appShortcutInfo.shortcutKey)) {
            ShortcutInfo shortcutInfoA = a(Process.myUserHandle(), appShortcutInfo.shortcutKey);
            if (shortcutInfoA == null) {
                Debug.w(getClass(), "Failed to get shortcutInfo:" + appShortcutInfo.shortcutKey, new Object[0]);
                return;
            }
            appShortcutInfo.setShortcutInfo(shortcutInfoA);
            appShortcutInfo.setIconDrawable(getShortcutIconDrawable(shortcutInfoA));
            appShortcutInfo.intent = getShortcutIntent(shortcutInfoA);
        }
    }

    public List<LauncherActivityInfo> getActivityList(UserHandle userHandle) {
        return this.a.getActivityList(null, userHandle);
    }

    public LauncherActivityInfo getActivityInfo(Intent intent, UserHandle userHandle) {
        return this.a.resolveActivity(intent, userHandle);
    }

    @Nullable
    @RequiresApi(api = 25)
    public Intent getShortcutIntent(ShortcutInfo info) {
        Object objInvokeMethodSafely = ReflectUtil.invokeMethodSafely(ReflectUtil.getDeclaredMethodSafely(this.a.getClass(), "getShortcutIntent", new Class[]{String.class, String.class, Bundle.class, UserHandle.class}), this.a, new Object[]{info.getPackage(), info.getId(), null, Process.myUserHandle()});
        if (!(objInvokeMethodSafely instanceof PendingIntent)) {
            return null;
        }
        Object objInvokeMethodSafely2 = ReflectUtil.invokeMethodSafely(ReflectUtil.getDeclaredMethodSafely(PendingIntent.class, "getIntent", new Class[0]), objInvokeMethodSafely, new Object[0]);
        if (objInvokeMethodSafely2 instanceof Intent) {
            return (Intent) objInvokeMethodSafely2;
        }
        return null;
    }

    @SuppressLint({"NewApi"})
    public ApplicationInfo getApplicationInfo(AppDataInfo appDataInfo) throws PackageManager.NameNotFoundException {
        return this.a.getApplicationInfo(appDataInfo.getPackageName(), 0, appDataInfo.getUserHandle());
    }

    public boolean startActivity(Intent intent, UserHandle user) {
        try {
            Debug.d(d, user + " startActivity:" + intent.getComponent(), new Object[0]);
            this.a.startMainActivity(intent.getComponent(), user, intent.getSourceBounds(), null);
            return true;
        } catch (Exception e2) {
            Log.e(d, TTFFont.UNKNOWN_FONT_NAME, e2);
            return false;
        }
    }

    public void registerLauncherAppCallback(LauncherApps.Callback callback) {
        this.a.registerCallback(callback);
    }

    public void unregisterLauncherAppCallback(LauncherApps.Callback callback) {
        this.a.unregisterCallback(callback);
    }

    public static class QueryResult extends ArrayList<ShortcutInfo> {
        static QueryResult b = new QueryResult();
        private final boolean a;

        QueryResult(List<ShortcutInfo> result) {
            super(result == null ? new ArrayList<ShortcutInfo>() : result);
            this.a = true;
        }

        public boolean isSuccessful() {
            return this.a;
        }

        QueryResult() {
            this.a = false;
        }
    }

    @RequiresApi(api = 25)
    private QueryResult a(int flags, String packageName, ComponentName activity, List<String> shortcutIds, UserHandle user) {
        LauncherApps.ShortcutQuery shortcutQuery = new LauncherApps.ShortcutQuery();
        shortcutQuery.setQueryFlags(flags);
        if (packageName != null) {
            shortcutQuery.setPackage(packageName);
            shortcutQuery.setActivity(activity);
            shortcutQuery.setShortcutIds(shortcutIds);
        }
        try {
            return new QueryResult(this.a.getShortcuts(shortcutQuery, user));
        } catch (Exception e2) {
            Log.e(d, "Failed to query for shortcuts", e2);
            return QueryResult.b;
        }
    }

    @RequiresApi(api = 25)
    @WorkerThread
    public QueryResult queryForPinnedShortcuts(String packageName, List<String> shortcutIds, UserHandle user) {
        return a(2, packageName, null, shortcutIds, user);
    }

    public List<ShortcutInfo> getShortcutInfoList(UserHandle userHandle) {
        if (this.b == null) {
            if (CompatibilityUtil.apiLevelCheck(25)) {
                this.b = getInstance().queryForPinnedShortcuts(null, userHandle);
            } else {
                this.b = Collections.emptyList();
            }
        }
        return this.b;
    }

    @Nullable
    @SuppressLint({"NewApi"})
    public ApplicationInfo getApplicationInfo(String packageName, int flags, UserHandle userHandle) {
        try {
            return this.a.getApplicationInfo(packageName, flags, userHandle);
        } catch (PackageManager.NameNotFoundException unused) {
            return null;
        }
    }

    @RequiresApi(api = 25)
    public boolean startShortcut(String packageName, String id, Rect sourceBounds, Bundle startActivityOptions, UserHandle user) {
        try {
            this.a.startShortcut(packageName, id, sourceBounds, startActivityOptions, user);
            return true;
        } catch (Exception e2) {
            Log.e(d, "Failed to start shortcut", e2);
            return false;
        }
    }

    @RequiresApi(api = 25)
    public Drawable getShortcutIconDrawable(ShortcutInfo shortcutInfo) {
        return getShortcutIconDrawable(shortcutInfo, ResManager.getDensityDpi());
    }

    @RequiresApi(api = 25)
    public List<ShortcutInfo> getShortcutInfoList(UserHandle userHandle, String packageName) {
        ArrayList arrayList = new ArrayList();
        for (ShortcutInfo shortcutInfo : getShortcutInfoList(userHandle)) {
            if (StringUtils.safelyEquals(shortcutInfo.getPackage(), packageName)) {
                arrayList.add(shortcutInfo);
            }
        }
        return arrayList;
    }

    @RequiresApi(api = 25)
    private ShortcutInfo a(UserHandle userHandle, String shortcutKey) {
        List<ShortcutInfo> shortcutInfoList = getShortcutInfoList(userHandle);
        this.b = shortcutInfoList;
        if (CollectionUtils.isNullOrEmpty(shortcutInfoList)) {
            return null;
        }
        for (ShortcutInfo shortcutInfo : this.b) {
            if (StringUtils.safelyEquals(shortcutKey, AppShortcutInfo.makeShortcutKey(shortcutInfo))) {
                return shortcutInfo;
            }
        }
        return null;
    }
}
