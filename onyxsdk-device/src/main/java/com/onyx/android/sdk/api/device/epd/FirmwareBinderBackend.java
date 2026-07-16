package com.onyx.android.sdk.api.device.epd;

import android.annotation.SuppressLint;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.view.View;
import androidx.annotation.Nullable;
import com.onyx.android.sdk.utils.ReflectUtil;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Strict, allow-listed SurfaceFlinger transport for operations exposed by the
 * installed BOOX framework. Unknown transaction codes are never guessed.
 */
final class FirmwareBinderBackend {
    private static final String BACKEND = "surfaceflinger-binder";
    private static final String HELPER_CLASS = "android.onyx.ViewUpdateHelper";
    private static final String DEFAULT_SERVICE = "SurfaceFlinger";
    private static final String DEFAULT_TOKEN = "android.ui.ISurfaceComposer";
    private static final FirmwareBinderBackend INSTANCE = new FirmwareBinderBackend();

    private final Map<String, Integer> transactionCodes = new LinkedHashMap<>();
    private String serviceName = DEFAULT_SERVICE;
    private String interfaceToken = DEFAULT_TOKEN;
    @Nullable
    private volatile IBinder binder;
    @Nullable
    private volatile IBinder.DeathRecipient deathRecipient;
    @Nullable
    private volatile String failure;
    private volatile boolean initialized;

    static FirmwareBinderBackend get() {
        return INSTANCE;
    }

    synchronized void resetForExternalExemption() {
        unlinkCurrentBinder();
        failure = null;
        initialized = false;
        transactionCodes.clear();
    }

    synchronized FirmwareBackendInfo info() {
        ensureInitialized();
        IBinder target = binder;
        return new FirmwareBackendInfo(
                target != null && target.isBinderAlive(),
                BACKEND,
                serviceName,
                interfaceToken,
                ReflectUtil.getHiddenApiAccessStatus(),
                failure != null ? failure : ReflectUtil.getHiddenApiFailure(),
                transactionCodes);
    }

    void waitForUpdateFinished() {
        transactVoid("WAIT_FOR_UPDATE_FINISHED", null);
    }

    void savePenAttachedFramebuffer() {
        transactVoid("SAVE_PEN_ATTACHED_FB", null);
    }

    void handwritingRepaint(View view, int left, int top, int right, int bottom) {
        if (view == null) {
            throw new FirmwareOperationException(
                    "HANDWRITING_REPAINT", BACKEND, "view must not be null");
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int[] region = new int[]{
                left + location[0], top + location[1],
                right + location[0], bottom + location[1]
        };
        transactVoid("HANDWRITING_REPAINT", data -> {
            data.writeInt(0);
            data.writeIntArray(region);
        });
    }

    int queryInt(String operation) {
        TransactionTarget transaction = requireTransaction(operation);
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken(transaction.interfaceToken);
            if (!transaction.binder.transact(transaction.code, data, reply, 0)) {
                throw rejected(operation, transaction.code);
            }
            if (reply.dataAvail() < Integer.BYTES) {
                throw new FirmwareOperationException(
                        operation, BACKEND, "Firmware returned no integer reply");
            }
            return reply.readInt();
        } catch (RemoteException | RuntimeException error) {
            if (error instanceof FirmwareOperationException) {
                throw (FirmwareOperationException) error;
            }
            invalidate(transaction, error);
            throw new FirmwareOperationException(
                    operation, BACKEND, "Firmware query failed", error);
        } finally {
            reply.recycle();
            data.recycle();
        }
    }

