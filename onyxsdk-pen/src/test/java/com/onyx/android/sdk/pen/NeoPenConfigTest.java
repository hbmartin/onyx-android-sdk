package com.onyx.android.sdk.pen;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.Base64;
import org.junit.Test;

public class NeoPenConfigTest {
    private static final String LEGACY_SERIALIZED_CONFIG =
            "rO0ABXNyACVjb20ub255eC5hbmRyb2lkLnNkay5wZW4uTmVvUGVuQ29uZmlnoxOH0IB6NpcCABtGAAFhRgAKYnJ1c2hBbmdsZUYACmJydXNoUmF0aW9JAApicnVzaFNoYXBlRgAMYnJ1c2hTcGFjaW5nSQAFY29sb3JaABBkaXJlY3Rpb25FbmFibGVkRgANZGlzcGxheVNjYWxlWEYADWRpc3BsYXlTY2FsZVlGAANkcGlaAAhmYXN0TW9kZUYAEG1heFRvdWNoUHJlc3N1cmVGAAhtaW5XaWR0aEYAE3ByZXNzdXJlU2Vuc2l0aXZpdHlJAAtyb3RhdGVBbmdsZUYADnNjYWxlUHJlY2lzaW9uRgALc21vb3RoTGV2ZWxaAAt0aWx0RW5hYmxlZEYACXRpbHRTY2FsZUkABHR5cGVGABF2ZWxvY2l0eUFtcGxpZmllckYAF3ZlbG9jaXR5SWdub3JlVGhyZXNob2xkRgASdmVsb2NpdHlMb3dlckJvdW5kRgATdmVsb2NpdHlTZW5zaXRpdml0eUYAEnZlbG9jaXR5VXBwZXJCb3VuZEYABXdpZHRoTAABYnQAEExqYXZhL3V0aWwvTGlzdDt4cD+AAAAAAAAAQKAAAAAAAAA+gAAA/wAAAAA/gAAAP4AAAEOgAAAAP4AAADqDEm8+mZmaAAAAAD+AAAA+TMzNAEBAAAAAAAABAAAAAAAAAAAAAAAAPwAAAAAAAABA8AAAcA==";

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

    @Test
    public void pinsLegacySerialVersionUid() {
        assertEquals(
                -6695858891391355241L,
                ObjectStreamClass.lookup(NeoPenConfig.class).getSerialVersionUID());
    }

    @Test
    public void deserializesLegacyConfigAndNormalizesRendererVersion() throws Exception {
        byte[] bytes = Base64.getDecoder().decode(LEGACY_SERIALIZED_CONFIG);
        NeoPenConfig config;
        try (ObjectInputStream input = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            config = (NeoPenConfig) input.readObject();
        }
        assertEquals(7.5f, config.getWidth(), 0.0f);
        assertEquals(NeoPenConfig.RENDERER_RECOVERED_V1, config.rendererVersion);
    }
}
