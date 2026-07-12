/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.graphics.Rect
 *  android.view.View
 */
package com.onyx.android.sdk.pen.multiview;

import android.graphics.Rect;
import android.view.View;
import com.onyx.android.sdk.pen.multiview.BaseViewWatcher;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SubFloatMenuViewWatcher
extends BaseViewWatcher<View> {
    @Override
    public List<Rect> getRects() {
        return new ArrayList<Rect>();
    }

    @Override
    public boolean hasVisibleObject() {
        Iterator this_ = ((BaseViewWatcher)((Object)this_)).getWatchedObjects().iterator();
        while (this_.hasNext()) {
            View view = (View)((WeakReference)this_.next()).get();
            if (view == null || view.getParent() == null) continue;
            return true;
        }
        return false;
    }
}

