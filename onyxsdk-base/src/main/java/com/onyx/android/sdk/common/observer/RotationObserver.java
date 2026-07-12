package com.onyx.android.sdk.common.observer;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings.System;
import com.onyx.android.sdk.rx.RxCallback;

public class RotationObserver extends ContentObserver {
   private ContentResolver a;
   private RxCallback<Boolean> b;

   public RotationObserver(Handler handler, ContentResolver resolver) {
      super(handler);
      this.a = resolver;
   }

   public RotationObserver setCallback(RxCallback<Boolean> callback) {
      this.b = callback;
      return this;
   }

   public void onChange(boolean selfChange) {
      super.onChange(selfChange);
      RxCallback var2;
      if ((var2 = this.b) != null) {
         var2.onNext(selfChange);
      }
   }

   public void start() {
      this.a.registerContentObserver(System.getUriFor("accelerometer_rotation"), false, this);
   }

   public void stop() {
      this.a.unregisterContentObserver(this);
      this.b = null;
   }
}
