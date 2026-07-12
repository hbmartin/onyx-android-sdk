// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.reader;

import android.os.Parcelable.Creator;
import android.os.Parcel;
import com.onyx.android.sdk.utils.StringUtils;
import com.onyx.android.sdk.data.ReaderPositionImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.JvmStatic;
import org.jetbrains.annotations.Nullable;
import kotlin.jvm.internal.Intrinsics;
import com.onyx.android.sdk.api.ReaderPosition;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;
import android.os.Parcelable;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u001b2\u00020\u0001:\u0001\u001bB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0014\u001a\u00020\u0003J\b\u0010\u0015\u001a\u00020\u0006H\u0016J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0006H\u0016R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\nR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013¨\u0006\u001c" }, d2 = { "Lcom/onyx/android/sdk/reader/TtsReaderPosition;", "Landroid/os/Parcelable;", "readerPosition", "Lcom/onyx/android/sdk/api/ReaderPosition;", "(Lcom/onyx/android/sdk/api/ReaderPosition;)V", "intPosition", "", "getIntPosition", "()I", "setIntPosition", "(I)V", "pageIndex", "getPageIndex", "setPageIndex", "strPosition", "", "getStrPosition", "()Ljava/lang/String;", "setStrPosition", "(Ljava/lang/String;)V", "convertToReaderPosition", "describeContents", "writeToParcel", "", "dest", "Landroid/os/Parcel;", "flags", "CREATOR", "onyxsdk-base_release" })
public final class TtsReaderPosition implements Parcelable
{
    @NotNull
    public static final CREATOR CREATOR;
    private int pageIndex;
    private int intPosition;
    @NotNull
    private String strPosition;
    
    public TtsReaderPosition(@NotNull final ReaderPosition readerPosition) {
        Intrinsics.checkNotNullParameter((Object)readerPosition, "readerPosition");
        this.pageIndex = -1;
        this.intPosition = -1;
        this.strPosition = "";
        this.pageIndex = readerPosition.getPageIndex();
        this.intPosition = readerPosition.asInteger();
        final String string = readerPosition.asString();
        Intrinsics.checkNotNullExpressionValue((Object)string, "readerPosition.asString()");
        this.strPosition = string;
    }
    
    @JvmStatic
    @Nullable
    public static final TtsReaderPosition create(@Nullable final ReaderPosition readerPosition) {
        return TtsReaderPosition.CREATOR.create(readerPosition);
    }
    
    @JvmStatic
    @NotNull
    public static final ReaderPosition convertToReaderPosition(@Nullable final TtsReaderPosition ttsReaderPosition) {
        return TtsReaderPosition.CREATOR.convertToReaderPosition(ttsReaderPosition);
    }
    
    static {
        CREATOR = new CREATOR(null);
    }
    
    public final int getPageIndex() {
        return this.pageIndex;
    }
    
    public final void setPageIndex(final int value) {
        this.pageIndex = value;
    }
    
    public final int getIntPosition() {
        return this.intPosition;
    }
    
    public final void setIntPosition(final int value) {
        this.intPosition = value;
    }
    
    @NotNull
    public final String getStrPosition() {
        return this.strPosition;
    }
    
    public final void setStrPosition(@NotNull final String value) {
        Intrinsics.checkNotNullParameter((Object)value, "<set-?>");
        this.strPosition = value;
    }
    
    @NotNull
    public final ReaderPosition convertToReaderPosition() {
        final ReaderPositionImpl readerPositionImpl = new ReaderPositionImpl();
        final int pageIndex;
        if ((pageIndex = this.pageIndex) > -1) {
            readerPositionImpl.fromPageIndex(pageIndex);
        }
        final int intPosition;
        if ((intPosition = this.intPosition) > -1) {
            readerPositionImpl.fromInteger(intPosition);
        }
        if (StringUtils.isNotBlank(this.strPosition)) {
            readerPositionImpl.fromString(this.strPosition);
        }
        return readerPositionImpl;
    }
    
    public int describeContents() {
        return 0;
    }
    
    public void writeToParcel(@NotNull final Parcel dest, final int flags) {
        Intrinsics.checkNotNullParameter((Object)dest, "dest");
        dest.writeInt(this.pageIndex);
        dest.writeInt(this.intPosition);
        dest.writeString(this.strPosition);
    }
    
    @Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0003J\u0012\u0010\u0004\u001a\u00020\u00052\b\u0010\u0006\u001a\u0004\u0018\u00010\u0002H\u0007J\u0014\u0010\u0007\u001a\u0004\u0018\u00010\u00022\b\u0010\b\u001a\u0004\u0018\u00010\u0005H\u0007J\u0010\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0016J\u001d\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¢\u0006\u0002\u0010\u0010¨\u0006\u0011" }, d2 = { "Lcom/onyx/android/sdk/reader/TtsReaderPosition$CREATOR;", "Landroid/os/Parcelable.Creator;", "Lcom/onyx/android/sdk/reader/TtsReaderPosition;", "()V", "convertToReaderPosition", "Lcom/onyx/android/sdk/api/ReaderPosition;", "ttsReaderPosition", "create", "readerPosition", "createFromParcel", "source", "Landroid/os/Parcel;", "newArray", "", "size", "", "(I)[Lcom/onyx/android/sdk/reader/TtsReaderPosition;", "onyxsdk-base_release" })
    public static final class CREATOR implements Parcelable.Creator<TtsReaderPosition>
    {
        private CREATOR() {
        }

        public CREATOR(DefaultConstructorMarker marker) {
            this();
        }
        
        @NotNull
        public TtsReaderPosition createFromParcel(@NotNull final Parcel source) {
            Intrinsics.checkNotNullParameter((Object)source, "source");
            final ReaderPositionImpl readerPositionImpl = new com.onyx.android.sdk.data.ReaderPositionImpl();
            final ReaderPositionImpl readerPositionImpl3;
            final ReaderPositionImpl readerPositionImpl2;
            final ReaderPositionImpl readerPosition = readerPositionImpl2 = (readerPositionImpl3 = readerPositionImpl);
            new ReaderPositionImpl();
            readerPositionImpl2.fromPageIndex(source.readInt());
            readerPositionImpl3.fromInteger(source.readInt());
            readerPositionImpl.fromString(source.readString());
            return new TtsReaderPosition(readerPosition);
        }
        
        @NotNull
        public TtsReaderPosition[] newArray(final int size) {
            return new TtsReaderPosition[size];
        }
        
        @JvmStatic
        @Nullable
        public final TtsReaderPosition create(@Nullable final ReaderPosition readerPosition) {
            TtsReaderPosition ttsReaderPosition;
            if (readerPosition == null) {
                ttsReaderPosition = null;
            }
            else {
                ttsReaderPosition = new TtsReaderPosition(readerPosition);
            }
            return ttsReaderPosition;
        }
        
        @JvmStatic
        @NotNull
        public final ReaderPosition convertToReaderPosition(@Nullable final TtsReaderPosition ttsReaderPosition) {
            ReaderPosition readerPosition;
            if (ttsReaderPosition == null) {
                readerPosition = null;
            }
            else {
                readerPosition = ttsReaderPosition.convertToReaderPosition();
            }
            if (readerPosition == null) {
                Intrinsics.checkNotNullExpressionValue((Object)(readerPosition = ReaderPosition.dummy), "dummy");
            }
            return readerPosition;
        }
    }
}
