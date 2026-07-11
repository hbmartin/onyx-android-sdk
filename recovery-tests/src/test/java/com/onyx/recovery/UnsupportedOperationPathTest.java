package com.onyx.recovery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.onyx.android.sdk.recovered.pen.DefaultArgumentGuard;
import org.junit.jupiter.api.Test;

class UnsupportedOperationPathTest {
    @Test
    void recoveredKotlinDefaultSuperMarkerRunsUnsupportedOperationPath() {
        UnsupportedOperationException failure = assertThrows(
                UnsupportedOperationException.class,
                () -> DefaultArgumentGuard.rejectSuperCall(new Object(), "onTouchDown"));
        assertInstanceOf(UnsupportedOperationException.class, failure);
        assertEquals(
                "Super calls with default arguments not supported in this target, function: onTouchDown",
                failure.getMessage());
    }
}
