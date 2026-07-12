package com.onyx.android.sdk.data;

import com.onyx.android.sdk.data.sync.KSyncConstant;

public class ResourceDocumentArgs {
   private String a;
   private String b;
   private String c = KSyncConstant.syncDocDirFilePath();
   private long d;
   private boolean e;

   public static int compare(ResourceDocumentArgs args1, ResourceDocumentArgs args2) {
      if (args1 == null && args2 == null) {
         return 0;
      } else if (args1 == null) {
         return -1;
      } else {
         return args2 == null ? 1 : Long.compare(args1.d, args2.d);
      }
   }

   public String getDocumentId() {
      return this.a;
   }

   public ResourceDocumentArgs setDocumentId(String documentId) {
      this.a = documentId;
      return this;
   }

   public ResourceDocumentArgs setDocDirPath(String docDirPath) {
      this.c = docDirPath;
      return this;
   }

   public String getDocDirPath() {
      return this.c;
   }

   public String getRevisionId() {
      return this.b;
   }

   public ResourceDocumentArgs setRevisionId(String revisionId) {
      this.b = revisionId;
      return this;
   }

   public long getCreateAt() {
      return this.d;
   }

   public ResourceDocumentArgs setCreateAt(long createAt) {
      this.d = createAt;
      return this;
   }

   public boolean isSaveExportRecord() {
      return this.e;
   }

   public ResourceDocumentArgs setSaveExportRecord(boolean save) {
      this.e = save;
      return this;
   }
}
