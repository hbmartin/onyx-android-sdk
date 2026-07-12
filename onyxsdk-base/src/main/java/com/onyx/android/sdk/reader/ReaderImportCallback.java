// 
// 

package com.onyx.android.sdk.reader;

import android.os.Parcel;
import android.os.IBinder;
import android.os.Binder;
import android.os.RemoteException;
import android.os.IInterface;

public interface ReaderImportCallback extends IInterface
{
    void notify(final int p0, final int p1) throws RemoteException;
    
    public abstract static class Stub extends Binder implements ReaderImportCallback
    {
        private static final String DESCRIPTOR = "com.onyx.android.sdk.reader.ReaderImportCallback";
        static final int b = 1;
        
        public Stub() {
            this.attachInterface((IInterface)this, "com.onyx.android.sdk.reader.ReaderImportCallback");
        }
        
        public static ReaderImportCallback asInterface(final IBinder obj) {
            if (obj == null) {
                return null;
            }
            final IInterface queryLocalInterface;
            if ((queryLocalInterface = obj.queryLocalInterface("com.onyx.android.sdk.reader.ReaderImportCallback")) != null && queryLocalInterface instanceof ReaderImportCallback) {
                return (ReaderImportCallback)queryLocalInterface;
            }
            return new a(obj);
        }
        
        public static boolean setDefaultImpl(final ReaderImportCallback impl) {
            if (Stub.a.b != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (impl != null) {
                Stub.a.b = impl;
                return true;
            }
            return false;
        }
        
        public static ReaderImportCallback getDefaultImpl() {
            return Stub.a.b;
        }
        
        public IBinder asBinder() {
            return (IBinder)this;
        }
        
        public boolean onTransact(final int code, final Parcel data, final Parcel reply, final int flags) throws RemoteException {
            final String s = "com.onyx.android.sdk.reader.ReaderImportCallback";
            if (code == 1) {
                data.enforceInterface(s);
                this.notify(data.readInt(), data.readInt());
                reply.writeNoException();
                return true;
            }
            if (code != 1598968902) {
                return super.onTransact(code, data, reply, flags);
            }
            reply.writeString(s);
            return true;
        }
        
        private static class a implements ReaderImportCallback
        {
            public static ReaderImportCallback b;
            private IBinder a;
            
            a(final IBinder remote) {
                this.a = remote;
            }
            
            public IBinder asBinder() {
                return this.a;
            }
            
            public String a() {
                return "com.onyx.android.sdk.reader.ReaderImportCallback";
            }
            
            @Override
            public void notify(final int progress, final int errorCode) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel3;
                final Parcel parcel2;
                final Parcel parcel = parcel2 = (parcel3 = obtain);
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.ReaderImportCallback");
                    parcel3.writeInt(progress);
                    obtain.writeInt(errorCode);
                    if (!this.a.transact(1, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel4 = parcel;
                        final Parcel parcel5 = obtain2;
                        Stub.getDefaultImpl().notify(progress, errorCode);
                        parcel5.recycle();
                        parcel4.recycle();
                        return;
                    }
                    final Parcel parcel6 = parcel;
                    final Parcel parcel7 = obtain2;
                    parcel7.readException();
                    parcel7.recycle();
                    parcel6.recycle();
                }
                finally {
                    final Parcel parcel8 = parcel;
                    obtain2.recycle();
                    parcel8.recycle();
                }
            }
        }
    }
    
    public static class Default implements ReaderImportCallback
    {
        @Override
        public void notify(final int progress, final int errorCode) throws RemoteException {
        }
        
        public IBinder asBinder() {
            return null;
        }
    }
}
