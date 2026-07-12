package com.onyx.android.sdk.rx;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;

public class ObservableHolder<T> {
   private Observable<T> a;
   private ObservableEmitter<T> b;
   private Disposable c;

   public ObservableHolder() {
      this.a();
   }

   public ObservableHolder(Observable<T> observable) {
      this.a = observable;
   }

   private void a() {
      this.a = Observable.create(new ObservableOnSubscribe<T>() {
         public void subscribe(ObservableEmitter<T> emitter) {
            ObservableHolder.this.b = emitter;
         }
      });
   }

   public ObservableHolder<T> onNext(T t) {
      ObservableEmitter var2 = this.b;
      if (this.b != null) {
         var2.onNext(t);
      }

      return this;
   }

   public ObservableHolder<T> onError(Throwable t) {
      ObservableEmitter var2 = this.b;
      if (this.b != null) {
         var2.onError(t);
      }

      return this;
   }

   public void onComplete() {
      ObservableEmitter var1;
      if ((var1 = this.b) != null) {
         var1.onComplete();
      }
   }

   public Observable<T> getObservable() {
      return this.a;
   }

   public Observable<T> subscribeOn(Scheduler scheduler) {
      return this.getObservable().subscribeOn(scheduler);
   }

   public Observable<T> observeOn(Scheduler scheduler) {
      return this.getObservable().observeOn(scheduler);
   }

   public ObservableHolder<T> setDisposable(Disposable disposable) {
      this.c = disposable;
      return this;
   }

   public void dispose() {
      Disposable var1;
      if ((var1 = this.c) != null) {
         var1.dispose();
      }
   }

   public boolean isDisposed() {
      Disposable var1;
      return (var1 = this.c) != null ? var1.isDisposed() : true;
   }
}
