package com.onyx.android.sdk.geometry.data;

import android.graphics.RectF;

/** Minimal geometry value required by the pen touch-data bridge. */
public class BoxInfo {
    private final RectF box = new RectF();

    public BoxInfo() {
    }

    public RectF getBox() {
        return box;
    }
}
