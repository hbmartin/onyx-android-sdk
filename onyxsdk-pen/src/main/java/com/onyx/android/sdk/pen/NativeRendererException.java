package com.onyx.android.sdk.pen;

/** Structured exception raised by the recovered native renderer boundary. */
public final class NativeRendererException extends RuntimeException {
    public enum Code {
        INVALID_ARGUMENT,
        INVALID_HANDLE,
        ALLOCATION_LIMIT,
        JNI_FAILURE,
        PANIC,
        NATIVE_FAILURE
    }

    private final Code code;

    /** Constructor used by JNI. Messages may begin with a bracketed {@link Code}. */
    public NativeRendererException(String message) {
        super(stripCode(message));
        this.code = parseCode(message);
    }

    public NativeRendererException(Code code, String message) {
        super(message);
        this.code = code == null ? Code.NATIVE_FAILURE : code;
    }

    public Code getCode() {
        return code;
    }

    private static Code parseCode(String message) {
        if (message != null && message.startsWith("[")) {
            int end = message.indexOf(']');
            if (end > 1) {
                try {
                    return Code.valueOf(message.substring(1, end));
                } catch (IllegalArgumentException ignored) {
                    // Fall through to the stable catch-all.
                }
            }
        }
        return Code.NATIVE_FAILURE;
    }

    private static String stripCode(String message) {
        if (message == null) return "Native renderer failure";
        int end = message.startsWith("[") ? message.indexOf(']') : -1;
        return end >= 0 ? message.substring(end + 1).trim() : message;
    }
}
