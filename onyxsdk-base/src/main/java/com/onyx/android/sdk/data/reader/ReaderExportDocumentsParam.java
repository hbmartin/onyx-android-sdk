// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.data.reader;

import org.jetbrains.annotations.Nullable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006H\u00c6\u0003J\t\u0010\u0014\u001a\u00020\bH\u00c6\u0003J7\u0010\u0015\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u00c6\u0001J\u0013\u0010\u0016\u001a\u00020\b2\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0018\u001a\u00020\u0019H\u00d6\u0001J\t\u0010\u001a\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\rR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001b" }, d2 = { "Lcom/onyx/android/sdk/data/reader/ReaderExportDocumentsParam;", "", "exportDir", "", "exportFileName", "pathList", "", "convertToPDF", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Z)V", "getConvertToPDF", "()Z", "getExportDir", "()Ljava/lang/String;", "getExportFileName", "getPathList", "()Ljava/util/List;", "component1", "component2", "component3", "component4", "copy", "equals", "other", "hashCode", "", "toString", "onyxsdk-base_release" })
public final class ReaderExportDocumentsParam
{
    @NotNull
    private final String a;
    @NotNull
    private final String b;
    @NotNull
    private final List<String> c;
    private final boolean d;
    
    public ReaderExportDocumentsParam(@NotNull final String exportDir, @NotNull final String exportFileName, @NotNull final List<String> pathList, final boolean convertToPDF) {
        Intrinsics.checkNotNullParameter((Object)exportDir, "exportDir");
        Intrinsics.checkNotNullParameter((Object)exportFileName, "exportFileName");
        Intrinsics.checkNotNullParameter((Object)pathList, "pathList");
        this.a = exportDir;
        this.b = exportFileName;
        this.c = pathList;
        this.d = convertToPDF;
    }

    public ReaderExportDocumentsParam(final String exportDir, final String exportFileName, final List pathList, final boolean convertToPDF, final int i, final DefaultConstructorMarker marker) {
        this(exportDir, exportFileName, (List<String>)pathList, (i & 8) != 0 ? false : convertToPDF);
    }

    @NotNull
    public final String getExportDir() {
        return this.a;
    }
    
    @NotNull
    public final String getExportFileName() {
        return this.b;
    }
    
    @NotNull
    public final List<String> getPathList() {
        return this.c;
    }
    
    public final boolean getConvertToPDF() {
        return this.d;
    }
    
    @NotNull
    public final String component1() {
        return this.a;
    }
    
    @NotNull
    public final String component2() {
        return this.b;
    }
    
    @NotNull
    public final List<String> component3() {
        return this.c;
    }
    
    public final boolean component4() {
        return this.d;
    }
    
    @NotNull
    public final ReaderExportDocumentsParam copy(@NotNull final String exportDir, @NotNull final String exportFileName, @NotNull final List<String> pathList, final boolean convertToPDF) {
        Intrinsics.checkNotNullParameter((Object)exportDir, "exportDir");
        Intrinsics.checkNotNullParameter((Object)exportFileName, "exportFileName");
        Intrinsics.checkNotNullParameter((Object)pathList, "pathList");
        return new ReaderExportDocumentsParam(exportDir, exportFileName, pathList, convertToPDF);
    }

    public static /* synthetic */ ReaderExportDocumentsParam copy$default(final ReaderExportDocumentsParam readerExportDocumentsParam, String exportDir, String exportFileName, List pathList, boolean convertToPDF, final int i, final Object obj) {
        if ((i & 1) != 0) {
            exportDir = readerExportDocumentsParam.a;
        }
        if ((i & 2) != 0) {
            exportFileName = readerExportDocumentsParam.b;
        }
        if ((i & 4) != 0) {
            pathList = readerExportDocumentsParam.c;
        }
        if ((i & 8) != 0) {
            convertToPDF = readerExportDocumentsParam.d;
        }
        return readerExportDocumentsParam.copy(exportDir, exportFileName, (List<String>)pathList, convertToPDF);
    }
    
    @NotNull
    @Override
    public String toString() {
        return "ReaderExportDocumentsParam(exportDir=" + this.a + ", exportFileName=" + this.b + ", pathList=" + this.c + ", convertToPDF=" + this.d + ')';
    }
    
    @Override
    public int hashCode() {
        final int n = ((this.a.hashCode() * 31 + this.b.hashCode()) * 31 + this.c.hashCode()) * 31;
        int d;
        if ((d = (this.d ? 1 : 0)) != 0) {
            d = 1;
        }
        return n + d;
    }
    
    @Override
    public boolean equals(@Nullable final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ReaderExportDocumentsParam)) {
            return false;
        }
        final ReaderExportDocumentsParam readerExportDocumentsParam = (ReaderExportDocumentsParam)other;
        return Intrinsics.areEqual((Object)this.a, (Object)readerExportDocumentsParam.a) && Intrinsics.areEqual((Object)this.b, (Object)readerExportDocumentsParam.b) && Intrinsics.areEqual((Object)this.c, (Object)readerExportDocumentsParam.c) && this.d == readerExportDocumentsParam.d;
    }
}
