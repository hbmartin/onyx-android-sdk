package com.onyx.android.sdk.data;

import android.util.Pair;
import android.view.KeyEvent;
import com.onyx.android.sdk.utils.TTFFont;
import java.util.HashMap;
import java.util.Map;

public class KeyBinding {
    private static KeyBinding c;
    private Pair<String, CustomBindKeyBean>[] a = new Pair[]{new Pair<>(KeyEvent.keyCodeToString(93), CustomBindKeyBean.createKeyBean(TTFFont.UNKNOWN_FONT_NAME, KeyAction.NEXT_SCREEN)), new Pair<>(KeyEvent.keyCodeToString(92), CustomBindKeyBean.createKeyBean(TTFFont.UNKNOWN_FONT_NAME, KeyAction.PREV_SCREEN)), new Pair<>(KeyEvent.keyCodeToString(25), CustomBindKeyBean.createKeyBean(TTFFont.UNKNOWN_FONT_NAME, KeyAction.NEXT_SCREEN)), new Pair<>(KeyEvent.keyCodeToString(24), CustomBindKeyBean.createKeyBean(TTFFont.UNKNOWN_FONT_NAME, KeyAction.PREV_SCREEN)), new Pair<>(KeyEvent.keyCodeToString(22), CustomBindKeyBean.createKeyBean(TTFFont.UNKNOWN_FONT_NAME, KeyAction.MOVE_RIGHT)), new Pair<>(KeyEvent.keyCodeToString(21), CustomBindKeyBean.createKeyBean(TTFFont.UNKNOWN_FONT_NAME, KeyAction.MOVE_LEFT)), new Pair<>(KeyEvent.keyCodeToString(19), CustomBindKeyBean.createKeyBean(TTFFont.UNKNOWN_FONT_NAME, KeyAction.MOVE_UP)), new Pair<>(KeyEvent.keyCodeToString(20), CustomBindKeyBean.createKeyBean(TTFFont.UNKNOWN_FONT_NAME, KeyAction.MOVE_DOWN)), new Pair<>(KeyEvent.keyCodeToString(82), CustomBindKeyBean.createKeyBean(TTFFont.UNKNOWN_FONT_NAME, "showMenu")), new Pair<>(KeyEvent.keyCodeToString(23), CustomBindKeyBean.createKeyBean(TTFFont.UNKNOWN_FONT_NAME, KeyAction.TOGGLE_BOOKMARK)), new Pair<>(KeyEvent.keyCodeToString(28), CustomBindKeyBean.createKeyBean(TTFFont.UNKNOWN_FONT_NAME, KeyAction.CHANGE_TO_ERASE_MODE)), new Pair<>(KeyEvent.keyCodeToString(57), CustomBindKeyBean.createKeyBean(TTFFont.UNKNOWN_FONT_NAME, KeyAction.CHANGE_TO_SCRIBBLE_MODE)), new Pair<>(KeyEvent.keyCodeToString(58), CustomBindKeyBean.createKeyBean(TTFFont.UNKNOWN_FONT_NAME, KeyAction.CHANGE_TO_SCRIBBLE_MODE)), new Pair<>(KeyEvent.keyCodeToString(108), CustomBindKeyBean.createKeyBean(TTFFont.UNKNOWN_FONT_NAME, KeyAction.CHANGE_TO_SCRIBBLE_MODE)), new Pair<>(KeyEvent.keyCodeToString(4), CustomBindKeyBean.createKeyBean(TTFFont.UNKNOWN_FONT_NAME, KeyAction.CLOSE))};
    private Map<String, CustomBindKeyBean> b = new HashMap();

    private void a() {
        for (Pair<String, CustomBindKeyBean> pair : this.a) {
            this.b.put((String) pair.first, (CustomBindKeyBean) pair.second);
        }
    }

    public static KeyBinding defaultValue() {
        if (c == null) {
            KeyBinding keyBinding = new KeyBinding();
            c = keyBinding;
            keyBinding.a();
        }
        return c;
    }

    public Map<String, CustomBindKeyBean> getHandlerManager() {
        return this.b;
    }

    public void setHandlerManager(Map<String, CustomBindKeyBean> handlerManager) {
        this.b = handlerManager;
    }
}
