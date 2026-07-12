package com.onyx.android.sdk.pen.event;

import com.onyx.android.sdk.data.note.TouchPoint;

public class PenActiveEvent {
    private TouchPoint a;

    public PenActiveEvent(TouchPoint point) {
        this.a = point;
    }

    public TouchPoint getPoint() {
        return this.a;
    }
}

