package com.onyx.android.sdk.rx.rxbroadcast;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.onyx.android.sdk.utils.ResManager;
import io.reactivex.Observer;

public class RxConditionBroadcastObservable extends RxBroadcastChangeObservable {
   private RxConditionBroadcastObservable.CheckConditionListener c;

   public RxConditionBroadcastObservable(String action, RxConditionBroadcastObservable.CheckConditionListener conditionChecker) {
      super(action);
      this.c = conditionChecker;
   }

   @NonNull
   @Override
   protected RxBroadcastChangeObservable.a createListener(Observer<? super Intent> observer) {
      Context var2 = ResManager.getAppContext();
      return new RxBroadcastChangeObservable.a(observer, var2) {
         @Override
         public void onReceive(Context context, Intent intent) {
            if (RxConditionBroadcastObservable.this.c.check(intent)) {
               super.onReceive(context, intent);
            }
         }
      };
   }

   public interface CheckConditionListener {
      boolean check(Intent var1);
   }
}
