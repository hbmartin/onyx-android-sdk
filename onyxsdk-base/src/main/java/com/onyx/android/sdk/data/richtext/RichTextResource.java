package com.onyx.android.sdk.data.richtext;

import com.onyx.android.sdk.data.richtext.v2.RichTextResourceLoaderV2;
import com.onyx.android.sdk.utils.FileUtils;
import com.onyx.android.sdk.utils.StringUtils;

public class RichTextResource {
   private String a;
   private final String b;

   public RichTextResource(String filePath) {
      this.b = filePath;
   }

   private RichTextResourceLoader a() throws Exception {
      return new RichTextResourceLoaderV2();
   }

   public String getContent() {
      return this.a;
   }

   public RichTextResource setContent(String content) {
      this.a = content;
      return this;
   }

   public String getFilePath() {
      return this.b;
   }

   public RichTextResource parse() throws Exception {
      this.a().parse(this);
      return this;
   }

   public boolean save() throws Exception {
      if (StringUtils.isNullOrEmpty(this.a)) {
         return false;
      }

      FileUtils.deleteFile(this.b);
      return this.a().save(this);
   }
}
