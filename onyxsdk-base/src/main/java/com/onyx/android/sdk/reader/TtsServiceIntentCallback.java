// 
// 

package com.onyx.android.sdk.reader;

import android.os.Parcel;
import android.os.IBinder;
import android.os.Binder;
import android.os.RemoteException;
import android.os.IInterface;

public interface TtsServiceIntentCallback extends IInterface
{
    void onTtsInitResult(final TtsInitResultInfo p0) throws RemoteException;
    
    void onTtsSpeechStart(final TtsReaderPosition p0, final TtsReaderPosition p1, final TtsReaderPosition p2, final TtsReaderPosition p3, final String p4) throws RemoteException;
    
    void onTtsSpeechDone(final TtsReaderPosition p0, final TtsReaderPosition p1, final TtsReaderPosition p2, final TtsReaderPosition p3) throws RemoteException;
    
    void onSeekBackWardSpeechStart(final TtsReaderPosition p0, final TtsReaderPosition p1, final TtsReaderPosition p2, final TtsReaderPosition p3) throws RemoteException;
    
    void onSeekForWardSpeechStart(final TtsReaderPosition p0, final TtsReaderPosition p1, final TtsReaderPosition p2, final TtsReaderPosition p3) throws RemoteException;
    
    void onTtsStateChanged(final boolean p0) throws RemoteException;
    
    void onParamChanged(final String p0, final boolean p1) throws RemoteException;
    
    void onTtsClose() throws RemoteException;
    
    void onError(final int p0) throws RemoteException;
    
    void onRequestNetwork() throws RemoteException;
    
    void onShowContinueTips() throws RemoteException;
    
    void onCheckPageTurn() throws RemoteException;
    
    void onGotoCurrentPage(final TtsReaderPosition p0, final TtsReaderPosition p1) throws RemoteException;
    
    public abstract static class Stub extends Binder implements TtsServiceIntentCallback
    {
        private static final String DESCRIPTOR = "com.onyx.android.sdk.reader.TtsServiceIntentCallback";
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
            this.attachInterface((IInterface)this, "com.onyx.android.sdk.reader.TtsServiceIntentCallback");
        }
        
        public static TtsServiceIntentCallback asInterface(final IBinder obj) {
            if (obj == null) {
                return null;
            }
            final IInterface queryLocalInterface;
            if ((queryLocalInterface = obj.queryLocalInterface("com.onyx.android.sdk.reader.TtsServiceIntentCallback")) != null && queryLocalInterface instanceof TtsServiceIntentCallback) {
                return (TtsServiceIntentCallback)queryLocalInterface;
            }
            return new a(obj);
        }
        
