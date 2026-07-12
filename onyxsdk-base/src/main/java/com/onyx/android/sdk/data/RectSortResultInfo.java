package com.onyx.android.sdk.data;

import android.graphics.RectF;
import java.util.List;

public class RectSortResultInfo {
   private List<RectF> a;
   private List<RectF> b;

   public List<RectF> getBoundingRectList() {
      return this.a;
   }

   public RectSortResultInfo setBoundingRectList(List<RectF> boundingRectList) {
      this.a = boundingRectList;
      return this;
   }

   public List<RectF> getSortResultList() {
      return this.b;
   }

   public RectSortResultInfo setSortResultList(List<RectF> sortResultList) {
      this.b = sortResultList;
      return this;
   }
}
