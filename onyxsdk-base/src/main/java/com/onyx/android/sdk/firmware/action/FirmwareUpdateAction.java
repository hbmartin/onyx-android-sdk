package com.onyx.android.sdk.firmware.action;

import android.content.Context;
import com.onyx.android.sdk.firmware.request.FirmwareUpdateRequest;
import com.onyx.android.sdk.rx.RxBaseAction;
import com.onyx.android.sdk.rx.RxScheduler;
import io.reactivex.Observable;

public class FirmwareUpdateAction extends RxBaseAction<FirmwareUpdateRequest> {
   private Context j;

   public FirmwareUpdateAction(Context context) {
      this.j = context;
   }

   private FirmwareUpdateRequest d() throws Exception {
      FirmwareUpdateRequest var10000 = new FirmwareUpdateRequest(this.j);
      var10000.execute();
      return var10000;
   }

   @Override
   protected Observable<FirmwareUpdateRequest> create() {
      return Observable.just(this).observeOn(RxScheduler.newSingleThreadManager().getObserveOn()).map(firmwareUpdateAction -> this.d());
   }
}
