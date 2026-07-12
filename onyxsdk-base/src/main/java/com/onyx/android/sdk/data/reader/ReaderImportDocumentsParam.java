// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.data.reader;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u001f\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001J\t\u0010\u0011\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012" }, d2 = { "Lcom/onyx/android/sdk/data/reader/ReaderImportDocumentsParam;", "", "zipFilePath", "", "storeDir", "(Ljava/lang/String;Ljava/lang/String;)V", "getStoreDir", "()Ljava/lang/String;", "getZipFilePath", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "onyxsdk-base_release" })
public final class ReaderImportDocumentsParam
{
    @NotNull
    private final String a;
    @Nullable
    private final String b;
    
    public ReaderImportDocumentsParam(@NotNull final String zipFilePath, @Nullable final String storeDir) {
        Intrinsics.checkNotNullParameter((Object)zipFilePath, "zipFilePath");
        this.a = zipFilePath;
        this.b = storeDir;
    }

    public ReaderImportDocumentsParam(final String zipFilePath, final String storeDir, final int i, final DefaultConstructorMarker marker) {
        this(zipFilePath, (i & 2) != 0 ? null : storeDir);
    }

    @NotNull
    public final String getZipFilePath() {
        return this.a;
    }
    
    @Nullable
    public final String getStoreDir() {
        return this.b;
    }
    
    @NotNull
    public final String component1() {
        return this.a;
    }
    
    @Nullable
    public final String component2() {
        return this.b;
    }
    
    @NotNull
    public final ReaderImportDocumentsParam copy(@NotNull final String zipFilePath, @Nullable final String storeDir) {
        Intrinsics.checkNotNullParameter((Object)zipFilePath, "zipFilePath");
        return new ReaderImportDocumentsParam(zipFilePath, storeDir);
    }

    public static /* synthetic */ ReaderImportDocumentsParam copy$default(final ReaderImportDocumentsParam readerImportDocumentsParam, String zipFilePath, String storeDir, final int i, final Object obj) {
        if ((i & 1) != 0) {
            zipFilePath = readerImportDocumentsParam.a;
        }
        if ((i & 2) != 0) {
            storeDir = readerImportDocumentsParam.b;
        }
        return readerImportDocumentsParam.copy(zipFilePath, storeDir);
    }
    
    @NotNull
    @Override
    public String toString() {
        return "ReaderImportDocumentsParam(zipFilePath=" + this.a + ", storeDir=" + (Object)this.b + ')';
    }
    
    @Override
    public int hashCode() {
        final int n = this.a.hashCode() * 31;
        final String b;
        int hashCode;
        if ((b = this.b) == null) {
            hashCode = 0;
        }
        else {
            hashCode = b.hashCode();
        }
        return n + hashCode;
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ReaderImportDocumentsParam)) {
            return false;
        }
        final ReaderImportDocumentsParam readerImportDocumentsParam = (ReaderImportDocumentsParam)other;
        return Intrinsics.areEqual((Object)this.a, (Object)readerImportDocumentsParam.a) && Intrinsics.areEqual((Object)this.b, (Object)readerImportDocumentsParam.b);
    }
}
