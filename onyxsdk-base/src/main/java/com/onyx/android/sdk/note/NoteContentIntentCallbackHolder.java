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
      NoteContentIntentCallback var2 = this.c;
      if (this.c == null) {
         Debug.e(this.getClass(), "reference is null", new Object[0]);
      } else {
         boolean var5 = false;

         NoteContentIntentCallbackHolder var10000;
         try {
            var5 = true;
            var10000 = this;
            var2.read(args);
            var5 = false;
         } catch (Exception var6) {
            var10000 = this;
            Debug.e(var6);
            var5 = false;
         } finally {
            if (var5) {
               this.c = null;
            }
         }

         var10000.c = null;
      }
   }
}
