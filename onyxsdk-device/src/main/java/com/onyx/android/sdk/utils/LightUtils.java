package com.onyx.android.sdk.utils;

import android.content.Context;
import com.onyx.android.sdk.device.BaseDevice;
import com.onyx.android.sdk.device.Device;

public class LightUtils {
    public static boolean adjustBrightness(Context context, int lightType, boolean increase) {
        int frontLightDeviceValue = Device.currentDevice().getFrontLightDeviceValue(context);
        Integer[] coldLightValues = null;
        if (Device.currentDevice().hasFLBrightness(context)) {
            Device.currentDevice();
            lightType = 1;
            coldLightValues = Device.currentDevice().getFLBrightnessValues(context);
        }
        int i = lightType;
        BaseDevice baseDevice = Device.currentDevice;
        if (i == 2) {
            frontLightDeviceValue = Device.currentDevice().getWarmLightConfigValue(context);
            coldLightValues = Device.currentDevice().getWarmLightValues(context);
        } else if (lightType == 3) {
            frontLightDeviceValue = Device.currentDevice().getColdLightConfigValue(context);
            coldLightValues = Device.currentDevice().getColdLightValues(context);
        }
        if (coldLightValues == null || coldLightValues.length == 0) {
            return false;
        }
        a(context, lightType, frontLightDeviceValue, coldLightValues, increase);
        return true;
    }

    private static void a(Context context, int lightType, int currentValue, Integer[] lightSteps, boolean increase) {
        int iAbs = Math.abs(currentValue - lightSteps[0].intValue());
        int i = 0;
        int i2 = 0;
        while (i2 < lightSteps.length) {
            int i3 = iAbs;
            int iAbs2 = Math.abs(currentValue - lightSteps[i2].intValue());
            int i4 = iAbs2;
            if (i3 >= iAbs2) {
                i = i2;
            } else {
                i4 = iAbs;
            }
            i2++;
            iAbs = i4;
        }
        int i5 = increase ? i + 1 : i - 1;
        if (i5 < 0 || i5 >= lightSteps.length) {
            return;
        }
        a(context, lightType, lightSteps[i5].intValue());
    }

    private static void a(Context context, int lightType, int value) {
        Device.currentDevice();
        if (lightType == 2) {
            Device.currentDevice().setWarmLightDeviceValue(context, value);
        }
        Device.currentDevice();
        if (lightType == 3) {
            Device.currentDevice().setColdLightDeviceValue(context, value);
        }
        Device.currentDevice();
        if (lightType == 1) {
            Device.currentDevice().setFrontLightDeviceValue(context, value);
        }
    }
}
