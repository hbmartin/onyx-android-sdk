package com.onyx.android.sdk.data;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import com.onyx.android.sdk.api.ReaderImage;
import com.onyx.android.sdk.api.ReaderPosition;
import com.onyx.android.sdk.utils.BitmapUtils;
import com.onyx.android.sdk.utils.PageInfoUtils;

public class ReaderImageImpl implements ReaderImage {
   private RectF a;
   private ReaderPosition b = ReaderPosition.dummy;
   private Bitmap c;
   private float d;

   public static ReaderImageImpl create(int width, int height, Config config) {
      return new ReaderImageImpl(width, height, config);
   }

   public ReaderImageImpl() {
   }

   public ReaderImageImpl(int width, int height, Config config) {
      this.c = Bitmap.createBitmap(width, height, config);
   }

   public void clear() {
      Bitmap var1;
      if ((var1 = this.c) != null) {
         var1.eraseColor(-1);
      }
   }

   public void recycleBitmap() {
      Bitmap var1 = this.c;
      if (this.c != null) {
         var1.recycle();
      }

      this.c = null;
   }

   @Override
   public RectF getRectangle() {
      return this.a;
   }

   @Override
   public Bitmap getBitmap() {
      return this.c;
   }

   @Override
   public int getPositionInt() {
      return this.b.asInteger();
   }

   @Override
   public ReaderPosition getPosition() {
      return this.b;
   }

   public void setGammaCorrection(float correction) {
      this.d = correction;
   }

   public float gammaCorrection() {
      return this.d;
   }

   public void update(int width, int height, Config config) {
      Bitmap var4 = this.c;
      if (this.c == null || var4.getWidth() != width || this.c.getHeight() != height) {
         this.recycleBitmap();
         this.c = Bitmap.createBitmap(width, height, config);
      }
   }

   public boolean copyFrom(Bitmap src) {
      this.recycleBitmap();
      Bitmap var2;
      this.c = var2 = src.copy(src.getConfig(), true);
      return BitmapUtils.isValid(var2);
   }

   public void attach(Bitmap src) {
      this.recycleBitmap();
      this.c = src;
   }

   public ReaderImageImpl setRectangle(RectF rectangle) {
      this.a = rectangle;
      return this;
   }

   public ReaderImageImpl setPositionInt(int positionInt) {
      if (PageInfoUtils.isInvalidPosition(this.b)) {
         this.b = new ReaderPositionImpl().fromInteger(positionInt);
      } else {
         this.b.fromInteger(positionInt);
      }

      return this;
   }

   public ReaderImageImpl setPosition(ReaderPosition position) {
      this.b = position;
      return this;
   }
}
