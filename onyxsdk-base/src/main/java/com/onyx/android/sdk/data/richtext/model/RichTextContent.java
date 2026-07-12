package com.onyx.android.sdk.data.richtext.model;

import java.io.Serializable;
import java.util.List;

public class RichTextContent implements Serializable {
   public List<RichTextContent> content;
   public String attrs;
   public String text;
   public String type;
   public List<RichTextStatus.Mark> marks;

   public List<RichTextContent> getContent() {
      return this.content;
   }

   public String getType() {
      return this.type;
   }
}
