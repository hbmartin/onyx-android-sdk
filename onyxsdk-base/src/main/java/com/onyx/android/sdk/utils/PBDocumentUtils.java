package com.onyx.android.sdk.utils;

import com.onyx.android.sdk.data.PBDocumentArgs;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class PBDocumentUtils {
   public static List<PBDocumentArgs> loadDocPBArgsList(String pbDirPath) {
      return loadDocPBFileList(pbDirPath).stream().map(o -> parseArgs(o.getName())).collect(Collectors.toList());
   }

   public static List<String> loadDocPBFileNameList(String pbDirPath) {
      return loadDocPBFileList(pbDirPath).stream().map(File::getName).collect(Collectors.toList());
   }

   public static List<File> loadDocPBFileList(String pbDirPath) {
      LinkedList<File> var10000 = FileUtils.listAllFiles(pbDirPath, null);
      var10000.sort((o1, o2) -> PBDocumentArgs.compare(parseArgs(o1.getName()), parseArgs(o2.getName())));
      return var10000;
   }

   public static boolean hasDocPBFile(String pbDirPath) {
      return CollectionUtils.isNonBlank(FileUtils.listAllFiles(pbDirPath, null));
   }

   public static long currentTimestamp() {
      return System.currentTimeMillis();
   }

   public static String newRevisionId() {
      return UUIDUtils.timeBasedEpochUUID();
   }

   public static PBDocumentArgs parseArgs(String fileName) {
      return new PBDocumentArgs().setRevisionId(fileName);
   }
}
