package com.onyx.android.sdk.api.device.screensaver;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import androidx.annotation.NonNull;
import com.onyx.android.sdk.api.utils.StringUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/api/device/screensaver/ScreenSaverUtils.class */
public class ScreenSaverUtils {
    private static String TAG = "ScreenSaverUtils";
    public static final int TYPE_WALLPAPER = 1;
    public static final int TYPE_SCREENSAVER = 16;
    public static final int TYPE_SHUTDOWN_IMAGE = 17;
    public static final String ONYX_SCREENSAVER_ACTION = "onyx.action.SCREENSAVER";
    public static final String TYPE_TAG = "type";
    public static final String FILE_TAG = "file";
    public static final String SHOW_RESULT_HINT_TAG = "show_result_hint";
    public static final long DREAM_IMAGE_SIZE_LIMIT_IN_BYTES = 10485760;
    public static final List<String> SUPPORT_IMAGE_TYPES = new ArrayList(Arrays.asList("png", "jpg", "jpeg", "bmp"));

    @Deprecated
    public static final String UPDATE_STANDBY_PIC_ACTION = "update_standby_pic";

    @Deprecated
    public static final String STAND_BY_SAVED_PATH = "/data/local/assets/images";

    @Deprecated
    public static final String STANDBY_FILE_NAME = "standby-1.png";

    public static void setScreenResource(@NonNull Context context, String filePath, int type, boolean showHint) {
        Intent intent = new Intent(ONYX_SCREENSAVER_ACTION);
        intent.putExtra(TYPE_TAG, type);
        intent.putExtra(FILE_TAG, filePath);
        intent.putExtra(SHOW_RESULT_HINT_TAG, showHint);
        context.sendBroadcast(intent);
    }

    @Deprecated
    static boolean setScreensaverBelowAndroidP(Context context, String filePath) {
        boolean zB;
        Bitmap bitmapProcessStandbyImage = processStandbyImage(context, filePath);
        String str = STAND_BY_SAVED_PATH + File.separator + STANDBY_FILE_NAME;
        if (b.e(filePath)) {
            zB = a.a(bitmapProcessStandbyImage, STAND_BY_SAVED_PATH, str, true);
        } else {
            if (!b.g(filePath) && !b.f(filePath)) {
                Log.e(TAG, "Input file type is invalid, we currently support formats include:" + StringUtils.join(SUPPORT_IMAGE_TYPES, ", "));
                return false;
            }
            zB = a.b(bitmapProcessStandbyImage, STAND_BY_SAVED_PATH, str, true);
        }
        if (zB) {
            context.sendBroadcast(new Intent(UPDATE_STANDBY_PIC_ACTION));
        }
        boolean z = zB;
        Log.i(TAG, "set screenSaver success: " + zB);
        return z;
    }

    private static Bitmap processStandbyImage(Context context, String filePath) {
        Bitmap bitmapDecodeFile = BitmapFactory.decodeFile(filePath);
        Bitmap bitmapCreateScaledBitmap = bitmapDecodeFile;
        if (bitmapDecodeFile.getHeight() > bitmapCreateScaledBitmap.getWidth()) {
            bitmapCreateScaledBitmap = a.b(bitmapCreateScaledBitmap, 270);
        }
        int[] screenSize = ScreenUtils.getScreenSize(context);
        int i = screenSize[1];
        int i2 = screenSize[0];
        if (bitmapCreateScaledBitmap.getWidth() != i || bitmapCreateScaledBitmap.getHeight() != i2) {
            bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmapCreateScaledBitmap, i, i2, true);
        }
        return bitmapCreateScaledBitmap;
    }
}
