package com.onyx.android.sdk.pen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.onyx.android.sdk.pen.touch.TouchRender;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;

public class TouchHelperConfigurationTest {
    @Test
    public void invalidStrokeWidthsAreSanitizedBeforeRendererMutation() {
        assertEquals(3.0f, TouchHelper.sanitizeStrokeWidth(0.0f), 0.0f);
        assertEquals(3.0f, TouchHelper.sanitizeStrokeWidth(-1.0f), 0.0f);
        assertEquals(3.0f, TouchHelper.sanitizeStrokeWidth(Float.NaN), 0.0f);
        assertEquals(3.0f, TouchHelper.sanitizeStrokeWidth(Float.POSITIVE_INFINITY), 0.0f);
        assertEquals(4.5f, TouchHelper.sanitizeStrokeWidth(4.5f), 0.0f);
    }

    @Test
    public void repeatMoveFilterIsDispatchedToTheMatchingRendererMethod() {
        AtomicInteger filterCalls = new AtomicInteger();
        AtomicInteger refreshCalls = new AtomicInteger();
        TouchRender renderer = (TouchRender) Proxy.newProxyInstance(
                TouchRender.class.getClassLoader(),
                new Class<?>[] {TouchRender.class},
                (proxy, method, args) -> {
                    if (method.getName().equals("setFilterRepeatMovePoint")) {
                        filterCalls.incrementAndGet();
                    } else if (method.getName().equals("setPenUpRefreshEnabled")) {
                        refreshCalls.incrementAndGet();
                    }
                    return null;
                });

        TouchHelper.dispatchFilterRepeatMovePoint(
                Collections.singletonList(renderer), true);

        assertEquals(1, filterCalls.get());
        assertEquals(0, refreshCalls.get());
    }

    @Test
    public void nullSingleLimitLeavesTheObservedLimitUnchanged() {
        assertNull(TouchHelper.observedSingleLimit(null));
    }
}
