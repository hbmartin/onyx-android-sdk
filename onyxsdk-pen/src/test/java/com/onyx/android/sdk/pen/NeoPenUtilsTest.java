package com.onyx.android.sdk.pen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import com.onyx.android.sdk.data.note.TouchPoint;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.Test;

public class NeoPenUtilsTest {
    @Test
    public void legacyNormalizationMutatesCallerPointsAndPreservesDivisionByZero() {
        TouchPoint finite = new TouchPoint(0.0f, 0.0f, 2048.0f, 1.0f, 1L);
        TouchPoint zero = new TouchPoint(1.0f, 1.0f, 0.0f, 1.0f, 2L);
        ArrayList<TouchPoint> points = new ArrayList<>(Arrays.asList(finite, zero));

        NeoPenUtils.normalizePressureInPlace(points, 0.0f);

        assertTrue(Float.isInfinite(finite.pressure));
        assertTrue(Float.isNaN(zero.pressure));
    }

    @Test
    public void configNormalizationUsesPrivateCopies() {
        TouchPoint source = new TouchPoint(0.0f, 0.0f, 2048.0f, 1.0f, 1L);

        ArrayList<TouchPoint> normalized =
                NeoPenUtils.normalizedPressureCopies(Arrays.asList(source), 4096.0f);

        assertNotSame(source, normalized.get(0));
        assertEquals(2048.0f, source.pressure, 0.0f);
        assertEquals(0.5f, normalized.get(0).pressure, 0.0f);
    }

}
