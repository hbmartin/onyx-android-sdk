package com.onyx.android.sdk.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CommonExtraIntentReceiver extends DeviceReceiver.ExtraIntentReceiver {
    private List<BaseReceiver> a;

    public CommonExtraIntentReceiver(List<BaseReceiver> receivers) {
        this.a = receivers;
    }

    public static List<IntentFilter> getIntentFilters(List<BaseReceiver> receivers) {
        ArrayList arrayList = new ArrayList();
        Iterator<BaseReceiver> it = receivers.iterator();
        while (it.hasNext()) {
            CollectionUtils.safeAddAll(arrayList, it.next().getIntentFilterList());
        }
        return arrayList;
    }

    public static boolean processReceiverIntent(Context context, Intent intent, List<BaseReceiver> receivers) {
        if (!BroadcastHelper.isValidIntent(intent)) {
            return false;
        }
        boolean zProcessReceiverIntent = false;
        Iterator<BaseReceiver> it = receivers.iterator();
        while (it.hasNext()) {
            zProcessReceiverIntent |= it.next().processReceiverIntent(context, intent);
        }
        return zProcessReceiverIntent;
    }

    @Override // com.onyx.android.sdk.utils.DeviceReceiver.ExtraIntentReceiver
    @NonNull
    public List<IntentFilter> getIntentFilterList() {
        return getIntentFilters(this.a);
    }

    @Override // com.onyx.android.sdk.utils.DeviceReceiver.ExtraIntentReceiver
    public boolean onReceive(Context context, Intent intent) {
        return processReceiverIntent(context, intent, this.a);
    }
}
