package com.onyx.recovery.validation;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
    private ResultRecorder recorder;
    private GuidedCanvasView canvas;
    private PenHarness pen;
    private TextView status;
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
        buildUi();
        pen = new PenHarness(this, recorder, canvas);
        String suite = getIntent().getStringExtra("suite");
        if (suite == null || suite.isEmpty()) suite = "automated";
        final String selected = suite;
        canvas.post(() -> runSuite(selected));
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
        addButton(controls, "Finish", () -> finishSuite("pen-live"));
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
            status.setText("Draw guided strokes now; capture ends in " + duration / 1000 + " seconds");
            pen.startLive(duration, () -> finishSuite(suite));
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

    private void finishSuite(String suite) {
        try {
            recorder.markDone(this, suite);
            status.setText("Complete: " + suite + " (" + BuildConfig.SDK_VARIANT + ")");
        } catch (IOException error) {
            status.setText("Could not finish: " + error);
        }
    }

    @Override
    protected void onDestroy() {
        if (pen != null) pen.quit();
        worker.shutdownNow();
        super.onDestroy();
    }
}
