package com.onyx.android.sdk.device;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/** Executes the R8-renamed com.onyx.android.sdk.device.a file-I/O path. */
class ObfuscatedCodePathTest {
    @TempDir java.nio.file.Path temporaryDirectory;

    @Test
    void obfuscatedClassAWritesAndReadsUtf8() throws Exception {
        var destination = temporaryDirectory.resolve("obfuscated.txt").toFile();
        assertTrue(a.a("recovered ✓", destination));
        assertEquals("recovered ✓", a.a(destination));
        assertEquals("recovered ✓", new String(
                Files.readAllBytes(destination.toPath()), StandardCharsets.UTF_8));
    }
}
