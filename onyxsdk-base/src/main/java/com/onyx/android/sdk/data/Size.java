package com.onyx.android.sdk.data;

import android.graphics.RectF;
import androidx.annotation.NonNull;
import java.util.Objects;

public class Size {
   public int width;
   public int height;

   public Size() {
      this.width = 0;
      this.height = 0;
   }

   public Size(int w, int h) {
      this.width = w;
      this.height = h;
   }

   public Size(Size s) {
      this.width = s.width;
      this.height = s.height;
   }

   public boolean equals(int w, int h) {
      return this.width == w && this.height == h;
   }

   public Size setWidth(int width) {
      this.width = width;
      return this;
   }

   public Size setHeight(int height) {
      this.height = height;
      return this;
   }

   public boolean isEmpty() {
      return this.width <= 0 || this.height <= 0;
   }

   public RectF toRectF() {
      float var2 = this.width;
      float var1 = this.height;
      return new RectF(0.0F, 0.0F, var2, var1);
   }

   public Size copy() {
      return new Size(this);
   }

   public String key() {
      return "w=" + this.width + "-h=" + this.height;
   }

   public Boolean isVertical() {
      return this.height > this.width;
   }

   public RectF ratioPageSize(Size maxPageSize) {
      if (Math.max(maxPageSize.width, maxPageSize.height) > Math.max(this.width, this.height)) {
         float var4 = this.width;
         float var8 = this.height;
         return new RectF(0.0F, 0.0F, var4, var8);
      }

      int var2;
      int var5;
      if (this.isVertical()) {
         var5 = maxPageSize.width;
         var2 = maxPageSize.height;
      } else {
         var5 = maxPageSize.height;
         var2 = maxPageSize.width;
      }

      float var6 = Math.max(this.width * 1.0F / var5, this.height * 1.0F / var2);
      float var3 = this.width / var6;
      float var7 = this.height / var6;
      return new RectF(0.0F, 0.0F, var3, var7);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         o = (Size)o;
         return this.width == ((Size)o).width && this.height == ((Size)o).height;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.width, this.height);
   }

   @NonNull
   @Override
   public String toString() {
      return "width:" + this.width + ",height:" + this.height;
   }
}
