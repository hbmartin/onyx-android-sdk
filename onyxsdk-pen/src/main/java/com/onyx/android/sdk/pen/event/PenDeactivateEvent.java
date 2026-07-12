package com.onyx.android.sdk.pen.event;

import com.onyx.android.sdk.data.note.TouchPoint;

public class PenDeactivateEvent {
    private TouchPoint a;

    public PenDeactivateEvent(TouchPoint point) {
        this.a = point;
    }

    public TouchPoint getPoint() {
        return this.a;
    }
}

