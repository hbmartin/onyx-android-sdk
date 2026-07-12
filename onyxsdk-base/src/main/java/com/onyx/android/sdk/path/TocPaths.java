// 
// 

package com.onyx.android.sdk.path;

import java.util.Iterator;
import java.util.LinkedList;
import java.io.File;
import java.util.LinkedHashSet;
import java.io.FilenameFilter;
import com.onyx.android.sdk.utils.FileUtils;
import java.util.Set;
import java.nio.file.Files;
import java.util.Arrays;
import java.nio.file.attribute.FileAttribute;
import com.onyx.android.sdk.extension.Paths;
import kotlin.jvm.internal.Intrinsics;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006J\u0016\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\r" }, d2 = { "Lcom/onyx/android/sdk/path/TocPaths;", "", "()V", "docTocPBFileDir", "Ljava/nio/file/Path;", "documentId", "", "docTocPBFileNameList", "", "docTocPBFilePath", "revisionId", "tocPBFileDir", "pbDirPath", "onyxsdk-base_release" })
public final class TocPaths
{
    @NotNull
    public static final TocPaths INSTANCE;
    
    private TocPaths() {
    }
    
    private final Path a(final String documentId) {
        return this.tocPBFileDir(AppPaths.INSTANCE.appDocDir(), documentId);
    }
    
    static {
        INSTANCE = new TocPaths();
    }
    
    @NotNull
    public final Path docTocPBFilePath(@NotNull final String documentId, @NotNull final String revisionId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        Intrinsics.checkNotNullParameter((Object)revisionId, "revisionId");
        return Paths.INSTANCE.append(this.a(documentId), revisionId);
    }
    
    @NotNull
    public final Path tocPBFileDir(@NotNull final String pbDirPath, @NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)pbDirPath, "pbDirPath");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final CommonPaths instance = CommonPaths.INSTANCE;
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                instance.pb(instance.toc(instance.docDir(pbDirPath, documentId))));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Set<String> docTocPBFileNameList(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final LinkedList<File> listAllFiles = FileUtils.listAllFiles(this.a(documentId).toAbsolutePath().toString(), null);
        Intrinsics.checkNotNullExpressionValue((Object)listAllFiles, "listAllFiles(docTocPBFil\u2026solutePathString(), null)");
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
