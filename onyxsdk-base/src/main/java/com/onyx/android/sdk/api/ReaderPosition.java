package com.onyx.android.sdk.api;

import com.onyx.android.sdk.data.ReaderPositionImpl;

public interface ReaderPosition {
   ReaderPosition dummy = new ReaderPositionImpl() {
      @Override
      public ReaderPosition fromPageIndex(int pageIndex) {
         throw new UnsupportedOperationException();
      }

      @Override
      public ReaderPosition fromInteger(int value) {
         throw new UnsupportedOperationException();
      }

      @Override
      public ReaderPosition fromString(String string) {
         throw new UnsupportedOperationException();
      }

      @Override
      public int compare(ReaderPosition otherPosition) {
         return -1;
      }

      @Override
      public ReaderPosition copy() {
         return this;
      }
   };

   int getPageIndex();

   ReaderPosition fromPageIndex(int var1);

   int asInteger();

   ReaderPosition fromInteger(int var1);

   String asString();

   ReaderPosition fromString(String var1);

   int compare(ReaderPosition var1);

   boolean compatEquals(ReaderPosition var1);

   int positionType();

   ReaderPosition copy();

   default void setInvalid() {
      this.fromPageIndex(-1);
      this.fromInteger(-1);
      this.fromString("");
   }
}
