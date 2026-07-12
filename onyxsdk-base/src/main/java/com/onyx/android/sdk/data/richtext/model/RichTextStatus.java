package com.onyx.android.sdk.data.richtext.model;

import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.utils.StringUtils;
import java.util.List;

public class RichTextStatus {
   public List<RichTextStatus.Node> nodes;
   public List<RichTextStatus.Mark> marks;

   public static class Attrs {
      public int level;
      public String fontFamily;
      public String fontFamilyName;
      public String fontSize;
      public String fontColor;
      public String textAlign;
      public String lineHeight;
      public String letterSpacing;
      public int indent;
      public int start;
      public String bgColor;
      public String href;
      @JSONField(name = "class")
      public String clazz;
      public String rel;
      public String target;

      public String getFontFamily() {
         return StringUtils.isBlank(this.fontFamilyName) ? this.fontFamily : this.fontFamilyName;
      }
   }

   public static class Mark {
      public RichTextStatus.Attrs attrs;
      public RichTextMark name;
      public String type;
   }

   public static class Node {
      public RichTextStatus.Attrs attrs;
      public RichTextNode name;
   }
}
