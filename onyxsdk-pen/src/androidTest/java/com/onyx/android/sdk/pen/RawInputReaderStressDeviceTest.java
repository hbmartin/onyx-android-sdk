package com.onyx.android.sdk.pen;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import java.io.File;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Lifecycle stress for the raw reader JNI: repeated start/pause/resume/quit
 * cycles must not leak file descriptors, strand reader threads, or grow the
 * native heap without bound.
 */
@RunWith(AndroidJUnit4.class)
public class RawInputReaderStressDeviceTest {
    private static final int CYCLES = 300;

    @Test
    public void repeatedLifecycleLeaksNothing() throws Exception {
        RawInputReader.debugLog(false);

        // Warm-up cycle so lazily created executors/buffers do not count
        // against the steady-state measurements.
        runCycle(new RawInputReader());
        Thread.sleep(500);

        int fdsBefore = openFdCount();
        long nativeHeapBefore = android.os.Debug.getNativeHeapAllocatedSize();
        long javaHeapBefore = usedJavaHeap();

        for (int i = 0; i < CYCLES; i++) {
            runCycle(new RawInputReader());
        }
        Thread.sleep(1000);

        int fdsAfter = openFdCount();
        long nativeHeapAfter = android.os.Debug.getNativeHeapAllocatedSize();
        long javaHeapAfter = usedJavaHeap();
        int readerThreads = readerThreadCount();

        // The shared reader executor legitimately keeps one worker alive;
        // anything more means quit() left poll loops stuck.
        assertTrue("stuck reader threads: " + readerThreads, readerThreads <= 2);
        assertTrue("fd leak: " + fdsBefore + " -> " + fdsAfter,
                fdsAfter <= fdsBefore + 8);
        long nativeGrowth = nativeHeapAfter - nativeHeapBefore;
        assertTrue("native heap grew by " + nativeGrowth + " bytes over "
                + CYCLES + " cycles", nativeGrowth < 16L * 1024 * 1024);
        long javaGrowth = javaHeapAfter - javaHeapBefore;
        assertTrue("java heap grew by " + javaGrowth + " bytes over "
                + CYCLES + " cycles", javaGrowth < 32L * 1024 * 1024);
    }

    private static long usedJavaHeap() {
        Runtime runtime = Runtime.getRuntime();
        // Two GC passes settle SoftReference/finalizer churn enough for a
        // leak bound this generous.
        runtime.gc();
        runtime.gc();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private static void runCycle(RawInputReader reader) throws Exception {
        reader.start();
        reader.pause();
        reader.resume();
        reader.quit();
        assertFalse(reader.isFdValid());
        // Give the executor a moment to drain so queued poll loops cannot
        // pile up behind a still-open predecessor.
        Thread.sleep(5);
    }

    private static int openFdCount() {
        String[] fds = new File("/proc/self/fd").list();
        return fds == null ? -1 : fds.length;
    }

    private static int readerThreadCount() {
        int count = 0;
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            String name = thread.getName();
            if (name != null && name.contains("raw_input")) {
                count++;
            }
        }
        return count;
    }
}
