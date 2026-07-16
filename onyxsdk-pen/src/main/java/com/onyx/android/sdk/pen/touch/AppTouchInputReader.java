package com.onyx.android.sdk.pen.touch;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.data.PenConstant;
import com.onyx.android.sdk.data.note.TouchPoint;
import com.onyx.android.sdk.pen.data.TouchPointList;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.TouchUtils;
import java.util.ArrayList;
import java.util.List;

public class AppTouchInputReader {
    private static final int k = 0;
    private static final int l = 1;
    private static final int m = 2;
    private static final int n = 3;
    private static final int o = 5;
    private static final int p = 0;
    private static final int q = 1;
    private static final int r = 2;
    private TouchPointList c;
    private AppInputCallback f;
    private boolean i;
    private List<Rect> a = new ArrayList();
    private List<Rect> b = new ArrayList();
    private float d = PenConstant.DEFAULT_STROKE_WIDTH;
    private int e = 0;
    private boolean g = true;
    private boolean h = false;
    private float j = 1500.0f;
    private int regionMode = p;
    private int lastRegionHit = -1;
    private boolean sideButtonEraseEnabled = true;
    private boolean currentErasing;
    private boolean currentShortcutErasing;

    public AppTouchInputReader(@NonNull AppInputCallback callback) {
        this.f = callback;
    }

    private boolean c(float px, float py) {
        int hit = b(px, py);
        if (!CollectionUtils.isNullOrEmpty(this.a) && hit < 0) {
            return false;
        }
        if (this.regionMode != p && !CollectionUtils.isNullOrEmpty(this.a)) {
            if (this.lastRegionHit < 0) {
                this.lastRegionHit = hit;
            } else if (this.regionMode != q || this.lastRegionHit != hit) {
                return false;
            }
        }
        return !a(px, py);
    }

    private int b(float x, float y) {
        if (CollectionUtils.isNullOrEmpty(this.a)) {
            return -1;
        }
        float margin = this.d / 2.0f;
        for (int index = 0; index < this.a.size(); index++) {
            Rect rect = this.a.get(index);
            if (isStrokeContained(
                    rect.left, rect.top, rect.right, rect.bottom, x, y, margin)) {
                return index;
            }
        }
        return -1;
    }

    private boolean a(float x, float y) {
        if (CollectionUtils.isNullOrEmpty(this.b)) {
            return false;
        }
        float margin = this.d / 2.0f;
        for (Rect rect : this.b) {
            if (doesStrokeIntersect(
                    rect.left, rect.top, rect.right, rect.bottom, x, y, margin)) {
                return true;
            }
        }
        return false;
    }

    private boolean e(MotionEvent event) {
        this.e = k;
        this.lastRegionHit = -1;
        this.currentErasing = false;
        this.currentShortcutErasing = false;
        if (!c(event.getX(), event.getY()) || a(event)) {
            return false;
        }
        c(event);
        return true;
    }

    private void f(MotionEvent event) {
        try {
            if (a(event) || this.e == k) {
                return;
            }
            a(event, m);
        } finally {
            this.e = k;
            this.lastRegionHit = -1;
            this.currentErasing = false;
            this.currentShortcutErasing = false;
        }
    }

    private void d(MotionEvent event) {
        TouchPoint touchPointCreate = TouchPoint.create(event);
        b(touchPointCreate);
        this.f.onPenActive(touchPointCreate);
    }

    private void g(MotionEvent event) {
        a();
        TouchPoint touchPointCreate = TouchPoint.create(event);
        b(touchPointCreate);
        this.currentShortcutErasing = isSideButtonErasing(
                this.sideButtonEraseEnabled, event.getButtonState());
        this.currentErasing = TouchUtils.isEraserTouchType(event)
                || this.currentShortcutErasing;
        if (a(touchPointCreate)) {
            if (this.currentErasing) {
                this.f.onBeginRawErasing(this.currentShortcutErasing, touchPointCreate);
            } else {
                this.f.onBeginRawDrawing(event, false, touchPointCreate);
            }
        }
    }

    public AppTouchInputReader setStrokeWidth(float width) {
        this.d = width;
        return this;
    }

    public float getStrokeWidth() {
        return this.d;
    }

    public void setLimitRectList(View hostView, List<Rect> limitRectList) {
        CollectionUtils.safeAddAll(this.a, limitRectList, true);
        this.lastRegionHit = -1;
        EpdController.setScreenHandWritingRegionLimit(hostView, (Rect[]) limitRectList.toArray(new Rect[0]));
    }

    public void setExcludeRectList(View hostView, List<Rect> excludeRectList) {
        if (excludeRectList == null) {
            return;
        }
        CollectionUtils.safeAddAll(this.b, excludeRectList, true);
        EpdController.setScreenHandWritingRegionExclude(hostView, (Rect[]) excludeRectList.toArray(new Rect[0]));
    }

