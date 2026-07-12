package com.onyx.recovery.validation;

import android.content.Context;

import com.onyx.android.sdk.common.MMKVBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

final class MmkvCompatibility {
    static final String STORE_ID = "onyx-mmkv-compat-1-0-19";
    private static final String SUITE = "mmkv-compat";
    private static final String ASSET_DIRECTORY = "mmkv-1.0.19";

    private MmkvCompatibility() {}

    static void writeFixture(Context context, ResultRecorder recorder) {
        try {
            File root = new File(context.getFilesDir(), "mmkv");
            deleteStore(root);
            MMKVBuilder builder = MMKVBuilder.init(context, STORE_ID);
            builder.putString("legacy_string", "onyx-mmkv-legacy");
            builder.putInt("legacy_int", -2_147_483_000);
            builder.putBoolean("legacy_boolean", true);
            builder.putFloat("legacy_float", 123.5f);
            builder.putLong("legacy_long", 9_876_543_210L);
            builder.putString("legacy_present", "present");
            builder.putString("legacy_removed", "remove-me");
            builder.remove("legacy_removed");
            builder.getMMKV().sync();
            recorder.value(SUITE, "fixture_written", null, ResultRecorder.map(
                    "storeId", STORE_ID,
                    "root", root.getAbsolutePath(),
                    "dataBytes", new File(root, STORE_ID).length(),
                    "crcBytes", new File(root, STORE_ID + ".crc").length()));
        } catch (Throwable error) {
            recorder.failure(SUITE, "fixture_written", error);
        }
    }

    static void verifyFixture(Context context, ResultRecorder recorder) {
        try {
            File root = new File(context.getFilesDir(), "mmkv");
            deleteStore(root);
            copyAsset(context, ASSET_DIRECTORY + "/" + STORE_ID, new File(root, STORE_ID));
            copyAsset(context, ASSET_DIRECTORY + "/" + STORE_ID + ".crc",
                    new File(root, STORE_ID + ".crc"));

            MMKVBuilder builder = MMKVBuilder.init(context, STORE_ID);
            requireEquals("legacy_string", "onyx-mmkv-legacy",
                    builder.getString("legacy_string", "missing"));
            requireEquals("legacy_int", -2_147_483_000,
                    builder.getInt("legacy_int", 0));
            requireEquals("legacy_boolean", true,
                    builder.getBoolean("legacy_boolean", false));
            requireEquals("legacy_float", 123.5f,
                    builder.getFloat("legacy_float", 0f));
            requireEquals("legacy_long", 9_876_543_210L,
                    builder.getLong("legacy_long", 0L));
            requireEquals("legacy_present", true, builder.containsKey("legacy_present"));
            requireEquals("legacy_removed", false, builder.containsKey("legacy_removed"));
            requireEquals("missing_default", "fallback",
                    builder.getString("missing_string", "fallback"));

            builder.putString("current_string", "mmkv-1.3.14");
            builder.putInt("current_int", 314);
            builder.putBoolean("current_boolean", false);
            builder.putFloat("current_float", -0.25f);
            builder.putLong("current_long", Long.MAX_VALUE - 14L);
            builder.putString("current_removed", "remove-me");
            builder.remove("current_removed");
            builder.getMMKV().sync();

            requireEquals("current_string", "mmkv-1.3.14",
                    builder.getString("current_string", "missing"));
            requireEquals("current_int", 314, builder.getInt("current_int", 0));
            requireEquals("current_boolean", false,
                    builder.getBoolean("current_boolean", true));
            requireEquals("current_float", -0.25f,
                    builder.getFloat("current_float", 0f));
            requireEquals("current_long", Long.MAX_VALUE - 14L,
                    builder.getLong("current_long", 0L));
            requireEquals("current_removed", false, builder.containsKey("current_removed"));

            recorder.value(SUITE, "legacy_store_read_and_extended", null, ResultRecorder.map(
                    "storeId", STORE_ID,
                    "legacyString", builder.getString("legacy_string", "missing"),
                    "legacyInt", builder.getInt("legacy_int", 0),
                    "legacyBoolean", builder.getBoolean("legacy_boolean", false),
                    "legacyFloat", builder.getFloat("legacy_float", 0f),
                    "legacyLong", builder.getLong("legacy_long", 0L),
                    "legacyPresent", builder.containsKey("legacy_present"),
                    "legacyRemoved", builder.containsKey("legacy_removed"),
                    "currentWriteRead", true,
                    "currentRemove", !builder.containsKey("current_removed")));
        } catch (Throwable error) {
            recorder.failure(SUITE, "legacy_store_read_and_extended", error);
        }
    }

    private static void copyAsset(Context context, String asset, File destination)
            throws IOException {
        File parent = destination.getParentFile();
        if (parent == null || (!parent.isDirectory() && !parent.mkdirs())) {
            throw new IOException("Could not create MMKV fixture directory: " + parent);
        }
        try (InputStream input = context.getAssets().open(asset);
             FileOutputStream output = new FileOutputStream(destination)) {
            byte[] buffer = new byte[8192];
            int count;
            while ((count = input.read(buffer)) != -1) {
                output.write(buffer, 0, count);
            }
        }
    }

    private static void deleteStore(File root) throws IOException {
        for (String suffix : new String[]{"", ".crc"}) {
            File file = new File(root, STORE_ID + suffix);
            if (file.exists() && !file.delete()) {
                throw new IOException("Could not delete stale MMKV file: " + file);
            }
        }
    }

    private static void requireEquals(String label, Object expected, Object actual) {
        boolean matches = expected instanceof Float && actual instanceof Float
                ? Float.compare((Float) expected, (Float) actual) == 0
                : expected.equals(actual);
        if (!matches) {
            throw new AssertionError(label + " expected " + expected + " but was " + actual);
        }
    }
}
