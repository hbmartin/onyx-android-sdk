package com.onyx.recovery.validation;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class ValidationActivity extends Activity {
    private static final String TAG = "OnyxValidation";
    private ResultRecorder recorder;
    private GuidedCanvasView canvas;
    private PenHarness pen;
    private TextView status;
    private String suite;
    private String scenario;
    private boolean finished;
    private final ExecutorService worker = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
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
        buildUi();
        pen = new PenHarness(this, recorder, canvas);
        canvas.post(() -> runSuite(suite));
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
            status.setText("Draw now — " + label + "; capture ends in " + duration / 1000 + " seconds");
            pen.startLive(duration, scenario, () -> finishSuite(suite));
            return;
        }
        if ("pen-replay".equals(suite)) {
            pen.replay(() -> finishSuite(suite));
            return;
        }
        worker.execute(() -> {
            ProbeRunner.inventory(this, recorder);
            if ("automated".equals(suite) || "base".equals(suite)) ProbeRunner.base(this, recorder);
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
