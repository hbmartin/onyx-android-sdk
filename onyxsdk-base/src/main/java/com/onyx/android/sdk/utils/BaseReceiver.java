package com.onyx.android.sdk.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/BaseReceiver.class */
public abstract class BaseReceiver {
    public static final String DATA_SCHEME_PACKAGE = "package";
    public static final String DATA_SCHEME_FILE = "file";
    public static final String DATA_SCHEME_NONE = "none";
    private boolean a = true;
    private Map<String, List<String>> b = new HashMap();

    public BaseReceiver() {
        initActions();
    }

    private void a(String dataSchema, String action) {
        CollectionUtils.ensureList(this.b, dataSchema).add(action);
    }

    public boolean isEnabled() {
        return this.a;
    }

    public BaseReceiver setEnabled(boolean enabled) {
        this.a = enabled;
        return this;
    }

    protected abstract void initActions();

    public boolean isShowNoActionLog() {
        return true;
    }

    public void addPackageSchemaAction(String... actions) {
        addAction(DATA_SCHEME_PACKAGE, actions);
    }

    public void addFileSchemaAction(String... actions) {
        addAction("file", actions);
    }

    public void addNoneSchemaAction(String... actions) {
        addAction(DATA_SCHEME_NONE, actions);
    }

    public void addAction(String dataScheme, String... actions) {
        for (String str : actions) {
            a(dataScheme, str);
        }
    }

    public List<IntentFilter> getIntentFilterList() {
        ArrayList arrayList = new ArrayList();
        if (CollectionUtils.isNullOrEmpty(this.b)) {
            if (isShowNoActionLog()) {
                Debug.w(getClass(), "no action, no intent filter!", new Object[0]);
            }
            return arrayList;
        }
        for (String str : this.b.keySet()) {
            a(arrayList, a(str, this.b.get(str)));
        }
        return arrayList;
    }

    public boolean processReceiverIntent(Context context, Intent intent) {
        if (!isEnabled() || !BroadcastHelper.isValidIntent(intent) || CollectionUtils.isNullOrEmpty(this.b) || !a(intent.getAction())) {
            return false;
        }
        onReceive(context, intent);
        return true;
    }

    public abstract void onReceive(Context context, Intent intent);

    private void a(List<IntentFilter> intentFilterList, IntentFilter intentFilter) {
        if (intentFilter.countActions() > 0) {
            intentFilterList.add(intentFilter);
        }
    }

    private IntentFilter a(String scheme, List<String> actionList) {
        IntentFilter intentFilter = new IntentFilter();
        if (CollectionUtils.isNullOrEmpty(actionList)) {
            if (isShowNoActionLog()) {
                Debug.w(getClass(), "no action!", new Object[0]);
            }
            return intentFilter;
        }
        if (!StringUtils.safelyEquals(DATA_SCHEME_NONE, scheme)) {
            intentFilter.addDataScheme(scheme);
        }
        Iterator<String> it = actionList.iterator();
        while (it.hasNext()) {
            intentFilter.addAction(it.next());
        }
        return intentFilter;
    }

    private boolean a(String action) {
        Iterator<List<String>> it = this.b.values().iterator();
        while (it.hasNext()) {
            if (CollectionUtils.safelyContains(it.next(), action)) {
                return true;
            }
        }
        return false;
    }
}
