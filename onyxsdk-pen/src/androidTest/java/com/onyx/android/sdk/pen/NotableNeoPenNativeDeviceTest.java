package com.onyx.android.sdk.pen;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Base64;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.onyx.android.sdk.pennative.NeoPenNative;
import com.onyx.android.sdk.pennative.PenConfig;
import com.onyx.android.sdk.pennative.PenInk;
import com.onyx.android.sdk.pennative.PenInkResult;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.lang.reflect.Method;
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

    private static double[] point(
            double x, double y, double pressure, double size,
            double tiltX, double tiltY, double timestamp) {
        return new double[]{x, y, pressure, size, tiltX, tiltY, timestamp};
    }

    private static double[] points(double[]... points) {
        double[] flattened = new double[points.length * 7];
        for (int index = 0; index < points.length; index++) {
            System.arraycopy(points[index], 0, flattened, index * 7, 7);
        }
        return flattened;
    }

    private static String inkSnapshot(PenInk ink, boolean includePixels) throws Exception {
        if (ink == null) return "null";
        StringBuilder result = new StringBuilder();
        result.append("{\"points\":").append(Arrays.toString(ink.getPoints()));
        result.append(",\"sizes\":").append(Arrays.toString(ink.getPointSizeArray()));
        result.append(",\"bitmaps\":[");
        for (int index = 0; index < ink.getBitmaps().length; index++) {
            if (index > 0) result.append(',');
            Bitmap bitmap = ink.getBitmaps()[index];
            result.append(bitmapSnapshot(bitmap, includePixels));
        }
        return result.append("]}").toString();
    }

    private static String bitmapSnapshot(Bitmap bitmap, boolean includePixels) throws Exception {
        if (bitmap == null) return "null";
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0,
                bitmap.getWidth(), bitmap.getHeight());
        byte[] argb = new byte[pixels.length * 4];
        for (int index = 0; index < pixels.length; index++) {
            int pixel = pixels[index];
            argb[index * 4] = (byte) (pixel >>> 24);
            argb[index * 4 + 1] = (byte) (pixel >>> 16);
            argb[index * 4 + 2] = (byte) (pixel >>> 8);
            argb[index * 4 + 3] = (byte) pixel;
        }
        StringBuilder result = new StringBuilder();
        result.append("{\"width\":").append(bitmap.getWidth())
                .append(",\"height\":").append(bitmap.getHeight())
                .append(",\"config\":\"").append(bitmap.getConfig()).append("\"")
                .append(",\"sha256\":\"").append(hex(MessageDigest.getInstance("SHA-256").digest(argb)))
                .append("\"");
        if (includePixels) {
            result.append(",\"argbBase64\":\"")
                    .append(Base64.encodeToString(argb, Base64.NO_WRAP)).append("\"");
        }
        return result.append('}').toString();
    }

    /** Keep the original text format consumed by compare-pen-snapshots.py. */
    private static String legacyInkSnapshot(PenInk ink) {
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

    private static String hex(byte[] bytes) {
        StringBuilder output = new StringBuilder(bytes.length * 2);
        for (byte value : bytes) output.append(String.format("%02x", value & 0xff));
        return output.toString();
    }

    private static String resultSnapshot(PenInkResult result, boolean includePixels) throws Exception {
        if (result == null) return "null";
        return "{\"real\":" + inkSnapshot(result.getRealInk(), includePixels)
                + ",\"prediction\":" + inkSnapshot(result.getPredictionInk(), includePixels) + "}";
    }

    private static String legacyResultSnapshot(PenInkResult result) {
        if (result == null) return "null";
        return "{real:" + legacyInkSnapshot(result.getRealInk())
                + ",prediction:" + legacyInkSnapshot(result.getPredictionInk()) + "}";
    }

    private static String exceptionSnapshot(Throwable throwable) {
        String message = throwable.getMessage();
        if (message == null) message = "";
        return "{\"class\":\"" + throwable.getClass().getName()
                + "\",\"message\":\"" + message.replace("\\", "\\\\").replace("\"", "\\\"")
                .replace("\n", "\\n").replace("\r", "\\r") + "\"}";
    }

    private static final class Trace {
        final double[] down;
        final double[] move;
        final double[] prediction;
        final double[] up;

        Trace(double[] down, double[] move, double[] prediction, double[] up) {
            this.down = down;
            this.move = move;
            this.prediction = prediction;
            this.up = up;
        }
    }

    private static Trace shortTrace() {
        return new Trace(
                point(10, 12, 0.25, 1_000),
                points(point(12, 14, 0.5, 1_008), point(17, 20, 0.75, 1_016)),
                point(20, 23, 0.8, 1_024),
                point(22, 25, 0.2, 1_032));
    }

    /** Trace copied from device-validation's pinned NoteAir4C raw-JNI fixture. */
    private static Trace recoveredFixtureTrace(boolean normalizePressure) {
        double divisor = normalizePressure ? 4096.0 : 1.0;
        return new Trace(
                point(100, 200, 100 / divisor, 0, 1, -1, 1_000),
                points(point(120, 230, 400 / divisor, 0, 2, -2, 2_000)),
                null,
                point(150, 260, 0, 0, 2, -2, 3_000));
    }

    /** Dense input shaped to exercise the quick-writing failure reported by Notable #8. */
    private static Trace quickWritingTrace() {
        double[][] movePoints = new double[62][];
        for (int index = 0; index < movePoints.length; index++) {
            double x = 31 + index * 1.7;
            double y = 45 + Math.sin(index / 4.0) * 8 + index * 0.55;
            double pressure = 0.08 + (index % 17) / 20.0;
            movePoints[index] = point(x, y, pressure, 0.35,
                    (index % 25) - 12, 8 - (index % 17), 10_004 + index * 4);
        }
        return new Trace(
                point(30, 45, 0.05, 0.35, -12, 8, 10_000),
                points(movePoints),
                point(137, 79, 0.6, 0.35, 8, -6, 10_252),
                point(140, 81, 0.02, 0.35, 9, -7, 10_256));
    }

    private static Trace geometryTrace(String geometry) {
        double[][] movePoints = new double[14][];
        for (int index = 0; index < movePoints.length; index++) {
            double x = 20 + (index + 1) * 5;
            double y = 40 + (index + 1) * 3;
            double pressure = 0.55;
            double timestamp = 20_000 + (index + 1) * 8;
            double tiltX = 6;
            double tiltY = -4;
            switch (geometry) {
                case "horizontal": y = 40; break;
                case "vertical": x = 20; break;
                case "zigzag": y = 40 + (index % 2 == 0 ? 12 : -12); break;
                case "pressureRamp": pressure = (index + 1) / 16.0; break;
                case "tiltRamp":
                    tiltX = -45 + index * 6;
                    tiltY = 45 - index * 5;
                    break;
                case "slowTimestamps": timestamp = 20_000 + (index + 1) * 250; break;
                case "repeated": x = 20; y = 40; break;
                default: throw new IllegalArgumentException(geometry);
            }
            movePoints[index] = point(x, y, pressure, 0.4, tiltX, tiltY, timestamp);
        }
        double endX = "vertical".equals(geometry) || "repeated".equals(geometry) ? 20 : 100;
        double endY = "horizontal".equals(geometry) || "repeated".equals(geometry) ? 40 : 88;
        double endTimestamp = "slowTimestamps".equals(geometry) ? 24_000 : 20_128;
        return new Trace(
                point(20, 40, 0.05, 0.4, -45, 45, 20_000),
                points(movePoints),
                point(endX - 2, endY - 2, 0.75, 0.4, 35, -25, endTimestamp - 4),
                point(endX, endY, 0.02, 0.4, 40, -30, endTimestamp));
    }

    private static PenConfig notableConfig(int type, boolean fastMode) {
        PenConfig config = new PenConfig();
        config.setType(type);
        config.setFastMode(fastMode);
        config.setWidth(6f);
        config.setMinWidth(0.001f);
        config.setPressureSensitivity(0.3f);
        config.setVelocitySensitivity(0.5f);
        config.setSmoothLevel(0.6f);
        config.setTiltScale(3f);
        return config;
    }

    private static void appendCase(
            StringBuilder output, String caseId, int penType,
            PenConfig config, Trace trace, boolean includePixels) throws Exception {
        appendCase(output, caseId, penType, config, trace, includePixels, false);
    }

    private static void appendCase(
            StringBuilder output, String caseId, int penType,
            PenConfig config, Trace trace, boolean includePixels, boolean repaint) throws Exception {
        output.append("{\"case\":\"").append(caseId).append("\",\"penType\":")
                .append(penType).append(",\"repaint\":").append(repaint);
        long handle = 0;
        try {
            handle = NeoPenNative.INSTANCE.createPen(penType, config);
            output.append(",\"handleNonzero\":").append(handle != 0);
            if (handle != 0) {
                output.append(",\"down\":").append(resultSnapshot(
                        NeoPenNative.INSTANCE.onPenDown(handle, trace.down, repaint), includePixels));
                output.append(",\"move\":").append(resultSnapshot(
                        NeoPenNative.INSTANCE.onPenMove(
                                handle, trace.move, trace.prediction, repaint), includePixels));
                output.append(",\"up\":").append(resultSnapshot(
                        NeoPenNative.INSTANCE.onPenUp(handle, trace.up, repaint), includePixels));
            }
        } catch (Throwable throwable) {
            output.append(",\"exception\":").append(exceptionSnapshot(throwable));
        } finally {
            if (handle != 0) NeoPenNative.INSTANCE.destroyPen(handle);
        }
        output.append("}\n");
    }

    private static void appendStreamingCase(
            StringBuilder output, String caseId, int penType, PenConfig config) throws Exception {
        output.append("{\"case\":\"").append(caseId).append("\",\"penType\":")
                .append(penType);
        long handle = 0;
        try {
            handle = NeoPenNative.INSTANCE.createPen(penType, config);
            output.append(",\"handleNonzero\":").append(handle != 0);
            if (handle != 0) {
                output.append(",\"down\":").append(resultSnapshot(
                        NeoPenNative.INSTANCE.onPenDown(
                                handle, point(30, 45, 0.05, 10_000), false), false));
                output.append(",\"moves\":[");
                for (int index = 0; index < 64; index++) {
                    if (index > 0) output.append(',');
                    double x = 31 + index * 1.7;
                    double y = 45 + Math.sin(index / 4.0) * 8 + index * 0.55;
                    double pressure = 0.08 + (index % 17) / 20.0;
                    double[] current = point(x, y, pressure, 0.35,
                            (index % 25) - 12, 8 - (index % 17), 10_004 + index * 4);
                    double[] prediction = point(x + 1.5, y + 0.8, pressure, 0.35,
                            (index % 25) - 11, 7 - (index % 17), 10_006 + index * 4);
                    output.append(resultSnapshot(NeoPenNative.INSTANCE.onPenMove(
                            handle, current, prediction, false), false));
                }
                output.append("],\"up\":").append(resultSnapshot(
                        NeoPenNative.INSTANCE.onPenUp(
                                handle, point(140, 81, 0.02, 10_264), false), false));
            }
        } catch (Throwable throwable) {
            output.append(",\"exception\":").append(exceptionSnapshot(throwable));
        } finally {
            if (handle != 0) NeoPenNative.INSTANCE.destroyPen(handle);
        }
        output.append("}\n");
    }

    private static PenConfig fieldVariant(String field, int penType) {
        PenConfig config = notableConfig(penType, false);
        switch (field) {
            case "color": config.setColor(0xff336699); break;
            case "width": config.setWidth(8f); break;
            case "minWidth": config.setMinWidth(1f); break;
            case "rotateAngle": config.setRotateAngle(45); break;
            case "tiltEnabled": config.setTiltEnabled(true); break;
            case "tiltScale": config.setTiltScale(7f); break;
            case "directionEnabled": config.setDirectionEnabled(true); break;
            case "maxTouchPressure": config.setMaxTouchPressure(4096f); break;
            case "dpi": config.setDpi(160f); break;
            case "displayScaleX": config.setDisplayScaleX(1.5f); break;
            case "displayScaleY": config.setDisplayScaleY(0.75f); break;
            case "scalePrecision": config.setScalePrecision(2f); break;
            case "brushSpacing": config.setBrushSpacing(0.5f); break;
            case "brushShape": config.setBrushShape(PenConfig.NEOPEN_BRUSH_SHAPE_ELLIPSE); break;
            case "brushRatio": config.setBrushRatio(2f); break;
            case "brushAngle": config.setBrushAngle(30f); break;
            case "pressureSensitivity": config.setPressureSensitivity(0.8f); break;
            case "velocitySensitivity": config.setVelocitySensitivity(0.8f); break;
            case "velocityAmplifier": config.setVelocityAmplifier(0.5f); break;
            case "velocityIgnoreThreshold": config.setVelocityIgnoreThreshold(0.1f); break;
            case "velocityLowerBound": config.setVelocityLowerBound(2f); break;
            case "velocityUpperBound": config.setVelocityUpperBound(20f); break;
            case "smoothLevel": config.setSmoothLevel(0.8f); break;
            case "startPointLimit": config.setStartPointLimit(2f); break;
            case "startLengthLimit": config.setStartLengthLimit(4f); break;
            case "endVelocitySensitivity": config.setEndVelocitySensitivity(0.5f); break;
            case "endThinningRate": config.setEndThinningRate(0.5f); break;
            case "ignorePressure": config.setIgnorePressure(0.5f); break;
            default: throw new IllegalArgumentException(field);
        }
        return config;
    }

    @Test
    public void writeBehaviorSnapshot() throws Exception {
        Assume.assumeTrue(
                "External Notable reference was not supplied or could not load: " + loadFailure,
                available);
        StringBuilder output = new StringBuilder();
        for (int penType = 1; penType <= PenConfig.NEOPEN_PEN_TYPE_BRUSH_SIGN; penType++) {
            for (boolean fastMode : new boolean[]{false, true}) {
                PenConfig config = new PenConfig();
                config.setType(penType);
                config.setFastMode(fastMode);
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
                    output.append("down=").append(legacyResultSnapshot(down)).append('\n');
                    output.append("move=").append(legacyResultSnapshot(move)).append('\n');
                    output.append("up=").append(legacyResultSnapshot(up)).append('\n');
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

    @Test
    public void writeComprehensiveOracleCorpus() throws Exception {
        Assume.assumeTrue(
                "External Notable reference was not supplied or could not load: " + loadFailure,
                available);
        StringBuilder output = new StringBuilder();
        output.append("{\"schema\":1,\"source\":\"libneopen_jni.so\",\"abi\":\"arm64-v8a\"}\n");

        for (int penType = 1; penType <= PenConfig.NEOPEN_PEN_TYPE_BRUSH_SIGN; penType++) {
            for (boolean fastMode : new boolean[]{false, true}) {
                appendCase(output, "default_short_fast_" + fastMode, penType,
                        notableConfig(penType, fastMode), shortTrace(), true);
            }
        }

        PenConfig rawPressureConfig = notableConfig(6, false);
        rawPressureConfig.setMaxTouchPressure(4096f);
        appendCase(output, "recovered_fixture_raw_pressure", 6,
                rawPressureConfig, recoveredFixtureTrace(false), true);
        appendCase(output, "recovered_fixture_normalized_pressure", 6,
                notableConfig(6, false), recoveredFixtureTrace(true), true);
        appendCase(output, "notable_issue_8_quick_writing", 6,
                notableConfig(6, false), quickWritingTrace(), true);
        appendStreamingCase(output, "notable_issue_8_streaming_64_moves", 6,
                notableConfig(6, false));
        appendCase(output, "repaint_true", 6,
                notableConfig(6, false), shortTrace(), false, true);
        appendCase(output, "config_type_mismatch_arg6_field1", 6,
                notableConfig(1, false), shortTrace(), true);

        for (String geometry : new String[]{
                "horizontal", "vertical", "zigzag", "pressureRamp",
                "tiltRamp", "slowTimestamps", "repeated"
        }) {
            appendCase(output, "type10_" + geometry, 10,
                    notableConfig(10, false), geometryTrace(geometry), true);
        }

        String[] fields = new String[]{
                "color", "width", "minWidth", "rotateAngle", "tiltEnabled", "tiltScale",
                "directionEnabled", "maxTouchPressure", "dpi", "displayScaleX", "displayScaleY",
                "scalePrecision", "brushSpacing", "brushShape", "brushRatio", "brushAngle",
                "pressureSensitivity", "velocitySensitivity", "velocityAmplifier",
                "velocityIgnoreThreshold", "velocityLowerBound", "velocityUpperBound",
                "smoothLevel", "startPointLimit", "startLengthLimit", "endVelocitySensitivity",
                "endThinningRate", "ignorePressure"
        };
        for (String field : fields) {
            appendCase(output, "type6_field_" + field, 6,
                    fieldVariant(field, 6), shortTrace(), false);
            appendCase(output, "type10_field_" + field, 10,
                    fieldVariant(field, 10), geometryTrace("pressureRamp"), false);
        }

        File snapshot = new File(
                InstrumentationRegistry.getInstrumentation().getContext().getFilesDir(),
                "libneopen-jni-oracle.jsonl");
        try (FileOutputStream stream = new FileOutputStream(snapshot)) {
            stream.write(output.toString().getBytes(StandardCharsets.UTF_8));
        }
    }

    @Test
    public void writeUtilityOracleSnapshot() throws Exception {
        Assume.assumeTrue(
                "External Notable reference was not supplied or could not load: " + loadFailure,
                available);
        StringBuilder output = new StringBuilder();
        long logger = NeoPenNative.INSTANCE.createLogger();
        output.append("{\"case\":\"logger\",\"handleNonzero\":")
                .append(logger != 0).append("}\n");
        NeoPenNative.INSTANCE.registerLogger(logger);
        for (int level = 0; level <= 5; level++) NeoPenNative.INSTANCE.setLogLevel(level);

        Bitmap bitmap = Bitmap.createBitmap(3, 2, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(new int[]{
                Color.WHITE, Color.BLACK, Color.RED,
                Color.TRANSPARENT, 0x80ffffff, 0xff336699
        }, 0, 3, 0, 0, 3, 2);
        String before = bitmapSnapshot(bitmap, true);
        Method bitmapColor = NeoPenNative.class.getDeclaredMethod(
                "nativeSetBitmapColor", Bitmap.class);
        bitmapColor.setAccessible(true);
        boolean result = (boolean) bitmapColor.invoke(NeoPenNative.INSTANCE, bitmap);
        String after = bitmapSnapshot(bitmap, true);
        output.append("{\"case\":\"setBitmapColor\",\"result\":").append(result)
                .append(",\"before\":").append(before)
                .append(",\"after\":").append(after).append("}\n");

        File snapshot = new File(
                InstrumentationRegistry.getInstrumentation().getContext().getFilesDir(),
                "libneopen-jni-utility.jsonl");
        try (FileOutputStream stream = new FileOutputStream(snapshot)) {
            stream.write(output.toString().getBytes(StandardCharsets.UTF_8));
        }
    }

    @Test
    public void modernRuntimeSupportsEveryNotablePenType() {
        Assume.assumeTrue(
                "Modern native library was not supplied or could not load: " + loadFailure,
                available);
        long logger = NeoPenNative.INSTANCE.createLogger();
        assertNotEquals(0L, logger);
        NeoPenNative.INSTANCE.registerLogger(logger);

        Trace trace = quickWritingTrace();
        for (int penType = 1; penType <= PenConfig.NEOPEN_PEN_TYPE_BRUSH_SIGN; penType++) {
            long handle = NeoPenNative.INSTANCE.createPen(penType, notableConfig(penType, false));
            assertNotEquals("pen type " + penType, 0L, handle);
            try {
                PenInkResult down = NeoPenNative.INSTANCE.onPenDown(
                        handle, trace.down, false);
                PenInkResult move = NeoPenNative.INSTANCE.onPenMove(
                        handle, trace.move, trace.prediction, false);
                PenInkResult up = NeoPenNative.INSTANCE.onPenUp(
                        handle, trace.up, false);
                assertNotNull("down result for type " + penType, down);
                assertNotNull("move result for type " + penType, move);
                assertNotNull("up result for type " + penType, up);
                int recordCount = down.getRealInk().getPointSizeArray().length
                        + move.getRealInk().getPointSizeArray().length
                        + up.getRealInk().getPointSizeArray().length;
                assertTrue("pen type " + penType + " emitted no records", recordCount > 0);
            } finally {
                NeoPenNative.INSTANCE.destroyPen(handle);
            }
        }
    }
}
