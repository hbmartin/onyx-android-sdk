package android.content.pm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IPackageDeleteObserver extends IInterface {
   void packageDeleted(String var1, int var2) throws RemoteException;

   abstract class Stub extends Binder implements IPackageDeleteObserver {
      public Stub() {
         throw new RuntimeException("Stub!");
      }

      public static IPackageDeleteObserver asInterface(IBinder obj) {
         throw new RuntimeException("Stub!");
      }

      public IBinder asBinder() {
         throw new RuntimeException("Stub!");
      }

      public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
         throw new RuntimeException("Stub!");
      }
   }
}
