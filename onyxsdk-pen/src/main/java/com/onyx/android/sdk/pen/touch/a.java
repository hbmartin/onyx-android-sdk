package com.onyx.android.sdk.pen.touch;

import android.view.MotionEvent;
import io.reactivex.functions.Consumer;
import java.util.List;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/touch/a.class */
public final /* synthetic */ class a implements Consumer {
    public final AppTouchRender a;
    public final MotionEvent b;

    public /* synthetic */ a(AppTouchRender appTouchRender, MotionEvent motionEvent) {
        this.a = appTouchRender;
        this.b = motionEvent;
    }

    public final void accept(Object obj) {
        this.a.o(this.b, (List) obj);
    }
}

