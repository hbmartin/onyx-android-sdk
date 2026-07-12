package com.onyx.android.sdk.utils;

import androidx.annotation.Nullable;
import com.onyx.android.sdk.data.LinkDocumentArgs;
import com.onyx.android.sdk.data.sync.KSyncConstant;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/LinkDocumentUtils.class */
public class LinkDocumentUtils {
    public static String getLinkFileName(LinkDocumentArgs args) {
        return args.getRevisionId();
    }

    @Nullable
    public static LinkDocumentArgs parseInfo(String fileName) {
        return new LinkDocumentArgs().setRevisionId(fileName);
    }

    public static String linkPbFilePath(LinkDocumentArgs args) {
        return linkPbFileDirPath(args.getDocDirPath(), args.getDocumentId()) + getLinkFileName(args);
    }

    public static String linkPbFileDirPath(String documentId) {
        return linkPbFileDirPath(KSyncConstant.syncDocDirFilePath(), documentId);
    }

    public static List<LinkDocumentArgs> docLinkArgsList(String documentId) {
        return docLinkArgsListByPath(linkPbFileDirPath(documentId));
    }

    public static List<LinkDocumentArgs> docLinkArgsListByPath(String docPbFilePath) {
        LinkedList<File> linkedListListAllFiles = FileUtils.listAllFiles(docPbFilePath, null);
        ArrayList<LinkDocumentArgs> arrayList = new ArrayList<>();
        Iterator<File> it = linkedListListAllFiles.iterator();
        while (it.hasNext()) {
            arrayList.add(parseInfo(it.next().getName()));
        }
        arrayList.sort(LinkDocumentArgs::compare);
        return arrayList;
    }

    public static Set<String> docLinkFilePbNameList(String documentId) {
        LinkedList<File> linkedListListAllFiles = FileUtils.listAllFiles(linkPbFileDirPath(documentId), null);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<File> it = linkedListListAllFiles.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(it.next().getName());
        }
        return linkedHashSet;
    }

    public static List<File> docLinkFilePbFileList(String documentId) {
        LinkedList<File> linkedListListAllFiles = FileUtils.listAllFiles(linkPbFileDirPath(documentId), null);
        linkedListListAllFiles.sort((o1, o2) -> {
            return LinkDocumentArgs.compare(parseInfo(o1.getName()), parseInfo(o2.getName()));
        });
        return linkedListListAllFiles;
    }

    public static long currentTimestamp() {
        return System.currentTimeMillis();
    }

    public static String newRevisionId() {
        return UUIDUtils.timeBasedEpochUUID();
    }

    public static String linkPbFileDirPath(String pbDirPath, String documentId) {
        StringBuilder sbAppend = new StringBuilder().append(KSyncConstant.docDirFilePath(pbDirPath, documentId));
        String str = File.separator;
        return sbAppend.append(str).append("link").append(str).append("pb").append(str).toString();
    }

    public static String linkPbFilePath(String pbDirPath, LinkDocumentArgs args) {
        return linkPbFileDirPath(pbDirPath, args.getDocumentId()) + getLinkFileName(args);
    }
}
