package com.onyx.android.sdk.firmware.request;

import android.content.Context;
import com.alibaba.fastjson2.JSON;
import com.onyx.android.sdk.api.device.OTAManager;
import com.onyx.android.sdk.data.ClusterHost;
import com.onyx.android.sdk.firmware.data.Firmware;
import com.onyx.android.sdk.firmware.utils.ServiceFactory;
import com.onyx.android.sdk.rx.RxBaseRequest;
import java.util.List;
import retrofit2.Response;

public class FirmwareUpdateRequest extends RxBaseRequest {
   private static final String d = ClusterHost.DEFAULT_CHINESE_DATA_HOST + "/api/";
   private Firmware c;

   public FirmwareUpdateRequest(Context context) {
      RxBaseRequest.init(context.getApplicationContext());
   }

   public Firmware execute() throws Exception {
      Response var1;
      if ((var1 = ServiceFactory.getOTAService(d).firmwareUpdate(JSON.toJSONString(OTAManager.getCurrentFirmware(RxBaseRequest.getAppContext()))).execute())
         .isSuccessful()) {
         this.c = (Firmware)var1.body();
      }

      return this.c;
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
