// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.reader;

import android.os.Parcel;
import android.os.IBinder;
import android.os.Binder;
import android.os.RemoteException;
import java.util.List;
import android.os.IInterface;

public interface ReaderExportCallback extends IInterface
{
    void notify(final int p0, final int p1, final String p2, final List<String> p3, final List<String> p4) throws RemoteException;
    
    public abstract static class Stub extends Binder implements ReaderExportCallback
    {
        private static final String DESCRIPTOR = "com.onyx.android.sdk.reader.ReaderExportCallback";
        static final int b = 1;
        
        public Stub() {
            this.attachInterface((IInterface)this, "com.onyx.android.sdk.reader.ReaderExportCallback");
        }
        
        public static ReaderExportCallback asInterface(final IBinder obj) {
            if (obj == null) {
                return null;
            }
            final IInterface queryLocalInterface;
            if ((queryLocalInterface = obj.queryLocalInterface("com.onyx.android.sdk.reader.ReaderExportCallback")) != null && queryLocalInterface instanceof ReaderExportCallback) {
                return (ReaderExportCallback)queryLocalInterface;
            }
            return new a(obj);
        }
        
        public static boolean setDefaultImpl(final ReaderExportCallback impl) {
            if (Stub.a.b != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (impl != null) {
                Stub.a.b = impl;
                return true;
            }
            return false;
        }
        
        public static ReaderExportCallback getDefaultImpl() {
            return Stub.a.b;
        }
        
        public IBinder asBinder() {
            return (IBinder)this;
        }
        
        public boolean onTransact(int code, final Parcel data, final Parcel reply, final int flags) throws RemoteException {
            final String s = "com.onyx.android.sdk.reader.ReaderExportCallback";
            if (code == 1) {
                data.enforceInterface(s);
                final int int1 = data.readInt();
                code = data.readInt();
                this.notify(int1, code, data.readString(), data.createStringArrayList(), data.createStringArrayList());
                reply.writeNoException();
                return true;
            }
            if (code != 1598968902) {
                return super.onTransact(code, data, reply, flags);
            }
            reply.writeString(s);
            return true;
        }
        
        private static class a implements ReaderExportCallback
        {
            public static ReaderExportCallback b;
            private IBinder a;
            
            a(final IBinder remote) {
                this.a = remote;
            }
            
            public IBinder asBinder() {
                return this.a;
            }
            
            public String a() {
                return "com.onyx.android.sdk.reader.ReaderExportCallback";
            }
            
            @Override
            public void notify(final int progress, final int errorCode, final String outputPath, final List<String> completeList, final List<String> errorList) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel6;
                final Parcel parcel5;
                final Parcel parcel4;
                final Parcel parcel3;
                final Parcel parcel2;
                final Parcel parcel = parcel2 = (parcel3 = (parcel4 = (parcel5 = (parcel6 = obtain))));
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.ReaderExportCallback");
                    parcel3.writeInt(progress);
                    parcel4.writeInt(errorCode);
                    parcel5.writeString(outputPath);
                    parcel6.writeStringList((List)completeList);
                    obtain.writeStringList((List)errorList);
                    if (!this.a.transact(1, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel7 = parcel;
                        final Parcel parcel8 = obtain2;
                        Stub.getDefaultImpl().notify(progress, errorCode, outputPath, completeList, errorList);
                        parcel8.recycle();
                        parcel7.recycle();
                        return;
                    }
                    final Parcel parcel9 = parcel;
                    final Parcel parcel10 = obtain2;
                    parcel10.readException();
                    parcel10.recycle();
                    parcel9.recycle();
                }
                finally {
                    final Parcel parcel11 = parcel;
                    obtain2.recycle();
                    parcel11.recycle();
                }
            }
        }
    }
    
    public static class Default implements ReaderExportCallback
    {
        @Override
        public void notify(final int progress, final int errorCode, final String outputPath, final List<String> completeList, final List<String> errorList) throws RemoteException {
        }
        
        public IBinder asBinder() {
            return null;
        }
    }
}
