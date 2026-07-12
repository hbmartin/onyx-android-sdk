package com.onyx.android.sdk.pen;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class NeoPenConfigTest {
    @Test
    public void recoveredRendererRemainsDefaultAndReferenceRendererIsOptIn() {
        NeoPenConfig config = new NeoPenConfig();
        assertEquals(NeoPenConfig.RENDERER_RECOVERED_V1, config.getRendererVersion());

        assertEquals(config, config.setRendererVersion(NeoPenConfig.RENDERER_REFERENCE_COMPAT));
        assertEquals(NeoPenConfig.RENDERER_REFERENCE_COMPAT, config.getRendererVersion());
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsUnknownRendererVersion() {
        new NeoPenConfig().setRendererVersion(99);
    }

    @Test
    public void rendererOptionsExposeAdditionsWithoutDependingOnKotlinMetadata() {
        NeoPenConfig config = new NeoPenConfig();
        assertEquals(config, NeoPenRendererOptions.useReferenceCompatibleRenderer(config));
        assertEquals(
                NeoPenConfig.RENDERER_REFERENCE_COMPAT,
                NeoPenRendererOptions.getRendererVersion(config));
        NeoPenRendererOptions.useRecoveredRenderer(config);
        assertEquals(NeoPenConfig.RENDERER_RECOVERED_V1, config.rendererVersion);
    }
}
