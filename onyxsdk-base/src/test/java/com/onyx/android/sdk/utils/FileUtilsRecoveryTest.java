package com.onyx.android.sdk.utils;

import static org.junit.Assert.assertEquals;

import java.util.Locale;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 35)
public class FileUtilsRecoveryTest {
    private Locale originalLocale;

    @Before
    public void useStableFormattingLocale() {
        originalLocale = Locale.getDefault();
        Locale.setDefault(Locale.US);
    }

    @After
    public void restoreFormattingLocale() {
        Locale.setDefault(originalLocale);
    }

    @Test
    public void formatsRecoveredBinaryBoundaries() {
        assertEquals("0", FileUtils.readableFileSize(-1L));
        assertEquals("0", FileUtils.readableFileSize(0L));
        assertEquals("1 B", FileUtils.readableFileSize(1L));
        assertEquals("1,023 B", FileUtils.readableFileSize(1023L));
        assertEquals("1 KB", FileUtils.readableFileSize(1024L));
        assertEquals("1.5 KB", FileUtils.readableFileSize(1536L));
        assertEquals("1 MB", FileUtils.readableFileSize(1L << 20));
        assertEquals("1 TB", FileUtils.readableFileSize(1L << 40));
    }

    @Test
    public void formatsWithTheRecoveredSingleFractionDigitPattern() {
        // 1792 bytes is 1.75 KB: the recovered "#,##0.#" pattern rounds to one
        // fraction digit ("1.8 KB") while a default DecimalFormat would print
        // "1.75 KB" — this is the case that distinguishes the two.
        assertEquals("1.8 KB", FileUtils.readableFileSize(1792L));
    }
}
