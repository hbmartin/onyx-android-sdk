package com.onyx.android.sdk.data.note;

import androidx.annotation.NonNull;
import java.util.Objects;

public class PenAttrs implements Cloneable {
   private int a;

   public static PenAttrs copy(PenAttrs attrs) {
      try {
         return attrs.clone();
      } catch (CloneNotSupportedException var1) {
         var1.printStackTrace();
         return new PenAttrs();
      }
   }

   public PenAttrs setTexture(int texture) {
      this.a = texture;
      return this;
   }

   public int getTexture() {
      return this.a;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         PenAttrs var2 = (PenAttrs)o;
         return this.a == var2.a;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.a);
   }

   @NonNull
   protected PenAttrs clone() throws CloneNotSupportedException {
      return (PenAttrs)super.clone();
   }
}
