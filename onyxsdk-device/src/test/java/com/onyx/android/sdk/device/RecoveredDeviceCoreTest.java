package com.onyx.android.sdk.device;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.onyx.android.sdk.api.device.epd.EPDMode;
import com.onyx.android.sdk.api.device.epd.UpdateMode;
import com.onyx.android.sdk.api.device.epd.UpdatePolicy;
import com.onyx.android.sdk.api.device.epd.UpdateScheme;
import com.onyx.android.sdk.utils.ReflectUtil;
import com.onyx.android.sdk.utils.Singleton;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

public class RecoveredDeviceCoreTest {
    @Test
    public void singletonCreatesExactlyOneInstanceUnderContention() throws Exception {
        AtomicInteger creations = new AtomicInteger();
        Singleton<Object> singleton = new Singleton<Object>() {
            @Override
            protected Object create() {
                creations.incrementAndGet();
                return new Object();
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(8);
        try {
            List<Callable<Object>> calls = new ArrayList<>();
            for (int index = 0; index < 64; index++) {
                calls.add(singleton::get);
            }
            List<Future<Object>> results = executor.invokeAll(calls);
            Object expected = results.get(0).get();
            for (Future<Object> result : results) {
                assertSame(expected, result.get());
            }
            assertEquals(1, creations.get());
        } finally {
            executor.shutdownNow();
        }
    }

    @Test
    public void reflectUtilExercisesConstructorMethodAndFields() {
        Constructor<?> constructor = ReflectUtil.getConstructorSafely(Fixture.class);
        assertNotNull(constructor);
        Fixture fixture = (Fixture) ReflectUtil.newInstance(constructor);
        assertNotNull(fixture);

        Method method = ReflectUtil.getMethodSafely(Fixture.class, "join", String.class);
        assertEquals("fixture:value", ReflectUtil.invokeMethodSafely(method, fixture, "value"));
        assertEquals(7, ReflectUtil.getDeclareIntFieldSafely(Fixture.class, fixture, "number"));
        assertTrue(ReflectUtil.setDeclareIntFieldSafely(Fixture.class, fixture, "number", 11));
        assertEquals(11, ReflectUtil.getDeclareIntFieldSafely(Fixture.class, fixture, "number"));
        assertEquals("hidden", ReflectUtil.getDeclareStringFieldSafely(
                Fixture.class, fixture, "secret"));
    }

    @Test
    public void recoveredSwitchTablesMatchEveryReferencedEnumBranch() {
        assertEquals(1, IMX6Device$a.a[EPDMode.FULL.ordinal()]);
        assertEquals(2, IMX6Device$a.a[EPDMode.AUTO.ordinal()]);
        assertEquals(3, IMX6Device$a.a[EPDMode.TEXT.ordinal()]);
        assertEquals(4, IMX6Device$a.a[EPDMode.AUTO_PART.ordinal()]);

        assertEquals(1, IMX6Device$a.b[UpdateMode.GU_FAST.ordinal()]);
        assertEquals(9, IMX6Device$a.b[UpdateMode.REGAL_D.ordinal()]);
        assertEquals(3, RK32XXDevice$a.b[UpdateMode.DU_QUALITY.ordinal()]);
        assertEquals(14, RK32XXDevice$a.b[UpdateMode.HAND_WRITING_REPAINT_MODE.ordinal()]);
        assertEquals(3, SDMDevice$a.b[UpdateMode.DU4.ordinal()]);
        assertEquals(16, SDMDevice$a.b[UpdateMode.REGAL_PLUS.ordinal()]);
        assertEquals(17, SDMDevice$a.b[UpdateMode.HAND_WRITING_REPAINT_MODE.ordinal()]);

        assertEquals(1, IMX6Device$a.c[UpdateScheme.SNAPSHOT.ordinal()]);
        assertEquals(3, IMX6Device$a.c[UpdateScheme.QUEUE_AND_MERGE.ordinal()]);
        assertEquals(1, IMX6Device$a.d[UpdatePolicy.Automatic.ordinal()]);
        assertEquals(2, IMX6Device$a.d[UpdatePolicy.GUIntervally.ordinal()]);

        assertNotSame(IMX6Device$a.a, RK31XXDevice$a.a);
        assertNotSame(IMX6Device$a.a, RK33XXDevice$a.a);
    }

    public static final class Fixture {
        private int number = 7;
        private String secret = "hidden";

        public Fixture() {
        }

        public String join(String suffix) {
            return "fixture:" + suffix;
        }
    }
}
