/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.View
 *  com.onyx.android.sdk.utils.ViewUtils
 */
package com.onyx.android.sdk.pen.multiview;

import android.graphics.Rect;
import android.view.View;
import com.onyx.android.sdk.pen.multiview.BaseViewWatcher;
import com.onyx.android.sdk.utils.ViewUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ViewWatcher
extends BaseViewWatcher<View> {
    private void a(View view, Rect rect) {
        Rect rect2;
        rect2 = ViewUtils.globalVisibleRect((View)rect2);
        rect.offsetTo(rect2.left - this.getContainerViewScreenX(), rect2.top - this.getContainerViewScreenY());
    }

    @Override
    public List<Rect> getRects() {
        ViewWatcher this_;
        ArrayList<Rect> arrayList;
        ArrayList arrayList2;
        ArrayList arrayList3 = arrayList2;
        arrayList2 = new ArrayList();
        ArrayList<Rect> arrayList4 = arrayList;
        arrayList = new ArrayList<Rect>();
        for (WeakReference weakReference : this_.getWatchedObjects()) {
            Rect rect;
            View view = (View)weakReference.get();
            if (view == null) {
                arrayList3.add(weakReference);
                continue;
            }
            if (!view.isShown()) continue;
            Rect rect2 = rect;
            rect = new Rect();
            view.getLocalVisibleRect(rect2);
            viewWatcher.a(view, rect2);
            arrayList4.add(rect2);
        }
        if (!arrayList3.isEmpty()) {
            viewWatcher.getWatchedObjects().removeAll(arrayList3);
        }
        if (arrayList4.isEmpty()) {
            Rect rect;
            ViewWatcher viewWatcher = rect;
            rect = new Rect(0, 0, 0, 0);
            arrayList4.add((Rect)viewWatcher);
        }
        return arrayList4;
    }

    @Override
    public boolean hasVisibleObject() {
        Iterator this_ = ((BaseViewWatcher)((Object)this_)).getWatchedObjects().iterator();
        while (this_.hasNext()) {
            View view = (View)((WeakReference)this_.next()).get();
            if (view == null || !view.isShown()) continue;
            return true;
        }
        return false;
    }
}