    public void setEnableFingerTouch(boolean enableFingerTouch) {
        this.g = enableFingerTouch;
    }

    public void setOnlyEnableFingerTouch(boolean onlyEnableFingerTouch) {
        this.h = onlyEnableFingerTouch;
    }

    public void setFingerTouchPressure(float fingerTouchPressure) {
        this.j = fingerTouchPressure;
    }

    public void setEnableFingerTouchPressure(boolean enableFingerTouchPressure) {
        this.i = enableFingerTouchPressure;
    }

    void setSingleRegionMode() {
        this.regionMode = q;
        this.lastRegionHit = -1;
    }

    void setMultiRegionMode() {
        this.regionMode = p;
        this.lastRegionHit = -1;
    }

    void enableSideBtnErase(boolean enable) {
        this.sideButtonEraseEnabled = enable;
    }

    public boolean processMotionEvent(MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                return e(event);
            case 1:
            case 3:
            case 6:
                f(event);
                return true;
            case 2:
                c(event);
                return true;
            case 4:
            case 5:
            default:
                return true;
        }
    }

    private void c(MotionEvent event) {
        if (a(event)) {
            return;
        }
        int i = c(event.getX(), event.getY()) ? 1 : 2;
        int i2 = this.e;
        if ((i2 == 0 && i == 1) || (i2 == 2 && i == 1)) {
            a(event, 0);
        } else if (i2 == 1) {
            if (i == 2) {
                a(event, 3);
            } else {
                a(event, 1);
            }
        }
        this.e = i;
    }

    private void b(MotionEvent event) {
        int historySize = event.getHistorySize();
        for (int i = 0; i < historySize; i++) {
            TouchPoint touchPoint = new TouchPoint(event.getHistoricalX(i), event.getHistoricalY(i), event.getHistoricalPressure(i), event.getHistoricalSize(i), event.getHistoricalEventTime(i));
            b(touchPoint);
            a(touchPoint);
            a(touchPoint, this.currentErasing);
        }
        TouchPoint touchPointCreate = TouchPoint.create(event);
        b(touchPointCreate);
        a(touchPointCreate);
        a(touchPointCreate, this.currentErasing);
    }

    private boolean a(MotionEvent event) {
        if (this.g) {
            return this.h && TouchUtils.isPenTouchType(event);
        }
        return !TouchUtils.isPenTouchType(event);
    }

    private void a(MotionEvent event, int state) {
        if (state == 0) {
            g(event);
            return;
        }
        if (state == 1) {
            b(event);
            return;
        }
        if (state == 2 || state == 3) {
            a(event, state == 3);
        } else if (state == 5) {
            d(event);
        }
    }

    private void b(TouchPoint touchPoint) {
        if (this.i) {
            touchPoint.setPressure(this.j);
        }
    }

    private void a(MotionEvent event, boolean releaseOutLimitRegion) {
        boolean zIsEraserTouchType = this.currentErasing;
        TouchPoint touchPointCreate = TouchPoint.create(event);
        b(touchPointCreate);
        TouchPointList touchPointList = this.c;
        if (touchPointList != null && touchPointList.size() > 0) {
            if (zIsEraserTouchType) {
                this.f.onRawErasingTouchPointListReceived(this.c);
            } else {
                this.f.onRawDrawingTouchPointListReceived(event, this.c);
            }
        }
        a();
        if (zIsEraserTouchType) {
            this.f.onEndRawErasing(releaseOutLimitRegion, touchPointCreate);
        } else {
            this.f.onEndRawDrawing(event, releaseOutLimitRegion, touchPointCreate);
        }
    }

    private void a(TouchPoint touchPoint, boolean erasing) {
        if (erasing) {
            this.f.onRawErasingTouchPointMoveReceived(touchPoint);
        } else {
            this.f.onRawDrawingTouchPointMoveReceived(touchPoint);
        }
    }

    private void a() {
        this.c = null;
    }

    private boolean a(TouchPoint touchPoint) {
        if (this.c == null) {
            this.c = new TouchPointList(600);
        }
        if (touchPoint == null) {
            return true;
        }
        this.c.add(touchPoint);
        return true;
    }

    static boolean isStrokeContained(
            float left,
            float top,
            float right,
            float bottom,
            float x,
            float y,
            float margin) {
        return x - margin >= left
                && x + margin <= right
                && y - margin >= top
                && y + margin <= bottom;
    }

    static boolean doesStrokeIntersect(
            float left,
            float top,
            float right,
            float bottom,
            float x,
            float y,
            float margin) {
        return x + margin >= left
                && x - margin <= right
                && y + margin >= top
                && y - margin <= bottom;
    }

    static boolean isSideButtonErasing(boolean enabled, int buttonState) {
        return enabled && (buttonState & MotionEvent.BUTTON_STYLUS_PRIMARY) != 0;
    }
}
