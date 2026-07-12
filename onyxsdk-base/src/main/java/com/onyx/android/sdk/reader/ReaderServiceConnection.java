package com.onyx.android.sdk.reader;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.onyx.android.sdk.common.service.MessengerRemoteServiceConnection;

public class ReaderServiceConnection extends MessengerRemoteServiceConnection {
   public ReaderServiceConnection(Context context) {
      super(context, b());
   }

   private static Intent b() {
      return new Intent().setComponent(new ComponentName("com.onyx.kreader", "com.onyx.android.sdk.readerview.service.ReaderService"));
   }
}
