package com.onyx.android.sdk.api.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/api/utils/ActivityUtil.class */
public class ActivityUtil {
    private static final String TAG = "ActivityUtil";

    public static boolean startActivitySafely(Context from, Class<?> activityCls) {
        return startActivitySafely(from, new Intent(from, activityCls));
    }

    public static boolean startActivitySafely(Context from, Intent intent) {
        try {
            from.startActivity(intent);
            return true;
        } catch (Throwable th) {
            Log.e(TAG, "", th);
            return false;
        }
    }
}
