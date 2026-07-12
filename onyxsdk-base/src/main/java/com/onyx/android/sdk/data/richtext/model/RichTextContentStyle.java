package com.onyx.android.sdk.data.richtext.model;

import com.onyx.android.sdk.utils.NumberUtils;
import com.onyx.android.sdk.utils.StringUtils;
import java.io.Serializable;

public class RichTextContentStyle implements Serializable {
   public String padding;
   public String textDirection;
   public String textOrientation;
   public String writingMode;

   public int getContentPadding() {
      return StringUtils.isBlank(this.padding) ? 0 : NumberUtils.parseInt(this.padding.replace("px", ""));
   }
}
