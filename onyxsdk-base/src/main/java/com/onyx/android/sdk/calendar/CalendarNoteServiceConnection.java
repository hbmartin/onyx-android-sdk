package com.onyx.android.sdk.calendar;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.onyx.android.sdk.common.service.MessengerRemoteServiceConnection;

public class CalendarNoteServiceConnection extends MessengerRemoteServiceConnection {
   public static final String KMAIL_PACKAGE_NAME = "com.onyx.mail";
   public static final String CALENDAR_NOTE_SERVICE_PACKAGE_NAME = "com.onyx.mail.calendar.service.CalendarNoteService";

   public CalendarNoteServiceConnection(Context context) {
      super(context, b());
   }

   private static Intent b() {
      return new Intent().setComponent(new ComponentName("com.onyx.mail", "com.onyx.mail.calendar.service.CalendarNoteService"));
   }
}
