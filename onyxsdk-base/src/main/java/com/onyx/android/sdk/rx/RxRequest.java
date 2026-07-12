package com.onyx.android.sdk.rx;

import android.content.Context;
import com.onyx.android.sdk.utils.ResManager;

public abstract class RxRequest {
   public static final String DEFAULT_IDENTIFIER = "default";
   private volatile boolean a = false;
   private Context b;

   public abstract void execute() throws Exception;

   public void setAbort() {
      this.a = true;
   }

   public boolean isAbort() {
      return this.a;
   }

   public Context getContext() {
      Context var1;
      if ((var1 = this.b) == null) {
         var1 = ResManager.getAppContext();
      }

      return var1;
   }

   public RxRequest setContext(Context context) {
      this.b = context;
      return this;
   }

   public String getIdentifier() {
      return "default";
   }
}
