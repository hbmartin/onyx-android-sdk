package com.onyx.android.sdk.utils;

import android.view.KeyEvent;
import com.onyx.android.sdk.common.event.NextPageEvent;
import com.onyx.android.sdk.common.event.PrevPageEvent;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/TurnPageUtils.class */
public class TurnPageUtils {
    public static boolean handleTurnPageKey(EventBus eventBus, KeyEvent event) {
        if (event.getAction() == 0) {
            int keyCode = event.getKeyCode();
            if (keyCode == 24 || keyCode == 25) {
                return !KeyCodeUtils.isSendByKeyboard(event);
            }
            return keyCode == 92 || keyCode == 93;
        }
        if (1 != event.getAction()) {
            return false;
        }
        int keyCode2 = event.getKeyCode();
        if (keyCode2 != 24) {
            if (keyCode2 != 25) {
                if (keyCode2 != 92) {
                    if (keyCode2 != 93) {
                        return false;
                    }
                }
            } else if (KeyCodeUtils.isSendByKeyboard(event)) {
                return false;
            }
            eventBus.post(new NextPageEvent());
            return true;
        }
        if (KeyCodeUtils.isSendByKeyboard(event)) {
            return false;
        }
        eventBus.post(new PrevPageEvent());
        return true;
    }
}
