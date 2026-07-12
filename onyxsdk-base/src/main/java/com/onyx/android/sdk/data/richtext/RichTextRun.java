package com.onyx.android.sdk.data.richtext;

import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.utils.ColorUtils;
import com.onyx.android.sdk.utils.NumberUtils;
import com.onyx.android.sdk.utils.StringUtils;
import java.util.List;

public class RichTextRun {
   public List<RichTextRun.TextRunChar> items;
   public RichTextRun.TextRunStyle editorStyle;

   public static class TextRunChar {
      @JSONField(name = "char")
      public String text;
      public float height;
      public float width;
      public float x;
      public float y;
      public float baseline;
      public RichTextRun.TextRunStyle style;

      public char[] getChars() {
         return StringUtils.isNullOrEmpty(this.text) ? new char[]{' '} : this.text.toCharArray();
      }

      public float getLeft() {
         return this.x;
      }

      public float getTop() {
         return this.y;
      }

      public float getBaseLine() {
         return this.baseline;
      }
   }

   public static class TextRunStyle {
      public static final String DECORATION_UNDERLINE = "underline";
      public static final String FONT_STYLE_ITALIC = "italic";
      public static final String FONT_WEIGHT_BOLD = "bold";
      public String backgroundColor;
      public String color;
      public String fontFamily;
      public String fontPath;
      public String fontSize;
      public String fontStyle;
      public String fontWeight;
      public String lineHeight;
      public String textDecoration;
      public int textColor;

      public int getColor() {
         int var1 = this.textColor;
         if (this.textColor == 0) {
            var1 = ColorUtils.parseRGBColor(this.color);
         }

         return var1;
      }

      public boolean isUnderline() {
         return StringUtils.safelyEqualsIgnoreCase(this.textDecoration, "underline");
      }

      public boolean isBold() {
         return StringUtils.safelyEqualsIgnoreCase(this.fontWeight, "bold");
      }

      public boolean isItalic() {
         return StringUtils.safelyEqualsIgnoreCase(this.fontStyle, "italic");
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

      public void setFontPath(String fontPath) {
         this.fontPath = fontPath;
      }

      public String getFontPath(String defValue) {
         if (!StringUtils.isBlank(this.fontPath)) {
            defValue = this.fontPath;
         }

         return defValue;
      }
   }
}
