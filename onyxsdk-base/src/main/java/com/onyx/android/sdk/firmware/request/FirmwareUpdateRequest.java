package com.onyx.android.sdk.firmware.request;

import android.content.Context;
import com.onyx.android.sdk.firmware.data.Firmware;
import com.onyx.android.sdk.rx.RxBaseRequest;
import java.util.List;

public class FirmwareUpdateRequest extends RxBaseRequest {
   private Firmware c;

   public FirmwareUpdateRequest(Context context) {
      RxBaseRequest.init(context.getApplicationContext());
   }

   public Firmware execute() throws Exception {
      throw new UnsupportedOperationException("Firmware update networking has been removed");
   }

   public final Firmware getResultFirmware() {
      return this.c;
   }

   public boolean isResultFirmwareValid() {
      Firmware var1;
      List var2;
      return (var1 = this.c) != null && (var2 = var1.downloadUrlList) != null && var2.size() > 0;
   }
}
