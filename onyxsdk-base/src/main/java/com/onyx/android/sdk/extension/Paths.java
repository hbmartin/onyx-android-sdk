// 
// 

package com.onyx.android.sdk.extension;

import kotlin.internal.ProgressionUtilKt;
import com.onyx.android.sdk.utils.MathUtilsKt;
import android.graphics.PathMeasure;
import java.util.ArrayList;
import com.onyx.android.sdk.base.data.TouchPoint;
import kotlin.collections.CollectionsKt;
import kotlin.io.path.PathsKt;
import kotlin.text.StringsKt;
import java.nio.file.LinkOption;
import com.onyx.android.sdk.utils.Debug;
import java.nio.file.attribute.FileAttribute;
import com.onyx.android.sdk.utils.FileUtils;
import java.util.Arrays;
import org.jetbrains.annotations.Nullable;
import java.io.File;
import com.onyx.android.sdk.utils.Dimens;
import java.util.List;
import java.util.Collection;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0004\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u0007\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004J\u0014\u0010\t\u001a\u00020\u0006*\u00020\u00062\b\u0010\n\u001a\u0004\u0018\u00010\u0004J\n\u0010\u000b\u001a\u00020\f*\u00020\u0006J\u001c\u0010\r\u001a\u00020\u000e*\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u00062\b\b\u0002\u0010\u0010\u001a\u00020\u000eJ+\u0010\u0011\u001a\u00020\u0006*\u00020\u00062\u001a\u0010\u0012\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00140\u0013\"\u0006\u0012\u0002\b\u00030\u0014¢\u0006\u0002\u0010\u0015J\n\u0010\u0016\u001a\u00020\u0017*\u00020\u0006J\n\u0010\u0018\u001a\u00020\u000e*\u00020\u0006J\n\u0010\u0019\u001a\u00020\u0006*\u00020\u0006J\n\u0010\u001a\u001a\u00020\u000e*\u00020\u0006J\u001a\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\f0\u001c*\u00020\u00062\b\b\u0002\u0010\u001d\u001a\u00020\u000eJ\u0012\u0010\u001e\u001a\u00020\u0004*\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004J\u001a\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020!0 *\u00020\"2\b\b\u0002\u0010#\u001a\u00020$J\n\u0010%\u001a\u00020\u0006*\u00020\u0004¨\u0006&" }, d2 = { "Lcom/onyx/android/sdk/extension/Paths;", "", "()V", "absolutePathString", "", "path", "Ljava/nio/file/Path;", "absolutePath", "pathPrefix", "append", "node", "asFile", "Ljava/io/File;", "copyFile", "", "dstPath", "override", "createParentDirectories", "attributes", "", "Ljava/nio/file/attribute/FileAttribute;", "(Ljava/nio/file/Path;[Ljava/nio/file/attribute/FileAttribute;)Ljava/nio/file/Path;", "deleteDirectory", "", "deleteFile", "getParentCompat", "isFile", "listFiles", "", "recursive", "relativePath", "resample", "", "Lcom/onyx/android/sdk/base/data/TouchPoint;", "Landroid/graphics/Path;", "step", "", "toPath", "onyxsdk-base_release" })
public final class Paths
{
    @NotNull
    public static final Paths INSTANCE;
    
    private Paths() {
    }
    
    @JvmStatic
    @NotNull
    public static final String absolutePathString(@NotNull final Path path) {
        Intrinsics.checkNotNullParameter((Object)path, "path");
        return path.toAbsolutePath().toString();
    }
    
    public static /* synthetic */ boolean copyFile$default(final Paths paths, final Path $this$copyFile, final Path dstPath, boolean override, final int n, final Object o) {
        if ((n & 0x2) != 0x0) {
            override = false;
        }
        return paths.copyFile($this$copyFile, dstPath, override);
    }

    public static /* synthetic */ Collection listFiles$default(final Paths paths, final Path $this$listFiles, boolean recursive, final int n, final Object o) {
        if ((n & 0x1) != 0x0) {
            recursive = true;
        }
        return paths.listFiles($this$listFiles, recursive);
    }

