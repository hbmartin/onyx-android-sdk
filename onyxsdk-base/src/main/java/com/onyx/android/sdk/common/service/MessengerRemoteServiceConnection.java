package com.onyx.android.sdk.common.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.onyx.android.sdk.utils.Debug;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class MessengerRemoteServiceConnection implements ServiceConnection {
   private static final int d = 3;
   private volatile boolean a = false;
   private volatile IBinder b;
   protected final Context appContext;
   protected final AtomicBoolean abort = new AtomicBoolean(false);
   protected final Intent serviceIntent;
   protected Messenger messenger;
   private long c = 6000L;

   public MessengerRemoteServiceConnection(Context context, Intent serviceIntent) {
      this.appContext = context.getApplicationContext();
      this.serviceIntent = serviceIntent;
   }

   private boolean a(Message message) throws RemoteException {
      Messenger var2;
      if ((var2 = this.loadMessenger()) == null) {
         Debug.e(this.getClass(), "resend messenger is null, bindService failed!", new Object[0]);
         return false;
      } else {
         var2.send(message);
         return true;
      }
   }

   private void a() throws InterruptedException, TimeoutException {
      byte var1 = 0;

      while (!this.a && !this.isAbort()) {
         Thread.sleep(100L);
         if ((var1 += 100) >= this.c) {
            throw new TimeoutException("connect remote service timeout: " + this.serviceIntent);
         }
      }
   }

   public MessengerRemoteServiceConnection setConnectTimeoutMs(long connectTimeoutMs) {
      this.c = connectTimeoutMs;
      return this;
   }

   public boolean isAbort() {
      return this.abort.get();
   }

   public void setAbort() {
      this.abort.set(true);
   }

   public MessengerRemoteServiceConnection bindService() throws InterruptedException, TimeoutException {
      this.appContext.bindService(this.serviceIntent, this, 5);
      this.a();
      return this;
   }

   public void unbindService() {
      if (this.isConnected()) {
         try {
            this.appContext.unbindService(this);
         } catch (IllegalArgumentException var2) {
            Debug.w(this.getClass(), var2);
         }

         this.a = false;
         this.messenger = null;
      }
   }

   public void stopService() {
      this.appContext.stopService(this.serviceIntent);
   }

   public Messenger loadMessenger() {
      if (!this.isConnected()) {
         try {
            this.bindService();
         } catch (InterruptedException var1) {
            Debug.e(var1);
         } catch (TimeoutException var2) {
            Debug.e(this.getClass(), var2);
         }
      }

      if (this.messenger == null && this.getRemoteService() != null) {
         this.messenger = new Messenger(this.getRemoteService());
      }

      return this.messenger;
   }

   public void sendMessage(Message message) {
      int var2 = 0;
      boolean var3 = false;

      while (var2++ < 3 && !var3) {
         boolean var6;
         try {
            var6 = this.a(message);
         } catch (RemoteException var5) {
            this.unbindService();
            Debug.e(this.getClass(), "send msg count: " + var2, var5);
            continue;
         }

         var3 = var6;
      }
   }

   public void onServiceDisconnected(ComponentName name) {
      Debug.e(this.getClass(), "onServiceDisconnected:" + name.getClassName(), new Object[0]);
      this.unbindService();
   }

   public void onServiceConnected(ComponentName name, IBinder service) {
      Debug.d(this.getClass(), "onServiceConnected:" + name.getClassName(), new Object[0]);
      this.a = true;
      this.b = service;
   }

   public boolean isConnected() {
      return this.a;
   }

   public IBinder getRemoteService() {
      return this.b;
   }
}
