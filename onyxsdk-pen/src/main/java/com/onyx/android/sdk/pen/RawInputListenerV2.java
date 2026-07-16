package com.onyx.android.sdk.pen;

import androidx.annotation.AnyThread;

/**
 * Listener for immutable raw events. Callbacks occur on the underlying input-source thread: the
 * native reader thread for SurfaceFlinger rendering or the host view's event thread for the
 * application renderer. One {@link TouchHelper} selects exactly one v2 source, callback invocations
 * are ordered and non-overlapping, and every physical sample is emitted exactly once. Event and
 * device-info objects are immutable and may be retained indefinitely.
 *
 * <p>Use {@link RawInputListeners#dispatching(java.util.concurrent.Executor, RawInputListenerV2)}
 * or the executor overload on {@link TouchHelper#setRawInputListenerV2} when callbacks must run on
 * an application-owned executor.</p>
 */
public interface RawInputListenerV2 {
    @AnyThread
    void onRawInputEvent(RawInputEventV2 event);

    @AnyThread
    default void onRawInputDeviceInfo(RawInputDeviceInfo deviceInfo) {}
}
