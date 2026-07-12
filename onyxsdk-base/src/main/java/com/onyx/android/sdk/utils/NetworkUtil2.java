package com.onyx.android.sdk.utils;

import android.content.Context;
import android.content.Intent;
import com.onyx.android.sdk.api.utils.NetworkUtil;
import com.onyx.android.sdk.data.CommonConstant;

public class NetworkUtil2 {
    public static void openWifiActivity(Context context, boolean enableWhenStart) {
        Intent intent = new Intent("onyx.settings.action.wifi");
        intent.putExtra(BroadcastHelper.ARGS_ENABLE_WHEN_STARTED, enableWhenStart);
        ActivityUtil.startActivitySafely(context, intent);
    }

    public static boolean isValidLink(String link) {
        for (String str : NetworkUtil.schemeList) {
            if (StringUtils.startsWithIgnoreCase(link, str) && !StringUtils.safelyEqualsIgnoreCase(link, str)) {
                return true;
            }
        }
        return false;
    }

    public static String autoAppendScheme(String link) {
        for (String str : NetworkUtil.schemeList) {
            if (StringUtils.startsWithIgnoreCase(link, str)) {
                return link;
            }
        }
        return CommonConstant.SCHEME_HTTP + link;
    }
}
