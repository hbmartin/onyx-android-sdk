// 
// 

package com.onyx.android.sdk.utils;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import android.media.RingtoneManager;
import android.os.Process;
import com.onyx.android.sdk.common.provider.OnyxFileProviderUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.io.File;
import android.view.ContextThemeWrapper;
import androidx.annotation.NonNull;
import android.preference.PreferenceActivity;
import android.view.ViewParent;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.FrameLayout;
import android.app.Dialog;
import android.view.View;
import android.preference.PreferenceScreen;
import androidx.annotation.Nullable;
import java.util.Iterator;
import java.util.Collection;
import java.util.Set;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.util.List;
import android.app.ActivityManager;
import android.os.Parcelable;
import android.os.Build;
import android.net.Uri;
import android.text.TextUtils;
import com.onyx.android.sdk.data.AppDataInfo;
import android.content.ActivityNotFoundException;
import android.content.pm.ActivityInfo;
import android.app.Activity;
import android.content.ComponentName;
import android.util.Log;
import android.content.Intent;
import android.content.Context;

public class ActivityUtil
{
    private static final String a = "ActivityUtil";
    public static final String ONYX_MAIL_PACKAGE_NAME = "com.onyx.mail";
    public static final String MAIL_TO_SCHEME = "mailto:";
    public static final String MAIL_DELETE_ATTACHMENT = "delete_attachment";
    public static final String MAIL_PUSH_READ_ACTIVITY = "com.onyx.mail.subscription.ui.SubscriptionActivity";
    public static final String BOOX_DROP_PACKAGE_NAME = "com.onyx.easytransfer";
    public static final String BOOX_DROP_CLASS_NAME = "com.onyx.easytransfer.main.MainActivity";
    public static final String BOOX_WECHAT_DROP_CLASS_NAME = "com.onyx.easytransfer.wechat.ui.WeChatDropActivity";
    public static final int REQUEST_CODE_CONFIG_APPWIDGET = 4096;
    
    public static boolean startActivitySafely(final Context from, final Class<?> activityCls) {
        return startActivitySafely(from, new Intent(from, (Class)activityCls));
    }
    
    public static boolean startActivitySafely(final Context from, final String packageName) {
        return startActivitySafely(from, packageName, false);
    }
    
    public static boolean startActivitySafely(final Context from, final String packageName, final boolean isNewTask) {
        final Intent launchIntentForPackage;
        if ((launchIntentForPackage = from.getPackageManager().getLaunchIntentForPackage(packageName)) != null && isNewTask) {
            launchIntentForPackage.addFlags(268435456);
        }
        return startActivitySafely(from, launchIntentForPackage);
    }
    
    public static boolean startActivitySafely(final Context from, final String packageName, final String activityClassName) {
        return startActivitySafely(from, packageName, activityClassName, false);
    }
    
    public static boolean startActivitySafely(final Context from, final String packageName, final String activityClassName, final boolean isNewTask) {
        if (isNewTask) {
            return startActivityInNewTaskSafely(from, packageName, activityClassName);
        }
        return startActivitySafely(from, createIntent(packageName, activityClassName));
    }
    
    public static boolean startActivityInNewTaskSafely(final Context from, final String packageName, final String activityClassName) {
        return startActivitySafely(from, createIntent(packageName, activityClassName), true);
    }
    
    public static boolean startActivitySafely(final Context from, final Intent intent) {
        return startActivitySafely(from, intent, false);
    }
    
    public static boolean startActivitySafely(final Context from, final Intent intent, final boolean isNewTask) {
        try {
            if (isNewTask) intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            from.startActivity(intent);
            return true;
        } catch (Throwable failure) {
            Log.e(ActivityUtil.a, "", failure);
            return false;
        }
    }
    
    public static boolean startActivitySafelyFromLauncher(final Context from, final String packageName) {
        final Intent launchIntentForPackage = from.getPackageManager().getLaunchIntentForPackage(packageName);
        launchIntentForPackage.setPackage((String)null);
        return startActivitySafely(from, launchIntentForPackage, true, true);
    }
    
    public static boolean startActivitySafely(final Context from, final Intent intent, final boolean isNewTask, final boolean isResetTask) {
        if (isResetTask) {
            intent.addFlags(2097152);
        }
        return startActivitySafely(from, intent, isNewTask);
    }
    
