package com.onyx.android.sdk.utils;

import android.content.Context;
import android.content.Intent;
import com.onyx.android.sdk.api.utils.CompatibilityUtil;

public class DeviceBroadcastHelper {

    public static class PowerManagerSettings {
        public static final String ARGS_TIMEOUT = "args_timeout";
        public static final String ARGS_ENABLE = "args_enable";
        public static final String ONYX_BASE_ACTION_PREFIX = "onyx.action";
        public static final String ACTION_PREFIX = "onyx.action.pm.";
        public static final String SET_LOW_WORK_WIFI_TIMEOUT_ACTION = "onyx.action.pm.set.lowwork.wifi.timeout";
        public static final String SET_LOW_WORK_BLUETOOTH_TIMEOUT_ACTION = "onyx.action.pm.set.lowwork.bluetooth.timeout";
        public static final String SET_LOW_WORK_AUDIO_TIMEOUT_ACTION = "onyx.action.pm.set.lowwork.audio.timeout";
        public static final String ENABLE_STANDBY_BY_PRESS_POWER_BUTTON_ACTION = "onyx.action.pm.enable.standby.by.press.powerbutton";
    }

    public static Intent addFlagsForAndroidO(Intent intent) {
        if (!CompatibilityUtil.isApiLevelSatisfied(26)) {
            return intent;
        }
        Object staticFieldSafely = ReflectUtil.getStaticFieldSafely(Intent.class, "FLAG_RECEIVER_INCLUDE_BACKGROUND");
        if (staticFieldSafely instanceof Integer) {
            intent.addFlags(((Integer) staticFieldSafely).intValue());
        }
        return intent;
    }

    public static void sendBroadcast(Context context, Intent intent) {
        context.getApplicationContext().sendBroadcast(addFlagsForAndroidO(intent));
    }

    public static void setLowWorkWifiTimeout(Context context, long value) {
        sendBroadcast(context, new Intent(PowerManagerSettings.SET_LOW_WORK_WIFI_TIMEOUT_ACTION).putExtra(PowerManagerSettings.ARGS_TIMEOUT, value));
    }

    public static void setLowWorkBluetoothTimeout(Context context, long value) {
        sendBroadcast(context, new Intent(PowerManagerSettings.SET_LOW_WORK_BLUETOOTH_TIMEOUT_ACTION).putExtra(PowerManagerSettings.ARGS_TIMEOUT, value));
    }

    public static void setLowWorkAudioTimeout(Context context, long value) {
        sendBroadcast(context, new Intent(PowerManagerSettings.SET_LOW_WORK_AUDIO_TIMEOUT_ACTION).putExtra(PowerManagerSettings.ARGS_TIMEOUT, value));
    }

    public static void enableStandbyByPressPowerButton(Context context, boolean enable) {
        sendBroadcast(context, new Intent(PowerManagerSettings.ENABLE_STANDBY_BY_PRESS_POWER_BUTTON_ACTION).putExtra(PowerManagerSettings.ARGS_ENABLE, enable));
    }
}
