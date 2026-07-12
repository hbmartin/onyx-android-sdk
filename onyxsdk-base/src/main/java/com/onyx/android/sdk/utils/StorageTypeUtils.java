// 
// 

package com.onyx.android.sdk.utils;

import org.jetbrains.annotations.Nullable;
import kotlin.TuplesKt;
import java.util.Iterator;
import kotlin.collections.CollectionsKt;
import kotlin.text.StringsKt;
import java.util.Locale;
import java.io.File;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.functions.Function1;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import kotlin.Pair;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import kotlin.Metadata;

@Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0003\u001a\u001b\u001cB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0007H\u0002J\b\u0010\u0010\u001a\u00020\tH\u0002J\b\u0010\u0011\u001a\u00020\tH\u0002J\b\u0010\u0012\u001a\u00020\tH\u0002J\u0018\u0010\u0013\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u0007H\u0002J\u0010\u0010\u0016\u001a\u00020\u00072\u0006\u0010\u0014\u001a\u00020\u0007H\u0002J\u000e\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u0007J\u0010\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0007H\u0007R&\u0010\u0003\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00040\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00070\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d" }, d2 = { "Lcom/onyx/android/sdk/utils/StorageTypeUtils;", "", "()V", "keywordFallbacks", "", "Lkotlin/Pair;", "Lcom/onyx/android/sdk/utils/StorageTypeUtils$StorageType;", "", "pathRules", "Lcom/onyx/android/sdk/utils/StorageTypeUtils$PathRule;", "usbKeywordList", "buildResult", "Lcom/onyx/android/sdk/utils/StorageTypeUtils$FilePathInferResult;", "absPath", "type", "root", "createInternalPathRule", "createTFCardPathRule", "createUSBPathRule", "extractRootByKeyword", "path", "keyword", "getUnifiedPath", "inferFilePathInfo", "rawPath", "inferStorageType", "FilePathInferResult", "PathRule", "StorageType", "onyxsdk-base_release" })
public final class StorageTypeUtils
{
    @NotNull
    public static final StorageTypeUtils INSTANCE;
    @NotNull
    private static final List<String> a;
    @NotNull
    private static final List<Pair<StorageType, List<String>>> b;
    @NotNull
    private static final List<a> c;
    
    private StorageTypeUtils() {
    }
    
    private final a c() {
        return new a(new Regex(".*(usb_otg|udisk|usbstorage|usb_disk|usbdisk|usb)(/|$)", RegexOption.IGNORE_CASE), StorageType.USB, (Function1<? super String, String>)StorageTypeUtils$d.a);
    }
    
    private final a b() {
        return new a(new Regex("(/mnt/media_rw/|/storage/)([a-f0-9]{4}-[a-f0-9]{4}|[a-f0-9]{8})"), StorageType.TF_CARD, (Function1<? super String, String>)StorageTypeUtils$c.a);
    }
    
    private final a a() {
        return new a(new Regex("^(/data/|/system/|/sdcard/|/storage/(emulated/|sdcard0/))"), StorageType.INTERNAL, (Function1<? super String, String>)StorageTypeUtils$b.a);
    }
    
    @JvmStatic
    @NotNull
    public static final StorageType inferStorageType(@NotNull final String rawPath) {
        Intrinsics.checkNotNullParameter((Object)rawPath, "rawPath");
        return StorageTypeUtils.INSTANCE.inferFilePathInfo(rawPath).getStorageType();
    }

    public static final List access$getUsbKeywordList$p() {
        return StorageTypeUtils.a;
    }
    
