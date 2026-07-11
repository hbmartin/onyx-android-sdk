package com.onyx.android.sdk.recovered.pen;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/** JNI surface recovered independently from each supplied Android ABI. */
public final class NativeContract {
    public static final List<String> ABIS = Collections.unmodifiableList(Arrays.asList(
            "armeabi-v7a", "arm64-v8a", "x86", "x86_64"));

    public static final List<String> JNI_EXPORTS = Collections.unmodifiableList(Arrays.asList(
            "Java_com_onyx_android_sdk_pen_RawInputReader_nativeDebug",
            "Java_com_onyx_android_sdk_pen_RawInputReader_nativeEnableSideBtnErase",
            "Java_com_onyx_android_sdk_pen_RawInputReader_nativeIsValid",
            "Java_com_onyx_android_sdk_pen_RawInputReader_nativePausePen",
            "Java_com_onyx_android_sdk_pen_RawInputReader_nativeRawClose",
            "Java_com_onyx_android_sdk_pen_RawInputReader_nativeRawReader",
            "Java_com_onyx_android_sdk_pen_RawInputReader_nativeSetExcludeRegion",
            "Java_com_onyx_android_sdk_pen_RawInputReader_nativeSetLimitRegion",
            "Java_com_onyx_android_sdk_pen_RawInputReader_nativeSetPenState",
            "Java_com_onyx_android_sdk_pen_RawInputReader_nativeSetRegionMode",
            "Java_com_onyx_android_sdk_pen_RawInputReader_nativeSetStrokeWidth"));

    private NativeContract() {
        throw new UnsupportedOperationException("utility class");
    }
}
