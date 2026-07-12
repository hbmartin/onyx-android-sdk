package com.onyx.android.sdk.data.richtext;

import android.text.Layout;
import com.onyx.android.sdk.extension.StringKt;
import com.onyx.android.sdk.utils.TTFFont;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/richtext/TextAlignType.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0001\u0018\u0000 \n2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\nB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\u000b"}, d2 = {"Lcom/onyx/android/sdk/data/richtext/TextAlignType;", TTFFont.UNKNOWN_FONT_NAME, "value", TTFFont.UNKNOWN_FONT_NAME, "(Ljava/lang/String;ILjava/lang/String;)V", "getValue", "()Ljava/lang/String;", "JUSTIFY_LEFT", "JUSTIFY_RIGHT", "JUSTIFY_CENTER", "Companion", "onyxsdk-base_release"})
public enum TextAlignType {
    JUSTIFY_LEFT("left"),
    JUSTIFY_RIGHT("right"),
    JUSTIFY_CENTER("center");


    @NotNull
    public static final Companion Companion = new Companion(null);

    @NotNull
    private final String a;

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/richtext/TextAlignType$Companion.class */
    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\u0007\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\tJ\u000e\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\t¨\u0006\f"}, d2 = {"Lcom/onyx/android/sdk/data/richtext/TextAlignType$Companion;", TTFFont.UNKNOWN_FONT_NAME, "()V", "fromAlignment", "Lcom/onyx/android/sdk/data/richtext/TextAlignType;", "alignment", "Landroid/text/Layout$Alignment;", "fromValue", "value", TTFFont.UNKNOWN_FONT_NAME, "toAlignment", "alignType", "onyxsdk-base_release"})
    public static final class Companion {

        /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/richtext/TextAlignType$Companion$WhenMappings.class */
        @Metadata(mv = {1, 6, 0}, k = 3, xi = 48)
        public static final /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;
            public static final /* synthetic */ int[] $EnumSwitchMapping$1;

            private WhenMappings() {
            }

            static {
                int[] iArr = new int[Layout.Alignment.values().length];
                iArr[Layout.Alignment.ALIGN_NORMAL.ordinal()] = 1;
                iArr[Layout.Alignment.ALIGN_OPPOSITE.ordinal()] = 2;
                iArr[Layout.Alignment.ALIGN_CENTER.ordinal()] = 3;
                $EnumSwitchMapping$0 = iArr;
                int[] iArr2 = new int[TextAlignType.values().length];
                iArr2[TextAlignType.JUSTIFY_LEFT.ordinal()] = 1;
                iArr2[TextAlignType.JUSTIFY_RIGHT.ordinal()] = 2;
                iArr2[TextAlignType.JUSTIFY_CENTER.ordinal()] = 3;
                $EnumSwitchMapping$1 = iArr2;
            }
        }

        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @NotNull
        public final TextAlignType fromValue(@Nullable String value) {
            TextAlignType[] textAlignTypeArrValues = TextAlignType.values();
            int i = 0;
            int length = textAlignTypeArrValues.length;
            while (i < length) {
                TextAlignType textAlignType = textAlignTypeArrValues[i];
                i++;
                if (StringKt.safelyEqualsIgnoreCase(value, textAlignType.getValue())) {
                    return textAlignType;
                }
            }
            return TextAlignType.JUSTIFY_LEFT;
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: kotlin.NoWhenBranchMatchedException */
        @NotNull
        public final TextAlignType fromAlignment(@NotNull Layout.Alignment alignment) throws NoWhenBranchMatchedException {
            Intrinsics.checkNotNullParameter(alignment, "alignment");
            int i = WhenMappings.$EnumSwitchMapping$0[alignment.ordinal()];
            if (i == 1) {
                return TextAlignType.JUSTIFY_LEFT;
            }
            if (i == 2) {
                return TextAlignType.JUSTIFY_RIGHT;
            }
            if (i == 3) {
                return TextAlignType.JUSTIFY_CENTER;
            }
            throw new NoWhenBranchMatchedException();
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: kotlin.NoWhenBranchMatchedException */
        @NotNull
        public final Layout.Alignment toAlignment(@NotNull String alignType) throws NoWhenBranchMatchedException {
            Intrinsics.checkNotNullParameter(alignType, "alignType");
            int i = WhenMappings.$EnumSwitchMapping$1[fromValue(alignType).ordinal()];
            if (i == 1) {
                return Layout.Alignment.ALIGN_NORMAL;
            }
            if (i == 2) {
                return Layout.Alignment.ALIGN_OPPOSITE;
            }
            if (i == 3) {
                return Layout.Alignment.ALIGN_CENTER;
            }
            throw new NoWhenBranchMatchedException();
        }
    }

    TextAlignType(String value) {
        this.a = value;
    }

    @NotNull
    public final String getValue() {
        return this.a;
    }
}
