package com.onyx.android.sdk.data.richtext.model;

import com.onyx.android.sdk.utils.LocaleUtils;
import com.onyx.android.sdk.utils.StringUtils;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/richtext/model/RichTextDirection.class */
public class RichTextDirection {
    public static final String TEXT_DIRECTION_LTR = "ltr";
    public static final String TEXT_DIRECTION_RTL = "rtl";
    public static final String TEXT_ORIENTATION_DEFAULT = "unset";
    public static final String TEXT_ORIENTATION_MIXED = "mixed";
    public static final String TEXT_ORIENTATION_UPRIGHT = "upright";
    public static final String TEXT_ORIENTATION_SIDEWAYS = "sideways";
    public static final String WRITING_MODE_DEFAULT = "unset";
    public static final String WRITING_MODE_HORIZONTAL_TB = "horizontal-tb";
    public static final String WRITING_MODE_VERTICAL_RL = "vertical-rl";
    public static final String WRITING_MODE_VERTICAL_LR = "vertical-lr";
    public static final String WRITING_MODE_SIDEWAYS_RL = "sideways-rl";
    public static final String WRITING_MODE_SIDEWAYS_LR = "sideways-lr";

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/richtext/model/RichTextDirection$Type.class */
    public enum Type {
        HORIZONTAL_TB(RichTextDirection.WRITING_MODE_HORIZONTAL_TB, "unset"),
        VERTICAL_LR(RichTextDirection.WRITING_MODE_VERTICAL_LR, RichTextDirection.TEXT_ORIENTATION_MIXED),
        VERTICAL_RL(RichTextDirection.WRITING_MODE_VERTICAL_RL, RichTextDirection.TEXT_ORIENTATION_MIXED),
        SIDEWAYS_LR(RichTextDirection.WRITING_MODE_VERTICAL_LR, RichTextDirection.TEXT_ORIENTATION_SIDEWAYS),
        SIDEWAYS_RL(RichTextDirection.WRITING_MODE_VERTICAL_RL, RichTextDirection.TEXT_ORIENTATION_SIDEWAYS);

        private String a;
        private String b;

        Type(String writingMode, String textOrientation) {
            this.a = writingMode;
            this.b = textOrientation;
        }

        public String getWritingMode() {
            return this.a;
        }

        public String getTextOrientation() {
            return this.b;
        }

        public boolean isVertical() {
            return this == VERTICAL_LR || this == VERTICAL_RL || this == SIDEWAYS_LR || this == SIDEWAYS_RL;
        }
    }

    public static String getDefaultWritingMode() {
        if (LocaleUtils.isCurrentLayoutDirectionVerticalLR()) {
            return WRITING_MODE_VERTICAL_LR;
        }
        return null;
    }

    public static String getDefaultDirection() {
        return LocaleUtils.isCurrentLayoutDirectionRTL() ? TEXT_DIRECTION_RTL : TEXT_DIRECTION_LTR;
    }

    public static Type getDefaultType() {
        return Type.HORIZONTAL_TB;
    }

    public static Type from(String writingMode, String textOrientation) {
        for (Type type : Type.values()) {
            if (StringUtils.safelyEquals(type.a, writingMode) && StringUtils.safelyEquals(type.b, textOrientation)) {
                return type;
            }
        }
        return getDefaultType();
    }
}
