package com.onyx.android.sdk.data.note.tag;

public class TagInfo {
   private String a;
   private String b;

   public String getId() {
      return this.a;
   }

   public TagInfo setId(String id) {
      this.a = id;
      return this;
   }

   public String getValue() {
      return this.b;
   }

   public TagInfo setValue(String value) {
      this.b = value;
      return this;
   }
}
