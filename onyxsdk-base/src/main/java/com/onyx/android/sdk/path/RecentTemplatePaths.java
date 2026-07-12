// 
// 

package com.onyx.android.sdk.path;

import java.io.File;
import com.onyx.android.sdk.utils.FileUtils;
import com.onyx.android.sdk.extension.Paths;
import kotlin.jvm.internal.Intrinsics;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006J\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006¨\u0006\r" }, d2 = { "Lcom/onyx/android/sdk/path/RecentTemplatePaths;", "", "()V", "appUserRecentTemplateDir", "Ljava/nio/file/Path;", "userId", "", "appUserRecentTemplatePdfPath", "fileName", "appUserRecentTemplateThumbnailDir", "appUserRecentTemplateThumbnailPath", "deleteRecentTemplateFile", "", "onyxsdk-base_release" })
public final class RecentTemplatePaths
{
    @NotNull
    public static final RecentTemplatePaths INSTANCE;
    
    private RecentTemplatePaths() {
    }
    
    static {
        INSTANCE = new RecentTemplatePaths();
    }
    
    @NotNull
    public final Path appUserRecentTemplateDir(@NotNull final String userId) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        return Paths.INSTANCE.append(TemplatePaths.INSTANCE.template(CommonPaths.INSTANCE.userNoteDir(AppPaths.INSTANCE.kSyncFilesPath(), userId)), "recent");
    }
    
    @NotNull
    public final Path appUserRecentTemplateThumbnailDir(@NotNull final String userId) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        return Paths.INSTANCE.append(this.appUserRecentTemplateDir(userId), "thumbnail");
    }
    
    @NotNull
    public final Path appUserRecentTemplatePdfPath(@NotNull final String userId, @NotNull final String fileName) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        Intrinsics.checkNotNullParameter((Object)fileName, "fileName");
        return Paths.INSTANCE.append(this.appUserRecentTemplateDir(userId), Intrinsics.stringPlus(fileName, (Object)".pdf"));
    }
    
    @NotNull
    public final Path appUserRecentTemplateThumbnailPath(@NotNull final String userId, @NotNull final String fileName) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        Intrinsics.checkNotNullParameter((Object)fileName, "fileName");
        return Paths.INSTANCE.append(this.appUserRecentTemplateThumbnailDir(userId), fileName);
    }
    
    public final void deleteRecentTemplateFile(@NotNull final String userId, @NotNull final String fileName) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        Intrinsics.checkNotNullParameter((Object)fileName, "fileName");
        final Paths instance = Paths.INSTANCE;
        final File file = instance.asFile(this.appUserRecentTemplatePdfPath(userId, fileName));
        final File file2 = instance.asFile(this.appUserRecentTemplateThumbnailPath(userId, fileName));
        FileUtils.deleteFile(file);
        FileUtils.deleteFile(file2);
    }
}
