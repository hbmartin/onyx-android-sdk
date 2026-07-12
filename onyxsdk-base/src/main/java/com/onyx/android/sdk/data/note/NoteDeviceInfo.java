package com.onyx.android.sdk.data.note;

import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.data.ReaderTextStyle;
import com.onyx.android.sdk.data.SizeF;
import com.onyx.android.sdk.utils.ResManager;
import com.onyx.android.sdk.utils.StringUtils;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/note/NoteDeviceInfo.class */
public class NoteDeviceInfo implements Serializable {
    public SizeF size;
    public String deviceName;

    public NoteDeviceInfo() {
        init();
    }

    public void init() {
        if (this.size == null) {
            this.size = new SizeF(ResManager.getShorterScreenSize(), ResManager.getLongerScreenSize());
        }
        if (StringUtils.isNullOrEmpty(this.deviceName)) {
            this.deviceName = Build.MODEL;
        }
    }

    @JSONField(serialize = false)
    public Rect getDeviceRect() {
        SizeF sizeF = this.size;
        return new Rect(0, 0, (int) sizeF.width, (int) sizeF.height);
    }

    @JSONField(serialize = false)
    public RectF getDeviceRectF() {
        SizeF sizeF = this.size;
        return new RectF(ReaderTextStyle.FONT_EMBOLDEN_NORMAL, ReaderTextStyle.FONT_EMBOLDEN_NORMAL, sizeF.width, sizeF.height);
    }
}
