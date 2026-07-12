package com.onyx.android.sdk.rx;

import androidx.annotation.NonNull;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class RxCallback<T> implements Observer<T> {
   public static <T> void onSubscribe(RxCallback<T> callback, @NonNull Disposable d) {
      if (callback != null) {
         callback.onSubscribe(d);
      }
   }

   public static <T> void onNext(RxCallback<T> callback, @NonNull T t) {
      if (callback != null) {
         callback.onNext(t);
      }
   }

   public static <T> void onError(RxCallback<T> callback, @NonNull Throwable e) {
      if (callback != null) {
         callback.onError(e);
      }
   }

   public static <T> void onComplete(RxCallback<T> callback) {
      if (callback != null) {
         callback.onComplete();
      }
   }

   public static <T> void onFinally(RxCallback<T> callback) {
      if (callback != null) {
         callback.onFinally();
      }
   }

   public void onSubscribe(@NonNull Disposable d) {
   }

   public abstract void onNext(@NonNull T var1);

   public void onError(@NonNull Throwable e) {
   }

   public void onComplete() {
   }

   public void onFinally() {
   }
}
