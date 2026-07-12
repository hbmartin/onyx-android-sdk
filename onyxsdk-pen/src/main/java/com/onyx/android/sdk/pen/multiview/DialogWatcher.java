package com.onyx.android.sdk.pen.multiview;

import android.app.Dialog;
import android.graphics.Rect;
import com.onyx.android.sdk.utils.Debug;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DialogWatcher extends BaseViewWatcher<Dialog> {
    private void a() {
        try {
            Iterator<WeakReference<Dialog>> it = getWatchedObjects().iterator();
            while (it.hasNext()) {
                Dialog dialog = it.next().get();
                if (dialog != null) {
                    dialog.dismiss();
                    Debug.w(getClass(), "dialog has leaked, safely dismiss dialog!", new Object[0]);
                }
            }
        } catch (Exception exception) {
            Debug.e(exception);
        }
    }

    @Override // com.onyx.android.sdk.pen.multiview.BaseViewWatcher
    public boolean hasVisibleObject() {
        Iterator<WeakReference<Dialog>> it = getWatchedObjects().iterator();
        while (it.hasNext()) {
            Dialog dialog = it.next().get();
            if (dialog != null && dialog.isShowing()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.onyx.android.sdk.pen.multiview.BaseViewWatcher
    public List<Rect> getRects() {
        return new ArrayList();
    }

    @Override // com.onyx.android.sdk.pen.multiview.BaseViewWatcher
    public void clear() {
        a();
        super.clear();
    }
}
