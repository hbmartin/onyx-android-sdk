// 
// 

package com.onyx.android.sdk.path;

import java.nio.file.LinkOption;
import com.onyx.android.sdk.utils.FileUtils;
import com.onyx.android.sdk.extension.Paths;
import java.nio.file.Files;
import java.util.Arrays;
import java.nio.file.attribute.FileAttribute;
import kotlin.jvm.internal.Intrinsics;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\n\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\r\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\r\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u000e\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u000f" }, d2 = { "Lcom/onyx/android/sdk/path/StashPaths;", "", "()V", "deleteStashShapeLockFile", "", "documentId", "", "ensureStashShapeLockFile", "Ljava/nio/file/Path;", "isStashShapeLockFileExist", "stashShapeLockFile", "stashShapePBArchivedDir", "pbDirPath", "stashShapePBFileDir", "stashShapeResourceDir", "onyxsdk-base_release" })
public final class StashPaths
{
    @NotNull
    public static final StashPaths INSTANCE;
    
    private StashPaths() {
    }
    
    static {
        INSTANCE = new StashPaths();
    }
    
    @NotNull
    public final Path stashShapePBFileDir(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        return this.stashShapePBFileDir(AppPaths.INSTANCE.appDocDirPath().toAbsolutePath().toString(), documentId);
    }
    
    @NotNull
    public final Path stashShapePBFileDir(@NotNull final String pbDirPath, @NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)pbDirPath, "pbDirPath");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final CommonPaths instance = CommonPaths.INSTANCE;
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                instance.shape(instance.stash(instance.docDir(pbDirPath, documentId))));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Path stashShapeResourceDir(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final CommonPaths instance = CommonPaths.INSTANCE;
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                instance.resource(instance.shape(instance.stash(instance.docDir(
                        AppPaths.INSTANCE.appDocDirPath().toAbsolutePath().toString(), documentId)))));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Path stashShapePBArchivedDir(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        return this.stashShapePBArchivedDir(AppPaths.INSTANCE.appDocDirPath().toAbsolutePath().toString(), documentId);
    }
    
    @NotNull
    public final Path stashShapePBArchivedDir(@NotNull final String pbDirPath, @NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)pbDirPath, "pbDirPath");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final Paths instance = Paths.INSTANCE;
        final CommonPaths instance2 = CommonPaths.INSTANCE;
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                instance.append(instance2.stash(instance2.docDir(pbDirPath, documentId)), "archivedShape"));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Path stashShapeLockFile(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final Paths instance = Paths.INSTANCE;
        final CommonPaths instance2 = CommonPaths.INSTANCE;
        return instance.append(instance2.stash(instance2.docDir(AppPaths.INSTANCE.appDocDir(), documentId)), ".locks");
    }
    
    @NotNull
    public final Path ensureStashShapeLockFile(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final Path stashShapeLockFile = this.stashShapeLockFile(documentId);
        FileUtils.ensureFileExists(stashShapeLockFile.toAbsolutePath().toString());
        return stashShapeLockFile;
    }
    
    public final boolean deleteStashShapeLockFile(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        try {
            return Files.deleteIfExists(this.stashShapeLockFile(documentId));
        } catch (java.io.IOException exception) {
            return false;
        }
    }
    
    public final boolean isStashShapeLockFileExist(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        return Files.exists(this.stashShapeLockFile(documentId), (LinkOption[])Arrays.copyOf(new LinkOption[0], 0));
    }
}
