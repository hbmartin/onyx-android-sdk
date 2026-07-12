package com.onyx.android.sdk.pen.event;

import com.onyx.android.sdk.data.note.TouchPoint;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/event/PenActiveEvent.class */
public class PenActiveEvent {
    private TouchPoint a;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public PenActiveEvent(TouchPoint point) {
        this.a = point;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchPoint getPoint() {
        return this.a;
    }
}

