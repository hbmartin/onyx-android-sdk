package com.onyx.android.sdk.pen.touch;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.view.MotionEvent;
import org.junit.Test;

public class AppTouchInputReaderTest {
    @Test
    public void limitRegionMustContainTheWholeStrokeFootprint() {
        assertFalse(AppTouchInputReader.isStrokeContained(
                0.0f, 0.0f, 100.0f, 100.0f, 5.0f, 50.0f, 10.0f));
        assertTrue(AppTouchInputReader.isStrokeContained(
                0.0f, 0.0f, 100.0f, 100.0f, 10.0f, 50.0f, 10.0f));
    }

    @Test
    public void excludeRegionRejectsAnyStrokeOverlap() {
        assertTrue(AppTouchInputReader.doesStrokeIntersect(
                0.0f, 0.0f, 100.0f, 100.0f, -5.0f, 50.0f, 10.0f));
        assertFalse(AppTouchInputReader.doesStrokeIntersect(
                0.0f, 0.0f, 100.0f, 100.0f, -11.0f, 50.0f, 10.0f));
    }

    @Test
    public void stylusPrimaryButtonSelectsShortcutErasingWhenEnabled() {
        assertTrue(AppTouchInputReader.isSideButtonErasing(
                true, MotionEvent.BUTTON_STYLUS_PRIMARY));
        assertFalse(AppTouchInputReader.isSideButtonErasing(
                false, MotionEvent.BUTTON_STYLUS_PRIMARY));
        assertFalse(AppTouchInputReader.isSideButtonErasing(true, 0));
    }
}
