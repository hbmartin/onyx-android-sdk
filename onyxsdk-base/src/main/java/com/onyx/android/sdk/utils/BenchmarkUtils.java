package com.onyx.android.sdk.utils;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/utils/BenchmarkUtils.class */
public class BenchmarkUtils {
    public static void benchmark(Class clz, Runnable runnable, long timeThreshold, String normalMsg, String warningMsg) {
        Benchmark benchmark = new Benchmark();
        runnable.run();
        if (benchmark.duration() > timeThreshold) {
            benchmark.reportWarn(ClassUtils.getSimpleName(clz) + ", " + warningMsg + ", expect time is less than " + timeThreshold + "ms, please check!");
        } else {
            benchmark.report(ClassUtils.getSimpleName(clz) + ", " + normalMsg);
        }
    }
}
