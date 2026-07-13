package com.onyx.android.sdk.api.device.epd;

/** A strict firmware operation could not be dispatched or was rejected. */
public final class FirmwareOperationException extends RuntimeException {
    private final String operation;
    private final String backend;

    public FirmwareOperationException(String operation, String backend, String message) {
        super(message);
        this.operation = operation;
        this.backend = backend;
    }

    public FirmwareOperationException(
            String operation, String backend, String message, Throwable cause) {
        super(message, cause);
        this.operation = operation;
        this.backend = backend;
    }

    public String getOperation() {
        return operation;
    }

    public String getBackend() {
        return backend;
    }
}
