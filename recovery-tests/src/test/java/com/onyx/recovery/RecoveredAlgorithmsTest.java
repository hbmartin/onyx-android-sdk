package com.onyx.recovery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.onyx.android.sdk.recovered.pen.NativeContract;
import com.onyx.android.sdk.pen.RawInputReader;
import com.onyx.android.sdk.utils.FileUtils;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.Test;

class RecoveredAlgorithmsTest {
    @Test
    void readableFileSizeMatchesRecoveredBoundaries() {
        Locale original = Locale.getDefault();
        try {
            Locale.setDefault(Locale.US);
            assertEquals("0", FileUtils.readableFileSize(-1));
            assertEquals("0", FileUtils.readableFileSize(0));
            assertEquals("1 B", FileUtils.readableFileSize(1));
            assertEquals("1 KB", FileUtils.readableFileSize(1024));
            assertEquals("1.5 KB", FileUtils.readableFileSize(1536));
            assertEquals("1 TB", FileUtils.readableFileSize(1L << 40));
        } finally {
            Locale.setDefault(original);
        }
    }

    @Test
    void nativeContractCoversEveryOriginalAbiAndJniEntryPoint() {
        assertEquals(List.of("armeabi-v7a", "arm64-v8a", "x86", "x86_64"),
                NativeContract.ABIS);
        assertEquals(11, NativeContract.JNI_EXPORTS.size());
        assertFalse(NativeContract.JNI_EXPORTS.stream().anyMatch(name -> !name.startsWith("Java_")));
        assertEquals(11, List.of(RawInputReader.class.getDeclaredMethods()).stream()
                .filter(method -> Modifier.isNative(method.getModifiers()))
                .count());
    }
}
