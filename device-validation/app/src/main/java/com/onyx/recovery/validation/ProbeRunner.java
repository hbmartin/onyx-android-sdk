package com.onyx.recovery.validation;

import android.app.Activity;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.view.InputDevice;
import android.view.View;

import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.api.device.epd.UpdateMode;
import com.onyx.android.sdk.data.Orientation;
import com.onyx.android.sdk.data.note.line.Line;
import com.onyx.android.sdk.device.BaseDevice;
import com.onyx.android.sdk.device.Device;
import com.onyx.android.sdk.extension.StringKt;
import com.onyx.android.sdk.utils.BuildUtils;
import com.onyx.android.sdk.utils.Colors;
import com.onyx.android.sdk.utils.DateTimeUtil;
import com.onyx.android.sdk.utils.DetectInputDeviceUtil;
import com.onyx.android.sdk.utils.DeviceFeatureUtil;
import com.onyx.android.sdk.utils.DeviceInfoUtil;
import com.onyx.android.sdk.utils.DeviceUtils;
import com.onyx.android.sdk.utils.ElementCounter;
import com.onyx.android.sdk.utils.FileUtils;
import com.onyx.android.sdk.utils.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class ProbeRunner {
    private ProbeRunner() {}

    static void inventory(Activity activity, ResultRecorder recorder) {
        try {
            recorder.value("inventory", "device", null, ResultRecorder.map(
                    "manufacturer", Build.MANUFACTURER,
                    "model", Build.MODEL,
                    "hardware", Build.HARDWARE,
                    "board", Build.BOARD,
                    "fingerprint", Build.FINGERPRINT,
                    "release", Build.VERSION.RELEASE,
                    "sdk", Build.VERSION.SDK_INT,
                    "abis", Build.SUPPORTED_ABIS,
                    "width", activity.getResources().getDisplayMetrics().widthPixels,
                    "height", activity.getResources().getDisplayMetrics().heightPixels,
                    "densityDpi", activity.getResources().getDisplayMetrics().densityDpi
            ));
            List<Object> devices = new ArrayList<>();
            for (int id : InputDevice.getDeviceIds()) {
                InputDevice input = InputDevice.getDevice(id);
                if (input == null) continue;
                List<Object> ranges = new ArrayList<>();
                for (InputDevice.MotionRange range : input.getMotionRanges()) {
                    ranges.add(ResultRecorder.map("axis", range.getAxis(), "source", range.getSource(),
                            "min", range.getMin(), "max", range.getMax(), "resolution", range.getResolution()));
                }
                devices.add(ResultRecorder.map("id", id, "name", input.getName(),
                        "descriptor", input.getDescriptor(), "sources", input.getSources(), "ranges", ranges));
            }
            recorder.value("inventory", "input_devices", null, devices);
        } catch (Throwable error) {
            recorder.failure("inventory", "device", error);
        }
    }

    static void base(Activity activity, ResultRecorder recorder) {
        long[] sizes = {0, 1, 1023, 1024, 1536, 1048576, 1099511627776L};
        for (long size : sizes) {
            probe(recorder, "base", "readable_file_size_" + size, size,
                    () -> FileUtils.readableFileSize(size));
        }
        probe(recorder, "base", "md5_bytes", "onyx-validation",
                () -> FileUtils.computeMD5("onyx-validation".getBytes(StandardCharsets.UTF_8)));
        probe(recorder, "base", "sanitize_display_name", "a:b/c?.txt",
                () -> FileUtils.sanitizeDisplayName("a:b/c?.txt"));
        probe(recorder, "base", "valid_fat_filename", "notes.txt",
                () -> FileUtils.isValidFatFilename("notes.txt"));
        probe(recorder, "base", "invalid_fat_filename", "bad:name",
                () -> FileUtils.isValidFatFilename("bad:name"));

        File file = new File(activity.getCacheDir(), "validation.txt");
        try {
            boolean saved = FileUtils.saveContentToFile("alpha\nbeta", file);
            recorder.value("base", "temporary_file_roundtrip", file.getName(), ResultRecorder.map(
                    "saved", saved,
                    "read", FileUtils.readContentOfFile(file),
                    "md5", FileUtils.computeMD5(file),
                    "size", FileUtils.getFileSize(file)
            ));
        } catch (Throwable error) {
            recorder.failure("base", "temporary_file_roundtrip", error);
        } finally {
            if (file.exists() && !file.delete()) file.deleteOnExit();
        }

        probe(recorder, "base", "device_utils", null, () -> ResultRecorder.map(
                "sdm", DeviceUtils.isSDMDevice(),
                "rk", DeviceUtils.isRkDevice(),
                "imx", DeviceUtils.isImx6Device(),
                "orientation", DeviceUtils.getScreenOrientation(activity),
                "landscape", DeviceUtils.isDeviceInLandscapeOrientation(activity),
                "touchDeviceCount", DeviceUtils.detectTouchDeviceCount(),
                "inputDevicePath", DeviceUtils.detectInputDevicePath(),
                "battery", DeviceUtils.getBatteryPercentLevel(activity),
                "density", DeviceUtils.getDensityDPI(activity),
                "interactive", DeviceUtils.isDeviceInteractive(activity)
        ));
        try (FileInputStream ignored = new FileInputStream("/proc/bus/input/devices")) {
            recorder.value("base", "proc_input_access", null, true);
        } catch (Throwable error) {
            recorder.failure("base", "proc_input_access", error);
        }

        // Repaired Kotlin binary surface: the same code compiles against the
        // reference JARs and the recovered AARs, so both variants must agree
        // through the $default bridges, companion fields, and final members.
        // The reference's $default bridges are ACC_SYNTHETIC, which javac
        // refuses to resolve from source, so those go through reflection —
        // exactly how a precompiled Kotlin caller would link against them.
        probe(recorder, "base", "string_last_int_number", "page12",
                () -> StringKt.getLastIntNumber("page12", 7));
        probe(recorder, "base", "string_last_int_number_default", "page12",
                () -> StringKt.class.getDeclaredMethod("getLastIntNumber$default",
                        String.class, int.class, int.class, Object.class)
                        .invoke(null, "page12", 7, 2, null));
        probe(recorder, "base", "orientation_companion_linkage", null,
                () -> Orientation.Companion.getClass().getName());
        probe(recorder, "base", "line_copy_default", null, () -> {
            Line line = new Line(1f, 2f, 3f, 4f);
            Line copy = (Line) Line.class.getDeclaredMethod("copy$default",
                    Line.class, float.class, float.class, float.class, float.class,
                    int.class, Object.class)
                    .invoke(null, line, 9f, 9f, 9f, 9f, 15, null);
            return ResultRecorder.map(
                    "startX", copy.getStartX(), "startY", copy.getStartY(),
                    "endX", copy.getEndX(), "endY", copy.getEndY());
        });
        probe(recorder, "base", "colors_luminance_default", null,
                () -> Colors.class.getDeclaredMethod("calculateLuminance$default",
                        Colors.class, int.class, Integer.class, int.class, Object.class)
                        .invoke(null, Colors.INSTANCE, 0xFF888888, null, 2, null));
        probe(recorder, "base", "element_counter", null, () -> {
            ElementCounter<String> counter = new ElementCounter<>();
            counter.put("stylus");
            counter.put("stylus");
            counter.put("eraser");
            return ResultRecorder.map(
                    "stylus", counter.get("stylus"),
                    "asc", counter.printAscList());
        });

        // Pure helpers: identical inputs must produce identical outputs
        // through both SDKs.
        probe(recorder, "base", "string_utils", null, () -> ResultRecorder.map(
                "blank", StringUtils.isBlank("  "),
                "integer", StringUtils.isInteger("42"),
                "notInteger", StringUtils.isInteger("4a2"),
                "join", StringUtils.join(Arrays.asList("a", "b", "c"), "-"),
                "splitLetterNumber", StringUtils.splitLetterAndNumber("page12"),
                "utf8", StringUtils.utf8("onyx".getBytes(StandardCharsets.UTF_8))
        ));

        // Read-only feature and configuration reads. None of these touch
        // accounts, radios, packages, power, or persistent settings; several
        // may legitimately record permission_denied or unsupported_hardware
        // on some firmware, which the comparison pins explicitly.
        probe(recorder, "base", "device_features", null, () -> ResultRecorder.map(
                "audio", DeviceFeatureUtil.hasAudio(activity),
                "wifi", DeviceFeatureUtil.hasWifi(activity),
                "bluetooth", DeviceFeatureUtil.hasBluetooth(activity),
                "touch", DeviceFeatureUtil.hasTouch(activity),
                "frontLight", DeviceFeatureUtil.hasFrontLight(activity),
                "ctmBrightness", DeviceFeatureUtil.hasCTMBrightness(activity),
                "camera", DeviceFeatureUtil.hasCamera(activity),
                "stylus", DeviceFeatureUtil.hasStylus(activity),
                "externalSd", DeviceFeatureUtil.supportExternalSD(activity),
                "fingerprint", DeviceFeatureUtil.hasFingerprint(activity),
                "eac", DeviceFeatureUtil.supportEAC(),
                "lowRam", DeviceFeatureUtil.isLowRamDevice(activity)
        ));
        probe(recorder, "base", "device_info", null, () -> ResultRecorder.map(
                "screen", String.valueOf(DeviceInfoUtil.getScreenResolution(activity)),
                "colorDevice", DeviceInfoUtil.isColorDevice(),
                "externalStorage", DeviceInfoUtil.getExternalStorageDirectory() != null,
                "removableSd", DeviceInfoUtil.getRemovableSDCardDirectory() != null,
                "kernelInfo", shape(DeviceInfoUtil.getDeviceKernelInfo()),
                "vcom", DeviceInfoUtil.getVComInfo(activity),
                "emtp", shape(DeviceInfoUtil.getEMTPInfo())
        ));
        probe(recorder, "base", "serial_access", null, () -> shape(BuildUtils.getSerial()));
        probe(recorder, "base", "sn_access", null, () -> shape(DeviceInfoUtil.loadSN(false)));
        probe(recorder, "base", "cpu_serial_access", null, () -> shape(DeviceInfoUtil.loadCPUSerial()));
        probe(recorder, "base", "detect_input_device_util", null,
                DetectInputDeviceUtil::detectInputDevicePath);
        probe(recorder, "base", "hour24_setting", null, () -> DateTimeUtil.isSystemHour24(activity));
    }

    static void device(Activity activity, View host, ResultRecorder recorder) {
        probe(recorder, "device", "detect", null, () -> ResultRecorder.map(
                "boardPlatform", Device.getBoardPlatform(),
                "index", String.valueOf(Device.currentDeviceIndex()),
                "class", Device.currentDevice().getClass().getName()
        ));
        probe(recorder, "device", "dimensions", null, () -> ResultRecorder.map(
                "touchWidth", EpdController.getTouchWidth(),
                "touchHeight", EpdController.getTouchHeight(),
                "maxPressure", EpdController.getMaxTouchPressure(),
                "epdWidth", EpdController.getEpdWidth(),
                "epdHeight", EpdController.getEpdHeight()
        ));
        probe(recorder, "device", "features", null, () -> ResultRecorder.map(
                "supportRegal", EpdController.supportRegal(),
                "regalEnabled", EpdController.isRegalEnabled(),
                "nightMode", EpdController.isSupportNightMode(),
                "fastMode", EpdController.isInFastMode(),
                "systemMode", String.valueOf(EpdController.getSystemDefaultUpdateMode()),
                "viewMode", String.valueOf(EpdController.getViewDefaultUpdateMode(host)),
                "penState", EpdController.getPenState(),
                "validPenState", EpdController.isValidPenState()
        ));
        probe(recorder, "device", "refresh_state", null, () -> ResultRecorder.map(
                "deepGc", EpdController.isDeepGcMode(host),
                "appScopeMode", String.valueOf(EpdController.getAppScopeRefreshMode()),
                "ctpDisableRegion", EpdController.isCTPDisableRegion(activity),
                "ctpPowerOn", EpdController.isCTPPowerOn(),
                "emtpPowerOn", EpdController.isEMTPPowerOn()
        ));
        // The per-model reflection wrapper behind Device.currentDevice() is
        // where firmware-specific behavior lives; read-only getters only.
        probe(recorder, "device", "current_device_features", null, () -> {
            BaseDevice device = Device.currentDevice();
            return ResultRecorder.map(
                    "supportDfb", device.supportDFB(),
                    "supportRegal", device.supportRegal(),
                    "touchpadEnable", device.isTouchpadEnable(),
                    "touchable", device.isTouchable(activity),
                    "legalSystem", device.isLegalSystem(activity),
                    "primaryStorageRemovable", device.isPrimaryStorageRemovable(activity),
                    "sideKeyMapping", device.getCurrentSideKeyMapping(activity),
                    "frontLightMin", device.getFrontLightBrightnessMinimum(activity),
                    "frontLightMax", device.getFrontLightBrightnessMaximum(activity),
                    "frontLightDefault", device.getFrontLightBrightnessDefault(activity)
            );
        });
        probe(recorder, "device", "raw_matrix", null, () -> matrix(EpdController.getRawTouchPointToScreenMatrix()));
        probe(recorder, "device", "mapping_roundtrip", null, () -> {
            float[] original = {100f, 200f, 900f, 1200f};
            float[] mapped = original.clone();
            EpdController.mapToRawTouchPoint(host, original, mapped);
            float[] roundtrip = mapped.clone();
            EpdController.mapFromRawTouchPoint(host, mapped, roundtrip);
            return ResultRecorder.map("source", original, "raw", mapped, "roundtrip", roundtrip);
        });

        try {
            recorder.record("device", "reversible_modes", "begin", ResultRecorder.MATCH, null, null, null);
            EpdController.setViewDefaultUpdateMode(host, UpdateMode.DU);
            EpdController.invalidate(host, UpdateMode.DU);
            EpdController.refreshScreenRegion(host, 0, 0,
                    Math.max(1, host.getWidth() / 2), Math.max(1, host.getHeight() / 2), UpdateMode.GU);
            EpdController.applyTransientUpdate(UpdateMode.DU);
            EpdController.enterScribbleMode(host);
            EpdController.setScreenHandWritingPenState(host, 4);
            EpdController.setScreenHandWritingRegionMode(host, 0);
            EpdController.setScreenHandWritingRegionLimit(host,
                    new Rect[]{new Rect(0, 0, Math.max(1, host.getWidth()), Math.max(1, host.getHeight()))});
            EpdController.penUp();
            EpdController.resetEpdPost();
            recorder.record("device", "reversible_modes", "observation", ResultRecorder.MATCH,
                    null, ResultRecorder.map("completed", true), null);
        } catch (Throwable error) {
            recorder.failure("device", "reversible_modes", error);
        } finally {
            cleanup(host, recorder);
        }
    }

    static void cleanup(View host, ResultRecorder recorder) {
        try {
            EpdController.leaveScribbleMode(host);
            EpdController.clearTransientUpdate(false);
            EpdController.resetViewUpdateMode(host);
            EpdController.setScreenHandWritingRegionMode(host, 0);
            EpdController.setScreenHandWritingRegionLimit(host);
            EpdController.setScreenHandWritingPenState(host, 4);
            EpdController.penUp();
            recorder.value("device", "cleanup", null, true);
        } catch (Throwable error) {
            recorder.failure("device", "cleanup", error);
        }
    }

    private static Object matrix(Matrix matrix) {
        if (matrix == null) return null;
        float[] values = new float[9];
        matrix.getValues(values);
        return values;
    }

    private static void probe(ResultRecorder recorder, String suite, String caseId,
                              Object input, CheckedSupplier supplier) {
        try {
            recorder.value(suite, caseId, input, supplier.get());
        } catch (Throwable error) {
            // Unsupported and permission-denied outcomes are expected results
            // on some firmware, recorded explicitly so the comparison can pin
            // them instead of treating them as flaky failures.
            if (isUnsupported(error)) {
                recorder.unsupported(suite, caseId, error.getClass().getName());
            } else {
                recorder.failure(suite, caseId, error);
            }
        }
    }

    private static boolean isUnsupported(Throwable error) {
        for (Throwable current = error; current != null; current = current.getCause()) {
            // The ITE wrapper is just reflection plumbing; classify by its cause.
            if (current instanceof java.lang.reflect.InvocationTargetException) continue;
            if (current instanceof UnsupportedOperationException
                    || current instanceof NoSuchMethodError
                    || current instanceof NoSuchFieldError
                    || current instanceof NoClassDefFoundError
                    || current instanceof ReflectiveOperationException) {
                return true;
            }
        }
        return false;
    }

    // Identity-shaped values (serials, device ids) are probed for behavior
    // but recorded as presence + length only, so promoted fixtures never
    // carry an identifier.
    private static Object shape(String value) {
        return ResultRecorder.map(
                "present", value != null && !value.trim().isEmpty(),
                "length", value == null ? 0 : value.length());
    }

    interface CheckedSupplier { Object get() throws Exception; }
}
