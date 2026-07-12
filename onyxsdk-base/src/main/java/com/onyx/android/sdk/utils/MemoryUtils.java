// 
// 

package com.onyx.android.sdk.utils;

import java.io.Reader;
import java.io.Closeable;
import java.io.BufferedReader;
import java.io.FileReader;
import android.content.Context;
import android.content.pm.PackageManager;
import android.app.ActivityManager;
import java.util.Iterator;
import java.util.Map;

public class MemoryUtils
{
    public static final int MB = 1048576;
    public static final int KB = 1024;
    
    public static void printMemoryInfo() {
        final Runtime runtime = Runtime.getRuntime();
        try {
            Debug.w((Class)MemoryUtils.class, "--- start print memory info ---", new Object[0]);
            a();
            printJavaMemoryInfo(runtime);
            Debug.w((Class)MemoryUtils.class, "getNativeHeapAllocatedSize: " + android.os.Debug.getNativeHeapAllocatedSize() / 1048576L + "M", new Object[0]);
            for (final Map.Entry entry : android.os.Debug.getRuntimeStats().entrySet()) {
                Debug.w((Class)MemoryUtils.class, (String)entry.getKey() + ": " + (String)entry.getValue(), new Object[0]);
            }
            for (final Map.Entry entry2 : getMemoryInfo().getMemoryStats().entrySet()) {
                Debug.w((Class)MemoryUtils.class, (String)entry2.getKey() + ": " + (String)entry2.getValue() + "K", new Object[0]);
            }
            Debug.w((Class)MemoryUtils.class, "--- end print memory info ---", new Object[0]);
        } catch (Throwable error) {
            Debug.e(error);
        }
    }
    
    private static void a() {
        final ActivityManager.MemoryInfo memoryInfo;
        ((ActivityManager)ResManager.getAppContext().getSystemService("activity")).getMemoryInfo(memoryInfo = new ActivityManager.MemoryInfo());
        Debug.w((Class)MemoryUtils.class, "------ start print system memory info ------", new Object[0]);
        Debug.w((Class)MemoryUtils.class, "system.memory.availMem: " + memoryInfo.availMem / 1048576L + "M", new Object[0]);
        Debug.w((Class)MemoryUtils.class, "system.memory.lowMemory: " + memoryInfo.lowMemory, new Object[0]);
        Debug.w((Class)MemoryUtils.class, "system.memory.threshold: " + memoryInfo.threshold / 1048576L + "M", new Object[0]);
        Debug.w((Class)MemoryUtils.class, "system.memory.totalMem: " + memoryInfo.totalMem / 1048576L + "M", new Object[0]);
        Debug.w((Class)MemoryUtils.class, "------ end print system memory info ------", new Object[0]);
    }
    
    public static void printJavaMemoryInfo(final Runtime r) throws PackageManager.NameNotFoundException {
        Debug.w((Class)MemoryUtils.class, "app: " + DeviceUtils.getApplicationName(ResManager.getAppContext()), new Object[0]);
        Debug.w((Class)MemoryUtils.class, "process: " + ApplicationUtil.getCurrentProcessName(ResManager.getAppContext()), new Object[0]);
        Debug.w((Class)MemoryUtils.class, "Java Maximum available memory:" + r.maxMemory() / 1048576L + "M", new Object[0]);
        Debug.w((Class)MemoryUtils.class, "Java Current available memory:" + r.totalMemory() / 1048576L + "M", new Object[0]);
        Debug.w((Class)MemoryUtils.class, "Java Current free memory:" + r.freeMemory() / 1048576L + "M", new Object[0]);
        Debug.w((Class)MemoryUtils.class, "Java Currently used memory:" + (r.totalMemory() - r.freeMemory()) / 1048576L + "M", new Object[0]);
        Debug.w((Class)MemoryUtils.class, "Java Remaining available memory: " + getTraceMemory() / 1048576L + "M", new Object[0]);
        Debug.w((Class)MemoryUtils.class, "Java Ratio of remaining free memory: " + getTraceMemoryRatio() * 100.0f + "%", new Object[0]);
    }
    
    public static void printSimpleJavaMemoryInfo() {
        Debug.w((Class)MemoryUtils.class, "Java Maximum available memory:" + Runtime.getRuntime().maxMemory() / 1048576L + "M", new Object[0]);
        Debug.w((Class)MemoryUtils.class, "Java Remaining available memory: " + getTraceMemory() / 1048576L + "M", new Object[0]);
    }
    
    public static long getNativeHeapAllocatedSize() {
        return android.os.Debug.getNativeHeapAllocatedSize();
    }
    
    public static android.os.Debug.MemoryInfo getMemoryInfo() {
        final android.os.Debug.MemoryInfo memoryInfo = new android.os.Debug.MemoryInfo();
        android.os.Debug.getMemoryInfo(memoryInfo);
        return memoryInfo;
    }
    
    public static long getTraceMemory() {
        final Runtime runtime;
        return (runtime = Runtime.getRuntime()).maxMemory() - (runtime.totalMemory() - runtime.freeMemory());
    }
    
    public static float getTraceMemoryRatio() {
        return getTraceMemory() / (float)Runtime.getRuntime().maxMemory();
    }
    
    public static long getTotalMemory(final Context context) {
        if (CompatibilityUtil.apiLevelCheck(16)) {
            return getMemoryInfo(context).totalMem;
        }
        try (BufferedReader reader = new BufferedReader(
                new FileReader("/proc/meminfo"), 8192)) {
            String[] fields = reader.readLine().split("\\s+");
            return Long.parseLong(fields[1]) * 1024L;
        } catch (Exception error) {
            error.printStackTrace();
            return 0L;
        }
    }
    
    public static ActivityManager.MemoryInfo getMemoryInfo(final Context context) {
        final ActivityManager.MemoryInfo memoryInfo = new android.app.ActivityManager.MemoryInfo();
        final ActivityManager.MemoryInfo memoryInfo2 = memoryInfo;
        new ActivityManager.MemoryInfo();
        TaskUtils.getActivityManager(context).getMemoryInfo(memoryInfo2);
        return memoryInfo;
    }
    
    public static void printStringMemoryInfo(final String prefix, final String str) {
        final int length = str.length();
        final Object[] array2;
        final Object[] array = array2 = new Object[3];
        final int n = length;
        final Object[] array3 = array2;
        final int i = length;
        array2[0] = prefix;
        array3[1] = i;
        array[2] = n * 2L;
        Debug.w((Class)MemoryUtils.class, "%s charArrayLength=%s charArrayMem=%sB", array2);
    }
}
