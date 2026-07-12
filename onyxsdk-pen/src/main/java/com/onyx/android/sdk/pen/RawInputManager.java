/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.graphics.RectF
 *  android.view.View
 *  com.onyx.android.sdk.data.note.TouchPoint
 */
package com.onyx.android.sdk.pen;

import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.RawInputCallback;
import com.onyx.android.sdk.pen.RawInputReader;
import com.onyx.android.sdk.pen.data.TouchPointList;
import java.util.List;

public class RawInputManager {
    private RawInputCallback a;
    private RawInputReader b = null;
    private boolean c = true;

    private RawInputReader a() {
        if (this.b == null) {
            RawInputReader rawInputReader;
            RawInputReader rawInputReader2 = rawInputReader;
            rawInputReader = new RawInputReader();
            this.b = rawInputReader2;
        }
        return this.b;
    }

    /*
     * WARNING - void declaration
     */
    public void setRawInputCallback(RawInputCallback callback) {
        void var1_1;
        this.a = var1_1;
    }

    public void startRawInputReader() {
        if (!this.isUseRawInput()) {
            return;
        }
        RawInputManager rawInputManager = this;
        rawInputManager.a().setRawInputCallback(new RawInputCallback(this){
            final /* synthetic */ RawInputManager a;
            {
                void var1_1;
                this.a = var1_1;
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onBeginRawDrawing(boolean shortcut, TouchPoint point) {
                if (!this.a.isUseRawInput()) {
                    return;
                }
                if (this.a.a != null) {
                    void var2_2;
                    void var1_1;
                    this.a.a.onBeginRawDrawing((boolean)var1_1, (TouchPoint)var2_2);
                }
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onEndRawDrawing(boolean outLimitRegion, TouchPoint point) {
                if (!this.a.isUseRawInput()) {
                    return;
                }
                if (this.a.a != null) {
                    void var2_2;
                    void var1_1;
                    this.a.a.onEndRawDrawing((boolean)var1_1, (TouchPoint)var2_2);
                }
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onRawDrawingTouchPointMoveReceived(TouchPoint point) {
                if (!this.a.isUseRawInput()) {
                    return;
                }
                if (this.a.a != null) {
                    void var1_1;
                    this.a.a.onRawDrawingTouchPointMoveReceived((TouchPoint)var1_1);
                }
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onRawDrawingTouchPointListReceived(TouchPointList pointList) {
                if (!this.a.isUseRawInput()) {
                    return;
                }
                if (this.a.a != null) {
                    void var1_1;
                    this.a.a.onRawDrawingTouchPointListReceived((TouchPointList)var1_1);
                }
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onBeginRawErasing(boolean shortcut, TouchPoint point) {
                if (!this.a.isUseRawInput()) {
                    return;
                }
                if (this.a.a != null) {
                    void var2_2;
                    void var1_1;
                    this.a.a.onBeginRawErasing((boolean)var1_1, (TouchPoint)var2_2);
                }
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onEndRawErasing(boolean outLimitRegion, TouchPoint point) {
                if (!this.a.isUseRawInput()) {
                    return;
                }
                if (this.a.a != null) {
                    void var2_2;
                    void var1_1;
                    this.a.a.onEndRawErasing((boolean)var1_1, (TouchPoint)var2_2);
                }
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onRawErasingTouchPointMoveReceived(TouchPoint point) {
                if (!this.a.isUseRawInput()) {
                    return;
                }
                if (this.a.a != null) {
                    void var1_1;
                    this.a.a.onRawErasingTouchPointMoveReceived((TouchPoint)var1_1);
                }
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onRawErasingTouchPointListReceived(TouchPointList pointList) {
                if (!this.a.isUseRawInput()) {
                    return;
                }
                if (this.a.a != null) {
                    void var1_1;
                    this.a.a.onRawErasingTouchPointListReceived((TouchPointList)var1_1);
                }
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onPenActive(TouchPoint point) {
                if (!this.a.isUseRawInput()) {
                    return;
                }
                if (this.a.a != null) {
                    void var1_1;
                    this.a.a.onPenActive((TouchPoint)var1_1);
                }
            }

            /*
             * WARNING - void declaration
             */
            @Override
            public void onPenUpRefresh(RectF refreshRect) {
                if (!this.a.isUseRawInput()) {
                    return;
                }
                if (this.a.a != null) {
                    void var1_1;
                    this.a.a.onPenUpRefresh((RectF)var1_1);
                }
            }
        });
        rawInputManager.a().start();
    }

    public void resumeRawInputReader() {
        if (!this.isUseRawInput()) {
            return;
        }
        this.a().resume();
    }

    public void pauseRawInputReader() {
        if (!this.isUseRawInput()) {
            return;
        }
        this.a().pause();
    }

    public void quitRawInputReader() {
        if (!this.isUseRawInput()) {
            return;
        }
        this.a().quit();
    }

    public boolean isUseRawInput() {
        return this.c;
    }

    /*
     * WARNING - void declaration
     */
    public RawInputManager setUseRawInput(boolean use) {
        void var1_1;
        this.c = var1_1;
        return this;
    }

    public View getHostView() {
        return this.a().getHostView();
    }

    /*
     * WARNING - void declaration
     */
    public RawInputManager setHostView(View view) {
        Rect rect;
        void var1_1;
        RawInputManager rawInputManager = this_;
        this_.a().setHostView((View)var1_1);
        RawInputManager this_ = rect;
        var1_1.getLocalVisibleRect(new Rect());
        rawInputManager.a().setLimitRect((Rect)this_);
        return rawInputManager;
    }

    /*
     * WARNING - void declaration
     */
    public RawInputManager setLimitRect(Rect limitRect, List<Rect> excludeRectList) {
        void var2_2;
        void var1_1;
        RawInputManager rawInputManager = this;
        rawInputManager.a().setLimitRect((Rect)var1_1);
        rawInputManager.a().setExcludeRect((List<Rect>)var2_2);
        return rawInputManager;
    }

    /*
     * WARNING - void declaration
     */
    public RawInputManager setLimitRect(List<Rect> limitRect, List<Rect> excludeRectList) {
        void var2_2;
        void var1_1;
        RawInputManager rawInputManager = this;
        rawInputManager.a().setLimitRect((List<Rect>)var1_1);
        rawInputManager.a().setExcludeRect((List<Rect>)var2_2);
        return rawInputManager;
    }

    /*
     * WARNING - void declaration
     */
    public RawInputManager setLimitRect(List<Rect> limitRectList) {
        void var1_1;
        RawInputManager rawInputManager = this;
        rawInputManager.a().setLimitRect((List<Rect>)var1_1);
        return rawInputManager;
    }

    /*
     * WARNING - void declaration
     */
    public RawInputManager setExcludeRect(List<Rect> excludeRectList) {
        void var1_1;
        RawInputManager rawInputManager = this;
        rawInputManager.a().setExcludeRect((List<Rect>)var1_1);
        return rawInputManager;
    }

    /*
     * WARNING - void declaration
     */
    public void setStrokeWidth(float w) {
        void var1_1;
        this.a().setStrokeWidth((float)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    public void setStrokeColor(int color) {
        void var1_1;
        this.a().setStrokeColor((int)var1_1);
    }

    public void setSingleRegionMode() {
        this.a().setSingleRegionMode();
    }

    public void setMultiRegionMode() {
        this.a().setMultiRegionMode();
    }

    /*
     * WARNING - void declaration
     */
    public void setPenUpRefreshTimeMs(int penUpRefreshTimeMs) {
        void var1_1;
        this.a().setPenUpRefreshTimeMs((int)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    public void setPenUpRefreshEnabled(boolean enable) {
        void var1_1;
        this.a().setPenUpRefreshEnabled((boolean)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    public void setFilterRepeatMovePoint(boolean filter) {
        void var1_1;
        this.a().setFilterRepeatMovePoint((boolean)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    public void setPostInputEvent(boolean post) {
        void var1_1;
        this.a().setPostInputEvent((boolean)var1_1);
    }

    /*
     * WARNING - void declaration
     */
    public void enableSideBtnErase(boolean enable) {
        void var1_1;
        this.a().enableSideBtnErase((boolean)var1_1);
    }

    public void setHostViewScrollListenerEnabled(boolean enable) {
    }

    public void printTouchInfo() {
        RawInputReader this_ = ((RawInputManager)((Object)this_)).b;
        if (this_ != null) {
            this_.printTouchInfo();
        }
    }
}

