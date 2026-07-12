package com.onyx.android.sdk.rx;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;

public class RxDebounce implements Disposable {
    private ObservableHolder<Long> a;

    public Observable<Boolean> subscribeDebounce(long time, Consumer<Boolean> beforeDebounce, Consumer<Boolean> subscribe) {
        ObservableHolder<Long> observableHolder = new ObservableHolder<>();
        this.a = observableHolder;
        Observable<Boolean> observableObserveOn = observableHolder.getObservable().buffer(2, 1).map(buffer -> {
            return Boolean.valueOf(buffer.size() == 2 && ((Long) buffer.get(1)).longValue() - ((Long) buffer.get(0)).longValue() < time);
        }).map(timer -> {
            if (beforeDebounce != null) {
                beforeDebounce.accept(timer);
            }
            return timer;
        }).debounce(timer2 -> {
            return timer2.booleanValue() ? Observable.timer(time, TimeUnit.MILLISECONDS) : Observable.empty();
        }).observeOn(AndroidSchedulers.mainThread());
        this.a.setDisposable(observableObserveOn.subscribe(subscribe));
        this.a.onNext(0L);
        return observableObserveOn;
    }

    public void onNext(long now) {
        ObservableHolder<Long> observableHolder = this.a;
        if (observableHolder != null) {
            observableHolder.onNext(Long.valueOf(now));
        }
    }

    public void dispose() {
        ObservableHolder<Long> observableHolder = this.a;
        if (observableHolder != null) {
            observableHolder.dispose();
        }
    }

    public boolean isDisposed() {
        ObservableHolder<Long> observableHolder = this.a;
        if (observableHolder != null) {
            return observableHolder.isDisposed();
        }
        return true;
    }
}
