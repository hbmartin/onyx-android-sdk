package com.onyx.android.sdk.data;

import com.onyx.android.sdk.data.sync.KSyncConstant;
import com.onyx.android.sdk.utils.StringUtils;
import java.util.UUID;

public class LinkDocumentArgs {
   private String a;
   private String b;
   private boolean c;
   private String d = KSyncConstant.syncDocDirFilePath();

   public static int compare(LinkDocumentArgs args1, LinkDocumentArgs args2) {
      if (isNullOrEmpty(args1) && isNullOrEmpty(args2)) {
         return 0;
      } else if (isNullOrEmpty(args1)) {
         return -1;
      } else {
         return isNullOrEmpty(args2) ? 1 : UUID.fromString(args1.b).compareTo(UUID.fromString(args2.b));
      }
   }

   public static boolean isNullOrEmpty(LinkDocumentArgs args) {
      return args == null || StringUtils.isNullOrEmpty(args.b);
   }

   public String getDocumentId() {
      return this.a;
   }

   public LinkDocumentArgs setDocumentId(String documentId) {
      this.a = documentId;
      return this;
   }

   public String getRevisionId() {
      return this.b;
   }

   public LinkDocumentArgs setRevisionId(String revisionId) {
      this.b = revisionId;
      return this;
   }

   public LinkDocumentArgs setDocDirPath(String docDirPath) {
      this.d = docDirPath;
      return this;
   }

   public String getDocDirPath() {
      return this.d;
   }

   public boolean isSaveExportRecord() {
      return this.c;
   }

   public LinkDocumentArgs setSaveExportRecord(boolean save) {
      this.c = save;
      return this;
   }
}
