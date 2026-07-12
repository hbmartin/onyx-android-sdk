// 
// 

package com.onyx.android.sdk.utils;

import android.content.pm.ComponentInfo;
import java.util.Arrays;
import com.onyx.android.sdk.api.device.eac.EACReflectUtils;
import java.util.UUID;
import java.io.IOException;
import android.os.storage.StorageManager;
import android.app.usage.StorageStats;
import android.app.usage.StorageStatsManager;
import io.reactivex.functions.Predicate;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import android.os.Parcelable;
import android.content.pm.LauncherApps;
import java.util.Comparator;
import com.onyx.android.sdk.data.AppShortcutInfo;
import android.app.ActivityManager;
import android.os.Process;
import com.onyx.android.sdk.data.AppWidgetInfo;
import android.appwidget.AppWidgetProviderInfo;
import android.content.pm.ServiceInfo;
import java.util.Collection;
import android.speech.tts.TextToSpeech;
import com.onyx.android.sdk.data.AppsConstant;
import android.text.TextUtils;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import java.util.Map;
import androidx.annotation.Nullable;
import android.content.ComponentName;
import android.os.UserHandle;
import android.content.pm.ActivityInfo;
import android.content.pm.LauncherActivityInfo;
import android.graphics.drawable.Drawable;
import android.content.Intent;
import com.onyx.android.sdk.data.AppBaseInfo;
import java.util.ArrayList;
import com.onyx.android.sdk.data.AppDataInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.util.Iterator;
import com.onyx.android.sdk.device.Device;
import java.io.File;
import android.content.Context;
import java.lang.reflect.Method;
import java.util.List;

public class ApplicationUtil
{
    private static final String a = "ApplicationUtil";
    public static final String DONE_TAG = "done";
    public static final String DATA_KEEP = "/data/keep";
    private static final String b = "android.onyx.intent.action.APP_INFO";
    private static final String c = "package";
    private static final String d = "uid";
    public static final String ONYX_APP_PKG_TAG = "com.onyx";
    public static final String SIMPLE_APP_PKG_TAG = "com.simplemobiletools";
    public static final String ONYX_IGET_PKG_TAG = "com.onyx.igetshop";
    public static final String FLOAT_BUTTON_PACKAGE_NAME = "com.onyx.floatingbutton";
    public static final String SYSTEM_UI_PACKAGE_NAME = "com.android.systemui";
    private static final String e = "android.net.VpnService";
    public static final List<String> BROWSER_APP_LIST;
    public static final String PREINSTALL_FILTER_APPS_DIR;
    private static final Method f;
    private static final String g = "web_control_rule_set";
    private static final String h = "user_usage_mode";
    
    private static boolean a(final Context context, final String packageName) {
        return StringUtils.isNotBlank(packageName) && new File("/data/keep", packageName).exists();
    }
    
    private static boolean b(final Context context, final String packageName) {
        return StringUtils.isNotBlank(packageName) && StringUtils.isNotBlank(Device.currentDevice().readSystemConfig(context, packageName));
    }
    
    public static boolean testAppRecordExist(final Context context, final String packageName) {
        return a(context, packageName) || b(context, packageName);
    }
    
    public static boolean setSystemVerifyFlagDone(final Context context, final String verifyFlag) {
        Device.currentDevice().saveSystemConfig(context, verifyFlag, "done");
        return true;
    }
    
    public static boolean clearAllTestApps(final Context context, final List<String> testAppList) {
        if (testAppList == null) {
            return false;
        }
        final Iterator<String> iterator = testAppList.iterator();
        while (iterator.hasNext()) {
            Device.currentDevice().saveSystemConfig(context, (String)iterator.next(), "done");
        }
        return true;
    }
    
    public static boolean isResourceVerified(final Context context, final String verifyTag) {
        return a(context, verifyTag) || b(context, verifyTag);
    }
    
    public static boolean isSystemApp(final String pkgName, final PackageManager pkgManager) throws PackageManager.NameNotFoundException {
        int n;
        if (CompatibilityUtil.apiLevelCheck(28)) {
            n = 134217728;
        }
        else {
            n = 64;
        }
        return isSystemApp(pkgManager.getPackageInfo(pkgName, n));
    }
    
    public static boolean isSystemApp(final PackageInfo pkgInfo) {
        return isSystemApp(pkgInfo.applicationInfo);
    }
    
    public static boolean isSystemApp(final ApplicationInfo applicationInfo) {
        return (applicationInfo.flags & 0x81) > 0;
    }
    
    public static boolean isOnyxApp(final PackageInfo info) {
        return isOnyxApp(info.packageName);
    }
    
    public static boolean isOnyxApp(final ResolveInfo info) {
        return isOnyxApp(getPackageName(info));
    }
    
