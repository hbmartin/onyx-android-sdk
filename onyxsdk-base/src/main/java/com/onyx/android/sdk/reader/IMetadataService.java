// 
// 

package com.onyx.android.sdk.reader;

import java.util.ArrayList;
import android.os.Parcel;
import android.os.IBinder;
import android.os.Binder;
import android.os.ParcelFileDescriptor;
import java.util.List;
import android.os.RemoteException;
import android.os.IInterface;

public interface IMetadataService extends IInterface
{
    boolean extractMetadataAndThumbnail(final String p0, final int p1) throws RemoteException;
    
    boolean extractMetadata(final List<String> p0, final int p1) throws RemoteException;
    
    void interrupt() throws RemoteException;
    
    boolean searchText(final String p0, final String p1) throws RemoteException;
    
    void cancelSearch() throws RemoteException;
    
    ParcelFileDescriptor loadThumbnail(final String p0, final int p1, final int p2, final int p3) throws RemoteException;
    
    void clearThumbnail(final List<String> p0) throws RemoteException;
    
    int clearPassword(final String p0, final String p1) throws RemoteException;
    
    int clearData(final String p0, final String p1, final String p2) throws RemoteException;
    
    int clearCache(final List<String> p0) throws RemoteException;
    
    int clearMetadataRecord(final String p0) throws RemoteException;
    
    int clearAllMetadataRecords() throws RemoteException;
    
    void closeDocument(final int p0) throws RemoteException;
    
    int closeDocumentList(final List<String> p0) throws RemoteException;
    
    List<String> getTabPathSet() throws RemoteException;
    
    void updateDocPurchasedInfo(final String p0) throws RemoteException;
    
    void openDatabase(final String p0) throws RemoteException;
    
    boolean isMultipleTabsEnabled() throws RemoteException;
    
    boolean updateDocTitle(final String p0, final String p1) throws RemoteException;
    
    void bindJdShopPin(final String p0, final String p1) throws RemoteException;
    
    public abstract static class Stub extends Binder implements IMetadataService
    {
        private static final String DESCRIPTOR = "com.onyx.android.sdk.reader.IMetadataService";
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
        static final int o = 14;
        static final int p = 15;
        static final int q = 16;
        static final int r = 17;
        static final int s = 18;
        static final int t = 19;
        static final int u = 20;
        
        public Stub() {
            this.attachInterface((IInterface)this, "com.onyx.android.sdk.reader.IMetadataService");
        }
        
        public static IMetadataService asInterface(final IBinder obj) {
            if (obj == null) {
                return null;
            }
            final IInterface queryLocalInterface;
            if ((queryLocalInterface = obj.queryLocalInterface("com.onyx.android.sdk.reader.IMetadataService")) != null && queryLocalInterface instanceof IMetadataService) {
                return (IMetadataService)queryLocalInterface;
            }
            return new a(obj);
        }
        
