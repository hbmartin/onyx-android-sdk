package com.onyx.android.sdk.note;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import com.onyx.android.sdk.common.service.OnyxRemoteServiceConnection;

public class NoteRemoteServiceConnection extends OnyxRemoteServiceConnection<INoteContentService> {
   public static Intent getSyncServiceIntent() {
      return new Intent().setComponent(new ComponentName("com.onyx.android.note", "com.onyx.android.note.note.NoteService"));
   }

   public NoteRemoteServiceConnection(Context context) {
      super(context, getSyncServiceIntent());
   }

   protected INoteContentService asInterface(IBinder serviceBinder) {
      return INoteContentService.Stub.asInterface(serviceBinder);
   }

   public INoteContentService loadSyncService() {
      return (INoteContentService)super.loadRemoteService();
   }
}
