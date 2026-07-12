package com.onyx.android.sdk.pen;

import android.view.View;
import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.api.device.epd.UpdateMode;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/EpdPenManager.class */
public class EpdPenManager {
    public static final int PEN_STOP = 0;
    public static final int PEN_START = 1;
    public static final int PEN_DRAWING = 2;
    public static final int PEN_PAUSE = 3;
    public static final int PEN_ERASING = 4;
    private View a = null;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void moveTo(float x, float y, float strokeWidth) {
        EpdController.moveTo(x, y, strokeWidth);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void quadTo(float x, float y, UpdateMode updateMode) {
        EpdController.quadTo(x, y, updateMode);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void setStrokeColor(int color) {
        EpdController.setStrokeColor(color);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public EpdPenManager setHostView(View view) {
        this.a = view;
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void startDrawing() {
        EpdController.setScreenHandWritingPenState(this.a, 1);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void resumeDrawing() {
        EpdController.setScreenHandWritingPenState(this.a, 2);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void pauseDrawing() {
        EpdController.setScreenHandWritingPenState(this.a, 3);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void quitDrawing() {
        EpdController.setScreenHandWritingPenState(this.a, 0);
        this.a = null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public void setStrokeStyle(int style) {
        EpdController.setStrokeStyle(style);
    }
}

