package com.onyx.android.sdk.data.richtext;

import com.onyx.android.sdk.utils.ColorUtils;
import com.onyx.android.sdk.utils.NumberUtils;
import com.onyx.android.sdk.utils.StringUtils;

public class EditorStyleInfo {
   public boolean bold;
   public boolean italic;
   public boolean underline;
   public String color;
   public String textAlign;
   public String lineHeight;
   public String letterSpacing;
   public String fontSize;
   public String fontFamily;
   public int charCount;
   public int textColor;

   public int getTextColor() {
      int var1 = this.textColor;
      if (this.textColor == 0) {
         var1 = ColorUtils.parseRGBColor(this.color);
      }

      return var1;
   }

   public float getLineHeight() {
      return NumberUtils.parseFloat(this.lineHeight);
   }

   public String getFontFamily(String defValue) {
      if (!StringUtils.isBlank(this.fontFamily)) {
         defValue = this.fontFamily;
      }

      return defValue;
   }

   public float getFontSize(float defValue) {
      if (StringUtils.isBlank(this.fontSize)) {
         return defValue;
      }

      float var2;
      if (!((var2 = NumberUtils.parseFloat(this.fontSize.replace("px", ""))) > 0.0F)) {
         var2 = defValue;
      }

      return var2;
   }
}