    public static boolean startActivitySafely(final Context from, final Intent intent, final ComponentName componentName) {
        if (componentName != null) {
            intent.setPackage(componentName.getPackageName());
            intent.setClassName(componentName.getPackageName(), componentName.getClassName());
        }
        return startActivitySafely(from, intent);
    }
    
    public static boolean startWidgetConfigActivity(final Activity from, final ComponentName componentName, final int widgetId) {
        final Intent addFlags = new Intent("android.appwidget.action.APPWIDGET_CONFIGURE").putExtra("appWidgetId", widgetId).addFlags(268435456);
        if (componentName != null) {
            addFlags.setComponent(componentName);
        }
        return startActivityForResultSafely(from, addFlags, 4096);
    }
    
    public static Intent createIntent(final String packageName, String activityClassName) {
        final Intent intent = new Intent();
        if ((activityClassName.contains(packageName) ^ true) && activityClassName.startsWith(".")) {
            activityClassName = packageName + activityClassName;
        }
        final Intent intent2 = intent;
        intent2.setComponent(new ComponentName(packageName, activityClassName));
        return intent2;
    }
    
    public static boolean startActivityForResultSafely(final Activity from, final Intent intent, final int requestCode) {
        try {
            from.startActivityForResult(intent, requestCode);
            return true;
        }
        catch (final Exception ex) {
            Log.e(ActivityUtil.a, "", (Throwable)ex);
            return false;
        }
    }
    
    public static boolean startActivitySafely(final Context from, final Intent intent, final ActivityInfo appInfo) {
        appInfo.applicationInfo.loadLabel(from.getPackageManager());
        try {
            intent.setPackage(appInfo.packageName);
            try {
                final String packageName = appInfo.packageName;
                try {
                    intent.setClassName(packageName, appInfo.name);
                    try {
                        from.startActivity(intent);
                        return true;
                    }
                    catch (final SecurityException ex) {}
                    catch (final ActivityNotFoundException ex2) {}
                }
                catch (final SecurityException ex3) {}
                catch (final ActivityNotFoundException ex4) {}
            }
            catch (final SecurityException ex5) {}
            catch (final ActivityNotFoundException ex6) {}
        }
        catch (final SecurityException ex7) {}
        catch (final ActivityNotFoundException ex8) {}
        return false;
    }
    
    public static boolean startActivitySafely(final Context context, final AppDataInfo appDataInfo, final boolean isNewTask) {
        if (appDataInfo == null) {
            return false;
        }
        if (StringUtils.isNotBlank(appDataInfo.action)) {
            final Intent intent = new Intent(appDataInfo.action);
            if (StringUtils.isNotBlank(appDataInfo.packageName) && StringUtils.isNotBlank(appDataInfo.activityClassName)) {
                intent.setComponent(new ComponentName(appDataInfo.packageName, appDataInfo.activityClassName));
            }
            if (isNewTask) {
                intent.addFlags(268435456);
            }
            return startActivitySafely(context, intent);
        }
        if (StringUtils.isNullOrEmpty(appDataInfo.activityClassName)) {
            return startActivitySafely(context, appDataInfo.packageName, isNewTask);
        }
        return startActivitySafely(context, appDataInfo.packageName, appDataInfo.activityClassName, isNewTask);
    }
    
    public static boolean startActivityForResultSafely(final Activity from, final Intent intent, final ActivityInfo appInfo, final int requestCode) {
        appInfo.applicationInfo.loadLabel(from.getPackageManager());
        try {
            intent.setPackage(appInfo.packageName);
            try {
                final String packageName = appInfo.packageName;
                try {
                    intent.setClassName(packageName, appInfo.name);
                    try {
                        from.startActivityForResult(intent, requestCode);
                        return true;
                    }
                    catch (final SecurityException ex) {}
                    catch (final ActivityNotFoundException ex2) {}
                }
                catch (final SecurityException ex3) {}
                catch (final ActivityNotFoundException ex4) {}
            }
            catch (final SecurityException ex5) {}
            catch (final ActivityNotFoundException ex6) {}
        }
        catch (final SecurityException ex7) {}
        catch (final ActivityNotFoundException ex8) {}
        return false;
    }
    
