package com.onyx.android.sdk.recovered.base;

import java.text.DecimalFormat;

/** Bytecode-verified reconstruction of FileUtils.readableFileSize(long). */
public final class ReadableFileSize {
    private static final String[] UNITS = {"B", "KB", "MB", "GB", "TB"};

    private ReadableFileSize() {
        throw new UnsupportedOperationException("utility class");
    }

    public static String format(long size) {
        if (size <= 0L) {
            return "0";
        }
        double value = size;
        int digitGroup = (int) (Math.log10(value) / Math.log10(1024.0));
        return new DecimalFormat("#,##0.#").format(
                value / Math.pow(1024.0, digitGroup)) + " " + UNITS[digitGroup];
    }
}
