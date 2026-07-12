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
import com.onyx.android.sdk.data.point.PointDocumentUtils;
import kotlin.jvm.JvmStatic;
import java.nio.file.Files;
import java.util.Arrays;
import java.nio.file.attribute.FileAttribute;
import com.onyx.android.sdk.extension.Paths;
import kotlin.jvm.internal.Intrinsics;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0002\b\u000e\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\u0006\u0010\t\u001a\u00020\u0006J\u0018\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u0006H\u0007J\u001e\u0010\f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u0006J\u0006\u0010\u000e\u001a\u00020\u0004J\u001e\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0006J\u0016\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0006J\u0016\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0016" }, d2 = { "Lcom/onyx/android/sdk/path/PointPaths;", "", "()V", "appDocPointDir", "Ljava/nio/file/Path;", "documentId", "", "appDocPointNameList", "", "documentUniqueId", "appPagePointDir", "pageId", "appPagePointPath", "revisionId", "appPointDir", "appPointFilePath", "pageUniqueId", "fileName", "pagePointDir", "docPointDir", "pointDir", "dirPath", "onyxsdk-base_release" })
public final class PointPaths
{
    @NotNull
    public static final PointPaths INSTANCE;
    
    private PointPaths() {
    }
    
    @JvmStatic
    @NotNull
    public static final Path appPagePointDir(@NotNull final String documentId, @NotNull final String pageId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        Intrinsics.checkNotNullParameter((Object)pageId, "pageId");
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                Paths.INSTANCE.append(PointPaths.INSTANCE.appDocPointDir(documentId), pageId));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    static {
        INSTANCE = new PointPaths();
    }
    
    @NotNull
    public final Path appPointDir() {
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                AppPaths.INSTANCE.appFilesDir("point"));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Path appDocPointDir(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                Paths.INSTANCE.append(AppPaths.INSTANCE.appFilesDir("point"), documentId));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Path appPagePointPath(@NotNull final String documentId, @NotNull final String pageId, @NotNull final String revisionId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        Intrinsics.checkNotNullParameter((Object)pageId, "pageId");
        Intrinsics.checkNotNullParameter((Object)revisionId, "revisionId");
        return Paths.INSTANCE.append(appPagePointDir(documentId, pageId), PointDocumentUtils.getPointFileName(pageId, revisionId));
    }
    
    @NotNull
    public final Path pointDir(@NotNull final String dirPath, @NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)dirPath, "dirPath");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final CommonPaths instance = CommonPaths.INSTANCE;
        return instance.point(instance.docDir(dirPath, documentId));
    }
    
    @NotNull
    public final Path pagePointDir(@NotNull final Path docPointDir, @NotNull final String pageId) {
        Intrinsics.checkNotNullParameter((Object)docPointDir, "docPointDir");
        Intrinsics.checkNotNullParameter((Object)pageId, "pageId");
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                Paths.INSTANCE.append(docPointDir, pageId));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Set<String> appDocPointNameList(@NotNull final String documentUniqueId) {
        Intrinsics.checkNotNullParameter((Object)documentUniqueId, "documentUniqueId");
        final LinkedList<File> listAllFiles = FileUtils.listAllFiles(this.appDocPointDir(documentUniqueId).toAbsolutePath().toString(), null);
        Intrinsics.checkNotNullExpressionValue((Object)listAllFiles, "listAllFiles(appDocPoint\u2026solutePathString(), null)");
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
    
    @NotNull
    public final Path appPointFilePath(@NotNull final String documentId, @NotNull final String pageUniqueId, @NotNull final String fileName) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        Intrinsics.checkNotNullParameter((Object)pageUniqueId, "pageUniqueId");
        Intrinsics.checkNotNullParameter((Object)fileName, "fileName");
        return Paths.INSTANCE.append(appPagePointDir(documentId, pageUniqueId), fileName);
    }
}
