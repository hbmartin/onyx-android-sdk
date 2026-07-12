package com.onyx.android.sdk.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import org.junit.Test;

public class ReflectUtilStaticMethodTest {
    public static final class Fixture {
        public static void staticMethod(int ignored) {
        }

        public void instanceMethod(int ignored) {
        }
    }

    @Test
    public void staticResolverRejectsInstanceMethods() {
        Method staticMethod = ReflectUtil.getStaticMethodSafely(
                Fixture.class, "staticMethod", Integer.TYPE);
        Method instanceMethod = ReflectUtil.getStaticMethodSafely(
                Fixture.class, "instanceMethod", Integer.TYPE);

        assertNotNull(staticMethod);
        assertTrue(ReflectUtil.isStaticMethodAvailable(staticMethod));
        assertNull(instanceMethod);
        assertFalse(ReflectUtil.isStaticMethodAvailable(instanceMethod));
    }
}
