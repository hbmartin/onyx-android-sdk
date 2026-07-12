package com.onyx.android.sdk.data.note.line;

public class ArrowStyle {
   public static final int ARROW_DIRECTION_NONE = 0;
   public static final int ARROW_DIRECTION_START = 1;
   public static final int ARROW_DIRECTION_END = 2;
   public static final int ARROW_DIRECTION_BOTH = 3;
   public static final ArrowStyle NONE = new ArrowStyle();
   public static final ArrowStyle DEFAULT = new ArrowStyle(2);
   public int direction = 0;
   public float arrowLenFactor = 6.0F;
   public float arrowHeight = 8.0F;
   public float halfBottomLine = 5.5F;

   public ArrowStyle() {
   }

   public ArrowStyle(int direction) {
      this.direction = direction;
   }

   public void setDirection(int direction) {
      this.direction = direction;
   }
}
