package com.onyx.android.sdk.data.span;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;
import androidx.annotation.NonNull;

public class FixVerticalImageSpan extends ImageSpan {
   private boolean a;

   public FixVerticalImageSpan(Drawable drawable) {
      super(drawable);
   }

   public FixVerticalImageSpan setFixVertical(boolean fixVertical) {
      this.a = fixVertical;
      return this;
   }

   public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
      Drawable var10 = this.getDrawable();
      if (this.a && var10 != null) {
         canvas.save();
         canvas.translate(x, 0.0F);
         var10.draw(canvas);
         canvas.restore();
      } else {
         super.draw(canvas, text, start, end, x, top, y, bottom, paint);
      }
   }
}