    public static /* synthetic */ List resample$default(final Paths paths, final android.graphics.Path $this$resample, Number step, final int n, final Object o) {
        if ((n & 0x1) != 0x0) {
            step = Dimens.INSTANCE.dp2PxFloat(2.0f);
        }
        return paths.resample($this$resample, step);
    }

    static {
        INSTANCE = new Paths();
    }

    @NotNull
    public final Path toPath(@NotNull final String $this$toPath) {
        Intrinsics.checkNotNullParameter((Object)$this$toPath, "<this>");
        final Path value = java.nio.file.Paths.get($this$toPath, new String[0]);
        Intrinsics.checkNotNullExpressionValue((Object)value, "get(path)");
        return value;
    }
    
    @NotNull
    public final File asFile(@NotNull final Path $this$asFile) {
        Intrinsics.checkNotNullParameter((Object)$this$asFile, "<this>");
        return new File($this$asFile.toAbsolutePath().toString());
    }
    
    @NotNull
    public final Path append(@NotNull final Path $this$append, @Nullable final String node) {
        Intrinsics.checkNotNullParameter((Object)$this$append, "<this>");
        if (node == null) {
            return $this$append;
        }
        final Path value = java.nio.file.Paths.get($this$append.toAbsolutePath().toString(), (String[])Arrays.copyOf(new String[] { node }, 1));
        Intrinsics.checkNotNullExpressionValue((Object)value, "get(base, *subpaths)");
        return value;
    }
    
    public final boolean deleteFile(@NotNull final Path $this$deleteFile) {
        Intrinsics.checkNotNullParameter((Object)$this$deleteFile, "<this>");
        return FileUtils.deleteFile($this$deleteFile.toAbsolutePath().toString());
    }
    
    @NotNull
    public final Path getParentCompat(@NotNull final Path $this$getParentCompat) {
        Intrinsics.checkNotNullParameter((Object)$this$getParentCompat, "<this>");
        final String parent = FileUtils.getParent($this$getParentCompat.toAbsolutePath().toString());
        Intrinsics.checkNotNullExpressionValue((Object)parent, "getParent(filePath)");
        return this.toPath(parent);
    }
    
    public final void deleteDirectory(@NotNull final Path $this$deleteDirectory) {
        Intrinsics.checkNotNullParameter((Object)$this$deleteDirectory, "<this>");
        Files.INSTANCE.deleteDirectory($this$deleteDirectory.toAbsolutePath().toString());
    }
    
    @NotNull
    public final Path createParentDirectories(@NotNull final Path $this$createParentDirectories, @NotNull final FileAttribute<?>... attributes) {
        Intrinsics.checkNotNullParameter((Object)$this$createParentDirectories, "<this>");
        Intrinsics.checkNotNullParameter((Object)attributes, "attributes");
        final File parentFile = asFile($this$createParentDirectories).getParentFile();
        if (parentFile != null && !parentFile.isDirectory()) {
            try {
                final String absolutePath = parentFile.getAbsolutePath();
                Intrinsics.checkNotNullExpressionValue((Object)absolutePath, "parent.absolutePath");
                final Path path = toPath(absolutePath);
                final FileAttribute<?>[] original = Arrays.copyOf(attributes, attributes.length);
                Intrinsics.checkNotNullExpressionValue((Object)java.nio.file.Files.createDirectories(path, (FileAttribute<?>[])Arrays.copyOf(original, original.length)), "createDirectories(this, *attributes)");
            }
            catch (final Exception ex) {
                Debug.e((Throwable)ex);
            }
        }
        return $this$createParentDirectories;
    }
    
    public final boolean copyFile(@NotNull final Path $this$copyFile, @NotNull final Path dstPath, final boolean override) {
        Intrinsics.checkNotNullParameter((Object)$this$copyFile, "<this>");
        Intrinsics.checkNotNullParameter((Object)dstPath, "dstPath");
        if (java.nio.file.Files.notExists($this$copyFile, (LinkOption[])Arrays.copyOf(new LinkOption[0], 0))) {
            return false;
        }
        if (!override && java.nio.file.Files.exists(dstPath, (LinkOption[])Arrays.copyOf(new LinkOption[0], 0))) {
            return false;
        }
        if (override && java.nio.file.Files.exists(dstPath, (LinkOption[])Arrays.copyOf(new LinkOption[0], 0))) {
            this.deleteFile(dstPath);
        }
        try {
            com.onyx.android.sdk.commons.io.FileUtils.copyFile(this.asFile($this$copyFile), this.asFile(dstPath));
            return true;
        }
        catch (final Exception ex) {
            Debug.e((Throwable)ex);
            return false;
        }
    }
    
