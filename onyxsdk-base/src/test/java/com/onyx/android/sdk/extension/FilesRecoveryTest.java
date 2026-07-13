package com.onyx.android.sdk.extension;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 35)
public class FilesRecoveryTest {
    @Test
    public void longFilenameReturnsTheUuidCandidateThatWasValidated() throws Exception {
        Path directory = java.nio.file.Files.createTempDirectory("onyx-unique-name");
        try {
            char[] characters = new char[260];
            Arrays.fill(characters, 'a');

            String result = Files.INSTANCE.generateUniqueFileName(
                    directory.toString(), new String(characters), "txt", 1);

            assertTrue(result.length() <= 254);
            assertTrue(result.endsWith(".txt"));
            assertFalse(result.startsWith("aaaa"));
        }
        finally {
            java.nio.file.Files.deleteIfExists(directory);
        }
    }

    @Test
    public void uniqueFilenameAddsACounterAfterCollision() throws Exception {
        Path directory = java.nio.file.Files.createTempDirectory("onyx-name-collision");
        Path existing = directory.resolve("note.txt");
        try {
            java.nio.file.Files.createFile(existing);

            assertEquals("note(1).txt", Files.INSTANCE.generateUniqueFileName(
                    directory.toString(), "note", "txt", 1));
        }
        finally {
            java.nio.file.Files.deleteIfExists(existing);
            java.nio.file.Files.deleteIfExists(directory);
        }
    }

    @Test
    public void recoveredReaderRetainsItsLineBreakContract() throws Exception {
        Path file = java.nio.file.Files.createTempFile("onyx-extension-files", ".txt");
        try {
            java.nio.file.Files.write(file, "first\nsecond\n\n".getBytes(StandardCharsets.UTF_8));

            assertEquals("firstsecond", Files.INSTANCE.readContentOfFile(file.toString(), false));
            assertEquals("first\nsecond", Files.INSTANCE.readContentOfFile(file.toString(), true));
        }
        finally {
            java.nio.file.Files.deleteIfExists(file);
        }
    }

    @Test
    public void copyInputStreamOwnsAndClosesTheSource() throws Exception {
        Path destination = java.nio.file.Files.createTempFile("onyx-stream-copy", ".bin");
        CloseTrackingInputStream source = new CloseTrackingInputStream(new byte[] { 1, 2, 3 });
        try {
            assertEquals(3L, Files.INSTANCE.copyInputStreamToFile(source, destination.toFile()));
            assertTrue(source.closed);
            assertArrayEquals(new byte[] { 1, 2, 3 }, java.nio.file.Files.readAllBytes(destination));
        }
        finally {
            java.nio.file.Files.deleteIfExists(destination);
        }
    }

    @Test
    public void nonPositiveChunkSizeFallsBackToCalculatedChunking() {
        List<Integer> results = Files.INSTANCE.parallelFileMetadata(
                Arrays.asList(1, 2, 3), 0, value -> value * 2);

        assertEquals(new HashSet<>(Arrays.asList(2, 4, 6)), new HashSet<>(results));
    }

    @Test
    public void filteredDeletionLeavesUnselectedChildrenAndParent() throws Exception {
        Path directory = java.nio.file.Files.createTempDirectory("onyx-filter-delete");
        Path delete = directory.resolve("delete.me");
        Path keep = directory.resolve("keep.me");
        try {
            java.nio.file.Files.createFile(delete);
            java.nio.file.Files.createFile(keep);

            Files.INSTANCE.deleteDirectoryQuietly(
                    directory.toFile(), null, file -> file.getName().startsWith("delete"));

            assertFalse(java.nio.file.Files.exists(delete));
            assertTrue(java.nio.file.Files.exists(keep));
            assertTrue(java.nio.file.Files.exists(directory));
        }
        finally {
            java.nio.file.Files.deleteIfExists(delete);
            java.nio.file.Files.deleteIfExists(keep);
            java.nio.file.Files.deleteIfExists(directory);
        }
    }

    @Test
    public void diskSynchronizedSaveReplacesContentAndCleansTemporaryFile() throws Exception {
        Path directory = java.nio.file.Files.createTempDirectory("onyx-disk-sync");
        File target = directory.resolve("content.txt").toFile();
        try {
            java.nio.file.Files.write(target.toPath(), "old".getBytes(StandardCharsets.UTF_8));

            assertTrue(Files.INSTANCE.saveContentWithDiskSync("new café", target));
            assertEquals("new café", new String(
                    java.nio.file.Files.readAllBytes(target.toPath()), StandardCharsets.UTF_8));
            assertFalse(new File(target.getAbsolutePath() + ".tmp").exists());
        }
        finally {
            java.nio.file.Files.deleteIfExists(target.toPath());
            java.nio.file.Files.deleteIfExists(directory);
        }
    }

    private static final class CloseTrackingInputStream extends ByteArrayInputStream {
        private boolean closed;

        private CloseTrackingInputStream(byte[] contents) {
            super(contents);
        }

        @Override
        public void close() throws java.io.IOException {
            closed = true;
            super.close();
        }
    }
}
