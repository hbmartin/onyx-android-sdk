package com.onyx.android.sdk.pen.multiview;

import android.graphics.Rect;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/pen/multiview/SubFloatMenuViewWatcher.class */
public class SubFloatMenuViewWatcher extends BaseViewWatcher<View> {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.multiview.BaseViewWatcher
    public List<Rect> getRects() {
        return new ArrayList();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.onyx.android.sdk.pen.multiview.BaseViewWatcher
    public boolean hasVisibleObject() {
        Iterator<WeakReference<View>> it = getWatchedObjects().iterator();
        while (it.hasNext()) {
            View view = it.next().get();
            if (view != null && view.getParent() != null) {
                return true;
            }
        }
        return false;
    }
}

