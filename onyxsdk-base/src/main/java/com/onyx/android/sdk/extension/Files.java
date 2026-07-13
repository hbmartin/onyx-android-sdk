
package com.onyx.android.sdk.extension;

import kotlin.ranges.RangesKt;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;
import com.onyx.android.sdk.base.utils.Debug;
import java.io.OutputStream;
import com.onyx.android.sdk.commons.io.IOUtils;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import com.onyx.android.sdk.commons.io.FilenameUtils;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
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
    private static final int DEFAULT_PARALLEL_CHUNKED_SIZE = 500;
    @NotNull
    private static final Comparator<File> FILE_MODIFIED_ASC_COMPARATOR;
    @NotNull
    private static final Comparator<File> FILE_MODIFIED_DESC_COMPARATOR;
    
    private Files() {
    }
    
    public static /* synthetic */ void deleteDirectoryQuietly$default(final Files files, final File directory, Comparator comparator, FileFilter fileFilter, final int mask, final Object unused) {
        if ((mask & 0x1) != 0x0) {
            comparator = null;
        }
        if ((mask & 0x2) != 0x0) {
            fileFilter = null;
        }
        files.deleteDirectoryQuietly(directory, comparator, fileFilter);
    }

    public static /* synthetic */ String fileNameInAssets$default(final Files files, final Context context, final String fileBaseName, String path, final int mask, final Object unused) {
        if ((mask & 0x4) != 0x0) {
            path = "";
        }
        return files.fileNameInAssets(context, fileBaseName, path);
    }

    private void clearEmptyFile(final String localFilePath) {
        try {
            if (FileUtils.fileExist(localFilePath) && FileUtils.getFileSize(localFilePath) == 0L) {
                FileUtils.deleteFile(localFilePath);
            }
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
    }

    public static /* synthetic */ String generateUniqueFileName$default(final Files files, final String fileDir, final String fileBaseName, String fileExtension, int tryTimes, final int mask, final Object unused) {
        if ((mask & 0x4) != 0x0) {
            fileExtension = "";
        }
        if ((mask & 0x8) != 0x0) {
            tryTimes = 254;
        }
        return files.generateUniqueFileName(fileDir, fileBaseName, fileExtension, tryTimes);
    }

    public static /* synthetic */ String readContentOfFile$default(final Files files, final String path, boolean keepLineBreak, final int mask, final Object unused) {
        if ((mask & 0x2) != 0x0) {
            keepLineBreak = false;
        }
        return files.readContentOfFile(path, keepLineBreak);
    }

    public static /* synthetic */ List parallelFileMetadata$default(final Files files, final List paths, int chunkedSize, final Function1 consumer, final int mask, final Object unused) {
        if ((mask & 0x1) != 0x0) {
            chunkedSize = files.calculateChunk(paths.size());
        }
        return files.parallelFileMetadata(paths, chunkedSize, consumer);
    }

    private static int compareModifiedAscending(final File f1, final File f2) {
        return Intrinsics.compare(f1.lastModified(), f2.lastModified());
    }
    
    private static int compareModifiedDescending(final File f1, final File f2) {
        return Intrinsics.compare(f2.lastModified(), f1.lastModified());
    }
    
    private static void deleteRecursively(final File file) {
        if (file.isDirectory()) {
            deleteDirectoryQuietly$default(INSTANCE, file, null, null, 3, null);
        }
        else {
            com.onyx.android.sdk.commons.io.FileUtils.deleteQuietly(file);
        }
    }
    
    private static <PathInfo, Result> ObservableSource<Result> processPath(
            final Function1<? super PathInfo, ? extends Result> consumer, final PathInfo path) {
        return Observable.just(path).observeOn(Schedulers.io()).map(ignored -> {
            final Result result = consumer.invoke(path);
            return result;
        });
    }
    
    private static <PathInfo, Result> List<Result> processChunk(
            final List<? extends PathInfo> paths,
            final Function1<? super PathInfo, ? extends Result> consumer) {
        final List<Result> results = new ArrayList<>(paths.size());
        for (PathInfo path : paths) {
            results.add(consumer.invoke(path));
        }
        return results;
    }
    
    private static <PathInfo, Result> ObservableSource<List<Result>> processChunkAsync(
            final Function1<? super PathInfo, ? extends Result> consumer,
            final List<? extends PathInfo> paths) {
        return Observable.just(paths).observeOn(Schedulers.io())
                .map(ignored -> processChunk(paths, consumer));
    }
    
    private static <Result> List<Result> flatten(final List<? extends List<? extends Result>> chunks) {
        final List<Result> results = new ArrayList<>();
        for (List<? extends Result> chunk : chunks) {
            results.addAll(chunk);
        }
        return results;
    }
    
    static {
        INSTANCE = new Files();
        FILE_MODIFIED_ASC_COMPARATOR = Files::compareModifiedAscending;
        FILE_MODIFIED_DESC_COMPARATOR = Files::compareModifiedDescending;
    }
    
    @NotNull
    public final Comparator<File> getFILE_MODIFIED_ASC_COMPARATOR() {
        return FILE_MODIFIED_ASC_COMPARATOR;
    }
    
    @NotNull
    public final Comparator<File> getFILE_MODIFIED_DESC_COMPARATOR() {
        return FILE_MODIFIED_DESC_COMPARATOR;
    }
    
    public final boolean isFileExist(@Nullable final String path) {
        return !StringKt.isEmpty(path) && new File(path).exists();
    }
    
    public final boolean isDirectory(@Nullable final String path) {
        return !StringKt.isEmpty(path) && new File(path).isDirectory();
    }
    
    public final void deleteDirectory(@Nullable final String path) {
        if (isDirectory(path)) {
            try {
                com.onyx.android.sdk.commons.io.FileUtils.deleteDirectory(new File(path));
            } catch (IOException failure) {
                Debug.INSTANCE.e(failure);
            }
        }
    }
    
    public final boolean deleteFile(@Nullable final String path) {
        if (StringKt.isEmpty(path)) {
            return false;
        }
        final File file = new File(path);
        return file.isFile() && file.delete();
    }
    
    @NotNull
    public final String getFileBaseName(@Nullable final String path) {
        final String baseName = FilenameUtils.getBaseName(path);
        Intrinsics.checkNotNullExpressionValue((Object)baseName, "getBaseName(this)");
        return baseName;
    }
    
    public final void deleteDirectoryQuietly(@NotNull final File directory, @Nullable final Comparator<File> comparator, @Nullable final FileFilter fileFilter) {
        Intrinsics.checkNotNullParameter((Object)directory, "<this>");
        if (!directory.isDirectory()) {
            return;
        }
        final File[] children = directory.listFiles();
        File[] selectedChildren = children;
        if (fileFilter != null && children != null) {
            final List<File> selected = new ArrayList<>(children.length);
            for (File child : children) {
                if (fileFilter.accept(child)) {
                    selected.add(child);
                }
            }
            selectedChildren = selected.toArray(new File[0]);
        }
        if (comparator != null && selectedChildren != null) {
            Arrays.sort(selectedChildren, comparator);
        }
        if (selectedChildren != null && selectedChildren.length > 0) {
            try {
                for (File child : selectedChildren) {
                    deleteRecursively(child);
                }
            } catch (Exception failure) {
                Debug.INSTANCE.e(failure);
            }
        }
        if (children == null || selectedChildren.length == children.length) {
            com.onyx.android.sdk.commons.io.FileUtils.deleteQuietly(directory);
        }
    }
    
    public final boolean deleteFileExt(@Nullable final String path) {
        return deleteFile(path);
    }
    
    @NotNull
    public final String getFileName(@Nullable final String path) {
        if (path == null) {
            return "";
        }
        return new File(path).getName();
    }
    
    @NotNull
    public final String fileNameInAssets(@NotNull final Context context, @Nullable final String fileBaseName, @NotNull final String path) {
        Intrinsics.checkNotNullParameter((Object)context, "context");
        Intrinsics.checkNotNullParameter((Object)path, "path");
        if (fileBaseName == null) {
            return "";
        }
        try {
            final String[] assetNames = context.getAssets().list(path);
            if (assetNames != null) {
                for (String assetName : assetNames) {
                    if (Intrinsics.areEqual(getFileBaseName(assetName), fileBaseName)) {
                        return assetName;
                    }
                }
            }
        }
        catch (final IOException ignored) {}
        return "";
    }
    
    public final boolean copyAssetsFile(@NotNull final Context context, @NotNull final String assetPath, @NotNull final String localFilePath) {
        Intrinsics.checkNotNullParameter((Object)context, "context");
        Intrinsics.checkNotNullParameter((Object)assetPath, "assetPath");
        Intrinsics.checkNotNullParameter((Object)localFilePath, "localFilePath");
        try {
            if (!FileUtils.ensureFileExists(localFilePath)) {
                Debug.INSTANCE.w(this.getClass(), "create file fail, path = " + localFilePath, new Object[0]);
                clearEmptyFile(localFilePath);
                return false;
            }
            return copyInputStreamToFile(context.getAssets().open(assetPath), new File(localFilePath)) >= 0L;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
        finally {
            clearEmptyFile(localFilePath);
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
        String extension = FilenameUtils.getExtension(fileName);
        Intrinsics.checkNotNullExpressionValue(extension, "extension");
        if (!extension.isEmpty()) {
            extension = "." + extension;
        }
        final String validFileName = FileUtils.getValidFileName(
                FilenameUtils.getBaseName(fileName), extension);
        Intrinsics.checkNotNullExpressionValue(
                validFileName, "getValidFileName(baseName, extension)");
        return validFileName;
    }
    
    @NotNull
    public final String generateUniqueFileName(@NotNull final String fileDir, @NotNull final String fileBaseName, @NotNull final String fileExtension, final int tryTimes) {
        Intrinsics.checkNotNullParameter((Object)fileDir, "fileDir");
        Intrinsics.checkNotNullParameter((Object)fileBaseName, "fileBaseName");
        Intrinsics.checkNotNullParameter((Object)fileExtension, "fileExtension");
        int attempts = 0;
        String candidateBaseName = fileBaseName;
        while (true) {
            String candidateName = FileUtils.combine(candidateBaseName, fileExtension);
            Intrinsics.checkNotNull(candidateName);
            if (candidateName.length() > 254) {
                candidateName = FileUtils.combine(UUID.randomUUID().toString(), fileExtension);
                Intrinsics.checkNotNull(candidateName);
            }
            final File candidateFile = new File(FileUtils.combinePath(fileDir, candidateName));
            if (!candidateFile.exists()) {
                return candidateName;
            }
            if (attempts++ == tryTimes) {
                throw new IllegalStateException("File already exists: " + candidateFile);
            }
            candidateBaseName = fileBaseName + '(' + attempts + ')';
        }
    }
    
    @NotNull
    public final List<File> listAllFiles(@Nullable final String path) {
        final LinkedList<File> emptyResult = new LinkedList<>();
        if (path == null || !new File(path).isDirectory()) {
            return emptyResult;
        }
        try {
            final Collection<File> files = com.onyx.android.sdk.commons.io.FileUtils.listFiles(
                    new File(path), (String[])null, true);
            return new ArrayList<>(files);
        }
        catch (final Exception exception) {
            Debug.INSTANCE.e(exception);
            return emptyResult;
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
        return RangesKt.coerceAtLeast(
                total / (Runtime.getRuntime().availableProcessors() + 2),
                DEFAULT_PARALLEL_CHUNKED_SIZE);
    }
    
    @NotNull
    public final <PathInfo, Result> List<Result> parallelFileMetadata(
            @NotNull final List<? extends PathInfo> paths,
            final int chunkedSize,
            @NotNull final Function1<? super PathInfo, ? extends Result> consumer) {
        Intrinsics.checkNotNullParameter((Object)paths, "<this>");
        Intrinsics.checkNotNullParameter((Object)consumer, "consumer");
        final int effectiveChunkSize = chunkedSize > 0
                ? chunkedSize
                : calculateChunk(paths.size());
        if (paths.size() < effectiveChunkSize) {
            final List<? extends Result> results = Observable.fromIterable(paths)
                    .flatMap(path -> Files.<PathInfo, Result>processPath(consumer, path))
                    .toList()
                    .blockingGet();
            return new ArrayList<>(results);
        }
        final List<List<? extends PathInfo>> chunks = new ArrayList<>();
        for (int start = 0; start < paths.size(); start += effectiveChunkSize) {
            chunks.add(paths.subList(start, Math.min(start + effectiveChunkSize, paths.size())));
        }
        final List<? extends Result> results = Observable.fromIterable(chunks)
                .flatMap(chunk -> Files.<PathInfo, Result>processChunkAsync(consumer, chunk))
                .toList()
                .map(Files::flatten)
                .blockingGet();
        return new ArrayList<>(results);
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
