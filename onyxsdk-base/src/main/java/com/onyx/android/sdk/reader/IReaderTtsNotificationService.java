// 
// 

package com.onyx.android.sdk.reader;

import android.os.Parcel;
import android.os.IBinder;
import android.os.Binder;
import android.os.RemoteException;
import android.os.IInterface;

public interface IReaderTtsNotificationService extends IInterface
{
    void updateNotification(final TtsNotificationArgs p0) throws RemoteException;
    
    void updateChapterTitle(final String p0) throws RemoteException;
    
    void updateCover(final String p0) throws RemoteException;
    
    void init(final TtsNotificationArgs p0, final TtsServiceIntentCallback p1) throws RemoteException;
    
    void playTts() throws RemoteException;
    
    void pauseTts() throws RemoteException;
    
    void continuePlayTts() throws RemoteException;
    
    void restartTts(final TtsNotificationArgs p0) throws RemoteException;
    
    void switchSpeaker(final String p0) throws RemoteException;
    
    void setSpeechRate(final int p0) throws RemoteException;
    
    void seekBy(final long p0) throws RemoteException;
    
    void updateChapterTiming(final boolean p0) throws RemoteException;
    
    String getActivityName() throws RemoteException;
    
    public abstract static class Stub extends Binder implements IReaderTtsNotificationService
    {
        private static final String DESCRIPTOR = "com.onyx.android.sdk.reader.IReaderTtsNotificationService";
        static final int b = 1;
        static final int c = 2;
        static final int d = 3;
        static final int e = 4;
        static final int f = 5;
        static final int g = 6;
        static final int h = 7;
        static final int i = 8;
        static final int j = 9;
        static final int k = 10;
        static final int l = 11;
        static final int m = 12;
        static final int n = 13;
        
        public Stub() {
            this.attachInterface((IInterface)this, "com.onyx.android.sdk.reader.IReaderTtsNotificationService");
        }
        
        public static IReaderTtsNotificationService asInterface(final IBinder obj) {
            if (obj == null) {
                return null;
            }
            final IInterface queryLocalInterface;
            if ((queryLocalInterface = obj.queryLocalInterface("com.onyx.android.sdk.reader.IReaderTtsNotificationService")) != null && queryLocalInterface instanceof IReaderTtsNotificationService) {
                return (IReaderTtsNotificationService)queryLocalInterface;
            }
            return new a(obj);
        }
        
