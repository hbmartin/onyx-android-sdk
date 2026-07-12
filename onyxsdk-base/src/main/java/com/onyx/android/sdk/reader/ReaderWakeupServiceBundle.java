package com.onyx.android.sdk.reader;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.onyx.android.sdk.common.service.MessengerRemoteServiceConnection;
import com.onyx.android.sdk.rx.RxManager;
import com.onyx.android.sdk.rx.RxRequest;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.ReaderUtils;
import com.onyx.android.sdk.utils.ResManager;
import com.onyx.android.sdk.utils.TestUtils;
import io.reactivex.Observable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/reader/ReaderWakeupServiceBundle.class */
public class ReaderWakeupServiceBundle {
    private static final long c = 30000;
    private static final int d = 2000;
    private static final ReaderWakeupServiceBundle e = new ReaderWakeupServiceBundle();
    private static List<String> f = Arrays.asList(ReaderUtils.READER_TAB_1_WAKE_UP_SERVICE_CLASS, ReaderUtils.READER_TAB_2_WAKE_UP_SERVICE_CLASS, ReaderUtils.READER_TAB_3_WAKE_UP_SERVICE_CLASS, ReaderUtils.READER_TAB_4_WAKE_UP_SERVICE_CLASS);
    private RxManager a;
    private Map<String, MessengerRemoteServiceConnection> b = new HashMap();

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/reader/ReaderWakeupServiceBundle$a.class */
    class a extends RxRequest {
        a() {
        }

        @Override // com.onyx.android.sdk.rx.RxRequest
        public void execute() throws Exception {
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/reader/ReaderWakeupServiceBundle$b.class */
    class b extends MessengerRemoteServiceConnection {
        b(Context context, Intent serviceIntent) {
            super(context, serviceIntent);
        }
    }

    public static ReaderWakeupServiceBundle getInstance() {
        return e;
    }

    private ReaderWakeupServiceBundle() {
    }

    private boolean b() {
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        a aVar = new a();
        aVar.setContext(ResManager.getAppContext());
        new RemoteMetadataServiceHelper(aVar, remoteService -> {
            atomicBoolean.set(remoteService.isMultipleTabsEnabled());
        }).executeRemoteService();
        Debug.d(getClass(), "isMultipleTabsEnabled = " + atomicBoolean.get(), new Object[0]);
        return atomicBoolean.get();
    }

    private ReaderWakeupServiceBundle c(String service) {
        try {
            Debug.d(getClass(), "start wakeupService, service = " + service, new Object[0]);
            ((MessengerRemoteServiceConnection) Optional.ofNullable(this.b.get(service)).orElseGet(() -> {
                return a(service);
            })).loadMessenger();
            TestUtils.sleep(2000L);
        } catch (Throwable th) {
            Debug.e(getClass(), th);
        }
        return this;
    }

    public void wakeUpAllTabService() {
        createWakeupServiceObservable(f).subscribe();
    }

    public Observable<ReaderWakeupServiceBundle> createWakeupServiceObservable(List<String> serviceList) {
        return Observable.just(this).observeOn(a().getObserveOn()).map((v0) -> {
            return v0.b();
        }).filter((v0) -> {
            return v0.booleanValue();
        }).flatMap(aBoolean -> {
            return Observable.fromIterable(serviceList);
        }).map(this::c);
    }

    private MessengerRemoteServiceConnection a(String service) {
        MessengerRemoteServiceConnection connectTimeoutMs = new b(ResManager.getAppContext(), new Intent().setComponent(new ComponentName("com.onyx.kreader", service))).setConnectTimeoutMs(c);
        this.b.put(service, connectTimeoutMs);
        return connectTimeoutMs;
    }

    private RxManager a() {
        if (this.a == null) {
            this.a = RxManager.Builder.newSingleThreadManager();
        }
        return this.a;
    }
}
