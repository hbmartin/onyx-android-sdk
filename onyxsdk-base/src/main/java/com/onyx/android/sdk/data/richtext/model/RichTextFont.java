package com.onyx.android.sdk.data.richtext.model;

import com.onyx.android.sdk.utils.StringUtils;

public class RichTextFont {
   public String fontFamily;
   public String fontFamilyName;
   public String fontSource;

   public RichTextFont() {
   }

   public RichTextFont(String fontFamily, String fontSource) {
      this.fontFamily = fontFamily;
      this.fontSource = fontSource;
   }

   public String getFileName() {
      return this.fontFamily;
   }

   public String getFontFamily() {
      return StringUtils.isBlank(this.fontFamilyName) ? this.fontFamily : this.fontFamilyName;
   }
}
