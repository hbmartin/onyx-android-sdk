package com.onyx.android.sdk.common.event;

import com.onyx.android.sdk.utils.Debug;

public class FunctionBarShowEvent {
   public boolean show;

   public FunctionBarShowEvent(boolean show) {
      this.show = show;
      Debug.d(new Throwable("for debug, function bar show: " + show));
   }
}
