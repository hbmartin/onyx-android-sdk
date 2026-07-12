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

    @Test
    public void throwingRecoveredMethodsAreCaughtAndNotPropagated() throws Exception {
        assertFailureContained(RK32XXDevice.class, "b0", "penUp");
        assertFailureContained(RK32XXDevice.class, "m0", "resetEpdPost");
        assertFailureContained(IMX6Device.class, "g0", "resetEpdPost");
        assertFailureContained(RK33XXDevice.class, "g0", "resetEpdPost");
        assertFailureContained(SDMDevice.class, "j0", "penUp");
        assertFailureContained(SDMDevice.class, "u0", "resetEpdPost");
        assertFailureContained(RK31XXDevice.class, "i0", "resetEpdPost");
    }

    @Test
    public void nullRecoveredMethodFieldsAreCaughtAndNotPropagated() throws Exception {
        assertNullFieldContained(RK32XXDevice.class, "b0", "penUp");
        assertNullFieldContained(RK32XXDevice.class, "m0", "resetEpdPost");
        assertNullFieldContained(IMX6Device.class, "g0", "resetEpdPost");
        assertNullFieldContained(RK33XXDevice.class, "g0", "resetEpdPost");
        assertNullFieldContained(SDMDevice.class, "j0", "penUp");
        assertNullFieldContained(SDMDevice.class, "u0", "resetEpdPost");
        assertNullFieldContained(RK31XXDevice.class, "i0", "resetEpdPost");
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

    private static void assertFailureContained(
            Class<?> owner, String fieldName, String methodName) throws Exception {
        Method target = Fixture.class.getDeclaredMethod("targetThrows");
        target.setAccessible(true);
        invokeWithInjectedMethod(owner, fieldName, methodName, target);
    }

    private static void assertNullFieldContained(
            Class<?> owner, String fieldName, String methodName) throws Exception {
        invokeWithInjectedMethod(owner, fieldName, methodName, null);
    }

    /**
     * The recovered wrappers guard the reflective call with a single outer
     * {@code catch (Exception)}; any propagation out of the public method
     * fails this helper via the raised InvocationTargetException.
     */
    private static void invokeWithInjectedMethod(
            Class<?> owner, String fieldName, String methodName, Method injected)
            throws Exception {
        Field field = owner.getDeclaredField(fieldName);
        field.setAccessible(true);
        Object original = field.get(null);
        try {
            field.set(null, injected);
            Constructor<?> constructor = owner.getDeclaredConstructor();
            constructor.setAccessible(true);
            Object device = constructor.newInstance();
            owner.getMethod(methodName).invoke(device);
        } finally {
            field.set(null, original);
        }
    }

    private static final class Fixture {
        private static final AtomicInteger invocations = new AtomicInteger();

        private static void target() {
            invocations.incrementAndGet();
        }

        private static void targetThrows() {
            throw new IllegalStateException("recovered failure branch");
        }
    }
}
