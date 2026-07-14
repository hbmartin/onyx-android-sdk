package com.onyx.android.sdk.api.device.epd;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FirmwareBinderBackendTest {
    @Test
    public void finitePositiveCheckAcceptsOnlyUsableDimensions() {
        assertTrue(FirmwareBinderBackend.isFinitePositive(Float.MIN_VALUE));
        assertTrue(FirmwareBinderBackend.isFinitePositive(1.0f));
        assertFalse(FirmwareBinderBackend.isFinitePositive(0.0f));
        assertFalse(FirmwareBinderBackend.isFinitePositive(-1.0f));
        assertFalse(FirmwareBinderBackend.isFinitePositive(Float.NaN));
        assertFalse(FirmwareBinderBackend.isFinitePositive(Float.POSITIVE_INFINITY));
        assertFalse(FirmwareBinderBackend.isFinitePositive(Float.NEGATIVE_INFINITY));
    }
}
