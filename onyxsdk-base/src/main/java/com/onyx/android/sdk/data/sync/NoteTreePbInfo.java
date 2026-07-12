package com.onyx.android.sdk.data.sync;

public class NoteTreePbInfo {
   private String a;
   private String b;
   private long c;
   private int d;
   private int e;
   private String f;

   public NoteTreePbInfo setOssKey(String ossKey) {
      this.a = ossKey;
      return this;
   }

   public String getOssKey() {
      return this.a;
   }

   public String getDevice() {
      return this.b;
   }

   public NoteTreePbInfo setDevice(String device) {
      this.b = device;
      return this;
   }

   public long getCreateAt() {
      return this.c;
   }

   public NoteTreePbInfo setCreateAt(long createAt) {
      this.c = createAt;
      return this;
   }

   public int getDocCount() {
      return this.d;
   }

   public NoteTreePbInfo setDocCount(int docCount) {
      this.d = docCount;
      return this;
   }

   public int getFolderCount() {
      return this.e;
   }

   public NoteTreePbInfo setFolderCount(int folderCount) {
      this.e = folderCount;
      return this;
   }

   public void setMd5(String md5) {
      this.f = md5;
   }

   public String getMd5() {
      return this.f;
   }
}
