package com.onyx.android.sdk.api.device;

import android.content.Context;
import com.onyx.android.sdk.device.BaseDevice;
import com.onyx.android.sdk.device.Device;
import com.onyx.android.sdk.device.RK31XXDevice;
import com.onyx.android.sdk.device.RK32XXDevice;
import java.util.List;

public class FrontLightController {
    public static final int LIGHT_TYPE_NONE = 0;
    public static final int LIGHT_TYPE_FL = 1;
    public static final int LIGHT_TYPE_CTM_WARM = 2;
    public static final int LIGHT_TYPE_CTM_COLD = 3;
    public static final int LIGHT_TYPE_CTM_ALL = 4;

    public static int getBrightnessMinimum(Context context) {
        return Device.currentDevice().getFrontLightBrightnessMinimum(context);
    }

    public static int getBrightnessMaximum(Context context) {
        return Device.currentDevice().getFrontLightBrightnessMaximum(context);
    }

    public static boolean turnOn(Context context) {
        return Device.currentDevice().openFrontLight(context);
    }

    public static boolean turnOff(Context context) {
        return Device.currentDevice().closeFrontLight(context);
    }

    @Deprecated
    public static boolean isLightOn(Context context) {
        if ((Device.currentDevice() instanceof RK31XXDevice) || (Device.currentDevice() instanceof RK32XXDevice)) {
            return Device.currentDevice().isBrightnessOn(context);
        }
        BaseDevice baseDeviceCurrentDevice = Device.currentDevice();
        return baseDeviceCurrentDevice.getFrontLightDeviceValue(context) > baseDeviceCurrentDevice.getFrontLightBrightnessMinimum(context);
    }

    public static List<Integer> getFrontLightValueList(Context context) {
        if (hasFLBrightness(context)) {
            return Device.currentDevice().getFrontLightValueList(context);
        }
        return null;
    }

    public static int getMaxFrontLightValue(Context context) {
        if (!hasFLBrightness(context)) {
            return -1;
        }
        List<Integer> frontLightValueList = getFrontLightValueList(context);
        return frontLightValueList.get(frontLightValueList.size() - 1).intValue();
    }

    public static int getMinFrontLightValue(Context context) {
        if (hasFLBrightness(context)) {
            return getFrontLightValueList(context).get(0).intValue();
        }
        return -1;
    }

    public static int getBrightness(Context context) {
        if (hasFLBrightness(context)) {
            return Device.currentDevice().getFrontLightConfigValue(context);
        }
        return -1;
    }

    public static boolean setBrightness(Context context, int level) {
        if (!hasFLBrightness(context)) {
            return false;
        }
        BaseDevice baseDeviceCurrentDevice = Device.currentDevice();
        if (baseDeviceCurrentDevice.setFrontLightDeviceValue(context, level)) {
            return baseDeviceCurrentDevice.setFrontLightConfigValue(context, level);
        }
        return false;
    }

    public static boolean setNaturalBrightness(Context context, int level) {
        if (!hasCTMBrightness(context)) {
            return false;
        }
        BaseDevice baseDeviceCurrentDevice = Device.currentDevice();
        if (baseDeviceCurrentDevice.setNaturalLightConfigValue(context, level)) {
            return baseDeviceCurrentDevice.setNaturalLightConfigValue(context, level);
        }
        return false;
    }

    public static boolean hasCTMBrightness(Context context) {
        return Device.currentDevice().hasCTMBrightness(context);
    }

    public static boolean hasFLBrightness(Context context) {
        return Device.currentDevice().hasFLBrightness(context);
    }

    public static Integer[] getWarmLightValues(Context context) {
        return Device.currentDevice().getWarmLightValues(context);
    }

    public static Integer[] getColdLightValues(Context context) {
        return Device.currentDevice().getColdLightValues(context);
    }

    public static int getWarmLightConfigValue(Context context) {
        return Device.currentDevice().getWarmLightConfigValue(context);
    }

    public static int getColdLightConfigValue(Context context) {
        return Device.currentDevice().getColdLightConfigValue(context);
    }

    public static boolean setWarmLightDeviceValue(Context context, int value) {
        return Device.currentDevice().setWarmLightDeviceValue(context, value);
    }

    public static boolean setColdLightDeviceValue(Context context, int value) {
        return Device.currentDevice().setColdLightDeviceValue(context, value);
    }

    public static boolean increaseBrightness(Context context, int lightType) {
        return Device.currentDevice().increaseBrightness(context, lightType);
    }

    public static boolean decreaseBrightness(Context context, int lightType) {
        return Device.currentDevice().decreaseBrightness(context, lightType);
    }

    public static boolean isWarmLightOn(Context context) {
        return Device.currentDevice().isLightOn(context, 2);
    }

    public static boolean openWarmLight() {
        return Device.currentDevice().openFrontLight(2);
    }

    public static boolean closeWarmLight() {
        return Device.currentDevice().closeFrontLight(2);
    }

    public static boolean isColdLightOn(Context context) {
        return Device.currentDevice().isLightOn(context, 3);
    }

    public static boolean openColdLight() {
        return Device.currentDevice().openFrontLight(3);
    }

    public static boolean closeColdLight() {
        return Device.currentDevice().closeFrontLight(3);
    }

    public static boolean isLightOn(Context context, int type) {
        return Device.currentDevice().isLightOn(context, type);
    }
}
