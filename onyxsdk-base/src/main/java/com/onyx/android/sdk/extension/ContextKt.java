// 
// 

package com.onyx.android.sdk.extension;

import android.content.pm.ApplicationInfo;
import kotlin.jvm.functions.Function0;
import com.onyx.android.sdk.base.utils.Debug;
import android.content.Intent;
import android.net.Uri;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import android.content.Context;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 2, xi = 48, d1 = { "\u0000*\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a\"\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005\u001a\u0012\u0010\u0006\u001a\u00020\u0007*\u00020\u00022\u0006\u0010\b\u001a\u00020\u0001\u001a(\u0010\t\u001a\u00020\n*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u00012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00012\b\b\u0002\u0010\f\u001a\u00020\u0007\u001a\u0012\u0010\r\u001a\u00020\u0007*\u00020\u00022\u0006\u0010\u000e\u001a\u00020\u000f¨\u0006\u0010" }, d2 = { "getMetaData", "", "Landroid/content/Context;", "key", "defaultValue", "Lkotlin/Function0;", "isPackageInstalled", "", "packageName", "openWithLocalApplication", "", "uri", "safeCheck", "startActivitySafely", "intent", "Landroid/content/Intent;", "onyxsdk-base_release" })
public final class ContextKt
{
    private ContextKt() {
    }

    public static final void openWithLocalApplication(@NotNull final Context $this$openWithLocalApplication, @NotNull final String uri, @Nullable final String packageName, final boolean safeCheck) {
        Intrinsics.checkNotNullParameter((Object)$this$openWithLocalApplication, "<this>");
        Intrinsics.checkNotNullParameter((Object)uri, "uri");
        final Intent setFlags;
        Intrinsics.checkNotNullExpressionValue((Object)(setFlags = new Intent("android.intent.action.VIEW", Uri.parse(uri)).setPackage(packageName).setFlags(268435456)), "Intent(ACTION_VIEW, Uri.\u2026s(FLAG_ACTIVITY_NEW_TASK)");
        if (!safeCheck) {
            $this$openWithLocalApplication.startActivity(setFlags);
            return;
        }
        if (!startActivitySafely($this$openWithLocalApplication, setFlags)) {
            Debug.INSTANCE.e((Class)$this$openWithLocalApplication.getClass(), Intrinsics.stringPlus("unsupported uri: ", (Object)uri), new Object[0]);
        }
    }

    public static /* synthetic */ void openWithLocalApplication$default(final Context context, final String uri, String packageName, boolean safeCheck, final int n, final Object o) {
        if ((n & 0x2) != 0x0) {
            packageName = null;
        }
        if ((n & 0x4) != 0x0) {
            safeCheck = true;
        }
        openWithLocalApplication(context, uri, packageName, safeCheck);
    }

    public static final boolean startActivitySafely(@NotNull final Context $this$startActivitySafely, @NotNull final Intent intent) {
        Intrinsics.checkNotNullParameter((Object)$this$startActivitySafely, "<this>");
        Intrinsics.checkNotNullParameter((Object)intent, "intent");
        boolean b;
        try {
            $this$startActivitySafely.startActivity(intent);
            b = true;
        }
        catch (final Exception ex) {
            Debug.INSTANCE.e((Class)$this$startActivitySafely.getClass(), Intrinsics.stringPlus("startActivitySafely fail:", (Object)intent), (Throwable)ex);
            b = false;
        }
        return b;
    }
    
    public static final boolean isPackageInstalled(@NotNull final Context $this$isPackageInstalled, @NotNull final String packageName) {
        Intrinsics.checkNotNullParameter((Object)$this$isPackageInstalled, "<this>");
        Intrinsics.checkNotNullParameter((Object)packageName, "packageName");
        try {
            $this$isPackageInstalled.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        }
        finally {
            return false;
        }
    }
    
    @NotNull
    public static final String getMetaData(@NotNull final Context $this$getMetaData, @NotNull final String key, @NotNull final Function0<String> defaultValue) {
        Intrinsics.checkNotNullParameter((Object)$this$getMetaData, "<this>");
        Intrinsics.checkNotNullParameter((Object)key, "key");
        Intrinsics.checkNotNullParameter((Object)defaultValue, "defaultValue");
        try {
            final ApplicationInfo applicationInfo = $this$getMetaData.getPackageManager()
                    .getApplicationInfo($this$getMetaData.getPackageName(), 128);
            String value = applicationInfo.metaData == null ? null
                    : applicationInfo.metaData.getString(key);
            return value == null ? defaultValue.invoke() : value;
        } catch (android.content.pm.PackageManager.NameNotFoundException exception) {
            return defaultValue.invoke();
        }
    }

    public static /* synthetic */ String getMetaData$default(final Context context, final String key, Function0 defaultValue, final int n, final Object o) {
        if ((n & 0x2) != 0x0) {
            defaultValue = () -> "";
        }
        return getMetaData(context, key, defaultValue);
    }
}