    public static boolean isOnyxApp(final String pkgName) {
        return StringUtils.safelyContains(pkgName, "com.onyx");
    }
    
    public static boolean isOnyxLauncher(final String pkgName) {
        return StringUtils.safelyEquals(pkgName, "com.onyx");
    }
    
    public static boolean isSimpleApp(final String pkgName) {
        return StringUtils.safelyContains(pkgName, "com.simplemobiletools");
    }
    
    public static boolean isBrowserApp(final String pkgName) {
        return CollectionUtils.safelyContains(ApplicationUtil.BROWSER_APP_LIST, pkgName);
    }
    
    public static boolean isFloatingButton(final String pkgName) {
        return StringUtils.safelyEquals(pkgName, "com.onyx.floatingbutton");
    }
    
    public static AppDataInfo appDataFromApplicationInfo(final Context context, final String packageName) {
        return appDataFromApplicationInfo(getPackageInfoFromPackageName(context, packageName), context.getPackageManager());
    }
    
    public static AppDataInfo appDataFromApplicationInfo(final PackageInfo pkgInfo, final PackageManager pkgManager) {
        return appDataFromApplicationInfo(pkgInfo, pkgManager, new ArrayList<ResolveInfo>());
    }
    
    public static AppDataInfo appDataFromApplicationInfo(final PackageInfo pkgInfo, final PackageManager pkgManager, final ResolveInfo resolveInfo) {
        final ArrayList list = new java.util.ArrayList();
        final ArrayList launcherResolveInfoList = list;
        new ArrayList(1);
        list.add(resolveInfo);
        return appDataFromApplicationInfo(pkgInfo, pkgManager, launcherResolveInfoList);
    }
    
    public static AppDataInfo appDataFromApplicationInfo(final PackageInfo pkgInfo, final PackageManager pkgManager, final List<ResolveInfo> launcherResolveInfoList) {
        return appDataFromApplicationInfo(pkgInfo, pkgManager, launcherResolveInfoList, new AppDataInfo());
    }
    
    public static AppDataInfo appDataFromApplicationInfo(final PackageInfo pkgInfo, final PackageManager pkgManager, final List<ResolveInfo> launcherResolveInfoList, final AppDataInfo appInfo) {
        if (pkgInfo == null) {
            return null;
        }
        final Intent launchIntentForPackage = ActivityUtil.getLaunchIntentForPackage(pkgManager, pkgInfo, launcherResolveInfoList);
        appInfo.packageName = pkgInfo.packageName;
        appInfo.isEnable = pkgInfo.applicationInfo.enabled;
        appInfo.lastUpdatedTime = pkgInfo.lastUpdateTime;
        appInfo.isSystemApp = isSystemApp(pkgInfo);
        appInfo.intent = launchIntentForPackage;
        appInfo.setType(AppBaseInfo.Type.APP);
        final String safelyGetStr;
        if (StringUtils.isNotBlank(safelyGetStr = StringUtils.safelyGetStr(pkgInfo.applicationInfo.loadLabel(pkgManager)))) {
            appInfo.labelName = safelyGetStr;
        }
        if (launchIntentForPackage != null) {
            if (launchIntentForPackage.getComponent() != null) {
                appInfo.activityClassName = launchIntentForPackage.getComponent().getClassName();
            }
            loadActivityInfo(pkgManager, launchIntentForPackage, appInfo);
        }
        else {
            try {
                if (pkgManager.getApplicationInfo(pkgInfo.packageName, 0).enabled) {
                    Debug.w(ApplicationUtil.a, pkgInfo.packageName
                            + " appDataFromApplicationInfo empty intent, not disabled app, return null",
                            new Object[0]);
                    return null;
                }
                Debug.w(ApplicationUtil.a, pkgInfo.packageName + " disabled", new Object[0]);
            } catch (PackageManager.NameNotFoundException exception) {
                Debug.w((Class) ApplicationUtil.class, (Throwable) exception);
            }
        }
        final Drawable loadIcon = pkgInfo.applicationInfo.loadIcon(pkgManager);
        if (appInfo.iconDrawable == null && loadIcon != null) {
            appInfo.iconDrawable = loadIcon;
        }
        final Drawable drawable;
        if ((drawable = ResManager.getDrawable(appInfo.iconDrawableName)) != null) {
            appInfo.iconDrawable = drawable;
        }
        return appInfo;
    }
    
    public static AppDataInfo appDataFromActivityInfo(final Context context, final LauncherActivityInfo activityInfo) {
        return appDataFromActivityInfo(context, new AppDataInfo(), activityInfo);
    }
    
