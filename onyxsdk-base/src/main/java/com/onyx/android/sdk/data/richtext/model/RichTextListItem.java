package com.onyx.android.sdk.data.richtext.model;

import com.onyx.android.sdk.utils.TTFFont;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u001f\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\u0010\b\u001a\u0004\u0018\u00010\t¢\u0006\u0002\u0010\nJ\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\fJ\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010\u001f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0005HÆ\u0003J\u0010\u0010!\u001a\u0004\u0018\u00010\tHÆ\u0003¢\u0006\u0002\u0010\u0017JJ\u0010\"\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tHÆ\u0001¢\u0006\u0002\u0010#J\u0013\u0010$\u001a\u00020\t2\b\u0010%\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010&\u001a\u00020\u0003HÖ\u0001J\t\u0010'\u001a\u00020\u0005HÖ\u0001R\u001e\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u000f\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0011\"\u0004\b\u0015\u0010\u0013R\u001e\u0010\b\u001a\u0004\u0018\u00010\tX\u0086\u000e¢\u0006\u0010\n\u0002\u0010\u001a\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0011\"\u0004\b\u001c\u0010\u0013¨\u0006("}, d2 = {"Lcom/onyx/android/sdk/data/richtext/model/RichTextListItem;", TTFFont.UNKNOWN_FONT_NAME, "blockIndent", TTFFont.UNKNOWN_FONT_NAME, "bulletSize", TTFFont.UNKNOWN_FONT_NAME, "bulletTop", "markerFontSize", "checked", TTFFont.UNKNOWN_FONT_NAME, "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;)V", "getBlockIndent", "()Ljava/lang/Integer;", "setBlockIndent", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "getBulletSize", "()Ljava/lang/String;", "setBulletSize", "(Ljava/lang/String;)V", "getBulletTop", "setBulletTop", "getChecked", "()Ljava/lang/Boolean;", "setChecked", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "getMarkerFontSize", "setMarkerFontSize", "component1", "component2", "component3", "component4", "component5", "copy", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Boolean;)Lcom/onyx/android/sdk/data/richtext/model/RichTextListItem;", "equals", "other", "hashCode", "toString", "onyxsdk-base_release"})
public final /* data */ class RichTextListItem {

    @Nullable
    private Integer blockIndent;

    @Nullable
    private String bulletSize;

    @Nullable
    private String bulletTop;

    @Nullable
    private String markerFontSize;

    @Nullable
    private Boolean checked;

    public RichTextListItem(@Nullable Integer blockIndent, @Nullable String bulletSize, @Nullable String bulletTop, @Nullable String markerFontSize, @Nullable Boolean checked) {
        this.blockIndent = blockIndent;
        this.bulletSize = bulletSize;
        this.bulletTop = bulletTop;
        this.markerFontSize = markerFontSize;
        this.checked = checked;
    }

    public static /* synthetic */ RichTextListItem copy$default(RichTextListItem richTextListItem, Integer num, String str, String str2, String str3, Boolean bool, int i, Object obj) {
        if ((i & 1) != 0) {
            num = richTextListItem.blockIndent;
        }
        if ((i & 2) != 0) {
            str = richTextListItem.bulletSize;
        }
        if ((i & 4) != 0) {
            str2 = richTextListItem.bulletTop;
        }
        if ((i & 8) != 0) {
            str3 = richTextListItem.markerFontSize;
        }
        if ((i & 16) != 0) {
            bool = richTextListItem.checked;
        }
        return richTextListItem.copy(num, str, str2, str3, bool);
    }

    @Nullable
    public final Integer getBlockIndent() {
        return this.blockIndent;
    }

    public final void setBlockIndent(@Nullable Integer num) {
        this.blockIndent = num;
    }

    @Nullable
    public final String getBulletSize() {
        return this.bulletSize;
    }

    public final void setBulletSize(@Nullable String str) {
        this.bulletSize = str;
    }

    @Nullable
    public final String getBulletTop() {
        return this.bulletTop;
    }

    public final void setBulletTop(@Nullable String str) {
        this.bulletTop = str;
    }

    @Nullable
    public final String getMarkerFontSize() {
        return this.markerFontSize;
    }

    public final void setMarkerFontSize(@Nullable String str) {
        this.markerFontSize = str;
    }

    @Nullable
    public final Boolean getChecked() {
        return this.checked;
    }

    public final void setChecked(@Nullable Boolean bool) {
        this.checked = bool;
    }

    @Nullable
    public final Integer component1() {
        return this.blockIndent;
    }

    @Nullable
    public final String component2() {
        return this.bulletSize;
    }

    @Nullable
    public final String component3() {
        return this.bulletTop;
    }

    @Nullable
    public final String component4() {
        return this.markerFontSize;
    }

    @Nullable
    public final Boolean component5() {
        return this.checked;
    }

    @NotNull
    public final RichTextListItem copy(@Nullable Integer blockIndent, @Nullable String bulletSize, @Nullable String bulletTop, @Nullable String markerFontSize, @Nullable Boolean checked) {
        return new RichTextListItem(blockIndent, bulletSize, bulletTop, markerFontSize, checked);
    }

    @NotNull
    public String toString() {
        return "RichTextListItem(blockIndent=" + this.blockIndent + ", bulletSize=" + ((Object) this.bulletSize) + ", bulletTop=" + ((Object) this.bulletTop) + ", markerFontSize=" + ((Object) this.markerFontSize) + ", checked=" + this.checked + ')';
    }

    public int hashCode() {
        Integer num = this.blockIndent;
        int iHashCode = (num == null ? 0 : num.hashCode()) * 31;
        String str = this.bulletSize;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.bulletTop;
        int iHashCode3 = (iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.markerFontSize;
        int iHashCode4 = (iHashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        Boolean bool = this.checked;
        return iHashCode4 + (bool == null ? 0 : bool.hashCode());
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RichTextListItem)) {
            return false;
        }
        RichTextListItem richTextListItem = (RichTextListItem) other;
        return Intrinsics.areEqual(this.blockIndent, richTextListItem.blockIndent) && Intrinsics.areEqual(this.bulletSize, richTextListItem.bulletSize) && Intrinsics.areEqual(this.bulletTop, richTextListItem.bulletTop) && Intrinsics.areEqual(this.markerFontSize, richTextListItem.markerFontSize) && Intrinsics.areEqual(this.checked, richTextListItem.checked);
    }
}
