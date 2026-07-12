package com.onyx.android.sdk.market;

import android.os.Bundle;
import android.os.Message;
import com.onyx.android.sdk.rx.RxScheduler;
import com.onyx.android.sdk.utils.ResManager;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class AppMarketServiceBundle {
   private static final AppMarketServiceBundle c = new AppMarketServiceBundle();
   private AppMarketRemoteServiceConnection a;
   private RxScheduler b;

   public static AppMarketServiceBundle getInstance() {
      return c;
   }

   private AppMarketServiceBundle() {
   }

   private RxScheduler a() {
      if (this.b == null) {
         this.b = RxScheduler.newSingleThreadManager();
      }

      return this.b;
   }

   public AppMarketRemoteServiceConnection getServiceConnection() {
      if (this.a == null) {
         this.a = new AppMarketRemoteServiceConnection(ResManager.getAppContext());
      }

      return this.a;
   }

   public Scheduler getObserveOn() {
      return this.a().getObserveOn();
   }

   public void sendMessage(Bundle bundle) {
      Observable.just(this).observeOn(this.getObserveOn()).map(o -> this.sendMessageImpl(bundle)).subscribe();
   }

   public AppMarketServiceBundle sendMessageImpl(Bundle bundle) {
      Message var2;
      (var2 = Message.obtain()).setData(bundle);
      this.getServiceConnection().sendMessage(var2);
      return this;
   }
}
