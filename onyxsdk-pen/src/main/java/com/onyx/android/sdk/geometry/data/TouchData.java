package com.onyx.android.sdk.geometry.data;

import com.onyx.android.sdk.data.note.TouchPoint;

/** Immutable touch event consumed by {@code NeoPenRender}. */
public final class TouchData {
    private final int action;
    private final int toolType;
    private final TouchPoint touchPoint;
    private final TouchPoint predictPoint;
    private BoxInfo boxInfo;

    public TouchData(int action, int toolType, TouchPoint touchPoint, TouchPoint predictPoint, BoxInfo boxInfo) {
        if (touchPoint == null) {
            throw new NullPointerException("touchPoint");
        }
        this.action = action;
        this.toolType = toolType;
        this.touchPoint = touchPoint;
        this.predictPoint = predictPoint;
        this.boxInfo = boxInfo;
    }

    public int getAction() { return action; }
    public int getToolType() { return toolType; }
    public TouchPoint getTouchPoint() { return touchPoint; }
    public TouchPoint getPredictPoint() { return predictPoint; }
    public BoxInfo getBoxInfo() { return boxInfo; }
    public void setBoxInfo(BoxInfo boxInfo) { this.boxInfo = boxInfo; }
    public boolean isTouchDown() { return action == 0; }
    public boolean isTouchUp() { return action == 1; }
    public BoxInfo ensureBoxInfo() { return boxInfo == null ? new BoxInfo() : boxInfo; }
}
