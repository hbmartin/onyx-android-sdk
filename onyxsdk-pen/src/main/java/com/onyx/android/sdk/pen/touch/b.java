package com.onyx.android.sdk.pen.touch;

import com.onyx.android.sdk.data.note.TouchPoint;
import io.reactivex.functions.Consumer;

/* JADX INFO: loaded from: onyxsdk-pen-native-classes.jar:com/onyx/android/sdk/pen/touch/b.class */
public final /* synthetic */ class b implements Consumer {
    public final AppTouchRender a;

    public b(AppTouchRender render) {
        this.a = render;
    }

    public final void accept(Object obj) {
        this.a.q((TouchPoint) obj);
    }
}
