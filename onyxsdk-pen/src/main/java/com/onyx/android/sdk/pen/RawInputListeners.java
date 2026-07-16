package com.onyx.android.sdk.pen;

import android.util.Log;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.concurrent.Executor;

/** Listener adapters for controlling raw-input callback dispatch. */
public final class RawInputListeners {
    private static final String TAG = "RawInputListeners";

    private RawInputListeners() {
    }

    /**
     * Returns a listener that preserves source order and prevents callback overlap even when the
     * supplied executor is a concurrent pool.
     */
    public static RawInputListenerV2 dispatching(
            Executor executor, RawInputListenerV2 delegate) {
        Objects.requireNonNull(executor, "executor");
        Objects.requireNonNull(delegate, "delegate");
        SerialExecutor serial = new SerialExecutor(executor);
        return new RawInputListenerV2() {
            @Override
            public void onRawInputEvent(RawInputEventV2 event) {
                serial.execute(() -> delegate.onRawInputEvent(event));
            }

            @Override
            public void onRawInputDeviceInfo(RawInputDeviceInfo deviceInfo) {
                serial.execute(() -> delegate.onRawInputDeviceInfo(deviceInfo));
            }
        };
    }

    private static final class SerialExecutor implements Executor {
        private final ArrayDeque<Runnable> tasks = new ArrayDeque<>();
        private final Executor delegate;
        private Runnable active;
        private boolean detached;

        private SerialExecutor(Executor delegate) {
            this.delegate = delegate;
        }

        @Override
        public synchronized void execute(Runnable command) {
            if (detached) {
                return;
            }
            tasks.add(() -> {
                try {
                    command.run();
                } catch (ThreadDeath | VirtualMachineError fatal) {
                    throw fatal;
                } catch (Throwable failure) {
                    detach();
                    Log.e(TAG, "RawInputListenerV2 callback failed; listener detached", failure);
                } finally {
                    scheduleNext();
                }
            });
            if (active == null) {
                scheduleNext();
            }
        }

        private synchronized void scheduleNext() {
            if (detached) {
                active = null;
                tasks.clear();
                return;
            }
            active = tasks.poll();
            if (active != null) {
                try {
                    delegate.execute(active);
                } catch (RuntimeException | Error failure) {
                    active = null;
                    tasks.clear();
                    throw failure;
                }
            }
        }

        private synchronized void detach() {
            detached = true;
            active = null;
            tasks.clear();
        }
    }
}
