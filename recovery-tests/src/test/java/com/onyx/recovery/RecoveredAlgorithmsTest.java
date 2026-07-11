package com.onyx.recovery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.onyx.android.sdk.recovered.base.DisposeFinally;
import com.onyx.android.sdk.recovered.base.ReadableFileSize;
import com.onyx.android.sdk.recovered.device.DeviceMethodRecovery;
import com.onyx.android.sdk.recovered.pen.NativeContract;
import com.onyx.android.sdk.pen.RawInputReader;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class RecoveredAlgorithmsTest {
    @Test
    void readableFileSizeMatchesRecoveredBoundaries() {
        assertEquals("0", ReadableFileSize.format(-1));
        assertEquals("0", ReadableFileSize.format(0));
        assertEquals("1 B", ReadableFileSize.format(1));
        assertEquals("1 KB", ReadableFileSize.format(1024));
        assertEquals("1.5 KB", ReadableFileSize.format(1536));
        assertEquals("1 TB", ReadableFileSize.format(1L << 40));
    }

    @Test
    void utilityConstructorsExerciseIntentionalUnsupportedOperationPath() throws Exception {
        var constructor = ReadableFileSize.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        var thrown = assertThrows(java.lang.reflect.InvocationTargetException.class,
                constructor::newInstance);
        assertTrue(thrown.getCause() instanceof UnsupportedOperationException);
    }

    @Test
    void disposeRunsAfterSuccessAndAfterCaughtException() {
        AtomicInteger disposals = new AtomicInteger();
        List<Exception> logged = new ArrayList<>();
        DisposeFinally.run(() -> {}, disposals::incrementAndGet, logged::add);
        assertEquals(1, disposals.get());
        assertTrue(logged.isEmpty());

        Exception expected = new Exception("recovered failure path");
        DisposeFinally.run(() -> { throw expected; }, disposals::incrementAndGet, logged::add);
        assertEquals(2, disposals.get());
        assertEquals(List.of(expected), logged);
    }

    @Test
    void disposeRunsBeforeUncaughtErrorEscapes() {
        AtomicBoolean disposed = new AtomicBoolean();
        AssertionError expected = new AssertionError("not an Exception");
        AssertionError actual = assertThrows(AssertionError.class,
                () -> DisposeFinally.run(
                        () -> { throw expected; },
                        () -> disposed.set(true),
                        ignored -> {}));
        assertSame(expected, actual);
        assertTrue(disposed.get());
    }

    @Test
    void allSevenDeviceMappingsExerciseSuccessAndExceptionBranches() throws Exception {
        assertEquals(7, DeviceMethodRecovery.METHODS.size());
        assertEquals(7, DeviceMethodRecovery.METHODS.stream().distinct().count());

        Method method = Fixtures.class.getDeclaredMethod("target");
        AtomicInteger invocations = new AtomicInteger();
        List<Exception> failures = new ArrayList<>();
        DeviceMethodRecovery.invoke(method, (candidate, receiver, arguments) -> {
            assertSame(method, candidate);
            assertEquals(0, arguments.length);
            assertEquals(null, receiver);
            invocations.incrementAndGet();
            return null;
        }, failures::add);
        assertEquals(1, invocations.get());
        assertTrue(failures.isEmpty());

        Exception expected = new Exception("visible decompiler-disagreement branch");
        DeviceMethodRecovery.invoke(method, (candidate, receiver, arguments) -> {
            throw expected;
        }, failures::add);
        assertEquals(List.of(expected), failures);
    }

    @Test
    void nativeContractCoversEveryOriginalAbiAndJniEntryPoint() {
        assertEquals(List.of("armeabi-v7a", "arm64-v8a", "x86", "x86_64"),
                NativeContract.ABIS);
        assertEquals(11, NativeContract.JNI_EXPORTS.size());
        assertFalse(NativeContract.JNI_EXPORTS.stream().anyMatch(name -> !name.startsWith("Java_")));
        assertEquals(11, List.of(RawInputReader.class.getDeclaredMethods()).stream()
                .filter(method -> Modifier.isNative(method.getModifiers()))
                .count());
    }

    private static final class Fixtures {
        private static void target() {}
    }
}
