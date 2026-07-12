package com.onyx.android.sdk.data.richtext.model;

public class RichTextImage {
   public String src;
   public String width;
   public String height;
   public String title;
   public String dataKey;

   public RichTextImage() {
   }

   public RichTextImage(String src, String dataKey) {
      this.src = src;
      this.dataKey = dataKey;
   }
}
