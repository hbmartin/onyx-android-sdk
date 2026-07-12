package com.onyx.android.sdk.note;

import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import com.onyx.android.sdk.rx.RxScheduler;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.DeviceFeatureUtil;
import com.onyx.android.sdk.utils.ResManager;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class KNoteServiceBundle {
   private static final KNoteServiceBundle c = new KNoteServiceBundle();
   private NoteRemoteServiceConnection a;
   private RxScheduler b;

   public static KNoteServiceBundle getInstance() {
      return c;
   }

   private KNoteServiceBundle() {
   }

   private RxScheduler a() {
      if (this.b == null) {
         this.b = RxScheduler.newSingleThreadManager();
      }

      return this.b;
   }

   public NoteRemoteServiceConnection getServiceConnection() {
      if (this.a == null) {
         this.a = new NoteRemoteServiceConnection(ResManager.getAppContext());
      }

      return this.a;
   }

   public Scheduler getObserveOn() {
      return this.a().getObserveOn();
   }

   public void sendMessage(Bundle bundle) {
      if (!DeviceFeatureUtil.hasStylus(ResManager.getAppContext())) {
         Debug.i(this.getClass(), "disable " + Build.MODEL + " send message to note for has not stylus", new Object[0]);
      } else {
         Observable.just(this).observeOn(this.getObserveOn()).map(o -> this.sendMessageImpl(bundle)).subscribe();
      }
   }

   public KNoteServiceBundle sendMessageImpl(Bundle bundle) throws RemoteException {
      this.getServiceConnection().loadSyncService().sendMessageAction(bundle);
      return this;
   }
}
