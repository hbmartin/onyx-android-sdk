package com.onyx.android.sdk.pen;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/** Package-private Java bridge for Kotlin unsigned-byte accessors with JVM-mangled names. */
final class PenBrushInkAccess {
    private static final Constructor<PenBrushInk> CONSTRUCTOR = constructor();
    private static final Method SIZE = method("getSize-w2LRezQ");
    private static final Method ANGLE = method("getAngle36-w2LRezQ");
    private static final Method ALPHA = method("getAlpha-w2LRezQ");

    private PenBrushInkAccess() {
    }

    static PenBrushInk create(float x, float y, byte size, byte angle, byte alpha) {
        try {
            return CONSTRUCTOR.newInstance(x, y, size, angle, alpha, null);
        } catch (InstantiationException | IllegalAccessException exception) {
            throw new IllegalStateException("Unable to create PenBrushInk", exception);
        } catch (InvocationTargetException exception) {
            throw rethrow("PenBrushInk constructor failed", exception);
        }
    }

    static byte size(PenBrushInk ink) {
        return invoke(SIZE, ink);
    }

    static byte angle(PenBrushInk ink) {
        return invoke(ANGLE, ink);
    }

    static byte alpha(PenBrushInk ink) {
        return invoke(ALPHA, ink);
    }

    private static Method method(String name) {
        try {
            return PenBrushInk.class.getDeclaredMethod(name);
        } catch (NoSuchMethodException exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }

    private static Constructor<PenBrushInk> constructor() {
        try {
            return PenBrushInk.class.getConstructor(
                    float.class,
                    float.class,
                    byte.class,
                    byte.class,
                    byte.class,
                    DefaultConstructorMarker.class);
        } catch (NoSuchMethodException exception) {
            throw new ExceptionInInitializerError(exception);
        }
    }

    private static byte invoke(Method method, PenBrushInk ink) {
        try {
            return (Byte) method.invoke(ink);
        } catch (IllegalAccessException exception) {
            throw new IllegalStateException("Unable to access PenBrushInk", exception);
        } catch (InvocationTargetException exception) {
            throw rethrow("PenBrushInk accessor failed", exception);
        }
    }

    private static RuntimeException rethrow(String message, InvocationTargetException exception) {
        Throwable cause = exception.getCause();
        if (cause instanceof RuntimeException) {
            return (RuntimeException) cause;
        }
        if (cause instanceof Error) {
            throw (Error) cause;
        }
        return new IllegalStateException(message, cause);
    }
}
