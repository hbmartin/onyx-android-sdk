package com.onyx.android.sdk.data.note;

import com.alibaba.fastjson2.annotation.JSONField;

public class MatrixValues {
   public float[] values;

   public MatrixValues() {
   }

   public MatrixValues(float[] values) {
      this.values = values;
   }

   @JSONField(serialize = false, deserialize = false)
   public boolean isEmpty() {
      float[] var1;
      return (var1 = this.values) == null || var1.length == 0;
   }
}
