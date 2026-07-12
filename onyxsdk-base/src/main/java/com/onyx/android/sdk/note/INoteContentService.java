// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.note;

import android.os.Parcel;
import android.os.IBinder;
import android.os.Binder;
import android.os.RemoteException;
import android.os.Bundle;
import android.os.IInterface;

public interface INoteContentService extends IInterface
{
    void sendMessageAction(final Bundle p0) throws RemoteException;
    
    void loadVirtualPageIdList(final String p0, final NoteContentIntentCallback p1) throws RemoteException;
    
    public abstract static class Stub extends Binder implements INoteContentService
    {
        private static final String DESCRIPTOR = "com.onyx.android.sdk.note.INoteContentService";
        static final int b = 1;
        static final int c = 2;
        
        public Stub() {
            this.attachInterface((IInterface)this, "com.onyx.android.sdk.note.INoteContentService");
        }
        
        public static INoteContentService asInterface(final IBinder obj) {
            if (obj == null) {
                return null;
            }
            final IInterface queryLocalInterface;
            if ((queryLocalInterface = obj.queryLocalInterface("com.onyx.android.sdk.note.INoteContentService")) != null && queryLocalInterface instanceof INoteContentService) {
                return (INoteContentService)queryLocalInterface;
            }
            return new a(obj);
        }
        
        public static boolean setDefaultImpl(final INoteContentService impl) {
            if (Stub.a.b != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (impl != null) {
                Stub.a.b = impl;
                return true;
            }
            return false;
        }
        
        public static INoteContentService getDefaultImpl() {
            return Stub.a.b;
        }
        
        public IBinder asBinder() {
            return (IBinder)this;
        }
        
        public boolean onTransact(final int code, final Parcel data, final Parcel reply, final int flags) throws RemoteException {
            final String s = "com.onyx.android.sdk.note.INoteContentService";
            if (code == 1) {
                data.enforceInterface(s);
                Bundle bundle;
                if (data.readInt() != 0) {
                    bundle = (Bundle)Bundle.CREATOR.createFromParcel(data);
                }
                else {
                    bundle = null;
                }
                this.sendMessageAction(bundle);
                return true;
            }
            if (code == 2) {
                data.enforceInterface(s);
                this.loadVirtualPageIdList(data.readString(), NoteContentIntentCallback.Stub.asInterface(data.readStrongBinder()));
                return true;
            }
            if (code != 1598968902) {
                return super.onTransact(code, data, reply, flags);
            }
            reply.writeString(s);
            return true;
        }
        
        private static class a implements INoteContentService
        {
            public static INoteContentService b;
            private IBinder a;
            
            a(final IBinder remote) {
                this.a = remote;
            }
            
            public IBinder asBinder() {
                return this.a;
            }
            
            public String a() {
                return "com.onyx.android.sdk.note.INoteContentService";
            }
            
            @Override
            public void sendMessageAction(final Bundle bundle) throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.note.INoteContentService");
                    if (bundle != null) {
                        final Parcel parcel2 = obtain;
                        parcel2.writeInt(1);
                        bundle.writeToParcel(parcel2, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    if (!this.a.transact(1, obtain, (Parcel)null, 1) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel3 = obtain;
                        Stub.getDefaultImpl().sendMessageAction(bundle);
                        parcel3.recycle();
                        return;
                    }
                    obtain.recycle();
                }
                finally {
                    obtain.recycle();
                }
            }
            
            @Override
            public void loadVirtualPageIdList(final String docId, final NoteContentIntentCallback callback) throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.onyx.android.sdk.note.INoteContentService");
                    parcel.writeString(docId);
                    IBinder binder;
                    if (callback != null) {
                        binder = ((IInterface)callback).asBinder();
                    }
                    else {
                        binder = null;
                    }
                    obtain.writeStrongBinder(binder);
                    if (!this.a.transact(2, obtain, (Parcel)null, 1) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel2 = obtain;
                        Stub.getDefaultImpl().loadVirtualPageIdList(docId, callback);
                        parcel2.recycle();
                        return;
                    }
                    obtain.recycle();
                }
                finally {
                    obtain.recycle();
                }
            }
        }
    }
    
    public static class Default implements INoteContentService
    {
        @Override
        public void sendMessageAction(final Bundle bundle) throws RemoteException {
        }
        
        @Override
        public void loadVirtualPageIdList(final String docId, final NoteContentIntentCallback callback) throws RemoteException {
        }
        
        public IBinder asBinder() {
            return null;
        }
    }
}
