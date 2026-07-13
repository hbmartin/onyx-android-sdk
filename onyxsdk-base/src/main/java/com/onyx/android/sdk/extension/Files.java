
package com.onyx.android.sdk.extension;

import kotlin.ranges.RangesKt;
import java.util.Collection;
import java.util.LinkedList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.io.FileAlreadyExistsException;
import java.util.UUID;
import com.onyx.android.sdk.base.utils.Debug;
import java.io.OutputStream;
import com.onyx.android.sdk.commons.io.IOUtils;
import java.io.Closeable;
import kotlin.io.CloseableKt;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import kotlin.collections.ArraysKt;
import com.onyx.android.sdk.commons.io.FilenameUtils;
import org.jetbrains.annotations.Nullable;
import java.util.Iterator;
import java.util.ArrayList;
import kotlin.collections.CollectionsKt;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.functions.Function1;
import java.util.List;
import com.onyx.android.sdk.utils.FileUtils;
import android.content.Context;
import java.io.FileFilter;
import java.io.File;
import java.util.Comparator;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010 \n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\f\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u0004J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u001e\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u0011J\u001a\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001b\u001a\u0004\u0018\u00010\u0007J\u001a\u0010\u001c\u001a\u00020\u00182\b\u0010\u001d\u001a\u0004\u0018\u00010\u001a2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0007J\u000e\u0010\u001f\u001a\u00020\u000f2\u0006\u0010 \u001a\u00020\u0007J\"\u0010!\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00152\b\u0010\"\u001a\u0004\u0018\u00010\u00112\b\b\u0002\u0010#\u001a\u00020\u0011J*\u0010$\u001a\u00020\u00112\u0006\u0010%\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020\u00112\b\b\u0002\u0010&\u001a\u00020\u00112\b\b\u0002\u0010'\u001a\u00020\u0004J\u000e\u0010(\u001a\u00020\u00112\u0006\u0010)\u001a\u00020\u0011J\u0016\u0010*\u001a\b\u0012\u0004\u0012\u00020\u00070+2\b\u0010#\u001a\u0004\u0018\u00010\u0011J\u001a\u0010,\u001a\u00020\u00112\b\u0010#\u001a\u0004\u0018\u00010\u00112\b\b\u0002\u0010-\u001a\u00020\u0013J\u0010\u0010.\u001a\u00020\u00112\b\u0010#\u001a\u0004\u0018\u00010\u0011J\u0016\u0010/\u001a\u00020\u00132\u0006\u00100\u001a\u00020\u00112\u0006\u00101\u001a\u00020\u0007J\f\u00102\u001a\u00020\u000f*\u0004\u0018\u00010\u0011J(\u00103\u001a\u00020\u000f*\u00020\u00072\u0010\b\u0002\u00104\u001a\n\u0012\u0004\u0012\u00020\u0007\u0018\u00010\u00062\n\b\u0002\u00105\u001a\u0004\u0018\u000106J\f\u00107\u001a\u00020\u0013*\u0004\u0018\u00010\u0011J\f\u00108\u001a\u00020\u0013*\u0004\u0018\u00010\u0011J\f\u00109\u001a\u00020\u0011*\u0004\u0018\u00010\u0011J\f\u0010:\u001a\u00020\u0011*\u0004\u0018\u00010\u0011J\f\u0010;\u001a\u00020\u0013*\u0004\u0018\u00010\u0011J\f\u0010<\u001a\u00020\u0013*\u0004\u0018\u00010\u0011JO\u0010=\u001a\b\u0012\u0004\u0012\u0002H>0+\"\u0004\b\u0000\u0010?\"\u0004\b\u0001\u0010>*\b\u0012\u0004\u0012\u0002H?0+2\b\b\u0002\u0010@\u001a\u00020\u00042!\u0010A\u001a\u001d\u0012\u0013\u0012\u0011H?¢\u0006\f\bC\u0012\b\bD\u0012\u0004\b\b(E\u0012\u0004\u0012\u0002H>0BR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006F" }, d2 = { "Lcom/onyx/android/sdk/extension/Files;", "", "()V", "DEFAULT_PARALLEL_CHUNKED_SIZE", "", "FILE_MODIFIED_ASC_COMPARATOR", "Ljava/util/Comparator;", "Ljava/io/File;", "getFILE_MODIFIED_ASC_COMPARATOR", "()Ljava/util/Comparator;", "FILE_MODIFIED_DESC_COMPARATOR", "getFILE_MODIFIED_DESC_COMPARATOR", "calculateChunk", "total", "clearEmptyFile", "", "localFilePath", "", "copyAssetsFile", "", "context", "Landroid/content/Context;", "assetPath", "copyInputStreamToFile", "", "source", "Ljava/io/InputStream;", "destination", "copyToFile", "inputStream", "file", "deleteDirSafely", "directory", "fileNameInAssets", "fileBaseName", "path", "generateUniqueFileName", "fileDir", "fileExtension", "tryTimes", "getValidFileName", "fileName", "listAllFiles", "", "readContentOfFile", "keepLineBreak", "readContentOfFileSafely", "saveContentWithDiskSync", "content", "targetFile", "deleteDirectory", "deleteDirectoryQuietly", "comparator", "fileFilter", "Ljava/io/FileFilter;", "deleteFile", "deleteFileExt", "getFileBaseName", "getFileName", "isDirectory", "isFileExist", "parallelFileMetadata", "Result", "PathInfo", "chunkedSize", "consumer", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "pathInfo", "onyxsdk-base_release" })
public final class Files
{
    @NotNull
    public static final Files INSTANCE;
    private static final int a = 500;
    @NotNull
    private static final Comparator<File> b;
    @NotNull
    private static final Comparator<File> c;
    
