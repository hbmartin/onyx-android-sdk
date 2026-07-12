package com.onyx.android.sdk.pen.multiview;

import android.graphics.Rect;
import android.view.View;
import com.onyx.android.sdk.utils.ViewUtils;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ViewWatcher extends BaseViewWatcher<View> {
    private void a(View view, Rect rect) {
        Rect rectGlobalVisibleRect = ViewUtils.globalVisibleRect(view);
        rect.offsetTo(rectGlobalVisibleRect.left - getContainerViewScreenX(), rectGlobalVisibleRect.top - getContainerViewScreenY());
    }

    @Override // com.onyx.android.sdk.pen.multiview.BaseViewWatcher
    public List<Rect> getRects() {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (WeakReference<View> weakReference : getWatchedObjects()) {
            View view = weakReference.get();
            if (view == null) {
                arrayList.add(weakReference);
            } else if (view.isShown()) {
                Rect rect = new Rect();
                view.getLocalVisibleRect(rect);
                a(view, rect);
                arrayList2.add(rect);
            }
        }
        if (!arrayList.isEmpty()) {
            getWatchedObjects().removeAll(arrayList);
        }
        if (arrayList2.isEmpty()) {
            arrayList2.add(new Rect(0, 0, 0, 0));
        }
        return arrayList2;
    }

    @Override // com.onyx.android.sdk.pen.multiview.BaseViewWatcher
    public boolean hasVisibleObject() {
        Iterator<WeakReference<View>> it = getWatchedObjects().iterator();
        while (it.hasNext()) {
            View view = it.next().get();
            if (view != null && view.isShown()) {
                return true;
            }
        }
        return false;
    }
}