    float queryFloat(String operation) {
        TransactionTarget transaction = requireTransaction(operation);
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken(transaction.interfaceToken);
            if (!transaction.binder.transact(transaction.code, data, reply, 0)) {
                throw rejected(operation, transaction.code);
            }
            if (reply.dataAvail() < Float.BYTES) {
                throw new FirmwareOperationException(
                        operation, BACKEND, "Firmware returned no float reply");
            }
            float value = reply.readFloat();
            if (!isFinitePositive(value)) {
                throw new FirmwareOperationException(
                        operation, BACKEND, "Firmware returned invalid dimension " + value);
            }
            return value;
        } catch (RemoteException | RuntimeException error) {
            if (error instanceof FirmwareOperationException) {
                throw (FirmwareOperationException) error;
            }
            invalidate(transaction, error);
            throw new FirmwareOperationException(
                    operation, BACKEND, "Firmware float query failed", error);
        } finally {
            reply.recycle();
            data.recycle();
        }
    }

    static boolean isFinitePositive(float value) {
        return !Float.isInfinite(value) && !Float.isNaN(value) && value > 0.0f;
    }

    private void transactVoid(String operation, @Nullable ParcelWriter writer) {
        TransactionTarget transaction = requireTransaction(operation);
        Parcel data = Parcel.obtain();
        try {
            data.writeInterfaceToken(transaction.interfaceToken);
            if (writer != null) {
                writer.write(data);
            }
            if (!transaction.binder.transact(transaction.code, data, null, 0)) {
                throw rejected(operation, transaction.code);
            }
        } catch (RemoteException | RuntimeException error) {
            if (error instanceof FirmwareOperationException) {
                throw (FirmwareOperationException) error;
            }
            invalidate(transaction, error);
            throw new FirmwareOperationException(
                    operation, BACKEND, "Firmware transaction failed", error);
        } finally {
            data.recycle();
        }
    }

    private FirmwareOperationException rejected(String operation, int code) {
        return new FirmwareOperationException(
                operation, BACKEND, "Firmware rejected transaction code " + code);
    }

    private synchronized TransactionTarget requireTransaction(String operation) {
        ensureInitialized();
        IBinder target = binder;
        if (target == null || !target.isBinderAlive()) {
            throw new FirmwareOperationException(
                    operation, BACKEND,
                    failure == null ? "SurfaceFlinger service is unavailable" : failure);
        }
        Integer code = transactionCodes.get(operation);
        if (code == null || code <= 0) {
            throw new FirmwareOperationException(
                    operation, BACKEND, "Transaction code is not verified on this firmware");
        }
        return new TransactionTarget(target, code, interfaceToken);
    }

    private synchronized void invalidate(TransactionTarget transaction, Throwable error) {
        if (binder == transaction.binder) {
            unlinkCurrentBinder();
            initialized = false;
            failure = error.getClass().getName() + ": " + error.getMessage();
        }
    }

    private synchronized void binderDied(IBinder deadBinder) {
        if (binder == deadBinder) {
            binder = null;
            deathRecipient = null;
            initialized = false;
            failure = "SurfaceFlinger Binder died";
        }
    }

    private void unlinkCurrentBinder() {
        IBinder target = binder;
        IBinder.DeathRecipient recipient = deathRecipient;
        binder = null;
        deathRecipient = null;
        if (target == null || recipient == null) {
            return;
        }
        try {
            target.unlinkToDeath(recipient, 0);
        } catch (RuntimeException ignored) {
            // A concurrently dead Binder may already have removed the recipient.
        }
    }

    @SuppressLint("PrivateApi")
    private synchronized void ensureInitialized() {
        IBinder target = binder;
        if (initialized) {
            if (target == null || target.isBinderAlive()) {
                return;
            }
        }
        unlinkCurrentBinder();
        initialized = true;
        transactionCodes.clear();
        try {
            Class<?> helper = Class.forName(HELPER_CLASS);
            String reflectedService = ReflectUtil.getDeclareStringFieldSafely(
                    helper, null, "SF_TAG");
            String reflectedToken = ReflectUtil.getDeclareStringFieldSafely(
                    helper, null, "TOKEN_TAG");
            if (reflectedService != null && !reflectedService.isEmpty()) {
                serviceName = reflectedService;
            }
            if (reflectedToken != null && !reflectedToken.isEmpty()) {
                interfaceToken = reflectedToken;
            }
            addCode(helper, "WAIT_FOR_UPDATE_FINISHED");
            addCode(helper, "HANDWRITING_REPAINT");
            addCode(helper, "SAVE_PEN_ATTACHED_FB");
            addCode(helper, "GET_MAX_TOUCH_PRESSURE");
            addCode(helper, "GET_EPD_WIDTH");
            addCode(helper, "GET_EPD_HEIGHT");
            addCode(helper, "GET_COLOR_TYPE");

            Class<?> serviceManager = Class.forName("android.os.ServiceManager");
            Method getService = serviceManager.getDeclaredMethod("getService", String.class);
            Object result = getService.invoke(null, serviceName);
            if (!(result instanceof IBinder)) {
                throw new IllegalStateException("SurfaceFlinger returned no Binder");
            }
            IBinder resolved = (IBinder) result;
            IBinder.DeathRecipient recipient = () -> binderDied(resolved);
            resolved.linkToDeath(recipient, 0);
            binder = resolved;
            deathRecipient = recipient;
            failure = null;
        } catch (Throwable error) {
            failure = error.getClass().getName() + ": " + error.getMessage();
            unlinkCurrentBinder();
        }
    }

    private void addCode(Class<?> helper, String name) {
        int code = ReflectUtil.getStaticIntDeclaredFieldSafely(helper, name);
        if (code > 0) {
            transactionCodes.put(name, code);
        }
    }

    private static final class TransactionTarget {
        private final IBinder binder;
        private final int code;
        private final String interfaceToken;

        private TransactionTarget(IBinder binder, int code, String interfaceToken) {
            this.binder = binder;
            this.code = code;
            this.interfaceToken = interfaceToken;
        }
    }

    private interface ParcelWriter {
        void write(Parcel parcel);
    }
}
