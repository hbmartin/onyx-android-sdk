package com.onyx.recovery.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RecordingRawInputCallbackTest {
    @Test
    public void canvasRendersOnlyExplicitDrawingAndErasingCallbacks() {
        assertTrue(RecordingRawInputCallback.rendersOnCanvas("semantic_draw_begin"));
        assertTrue(RecordingRawInputCallback.rendersOnCanvas("semantic_draw_move"));
        assertTrue(RecordingRawInputCallback.rendersOnCanvas("semantic_draw_end"));
        assertTrue(RecordingRawInputCallback.rendersOnCanvas("semantic_erase_begin"));
        assertTrue(RecordingRawInputCallback.rendersOnCanvas("semantic_erase_move"));
        assertTrue(RecordingRawInputCallback.rendersOnCanvas("semantic_erase_end"));

        assertFalse(RecordingRawInputCallback.rendersOnCanvas("semantic_pen_active"));
        assertFalse(RecordingRawInputCallback.rendersOnCanvas("semantic_pen_refresh"));
    }
}
