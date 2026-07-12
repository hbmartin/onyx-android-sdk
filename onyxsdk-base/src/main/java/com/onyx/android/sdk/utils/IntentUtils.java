package com.onyx.android.sdk.utils;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.onyx.android.sdk.data.CommonConstant;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/IntentUtils.class */
public class IntentUtils {
    public static final String MUSIC_PLAYER_ACTION = "android.intent.action.MUSIC_PLAYER";
    public static final String CATEGORY_LAUNCHER_APP = "android.intent.category.LAUNCHER_APP";
    public static final int INTENT_FILTER_DOMAIN_VERIFICATION_STATUS_ASK = 1;
    public static final int INTENT_FILTER_DOMAIN_VERIFICATION_STATUS_ALWAYS = 2;
    public static final int INTENT_FILTER_DOMAIN_VERIFICATION_STATUS_NEVER = 3;

    public static Intent createIntent(String packageName, String activityClassName) {
        Intent intent = new Intent();
        if (!activityClassName.contains(packageName)) {
            activityClassName = packageName + activityClassName;
        }
        intent.setComponent(new ComponentName(packageName, activityClassName));
        return intent;
    }

    public static String getRemovedPackageName(Intent intent) {
        return getPackageFromIntent(intent);
    }

    public static String getPackageFromIntent(Intent intent) {
        String schemeSpecificPart = null;
        if (intent.getData() != null) {
            schemeSpecificPart = intent.getData().getSchemeSpecificPart();
        }
        return schemeSpecificPart;
    }

    public static List<Uri> getExtraStreamUris(Intent intent) {
        ArrayList parcelableArrayListExtra = intent.getParcelableArrayListExtra("android.intent.extra.STREAM");
        if (CollectionUtils.isNonBlank(parcelableArrayListExtra)) {
            return parcelableArrayListExtra;
        }
        ArrayList arrayList = new ArrayList();
        CollectionUtils.safeAdd(arrayList, intent.getParcelableExtra("android.intent.extra.STREAM"));
        return arrayList;
    }

    public static List<String> getExtraTextStringList(Intent intent) {
        return getExtraStringList(intent, "android.intent.extra.TEXT");
    }

    public static List<String> getExtraHtmlTextStringList(Intent intent) {
        return getExtraStringList(intent, "android.intent.extra.HTML_TEXT");
    }

    public static List<String> getExtraStringList(Intent intent, String key) {
        ArrayList<String> stringArrayListExtra = intent.getStringArrayListExtra(key);
        if (CollectionUtils.isNonBlank(stringArrayListExtra)) {
            return stringArrayListExtra;
        }
        ArrayList arrayList = new ArrayList();
        CollectionUtils.safeAdd(arrayList, intent.getStringExtra(key));
        return arrayList;
    }

    public static String getStringExtra(Intent intent, String key, String defaultValue) {
        String stringExtra = intent.getStringExtra(key);
        String str = stringExtra;
        if (stringExtra == null) {
            str = defaultValue;
        }
        return str;
    }

    public static boolean getBooleanExtra(Bundle bundle, String key, boolean defaultValue) {
        return bundle == null ? defaultValue : bundle.getBoolean(key, defaultValue);
    }

    public static int getIntExtra(Bundle bundle, String key, int defaultValue) {
        return bundle == null ? defaultValue : bundle.getInt(key, defaultValue);
    }

    public static Object getExtra(@Nullable Bundle bundle, String key, Object defaultValue) {
        Object obj = null;
        if (bundle != null) {
            obj = bundle.get(key);
        }
        if (obj == null) {
            obj = defaultValue;
        }
        return obj;
    }

    public static String getMTPPathFromIntent(Intent intent) {
        String path;
        if (CompatibilityUtil.apiLevelCheck(24)) {
            path = intent.getStringExtra(CommonConstant.MTP_EXTRA_TAG_FILE_PATH);
        } else {
            path = intent.getData() == null ? null : intent.getData().getPath();
        }
        return path;
    }

    @Nullable
    public static String getIdentifier(Intent intent) {
        if (CompatibilityUtil.apiLevelCheck(29)) {
            return intent.getIdentifier();
        }
        return null;
    }

    public static Intent setIdentifier(Intent intent, String identifier) {
        if (CompatibilityUtil.apiLevelCheck(29)) {
            intent.setIdentifier(identifier);
        }
        return intent;
    }

    public static boolean isHomeIntent(Intent intent) {
        return intent != null && StringUtils.safelyEquals("android.intent.action.MAIN", intent.getAction()) && CollectionUtils.safelyContains(intent.getCategories(), "android.intent.category.HOME");
    }

    public static boolean isMainLauncherIntent(Intent intent) {
        return intent != null && StringUtils.safelyEquals("android.intent.action.MAIN", intent.getAction()) && CollectionUtils.safelyContains(intent.getCategories(), "android.intent.category.LAUNCHER");
    }

    public static Intent getBrowserIntent() {
        return new Intent("android.intent.action.VIEW").setData(Uri.parse(CommonConstant.SCHEME_HTTP)).addCategory("android.intent.category.BROWSABLE").addCategory("android.intent.category.DEFAULT");
    }

    public static Intent getImageGalleryIntent() {
        return new Intent("android.intent.action.VIEW").setType("image/*");
    }

    public static Intent getAudioIntent() {
        Intent intentIntentFromMimeType = ViewDocumentUtils.intentFromMimeType(new File(ResManager.getAppContext().getCacheDir(), FileUtils.combine("dummy", "mp3")));
        intentIntentFromMimeType.setDataAndType(intentIntentFromMimeType.getData(), "audio/*");
        return intentIntentFromMimeType;
    }

    public static Intent getVideoIntent() {
        Intent intentIntentFromMimeType = ViewDocumentUtils.intentFromMimeType(new File(ResManager.getAppContext().getCacheDir(), FileUtils.combine("dummy", "mp4")));
        intentIntentFromMimeType.setDataAndType(intentIntentFromMimeType.getData(), "video/*");
        return intentIntentFromMimeType;
    }

    public static Intent getTakePhotoIntent(Uri uri) {
        return new Intent("android.media.action.IMAGE_CAPTURE").putExtra("output", uri).addFlags(3);
    }

    public static String getStringExtra(Bundle bundle, String key, String defaultValue) {
        return bundle == null ? defaultValue : bundle.getString(key, defaultValue);
    }
}
