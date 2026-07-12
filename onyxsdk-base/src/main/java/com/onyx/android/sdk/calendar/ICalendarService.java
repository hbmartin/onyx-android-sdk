// 
// 

package com.onyx.android.sdk.calendar;

import java.util.ArrayList;
import android.os.Parcel;
import android.os.IBinder;
import android.os.Binder;
import android.os.RemoteException;
import java.util.List;
import android.os.IInterface;

public interface ICalendarService extends IInterface
{
    List exportDateMemoPngList(final int p0, final int p1, final int p2) throws RemoteException;
    
    String exportDateIndexMemoPngList(final int p0, final int p1, final int p2, final int p3) throws RemoteException;
    
    String exportLunarDate(final int p0, final int p1, final int p2) throws RemoteException;
    
    String loadCalendarConfig() throws RemoteException;
    
    void interrupt() throws RemoteException;
    
    public abstract static class Stub extends Binder implements ICalendarService
    {
        private static final String DESCRIPTOR = "com.onyx.android.sdk.calendar.ICalendarService";
        static final int b = 1;
        static final int c = 2;
        static final int d = 3;
        static final int e = 4;
        static final int f = 5;
        
        public Stub() {
            this.attachInterface((IInterface)this, "com.onyx.android.sdk.calendar.ICalendarService");
        }
        
        public static ICalendarService asInterface(final IBinder obj) {
            if (obj == null) {
                return null;
            }
            final IInterface queryLocalInterface;
            if ((queryLocalInterface = obj.queryLocalInterface("com.onyx.android.sdk.calendar.ICalendarService")) != null && queryLocalInterface instanceof ICalendarService) {
                return (ICalendarService)queryLocalInterface;
            }
            return new a(obj);
        }
        
        public static boolean setDefaultImpl(final ICalendarService impl) {
            if (Stub.a.b != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (impl != null) {
                Stub.a.b = impl;
                return true;
            }
            return false;
        }
        
        public static ICalendarService getDefaultImpl() {
            return Stub.a.b;
        }
        
        public IBinder asBinder() {
            return (IBinder)this;
        }
        
        public boolean onTransact(int code, final Parcel data, final Parcel reply, final int flags) throws RemoteException {
            final String s = "com.onyx.android.sdk.calendar.ICalendarService";
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
                    this.interrupt();
                    reply.writeNoException();
                    return true;
                }
                case 4: {
                    data.enforceInterface(s);
                    final String loadCalendarConfig = this.loadCalendarConfig();
                    reply.writeNoException();
                    reply.writeString(loadCalendarConfig);
                    return true;
                }
                case 3: {
                    data.enforceInterface(s);
                    final int int1 = data.readInt();
                    code = data.readInt();
                    final String exportLunarDate = this.exportLunarDate(int1, code, data.readInt());
                    reply.writeNoException();
                    reply.writeString(exportLunarDate);
                    return true;
                }
                case 2: {
                    data.enforceInterface(s);
                    final int int2 = data.readInt();
                    code = data.readInt();
                    final String exportDateIndexMemoPngList = this.exportDateIndexMemoPngList(int2, code, data.readInt(), data.readInt());
                    reply.writeNoException();
                    reply.writeString(exportDateIndexMemoPngList);
                    return true;
                }
                case 1: {
                    data.enforceInterface(s);
                    final int int3 = data.readInt();
                    code = data.readInt();
                    final List exportDateMemoPngList = this.exportDateMemoPngList(int3, code, data.readInt());
                    reply.writeNoException();
                    reply.writeList(exportDateMemoPngList);
                    return true;
                }
            }
        }
        
        private static class a implements ICalendarService
        {
            public static ICalendarService b;
            private IBinder a;
            
            a(final IBinder remote) {
                this.a = remote;
            }
            
            public IBinder asBinder() {
                return this.a;
            }
            
            public String a() {
                return "com.onyx.android.sdk.calendar.ICalendarService";
            }
            
