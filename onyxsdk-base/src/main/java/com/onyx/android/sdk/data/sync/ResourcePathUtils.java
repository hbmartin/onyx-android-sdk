package com.onyx.android.sdk.data.sync;

import com.onyx.android.sdk.path.KSyncFilePaths;
import com.onyx.android.sdk.utils.FileUtils;
import com.onyx.android.sdk.utils.StringUtils;
import com.onyx.android.sdk.utils.TTFFont;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/sync/ResourcePathUtils.class */
public class ResourcePathUtils {
    public static String getResourceDataDirPath(String documentId) {
        StringBuilder sbAppend = new StringBuilder().append(KSyncConstant.docDirFilePath(documentId));
        String str = File.separator;
        String string = sbAppend.append(str).append("resource").append(str).append("data").toString();
        FileUtils.mkdirs(string);
        return string;
    }

    public static String getResourceDataDirPathByDocDirPath(String docDirPath, String documentId) {
        StringBuilder sbAppend = new StringBuilder().append(KSyncConstant.docDirFilePath(docDirPath, documentId));
        String str = File.separator;
        String string = sbAppend.append(str).append("resource").append(str).append("data").toString();
        FileUtils.mkdirs(string);
        return string;
    }

    public static String getOldResourceDirPath(String dirPath) {
        return dirPath + File.separator + "resource";
    }

    public static String getLocalResourceDataFilePath(String documentId, String fileName) {
        return getResourceDataDirPath(documentId) + File.separator + fileName;
    }

    public static Set<String> docResourceDataFileNameList(String documentId) {
        LinkedList<File> linkedListListAllFiles = FileUtils.listAllFiles(getResourceDataDirPath(documentId), null);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Iterator<File> it = linkedListListAllFiles.iterator();
        while (it.hasNext()) {
            linkedHashSet.add(it.next().getName());
        }
        return linkedHashSet;
    }

    public static String getResourceDataFilePath(String documentId, String resourceId, String fileExtension) {
        return FileUtils.combine(getLocalResourceDataFilePath(documentId, resourceId), fileExtension);
    }

    public static String getBKGroundResDataFilePath(String documentId, String fileName) {
        return getLocalResourceDataFilePath(documentId, fileName);
    }

    public static String getResourceDataCopyPath(String documentId, String srcPath) {
        return getLocalResourceDataFilePath(documentId, FileUtils.getFileName(srcPath));
    }

    public static String getRelativePath(String documentId, String filePath) {
        if (StringUtils.isNullOrEmpty(filePath)) {
            return TTFFont.UNKNOWN_FONT_NAME;
        }
        if (StringUtils.isNotBlank(filePath)) {
            Iterator<String> it = KSyncFilePaths.INSTANCE.getBooxSyncFilePathList().iterator();
            while (it.hasNext()) {
                String resourceDataDirPath = getResourceDataDirPath(it.next(), documentId);
                if (filePath.startsWith(resourceDataDirPath)) {
                    return filePath.substring(resourceDataDirPath.length());
                }
            }
            Iterator<String> it2 = KSyncFilePaths.INSTANCE.getBooxSyncFilePathList().iterator();
            while (it2.hasNext()) {
                String oldResourceDirPath = getOldResourceDirPath(it2.next());
                if (filePath.startsWith(oldResourceDirPath)) {
                    String strSubstring = filePath.substring(oldResourceDirPath.length());
                    String strSubstring2 = strSubstring;
                    String str = File.separator + documentId;
                    if (strSubstring.startsWith(str)) {
                        strSubstring2 = strSubstring2.substring(str.length());
                    }
                    return strSubstring2;
                }
            }
        }
        return File.separator + FileUtils.getFileName(filePath);
    }

    public static boolean isKSyncFile(String path) {
        return StringUtils.startsWithIgnoreCase(path, KSyncFilePaths.INSTANCE.getKSyncFilesPath());
    }

    public static boolean isNoteAttachmentFile(String path) {
        return StringUtils.startsWithIgnoreCase(path, KSyncConstant.syncDocDirFilePath()) && !isNetDiskFile(path);
    }

    public static boolean isNetDiskFile(String path) {
        return StringUtils.startsWithIgnoreCase(path, KSyncConstant.netDiskDirPath());
    }

    public static String parseDocumentIdByResourceFile(String resourcePath) {
        if (!isKSyncFile(resourcePath)) {
            return TTFFont.UNKNOWN_FONT_NAME;
        }
        String[] strArrSplit = resourcePath.substring(KSyncFilePaths.INSTANCE.getKSyncFilesPath().length()).split(File.separator);
        return (strArrSplit.length >= 3 && StringUtils.safelyEqualsIgnoreCase("document", strArrSplit[1])) ? strArrSplit[2] : TTFFont.UNKNOWN_FONT_NAME;
    }

    public static String getResourceDataDirPath(String dirPath, String documentId) {
        StringBuilder sbAppend = new StringBuilder().append(dirPath);
        String str = File.separator;
        String string = sbAppend.append(str).append("document").append(str).append(documentId).append(str).append("resource").append(str).append("data").toString();
        FileUtils.mkdirs(string);
        return string;
    }
}