        public static boolean setDefaultImpl(final TtsServiceIntentCallback impl) {
            if (Stub.a.b != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (impl != null) {
                Stub.a.b = impl;
                return true;
            }
            return false;
        }
        
        public static TtsServiceIntentCallback getDefaultImpl() {
            return Stub.a.b;
        }
        
        public IBinder asBinder() {
            return (IBinder)this;
        }
        
        public boolean onTransact(int code, final Parcel data, final Parcel reply, final int flags) throws RemoteException {
            final String s = "com.onyx.android.sdk.reader.TtsServiceIntentCallback";
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
                    TtsReaderPosition fromParcel;
                    if (data.readInt() != 0) {
                        fromParcel = TtsReaderPosition.CREATOR.createFromParcel(data);
                    }
                    else {
                        fromParcel = null;
                    }
                    TtsReaderPosition fromParcel2;
                    if (data.readInt() != 0) {
                        fromParcel2 = TtsReaderPosition.CREATOR.createFromParcel(data);
                    }
                    else {
                        fromParcel2 = null;
                    }
                    this.onGotoCurrentPage(fromParcel, fromParcel2);
                    reply.writeNoException();
                    return true;
                }
                case 12: {
                    data.enforceInterface(s);
                    this.onCheckPageTurn();
                    reply.writeNoException();
                    return true;
                }
                case 11: {
                    data.enforceInterface(s);
                    this.onShowContinueTips();
                    reply.writeNoException();
                    return true;
                }
                case 10: {
                    data.enforceInterface(s);
                    this.onRequestNetwork();
                    reply.writeNoException();
                    return true;
                }
                case 9: {
                    data.enforceInterface(s);
                    this.onError(data.readInt());
                    reply.writeNoException();
                    return true;
                }
                case 8: {
                    data.enforceInterface(s);
                    this.onTtsClose();
                    reply.writeNoException();
                    return true;
                }
                case 7: {
                    data.enforceInterface(s);
                    this.onParamChanged(data.readString(), data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                }
                case 6: {
                    data.enforceInterface(s);
                    if (data.readInt() != 0) {
                        code = 1;
                    }
                    else {
                        code = 0;
                    }
                    this.onTtsStateChanged((boolean)(code != 0));
                    reply.writeNoException();
                    return true;
                }
                case 5: {
                    data.enforceInterface(s);
                    TtsReaderPosition fromParcel3;
                    if (data.readInt() != 0) {
                        fromParcel3 = TtsReaderPosition.CREATOR.createFromParcel(data);
                    }
                    else {
                        fromParcel3 = null;
                    }
                    TtsReaderPosition fromParcel4;
                    if (data.readInt() != 0) {
                        fromParcel4 = TtsReaderPosition.CREATOR.createFromParcel(data);
                    }
                    else {
                        fromParcel4 = null;
                    }
                    TtsReaderPosition fromParcel5;
                    if (data.readInt() != 0) {
                        fromParcel5 = TtsReaderPosition.CREATOR.createFromParcel(data);
                    }
                    else {
                        fromParcel5 = null;
                    }
                    TtsReaderPosition fromParcel6;
                    if (data.readInt() != 0) {
                        fromParcel6 = TtsReaderPosition.CREATOR.createFromParcel(data);
                    }
                    else {
                        fromParcel6 = null;
                    }
                    this.onSeekForWardSpeechStart(fromParcel3, fromParcel4, fromParcel5, fromParcel6);
                    reply.writeNoException();
                    return true;
                }
                case 4: {
                    data.enforceInterface(s);
                    TtsReaderPosition fromParcel7;
                    if (data.readInt() != 0) {
                        fromParcel7 = TtsReaderPosition.CREATOR.createFromParcel(data);
                    }
                    else {
                        fromParcel7 = null;
                    }
                    TtsReaderPosition fromParcel8;
                    if (data.readInt() != 0) {
                        fromParcel8 = TtsReaderPosition.CREATOR.createFromParcel(data);
                    }
                    else {
                        fromParcel8 = null;
                    }
                    TtsReaderPosition fromParcel9;
                    if (data.readInt() != 0) {
                        fromParcel9 = TtsReaderPosition.CREATOR.createFromParcel(data);
                    }
                    else {
                        fromParcel9 = null;
                    }
                    TtsReaderPosition fromParcel10;
                    if (data.readInt() != 0) {
                        fromParcel10 = TtsReaderPosition.CREATOR.createFromParcel(data);
                    }
                    else {
                        fromParcel10 = null;
                    }
                    this.onSeekBackWardSpeechStart(fromParcel7, fromParcel8, fromParcel9, fromParcel10);
                    reply.writeNoException();
                    return true;
                }
                case 3: {
                    data.enforceInterface(s);
                    TtsReaderPosition fromParcel11;
                    if (data.readInt() != 0) {
                        fromParcel11 = TtsReaderPosition.CREATOR.createFromParcel(data);
                    }
                    else {
                        fromParcel11 = null;
                    }
                    TtsReaderPosition fromParcel12;
                    if (data.readInt() != 0) {
                        fromParcel12 = TtsReaderPosition.CREATOR.createFromParcel(data);
                    }
                    else {
                        fromParcel12 = null;
                    }
                    TtsReaderPosition fromParcel13;
                    if (data.readInt() != 0) {
                        fromParcel13 = TtsReaderPosition.CREATOR.createFromParcel(data);
                    }
                    else {
                        fromParcel13 = null;
                    }
                    TtsReaderPosition fromParcel14;
                    if (data.readInt() != 0) {
                        fromParcel14 = TtsReaderPosition.CREATOR.createFromParcel(data);
                    }
                    else {
                        fromParcel14 = null;
                    }
                    this.onTtsSpeechDone(fromParcel11, fromParcel12, fromParcel13, fromParcel14);
                    reply.writeNoException();
                    return true;
                }
                case 2: {
                    data.enforceInterface(s);
                    TtsReaderPosition fromParcel15;
                    if (data.readInt() != 0) {
                        fromParcel15 = TtsReaderPosition.CREATOR.createFromParcel(data);
                    }
                    else {
                        fromParcel15 = null;
                    }
                    TtsReaderPosition fromParcel16;
                    if (data.readInt() != 0) {
                        fromParcel16 = TtsReaderPosition.CREATOR.createFromParcel(data);
                    }
                    else {
                        fromParcel16 = null;
                    }
                    TtsReaderPosition fromParcel17;
                    if (data.readInt() != 0) {
                        fromParcel17 = TtsReaderPosition.CREATOR.createFromParcel(data);
                    }
                    else {
                        fromParcel17 = null;
                    }
                    TtsReaderPosition fromParcel18;
                    if (data.readInt() != 0) {
                        fromParcel18 = TtsReaderPosition.CREATOR.createFromParcel(data);
                    }
                    else {
                        fromParcel18 = null;
                    }
                    this.onTtsSpeechStart(fromParcel15, fromParcel16, fromParcel17, fromParcel18, data.readString());
                    reply.writeNoException();
                    return true;
                }
                case 1: {
                    data.enforceInterface(s);
                    TtsInitResultInfo fromParcel19;
                    if (data.readInt() != 0) {
                        fromParcel19 = TtsInitResultInfo.CREATOR.createFromParcel(data);
                    }
                    else {
                        fromParcel19 = null;
                    }
                    this.onTtsInitResult(fromParcel19);
                    reply.writeNoException();
                    return true;
                }
            }
        }
        
