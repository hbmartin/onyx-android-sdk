
package com.onyx.android.sdk.utils;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.content.pm.PackageItemInfo;
import androidx.annotation.NonNull;
import java.util.HashSet;
import java.util.Set;
import java.util.Arrays;
import java.lang.reflect.Method;
import androidx.annotation.Nullable;
import android.os.Bundle;
import java.util.Date;
import android.graphics.drawable.Drawable;
import android.content.pm.PackageInfo;
import android.os.Build;
import java.util.List;
import android.app.ActivityManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import android.app.PendingIntent;
import java.io.IOException;
import java.util.Iterator;
import java.util.Collections;
import androidx.annotation.RequiresApi;
import android.content.pm.PackageInstaller;
import java.util.Collection;
import android.content.BroadcastReceiver;
import android.util.Log;
import android.net.Uri;
import android.content.Intent;
import java.io.File;
import android.content.Context;
import android.os.Process;
import android.content.ComponentName;

public class PackageUtils
{
    public static final String TAG = "PackageUtils";
    public static final int APP_INSTALL_AUTO = 0;
    public static final int APP_INSTALL_INTERNAL = 1;
    public static final int APP_INSTALL_EXTERNAL = 2;
    public static final String APP_CHANNEL = "channel";
    public static final String APP_CHANNEL_ONYX = "ONYX";
    public static final String APP_TYPE = "type";
    public static final String APP_TYPE_RELEASE = "RELEASE";
    public static final String APP_TYPE_DEBUG = "DEBUG";
    public static final String APP_PLATFORM = "platform";
    public static final String APP_PLATFORM_ONYX = "ONYX";
    public static final String APP_PLATFORM_RK3026 = "RK3026";
    public static final String APP_PLATFORM_IMX6 = "IMX6";
    public static final String APP_TIMESTAMP = "timestamp";
    private static final String a = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install ";
    private static final String b = "LD_LIBRARY_PATH=/vendor/lib64:/system/lib64 pm install ";
    private static final ComponentName[] c;
    private static ComponentName d;
    public static final int INSTALL_SUCCEEDED = 1;
    public static final int INSTALL_FAILED_ALREADY_EXISTS = -1;
    public static final int INSTALL_FAILED_INVALID_APK = -2;
    public static final int INSTALL_FAILED_INVALID_URI = -3;
    public static final int INSTALL_FAILED_INSUFFICIENT_STORAGE = -4;
    public static final int INSTALL_FAILED_DUPLICATE_PACKAGE = -5;
    public static final int INSTALL_FAILED_NO_SHARED_USER = -6;
    public static final int INSTALL_FAILED_UPDATE_INCOMPATIBLE = -7;
    public static final int INSTALL_FAILED_SHARED_USER_INCOMPATIBLE = -8;
    public static final int INSTALL_FAILED_MISSING_SHARED_LIBRARY = -9;
    public static final int INSTALL_FAILED_REPLACE_COULDNT_DELETE = -10;
    public static final int INSTALL_FAILED_DEXOPT = -11;
    public static final int INSTALL_FAILED_OLDER_SDK = -12;
    public static final int INSTALL_FAILED_CONFLICTING_PROVIDER = -13;
    public static final int INSTALL_FAILED_NEWER_SDK = -14;
    public static final int INSTALL_FAILED_TEST_ONLY = -15;
    public static final int INSTALL_FAILED_CPU_ABI_INCOMPATIBLE = -16;
    public static final int INSTALL_FAILED_MISSING_FEATURE = -17;
    public static final int INSTALL_FAILED_CONTAINER_ERROR = -18;
    public static final int INSTALL_FAILED_INVALID_INSTALL_LOCATION = -19;
    public static final int INSTALL_FAILED_MEDIA_UNAVAILABLE = -20;
    public static final int INSTALL_FAILED_VERIFICATION_TIMEOUT = -21;
    public static final int INSTALL_FAILED_VERIFICATION_FAILURE = -22;
    public static final int INSTALL_FAILED_PACKAGE_CHANGED = -23;
    public static final int INSTALL_FAILED_UID_CHANGED = -24;
    public static final int INSTALL_PARSE_FAILED_NOT_APK = -100;
    public static final int INSTALL_PARSE_FAILED_BAD_MANIFEST = -101;
    public static final int INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION = -102;
    public static final int INSTALL_PARSE_FAILED_NO_CERTIFICATES = -103;
    public static final int INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES = -104;
    public static final int INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING = -105;
    public static final int INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME = -106;
    public static final int INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID = -107;
    public static final int INSTALL_PARSE_FAILED_MANIFEST_MALFORMED = -108;
    public static final int INSTALL_PARSE_FAILED_MANIFEST_EMPTY = -109;
    public static final int INSTALL_FAILED_INTERNAL_ERROR = -110;
    public static final int INSTALL_FAILED_OTHER = -1000000;
    public static final int DELETE_SUCCEEDED = 1;
    public static final int DELETE_FAILED_INTERNAL_ERROR = -1;
    public static final int DELETE_FAILED_DEVICE_POLICY_MANAGER = -2;
    public static final int DELETE_FAILED_INVALID_PACKAGE = -3;
    public static final int DELETE_FAILED_PERMISSION_DENIED = -4;
    
