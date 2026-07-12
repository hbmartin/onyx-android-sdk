// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.path;

import kotlin.text.StringsKt;
import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.nio.file.attribute.FileAttribute;
import com.onyx.android.sdk.extension.Paths;
import java.nio.file.Path;
import kotlin.jvm.internal.Intrinsics;
import com.onyx.android.sdk.base.utils.ResManager;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u000f\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0007\u001a\u00020\u0004J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0004J\u0006\u0010\n\u001a\u00020\bJ\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u0004J\u0006\u0010\r\u001a\u00020\u0004J\u0006\u0010\u000e\u001a\u00020\bJ\u000e\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0004J\u0016\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u0012\u001a\u00020\u0004J\u000e\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0004J\u0006\u0010\u0014\u001a\u00020\u0004J\n\u0010\u0015\u001a\u00020\u0004*\u00020\u0004J\n\u0010\u0016\u001a\u00020\u0004*\u00020\u0004R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0017" }, d2 = { "Lcom/onyx/android/sdk/path/AppPaths;", "", "()V", "FILES_PATH", "", "getFILES_PATH", "()Ljava/lang/String;", "appDocDir", "Ljava/nio/file/Path;", "docId", "appDocDirPath", "appFilesDir", "subDir", "appFilesPath", "appFontDir", "appPBDir", "dirPath", "appPBPath", "revisionId", "fontDir", "kSyncFilesPath", "filesDirAbsolutePath", "filesDirRelativePath", "onyxsdk-base_release" })
public final class AppPaths
{
    @NotNull
    public static final AppPaths INSTANCE;
    @NotNull
    private static final String a;
    
    private AppPaths() {
    }
    
    static {
        INSTANCE = new AppPaths();
        final String absolutePath = ResManager.INSTANCE.getAppContext().getFilesDir().getAbsolutePath();
        Intrinsics.checkNotNullExpressionValue((Object)absolutePath, "ResManager.getAppContext().filesDir.absolutePath");
        a = absolutePath;
    }
    
    @NotNull
    public final String getFILES_PATH() {
        return AppPaths.a;
    }
    
    @NotNull
    public final String appFilesPath() {
        return AppPaths.a;
    }
    
    @NotNull
    public final Path appFontDir() {
        return this.fontDir(this.kSyncFilesPath());
    }
    
    @NotNull
    public final Path fontDir(@NotNull final String dirPath) {
        Intrinsics.checkNotNullParameter((Object)dirPath, "dirPath");
        final CommonPaths instance = CommonPaths.INSTANCE;
        return instance.font(instance.app(Paths.INSTANCE.toPath(dirPath)));
    }
    
    @NotNull
    public final Path appPBPath(@NotNull final String dirPath, @NotNull final String revisionId) {
        Intrinsics.checkNotNullParameter((Object)dirPath, "dirPath");
        Intrinsics.checkNotNullParameter((Object)revisionId, "revisionId");
        return Paths.INSTANCE.append(this.appPBDir(dirPath), revisionId);
    }
    
    @NotNull
    public final Path appPBDir(@NotNull final String dirPath) {
        Intrinsics.checkNotNullParameter((Object)dirPath, "dirPath");
        final CommonPaths instance = CommonPaths.INSTANCE;
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                instance.pb(instance.app(Paths.INSTANCE.toPath(dirPath))));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final String appDocDir() {
        return this.kSyncFilesPath() + (Object)File.separator + "document";
    }
    
    @NotNull
    public final Path appDocDirPath() {
        return this.appFilesDir("document");
    }
    
    @NotNull
    public final Path appDocDir(@NotNull final String docId) {
        Intrinsics.checkNotNullParameter((Object)docId, "docId");
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                Paths.INSTANCE.append(this.appDocDirPath(), docId));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final String kSyncFilesPath() {
        return KSyncFilePaths.INSTANCE.getKSyncFilesPath();
    }
    
    @NotNull
    public final Path appFilesDir(@NotNull final String subDir) {
        Intrinsics.checkNotNullParameter((Object)subDir, "subDir");
        final Paths instance = Paths.INSTANCE;
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                instance.append(instance.toPath(this.kSyncFilesPath()), subDir));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
    
    @NotNull
    public final String filesDirRelativePath(@NotNull final String $this$filesDirRelativePath) {
        Intrinsics.checkNotNullParameter((Object)$this$filesDirRelativePath, "<this>");
        return Paths.INSTANCE.relativePath($this$filesDirRelativePath, this.kSyncFilesPath());
    }
    
    @NotNull
    public final String filesDirAbsolutePath(@NotNull final String $this$filesDirAbsolutePath) {
        Intrinsics.checkNotNullParameter((Object)$this$filesDirAbsolutePath, "<this>");
        if ($this$filesDirAbsolutePath.startsWith(CommonPaths.INSTANCE.getExternalStorageDir())) {
            return $this$filesDirAbsolutePath;
        }
        return Paths.INSTANCE.absolutePath($this$filesDirAbsolutePath, this.kSyncFilesPath());
    }
}
