package com.onyx.android.sdk.pen.multiview;

import android.graphics.Rect;
import android.widget.PopupWindow;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PopupWindowWatcher extends BaseViewWatcher<PopupWindow> {
    @Override // com.onyx.android.sdk.pen.multiview.BaseViewWatcher
    public boolean hasVisibleObject() {
        Iterator<WeakReference<PopupWindow>> it = getWatchedObjects().iterator();
        while (it.hasNext()) {
            PopupWindow popupWindow = it.next().get();
            if (popupWindow != null && popupWindow.isShowing()) {
                return true;
            }
        }
        return false;
    }

    @Override // com.onyx.android.sdk.pen.multiview.BaseViewWatcher
    public List<Rect> getRects() {
        return new ArrayList();
    }
}

