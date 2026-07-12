package com.onyx.android.sdk.pen.touch;

import com.onyx.android.sdk.data.note.TouchPoint;
import io.reactivex.functions.Consumer;

public final /* synthetic */ class b implements Consumer {
    public final AppTouchRender a;

    public b(AppTouchRender render) {
        this.a = render;
    }

    public final void accept(Object obj) {
        this.a.q((TouchPoint) obj);
    }
}
