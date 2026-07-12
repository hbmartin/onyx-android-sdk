package com.onyx.android.sdk.device;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

public class RecoveredDeviceMethodTest {
    @Test
    public void invokesAllSevenRecoveredStaticZeroArgumentMethods() throws Exception {
        assertMapping(RK32XXDevice.class, "b0", "penUp");
        assertMapping(RK32XXDevice.class, "m0", "resetEpdPost");
        assertMapping(IMX6Device.class, "g0", "resetEpdPost");
        assertMapping(RK33XXDevice.class, "g0", "resetEpdPost");
        assertMapping(SDMDevice.class, "j0", "penUp");
        assertMapping(SDMDevice.class, "u0", "resetEpdPost");
        assertMapping(RK31XXDevice.class, "i0", "resetEpdPost");
    }

    private static void assertMapping(
            Class<?> owner, String fieldName, String methodName) throws Exception {
        Field field = owner.getDeclaredField(fieldName);
        field.setAccessible(true);
        Object original = field.get(null);

        Method target = Fixture.class.getDeclaredMethod("target");
        target.setAccessible(true);
        Fixture.invocations.set(0);

        try {
            field.set(null, target);
            Constructor<?> constructor = owner.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object device = constructor.newInstance();
            owner.getMethod(methodName).invoke(device);
            assertEquals(owner.getSimpleName() + "." + methodName, 1,
                    Fixture.invocations.get());
        } finally {
            field.set(null, original);
        }
    }

    private static final class Fixture {
        private static final AtomicInteger invocations = new AtomicInteger();

        private static void target() {
            invocations.incrementAndGet();
        }
    }
}
