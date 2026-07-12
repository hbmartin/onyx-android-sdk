package com.onyx.android.sdk.device;

import android.os.Build;
import android.text.TextUtils;
import androidx.annotation.NonNull;
import com.onyx.android.sdk.utils.ReflectUtil;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/device/Device.class */
public class Device {
    private static final String TAG = "Device";
    public static final String BOARD_PLATFORM_RK3288 = "rk3288";
    public static final String BOARD_PLATFORM_RK312X = "rk312x";
    public static final String BOARD_PLATFORM_RK3368 = "rk3368";
    public static final String BOARD_PLATFORM_MSM8953 = "msm8953";
    public static final String BOARD_PLATFORM_SDM660 = "sdm660";
    public static final String BOARD_PLATFORM_BENGAL = "bengal";
    public static final String BOARD_PLATFORM_MSMNILE = "msmnile";
    public static final String BOARD_PLATFORM_LITO = "lito";
    public static final String BOARD_PLATFORM_VOLCANO = "volcano";
    public static final DeviceIndex currentDeviceIndex = a();
    public static final BaseDevice currentDevice = detectDevice();
    private static Map<String, DeviceIndex> b;

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/device/Device$DeviceIndex.class */
    public enum DeviceIndex {
        BaseDevice,
        imx6,
        imx7,
        Rk3026,
        Rk32xx,
        Rk31xx,
        Rk33xx,
        SDM
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/device/Device$a.class */
    static /* synthetic */ class a {
        static final /* synthetic */ int[] a;

        static {
            int[] iArr = new int[DeviceIndex.values().length];
            a = iArr;
            try {
                iArr[DeviceIndex.SDM.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                a[DeviceIndex.imx6.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                a[DeviceIndex.Rk31xx.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                a[DeviceIndex.imx7.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                a[DeviceIndex.Rk32xx.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                a[DeviceIndex.Rk33xx.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                a[DeviceIndex.Rk3026.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static BaseDevice currentDevice() {
        return currentDevice;
    }

    public static DeviceIndex currentDeviceIndex() {
        return currentDeviceIndex;
    }

    private static Map<String, DeviceIndex> b() {
        if (b == null) {
            HashMap map = new HashMap();
            b = map;
            map.put(BOARD_PLATFORM_RK3288, DeviceIndex.Rk32xx);
            b.put(BOARD_PLATFORM_RK312X, DeviceIndex.Rk31xx);
            b.put(BOARD_PLATFORM_RK3368, DeviceIndex.Rk33xx);
            Map<String, DeviceIndex> map2 = b;
            DeviceIndex deviceIndex = DeviceIndex.SDM;
            map2.put(BOARD_PLATFORM_MSM8953, deviceIndex);
            b.put(BOARD_PLATFORM_SDM660, deviceIndex);
            b.put(BOARD_PLATFORM_BENGAL, deviceIndex);
            b.put(BOARD_PLATFORM_MSMNILE, deviceIndex);
            b.put(BOARD_PLATFORM_LITO, deviceIndex);
            b.put(BOARD_PLATFORM_VOLCANO, deviceIndex);
        }
        return b;
    }

    @NonNull
    public static synchronized BaseDevice detectDevice() {
        switch (a.a[currentDeviceIndex.ordinal()]) {
            case 1:
                return SDMDevice.createDevice();
            case 2:
                return IMX6Device.createDevice();
            case 3:
                return RK31XXDevice.createDevice();
            case 4:
                return IMX7Device.createDevice();
            case 5:
                return RK32XXDevice.createDevice();
            case BaseDevice.LIGHT_TYPE_CTM_TEMPERATURE /* 6 */:
                return RK33XXDevice.createDevice();
            case BaseDevice.LIGHT_TYPE_CTM_BRIGHTNESS /* 7 */:
                return RK3026Device.createDevice();
            default:
                return new BaseDevice();
        }
    }

    private static DeviceIndex a() {
        String strTrim = getBoardPlatform().trim();
        DeviceIndex deviceIndex = DeviceIndex.BaseDevice;
        String str = Build.HARDWARE;
        if (str.contains("freescale") && "imx7".equals(strTrim)) {
            deviceIndex = DeviceIndex.imx7;
        } else if (str.contains("freescale")) {
            deviceIndex = DeviceIndex.imx6;
        } else if (!TextUtils.isEmpty(strTrim) && b().containsKey(strTrim)) {
            deviceIndex = b().get(strTrim);
        } else if (str.contentEquals("rk30board")) {
            deviceIndex = DeviceIndex.Rk3026;
        }
        return deviceIndex;
    }

    public static String getBoardPlatform() {
        return (String) ReflectUtil.invokeMethodSafely(ReflectUtil.getDeclaredMethodSafely(Build.class, "getString", String.class), null, "ro.board.platform");
    }
}
