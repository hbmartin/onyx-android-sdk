package com.onyx.android.sdk.api.device.epd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import android.os.IBinder;
import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Before;
import org.junit.Test;

public class StrokeTransportTest {
    private static final AtomicInteger LOOKUP_COUNT = new AtomicInteger();
    private static final AtomicReference<IBinder> SERVICE = new AtomicReference<>();

    @Before
    public void resetService() {
        LOOKUP_COUNT.set(0);
        SERVICE.set(null);
    }

    @Test
    public void retriesBinderResolutionOnlyAtNextStrokeBoundary() {
        AtomicBoolean initialBinderAlive = new AtomicBoolean(true);
        IBinder initialBinder = binder(initialBinderAlive);
        IBinder recoveredBinder = binder(new AtomicBoolean(true));
        SERVICE.set(initialBinder);

        SurfaceFlingerStrokeTransport transport = new SurfaceFlingerStrokeTransport(
                new StrokeTransportConfig(100, 101, 102), null, serviceName -> {
                    LOOKUP_COUNT.incrementAndGet();
                    return SERVICE.get();
                });

        assertTrue(transport.isAvailable());
        assertEquals(1, LOOKUP_COUNT.get());

        initialBinderAlive.set(false);
        SERVICE.set(null);
        assertNull(transport.resolveBinder(true));
        assertEquals(2, LOOKUP_COUNT.get());

        SERVICE.set(recoveredBinder);
        assertNull(transport.resolveBinder(false));
        assertEquals(2, LOOKUP_COUNT.get());

        assertSame(recoveredBinder, transport.resolveBinder(true));
        assertEquals(3, LOOKUP_COUNT.get());
    }

    @Test
    public void rejectedStartLatchesFrameworkFallbackForWholeStroke() {
        AtomicInteger transactions = new AtomicInteger();
        RecordingTransport fallback = new RecordingTransport();
        SERVICE.set(binder(new AtomicBoolean(true)));
        SurfaceFlingerStrokeTransport transport = new SurfaceFlingerStrokeTransport(
                new StrokeTransportConfig(100, 101, 102), fallback, serviceName -> {
                    LOOKUP_COUNT.incrementAndGet();
                    return SERVICE.get();
                }, (target, code, baseWidth, x, y, pressure, size, time) -> {
                    transactions.incrementAndGet();
                    return null;
                });

        assertEquals(11.0f, transport.startStroke(1.0f, 0, 0, 0, 0, 0), 0.0f);
        assertEquals(12.0f, transport.addStrokePoint(2.0f, 0, 0, 0, 0, 0), 0.0f);
        assertEquals(13.0f, transport.finishStroke(3.0f, 0, 0, 0, 0, 0), 0.0f);
        assertEquals(1, transactions.get());
        assertEquals(1, fallback.starts);
        assertEquals(1, fallback.adds);
        assertEquals(1, fallback.finishes);

        transport.startStroke(4.0f, 0, 0, 0, 0, 0);
        assertEquals(2, LOOKUP_COUNT.get());
        assertEquals(2, transactions.get());
    }

    @Test
    public void midStrokeBinderFailureDoesNotEmitUnbalancedFallbackCalls() {
        AtomicInteger transactions = new AtomicInteger();
        RecordingTransport fallback = new RecordingTransport();
        SERVICE.set(binder(new AtomicBoolean(true)));
        SurfaceFlingerStrokeTransport transport = new SurfaceFlingerStrokeTransport(
                new StrokeTransportConfig(100, 101, 102), fallback, serviceName -> {
                    LOOKUP_COUNT.incrementAndGet();
                    return SERVICE.get();
                }, (target, code, baseWidth, x, y, pressure, size, time) ->
                        transactions.incrementAndGet() == 1 ? baseWidth + 1.0f : null);

        assertEquals(2.0f, transport.startStroke(1.0f, 0, 0, 0, 0, 0), 0.0f);
        assertEquals(2.0f, transport.addStrokePoint(2.0f, 0, 0, 0, 0, 0), 0.0f);
        assertEquals(3.0f, transport.finishStroke(3.0f, 0, 0, 0, 0, 0), 0.0f);
        assertEquals(0, fallback.starts);
        assertEquals(0, fallback.adds);
        assertEquals(0, fallback.finishes);
        assertEquals(2, transactions.get());

        transport.startStroke(4.0f, 0, 0, 0, 0, 0);
        assertEquals(2, LOOKUP_COUNT.get());
    }

    private static IBinder binder(AtomicBoolean alive) {
        return (IBinder) Proxy.newProxyInstance(
                IBinder.class.getClassLoader(),
                new Class<?>[]{IBinder.class},
                (proxy, method, args) -> {
                    if ("isBinderAlive".equals(method.getName())) {
                        return alive.get();
                    }
                    if ("toString".equals(method.getName())) {
                        return "test-binder";
                    }
                    throw new AssertionError("Unexpected IBinder call: " + method.getName());
                });
    }

    private static final class RecordingTransport implements StrokeTransport {
        int starts;
        int adds;
        int finishes;

        @Override
        public float startStroke(float baseWidth, float x, float y,
                                 float pressure, float size, float time) {
            starts++;
            return baseWidth + 10.0f;
        }

        @Override
        public float addStrokePoint(float baseWidth, float x, float y,
                                    float pressure, float size, float time) {
            adds++;
            return baseWidth + 10.0f;
        }

        @Override
        public float finishStroke(float baseWidth, float x, float y,
                                  float pressure, float size, float time) {
            finishes++;
            return baseWidth + 10.0f;
        }

        @Override
        public boolean isAvailable() {
            return true;
        }

        @Override
        public String getName() {
            return "recording";
        }
    }
}
