// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.path;

import com.onyx.android.sdk.extension.Paths;
import kotlin.jvm.internal.Intrinsics;
import java.nio.file.Files;
import java.util.Arrays;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006J\u000e\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006J\u0016\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006J\u001e\u0010\r\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006J\u0018\u0010\u000e\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u001e\u0010\u000f\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u0006J\n\u0010\u0010\u001a\u00020\u0004*\u00020\u0004J\n\u0010\u0011\u001a\u00020\u0004*\u00020\u0004¨\u0006\u0012" }, d2 = { "Lcom/onyx/android/sdk/path/CoverPaths;", "", "()V", "appUserNoteCoverResDir", "Ljava/nio/file/Path;", "userId", "", "appUserNoteCoverResPath", "title", "appUserNoteCoverThumbnailResDir", "appUserNoteCoverThumbnailResPath", "userNoteCoverResDir", "dirPath", "userNoteCoverResPath", "userNoteCoverThumbnailResDir", "userNoteCoverThumbnailResPath", "cover", "coverRes", "onyxsdk-base_release" })
public final class CoverPaths
{
    @NotNull
    public static final CoverPaths INSTANCE;
    
    private CoverPaths() {
    }
    
    private final Path a(final String dirPath, final String userId) {
        try {
            return Files.createDirectories(CommonPaths.INSTANCE.thumbnail(
                    this.userNoteCoverResDir(dirPath, userId)));
        } catch (java.io.IOException exception) {
            throw new IllegalStateException(exception);
        }
    }
    
    static {
        INSTANCE = new CoverPaths();
    }
    
    @NotNull
    public final Path cover(@NotNull final Path $this$cover) {
        Intrinsics.checkNotNullParameter((Object)$this$cover, "<this>");
        return Paths.INSTANCE.append($this$cover, "cover");
    }
    
    @NotNull
    public final Path coverRes(@NotNull final Path $this$coverRes) {
        Intrinsics.checkNotNullParameter((Object)$this$coverRes, "<this>");
        return Paths.INSTANCE.append($this$coverRes, "coverRes");
    }
    
    @NotNull
    public final Path appUserNoteCoverResDir(@NotNull final String userId) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        return this.userNoteCoverResDir(AppPaths.INSTANCE.kSyncFilesPath(), userId);
    }
    
    @NotNull
    public final Path appUserNoteCoverThumbnailResDir(@NotNull final String userId) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        return this.a(AppPaths.INSTANCE.kSyncFilesPath(), userId);
    }
    
    @NotNull
    public final Path appUserNoteCoverResPath(@NotNull final String userId, @NotNull final String title) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        Intrinsics.checkNotNullParameter((Object)title, "title");
        return Paths.INSTANCE.append(this.appUserNoteCoverResDir(userId), title);
    }
    
    @NotNull
    public final Path appUserNoteCoverThumbnailResPath(@NotNull final String userId, @NotNull final String title) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        Intrinsics.checkNotNullParameter((Object)title, "title");
        return Paths.INSTANCE.append(this.appUserNoteCoverThumbnailResDir(userId), title);
    }
    
    @NotNull
    public final Path userNoteCoverThumbnailResPath(@NotNull final String dirPath, @NotNull final String userId, @NotNull final String title) {
        Intrinsics.checkNotNullParameter((Object)dirPath, "dirPath");
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        Intrinsics.checkNotNullParameter((Object)title, "title");
        return Paths.INSTANCE.append(this.a(dirPath, userId), title);
    }
    
    @NotNull
    public final Path userNoteCoverResPath(@NotNull final String dirPath, @NotNull final String userId, @NotNull final String title) {
        Intrinsics.checkNotNullParameter((Object)dirPath, "dirPath");
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        Intrinsics.checkNotNullParameter((Object)title, "title");
        return Paths.INSTANCE.append(this.userNoteCoverResDir(dirPath, userId), title);
    }
    
    @NotNull
    public final Path userNoteCoverResDir(@NotNull final String dirPath, @NotNull final String userId) {
        Intrinsics.checkNotNullParameter((Object)dirPath, "dirPath");
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        try {
            return Files.createDirectories(this.coverRes(
                    CommonPaths.INSTANCE.userNoteDir(dirPath, userId)));
        } catch (java.io.IOException exception) {
            throw new IllegalStateException(exception);
        }
    }
}