        public static boolean setDefaultImpl(final IReaderTtsNotificationService impl) {
            if (Stub.a.b != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (impl != null) {
                Stub.a.b = impl;
                return true;
            }
            return false;
        }
        
        public static IReaderTtsNotificationService getDefaultImpl() {
            return Stub.a.b;
        }
        
        public IBinder asBinder() {
            return (IBinder)this;
        }
        
        public boolean onTransact(int code, final Parcel data, final Parcel reply, final int flags) throws RemoteException {
            final String s = "com.onyx.android.sdk.reader.IReaderTtsNotificationService";
            if (code == 1598968902) {
                reply.writeString(s);
                return true;
            }
            switch (code) {
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
                case 13: {
                    data.enforceInterface(s);
                    final String activityName = this.getActivityName();
                    reply.writeNoException();
                    reply.writeString(activityName);
                    return true;
                }
                case 12: {
                    data.enforceInterface(s);
                    if (data.readInt() != 0) {
                        code = 1;
                    }
                    else {
                        code = 0;
                    }
                    this.updateChapterTiming((boolean)(code != 0));
                    reply.writeNoException();
                    return true;
                }
                case 11: {
                    data.enforceInterface(s);
                    this.seekBy(data.readLong());
                    reply.writeNoException();
                    return true;
                }
                case 10: {
                    data.enforceInterface(s);
                    this.setSpeechRate(data.readInt());
                    reply.writeNoException();
                    return true;
                }
                case 9: {
                    data.enforceInterface(s);
                    this.switchSpeaker(data.readString());
                    reply.writeNoException();
                    return true;
                }
                case 8: {
                    data.enforceInterface(s);
                    TtsNotificationArgs ttsNotificationArgs;
                    if (data.readInt() != 0) {
                        ttsNotificationArgs = (TtsNotificationArgs)TtsNotificationArgs.CREATOR.createFromParcel(data);
                    }
                    else {
                        ttsNotificationArgs = null;
                    }
                    this.restartTts(ttsNotificationArgs);
                    reply.writeNoException();
                    return true;
                }
                case 7: {
                    data.enforceInterface(s);
                    this.continuePlayTts();
                    reply.writeNoException();
                    return true;
                }
                case 6: {
                    data.enforceInterface(s);
                    this.pauseTts();
                    reply.writeNoException();
                    return true;
                }
                case 5: {
                    data.enforceInterface(s);
                    this.playTts();
                    reply.writeNoException();
                    return true;
                }
                case 4: {
                    data.enforceInterface(s);
                    TtsNotificationArgs ttsNotificationArgs2;
                    if (data.readInt() != 0) {
                        ttsNotificationArgs2 = (TtsNotificationArgs)TtsNotificationArgs.CREATOR.createFromParcel(data);
                    }
                    else {
                        ttsNotificationArgs2 = null;
                    }
                    this.init(ttsNotificationArgs2, TtsServiceIntentCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                }
                case 3: {
                    data.enforceInterface(s);
                    this.updateCover(data.readString());
                    reply.writeNoException();
                    return true;
                }
                case 2: {
                    data.enforceInterface(s);
                    this.updateChapterTitle(data.readString());
                    reply.writeNoException();
                    return true;
                }
                case 1: {
                    data.enforceInterface(s);
                    TtsNotificationArgs ttsNotificationArgs3;
                    if (data.readInt() != 0) {
                        ttsNotificationArgs3 = (TtsNotificationArgs)TtsNotificationArgs.CREATOR.createFromParcel(data);
                    }
                    else {
                        ttsNotificationArgs3 = null;
                    }
                    this.updateNotification(ttsNotificationArgs3);
                    reply.writeNoException();
                    return true;
                }
            }
        }
        
        private static class a implements IReaderTtsNotificationService
        {
            public static IReaderTtsNotificationService b;
            private IBinder a;
            
            a(final IBinder remote) {
                this.a = remote;
            }
            
            public IBinder asBinder() {
                return this.a;
            }
            
            public String a() {
                return "com.onyx.android.sdk.reader.IReaderTtsNotificationService";
            }
            
            @Override
            public void updateNotification(final TtsNotificationArgs args) throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.IReaderTtsNotificationService");
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
                        Stub.getDefaultImpl().updateNotification(args);
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
            
            @Override
            public void updateChapterTitle(final String chapterTitle) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel2;
                final Parcel parcel = parcel2 = obtain;
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IReaderTtsNotificationService");
                    obtain.writeString(chapterTitle);
                    if (!this.a.transact(2, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel3 = parcel;
                        final Parcel parcel4 = obtain2;
                        Stub.getDefaultImpl().updateChapterTitle(chapterTitle);
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
            public void updateCover(final String coverPath) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel2;
                final Parcel parcel = parcel2 = obtain;
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IReaderTtsNotificationService");
                    obtain.writeString(coverPath);
                    if (!this.a.transact(3, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel3 = parcel;
                        final Parcel parcel4 = obtain2;
                        Stub.getDefaultImpl().updateCover(coverPath);
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
            public void init(final TtsNotificationArgs args, final TtsServiceIntentCallback callback) throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.IReaderTtsNotificationService");
                    if (args != null) {
                        final Parcel dest = obtain;
                        dest.writeInt(1);
                        args.writeToParcel(dest, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    IBinder binder;
                    if (callback != null) {
                        binder = ((IInterface)callback).asBinder();
                    }
                    else {
                        binder = null;
                    }
                    obtain.writeStrongBinder(binder);
                    if (!this.a.transact(4, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel2 = obtain;
                        final Parcel parcel3 = obtain2;
                        Stub.getDefaultImpl().init(args, callback);
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
            
            @Override
            public void playTts() throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.IReaderTtsNotificationService");
                    if (!this.a.transact(5, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel2 = obtain;
                        final Parcel parcel3 = obtain2;
                        Stub.getDefaultImpl().playTts();
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
            
            @Override
            public void pauseTts() throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.IReaderTtsNotificationService");
                    if (!this.a.transact(6, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel2 = obtain;
                        final Parcel parcel3 = obtain2;
                        Stub.getDefaultImpl().pauseTts();
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
            
            @Override
            public void continuePlayTts() throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.IReaderTtsNotificationService");
                    if (!this.a.transact(7, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel2 = obtain;
                        final Parcel parcel3 = obtain2;
                        Stub.getDefaultImpl().continuePlayTts();
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
            
            @Override
            public void restartTts(final TtsNotificationArgs args) throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.IReaderTtsNotificationService");
                    if (args != null) {
                        final Parcel dest = obtain;
                        dest.writeInt(1);
                        args.writeToParcel(dest, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    if (!this.a.transact(8, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel2 = obtain;
                        final Parcel parcel3 = obtain2;
                        Stub.getDefaultImpl().restartTts(args);
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
            
            @Override
            public void switchSpeaker(final String speaker) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel2;
                final Parcel parcel = parcel2 = obtain;
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IReaderTtsNotificationService");
                    obtain.writeString(speaker);
                    if (!this.a.transact(9, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel3 = parcel;
                        final Parcel parcel4 = obtain2;
                        Stub.getDefaultImpl().switchSpeaker(speaker);
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
            public void setSpeechRate(final int rate) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel2;
                final Parcel parcel = parcel2 = obtain;
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IReaderTtsNotificationService");
                    obtain.writeInt(rate);
                    if (!this.a.transact(10, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel3 = parcel;
                        final Parcel parcel4 = obtain2;
                        Stub.getDefaultImpl().setSpeechRate(rate);
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
            public void seekBy(final long deltaMs) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel2;
                final Parcel parcel = parcel2 = obtain;
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IReaderTtsNotificationService");
                    obtain.writeLong(deltaMs);
                    if (!this.a.transact(11, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel3 = parcel;
                        final Parcel parcel4 = obtain2;
                        Stub.getDefaultImpl().seekBy(deltaMs);
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
            public void updateChapterTiming(final boolean isChapterTiming) throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.IReaderTtsNotificationService");
                    int n;
                    if (isChapterTiming) {
                        n = 1;
                    }
                    else {
                        n = 0;
                    }
                    obtain.writeInt(n);
                    if (!this.a.transact(12, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel2 = obtain;
                        final Parcel parcel3 = obtain2;
                        Stub.getDefaultImpl().updateChapterTiming(isChapterTiming);
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
            
            @Override
            public String getActivityName() throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.IReaderTtsNotificationService");
                    if (!this.a.transact(13, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final String activityName = Stub.getDefaultImpl().getActivityName();
                        final Parcel parcel2 = obtain;
                        obtain2.recycle();
                        parcel2.recycle();
                        return activityName;
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
        }
    }
    
    public static class Default implements IReaderTtsNotificationService
    {
        @Override
        public void updateNotification(final TtsNotificationArgs args) throws RemoteException {
        }
        
        @Override
        public void updateChapterTitle(final String chapterTitle) throws RemoteException {
        }
        
        @Override
        public void updateCover(final String coverPath) throws RemoteException {
        }
        
        @Override
        public void init(final TtsNotificationArgs args, final TtsServiceIntentCallback callback) throws RemoteException {
        }
        
        @Override
        public void playTts() throws RemoteException {
        }
        
        @Override
        public void pauseTts() throws RemoteException {
        }
        
        @Override
        public void continuePlayTts() throws RemoteException {
        }
        
        @Override
        public void restartTts(final TtsNotificationArgs args) throws RemoteException {
        }
        
        @Override
        public void switchSpeaker(final String speaker) throws RemoteException {
        }
        
        @Override
        public void setSpeechRate(final int rate) throws RemoteException {
        }
        
        @Override
        public void seekBy(final long deltaMs) throws RemoteException {
        }
        
        @Override
        public void updateChapterTiming(final boolean isChapterTiming) throws RemoteException {
        }
        
        @Override
        public String getActivityName() throws RemoteException {
            return null;
        }
        
        public IBinder asBinder() {
            return null;
        }
    }
}
