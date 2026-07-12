package com.onyx.android.sdk.utils;

import android.content.Context;
import android.os.Build;
import com.onyx.android.sdk.data.GObject;
import java.util.List;

public class LightConfig {
    private static String b;
    private static LightConfig c;
    public static final String WARM_LIGHT_VALUE = "warm_light_value";
    public static final String COLD_LIGHT_VALUE = "cold_light_value";
    private GObject a;

    public static LightConfig sharedInstance(Context context) {
        if (c == null) c = new LightConfig(context);
        return c;
    }

    private LightConfig(Context context) {
        a = a(context, Build.MODEL.toLowerCase());
        if (a == null) a = a(context, "brightness_config");
    }

    private GObject a(Context context, String resourceName) {
        try {
            int id = context.getResources().getIdentifier(
                    resourceName.toLowerCase(), "raw", context.getPackageName());
            return id == 0 ? null : RawResourceUtil.objectFromRawResource(context, id);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public Integer[] getWarmLightValues() {
        if (a != null && a.hasKey(WARM_LIGHT_VALUE)) {
            List<Integer> values = a.getList(WARM_LIGHT_VALUE);
            return values.toArray(new Integer[values.size()]);
        }
        return defaults();
    }

    public Integer[] getColdLightValues() {
        if (a != null && a.hasKey(COLD_LIGHT_VALUE)) {
            List<Integer> values = a.getList(COLD_LIGHT_VALUE);
            return values.toArray(new Integer[values.size()]);
        }
        return defaults();
    }

    private static Integer[] defaults() {
        return new Integer[]{0, 3, 6, 9, 12, 15, 17, 19, 21, 23, 25, 26, 27, 28, 29, 30, 31};
    }

    public Integer[][] getNaturalLightValues() {
        return new Integer[][]{getColdLightValues(), getWarmLightValues()};
    }
}
