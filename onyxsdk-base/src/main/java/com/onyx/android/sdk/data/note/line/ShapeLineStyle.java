package com.onyx.android.sdk.data.note.line;

import com.onyx.android.sdk.utils.JSONUtils;
import com.onyx.android.sdk.utils.StringUtils;
import java.io.Serializable;

public class ShapeLineStyle implements Serializable, Cloneable {
   private LineStyle a = StraightLineStyle.INSTANCE;
   private ArrowStyle b;

   public static ShapeLineStyle parse(String jsonString) {
      if (StringUtils.isNullOrEmpty(jsonString)) {
         return null;
      }

      ShapeLineStyle var1;
      if ((var1 = JSONUtils.parseObject(jsonString, ShapeLineStyle.class)) == null) {
         return null;
      }

      a(var1, jsonString);
      return var1;
   }

   private static void a(ShapeLineStyle shapeLineStyle, String jsonString) {
      LineStyle var2 = null;
      String var3;
      if (jsonString.contains(var3 = org.apache.commons.lang3.StringUtils.uncapitalize(LineStyle.class.getSimpleName()))) {
         var2 = LineStyle.parse(JSONUtils.parseObject(jsonString).getString(var3));
      }

      if (var2 == null) {
         var2 = LineStyle.parse(jsonString);
      }

      if (var2 != null) {
         shapeLineStyle.setLineStyle(var2);
      }
   }

   public LineStyle getLineStyle() {
      return this.a;
   }

   public ShapeLineStyle setLineStyle(LineStyle lineStyle) {
      this.a = lineStyle;
      return this;
   }

   public ShapeLineStyle setArrowStyle(ArrowStyle arrowStyle) {
      this.b = arrowStyle;
      return this;
   }

   public ArrowStyle getArrowStyle() {
      return this.b;
   }

   public ShapeLineStyle clone() {
      return parse(JSONUtils.toJson(this));
   }
}
