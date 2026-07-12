package com.onyx.android.sdk.device;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class ObfuscatedFileHelperTest {
    @Rule public final TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void writesAndReadsUtf8WithoutAnOriginalJar() throws Exception {
        java.io.File destination = temporaryFolder.newFile("utf8.txt");
        assertTrue(a.a("recovered ✓", destination));
        assertEquals("recovered ✓", a.a(destination));
        assertEquals("recovered ✓", new String(
                Files.readAllBytes(destination.toPath()), StandardCharsets.UTF_8));
    }
}
