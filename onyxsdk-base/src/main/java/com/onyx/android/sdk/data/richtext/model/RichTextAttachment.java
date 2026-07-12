package com.onyx.android.sdk.data.richtext.model;

public class RichTextAttachment {
   public String src;
   public String key;
   public String title;
   public String format;
   public long size;

   @Override
   public String toString() {
      return "RichTextAttachment{src='"
         + this.src
         + '\''
         + ", key='"
         + this.key
         + '\''
         + ", title='"
         + this.title
         + '\''
         + ", format='"
         + this.format
         + '\''
         + ", size="
         + this.size
         + '}';
   }
}
