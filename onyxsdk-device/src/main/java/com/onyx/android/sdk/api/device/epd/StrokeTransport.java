package com.onyx.android.sdk.api.device.epd;

import android.annotation.SuppressLint;
import android.os.IBinder;
import android.os.Parcel;
import android.util.Log;
import com.onyx.android.sdk.device.Device;
import java.lang.reflect.Method;

interface StrokeTransport {
    float startStroke(float baseWidth, float x, float y, float pressure, float size, float time);

    float addStrokePoint(float baseWidth, float x, float y, float pressure, float size, float time);

    float finishStroke(float baseWidth, float x, float y, float pressure, float size, float time);

    boolean isAvailable();

    String getName();
}

final class FrameworkStrokeTransport implements StrokeTransport {
    @Override
    public float startStroke(float baseWidth, float x, float y, float pressure, float size, float time) {
        return Device.currentDevice().startStroke(baseWidth, x, y, pressure, size, time);
    }

    @Override
    public float addStrokePoint(float baseWidth, float x, float y, float pressure, float size, float time) {
        return Device.currentDevice().addStrokePoint(baseWidth, x, y, pressure, size, time);
    }

    @Override
    public float finishStroke(float baseWidth, float x, float y, float pressure, float size, float time) {
        return Device.currentDevice().finishStroke(baseWidth, x, y, pressure, size, time);
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getName() {
        return "framework-reflection";
    }
}

final class SurfaceFlingerStrokeTransport implements StrokeTransport {
    private static final String TAG = "SurfaceFlingerStroke";

    private final StrokeTransportConfig config;
    private final StrokeTransport fallback;
    private volatile IBinder binder;
    private volatile boolean resolutionAttempted;

    SurfaceFlingerStrokeTransport(StrokeTransportConfig config, StrokeTransport fallback) {
        this.config = config;
        this.fallback = fallback;
    }

    @Override
    public float startStroke(float baseWidth, float x, float y, float pressure, float size, float time) {
        return transact(config.getStartTransactionCode(), baseWidth, x, y, pressure, size, time, 0);
    }

    @Override
    public float addStrokePoint(float baseWidth, float x, float y, float pressure, float size, float time) {
        return transact(config.getAddPointTransactionCode(), baseWidth, x, y, pressure, size, time, 1);
    }

    @Override
    public float finishStroke(float baseWidth, float x, float y, float pressure, float size, float time) {
        return transact(config.getFinishTransactionCode(), baseWidth, x, y, pressure, size, time, 2);
    }

    @Override
    public boolean isAvailable() {
        return resolveBinder() != null;
    }

    @Override
    public String getName() {
        return "surfaceflinger-binder";
    }

    @SuppressLint("PrivateApi") // Opt-in path for a firmware-verified hidden service.
    private IBinder resolveBinder() {
        IBinder current = binder;
        if (current != null && current.isBinderAlive()) {
            return current;
        }
        if (resolutionAttempted && current == null) {
            return null;
        }
        synchronized (this) {
            current = binder;
            if (current != null && current.isBinderAlive()) {
                return current;
            }
            resolutionAttempted = true;
            try {
                Class<?> serviceManager = Class.forName("android.os.ServiceManager");
                Method getService = serviceManager.getMethod("getService", String.class);
                Object service = getService.invoke(null, config.getServiceName());
                if (service instanceof IBinder) {
                    binder = (IBinder) service;
                }
            } catch (ReflectiveOperationException | RuntimeException error) {
                Log.w(TAG, "Unable to resolve configured Binder service", error);
            }
            return binder;
        }
    }

    private float transact(int code, float baseWidth, float x, float y,
                           float pressure, float size, float time, int operation) {
        IBinder target = resolveBinder();
        if (target == null) {
            return fallback(operation, baseWidth, x, y, pressure, size, time);
        }
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            String token = config.getInterfaceToken();
            if (token != null && !token.isEmpty()) {
                data.writeInterfaceToken(token);
            }
            data.writeFloat(baseWidth);
            data.writeFloat(x);
            data.writeFloat(y);
            data.writeFloat(pressure);
            data.writeFloat(size);
            data.writeFloat(time);
            if (!target.transact(code, data, reply, 0)) {
                return fallback(operation, baseWidth, x, y, pressure, size, time);
            }
            if (config.isReplyHasExceptionHeader()) {
                reply.readException();
            }
            if (reply.dataAvail() < 4) {
                return baseWidth;
            }
            return reply.readFloat();
        } catch (RuntimeException | android.os.RemoteException error) {
            Log.w(TAG, "Stroke Binder transaction failed; using framework transport", error);
            binder = null;
            resolutionAttempted = false;
            return fallback(operation, baseWidth, x, y, pressure, size, time);
        } finally {
            reply.recycle();
            data.recycle();
        }
    }

    private float fallback(int operation, float baseWidth, float x, float y,
                           float pressure, float size, float time) {
        if (operation == 0) {
            return fallback.startStroke(baseWidth, x, y, pressure, size, time);
        }
        if (operation == 1) {
            return fallback.addStrokePoint(baseWidth, x, y, pressure, size, time);
        }
        return fallback.finishStroke(baseWidth, x, y, pressure, size, time);
    }
}
