package com.onyx.android.sdk.api.device.epd;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 35)
public class FirmwareBinderBackendTest {
    private static final String OPERATION = "GET_COLOR_TYPE";
    private static final int CODE = 101;
    private static final String OLD_TOKEN = "test.old.IFirmware";
    private static final String NEW_TOKEN = "test.new.IFirmware";
    private static final int NEW_CODE = 202;

    @Test
    public void finitePositiveCheckAcceptsOnlyUsableDimensions() {
        assertTrue(FirmwareBinderBackend.isFinitePositive(Float.MIN_VALUE));
        assertTrue(FirmwareBinderBackend.isFinitePositive(1.0f));
        assertFalse(FirmwareBinderBackend.isFinitePositive(0.0f));
        assertFalse(FirmwareBinderBackend.isFinitePositive(-1.0f));
        assertFalse(FirmwareBinderBackend.isFinitePositive(Float.NaN));
        assertFalse(FirmwareBinderBackend.isFinitePositive(Float.POSITIVE_INFINITY));
        assertFalse(FirmwareBinderBackend.isFinitePositive(Float.NEGATIVE_INFINITY));
    }

    @Test
    public void queryIntReadsVerifiedBinderReply() throws Exception {
        FirmwareBinderBackend backend = backendWithBinder(new Binder() {
            @Override
            protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) {
                reply.writeInt(7);
                return true;
            }
        });

