package com.onyx.android.sdk.pen.touch;

import android.view.MotionEvent;
import android.view.View;

public final /* synthetic */ class c implements View.OnTouchListener {
    public final AppTouchRender a;

    public c(AppTouchRender render) {
        this.a = render;
    }

    @Override // android.view.View.OnTouchListener
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        return this.a.k(view, motionEvent);
    }
}
