package com.onyx.recovery.validation;

import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;

import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.api.device.epd.UpdateMode;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class PenHarness {
    // Replay delivery is synchronous; these bounds only trip on pathological
    // blocking inside the recovered reader, not on normal device jitter.
    private static final long DELIVER_P95_BOUND_NS = 50_000_000L;
    private static final long DELIVER_MAX_BOUND_NS = 250_000_000L;
    private static final long REPLAY_SETTLE_MS = 1_000L;

    private final ValidationActivity activity;
    private final ResultRecorder recorder;
    private final GuidedCanvasView canvas;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final List<Runnable> scheduled = new ArrayList<>();
    private TracingRawInputReader reader;
    private RecordingRawInputCallback callback;
    private boolean paused;
    private Runnable pendingFinish;
    private String scenario = "";

    PenHarness(ValidationActivity activity, ResultRecorder recorder, GuidedCanvasView canvas) {
        this.activity = activity;
        this.recorder = recorder;
        this.canvas = canvas;
    }

    void configure() {
        paused = false;
        reader = new TracingRawInputReader(recorder);
        callback = new RecordingRawInputCallback(recorder, canvas);
        reader.setHostView(canvas);
        reader.setRawInputCallback(callback);
        reader.setPostInputEvent(true);
        reader.setPenUpRefreshEnabled(true);
        reader.setFilterRepeatMovePoint(false);
        reader.enableSideBtnErase(true);
        reader.setStrokeWidth(4f);
    }

    void startLive(long durationMs, String scenarioId, Runnable finished) {
        try {
            scenario = scenarioId == null ? "" : scenarioId;
            configure();
            applyScenarioSetup();
            reader.start();
            reader.resume();
            recorder.value("pen", "live_started", durationMs,
                    scenario.isEmpty() ? true : scenario);
            scheduleScenarioActions(durationMs);
            pendingFinish = () -> finishLive(finished);
            handler.postDelayed(pendingFinish, durationMs);
        } catch (Throwable error) {
            recorder.failure("pen", "live_start", error);
            cancelScheduled();
            pendingFinish = null;
            quit();
            finished.run();
        }
    }

    // The capture timer and the operator's Finish button both funnel through
    // finishLive; whichever fires first wins and the other becomes a no-op,
    // so an early finish cannot double-write the done marker or skip the
    // fd_valid observation.
    void finishLiveEarly() {
        Runnable finish = pendingFinish;
        if (finish == null) return;
        handler.removeCallbacks(finish);
        finish.run();
    }

    private void finishLive(Runnable finished) {
        if (pendingFinish == null) return;
        pendingFinish = null;
        cancelScheduled();
        recorder.value("pen", "fd_valid", null, safeFdValid());
        teardownScenario();
        quit();
        finished.run();
    }

    void replay(Runnable finished) {
        List<Event> events;
        long[] deliverNs;
        try {
            configure();
            reader.resume();
            events = readReplay(new File(activity.getFilesDir(), "replay.jsonl"));
            if (events.isEmpty()) events = syntheticReplay();
            recorder.value("pen", "replay_source", null,
                    new File(activity.getFilesDir(), "replay.jsonl").isFile() ? "reference_capture" : "synthetic");
            deliverNs = new long[events.size()];
            for (int i = 0; i < events.size(); i++) {
                long begin = System.nanoTime();
                events.get(i).deliver(reader);
                deliverNs[i] = System.nanoTime() - begin;
            }
            recorder.value("pen", "replay_count", null, events.size());
        } catch (Throwable error) {
            recorder.failure("pen", "replay", error);
            quit();
            finished.run();
            return;
        }
        // Semantic callbacks may still be draining through posted messages;
        // measure health after the queue settles instead of blocking the
        // main thread they are delivered on.
        final int delivered = events.size();
        final TracingRawInputReader measuredReader = reader;
        final RecordingRawInputCallback measuredCallback = callback;
        handler.postDelayed(() -> {
            try {
                recordReplayHealth(delivered, deliverNs,
                        measuredReader.rawEventCount(), measuredCallback.callbackCount());
            } catch (Throwable error) {
                recorder.failure("pen", "replay_health", error);
            } finally {
                quit();
                finished.run();
            }
        }, REPLAY_SETTLE_MS);
    }

    private void recordReplayHealth(int delivered, long[] deliverNs, long rawRecorded, long semanticCallbacks) {
        long[] sorted = deliverNs.clone();
        Arrays.sort(sorted);
        long p50 = percentile(sorted, 50);
        long p95 = percentile(sorted, 95);
        long max = sorted.length == 0 ? 0 : sorted[sorted.length - 1];
        long dropped = delivered - rawRecorded;
        boolean healthy = dropped == 0
                && (delivered == 0 || semanticCallbacks > 0)
                && p95 <= DELIVER_P95_BOUND_NS
                && max <= DELIVER_MAX_BOUND_NS;
        recorder.value("pen", "replay_health", null, ResultRecorder.map(
                "healthy", healthy,
                "delivered", delivered,
                "rawRecorded", rawRecorded,
                "dropped", dropped,
                "semanticCallbacks", semanticCallbacks
        ));
        // Raw latency numbers vary run to run; phase "metrics" keeps them out
        // of the cross-variant comparison while still landing in the results.
        recorder.record("pen", "replay_metrics", "metrics", ResultRecorder.MATCH, null, ResultRecorder.map(
                "deliverP50Ns", p50,
                "deliverP95Ns", p95,
                "deliverMaxNs", max,
                "p95BoundNs", DELIVER_P95_BOUND_NS,
                "maxBoundNs", DELIVER_MAX_BOUND_NS,
                "settleMs", REPLAY_SETTLE_MS
        ), null);
    }

    private static long percentile(long[] sorted, int percent) {
        if (sorted.length == 0) return 0;
        int index = (int) Math.ceil(sorted.length * percent / 100.0) - 1;
        return sorted[Math.max(0, Math.min(sorted.length - 1, index))];
    }

    // Guided scenarios: reversible display/reader configuration applied for
    // one capture and undone in teardownScenario(). Identical schedules run
    // on both variants, so scenario_action records stay comparable.
    private void applyScenarioSetup() {
        switch (scenario) {
            case GuidedScenarios.DU_REFRESH:
                EpdController.setViewDefaultUpdateMode(canvas, UpdateMode.DU);
                break;
            case GuidedScenarios.GU_REFRESH:
                EpdController.setViewDefaultUpdateMode(canvas, UpdateMode.GU);
                break;
            case GuidedScenarios.GC_REFRESH:
                EpdController.setViewDefaultUpdateMode(canvas, UpdateMode.GC);
                break;
            case GuidedScenarios.SCRIBBLE_MODE:
                EpdController.enterScribbleMode(canvas);
                EpdController.setScreenHandWritingPenState(canvas, 4);
                break;
            case GuidedScenarios.REGION_REFRESH:
                setLimitMode();
                break;
            case GuidedScenarios.GHOSTING_CLEANUP:
                EpdController.setViewDefaultUpdateMode(canvas, UpdateMode.DU);
                break;
            default:
                break;
        }
        if (!scenario.isEmpty()) {
            recorder.value("pen", "scenario", null, scenario);
        }
    }

    private void scheduleScenarioActions(long durationMs) {
        switch (scenario) {
            case GuidedScenarios.PAUSE_RESUME:
                postScenarioAction(durationMs / 3, "pause", () -> reader.pause());
                postScenarioAction(durationMs * 2 / 3, "resume", () -> reader.resume());
                break;
            case GuidedScenarios.RESTART:
                postScenarioAction(durationMs / 2, "restart", () -> {
                    stopReader();
                    configure();
                    reader.start();
                    reader.resume();
                });
                break;
            case GuidedScenarios.GHOSTING_CLEANUP:
                postScenarioAction(durationMs * 2 / 3, "gc_cleanup", () -> {
                    canvas.clearPaths();
                    EpdController.invalidate(canvas, UpdateMode.GC);
                });
                break;
            default:
                break;
        }
    }

    private void postScenarioAction(long delayMs, String label, Runnable action) {
        Runnable wrapped = () -> {
            try {
                action.run();
                recorder.value("pen", "scenario_action", label, true);
            } catch (Throwable error) {
                recorder.failure("pen", "scenario_action_" + label, error);
            }
        };
        scheduled.add(wrapped);
        handler.postDelayed(wrapped, delayMs);
    }

    private void cancelScheduled() {
        for (Runnable action : scheduled) handler.removeCallbacks(action);
        scheduled.clear();
    }

    private void teardownScenario() {
        if (scenario.isEmpty()) return;
        ProbeRunner.cleanup(canvas, recorder);
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

    private void stopReader() {
        if (reader == null) return;
        try { reader.quit(); } catch (Throwable error) { recorder.failure("pen", "quit", error); }
        reader = null;
        callback = null;
    }

    void quit() {
        // This Handler belongs exclusively to the harness. Clear every live
        // capture, guided-scenario, and replay-settle callback before releasing
        // the activity and reader references so nothing can run post-destroy.
        handler.removeCallbacksAndMessages(null);
        scheduled.clear();
        pendingFinish = null;
        stopReader();
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
