package com.onyx.android.sdk.pen;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.onyx.android.sdk.base.data.TouchPoint;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import kotlin.Pair;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class NeoPenNativeDeviceTest {
    private static TouchPoint point(float x, float y, float pressure, long timestamp) {
        return new TouchPoint(x, y, pressure, 0.5f, 12, -8, timestamp);
    }

    private static void assertInk(PenInk ink) {
        assertNotNull(ink);
        assertNotNull(ink.getPoints());
        assertNotNull(ink.getPointSizeArray());
        assertNotNull(ink.getBitmaps());
    }

    private static void assertEmpty(PenInk ink) {
        assertInk(ink);
        assertEquals(0, ink.getPoints().length);
        assertEquals(0, ink.getPointSizeArray().length);
        assertEquals(0, ink.getBitmaps().length);
    }

    private static void assertEncoding(PenInk ink, int recordSize, boolean bitmaps) {
        assertInk(ink);
        assertTrue(ink.getPoints().length > 0);
        assertTrue(ink.getPointSizeArray().length > 0);
        int[] expectedSizes = new int[ink.getPointSizeArray().length];
        Arrays.fill(expectedSizes, recordSize);
        assertArrayEquals(expectedSizes, ink.getPointSizeArray());
        assertEquals(bitmaps ? ink.getPointSizeArray().length : 0, ink.getBitmaps().length);
    }

    private static String inkSnapshot(PenInk ink) {
        if (ink == null) {
            return "null";
        }
        StringBuilder result = new StringBuilder();
        result.append("{points:").append(Arrays.toString(ink.getPoints()));
        result.append(",sizes:").append(Arrays.toString(ink.getPointSizeArray()));
        result.append(",bitmaps:[");
        for (int i = 0; i < ink.getBitmaps().length; i++) {
            if (i > 0) result.append(',');
            android.graphics.Bitmap bitmap = ink.getBitmaps()[i];
            result.append(bitmap == null ? "null"
                    : bitmap.getWidth() + "x" + bitmap.getHeight()
                            + "#" + Integer.toHexString(pixelDigest(bitmap)));
        }
        return result.append("]}").toString();
    }

    /**
     * Content digest so the differential compares texture pixels, not just
     * dimensions — an all-transparent stamp must not match a tinted one.
     */
    private static int pixelDigest(android.graphics.Bitmap bitmap) {
        int digest = 1;
        for (int y = 0; y < bitmap.getHeight(); y++) {
            for (int x = 0; x < bitmap.getWidth(); x++) {
                digest = 31 * digest + bitmap.getPixel(x, y);
            }
        }
        return digest;
    }

    private static String pixelRows(android.graphics.Bitmap bitmap) {
        StringBuilder result = new StringBuilder();
        for (int y = 0; y < bitmap.getHeight(); y++) {
            if (y > 0) result.append('/');
            for (int x = 0; x < bitmap.getWidth(); x++) {
                if (x > 0) result.append(',');
                result.append(Integer.toHexString(bitmap.getPixel(x, y)));
            }
        }
        return result.toString();
    }

    private static String resultSnapshot(NeoPenResult result) {
        if (result == null) {
            return "null";
        }
        return "{real:" + inkSnapshot(result.getRealInk())
                + ",prediction:" + inkSnapshot(result.getPredictionInk()) + "}";
    }

    private static float[] realPoints(NeoPenResult result) {
        assertNotNull(result);
        assertInk(result.getRealInk());
        return result.getRealInk().getPoints();
    }

    private static NeoPen createHighLevelPen(int penType, NeoPenConfig config) {
        switch (penType) {
            case NeoPenConfig.NEOPEN_PEN_TYPE_BRUSH:
                return NeoBrushPen.Companion.create(config);
            case NeoPenConfig.NEOPEN_PEN_TYPE_FOUNTAIN:
                return NeoFountainPen.Companion.create(config);
            case NeoPenConfig.NEOPEN_PEN_TYPE_MARKER:
                return NeoMarkerPen.Companion.create(config);
            case NeoPenConfig.NEOPEN_PEN_TYPE_CHARCOAL:
                return NeoCharcoalPen.Companion.create(config);
            case NeoPenConfig.NEOPEN_PEN_TYPE_CHARCOAL_V2:
                return NeoCharcoalPenV2.Companion.create(config);
            case NeoPenConfig.NEOPEN_PEN_TYPE_FOUNTAIN_V2:
                return NeoFountainPenV2.Companion.create(config);
            case NeoPenConfig.NEOPEN_PEN_TYPE_PENCIL:
                return NeoPencilPen.Companion.create(config);
            case NeoPenConfig.NEOPEN_PEN_TYPE_BALLPOINT:
                return NeoBallpointInkPen.Companion.create(config);
            case NeoPenConfig.NEOPEN_PEN_TYPE_SQUARE:
                return NeoSquarePen.Companion.create(config);
            default:
                throw new IllegalArgumentException("Unknown pen type " + penType);
        }
    }

    private static void drawPair(Pair<PenResult, PenResult> pair, Canvas canvas, Paint paint) {
        assertNotNull(pair);
        if (pair.getFirst() != null) {
            pair.getFirst().draw(canvas, paint);
        }
        if (pair.getSecond() != null) {
            pair.getSecond().draw(canvas, paint);
        }
    }

    @Test
    public void writeBehaviorSnapshot() throws Exception {
        StringBuilder output = new StringBuilder();
        for (int penType = 1; penType <= 9; penType++) {
            for (boolean fastMode : new boolean[]{false, true}) {
                NeoPenConfig config = new NeoPenConfig();
                config.type = penType;
                config.fastMode = fastMode;
                long handle = NeoPenNative.INSTANCE.createPen(penType, config);
                output.append("type=").append(penType).append(",fast=").append(fastMode)
                        .append(",handle=").append(handle == 0 ? 0 : 1).append('\n');
                if (handle == 0) continue;
                try {
                    output.append("down=").append(resultSnapshot(NeoPenNative.INSTANCE.onPenDown(
                            handle, point(10f, 12f, 0.25f, 1_000L), false))).append('\n');
                    output.append("move=").append(resultSnapshot(NeoPenNative.INSTANCE.onPenMove(
                            handle,
                            Arrays.asList(point(12f, 14f, 0.5f, 1_008L),
                                    point(17f, 20f, 0.75f, 1_016L)),
                            point(20f, 23f, 0.8f, 1_024L), false))).append('\n');
                    output.append("up=").append(resultSnapshot(NeoPenNative.INSTANCE.onPenUp(
                            handle, point(22f, 25f, 0.2f, 1_032L), false))).append('\n');
                } finally {
                    NeoPenNative.INSTANCE.destroyPen(handle);
                }
            }
        }
        File snapshot = new File(
                InstrumentationRegistry.getInstrumentation().getContext().getFilesDir(),
                "pen-snapshot.txt");
        try (FileOutputStream stream = new FileOutputStream(snapshot)) {
            stream.write(output.toString().getBytes(StandardCharsets.UTF_8));
        }
    }

    @Test
    public void writeTexturePixelSnapshot() throws Exception {
        StringBuilder output = new StringBuilder();
        for (int penType : new int[]{4, 5}) {
            NeoPenConfig config = new NeoPenConfig();
            config.type = penType;
            long handle = NeoPenNative.INSTANCE.createPen(penType, config);
            try {
                NeoPenNative.INSTANCE.onPenDown(handle, point(10f, 12f, 0.25f, 1_000L), false);
                NeoPenResult move = NeoPenNative.INSTANCE.onPenMove(
                        handle,
                        Arrays.asList(point(12f, 14f, 0.5f, 1_008L),
                                point(17f, 20f, 0.75f, 1_016L)),
                        point(20f, 23f, 0.8f, 1_024L), false);
                NeoPenResult up = NeoPenNative.INSTANCE.onPenUp(
                        handle, point(22f, 25f, 0.2f, 1_032L), false);
                for (String phase : new String[]{"move", "up"}) {
                    PenInk ink = "move".equals(phase) ? move.getRealInk() : up.getRealInk();
                    for (int i = 0; i < ink.getBitmaps().length; i++) {
                        android.graphics.Bitmap bitmap = ink.getBitmaps()[i];
                        output.append("type=").append(penType)
                                .append(",phase=").append(phase)
                                .append(",bitmap=").append(i)
                                .append(",size=").append(bitmap.getWidth()).append('x')
                                .append(bitmap.getHeight()).append(",pixels=")
                                .append(pixelRows(bitmap)).append('\n');
                    }
                }
            } finally {
                NeoPenNative.INSTANCE.destroyPen(handle);
            }
        }
        File snapshot = new File(
                InstrumentationRegistry.getInstrumentation().getContext().getFilesDir(),
                "texture-pixels.txt");
        try (FileOutputStream stream = new FileOutputStream(snapshot)) {
            stream.write(output.toString().getBytes(StandardCharsets.UTF_8));
        }
    }

    @Test
    public void everyNativePenTypeCompletesAStroke() {
        for (int penType = 1; penType <= 9; penType++) {
            NeoPenConfig config = new NeoPenConfig();
            config.type = penType;
            config.fastMode = penType == NeoPenConfig.NEOPEN_PEN_TYPE_FOUNTAIN_V2;
            long handle = NeoPenNative.INSTANCE.createPen(penType, config);
            assertNotEquals("pen type " + penType, 0L, handle);
            try {
                NeoPenResult down = NeoPenNative.INSTANCE.onPenDown(
                        handle, point(10f, 12f, 0.25f, 1_000L), false);
                NeoPenResult move = NeoPenNative.INSTANCE.onPenMove(
                        handle,
                        Arrays.asList(
                                point(12f, 14f, 0.5f, 1_008L),
                                point(17f, 20f, 0.75f, 1_016L)),
                        point(20f, 23f, 0.8f, 1_024L),
                        false);
                NeoPenResult up = NeoPenNative.INSTANCE.onPenUp(
                        handle, point(22f, 25f, 0.2f, 1_032L), false);

                assertNotNull(down);
                assertNotNull(move);
                assertNotNull(up);
                assertInk(down.getRealInk());
                assertInk(down.getPredictionInk());
                assertInk(move.getRealInk());
                assertInk(move.getPredictionInk());
                assertInk(up.getRealInk());
                assertInk(up.getPredictionInk());
                assertEmpty(down.getPredictionInk());
                assertEmpty(up.getPredictionInk());
                switch (penType) {
                    case NeoPenConfig.NEOPEN_PEN_TYPE_BRUSH:
                    case NeoPenConfig.NEOPEN_PEN_TYPE_FOUNTAIN:
                    case NeoPenConfig.NEOPEN_PEN_TYPE_MARKER:
                        assertEncoding(down.getRealInk(), 3, false);
                        assertEncoding(move.getRealInk(), 3, false);
                        assertEncoding(up.getRealInk(), 3, false);
                        assertEmpty(move.getPredictionInk());
                        break;
                    case NeoPenConfig.NEOPEN_PEN_TYPE_CHARCOAL:
                        assertEmpty(down.getRealInk());
                        assertEncoding(move.getRealInk(), 2, true);
                        assertEncoding(up.getRealInk(), 2, true);
                        assertEmpty(move.getPredictionInk());
                        break;
                    case NeoPenConfig.NEOPEN_PEN_TYPE_CHARCOAL_V2:
                        assertEmpty(down.getRealInk());
                        assertEmpty(move.getRealInk());
                        assertEncoding(up.getRealInk(), 2, true);
                        assertEmpty(move.getPredictionInk());
                        break;
                    case NeoPenConfig.NEOPEN_PEN_TYPE_FOUNTAIN_V2:
                        assertEmpty(down.getRealInk());
                        assertEncoding(move.getRealInk(), 3, false);
                        assertEncoding(up.getRealInk(), 3, false);
                        assertEmpty(move.getPredictionInk());
                        break;
                    case NeoPenConfig.NEOPEN_PEN_TYPE_PENCIL:
                        assertEmpty(down.getRealInk());
                        assertEmpty(move.getRealInk());
                        assertEncoding(move.getPredictionInk(), 5, false);
                        assertEncoding(up.getRealInk(), 5, false);
                        break;
                    case NeoPenConfig.NEOPEN_PEN_TYPE_BALLPOINT:
                    case NeoPenConfig.NEOPEN_PEN_TYPE_SQUARE:
                        assertEmpty(down.getRealInk());
                        assertEmpty(move.getRealInk());
                        assertEncoding(move.getPredictionInk(), 12, false);
                        assertEncoding(up.getRealInk(), 12, false);
                        break;
                    default:
                        throw new AssertionError("Unexpected pen type " + penType);
                }
            } finally {
                NeoPenNative.INSTANCE.destroyPen(handle);
            }
        }
    }

    @Test
    public void emptyMovesReturnNullLikeTheReferenceLibrary() {
        NeoPenConfig config = new NeoPenConfig();
        long handle = NeoPenNative.INSTANCE.createPen(NeoPenConfig.NEOPEN_PEN_TYPE_BRUSH, config);
        assertNotEquals(0L, handle);
        try {
            NeoPenNative.INSTANCE.onPenDown(handle, point(0f, 0f, 0.5f, 1L), false);
            NeoPenResult result = NeoPenNative.INSTANCE.onPenMove(
                    handle,
                    Collections.emptyList(),
                    point(30f, 30f, 0.5f, 2L),
                    false);
            assertNull(result);
        } finally {
            NeoPenNative.INSTANCE.destroyPen(handle);
        }
    }


    @Test
    public void invalidHandlesRaiseStructuredNativeFailures() {
        assertThrows(NativeRendererException.class, () ->
                NeoPenNative.INSTANCE.onPenDown(
                        0L, point(0f, 0f, 0.5f, 1L), false));
        assertThrows(NativeRendererException.class, () ->
                NeoPenNative.INSTANCE.onPenMove(
                        Long.MAX_VALUE,
                        Collections.singletonList(point(1f, 1f, 0.5f, 2L)),
                        null,
                        false));
        assertThrows(NativeRendererException.class, () ->
                NeoPenNative.INSTANCE.onPenUp(
                        -1L, point(2f, 2f, 0.5f, 3L), false));
        assertThrows(NativeRendererException.class, () ->
                NeoPenNative.INSTANCE.destroyPen(0L));
        assertThrows(NativeRendererException.class, () ->
                NeoPenNative.INSTANCE.destroyPen(Long.MAX_VALUE));
    }

    @Test
    public void simplePensMatchTheRecoveredReferenceValues() {
        for (int penType = 1; penType <= 3; penType++) {
            NeoPenConfig config = new NeoPenConfig();
            long handle = NeoPenNative.INSTANCE.createPen(penType, config);
            assertNotEquals(0L, handle);
            try {
                NeoPenResult down = NeoPenNative.INSTANCE.onPenDown(
                        handle, point(10f, 12f, 0.25f, 1_000L), false);
                NeoPenResult move = NeoPenNative.INSTANCE.onPenMove(
                        handle,
                        Arrays.asList(
                                point(12f, 14f, 0.5f, 1_008L),
                                point(17f, 20f, 0.75f, 1_016L)),
                        point(20f, 23f, 0.8f, 1_024L),
                        false);
                NeoPenResult up = NeoPenNative.INSTANCE.onPenUp(
                        handle, point(22f, 25f, 0.2f, 1_032L), false);
                if (penType == NeoPenConfig.NEOPEN_PEN_TYPE_BRUSH) {
                    assertArrayEquals(new float[]{10f, 12f, 3f}, realPoints(down), 0.00001f);
                    assertArrayEquals(
                            new float[]{10f, 12f, 3.01125f}, realPoints(move), 0.00001f);
                    assertArrayEquals(
                            new float[]{17f, 20f, 3.01125f, 22f, 25f, 2.6832814f},
                            realPoints(up),
                            0.00001f);
                } else if (penType == NeoPenConfig.NEOPEN_PEN_TYPE_FOUNTAIN) {
                    assertArrayEquals(new float[]{10f, 12f, 2.25f}, realPoints(down), 0.00001f);
                    assertArrayEquals(
                            new float[]{12f, 14f, 3f, 14.5f, 17f, 3.625f, 17f, 20f, 4.25f},
                            realPoints(move),
                            0.00001f);
                    assertArrayEquals(
                            new float[]{
                                    18.666666f, 21.666666f, 3.5533333f,
                                    20.333334f, 23.333334f, 2.8566666f,
                                    22f, 25f, 2.16f
                            },
                            realPoints(up),
                            0.00001f);
                } else {
                    assertArrayEquals(new float[]{10f, 12f, 2.4f}, realPoints(down), 0.00001f);
                    assertArrayEquals(
                            new float[]{12f, 14f, 2.7f, 17f, 20f, 3f},
                            realPoints(move),
                            0.00001f);
                    assertArrayEquals(new float[]{22f, 25f, 2.4f}, realPoints(up), 0.00001f);
                }
            } finally {
                NeoPenNative.INSTANCE.destroyPen(handle);
            }
        }
    }

    @Test
    public void referenceCompatibleFountainIsOptInForBothApiGenerations() {
        NeoPenConfig config = new NeoPenConfig()
                .setRendererVersion(NeoPenConfig.RENDERER_REFERENCE_COMPAT);
        config.type = NeoPenConfig.NEOPEN_PEN_TYPE_FOUNTAIN;
        config.velocitySensitivity = 1.0f;
        config.velocityUpperBound = 20.0f;
        config.tiltEnabled = true;

        long handle = NeoPenNative.INSTANCE.createPen(
                NeoPenConfig.NEOPEN_PEN_TYPE_FOUNTAIN, config);
        assertNotEquals(0L, handle);
        try {
            NeoPenResult down = NeoPenNative.INSTANCE.onPenDown(
                    handle, point(0f, 0f, 0.4f, 1_000L), false);
            NeoPenResult move = NeoPenNative.INSTANCE.onPenMove(
                    handle,
                    Arrays.asList(point(10f, 2f, 0.8f, 1_008L),
                            point(20f, 5f, 0.7f, 1_016L)),
                    point(25f, 7f, 0.6f, 1_024L),
                    false);
            assertEncoding(down.getRealInk(), 3, false);
            assertEncoding(move.getRealInk(), 3, false);
            assertEncoding(move.getPredictionInk(), 3, false);
        } finally {
            NeoPenNative.INSTANCE.destroyPen(handle);
        }

        com.onyx.android.sdk.data.note.TouchPoint first =
                new com.onyx.android.sdk.data.note.TouchPoint(0f, 0f, 0.4f, 0.5f, 12, -8, 1_000L);
        com.onyx.android.sdk.data.note.TouchPoint second =
                new com.onyx.android.sdk.data.note.TouchPoint(10f, 2f, 0.8f, 0.5f, 12, -8, 1_008L);
        assertTrue(NeoPenWrapper.initPen(config));
        try {
            assertTrue(NeoPenWrapper.onPenDown(first).length > 0);
            assertTrue(NeoPenWrapper.onPenMove(second).length > 0);
        } finally {
            NeoPenWrapper.destroyPen();
        }
    }

    @Test
    public void legacyWrapperRunsThroughTheRustJni() {
        com.onyx.android.sdk.data.note.TouchPoint first =
                new com.onyx.android.sdk.data.note.TouchPoint(10f, 12f, 0.25f, 0.5f, 12, -8, 1_000L);
        com.onyx.android.sdk.data.note.TouchPoint middle =
                new com.onyx.android.sdk.data.note.TouchPoint(17f, 20f, 0.75f, 0.5f, 12, -8, 1_016L);
        com.onyx.android.sdk.data.note.TouchPoint last =
                new com.onyx.android.sdk.data.note.TouchPoint(22f, 25f, 0.2f, 0.5f, 12, -8, 1_032L);
        assertEquals(7, PenUtils.getPointDoubleArray(first, 1f).length);

        NeoPenConfig config = new NeoPenConfig();
        config.type = NeoPenConfig.NEOPEN_PEN_TYPE_BRUSH;
        assertTrue(NeoPenWrapper.initPen(config));
        try {
            assertTrue(NeoPenWrapper.onPenDown(first).length > 0);
            assertTrue(NeoPenWrapper.onPenMove(middle).length > 0);
            assertTrue(NeoPenWrapper.onPenUp(last).length > 0);
            assertTrue(NeoPenWrapper.computeRenderPoints(Arrays.asList(first, middle, last)).length > 0);
            assertNotNull(NeoPenWrapper.getRenderedBitmaps());
        } finally {
            NeoPenWrapper.destroyPen();
        }
    }

    @Test
    public void highLevelFactoriesBuildAndDrawAllNinePenTypes() {
        Bitmap bitmap = Bitmap.createBitmap(128, 128, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xff123456);
        paint.setStrokeWidth(3f);
        for (int penType = 1; penType <= 9; penType++) {
            NeoPenConfig config = new NeoPenConfig();
            config.type = penType;
            config.fastMode = penType == NeoPenConfig.NEOPEN_PEN_TYPE_FOUNTAIN_V2;
            NeoPen pen = createHighLevelPen(penType, config);
            assertNotNull("pen type " + penType, pen);
            try {
                drawPair(pen.onPenDown(point(10f, 12f, 0.25f, 1_000L), false), canvas, paint);
                drawPair(
                        pen.onPenMove(
                                Arrays.asList(
                                        point(12f, 14f, 0.5f, 1_008L),
                                        point(17f, 20f, 0.75f, 1_016L)),
                                point(20f, 23f, 0.8f, 1_024L),
                                false),
                        canvas,
                        paint);
                drawPair(pen.onPenUp(point(22f, 25f, 0.2f, 1_032L), false), canvas, paint);
            } finally {
                pen.destroy();
            }
        }
    }
}