        assertEquals(7, backend.queryInt(OPERATION));
    }

    @Test
    public void queryIntRejectsParcelUnderflow() throws Exception {
        FirmwareBinderBackend backend = backendWithBinder(new Binder() {
            @Override
            protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) {
                return true;
            }
        });

        assertThrows(FirmwareOperationException.class, () -> backend.queryInt(OPERATION));
    }

    @Test
    public void queryFloatReadsFinitePositiveReply() throws Exception {
        FirmwareBinderBackend backend = backendWithBinder(new Binder() {
            @Override
            protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) {
                reply.writeFloat(1404.0f);
                return true;
            }
        });

        assertEquals(1404.0f, backend.queryFloat(OPERATION), 0.0f);
    }

    @Test
    public void queryFloatRejectsInvalidDimension() throws Exception {
        FirmwareBinderBackend backend = backendWithBinder(new Binder() {
            @Override
            protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) {
                reply.writeFloat(Float.NaN);
                return true;
            }
        });

        assertThrows(FirmwareOperationException.class, () -> backend.queryFloat(OPERATION));
    }

    @Test
    public void rejectedTransactionIsReported() throws Exception {
        FirmwareBinderBackend backend = backendWithBinder(new Binder() {
            @Override
            protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) {
                return false;
            }
        });

        assertThrows(FirmwareOperationException.class, () -> backend.queryInt(OPERATION));
    }

    @Test
    public void remoteExceptionInvalidatesCachedBinder() throws Exception {
        IBinder failing = (IBinder) Proxy.newProxyInstance(
                IBinder.class.getClassLoader(),
                new Class<?>[]{IBinder.class},
                (proxy, method, args) -> {
                    if ("isBinderAlive".equals(method.getName())) return true;
                    if ("transact".equals(method.getName())) throw new RemoteException("gone");
                    if ("toString".equals(method.getName())) return "failing-binder";
                    throw new AssertionError("Unexpected IBinder call: " + method.getName());
                });
        FirmwareBinderBackend backend = backendWithBinder(failing);

        assertThrows(FirmwareOperationException.class, () -> backend.queryInt(OPERATION));

        Field binder = FirmwareBinderBackend.class.getDeclaredField("binder");
        binder.setAccessible(true);
        assertNull(binder.get(backend));
    }

    @Test
    public void transactionSnapshotCannotMixStateAcrossReset() throws Exception {
        CountDownLatch secondAliveCheck = new CountDownLatch(1);
        CountDownLatch releaseAliveCheck = new CountDownLatch(1);
        AtomicInteger aliveChecks = new AtomicInteger();
        AtomicInteger transactedCode = new AtomicInteger();
        IBinder oldBinder = (IBinder) Proxy.newProxyInstance(
                IBinder.class.getClassLoader(),
                new Class<?>[]{IBinder.class},
                (proxy, method, args) -> {
                    if ("isBinderAlive".equals(method.getName())) {
                        if (aliveChecks.incrementAndGet() == 2) {
                            secondAliveCheck.countDown();
                            assertTrue(releaseAliveCheck.await(2, TimeUnit.SECONDS));
                        }
                        return true;
                    }
                    if ("transact".equals(method.getName())) {
                        transactedCode.set((Integer) args[0]);
                        Parcel data = (Parcel) args[1];
                        Parcel reply = (Parcel) args[2];
                        data.setDataPosition(0);
                        data.enforceInterface(OLD_TOKEN);
                        reply.writeInt(7);
                        reply.setDataPosition(0);
                        return true;
                    }
                    if ("toString".equals(method.getName())) return "old-binder";
                    throw new AssertionError("Unexpected IBinder call: " + method.getName());
                });
        FirmwareBinderBackend backend = backendWithBinder(oldBinder);
        setField(backend, "interfaceToken", OLD_TOKEN);
        ExecutorService queries = Executors.newSingleThreadExecutor();
        AtomicReference<Throwable> resetFailure = new AtomicReference<>();
        CountDownLatch resetAttempted = new CountDownLatch(1);
        CountDownLatch resetComplete = new CountDownLatch(1);

        try {
            Future<Integer> query = queries.submit(() -> backend.queryInt(OPERATION));
            assertTrue(secondAliveCheck.await(1, TimeUnit.SECONDS));
            Thread resetThread = new Thread(() -> {
                resetAttempted.countDown();
                try {
                    backend.resetForExternalExemption();
                    installBackendState(backend, new Binder(), NEW_CODE, NEW_TOKEN);
                } catch (Throwable failure) {
                    resetFailure.set(failure);
                } finally {
                    resetComplete.countDown();
                }
            }, "firmware-backend-reset");
            resetThread.start();
            assertTrue(resetAttempted.await(1, TimeUnit.SECONDS));
            waitForResetToBlockOrComplete(resetThread, resetComplete);

            releaseAliveCheck.countDown();

            assertEquals(7, query.get(2, TimeUnit.SECONDS).intValue());
            resetThread.join(2_000L);
            assertFalse(resetThread.isAlive());
            assertNull(resetFailure.get());
            assertEquals(CODE, transactedCode.get());
        } finally {
            releaseAliveCheck.countDown();
            queries.shutdownNow();
        }
    }

    @SuppressWarnings("unchecked")
    private static FirmwareBinderBackend backendWithBinder(IBinder binder) throws Exception {
        FirmwareBinderBackend backend = new FirmwareBinderBackend();
        installBackendState(backend, binder, CODE, "android.ui.ISurfaceComposer");
        return backend;
    }

    @SuppressWarnings("unchecked")
    private static void installBackendState(
            FirmwareBinderBackend backend,
            IBinder binder,
            int code,
            String token) throws Exception {
        synchronized (backend) {
            setField(backend, "initialized", true);
            setField(backend, "binder", binder);
            setField(backend, "interfaceToken", token);
            Field codesField = FirmwareBinderBackend.class.getDeclaredField("transactionCodes");
            codesField.setAccessible(true);
            Map<String, Integer> codes = (Map<String, Integer>) codesField.get(backend);
            codes.clear();
            codes.put(OPERATION, code);
        }
    }

    private static void setField(Object target, String name, Object value) throws Exception {
        Field field = target.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(target, value);
    }

    private static void waitForResetToBlockOrComplete(
            Thread resetThread,
            CountDownLatch resetComplete) throws InterruptedException {
        long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(1);
        while (
                resetComplete.getCount() != 0
                        && resetThread.getState() != Thread.State.BLOCKED
                        && System.nanoTime() < deadline
        ) {
            Thread.yield();
        }
        assertTrue(
                "Reset did not reach the backend lock",
                resetComplete.getCount() == 0 || resetThread.getState() == Thread.State.BLOCKED);
    }
}
