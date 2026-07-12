package com.onyx.android.sdk.pen.event;

import com.onyx.android.sdk.data.note.TouchPoint;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/event/PenDeactivateEvent.class */
public class PenDeactivateEvent {
    private TouchPoint a;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public PenDeactivateEvent(TouchPoint point) {
        this.a = point;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public TouchPoint getPoint() {
        return this.a;
    }
}

