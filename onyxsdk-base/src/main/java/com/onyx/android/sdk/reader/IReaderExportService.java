// 
// 

package com.onyx.android.sdk.reader;

import android.os.Parcel;
import android.os.IBinder;
import android.os.Binder;
import android.os.RemoteException;
import android.os.IInterface;

public interface IReaderExportService extends IInterface
{
    int export(final String p0) throws RemoteException;
    
    void interrupt(final String p0) throws RemoteException;
    
    void convertToPDF(final String p0, final String p1, final ReaderPDFConverterCallback p2) throws RemoteException;
    
    void exportDocuments(final String p0, final ReaderExportCallback p1) throws RemoteException;
    
    void importDocuments(final String p0, final ReaderImportCallback p1) throws RemoteException;
    
    public abstract static class Stub extends Binder implements IReaderExportService
    {
        private static final String DESCRIPTOR = "com.onyx.android.sdk.reader.IReaderExportService";
        static final int b = 1;
        static final int c = 2;
        static final int d = 3;
        static final int e = 4;
        static final int f = 5;
        
        public Stub() {
            this.attachInterface((IInterface)this, "com.onyx.android.sdk.reader.IReaderExportService");
        }
        
        public static IReaderExportService asInterface(final IBinder obj) {
            if (obj == null) {
                return null;
            }
            final IInterface queryLocalInterface;
            if ((queryLocalInterface = obj.queryLocalInterface("com.onyx.android.sdk.reader.IReaderExportService")) != null && queryLocalInterface instanceof IReaderExportService) {
                return (IReaderExportService)queryLocalInterface;
            }
            return new a(obj);
        }
        
