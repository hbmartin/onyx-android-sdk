package com.onyx.android.sdk.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import androidx.annotation.RequiresPermission;
import androidx.annotation.WorkerThread;
import com.onyx.android.sdk.device.Device;
import com.onyx.android.sdk.device.IMX6Device;
import com.onyx.android.sdk.device.RK3026Device;
import com.onyx.android.sdk.device.RK31XXDevice;
import com.onyx.android.sdk.device.RK32XXDevice;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceInfoUtil {
    private static final String a = "/proc/version";
    private static final String b = "/sys/onyx_misc/build_id";
    private static final String c = "-";
    private static final String d = "DeviceInfoUtil";
    private static final String e = "unknown";
    private static final String f = "sys/onyx_misc/cpu_serial";

    public static File getExternalStorageDirectory() {
        File directory = Device.currentDevice.getExternalStorageDirectory();
        Log.d(d, "external storage=" + directory);
        return directory;
    }

    public static File getRemovableSDCardDirectory() {
        File directory = Device.currentDevice.getRemovableSDCardDirectory();
        Log.d(d, "removable storage=" + directory);
        return directory;
    }

    public static Point getScreenResolution(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        int iIntValue = displayMetrics.widthPixels;
        int iIntValue2 = displayMetrics.heightPixels;
        int i = Build.VERSION.SDK_INT;
        if (i >= 14 && i < 17) {
            try {
                iIntValue = ((Integer) Display.class.getMethod("getRawWidth", new Class[0]).invoke(defaultDisplay, new Object[0])).intValue();
                iIntValue2 = ((Integer) Display.class.getMethod("getRawHeight", new Class[0]).invoke(defaultDisplay, new Object[0])).intValue();
            } catch (Exception unused) {
            }
        }
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                Point point = new Point();
                Class[] clsArr = new Class[1];
                clsArr[0] = Point.class;
                Display.class.getMethod("getRealSize", clsArr).invoke(defaultDisplay, point);
                iIntValue = point.x;
                iIntValue2 = point.y;
            } catch (Exception unused2) {
            }
        }
        return new Point(iIntValue, iIntValue2);
    }

    public static String deviceInfo() {
        return (((TTFFont.UNKNOWN_FONT_NAME + "\n OS Version: " + System.getProperty("os.version") + "(" + Build.VERSION.INCREMENTAL + ")") + "\n OS API Level: " + Build.VERSION.SDK_INT) + "\n Device: " + Build.DEVICE) + "\n Model (and Product): " + Build.MODEL + " (" + Build.PRODUCT + ")";
    }

    public static String getDeviceKernelInfo() {
        String line = null;
        try {
            line = StringUtils.readLine(b);
            Log.d(d, "kernel build-id readable=" + !StringUtils.isNullOrEmpty(line));
        } catch (IOException unused) {
            Log.d(d, "kernel build-id unavailable at " + b);
        }
        if (!StringUtils.isNullOrEmpty(line)) {
            return line;
        }
        try {
            String raw = StringUtils.readLine(a);
            String parsed = a(raw);
            Log.d(d, "kernel /proc fallback parsed=" + !"Unavailable".equals(parsed));
            return parsed;
        } catch (IOException e2) {
            Log.e(d, "IO Exception when getting kernel version for Device Info screen", e2);
            return "Unavailable";
        }
    }

    private static String a(String rawKernelVersion) {
        Matcher matcher = Pattern.compile("Linux version (\\S+) \\((\\S+?)\\) (?:\\((?:gcc|clang).+\\)) (#\\d+) (?:.*?)?((Sun|Mon|Tue|Wed|Thu|Fri|Sat).+)").matcher(rawKernelVersion);
        if (!matcher.matches()) {
            Log.e(d, "Regex did not match on /proc/version: " + rawKernelVersion);
            return "Unavailable";
        }
        if (matcher.groupCount() >= 4) {
            return matcher.group(1) + ShellUtils.COMMAND_LINE_END + matcher.group(2) + " " + matcher.group(3) + ShellUtils.COMMAND_LINE_END + matcher.group(4);
        }
        Log.e(d, "Regex match on /proc/version only returned " + matcher.groupCount() + " groups");
        return "Unavailable";
    }

    public static String getEMTPInfo() {
        String raw = FileUtils.readContentOfFile("/sys/onyx_misc/stylus_fwver");
        Log.d(d, "stylus firmware sysfs readable=" + !StringUtils.isNullOrEmpty(raw));
        String strB = b(raw);
        String str = strB;
        if (StringUtils.isNullOrEmpty(strB) || str.equals(e)) {
            str = OnyxSystemProperties.get("sys.onyx.emtp", TTFFont.UNKNOWN_FONT_NAME);
            Log.d(d, "stylus firmware using system-property fallback, present="
                    + !StringUtils.isNullOrEmpty(str));
        }
        return str;
    }

    private static String b(String emtpInfo) {
        if (!StringUtils.isNotBlank(emtpInfo) || !emtpInfo.contains("-")) {
            return emtpInfo;
        }
        String[] strArrSplit = emtpInfo.split("-");
        return strArrSplit[strArrSplit.length - 1];
    }

    public static String getVComInfo(Context context) {
        int i = 0;
        for (int i2 = 0; i2 < 3; i2++) {
            int vCom = Device.currentDevice.getVCom(context, a());
            i = vCom;
            Log.d(d, "vcom : " + i);
            if (vCom == 50) {
                ThreadUtils.mySleep(1000);
            }
        }
        return i != -1 ? (((double) i) / 100.0d) + " V" : null;
    }

    public static boolean isColorDevice() {
        int colorType = Device.currentDevice().getColorType();
        Log.d(d, "color type=" + colorType);
        return colorType > 0;
    }

    @RequiresPermission("android.permission.READ_PHONE_STATE")
    @WorkerThread
    public static String loadSN(boolean cpuType) {
        return cpuType ? loadCPUSerial() : BuildUtils.getSerial();
    }

    public static String loadCPUSerial() {
        if (!FileUtils.fileExist(f)) {
            return TTFFont.UNKNOWN_FONT_NAME;
        }
        String contentOfFile = FileUtils.readContentOfFile(f);
        return StringUtils.isNotBlank(contentOfFile) ? contentOfFile : TTFFont.UNKNOWN_FONT_NAME;
    }

    private static String a() {
        if (Device.currentDevice() instanceof RK3026Device) {
            return "/sys/devices/platform/onyx_misc.0/vcom_value";
        }
        if (Device.currentDevice() instanceof IMX6Device) {
            return "/sys/class/hwmon/hwmon0/device/vcom_value";
        }
        if (Device.currentDevice() instanceof RK32XXDevice) {
            return "/sys/class/hwmon/hwmon1/device/vcom_value";
        }
        if (Device.currentDevice() instanceof RK31XXDevice) {
            return "/sys/class/hwmon/hwmon0/device/vcom_value";
        }
        return null;
    }
}
