package com.onyx.android.sdk.api.device.brightness;

import android.content.Context;
import com.onyx.android.sdk.api.utils.CollectionUtils;
import com.onyx.android.sdk.device.Device;
import java.util.HashMap;
import java.util.Map;

public class BrightnessController {
    public static final int INVALID_VALUE = -1;
    public static final Map<Integer, BaseBrightnessProvider> providerMap = new HashMap();
    private static BrightnessType brightnessType = BrightnessType.NONE;

    private static void initProviderMap(Context context) {
        Context applicationContext = context.getApplicationContext();
        if (Device.currentDevice().checkCTM()) {
            brightnessType = BrightnessType.CTM;
            Map<Integer, BaseBrightnessProvider> map = providerMap;
            map.put(6, new CTMTemperatureProvider(applicationContext));
            map.put(7, new CTMBrightnessProvider(applicationContext));
            return;
        }
        if (Device.currentDevice().hasFLBrightness(context)) {
            brightnessType = BrightnessType.FL;
            providerMap.put(1, new FLBrightnessProvider(applicationContext));
        } else {
            if (!Device.currentDevice().hasCTMBrightness(context)) {
                brightnessType = BrightnessType.NONE;
                return;
            }
            brightnessType = BrightnessType.WARM_AND_COLD;
            Map<Integer, BaseBrightnessProvider> map2 = providerMap;
            map2.put(2, new WarmBrightnessProvider(applicationContext));
            map2.put(3, new ColdBrightnessProvider(applicationContext));
        }
    }

    private static void checkInit(Context context) {
        if (CollectionUtils.isNullOrEmpty(providerMap)) {
            initProviderMap(context);
        }
    }

    public static BaseBrightnessProvider getBrightnessProvider(Context context, int type) {
        checkInit(context);
        return providerMap.get(Integer.valueOf(type));
    }

    public static BrightnessType getBrightnessType(Context context) {
        checkInit(context);
        return brightnessType;
    }
}
