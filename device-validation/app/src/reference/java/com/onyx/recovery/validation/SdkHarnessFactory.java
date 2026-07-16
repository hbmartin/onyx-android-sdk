package com.onyx.recovery.validation;

import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.view.SurfaceView;
import android.widget.TextView;

import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.api.device.epd.UpdateMode;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.RawInputCallback;
import com.onyx.android.sdk.pen.TouchHelper;
import com.onyx.android.sdk.pen.data.TouchPointList;

final class SdkHarnessFactory {
    private SdkHarnessFactory() {}

    static SdkHarness create(ValidationActivity activity, ResultRecorder recorder) {
        return new ReferenceAdapter(recorder);
    }

    private static final class ReferenceAdapter implements SdkHarness {
        private final ResultRecorder recorder;
        private final Handler handler = new Handler(Looper.getMainLooper());
        private TouchHelper activeHelper;

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
            closeInk();
            try {
                activeHelper = TouchHelper.create(surface, new RawInputCallback() {
                    @Override public void onBeginRawDrawing(boolean shortcut, TouchPoint point) {}
                    @Override public void onEndRawDrawing(boolean outside, TouchPoint point) {}
                    @Override public void onRawDrawingTouchPointMoveReceived(TouchPoint point) {}
                    @Override public void onRawDrawingTouchPointListReceived(TouchPointList points) {}
                    @Override public void onBeginRawErasing(boolean shortcut, TouchPoint point) {}
                    @Override public void onEndRawErasing(boolean outside, TouchPoint point) {}
                    @Override public void onRawErasingTouchPointMoveReceived(TouchPoint point) {}
                    @Override public void onRawErasingTouchPointListReceived(TouchPointList points) {}
                });
                activeHelper.openRawDrawing().setRawDrawingEnabled(true);
                status.setText("Reference flavor: legacy raw drawing overlay");
                handler.postDelayed(() -> {
                    closeInk();
                    finished.run();
                }, durationMs);
            } catch (Throwable failure) {
                closeInk();
                recorder.failure("sdk-facade", "reference_legacy_ink", failure);
                finished.run();
            }
        }

        @Override
        public void close() {
            handler.removeCallbacksAndMessages(null);
            closeInk();
        }

        private void closeInk() {
            if (activeHelper == null) return;
            TouchHelper helper = activeHelper;
            try {
                try {
                    helper.setRawDrawingEnabled(false);
                } catch (Throwable failure) {
                    recorder.failure("sdk-facade", "reference_legacy_ink_close", failure);
                }
            } finally {
                try {
                    helper.closeRawDrawing();
                } catch (Throwable failure) {
                    recorder.failure("sdk-facade", "reference_legacy_ink_close", failure);
                } finally {
                    activeHelper = null;
                }
            }
        }
    }
}
