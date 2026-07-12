package com.onyx.android.sdk.data;

public class WindowParameters {
   public int width = -1;
   public int height = -1;
   public int gravity = 80;

   public WindowParameters() {
   }

   public WindowParameters(int w, int h, int g) {
      this.width = w;
      this.height = h;
      this.gravity = g;
   }

   public void update(int w, int h, int g) {
      this.width = w;
      this.height = h;
      this.gravity = g;
   }
}
