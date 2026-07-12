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
import com.onyx.android.sdk.device.Device;
import com.onyx.android.sdk.utils.DeviceUtils;
import com.onyx.android.sdk.utils.FileUtils;

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
            recorder.failure(suite, caseId, error);
        }
    }

    interface CheckedSupplier { Object get() throws Exception; }
}
