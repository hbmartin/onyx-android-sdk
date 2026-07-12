package com.onyx.android.sdk.api.device.screensaver;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ScreenSaverFileAlgorithmsTest {
    @Rule public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void recursivelyMeasuresFileBytes() throws Exception {
        java.io.File root = temporaryFolder.newFolder("screensavers");
        java.io.File nested = new java.io.File(root, "nested");
        Files.createDirectories(nested.toPath());
        Files.write(new java.io.File(root, "one.bin").toPath(), new byte[] {1, 2, 3});
        Files.write(new java.io.File(nested, "two.bin").toPath(), new byte[] {4, 5, 6, 7});

        assertEquals(7L, b.a(root));
    }

    @Test
    public void writesBmpHeaderIntegersLittleEndian() throws Exception {
        java.io.File output = temporaryFolder.newFile("header.bin");
        try (FileOutputStream stream = new FileOutputStream(output)) {
            a.a(stream, 0x1234);
            a.a(stream, 0x12345678L);
            a.b(stream, 0x0a0b0c0dL);
        }

        assertArrayEquals(new byte[] {
                0x34, 0x12,
                0x78, 0x56, 0x34, 0x12,
                0x0d, 0x0c, 0x0b, 0x0a
        }, Files.readAllBytes(output.toPath()));
    }
}