    @NotNull
    public final String relativePath(@NotNull final String $this$relativePath, @NotNull final String pathPrefix) {
        Intrinsics.checkNotNullParameter((Object)$this$relativePath, "<this>");
        Intrinsics.checkNotNullParameter((Object)pathPrefix, "pathPrefix");
        if (!$this$relativePath.startsWith(pathPrefix)) {
            return $this$relativePath;
        }
        final Path value = java.nio.file.Paths.get($this$relativePath, new String[0]);
        Intrinsics.checkNotNullExpressionValue((Object)value, "get(path)");
        return PathsKt.relativeTo(value, this.toPath(pathPrefix)).toString();
    }
    
    @NotNull
    public final String absolutePath(@NotNull final String $this$absolutePath, @NotNull final String pathPrefix) {
        Intrinsics.checkNotNullParameter((Object)$this$absolutePath, "<this>");
        Intrinsics.checkNotNullParameter((Object)pathPrefix, "pathPrefix");
        if ($this$absolutePath.startsWith(pathPrefix)) {
            return $this$absolutePath;
        }
        return this.append(this.toPath(pathPrefix), $this$absolutePath).toAbsolutePath().toString();
    }
    
    @NotNull
    public final Collection<File> listFiles(@NotNull final Path $this$listFiles, final boolean recursive) {
        Intrinsics.checkNotNullParameter((Object)$this$listFiles, "<this>");
        if (java.nio.file.Files.notExists($this$listFiles, (LinkOption[])Arrays.copyOf(new LinkOption[0], 0))) {
            return CollectionsKt.emptyList();
        }
        Collection collection;
        if ((collection = com.onyx.android.sdk.commons.io.FileUtils.listFiles(this.asFile($this$listFiles), (String[])null, recursive)) == null) {
            collection = CollectionsKt.emptyList();
        }
        return collection;
    }
    
    public final boolean isFile(@NotNull final Path $this$isFile) {
        Intrinsics.checkNotNullParameter((Object)$this$isFile, "<this>");
        return this.asFile($this$isFile).isFile();
    }
    
    @NotNull
    public final List<TouchPoint> resample(@NotNull final android.graphics.Path $this$resample, @NotNull final Number step) {
        Intrinsics.checkNotNullParameter((Object)$this$resample, "<this>");
        Intrinsics.checkNotNullParameter((Object)step, "step");
        final ArrayList<TouchPoint> list = new ArrayList<TouchPoint>();
        final PathMeasure pathMeasure2;
        final PathMeasure pathMeasure = pathMeasure2 = new PathMeasure($this$resample, (boolean)(0 != 0));
        final float[] array = new float[2];
        final int n = (int)(pathMeasure.getLength() + step.floatValue() - 1);
        final int intValue;
        if ((intValue = MathUtilsKt.INSTANCE.atLeast(step.intValue(), 1).intValue()) > 0) {
            int i = 0;
            final int progressionLastElement;
            if ((progressionLastElement = ProgressionUtilKt.getProgressionLastElement(0, n, intValue)) >= 0) {
                while (true) {
                    final PathMeasure pathMeasure3 = pathMeasure2;
                    final int n2 = i + intValue;
                    if (pathMeasure3.getPosTan((float)MathUtilsKt.INSTANCE.atMost(i, (int)pathMeasure2.getLength()).intValue(), array, (float[])null)) {
                        final ArrayList<TouchPoint> list2 = list;
                        final float[] array2 = array;
                        final TouchPoint touchPoint = new TouchPoint(array2[0], array2[1]);
                        list2.add(touchPoint);
                    }
                    if (i == progressionLastElement) {
                        break;
                    }
                    i = n2;
                }
            }
            return list;
        }
        throw new IllegalArgumentException("Step must be positive, was: " + intValue + '.');
    }
}
