package com.onyx.android.sdk.pen.touch;

import static org.junit.Assert.assertSame;

import com.onyx.android.sdk.pen.RawInputTool;
import org.junit.Test;

public class AppTouchRenderTest {
    @Test
    public void shortcutErasingUsesSideEraserTool() {
        assertSame(RawInputTool.SIDE_ERASER, AppTouchRender.rawInputToolForErasing(true));
    }

    @Test
    public void nonShortcutErasingUsesTailEraserTool() {
        assertSame(RawInputTool.TAIL_ERASER, AppTouchRender.rawInputToolForErasing(false));
    }
}
