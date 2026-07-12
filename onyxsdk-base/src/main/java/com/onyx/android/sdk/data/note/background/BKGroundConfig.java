package com.onyx.android.sdk.data.note.background;

public class BKGroundConfig {
   public boolean asDefault = true;
   public boolean applyAllPage = false;
   public boolean canvasAutoExpand = false;
   public int scaleType = 1;

   public int getScaleType() {
      return this.scaleType;
   }

   public boolean sameWith(BKGroundConfig config) {
      return config == null
         ? false
         : this.asDefault == config.asDefault
            && this.applyAllPage == config.applyAllPage
            && this.canvasAutoExpand == config.canvasAutoExpand
            && this.scaleType == config.scaleType;
   }

   @Override
   public String toString() {
      return "BKGroundConfig{asDefault="
         + this.asDefault
         + ", applyAllPage="
         + this.applyAllPage
         + ", canvasAutoExpand="
         + this.canvasAutoExpand
         + ", scaleType="
         + this.scaleType
         + '}';
   }
}
