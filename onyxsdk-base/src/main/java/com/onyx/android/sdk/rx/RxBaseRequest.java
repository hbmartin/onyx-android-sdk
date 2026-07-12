package com.onyx.android.sdk.rx;

import android.content.Context;
import com.onyx.android.sdk.utils.ResManager;

public abstract class RxBaseRequest<T> {
   private static Context b;
   private volatile boolean a = false;

   public static void init(Context context) {
      b = context.getApplicationContext();
   }

   public static Context getAppContext() {
      Context var0 = b;
      if (b == null) {
         var0 = ResManager.getAppContext();
      }

      return var0;
   }

   public abstract T execute() throws Exception;

   public void setAbort() {
      this.a = true;
   }

   public boolean isAbort() {
      return this.a;
   }
}
