// 
// 

package com.onyx.android.sdk.path;

import kotlin.text.StringsKt;
import org.apache.commons.lang3.StringUtils;
import com.onyx.android.sdk.commons.io.FilenameUtils;
import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import java.util.Collection;
import java.io.File;
import java.util.LinkedHashSet;
import com.onyx.android.sdk.commons.io.FileUtils;
import java.util.Set;
import com.onyx.android.sdk.extension.Paths;
import java.nio.file.Files;
import java.util.Arrays;
import java.nio.file.attribute.FileAttribute;
import kotlin.jvm.internal.Intrinsics;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\b\u0012\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006J\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\n2\u0006\u0010\u0005\u001a\u00020\u0006J\u0018\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\f\u001a\u0004\u0018\u00010\u0006J\u000e\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u000e\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006J\u0016\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006J\u001e\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006J\u0016\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006J\u001e\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006J\u000e\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0006J\u0016\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006J\u0016\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0019\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006J\u001e\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006¨\u0006\u001c" }, d2 = { "Lcom/onyx/android/sdk/path/ResourcePaths;", "", "()V", "appDocResourceContentDir", "Ljava/nio/file/Path;", "documentId", "", "appDocResourceContentFilePath", "fileName", "appDocResourceDataFileNameList", "", "appResourceDataCopyPath", "srcPath", "appResourceDataDir", "appResourceDataFilePath", "docResourceContentDir", "docDirPath", "docResourceContentFilePath", "docResourceDataDir", "docResourceDataFilePath", "oldResourceDir", "dirPath", "relativePath", "filePath", "resourceDataDir", "resourceDataDirPathByDocDir", "resourceEncryptCachePath", "userId", "onyxsdk-base_release" })
public final class ResourcePaths
{
    @NotNull
    public static final ResourcePaths INSTANCE;
    
    private ResourcePaths() {
    }
    
    static {
        INSTANCE = new ResourcePaths();
    }
    
