package com.onyx.android.sdk.pen;

/** Java/Kotlin-friendly access to additive renderer selection. */
public final class NeoPenRendererOptions {
    private NeoPenRendererOptions() {
    }

    public static NeoPenConfig useRecoveredRenderer(NeoPenConfig config) {
        return requireConfig(config).setRendererVersion(NeoPenConfig.RENDERER_RECOVERED_V1);
    }

    public static NeoPenConfig useReferenceCompatibleRenderer(NeoPenConfig config) {
        return requireConfig(config).setRendererVersion(NeoPenConfig.RENDERER_REFERENCE_COMPAT);
    }

    public static int getRendererVersion(NeoPenConfig config) {
        return requireConfig(config).rendererVersion;
    }

    private static NeoPenConfig requireConfig(NeoPenConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("config must not be null");
        }
        return config;
    }
}
