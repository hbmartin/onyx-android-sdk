package com.onyx.android.sdk.pen;

import androidx.annotation.AnyThread;

/** Listener for immutable raw events. Callbacks occur on the native input-reader thread. */
public interface RawInputListenerV2 {
    @AnyThread
    void onRawInputEvent(RawInputEventV2 event);

    @AnyThread
    default void onRawInputDeviceInfo(RawInputDeviceInfo deviceInfo) {}
}
