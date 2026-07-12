package com.onyx.android.sdk.data;

import com.onyx.android.sdk.utils.StringUtils;
import com.onyx.android.sdk.utils.UUIDUtils;

public class PBDocumentArgs {
   private String a;
   private String b;
   private boolean c;

   public static int compare(PBDocumentArgs args1, PBDocumentArgs args2) {
      if (isNullOrEmpty(args1) && isNullOrEmpty(args2)) {
         return 0;
      } else if (isNullOrEmpty(args1)) {
         return -1;
      } else {
         return isNullOrEmpty(args2) ? 1 : UUIDUtils.safelyCompareTo(args1.b, args2.b);
      }
   }

   public static boolean isNullOrEmpty(PBDocumentArgs args) {
      return args == null || StringUtils.isNullOrEmpty(args.b);
   }

   public String getDocumentId() {
      return this.a;
   }

   public PBDocumentArgs setDocumentId(String documentId) {
      this.a = documentId;
      return this;
   }

   public String getRevisionId() {
      return this.b;
   }

   public PBDocumentArgs setRevisionId(String revisionId) {
      this.b = revisionId;
      return this;
   }

   public boolean isSaveExportRecord() {
      return this.c;
   }

   public PBDocumentArgs setSaveExportRecord(boolean save) {
      this.c = save;
      return this;
   }
}
