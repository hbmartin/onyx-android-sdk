// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.extension;

import kotlin.ranges.RangesKt;
import java.util.Collection;
import java.util.LinkedList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.io.FileAlreadyExistsException;
import java.util.UUID;
import com.onyx.android.sdk.base.utils.Debug;
import java.io.OutputStream;
import org.apache.commons.io.IOUtils;
import java.io.Closeable;
import kotlin.io.CloseableKt;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.function.IOConsumer;
import java.util.Arrays;
import kotlin.collections.ArraysKt;
import org.apache.commons.io.FilenameUtils;
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
            org.apache.commons.io.FileUtils.deleteQuietly(it);
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
                org.apache.commons.io.FileUtils.deleteDirectory(new File($this$deleteDirectory));
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
            final IOConsumer<File> ioConsumer = Files::a;
            final File[] original = $this$isNotEmpty;
            try {
                IOConsumer.forAll(ioConsumer, Arrays.copyOf(original, original.length));
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
            org.apache.commons.io.FileUtils.deleteQuietly($this$deleteDirectoryQuietly);
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
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: dup            
        //     2: aload_2        
        //     3: aload_1        
        //     4: ldc_w           "context"
        //     7: invokestatic    kotlin/jvm/internal/Intrinsics.checkNotNullParameter:(Ljava/lang/Object;Ljava/lang/String;)V
        //    10: ldc_w           "assetPath"
        //    13: invokestatic    kotlin/jvm/internal/Intrinsics.checkNotNullParameter:(Ljava/lang/Object;Ljava/lang/String;)V
        //    16: ldc_w           "localFilePath"
        //    19: invokestatic    kotlin/jvm/internal/Intrinsics.checkNotNullParameter:(Ljava/lang/Object;Ljava/lang/String;)V
        //    22: invokestatic    com/onyx/android/sdk/utils/FileUtils.ensureFileExists:(Ljava/lang/String;)Z
        //    25: ifne            56
        //    28: aload_0        
        //    29: aload_3        
        //    30: getstatic       com/onyx/android/sdk/base/utils/Debug.INSTANCE:Lcom/onyx/android/sdk/base/utils/Debug;
        //    33: aload_0        
        //    34: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
        //    37: ldc_w           "create file fail, path = "
        //    40: aload_3        
        //    41: invokestatic    kotlin/jvm/internal/Intrinsics.stringPlus:(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;
        //    44: iconst_0       
        //    45: anewarray       Ljava/lang/Object;
        //    48: invokevirtual   com/onyx/android/sdk/base/utils/Debug.w:(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)V
        //    51: invokespecial   com/onyx/android/sdk/extension/Files.a:(Ljava/lang/String;)V
        //    54: iconst_0       
        //    55: ireturn        
        //    56: aload_0        
        //    57: aload_1        
        //    58: invokevirtual   android/content/Context.getAssets:()Landroid/content/res/AssetManager;
        //    61: aload_2        
        //    62: invokevirtual   android/content/res/AssetManager.open:(Ljava/lang/String;)Ljava/io/InputStream;
        //    65: new             Ljava/io/File;
        //    68: dup            
        //    69: aload_3        
        //    70: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //    73: invokevirtual   com/onyx/android/sdk/extension/Files.copyInputStreamToFile:(Ljava/io/InputStream;Ljava/io/File;)J
        //    76: lconst_0       
        //    77: lcmp           
        //    78: iflt            86
        //    81: iconst_1       
        //    82: istore_1       
        //    83: goto            88
        //    86: iconst_0       
        //    87: istore_1       
        //    88: aload_0        
        //    89: aload_3        
        //    90: invokespecial   com/onyx/android/sdk/extension/Files.a:(Ljava/lang/String;)V
        //    93: goto            109
        //    96: goto            111
        //    99: invokevirtual   java/lang/Exception.printStackTrace:()V
        //   102: aload_0        
        //   103: aload_3        
        //   104: iconst_0       
        //   105: istore_1       
        //   106: invokespecial   com/onyx/android/sdk/extension/Files.a:(Ljava/lang/String;)V
        //   109: iload_1        
        //   110: ireturn        
        //   111: aload_0        
        //   112: aload_3        
        //   113: invokespecial   com/onyx/android/sdk/extension/Files.a:(Ljava/lang/String;)V
        //   116: athrow         
        //    StackMapTable: 00 07 38 FF 00 1D 00 04 07 00 05 00 00 07 00 77 00 00 FF 00 01 00 04 07 00 05 01 00 07 00 77 00 00 FF 00 07 00 04 07 00 05 00 00 07 00 77 00 01 07 01 DE 42 07 00 7F FF 00 09 00 02 00 01 00 00 FF 00 01 00 04 07 00 05 00 00 07 00 77 00 01 07 01 DE
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  22     25     99     109    Ljava/lang/Exception;
        //  22     25     96     99     Any
        //  28     51     99     109    Ljava/lang/Exception;
        //  28     51     96     99     Any
        //  56     76     99     109    Ljava/lang/Exception;
        //  56     76     96     99     Any
        //  99     102    96     99     Any
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Inconsistent stack size at #0109 (coming from #0106).
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2239)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:203)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:93)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:868)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:761)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:638)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:605)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:195)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:162)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:137)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:333)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:254)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:144)
        // 
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
        try (OutputStream output = org.apache.commons.io.FileUtils.newOutputStream(file, false)) {
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
            final Collection listFiles = org.apache.commons.io.FileUtils.listFiles(file, (String[])null, true);
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
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokestatic    com/onyx/android/sdk/utils/FileUtils.fileExist:(Ljava/lang/String;)Z
        //     4: ifne            51
        //     7: getstatic       com/onyx/android/sdk/base/utils/Debug.INSTANCE:Lcom/onyx/android/sdk/base/utils/Debug;
        //    10: new             Ljava/lang/StringBuilder;
        //    13: dup            
        //    14: invokespecial   java/lang/StringBuilder.<init>:()V
        //    17: ldc_w           "file("
        //    20: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    23: aload_1        
        //    24: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //    27: ldc_w           ") not exist"
        //    30: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    33: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    36: iconst_0       
        //    37: anewarray       Ljava/lang/Object;
        //    40: astore_0       
        //    41: ldc             Lcom/onyx/android/sdk/extension/Files;.class
        //    43: swap           
        //    44: aload_0        
        //    45: invokevirtual   com/onyx/android/sdk/base/utils/Debug.e:(Ljava/lang/Class;Ljava/lang/String;[Ljava/lang/Object;)V
        //    48: ldc             ""
        //    50: areturn        
        //    51: new             Ljava/lang/StringBuilder;
        //    54: dup            
        //    55: astore_0       
        //    56: invokespecial   java/lang/StringBuilder.<init>:()V
        //    59: new             Ljava/io/File;
        //    62: dup            
        //    63: astore_3       
        //    64: aload_1        
        //    65: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //    68: getstatic       kotlin/text/Charsets.UTF_8:Ljava/nio/charset/Charset;
        //    71: astore_1       
        //    72: sipush          8192
        //    75: istore          4
        //    77: new             Ljava/io/InputStreamReader;
        //    80: dup            
        //    81: dup            
        //    82: astore          5
        //    84: new             Ljava/io/FileInputStream;
        //    87: dup            
        //    88: aload_1        
        //    89: swap           
        //    90: aload_3        
        //    91: invokespecial   java/io/FileInputStream.<init>:(Ljava/io/File;)V
        //    94: invokespecial   java/io/InputStreamReader.<init>:(Ljava/io/InputStream;Ljava/nio/charset/Charset;)V
        //    97: instanceof      Ljava/io/BufferedReader;
        //   100: ifeq            112
        //   103: aload           5
        //   105: checkcast       Ljava/io/BufferedReader;
        //   108: astore_1       
        //   109: goto            124
        //   112: new             Ljava/io/BufferedReader;
        //   115: dup            
        //   116: astore_1       
        //   117: aload           5
        //   119: iload           4
        //   121: invokespecial   java/io/BufferedReader.<init>:(Ljava/io/Reader;I)V
        //   124: aload_1        
        //   125: aconst_null    
        //   126: astore_3       
        //   127: aconst_null    
        //   128: astore          4
        //   130: invokestatic    kotlin/io/TextStreamsKt.lineSequence:(Ljava/io/BufferedReader;)Lkotlin/sequences/Sequence;
        //   133: invokeinterface kotlin/sequences/Sequence.iterator:()Ljava/util/Iterator;
        //   138: astore          5
        //   140: aload           5
        //   142: invokeinterface java/util/Iterator.hasNext:()Z
        //   147: ifeq            180
        //   150: iload_2        
        //   151: aload_0        
        //   152: aload           5
        //   154: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   159: checkcast       Ljava/lang/String;
        //   162: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   165: pop            
        //   166: ifeq            140
        //   169: aload_0        
        //   170: ldc_w           "\n"
        //   173: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   176: pop            
        //   177: goto            140
        //   180: aload_0        
        //   181: aload_1        
        //   182: aload_3        
        //   183: aload_1        
        //   184: aload           4
        //   186: invokestatic    kotlin/io/CloseableKt.closeFinally:(Ljava/io/Closeable;Ljava/lang/Throwable;)V
        //   189: invokestatic    kotlin/io/CloseableKt.closeFinally:(Ljava/io/Closeable;Ljava/lang/Throwable;)V
        //   192: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   195: dup            
        //   196: dup            
        //   197: astore_0       
        //   198: ldc_w           "total.toString()"
        //   201: invokestatic    kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue:(Ljava/lang/Object;Ljava/lang/String;)V
        //   204: invokeinterface java/lang/CharSequence.length:()I
        //   209: iconst_m1      
        //   210: iadd           
        //   211: dup            
        //   212: istore_1       
        //   213: iflt            267
        //   216: aload_0        
        //   217: iload_1        
        //   218: dup            
        //   219: iconst_m1      
        //   220: iadd           
        //   221: istore_2       
        //   222: invokeinterface java/lang/CharSequence.charAt:(I)C
        //   227: bipush          10
        //   229: if_icmpne       236
        //   232: iconst_1       
        //   233: goto            237
        //   236: iconst_0       
        //   237: ifne            255
        //   240: aload_0        
        //   241: iload_1        
        //   242: iconst_1       
        //   243: iadd           
        //   244: iconst_0       
        //   245: swap           
        //   246: invokeinterface java/lang/CharSequence.subSequence:(II)Ljava/lang/CharSequence;
        //   251: astore_0       
        //   252: goto            270
        //   255: iload_2        
        //   256: ifge            262
        //   259: goto            267
        //   262: iload_2        
        //   263: istore_1       
        //   264: goto            216
        //   267: ldc             ""
        //   269: astore_0       
        //   270: aload_0        
        //   271: invokevirtual   java/lang/Object.toString:()Ljava/lang/String;
        //   274: areturn        
        //   275: astore_0       
        //   276: aload_0        
        //   277: athrow         
        //   278: aload_1        
        //   279: aload_0        
        //   280: invokestatic    kotlin/io/CloseableKt.closeFinally:(Ljava/io/Closeable;Ljava/lang/Throwable;)V
        //   283: athrow         
        //   284: astore_0       
        //   285: aload_0        
        //   286: athrow         
        //   287: aload_1        
        //   288: aload_0        
        //   289: invokestatic    kotlin/io/CloseableKt.closeFinally:(Ljava/io/Closeable;Ljava/lang/Throwable;)V
        //   292: athrow         
        //   293: invokevirtual   java/lang/Exception.printStackTrace:()V
        //   296: ldc             ""
        //   298: areturn        
        //    StackMapTable: 00 11 FF 00 33 00 03 00 07 00 77 01 00 00 FF 00 3C 00 06 07 02 26 00 01 00 01 07 02 55 00 00 FF 00 0B 00 03 07 02 26 07 02 5E 01 00 00 FE 00 0F 05 05 07 01 04 FF 00 27 00 05 07 02 26 07 02 5E 00 05 05 00 00 FF 00 23 00 02 07 00 77 01 00 00 FC 00 13 01 40 01 FF 00 11 00 03 07 00 77 00 01 00 00 06 F8 00 04 FC 00 02 07 00 07 FF 00 04 00 02 00 07 02 5E 00 01 07 01 DE FF 00 02 00 02 07 01 DE 07 02 5E 00 01 07 01 DE FF 00 05 00 02 00 07 02 5E 00 01 07 01 DE FF 00 02 00 02 07 01 DE 07 02 5E 00 01 07 01 DE FF 00 05 00 00 00 01 07 00 7F
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  51     54     293    299    Ljava/lang/Exception;
        //  56     62     293    299    Ljava/lang/Exception;
        //  64     71     293    299    Ljava/lang/Exception;
        //  77     80     293    299    Ljava/lang/Exception;
        //  84     87     293    299    Ljava/lang/Exception;
        //  88     100    293    299    Ljava/lang/Exception;
        //  103    108    293    299    Ljava/lang/Exception;
        //  112    115    293    299    Ljava/lang/Exception;
        //  117    124    293    299    Ljava/lang/Exception;
        //  130    138    275    284    Any
        //  140    147    275    284    Any
        //  150    165    275    284    Any
        //  169    176    275    284    Any
        //  180    189    284    293    Any
        //  189    195    293    299    Ljava/lang/Exception;
        //  198    209    293    299    Ljava/lang/Exception;
        //  222    227    293    299    Ljava/lang/Exception;
        //  244    251    293    299    Ljava/lang/Exception;
        //  270    274    293    299    Ljava/lang/Exception;
        //  276    278    278    284    Any
        //  278    284    284    293    Any
        //  285    287    287    293    Any
        //  287    293    293    299    Ljava/lang/Exception;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0180:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2604)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:206)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:93)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:868)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:761)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:638)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:605)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:195)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:162)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:137)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:333)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:254)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:144)
        // 
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
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1        
        //     2: ldc_w           "content"
        //     5: invokestatic    kotlin/jvm/internal/Intrinsics.checkNotNullParameter:(Ljava/lang/Object;Ljava/lang/String;)V
        //     8: ldc_w           "targetFile"
        //    11: invokestatic    kotlin/jvm/internal/Intrinsics.checkNotNullParameter:(Ljava/lang/Object;Ljava/lang/String;)V
        //    14: new             Ljava/io/File;
        //    17: dup            
        //    18: astore_0       
        //    19: aload_2        
        //    20: aload_0        
        //    21: aload_2        
        //    22: invokevirtual   java/io/File.getAbsolutePath:()Ljava/lang/String;
        //    25: ldc_w           ".tmp"
        //    28: invokestatic    kotlin/jvm/internal/Intrinsics.stringPlus:(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;
        //    31: invokespecial   java/io/File.<init>:(Ljava/lang/String;)V
        //    34: new             Ljava/io/FileOutputStream;
        //    37: dup            
        //    38: dup2           
        //    39: astore_3       
        //    40: aload_1        
        //    41: aload_3        
        //    42: aload_0        
        //    43: invokespecial   java/io/FileOutputStream.<init>:(Ljava/io/File;)V
        //    46: aconst_null    
        //    47: astore          4
        //    49: getstatic       kotlin/text/Charsets.UTF_8:Ljava/nio/charset/Charset;
        //    52: invokevirtual   java/lang/String.getBytes:(Ljava/nio/charset/Charset;)[B
        //    55: dup            
        //    56: ldc_w           "this as java.lang.String).getBytes(charset)"
        //    59: invokestatic    kotlin/jvm/internal/Intrinsics.checkNotNullExpressionValue:(Ljava/lang/Object;Ljava/lang/String;)V
        //    62: invokevirtual   java/io/FileOutputStream.write:([B)V
        //    65: invokevirtual   java/io/FileOutputStream.flush:()V
        //    68: aload           4
        //    70: aload_3        
        //    71: invokevirtual   java/io/FileOutputStream.getFD:()Ljava/io/FileDescriptor;
        //    74: invokevirtual   java/io/FileDescriptor.sync:()V
        //    77: invokestatic    kotlin/io/CloseableKt.closeFinally:(Ljava/io/Closeable;Ljava/lang/Throwable;)V
        //    80: invokevirtual   java/io/File.renameTo:(Ljava/io/File;)Z
        //    83: ifne            91
        //    86: aload_0        
        //    87: invokestatic    com/onyx/android/sdk/utils/FileUtils.deleteFile:(Ljava/io/File;)Z
        //    90: pop            
        //    91: aload_2        
        //    92: aconst_null    
        //    93: iconst_1       
        //    94: aconst_null    
        //    95: invokestatic    kotlin/io/FilesKt.readText$default:(Ljava/io/File;Ljava/nio/charset/Charset;ILjava/lang/Object;)Ljava/lang/String;
        //    98: aload_1        
        //    99: invokestatic    kotlin/jvm/internal/Intrinsics.areEqual:(Ljava/lang/Object;Ljava/lang/Object;)Z
        //   102: ifeq            107
        //   105: iconst_1       
        //   106: ireturn        
        //   107: aload_2        
        //   108: invokestatic    com/onyx/android/sdk/utils/FileUtils.deleteFile:(Ljava/io/File;)Z
        //   111: pop            
        //   112: iconst_0       
        //   113: ireturn        
        //   114: astore_1       
        //   115: aload_1        
        //   116: athrow         
        //   117: aload_3        
        //   118: aload_1        
        //   119: invokestatic    kotlin/io/CloseableKt.closeFinally:(Ljava/io/Closeable;Ljava/lang/Throwable;)V
        //   122: athrow         
        //   123: aload_2        
        //   124: aload_0        
        //   125: invokestatic    com/onyx/android/sdk/utils/FileUtils.deleteFile:(Ljava/io/File;)Z
        //   128: pop            
        //   129: invokestatic    com/onyx/android/sdk/utils/FileUtils.deleteFile:(Ljava/io/File;)Z
        //   132: pop            
        //   133: invokevirtual   java/lang/Exception.printStackTrace:()V
        //   136: iconst_0       
        //   137: ireturn        
        //    StackMapTable: 00 05 FF 00 5B 00 03 07 00 63 07 00 77 07 00 63 00 00 FF 00 0F 00 03 07 00 63 00 07 00 63 00 00 FF 00 06 00 04 07 00 63 00 07 00 63 07 02 C4 00 01 07 01 DE FF 00 02 00 04 07 00 63 07 01 DE 07 00 63 07 02 C4 00 01 07 01 DE FF 00 05 00 03 07 00 63 00 07 00 63 00 01 07 00 7F
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  34     37     123    138    Ljava/lang/Exception;
        //  40     46     123    138    Ljava/lang/Exception;
        //  49     77     114    123    Any
        //  77     83     123    138    Ljava/lang/Exception;
        //  86     90     123    138    Ljava/lang/Exception;
        //  91     102    123    138    Ljava/lang/Exception;
        //  107    111    123    138    Ljava/lang/Exception;
        //  115    117    117    123    Any
        //  117    123    123    138    Ljava/lang/Exception;
        // 
        // The error that occurred was:
        // 
        // java.lang.IllegalStateException: Expression is linked from several locations: Label_0091:
        //     at com.strobel.decompiler.ast.Error.expressionLinkedFromMultipleLocations(Error.java:27)
        //     at com.strobel.decompiler.ast.AstOptimizer.mergeDisparateObjectInitializations(AstOptimizer.java:2604)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:235)
        //     at com.strobel.decompiler.ast.AstOptimizer.optimize(AstOptimizer.java:42)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:206)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:93)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:868)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:761)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:638)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:605)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:195)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:162)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:137)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:333)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:254)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:144)
        // 
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
            String saved = org.apache.commons.io.FileUtils.readFileToString(targetFile, StandardCharsets.UTF_8);
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