        public static boolean setDefaultImpl(final IReaderExportService impl) {
            if (Stub.a.b != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (impl != null) {
                Stub.a.b = impl;
                return true;
            }
            return false;
        }
        
        public static IReaderExportService getDefaultImpl() {
            return Stub.a.b;
        }
        
        public IBinder asBinder() {
            return (IBinder)this;
        }
        
        public boolean onTransact(final int code, final Parcel data, final Parcel reply, final int flags) throws RemoteException {
            final String s = "com.onyx.android.sdk.reader.IReaderExportService";
            if (code == 1598968902) {
                reply.writeString(s);
                return true;
            }
            switch (code) {
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
                case 5: {
                    data.enforceInterface(s);
                    this.importDocuments(data.readString(), ReaderImportCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                }
                case 4: {
                    data.enforceInterface(s);
                    this.exportDocuments(data.readString(), ReaderExportCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                }
                case 3: {
                    data.enforceInterface(s);
                    this.convertToPDF(data.readString(), data.readString(), ReaderPDFConverterCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                }
                case 2: {
                    data.enforceInterface(s);
                    this.interrupt(data.readString());
                    reply.writeNoException();
                    return true;
                }
                case 1: {
                    data.enforceInterface(s);
                    final int export = this.export(data.readString());
                    reply.writeNoException();
                    reply.writeInt(export);
                    return true;
                }
            }
        }
        
        private static class a implements IReaderExportService
        {
            public static IReaderExportService b;
            private IBinder a;
            
            a(final IBinder remote) {
                this.a = remote;
            }
            
            public IBinder asBinder() {
                return this.a;
            }
            
            public String a() {
                return "com.onyx.android.sdk.reader.IReaderExportService";
            }
            
            @Override
            public int export(final String json) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel2;
                final Parcel parcel = parcel2 = obtain;
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IReaderExportService");
                    obtain.writeString(json);
                    if (!this.a.transact(1, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final int export = Stub.getDefaultImpl().export(json);
                        final Parcel parcel3 = parcel;
                        obtain2.recycle();
                        parcel3.recycle();
                        return export;
                    }
                    final Parcel parcel4 = obtain2;
                    parcel4.readException();
                    final int int1 = parcel4.readInt();
                    final Parcel parcel5 = parcel;
                    obtain2.recycle();
                    parcel5.recycle();
                    return int1;
                }
                finally {
                    final Parcel parcel6 = parcel;
                    obtain2.recycle();
                    parcel6.recycle();
                }
            }
            
            @Override
            public void interrupt(final String outputPath) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel2;
                final Parcel parcel = parcel2 = obtain;
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IReaderExportService");
                    obtain.writeString(outputPath);
                    if (!this.a.transact(2, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel3 = parcel;
                        final Parcel parcel4 = obtain2;
                        Stub.getDefaultImpl().interrupt(outputPath);
                        parcel4.recycle();
                        parcel3.recycle();
                        return;
                    }
                    final Parcel parcel5 = parcel;
                    final Parcel parcel6 = obtain2;
                    parcel6.readException();
                    parcel6.recycle();
                    parcel5.recycle();
                }
                finally {
                    final Parcel parcel7 = parcel;
                    obtain2.recycle();
                    parcel7.recycle();
                }
            }
            
            @Override
            public void convertToPDF(final String path, final String outputPath, final ReaderPDFConverterCallback callback) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel3;
                final Parcel parcel2;
                final Parcel parcel = parcel2 = (parcel3 = obtain);
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IReaderExportService");
                    parcel3.writeString(path);
                    obtain.writeString(outputPath);
                    IBinder binder;
                    if (callback != null) {
                        binder = ((IInterface)callback).asBinder();
                    }
                    else {
                        binder = null;
                    }
                    parcel.writeStrongBinder(binder);
                    if (!this.a.transact(3, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel4 = parcel;
                        final Parcel parcel5 = obtain2;
                        Stub.getDefaultImpl().convertToPDF(path, outputPath, callback);
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
            
            @Override
            public void exportDocuments(final String exportParamJson, final ReaderExportCallback callback) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel2;
                final Parcel parcel = parcel2 = obtain;
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IReaderExportService");
                    obtain.writeString(exportParamJson);
                    IBinder binder;
                    if (callback != null) {
                        binder = ((IInterface)callback).asBinder();
                    }
                    else {
                        binder = null;
                    }
                    parcel.writeStrongBinder(binder);
                    if (!this.a.transact(4, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel3 = parcel;
                        final Parcel parcel4 = obtain2;
                        Stub.getDefaultImpl().exportDocuments(exportParamJson, callback);
                        parcel4.recycle();
                        parcel3.recycle();
                        return;
                    }
                    final Parcel parcel5 = parcel;
                    final Parcel parcel6 = obtain2;
                    parcel6.readException();
                    parcel6.recycle();
                    parcel5.recycle();
                }
                finally {
                    final Parcel parcel7 = parcel;
                    obtain2.recycle();
                    parcel7.recycle();
                }
            }
            
            @Override
            public void importDocuments(final String importParamJson, final ReaderImportCallback callback) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel2;
                final Parcel parcel = parcel2 = obtain;
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IReaderExportService");
                    obtain.writeString(importParamJson);
                    IBinder binder;
                    if (callback != null) {
                        binder = ((IInterface)callback).asBinder();
                    }
                    else {
                        binder = null;
                    }
                    parcel.writeStrongBinder(binder);
                    if (!this.a.transact(5, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel3 = parcel;
                        final Parcel parcel4 = obtain2;
                        Stub.getDefaultImpl().importDocuments(importParamJson, callback);
                        parcel4.recycle();
                        parcel3.recycle();
                        return;
                    }
                    final Parcel parcel5 = parcel;
                    final Parcel parcel6 = obtain2;
                    parcel6.readException();
                    parcel6.recycle();
                    parcel5.recycle();
                }
                finally {
                    final Parcel parcel7 = parcel;
                    obtain2.recycle();
                    parcel7.recycle();
                }
            }
        }
    }
    
    public static class Default implements IReaderExportService
    {
        @Override
        public int export(final String json) throws RemoteException {
            return 0;
        }
        
        @Override
        public void interrupt(final String outputPath) throws RemoteException {
        }
        
        @Override
        public void convertToPDF(final String path, final String outputPath, final ReaderPDFConverterCallback callback) throws RemoteException {
        }
        
        @Override
        public void exportDocuments(final String exportParamJson, final ReaderExportCallback callback) throws RemoteException {
        }
        
        @Override
        public void importDocuments(final String importParamJson, final ReaderImportCallback callback) throws RemoteException {
        }
        
        public IBinder asBinder() {
            return null;
        }
    }
}
