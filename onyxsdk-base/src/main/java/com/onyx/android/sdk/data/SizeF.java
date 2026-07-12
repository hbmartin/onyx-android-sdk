package com.onyx.android.sdk.data;

import java.io.Serializable;

public class SizeF implements Serializable {
   public float width;
   public float height;

   public SizeF() {
      this.width = 0.0F;
      this.height = 0.0F;
   }

   public SizeF(float w, float h) {
      this.width = w;
      this.height = h;
   }
}
