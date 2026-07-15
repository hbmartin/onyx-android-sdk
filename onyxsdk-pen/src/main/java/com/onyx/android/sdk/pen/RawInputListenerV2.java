package com.onyx.android.sdk.pen;

import androidx.annotation.AnyThread;

/**
 * Listener for immutable raw events. Callbacks occur on the underlying input-source thread: the
 * native reader thread for SurfaceFlinger rendering or the host view's event thread for the
 * application renderer.
 */
public interface RawInputListenerV2 {
    @AnyThread
    void onRawInputEvent(RawInputEventV2 event);

    @AnyThread
    default void onRawInputDeviceInfo(RawInputDeviceInfo deviceInfo) {}
}
