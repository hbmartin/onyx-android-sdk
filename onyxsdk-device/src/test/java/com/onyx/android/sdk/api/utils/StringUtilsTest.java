package com.onyx.android.sdk.api.utils;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import org.junit.Test;

public class StringUtilsTest {
    @Test
    public void joinPreservesLeadingEmptyElementBehavior() {
        assertEquals("png,jpg", StringUtils.join(Arrays.asList("", "png", "jpg"), ","));
    }
}