        public static boolean setDefaultImpl(final IMetadataService impl) {
            if (Stub.a.b != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (impl != null) {
                Stub.a.b = impl;
                return true;
            }
            return false;
        }
        
        public static IMetadataService getDefaultImpl() {
            return Stub.a.b;
        }
        
        public IBinder asBinder() {
            return (IBinder)this;
        }
        
        public boolean onTransact(int code, final Parcel data, final Parcel reply, int flags) throws RemoteException {
            final String s = "com.onyx.android.sdk.reader.IMetadataService";
            if (code == 1598968902) {
                reply.writeString(s);
                return true;
            }
            switch (code) {
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
                case 20: {
                    data.enforceInterface(s);
                    this.bindJdShopPin(data.readString(), data.readString());
                    reply.writeNoException();
                    return true;
                }
                case 19: {
                    data.enforceInterface(s);
                    final int updateDocTitle = this.updateDocTitle(data.readString(), data.readString()) ? 1 : 0;
                    reply.writeNoException();
                    reply.writeInt(updateDocTitle);
                    return true;
                }
                case 18: {
                    data.enforceInterface(s);
                    final int multipleTabsEnabled = this.isMultipleTabsEnabled() ? 1 : 0;
                    reply.writeNoException();
                    reply.writeInt(multipleTabsEnabled);
                    return true;
                }
                case 17: {
                    data.enforceInterface(s);
                    this.openDatabase(data.readString());
                    return true;
                }
                case 16: {
                    data.enforceInterface(s);
                    this.updateDocPurchasedInfo(data.readString());
                    reply.writeNoException();
                    return true;
                }
                case 15: {
                    data.enforceInterface(s);
                    final List<String> tabPathSet = this.getTabPathSet();
                    reply.writeNoException();
                    reply.writeStringList((List)tabPathSet);
                    return true;
                }
                case 14: {
                    data.enforceInterface(s);
                    final int closeDocumentList = this.closeDocumentList(data.createStringArrayList());
                    reply.writeNoException();
                    reply.writeInt(closeDocumentList);
                    return true;
                }
                case 13: {
                    data.enforceInterface(s);
                    this.closeDocument(data.readInt());
                    reply.writeNoException();
                    return true;
                }
                case 12: {
                    data.enforceInterface(s);
                    final int clearAllMetadataRecords = this.clearAllMetadataRecords();
                    reply.writeNoException();
                    reply.writeInt(clearAllMetadataRecords);
                    return true;
                }
                case 11: {
                    data.enforceInterface(s);
                    final int clearMetadataRecord = this.clearMetadataRecord(data.readString());
                    reply.writeNoException();
                    reply.writeInt(clearMetadataRecord);
                    return true;
                }
                case 10: {
                    data.enforceInterface(s);
                    final int clearCache = this.clearCache(data.createStringArrayList());
                    reply.writeNoException();
                    reply.writeInt(clearCache);
                    return true;
                }
                case 9: {
                    data.enforceInterface(s);
                    final int clearData = this.clearData(data.readString(), data.readString(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(clearData);
                    return true;
                }
                case 8: {
                    data.enforceInterface(s);
                    final int clearPassword = this.clearPassword(data.readString(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(clearPassword);
                    return true;
                }
                case 7: {
                    data.enforceInterface(s);
                    this.clearThumbnail(data.createStringArrayList());
                    reply.writeNoException();
                    return true;
                }
                case 6: {
                    data.enforceInterface(s);
                    final String string = data.readString();
                    code = data.readInt();
                    final int int1 = data.readInt();
                    flags = data.readInt();
                    final ParcelFileDescriptor loadThumbnail = this.loadThumbnail(string, code, int1, flags);
                    reply.writeNoException();
                    if (loadThumbnail != null) {
                        final ParcelFileDescriptor parcelFileDescriptor = loadThumbnail;
                        reply.writeInt(1);
                        parcelFileDescriptor.writeToParcel(reply, 1);
                    }
                    else {
                        reply.writeInt(0);
                    }
                    return true;
                }
                case 5: {
                    data.enforceInterface(s);
                    this.cancelSearch();
                    reply.writeNoException();
                    return true;
                }
                case 4: {
                    data.enforceInterface(s);
                    final int searchText = this.searchText(data.readString(), data.readString()) ? 1 : 0;
                    reply.writeNoException();
                    reply.writeInt(searchText);
                    return true;
                }
                case 3: {
                    data.enforceInterface(s);
                    this.interrupt();
                    reply.writeNoException();
                    return true;
                }
                case 2: {
                    data.enforceInterface(s);
                    final int metadata = this.extractMetadata(data.createStringArrayList(), data.readInt()) ? 1 : 0;
                    reply.writeNoException();
                    reply.writeInt(metadata);
                    return true;
                }
                case 1: {
                    data.enforceInterface(s);
                    final int metadataAndThumbnail = this.extractMetadataAndThumbnail(data.readString(), data.readInt()) ? 1 : 0;
                    reply.writeNoException();
                    reply.writeInt(metadataAndThumbnail);
                    return true;
                }
            }
        }
        
        private static class a implements IMetadataService
        {
            public static IMetadataService b;
            private IBinder a;
            
            a(final IBinder remote) {
                this.a = remote;
            }
            
            public IBinder asBinder() {
                return this.a;
            }
            
            public String a() {
                return "com.onyx.android.sdk.reader.IMetadataService";
            }
            
            @Override
            public boolean extractMetadataAndThumbnail(final String filePath, final int timeout) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel3;
                final Parcel parcel2;
                final Parcel parcel = parcel2 = (parcel3 = obtain);
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IMetadataService");
                    parcel3.writeString(filePath);
                    obtain.writeInt(timeout);
                    if (!this.a.transact(1, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final boolean metadataAndThumbnail = Stub.getDefaultImpl().extractMetadataAndThumbnail(filePath, timeout);
                        final Parcel parcel4 = parcel;
                        obtain2.recycle();
                        parcel4.recycle();
                        return metadataAndThumbnail;
                    }
                    final Parcel parcel5 = obtain2;
                    parcel5.readException();
                    final boolean b = parcel5.readInt() != 0;
                    final Parcel parcel6 = parcel;
                    obtain2.recycle();
                    parcel6.recycle();
                    return b;
                }
                finally {
                    final Parcel parcel7 = parcel;
                    obtain2.recycle();
                    parcel7.recycle();
                }
            }
            
            @Override
            public boolean extractMetadata(final List<String> fileList, final int timeout) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel3;
                final Parcel parcel2;
                final Parcel parcel = parcel2 = (parcel3 = obtain);
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IMetadataService");
                    parcel3.writeStringList((List)fileList);
                    obtain.writeInt(timeout);
                    if (!this.a.transact(2, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final boolean metadata = Stub.getDefaultImpl().extractMetadata(fileList, timeout);
                        final Parcel parcel4 = parcel;
                        obtain2.recycle();
                        parcel4.recycle();
                        return metadata;
                    }
                    final Parcel parcel5 = obtain2;
                    parcel5.readException();
                    final boolean b = parcel5.readInt() != 0;
                    final Parcel parcel6 = parcel;
                    obtain2.recycle();
                    parcel6.recycle();
                    return b;
                }
                finally {
                    final Parcel parcel7 = parcel;
                    obtain2.recycle();
                    parcel7.recycle();
                }
            }
            
            @Override
            public void interrupt() throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.IMetadataService");
                    if (!this.a.transact(3, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
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
            
            @Override
            public boolean searchText(final String filePath, final String query) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel3;
                final Parcel parcel2;
                final Parcel parcel = parcel2 = (parcel3 = obtain);
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IMetadataService");
                    parcel3.writeString(filePath);
                    obtain.writeString(query);
                    if (!this.a.transact(4, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final boolean searchText = Stub.getDefaultImpl().searchText(filePath, query);
                        final Parcel parcel4 = parcel;
                        obtain2.recycle();
                        parcel4.recycle();
                        return searchText;
                    }
                    final Parcel parcel5 = obtain2;
                    parcel5.readException();
                    final boolean b = parcel5.readInt() != 0;
                    final Parcel parcel6 = parcel;
                    obtain2.recycle();
                    parcel6.recycle();
                    return b;
                }
                finally {
                    final Parcel parcel7 = parcel;
                    obtain2.recycle();
                    parcel7.recycle();
                }
            }
            
            @Override
            public void cancelSearch() throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.IMetadataService");
                    if (!this.a.transact(5, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel2 = obtain;
                        final Parcel parcel3 = obtain2;
                        Stub.getDefaultImpl().cancelSearch();
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
            public ParcelFileDescriptor loadThumbnail(final String filePath, final int pageNumber, final int width, final int height) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel5;
                final Parcel parcel4;
                final Parcel parcel3;
                final Parcel parcel2;
                final Parcel parcel = parcel2 = (parcel3 = (parcel4 = (parcel5 = obtain)));
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IMetadataService");
                    parcel3.writeString(filePath);
                    parcel4.writeInt(pageNumber);
                    parcel5.writeInt(width);
                    obtain.writeInt(height);
                    if (!this.a.transact(6, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final ParcelFileDescriptor loadThumbnail = Stub.getDefaultImpl().loadThumbnail(filePath, pageNumber, width, height);
                        final Parcel parcel6 = parcel;
                        obtain2.recycle();
                        parcel6.recycle();
                        return loadThumbnail;
                    }
                    final Parcel parcel7 = obtain2;
                    parcel7.readException();
                    ParcelFileDescriptor parcelFileDescriptor;
                    if (parcel7.readInt() != 0) {
                        parcelFileDescriptor = (ParcelFileDescriptor)ParcelFileDescriptor.CREATOR.createFromParcel(obtain2);
                    }
                    else {
                        parcelFileDescriptor = null;
                    }
                    final ParcelFileDescriptor parcelFileDescriptor2 = parcelFileDescriptor;
                    final Parcel parcel8 = parcel;
                    obtain2.recycle();
                    parcel8.recycle();
                    return parcelFileDescriptor2;
                }
                finally {
                    final Parcel parcel9 = parcel;
                    obtain2.recycle();
                    parcel9.recycle();
                }
            }
            
            @Override
            public void clearThumbnail(final List<String> thumbnailList) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel2;
                final Parcel parcel = parcel2 = obtain;
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IMetadataService");
                    obtain.writeStringList((List)thumbnailList);
                    if (!this.a.transact(7, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel3 = parcel;
                        final Parcel parcel4 = obtain2;
                        Stub.getDefaultImpl().clearThumbnail(thumbnailList);
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
            public int clearPassword(final String filePath, final String password) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel3;
                final Parcel parcel2;
                final Parcel parcel = parcel2 = (parcel3 = obtain);
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IMetadataService");
                    parcel3.writeString(filePath);
                    obtain.writeString(password);
                    if (!this.a.transact(8, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final int clearPassword = Stub.getDefaultImpl().clearPassword(filePath, password);
                        final Parcel parcel4 = parcel;
                        obtain2.recycle();
                        parcel4.recycle();
                        return clearPassword;
                    }
                    final Parcel parcel5 = obtain2;
                    parcel5.readException();
                    final int int1 = parcel5.readInt();
                    final Parcel parcel6 = parcel;
                    obtain2.recycle();
                    parcel6.recycle();
                    return int1;
                }
                finally {
                    final Parcel parcel7 = parcel;
                    obtain2.recycle();
                    parcel7.recycle();
                }
            }
            
            @Override
            public int clearData(final String filePath, final String password, final String clusterInfo) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel4;
                final Parcel parcel3;
                final Parcel parcel2;
                final Parcel parcel = parcel2 = (parcel3 = (parcel4 = obtain));
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IMetadataService");
                    parcel3.writeString(filePath);
                    parcel4.writeString(password);
                    obtain.writeString(clusterInfo);
                    if (!this.a.transact(9, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final int clearData = Stub.getDefaultImpl().clearData(filePath, password, clusterInfo);
                        final Parcel parcel5 = parcel;
                        obtain2.recycle();
                        parcel5.recycle();
                        return clearData;
                    }
                    final Parcel parcel6 = obtain2;
                    parcel6.readException();
                    final int int1 = parcel6.readInt();
                    final Parcel parcel7 = parcel;
                    obtain2.recycle();
                    parcel7.recycle();
                    return int1;
                }
                finally {
                    final Parcel parcel8 = parcel;
                    obtain2.recycle();
                    parcel8.recycle();
                }
            }
            
            @Override
            public int clearCache(final List<String> filePaths) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel2;
                final Parcel parcel = parcel2 = obtain;
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IMetadataService");
                    obtain.writeStringList((List)filePaths);
                    if (!this.a.transact(10, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final int clearCache = Stub.getDefaultImpl().clearCache(filePaths);
                        final Parcel parcel3 = parcel;
                        obtain2.recycle();
                        parcel3.recycle();
                        return clearCache;
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
            public int clearMetadataRecord(final String docId) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel2;
                final Parcel parcel = parcel2 = obtain;
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IMetadataService");
                    obtain.writeString(docId);
                    if (!this.a.transact(11, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final int clearMetadataRecord = Stub.getDefaultImpl().clearMetadataRecord(docId);
                        final Parcel parcel3 = parcel;
                        obtain2.recycle();
                        parcel3.recycle();
                        return clearMetadataRecord;
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
            public int clearAllMetadataRecords() throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.IMetadataService");
                    if (!this.a.transact(12, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final int clearAllMetadataRecords = Stub.getDefaultImpl().clearAllMetadataRecords();
                        final Parcel parcel2 = obtain;
                        obtain2.recycle();
                        parcel2.recycle();
                        return clearAllMetadataRecords;
                    }
                    final Parcel parcel3 = obtain2;
                    parcel3.readException();
                    final int int1 = parcel3.readInt();
                    final Parcel parcel4 = obtain;
                    obtain2.recycle();
                    parcel4.recycle();
                    return int1;
                }
                finally {
                    final Parcel parcel5 = obtain;
                    obtain2.recycle();
                    parcel5.recycle();
                }
            }
            
            @Override
            public void closeDocument(final int bookType) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel2;
                final Parcel parcel = parcel2 = obtain;
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IMetadataService");
                    obtain.writeInt(bookType);
                    if (!this.a.transact(13, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel3 = parcel;
                        final Parcel parcel4 = obtain2;
                        Stub.getDefaultImpl().closeDocument(bookType);
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
            public int closeDocumentList(final List<String> filePathList) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel2;
                final Parcel parcel = parcel2 = obtain;
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IMetadataService");
                    obtain.writeStringList((List)filePathList);
                    if (!this.a.transact(14, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final int closeDocumentList = Stub.getDefaultImpl().closeDocumentList(filePathList);
                        final Parcel parcel3 = parcel;
                        obtain2.recycle();
                        parcel3.recycle();
                        return closeDocumentList;
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
            public List<String> getTabPathSet() throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.IMetadataService");
                    if (!this.a.transact(15, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final List<String> tabPathSet = Stub.getDefaultImpl().getTabPathSet();
                        final Parcel parcel2 = obtain;
                        obtain2.recycle();
                        parcel2.recycle();
                        return tabPathSet;
                    }
                    final Parcel parcel3 = obtain2;
                    parcel3.readException();
                    final ArrayList stringArrayList = parcel3.createStringArrayList();
                    final Parcel parcel4 = obtain;
                    obtain2.recycle();
                    parcel4.recycle();
                    return stringArrayList;
                }
                finally {
                    final Parcel parcel5 = obtain;
                    obtain2.recycle();
                    parcel5.recycle();
                }
            }
            
            @Override
            public void updateDocPurchasedInfo(final String filePath) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel2;
                final Parcel parcel = parcel2 = obtain;
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IMetadataService");
                    obtain.writeString(filePath);
                    if (!this.a.transact(16, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel3 = parcel;
                        final Parcel parcel4 = obtain2;
                        Stub.getDefaultImpl().updateDocPurchasedInfo(filePath);
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
            public void openDatabase(final String databaseName) throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.onyx.android.sdk.reader.IMetadataService");
                    parcel.writeString(databaseName);
                    if (!this.a.transact(17, obtain, (Parcel)null, 1) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel2 = obtain;
                        Stub.getDefaultImpl().openDatabase(databaseName);
                        parcel2.recycle();
                        return;
                    }
                    obtain.recycle();
                }
                finally {
                    obtain.recycle();
                }
            }
            
            @Override
            public boolean isMultipleTabsEnabled() throws RemoteException {
                final Parcel obtain;
                final Parcel parcel = obtain = Parcel.obtain();
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("com.onyx.android.sdk.reader.IMetadataService");
                    if (!this.a.transact(18, obtain, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final boolean multipleTabsEnabled = Stub.getDefaultImpl().isMultipleTabsEnabled();
                        final Parcel parcel2 = obtain;
                        obtain2.recycle();
                        parcel2.recycle();
                        return multipleTabsEnabled;
                    }
                    final Parcel parcel3 = obtain2;
                    parcel3.readException();
                    final boolean b = parcel3.readInt() != 0;
                    final Parcel parcel4 = obtain;
                    obtain2.recycle();
                    parcel4.recycle();
                    return b;
                }
                finally {
                    final Parcel parcel5 = obtain;
                    obtain2.recycle();
                    parcel5.recycle();
                }
            }
            
            @Override
            public boolean updateDocTitle(final String filePath, final String title) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel3;
                final Parcel parcel2;
                final Parcel parcel = parcel2 = (parcel3 = obtain);
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IMetadataService");
                    parcel3.writeString(filePath);
                    obtain.writeString(title);
                    if (!this.a.transact(19, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final boolean updateDocTitle = Stub.getDefaultImpl().updateDocTitle(filePath, title);
                        final Parcel parcel4 = parcel;
                        obtain2.recycle();
                        parcel4.recycle();
                        return updateDocTitle;
                    }
                    final Parcel parcel5 = obtain2;
                    parcel5.readException();
                    final boolean b = parcel5.readInt() != 0;
                    final Parcel parcel6 = parcel;
                    obtain2.recycle();
                    parcel6.recycle();
                    return b;
                }
                finally {
                    final Parcel parcel7 = parcel;
                    obtain2.recycle();
                    parcel7.recycle();
                }
            }
            
            @Override
            public void bindJdShopPin(final String newPin, final String oldPin) throws RemoteException {
                final Parcel obtain = Parcel.obtain();
                final Parcel parcel3;
                final Parcel parcel2;
                final Parcel parcel = parcel2 = (parcel3 = obtain);
                final Parcel obtain2 = Parcel.obtain();
                try {
                    parcel2.writeInterfaceToken("com.onyx.android.sdk.reader.IMetadataService");
                    parcel3.writeString(newPin);
                    obtain.writeString(oldPin);
                    if (!this.a.transact(20, parcel, obtain2, 0) && Stub.getDefaultImpl() != null) {
                        final Parcel parcel4 = parcel;
                        final Parcel parcel5 = obtain2;
                        Stub.getDefaultImpl().bindJdShopPin(newPin, oldPin);
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
    
    public static class Default implements IMetadataService
    {
        @Override
        public boolean extractMetadataAndThumbnail(final String filePath, final int timeout) throws RemoteException {
            return false;
        }
        
        @Override
        public boolean extractMetadata(final List<String> fileList, final int timeout) throws RemoteException {
            return false;
        }
        
        @Override
        public void interrupt() throws RemoteException {
        }
        
        @Override
        public boolean searchText(final String filePath, final String query) throws RemoteException {
            return false;
        }
        
        @Override
        public void cancelSearch() throws RemoteException {
        }
        
        @Override
        public ParcelFileDescriptor loadThumbnail(final String filePath, final int pageNumber, final int width, final int height) throws RemoteException {
            return null;
        }
        
        @Override
        public void clearThumbnail(final List<String> thumbnailList) throws RemoteException {
        }
        
        @Override
        public int clearPassword(final String filePath, final String password) throws RemoteException {
            return 0;
        }
        
        @Override
        public int clearData(final String filePath, final String password, final String clusterInfo) throws RemoteException {
            return 0;
        }
        
        @Override
        public int clearCache(final List<String> filePaths) throws RemoteException {
            return 0;
        }
        
        @Override
        public int clearMetadataRecord(final String docId) throws RemoteException {
            return 0;
        }
        
        @Override
        public int clearAllMetadataRecords() throws RemoteException {
            return 0;
        }
        
        @Override
        public void closeDocument(final int bookType) throws RemoteException {
        }
        
        @Override
        public int closeDocumentList(final List<String> filePathList) throws RemoteException {
            return 0;
        }
        
        @Override
        public List<String> getTabPathSet() throws RemoteException {
            return null;
        }
        
        @Override
        public void updateDocPurchasedInfo(final String filePath) throws RemoteException {
        }
        
        @Override
        public void openDatabase(final String databaseName) throws RemoteException {
        }
        
        @Override
        public boolean isMultipleTabsEnabled() throws RemoteException {
            return false;
        }
        
        @Override
        public boolean updateDocTitle(final String filePath, final String title) throws RemoteException {
            return false;
        }
        
        @Override
        public void bindJdShopPin(final String newPin, final String oldPin) throws RemoteException {
        }
        
        public IBinder asBinder() {
            return null;
        }
    }
}
