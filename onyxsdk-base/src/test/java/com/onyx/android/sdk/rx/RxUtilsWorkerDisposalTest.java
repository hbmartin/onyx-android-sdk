package com.onyx.android.sdk.rx;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

public class RxUtilsWorkerDisposalTest {
    @Test
    public void disposesExactlyOnceAfterSuccess() {
        RecordingWorker worker = new RecordingWorker();

        new RxUtils.d(() -> {}, worker).run();

        assertEquals(1, worker.disposals.get());
    }

    @Test
    public void catchesExceptionAndDisposesExactlyOnce() {
        RecordingWorker worker = new RecordingWorker();

        new RxUtils.d(() -> {
            throw new Exception("recovered failure path");
        }, worker).run();

        assertEquals(1, worker.disposals.get());
    }

    @Test
    public void disposesBeforeErrorEscapes() {
        RecordingWorker worker = new RecordingWorker();
        AssertionError expected = new AssertionError("not an Exception");

        try {
            new RxUtils.d(() -> {
                throw expected;
            }, worker).run();
            fail("expected AssertionError");
        } catch (AssertionError actual) {
            assertSame(expected, actual);
        }

        assertEquals(1, worker.disposals.get());
    }

    private static final class RecordingWorker extends Scheduler.Worker {
        private final AtomicInteger disposals = new AtomicInteger();

        @Override
        public Disposable schedule(Runnable action, long delay, TimeUnit unit) {
            return Disposables.empty();
        }

        @Override
        public void dispose() {
            disposals.incrementAndGet();
        }

        @Override
        public boolean isDisposed() {
            return disposals.get() > 0;
        }
    }
}
