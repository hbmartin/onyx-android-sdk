package com.onyx.android.sdk.data.note;

import java.io.Serializable;

public class PenArgs implements Serializable {
   private String a;
   private int b;
   private float c;
   private int d;
   private PenAttrs e;
   private Float f;
   private Float g;

   public String getId() {
      return this.a;
   }

   public void setId(String id) {
      this.a = id;
   }

   public int getType() {
      return this.b;
   }

   public void setType(int type) {
      this.b = type;
   }

   public float getWidth() {
      return this.c;
   }

   public void setWidth(float width) {
      this.c = width;
   }

   public int getColor() {
      return this.d;
   }

   public void setColor(int color) {
      this.d = color;
   }

   public void setAttrs(PenAttrs attrs) {
      this.e = attrs;
   }

   public PenAttrs getAttrs() {
      return this.e;
   }

   public Float getPressureSensitivityV2() {
      return this.f;
   }

   public void setPressureSensitivityV2(Float pressureSensitivityV2) {
      this.f = pressureSensitivityV2;
   }

   public Float getSmoothLevel() {
      return this.g;
   }

   public void setSmoothLevel(Float smoothLevel) {
      this.g = smoothLevel;
   }
}