    private final String a(final String path) {
        String s2 = null;
        try {
            final String canonicalPath = new File(path).getCanonicalPath();
            Intrinsics.checkNotNullExpressionValue((Object)canonicalPath, "File(path).canonicalPath");
            final String lowerCase = canonicalPath.toLowerCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue((Object)lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            final String replace$default = lowerCase.replace('\\', '/');
            try {
                try {
                    final String replace = new Regex("/+").replace((CharSequence)replace$default, "/");
                    final String s = "/";
                    try {
                        s2 = StringsKt.removeSuffix(replace, (CharSequence)s);
                    }
                    catch (final Exception ex) {
                        final String lowerCase2 = path.toLowerCase(Locale.ROOT);
                        Intrinsics.checkNotNullExpressionValue((Object)lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        s2 = new Regex("/+").replace(lowerCase2.replace('\\', '/'), "/");
                        while (s2.endsWith("/")) s2 = s2.substring(0, s2.length() - 1);
                    }
                }
                catch (final Exception ex2) {}
            }
            catch (final Exception ex3) {}
        }
        catch (final Exception ex4) {}
        return s2;
    }
    
    private final String a(final String path, final String keyword) {
        final List<String> split$default = java.util.Arrays.asList(path.split("/", -1));
        int n = 0;
        final Iterator<String> iterator = split$default.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().contains(keyword)) {
                return String.join("/", split$default.subList(0, n + 1));
            }
            ++n;
        }
        n = -1;
        int keywordIndex = path.indexOf(keyword);
        return keywordIndex < 0 ? path : path.substring(0, keywordIndex);
    }
    
    private final FilePathInferResult a(final String absPath, final StorageType type, final String root) {
        String removePrefix;
        if (absPath.startsWith(root)) {
            final String substring = absPath.substring(root.length());
            Intrinsics.checkNotNullExpressionValue((Object)substring, "this as java.lang.String).substring(startIndex)");
            removePrefix = StringsKt.removePrefix(substring, (CharSequence)"/");
        }
        else {
            removePrefix = absPath;
        }
        return new FilePathInferResult(absPath, type, removePrefix, root);
    }
    
    static {
        final StorageTypeUtils storageTypeUtils = INSTANCE = new StorageTypeUtils();
        a = java.util.Arrays.asList("usbdrive", "otg", "usb_host", "flashdrive", "thumb", "usb_disk", "usbdisk", "udisk", "usb_otg", "usbstorage", "usb");
        b = java.util.Arrays.asList(
                TuplesKt.to(StorageType.TF_CARD, java.util.Arrays.asList("extsd", "memorycard", "_sd", "microsd", "sdcard", "ext_card", "tf_card")),
                TuplesKt.to(StorageType.USB, a));
        final a[] array2;
        final a[] array = array2 = new a[3];
        final StorageTypeUtils storageTypeUtils2 = storageTypeUtils;
        final a[] array3 = array2;
        final StorageTypeUtils storageTypeUtils3 = storageTypeUtils;
        array2[0] = storageTypeUtils.a();
        array3[1] = storageTypeUtils3.b();
        array[2] = storageTypeUtils2.c();
        c = java.util.Arrays.asList(array);
    }
    
    @NotNull
    public final FilePathInferResult inferFilePathInfo(@NotNull String rawPath) {
        final String path = rawPath;
        Intrinsics.checkNotNullParameter((Object)path, "rawPath");
        rawPath = this.a(path);
        final StorageType unknown = StorageType.UNKNOWN;
        final Iterator<a> iterator = StorageTypeUtils.c.iterator();
        while (iterator.hasNext()) {
            final a a;
            if ((a = iterator.next()).d().find(rawPath, 0) == null) {
                continue;
            }
            final a a2 = a;
            return StorageTypeUtils.INSTANCE.a(rawPath, a2.f(), a2.e().invoke(rawPath));
        }
        for (final Pair<StorageType, List<String>> pair : StorageTypeUtils.b) {
            final StorageType type = pair.component1();
            for (String keyword : pair.component2()) {
                if (rawPath.contains(keyword)) {
                    return this.a(rawPath, type, this.a(rawPath, keyword));
                }
            }
        }
        return this.a(rawPath, StorageType.UNKNOWN, "");
    }
    
    @Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004J\u0006\u0010\u0005\u001a\u00020\u0004j\u0002\b\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t¨\u0006\n" }, d2 = { "Lcom/onyx/android/sdk/utils/StorageTypeUtils$StorageType;", "", "(Ljava/lang/String;I)V", "isExternalRemovableStorage", "", "isInternalSDCard", "INTERNAL", "TF_CARD", "USB", "UNKNOWN", "onyxsdk-base_release" })
    public enum StorageType
    {
        INTERNAL, 
        TF_CARD, 
        USB, 
        UNKNOWN;
        
        public final boolean isInternalSDCard() {
            return this == StorageType.INTERNAL;
        }
        
        public final boolean isExternalRemovableStorage() {
            final int n;
            return (n = WhenMappings.$EnumSwitchMapping$0[this.ordinal()]) == 1 || n == 2;
        }