    @NotNull
    public final Path appResourceDataDir(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final CommonPaths instance = CommonPaths.INSTANCE;
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                instance.data(instance.resource(instance.appDocDir(documentId))));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Path resourceDataDir(@NotNull final String dirPath, @NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)dirPath, "dirPath");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final CommonPaths instance;
        final CommonPaths commonPaths = instance = CommonPaths.INSTANCE;
        final Paths instance2 = Paths.INSTANCE;
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                commonPaths.data(commonPaths.resource(
                        instance2.append(instance.document(instance2.toPath(dirPath)), documentId))));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Path appDocResourceContentFilePath(@NotNull final String documentId, @NotNull final String fileName) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        Intrinsics.checkNotNullParameter((Object)fileName, "fileName");
        return Paths.INSTANCE.append(this.appDocResourceContentDir(documentId), fileName);
    }
    
    @NotNull
    public final Path appDocResourceContentDir(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        return this.docResourceContentDir(AppPaths.INSTANCE.appDocDirPath().toAbsolutePath().toString(), documentId);
    }
    
    @NotNull
    public final Path docResourceContentFilePath(@NotNull final String docDirPath, @NotNull final String documentId, @NotNull final String fileName) {
        Intrinsics.checkNotNullParameter((Object)docDirPath, "docDirPath");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        Intrinsics.checkNotNullParameter((Object)fileName, "fileName");
        return Paths.INSTANCE.append(this.docResourceContentDir(docDirPath, documentId), fileName);
    }
    
    @NotNull
    public final Path docResourceDataFilePath(@NotNull final String documentId, @NotNull final String fileName) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        Intrinsics.checkNotNullParameter((Object)fileName, "fileName");
        return Paths.INSTANCE.append(this.docResourceDataDir(AppPaths.INSTANCE.appDocDirPath().toAbsolutePath().toString(), documentId), fileName);
    }
    
    @NotNull
    public final Path docResourceDataFilePath(@NotNull final String docDirPath, @NotNull final String documentId, @NotNull final String fileName) {
        Intrinsics.checkNotNullParameter((Object)docDirPath, "docDirPath");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        Intrinsics.checkNotNullParameter((Object)fileName, "fileName");
        return Paths.INSTANCE.append(this.docResourceDataDir(docDirPath, documentId), fileName);
    }
    
    @NotNull
    public final Path docResourceDataDir(@NotNull final String docDirPath, @NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)docDirPath, "docDirPath");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final CommonPaths instance = CommonPaths.INSTANCE;
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                instance.data(instance.resource(instance.docDir(docDirPath, documentId))));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Path docResourceContentDir(@NotNull final String docDirPath, @NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)docDirPath, "docDirPath");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final CommonPaths instance = CommonPaths.INSTANCE;
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                instance.content(instance.resource(instance.docDir(docDirPath, documentId))));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Path resourceDataDirPathByDocDir(@NotNull final String docDirPath, @NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)docDirPath, "docDirPath");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final CommonPaths instance = CommonPaths.INSTANCE;
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                instance.data(instance.resource(instance.docDir(docDirPath, documentId))));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final Path oldResourceDir(@NotNull final String dirPath) {
        Intrinsics.checkNotNullParameter((Object)dirPath, "dirPath");
        return CommonPaths.INSTANCE.resource(Paths.INSTANCE.toPath(dirPath));
    }
    
    @NotNull
    public final Path appResourceDataFilePath(@NotNull final String documentId, @NotNull final String fileName) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        Intrinsics.checkNotNullParameter((Object)fileName, "fileName");
        return Paths.INSTANCE.append(this.appResourceDataDir(documentId), fileName);
    }
    
    @NotNull
    public final Set<String> appDocResourceDataFileNameList(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final Collection listFiles = FileUtils.listFiles(Paths.INSTANCE.asFile(this.appResourceDataDir(documentId)), (String[])null, true);
        final LinkedHashSet set = new LinkedHashSet();
        final Iterator iterator = listFiles.iterator();
        while (iterator.hasNext()) {
            set.add(((File)iterator.next()).getName());
        }
        return set;
    }
    
    @NotNull
    public final Path appResourceDataCopyPath(@NotNull final String documentId, @Nullable final String srcPath) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final String name;
        Intrinsics.checkNotNullExpressionValue((Object)(name = FilenameUtils.getName(srcPath)), "getName(srcPath)");
        return this.appResourceDataFilePath(documentId, name);
    }
    
    @NotNull
    public final String relativePath(@NotNull String documentId, @NotNull final String filePath) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        Intrinsics.checkNotNullParameter((Object)filePath, "filePath");
        if (StringUtils.isEmpty((CharSequence)filePath)) {
            return "";
        }
        if (StringUtils.isNotBlank((CharSequence)filePath)) {
            final KSyncFilePaths instance;
            final String string;
            if (filePath.startsWith(string = this.resourceDataDir((instance = KSyncFilePaths.INSTANCE).getKSyncFilesPath(), documentId).toAbsolutePath().toString())) {
                final String substring = filePath.substring(string.length());
                Intrinsics.checkNotNullExpressionValue((Object)substring, "this as java.lang.String).substring(startIndex)");
                return substring;
            }
            final String string2;
            if (filePath.startsWith(string2 = this.oldResourceDir(instance.getKSyncFilesPath()).toAbsolutePath().toString())) {
                String s2;
                final String s = s2 = filePath.substring(string2.length());
                Intrinsics.checkNotNullExpressionValue((Object)s, "this as java.lang.String).substring(startIndex)");
                if (s.startsWith(documentId = Intrinsics.stringPlus(File.separator, (Object)documentId))) {
                    Intrinsics.checkNotNullExpressionValue((Object)(s2 = s2.substring(documentId.length())), "this as java.lang.String).substring(startIndex)");
                }
                return s2;
            }
        }
        return Intrinsics.stringPlus(File.separator, (Object)FilenameUtils.getName(filePath));
    }
    
    @NotNull
    public final String resourceEncryptCachePath(@NotNull final String userId, @NotNull final String documentId, @NotNull final String fileName) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        Intrinsics.checkNotNullParameter((Object)fileName, "fileName");
        final Paths instance = Paths.INSTANCE;
        return instance.append(instance.append(instance.append(instance.append(instance.toPath(KSyncFilePaths.INSTANCE.getKSyncCacheDir()), "temp_encrypt_dir"), userId), documentId), fileName).toAbsolutePath().toString();
    }
}
