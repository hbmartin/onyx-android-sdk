package com.onyx.android.sdk.wifi;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import androidx.annotation.NonNull;
import com.onyx.android.sdk.R;
import com.onyx.android.sdk.rx.RxFilter;
import com.onyx.android.sdk.rx.RxUtils;
import com.onyx.android.sdk.rx.SingleThreadScheduler;
import com.onyx.android.sdk.utils.BroadcastHelper;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.ResManager;
import com.onyx.android.sdk.utils.StringUtils;
import com.onyx.android.sdk.utils.TTFFont;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin.class */
@SuppressLint({"MissingPermission"})
public class RxWifiAdmin {
    private static final String g = "RxWifiAdmin";
    private static final int h = 3000;
    public static final String CONFIGURED_NETWORKS_CHANGED_ACTION = "android.net.wifi.CONFIGURED_NETWORKS_CHANGE";
    public static final String LINK_CONFIGURATION_CHANGED_ACTION = "android.net.wifi.LINK_CONFIGURATION_CHANGED";
    private Context a;
    private WifiManager b;
    private Callback c;
    private Scheduler d;
    private RxFilter<Runnable> e;
    private List<Disposable> f;

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$Callback.class */
    public interface Callback {
        void onWifiStateChange(boolean z, int i);

        void onScanResultReady(List<AccessPoint> list);

        void onSupplicantStateChanged(NetworkInfo.DetailedState detailedState);

        void onNetworkConnectionChange(NetworkInfo.DetailedState detailedState);

        void onLinkConfigurationChange(List<AccessPoint> list);

        void onConfiguredNetworksChange(List<AccessPoint> list);

        void onConnectedNetworkRSSIChange(int i);
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$DefaultCallback.class */
    public static class DefaultCallback implements Callback {
        @Override // com.onyx.android.sdk.wifi.RxWifiAdmin.Callback
        public void onWifiStateChange(boolean isWifiEnable, int wifiExtraState) {
        }

        @Override // com.onyx.android.sdk.wifi.RxWifiAdmin.Callback
        public void onScanResultReady(List<AccessPoint> scanResult) {
        }

        @Override // com.onyx.android.sdk.wifi.RxWifiAdmin.Callback
        public void onSupplicantStateChanged(NetworkInfo.DetailedState state) {
        }

        @Override // com.onyx.android.sdk.wifi.RxWifiAdmin.Callback
        public void onNetworkConnectionChange(NetworkInfo.DetailedState state) {
        }

        @Override // com.onyx.android.sdk.wifi.RxWifiAdmin.Callback
        public void onLinkConfigurationChange(List<AccessPoint> scanResult) {
        }

        @Override // com.onyx.android.sdk.wifi.RxWifiAdmin.Callback
        public void onConfiguredNetworksChange(List<AccessPoint> scanResult) {
        }

