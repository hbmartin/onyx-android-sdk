// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.path;

import java.util.Iterator;
import java.util.LinkedList;
import java.io.File;
import java.util.LinkedHashSet;
import java.io.FilenameFilter;
import com.onyx.android.sdk.utils.FileUtils;
import java.util.Set;
import com.onyx.android.sdk.data.sync.KSyncConstant;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\"\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004J\u000e\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004J\u0016\u0010\b\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004J\u0016\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004J\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\f2\u0006\u0010\u0005\u001a\u00020\u0004¨\u0006\r" }, d2 = { "Lcom/onyx/android/sdk/path/VirtualFilePaths;", "", "()V", "getDocPBFileDirPath", "", "documentId", "pbDirPath", "getDocPBFilePath", "getPagePBFileDirPath", "getPagePBFilePath", "revisionId", "pagePBFileNameList", "", "onyxsdk-base_release" })
public final class VirtualFilePaths
{
    @NotNull
    public static final VirtualFilePaths INSTANCE;
    
    private VirtualFilePaths() {
    }
    
    static {
        INSTANCE = new VirtualFilePaths();
    }
    
    @NotNull
    public final String getPagePBFilePath(@NotNull final String documentId, @NotNull final String revisionId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        Intrinsics.checkNotNullParameter((Object)revisionId, "revisionId");
        final Path value = Paths.get(this.getPagePBFileDirPath(documentId), (String[])Arrays.copyOf(new String[] { revisionId }, 1));
        Intrinsics.checkNotNullExpressionValue((Object)value, "get(base, *subpaths)");
        return value.toAbsolutePath().toString();
    }
    
    @NotNull
    public final String getPagePBFileDirPath(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final String syncDocDirFilePath = KSyncConstant.syncDocDirFilePath();
        Intrinsics.checkNotNullExpressionValue((Object)syncDocDirFilePath, "syncDocDirFilePath()");
        return this.getPagePBFileDirPath(syncDocDirFilePath, documentId);
    }
    
    @NotNull
    public final String getPagePBFileDirPath(@NotNull final String pbDirPath, @NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)pbDirPath, "pbDirPath");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final String docDirFilePath = KSyncConstant.docDirFilePath(pbDirPath, documentId);
        Intrinsics.checkNotNullExpressionValue((Object)docDirFilePath, "docDirFilePath(pbDirPath, documentId)");
        final Path value = Paths.get(docDirFilePath, (String[])Arrays.copyOf(new String[] { "virtual", "page", "pb" }, 3));
        Intrinsics.checkNotNullExpressionValue((Object)value, "get(base, *subpaths)");
        return value.toAbsolutePath().toString();
    }
    
    @NotNull
    public final String getDocPBFilePath(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final Path value = Paths.get(this.getDocPBFileDirPath(documentId), (String[])Arrays.copyOf(new String[] { documentId }, 1));
        Intrinsics.checkNotNullExpressionValue((Object)value, "get(base, *subpaths)");
        return value.toAbsolutePath().toString();
    }
    
    @NotNull
    public final String getDocPBFileDirPath(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final String syncDocDirFilePath = KSyncConstant.syncDocDirFilePath();
        Intrinsics.checkNotNullExpressionValue((Object)syncDocDirFilePath, "syncDocDirFilePath()");
        return this.getDocPBFileDirPath(syncDocDirFilePath, documentId);
    }
    
    @NotNull
    public final String getDocPBFileDirPath(@NotNull final String pbDirPath, @NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)pbDirPath, "pbDirPath");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final String docDirFilePath = KSyncConstant.docDirFilePath(pbDirPath, documentId);
        Intrinsics.checkNotNullExpressionValue((Object)docDirFilePath, "docDirFilePath(pbDirPath, documentId)");
        final Path value = Paths.get(docDirFilePath, (String[])Arrays.copyOf(new String[] { "virtual", "doc", "pb" }, 3));
        Intrinsics.checkNotNullExpressionValue((Object)value, "get(base, *subpaths)");
        return value.toAbsolutePath().toString();
    }
    
    @NotNull
    public final Set<String> pagePBFileNameList(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final LinkedList<File> listAllFiles = FileUtils.listAllFiles(this.getPagePBFileDirPath(documentId), null);
        Intrinsics.checkNotNullExpressionValue((Object)listAllFiles, "listAllFiles(getPagePBFi\u2026irPath(documentId), null)");
        final LinkedHashSet<String> set = new LinkedHashSet<String>();
        final Iterator<File> iterator = listAllFiles.iterator();
        while (iterator.hasNext()) {
            final LinkedHashSet<String> set2 = set;
            final String name = iterator.next().getName();
            Intrinsics.checkNotNullExpressionValue((Object)name, "file.name");
            set2.add(name);
        }
        return set;
    }
}
