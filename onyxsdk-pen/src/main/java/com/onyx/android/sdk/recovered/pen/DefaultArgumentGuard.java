package com.onyx.android.sdk.recovered.pen;

/** Recovered behavior of Kotlin's default-argument super-call guard. */
public final class DefaultArgumentGuard {
    private DefaultArgumentGuard() {
        throw new UnsupportedOperationException("utility class");
    }

    public static void rejectSuperCall(Object marker, String functionName) {
        if (marker != null) {
            throw new UnsupportedOperationException(
                    "Super calls with default arguments not supported in this target, function: "
                            + functionName);
        }
    }
}
