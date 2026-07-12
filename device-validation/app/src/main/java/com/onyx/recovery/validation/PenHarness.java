package com.onyx.recovery.validation;

import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

final class PenHarness {
    private final ValidationActivity activity;
    private final ResultRecorder recorder;
    private final GuidedCanvasView canvas;
    private TracingRawInputReader reader;
    private boolean paused;

    PenHarness(ValidationActivity activity, ResultRecorder recorder, GuidedCanvasView canvas) {
        this.activity = activity;
        this.recorder = recorder;
        this.canvas = canvas;
    }

    void configure() {
        reader = new TracingRawInputReader(recorder);
        reader.setHostView(canvas);
        reader.setRawInputCallback(new RecordingRawInputCallback(recorder, canvas));
        reader.setPostInputEvent(true);
        reader.setPenUpRefreshEnabled(true);
        reader.setFilterRepeatMovePoint(false);
        reader.enableSideBtnErase(true);
        reader.setStrokeWidth(4f);
    }

    void startLive(long durationMs, Runnable finished) {
        try {
            configure();
            reader.start();
            reader.resume();
            recorder.value("pen", "live_started", durationMs, true);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                recorder.value("pen", "fd_valid", null, safeFdValid());
                quit();
                finished.run();
            }, durationMs);
        } catch (Throwable error) {
            recorder.failure("pen", "live_start", error);
            quit();
            finished.run();
        }
    }

    void replay(Runnable finished) {
        try {
            configure();
            reader.resume();
            List<Event> events = readReplay(new File(activity.getFilesDir(), "replay.jsonl"));
            if (events.isEmpty()) events = syntheticReplay();
            recorder.value("pen", "replay_source", null,
                    new File(activity.getFilesDir(), "replay.jsonl").isFile() ? "reference_capture" : "synthetic");
            for (Event event : events) event.deliver(reader);
            recorder.value("pen", "replay_count", null, events.size());
        } catch (Throwable error) {
            recorder.failure("pen", "replay", error);
        } finally {
            quit();
            finished.run();
        }
    }

    void setLimitMode() {
        if (reader == null) return;
        reader.setMultiRegionMode();
        reader.setLimitRect(new Rect(canvas.getWidth() / 10, canvas.getHeight() / 10,
                canvas.getWidth() * 9 / 10, canvas.getHeight() * 9 / 10));
        recorder.value("pen", "mode", null, "limit");
    }

    void setExcludeMode() {
        if (reader == null) return;
        List<Rect> regions = new ArrayList<>();
        regions.add(new Rect(canvas.getWidth() * 35 / 100, canvas.getHeight() * 35 / 100,
                canvas.getWidth() * 65 / 100, canvas.getHeight() * 65 / 100));
        reader.setExcludeRect(regions);
        recorder.value("pen", "mode", null, "exclude_center");
    }

    void setSingleMode() {
        if (reader == null) return;
        reader.setSingleRegionMode();
        recorder.value("pen", "mode", null, "single_region");
    }

    void togglePause() {
        if (reader == null) return;
        if (paused) reader.resume(); else reader.pause();
        paused = !paused;
        recorder.value("pen", "mode", null, paused ? "paused" : "resumed");
    }

    void quit() {
        if (reader == null) return;
        try { reader.quit(); } catch (Throwable error) { recorder.failure("pen", "quit", error); }
        reader = null;
    }

    private boolean safeFdValid() {
        try { return reader != null && reader.isFdValid(); }
        catch (Throwable error) { recorder.failure("pen", "fd_valid", error); return false; }
    }

    private static List<Event> syntheticReplay() {
        List<Event> result = new ArrayList<>();
        result.add(new Event(100, 200, 100, 1, -1, false, false, false, 0, 1_000));
        result.add(new Event(120, 230, 400, 2, -2, false, false, false, 1, 2_000));
        result.add(new Event(150, 260, 0, 2, -2, false, false, false, 2, 3_000));
        result.add(new Event(300, 400, 800, 0, 0, true, false, true, 0, 4_000));
        result.add(new Event(320, 420, 900, 0, 0, true, false, true, 1, 5_000));
        result.add(new Event(340, 440, 0, 0, 0, true, false, true, 3, 6_000));
        result.add(new Event(340, 440, 0, 0, 0, false, false, false, 6, 7_000));
        return result;
    }

    private static List<Event> readReplay(File file) throws Exception {
        List<Event> events = new ArrayList<>();
        if (!file.isFile()) return events;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                JSONObject object = new JSONObject(line);
                JSONObject value = object.has("output") ? object.getJSONObject("output") : object;
                if (!"raw_jni".equals(object.optString("caseId", "raw_jni"))) continue;
                events.add(new Event(
                        (float) value.getDouble("x"), (float) value.getDouble("y"), value.getInt("pressure"),
                        value.getInt("tiltX"), value.getInt("tiltY"), value.getBoolean("erasing"),
                        value.getBoolean("shortcutDrawing"), value.getBoolean("shortcutErasing"),
                        value.getInt("state"), value.getLong("timestamp")));
            }
        }
        return events;
    }

    private static final class Event {
        final float x, y; final int pressure, tx, ty; final boolean erasing, drawing, shortcutErase;
        final int state; final long timestamp;
        Event(float x, float y, int pressure, int tx, int ty, boolean erasing,
              boolean drawing, boolean shortcutErase, int state, long timestamp) {
            this.x = x; this.y = y; this.pressure = pressure; this.tx = tx; this.ty = ty;
            this.erasing = erasing; this.drawing = drawing; this.shortcutErase = shortcutErase;
            this.state = state; this.timestamp = timestamp;
        }
        void deliver(TracingRawInputReader reader) {
            reader.onTouchPointReceived(x, y, pressure, tx, ty, erasing, drawing, shortcutErase, state, timestamp);
        }
    }
}
