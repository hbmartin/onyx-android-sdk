// 
// Decompiled by Procyon v0.6.0
// 

package com.onyx.android.sdk.api.device.screensaver;

import java.util.Locale;
import androidx.annotation.RequiresApi;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import com.onyx.android.sdk.utils.Debug;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.SimpleFileVisitor;
import java.util.concurrent.atomic.AtomicLong;
import java.nio.file.Path;
import java.nio.file.Paths;
import android.os.Build;
import com.onyx.android.sdk.api.utils.StringUtils;
import java.io.File;
import android.text.TextUtils;

class b
{
    public static final String a = ".";

    @SuppressWarnings("rawtypes")
    private static final class a extends SimpleFileVisitor {
        final AtomicLong a;

        a(final AtomicLong size) {
            this.a = size;
        }

        public FileVisitResult a(final Path file, final BasicFileAttributes attrs) {
            this.a.addAndGet(attrs.size());
            return FileVisitResult.CONTINUE;
        }

        public FileVisitResult b(final Path file, final IOException exc) {
            return FileVisitResult.CONTINUE;
        }

        public FileVisitResult a(final Path dir, final IOException exc) {
            if (exc != null) {
                Debug.d("had trouble traversing: " + dir + " (" + exc + ")");
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(final Object file, final BasicFileAttributes attrs)
                throws IOException {
            return a((Path) file, attrs);
        }

        @Override
        public FileVisitResult visitFileFailed(final Object file, final IOException exc)
                throws IOException {
            return b((Path) file, exc);
        }

        @Override
        public FileVisitResult postVisitDirectory(final Object dir, final IOException exc)
                throws IOException {
            return a((Path) dir, exc);
        }
    }
    
    public static boolean a(final String path) {
        return !TextUtils.isEmpty((CharSequence)path) && new File(path).exists();
    }
    
    public static String d(final String path) {
        return new File(path).getParent();
    }
    
    public static long c(final String filePath) {
        if (StringUtils.isNullOrEmpty(filePath)) {
            return 0L;
        }
        return a(new File(filePath));
    }
    
    public static long a(final File file) {
        if (Build.VERSION.SDK_INT >= 26) {
            return a(Paths.get(file.getAbsolutePath(), new String[0]));
        }
        return b(file);
    }
    
    private static long b(final File file) {
        if (file.isDirectory()) {
            long n = 0L;
            try {
                final File[] listFiles;
                if ((listFiles = file.listFiles()) == null) {
                    return 0L;
                }
                for (int length = listFiles.length, i = 0; i < length; ++i) {
                    final File file2;
                    long n2;
                    long n3;
                    if ((file2 = listFiles[i]).isDirectory()) {
                        n2 = n;
                        n3 = a(file2);
                    }
                    else {
                        n2 = n;
                        n3 = file2.length();
                    }
                    n = n2 + n3;
                }
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
            return n;
        }
        if (file.exists() && file.isFile()) {
            return file.length();
        }
        return -1L;
    }
    
    @RequiresApi(api = 26)
    private static long a(Path path) {
        final AtomicLong size = new AtomicLong(0L);
        try {
            Files.walkFileTree(path, new a(size));
        }
        catch (final IOException ex) {
            ex.printStackTrace();
        }
        return size.get();
    }
    
    public static String b(final String fileName) {
        if (StringUtils.isNullOrEmpty(fileName)) {
            return "";
        }
        final int lastIndex;
        if ((lastIndex = fileName.lastIndexOf(".")) >= 0) {
            return fileName.substring(lastIndex + 1).toLowerCase(Locale.getDefault());
        }
        return "";
    }
    
    public static boolean g(final String filePath) {
        return a(filePath, ".png");
    }
    
    public static boolean e(final String filePath) {
        return a(filePath, ".bmp");
    }
    
    public static boolean f(final String filePath) {
        return a(filePath, "jpg") || a(filePath, "jpeg");
    }
    
    private static boolean a(final String filePath, final String extension) {
        return !StringUtils.isNullOrEmpty(filePath) && !StringUtils.isNullOrEmpty(extension) && filePath.toLowerCase().endsWith(extension);
    }
}
