// 
// 

package com.onyx.android.sdk.utils;

import android.net.Uri;
import android.content.Intent;
import androidx.core.app.ActivityCompat;
import kotlin.jvm.internal.Intrinsics;
import android.os.Environment;
import android.os.Build;
import androidx.core.content.ContextCompat;
import android.app.Activity;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import android.content.Context;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J,\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\fJ$\u0010\u000e\u001a\u00020\b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\f2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\fH\u0002J,\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\b0\f2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\fH\u0002J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0004H\u0002J\u000e\u0010\u0013\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\nJ\b\u0010\u0014\u001a\u00020\u0011H\u0002J\u000e\u0010\u0015\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0010\u0010\u0016\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u0018H\u0002J)\u0010\u0019\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\u00182\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00040\u001b2\u0006\u0010\u001c\u001a\u00020\u0006¢\u0006\u0002\u0010\u001dJ\u0016\u0010\u001e\u001a\u00020\u00112\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T¢\u0006\u0002\n\u0000¨\u0006\u001f" }, d2 = { "Lcom/onyx/android/sdk/utils/PermissionUtils;", "", "()V", "PACKAGE_TAG", "", "STORAGE_PERMISSION_REQUEST_CODE", "", "checkAndRequestStoragePermission", "", "context", "Landroid/content/Context;", "onShowRationale", "Lkotlin/Function0;", "onPermissionGranted", "handleAndroidRStoragePermission", "handleLegacyStoragePermission", "hasPermission", "", "permission", "hasStoragePermission", "isExternalStorageManager", "openSystemStorageSettings", "requestLegacyStoragePermissions", "activity", "Landroid/app/Activity;", "requestPermissions", "permissions", "", "requestCode", "(Landroid/app/Activity;[Ljava/lang/String;I)V", "showPermissionRationale", "onyxsdk-base_release" })
public final class PermissionUtils
{
    @NotNull
    public static final PermissionUtils INSTANCE;
    @NotNull
    private static final String a = "package";
    public static final int STORAGE_PERMISSION_REQUEST_CODE = 1001;
    
    private PermissionUtils() {
    }
    
    private final void a(final Function0<Unit> onPermissionGranted, final Function0<Unit> onShowRationale) {
        if (this.a()) {
            onPermissionGranted.invoke();
        }
        else {
            onShowRationale.invoke();
        }
    }
    
    private final void a(final Context context, final Function0<Unit> onPermissionGranted, final Function0<Unit> onShowRationale) {
        if (this.hasStoragePermission(context)) {
            onPermissionGranted.invoke();
        }
        else if (this.showPermissionRationale(context, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            onShowRationale.invoke();
        }
        else if (context instanceof Activity) {
            this.a((Activity)context);
        }
        else {
            onShowRationale.invoke();
        }
    }
    
    private final void a(final Activity activity) {
        final String[] permissions;
        final String[] array = permissions = new String[2];
        array[0] = "android.permission.READ_EXTERNAL_STORAGE";
        array[1] = "android.permission.WRITE_EXTERNAL_STORAGE";
        this.requestPermissions(activity, permissions, 1001);
    }
    
    private final boolean a(final Context context, final String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == 0;
    }
    
    private final boolean a() {
        return Build.VERSION.SDK_INT > 30 && Environment.isExternalStorageManager();
    }
    
    static {
        INSTANCE = new PermissionUtils();
    }
    
    public final void checkAndRequestStoragePermission(@NotNull final Context context, @NotNull final Function0<Unit> onShowRationale, @NotNull final Function0<Unit> onPermissionGranted) {
        Intrinsics.checkNotNullParameter((Object)context, "context");
        Intrinsics.checkNotNullParameter((Object)onShowRationale, "onShowRationale");
        Intrinsics.checkNotNullParameter((Object)onPermissionGranted, "onPermissionGranted");
        if (Build.VERSION.SDK_INT > 30) {
            this.a(onPermissionGranted, onShowRationale);
        }
        else {
            this.a(context, onPermissionGranted, onShowRationale);
        }
    }

    public static /* synthetic */ void checkAndRequestStoragePermission$default(final PermissionUtils permissionUtils, final Context context, Function0 onShowRationale, final Function0 onPermissionGranted, final int i, final Object obj) {
        if ((i & 2) != 0) {
            onShowRationale = PermissionUtils$a.a;
        }
        permissionUtils.checkAndRequestStoragePermission(context, onShowRationale, onPermissionGranted);
    }
    
    public final boolean showPermissionRationale(@NotNull final Context context, @NotNull final String permission) {
        Intrinsics.checkNotNullParameter((Object)context, "context");
        Intrinsics.checkNotNullParameter((Object)permission, "permission");
        Activity activity;
        if (context instanceof Activity) {
            activity = (Activity)context;
        }
        else {
            activity = null;
        }
        return activity != null && ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }
    
    public final void requestPermissions(@NotNull final Activity activity, @NotNull final String[] permissions, final int requestCode) {
        Intrinsics.checkNotNullParameter((Object)activity, "activity");
        Intrinsics.checkNotNullParameter((Object)permissions, "permissions");
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }
    
    public final boolean hasStoragePermission(@NotNull final Context context) {
        Intrinsics.checkNotNullParameter((Object)context, "context");
        return this.a(context, "android.permission.READ_EXTERNAL_STORAGE") && this.a(context, "android.permission.WRITE_EXTERNAL_STORAGE");
    }
    
    public final void openSystemStorageSettings(@NotNull final Context context) {
        Intrinsics.checkNotNullParameter((Object)context, "context");
        Intent intent;
        if (Build.VERSION.SDK_INT > 30) {
            intent = new Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION").setData(Uri.parse(Intrinsics.stringPlus("package:", (Object)context.getPackageName())));
        }
        else {
            intent = new Intent("onyx.settings.action.app.management").putExtra("package", context.getPackageName());
        }
        final Intent intent2 = intent;
        Intrinsics.checkNotNullExpressionValue((Object)intent2, "if (Build.VERSION.SDK_IN\u2026xt.packageName)\n        }");
        intent2.setFlags(268435456);
        ActivityUtil.startActivitySafely(context, intent);
    }
}
