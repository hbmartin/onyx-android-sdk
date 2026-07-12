package com.onyx.android.sdk.utils;

import android.content.Context;
import android.content.Intent;

public class MockKeyEventUtil {
    private static final String a = "onyx.android.intent.send.key.event";
    private static final String b = "key_code";
    private static final String c = "source";

    public static void sendMockKeyEvent(Context context, int keycode) {
        Intent intent = new Intent(a);
        intent.putExtra(b, keycode);
        intent.putExtra(c, 0);
        context.sendBroadcast(intent);
    }
}
