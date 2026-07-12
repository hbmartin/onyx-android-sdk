package com.onyx.android.sdk.rx.rxbroadcast;

import android.content.Context;
import com.onyx.android.sdk.rx.RxCallback;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

public class RxBroadcast {
    public static final String TAG = "RxBroadcast";

    static class a implements Consumer<Boolean> {
        final /* synthetic */ RxCallback a;

        a(RxCallback rxCallback) {
            this.a = rxCallback;
        }

        public void accept(Boolean aBoolean) throws Exception {
            RxCallback.onNext(this.a, aBoolean);
        }
    }

    static class b implements Consumer<Throwable> {
        final /* synthetic */ RxCallback a;

        b(RxCallback rxCallback) {
            this.a = rxCallback;
        }

        public void accept(Throwable throwable) throws Exception {
            RxCallback.onError(this.a, throwable);
        }
    }

    static class c implements Consumer<Boolean> {
        final /* synthetic */ RxCallback a;

        c(RxCallback rxCallback) {
            this.a = rxCallback;
        }

        public void accept(Boolean aBoolean) throws Exception {
            RxCallback.onNext(this.a, aBoolean);
        }
    }

    static class d implements Consumer<Throwable> {
        final /* synthetic */ RxCallback a;

        d(RxCallback rxCallback) {
            this.a = rxCallback;
        }

        public void accept(Throwable throwable) throws Exception {
            RxCallback.onError(this.a, throwable);
        }
    }

    public static Disposable connectivityChangeWithTimeOut(Context context, RxCallback<Boolean> callback) {
        return new RxConnectivityChangeObservable(context).subscribeOn(AndroidSchedulers.mainThread()).timeout(8L, TimeUnit.SECONDS).subscribe(new a(callback), new b(callback));
    }

    public static Disposable connectivityChange(Context context, RxCallback<Boolean> callback) {
        return new RxConnectivityChangeObservable(context).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new c(callback), new d(callback));
    }
}