    private Files() {
    }
    
    public static /* synthetic */ void deleteDirectoryQuietly$default(final Files files, final File $this$deleteDirectoryQuietly, Comparator comparator, FileFilter fileFilter, final int n, final Object o) {
        if ((n & 0x1) != 0x0) {
            comparator = null;
        }
        if ((n & 0x2) != 0x0) {
            fileFilter = null;
        }
        files.deleteDirectoryQuietly($this$deleteDirectoryQuietly, comparator, fileFilter);
    }

    public static /* synthetic */ String fileNameInAssets$default(final Files files, final Context context, final String fileBaseName, String path, final int n, final Object o) {
        if ((n & 0x4) != 0x0) {
            path = "";
        }
        return files.fileNameInAssets(context, fileBaseName, path);
    }

    private final void a(final String localFilePath) {
        try {
            if (!FileUtils.fileExist(localFilePath)) {
                return;
            }
            if (FileUtils.getFileSize(localFilePath) == 0L) {
                FileUtils.deleteFile(localFilePath);
            }
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    public static /* synthetic */ String generateUniqueFileName$default(final Files files, final String fileDir, final String fileBaseName, String fileExtension, int tryTimes, final int n, final Object o) {
        if ((n & 0x4) != 0x0) {
            fileExtension = "";
        }
        if ((n & 0x8) != 0x0) {
            tryTimes = 254;
        }
        return files.generateUniqueFileName(fileDir, fileBaseName, fileExtension, tryTimes);
    }

    public static /* synthetic */ String readContentOfFile$default(final Files files, final String path, boolean keepLineBreak, final int n, final Object o) {
        if ((n & 0x2) != 0x0) {
            keepLineBreak = false;
        }
        return files.readContentOfFile(path, keepLineBreak);
    }

    public static /* synthetic */ List parallelFileMetadata$default(final Files files, final List $this$parallelFileMetadata, int chunkedSize, final Function1 consumer, final int n, final Object o) {
        if ((n & 0x1) != 0x0) {
            chunkedSize = files.calculateChunk($this$parallelFileMetadata.size());
        }
        return files.parallelFileMetadata($this$parallelFileMetadata, chunkedSize, consumer);
    }

    private static final int a(final File f1, final File f2) {
        return Intrinsics.compare(f1.lastModified(), f2.lastModified());
    }
    
    private static final int b(final File f1, final File f2) {
        return Intrinsics.compare(f2.lastModified(), f1.lastModified());
    }
    
    private static final void a(final File it) {
        if (it.isDirectory()) {
            final Files instance = Files.INSTANCE;
            Intrinsics.checkNotNullExpressionValue((Object)it, "it");
            deleteDirectoryQuietly$default(instance, it, null, null, 3, null);
        }
        else {
            com.onyx.android.sdk.commons.io.FileUtils.deleteQuietly(it);
        }
    }
    
    private static final Object b(final Function1 $consumer, final Object it) {
        Intrinsics.checkNotNullParameter((Object)$consumer, "$consumer");
        return $consumer.invoke(it);
    }
    
    private static final ObservableSource a(final Function1 $consumer, final Object path) {
        Intrinsics.checkNotNullParameter((Object)$consumer, "$consumer");
        return Observable.just(path).observeOn(Schedulers.io()).map(it -> b($consumer, it));
    }
    
    private static final List a(final List $pathSubList, final Function1 $consumer, final List it) {
        Intrinsics.checkNotNullParameter((Object)$pathSubList, "$pathSubList");
        Intrinsics.checkNotNullParameter((Object)$consumer, "$consumer");
        Intrinsics.checkNotNullParameter((Object)it, "it");
        final ArrayList list = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$pathSubList, 10));
        final Iterator iterator = $pathSubList.iterator();
        while (iterator.hasNext()) {
            list.add($consumer.invoke(iterator.next()));
        }
        return list;
    }
    
