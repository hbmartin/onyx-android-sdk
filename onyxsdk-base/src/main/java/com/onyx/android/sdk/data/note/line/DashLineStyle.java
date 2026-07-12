package com.onyx.android.sdk.data.note.line;

import android.graphics.DashPathEffect;
import android.graphics.Paint;

public class DashLineStyle extends LineStyle {
   public static final DashLineStyle DASH_LINE_1 = create(new float[]{8.0F, 5.0F});
   public static final DashLineStyle DASH_LINE_2 = create(new float[]{20.0F, 5.0F});
   public static final DashLineStyle DASH_LINE_3 = create(new float[]{40.0F, 15.0F});
   public static final DashLineStyle DASH_LINE_4 = create(new float[]{20.0F, 10.0F, 5.0F, 10.0F});
   public static final DashLineStyle DASH_LINE_5 = create(new float[]{20.0F, 10.0F, 5.0F, 10.0F, 5.0F, 10.0F});
   public static final DashLineStyle DASH_LINE_6 = create(new float[]{40.0F, 10.0F, 10.0F, 10.0F});

   public static DashLineStyle create(float[] dashLineIntervals) {
      return create(dashLineIntervals, 0.0F);
   }

   public static DashLineStyle create(float[] dashLineIntervals, float phase) {
      DashLineStyle var10000 = new DashLineStyle();
      var10000.setDashLineIntervals(dashLineIntervals);
      var10000.setPhase(phase);
      var10000.setType(1);
      return var10000;
   }

   public static void applyStyle(LineStyle lineStyle, Paint paint, float strokeWidth, float scale) {
      if (lineStyle.ensureIntervalsValid()) {
         paint.setPathEffect(new DashPathEffect(lineStyle.getIntervalsWithScale(strokeWidth, scale), lineStyle.getPhase() * scale));
      }
   }

   @Override
   public void applyStyle(Paint paint, float strokeWidth, float scale) {
      applyStyle(this, paint, strokeWidth, scale);
   }
}
