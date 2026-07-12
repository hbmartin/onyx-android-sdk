package com.onyx.android.sdk.rx.rxbroadcast;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.onyx.android.sdk.api.utils.NetworkUtil;
import com.onyx.android.sdk.utils.BroadcastHelper;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RxConnectivityChangeObservable extends Observable<Boolean> {
    private Context a;

    static final class a extends BroadcastReceiver implements Disposable {
        private Observer<? super Boolean> a;
        private Context b;

        public a(Observer<? super Boolean> observer, Context context) {
            this.a = observer;
            this.b = context;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (NetworkUtil.isWiFiConnected(context)) {
                this.a.onNext(Boolean.TRUE);
            } else {
                this.a.onNext(Boolean.FALSE);
            }
        }

        public void dispose() {
            this.b.unregisterReceiver(this);
        }

        public boolean isDisposed() {
            return false;
        }
    }

    public RxConnectivityChangeObservable(Context context) {
        this.a = context.getApplicationContext();
    }

    @SuppressLint({"NewApi"})
    protected void subscribeActual(Observer<? super Boolean> observer) {
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        a aVar = new a(observer, this.a);
        observer.onSubscribe(aVar);
        this.a.registerReceiver(aVar, intentFilter, BroadcastHelper.ReceiverFlags.RECEIVER_EXPORTED);
    }
}
