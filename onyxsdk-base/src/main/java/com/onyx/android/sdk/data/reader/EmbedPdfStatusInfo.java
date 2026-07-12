package com.onyx.android.sdk.data.reader;

public class EmbedPdfStatusInfo {
   public static final int STATUS_START = 0;
   public static final int STATUS_SUCCESS = 1;
   public static final int STATUS_FAIL = 2;
   private String a;
   private int b = 0;

   public String getPath() {
      return this.a;
   }

   public EmbedPdfStatusInfo setPath(String path) {
      this.a = path;
      return this;
   }

   public int getStatus() {
      return this.b;
   }

   public EmbedPdfStatusInfo setStatus(int status) {
      this.b = status;
      return this;
   }

   public boolean isStartStatus() {
      return this.b == 0;
   }

   public boolean isSuccessStatus() {
      return this.b == 1;
   }

   public boolean isFailStatus() {
      return this.b == 2;
   }
}