    private static final ObservableSource a(final Function1 $consumer, final List pathSubList) {
        Intrinsics.checkNotNullParameter((Object)$consumer, "$consumer");
        Intrinsics.checkNotNullParameter((Object)pathSubList, "pathSubList");
        return Observable.just(pathSubList).observeOn(Schedulers.io()).map(it -> a(pathSubList, $consumer, it));
    }
    
    private static final List a(final List it) {
        Intrinsics.checkNotNullParameter((Object)it, "it");
        return CollectionsKt.flatten((Iterable)it);
    }
    
    static {
        INSTANCE = new Files();
        b = Files::a;
        c = Files::b;
    }
    
    @NotNull
    public final Comparator<File> getFILE_MODIFIED_ASC_COMPARATOR() {
        return Files.b;
    }
    
    @NotNull
    public final Comparator<File> getFILE_MODIFIED_DESC_COMPARATOR() {
        return Files.c;
    }
    
    public final boolean isFileExist(@Nullable final String $this$isFileExist) {
        return !StringKt.isEmpty($this$isFileExist) && new File($this$isFileExist).exists();
    }
    
    public final boolean isDirectory(@Nullable final String $this$isDirectory) {
        return !StringKt.isEmpty($this$isDirectory) && this.isFileExist($this$isDirectory) && new File($this$isDirectory).isDirectory();
    }
    
    public final void deleteDirectory(@Nullable final String $this$deleteDirectory) {
        if (!StringKt.isEmpty($this$deleteDirectory) && this.isDirectory($this$deleteDirectory)) {
            try {
                com.onyx.android.sdk.commons.io.FileUtils.deleteDirectory(new File($this$deleteDirectory));
            } catch (IOException failure) {
                Debug.INSTANCE.e(failure);
            }
        }
    }
    
    public final boolean deleteFile(@Nullable final String $this$deleteFile) {
        final File file;
        return !StringKt.isEmpty($this$deleteFile) && this.isFileExist($this$deleteFile) && (file = new File($this$deleteFile)).isFile() && file.delete();
    }
    
    @NotNull
    public final String getFileBaseName(@Nullable final String $this$getFileBaseName) {
        final String baseName = FilenameUtils.getBaseName($this$getFileBaseName);
        Intrinsics.checkNotNullExpressionValue((Object)baseName, "getBaseName(this)");
        return baseName;
    }
    
