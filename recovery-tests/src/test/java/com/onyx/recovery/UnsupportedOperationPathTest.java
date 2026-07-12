package com.onyx.recovery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.onyx.android.sdk.pen.NeoPenRender;
import com.onyx.android.sdk.pen.NeoSegmentPathResultPen;
import org.junit.jupiter.api.Test;

class UnsupportedOperationPathTest {
    @Test
    void recoveredKotlinDefaultSuperMarkerRunsUnsupportedOperationPath() {
        UnsupportedOperationException failure = assertThrows(
                UnsupportedOperationException.class,
                () -> NeoPenRender.onTouchDown$default(
                        new NeoPenRender(new NeoSegmentPathResultPen()),
                        null,
                        false,
                        0,
                        new Object()));
        assertInstanceOf(UnsupportedOperationException.class, failure);
        assertEquals(
                "Super calls with default arguments not supported in this target, function: onTouchDown",
                failure.getMessage());
    }
}
