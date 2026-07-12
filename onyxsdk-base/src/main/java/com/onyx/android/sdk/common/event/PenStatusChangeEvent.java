package com.onyx.android.sdk.common.event;

import com.onyx.android.sdk.data.PenStatusBean;

public class PenStatusChangeEvent {
   private final PenStatusBean a;

   public PenStatusChangeEvent(PenStatusBean statusBean) {
      this.a = statusBean;
   }

   public PenStatusBean getStatusBean() {
      return this.a;
   }
}
