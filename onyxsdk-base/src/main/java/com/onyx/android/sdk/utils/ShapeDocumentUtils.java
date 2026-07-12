package com.onyx.android.sdk.utils;

import com.onyx.android.sdk.data.ShapeDocumentArgs;
import com.onyx.android.sdk.data.sync.KSyncConstant;
import com.onyx.android.sdk.extension.AnyKt;
import com.onyx.android.sdk.extension.CollectionKt;
import com.onyx.android.sdk.extension.Files;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/ShapeDocumentUtils.class */
@Metadata(mv = {1, 6, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\b\t\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\b\u001a\u00020\tH\u0007J\u001c\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\u0004J\"\u0010\u000f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0011\u001a\u00020\u0004H\u0007J\u001e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00100\u000b2\u0006\u0010\u0013\u001a\u00020\u00042\u0006\u0010\u0011\u001a\u00020\u0004H\u0007J:\u0010\u0014\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u000b0\u00152\u0006\u0010\u0013\u001a\u00020\u00042\u0018\b\u0002\u0010\u0016\u001a\u0012\u0012\u0004\u0012\u00020\u00100\u0017j\b\u0012\u0004\u0012\u00020\u0010`\u0018J.\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00100\u000b2\u0006\u0010\u0013\u001a\u00020\u00042\u0018\b\u0002\u0010\u0016\u001a\u0012\u0012\u0004\u0012\u00020\u00100\u0017j\b\u0012\u0004\u0012\u00020\u0010`\u0018J\u001a\u0010\u0019\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u000b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0004H\u0007J\u0018\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00100\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0004H\u0007J\u0018\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0004H\u0007J\u0018\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00040\u001d2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0004H\u0007J\u0010\u0010\u001e\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u0010H\u0007J\u0012\u0010 \u001a\u0004\u0018\u00010\u00102\u0006\u0010!\u001a\u00020\u0004H\u0007J\u0012\u0010\"\u001a\u00020\u00042\b\u0010\u000e\u001a\u0004\u0018\u00010\u0004H\u0007J\u001c\u0010\"\u001a\u00020\u00042\b\u0010#\u001a\u0004\u0018\u00010\u00042\b\u0010\u000e\u001a\u0004\u0018\u00010\u0004H\u0007J\u0010\u0010$\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u0010H\u0007J\u0010\u0010%\u001a\u00020\u00042\u0006\u0010\u001f\u001a\u00020\u0010H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006&"}, d2 = {"Lcom/onyx/android/sdk/utils/ShapeDocumentUtils;", TTFFont.UNKNOWN_FONT_NAME, "()V", "SHAPE_FILE_END", TTFFont.UNKNOWN_FONT_NAME, "SHAPE_FILE_NAME_SEGMENT_COUNT", TTFFont.UNKNOWN_FONT_NAME, "SHAPE_FILE_SPLIT_CHAR", "currentTimestamp", TTFFont.UNKNOWN_FONT_NAME, "docHwrShapeFileList", TTFFont.UNKNOWN_FONT_NAME, "Ljava/io/File;", "parentDir", "documentId", "docPageShapeArgsList", "Lcom/onyx/android/sdk/data/ShapeDocumentArgs;", "pageId", "docPageShapeArgsListByPath", "docPbDirPath", "docPageShapeArgsMapByPath", TTFFont.UNKNOWN_FONT_NAME, "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "docShapeArgsListByPath", "docShapeFileArgsList", "docShapeFileList", "docShapeFileNameList", TTFFont.UNKNOWN_FONT_NAME, "getPageShapeFileName", "args", "parseInfo", "fileName", "shapeFileDirPath", "pbDirPath", "shapeFilePath", "shapeZipFilePath", "onyxsdk-base_release"})
public final class ShapeDocumentUtils {

    @NotNull
    public static final ShapeDocumentUtils INSTANCE = new ShapeDocumentUtils();

    @NotNull
    private static final String a = "#";

    @NotNull
    public static final String SHAPE_FILE_END = ".zip";
    private static final int b = 3;

    private ShapeDocumentUtils() {
    }

    @JvmStatic
    @NotNull
    public static final String getPageShapeFileName(@NotNull ShapeDocumentArgs args) {
        Intrinsics.checkNotNullParameter(args, "args");
        return ((Object) args.getPageId()) + "#" + ((Object) args.getRevisionId()) + "#" + args.getCreateAt();
    }

    @JvmStatic
    @Nullable
    public static final ShapeDocumentArgs parseInfo(@NotNull String fileName) {
        List listEmptyList;
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        List listSplit = new Regex("#").split(fileName.replace(".zip", TTFFont.UNKNOWN_FONT_NAME), 0);
        if (!listSplit.isEmpty()) {
            ListIterator listIterator = listSplit.listIterator(listSplit.size());
            while (true) {
                if (!listIterator.hasPrevious()) {
                    listEmptyList = CollectionsKt.emptyList();
                    break;
                }
                if (!(((String) listIterator.previous()).length() == 0)) {
                    listEmptyList = CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                    break;
                }
            }
        } else {
            listEmptyList = CollectionsKt.emptyList();
        }
        Object[] array = listEmptyList.toArray(new String[0]);
        if (array == null) {
            throw new NullPointerException("null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>");
        }
        String[] strArr = (String[]) array;
        if (strArr.length != 3) {
            return null;
        }
        return new ShapeDocumentArgs().setPageId(strArr[0]).setRevisionId(strArr[1]).setCreateAt(NumberUtils.parseLong(strArr[2]));
    }

    @JvmStatic
    @NotNull
    public static final String shapeFilePath(@NotNull ShapeDocumentArgs args) {
        Intrinsics.checkNotNullParameter(args, "args");
        return Intrinsics.stringPlus(shapeFileDirPath(args.getPbDirPath(), args.getDocumentId()), getPageShapeFileName(args));
    }

    @JvmStatic
    @NotNull
    public static final String shapeZipFilePath(@NotNull ShapeDocumentArgs args) {
        Intrinsics.checkNotNullParameter(args, "args");
        return Intrinsics.stringPlus(shapeFilePath(args), ".zip");
    }

    @JvmStatic
    @NotNull
    public static final String shapeFileDirPath(@Nullable String documentId) {
        return shapeFileDirPath(KSyncConstant.syncDocDirFilePath(), documentId);
    }

    @JvmStatic
    @NotNull
    public static final Set<String> docShapeFileNameList(@Nullable String documentId) {
        LinkedList<File> linkedListListAllFiles = FileUtils.listAllFiles(shapeFileDirPath(documentId), null);
        Intrinsics.checkNotNullExpressionValue(linkedListListAllFiles, "listAllFiles(shapeFileDirPath(documentId), null)");
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<File> it = linkedListListAllFiles.iterator();
        while (it.hasNext()) {
            String name = it.next().getName();
            Intrinsics.checkNotNullExpressionValue(name, "file.name");
            linkedHashSet.add(name);
        }
        return linkedHashSet;
    }

    @JvmStatic
    @NotNull
    public static final List<ShapeDocumentArgs> docPageShapeArgsList(@Nullable String documentId, @NotNull String pageId) {
        Intrinsics.checkNotNullParameter(pageId, "pageId");
        return docPageShapeArgsListByPath(shapeFileDirPath(documentId), pageId);
    }

    @JvmStatic
    @NotNull
    public static final List<ShapeDocumentArgs> docPageShapeArgsListByPath(@NotNull String docPbDirPath, @NotNull String pageId) {
        Intrinsics.checkNotNullParameter(docPbDirPath, "docPbDirPath");
        Intrinsics.checkNotNullParameter(pageId, "pageId");
        List listDocShapeArgsListByPath$default = docShapeArgsListByPath$default(INSTANCE, docPbDirPath, null, 2, null);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listDocShapeArgsListByPath$default) {
            if (Intrinsics.areEqual(((ShapeDocumentArgs) obj).getPageId(), pageId)) {
                arrayList.add(obj);
            }
        }
        return arrayList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ List docShapeArgsListByPath$default(ShapeDocumentUtils shapeDocumentUtils, String str, Comparator comparator, int i, Object obj) {
        if ((i & 2) != 0) {
            comparator = (Comparator<ShapeDocumentArgs>) ShapeDocumentArgs::compare;
        }
        return shapeDocumentUtils.docShapeArgsListByPath(str, comparator);
    }

    @JvmStatic
    @NotNull
    public static final List<File> docShapeFileList(@Nullable String documentId) {
        LinkedList<File> linkedListListAllFiles = FileUtils.listAllFiles(shapeFileDirPath(documentId), null);
        Intrinsics.checkNotNullExpressionValue(linkedListListAllFiles, "listAllFiles(shapeFileDirPath(documentId), null)");
        CollectionsKt.sortedWith(linkedListListAllFiles, ShapeDocumentUtils::b);
        return linkedListListAllFiles;
    }

    @JvmStatic
    @NotNull
    public static final List<ShapeDocumentArgs> docShapeFileArgsList(@Nullable String documentId) {
        LinkedList<File> linkedListListAllFiles = FileUtils.listAllFiles(shapeFileDirPath(documentId), null);
        Intrinsics.checkNotNullExpressionValue(linkedListListAllFiles, "listAllFiles(shapeFileDirPath(documentId), null)");
        ArrayList arrayList = new ArrayList();
        Iterator<File> it = linkedListListAllFiles.iterator();
        while (it.hasNext()) {
            String name = it.next().getName();
            Intrinsics.checkNotNullExpressionValue(name, "docFile.name");
            CollectionKt.safeAdd(arrayList, parseInfo(name));
        }
        CollectionsKt.sortWith(arrayList, (Comparator<ShapeDocumentArgs>) ShapeDocumentArgs::compare);
        return arrayList;
    }

    @JvmStatic
    public static final long currentTimestamp() {
        return System.currentTimeMillis();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Map docPageShapeArgsMapByPath$default(ShapeDocumentUtils shapeDocumentUtils, String str, Comparator comparator, int i, Object obj) {
        if ((i & 2) != 0) {
            comparator = (Comparator<ShapeDocumentArgs>) ShapeDocumentArgs::compare;
        }
        return shapeDocumentUtils.docPageShapeArgsMapByPath(str, comparator);
    }

    private static final int b(File o1, File o2) {
        Intrinsics.checkNotNullParameter(o1, "o1");
        Intrinsics.checkNotNullParameter(o2, "o2");
        String name = o1.getName();
        Intrinsics.checkNotNullExpressionValue(name, "o1.name");
        ShapeDocumentArgs info = parseInfo(name);
        String name2 = o2.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "o2.name");
        return ShapeDocumentArgs.compare(info, parseInfo(name2));
    }

    private static final int a(File o1, File o2) {
        Intrinsics.checkNotNullParameter(o1, "o1");
        Intrinsics.checkNotNullParameter(o2, "o2");
        String name = o1.getName();
        Intrinsics.checkNotNullExpressionValue(name, "o1.name");
        ShapeDocumentArgs info = parseInfo(name);
        String name2 = o2.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "o2.name");
        return ShapeDocumentArgs.compare(info, parseInfo(name2));
    }

    @NotNull
    public final List<ShapeDocumentArgs> docShapeArgsListByPath(@NotNull String docPbDirPath, @NotNull Comparator<ShapeDocumentArgs> comparator) {
        Intrinsics.checkNotNullParameter(docPbDirPath, "docPbDirPath");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        Benchmark benchmark = new Benchmark();
        List<File> listListAllFiles = Files.INSTANCE.listAllFiles(docPbDirPath);
        benchmark.report(Intrinsics.stringPlus("docShapeArgsListByPath list all file size: ", Integer.valueOf(listListAllFiles.size())), 1000L);
        ArrayList arrayList = new ArrayList();
        for (Object obj : listListAllFiles) {
            File file = (File) obj;
            if (file.exists() && file.isFile()) {
                arrayList.add(obj);
            }
        }
        if (CollectionKt.getSize(listListAllFiles) != CollectionKt.getSize(arrayList)) {
            String simpleNameString = AnyKt.toSimpleNameString(this);
            Set setMinus = SetsKt.minus(CollectionsKt.toSet(listListAllFiles), CollectionsKt.toSet(arrayList));
            Debug.w(simpleNameString, "docPageShapeArgsListByPath listAllFiles(" + CollectionKt.getSize(listListAllFiles) + ")!=docFiles(" + CollectionKt.getSize(arrayList) + ')', new Object[0]);
            Iterator it = setMinus.iterator();
            while (it.hasNext()) {
                Debug.w(simpleNameString, Intrinsics.stringPlus("docPageShapeArgsListByPath not exist file:", (File) it.next()), new Object[0]);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            String name = ((File) it2.next()).getName();
            Intrinsics.checkNotNullExpressionValue(name, "docFile.name");
            CollectionKt.safeAdd(arrayList2, parseInfo(name));
        }
        CollectionsKt.sortWith(arrayList2, comparator);
        return arrayList2;
    }

    @NotNull
    public final List<File> docHwrShapeFileList(@NotNull String parentDir, @NotNull String documentId) {
        Intrinsics.checkNotNullParameter(parentDir, "parentDir");
        Intrinsics.checkNotNullParameter(documentId, "documentId");
        LinkedList<File> linkedListListAllFiles = FileUtils.listAllFiles(shapeFileDirPath(parentDir, documentId), null);
        Intrinsics.checkNotNullExpressionValue(linkedListListAllFiles, "listAllFiles(shapeFileDi…ntDir, documentId), null)");
        CollectionsKt.sortedWith(linkedListListAllFiles, ShapeDocumentUtils::a);
        return linkedListListAllFiles;
    }

    @NotNull
    public final Map<String, List<ShapeDocumentArgs>> docPageShapeArgsMapByPath(@NotNull String docPbDirPath, @NotNull Comparator<ShapeDocumentArgs> comparator) {
        Intrinsics.checkNotNullParameter(docPbDirPath, "docPbDirPath");
        Intrinsics.checkNotNullParameter(comparator, "comparator");
        List<ShapeDocumentArgs> listDocShapeArgsListByPath = docShapeArgsListByPath(docPbDirPath, comparator);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Object obj : listDocShapeArgsListByPath) {
            String pageId = ((ShapeDocumentArgs) obj).getPageId();
            String str = pageId;
            if (pageId == null) {
                str = TTFFont.UNKNOWN_FONT_NAME;
            }
            Object obj2 = linkedHashMap.get(str);
            Object obj3 = obj2;
            if (obj2 == null) {
                obj3 = new ArrayList();
                linkedHashMap.put(str, obj3);
            }
            ((List) obj3).add(obj);
        }
        return linkedHashMap;
    }

    @JvmStatic
    @NotNull
    public static final String shapeFileDirPath(@Nullable String pbDirPath, @Nullable String documentId) {
        StringBuilder sbAppend = new StringBuilder().append(KSyncConstant.docDirFilePath(pbDirPath, documentId));
        String str = File.separator;
        String string = sbAppend.append((Object) str).append("shape").append((Object) str).toString();
        FileUtils.mkdirs(string);
        return string;
    }

    @JvmStatic
    @NotNull
    public static final List<ShapeDocumentArgs> docShapeArgsListByPath(@Nullable String docPbDirPath) {
        LinkedList<File> linkedListListAllFiles = FileUtils.listAllFiles(docPbDirPath, null);
        Intrinsics.checkNotNullExpressionValue(linkedListListAllFiles, "listAllFiles(docPbDirPath, null)");
        ArrayList arrayList = new ArrayList();
        Iterator<File> it = linkedListListAllFiles.iterator();
        while (it.hasNext()) {
            String name = it.next().getName();
            Intrinsics.checkNotNullExpressionValue(name, "fileName");
            arrayList.add(parseInfo(name));
        }
        CollectionsKt.sortWith(arrayList, (Comparator<ShapeDocumentArgs>) ShapeDocumentArgs::compare);
        return arrayList;
    }
}
