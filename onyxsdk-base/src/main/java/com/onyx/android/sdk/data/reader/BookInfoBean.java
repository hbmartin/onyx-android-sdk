package com.onyx.android.sdk.data.reader;

import com.onyx.android.sdk.utils.StringUtils;

public class BookInfoBean {
   public static final String BOOK_INFO = "book_info";
   public String jdUserPin;
   public String jdPublicationChapterName;
   public String jdGotoChapterId;
   public String position;
   private String a;
   private String b;
   private String c;
   private String d;
   private boolean e = false;
   public int fetchSource;

   public void setFetchSource(int fetchSource) {
      this.fetchSource = fetchSource;
   }

   public BookInfoBean setJdUserPin(String jdUserPin) {
      this.jdUserPin = jdUserPin;
      return this;
   }

   public BookInfoBean setJdPublicationChapterName(String jdPublicationChapterName) {
      this.jdPublicationChapterName = jdPublicationChapterName;
      return this;
   }

   public BookInfoBean setJdGotoChapterId(String jdGotoChapterId) {
      this.jdGotoChapterId = jdGotoChapterId;
      return this;
   }

   public BookInfoBean setPosition(String position) {
      this.position = position;
      return this;
   }

   public int getFetchSource() {
      return this.fetchSource;
   }

   public String getJdUserPin() {
      return StringUtils.safelyGetStr(this.jdUserPin);
   }

   public String getJdPublicationChapterName() {
      return StringUtils.safelyGetStr(this.jdPublicationChapterName);
   }

   public String getJdGotoChapterId() {
      return StringUtils.safelyGetStr(this.jdGotoChapterId);
   }

   public String getPosition() {
      return StringUtils.safelyGetStr(this.position);
   }

   public String getNote() {
      return this.b;
   }

   public void setNote(String note) {
      this.b = note;
   }

   public String getOrgText() {
      return this.a;
   }

   public void setOrgText(String orgText) {
      this.a = orgText;
   }

   public BookInfoBean setPath(String path) {
      this.c = path;
      return this;
   }

   public String getPath() {
      return this.c;
   }

   public String getAttachmentPath() {
      return StringUtils.safelyGetStr(this.d);
   }

   public BookInfoBean setAttachmentPath(String attachmentPath) {
      this.d = attachmentPath;
      return this;
   }

   public boolean isClearPaginationCache() {
      return this.e;
   }

   public void setClearPaginationCache(boolean clearPaginationCache) {
      this.e = clearPaginationCache;
   }
}
