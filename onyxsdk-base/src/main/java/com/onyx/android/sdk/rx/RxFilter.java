package com.onyx.android.sdk.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RxFilter<T> implements Disposable {
   public static final int DEFAULT_INTERVAL_DURATION = 400;
   private ObservableEmitter<T> a;
   protected Disposable disposable;
   private T b;

   public void subscribeDebounce(long time, Consumer<? super T> onNext) {
      this.disposable = this.createObservable()
         .debounce(time, TimeUnit.MILLISECONDS)
         .subscribeOn(AndroidSchedulers.mainThread())
         .observeOn(AndroidSchedulers.mainThread())
         .subscribe(onNext);
   }

   public void subscribeThrottleLast(long time, Consumer<? super T> onNext) {
      this.disposable = this.createObservable()
         .throttleLast(time, TimeUnit.MILLISECONDS)
         .subscribeOn(AndroidSchedulers.mainThread())
         .observeOn(AndroidSchedulers.mainThread())
         .subscribe(onNext);
   }

   public void subscribeBuffer(int count, Consumer<? super List<T>> onNext) {
      this.disposable = this.createObservable()
         .buffer(count)
         .subscribeOn(AndroidSchedulers.mainThread())
         .observeOn(AndroidSchedulers.mainThread())
         .subscribe(onNext);
   }

   public void subscribeThrottleLast(Consumer<? super T> onNext) {
      this.subscribeThrottleLast(400L, onNext);
   }

   public void subscribeThrottleFirst(long time, Consumer<? super T> onNext) {
      Scheduler var4 = AndroidSchedulers.mainThread();
      this.subscribeThrottleFirst(time, var4, onNext);
   }

   public void subscribeThrottleFirst(Consumer<? super T> onNext) {
      this.subscribeThrottleFirst(400L, onNext);
   }

   public void subscribeThrottleFirst(long time, Scheduler scheduler, Consumer<? super T> onNext) {
      this.disposable = this.createObservable().throttleFirst(time, TimeUnit.MILLISECONDS).subscribeOn(scheduler).observeOn(scheduler).subscribe(onNext);
   }

   public void onNext(T event) {
      ObservableEmitter var2 = this.a;
      if (this.a != null) {
         var2.onNext(event);
      } else {
         this.b = event;
      }
   }

   protected Observable<T> createObservable() {
      return Observable.create(new ObservableOnSubscribe<T>() {
         public void subscribe(ObservableEmitter<T> e) {
            RxFilter.this.a = e;
            if (RxFilter.this.b != null) {
               RxFilter.this.onNext(RxFilter.this.b);
               RxFilter.this.b = null;
            }
         }
      });
   }

   public void dispose() {
      Disposable var1 = this.disposable;
      if (this.disposable != null) {
         var1.dispose();
         this.disposable = null;
         this.a = null;
      }
   }

   public boolean isDisposed() {
      Disposable var1;
      return (var1 = this.disposable) != null ? var1.isDisposed() : true;
   }
}
