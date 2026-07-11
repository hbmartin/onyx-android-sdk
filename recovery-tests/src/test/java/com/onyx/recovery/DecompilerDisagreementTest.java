package com.onyx.recovery;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.onyx.android.sdk.recovered.base.ReadableFileSize;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.List;
import org.junit.jupiter.api.Test;

class DecompilerDisagreementTest {
    private static final Path ROOT = Paths.get(System.getProperty("recovery.root"));

    @Test
    void documentedReadableFileSizeReconstructionProducesExpectedValues() {
        assertEquals("0", ReadableFileSize.format(-1L));
        assertEquals("0", ReadableFileSize.format(0L));
        assertEquals("1 B", ReadableFileSize.format(1L));
        assertEquals("1,023 B", ReadableFileSize.format(1023L));
        assertEquals("1 KB", ReadableFileSize.format(1024L));
        assertEquals("1.5 KB", ReadableFileSize.format(1536L));
        assertEquals("1 MB", ReadableFileSize.format(1L << 20));
        assertEquals("1 TB", ReadableFileSize.format(1L << 40));
    }

    @Test
    void disagreementDocumentNamesAllNineResolvedSites() throws Exception {
        String document = read(ROOT.resolve("docs/DECOMPILER_DISAGREEMENTS.md"));
        assertTrue(document.contains("`FileUtils.readableFileSize`"));
        assertTrue(document.contains("`RxUtils$d.run`"));
        assertEquals(2, occurrences(document, "| `RK32XXDevice` |"));
        assertEquals(1, occurrences(document, "| `IMX6Device` |"));
        assertEquals(1, occurrences(document, "| `RK33XXDevice` |"));
        assertEquals(2, occurrences(document, "| `SDMDevice` |"));
        assertEquals(1, occurrences(document, "| `RK31XXDevice` |"));
    }

    @Test
    void recoveredSourcesRetainDiagnosticsButNotFakeMethodBodies() throws Exception {
        List<Path> repairedFiles = List.of(
                ROOT.resolve("recovery-evidence/decompilers/base/jadx/com/onyx/android/sdk/utils/FileUtils.java"),
                ROOT.resolve("recovery-evidence/decompilers/base/jadx/com/onyx/android/sdk/rx/RxUtils.java"),
                ROOT.resolve("recovery-evidence/decompilers/device/jadx/com/onyx/android/sdk/device/RK32XXDevice.java"),
                ROOT.resolve("recovery-evidence/decompilers/device/jadx/com/onyx/android/sdk/device/IMX6Device.java"),
                ROOT.resolve("recovery-evidence/decompilers/device/jadx/com/onyx/android/sdk/device/RK33XXDevice.java"),
                ROOT.resolve("recovery-evidence/decompilers/device/jadx/com/onyx/android/sdk/device/SDMDevice.java"),
                ROOT.resolve("recovery-evidence/decompilers/device/jadx/com/onyx/android/sdk/device/RK31XXDevice.java"));

        for (Path source : repairedFiles) {
            String text = read(source);
            assertFalse(text.contains("UnsupportedOperationException(\"Method not decompiled"), source.toString());
            assertFalse(text.contains("throw new IllegalStateException(\"Decompilation failed\")"), source.toString());
        }
    }

    @Test
    void repairedSourceBodiesContainEveryBytecodeVerifiedCall() throws Exception {
        Path deviceRoot = ROOT.resolve(
                "recovery-evidence/decompilers/device/jadx/com/onyx/android/sdk/device");
        Map<String, List<String>> expectedCalls = Map.of(
                "RK32XXDevice.java", List.of(
                        "ReflectUtil.invokeMethodSafely(b0, null, new Object[0]);",
                        "ReflectUtil.invokeMethodSafely(m0, null, new Object[0]);"),
                "IMX6Device.java", List.of(
                        "ReflectUtil.invokeMethodSafely(g0, null, new Object[0]);"),
                "RK33XXDevice.java", List.of(
                        "ReflectUtil.invokeMethodSafely(g0, null, new Object[0]);"),
                "SDMDevice.java", List.of(
                        "ReflectUtil.invokeMethodSafely(j0, null, new Object[0]);",
                        "ReflectUtil.invokeMethodSafely(u0, null, new Object[0]);"),
                "RK31XXDevice.java", List.of(
                        "ReflectUtil.invokeMethodSafely(i0, null, new Object[0]);"));

        for (var entry : expectedCalls.entrySet()) {
            String source = read(deviceRoot.resolve(entry.getKey()));
            for (String call : entry.getValue()) {
                assertTrue(source.contains(call), entry.getKey() + " missing " + call);
            }
        }

        String fileUtils = read(ROOT.resolve(
                "recovery-evidence/decompilers/base/jadx/com/onyx/android/sdk/utils/FileUtils.java"));
        assertTrue(fileUtils.contains("readableFileSize(long size)"));
        assertTrue(fileUtils.contains("new DecimalFormat(\"#,##0.#\")"));
        assertTrue(fileUtils.contains("Math.pow(1024.0d, digitGroup)"));

        String rxUtils = read(ROOT.resolve(
                "recovery-evidence/decompilers/base/jadx/com/onyx/android/sdk/rx/RxUtils.java"));
        assertTrue(rxUtils.contains("this.a.run();"));
        assertTrue(rxUtils.contains("catch (Exception e)"));
        assertTrue(rxUtils.contains("finally"));
        assertTrue(rxUtils.contains("this.b.dispose();"));
    }

    private static String read(Path path) throws Exception {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

    private static int occurrences(String text, String fragment) {
        int count = 0;
        int index = 0;
        while ((index = text.indexOf(fragment, index)) >= 0) {
            count++;
            index += fragment.length();
        }
        return count;
    }
}
