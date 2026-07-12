/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  com.onyx.android.sdk.api.device.epd.EpdController
 *  com.onyx.android.sdk.api.device.epd.UpdateMode
 */
package com.onyx.android.sdk.pen;

import android.view.View;
import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.api.device.epd.UpdateMode;

public class EpdPenManager {
    public static final int PEN_STOP = 0;
    public static final int PEN_START = 1;
    public static final int PEN_DRAWING = 2;
    public static final int PEN_PAUSE = 3;
    public static final int PEN_ERASING = 4;
    private View a = null;

    /*
     * WARNING - void declaration
     */
    public static void moveTo(float x, float y, float strokeWidth) {
        void var2_2;
        void var1_1;
        EpdController.moveTo((float)x, (float)var1_1, (float)var2_2);
    }

    /*
     * WARNING - void declaration
     */
    public static void quadTo(float x, float y, UpdateMode updateMode) {
        void var2_2;
        void var1_1;
        EpdController.quadTo((float)x, (float)var1_1, (UpdateMode)var2_2);
    }

    public static void setStrokeColor(int color) {
        EpdController.setStrokeColor((int)color);
    }

    /*
     * WARNING - void declaration
     */
    public EpdPenManager setHostView(View view) {
        void var1_1;
        this.a = var1_1;
        return this;
    }

    public void startDrawing() {
        EpdController.setScreenHandWritingPenState((View)this.a, (int)1);
    }

    public void resumeDrawing() {
        EpdController.setScreenHandWritingPenState((View)this.a, (int)2);
    }

    public void pauseDrawing() {
        EpdController.setScreenHandWritingPenState((View)this.a, (int)3);
    }

    public void quitDrawing() {
        EpdController.setScreenHandWritingPenState((View)this.a, (int)0);
        this.a = null;
    }

    public void setStrokeStyle(int style) {
        EpdController.setStrokeStyle((int)style);
    }
}

