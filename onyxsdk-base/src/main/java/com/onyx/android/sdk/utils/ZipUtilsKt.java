// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.utils;

import net.lingala.zip4j.ZipFile;
import java.io.InputStream;
import com.onyx.android.sdk.commons.io.FileUtils;
import java.io.File;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006J\u0016\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0006J\u0016\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006¨\u0006\f" }, d2 = { "Lcom/onyx/android/sdk/utils/ZipUtilsKt;", "", "()V", "decompress", "", "zipFileName", "", "extractDirectory", "decompressAssetToFolder", "asset", "directory", "decompressSafely", "onyxsdk-base_release" })
public final class ZipUtilsKt
{
    @NotNull
    public static final ZipUtilsKt INSTANCE;
    
    private ZipUtilsKt() {
    }
    
    static {
        INSTANCE = new ZipUtilsKt();
    }
    
    public final void decompressAssetToFolder(@NotNull final String asset, @NotNull final String directory) {
        Intrinsics.checkNotNullParameter((Object)asset, "asset");
        Intrinsics.checkNotNullParameter((Object)directory, "directory");
        final String string = ResManager.getAppContext().getDataDir().getAbsolutePath() + '/' + (Object)UUIDUtils.randomUUID() + ".zip";
        try (InputStream open = ResManager.getAppContext().getAssets().open(asset)) {
            FileUtils.copyToFile(open, new File(string));
            this.decompress(string, directory);
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        } finally {
            FileUtils.deleteQuietly(new File(string));
        }
    }
    
    public final void decompress(@NotNull final String zipFileName, @NotNull final String extractDirectory) {
        Intrinsics.checkNotNullParameter((Object)zipFileName, "zipFileName");
        Intrinsics.checkNotNullParameter((Object)extractDirectory, "extractDirectory");
        try {
            new ZipFile(zipFileName).extractAll(extractDirectory);
        } catch (net.lingala.zip4j.exception.ZipException exception) {
            throw new IllegalStateException(exception);
        }
    }
    
    public final void decompressSafely(@NotNull final String zipFileName, @NotNull final String extractDirectory) {
        Intrinsics.checkNotNullParameter((Object)zipFileName, "zipFileName");
        Intrinsics.checkNotNullParameter((Object)extractDirectory, "extractDirectory");
        try {
            this.decompress(zipFileName, extractDirectory);
        }
        catch (final Exception ex) {
            Debug.e((Class)ZipUtilsKt.class, Intrinsics.stringPlus(zipFileName, (Object)" decompress fail"), (Throwable)ex);
        }
    }
}
