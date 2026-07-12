package com.onyx.android.sdk.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import com.onyx.android.sdk.rx.RxBaseRequest;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/ServiceUtils.class */
public class ServiceUtils {
    public static final int SERVICE_CONNECT_TIME_OUT_MS = 6000;
    public static final int SERVICE_WAIT_SLEEP_TIME = 100;

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/ServiceUtils$OnyxServiceConnection.class */
    public static class OnyxServiceConnection implements ServiceConnection {
        public static final int WAIT_SLEEP_TIME = 100;
        private volatile boolean a = false;
        private volatile IBinder b;

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            this.a = false;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            this.a = true;
            this.b = service;
        }

        public IBinder getRemoteService() {
            return this.b;
        }

        public void waitUntilConnected(RxBaseRequest request) throws InterruptedException {
            while (!this.a && !request.isAbort()) {
                Thread.sleep(100L);
            }
        }
    }

    public static ResolveInfo getResolveInfoByFileType(File file, Context context, String action) {
        Intent intentIntentFromMimeType = ViewDocumentUtils.intentFromMimeType(file);
        intentIntentFromMimeType.setAction(action);
        return getResolveInfoByIntent(context, intentIntentFromMimeType);
    }

    public static ResolveInfo getResolveInfoByIntent(Context context, Intent intent) {
        List<ResolveInfo> listQueryIntentServices = context.getPackageManager().queryIntentServices(intent, 65536);
        if (!CollectionUtils.isNullOrEmpty(listQueryIntentServices)) {
            return listQueryIntentServices.get(0);
        }
        if (!Debug.getDebug()) {
            return null;
        }
        Debug.w(ServiceUtils.class, "can't retrieve supported aidl services: " + intent.getAction(), new Object[0]);
        return null;
    }

    public static void executeRemoteService(RxBaseRequest request, ResolveInfo resolve, Consumer<OnyxServiceConnection> consumer) {
        Intent intent = new Intent();
        ServiceInfo serviceInfo = resolve.serviceInfo;
        intent.setComponent(new ComponentName(serviceInfo.packageName, serviceInfo.name));
        executeRemoteService(request, intent, consumer);
    }

    public static void executeRemoteService(RxBaseRequest request, Intent service, Consumer<OnyxServiceConnection> consumer) {
        try {
            Benchmark benchmark = new Benchmark();
            OnyxServiceConnection onyxServiceConnection = new OnyxServiceConnection();
            RxBaseRequest.getAppContext().bindService(service, onyxServiceConnection, 5);
            onyxServiceConnection.waitUntilConnected(request);
            consumer.accept(onyxServiceConnection);
            RxBaseRequest.getAppContext().unbindService(onyxServiceConnection);
            benchmark.report("execute remote service");
        } catch (Exception e) {
            if (Debug.getDebug()) {
                Debug.w(ServiceUtils.class, "RemoteService connect failed", new Object[]{e});
            }
        }
    }
}
