package com.onyx.android.sdk.pen.touch;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.RawInputDeviceInfo;
import com.onyx.android.sdk.pen.RawInputEventV2;
import com.onyx.android.sdk.pen.RawInputListenerV2;
import com.onyx.android.sdk.pen.RawInputPhase;
import com.onyx.android.sdk.pen.RawInputTool;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

public class AppTouchRenderTest {
    @Test
    public void shortcutErasingUsesSideEraserTool() {
        assertSame(RawInputTool.SIDE_ERASER, AppTouchRender.rawInputToolForErasing(true));
    }

    @Test
    public void nonShortcutErasingUsesTailEraserTool() {
        assertSame(RawInputTool.TAIL_ERASER, AppTouchRender.rawInputToolForErasing(false));
    }

    @Test
    public void registrationReplayAndEventsCannotOverlap() throws Exception {
        AppTouchRender render = new AppTouchRender(null, null);
        CountDownLatch replayEntered = new CountDownLatch(1);
        CountDownLatch releaseReplay = new CountDownLatch(1);
        CountDownLatch eventDelivered = new CountDownLatch(1);
        AtomicInteger active = new AtomicInteger();
        AtomicInteger maximumActive = new AtomicInteger();
        RawInputListenerV2 listener = new RawInputListenerV2() {
            @Override
            public void onRawInputEvent(RawInputEventV2 event) {
                maximumActive.accumulateAndGet(active.incrementAndGet(), Math::max);
                active.decrementAndGet();
                eventDelivered.countDown();
            }

            @Override
            public void onRawInputDeviceInfo(RawInputDeviceInfo info) {
                maximumActive.accumulateAndGet(active.incrementAndGet(), Math::max);
                replayEntered.countDown();
                try {
                    assertTrue(releaseReplay.await(2, TimeUnit.SECONDS));
                } catch (InterruptedException interrupted) {
                    Thread.currentThread().interrupt();
                    throw new AssertionError(interrupted);
                } finally {
                    active.decrementAndGet();
                }
            }
        };
        Thread registration = new Thread(
                () -> render.setRawInputListenerV2(listener), "listener-registration");
        registration.start();
        assertTrue(replayEntered.await(1, TimeUnit.SECONDS));

        Method emit = AppTouchRender.class.getDeclaredMethod(
                "emitRawInput", RawInputPhase.class, TouchPoint.class,
                boolean.class, boolean.class);
        emit.setAccessible(true);
        CountDownLatch eventDispatchAttempted = new CountDownLatch(1);
        Thread event = new Thread(() -> {
            try {
                eventDispatchAttempted.countDown();
                emit.invoke(render, RawInputPhase.MOVE,
                        new TouchPoint(1f, 2f, 0.5f, 0f, 3L), false, false);
            } catch (ReflectiveOperationException failure) {
                throw new AssertionError(failure);
            }
        }, "input-event");
        event.start();
        assertTrue(eventDispatchAttempted.await(1, TimeUnit.SECONDS));
        assertTrue(awaitThreadState(event, Thread.State.BLOCKED, 1, TimeUnit.SECONDS));
        releaseReplay.countDown();

        registration.join(2_000L);
        event.join(2_000L);
        assertTrue(eventDelivered.await(1, TimeUnit.SECONDS));
        assertEquals(1, maximumActive.get());
    }

    private static boolean awaitThreadState(
            Thread thread, Thread.State expected, long timeout, TimeUnit unit)
            throws InterruptedException {
        long deadline = System.nanoTime() + unit.toNanos(timeout);
        while (thread.getState() != expected && System.nanoTime() < deadline) {
            Thread.sleep(1L);
        }
        return thread.getState() == expected;
    }

    @Test
    public void rejectedRegistrationCallbackIsDetachedWithoutEscaping() throws Exception {
        AppTouchRender render = new AppTouchRender(null, null);
        RawInputListenerV2 rejected = new RawInputListenerV2() {
            @Override
            public void onRawInputEvent(RawInputEventV2 event) { }

            @Override
            public void onRawInputDeviceInfo(RawInputDeviceInfo info) {
                throw new RejectedExecutionException("executor shut down");
            }
        };

        render.setRawInputListenerV2(rejected);

        Field listener = AppTouchRender.class.getDeclaredField("rawInputListenerV2");
        listener.setAccessible(true);
        assertNull(listener.get(render));
    }
}
