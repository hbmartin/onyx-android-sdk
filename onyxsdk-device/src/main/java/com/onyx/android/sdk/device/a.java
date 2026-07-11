package com.onyx.android.sdk.device;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Source reconstruction of the R8-obfuscated file helper. The short class and
 * method names are retained because callers in the recovered bytecode used them.
 */
final class a {
    private a() {
        throw new UnsupportedOperationException("utility class");
    }

    public static boolean a(String content, File destination) {
        try (FileOutputStream output = new FileOutputStream(destination)) {
            output.write(content.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }

    public static String a(File source) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(source), StandardCharsets.UTF_8))) {
            String lineSeparator = System.lineSeparator();
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                if (result.length() > 0) {
                    result.append(lineSeparator);
                }
                result.append(line);
            }
            return result.toString();
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
