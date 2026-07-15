package com.onyx.android.sdk.pen;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TouchHelperConfigurationTest {
    @Test
    public void invalidStrokeWidthsAreSanitizedBeforeRendererMutation() {
        assertEquals(3.0f, TouchHelper.sanitizeStrokeWidth(0.0f), 0.0f);
        assertEquals(3.0f, TouchHelper.sanitizeStrokeWidth(-1.0f), 0.0f);
        assertEquals(3.0f, TouchHelper.sanitizeStrokeWidth(Float.NaN), 0.0f);
        assertEquals(3.0f, TouchHelper.sanitizeStrokeWidth(Float.POSITIVE_INFINITY), 0.0f);
        assertEquals(4.5f, TouchHelper.sanitizeStrokeWidth(4.5f), 0.0f);
    }
}
