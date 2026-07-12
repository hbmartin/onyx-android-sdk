// 
// 

package com.onyx.android.sdk.path;

import com.onyx.android.sdk.extension.Paths;
import java.nio.file.Path;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\t\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u0004J\u000e\u0010\r\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u0004J\u001e\u0010\u000f\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u0004J\u000e\u0010\u0011\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\u0004J\u0016\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0004J\u0016\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0010\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0004R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\u0013" }, d2 = { "Lcom/onyx/android/sdk/path/ThumbnailPaths;", "", "()V", "THUMBNAIL_FILE_EXTENSION", "", "getTHUMBNAIL_FILE_EXTENSION", "()Ljava/lang/String;", "setTHUMBNAIL_FILE_EXTENSION", "(Ljava/lang/String;)V", "appPageThumbnailPath", "Ljava/nio/file/Path;", "documentId", "pageId", "appThumbnailPath", "documentUniqueId", "pageThumbnailPath", "dir", "thumbnailCachePath", "thumbnailPath", "onyxsdk-base_release" })
public final class ThumbnailPaths
{
    @NotNull
    public static final ThumbnailPaths INSTANCE;
    @NotNull
    private static String a;
    
    private ThumbnailPaths() {
    }
    
    static {
        INSTANCE = new ThumbnailPaths();
        ThumbnailPaths.a = ".png";
    }
    
    @NotNull
    public final String getTHUMBNAIL_FILE_EXTENSION() {
        return ThumbnailPaths.a;
    }
    
    public final void setTHUMBNAIL_FILE_EXTENSION(@NotNull final String value) {
        Intrinsics.checkNotNullParameter((Object)value, "<set-?>");
        ThumbnailPaths.a = value;
    }
    
    @NotNull
    public final Path appThumbnailPath(@NotNull final String documentUniqueId) {
        Intrinsics.checkNotNullParameter((Object)documentUniqueId, "documentUniqueId");
        return this.thumbnailPath(AppPaths.INSTANCE.appDocDirPath().toAbsolutePath().toString(), documentUniqueId);
    }
    
    @NotNull
    public final Path thumbnailPath(@NotNull final String dir, @NotNull final String documentUniqueId) {
        Intrinsics.checkNotNullParameter((Object)dir, "dir");
        Intrinsics.checkNotNullParameter((Object)documentUniqueId, "documentUniqueId");
        final Paths instance = Paths.INSTANCE;
        return instance.append(instance.append(instance.toPath(dir), documentUniqueId), Intrinsics.stringPlus(documentUniqueId, (Object)ThumbnailPaths.a));
    }
    
    @NotNull
    public final Path appPageThumbnailPath(@NotNull final String documentId, @NotNull final String pageId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        Intrinsics.checkNotNullParameter((Object)pageId, "pageId");
        return this.pageThumbnailPath(AppPaths.INSTANCE.appDocDirPath().toAbsolutePath().toString(), documentId, pageId);
    }
    
    @NotNull
    public final Path pageThumbnailPath(@NotNull final String dir, @NotNull final String documentUniqueId, @NotNull final String pageId) {
        Intrinsics.checkNotNullParameter((Object)dir, "dir");
        Intrinsics.checkNotNullParameter((Object)documentUniqueId, "documentUniqueId");
        Intrinsics.checkNotNullParameter((Object)pageId, "pageId");
        final Paths instance = Paths.INSTANCE;
        return instance.append(instance.append(instance.toPath(dir), documentUniqueId), Intrinsics.stringPlus(pageId, (Object)ThumbnailPaths.a));
    }
    
    @NotNull
    public final Path thumbnailCachePath(@NotNull final String documentUniqueId) {
        Intrinsics.checkNotNullParameter((Object)documentUniqueId, "documentUniqueId");
        return this.thumbnailCachePath(NoteCommonPaths.INSTANCE.getNoteThumbnailDir(), documentUniqueId);
    }
    
    @NotNull
    public final Path thumbnailCachePath(@NotNull final String dir, @NotNull final String documentUniqueId) {
        Intrinsics.checkNotNullParameter((Object)dir, "dir");
        Intrinsics.checkNotNullParameter((Object)documentUniqueId, "documentUniqueId");
        final Paths instance = Paths.INSTANCE;
        return instance.append(instance.toPath(dir), Intrinsics.stringPlus(documentUniqueId, (Object)ThumbnailPaths.a));
    }
}
