package com.onyx.android.sdk.recovered.device;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/** Executable, dependency-free representation of the seven CFR-recovered wrappers. */
public final class DeviceMethodRecovery {
    public static final List<RecoveredMethod> METHODS = Collections.unmodifiableList(Arrays.asList(
            new RecoveredMethod("RK32XXDevice", "penUp", "b0"),
            new RecoveredMethod("RK32XXDevice", "resetEpdPost", "m0"),
            new RecoveredMethod("IMX6Device", "resetEpdPost", "g0"),
            new RecoveredMethod("RK33XXDevice", "resetEpdPost", "g0"),
            new RecoveredMethod("SDMDevice", "penUp", "j0"),
            new RecoveredMethod("SDMDevice", "resetEpdPost", "u0"),
            new RecoveredMethod("RK31XXDevice", "resetEpdPost", "i0")));

    private DeviceMethodRecovery() {
        throw new UnsupportedOperationException("utility class");
    }

    public static void invoke(Method method, SafeReflector reflector, ExceptionSink sink) {
        Objects.requireNonNull(reflector, "reflector");
        Objects.requireNonNull(sink, "sink");
        try {
            reflector.invokeMethodSafely(method, null, new Object[0]);
        } catch (Exception exception) {
            sink.accept(exception);
        }
    }

    public static final class RecoveredMethod {
        private final String owner;
        private final String method;
        private final String field;

        public RecoveredMethod(String owner, String method, String field) {
            this.owner = owner;
            this.method = method;
            this.field = field;
        }

        public String owner() {
            return owner;
        }

        public String method() {
            return method;
        }

        public String field() {
            return field;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof RecoveredMethod)) {
                return false;
            }
            RecoveredMethod that = (RecoveredMethod) other;
            return owner.equals(that.owner)
                    && method.equals(that.method)
                    && field.equals(that.field);
        }

        @Override
        public int hashCode() {
            return Objects.hash(owner, method, field);
        }
    }

    @FunctionalInterface
    public interface SafeReflector {
        Object invokeMethodSafely(Method method, Object receiver, Object[] arguments)
                throws Exception;
    }

    @FunctionalInterface
    public interface ExceptionSink {
        void accept(Exception exception);
    }
}
