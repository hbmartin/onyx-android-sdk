package com.onyx.android.sdk.data;

import android.util.Pair;
import com.onyx.android.sdk.utils.TTFFont;
import java.util.HashMap;
import java.util.Map;

public class TouchBinding {
    public static final String TOUCH_LEFT_TOP = "TOUCH_LEFT_TOP";
    public static final String TOUCH_LEFT_BOTTOM = "TOUCH_LEFT_BOTTOM";
    public static final String TOUCH_RIGHT_TOP = "TOUCH_RIGHT_TOP";
    public static final String TOUCH_RIGHT_BOTTOM = "TOUCH_RIGHT_BOTTOM";
    public static final String TOUCH_CENTER = "TOUCH_CENTER";
    private static TouchBinding c;
    private Map<String, CustomBindKeyBean> a = new HashMap();
    private Pair<String, CustomBindKeyBean>[] b = new Pair[]{new Pair<>(TOUCH_LEFT_TOP, CustomBindKeyBean.createKeyBean(TTFFont.UNKNOWN_FONT_NAME, "prevPage")), new Pair<>(TOUCH_LEFT_BOTTOM, CustomBindKeyBean.createKeyBean(TTFFont.UNKNOWN_FONT_NAME, "prevPage")), new Pair<>(TOUCH_RIGHT_TOP, CustomBindKeyBean.createKeyBean(TTFFont.UNKNOWN_FONT_NAME, "nextPage")), new Pair<>(TOUCH_RIGHT_BOTTOM, CustomBindKeyBean.createKeyBean(TTFFont.UNKNOWN_FONT_NAME, "nextPage")), new Pair<>(TOUCH_CENTER, CustomBindKeyBean.createKeyBean(TTFFont.UNKNOWN_FONT_NAME, "showMenu"))};

    public static TouchBinding defaultValue() {
        if (c == null) {
            TouchBinding touchBinding = new TouchBinding();
            c = touchBinding;
            touchBinding.useDefaultValue();
        }
        return c;
    }

    public void useDefaultValue() {
        for (Pair<String, CustomBindKeyBean> pair : this.b) {
            this.a.put((String) pair.first, (CustomBindKeyBean) pair.second);
        }
    }

    public Map<String, CustomBindKeyBean> getBindingMap() {
        return this.a;
    }

    public void setBindingMap(Map<String, CustomBindKeyBean> bindingMap) {
        this.a = bindingMap;
    }
}
