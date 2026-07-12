package com.onyx.android.sdk.common.request;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class SingleThreadExecutor {
   private ExecutorService a;
   private int b;

   public SingleThreadExecutor(int threadPriority) {
      this.b = threadPriority;
   }

   public synchronized ExecutorService get() {
      if (this.a == null) {
         this.a = Executors.newSingleThreadExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
               Thread var10000 = new Thread(r);
               var10000.setPriority(SingleThreadExecutor.this.b);
               return var10000;
            }
         });
      }

      return this.a;
   }

   public synchronized void shutdown() {
      ExecutorService var1;
      if ((var1 = this.a) != null) {
         var1.shutdown();
      }
   }
}