    private static String a() {
        if (CompatibilityUtil.apiLevelCheck(23)) {
            return Process.is64Bit() ? "LD_LIBRARY_PATH=/vendor/lib64:/system/lib64 pm install " : "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install ";
        }
        return "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install ";
    }
    
    public static Intent installIntent(final Context context, final File file) {
        final ComponentName usableDefaultInstallerComponentName = getUsableDefaultInstallerComponentName(context);
        final Intent installIntent = getInstallIntent(file);
        if (usableDefaultInstallerComponentName != null) {
            installIntent.setComponent(usableDefaultInstallerComponentName);
        }
        return installIntent;
    }
    
    public static Intent getInstallIntent(final File file) {
        final Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(Uri.parse("file://" + file.getAbsolutePath()), "application/vnd.android.package-archive");
        intent.addFlags(268435456);
        return intent;
    }
    
    public static final int install(final Context context, final String filePath) {
        if (!isSystemApplication(context) && !ShellUtils.checkRootPermission()) {
            return installNormal(context, filePath) ? 1 : -3;
        }
        return installSilent(context, filePath);
    }
    
    public static boolean installNormal(final Context context, final String filePath) {
        final File file;
        if ((file = new File(filePath)).exists() && file.isFile() && file.length() > 0L) {
            context.startActivity(installIntent(context, file));
            return true;
        }
        return false;
    }
    
    public static int installSilent(final Context context, final String filePath) {
        return installSilent(context, filePath, " -r " + a(getInstallLocation()));
    }
    
    public static int installSilent(final Context context, final String filePath, final boolean isRoot) {
        return installSilent(context, filePath, " -r " + a(1), isRoot);
    }
    
    public static int installSilent(final Context context, final String filePath, final String pmParams) {
        return installSilent(context, filePath, pmParams, isSystemApplication(context) ^ true);
    }
    
