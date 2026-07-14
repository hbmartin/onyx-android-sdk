package com.onyx.recovery.validation;

import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.view.SurfaceView;
import android.widget.TextView;

import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.api.device.epd.UpdateMode;

final class SdkHarnessFactory {
    private SdkHarnessFactory() {}

    static SdkHarness create(ValidationActivity activity, ResultRecorder recorder) {
        return new ReferenceAdapter(recorder);
    }

    private static final class ReferenceAdapter implements SdkHarness {
        private final ResultRecorder recorder;
        private final Handler handler = new Handler(Looper.getMainLooper());

        ReferenceAdapter(ResultRecorder recorder) {
            this.recorder = recorder;
        }

        @Override
        public void runAutomated(SurfaceView surface, TextView status, Runnable finished) {
            try {
                UpdateMode before = EpdController.getViewDefaultUpdateMode(surface);
                EpdController.setViewDefaultUpdateMode(surface, UpdateMode.DU);
                EpdController.refreshScreenRegion(surface, 0, 0,
                        Math.max(1, surface.getWidth()), Math.max(1, surface.getHeight()), UpdateMode.DU);
                EpdController.setViewDefaultUpdateMode(surface, before);
                recorder.value("sdk-facade", "reference_legacy_adapter", null,
                        ResultRecorder.map("refresh", true, "modeRestored", before));
            } catch (Throwable failure) {
                recorder.failure("sdk-facade", "reference_legacy_adapter", failure);
            } finally {
                finished.run();
            }
        }

        @Override
        public void startInk(SurfaceView surface, TextView status, long durationMs, Runnable finished) {
            status.setText("Reference flavor: legacy surface refresh adapter");
            runAutomated(surface, status, () -> handler.postDelayed(finished, durationMs));
        }

        @Override
        public void close() {
            handler.removeCallbacksAndMessages(null);
        }
    }
}
