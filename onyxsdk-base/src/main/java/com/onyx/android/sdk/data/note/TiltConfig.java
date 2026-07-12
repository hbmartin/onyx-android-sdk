package com.onyx.android.sdk.data.note;

public class TiltConfig {
   private boolean a = false;
   private float b = 3.0F;

   public boolean isTiltEnabled() {
      return this.a;
   }

   public TiltConfig setTiltEnabled(boolean tiltEnabled) {
      this.a = tiltEnabled;
      return this;
   }

   public float getTiltScale() {
      return this.b;
   }

   public TiltConfig setTiltScale(float tiltScale) {
      this.b = tiltScale;
      return this;
   }
}
