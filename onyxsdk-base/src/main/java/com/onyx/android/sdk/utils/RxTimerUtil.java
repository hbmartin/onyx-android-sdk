package com.onyx.android.sdk.utils;

import androidx.annotation.NonNull;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class RxTimerUtil {
   private static final long a = 500L;
   private static ConcurrentHashMap<Integer, SoftReference<RxTimerUtil.TimerObserver>> b = new ConcurrentHashMap<>();
   static boolean c = false;

   public static boolean isPrintLog() {
      return c;
   }

   public static void setPrintLog(boolean printLog) {
      c = printLog;
   }

   public static int timer(long mills, RxTimerUtil.TimerObserver timerObserver) {
      return timer(mills, TimeUnit.MILLISECONDS, timerObserver);
   }

   public static int shortTimer(RxTimerUtil.TimerObserver timerObserver) {
      TimeUnit var1 = TimeUnit.MILLISECONDS;
      return timer(500L, var1, timerObserver);
   }

   public static int timer(long delay, TimeUnit timeUnit, RxTimerUtil.TimerObserver timerObserver) {
      long var4 = TimeUnit.MILLISECONDS.convert(delay, timeUnit);
      if (isPrintLog()) {
         Class<RxTimerUtil> var6 = RxTimerUtil.class;
         StringBuilder var7 = new StringBuilder().append("timer start, delayMs=").append(var4).append(", observerHash=");
         Serializable var5;
         if (timerObserver != null) {
            var5 = timerObserver.hashCode();
         } else {
            var5 = "null";
         }

         Debug.i(var6, var7.append(var5).toString(), new Object[0]);
      }

      return subscribe(Observable.timer(delay, timeUnit).observeOn(AndroidSchedulers.mainThread()), timerObserver, true);
   }

   public static int timerOnCurScheduler(long delay, TimeUnit timeUnit, RxTimerUtil.TimerObserver timerObserver) {
      return subscribe(Observable.timer(delay, timeUnit), timerObserver, true);
   }

   public static int timer(long initialDelay, long period, RxTimerUtil.TimerObserver timerObserver) {
      return timer(initialDelay, period, AndroidSchedulers.mainThread(), timerObserver);
   }

   public static int timer(long initialDelay, long period, @NonNull Scheduler observeOn, RxTimerUtil.TimerObserver timerObserver) {
      return subscribe(Observable.interval(initialDelay, period, TimeUnit.MILLISECONDS).observeOn(observeOn), timerObserver, false);
   }

   public static int timerRange(long start, long count, long initialDelay, long period, TimeUnit unit, RxTimerUtil.TimerObserver timerObserver) {
      return subscribe(Observable.intervalRange(start, count, initialDelay, period, unit).observeOn(AndroidSchedulers.mainThread()), timerObserver, false);
   }

   public static int subscribe(Observable<Long> observable, RxTimerUtil.TimerObserver timerObserver, boolean autoCancel) {
      if (timerObserver == null) {
         return 0;
      }

      int var3;
      int var10000 = var3 = timerObserver.hashCode();
      b.put(var3, new SoftReference<>(timerObserver));
      observable.doFinally(() -> {
         if (autoCancel) {
            cancel(var3);
         }
      }).subscribe(timerObserver);
      return var10000;
   }

   public static void cancel(RxTimerUtil.TimerObserver timerObserver) {
      if (timerObserver != null) {
         if (isPrintLog()) {
            String var10000 = "timer cancel, observer/key=" + timerObserver.hashCode();
            Object[] var1 = new Object[0];
            Debug.i(RxTimerUtil.class, var10000, var1);
         }

         cancel(timerObserver.hashCode());
      }
   }

   public static Boolean isCanceled(RxTimerUtil.TimerObserver timerObserver) {
      return timerObserver == null ? Boolean.TRUE : b.containsKey(timerObserver.hashCode()) ^ true;
   }

   public static void cancel(int timerObserverKey) {
      if (isPrintLog()) {
         String var10000 = "timer cancel, observer/key=" + timerObserverKey;
         Object[] var1 = new Object[0];
         Debug.i(RxTimerUtil.class, var10000, var1);
      }

      if (!CollectionUtils.isNullOrEmpty(b)) {
         SoftReference var2;
         if ((var2 = b.get(timerObserverKey)) != null) {
            RxTimerUtil.TimerObserver var3;
            if ((var3 = (RxTimerUtil.TimerObserver)var2.get()) != null) {
               var3.cancel();
               b.remove(timerObserverKey);
            }
         }
      }
   }

   public static void cancelAll() {
      ConcurrentHashMap var0 = b;
      if (b != null && var0.size() != 0) {
         Iterator var2 = b.entrySet().iterator();

         while (var2.hasNext()) {
            SoftReference var1;
            RxTimerUtil.TimerObserver var3;
            if ((var1 = (SoftReference)((Entry)var2.next()).getValue()) != null && (var3 = (RxTimerUtil.TimerObserver)var1.get()) != null) {
               var3.cancel();
            }
         }

         b.clear();
      }
   }

   public abstract static class TimerObserver implements Observer<Long> {
      private Disposable a;

      public void onSubscribe(Disposable disposable) {
         this.a = disposable;
      }

      public void onError(Throwable e) {
         this.cancel();
      }

      public void onComplete() {
         this.cancel();
      }

      public void cancel() {
         Disposable var1 = this.a;
         if (this.a != null && !var1.isDisposed()) {
            this.a.dispose();
         }
      }
   }
}
