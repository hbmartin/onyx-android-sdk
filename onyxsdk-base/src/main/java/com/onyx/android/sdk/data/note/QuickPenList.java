package com.onyx.android.sdk.data.note;

import com.onyx.android.sdk.utils.StringUtils;
import io.reactivex.annotations.Nullable;
import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/note/QuickPenList.class */
public class QuickPenList implements Serializable {
    private List<PenArgs> a = new CopyOnWriteArrayList();
    private String b;

    public List<PenArgs> getQuickPens() {
        return this.a;
    }

    public QuickPenList setQuickPens(List<PenArgs> quickPens) {
        this.a = quickPens;
        return this;
    }

    public String getSelectedId() {
        return this.b;
    }

    public QuickPenList setSelectedId(String selectedId) {
        this.b = selectedId;
        return this;
    }

    @Nullable
    public PenArgs loadPenArgsById(String penId) {
        for (PenArgs penArgs : this.a) {
            if (StringUtils.safelyEquals(penArgs.getId(), penId)) {
                return penArgs;
            }
        }
        return null;
    }
}
