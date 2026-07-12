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
}