    public static AppDataInfo appDataFromActivityInfo(final Context context, final AppDataInfo appDataInfo, final LauncherActivityInfo activityInfo) {
        appDataInfo.activityClassName = activityInfo.getComponentName().getClassName();
        appDataInfo.packageName = activityInfo.getComponentName().getPackageName();
        appDataInfo.labelName = StringUtils.safelyGetStr(StringUtils.ensureNotNull(String.valueOf(activityInfo.getLabel())), activityInfo.getName());
        appDataInfo.iconDrawable = activityInfo.getIcon(ResManager.getDensityDpi());
        appDataInfo.isSystemApp = isSystemApp(activityInfo.getApplicationInfo());
        appDataInfo.isWork = (DPMUtils.isMyUserHandle(context, appDataInfo.userId = DPMUtils.getIdForUser(context, activityInfo.getUser())) ^ true);
        appDataInfo.intent = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setComponent(activityInfo.getComponentName()).setFlags(270532608);
        return appDataInfo;
    }
    
    public static AppDataInfo appDataFromActivityInfoAsUser(final Context context, final AppDataInfo appDataInfo, ActivityInfo activityInfo, final UserHandle userHandle) {
        appDataInfo.activityClassName = activityInfo.name;
        appDataInfo.packageName = activityInfo.packageName;
        final PackageManager packageManager;
        appDataInfo.labelName = StringUtils.safelyGetStr(StringUtils.ensureNotNull(String.valueOf(activityInfo.loadLabel(packageManager = context.getPackageManager()))), activityInfo.name);
        appDataInfo.iconDrawable = activityInfo.loadIcon(packageManager);
        boolean systemApp = false;
        try {
            systemApp = isSystemApp(activityInfo.packageName, packageManager);
        }
        catch (final PackageManager.NameNotFoundException ex) {
            Debug.w((Class)ApplicationUtil.class, (Throwable)ex);
        }
        appDataInfo.isSystemApp = systemApp;
        appDataInfo.isWork = (DPMUtils.isMyUserHandle(context, appDataInfo.userId = DPMUtils.getIdForUser(context, userHandle)) ^ true);
        appDataInfo.intent = new Intent("android.intent.action.MAIN").addCategory("android.intent.category.LAUNCHER").setComponent(new ComponentName(appDataInfo.packageName, appDataInfo.activityClassName)).setFlags(270532608);
        return appDataInfo;
    }
    
