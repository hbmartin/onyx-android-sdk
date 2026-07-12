// 
// 

package com.onyx.android.sdk.path;

import com.onyx.android.sdk.extension.Files;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0004J\u001e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004¨\u0006\b" }, d2 = { "Lcom/onyx/android/sdk/path/ContentPaths;", "", "()V", "contentAbsolutePath", "", "userId", "contentType", "contentRelativePath", "onyxsdk-base_release" })
public final class ContentPaths
{
    @NotNull
    public static final ContentPaths INSTANCE;
    
    private ContentPaths() {
    }
    
    static {
        INSTANCE = new ContentPaths();
    }
    
    @NotNull
    public final String contentAbsolutePath(@NotNull final String userId, @NotNull final String contentType, @NotNull final String contentRelativePath) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        Intrinsics.checkNotNullParameter((Object)contentType, "contentType");
        Intrinsics.checkNotNullParameter((Object)contentRelativePath, "contentRelativePath");
        String $this$isFileExist;
        if (Intrinsics.areEqual((Object)contentType, (Object)"pdf")) {
            $this$isFileExist = TemplatePaths.INSTANCE.templateResAbsolutePath(contentRelativePath, userId);
        }
        else {
            $this$isFileExist = AppPaths.INSTANCE.filesDirAbsolutePath(contentRelativePath);
        }
        if (!Files.INSTANCE.isFileExist($this$isFileExist)) {
            $this$isFileExist = AppPaths.INSTANCE.filesDirAbsolutePath(contentRelativePath);
        }
        return $this$isFileExist;
    }
    
    @NotNull
    public final String contentRelativePath(@NotNull final String userId, @NotNull final String contentType, @NotNull final String contentAbsolutePath) {
        Intrinsics.checkNotNullParameter((Object)userId, "userId");
        Intrinsics.checkNotNullParameter((Object)contentType, "contentType");
        Intrinsics.checkNotNullParameter((Object)contentAbsolutePath, "contentAbsolutePath");
        return Intrinsics.areEqual((Object)contentType, (Object)"pdf") ? TemplatePaths.INSTANCE.templateResRelativePath(contentAbsolutePath, userId) : AppPaths.INSTANCE.filesDirRelativePath(contentAbsolutePath);
    }
}
