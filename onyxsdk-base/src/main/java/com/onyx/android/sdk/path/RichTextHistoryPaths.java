// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.path;

import com.onyx.android.sdk.utils.FileUtils;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.Arrays;
import java.nio.file.attribute.FileAttribute;
import com.onyx.android.sdk.data.sync.KSyncConstant;
import com.onyx.android.sdk.extension.Paths;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004J\u0016\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0004J\u001e\u0010\b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0004J\u0016\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004¨\u0006\f" }, d2 = { "Lcom/onyx/android/sdk/path/RichTextHistoryPaths;", "", "()V", "getHistoryDirPath", "", "documentId", "getHistoryDirPathByDocDirPath", "docDirPath", "getHistoryFilePath", "fileName", "fileExtension", "getLocalHistoryFilePath", "onyxsdk-base_release" })
public final class RichTextHistoryPaths
{
    @NotNull
    public static final RichTextHistoryPaths INSTANCE;
    
    private RichTextHistoryPaths() {
    }
    
    static {
        INSTANCE = new RichTextHistoryPaths();
    }
    
    @NotNull
    public final String getHistoryDirPath(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final Paths instance = Paths.INSTANCE;
        final String docDirFilePath = KSyncConstant.docDirFilePath(documentId);
        Intrinsics.checkNotNullExpressionValue((Object)docDirFilePath, "docDirFilePath(documentId)");
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                instance.append(instance.toPath(docDirFilePath), "history"));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories.toAbsolutePath().toString();
    }
    
    @NotNull
    public final String getLocalHistoryFilePath(@NotNull final String documentId, @NotNull final String fileName) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        Intrinsics.checkNotNullParameter((Object)fileName, "fileName");
        final Paths instance = Paths.INSTANCE;
        return instance.append(instance.toPath(this.getHistoryDirPath(documentId)), fileName).toAbsolutePath().toString();
    }
    
    @NotNull
    public final String getHistoryFilePath(@NotNull final String documentId, @NotNull final String fileName, @NotNull final String fileExtension) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        Intrinsics.checkNotNullParameter((Object)fileName, "fileName");
        Intrinsics.checkNotNullParameter((Object)fileExtension, "fileExtension");
        final String combine = FileUtils.combine(this.getLocalHistoryFilePath(documentId, fileName), fileExtension);
        Intrinsics.checkNotNullExpressionValue((Object)combine, "combine(filePath, fileExtension)");
        return combine;
    }
    
    @NotNull
    public final String getHistoryDirPathByDocDirPath(@NotNull final String docDirPath, @NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)docDirPath, "docDirPath");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final Paths instance = Paths.INSTANCE;
        final String docDirFilePath = KSyncConstant.docDirFilePath(docDirPath, documentId);
        Intrinsics.checkNotNullExpressionValue((Object)docDirFilePath, "docDirFilePath(docDirPath, documentId)");
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                instance.append(instance.toPath(docDirFilePath), "history"));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories.toAbsolutePath().toString();
    }
}
