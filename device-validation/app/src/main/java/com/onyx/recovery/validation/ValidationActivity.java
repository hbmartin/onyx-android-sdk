package com.onyx.recovery.validation;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ValidationActivity extends Activity {
    private static final String TAG = "OnyxValidation";
    private ResultRecorder recorder;
    private GuidedCanvasView canvas;
    private PenHarness pen;
    private SdkHarness sdkHarness;
    private SurfaceView rawSurface;
    private TextView status;
    private String suite;
    private String scenario;
    private String mmkvMode;
    private boolean finished;
    private final ExecutorService worker = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        new File(getFilesDir(), "done").delete();
        try {
            recorder = new ResultRecorder(this);
        } catch (IOException error) {
            throw new IllegalStateException(error);
        }
        suite = getIntent().getStringExtra("suite");
        if (suite == null || suite.isEmpty()) suite = "automated";
        scenario = getIntent().getStringExtra("scenario");
        mmkvMode = getIntent().getStringExtra("mmkvMode");
        buildUi();
        pen = new PenHarness(this, recorder, canvas);
        sdkHarness = SdkHarnessFactory.create(this, recorder);
        scheduleSuite();
    }

    private void scheduleSuite() {
        if (!suite.startsWith("sdk-")) {
            canvas.post(() -> runSuite(suite));
            return;
        }
        StableSurfaceStarter starter = new StableSurfaceStarter();
        rawSurface.getHolder().addCallback(starter);
        starter.schedule();
    }

    /** Waits past the first layout-time Surface replacement before exercising raw ink. */
    private final class StableSurfaceStarter implements SurfaceHolder.Callback, Runnable {
        private final long deadlineMs = SystemClock.uptimeMillis() + 5_000L;
        private int consecutiveValidFrames;
        private boolean scheduled;
        private boolean started;

        void schedule() {
            if (started || scheduled) return;
            scheduled = true;
            rawSurface.postOnAnimation(this);
        }

        @Override
        public void run() {
            scheduled = false;
            boolean valid = rawSurface.isAttachedToWindow()
                    && rawSurface.getWidth() > 0
                    && rawSurface.getHeight() > 0
                    && rawSurface.getHolder().getSurface().isValid();
            consecutiveValidFrames = valid ? consecutiveValidFrames + 1 : 0;
            if (consecutiveValidFrames >= 2 || SystemClock.uptimeMillis() >= deadlineMs) {
                started = true;
                rawSurface.getHolder().removeCallback(this);
                runSuite(suite);
            } else {
                schedule();
            }
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            schedule();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            schedule();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            consecutiveValidFrames = 0;
            schedule();
        }
    }

    private void buildUi() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(Color.WHITE);
        status = new TextView(this);
        status.setTextSize(18f);
        status.setTextColor(Color.BLACK);
        status.setGravity(Gravity.CENTER);
        status.setText("Onyx SDK validation: " + BuildConfig.SDK_VARIANT);
        root.addView(status, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        LinearLayout controls = new LinearLayout(this);
        controls.setOrientation(LinearLayout.HORIZONTAL);
        addButton(controls, "Limit", () -> pen.setLimitMode());
        addButton(controls, "Exclude", () -> pen.setExcludeMode());
        addButton(controls, "Single", () -> pen.setSingleMode());
        addButton(controls, "Pause", () -> pen.togglePause());
        addButton(controls, "Clear", () -> canvas.clearPaths());
        if ("pen-live".equals(suite)) {
            // Only live captures may finish early, and only through the pen
            // harness so the capture timer is cancelled and the fd_valid
            // observation still lands exactly once.
            addButton(controls, "Finish", () -> pen.finishLiveEarly());
        }
        root.addView(controls, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        canvas = new GuidedCanvasView(this);
        root.addView(canvas, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f));
        rawSurface = new SurfaceView(this);
        rawSurface.setBackgroundColor(Color.WHITE);
        root.addView(rawSurface, new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 320));
        setContentView(root);
    }

    private void addButton(LinearLayout parent, String text, Runnable action) {
        Button button = new Button(this);
        button.setText(text);
        button.setOnClickListener(view -> action.run());
        parent.addView(button, new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
    }

    private void runSuite(String suite) {
        status.setText("Running " + suite + " (" + BuildConfig.SDK_VARIANT + ")");
        if ("pen-live".equals(suite)) {
            long duration = getIntent().getLongExtra("durationMs", 30_000L);
            String label = scenario == null || scenario.isEmpty() ? "guided strokes" : scenario;
            status.setText("Draw now — " + label + "; red/brown marks identify eraser events; "
                    + "capture ends in " + duration / 1000 + " seconds");
            pen.startLive(duration, scenario, () -> finishSuite(suite));
            return;
        }
        if ("pen-replay".equals(suite)) {
            pen.replay(() -> finishSuite(suite));
            return;
        }
        if ("sdk-automated".equals(suite)) {
            sdkHarness.runAutomated(rawSurface, status, () -> finishSuite(suite));
            return;
        }
        if ("sdk-ink".equals(suite)) {
            long duration = getIntent().getLongExtra("durationMs", 30_000L);
            sdkHarness.startInk(rawSurface, status, duration, () -> finishSuite(suite));
            return;
        }
        worker.execute(() -> {
            ProbeRunner.inventory(this, recorder);
            if ("automated".equals(suite) || "base".equals(suite)) ProbeRunner.base(this, recorder);
            if ("mmkv-compat".equals(suite)) {
                if ("write-fixture".equals(mmkvMode)) {
                    MmkvCompatibility.writeFixture(this, recorder);
                } else {
                    MmkvCompatibility.verifyFixture(this, recorder);
                }
            }
            runOnUiThread(() -> {
                if (isDestroyed() || isFinishing()) return;
                if ("automated".equals(suite) || "device".equals(suite)) {
                    ProbeRunner.device(this, canvas, recorder);
                }
                if ("automated".equals(suite)) {
                    pen.replay(() -> finishSuite(suite));
                } else {
                    finishSuite(suite);
                }
            });
        });
    }

    private synchronized void finishSuite(String suite) {
        if (finished) return;
        try {
            recorder.markDone(this, suite);
            finished = true;
            status.setText("Complete: " + suite + " (" + BuildConfig.SDK_VARIANT + ")");
        } catch (IOException error) {
            status.setText("Could not finish: " + error);
        }
    }

    @Override
    protected void onDestroy() {
        if (pen != null) pen.quit();
        if (sdkHarness != null) sdkHarness.close();
        worker.shutdownNow();
        if (recorder != null) {
            try {
                recorder.close();
            } catch (IOException error) {
                Log.e(TAG, "Could not close validation result recorder", error);
            }
        }
        super.onDestroy();
    }
}
