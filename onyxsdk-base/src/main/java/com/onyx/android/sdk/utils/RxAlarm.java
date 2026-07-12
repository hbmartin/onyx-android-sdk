package com.onyx.android.sdk.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/RxAlarm.class */
public class RxAlarm implements Disposable {
    private static final Class k = RxAlarm.class;
    private static final long l = 50;
    private Context a;
    private AlarmManager b;
    private PendingIntent c;
    private long e;
    private ObservableEmitter<Long> h;
    private Disposable i;
    private String d = RxAlarm.class.getCanonicalName() + hashCode();
    private int f = 0;
    private int g = hashCode();
    private BroadcastReceiver j = new b();

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/RxAlarm$a.class */
    class a implements ObservableOnSubscribe<Long> {
        a() {
        }

        public void subscribe(ObservableEmitter<Long> e) {
            RxAlarm.this.h = e;
            RxAlarm.this.c();
        }
    }

    /* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/RxAlarm$b.class */
    class b extends BroadcastReceiver {
        b() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (StringUtils.safelyEquals(RxAlarm.this.d, intent.getAction())) {
                RxAlarm.this.d();
            }
        }
    }

    public RxAlarm(Context context) {
        this.a = context.getApplicationContext();
        this.b = (AlarmManager) context.getSystemService("alarm");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        ObservableEmitter<Long> observableEmitter = this.h;
        if (observableEmitter == null) {
            return;
        }
        observableEmitter.onNext(0L);
        this.h = null;
    }

    public RxAlarm setAction(@NonNull String action) {
        this.d = action;
        return this;
    }

    public RxAlarm setRequestCode(int requestCode) {
        this.g = requestCode;
        return this;
    }

    public void clean() {
        dispose();
    }

    public long getIntervalInMills() {
        return this.e;
    }

    public void timer(long intervalInMills, Consumer<Long> onNext) {
        this.e = intervalInMills - l;
        dispose();
        this.i = Observable.timer(l, TimeUnit.MILLISECONDS).flatMap(aLong -> {
            return b();
        }).subscribeOn(AndroidSchedulers.mainThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(onNext);
    }

    public void dispose() {
        Disposable disposable = this.i;
        if (disposable == null) {
            return;
        }
        disposable.dispose();
        this.i = null;
        this.h = null;
        a();
    }

    public boolean isDisposed() {
        Disposable disposable = this.i;
        return disposable == null || disposable.isDisposed();
    }

    private Observable<Long> b() {
        return Observable.create(new a());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        a();
        if (this.e <= 0) {
            return;
        }
        this.f++;
        long jCurrentTimeMillis = System.currentTimeMillis() + this.e;
        Debug.d(getClass(), "setAlarm, count = " + this.f + ",alarmTime:" + DateTimeUtil.formatDate(jCurrentTimeMillis), new Object[0]);
        Intent intent = new Intent(this.d);
        this.c = PendingIntent.getBroadcast(this.a, this.g, intent, 268435456);
        BroadcastHelper.ensureRegisterReceiver(this.a, this.j, new IntentFilter(intent.getAction()));
        this.b.setExact(0, jCurrentTimeMillis, this.c);
    }

    private void a() {
        PendingIntent pendingIntent = this.c;
        if (pendingIntent == null || this.j == null) {
            return;
        }
        this.b.cancel(pendingIntent);
        BroadcastHelper.ensureUnregisterReceiver(this.a, this.j);
    }
}
