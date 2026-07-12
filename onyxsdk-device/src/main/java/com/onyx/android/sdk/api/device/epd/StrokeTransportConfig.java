package com.onyx.android.sdk.api.device.epd;

import androidx.annotation.Nullable;

/**
 * Firmware-specific SurfaceFlinger stroke transaction configuration.
 *
 * <p>No transaction codes are guessed by the SDK. Applications must supply
 * values verified for the target firmware before opting into the Binder
 * transport.</p>
 */
public final class StrokeTransportConfig {
    public static final String DEFAULT_SERVICE_NAME = "SurfaceFlinger";

    private final String serviceName;
    @Nullable
    private final String interfaceToken;
    private final int startTransactionCode;
    private final int addPointTransactionCode;
    private final int finishTransactionCode;
    private final boolean replyHasExceptionHeader;

    public StrokeTransportConfig(int startTransactionCode,
                                 int addPointTransactionCode,
                                 int finishTransactionCode) {
        this(DEFAULT_SERVICE_NAME, null, startTransactionCode,
                addPointTransactionCode, finishTransactionCode, false);
    }

    public StrokeTransportConfig(String serviceName,
                                 @Nullable String interfaceToken,
                                 int startTransactionCode,
                                 int addPointTransactionCode,
                                 int finishTransactionCode,
                                 boolean replyHasExceptionHeader) {
        if (serviceName == null || serviceName.trim().isEmpty()) {
            throw new IllegalArgumentException("serviceName must not be empty");
        }
        if (startTransactionCode <= 0 || addPointTransactionCode <= 0
                || finishTransactionCode <= 0) {
            throw new IllegalArgumentException("transaction codes must be positive");
        }
        this.serviceName = serviceName;
        this.interfaceToken = interfaceToken;
        this.startTransactionCode = startTransactionCode;
        this.addPointTransactionCode = addPointTransactionCode;
        this.finishTransactionCode = finishTransactionCode;
        this.replyHasExceptionHeader = replyHasExceptionHeader;
    }

    public String getServiceName() {
        return serviceName;
    }

    @Nullable
    public String getInterfaceToken() {
        return interfaceToken;
    }

    public int getStartTransactionCode() {
        return startTransactionCode;
    }

    public int getAddPointTransactionCode() {
        return addPointTransactionCode;
    }

    public int getFinishTransactionCode() {
        return finishTransactionCode;
    }

    public boolean isReplyHasExceptionHeader() {
        return replyHasExceptionHeader;
    }
}
