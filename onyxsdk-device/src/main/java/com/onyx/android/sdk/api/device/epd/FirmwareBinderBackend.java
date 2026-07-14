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
    private volatile String failure;
    private volatile boolean initialized;

    static FirmwareBinderBackend get() {
        return INSTANCE;
    }

    synchronized void resetForExternalExemption() {
        binder = null;
        failure = null;
        initialized = false;
        transactionCodes.clear();
    }

    FirmwareBackendInfo info() {
        ensureInitialized();
        return new FirmwareBackendInfo(
                binder != null && binder.isBinderAlive(),
                BACKEND,
                serviceName,
                interfaceToken,
                ReflectUtil.getHiddenApiAccessStatus(),
                failure != null ? failure : ReflectUtil.getHiddenApiFailure(),
                transactionCodes);
    }

    boolean supports(String operation) {
        ensureInitialized();
        return transactionCodes.containsKey(operation) && binder != null && binder.isBinderAlive();
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
        IBinder target = requireBinder(operation);
        int code = requireCode(operation);
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken(interfaceToken);
            if (!target.transact(code, data, reply, 0)) {
                throw rejected(operation, code);
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
            invalidate(error);
            throw new FirmwareOperationException(
                    operation, BACKEND, "Firmware query failed", error);
        } finally {
            reply.recycle();
            data.recycle();
        }
    }

    float queryFloat(String operation) {
        IBinder target = requireBinder(operation);
        int code = requireCode(operation);
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken(interfaceToken);
            if (!target.transact(code, data, reply, 0)) {
                throw rejected(operation, code);
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
            invalidate(error);
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
        IBinder target = requireBinder(operation);
        int code = requireCode(operation);
        Parcel data = Parcel.obtain();
        try {
            data.writeInterfaceToken(interfaceToken);
            if (writer != null) {
                writer.write(data);
            }
            if (!target.transact(code, data, null, 0)) {
                throw rejected(operation, code);
            }
        } catch (RemoteException | RuntimeException error) {
            if (error instanceof FirmwareOperationException) {
                throw (FirmwareOperationException) error;
            }
            invalidate(error);
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

    private int requireCode(String operation) {
        Integer code = transactionCodes.get(operation);
        if (code == null || code <= 0) {
            throw new FirmwareOperationException(
                    operation, BACKEND, "Transaction code is not verified on this firmware");
        }
        return code;
    }

    private IBinder requireBinder(String operation) {
        ensureInitialized();
        IBinder target = binder;
        if (target == null || !target.isBinderAlive()) {
            throw new FirmwareOperationException(
                    operation, BACKEND,
                    failure == null ? "SurfaceFlinger service is unavailable" : failure);
        }
        return target;
    }

    private void invalidate(Throwable error) {
        binder = null;
        failure = error.getClass().getName() + ": " + error.getMessage();
    }

    @SuppressLint("PrivateApi")
    private synchronized void ensureInitialized() {
        if (initialized && binder != null && binder.isBinderAlive()) {
            return;
        }
        initialized = true;
        binder = null;
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
            resolved.linkToDeath(() -> {
                binder = null;
                failure = "SurfaceFlinger Binder died";
            }, 0);
            binder = resolved;
            failure = null;
        } catch (Throwable error) {
            failure = error.getClass().getName() + ": " + error.getMessage();
            binder = null;
        }
    }

    private void addCode(Class<?> helper, String name) {
        int code = ReflectUtil.getStaticIntDeclaredFieldSafely(helper, name);
        if (code > 0) {
            transactionCodes.put(name, code);
        }
    }

    private interface ParcelWriter {
        void write(Parcel parcel);
    }
}
