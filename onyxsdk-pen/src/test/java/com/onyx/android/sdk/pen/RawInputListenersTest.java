package com.onyx.android.sdk.pen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

public class RawInputListenersTest {
    @Test
    public void concurrentExecutorStillReceivesOrderedNonOverlappingEvents() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(4);
        int count = 50;
        CountDownLatch completed = new CountDownLatch(count);
        AtomicInteger active = new AtomicInteger();
        AtomicInteger maximumActive = new AtomicInteger();
        List<Long> sequences = Collections.synchronizedList(new ArrayList<>());
        RawInputListenerV2 listener = RawInputListeners.dispatching(pool, event -> {
            maximumActive.accumulateAndGet(active.incrementAndGet(), Math::max);
            try {
                sequences.add(event.getSequence());
            } finally {
                active.decrementAndGet();
                completed.countDown();
            }
        });
        try {
            for (int index = 0; index < count; index++) {
                listener.onRawInputEvent(event(index));
            }
            assertTrue(completed.await(5, TimeUnit.SECONDS));
            assertEquals(1, maximumActive.get());
            for (int index = 0; index < count; index++) {
                assertEquals(index, sequences.get(index).longValue());
            }
        } finally {
            pool.shutdownNow();
        }
    }

    @Test
    public void legacyCallbackMethodsAreOptional() {
        new RawInputCallback() { };
    }

    private static RawInputEventV2 event(long sequence) {
        return new RawInputEventV2(
                RawInputPhase.MOVE,
                RawInputTool.PEN,
                1,
                2,
                3,
                0.5f,
                6,
                0,
                0,
                sequence,
                sequence,
                false,
                false);
    }
}
