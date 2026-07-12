package com.onyx.android.sdk.pen;

import static org.junit.Assert.assertFalse;

import android.graphics.Rect;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RawInputReaderDeviceTest {
    @Test
    public void touchReaderJniConfigurationAndLifecycleCallsResolve() {
        RawInputReader.debugLog(false);
        RawInputReader reader = new RawInputReader();
        assertFalse(reader.isFdValid());
        reader.setStrokeWidth(3.5f);
        reader.setSingleRegionMode();
        reader.setMultiRegionMode();
        reader.enableSideBtnErase(true);
        reader.enableSideBtnErase(false);
        reader.setLimitRect(Arrays.asList(new Rect(0, 0, 100, 100)));
        reader.setExcludeRect(Arrays.asList(new Rect(20, 20, 30, 30)));
        reader.resume();
        reader.pause();
        reader.quit();
        assertFalse(reader.isFdValid());
    }
}
