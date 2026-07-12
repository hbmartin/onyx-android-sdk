package com.onyx.android.sdk.device;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.Nullable;
import com.onyx.android.sdk.utils.Debug;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/** Original short-name file and URI helper retained for binary compatibility. */
class a {
    public static boolean a(String content, File destination) {
        try (FileOutputStream output = new FileOutputStream(destination)) {
            output.write(content.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (Exception error) {
            error.printStackTrace();
            return false;
        }
    }

    public static String a(File source) {
        try (FileInputStream input = new FileInputStream(source);
             InputStreamReader inputReader = new InputStreamReader(input, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(inputReader)) {
            String separator = System.getProperty("line.separator");
            StringBuilder content = new StringBuilder();
            boolean first = true;
            String line;
            while ((line = reader.readLine()) != null) {
                if (first) {
                    first = false;
                } else {
                    content.append(separator);
                }
                content.append(line);
            }
            return content.toString();
        } catch (IOException error) {
            error.printStackTrace();
            return null;
        }
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException error) {
                error.printStackTrace();
            }
        }
    }

    public static void a(Context context, File file) {
        MediaScannerConnection.scanFile(
                context,
                new String[]{file.getAbsolutePath()},
                null,
                new a$a());
    }

    @Nullable
    public static String b(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        if (!"content".equals(uri.getScheme())) {
            return uri.getPath();
        }
        return Build.VERSION.SDK_INT >= 23
                ? c(context, uri)
                : a(context, uri, "_data");
    }

    private static String c(Context context, Uri uri) {
        String authority = uri.getAuthority();
        return authority != null && authority.contains("fileprovider")
                ? a(context, uri)
                : a(context, uri, "_data");
    }

    private static String a(Context context, Uri uri) {
        try {
            String decodedPath = URLDecoder.decode(uri.getEncodedPath(), "UTF-8");
            String[] segments = decodedPath.split(File.separator);
            if (segments.length < 2) {
                return "";
            }
            String relativePath = decodedPath.substring(("/" + segments[1]).length());
            if ("root".equals(segments[1])) {
                return relativePath;
            }
            if ("external".equals(segments[1]) || "bluetooth".equals(segments[1])) {
                return Device.currentDevice().getExternalStorageDirectory().getAbsolutePath()
                        + relativePath;
            }
            return "";
        } catch (Exception error) {
            error.printStackTrace();
            return null;
        }
    }

    public static String a(Context context, Uri uri, String projectionName) {
        Cursor cursor = context.getContentResolver().query(
                uri, new String[]{projectionName}, null, null, null);
        if (cursor == null) {
            return "";
        }
        try {
            return cursor.moveToFirst() ? cursor.getString(0) : "";
        } finally {
            cursor.close();
        }
    }
}

/** Recovered media-scan callback. The dollar is part of the original binary name. */
final class a$a implements MediaScannerConnection.OnScanCompletedListener {
    @Override
    public void onScanCompleted(String path, Uri uri) {
        Debug.i(a.class,
                "file " + path + " was scanned successfully: " + uri,
                new Object[0]);
    }
}