    public final void deleteDirectoryQuietly(@NotNull final File $this$deleteDirectoryQuietly, @Nullable final Comparator<File> comparator, @Nullable final FileFilter fileFilter) {
        Intrinsics.checkNotNullParameter((Object)$this$deleteDirectoryQuietly, "<this>");
        if (!$this$deleteDirectoryQuietly.isDirectory()) {
            return;
        }
        final File[] listFiles = $this$deleteDirectoryQuietly.listFiles();
        File[] array;
        if (fileFilter != null) {
            if (listFiles == null) {
                array = null;
            }
            else {
                final File[] array2 = listFiles;
                final ArrayList list = new ArrayList();
                for (int i = 0; i < array2.length; ++i) {
                    final File file;
                    if (fileFilter.accept(file = listFiles[i])) {
                        list.add(file);
                    }
                }
                final Object[] array3;
                if ((array3 = list.toArray(new File[0])) == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
                }
                array = (File[])array3;
            }
        }
        else {
            array = listFiles;
        }
        File[] $this$isNotEmpty;
        if (comparator == null) {
            $this$isNotEmpty = array;
        }
        else {
            final List sortedWith;
            if (array != null && (sortedWith = ArraysKt.sortedWith((Object[])array, (Comparator)comparator)) != null) {
                final Object[] array4;
                if ((array4 = sortedWith.toArray(new File[0])) == null) {
                    throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
                }
                $this$isNotEmpty = (File[])array4;
            }
            else {
                $this$isNotEmpty = null;
            }
        }
        if (com.onyx.android.sdk.extension.ArraysKt.isNotEmpty($this$isNotEmpty)) {
            try {
                for (File file : $this$isNotEmpty) {
                    a(file);
                }
            } catch (Exception failure) {
                Debug.INSTANCE.e(failure);
            }
        }
        Object value;
        if ($this$isNotEmpty == null) {
            value = null;
        }
        else {
            value = $this$isNotEmpty.length;
        }
        Object value2;
        if (listFiles == null) {
            value2 = null;
        }
        else {
            value2 = listFiles.length;
        }
        if (Intrinsics.areEqual(value, value2)) {
            com.onyx.android.sdk.commons.io.FileUtils.deleteQuietly($this$deleteDirectoryQuietly);
        }
    }
    
    public final boolean deleteFileExt(@Nullable final String $this$deleteFileExt) {
        final File file;
        return !StringKt.isEmpty($this$deleteFileExt) && this.isFileExist($this$deleteFileExt) && (file = new File($this$deleteFileExt)).isFile() && file.delete();
    }
    
    @NotNull
    public final String getFileName(@Nullable final String $this$getFileName) {
        if ($this$getFileName == null) {
            return "";
        }
        final String name = new File($this$getFileName).getName();
        Intrinsics.checkNotNullExpressionValue((Object)name, "File(this).name");
        return name;
    }
    
    @NotNull
    public final String fileNameInAssets(@NotNull final Context context, @Nullable final String fileBaseName, @NotNull final String path) {
        Intrinsics.checkNotNullParameter((Object)context, "context");
        Intrinsics.checkNotNullParameter((Object)path, "path");
        if (fileBaseName == null) {
            return "";
        }
        try {
            final String[] list;
            if ((list = context.getAssets().list(path)) != null) {
                final String[] array = list;
                int i = 0;
                while (i < array.length) {
                    final String $this$getFileBaseName = list[i];
                    if (Intrinsics.areEqual((Object)Files.INSTANCE.getFileBaseName($this$getFileBaseName), (Object)fileBaseName)) {
                            final String s = $this$getFileBaseName;
                            Intrinsics.checkNotNullExpressionValue((Object)s, "it");
                            return s;
                    }
                    ++i;
                }
            }
        }
        catch (final IOException ex2) {}
        return "";
    }
    
