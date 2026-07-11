package com.onyx.android.sdk.pen;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Source-native Java bridge for the recovered Rust input driver.
 *
 * <p>The callback signature and all eleven native method names are retained
 * exactly because they are the ABI contract exported by the Rust library.</p>
 */
public final class RawInputReader implements AutoCloseable {
    private final ExecutorService readerExecutor = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "onyx-pen-raw-input");
        thread.setDaemon(true);
        return thread;
    });
    private volatile Listener listener = event -> {};
    private volatile Future<?> readerTask;

    static {
        System.loadLibrary("onyx_pen_touch_reader");
    }

    private native void nativeRawReader();

    private native void nativeRawClose();

    private native boolean nativeIsValid();

    private static native void nativeDebug(boolean enabled);

    private native void nativeSetStrokeWidth(float width);

    private native void nativeSetLimitRegion(float[] rectangles);

    private native void nativeSetExcludeRegion(float[] rectangles);

    private native void nativeSetPenState(int state);

    private native void nativePausePen();

    private native void nativeSetRegionMode(int mode);

    private native void nativeEnableSideBtnErase(boolean enabled);

    public static void debugLog(boolean enabled) {
        nativeDebug(enabled);
    }

    public void setListener(Listener listener) {
        this.listener = Objects.requireNonNull(listener, "listener");
    }

    public synchronized void start() {
        if (readerTask == null || readerTask.isDone()) {
            readerTask = readerExecutor.submit(this::nativeRawReader);
        }
    }

    public void resume() {
        nativeSetPenState(4);
    }

    public void pause() {
        nativePausePen();
    }

    public boolean isFdValid() {
        return nativeIsValid();
    }

    public void setStrokeWidth(float width) {
        nativeSetStrokeWidth(width);
    }

    public void setLimitRegions(float... rectangles) {
        nativeSetLimitRegion(rectangles.clone());
    }

    public void setExcludeRegions(float... rectangles) {
        nativeSetExcludeRegion(rectangles.clone());
    }

    public void setSingleRegionMode() {
        nativeSetRegionMode(1);
    }

    public void setMultiRegionMode() {
        nativeSetRegionMode(0);
    }

    public void enableSideButtonErase(boolean enabled) {
        nativeEnableSideBtnErase(enabled);
    }

    @Override
    public synchronized void close() {
        nativeRawClose();
        readerExecutor.shutdownNow();
        readerTask = null;
    }

    /** Called from JNI; keep the descriptor synchronized with the Rust bridge. */
    public void onTouchPointReceived(
            float x,
            float y,
            int pressure,
            int tiltX,
            int tiltY,
            boolean erasing,
            boolean shortcutDrawing,
            boolean shortcutErasing,
            int state,
            long timestampMicros) {
        listener.onPoint(new PenEvent(
                x,
                y,
                pressure,
                tiltX,
                tiltY,
                erasing,
                shortcutDrawing,
                shortcutErasing,
                state,
                timestampMicros));
    }

    @FunctionalInterface
    public interface Listener {
        void onPoint(PenEvent event);
    }

    public static final class PenEvent {
        private final float x;
        private final float y;
        private final int pressure;
        private final int tiltX;
        private final int tiltY;
        private final boolean erasing;
        private final boolean shortcutDrawing;
        private final boolean shortcutErasing;
        private final int state;
        private final long timestampMicros;

        public PenEvent(
                float x,
                float y,
                int pressure,
                int tiltX,
                int tiltY,
                boolean erasing,
                boolean shortcutDrawing,
                boolean shortcutErasing,
                int state,
                long timestampMicros) {
            this.x = x;
            this.y = y;
            this.pressure = pressure;
            this.tiltX = tiltX;
            this.tiltY = tiltY;
            this.erasing = erasing;
            this.shortcutDrawing = shortcutDrawing;
            this.shortcutErasing = shortcutErasing;
            this.state = state;
            this.timestampMicros = timestampMicros;
        }

        public float x() { return x; }
        public float y() { return y; }
        public int pressure() { return pressure; }
        public int tiltX() { return tiltX; }
        public int tiltY() { return tiltY; }
        public boolean erasing() { return erasing; }
        public boolean shortcutDrawing() { return shortcutDrawing; }
        public boolean shortcutErasing() { return shortcutErasing; }
        public int state() { return state; }
        public long timestampMicros() { return timestampMicros; }
    }
}
