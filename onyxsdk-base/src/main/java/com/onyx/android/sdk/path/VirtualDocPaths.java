// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.path;

import java.nio.file.Files;
import java.util.Arrays;
import java.nio.file.attribute.FileAttribute;
import com.onyx.android.sdk.extension.Paths;
import kotlin.jvm.internal.Intrinsics;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u000e\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\n" }, d2 = { "Lcom/onyx/android/sdk/path/VirtualDocPaths;", "", "()V", "appDocPBFileDir", "Ljava/nio/file/Path;", "documentId", "", "appDocPBFilePath", "docPBFileDir", "pbDirPath", "onyxsdk-base_release" })
public final class VirtualDocPaths
{
    @NotNull
    public static final VirtualDocPaths INSTANCE;
    
    private VirtualDocPaths() {
    }
    
    private final Path a(final String documentId) {
        return this.docPBFileDir(AppPaths.INSTANCE.appDocDir(), documentId);
    }
    
    static {
        INSTANCE = new VirtualDocPaths();
    }
    
    @NotNull
    public final Path appDocPBFilePath(@NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        return Paths.INSTANCE.append(this.a(documentId), documentId);
    }
    
    @NotNull
    public final Path docPBFileDir(@NotNull final String pbDirPath, @NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)pbDirPath, "pbDirPath");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final CommonPaths instance = CommonPaths.INSTANCE;
        final Path directories = CommonPaths.INSTANCE.ensureCreateDirectories(
                instance.pb(instance.doc(instance.virtual(instance.docDir(pbDirPath, documentId)))));
        Intrinsics.checkNotNullExpressionValue((Object)directories, "createDirectories(this, *attributes)");
        return directories;
    }
}
