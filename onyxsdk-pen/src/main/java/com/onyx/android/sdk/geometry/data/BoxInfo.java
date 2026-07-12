package com.onyx.android.sdk.geometry.data;

import android.graphics.RectF;

/**
 * Minimal geometry value required by the pen touch-data bridge.
 *
 * <p>Intentional recovery extension: the original class ships in a separate
 * Onyx geometry artifact that was not supplied for recovery. It must stay
 * public because {@link TouchData} exposes it through the public
 * {@code NeoPenRender.onTouchData} API.
 */
public class BoxInfo {
    private final RectF box = new RectF();

    public BoxInfo() {
    }

    public RectF getBox() {
        return box;
    }
}