        @Metadata(mv = { 1, 6, 0 }, k = 3, xi = 48)
        public static final class WhenMappings {
            public static final int[] $EnumSwitchMapping$0 = new int[StorageType.values().length];
            private WhenMappings() {
            }
            static {
                try { $EnumSwitchMapping$0[StorageType.TF_CARD.ordinal()] = 1; } catch (NoSuchFieldError ignored) {}
                try { $EnumSwitchMapping$0[StorageType.USB.ordinal()] = 2; } catch (NoSuchFieldError ignored) {}
            }
        }
    }
    
    @Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0005H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J1\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\u000e\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u0000J\t\u0010\u001a\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001b" }, d2 = { "Lcom/onyx/android/sdk/utils/StorageTypeUtils$FilePathInferResult;", "", "absolutePath", "", "storageType", "Lcom/onyx/android/sdk/utils/StorageTypeUtils$StorageType;", "relativePath", "rootPath", "(Ljava/lang/String;Lcom/onyx/android/sdk/utils/StorageTypeUtils$StorageType;Ljava/lang/String;Ljava/lang/String;)V", "getAbsolutePath", "()Ljava/lang/String;", "getRelativePath", "getRootPath", "getStorageType", "()Lcom/onyx/android/sdk/utils/StorageTypeUtils$StorageType;", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "isResultEqual", "toString", "onyxsdk-base_release" })
    public static final class FilePathInferResult
    {
        @NotNull
        private final String a;
        @NotNull
        private final StorageType b;
        @NotNull
        private final String c;
        @NotNull
        private final String d;
        
        public FilePathInferResult(@NotNull final String absolutePath, @NotNull final StorageType storageType, @NotNull final String relativePath, @NotNull final String rootPath) {
            Intrinsics.checkNotNullParameter((Object)absolutePath, "absolutePath");
            Intrinsics.checkNotNullParameter((Object)storageType, "storageType");
            Intrinsics.checkNotNullParameter((Object)relativePath, "relativePath");
            Intrinsics.checkNotNullParameter((Object)rootPath, "rootPath");
            this.a = absolutePath;
            this.b = storageType;
            this.c = relativePath;
            this.d = rootPath;
        }
        
        @NotNull
        public final String getAbsolutePath() {
            return this.a;
        }
        
        @NotNull
        public final StorageType getStorageType() {
            return this.b;
        }
        
        @NotNull
        public final String getRelativePath() {
            return this.c;
        }
        
        @NotNull
        public final String getRootPath() {
            return this.d;
        }
        
        public final boolean isResultEqual(@NotNull final FilePathInferResult other) {
            Intrinsics.checkNotNullParameter((Object)other, "other");
            return this.b == other.b && Intrinsics.areEqual((Object)this.c, (Object)other.c) && Intrinsics.areEqual((Object)this.d, (Object)other.d);
        }
        
        @NotNull
        public final String component1() {
            return this.a;
        }
        
        @NotNull
        public final StorageType component2() {
            return this.b;
        }
        
        @NotNull
        public final String component3() {
            return this.c;
        }
        
        @NotNull
        public final String component4() {
            return this.d;
        }
        
        @NotNull
        public final FilePathInferResult copy(@NotNull final String absolutePath, @NotNull final StorageType storageType, @NotNull final String relativePath, @NotNull final String rootPath) {
            Intrinsics.checkNotNullParameter((Object)absolutePath, "absolutePath");
            Intrinsics.checkNotNullParameter((Object)storageType, "storageType");
            Intrinsics.checkNotNullParameter((Object)relativePath, "relativePath");
            Intrinsics.checkNotNullParameter((Object)rootPath, "rootPath");
            return new FilePathInferResult(absolutePath, storageType, relativePath, rootPath);
        }

        public static /* synthetic */ FilePathInferResult copy$default(final FilePathInferResult filePathInferResult, String absolutePath, StorageType storageType, String relativePath, String rootPath, final int i, final Object obj) {
            if ((i & 1) != 0) {
                absolutePath = filePathInferResult.a;
            }
            if ((i & 2) != 0) {
                storageType = filePathInferResult.b;
            }
            if ((i & 4) != 0) {
                relativePath = filePathInferResult.c;
            }
            if ((i & 8) != 0) {
                rootPath = filePathInferResult.d;
            }
            return filePathInferResult.copy(absolutePath, storageType, relativePath, rootPath);
        }
        