            @Override
            public List exportDateMemoPngList(final int year, final int month, final int day) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel4;
                final Parcel parcel3;
                final Parcel parcel2;
                final Parcel parcel = parcel2 = (parcel3 = (parcel4 = obtain));
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.calendar.ICalendarService");
                    parcel3.writeInt(year);
                    parcel4.writeInt(month);
                    obtain.writeInt(day);
                    if (!this.a.transact(1, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final List exportDateMemoPngList = Stub.getDefaultImpl().exportDateMemoPngList(year, month, day);
                        final Parcel parcel5 = parcel;
                        obtain2.recycle();
                        parcel5.recycle();
                        return exportDateMemoPngList;
                    }
                    final Parcel parcel6 = obtain2;
                    obtain2.readException();
                    final ArrayList arrayList = parcel6.readArrayList(this.getClass().getClassLoader());
                    final Parcel parcel7 = parcel;
                    obtain2.recycle();
                    parcel7.recycle();
                    return arrayList;
                }
                finally {
                    final Parcel parcel8 = parcel;
                    obtain2.recycle();
                    parcel8.recycle();
                }
            }
            
            @Override
            public String exportDateIndexMemoPngList(final int year, final int month, final int day, final int index) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel5;
                final Parcel parcel4;
                final Parcel parcel3;
                final Parcel parcel2;
                final Parcel parcel = parcel2 = (parcel3 = (parcel4 = (parcel5 = obtain)));
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.calendar.ICalendarService");
                    parcel3.writeInt(year);
                    parcel4.writeInt(month);
                    parcel5.writeInt(day);
                    obtain.writeInt(index);
                    if (!this.a.transact(2, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final String exportDateIndexMemoPngList = Stub.getDefaultImpl().exportDateIndexMemoPngList(year, month, day, index);
                        final Parcel parcel6 = parcel;
                        obtain2.recycle();
                        parcel6.recycle();
                        return exportDateIndexMemoPngList;
                    }
                    final Parcel parcel7 = obtain2;
                    parcel7.readException();
                    final String string = parcel7.readString();
                    final Parcel parcel8 = parcel;
                    obtain2.recycle();
                    parcel8.recycle();
                    return string;
                }
                finally {
                    final Parcel parcel9 = parcel;
                    obtain2.recycle();
                    parcel9.recycle();
                }
            }
            
            @Override
            public String exportLunarDate(final int year, final int month, final int day) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel4;
                final Parcel parcel3;
                final Parcel parcel2;
                final Parcel parcel = parcel2 = (parcel3 = (parcel4 = obtain));
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.calendar.ICalendarService");
                    parcel3.writeInt(year);
                    parcel4.writeInt(month);
                    obtain.writeInt(day);
                    if (!this.a.transact(3, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final String exportLunarDate = Stub.getDefaultImpl().exportLunarDate(year, month, day);
                        final Parcel parcel5 = parcel;
                        obtain2.recycle();
                        parcel5.recycle();
                        return exportLunarDate;
                    }
                    final Parcel parcel6 = obtain2;
                    parcel6.readException();
                    final String string = parcel6.readString();
                    final Parcel parcel7 = parcel;
                    obtain2.recycle();
                    parcel7.recycle();
                    return string;
                }
                finally {
                    final Parcel parcel8 = parcel;
                    obtain2.recycle();
                    parcel8.recycle();
                }
            }
            
            @Override
            public String loadCalendarConfig() throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.calendar.ICalendarService");
                    if (!this.a.transact(4, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final String loadCalendarConfig = Stub.getDefaultImpl().loadCalendarConfig();
                        final Parcel parcel2 = obtain;
                        obtain2.recycle();
                        parcel2.recycle();
                        return loadCalendarConfig;
                    }
                    final Parcel parcel3 = obtain2;
                    parcel3.readException();
                    final String string = parcel3.readString();
                    final Parcel parcel4 = obtain;
                    obtain2.recycle();
                    parcel4.recycle();
                    return string;
                }
                finally {
                    final Parcel parcel5 = obtain;
                    obtain2.recycle();
                    parcel5.recycle();
                }
            }
            
            @Override
            public void interrupt() throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.calendar.ICalendarService");
                    if (!this.a.transact(5, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel2 = obtain;
                        final Parcel parcel3 = obtain2;
                        Stub.getDefaultImpl().interrupt();
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
    
    public static class Default implements ICalendarService
    {
        @Override
        public List exportDateMemoPngList(final int year, final int month, final int day) throws RemoteException {
            return null;
        }
        
        @Override
        public String exportDateIndexMemoPngList(final int year, final int month, final int day, final int index) throws RemoteException {
            return null;
        }
        
        @Override
        public String exportLunarDate(final int year, final int month, final int day) throws RemoteException {
            return null;
        }
        
        @Override
        public String loadCalendarConfig() throws RemoteException {
            return null;
        }
        
        @Override
        public void interrupt() throws RemoteException {
        }
        
        public IBinder asBinder() {
            return null;
        }
    }
}
