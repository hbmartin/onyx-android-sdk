/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.graphics.Rect
 *  com.onyx.android.sdk.utils.Debug
 */
package com.onyx.android.sdk.pen.multiview;

import android.app.Dialog;
import android.graphics.Rect;
import com.onyx.android.sdk.pen.multiview.BaseViewWatcher;
import com.onyx.android.sdk.utils.Debug;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DialogWatcher
extends BaseViewWatcher<Dialog> {
    private void a() {
        Iterator iterator;
        try {
            iterator = this.getWatchedObjects().iterator();
        }
        catch (Exception exception) {
            Debug.e((Throwable)exception);
        }
        while (true) {
            if (!iterator.hasNext()) break;
            Dialog dialog = (Dialog)iterator.next().get();
            if (dialog == null) continue;
            dialog.dismiss();
            Class<?> clazz = this.getClass();
            Debug.w(clazz, (String)"dialog has leaked, safely dismiss dialog!", (Object[])new Object[0]);
            continue;
            break;
        }
    }

    @Override
    public boolean hasVisibleObject() {
        Iterator this_ = ((BaseViewWatcher)((Object)this_)).getWatchedObjects().iterator();
        while (this_.hasNext()) {
            Dialog dialog = (Dialog)((WeakReference)this_.next()).get();
            if (dialog == null || !dialog.isShowing()) continue;
            return true;
        }
        return false;
    }

    @Override
    public List<Rect> getRects() {
        return new ArrayList<Rect>();
    }

    @Override
    public void clear() {
        DialogWatcher dialogWatcher = this;
        dialogWatcher.a();
        super.clear();
    }
}