    public static int installSilent(final Context context, String filePath, String pmParams, final boolean isRoot) {
        if (filePath == null || filePath.length() == 0) {
            return -3;
        }
        final File file;
        if ((file = new File(filePath)).length() <= 0L || !file.exists() || !file.isFile()) {
            return -3;
        }
        final String s = pmParams;
        final StringBuilder append = new StringBuilder().append(a());
        if (s == null) {
            pmParams = "";
        }
        final ShellUtils.CommandResult execCommand;
        if ((filePath = (execCommand = ShellUtils.execCommand(append.append(pmParams).append(" ").append(filePath.replace(" ", "\\ ")).toString(), isRoot, true)).successMsg) != null && (filePath.contains("Success") || execCommand.successMsg.contains("success"))) {
            return 1;
        }
        final ShellUtils.CommandResult commandResult = execCommand;
        Log.e("PackageUtils", "installSilent successMsg:" + execCommand.successMsg + ", ErrorMsg:" + execCommand.errorMsg);
        if ((filePath = commandResult.errorMsg) == null) {
            return -1000000;
        }
        if (filePath.contains("INSTALL_FAILED_ALREADY_EXISTS")) {
            return -1;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_INVALID_APK")) {
            return -2;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_INVALID_URI")) {
            return -3;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_INSUFFICIENT_STORAGE")) {
            return -4;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_DUPLICATE_PACKAGE")) {
            return -5;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_NO_SHARED_USER")) {
            return -6;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_UPDATE_INCOMPATIBLE")) {
            return -7;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_SHARED_USER_INCOMPATIBLE")) {
            return -8;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_MISSING_SHARED_LIBRARY")) {
            return -9;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_REPLACE_COULDNT_DELETE")) {
            return -10;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_DEXOPT")) {
            return -11;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_OLDER_SDK")) {
            return -12;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_CONFLICTING_PROVIDER")) {
            return -13;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_NEWER_SDK")) {
            return -14;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_TEST_ONLY")) {
            return -15;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_CPU_ABI_INCOMPATIBLE")) {
            return -16;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_MISSING_FEATURE")) {
            return -17;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_CONTAINER_ERROR")) {
            return -18;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_INVALID_INSTALL_LOCATION")) {
            return -19;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_MEDIA_UNAVAILABLE")) {
            return -20;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_VERIFICATION_TIMEOUT")) {
            return -21;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_VERIFICATION_FAILURE")) {
            return -22;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_PACKAGE_CHANGED")) {
            return -23;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_UID_CHANGED")) {
            return -24;
        }
        if (execCommand.errorMsg.contains("INSTALL_PARSE_FAILED_NOT_APK")) {
            return -100;
        }
        if (execCommand.errorMsg.contains("INSTALL_PARSE_FAILED_BAD_MANIFEST")) {
            return -101;
        }
        if (execCommand.errorMsg.contains("INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION")) {
            return -102;
        }
        if (execCommand.errorMsg.contains("INSTALL_PARSE_FAILED_NO_CERTIFICATES")) {
            return -103;
        }
        if (execCommand.errorMsg.contains("INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES")) {
            return -104;
        }
        if (execCommand.errorMsg.contains("INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING")) {
            return -105;
        }
        if (execCommand.errorMsg.contains("INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME")) {
            return -106;
        }
        if (execCommand.errorMsg.contains("INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID")) {
            return -107;
        }
        if (execCommand.errorMsg.contains("INSTALL_PARSE_FAILED_MANIFEST_MALFORMED")) {
            return -108;
        }
        if (execCommand.errorMsg.contains("INSTALL_PARSE_FAILED_MANIFEST_EMPTY")) {
            return -109;
        }
        if (execCommand.errorMsg.contains("INSTALL_FAILED_INTERNAL_ERROR")) {
            return -110;
        }
        return -1000000;
    }
    
    @RequiresApi(api = 21)
    public static boolean installSilentByStreamForTargetUser(final Context context, final Class<? extends BroadcastReceiver> receiverClass, final Collection<String> splitApkFiles, int userID) {
        final PackageInstaller packageInstaller;
        if ((userID = DPMUtils.createSessionForTargetUser(packageInstaller = context.getPackageManager().getPackageInstaller(), createSessionParams(splitApkFiles), userID)) == -1) {
            Debug.e("PackageUtils", "can't create package install session", new Object[0]);
            return false;
        }
        return a(packageInstaller, userID, splitApkFiles) && a(context, receiverClass, packageInstaller, userID);
    }
    
    @RequiresApi(api = 21)
    public static void installSilentByStream(final Context context, final Class<? extends BroadcastReceiver> receiverClass, final String apkFilePath) {
        installSilentByStream(context, receiverClass, Collections.singleton(apkFilePath));
    }
    
    @RequiresApi(api = 21)
    public static void installSilentByStream(final Context context, final Class<? extends BroadcastReceiver> receiverClass, final Collection<String> splitApkFiles) {
        a(context, receiverClass, createSession(context, splitApkFiles), splitApkFiles);
    }
    
    @RequiresApi(api = 21)
    public static void installSilentByStream(final Context context, final Class<? extends BroadcastReceiver> receiverClass, final String apkFilePath, final int sessionId) {
        a(context, receiverClass, sessionId, Collections.singleton(apkFilePath));
    }
    
    @RequiresApi(api = 21)
    private static void a(final Context context, final Class<? extends BroadcastReceiver> receiverClass, final int sessionId, final Collection<String> splitApkFiles) {
        final PackageInstaller packageInstaller;
        if (sessionId != -1 && a(packageInstaller = context.getPackageManager().getPackageInstaller(), sessionId, splitApkFiles)) {
            a(context, receiverClass, packageInstaller, sessionId);
        }
    }
    