        @Override // com.onyx.android.sdk.wifi.RxWifiAdmin.Callback
        public void onConnectedNetworkRSSIChange(int newRSSI) {
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$a.class */
    class a implements Function<Intent, List<AccessPoint>> {
        a() {
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public List<AccessPoint> apply(Intent intent) throws Exception {
            RxWifiAdmin rxWifiAdmin = RxWifiAdmin.this;
            return rxWifiAdmin.buildResultList(rxWifiAdmin.b.getScanResults());
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$a0.class */
    class a0 implements Consumer<WifiState> {
        a0() {
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void accept(WifiState wifiState) throws Exception {
            RxWifiAdmin.this.startScanIfEnable();
            if (RxWifiAdmin.this.c == null) {
                return;
            }
            RxWifiAdmin.this.c.onWifiStateChange(RxWifiAdmin.this.b.isWifiEnabled(), wifiState.state);
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$b.class */
    class b implements ObservableOnSubscribe<Intent> {
        final /* synthetic */ Context a;
        final /* synthetic */ IntentFilter b;

        b(Context context, IntentFilter intentFilter) {
            this.a = context;
            this.b = intentFilter;
        }

        public void subscribe(ObservableEmitter<Intent> emitter) throws Exception {
            BroadcastReceiver broadcastReceiverCreateWifiScanResultsReceiver = RxWifiAdmin.this.createWifiScanResultsReceiver(emitter);
            BroadcastHelper.ensureRegisterReceiver(this.a, broadcastReceiverCreateWifiScanResultsReceiver, this.b);
            RxWifiAdmin.setDisposable(this.a, emitter, broadcastReceiverCreateWifiScanResultsReceiver);
            RxWifiAdmin.this.triggerWifiScan();
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$b0.class */
    class b0 implements Consumer<List<AccessPoint>> {
        b0() {
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void accept(List<AccessPoint> list) throws Exception {
            if (RxWifiAdmin.this.c == null) {
                return;
            }
            RxWifiAdmin.this.c.onScanResultReady(list);
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$c.class */
    class c implements ObservableOnSubscribe<Integer> {
        final /* synthetic */ Context a;
        final /* synthetic */ IntentFilter b;

        c(Context context, IntentFilter intentFilter) {
            this.a = context;
            this.b = intentFilter;
        }

        public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
            BroadcastReceiver broadcastReceiverA = RxWifiAdmin.this.a(emitter);
            BroadcastHelper.ensureRegisterReceiver(this.a, broadcastReceiverA, this.b);
            RxWifiAdmin.setDisposable(this.a, emitter, broadcastReceiverA);
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$c0.class */
    class c0 implements Consumer<List<AccessPoint>> {
        c0() {
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void accept(List<AccessPoint> list) throws Exception {
            if (RxWifiAdmin.this.c == null) {
                return;
            }
            RxWifiAdmin.this.c.onConfiguredNetworksChange(list);
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$d.class */
    class d implements ObservableOnSubscribe<SupplicantState> {
        final /* synthetic */ Context a;
        final /* synthetic */ IntentFilter b;

        d(Context context, IntentFilter intentFilter) {
            this.a = context;
            this.b = intentFilter;
        }

        public void subscribe(ObservableEmitter<SupplicantState> emitter) throws Exception {
            BroadcastReceiver broadcastReceiverB = RxWifiAdmin.this.b(emitter);
            BroadcastHelper.ensureRegisterReceiver(this.a, broadcastReceiverB, this.b);
            RxWifiAdmin.setDisposable(this.a, emitter, broadcastReceiverB);
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$d0.class */
    class d0 implements Consumer<List<AccessPoint>> {
        d0() {
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void accept(List<AccessPoint> list) throws Exception {
            if (RxWifiAdmin.this.c == null) {
                return;
            }
            RxWifiAdmin.this.c.onLinkConfigurationChange(list);
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$e.class */
    class e implements ObservableOnSubscribe<NetworkInfo> {
        final /* synthetic */ Context a;
        final /* synthetic */ IntentFilter b;

        e(Context context, IntentFilter intentFilter) {
            this.a = context;
            this.b = intentFilter;
        }

        public void subscribe(ObservableEmitter<NetworkInfo> emitter) throws Exception {
            BroadcastReceiver broadcastReceiverCreateNetworkChangesReceiver = RxWifiAdmin.this.createNetworkChangesReceiver(emitter);
            BroadcastHelper.ensureRegisterReceiver(this.a, broadcastReceiverCreateNetworkChangesReceiver, this.b);
            RxWifiAdmin.setDisposable(this.a, emitter, broadcastReceiverCreateNetworkChangesReceiver);
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$f.class */
    class f implements ObservableOnSubscribe<WifiState> {
        final /* synthetic */ Context a;
        final /* synthetic */ IntentFilter b;

        f(Context context, IntentFilter intentFilter) {
            this.a = context;
            this.b = intentFilter;
        }

        public void subscribe(ObservableEmitter<WifiState> emitter) throws Exception {
            BroadcastReceiver broadcastReceiverCreateWifiStateChangeReceiver = RxWifiAdmin.this.createWifiStateChangeReceiver(emitter);
            BroadcastHelper.ensureRegisterReceiver(this.a, broadcastReceiverCreateWifiStateChangeReceiver, this.b);
            RxWifiAdmin.setDisposable(this.a, emitter, broadcastReceiverCreateWifiStateChangeReceiver);
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$g.class */
    class g extends BroadcastReceiver {
        final /* synthetic */ ObservableEmitter a;

        g(ObservableEmitter observableEmitter) {
            this.a = observableEmitter;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            this.a.onNext(intent);
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$h.class */
    class h extends BroadcastReceiver {
        final /* synthetic */ ObservableEmitter a;

        h(ObservableEmitter observableEmitter) {
            this.a = observableEmitter;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            this.a.onNext(Integer.valueOf(intent.getIntExtra("newRssi", -200)));
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$i.class */
    class i extends BroadcastReceiver {
        final /* synthetic */ ObservableEmitter a;

        i(ObservableEmitter observableEmitter) {
            this.a = observableEmitter;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            SupplicantState supplicantState = (SupplicantState) intent.getParcelableExtra("newState");
            if (supplicantState == null || !SupplicantState.isValidState(supplicantState)) {
                return;
            }
            this.a.onNext(supplicantState);
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$j.class */
    class j extends BroadcastReceiver {
        final /* synthetic */ ObservableEmitter a;

        j(ObservableEmitter observableEmitter) {
            this.a = observableEmitter;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            this.a.onNext((NetworkInfo) intent.getParcelableExtra("networkInfo"));
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$k.class */
    class k implements Consumer<Runnable> {
        k() {
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void accept(Runnable runnable) throws Exception {
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$l.class */
    class l extends BroadcastReceiver {
        final /* synthetic */ ObservableEmitter a;

        l(ObservableEmitter observableEmitter) {
            this.a = observableEmitter;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            this.a.onNext(WifiState.fromState(intent.getIntExtra("wifi_state", 4)));
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$m.class */
    class m implements Runnable {
        m() {
        }

        @Override // java.lang.Runnable
        public void run() {
            RxWifiAdmin.this.b.setWifiEnabled(!RxWifiAdmin.this.b.isWifiEnabled());
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$n.class */
    class n implements Runnable {
        final /* synthetic */ boolean a;

        n(boolean z) {
            this.a = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            RxWifiAdmin.this.b.setWifiEnabled(this.a);
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$o.class */
    class o implements Runnable {
        o() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (RxWifiAdmin.this.b.isWifiEnabled()) {
                RxWifiAdmin.this.b.startScan();
            }
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$p.class */
    class p implements Runnable {
        final /* synthetic */ AccessPoint a;

        p(AccessPoint accessPoint) {
            this.a = accessPoint;
        }

        @Override // java.lang.Runnable
        public void run() {
            RxWifiAdmin.this.b.removeNetwork(this.a.getWifiConfiguration().networkId);
            RxWifiAdmin.this.b.saveConfiguration();
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$q.class */
    class q implements Runnable {
        final /* synthetic */ WifiManager a;
        final /* synthetic */ String b;

        q(WifiManager wifiManager, String str) {
            this.a = wifiManager;
            this.b = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            WifiConfiguration wifiConfigurationIsExistConfiguration = WifiUtil.isExistConfiguration(this.a, this.b);
            if (wifiConfigurationIsExistConfiguration != null) {
                this.a.removeNetwork(wifiConfigurationIsExistConfiguration.networkId);
            }
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$r.class */
    class r implements Runnable {
        final /* synthetic */ WifiConfiguration a;

        r(WifiConfiguration wifiConfiguration) {
            this.a = wifiConfiguration;
        }

        @Override // java.lang.Runnable
        public void run() {
            RxWifiAdmin.this.b.enableNetwork(RxWifiAdmin.this.b.addNetwork(this.a), true);
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$s.class */
    class s implements Runnable {
        final /* synthetic */ ArrayList a;

        s(ArrayList arrayList) {
            this.a = arrayList;
        }

        @Override // java.lang.Runnable
        public void run() {
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                RxWifiAdmin.this.b.enableNetwork(RxWifiAdmin.this.b.addNetwork((WifiConfiguration) it.next()), true);
            }
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$t.class */
    static class t implements Action {
        final /* synthetic */ Context a;
        final /* synthetic */ BroadcastReceiver b;

        t(Context context, BroadcastReceiver broadcastReceiver) {
            this.a = context;
            this.b = broadcastReceiver;
        }

        public void run() {
            BroadcastHelper.ensureUnregisterReceiver(this.a, this.b);
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$u.class */
    class u implements Comparator<AccessPoint> {
        u() {
        }

        @Override // java.util.Comparator
        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public int compare(AccessPoint a1, AccessPoint a2) {
            return a2.getSignalLevel() - a1.getSignalLevel();
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$v.class */
    class v implements Consumer<NetworkInfo> {
        v() {
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void accept(NetworkInfo networkInfo) throws Exception {
            if (RxWifiAdmin.this.c == null) {
                return;
            }
            RxWifiAdmin.this.c.onNetworkConnectionChange(networkInfo.getDetailedState());
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$w.class */
    class w implements Runnable {
        w() {
        }

        @Override // java.lang.Runnable
        public void run() {
            RxWifiAdmin.this.b.startScan();
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$x.class */
    class x implements Consumer<Integer> {
        x() {
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void accept(Integer rssi) throws Exception {
            if (RxWifiAdmin.this.c == null) {
                return;
            }
            RxWifiAdmin.this.c.onConnectedNetworkRSSIChange(rssi.intValue());
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$y.class */
    class y implements Consumer<SupplicantState> {
        y() {
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void accept(SupplicantState supplicantState) throws Exception {
            if (RxWifiAdmin.this.c == null) {
                return;
            }
            RxWifiAdmin.this.c.onSupplicantStateChanged(WifiInfo.getDetailedStateOf(supplicantState));
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/wifi/RxWifiAdmin$z.class */
    class z implements Consumer<NetworkInfo> {
        z() {
        }

        /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
        public void accept(NetworkInfo networkInfo) throws Exception {
            if (RxWifiAdmin.this.c == null) {
                return;
            }
            RxWifiAdmin.this.c.onNetworkConnectionChange(networkInfo.getDetailedState());
        }
    }

    public RxWifiAdmin(Context context) {
        this.e = new RxFilter<>();
        this.f = new ArrayList();
        Context applicationContext = context.getApplicationContext();
        this.a = applicationContext;
        ResManager.init(applicationContext);
        this.b = (WifiManager) this.a.getSystemService("wifi");
        this.d = SingleThreadScheduler.newScheduler();
        this.e.subscribeThrottleFirst(3000L, SingleThreadScheduler.newScheduler(), new k());
    }

    public static <T> void setDisposable(Context context, ObservableEmitter<T> emitter, BroadcastReceiver receiver) {
        emitter.setDisposable(RxUtils.disposeInUiThread(new t(context, receiver)));
    }

    public RxWifiAdmin setCallback(Callback callback) {
        this.c = callback;
        return this;
    }

    public <T> void addWifiStateDispose(Observable<T> observable, Consumer<T> consumer) {
        this.f.add(observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(consumer));
    }

    public void observeWifiStateFilter(Context context) {
        if (this.b == null) {
            return;
        }
        addWifiStateDispose(observeNetworkStateChanges(context), new v());
        addWifiStateDispose(observeWifiSignalLevel(context), new x());
        addWifiStateDispose(observeSupplicantState(context), new y());
        addWifiStateDispose(observeNetworkStateChanges(context), new z());
        addWifiStateDispose(observeWifiStateChange(context), new a0());
        addWifiStateDispose(observeWifiAccessPoints(context), new b0());
        addWifiStateDispose(observeNetworksChangedAccessPoints(context), new c0());
        addWifiStateDispose(observeLinkChangedAccessPoints(context), new d0());
    }

    public Observable<List<AccessPoint>> observeWifiAccessPoints(Context context) {
        return observeWifiScanResults(context, Arrays.asList("android.net.wifi.RSSI_CHANGED", "android.net.wifi.SCAN_RESULTS"));
    }

    public Observable<List<AccessPoint>> observeNetworksChangedAccessPoints(Context context) {
        return observeWifiScanResults(context, Collections.singletonList(CONFIGURED_NETWORKS_CHANGED_ACTION));
    }

    public Observable<List<AccessPoint>> observeLinkChangedAccessPoints(Context context) {
        return observeWifiScanResults(context, Collections.singletonList(LINK_CONFIGURATION_CHANGED_ACTION));
    }

    public Observable<List<AccessPoint>> observeWifiScanResults(Context context, List<String> actions) {
        IntentFilter intentFilter = new IntentFilter();
        Iterator<String> it = actions.iterator();
        while (it.hasNext()) {
            intentFilter.addAction(it.next());
        }
        return Observable.create(new b(context, intentFilter)).observeOn(Schedulers.io()).map(new a());
    }

    public Observable<Integer> observeWifiSignalLevel(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.RSSI_CHANGED");
        return Observable.create(new c(context, intentFilter));
    }

    public Observable<SupplicantState> observeSupplicantState(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.supplicant.STATE_CHANGE");
        return Observable.create(new d(context, intentFilter)).defaultIfEmpty(SupplicantState.UNINITIALIZED);
    }

    public Observable<NetworkInfo> observeNetworkStateChanges(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        return Observable.create(new e(context, intentFilter));
    }

    public Observable<WifiState> observeWifiStateChange(Context context) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        return Observable.create(new f(context, intentFilter));
    }

    @NonNull
    public BroadcastReceiver createWifiScanResultsReceiver(ObservableEmitter<Intent> emitter) {
        return new g(emitter);
    }

    public BroadcastReceiver createNetworkChangesReceiver(ObservableEmitter<NetworkInfo> emitter) {
        return new j(emitter);
    }

    public BroadcastReceiver createWifiStateChangeReceiver(ObservableEmitter<WifiState> emitter) {
        return new l(emitter);
    }

    public void toggleWifi() {
        if (this.b == null) {
            return;
        }
        runInScheduler(new m());
    }

    public void setWifiEnabled(boolean enable) {
        if (this.b == null) {
            return;
        }
        runInScheduler(new n(enable));
    }

    public void startScanIfEnable() {
        if (this.b == null) {
            return;
        }
        a(new o());
    }

    public void connectWifi(AccessPoint accessPoint) {
        int iAddNetwork;
        WifiConfiguration wifiConfiguration = accessPoint.getWifiConfiguration();
        if (wifiConfiguration != null) {
            iAddNetwork = wifiConfiguration.networkId;
        } else {
            iAddNetwork = this.b.addNetwork(WifiUtil.createWifiConfiguration(accessPoint));
        }
        if (this.b.enableNetwork(iAddNetwork, true)) {
            this.b.saveConfiguration();
        }
    }

    public boolean forget(AccessPoint accessPoint) {
        if (accessPoint == null || accessPoint.getWifiConfiguration() == null) {
            return false;
        }
        runInScheduler(new p(accessPoint));
        return true;
    }

    public WifiConfiguration createWifiConfiguration(String ssid, String password, int type) {
        a(this.b, ssid);
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.allowedAuthAlgorithms.clear();
        wifiConfiguration.allowedGroupCiphers.clear();
        wifiConfiguration.allowedKeyManagement.clear();
        wifiConfiguration.allowedPairwiseCiphers.clear();
        wifiConfiguration.allowedProtocols.clear();
        wifiConfiguration.SSID = "\"" + ssid + "\"";
        if (type == 0) {
            wifiConfiguration.wepKeys[0] = TTFFont.UNKNOWN_FONT_NAME;
            wifiConfiguration.allowedKeyManagement.set(0);
            wifiConfiguration.wepTxKeyIndex = 0;
        } else if (type == 1) {
            wifiConfiguration.hiddenSSID = true;
            wifiConfiguration.wepKeys[0] = "\"" + password + "\"";
            wifiConfiguration.allowedAuthAlgorithms.set(1);
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedGroupCiphers.set(2);
            wifiConfiguration.allowedGroupCiphers.set(0);
            wifiConfiguration.allowedGroupCiphers.set(1);
            wifiConfiguration.allowedKeyManagement.set(0);
            wifiConfiguration.wepTxKeyIndex = 0;
        } else if (type == 2) {
            wifiConfiguration.preSharedKey = "\"" + password + "\"";
            wifiConfiguration.hiddenSSID = true;
            wifiConfiguration.allowedAuthAlgorithms.set(0);
            wifiConfiguration.allowedGroupCiphers.set(2);
            wifiConfiguration.allowedKeyManagement.set(1);
            wifiConfiguration.allowedPairwiseCiphers.set(1);
            wifiConfiguration.allowedGroupCiphers.set(3);
            wifiConfiguration.allowedPairwiseCiphers.set(2);
            wifiConfiguration.status = 2;
        }
        return wifiConfiguration;
    }

    public void addNetwork(String ssid, String password, int securityType) {
        addNetwork(createWifiConfiguration(ssid, password, securityType));
    }

    public WifiInfo getCurrentConnectionInfo() {
        return this.b.getConnectionInfo();
    }

    public List<AccessPoint> buildResultList(Collection<ScanResult> scanResults) {
        HashMap map = new HashMap();
        AccessPoint accessPoint = null;
        for (ScanResult scanResult : scanResults) {
            if (!StringUtils.isNullOrEmpty(WifiUtil.getUtf8Ssid(scanResult))) {
                AccessPoint accessPointCreateAccessPoint = createAccessPoint(scanResult, this.b);
                if (accessPointCreateAccessPoint.getWifiConfiguration() != null && Debug.getDebug()) {
                    Log.e(g, accessPointCreateAccessPoint.getWifiConfiguration().SSID + "(networkID):" + accessPointCreateAccessPoint.getWifiConfiguration().networkId);
                }
                if (WifiUtil.isAccessPointCurrentConnected(this.b, accessPointCreateAccessPoint)) {
                    a(accessPointCreateAccessPoint);
                    accessPoint = accessPointCreateAccessPoint;
                }
                if (WifiUtil.checkAccessPointLegality(this.b, map, accessPointCreateAccessPoint)) {
                    map.put(accessPointCreateAccessPoint.getSsidForDisplay(), accessPointCreateAccessPoint);
                }
            }
        }
        AccessPoint accessPoint2 = accessPoint;
        LinkedList linkedList = new LinkedList(map.values());
        Collections.sort(linkedList, new u());
        if (accessPoint2 != null) {
            linkedList.remove(accessPoint);
            linkedList.add(0, accessPoint);
        }
        return linkedList;
    }

    protected AccessPoint createAccessPoint(ScanResult result, WifiManager wifiManager) {
        return new AccessPoint(result, wifiManager);
    }

    public boolean registerReceiver() {
        a();
        Context context = this.a;
        if (context == null) {
            return false;
        }
        observeWifiStateFilter(context);
        return true;
    }

    public boolean unregisterReceiver() {
        a();
        return this.a != null;
    }

    public boolean isWifiEnabled() {
        WifiManager wifiManager = this.b;
        return wifiManager != null && wifiManager.isWifiEnabled();
    }

    public int getWifiState() {
        WifiManager wifiManager = this.b;
        if (wifiManager == null) {
            return 4;
        }
        return wifiManager.getWifiState();
    }

    public void triggerWifiScan() {
        if (this.b == null) {
            return;
        }
        a(new w());
    }

    public String getSignalString(int signal) {
        return this.a.getResources().getStringArray(R.array.wifi_signal)[signal];
    }

    public int getWifiSignalLevel(int rssi) {
        return WifiUtil.getWifiSignalLevel(rssi);
    }

    public String getLocalIPAddress() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddressNextElement = inetAddresses.nextElement();
                if (!inetAddressNextElement.isLoopbackAddress() && (inetAddressNextElement instanceof Inet4Address)) {
                    return inetAddressNextElement.getHostAddress();
                }
            }
        }
        return TTFFont.UNKNOWN_FONT_NAME;
    }

    public void runInScheduler(Runnable run) {
        RxUtils.run(run, this.d);
    }

    public WifiManager getWifiManager() {
        return this.b;
    }

    public Scheduler getScheduler() {
        return this.d;
    }

    public void addNetwork(WifiConfiguration wcg) {
        runInScheduler(new r(wcg));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BroadcastReceiver a(ObservableEmitter<Integer> emitter) {
        return new h(emitter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BroadcastReceiver b(ObservableEmitter<SupplicantState> emitter) {
        return new i(emitter);
    }

    public void addNetwork(ArrayList<WifiConfiguration> wcgList) {
        if (CollectionUtils.isNullOrEmpty(wcgList)) {
            return;
        }
        runInScheduler(new s(wcgList));
    }

    private void a(WifiManager wifiManager, String ssid) {
        runInScheduler(new q(wifiManager, ssid));
    }

    private void a(AccessPoint point) {
        point.updateWifiInfo();
        if (getCurrentConnectionInfo().getIpAddress() != 0) {
            point.setDetailedState(NetworkInfo.DetailedState.CONNECTED);
            point.setSecurityString(this.a.getString(R.string.wifi_connected));
        }
    }

    private void a() {
        Iterator<Disposable> it = this.f.iterator();
        while (it.hasNext()) {
            it.next().dispose();
        }
        this.f = new ArrayList();
    }

    private void a(Runnable run) {
        this.e.onNext(run);
    }

    public RxWifiAdmin(Context context, Callback callback) {
        this(context);
        setCallback(callback);
    }
}
