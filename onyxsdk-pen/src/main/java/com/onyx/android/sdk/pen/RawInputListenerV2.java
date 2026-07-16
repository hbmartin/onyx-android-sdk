package com.onyx.android.sdk.pen;

import androidx.annotation.AnyThread;

/**
 * Listener for immutable raw events. Event callbacks occur on the underlying input-source thread:
 * the native reader thread for SurfaceFlinger rendering or the host view's event thread for the
 * application renderer. A cached device-info callback may run synchronously on the thread that
 * registers the listener. One {@link TouchHelper} selects exactly one v2 source; all callbacks are
 * serialized and non-overlapping, and every physical sample is emitted exactly once. Event and
 * device-info objects are immutable and may be retained indefinitely. A listener that throws is
 * logged and detached so it cannot terminate the native reader loop.
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
