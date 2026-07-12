package com.onyx.android.sdk.data.note;

import androidx.annotation.Nullable;
import com.onyx.android.sdk.data.ResourceDocumentArgs;
import com.onyx.android.sdk.data.sync.KSyncConstant;
import com.onyx.android.sdk.utils.FileUtils;
import com.onyx.android.sdk.utils.NumberUtils;
import com.onyx.android.sdk.utils.UUIDUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ResourcePBDocumentUtils {
    private static final String a = "#";
    private static final int b = 2;

    public static String getResourceFileName(ResourceDocumentArgs args) {
        return args.getRevisionId() + "#" + args.getCreateAt();
    }

    @Nullable
    public static ResourceDocumentArgs parseInfo(String fileName) {
        String[] strArrSplit = fileName.split("#");
        if (strArrSplit.length != 2) {
            return null;
        }
        return new ResourceDocumentArgs().setRevisionId(strArrSplit[0]).setCreateAt(NumberUtils.parseLong(strArrSplit[1]));
    }

    public static String resourcePbFilePath(ResourceDocumentArgs args) {
        return resourcePbFileDirPath(args.getDocDirPath(), args.getDocumentId()) + getResourceFileName(args);
    }

    public static String resourcePbFileDirPath(String documentId) {
        return resourcePbFileDirPath(KSyncConstant.syncDocDirFilePath(), documentId);
    }

    public static List<ResourceDocumentArgs> docResourceArgsList(String documentId) {
        return docResourceArgsListByPath(resourcePbFileDirPath(documentId));
    }

    public static List<ResourceDocumentArgs> docResourceArgsListByPath(String docPBPath) {
        LinkedList<File> linkedListListAllFiles = FileUtils.listAllFiles(docPBPath, null);
        ArrayList arrayList = new ArrayList();
        Iterator<File> it = linkedListListAllFiles.iterator();
        while (it.hasNext()) {
            arrayList.add(parseInfo(it.next().getName()));
        }
          arrayList.sort((left, right) -> ResourceDocumentArgs.compare(
                  (ResourceDocumentArgs) left, (ResourceDocumentArgs) right));
        return arrayList;
    }

    public static Set<String> docResourceFilePbNameList(String documentId) {
        LinkedList<File> linkedListListAllFiles = FileUtils.listAllFiles(resourcePbFileDirPath(documentId), null);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<File> it = linkedListListAllFiles.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(it.next().getName());
        }
        return linkedHashSet;
    }

    public static long currentTimestamp() {
        return System.currentTimeMillis();
    }

    public static String newRevisionId() {
        return UUIDUtils.randomRawUUID();
    }

    public static String resourcePbFileDirPath(String pbDirPath, String documentId) {
        StringBuilder sbAppend = new StringBuilder().append(KSyncConstant.docDirFilePath(pbDirPath, documentId));
        String str = File.separator;
        return sbAppend.append(str).append("resource").append(str).append("pb").append(str).toString();
    }

    public static String resourcePbFilePath(String pbDirPath, ResourceDocumentArgs args) {
        return resourcePbFileDirPath(pbDirPath, args.getDocumentId()) + getResourceFileName(args);
    }
}
