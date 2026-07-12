package com.onyx.android.sdk.api.device.epd;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StrokeConfigurationTest {
    @Test
    public void configurationDefensivelyCopiesParameters() {
        float[] input = {1.0f, 2.0f};
        StrokeConfiguration configuration = new StrokeConfiguration(
                0xff123456, 3.5f, EpdController.STROKE_STYLE_BRUSH, input);
        input[0] = 9.0f;

        assertEquals(0xff123456, configuration.getColor());
        assertEquals(3.5f, configuration.getWidth(), 0.0f);
        assertEquals(EpdController.STROKE_STYLE_BRUSH, configuration.getStyle());
        assertArrayEquals(new float[]{1.0f, 2.0f}, configuration.getExtraParameters(), 0.0f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsNonFiniteWidth() {
        new StrokeConfiguration(0, Float.NaN, 0, null);
    }
}
