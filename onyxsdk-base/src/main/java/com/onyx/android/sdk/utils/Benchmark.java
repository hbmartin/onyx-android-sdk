package com.onyx.android.sdk.utils;

public class Benchmark {
   private static boolean c;
   private static final long d = 5L;
   private static Benchmark e = new Benchmark();
   private long a = 0L;
   private long b = 0L;

   public static Benchmark globalBenchmark() {
      return e;
   }

   public Benchmark() {
      this.restart();
   }

   public static boolean isShowShortDurationInfoLog() {
      return c;
   }

   public static void setShowShortDurationInfoLog(boolean showShortDurationInfoLog) {
      c = showShortDurationInfoLog;
   }

   public void restart() {
      this.a = System.currentTimeMillis();
   }

   public void report(String msg) {
      long var2;
      if ((var2 = this.duration()) >= 5L || isShowShortDurationInfoLog()) {
         Debug.i(this.getClass(), msg + " ---> " + var2 + "ms", new Object[0]);
      }
   }

   public void report(String msg, long reportDuration) {
      if (this.duration() >= reportDuration) {
         this.report(msg);
      }
   }

   public void reportAndRestart(String msg, long reportDuration) {
      if (this.duration() < reportDuration) {
         this.restart();
      } else {
         this.reportAndRestart(msg);
      }
   }

   public void reportAndRestart(String msg) {
      this.report(msg);
      this.restart();
   }

   public void reportDebugAndRestart(String msg) {
      Debug.d(this.getClass(), msg + " ---> " + this.duration() + "ms", new Object[0]);
      this.restart();
   }

   public void reportError(String msg) {
      Debug.e(this.getClass(), msg + " ---> " + this.duration() + "ms", new Object[0]);
   }

   public void reportWarn(String msg) {
      Debug.w(this.getClass(), msg + " ---> " + this.duration() + "ms", new Object[0]);
   }

   public void reportDebug(String msg) {
      Debug.d(this.getClass(), msg + " ---> " + this.duration() + "ms", new Object[0]);
   }

   public long duration() {
      long var1;
      long var10000 = var1 = System.currentTimeMillis();
      this.b = var1;
      return var10000 - this.a;
   }

   public String durationString() {
      return " duration=" + this.duration() + "ms";
   }

   public long durationAndRestart() {
      long var10000 = this.duration();
      this.restart();
      return var10000;
   }
}
