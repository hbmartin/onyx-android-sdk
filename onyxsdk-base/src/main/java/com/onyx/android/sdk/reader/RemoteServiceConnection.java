package com.onyx.android.sdk.reader;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.onyx.android.sdk.rx.RxRequest;

public class RemoteServiceConnection implements ServiceConnection {
   public static final int WAIT_SLEEP_TIME = 100;
   private volatile boolean a = false;
   private volatile IBinder b;

   public void onServiceDisconnected(ComponentName name) {
      this.a = true;
   }

   public void onServiceConnected(ComponentName name, IBinder service) {
      this.a = true;
      this.b = service;
   }

   public boolean isConnected() {
      return this.a;
   }

   public IBinder getRemoteService() {
      return this.b;
   }

   public void waitUntilConnected(RxRequest request) throws InterruptedException {
      while (!this.a && !request.isAbort()) {
         Thread.sleep(100L);
      }
   }
}
