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

@FunctionalInterface
interface BinderServiceResolver {
    IBinder resolve(String serviceName);
}

@FunctionalInterface
interface BinderTransactionCaller {
    /** Returns null when the Binder rejected the code or the transaction failed. */
    Float transact(IBinder target, int code, float baseWidth, float x, float y,
                   float pressure, float size, float time);
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

    private enum StrokeRoute {
        NONE,
        BINDER,
        FALLBACK,
        FAILED
    }

    private final StrokeTransportConfig config;
    private final StrokeTransport fallback;
    private final BinderServiceResolver serviceResolver;
    private final BinderTransactionCaller transactionCaller;
    private volatile IBinder binder;
    private volatile boolean resolutionAttempted;
    private StrokeRoute strokeRoute = StrokeRoute.NONE;

    SurfaceFlingerStrokeTransport(StrokeTransportConfig config, StrokeTransport fallback) {
        this(config, fallback, null, null);
    }

    SurfaceFlingerStrokeTransport(StrokeTransportConfig config, StrokeTransport fallback,
                                  BinderServiceResolver serviceResolver) {
        this(config, fallback, serviceResolver, null);
    }

    SurfaceFlingerStrokeTransport(StrokeTransportConfig config, StrokeTransport fallback,
                                  BinderServiceResolver serviceResolver,
                                  BinderTransactionCaller transactionCaller) {
        this.config = config;
        this.fallback = fallback;
        this.serviceResolver = serviceResolver;
        this.transactionCaller = transactionCaller;
    }

    @Override
    public synchronized float startStroke(float baseWidth, float x, float y,
                                          float pressure, float size, float time) {
        strokeRoute = StrokeRoute.NONE;
        IBinder target = resolveBinder(true);
        if (target == null) {
            strokeRoute = StrokeRoute.FALLBACK;
            return fallback.startStroke(baseWidth, x, y, pressure, size, time);
        }
        Float result = transactBinder(
                target, config.getStartTransactionCode(), baseWidth, x, y, pressure, size, time);
        if (result == null) {
            invalidateBinder();
            strokeRoute = StrokeRoute.FALLBACK;
            return fallback.startStroke(baseWidth, x, y, pressure, size, time);
        }
        strokeRoute = StrokeRoute.BINDER;
        return result;
    }

    @Override
    public synchronized float addStrokePoint(float baseWidth, float x, float y,
                                             float pressure, float size, float time) {
        if (strokeRoute == StrokeRoute.FALLBACK || strokeRoute == StrokeRoute.NONE) {
            strokeRoute = StrokeRoute.FALLBACK;
            return fallback.addStrokePoint(baseWidth, x, y, pressure, size, time);
        }
        if (strokeRoute == StrokeRoute.FAILED) {
            return baseWidth;
        }
        IBinder target = resolveBinder(false);
        Float result = target == null ? null : transactBinder(
                target, config.getAddPointTransactionCode(), baseWidth, x, y, pressure, size, time);
        if (result == null) {
            invalidateBinder();
            // The framework transport never received this stroke's start. Do
            // not send it an unmatched add/finish sequence; retry next stroke.
            strokeRoute = StrokeRoute.FAILED;
            return baseWidth;
        }
        return result;
    }

    @Override
    public synchronized float finishStroke(float baseWidth, float x, float y,
                                           float pressure, float size, float time) {
        try {
            if (strokeRoute == StrokeRoute.FALLBACK || strokeRoute == StrokeRoute.NONE) {
                return fallback.finishStroke(baseWidth, x, y, pressure, size, time);
            }
            if (strokeRoute == StrokeRoute.FAILED) {
                return baseWidth;
            }
            IBinder target = resolveBinder(false);
            Float result = target == null ? null : transactBinder(
                    target, config.getFinishTransactionCode(),
                    baseWidth, x, y, pressure, size, time);
            if (result == null) {
                invalidateBinder();
                return baseWidth;
            }
            return result;
        } finally {
            strokeRoute = StrokeRoute.NONE;
        }
    }

    @Override
    public boolean isAvailable() {
        return resolveBinder(true) != null;
    }

    @Override
    public String getName() {
        return "surfaceflinger-binder";
    }

    @SuppressLint("PrivateApi") // Opt-in path for a firmware-verified hidden service.
    IBinder resolveBinder(boolean allowRetry) {
        IBinder current = binder;
        if (current != null && current.isBinderAlive()) {
            return current;
        }
        if (resolutionAttempted && !allowRetry) {
            return null;
        }
        synchronized (this) {
            current = binder;
            if (current != null && current.isBinderAlive()) {
                return current;
            }
            if (resolutionAttempted && !allowRetry) {
                return null;
            }
            resolutionAttempted = true;
            binder = null;
            try {
                Object service;
                if (serviceResolver != null) {
                    service = serviceResolver.resolve(config.getServiceName());
                } else {
                    Class<?> serviceManager = Class.forName("android.os.ServiceManager");
                    Method getService = serviceManager.getMethod("getService", String.class);
                    service = getService.invoke(null, config.getServiceName());
                }
                if (service instanceof IBinder) {
                    binder = (IBinder) service;
                }
            } catch (ReflectiveOperationException | RuntimeException error) {
                Log.w(TAG, "Unable to resolve configured Binder service", error);
            }
            return binder;
        }
    }

    private void invalidateBinder() {
        resolutionAttempted = true;
        binder = null;
    }

    private Float transactBinder(IBinder target, int code, float baseWidth, float x, float y,
                                 float pressure, float size, float time) {
        if (transactionCaller != null) {
            return transactionCaller.transact(
                    target, code, baseWidth, x, y, pressure, size, time);
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
                Log.w(TAG, "Stroke Binder rejected transaction code " + code);
                return null;
            }
            if (config.isReplyHasExceptionHeader()) {
                reply.readException();
            }
            if (reply.dataAvail() < 4) {
                return baseWidth;
            }
            return reply.readFloat();
        } catch (RuntimeException | android.os.RemoteException error) {
            Log.w(TAG, "Stroke Binder transaction failed", error);
            return null;
        } finally {
            reply.recycle();
            data.recycle();
        }
    }
}