        @NotNull
        @Override
        public String toString() {
            return "FilePathInferResult(absolutePath=" + this.a + ", storageType=" + this.b + ", relativePath=" + this.c + ", rootPath=" + this.d + ')';
        }
        
        @Override
        public int hashCode() {
            return ((this.a.hashCode() * 31 + this.b.hashCode()) * 31 + this.c.hashCode()) * 31 + this.d.hashCode();
        }
        
        @Override
        public boolean equals(@Nullable final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof FilePathInferResult)) {
                return false;
            }
            final FilePathInferResult filePathInferResult = (FilePathInferResult)other;
            return Intrinsics.areEqual((Object)this.a, (Object)filePathInferResult.a) && this.b == filePathInferResult.b && Intrinsics.areEqual((Object)this.c, (Object)filePathInferResult.c) && Intrinsics.areEqual((Object)this.d, (Object)filePathInferResult.d);
        }
    }
    
    @Metadata(mv = { 1, 6, 0 }, k = 1, xi = 48, d1 = { "\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\u0002\u0010\tJ\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0005H\u00c6\u0003J\u0015\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J3\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u0014\b\u0002\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\bH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\b0\u0007¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001a" }, d2 = { "Lcom/onyx/android/sdk/utils/StorageTypeUtils$PathRule;", "", "pattern", "Lkotlin/text/Regex;", "storageType", "Lcom/onyx/android/sdk/utils/StorageTypeUtils$StorageType;", "rootExtractor", "Lkotlin/Function1;", "", "(Lkotlin/text/Regex;Lcom/onyx/android/sdk/utils/StorageTypeUtils$StorageType;Lkotlin/jvm/functions/Function1;)V", "getPattern", "()Lkotlin/text/Regex;", "getRootExtractor", "()Lkotlin/jvm/functions/Function1;", "getStorageType", "()Lcom/onyx/android/sdk/utils/StorageTypeUtils$StorageType;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "onyxsdk-base_release" })
    private static final class a
    {
        @NotNull
        private final Regex a;
        @NotNull
        private final StorageType b;
        @NotNull
        private final Function1<String, String> c;
        
        public a(@NotNull final Regex pattern, @NotNull final StorageType storageType, @NotNull final Function1<? super String, String> rootExtractor) {
            Intrinsics.checkNotNullParameter((Object)pattern, "pattern");
            Intrinsics.checkNotNullParameter((Object)storageType, "storageType");
            Intrinsics.checkNotNullParameter((Object)rootExtractor, "rootExtractor");
            this.a = pattern;
            this.b = storageType;
            this.c = (Function1<String, String>)rootExtractor;
        }
        
        @NotNull
        public final Regex d() {
            return this.a;
        }
        
        @NotNull
        public final StorageType f() {
            return this.b;
        }
        
        @NotNull
        public final Function1<String, String> e() {
            return this.c;
        }
        
        @NotNull
        public final Regex a() {
            return this.a;
        }
        
        @NotNull
        public final StorageType b() {
            return this.b;
        }
        
        @NotNull
        public final Function1<String, String> c() {
            return this.c;
        }
        
        @NotNull
        public final a a(@NotNull final Regex pattern, @NotNull final StorageType storageType, @NotNull final Function1<? super String, String> rootExtractor) {
            Intrinsics.checkNotNullParameter((Object)pattern, "pattern");
            Intrinsics.checkNotNullParameter((Object)storageType, "storageType");
            Intrinsics.checkNotNullParameter((Object)rootExtractor, "rootExtractor");
            return new a(pattern, storageType, rootExtractor);
        }
        
        @NotNull
        @Override
        public String toString() {
            return "PathRule(pattern=" + this.a + ", storageType=" + this.b + ", rootExtractor=" + this.c + ')';
        }
        
        @Override
        public int hashCode() {
            return (this.a.hashCode() * 31 + this.b.hashCode()) * 31 + this.c.hashCode();
        }
        
        @Override
        public boolean equals(@Nullable final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof a)) {
                return false;
            }
            final a a = (a)other;
            return Intrinsics.areEqual((Object)this.a, (Object)a.a) && this.b == a.b && Intrinsics.areEqual((Object)this.c, (Object)a.c);
        }
    }
}
