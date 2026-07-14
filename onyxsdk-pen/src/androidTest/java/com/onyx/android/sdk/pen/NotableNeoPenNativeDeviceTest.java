package com.onyx.android.sdk.pen;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import android.graphics.Bitmap;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.onyx.android.sdk.pennative.NeoPenNative;
import com.onyx.android.sdk.pennative.PenConfig;
import com.onyx.android.sdk.pennative.PenInk;
import com.onyx.android.sdk.pennative.PenInkResult;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Black-box differential adapter for the hash-pinned libneopen_jni.so from
 * the installed Notable 0.2.3 APK. The binary itself is never distributed.
 */
@RunWith(AndroidJUnit4.class)
public final class NotableNeoPenNativeDeviceTest {
    private static boolean available;
    private static String loadFailure;

    @BeforeClass
    public static void loadExternalReference() {
        try {
            System.loadLibrary("neopen_jni");
            available = true;
        } catch (UnsatisfiedLinkError unavailable) {
            available = false;
            loadFailure = unavailable.toString();
        }
    }

    private static double[] point(
            double x, double y, double pressure, double timestamp) {
        return new double[]{x, y, pressure, 0.5, 12.0, -8.0, timestamp};
    }

    private static double[] points(double[]... points) {
        double[] flattened = new double[points.length * 7];
        for (int index = 0; index < points.length; index++) {
            System.arraycopy(points[index], 0, flattened, index * 7, 7);
        }
        return flattened;
    }

    private static String inkSnapshot(PenInk ink) {
        if (ink == null) return "null";
        StringBuilder result = new StringBuilder();
        result.append("{points:").append(Arrays.toString(ink.getPoints()));
        result.append(",sizes:").append(Arrays.toString(ink.getPointSizeArray()));
        result.append(",bitmaps:[");
        for (int index = 0; index < ink.getBitmaps().length; index++) {
            if (index > 0) result.append(',');
            Bitmap bitmap = ink.getBitmaps()[index];
            result.append(bitmap == null ? "null"
                    : bitmap.getWidth() + "x" + bitmap.getHeight()
                            + "#" + Integer.toHexString(pixelDigest(bitmap)));
        }
        return result.append("]}").toString();
    }

    private static int pixelDigest(Bitmap bitmap) {
        int digest = 1;
        for (int y = 0; y < bitmap.getHeight(); y++) {
            for (int x = 0; x < bitmap.getWidth(); x++) {
                digest = 31 * digest + bitmap.getPixel(x, y);
            }
        }
        return digest;
    }

    private static String resultSnapshot(PenInkResult result) {
        if (result == null) return "null";
        return "{real:" + inkSnapshot(result.getRealInk())
                + ",prediction:" + inkSnapshot(result.getPredictionInk()) + "}";
    }

    @Test
    public void writeBehaviorSnapshot() throws Exception {
        Assume.assumeTrue(
                "External Notable reference was not supplied or could not load: " + loadFailure,
                available);
        StringBuilder output = new StringBuilder();
        for (int penType = 1; penType <= 9; penType++) {
            for (boolean fastMode : new boolean[]{false, true}) {
                PenConfig config = new PenConfig().setType(penType).setFastMode(fastMode);
                long handle = NeoPenNative.INSTANCE.createPen(penType, config);
                output.append("type=").append(penType).append(",fast=").append(fastMode)
                        .append(",handle=").append(handle == 0 ? 0 : 1).append('\n');
                assertNotEquals("pen type " + penType, 0L, handle);
                try {
                    PenInkResult down = NeoPenNative.INSTANCE.onPenDown(
                            handle, point(10, 12, 0.25, 1_000), false);
                    PenInkResult move = NeoPenNative.INSTANCE.onPenMove(
                            handle,
                            points(point(12, 14, 0.5, 1_008), point(17, 20, 0.75, 1_016)),
                            point(20, 23, 0.8, 1_024),
                            false);
                    PenInkResult up = NeoPenNative.INSTANCE.onPenUp(
                            handle, point(22, 25, 0.2, 1_032), false);
                    assertNotNull(down);
                    assertNotNull(move);
                    assertNotNull(up);
                    output.append("down=").append(resultSnapshot(down)).append('\n');
                    output.append("move=").append(resultSnapshot(move)).append('\n');
                    output.append("up=").append(resultSnapshot(up)).append('\n');
                } finally {
                    NeoPenNative.INSTANCE.destroyPen(handle);
                }
            }
        }
        File snapshot = new File(
                InstrumentationRegistry.getInstrumentation().getContext().getFilesDir(),
                "notable-pen-snapshot.txt");
        try (FileOutputStream stream = new FileOutputStream(snapshot)) {
            stream.write(output.toString().getBytes(StandardCharsets.UTF_8));
        }
    }
}
