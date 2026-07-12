package com.onyx.android.sdk.market;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.onyx.android.sdk.common.service.MessengerRemoteServiceConnection;

public class AppMarketRemoteServiceConnection extends MessengerRemoteServiceConnection {
   public AppMarketRemoteServiceConnection(Context context) {
      super(context, b());
   }

   private static Intent b() {
      return new Intent().setComponent(new ComponentName("com.onyx.appmarket", "com.onyx.appmarket.service.AppCheckUpdateService"));
   }
}
