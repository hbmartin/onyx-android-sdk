package com.onyx.android.sdk.rx;

import com.onyx.android.sdk.utils.Debug;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import java.util.concurrent.TimeUnit;

public class RetryWithDelayFunction implements Function<Observable<? extends Throwable>, Observable<?>> {
   private final int a;
   private final int b;
   private int c = 0;

   public RetryWithDelayFunction(int maxRetries, int retryDelayMillis) {
      this.a = maxRetries;
      this.b = retryDelayMillis;
   }

   public Observable<?> apply(Observable<? extends Throwable> attempts) {
      return attempts.flatMap(e -> {
         this.c++;
         Debug.e(this.getClass(), "retry with delay count:" + this.c, e);
         return this.c < this.a ? this.getDelayObservable(e, this.b) : Observable.error(e);
      });
   }

   protected Observable<?> getDelayObservable(Throwable e, int delay) {
      return Observable.timer(delay, TimeUnit.MILLISECONDS);
   }
}
