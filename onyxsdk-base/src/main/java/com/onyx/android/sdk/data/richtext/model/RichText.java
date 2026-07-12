package com.onyx.android.sdk.data.richtext.model;

import java.io.Serializable;

public class RichText extends BaseRichDoc implements Serializable {
   public static final int VERSION = 1;
   public static final int VERSION_2 = 2;
   public RichTextContent content;
   public RichTextContentStyle contentStyle;
   public int currentPos;
}
