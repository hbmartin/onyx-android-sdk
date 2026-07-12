/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.widget.PopupWindow
 */
package com.onyx.android.sdk.pen.multiview;

import android.graphics.Rect;
import android.widget.PopupWindow;
import com.onyx.android.sdk.pen.multiview.BaseViewWatcher;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PopupWindowWatcher
extends BaseViewWatcher<PopupWindow> {
    @Override
    public boolean hasVisibleObject() {
        Iterator this_ = ((BaseViewWatcher)((Object)this_)).getWatchedObjects().iterator();
        while (this_.hasNext()) {
            PopupWindow popupWindow = (PopupWindow)((WeakReference)this_.next()).get();
            if (popupWindow == null || !popupWindow.isShowing()) continue;
            return true;
        }
        return false;
    }

    @Override
    public List<Rect> getRects() {
        return new ArrayList<Rect>();
    }
}