    public final boolean copyAssetsFile(@NotNull final Context context, @NotNull final String assetPath, @NotNull final String localFilePath) {
        Intrinsics.checkNotNullParameter((Object)context, "context");
        Intrinsics.checkNotNullParameter((Object)assetPath, "assetPath");
        Intrinsics.checkNotNullParameter((Object)localFilePath, "localFilePath");
        try {
            if (!FileUtils.ensureFileExists(localFilePath)) {
                Debug.INSTANCE.w(this.getClass(), "create file fail, path = " + localFilePath, new Object[0]);
                a(localFilePath);
                return false;
            }
            return copyInputStreamToFile(context.getAssets().open(assetPath), new File(localFilePath)) >= 0L;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        finally {
            a(localFilePath);
        }
    }
    
    public final long copyInputStreamToFile(@Nullable final InputStream source, @Nullable final File destination) throws IOException {
        if (source == null) {
            return 0L;
        }
        try (InputStream input = source) {
            return copyToFile(input, destination);
        }
    }
    
    public final long copyToFile(@Nullable InputStream inputStream, @Nullable final File file) throws IOException {
        if (inputStream == null || file == null) {
            return 0L;
        }
        try (OutputStream output = new FileOutputStream(file, false)) {
            return IOUtils.copyLarge(inputStream, output);
        }
    }
    
    @NotNull
    public final String readContentOfFileSafely(@Nullable final String path) {
        try {
            final String contentOfFile = FileUtils.readContentOfFile(path);
            Intrinsics.checkNotNullExpressionValue((Object)contentOfFile, "readContentOfFile(path)");
            return contentOfFile;
        }
        catch (final Exception ex) {
            Debug.INSTANCE.e((Throwable)ex);
            return "";
        }
    }
    
    @NotNull
    public final String getValidFileName(@NotNull final String fileName) {
        Intrinsics.checkNotNullParameter((Object)fileName, "fileName");
        String extendedName;
        final String s = extendedName = FilenameUtils.getExtension(fileName);
        Intrinsics.checkNotNullExpressionValue((Object)s, "extension");
        if (s.length() > 0) {
            extendedName = Intrinsics.stringPlus(".", (Object)extendedName);
        }
        final String validFileName = FileUtils.getValidFileName(FilenameUtils.getBaseName(fileName), extendedName);
        Intrinsics.checkNotNullExpressionValue((Object)validFileName, "getValidFileName(\n      \u2026      extension\n        )");
        return validFileName;
    }
    
    @NotNull
    public final String generateUniqueFileName(@NotNull final String fileDir, @NotNull final String fileBaseName, @NotNull final String fileExtension, final int tryTimes) {
        Intrinsics.checkNotNullParameter((Object)fileDir, "fileDir");
        Intrinsics.checkNotNullParameter((Object)fileBaseName, "fileBaseName");
        Intrinsics.checkNotNullParameter((Object)fileExtension, "fileExtension");
        int i = 0;
        String string = fileBaseName;
        while (true) {
            String name;
            final String s = name = FileUtils.combine(string, fileExtension);
            Intrinsics.checkNotNull((Object)s);
            if (s.length() > 254 - fileExtension.length()) {
                Intrinsics.checkNotNull((Object)(name = FileUtils.combine(UUID.randomUUID().toString(), fileExtension)));
            }
            final File file;
            if (!(file = new File(FileUtils.combinePath(fileDir, name))).exists()) {
                final String combine = FileUtils.combine(string, fileExtension);
                Intrinsics.checkNotNull((Object)combine);
                return combine;
            }
            if (i++ == tryTimes) {
                throw new IllegalStateException("File already exists: " + file);
            }
            string = fileBaseName + '(' + i + ')';
        }
    }
    
    @NotNull
    public final List<File> listAllFiles(@Nullable final String path) {
        final LinkedList<File> list = new LinkedList<File>();
        if (path == null) {
            return list;
        }
        if (!FileUtils.fileExist(path)) {
            return list;
        }
        final File file;
        if (!(file = new File(path)).isDirectory()) {
            return list;
        }
        try {
            final Collection listFiles = com.onyx.android.sdk.commons.io.FileUtils.listFiles(file, (String[])null, true);
            Intrinsics.checkNotNullExpressionValue((Object)listFiles, "listFiles(directory, null, true)");
            return CollectionsKt.toList((Iterable)listFiles);
        }
        catch (final Exception ex) {
            final LinkedList<File> list2 = list;
            Debug.INSTANCE.e((Throwable)ex);
            return list2;
        }
    }
    
    @NotNull
    public final String readContentOfFile(@Nullable final String path, final boolean keepLineBreak) {
        if (!FileUtils.fileExist(path)) {
            Debug.INSTANCE.e(Files.class, "file(" + path + ") not exist", new Object[0]);
            return "";
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(new File(path)), StandardCharsets.UTF_8), 8192)) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
                if (keepLineBreak) {
                    content.append('\n');
                }
            }
            int end = content.length();
            while (end > 0 && content.charAt(end - 1) == '\n') {
                end--;
            }
            return content.substring(0, end);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return "";
        }
    }
    
    public final int calculateChunk(final int total) {
        return RangesKt.coerceAtLeast(total / (Runtime.getRuntime().availableProcessors() + 2), 500);
    }
    
    @NotNull
    public final <PathInfo, Result> List<Result> parallelFileMetadata(@NotNull final List<? extends PathInfo> $this$parallelFileMetadata, final int chunkedSize, @NotNull final Function1<? super PathInfo, ? extends Result> consumer) {
        Intrinsics.checkNotNullParameter((Object)$this$parallelFileMetadata, "<this>");
        Intrinsics.checkNotNullParameter((Object)consumer, "consumer");
        if ($this$parallelFileMetadata.size() < chunkedSize) {
            final Object blockingGet = Observable.fromIterable($this$parallelFileMetadata)
                    .flatMap(path -> a(consumer, path)).toList().blockingGet();
            Intrinsics.checkNotNullExpressionValue(blockingGet, "fromIterable(pathList)\n \u2026           .blockingGet()");
            return (List<Result>)blockingGet;
        }
        final Object blockingGet2 = Observable.fromIterable(CollectionsKt.chunked($this$parallelFileMetadata, chunkedSize))
                .flatMap(paths -> a(consumer, paths)).toList().map(items -> a((List) items)).blockingGet();
        Intrinsics.checkNotNullExpressionValue(blockingGet2, "fromIterable(pathList.ch\u2026           .blockingGet()");
        return (List<Result>)blockingGet2;
    }
    
    public final void deleteDirSafely(@NotNull final File directory) {
        Intrinsics.checkNotNullParameter((Object)directory, "directory");
        try {
            com.onyx.android.sdk.commons.io.FileUtils.deleteDirectory(directory);
        }
        catch (final Exception ex) {
            Debug.INSTANCE.e((Throwable)ex);
        }
    }
    
    public final boolean saveContentWithDiskSync(@NotNull final String content, @NotNull final File targetFile) {
        Intrinsics.checkNotNullParameter((Object)content, "content");
        Intrinsics.checkNotNullParameter((Object)targetFile, "targetFile");
        File temporaryFile = new File(targetFile.getAbsolutePath() + ".tmp");
        try {
            try (FileOutputStream output = new FileOutputStream(temporaryFile)) {
                output.write(content.getBytes(StandardCharsets.UTF_8));
                output.flush();
                output.getFD().sync();
            }
            if (!temporaryFile.renameTo(targetFile)) {
                FileUtils.deleteFile(temporaryFile);
            }
            String saved = com.onyx.android.sdk.commons.io.FileUtils.readFileToString(targetFile, StandardCharsets.UTF_8);
            if (Intrinsics.areEqual((Object)saved, (Object)content)) {
                return true;
            }
            FileUtils.deleteFile(targetFile);
            return false;
        }
        catch (Exception exception) {
            FileUtils.deleteFile(targetFile);
            FileUtils.deleteFile(temporaryFile);
            exception.printStackTrace();
            return false;
        }
    }
}
