// 
// 

package com.onyx.android.sdk.path;

import java.io.IOException;
import com.onyx.android.sdk.commons.io.FileUtils;
import kotlin.ExceptionsKt;
import com.onyx.android.sdk.base.utils.Debug;
import java.nio.file.LinkOption;
import java.nio.file.Files;
import java.util.Arrays;
import java.nio.file.attribute.FileAttribute;
import java.io.File;
import com.onyx.android.sdk.device.EnvironmentUtil;
import com.onyx.android.sdk.extension.Paths;
import kotlin.jvm.internal.Intrinsics;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u001d\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004J\u000e\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\f\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u0004J\u0016\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0004J/\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\b2\u001a\u0010\u0011\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00130\u0012\"\u0006\u0012\u0002\b\u00030\u0013¢\u0006\u0002\u0010\u0014J\u0016\u0010\u0015\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\u0016\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u0004J\u0016\u0010\u0017\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u0016\u0010\u0018\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\u001e\u0010\u001a\u001a\u00020\b2\u0006\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0004J\u0016\u0010\u001c\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u000b\u001a\u00020\u0004J\n\u0010\u001d\u001a\u00020\b*\u00020\bJ\n\u0010\u001e\u001a\u00020\b*\u00020\bJ\n\u0010\u001f\u001a\u00020\b*\u00020\bJ\n\u0010 \u001a\u00020\b*\u00020\bJ\n\u0010!\u001a\u00020\b*\u00020\bJ\n\u0010\"\u001a\u00020\b*\u00020\bJ\n\u0010#\u001a\u00020\b*\u00020\bJ\n\u0010$\u001a\u00020\b*\u00020\bJ\n\u0010%\u001a\u00020\b*\u00020\bJ\n\u0010&\u001a\u00020\b*\u00020\bJ\n\u0010'\u001a\u00020\b*\u00020\bJ\n\u0010(\u001a\u00020\b*\u00020\bJ\n\u0010)\u001a\u00020\b*\u00020\bJ\n\u0010*\u001a\u00020\b*\u00020\bJ\n\u0010+\u001a\u00020\b*\u00020\bJ\n\u0010,\u001a\u00020\b*\u00020\bJ\n\u0010-\u001a\u00020\b*\u00020\bJ\n\u0010.\u001a\u00020\b*\u00020\bJ\n\u0010/\u001a\u00020\b*\u00020\bR\u0011\u0010\u0003\u001a\u00020\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u00060" }, d2 = { "Lcom/onyx/android/sdk/path/CommonPaths;", "", "()V", "externalStorageDir", "", "getExternalStorageDir", "()Ljava/lang/String;", "appDocDir", "Ljava/nio/file/Path;", "docId", "appUserNoteDir", "userId", "appUserPbDir", "docDir", "dir", "ensureCreateDirectories", "path", "attributes", "", "Ljava/nio/file/attribute/FileAttribute;", "(Ljava/nio/file/Path;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "userDataRepository", "userDir", "userNoteDir", "userPBDir", "pbDirPath", "userPBPath", "revisionId", "userShareResourceDir", "app", "content", "data", "doc", "document", "fileLocks", "font", "json", "note", "page", "pageModel", "pb", "point", "resource", "shape", "stash", "thumbnail", "toc", "virtual", "onyxsdk-base_release" })
public final class CommonPaths
{
    @NotNull
    public static final CommonPaths INSTANCE;
    
    private CommonPaths() {
    }
    
    static {
        INSTANCE = new CommonPaths();
    }
    
    @NotNull
    public final Path pb(@NotNull final Path $this$pb) {
        Intrinsics.checkNotNullParameter((Object)$this$pb, "<this>");
        return Paths.INSTANCE.append($this$pb, "pb");
    }
    
    @NotNull
    public final Path document(@NotNull final Path $this$document) {
        Intrinsics.checkNotNullParameter((Object)$this$document, "<this>");
        return Paths.INSTANCE.append($this$document, "document");
    }
    
    @NotNull
    public final Path data(@NotNull final Path $this$data) {
        Intrinsics.checkNotNullParameter((Object)$this$data, "<this>");
        return Paths.INSTANCE.append($this$data, "data");
    }
    
    @NotNull
    public final Path content(@NotNull final Path $this$content) {
        Intrinsics.checkNotNullParameter((Object)$this$content, "<this>");
        return Paths.INSTANCE.append($this$content, "content");
    }
    
    @NotNull
    public final Path note(@NotNull final Path $this$note) {
        Intrinsics.checkNotNullParameter((Object)$this$note, "<this>");
        return Paths.INSTANCE.append($this$note, "note");
    }
    
    @NotNull
    public final Path thumbnail(@NotNull final Path $this$thumbnail) {
        Intrinsics.checkNotNullParameter((Object)$this$thumbnail, "<this>");
        return Paths.INSTANCE.append($this$thumbnail, "thumbnail");
    }
    
    @NotNull
    public final Path json(@NotNull final Path $this$json) {
        Intrinsics.checkNotNullParameter((Object)$this$json, "<this>");
        return Paths.INSTANCE.append($this$json, "json");
    }
    
    @NotNull
    public final Path virtual(@NotNull final Path $this$virtual) {
        Intrinsics.checkNotNullParameter((Object)$this$virtual, "<this>");
        return Paths.INSTANCE.append($this$virtual, "virtual");
    }
    
    @NotNull
    public final Path page(@NotNull final Path $this$page) {
        Intrinsics.checkNotNullParameter((Object)$this$page, "<this>");
        return Paths.INSTANCE.append($this$page, "page");
    }
    
    @NotNull
    public final Path pageModel(@NotNull final Path $this$pageModel) {
        Intrinsics.checkNotNullParameter((Object)$this$pageModel, "<this>");
        return Paths.INSTANCE.append($this$pageModel, "pageModel");
    }
    
    @NotNull
    public final Path fileLocks(@NotNull final Path $this$fileLocks) {
        Intrinsics.checkNotNullParameter((Object)$this$fileLocks, "<this>");
        return Paths.INSTANCE.append($this$fileLocks, ".locks");
    }
    
    @NotNull
    public final Path doc(@NotNull final Path $this$doc) {
        Intrinsics.checkNotNullParameter((Object)$this$doc, "<this>");
        return Paths.INSTANCE.append($this$doc, "doc");
    }
    
    @NotNull
    public final Path toc(@NotNull final Path $this$toc) {
        Intrinsics.checkNotNullParameter((Object)$this$toc, "<this>");
        return Paths.INSTANCE.append($this$toc, "toc");
    }
    
    @NotNull
    public final Path point(@NotNull final Path $this$point) {
        Intrinsics.checkNotNullParameter((Object)$this$point, "<this>");
        return Paths.INSTANCE.append($this$point, "point");
    }
    
    @NotNull
    public final Path resource(@NotNull final Path $this$resource) {
        Intrinsics.checkNotNullParameter((Object)$this$resource, "<this>");
        return Paths.INSTANCE.append($this$resource, "resource");
    }
    
    @NotNull
    public final Path shape(@NotNull final Path $this$shape) {
        Intrinsics.checkNotNullParameter((Object)$this$shape, "<this>");
        return Paths.INSTANCE.append($this$shape, "shape");
    }
    
    @NotNull
    public final Path font(@NotNull final Path $this$font) {
        Intrinsics.checkNotNullParameter((Object)$this$font, "<this>");
        return Paths.INSTANCE.append($this$font, "font");
    }
    
    @NotNull
    public final Path app(@NotNull final Path $this$app) {
        Intrinsics.checkNotNullParameter((Object)$this$app, "<this>");
        return Paths.INSTANCE.append($this$app, "app");
    }
    
    @NotNull
    public final Path stash(@NotNull final Path $this$stash) {
        Intrinsics.checkNotNullParameter((Object)$this$stash, "<this>");
        return Paths.INSTANCE.append($this$stash, "stash");
    }
    
    @NotNull
    public final String getExternalStorageDir() {
        return Intrinsics.stringPlus(EnvironmentUtil.getExternalStorageDirectory().getPath(), (Object)File.separator);
    }
    
    @NotNull
    public final Path userDir(@NotNull final String userId) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        final Path directories = ensureCreateDirectories(AppPaths.INSTANCE.appFilesDir(userId));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Path appUserNoteDir(@NotNull final String userId) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        return this.userNoteDir(AppPaths.INSTANCE.kSyncFilesPath(), userId);
    }
    
    @NotNull
    public final Path userNoteDir(@NotNull final String dir, @NotNull final String userId) {
        Intrinsics.checkNotNullParameter((Object)dir, "dir");
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        final Paths instance = Paths.INSTANCE;
        final Path value = java.nio.file.Paths.get(dir, (String[])Arrays.copyOf(new String[] { userId }, 1));
        Intrinsics.checkNotNullExpressionValue((Object)value, "get(base, *subpaths)");
        final Path directories = ensureCreateDirectories(instance.append(value, "note"));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Path appDocDir(@NotNull final String docId) {
        Intrinsics.checkNotNullParameter((Object)docId, "docId");
        final Path directories = ensureCreateDirectories(Paths.INSTANCE.append(AppPaths.INSTANCE.appDocDirPath(), docId));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Path docDir(@NotNull final String dir, @NotNull final String docId) {
        Intrinsics.checkNotNullParameter((Object)dir, "dir");
        Intrinsics.checkNotNullParameter((Object)docId, "docId");
        final Path value = java.nio.file.Paths.get(dir, (String[])Arrays.copyOf(new String[] { docId }, 1));
        Intrinsics.checkNotNullExpressionValue((Object)value, "get(base, *subpaths)");
        final Path directories = ensureCreateDirectories(value);
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Path appUserPbDir(@NotNull final String userId) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        return this.userPBDir(AppPaths.INSTANCE.kSyncFilesPath(), userId);
    }
    
    @NotNull
    public final Path userPBPath(@NotNull final String pbDirPath, @NotNull final String userId, @NotNull final String revisionId) {
        Intrinsics.checkNotNullParameter((Object)pbDirPath, "pbDirPath");
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        Intrinsics.checkNotNullParameter((Object)revisionId, "revisionId");
        return Paths.INSTANCE.append(this.userPBDir(pbDirPath, userId), revisionId);
    }
    
    @NotNull
    public final Path userPBDir(@NotNull final String pbDirPath, @NotNull final String userId) {
        Intrinsics.checkNotNullParameter((Object)pbDirPath, "pbDirPath");
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        final Path directories = ensureCreateDirectories(this.pb(this.userNoteDir(pbDirPath, userId)));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Path userDataRepository(@NotNull final String dir, @NotNull final String userId) {
        Intrinsics.checkNotNullParameter((Object)dir, "dir");
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        final Paths instance = Paths.INSTANCE;
        final Path value = java.nio.file.Paths.get(dir, (String[])Arrays.copyOf(new String[] { userId }, 1));
        Intrinsics.checkNotNullExpressionValue((Object)value, "get(base, *subpaths)");
        final Path directories = ensureCreateDirectories(instance.append(this.ensureCreateDirectories(value), "dataRepository"));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Path userShareResourceDir(@NotNull final String dir, @NotNull final String userId) {
        Intrinsics.checkNotNullParameter((Object)dir, "dir");
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        final Paths instance = Paths.INSTANCE;
        final Path value = java.nio.file.Paths.get(dir, (String[])Arrays.copyOf(new String[] { userId }, 1));
        Intrinsics.checkNotNullExpressionValue((Object)value, "get(base, *subpaths)");
        final Path directories = ensureCreateDirectories(instance.append(this.ensureCreateDirectories(value), "shareResource"));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Path ensureCreateDirectories(@NotNull final Path path, @NotNull final FileAttribute<?>... attributes) {
        Intrinsics.checkNotNullParameter((Object)path, "path");
        Intrinsics.checkNotNullParameter((Object)attributes, "attributes");
        final int newLength = 0;
        try {
            if (Files.isDirectory(path, (LinkOption[])Arrays.copyOf(new LinkOption[newLength], newLength))) {
                return path;
            }
            final FileAttribute<?>[] original = Arrays.copyOf(attributes, attributes.length);
            final Path directories = Files.createDirectories(path, (FileAttribute<?>[])Arrays.copyOf(original, original.length));
            Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
            return directories;
        }
        catch (final IOException ex) {
            final IOException $this$isFileAlreadyExistsException = ex;
            $this$isFileAlreadyExistsException.printStackTrace();
            Debug.INSTANCE.e("createDirectories fail", ExceptionsKt.stackTraceToString((Throwable)ex), new Object[0]);
            final File file;
            if (com.onyx.android.sdk.extension.ExceptionsKt.isFileAlreadyExistsException($this$isFileAlreadyExistsException) && (file = path.toFile()).exists() && file.isFile()) {
                final File file2 = file;
                final StringBuilder sb = new StringBuilder();
                final Exception ex2 = new Exception(sb.append("Detect directory is file:").append((Object)file.getAbsolutePath()).append(",length:").append(file.length()).toString());
                Debug.INSTANCE.w((Class)CommonPaths.class, (Throwable)ex2);
                Debug.INSTANCE.e("createDirectories fail", ExceptionsKt.stackTraceToString((Throwable)ex2), new Object[0]);
                FileUtils.deleteQuietly(file2);
                final FileAttribute<?>[] original2 = Arrays.copyOf(attributes, attributes.length);
                try {
                    return Files.createDirectories(path, original2);
                } catch (IOException retryFailure) {
                    throw new IllegalStateException(retryFailure);
                }
            }
            throw new IllegalStateException(ex);
        }
    }
}
