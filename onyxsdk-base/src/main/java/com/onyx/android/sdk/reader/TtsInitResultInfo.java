package com.onyx.android.sdk.reader;

import android.os.Parcel;
import android.os.Parcelable;
import com.onyx.android.sdk.data.GAdapterUtil;
import com.onyx.android.sdk.utils.TTFFont;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u0000 \u001d2\u00020\u0001:\u0001\u001dB\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0005¢\u0006\u0002\u0010\u0005J\b\u0010\u0018\u001a\u00020\u0019H\u0016J\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001c\u001a\u00020\u0019H\u0016R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\t\"\u0004\b\u0014\u0010\u000bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\t\"\u0004\b\u0017\u0010\u000b¨\u0006\u001e"}, d2 = {"Lcom/onyx/android/sdk/reader/TtsInitResultInfo;", "Landroid/os/Parcelable;", "parcel", "Landroid/os/Parcel;", "(Landroid/os/Parcel;)V", "()V", "engine", TTFFont.UNKNOWN_FONT_NAME, "getEngine", "()Ljava/lang/String;", "setEngine", "(Ljava/lang/String;)V", "initSuccess", TTFFont.UNKNOWN_FONT_NAME, "getInitSuccess", "()Z", "setInitSuccess", "(Z)V", "label", "getLabel", "setLabel", "languageName", "getLanguageName", "setLanguageName", "describeContents", TTFFont.UNKNOWN_FONT_NAME, "writeToParcel", TTFFont.UNKNOWN_FONT_NAME, "flags", "CREATOR", "onyxsdk-base_release"})
public final class TtsInitResultInfo implements Parcelable {

    @NotNull
    public static final CREATOR CREATOR = new CREATOR(null);

    @Nullable
    private String engine;

    @Nullable
    private String languageName;
    private boolean initSuccess;

    @Nullable
    private String label;

    @Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0008\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0008\n\u0002\u0008\u0002\u0008\u0086\u0003\u0018\u00002\u0008\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\u0008\u0002\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u001d\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u00082\u0006\u0010\t\u001a\u00020\nH\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\u000c"}, d2 = {"Lcom/onyx/android/sdk/reader/TtsInitResultInfo$CREATOR;", "Landroid/os/Parcelable$Creator;", "Lcom/onyx/android/sdk/reader/TtsInitResultInfo;", "()V", "createFromParcel", "parcel", "Landroid/os/Parcel;", "newArray", "", "size", "", "(I)[Lcom/onyx/android/sdk/reader/TtsInitResultInfo;", "onyxsdk-base_release"})
    public static final class CREATOR implements Parcelable.Creator<TtsInitResultInfo> {
        private CREATOR() {
        }

        public /* synthetic */ CREATOR(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        @Override // android.os.Parcelable.Creator
        @NotNull
        public TtsInitResultInfo createFromParcel(@NotNull Parcel parcel) {
            Intrinsics.checkNotNullParameter(parcel, "parcel");
            return new TtsInitResultInfo(parcel);
        }

        @Override // android.os.Parcelable.Creator
        @NotNull
        public TtsInitResultInfo[] newArray(int size) {
            return new TtsInitResultInfo[size];
        }
    }

    public TtsInitResultInfo() {
    }

    @Nullable
    public final String getEngine() {
        return this.engine;
    }

    public final void setEngine(@Nullable String str) {
        this.engine = str;
    }

    @Nullable
    public final String getLanguageName() {
        return this.languageName;
    }

    public final void setLanguageName(@Nullable String str) {
        this.languageName = str;
    }

    public final boolean getInitSuccess() {
        return this.initSuccess;
    }

    public final void setInitSuccess(boolean z) {
        this.initSuccess = z;
    }

    @Nullable
    public final String getLabel() {
        return this.label;
    }

    public final void setLabel(@Nullable String str) {
        this.label = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(@NotNull Parcel parcel, int flags) {
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        parcel.writeString(this.engine);
        parcel.writeString(this.languageName);
        parcel.writeByte(this.initSuccess ? (byte) 1 : (byte) 0);
        parcel.writeString(this.label);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TtsInitResultInfo(@NotNull Parcel parcel) {
        this();
        Intrinsics.checkNotNullParameter(parcel, "parcel");
        this.engine = parcel.readString();
        this.languageName = parcel.readString();
        this.initSuccess = parcel.readByte() != 0;
        this.label = parcel.readString();
    }
}
