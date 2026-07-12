package com.onyx.android.sdk.data.note.line;

import android.graphics.Paint;
import com.onyx.android.sdk.utils.JSONUtils;
import com.onyx.android.sdk.utils.StringUtils;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class LineStyle implements Serializable, Cloneable {
   public static final int STYLE_TYPE_STRAIGHT_LINE = 0;
   public static final int STYLE_TYPE_DASH_LINE = 1;
   private float a;
   private int b = 0;
   private float[] c;

   public static void applyStyle(LineStyle lineStyle, Paint paint, float strokeWidth, float scale) {
      if (lineStyle != null) {
         if (lineStyle.getType() != 0) {
            DashLineStyle.applyStyle(lineStyle, paint, strokeWidth, scale);
         } else {
            StraightLineStyle.INSTANCE.applyStyle(paint, strokeWidth, scale);
         }
      }
   }

   public static LineStyle parse(String jsonString) {
      if (StringUtils.isNullOrEmpty(jsonString)) {
         return null;
      } else {
         LineStyle var1;
         if ((var1 = JSONUtils.parseObject(jsonString, LineStyle.class)) == null) {
            return null;
         } else {
            int var2;
            if ((var2 = var1.getType()) != 0) {
               return var2 != 1 ? var1 : JSONUtils.parseObject(jsonString, DashLineStyle.class);
            } else {
               return JSONUtils.parseObject(jsonString, StraightLineStyle.class);
            }
         }
      }
   }

   public void setPhase(float phase) {
      this.a = phase;
   }

   public float getPhase() {
      return this.a;
   }

   public int getType() {
      return this.b;
   }

   public void setType(int type) {
      this.b = type;
   }

   public void setDashLineIntervals(float[] dashLineIntervals) {
      this.c = dashLineIntervals;
   }

   public float[] getDashLineIntervals() {
      return this.c;
   }

   public boolean ensureIntervalsValid() {
      if (this.getDashLineIntervals() != null && this.getDashLineIntervals().length >= 2) {
         if (this.getDashLineIntervals().length % 2 != 0) {
            this.setDashLineIntervals(Arrays.copyOfRange(this.getDashLineIntervals(), 0, this.getDashLineIntervals().length - 1));
         }

         return true;
      } else {
         return false;
      }
   }

   public float[] getIntervalsWithScale(float strokeWidth, float scale) {
      float[] var4 = Arrays.copyOf(this.getDashLineIntervals(), this.getDashLineIntervals().length);

      for (int var3 = 0; var3 < var4.length; var3++) {
         var4[var3] += strokeWidth;
         var4[var3] *= scale;
      }

      return var4;
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else {
         return object == null ? false : StringUtils.safelyEquals(JSONUtils.toJson(this), JSONUtils.toJson(object));
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(JSONUtils.toJson(this));
   }

   public LineStyle clone() {
      return parse(JSONUtils.toJson(this));
   }

   public void applyStyle(Paint paint, float strokeWidth, float scale) {
   }
}
