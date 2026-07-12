package com.onyx.android.sdk.data;

import android.content.Context;
import com.onyx.android.sdk.utils.DateTimeUtil;
import com.onyx.android.sdk.utils.PowerUtil;
import java.io.Serializable;

public class BatteryInfo implements Serializable {
   public int batteryLevel = 100;
   public String batteryPercentString = "100%";
   public String chargeLabel = "100%";
   public String statusLabel;
   public boolean discharging = true;
   public long remainingTimeUs = 0L;
   public long averageTimeToDischarge = -1L;

   public String getCustomStatusLabel(Context context, int dischargeStrResId, int chargeStrResId) {
      long var4;
      if (this.discharging) {
         var4 = this.remainingTimeUs;
      } else {
         var4 = this.averageTimeToDischarge;
      }

      Object[] var6;
      (var6 = new Object[1])[0] = DateTimeUtil.formatDuration(PowerUtil.convertUsToMs(Math.min(0L, var4)));
      String var7 = context.getString(chargeStrResId, var6);
      if (var4 < 0L) {
         var7 = this.statusLabel;
      }

      return var7;
   }
}
