package com.onyx.android.sdk.reader;

import android.content.Intent;
import android.os.Message;
import com.onyx.android.sdk.rx.RxScheduler;
import com.onyx.android.sdk.utils.ResManager;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class ReaderServiceBundle {
   private static final ReaderServiceBundle c = new ReaderServiceBundle();
   private ReaderServiceConnection a;
   private RxScheduler b;

   public static ReaderServiceBundle getInstance() {
      return c;
   }

   private ReaderServiceBundle() {
   }

   private RxScheduler a() {
      if (this.b == null) {
         this.b = RxScheduler.newSingleThreadManager();
      }

      return this.b;
   }

   private ReaderServiceBundle a(Intent intent) {
      Message var2;
      (var2 = Message.obtain()).obj = intent;
      this.getServiceConnection().sendMessage(var2);
      return this;
   }

   public ReaderServiceConnection getServiceConnection() {
      if (this.a == null) {
         this.a = new ReaderServiceConnection(ResManager.getAppContext());
      }

      return this.a;
   }

   public Scheduler getObserveOn() {
      return this.a().getObserveOn();
   }

   public void sendMessage(Intent intent) {
      Observable.just(this).observeOn(this.getObserveOn()).map(o -> this.a(intent)).subscribe();
   }
}
