package com.onyx.android.sdk.common.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import com.onyx.android.sdk.utils.Debug;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class OnyxRemoteServiceConnection<S extends IInterface> implements ServiceConnection {
   private volatile boolean a = false;
   private volatile IBinder b;
   private volatile S c;
   protected final Context appContext;
   protected final AtomicBoolean abort = new AtomicBoolean(false);
   protected final Intent serviceIntent;
   private long d = 6000L;
   private IServiceConnectListener e;

   public OnyxRemoteServiceConnection(Context context, Intent serviceIntent) {
      this.appContext = context.getApplicationContext();
      this.serviceIntent = serviceIntent;
   }

   private void a(boolean connected) {
      IServiceConnectListener var2;
      if ((var2 = this.e) != null) {
         var2.onConnectionChanged(connected);
      }
   }

   private void a() throws InterruptedException, TimeoutException {
      byte var1 = 0;

      while (!this.a && !this.isAbort()) {
         Thread.sleep(100L);
         if ((var1 += 100) >= this.d) {
            throw new TimeoutException("connect remote service timeout: " + this.serviceIntent);
         }
      }
   }

   public OnyxRemoteServiceConnection<S> setConnectTimeoutMs(long connectTimeoutMs) {
      this.d = connectTimeoutMs;
      return this;
   }

   public OnyxRemoteServiceConnection<S> setServiceConnectListener(IServiceConnectListener connectListener) {
      this.e = connectListener;
      return this;
   }

   public boolean isAbort() {
      return this.abort.get();
   }

   public void setAbort() {
      this.abort.set(true);
   }

   public OnyxRemoteServiceConnection<S> bindService() throws InterruptedException, TimeoutException {
      this.appContext.bindService(this.serviceIntent, this, 5);
      this.a();
      return this;
   }

   public void unbindService() {
      if (this.isConnected()) {
         this.appContext.unbindService(this);
         this.a = false;
         this.c = null;
         this.b = null;
      }
   }

   public void stopService() {
      this.appContext.stopService(this.serviceIntent);
   }

   public S loadRemoteService() {
      if (!this.isConnected()) {
         try {
            this.bindService();
         } catch (InterruptedException var1) {
            Debug.e(var1);
         } catch (TimeoutException var2) {
            Debug.e(this.getClass(), var2);
         }
      }

      if (this.c == null && this.getRemoteService() != null) {
         this.c = this.asInterface(this.getRemoteService());
      }

      return this.c;
   }

   public Optional<S> getService() {
      return Optional.ofNullable(this.c);
   }

   protected abstract S asInterface(IBinder var1);

   public void onServiceDisconnected(ComponentName name) {
      Debug.e(this.getClass(), "onServiceDisconnected:" + name.getClassName(), new Object[0]);
      this.unbindService();
      this.a(false);
   }

   public void onServiceConnected(ComponentName name, IBinder service) {
      Debug.d(this.getClass(), "onServiceConnected:" + name.getClassName(), new Object[0]);
      this.a = true;
      this.b = service;
      this.a(true);
   }

   public boolean isConnected() {
      return this.a;
   }

   public IBinder getRemoteService() {
      return this.b;
   }
}
