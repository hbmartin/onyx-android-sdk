package com.onyx.android.sdk.note;

import android.os.RemoteException;
import com.onyx.android.sdk.utils.Debug;

public class NoteContentIntentCallbackHolder extends NoteContentIntentCallback.Stub {
   private NoteContentIntentCallback c;

   public NoteContentIntentCallbackHolder(NoteContentIntentCallback callback) {
      this.c = callback;
   }

   @Override
   public void read(NoteContentIntentArgs args) throws RemoteException {
      NoteContentIntentCallback callback = this.c;
      if (callback == null) {
         Debug.e(this.getClass(), "reference is null", new Object[0]);
         return;
      }
      try {
         callback.read(args);
      } catch (Exception exception) {
         Debug.e(exception);
      } finally {
         this.c = null;
      }
   }
}
