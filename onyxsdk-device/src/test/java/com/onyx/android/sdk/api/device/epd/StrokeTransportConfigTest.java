package com.onyx.android.sdk.api.device.epd;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class StrokeTransportConfigTest {
    @Test
    public void simpleConstructorUsesSafeDefaults() {
        StrokeTransportConfig config = new StrokeTransportConfig(100, 101, 102);

        assertEquals(StrokeTransportConfig.DEFAULT_SERVICE_NAME, config.getServiceName());
        assertNull(config.getInterfaceToken());
        assertEquals(100, config.getStartTransactionCode());
        assertEquals(101, config.getAddPointTransactionCode());
        assertEquals(102, config.getFinishTransactionCode());
        assertFalse(config.isReplyHasExceptionHeader());
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsUnverifiedTransactionCodePlaceholder() {
        new StrokeTransportConfig(0, 101, 102);
    }
}
