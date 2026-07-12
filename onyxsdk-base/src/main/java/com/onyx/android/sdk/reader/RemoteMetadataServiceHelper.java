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
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:266)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:361)
      //   at java.base/java.util.ArrayList.remove(ArrayList.java:504)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.removeExceptionInstructionsEx(FinallyProcessor.java:1058)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.verifyFinallyEx(FinallyProcessor.java:573)
      //   at org.jetbrains.java.decompiler.modules.decompiler.FinallyProcessor.iterateGraph(FinallyProcessor.java:90)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:185)
      //
      // Bytecode:
      // 00: aload 0
      // 01: dup
      // 02: dup
      // 03: dup2
      // 04: dup2
      // 05: dup2
      // 06: new com/onyx/android/sdk/reader/RemoteServiceConnection
      // 09: dup
      // 0a: invokespecial com/onyx/android/sdk/reader/RemoteServiceConnection.<init> ()V
      // 0d: putfield com/onyx/android/sdk/reader/RemoteMetadataServiceHelper.c Lcom/onyx/android/sdk/reader/RemoteServiceConnection;
      // 10: new android/content/Intent
      // 13: dup
      // 14: dup
      // 15: astore 1
      // 16: invokespecial android/content/Intent.<init> ()V
      // 19: new android/content/ComponentName
      // 1c: dup
      // 1d: ldc "com.onyx.kreader"
      // 1f: ldc "com.onyx.android.sdk.readerview.service.ReaderMetadataService"
      // 21: invokespecial android/content/ComponentName.<init> (Ljava/lang/String;Ljava/lang/String;)V
      // 24: invokevirtual android/content/Intent.setComponent (Landroid/content/ComponentName;)Landroid/content/Intent;
      // 27: pop
      // 28: invokevirtual com/onyx/android/sdk/reader/RemoteMetadataServiceHelper.getContext ()Landroid/content/Context;
      // 2b: aload 1
      // 2c: aload 0
      // 2d: getfield com/onyx/android/sdk/reader/RemoteMetadataServiceHelper.c Lcom/onyx/android/sdk/reader/RemoteServiceConnection;
      // 30: bipush 5
      // 31: invokevirtual android/content/Context.bindService (Landroid/content/Intent;Landroid/content/ServiceConnection;I)Z
      // 34: pop
      // 35: getfield com/onyx/android/sdk/reader/RemoteMetadataServiceHelper.c Lcom/onyx/android/sdk/reader/RemoteServiceConnection;
      // 38: aload 0
      // 39: getfield com/onyx/android/sdk/reader/RemoteMetadataServiceHelper.a Lcom/onyx/android/sdk/rx/RxRequest;
      // 3c: invokevirtual com/onyx/android/sdk/reader/RemoteServiceConnection.waitUntilConnected (Lcom/onyx/android/sdk/rx/RxRequest;)V
      // 3f: getfield com/onyx/android/sdk/reader/RemoteMetadataServiceHelper.c Lcom/onyx/android/sdk/reader/RemoteServiceConnection;
      // 42: invokevirtual com/onyx/android/sdk/reader/RemoteServiceConnection.getRemoteService ()Landroid/os/IBinder;
      // 45: invokestatic com/onyx/android/sdk/reader/IMetadataService$Stub.asInterface (Landroid/os/IBinder;)Lcom/onyx/android/sdk/reader/IMetadataService;
      // 48: dup
      // 49: astore 1
      // 4a: putfield com/onyx/android/sdk/reader/RemoteMetadataServiceHelper.d Lcom/onyx/android/sdk/reader/IMetadataService;
      // 4d: aload 1
      // 4e: invokespecial com/onyx/android/sdk/reader/RemoteMetadataServiceHelper.a (Lcom/onyx/android/sdk/reader/IMetadataService;)V
      // 51: invokevirtual com/onyx/android/sdk/reader/RemoteMetadataServiceHelper.getContext ()Landroid/content/Context;
      // 54: aload 0
      // 55: getfield com/onyx/android/sdk/reader/RemoteMetadataServiceHelper.c Lcom/onyx/android/sdk/reader/RemoteServiceConnection;
      // 58: invokevirtual android/content/Context.unbindService (Landroid/content/ServiceConnection;)V
      // 5b: aconst_null
      // 5c: putfield com/onyx/android/sdk/reader/RemoteMetadataServiceHelper.c Lcom/onyx/android/sdk/reader/RemoteServiceConnection;
      // 5f: aconst_null
      // 60: putfield com/onyx/android/sdk/reader/RemoteMetadataServiceHelper.d Lcom/onyx/android/sdk/reader/IMetadataService;
      // 63: goto 79
      // 66: astore 1
      // 67: aload 0
      // 68: dup
      // 69: dup
      // 6a: invokevirtual java/lang/Object.getClass ()Ljava/lang/Class;
      // 6d: aload 1
      // 6e: invokestatic com/onyx/android/sdk/utils/Debug.w (Ljava/lang/Class;Ljava/lang/Throwable;)V
      // 71: aconst_null
      // 72: putfield com/onyx/android/sdk/reader/RemoteMetadataServiceHelper.c Lcom/onyx/android/sdk/reader/RemoteServiceConnection;
      // 75: aconst_null
      // 76: putfield com/onyx/android/sdk/reader/RemoteMetadataServiceHelper.d Lcom/onyx/android/sdk/reader/IMetadataService;
      // 79: return
      // 7a: aload 0
      // 7b: dup
      // 7c: aconst_null
      // 7d: putfield com/onyx/android/sdk/reader/RemoteMetadataServiceHelper.c Lcom/onyx/android/sdk/reader/RemoteServiceConnection;
      // 80: aconst_null
      // 81: putfield com/onyx/android/sdk/reader/RemoteMetadataServiceHelper.d Lcom/onyx/android/sdk/reader/IMetadataService;
      // 84: athrow
      // try (0 -> 11): 50 null
      // try (14 -> 21): 50 null
      // try (22 -> 28): 50 null
      // try (29 -> 36): 50 null
      // try (38 -> 45): 50 null
      // try (51 -> 57): 62 null
   }

   public interface OnCallRemoteServiceApiListener {
      void onCallRemoteServiceApi(IMetadataService var1) throws Throwable;
   }
}
