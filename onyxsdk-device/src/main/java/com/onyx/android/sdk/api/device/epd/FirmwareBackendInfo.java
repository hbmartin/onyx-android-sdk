package com.onyx.android.sdk.api.device.epd;

import androidx.annotation.Nullable;
import com.onyx.android.sdk.utils.ReflectUtil;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Immutable diagnostic snapshot for the strict firmware backend. */
public final class FirmwareBackendInfo {
    private final boolean available;
    private final String backend;
    private final String serviceName;
    private final String interfaceToken;
    private final ReflectUtil.HiddenApiAccessStatus hiddenApiAccessStatus;
    @Nullable
    private final String failure;
    private final Map<String, Integer> transactionCodes;

    FirmwareBackendInfo(
            boolean available,
            String backend,
            String serviceName,
            String interfaceToken,
            ReflectUtil.HiddenApiAccessStatus hiddenApiAccessStatus,
            @Nullable String failure,
            Map<String, Integer> transactionCodes) {
        this.available = available;
        this.backend = backend;
        this.serviceName = serviceName;
        this.interfaceToken = interfaceToken;
        this.hiddenApiAccessStatus = hiddenApiAccessStatus;
        this.failure = failure;
        this.transactionCodes = Collections.unmodifiableMap(
                new LinkedHashMap<>(transactionCodes));
    }

    public boolean isAvailable() {
        return available;
    }

    public String getBackend() {
        return backend;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getInterfaceToken() {
        return interfaceToken;
    }

    public ReflectUtil.HiddenApiAccessStatus getHiddenApiAccessStatus() {
        return hiddenApiAccessStatus;
    }

    @Nullable
    public String getFailure() {
        return failure;
    }

    public Map<String, Integer> getTransactionCodes() {
        return transactionCodes;
    }
}
