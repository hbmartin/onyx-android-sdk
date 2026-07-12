package com.onyx.android.sdk.data;

public class RefValue<T> {
   private T a = (T)null;

   public RefValue() {
   }

   public RefValue(T v) {
      this.a = v;
   }

   public T getValue() {
      return this.a;
   }

   public void setValue(T v) {
      this.a = v;
   }
}
