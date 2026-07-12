package com.onyx.android.sdk.api.device.screensaver;

import android.content.Context;
import android.util.Log;
import com.onyx.android.sdk.api.utils.CompatibilityUtil;
import com.onyx.android.sdk.api.utils.StringUtils;
import com.onyx.android.sdk.utils.SystemPropertiesUtil;
import java.io.File;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/api/device/screensaver/ScreenResourceManager.class */
public class ScreenResourceManager {
    public static final String TAG = "ScreenResourceManager";

    public static boolean setScreensaver(Context context, String filePath, boolean showHint) {
        if (!checkFileValid(filePath)) {
            return false;
        }
        if (!CompatibilityUtil.isApiLevelSatisfied(28)) {
            return ScreenSaverUtils.setScreensaverBelowAndroidP(context, filePath);
        }
        ScreenSaverUtils.setScreenResource(context, filePath, 16, showHint);
        return true;
    }

    public static boolean setShutdown(Context context, String filePath, boolean showHint) {
        if (!supportShutdownSetting()) {
            Log.d(TAG, "This device does not support set up shutdown image.");
            return false;
        }
        if (!checkFileValid(filePath)) {
            return false;
        }
        ScreenSaverUtils.setScreenResource(context, filePath, 17, showHint);
        return true;
    }

    public static boolean setWallpaper(Context context, String filePath, boolean showHint) {
        if (!supportWallpaperSetting()) {
            Log.d(TAG, "This device does not support set up wallpaper image.");
            return false;
        }
        if (!checkFileValid(filePath)) {
            return false;
        }
        ScreenSaverUtils.setScreenResource(context, filePath, 1, showHint);
        return true;
    }

    public static boolean supportWallpaperSetting() {
        return CompatibilityUtil.isApiLevelSatisfied(28) && SystemPropertiesUtil.isPhone();
    }

    public static boolean supportShutdownSetting() {
        return CompatibilityUtil.isApiLevelSatisfied(28);
    }

    public static boolean checkFileValid(String filePath) {
        if (StringUtils.isNullOrEmpty(filePath)) {
            Log.e(TAG, "Input path is empty!");
            return false;
        }
        File file = new File(filePath);
        if (!b.a(filePath) || b.a(file) <= 0) {
            Log.e(TAG, "Input file is invalid!");
            return false;
        }
        List<String> list = ScreenSaverUtils.SUPPORT_IMAGE_TYPES;
        if (!list.contains(b.b(filePath))) {
            Log.e(TAG, "Input file type is invalid, we currently support formats include:" + StringUtils.join(list, ", "));
            return false;
        }
        if (b.a(file) <= ScreenSaverUtils.DREAM_IMAGE_SIZE_LIMIT_IN_BYTES) {
            return true;
        }
        Log.e(TAG, "Input file too large, Please ensure image less than 10M!");
        return false;
    }
}
