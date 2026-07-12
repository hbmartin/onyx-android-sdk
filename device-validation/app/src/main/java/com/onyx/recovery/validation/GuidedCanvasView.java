package com.onyx.recovery.validation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

final class GuidedCanvasView extends View {
    private final Paint guide = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint ink = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint erase = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path inkPath = new Path();
    private final Path erasePath = new Path();
    private boolean hasInk;
    private boolean hasErase;

    GuidedCanvasView(Context context) {
        super(context);
        setBackgroundColor(Color.WHITE);
        guide.setColor(Color.LTGRAY);
        guide.setStrokeWidth(2f);
        guide.setStyle(Paint.Style.STROKE);
        ink.setColor(Color.BLACK);
        ink.setStrokeWidth(4f);
        ink.setStyle(Paint.Style.STROKE);
        erase.setColor(Color.RED);
        erase.setStrokeWidth(4f);
        erase.setStyle(Paint.Style.STROKE);
    }

    synchronized void addPoint(float x, float y, boolean erasing) {
        Path path = erasing ? erasePath : inkPath;
        boolean has = erasing ? hasErase : hasInk;
        if (has) path.lineTo(x, y); else path.moveTo(x, y);
        if (erasing) hasErase = true; else hasInk = true;
        postInvalidate();
    }

    synchronized void clearPaths() {
        inkPath.reset();
        erasePath.reset();
        hasInk = false;
        hasErase = false;
        invalidate();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float w = getWidth();
        float h = getHeight();
        canvas.drawRect(w * .1f, h * .1f, w * .9f, h * .9f, guide);
        canvas.drawRect(w * .35f, h * .35f, w * .65f, h * .65f, guide);
        canvas.drawLine(w * .1f, h * .2f, w * .9f, h * .2f, guide);
        canvas.drawLine(w * .1f, h * .8f, w * .9f, h * .8f, guide);
        canvas.drawPath(inkPath, ink);
        canvas.drawPath(erasePath, erase);
    }
}
