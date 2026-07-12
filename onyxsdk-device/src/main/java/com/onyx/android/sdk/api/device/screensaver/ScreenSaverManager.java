package com.onyx.android.sdk.api.device.screensaver;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/api/device/screensaver/ScreenSaverManager.class */
public class ScreenSaverManager {
    public static int SCREEN_SAVER_COUNT_LIMIT = 3;
    private static ScreenSaverManager globalManager;
    private ScreenSaverConfig globalConfig;

    private ScreenSaverManager() {
    }

    public static ScreenSaverManager init(ScreenSaverConfig config) {
        ScreenSaverManager screenSaverManager = new ScreenSaverManager();
        globalManager = screenSaverManager;
        screenSaverManager.globalConfig = config;
        return screenSaverManager;
    }

    public static ScreenSaverManager getInstance() {
        return globalManager;
    }

    public static ScreenSaverConfig getScreenSaverConfig() {
        return globalManager.globalConfig;
    }

    public static String getSourcePicPath(ScreenSaverConfig config, String targetFileName) {
        File file = new File(config.sourcePicPathString);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (file.isDirectory()) {
            return new File(config.sourcePicPathString, targetFileName).getAbsolutePath();
        }
        if (file.isFile()) {
            return TextUtils.isEmpty(targetFileName) ? config.sourcePicPathString : new File(b.d(config.sourcePicPathString), targetFileName).getAbsolutePath();
        }
        return config.sourcePicPathString;
    }

    public static void setAllScreenSaver(Context context, ScreenSaverConfig config) {
        for (int i = config.screenSaverInitialNumber; i < config.screenSaverInitialNumber + SCREEN_SAVER_COUNT_LIMIT; i++) {
            ScreenSaverConfig screenSaverConfigCopy = config.copy();
            screenSaverConfigCopy.targetPicPathString = screenSaverConfigCopy.createTargetPicPath(i);
            saveScreenFile(context, screenSaverConfigCopy);
        }
    }

    private static void saveScreenFile(Context context, ScreenSaverConfig config) {
        int i = config.fullScreenPhysicalHeight;
        int i2 = config.fullScreenPhysicalWidth;
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(config.sourcePicPathString);
        Bitmap bitmapCreateScaledBitmap = bitmapDecodeFile;
        if (bitmapDecodeFile.getHeight() > bitmapCreateScaledBitmap.getWidth()) {
            bitmapCreateScaledBitmap = a.b(bitmapCreateScaledBitmap, config.picRotateDegrees);
        }
        if (bitmapCreateScaledBitmap.getWidth() != i || bitmapCreateScaledBitmap.getHeight() != i2) {
            bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapCreateScaledBitmap, i, i2, true);
        }
        if (config.convertToGrayScale) {
            bitmapCreateScaledBitmap = a.b(a.a(bitmapCreateScaledBitmap, -1));
        }
        boolean zB = false;
        if (config.targetFormat.contains("bmp")) {
            zB = a.a(bitmapCreateScaledBitmap, config.targetDir, config.targetPicPathString, true);
        } else if (config.targetFormat.contains("png")) {
            zB = a.b(bitmapCreateScaledBitmap, config.targetDir, config.targetPicPathString, true);
        }
        if (zB) {
            Log.i("screenSaver", "success");
            context.sendBroadcast(new Intent(ScreenSaverUtils.UPDATE_STANDBY_PIC_ACTION));
        }
    }
}
