// 
// 

package com.onyx.android.sdk.path;

import org.apache.commons.lang3.StringUtils;
import com.onyx.android.sdk.utils.FileUtils;
import kotlin.jvm.internal.Intrinsics;
import java.io.File;
import com.onyx.android.sdk.device.EnvironmentUtil;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u001e\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0019\u001a\u00020\u0004J\u000e\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u0004J\u0016\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001d\u001a\u00020\u0004J\u0016\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u0004J\u0016\u0010 \u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u0004J\u0016\u0010!\u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u0004J\u000e\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\u0013\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015¨\u0006&" }, d2 = { "Lcom/onyx/android/sdk/path/ReaderCommonPaths;", "", "()V", "ANNOTATION", "", "BACKUP_FILE_EXTENSION", "BOOKMARK", "DATA", "DOCUMENT", "FONTS", "LIBRARY", "METADATA", "METADATA_COLLECTION", "NEW_WORD", "OCR", "POINT", "RESOURCE", "SHAPE", "THUMBNAIL", "defaultExportDir", "getDefaultExportDir", "()Ljava/lang/String;", "defaultImportStoreDir", "getDefaultImportStoreDir", "getExportName", "title", "getLibraryDir", "parentDir", "getLibraryThumbnailFileDir", "libraryId", "getMetadataThumbnailFileDir", "documentId", "getPointFileDir", "getResourceDataDir", "isBackupFile", "", "file", "Ljava/io/File;", "onyxsdk-base_release" })
public final class ReaderCommonPaths
{
    @NotNull
    public static final ReaderCommonPaths INSTANCE;
    @NotNull
    public static final String ANNOTATION = "annotation";
    @NotNull
    public static final String BOOKMARK = "bookmark";
    @NotNull
    public static final String METADATA = "metadata";
    @NotNull
    public static final String DOCUMENT = "document";
    @NotNull
    public static final String RESOURCE = "resource";
    @NotNull
    public static final String LIBRARY = "library";
    @NotNull
    public static final String THUMBNAIL = "thumbnail";
    @NotNull
    public static final String NEW_WORD = "new_word";
    @NotNull
    public static final String OCR = "ocr";
    @NotNull
    public static final String METADATA_COLLECTION = "metadata_collection";
    @NotNull
    public static final String SHAPE = "shape";
    @NotNull
    public static final String POINT = "point";
    @NotNull
    public static final String DATA = "data";
    @NotNull
    public static final String FONTS = "fonts";
    @NotNull
    public static final String BACKUP_FILE_EXTENSION = ".booxc";
    @NotNull
    private static final String a;
    @NotNull
    private static final String b;
    
    private ReaderCommonPaths() {
    }
    
    static {
        INSTANCE = new ReaderCommonPaths();
        final String separator;
        a = EnvironmentUtil.getExternalStorageDirectory().getPath() + (Object)(separator = File.separator) + "reader" + (Object)separator;
        b = EnvironmentUtil.getExternalStorageDirectory().getPath() + (Object)separator + "Books" + (Object)separator;
    }
    
    @NotNull
    public final String getDefaultExportDir() {
        return ReaderCommonPaths.a;
    }
    
    @NotNull
    public final String getDefaultImportStoreDir() {
        return ReaderCommonPaths.b;
    }
    
    @NotNull
    public final String getExportName(@NotNull final String title) {
        Intrinsics.checkNotNullParameter((Object)title, "title");
        final String validFileName = FileUtils.getValidFileName(title, ".booxc");
        Intrinsics.checkNotNullExpressionValue((Object)validFileName, "getValidFileName(title, BACKUP_FILE_EXTENSION)");
        return validFileName;
    }
    
    public final boolean isBackupFile(@NotNull final File file) {
        Intrinsics.checkNotNullParameter((Object)file, "file");
        return StringUtils.endsWithIgnoreCase((CharSequence)FileUtils.getAbsolutePath(file), (CharSequence)".booxc");
    }
    
    @NotNull
    public final String getResourceDataDir(@NotNull final String parentDir, @NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)parentDir, "parentDir");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final StringBuilder append = new StringBuilder().append(parentDir);
        final String separator = File.separator;
        return append.append((Object)separator).append("resource").append((Object)separator).append("data").toString();
    }
    
    @NotNull
    public final String getPointFileDir(@NotNull final String parentDir, @NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)parentDir, "parentDir");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        return parentDir + (Object)File.separator + "point";
    }
    
    @NotNull
    public final String getMetadataThumbnailFileDir(@NotNull final String parentDir, @NotNull final String documentId) {
        Intrinsics.checkNotNullParameter((Object)parentDir, "parentDir");
        Intrinsics.checkNotNullParameter((Object)documentId, "documentId");
        final StringBuilder append = new StringBuilder().append(parentDir);
        final String separator = File.separator;
        return append.append((Object)separator).append("thumbnail").append((Object)separator).append("data").toString();
    }
    
    @NotNull
    public final String getLibraryThumbnailFileDir(@NotNull final String parentDir, @NotNull final String libraryId) {
        Intrinsics.checkNotNullParameter((Object)parentDir, "parentDir");
        Intrinsics.checkNotNullParameter((Object)libraryId, "libraryId");
        final StringBuilder append = new StringBuilder().append(parentDir);
        final String separator = File.separator;
        return append.append((Object)separator).append("thumbnail").append((Object)separator).append("data").toString();
    }
    
    @NotNull
    public final String getLibraryDir(@NotNull final String parentDir) {
        Intrinsics.checkNotNullParameter((Object)parentDir, "parentDir");
        return parentDir + (Object)File.separator + "library";
    }
}
