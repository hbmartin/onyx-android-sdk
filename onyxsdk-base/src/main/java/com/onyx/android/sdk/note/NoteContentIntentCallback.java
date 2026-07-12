// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.note;

import android.os.Parcel;
import android.os.IBinder;
import android.os.Binder;
import android.os.RemoteException;
import android.os.IInterface;

public interface NoteContentIntentCallback extends IInterface
{
    void read(final NoteContentIntentArgs p0) throws RemoteException;
    
    public abstract static class Stub extends Binder implements NoteContentIntentCallback
    {
        private static final String DESCRIPTOR = "com.onyx.android.sdk.note.NoteContentIntentCallback";
        static final int b = 1;
        
        public Stub() {
            this.attachInterface((IInterface)this, "com.onyx.android.sdk.note.NoteContentIntentCallback");
        }
        
        public static NoteContentIntentCallback asInterface(final IBinder obj) {
            if (obj == null) {
                return null;
            }
            final IInterface queryLocalInterface;
            if ((queryLocalInterface = obj.queryLocalInterface("com.onyx.android.sdk.note.NoteContentIntentCallback")) != null && queryLocalInterface instanceof NoteContentIntentCallback) {
                return (NoteContentIntentCallback)queryLocalInterface;
            }
            return new a(obj);
        }
        
        public static boolean setDefaultImpl(final NoteContentIntentCallback impl) {
            if (Stub.a.b != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (impl != null) {
                Stub.a.b = impl;
                return true;
            }
            return false;
        }
        
        public static NoteContentIntentCallback getDefaultImpl() {
            return Stub.a.b;
        }
        
        public IBinder asBinder() {
            return (IBinder)this;
        }
        
        public boolean onTransact(final int code, final Parcel data, final Parcel reply, final int flags) throws RemoteException {
            final String s = "com.onyx.android.sdk.note.NoteContentIntentCallback";
            if (code == 1) {
                data.enforceInterface(s);
                NoteContentIntentArgs noteContentIntentArgs;
                if (data.readInt() != 0) {
                    noteContentIntentArgs = (NoteContentIntentArgs)NoteContentIntentArgs.CREATOR.createFromParcel(data);
                }
                else {
                    noteContentIntentArgs = null;
                }
                this.read(noteContentIntentArgs);
                reply.writeNoException();
                return true;
            }
            if (code != 1598968902) {
                return super.onTransact(code, data, reply, flags);
            }
            reply.writeString(s);
            return true;
        }
        
        private static class a implements NoteContentIntentCallback
        {
            public static NoteContentIntentCallback b;
            private IBinder a;
            
            a(final IBinder remote) {
                this.a = remote;
            }
            
            public IBinder asBinder() {
                return this.a;
            }
            
            public String a() {
                return "com.onyx.android.sdk.note.NoteContentIntentCallback";
            }
            
            @Override
            public void read(final NoteContentIntentArgs args) throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.note.NoteContentIntentCallback");
                    if (args != null) {
                        final Parcel dest = obtain;
                        dest.writeInt(1);
                        args.writeToParcel(dest, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    if (!this.a.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel2 = obtain;
                        final Parcel parcel3 = obtain2;
                        Stub.getDefaultImpl().read(args);
                        parcel3.recycle();
                        parcel2.recycle();
                        return;
                    }
                    final Parcel parcel4 = obtain;
                    final Parcel parcel5 = obtain2;
                    parcel5.readException();
                    parcel5.recycle();
                    parcel4.recycle();
                }
                finally {
                    final Parcel parcel6 = obtain;
                    obtain2.recycle();
                    parcel6.recycle();
                }
            }
        }
    }
    
    public static class Default implements NoteContentIntentCallback
    {
        @Override
        public void read(final NoteContentIntentArgs args) throws RemoteException {
        }
        
        public IBinder asBinder() {
            return null;
        }
    }
}
