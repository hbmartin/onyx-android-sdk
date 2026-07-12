package com.onyx.android.sdk.data.note;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.alibaba.fastjson2.annotation.JSONField;
import com.onyx.android.sdk.utils.StringUtils;
import java.io.Serializable;

public class LayerInfo implements Serializable, Cloneable {
   private boolean a = true;
   private int b;
   private boolean c = false;
   private String d;

   public boolean isShow() {
      return this.a;
   }

   public LayerInfo setShow(boolean show) {
      this.a = show;
      return this;
   }

   public int getId() {
      return this.b;
   }

   public LayerInfo setId(int id) {
      this.b = id;
      return this;
   }

   public boolean isLock() {
      return this.c;
   }

   public void setLock(boolean lock) {
      this.c = lock;
   }

   @NonNull
   public LayerInfo clone() throws CloneNotSupportedException {
      return (LayerInfo)super.clone();
   }

   @Override
   public boolean equals(@Nullable Object obj) {
      if (!(obj instanceof LayerInfo)) {
         return false;
      }

      obj = (LayerInfo)obj;
      return this.a == ((LayerInfo)obj).a
         && this.b == ((LayerInfo)obj).b
         && this.c == ((LayerInfo)obj).c
         && StringUtils.safelyEquals(this.d, ((LayerInfo)obj).d);
   }

   @JSONField(serialize = false)
   public void mergeLayerInfo(LayerInfo mergeLayerInfo) {
      this.setShow(mergeLayerInfo.a);
      this.setLock(mergeLayerInfo.c);
      this.setTitle(mergeLayerInfo.d);
   }

   public String getTitle() {
      return this.d;
   }

   public void setTitle(String title) {
      this.d = title;
   }
}