    public static boolean isActivityForeground(final Context context, final String activityClassName) {
        if (context == null || TextUtils.isEmpty((CharSequence)activityClassName)) {
            return false;
        }
        final ComponentName a;
        if ((a = a(context)) == null) {
            Log.d(ActivityUtil.a, "Top component is null");
            return false;
        }
        final ComponentName componentName = a;
        Log.d(ActivityUtil.a, "activityClassName : " + activityClassName + " top activity : " + a.getClassName());
        return activityClassName.equals(componentName.getClassName());
    }
    
    public static boolean isActivityForegroundByShell(final Context context, final String activityClassName) {
        if (context == null || TextUtils.isEmpty((CharSequence)activityClassName)) {
            return false;
        }
        final ShellUtils.CommandResult execCommand;
        if ((execCommand = ShellUtils.execCommand("dumpsys window |grep mCurrentFocus", false)) != null && !StringUtils.isNullOrEmpty(execCommand.successMsg)) {
            final ShellUtils.CommandResult commandResult = execCommand;
            Log.d(ActivityUtil.a, "Current focus window : " + execCommand.successMsg);
            return commandResult.successMsg.contains(activityClassName);
        }
        Log.d(ActivityUtil.a, "Can't get current focus window.");
        return false;
    }
    
    public static boolean isPackageForeground(final Context context, final String packageName) {
        if (context == null || TextUtils.isEmpty((CharSequence)packageName)) {
            return false;
        }
        final ComponentName a;
        if ((a = a(context)) == null) {
            Log.d(ActivityUtil.a, "Top component is null");
            return false;
        }
        final ComponentName componentName = a;
        Log.d(ActivityUtil.a, "packageName : " + packageName + " top packageName : " + a.getPackageName());
        return packageName.equals(componentName.getPackageName());
    }
    
