package com.onyx.android.sdk.utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import android.graphics.Bitmap;
import android.graphics.Color;
import com.onyx.android.sdk.extension.BitmapKt;
import java.util.Locale;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.GraphicsMode;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 35)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
public class RecoveredRenderingAndStorageTest {
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
    public void storageFormatterUsesRequestedDecimalPattern() {
        assertEquals("1.5KB", StorageUtils.getFormattedStorageSpaceStr(1500, "#.0", true));
        assertEquals("2KB", StorageUtils.getFormattedStorageSpaceStr(1500, "#", true));
    }

    @Test
    public void alphaCompositionWorksWithoutApi26ColorObjects() {
        assertEquals(
                Color.argb(0x40, 0x11, 0x22, 0x33),
                ColorUtils.applyAlphaToColor(0x40123456, 0xff112233));
    }

    @Test
    public void scaledBitmapDrawsIntoReturnedBitmap() {
        Bitmap source = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        source.eraseColor(Color.RED);
        Bitmap scaled = BitmapUtils.createScaledBitmap(source, 3, 3);
        try {
            assertEquals(Color.RED, scaled.getPixel(1, 1));
        }
        finally {
            source.recycle();
            scaled.recycle();
        }
    }

    @Test
    public void roundedBitmapMatchesOriginalKotlinImplementation() {
        Bitmap source = Bitmap.createBitmap(17, 17, Bitmap.Config.ARGB_8888);
        source.eraseColor(Color.BLUE);
        Bitmap rounded = BitmapUtils.roundCornerBitmap(source, 17, 17, 5.0f);
        Bitmap original = BitmapKt.roundCorner(source, 17, 17, 5.0f);
        try {
            assertEquals(Color.BLUE, rounded.getPixel(8, 8));
            assertArrayEquals(pixels(original), pixels(rounded));
        }
        finally {
            source.recycle();
            rounded.recycle();
            original.recycle();
        }
    }

    private static int[] pixels(Bitmap bitmap) {
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0,
                bitmap.getWidth(), bitmap.getHeight());
        return pixels;
    }
}
