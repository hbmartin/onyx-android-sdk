package com.onyx.android.sdk.data.file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/* JADX INFO: loaded from: classes.jar:com/onyx/android/sdk/data/file/StreamProvider.class */
public class StreamProvider {
    private static final boolean a = false;
    private static final BaseStream b;

    private static boolean a() {
        return false;
    }

    public static FileOutputStream getOutputStream(String filePath) throws FileNotFoundException {
        return getOutputStream(filePath, a());
    }

    static {
        b = a() ? new MediaStoreStream() : new FileApiStream();
    }

    public static FileOutputStream getOutputStream(String filePath, boolean clearFile) throws FileNotFoundException {
        return b.getOutputStream(filePath, clearFile);
    }
}