    public static PackageInstaller.SessionParams createSessionParams(final Collection<String> splitApkFiles) {
        long size = 0L;
        final Iterator<String> iterator = splitApkFiles.iterator();
        while (iterator.hasNext()) {
            size += new File(iterator.next()).length();
        }
        final PackageInstaller.SessionParams sessionParams = new PackageInstaller.SessionParams(1);
        sessionParams.setSize(size);
        return sessionParams;
    }
    
    @RequiresApi(api = 21)
    public static int createSession(final Context context, final String splitApkFile) {
        return createSession(context, Collections.singleton(splitApkFile));
    }
    
    @RequiresApi(api = 21)
    public static int createSession(final Context context, final Collection<String> splitApkFiles) {
        return a(context.getPackageManager().getPackageInstaller(), createSessionParams(splitApkFiles));
    }
    
    @RequiresApi(api = 21)
    private static int a(PackageInstaller packageInstaller, final PackageInstaller.SessionParams sessionParams) {
        int sessionId = -1;
        try {
            sessionId = packageInstaller.createSession(sessionParams);
        }
        catch (final IOException ex) {
            ex.printStackTrace();
        }
        return sessionId;
    }
    
    private static boolean a(final PackageInstaller packageInstaller, final int sessionId, final Collection<String> splitApkFiles) {
        boolean a = false;
        final Iterator<String> iterator = splitApkFiles.iterator();
        while (iterator.hasNext()) {
            if (!(a = a(packageInstaller, sessionId, iterator.next()))) {
                return false;
            }
        }
        return a;
    }
    
