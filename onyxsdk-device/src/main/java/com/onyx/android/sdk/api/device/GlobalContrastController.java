package com.onyx.android.sdk.api.device;

import android.util.Log;
import com.onyx.android.sdk.device.Device;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.SystemPropertiesUtil;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/api/device/GlobalContrastController.class */
public class GlobalContrastController {
    private static final String TAG = "GlobalContrastController";

    public static int getMaxContrastLevel() {
        return 100;
    }

    public static int getGlobalContrast() {
        return Device.currentDevice().getGlobalContrast();
    }

    public static boolean setGlobalContrast(int contrast) {
        if (contrast < 0 || contrast > getMaxContrastLevel()) {
            Log.e(TAG, "contrast: " + contrast + ", contrast value invalid, it must be in [0 - " + getMaxContrastLevel() + "]");
            return false;
        }
        Device.currentDevice().setGlobalContrast(contrast);
        return true;
    }

    public static boolean isHighContrastEnabled() {
        if (isSupportHighContrast()) {
            return Device.currentDevice().getDitherThreshold() == 255;
        }
        Debug.w("This device does not support high contrast functionality.");
        return false;
    }

    public static void setHighContrastEnabled(boolean enable) {
        if (isSupportHighContrast()) {
            Device.currentDevice().setDitherThreshold(enable ? 255 : 128);
        } else {
            Debug.w("This device does not support high contrast functionality.");
        }
    }

    private static boolean isSupportHighContrast() {
        return SystemPropertiesUtil.isTablet() || SystemPropertiesUtil.isPhone();
    }
}
