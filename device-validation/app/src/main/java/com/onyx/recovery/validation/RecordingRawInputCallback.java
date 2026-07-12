package com.onyx.recovery.validation;

import android.graphics.RectF;

import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.RawInputCallback;
import com.onyx.android.sdk.pen.data.TouchPointList;

import java.util.concurrent.atomic.AtomicLong;

final class RecordingRawInputCallback extends RawInputCallback {
    private final ResultRecorder recorder;
    private final GuidedCanvasView canvas;
    private final AtomicLong callbacks = new AtomicLong();

    RecordingRawInputCallback(ResultRecorder recorder, GuidedCanvasView canvas) {
        this.recorder = recorder;
        this.canvas = canvas;
    }

    long callbackCount() {
        return callbacks.get();
    }

    static boolean rendersOnCanvas(String kind) {
        return kind.startsWith("semantic_draw_") || kind.startsWith("semantic_erase_");
    }

    private void point(String kind, boolean flag, TouchPoint point) {
        callbacks.incrementAndGet();
        recorder.event(kind, ResultRecorder.map(
                "flag", flag,
                "point", pointMap(point)
        ));
        // onPenActive reports hover/proximity motion as well as contact. Keep
        // recording it for the differential, but only paint explicit drawing
        // and erasing callbacks or hovering between strokes connects them.
        if (point != null && rendersOnCanvas(kind)) {
            canvas.addPoint(point.x, point.y, kind.startsWith("semantic_erase_"));
        }
    }

    private void list(String kind, TouchPointList points) {
        callbacks.incrementAndGet();
        recorder.event(kind, ResultRecorder.map(
                "size", points == null ? -1 : points.size(),
                "first", points == null ? null : pointMap(points.first()),
                "last", points == null ? null : pointMap(points.last())
        ));
    }

    static Object pointMap(TouchPoint point) {
        if (point == null) return null;
        return ResultRecorder.map(
                "x", point.x, "y", point.y, "pressure", point.pressure,
                "size", point.size, "tiltX", point.tiltX, "tiltY", point.tiltY,
                "timestamp", point.timestamp
        );
    }

    @Override public void onBeginRawDrawing(boolean flag, TouchPoint point) { canvas.breakStroke(false); point("semantic_draw_begin", flag, point); }
    @Override public void onEndRawDrawing(boolean flag, TouchPoint point) { point("semantic_draw_end", flag, point); }
    @Override public void onRawDrawingTouchPointMoveReceived(TouchPoint point) { point("semantic_draw_move", false, point); }
    @Override public void onRawDrawingTouchPointListReceived(TouchPointList points) { list("semantic_draw_list", points); }
    @Override public void onBeginRawErasing(boolean flag, TouchPoint point) { canvas.breakStroke(true); point("semantic_erase_begin", flag, point); }
    @Override public void onEndRawErasing(boolean flag, TouchPoint point) { point("semantic_erase_end", flag, point); }
    @Override public void onRawErasingTouchPointMoveReceived(TouchPoint point) { point("semantic_erase_move", false, point); }
    @Override public void onRawErasingTouchPointListReceived(TouchPointList points) { list("semantic_erase_list", points); }
    @Override public void onPenActive(TouchPoint point) { point("semantic_pen_active", false, point); }

    @Override
    public void onPenUpRefresh(RectF rect) {
        recorder.event("semantic_pen_refresh", ResultRecorder.map(
                "left", rect == null ? null : rect.left,
                "top", rect == null ? null : rect.top,
                "right", rect == null ? null : rect.right,
                "bottom", rect == null ? null : rect.bottom
        ));
    }
}