    @RequiresApi(api = 21)
    private static boolean a(final PackageInstaller packageInstaller, final int sessionId, final String apkFilePath) {
        PackageInstaller.Session session = null;
        InputStream input = null;
        OutputStream output = null;
        try {
            File apk = new File(apkFilePath);
            session = packageInstaller.openSession(sessionId);
            output = session.openWrite(apk.getName(), 0L, apk.length());
            input = new FileInputStream(apk);
            byte[] buffer = new byte[65536];
            int streamed = 0;
            int count;
            while ((count = input.read(buffer)) != -1) {
                streamed += count;
                output.write(buffer, 0, count);
            }
            session.fsync(output);
            Debug.i(PackageUtils.class, "streamed: " + streamed + " bytes", new Object[0]);
            return true;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        finally {
            FileUtils.closeQuietly(output);
            FileUtils.closeQuietly(input);
            FileUtils.closeQuietly((Closeable) session);
        }
    }
    
    @RequiresApi(api = 21)
    private static boolean a(final Context context, final Class<? extends BroadcastReceiver> receiverClass, PackageInstaller packageInstaller, final int sessionId) {
        PackageInstaller.Session session = null;
        try {
            session = packageInstaller.openSession(sessionId);
            session.commit(PendingIntent.getBroadcast(
                    context,
                    1,
                    new Intent(context, receiverClass),
                    134217728).getIntentSender());
            return true;
        } catch (final IOException ex) {
            ex.printStackTrace();
            return false;
        } finally {
            FileUtils.closeQuietly((Closeable) session);
        }
    }
    
    public static final int uninstall(final Context context, final String packageName) {
        if (!isSystemApplication(context) && !ShellUtils.checkRootPermission()) {
            return uninstallNormal(context, packageName) ? 1 : -3;
        }
        return uninstallSilent(context, packageName);
    }
    
    public static boolean uninstallNormal(final Context context, final String packageName) {
        return uninstallNormal(context, packageName, DPMUtils.getMyUserId(context));
    }
    
    public static boolean uninstallNormal(final Context context, final String packageName, final int userId) {
        if (StringUtils.isNullOrEmpty(packageName)) {
            return false;
        }
        Debug.d((Class)PackageUtils.class, "uninstallNormal:" + packageName + " " + userId, new Object[0]);
        return ActivityUtil.startActivitySafely(context, new Intent("android.intent.action.DELETE", createPackageUri(packageName)).putExtra("android.intent.extra.USER", (Parcelable)DPMUtils.getUserForId(context, userId)).addFlags(268435456));
    }
    
    public static int uninstallSilent(final Context context, final String packageName) {
        return uninstallSilent(context, packageName, true);
    }
    
    public static int uninstallSilent(final Context context, String packageName, final boolean isKeepData) {
        if (packageName == null || packageName.length() == 0) {
            return -3;
        }
        final StringBuilder append = new StringBuilder().append("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm uninstall");
        String str;
        if (isKeepData) {
            str = " -k ";
        }
        else {
            str = " ";
        }
        final ShellUtils.CommandResult execCommand;
        if ((packageName = (execCommand = ShellUtils.execCommand(append.append(str).append(packageName.replace(" ", "\\ ")).toString(), isSystemApplication(context) ^ true, true)).successMsg) != null && (packageName.contains("Success") || execCommand.successMsg.contains("success"))) {
            return 1;
        }
        final ShellUtils.CommandResult commandResult = execCommand;
        Log.e("PackageUtils", "uninstallSilent successMsg:" + execCommand.successMsg + ", ErrorMsg:" + execCommand.errorMsg);
        final String errorMsg;
        if ((errorMsg = commandResult.errorMsg) == null) {
            return -1;
        }
        if (errorMsg.contains("Permission denied")) {
            return -4;
        }
        return -1;
    }
    
    public static boolean isSystemApplication(final Context context) {
        return context != null && isSystemApplication(context, context.getPackageName());
    }
    
    public static boolean isSystemApplication(final Context context, final String packageName) {
        return context != null && isSystemApplication(context.getPackageManager(), packageName);
    }
    
    public static boolean isSystemApplication(final PackageManager packageManager, final String packageName) {
        if (packageManager != null && packageName != null) {
            if (packageName.length() != 0) {
                try {
                    final ApplicationInfo applicationInfo;
                    return (applicationInfo = packageManager.getApplicationInfo(packageName, 0)) != null && (applicationInfo.flags & 0x1) > 0;
                }
                catch (final PackageManager.NameNotFoundException ex) {
                    ex.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }
    
    public static Boolean isTopActivity(final Context context, final String packageName) {
        if (context != null && !StringUtils.isNullOrEmpty(packageName)) {
            final List runningTasks;
            if ((runningTasks = ((ActivityManager)context.getSystemService("activity")).getRunningTasks(1)) != null) {
                if (runningTasks.size() > 0) {
                    try {
                        return packageName.equals(((ActivityManager.RunningTaskInfo)runningTasks.get(0)).topActivity.getPackageName());
                    }
                    catch (final Exception ex) {
                        ex.printStackTrace();
                        return Boolean.FALSE;
                    }
                }
            }
            return null;
        }
        return null;
    }
    
    public static int getTopTaskId() {
        final Iterator iterator;
        if ((iterator = ((ActivityManager)ResManager.getAppContext().getSystemService("activity")).getRunningTasks(Integer.MAX_VALUE).iterator()).hasNext()) {
            return ((ActivityManager.RunningTaskInfo)iterator.next()).id;
        }
        return 0;
    }
    
    public static int getAppVersionCode(final Context context) {
        return getAppVersionCode(context, context.getPackageName());
    }
    
    public static int getInstallLocation() {
        final ShellUtils.CommandResult execCommand;
        final String successMsg;
        if ((execCommand = ShellUtils.execCommand("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm get-install-location", (boolean)(0 != 0), (boolean)(1 != 0))).result == 0 && (successMsg = execCommand.successMsg) != null && successMsg.length() > 0) {
            try {
                final int int1;
                if ((int1 = Integer.parseInt(execCommand.successMsg.substring(0, 1))) == 1) {
                    return 1;
                }
                if (int1 == 2) {
                    return 2;
                }
            }
            catch (final NumberFormatException ex) {
                ex.printStackTrace();
                Log.e("PackageUtils", "pm get-install-location error");
            }
        }
        return 0;
    }
    
    private static String a(final int location) {
        if (location == 1) {
            return "-f";
        }
        if (location != 2) {
            return "";
        }
        return "-s";
    }
    
    public static void startInstalledAppDetails(final Context context, final String packageName) {
        final Intent intent = new Intent();
        final int sdk_INT;
        if ((sdk_INT = Build.VERSION.SDK_INT) >= 9) {
            final Intent intent2 = intent;
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent2.setData(createPackageUri(packageName));
        }
        else {
            final int n = sdk_INT;
            final Intent intent3 = intent;
            intent3.setAction("android.intent.action.VIEW");
            intent3.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            String s;
            if (n == 8) {
                s = "pkg";
            }
            else {
                s = "com.android.settings.ApplicationPkgName";
            }
            intent.putExtra(s, packageName);
        }
        intent.addFlags(268435456);
        context.startActivity(intent);
    }
    
    public static boolean checkAppInstalled(final Context context, final String packageName) {
        if (StringUtils.isNullOrEmpty(packageName)) {
            return false;
        }
        try {
            context.getPackageManager().getApplicationInfo(packageName, 8192);
            return true;
        }
        catch (final PackageManager.NameNotFoundException ex) {
            return false;
        }
    }
    
    public static String getAppVersionName(final Context context) {
        return getAppVersionName(context, context.getPackageName());
    }
    
    public static String getAppVersionName(final Context context, final String packageName) {
        final PackageInfo packageInfo;
        return ((packageInfo = getPackageInfo(context, packageName)) == null) ? null : packageInfo.versionName;
    }
    
    public static int getAppVersionCode(final Context context, final String packageName) {
        final PackageInfo packageInfo;
        return ((packageInfo = getPackageInfo(context, packageName)) == null) ? -1 : packageInfo.versionCode;
    }
    
    public static String getAppDisplayName(final Context context, final String packageName) {
        final PackageInfo packageInfo;
        return ((packageInfo = getPackageInfo(context, packageName)) == null) ? null : ((String)packageInfo.applicationInfo.loadLabel(context.getPackageManager()));
    }
    
    public static Drawable getAppIconDrawable(Context context, final String packageName) {
        Drawable result = null;
        try {
            result = context.getPackageManager().getApplicationIcon(packageName);
        }
        catch (final PackageManager.NameNotFoundException ex) {
            Debug.i((Class)ex.getClass(), "getAppIconDrawable:" + packageName, new Object[0]);
        }
        return result;
    }
    
    public static String getAppType(final Context context) {
        return getAppType(context, context.getPackageName());
    }
    
    public static String getAppType(final Context context, final String packageName) {
        final String applicationMetaData;
        if (StringUtils.isNotBlank(applicationMetaData = getApplicationMetaData(context, packageName, "type"))) {
            return applicationMetaData;
        }
        return "RELEASE";
    }
    
    public static String getAppChannel(final Context context) {
        return getAppChannel(context, context.getPackageName());
    }
    
    public static String getAppChannel(final Context context, final String packageName) {
        final String applicationMetaData;
        if (StringUtils.isNotBlank(applicationMetaData = getApplicationMetaData(context, packageName, "channel"))) {
            return applicationMetaData;
        }
        return "ONYX";
    }
    
    public static String getAppPlatform(final Context context) {
        return getAppPlatform(context, context.getPackageName());
    }
    
    public static String getAppPlatform(final Context context, final String packageName) {
        final String applicationMetaData;
        if (StringUtils.isNotBlank(applicationMetaData = getApplicationMetaData(context, packageName, "platform"))) {
            return applicationMetaData;
        }
        return "ONYX";
    }
    
    private static long a(final String longString) {
        long long1 = -1L;
        try {
            long1 = Long.parseLong(longString);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
        return long1;
    }
    
    public static Date getAppTimestamp(final Context context, final String packageName) {
        final String applicationMetaData;
        if (StringUtils.isNullOrEmpty(applicationMetaData = getApplicationMetaData(context, packageName, "timestamp"))) {
            return getApkFileCreatedTime(context, packageName);
        }
        final long a;
        Date date;
        if ((a = a(applicationMetaData.replaceAll("L", ""))) <= 0L) {
            date = null;
        }
        else {
            date = new Date(a);
        }
        return date;
    }
    
    public static String getApplicationMetaData(final Context context, final String packageName, final String key) {
        final ApplicationInfo applicationInfo;
        final Bundle metaData;
        if ((applicationInfo = getApplicationInfo(context, packageName)) != null && (metaData = applicationInfo.metaData) != null) {
            return metaData.getString(key);
        }
        return null;
    }
    
    @Nullable
    public static ApplicationInfo getApplicationInfo(final Context context, final String packageName) {
        return getApplicationInfo(context, packageName, 128);
    }
    
    @Nullable
    public static ApplicationInfo getApplicationInfo(Context context, final String packageName, final int flag) {
        ApplicationInfo result = null;
        try {
            result = context.getPackageManager().getApplicationInfo(packageName, flag);
        }
        catch (final PackageManager.NameNotFoundException ex) {
            Debug.i((Class)ex.getClass(), "getApplicationInfo:" + packageName, new Object[0]);
        }
        return result;
    }
    
    @Nullable
    public static ApplicationInfo getApplicationInfoAsUser(final Context context, final String packageName, final int flag, final int userId) {
        final Class[] array2;
        final Class[] array = array2 = new Class[3];
        array[0] = String.class;
        array[2] = (array[1] = Integer.TYPE);
        final Method declaredMethodSafely = ReflectUtil.getDeclaredMethodSafely((Class)PackageManager.class, "getApplicationInfoAsUser", array2);
        final PackageManager packageManager = context.getPackageManager();
        final Object[] array3 = new Object[3];
        final Object[] array4;
        (array4 = array3)[0] = packageName;
        array4[1] = flag;
        array3[2] = userId;
        final Object invokeMethodSafely;
        if ((invokeMethodSafely = ReflectUtil.invokeMethodSafely(declaredMethodSafely, (Object)packageManager, array3)) instanceof ApplicationInfo) {
            return (ApplicationInfo)invokeMethodSafely;
        }
        Debug.w("PackageUtils", "getApplicationInfoAsUser for " + packageName + " failed.", new Object[0]);
        return null;
    }
    
    public static Date getApkFileCreatedTime(final Context context, final String packageName) {
        final ApplicationInfo applicationInfo;
        if ((applicationInfo = getApplicationInfo(context, packageName)) == null) {
            return null;
        }
        Date date = null;
        final File file;
        final long lastModified;
        if ((file = new File(applicationInfo.publicSourceDir)).exists() && (lastModified = file.lastModified()) > 0L) {
            date = new Date(lastModified);
        }
        return date;
    }
    
    @Nullable
    public static File getApkFile(final Context context, final String packageName) {
        try {
            final ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 0);
            return new File(applicationInfo.publicSourceDir);
        } catch (PackageManager.NameNotFoundException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    @Nullable
    public static List<String> getSplitApkFiles(final Context context, final String packageName) {
        try {
            return Arrays.asList(context.getPackageManager().getApplicationInfo(packageName, 0).splitPublicSourceDirs);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static int getApkFileSize(final Context context, final String packageName) {
        final File apkFile;
        if ((apkFile = getApkFile(context, packageName)) != null && apkFile.exists()) {
            return (int)apkFile.length();
        }
        return 0;
    }
    
    public static PackageInfo getApkInfo(final Context context, final String path) {
        if (!StringUtils.isNullOrEmpty(path) && new File(path).exists() && path.endsWith(".apk")) {
            return context.getPackageManager().getPackageArchiveInfo(path, 1);
        }
        return null;
    }
    
    public static boolean isPackageExist(final Context context, final String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, 1);
            return true;
        }
        catch (final PackageManager.NameNotFoundException ex) {
            return false;
        }
    }
    
    public static boolean checkComponentName(final Context context, final ComponentName componentName) {
        try {
            return context.getPackageManager().getActivityInfo(componentName, 0) != null;
        }
        catch (final PackageManager.NameNotFoundException ex) {
            return false;
        }
    }
    
    public static ComponentName getUsableDefaultInstallerComponentName(final Context context) {
        final ComponentName d;
        if ((d = PackageUtils.d) != null) {
            return d;
        }
        final ComponentName[] c;
        if ((c = PackageUtils.c) != null) {
            for (int length = c.length, i = 0; i < length; ++i) {
                final ComponentName d2;
                if (checkComponentName(context, d2 = c[i])) {
                    PackageUtils.d = d2;
                }
            }
        }
        return PackageUtils.d;
    }
    
    public static String getPermissionControllerName(final Context context) {
        final ComponentName usableDefaultInstallerComponentName;
        if ((usableDefaultInstallerComponentName = getUsableDefaultInstallerComponentName(context)) != null) {
            return usableDefaultInstallerComponentName.getPackageName();
        }
        return "";
    }
    
    public static String getAppNameByPkgName(final Context context, final String packageName) {
        final ApplicationInfo applicationInfo;
        return ((applicationInfo = getApplicationInfo(context, packageName)) == null) ? null : ((String)context.getPackageManager().getApplicationLabel(applicationInfo));
    }
    
    public static String getPackageName(final Context context) {
        if (context == null) {
            Debug.e("null context for getPackageName");
            return "";
        }
        return context.getPackageName();
    }
    
    public static List<PackageInfo> getInstalledPackages(final Context context) {
        try {
            return context.getApplicationContext().getPackageManager().getInstalledPackages(0);
        }
        catch (final Exception ex) {
            Debug.e((Class)PackageUtils.class, (Throwable)ex);
            return sneakyThrow(ex);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T, E extends Throwable> T sneakyThrow(Throwable failure) throws E {
        throw (E) failure;
    }
    
    public static Set<String> getInstalledPackageName(final Context context) {
        return toPackageNameList(getInstalledPackages(context));
    }
    
    public static Set<String> toPackageNameList(final List<PackageInfo> packageInfoList) {
        final HashSet set = new HashSet();
        final Iterator<PackageInfo> iterator = packageInfoList.iterator();
        while (iterator.hasNext()) {
            set.add(iterator.next().packageName);
        }
        return set;
    }
    
    public static Intent getLaunchIntentForPackage(final Context context, final String packageName) {
        return context.getPackageManager().getLaunchIntentForPackage(packageName);
    }
    
    public static Intent getHomeLaunchIntent() {
        return new Intent("android.intent.action.MAIN").addCategory("android.intent.category.HOME").addCategory("android.intent.category.DEFAULT");
    }
    
    @Nullable
    public static PackageItemInfo getGroupInfo(@NonNull final Context context, @NonNull final String groupName) {
        try {
            return (PackageItemInfo)context.getPackageManager().getPermissionGroupInfo(groupName, 0);
        }
        catch (final PackageManager.NameNotFoundException ex) {
            try {
                return (PackageItemInfo)context.getPackageManager().getPermissionInfo(groupName, 0);
            }
            catch (final PackageManager.NameNotFoundException ex2) {
                return null;
            }
        }
    }
    
    @Nullable
    public static Drawable loadPermGroupIcon(@NonNull final Context context, @NonNull final String groupName) {
        final PackageItemInfo groupInfo;
        final PackageItemInfo packageItemInfo = groupInfo = getGroupInfo(context, groupName);
        Drawable loadDrawable = null;
        if (packageItemInfo != null && groupInfo.icon != 0) {
            final PackageManager packageManager = context.getPackageManager();
            final PackageItemInfo packageItemInfo2 = groupInfo;
            loadDrawable = loadDrawable(packageManager, packageItemInfo2.packageName, packageItemInfo2.icon);
        }
        return loadDrawable;
    }
    
    @Nullable
    public static Drawable loadDrawable(final PackageManager pm, final String pkg, final int resId) {
        try {
            return pm.getResourcesForApplication(pkg).getDrawable(resId, (Resources.Theme)null);
        }
        catch (final PackageManager.NameNotFoundException | Resources.NotFoundException failure) {
            Log.d("PackageUtils", "Couldn't get resource", failure);
        }
        return null;
    }
    
    public static Uri createPackageUri(final String packageName) {
        return Uri.fromParts("package", packageName, (String)null);
    }
    
    @Nullable
    public static PackageInfo getPackageInfo(final Context context, final String packageName) {
        return getPackageInfo(context.getPackageManager(), packageName);
    }
    
    @Nullable
    public static PackageInfo getPackageInfo(final PackageManager pkgManager, final String packageName) {
        try {
            return pkgManager.getPackageInfo(packageName, 0);
        }
        catch (final PackageManager.NameNotFoundException ex) {
            Debug.i((Class)ex.getClass(), "getPackageInfo:" + packageName, new Object[0]);
            return null;
        }
    }
    
    public static ActivityInfo getActivityInfo(final Context context, final ComponentName componentName) {
        try {
            return context.getPackageManager().getActivityInfo(componentName, 0);
        }
        catch (final PackageManager.NameNotFoundException ex) {
            Debug.i((Class)ex.getClass(), "getActivityInfo:" + componentName, new Object[0]);
            return null;
        }
    }
    
    static {
        c = new ComponentName[] { new ComponentName("com.android.packageinstaller", "com.android.packageinstaller.PackageInstallerActivity"), new ComponentName("com.google.android.packageinstaller", "com.android.packageinstaller.PackageInstallerActivity") };
        PackageUtils.d = null;
    }
}
