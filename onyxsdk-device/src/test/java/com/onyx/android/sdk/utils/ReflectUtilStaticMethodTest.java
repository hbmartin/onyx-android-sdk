package com.onyx.android.sdk.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
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

    @Test
    public void hiddenApiPrefixesCoverRecoveredReflectionPackagesAndNestedViews() {
        List<String> prefixes = Arrays.asList(ReflectUtil.hiddenApiExemptionPrefixes());

        assertTrue(prefixes.contains("Landroid/app/"));
        assertTrue(prefixes.contains("Landroid/hardware/"));
        assertTrue(prefixes.contains("Landroid/os/"));
        assertTrue(prefixes.contains("Landroid/view/"));
        assertTrue(prefixes.contains("Lcom/android/internal/"));
        assertFalse(prefixes.contains("Landroid/view/View;"));
        assertFalse(prefixes.contains("L"));
    }
}
