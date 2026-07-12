package com.onyx.android.sdk.data.dpm;

import android.content.pm.IPackageDeleteObserver;
import android.os.RemoteException;

public abstract class PackageDeleteObserver extends IPackageDeleteObserver.Stub {
   private static final String a = PackageDeleteObserver.class.getSimpleName();

   @Override
   public abstract void packageDeleted(String var1, int var2) throws RemoteException;
}
