package com.onyx.android.sdk.data.note.line;

import android.graphics.Paint;

public class StraightLineStyle extends LineStyle {
   public static final StraightLineStyle INSTANCE = new StraightLineStyle();

   @Override
   public void applyStyle(Paint paint, float strokeWidth, float scale) {
      paint.setPathEffect(null);
   }
}
