package com.onyx.android.sdk.calendar;

import android.os.Bundle;
import android.os.Message;
import com.onyx.android.sdk.rx.RxScheduler;
import com.onyx.android.sdk.utils.ResManager;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class KCalendarServiceBundle {
   private static final KCalendarServiceBundle c = new KCalendarServiceBundle();
   private CalendarNoteServiceConnection a;
   private RxScheduler b;

   public static KCalendarServiceBundle getInstance() {
      return c;
   }

   private KCalendarServiceBundle() {
   }

   private RxScheduler a() {
      if (this.b == null) {
         this.b = RxScheduler.newSingleThreadManager();
      }

      return this.b;
   }

   private KCalendarServiceBundle a(Bundle bundle) {
      Message var2;
      (var2 = Message.obtain()).setData(bundle);
      this.getServiceConnection().sendMessage(var2);
      return this;
   }

   public CalendarNoteServiceConnection getServiceConnection() {
      if (this.a == null) {
         this.a = new CalendarNoteServiceConnection(ResManager.getAppContext());
      }

      return this.a;
   }

   public Scheduler getObserveOn() {
      return this.a().getObserveOn();
   }

   public void sendMessage(Bundle bundle) {
      Observable.just(this).observeOn(this.getObserveOn()).map(o -> this.a(bundle)).subscribe();
   }
}
