package com.onyx.android.sdk.reader;

import android.content.Context;
import com.onyx.android.sdk.rx.RxRequest;

public class RemoteMetadataServiceHelper {
   public static final String PACKAGE_NAME = "com.onyx.kreader";
   public static final String READER_METADATA_SERVICE_CLASS = "com.onyx.android.sdk.readerview.service.ReaderMetadataService";
   private RxRequest a;
   private RemoteMetadataServiceHelper.OnCallRemoteServiceApiListener b;
   private RemoteServiceConnection c;
   private IMetadataService d;

   public RemoteMetadataServiceHelper(RxRequest rxRequest, RemoteMetadataServiceHelper.OnCallRemoteServiceApiListener listener) {
      this.a = rxRequest;
      this.b = listener;
   }

   private void a(IMetadataService remoteService) throws Throwable {
      this.b.onCallRemoteServiceApi(remoteService);
   }

   public Context getContext() {
      return this.a.getContext();
   }

   public RemoteServiceConnection getConnection() {
      return this.c;
   }

   public IMetadataService getRemoteService() {
      return this.d;
   }

   public RemoteMetadataServiceHelper setConnection(RemoteServiceConnection connection) {
      this.c = connection;
      return this;
   }

   public RemoteMetadataServiceHelper setRemoteService(IMetadataService remoteService) {
      this.d = remoteService;
      return this;
   }

   public void executeRemoteService() {
   }

   public interface OnCallRemoteServiceApiListener {
      void onCallRemoteServiceApi(IMetadataService var1) throws Throwable;
   }
}
