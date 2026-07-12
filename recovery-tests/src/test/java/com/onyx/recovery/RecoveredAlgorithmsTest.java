package com.onyx.recovery;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.onyx.android.sdk.pen.NeoPenNative;
import com.onyx.android.sdk.pen.NeoPenWrapper;
import com.onyx.android.sdk.pen.RawInputReader;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;

class RecoveredAlgorithmsTest {
    @Test
    void productionClassesDeclareEveryNativeEntryPoint() {
        assertEquals(11, java.util.Arrays.stream(RawInputReader.class.getDeclaredMethods())
                .filter(method -> Modifier.isNative(method.getModifiers()))
                .count());
        assertEquals(5, java.util.Arrays.stream(NeoPenNative.class.getDeclaredMethods())
                .filter(method -> Modifier.isNative(method.getModifiers()))
                .count());
        assertEquals(7, java.util.Arrays.stream(NeoPenWrapper.class.getDeclaredMethods())
                .filter(method -> Modifier.isNative(method.getModifiers()))
                .count());
    }
}
