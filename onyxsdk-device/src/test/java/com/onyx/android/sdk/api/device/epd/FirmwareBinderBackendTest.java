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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 35)
public class FirmwareBinderBackendTest {
    private static final String OPERATION = "GET_COLOR_TYPE";
    private static final int CODE = 101;

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

    @SuppressWarnings("unchecked")
    private static FirmwareBinderBackend backendWithBinder(IBinder binder) throws Exception {
        FirmwareBinderBackend backend = new FirmwareBinderBackend();
        Field initialized = FirmwareBinderBackend.class.getDeclaredField("initialized");
        initialized.setAccessible(true);
        initialized.setBoolean(backend, true);
        Field binderField = FirmwareBinderBackend.class.getDeclaredField("binder");
        binderField.setAccessible(true);
        binderField.set(backend, binder);
        Field codesField = FirmwareBinderBackend.class.getDeclaredField("transactionCodes");
        codesField.setAccessible(true);
        ((Map<String, Integer>) codesField.get(backend)).put(OPERATION, CODE);
        return backend;
    }
}
