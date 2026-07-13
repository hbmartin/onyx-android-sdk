package com.onyx.android.sdk.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import android.graphics.Bitmap;
import android.net.Uri;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
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

    @Test
    public void stringPathReadPreservesUtf8AndStripsLineSeparators() throws Exception {
        Path path = Files.createTempFile("onyx-file-utils", ".txt");
        try {
            Files.write(path, "café\nβeta".getBytes(StandardCharsets.UTF_8));

            assertEquals("caféβeta", FileUtils.readContentOfFile(path.toString()));
        }
        finally {
            Files.deleteIfExists(path);
        }
    }

    @Test
    public void recoveredFileReadersPreserveTheirDistinctContracts() throws Exception {
        Path path = Files.createTempFile("onyx-recovered-readers", ".txt");
        byte[] bytes = "first\n café \nβeta".getBytes(StandardCharsets.UTF_8);
        try {
            Files.write(path, bytes);

            assertArrayEquals(bytes, FileUtils.readBytesOfFile(path.toString()));
            assertEquals(Arrays.asList("first", " café ", "βeta"),
                    FileUtils.readStringListOfFile(path.toFile()));
            assertEquals("first café βeta", FileUtils.readContentFromFile(path.toString()));
        }
        finally {
            Files.deleteIfExists(path);
        }
    }

    @Test
    public void recoveredFileReadersRetainFailureValues() {
        File missing = new File("missing-" + System.nanoTime());

        assertNull(FileUtils.readBytesOfFile(missing.getAbsolutePath()));
        assertNull(FileUtils.readStringListOfFile(missing));
        assertEquals("", FileUtils.readContentFromFile(missing.getAbsolutePath()));
        assertEquals("", FileUtils.getRealFilePathFromUriByContentResolver(null, null, "_data"));
    }

    @Test
    public void bitmapCompressionFailureReturnsFalse() {
        Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        try {
            assertFalse(FileUtils.saveBitmapToMemoryFile(
                    bitmap, null, Bitmap.CompressFormat.PNG, 100));
        }
        finally {
            bitmap.recycle();
        }
    }

    @Test
    public void recoveredUriFileOperationsHandleFileAndUnknownSchemes() throws Exception {
        Path path = Files.createTempFile("onyx-uri-utils", ".txt");
        try {
            Files.write(path, new byte[] { 1, 2, 3 });
            Uri fileUri = Uri.fromFile(path.toFile());

            assertEquals(path.toString(), UriUtils.getRealFilePath(null, fileUri));
            assertEquals(3L, UriUtils.length(null, fileUri));
            assertEquals(0L, UriUtils.length(null, Uri.parse("relative-path")));
            assertEquals(0L, UriUtils.length(null, null));
        }
        finally {
            Files.deleteIfExists(path);
        }
    }
}