    public static AppDataInfo appDataFromPackageInfo(Context context, final PackageInfo packageInfo) {
        AppDataInfo result = null;
        try {
            result = appDataFromApplicationInfo(packageInfo, context.getPackageManager());
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    public static AppDataInfo appDataFromPackageInfo(Context context, final List<ResolveInfo> apps, final PackageInfo packageInfo) {
        AppDataInfo result = null;
        try {
            result = appDataFromApplicationInfo(packageInfo, context.getPackageManager(), apps);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    @Nullable
    public static PackageInfo getPackageInfoFromPackageName(final Context context, final String packageName) {
        return PackageUtils.getPackageInfo(context, packageName);
    }
    
    public static boolean checkAppInstalled(final AppDataInfo appDataInfo) {
        try {
            LauncherAppsManager.getInstance().getApplicationInfo(appDataInfo);
            return true;
        }
        catch (final PackageManager.NameNotFoundException ex) {
            return false;
        }
    }
    
    public static PackageInfo getPermissionPackageInfo(final Context context, final String packageName) {
        try {
            return context.getPackageManager().getPackageInfo(packageName, 4096);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    @Nullable
    public static AppDataInfo getAppDataInfoFromPackageName(final Context context, final String packageName) {
        final PackageInfo packageInfoFromPackageName;
        if ((packageInfoFromPackageName = getPackageInfoFromPackageName(context, packageName)) == null) {
            return null;
        }
        final AppDataInfo appDataInfo = new com.onyx.android.sdk.data.AppDataInfo();
        final AppDataInfo appInfo = appDataInfo;
        final PackageInfo packageInfo = packageInfoFromPackageName;
        final AppDataInfo appDataInfo2 = appInfo;
        final AppDataInfo appDataInfo3 = appInfo;
        final PackageInfo pkgInfo = packageInfoFromPackageName;
        final AppDataInfo appDataInfo4 = appInfo;
        final PackageInfo packageInfo2 = packageInfoFromPackageName;
        final AppDataInfo appDataInfo5 = appInfo;
        final PackageInfo packageInfo3 = packageInfoFromPackageName;
        final AppDataInfo appDataInfo6 = appInfo;
        final PackageInfo packageInfo4 = packageInfoFromPackageName;
        new AppDataInfo();
        appDataInfo6.packageName = packageInfo4.packageName;
        appDataInfo5.labelName = packageInfo3.applicationInfo.loadLabel(context.getPackageManager()).toString();
        appDataInfo4.lastUpdatedTime = packageInfo2.lastUpdateTime;
        appDataInfo3.isSystemApp = isSystemApp(pkgInfo);
        appDataInfo2.intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        appDataInfo.iconDrawable = packageInfo.applicationInfo.loadIcon(context.getPackageManager());
        if (appDataInfo.intent != null) {
            loadActivityInfo(context.getPackageManager(), appInfo.intent, appInfo);
        }
        return appInfo;
    }
    
    public static void checkCustomIcon(final Context context, final Map<String, String> customizedIconAppsMap, final AppDataInfo appDataInfo) {
        if (customizedIconAppsMap == null) {
            return;
        }
        final String resourceName;
        if (StringUtils.isNullOrEmpty(resourceName = customizedIconAppsMap.get(getAppInfoLaunchName(appDataInfo)))) {
            return;
        }
        final Drawable drawableByResourceName;
        if ((drawableByResourceName = RawResourceUtil.getDrawableByResourceName(context, resourceName)) != null) {
            appDataInfo.iconDrawable = drawableByResourceName;
        }
    }
    
    public static void checkIconByTargetDir(final String fileDir, final AppDataInfo appDataInfo) {
        if (fileDir == null) {
            return;
        }
        File file;
        boolean b;
        if (!(b = (file = a(fileDir, getAppInfoLaunchName(appDataInfo))).exists())) {
            b = (file = a(fileDir, appDataInfo.getPackageName())).exists();
        }
        final Bitmap loadBitmapFromFile;
        if (b && BitmapUtils.isValid(loadBitmapFromFile = BitmapUtils.loadBitmapFromFile(file.getAbsolutePath()))) {
            appDataInfo.iconDrawable = (Drawable)new BitmapDrawable(loadBitmapFromFile);
        }
    }
    
    @Nullable
    public static Drawable loadIconByPackageName(final Context context, final String pkg, final Map<String, String> iconAppsMap, final String iconDir) {
        final Drawable a;
        if ((a = a(context, pkg, iconAppsMap, iconDir)) != null) {
            return a;
        }
        return PackageUtils.getAppIconDrawable(context, pkg);
    }
    
    @Nullable
    public static Drawable loadIconByPackageInfo(final Context context, final PackageInfo info, final Map<String, String> iconAppsMap, final String iconDir) {
        final Drawable a;
        if ((a = a(context, info.packageName, iconAppsMap, iconDir)) != null) {
            return a;
        }
        return context.getPackageManager().getApplicationIcon(info.applicationInfo);
    }
    
    @Nullable
    private static Drawable a(final Context context, final String pkgName, final Map<String, String> iconAppsMap, final String iconDir) {
        if (TextUtils.isEmpty((CharSequence)pkgName)) {
            return null;
        }
        if (CollectionUtils.safelyContains(iconAppsMap.keySet(), pkgName)) {
            return RawResourceUtil.getDrawableByResourceName(context, iconAppsMap.get(pkgName));
        }
        final File a;
        final Bitmap loadBitmapFromFile;
        if (!TextUtils.isEmpty((CharSequence)iconDir) && (a = a(iconDir, pkgName)).exists() && (loadBitmapFromFile = BitmapUtils.loadBitmapFromFile(a.getAbsolutePath())) != null) {
            return (Drawable)new BitmapDrawable(context.getResources(), loadBitmapFromFile);
        }
        return null;
    }
    
    public static File getOnyxApkIconFilePath(final String packageName) {
        return a(AppsConstant.ONYX_CUSTOM_ICON_CACHE_DIR, packageName);
    }
    
    private static File a(String fileDir, final String packageName) {
        final File parent;
        if (!(parent = new File(fileDir)).exists()) {
            Debug.i((Class)ApplicationUtil.class, fileDir + " mkdir status :" + parent.mkdirs(), new Object[0]);
        }
        fileDir = packageName.replaceAll("\\.", "_") + ".png";
        return new File(parent, fileDir);
    }
    
    public static Intent getAppInfoIntent(final Context context, final String packageName) {
        final Intent intent = new Intent("android.onyx.intent.action.APP_INFO");
        if (CompatibilityUtil.apiLevelCheck(26)) {
            intent.setPackage("com.android.settings");
        }
        intent.putExtra("package", packageName);
        final int appUid;
        if ((appUid = getAppUid(context, packageName)) >= 0) {
            intent.putExtra("uid", appUid);
        }
        return intent;
    }
    
    public static List<String> getAllIMEPkg() {
        return InputMethodUtils.getInstalledIMEPackageList(ResManager.getAppContext());
    }
    
    public static List<String> getAllTTSPkg() {
        final ArrayList list = new ArrayList();
        final Context appContext;
        if ((appContext = ResManager.getAppContext()) != null) {
            final TextToSpeech textToSpeech;
            final Iterator iterator = (textToSpeech = new TextToSpeech(appContext, (TextToSpeech.OnInitListener)null)).getEngines().iterator();
            while (iterator.hasNext()) {
                list.add(((TextToSpeech.EngineInfo)iterator.next()).name);
            }
            final ArrayList list2 = list;
            textToSpeech.shutdown();
            return list2;
        }
        throw new IllegalStateException("Init ResManager in your application first");
    }
    
    public static List<TextToSpeech.EngineInfo> getAllTTSEngine() {
        final Context appContext;
        if ((appContext = ResManager.getAppContext()) != null) {
            final TextToSpeech textToSpeech;
            final ArrayList list = new ArrayList<TextToSpeech.EngineInfo>((textToSpeech = new TextToSpeech(appContext, (TextToSpeech.OnInitListener)null)).getEngines());
            textToSpeech.shutdown();
            return (List<TextToSpeech.EngineInfo>)list;
        }
        throw new IllegalStateException("Init ResManager in your application first");
    }
    
    public static List<String> getAllVPNPkg() {
        final ArrayList list = new ArrayList();
        final Context appContext;
        if ((appContext = ResManager.getAppContext()) != null) {
            final PackageManager packageManager = appContext.getPackageManager();
            final Intent intent = new Intent();
            intent.setAction("android.net.VpnService");
            final Iterator iterator = packageManager.queryIntentServices(intent, 64).iterator();
            while (iterator.hasNext()) {
                final ServiceInfo serviceInfo;
                if ((serviceInfo = ((ResolveInfo)iterator.next()).serviceInfo) != null) {
                    list.add(serviceInfo.packageName);
                }
            }
            return list;
        }
        throw new IllegalStateException("Init ResManager in your application first");
    }
    
    public static List<ResolveInfo> getLaunchResolveInfoList(final PackageManager packageManager) {
        return packageManager.queryIntentActivities(ActivityUtil.buildMainLaunchIntent(), 512);
    }
    
    public static AppDataInfo getCustomAppDataInfo(final Context context, final AppDataInfo appData) {
        if (appData != null && appData.intent == null) {
            updateCustomAppDataInfo(context, appData);
            return appData;
        }
        return null;
    }
    
    public static AppDataInfo updateCustomAppDataInfo(final Context context, final AppDataInfo appData) {
        if (appData == null) {
            return null;
        }
        AppDataInfo appDataFromPackageInfo = null;
        final PackageInfo packageInfoFromPackageName;
        if (StringUtils.isNotBlank(appData.packageName) && (packageInfoFromPackageName = getPackageInfoFromPackageName(context, appData.packageName)) != null) {
            final PackageInfo packageInfo = packageInfoFromPackageName;
            appData.lastUpdatedTime = packageInfoFromPackageName.lastUpdateTime;
            appDataFromPackageInfo = appDataFromPackageInfo(context, packageInfo);
        }
        appData.labelName = appData.getName();
        if (appData.iconDrawable == null && StringUtils.isNotBlank(appData.iconDrawableName)) {
            appData.iconDrawable = ResManager.getDrawable(appData.iconDrawableName);
        }
        final PackageManager packageManager = context.getPackageManager();
        if (StringUtils.isNullOrEmpty(appData.activityClassName) && appDataFromPackageInfo != null) {
            appData.activityClassName = appDataFromPackageInfo.activityClassName;
        }
        if (appData.intent == null) {
            appData.intent = new Intent();
        }
        if (StringUtils.isNotBlank(appData.action)) {
            appData.intent.setAction(appData.action);
        }
        if (StringUtils.isNotBlank(appData.packageName) && StringUtils.isNotBlank(appData.activityClassName)) {
            appData.intent.setComponent(new ComponentName(appData.packageName, appData.activityClassName));
        }
        final CharSequence activityLabelName;
        String labelName;
        if (TextUtils.isEmpty(activityLabelName = getActivityLabelName(packageManager, appData.intent))) {
            labelName = appData.getName();
        }
        else {
            labelName = activityLabelName.toString();
        }
        appData.labelName = labelName;
        if (appData.iconDrawable == null) {
            if (StringUtils.isNotBlank(appData.iconDrawableName)) {
                appData.iconDrawable = ResManager.getDrawable(appData.iconDrawableName);
            }
            if (appData.iconDrawable == null) {
                appData.iconDrawable = getActivityIcon(packageManager, appData.intent);
            }
            if (appData.iconDrawable == null && appDataFromPackageInfo != null) {
                appData.iconDrawable = appDataFromPackageInfo.iconDrawable;
            }
        }
        return appData;
    }
    
    public static String getAppInfoLaunchName(final AppBaseInfo baseInfo) {
        return baseInfo.getLaunchName();
    }
    
    public static AppWidgetInfo getAppWidgetInfo(final Context context, final AppWidgetProviderInfo providerInfo) {
        return getAppWidgetInfo(context, providerInfo, true);
    }
    
    public static AppWidgetInfo getAppWidgetInfo(final Context context, final AppWidgetProviderInfo providerInfo, final boolean getAppIcon) {
        final AppWidgetInfo appWidgetInfo = new AppWidgetInfo();
        appWidgetInfo.idString = TestUtils.generateUniqueId();
        return getAppWidgetInfo(context, appWidgetInfo, providerInfo, getAppIcon);
    }
    
    public static AppWidgetInfo getAppWidgetInfo(final Context context, final AppWidgetInfo appWidgetInfo, final AppWidgetProviderInfo info) {
        return getAppWidgetInfo(context, appWidgetInfo, info, true);
    }
    
    public static AppWidgetInfo getAppWidgetInfo(final Context context, final AppWidgetInfo appWidgetInfo, final AppWidgetProviderInfo info, final boolean getAppIcon) {
        appWidgetInfo.setProviderInfo(context, info);
        final PackageInfo packageInfoFromPackageName;
        if ((packageInfoFromPackageName = getPackageInfoFromPackageName(context, info.provider.getPackageName())) == null) {
            return appWidgetInfo;
        }
        final int densityDpi = ResManager.getDensityDpi();
        appWidgetInfo.previewDrawable = ResManager.getDrawableForDensity(info.provider.getPackageName(), info.previewImage, densityDpi);
        Drawable iconDrawable;
        if (getAppIcon) {
            iconDrawable = packageInfoFromPackageName.applicationInfo.loadIcon(context.getPackageManager());
        }
        else {
            iconDrawable = ResManager.getDrawableForDensity(info.provider.getPackageName(), info.icon, densityDpi);
        }
        appWidgetInfo.iconDrawable = iconDrawable;
        return appWidgetInfo;
    }
    
    public static int pxToWidgetSpan(final Context context, final int minPxValue, final int maxSpan) {
        return Math.min(Math.max(1, (DimenUtils.px2dip(context, (float)minPxValue) + 30) / 70), maxSpan);
    }
    
    public static int spanToWidgetSize(final Context context, final int span, final int maxSpan) {
        return DimenUtils.dip2px(context, (float)(Math.min(span, maxSpan) * 70 - 30));
    }
    
    public static boolean isPreInstallApp(final AppDataInfo appDataInfo) {
        return appDataInfo.preInstall && !isPreInstallFilterApp(appDataInfo.packageName);
    }
    
    public static boolean isPreInstallFilterApp(final String packageName) {
        return !StringUtils.isNullOrEmpty(packageName) && a(ApplicationUtil.PREINSTALL_FILTER_APPS_DIR, packageName, false);
    }
    
    public static boolean addToPreInstallAppFilter(final String packageName) {
        return !StringUtils.isNullOrEmpty(packageName) && a(ApplicationUtil.PREINSTALL_FILTER_APPS_DIR, packageName, true);
    }
    
    private static boolean a(String dir, final String packageName, final boolean forceCreate) {
        FileUtils.mkdirs(dir);
        dir = new File(dir, packageName).getAbsolutePath();
        if (forceCreate) {
            return FileUtils.ensureFileExists(dir);
        }
        return FileUtils.fileExist(dir);
    }
    
    public static String getCurrentProcessName(final Context context) {
        if (context == null) {
            return "";
        }
        final int myPid = Process.myPid();
        String processName = "";
        final ActivityManager activityManager;
        if (CollectionUtils.isNonBlank((activityManager = (ActivityManager)context.getApplicationContext().getSystemService("activity")).getRunningAppProcesses())) {
            final Iterator iterator = activityManager.getRunningAppProcesses().iterator();
            while (iterator.hasNext()) {
                final ActivityManager.RunningAppProcessInfo runningAppProcessInfo;
                if ((runningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)iterator.next()).pid == myPid) {
                    processName = runningAppProcessInfo.processName;
                }
            }
        }
        return processName;
    }
    
    @Nullable
    public static String getInstalledBrowserPkgName(final Context context) {
        return getInstalledPkgName(context, ApplicationUtil.BROWSER_APP_LIST);
    }
    
    @Nullable
    public static String getInstalledPkgName(final Context context, final List<String> pkgList) {
        final Iterator<String> iterator = pkgList.iterator();
        while (iterator.hasNext()) {
            final String s;
            if (PackageUtils.checkAppInstalled(context, s = iterator.next())) {
                return s;
            }
        }
        return null;
    }
    
    public static boolean isAppResizeable(final Context context, final List<ResolveInfo> resolveInfo, final AppDataInfo info) {
        final String packageName = info.getPackageName();
        final String activityClassName = info.getActivityClassName();
        ComponentName a;
        if (!StringUtils.isNullOrEmpty(packageName) && !StringUtils.isNullOrEmpty(activityClassName) && !a(info)) {
            a = new ComponentName(packageName, activityClassName);
        }
        else {
            a = a(info.intent);
        }
        ActivityInfo activityInfo;
        if (a == null) {
            activityInfo = a(resolveInfo, packageName);
        }
        else if ((activityInfo = PackageUtils.getActivityInfo(context, a)) == null) {
            activityInfo = a(resolveInfo, packageName);
        }
        return isAppResizeable(activityInfo);
    }
    
    private static boolean a(final AppDataInfo info) {
        return info instanceof AppShortcutInfo && info.intent != null;
    }
    
    private static ActivityInfo a(final List<ResolveInfo> resolveInfo, final String pkgName) {
        ActivityInfo activityInfo = null;
        final Iterator<ResolveInfo> iterator = resolveInfo.iterator();
        while (iterator.hasNext()) {
            final ResolveInfo resolveInfo2;
            if (StringUtils.isEquals((resolveInfo2 = iterator.next()).activityInfo.packageName, pkgName)) {
                activityInfo = resolveInfo2.activityInfo;
                break;
            }
        }
        return activityInfo;
    }
    
    public static boolean isAppResizeable(final ActivityInfo activityInfo) {
        final Object invokeMethodSafely;
        return activityInfo != null && (invokeMethodSafely = ReflectUtil.invokeMethodSafely(ReflectUtil.getDeclaredMethodSafely((Class)ActivityInfo.class, "isResizeableMode", new Class[] { Integer.TYPE }), (Object)activityInfo, new Object[] { ReflectUtil.getDeclareIntFieldSafely((Class)ActivityInfo.class, (Object)activityInfo, "resizeMode") })) instanceof Boolean && (boolean)invokeMethodSafely;
    }
    
    private static ComponentName a(final Intent intent) {
        if (intent == null) {
            return null;
        }
        return intent.getComponent();
    }
    
    public static boolean isSameAppDataInfo(final AppDataInfo first, final AppDataInfo second) {
        return StringUtils.safelyEquals(getAppInfoLaunchName(first), getAppInfoLaunchName(second));
    }
    
    public static Comparator<AppDataInfo> getAppInfoLaunchNameComparator() {
        return new Comparator<AppDataInfo>() {
            public int compare(final AppDataInfo o1, final AppDataInfo o2) {
                return (ApplicationUtil.isSameAppDataInfo(o1, o2) ^ true) ? 1 : 0;
            }
        };
    }
    
    public static void loadActivityInfo(final PackageManager pkgManager, final Intent i, final AppDataInfo appInfo) {
        final CharSequence activityLabelName;
        if (!TextUtils.isEmpty(activityLabelName = getActivityLabelName(pkgManager, i))) {
            appInfo.labelName = activityLabelName.toString();
        }
        final Drawable activityIcon;
        if ((activityIcon = getActivityIcon(pkgManager, i)) != null) {
            appInfo.iconDrawable = activityIcon;
        }
    }
    
    public static CharSequence getActivityLabelName(final PackageManager pkgManager, final Intent i) {
        return getActivityLabelName(pkgManager, i, 0);
    }
    
    public static CharSequence getActivityLabelName(final PackageManager pkgManager, final Intent i, final int flags) {
        if (i == null) {
            return null;
        }
        final ResolveInfo resolveActivity;
        if ((resolveActivity = pkgManager.resolveActivity(i, flags)) != null) {
            return resolveActivity.loadLabel(pkgManager);
        }
        return null;
    }
    
    @Nullable
    public static Drawable loadAppIcon(final PackageManager pkgManager, final String packageName) {
        final PackageInfo packageInfo;
        return ((packageInfo = PackageUtils.getPackageInfo(pkgManager, packageName)) == null) ? null : packageInfo.applicationInfo.loadIcon(pkgManager);
    }
    
    public static Drawable getActivityIcon(final PackageManager pkgManager, final Intent i) {
        final ResolveInfo resolveActivity;
        if ((resolveActivity = pkgManager.resolveActivity(i, 0)) != null) {
            return resolveActivity.loadIcon(pkgManager);
        }
        return null;
    }
    
    @RequiresApi(api = 26)
    public static LauncherApps.PinItemRequest getPinItemRequest(final Intent intent) {
        final Parcelable parcelableExtra;
        return ((parcelableExtra = intent.getParcelableExtra("android.content.pm.extra.PIN_ITEM_REQUEST")) instanceof LauncherApps.PinItemRequest) ? (LauncherApps.PinItemRequest) parcelableExtra : null;
    }
    
    public static AppType getApplicationType(@NonNull final Context context, @NonNull final String pkgName) {
        if (TextUtils.isEmpty((CharSequence)pkgName)) {
            return AppType.Error;
        }
        if (isOnyxApp(pkgName)) {
            return AppType.Onyx;
        }
        try {
            if (isSystemApp(pkgName, context.getPackageManager())) {
                return AppType.System;
            }
            return AppType.ThirdParty;
        }
        catch (final PackageManager.NameNotFoundException ex) {
            ex.printStackTrace();
            return AppType.Error;
        }
    }
    
    public static AppType getApplicationType(@NonNull final PackageInfo info) {
        if (TextUtils.isEmpty((CharSequence)info.packageName)) {
            return AppType.Error;
        }
        if (isOnyxApp(info.packageName)) {
            return AppType.Onyx;
        }
        return isSystemApp(info) ? AppType.System : AppType.ThirdParty;
    }
    
    @Nullable
    public static Context getApplicationContext() {
        final Context context;
        if ((context = (Context)ReflectUtil.invokeMethodSafely(ApplicationUtil.f, (Object)null, new Object[0])) == null) {
            Debug.e((Class)ApplicationUtil.class, "getApplicationContext failed", new Object[0]);
        }
        return context;
    }
    
    public static List<ResolveInfo> queryMatchResolveInfo(final Context context, final String action, final Predicate<ResolveInfo> test) {
        return CollectionUtils.findItems(context.getPackageManager().queryIntentActivities(new Intent(action), 0), test);
    }
    
    public static String getPackageName(final ResolveInfo resolveInfo) {
        final String resolvePackageName;
        if (StringUtils.isNotBlank(resolvePackageName = resolveInfo.resolvePackageName)) {
            return resolvePackageName;
        }
        Object o;
        if ((o = resolveInfo.activityInfo) == null) {
            if ((o = resolveInfo.providerInfo) == null) {
                o = resolveInfo.serviceInfo;
            }
        }
        if (o != null) {
            return ((ComponentInfo)o).packageName;
        }
        return null;
    }
    
    public static int getAppUid(final Context context, final String pkgName) {
        final ApplicationInfo applicationInfo;
        return ((applicationInfo = PackageUtils.getApplicationInfo(context, pkgName)) == null) ? -1 : applicationInfo.uid;
    }
    
    public static long getAppStorageSize(final Context context, final StorageStatsManager storageStatsManager, final String packageName) {
        final StorageStats appStorageStats;
        if ((appStorageStats = getAppStorageStats(context, storageStatsManager, packageName)) == null) {
            return 0L;
        }
        return appStorageStats.getAppBytes() + appStorageStats.getCacheBytes() + appStorageStats.getDataBytes();
    }
    
    public static StorageStats getAppStorageStats(final Context context, final StorageStatsManager storageStatsManager, final String packageName) {
        try {
            return storageStatsManager.queryStatsForUid(
                    StorageManager.UUID_DEFAULT, getAppUid(context, packageName));
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static void saveWebControlRuleSet(final Context context, final String value) {
        Device.currentDevice().saveSystemConfig(context, "web_control_rule_set", value);
        Device.currentDevice().setSystemConfigFilePermission(context, "web_control_rule_set", true, false);
        BroadcastHelper.sendBroadcast(context, "onyx.action.child.WEB_CONTROL_RULE_SET_CHANGED");
    }
    
    public static void setUserMode(final Context context, final int mode) {
        ReflectUtil.invokeMethodSafely(EACReflectUtils.sMethodSetUserMode, (Object)null, new Object[] { mode });
        DeviceBroadcastHelper.sendBroadcast(context, new Intent("onyx.action.child.USAGE_MODE_CHANGED").putExtra("user_usage_mode", mode));
    }
    
    public static int getUserMode() {
        final Integer n;
        if ((n = (Integer)ReflectUtil.invokeMethodSafely(EACReflectUtils.sMethodGetUserMode, (Object)null, new Object[0])) == null) {
            return 0;
        }
        return Math.max(0, n);
    }
    
    public static boolean isChildUserMode() {
        return getUserMode() != 0;
    }
    
    public static void enterChildUserMode(final Context context) {
        setUserMode(context, 1);
    }
    
    public static void quitChildUserMode(final Context context) {
        setUserMode(context, 0);
    }
    
    static {
        BROWSER_APP_LIST = Arrays.asList("org.chromium.chrome", "com.android.chrome", "com.android.browser");
        PREINSTALL_FILTER_APPS_DIR = Device.currentDevice().getSystemConfigPrefix(ResManager.getAppContext()) + "preinstall_filter_apps";
        f = ReflectUtil.getMethodSafely(ReflectUtil.classForName("android.app.ActivityThread"), "currentApplication", new Class[0]);
    }
    
    public enum AppType
    {
        Onyx, 
        System, 
        ThirdParty, 
        Error;
    }
}