        private static class a implements TtsServiceIntentCallback
        {
            public static TtsServiceIntentCallback b;
            private IBinder a;
            
            a(final IBinder remote) {
                this.a = remote;
            }
            
            public IBinder asBinder() {
                return this.a;
            }
            
            public String a() {
                return "com.onyx.android.sdk.reader.TtsServiceIntentCallback";
            }
            
            @Override
            public void onTtsInitResult(final TtsInitResultInfo initResult) throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.TtsServiceIntentCallback");
                    if (initResult != null) {
                        final Parcel parcel2 = obtain;
                        parcel2.writeInt(1);
                        initResult.writeToParcel(parcel2, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    if (!this.a.transact(1, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel3 = obtain;
                        final Parcel parcel4 = obtain2;
                        Stub.getDefaultImpl().onTtsInitResult(initResult);
                        parcel4.recycle();
                        parcel3.recycle();
                        return;
                    }
                    final Parcel parcel5 = obtain;
                    final Parcel parcel6 = obtain2;
                    parcel6.readException();
                    parcel6.recycle();
                    parcel5.recycle();
                }
                finally {
                    final Parcel parcel7 = obtain;
                    obtain2.recycle();
                    parcel7.recycle();
                }
            }
            
            @Override
            public void onTtsSpeechStart(final TtsReaderPosition selectionStart, final TtsReaderPosition selectionEnd, final TtsReaderPosition pageStart, final TtsReaderPosition pageEnd, final String content) throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.TtsServiceIntentCallback");
                    if (selectionStart != null) {
                        final Parcel dest = obtain;
                        dest.writeInt(1);
                        selectionStart.writeToParcel(dest, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    if (selectionEnd != null) {
                        final Parcel dest2 = obtain;
                        dest2.writeInt(1);
                        selectionEnd.writeToParcel(dest2, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    if (pageStart != null) {
                        final Parcel dest3 = obtain;
                        dest3.writeInt(1);
                        pageStart.writeToParcel(dest3, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    if (pageEnd != null) {
                        final Parcel dest4 = obtain;
                        dest4.writeInt(1);
                        pageEnd.writeToParcel(dest4, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(content);
                    if (!this.a.transact(2, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel2 = obtain;
                        final Parcel parcel3 = obtain2;
                        Stub.getDefaultImpl().onTtsSpeechStart(selectionStart, selectionEnd, pageStart, pageEnd, content);
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
            public void onTtsSpeechDone(final TtsReaderPosition selectionStart, final TtsReaderPosition selectionEnd, final TtsReaderPosition pageStart, final TtsReaderPosition pageEnd) throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.TtsServiceIntentCallback");
                    if (selectionStart != null) {
                        final Parcel dest = obtain;
                        dest.writeInt(1);
                        selectionStart.writeToParcel(dest, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    if (selectionEnd != null) {
                        final Parcel dest2 = obtain;
                        dest2.writeInt(1);
                        selectionEnd.writeToParcel(dest2, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    if (pageStart != null) {
                        final Parcel dest3 = obtain;
                        dest3.writeInt(1);
                        pageStart.writeToParcel(dest3, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    if (pageEnd != null) {
                        final Parcel dest4 = obtain;
                        dest4.writeInt(1);
                        pageEnd.writeToParcel(dest4, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    if (!this.a.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel2 = obtain;
                        final Parcel parcel3 = obtain2;
                        Stub.getDefaultImpl().onTtsSpeechDone(selectionStart, selectionEnd, pageStart, pageEnd);
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
            public void onSeekBackWardSpeechStart(final TtsReaderPosition selectionStart, final TtsReaderPosition selectionEnd, final TtsReaderPosition pageStart, final TtsReaderPosition pageEnd) throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.TtsServiceIntentCallback");
                    if (selectionStart != null) {
                        final Parcel dest = obtain;
                        dest.writeInt(1);
                        selectionStart.writeToParcel(dest, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    if (selectionEnd != null) {
                        final Parcel dest2 = obtain;
                        dest2.writeInt(1);
                        selectionEnd.writeToParcel(dest2, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    if (pageStart != null) {
                        final Parcel dest3 = obtain;
                        dest3.writeInt(1);
                        pageStart.writeToParcel(dest3, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    if (pageEnd != null) {
                        final Parcel dest4 = obtain;
                        dest4.writeInt(1);
                        pageEnd.writeToParcel(dest4, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    if (!this.a.transact(4, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel2 = obtain;
                        final Parcel parcel3 = obtain2;
                        Stub.getDefaultImpl().onSeekBackWardSpeechStart(selectionStart, selectionEnd, pageStart, pageEnd);
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
            public void onSeekForWardSpeechStart(final TtsReaderPosition selectionStart, final TtsReaderPosition selectionEnd, final TtsReaderPosition pageStart, final TtsReaderPosition pageEnd) throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.TtsServiceIntentCallback");
                    if (selectionStart != null) {
                        final Parcel dest = obtain;
                        dest.writeInt(1);
                        selectionStart.writeToParcel(dest, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    if (selectionEnd != null) {
                        final Parcel dest2 = obtain;
                        dest2.writeInt(1);
                        selectionEnd.writeToParcel(dest2, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    if (pageStart != null) {
                        final Parcel dest3 = obtain;
                        dest3.writeInt(1);
                        pageStart.writeToParcel(dest3, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    if (pageEnd != null) {
                        final Parcel dest4 = obtain;
                        dest4.writeInt(1);
                        pageEnd.writeToParcel(dest4, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    if (!this.a.transact(5, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel2 = obtain;
                        final Parcel parcel3 = obtain2;
                        Stub.getDefaultImpl().onSeekForWardSpeechStart(selectionStart, selectionEnd, pageStart, pageEnd);
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
            public void onTtsStateChanged(final boolean isSpeaking) throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.TtsServiceIntentCallback");
                    int n;
                    if (isSpeaking) {
                        n = 1;
                    }
                    else {
                        n = 0;
                    }
                    obtain.writeInt(n);
                    if (!this.a.transact(6, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel2 = obtain;
                        final Parcel parcel3 = obtain2;
                        Stub.getDefaultImpl().onTtsStateChanged(isSpeaking);
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
            public void onParamChanged(final String type, final boolean isSuccess) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel2;
                final Parcel parcel = parcel2 = obtain;
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.TtsServiceIntentCallback");
                    obtain.writeString(type);
                    int n;
                    if (isSuccess) {
                        n = 1;
                    }
                    else {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.a.transact(7, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel3 = parcel;
                        final Parcel parcel4 = obtain2;
                        Stub.getDefaultImpl().onParamChanged(type, isSuccess);
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
            public void onTtsClose() throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.TtsServiceIntentCallback");
                    if (!this.a.transact(8, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel2 = obtain;
                        final Parcel parcel3 = obtain2;
                        Stub.getDefaultImpl().onTtsClose();
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
            public void onError(final int code) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel2;
                final Parcel parcel = parcel2 = obtain;
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.TtsServiceIntentCallback");
                    obtain.writeInt(code);
                    if (!this.a.transact(9, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel3 = parcel;
                        final Parcel parcel4 = obtain2;
                        Stub.getDefaultImpl().onError(code);
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
            public void onRequestNetwork() throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.TtsServiceIntentCallback");
                    if (!this.a.transact(10, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel2 = obtain;
                        final Parcel parcel3 = obtain2;
                        Stub.getDefaultImpl().onRequestNetwork();
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
            public void onShowContinueTips() throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.TtsServiceIntentCallback");
                    if (!this.a.transact(11, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel2 = obtain;
                        final Parcel parcel3 = obtain2;
                        Stub.getDefaultImpl().onShowContinueTips();
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
            public void onCheckPageTurn() throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.TtsServiceIntentCallback");
                    if (!this.a.transact(12, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel2 = obtain;
                        final Parcel parcel3 = obtain2;
                        Stub.getDefaultImpl().onCheckPageTurn();
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
            public void onGotoCurrentPage(final TtsReaderPosition pagePosition, final TtsReaderPosition sentencePosition) throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.TtsServiceIntentCallback");
                    if (pagePosition != null) {
                        final Parcel dest = obtain;
                        dest.writeInt(1);
                        pagePosition.writeToParcel(dest, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    if (sentencePosition != null) {
                        final Parcel dest2 = obtain;
                        dest2.writeInt(1);
                        sentencePosition.writeToParcel(dest2, 0);
                    }
                    else {
                        obtain.writeInt(0);
                    }
                    if (!this.a.transact(13, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel2 = obtain;
                        final Parcel parcel3 = obtain2;
                        Stub.getDefaultImpl().onGotoCurrentPage(pagePosition, sentencePosition);
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
    
    public static class Default implements TtsServiceIntentCallback
    {
        @Override
        public void onTtsInitResult(final TtsInitResultInfo initResult) throws RemoteException {
        }
        
        @Override
        public void onTtsSpeechStart(final TtsReaderPosition selectionStart, final TtsReaderPosition selectionEnd, final TtsReaderPosition pageStart, final TtsReaderPosition pageEnd, final String content) throws RemoteException {
        }
        
        @Override
        public void onTtsSpeechDone(final TtsReaderPosition selectionStart, final TtsReaderPosition selectionEnd, final TtsReaderPosition pageStart, final TtsReaderPosition pageEnd) throws RemoteException {
        }
        
        @Override
        public void onSeekBackWardSpeechStart(final TtsReaderPosition selectionStart, final TtsReaderPosition selectionEnd, final TtsReaderPosition pageStart, final TtsReaderPosition pageEnd) throws RemoteException {
        }
        
        @Override
        public void onSeekForWardSpeechStart(final TtsReaderPosition selectionStart, final TtsReaderPosition selectionEnd, final TtsReaderPosition pageStart, final TtsReaderPosition pageEnd) throws RemoteException {
        }
        
        @Override
        public void onTtsStateChanged(final boolean isSpeaking) throws RemoteException {
        }
        
        @Override
        public void onParamChanged(final String type, final boolean isSuccess) throws RemoteException {
        }
        
        @Override
        public void onTtsClose() throws RemoteException {
        }
        
        @Override
        public void onError(final int code) throws RemoteException {
        }
        
        @Override
        public void onRequestNetwork() throws RemoteException {
        }
        
        @Override
        public void onShowContinueTips() throws RemoteException {
        }
        
        @Override
        public void onCheckPageTurn() throws RemoteException {
        }
        
        @Override
        public void onGotoCurrentPage(final TtsReaderPosition pagePosition, final TtsReaderPosition sentencePosition) throws RemoteException {
        }
        
        public IBinder asBinder() {
            return null;
        }
    }
}