    public static Intent cropImageUri(final Uri orgUri, final Uri desUri, final int aspectX, final int aspectY, final int width, final int height) {
        final Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= 24) {
            intent.addFlags(1);
        }
        final Intent intent2 = intent;
        intent2.setDataAndType(orgUri, "image/*");
        intent2.putExtra("crop", "true");
        intent2.putExtra("aspectX", aspectX);
        intent2.putExtra("aspectY", aspectY);
        intent2.putExtra("outputX", width);
        intent2.putExtra("outputY", height);
        intent2.putExtra("scale", true);
        intent2.putExtra("output", (Parcelable)desUri);
        return intent2;
    }
    
    private static ComponentName a(final Context context) {
        final ActivityManager activityManager;
        if ((activityManager = (ActivityManager)context.getSystemService("activity")) == null) {
            Log.w(ActivityUtil.a, "fail to get the activity manager!");
            return null;
        }
        final List<ActivityManager.RunningTaskInfo> runningTasks;
        if ((runningTasks = activityManager.getRunningTasks(1)) == null || runningTasks.size() <= 0) {
            return null;
        }
        final ActivityManager.RunningTaskInfo runningTaskInfo;
        if ((runningTaskInfo = runningTasks.get(0)) == null) {
            Log.d(ActivityUtil.a, "Can't get RunningTaskInfo");
            return null;
        }
        return runningTaskInfo.topActivity;
    }
    
    public static Intent getLaunchIntentForPackage(final PackageManager pm, final PackageInfo packageInfo, final List<ResolveInfo> launchResolveInfoList) {
        Intent launchIntentForPackage;
        if ((launchIntentForPackage = pm.getLaunchIntentForPackage(packageInfo.packageName)) == null) {
            final ActivityInfo a;
            if ((a = a(packageInfo.packageName, launchResolveInfoList)) != null && a.exported && (a.isEnabled() || !packageInfo.applicationInfo.enabled)) {
                final ActivityInfo activityInfo = a;
                launchIntentForPackage = new Intent("android.intent.action.MAIN");
                final String packageName = activityInfo.packageName;
                String s;
                if (TextUtils.isEmpty((CharSequence)activityInfo.targetActivity)) {
                    s = a.name;
                }
                else {
                    s = a.targetActivity;
                }
                launchIntentForPackage.setComponent(new ComponentName(packageName, s));
            }
        }
        else {
            final Set categories;
            if (ApplicationUtil.isSystemApp(packageInfo) && (categories = launchIntentForPackage.getCategories()) != null && !categories.isEmpty()) {
                if (!categories.contains("android.intent.category.LAUNCHER")) {
                    launchIntentForPackage = null;
                }
                return launchIntentForPackage;
            }
        }
        return launchIntentForPackage;
    }
    
    public static boolean isPackageHasLauncher(final PackageManager pm, final PackageInfo packageInfo) {
        final Intent launchIntentForPackage;
        final Set categories;
        return (launchIntentForPackage = pm.getLaunchIntentForPackage(packageInfo.packageName)) != null && !CollectionUtils.isNullOrEmpty(categories = launchIntentForPackage.getCategories()) && categories.contains("android.intent.category.LAUNCHER");
    }
    
    @Nullable
    private static ActivityInfo a(final String packageName, final List<ResolveInfo> launchResolveInfoList) {
        if (CollectionUtils.isNullOrEmpty(launchResolveInfoList)) {
            return null;
        }
        final Iterator<ResolveInfo> iterator = launchResolveInfoList.iterator();
        while (iterator.hasNext()) {
            final ResolveInfo resolveInfo;
            if ((resolveInfo = iterator.next()).activityInfo.packageName.equalsIgnoreCase(packageName)) {
                return resolveInfo.activityInfo;
            }
        }
        return null;
    }
    
    @Nullable
    public static ActivityInfo getActivityInfoFromPackageName(final PackageManager pm, final String packageName) {
        try {
            final ActivityInfo[] activities;
            ActivityInfo activityInfo;
            if ((activities = pm.getPackageInfo(packageName, 1).activities) == null) {
                activityInfo = null;
            }
            else {
                activityInfo = activities[0];
            }
            return activityInfo;
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static void enableActionBarBackFunc(final PreferenceScreen preferenceScreen) {
        final Dialog dialog;
        if ((dialog = preferenceScreen.getDialog()) != null) {
            final Dialog dialog2 = dialog;
            dialog2.getActionBar().setHomeButtonEnabled(true);
            dialog2.getActionBar().setDisplayHomeAsUpEnabled(true);
            final View viewById;
            if ((viewById = dialog2.findViewById(16908332)) != null) {
                final View view = viewById;
                final View.OnClickListener onClickListener = (View.OnClickListener)new View.OnClickListener() {
                    public void onClick(final View v) {
                        dialog.dismiss();
                    }
                };
                final ViewParent parent;
                if ((parent = view.getParent()) instanceof FrameLayout) {
                    final ViewGroup viewGroup;
                    if ((viewGroup = (ViewGroup)parent.getParent()) instanceof LinearLayout) {
                        ((LinearLayout)viewGroup).setOnClickListener((View.OnClickListener)onClickListener);
                    }
                    else {
                        ((FrameLayout)parent).setOnClickListener((View.OnClickListener)onClickListener);
                    }
                }
                else {
                    viewById.setOnClickListener((View.OnClickListener)onClickListener);
                }
            }
        }
    }
    
    public static void enableActionBarBackFunc(final PreferenceActivity activity) {
        final View viewById;
        if ((viewById = activity.findViewById(16908332)) != null) {
            final View view = viewById;
            final View.OnClickListener onClickListener = (View.OnClickListener)new View.OnClickListener() {
                public void onClick(final View v) {
                    activity.onBackPressed();
                }
            };
            final ViewParent parent;
            if ((parent = view.getParent()) instanceof FrameLayout) {
                final ViewGroup viewGroup = (ViewGroup)parent.getParent();
                FrameLayout frameLayout;
                for (int i = 0; i < (frameLayout = (FrameLayout)parent).getChildCount(); ++i) {
                    frameLayout.getChildAt(i).setVisibility(0);
                }
                if (viewGroup instanceof LinearLayout) {
                    final ViewGroup viewGroup2 = viewGroup;
                    viewGroup2.setEnabled(true);
                    viewGroup2.setClickable(true);
                    ((LinearLayout)viewGroup2).setOnClickListener((View.OnClickListener)onClickListener);
                }
                else {
                    final FrameLayout frameLayout2 = frameLayout;
                    frameLayout2.setEnabled(true);
                    frameLayout2.setClickable(true);
                    frameLayout2.setOnClickListener((View.OnClickListener)onClickListener);
                }
            }
            else {
                final View view2 = viewById;
                view2.setClickable(true);
                view2.setEnabled(true);
                view2.setOnClickListener((View.OnClickListener)onClickListener);
            }
        }
    }
    
    public static Intent buildMainLaunchIntent() {
        final Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        return intent;
    }
    
    public static void finishAndRemoveTask(final Activity activity) {
        if (activity == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            activity.finishAndRemoveTask();
        }
        else {
            activity.finish();
        }
    }
    
    public static void removeTask(final Context context, final int taskId) {
        try {
            final ActivityManager obj;
            final Class<? extends ActivityManager> class1 = (obj = (ActivityManager)context.getSystemService("activity")).getClass();
            final String name = "removeTaskWithoutPermissionCheck";
            try {
                class1.getMethod(name, Integer.TYPE).invoke(obj, taskId);
            }
            catch (final Exception ex) {
                Log.e(ActivityUtil.a, "", (Throwable)ex);
            }
        }
        catch (final Exception ex2) {}
    }
    
    public static void finish(final Activity activity) {
        if (activity == null) {
            return;
        }
        activity.finish();
    }
    
    public static Activity getActivitySafety(@NonNull Context context) {
        if (isActivity(context)) {
            return (Activity)context;
        }
        if (context instanceof ContextThemeWrapper) {
            context = ((ContextThemeWrapper)context).getBaseContext();
        }
        if (context instanceof androidx.appcompat.view.ContextThemeWrapper) {
            context = ((androidx.appcompat.view.ContextThemeWrapper)context).getBaseContext();
        }
        return (Activity)context;
    }
    
    public static boolean isActivity(final Context context) {
        return context instanceof Activity;
    }
    
    public static boolean startEmailActivity(final Context context, final List<String> primaryMailToList, final String subject, final String contentText, final List<File> fileList, final boolean deleteAttachment) {
        final Intent intent2;
        final Intent intent = intent2 = new Intent("android.intent.action.SENDTO");
        intent.setPackage("com.onyx.mail");
        intent.setData(Uri.parse("mailto:"));
        final Intent intent3 = new android.content.Intent();
        final Intent intent5;
        final Intent intent4 = intent5 = intent3;
        final Intent selector = intent2;
        new Intent("android.intent.action.SEND_MULTIPLE");
        intent5.setSelector(selector);
        intent3.putExtra("android.intent.extra.EMAIL", (String[])primaryMailToList.toArray(new String[0]));
        intent3.putExtra("android.intent.extra.SUBJECT", subject);
        if (StringUtils.isNotBlank(contentText)) {
            final Intent intent6 = intent4;
            final ArrayList<Object> list;
            (list = new ArrayList<Object>()).add(contentText);
            intent6.putExtra("android.intent.extra.TEXT", (Serializable)list);
        }
        final Intent intent7 = intent4;
        final Intent intent8 = intent4;
        final Intent intent9 = intent4;
        intent9.addFlags(1);
        intent9.addFlags(2);
        intent8.putParcelableArrayListExtra("android.intent.extra.STREAM", (ArrayList)OnyxFileProviderUtil.getUriForFileList(context, fileList));
        intent8.putExtra("delete_attachment", deleteAttachment);
        return intent7.resolveActivity(context.getPackageManager()) != null && startActivitySafely(context, intent4);
    }
    
    public static void startOnyxResolverActivity(final Context activityContext, @Nullable final String title, final Intent detectIntent) {
        detectIntent.addCategory("android.intent.category.DEFAULT");
        final Intent intent = new Intent();
        if (StringUtils.isNotBlank(title)) {
            intent.putExtra("args_title", title);
        }
        final Intent intent2 = intent;
        intent2.setComponent(new ComponentName("com.android.systemui", "com.android.systemui.onyx.OnyxResolverActivity"));
        intent2.putExtra("android.intent.extra.INTENT", (Parcelable)detectIntent);
        intent2.setFlags(1073741824);
        startActivitySafely(activityContext, intent);
    }
    
    public static boolean isMainProcess(final Context appContext) {
        return StringUtils.safelyEquals(appContext.getPackageName(), getCurrentProcessName(appContext));
    }
    
    public static String getCurrentProcessName(final Context appContext) {
        String processName = "";
        final ActivityManager activityManager;
        if ((activityManager = (ActivityManager)appContext.getSystemService("activity")) != null && activityManager.getRunningAppProcesses() != null) {
            final ActivityManager activityManager2 = activityManager;
            final int myPid = Process.myPid();
            final Iterator iterator = activityManager2.getRunningAppProcesses().iterator();
            while (iterator.hasNext()) {
                final ActivityManager.RunningAppProcessInfo runningAppProcessInfo;
                if ((runningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)iterator.next()).pid == myPid) {
                    processName = runningAppProcessInfo.processName;
                }
            }
            return processName;
        }
        return processName;
    }
    
    public static void startPushReadActivity(final Context appContext) {
        startActivitySafely(appContext, IntentUtils.createIntent("com.onyx.mail", "com.onyx.mail.subscription.ui.SubscriptionActivity"), true);
    }
    
    public static void startWechatTransfer(final Context context) {
        startActivityInNewTaskSafely(context, "com.onyx.easytransfer", "com.onyx.easytransfer.wechat.ui.WeChatDropActivity");
    }
    
    public static void startBooxDrop(final Context context) {
        startActivityInNewTaskSafely(context, "com.onyx.easytransfer", "com.onyx.easytransfer.main.MainActivity");
    }
    
    public static void shareText(final Context context, final String text) {
        startActivitySafely(context, a(text));
    }
    
    public static void webSearchText(final Context context, final String text) {
        webSearchText(context, text, null);
    }
    
    public static void webSearchText(final Context context, final String text, @Nullable final String packageName) {
        final Intent intent2;
        final Intent intent = intent2 = new Intent();
        intent.setPackage(packageName);
        intent.setAction("android.intent.action.WEB_SEARCH");
        intent.putExtra("query", text);
        startActivitySafely(context, intent2);
    }
    
    private static Intent a(final String text) {
        final Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", text);
        return intent;
    }
    
    public static void shareUrl(final Context context, final String url) {
        final Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        final Intent chooser = Intent.createChooser(a(url), (CharSequence)null);
        final Intent[] array2;
        final Intent[] array = array2 = new Intent[] { null };
        array[0] = intent;
        chooser.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[])array2);
        startActivitySafely(context, chooser);
    }
    
    public static void openUrl(final Context context, final String url) {
        try {
            startActivitySafely(context, new Intent("android.intent.action.VIEW", Uri.parse(url)));
        }
        catch (final Exception ex) {
            Debug.e((Throwable)ex);
        }
    }
    
    public static boolean pickNotificationRingtone(final Activity activity, final String title, final int requestCode) {
        return pickRingtone(activity, title, 2, requestCode);
    }
    
    public static boolean pickAlarmRingtone(final Activity activity, final String title, final int requestCode) {
        return pickRingtone(activity, title, 4, requestCode);
    }
    
    public static boolean pickRingtone(final Activity activity, final String title, final int type, final int requestCode) {
        return startActivityForResultSafely(activity, getRingtonePickerIntent(title, type), requestCode);
    }
    
    public static Intent getRingtonePickerIntent(final String title, final int type) {
        return new Intent("android.intent.action.RINGTONE_PICKER").putExtra("android.intent.extra.ringtone.SHOW_SILENT", true).putExtra("android.intent.extra.ringtone.SHOW_DEFAULT", true).putExtra("android.intent.extra.ringtone.TYPE", type).putExtra("android.intent.extra.ringtone.TITLE", title).putExtra("android.intent.extra.ringtone.EXISTING_URI", (Parcelable)RingtoneManager.getActualDefaultRingtoneUri(ResManager.getAppContext(), type));
    }
    
    public static Intent getMailIntent() {
        return new Intent("android.intent.action.SENDTO").setData(Uri.parse("mailto:")).addCategory("android.intent.category.DEFAULT");
    }
    
    @Nullable
    public static FragmentManager getSupportFragmentManager(final Context context) {
        final Activity activitySafety;
        if ((activitySafety = getActivitySafety(context)) instanceof FragmentActivity) {
            return ((FragmentActivity)activitySafety).getSupportFragmentManager();
        }
        return null;
    }
}
