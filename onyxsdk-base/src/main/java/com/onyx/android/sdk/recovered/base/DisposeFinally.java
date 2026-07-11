package com.onyx.android.sdk.recovered.base;

import java.util.Objects;

/**
 * Dependency-free model of the recovered RxUtils scheduled action. It catches
 * Exception, reports it, always disposes, and deliberately does not catch Error.
 */
public final class DisposeFinally {
    private DisposeFinally() {
        throw new UnsupportedOperationException("utility class");
    }

    public static void run(CheckedAction action, Disposable worker, ErrorSink sink) {
        Objects.requireNonNull(action, "action");
        Objects.requireNonNull(worker, "worker");
        Objects.requireNonNull(sink, "sink");
        try {
            action.run();
        } catch (Exception exception) {
            sink.accept(exception);
        } finally {
            worker.dispose();
        }
    }

    @FunctionalInterface
    public interface CheckedAction {
        void run() throws Exception;
    }

    @FunctionalInterface
    public interface Disposable {
        void dispose();
    }

    @FunctionalInterface
    public interface ErrorSink {
        void accept(Exception exception);
    }
}
