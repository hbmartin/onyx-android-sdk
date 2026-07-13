
package com.onyx.android.sdk.utils;

import java.nio.channels.spi.AbstractInterruptibleChannel;
import android.os.Bundle;
import java.util.regex.Matcher;
import java.nio.channels.WritableByteChannel;
import android.util.Base64;
import android.system.Os;
import android.os.Process;
import com.onyx.android.sdk.rx.RxUtils;
import java.nio.charset.StandardCharsets;
import androidx.annotation.NonNull;
import java.io.FilenameFilter;
import com.onyx.android.sdk.data.SortBy;
import androidx.annotation.RequiresApi;
import java.nio.file.FileVisitor;
import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.SimpleFileVisitor;
import java.util.concurrent.atomic.AtomicLong;
import android.media.MediaScannerConnection;
import java.util.Collections;
import com.onyx.android.sdk.data.SortOrder;
import java.util.LinkedList;
import java.nio.file.DirectoryStream;
import io.reactivex.functions.Consumer;
import com.onyx.android.sdk.commons.io.IOUtils;
import java.io.OutputStream;
import java.text.DecimalFormat;
import com.onyx.android.sdk.common.provider.OnyxFileProviderUtil;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;
import android.annotation.SuppressLint;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.io.FileNotFoundException;
import android.os.Environment;
import android.provider.DocumentsContract;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import com.onyx.android.sdk.device.Device;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.Optional;
import android.content.Intent;
import androidx.annotation.WorkerThread;
import java.util.ArrayList;
import android.net.Uri;
import android.os.MemoryFile;
import android.graphics.Bitmap;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.io.RandomAccessFile;
import java.io.FileDescriptor;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.Locale;
import android.os.ParcelFileDescriptor;
import java.util.Map;
import java.util.Iterator;
import java.io.Closeable;
import android.database.Cursor;
import androidx.annotation.Nullable;
import java.util.Arrays;
import java.util.Stack;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicBoolean;
import com.onyx.android.sdk.device.EnvironmentUtil;
import java.util.Collection;
import java.util.Set;
import com.onyx.android.sdk.commons.io.FilenameUtils;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.Files;
import java.nio.file.Paths;
import android.os.Build;
import android.content.res.AssetManager;
import java.io.IOException;
import android.content.Context;
import java.io.File;
import java.io.ByteArrayOutputStream;
import java.util.regex.Pattern;
import java.util.List;

public class FileUtils
{
    private static final String a = "FileUtils";
    private static final String b = "com.microsoft.office.onenote.filename";
    private static final String c = "com.android.externalstorage.documents";
    private static List<String> d;
    private static List<String> e;
    public static final String LINE_SEPARATOR = "line.separator";
    public static final String FILE_EXTENSION_CHAR = ".";
    public static final String NO_MEDIA_FILE = ".nomedia";
    public static final String ANDROID_DIR_PATH;
    public static final int MAX_FILE_NAME_LENGTH = 254;
    public static final String[] IMG_EXTENSION;
    public static final Pattern JD_SHOP_PATH_PATTERN;
    
    public static boolean fileExist(final String path) {
        return !StringUtils.isNullOrEmpty(path) && new File(path).exists();
    }
    
    public static boolean fileExist(final File file) {
        return file != null && file.exists();
    }
    
    public static boolean assetFileExist(final Context context, final String path) {
        return assetFileExist(context, new File(path));
    }
    
    public static boolean assetFileExist(final Context context, final File file) {
        if (file == null) {
            return false;
        }
        final AssetManager assets = context.getAssets();
        final String parent;
        String s;
        if ((parent = file.getParent()) == null) {
            s = "";
        }
        else {
            s = parent;
        }
        try {
            String[] list;
            if ((list = assets.list(s)) == null) {
                list = new String[0];
            }
            for (int length = list.length, i = 0; i < length; ++i) {
                if (StringUtils.safelyEquals(parent + File.separator + list[i], file.getPath())) {
                    return true;
                }
            }
            return false;
        }
        catch (final IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public static boolean isEmpty(final String path) {
        return !fileExist(path) || getFileSize(path) <= 0L;
    }
    
    public static boolean fileCanRead(final String path) {
        final File file;
        return !StringUtils.isNullOrEmpty(path) && (file = new File(path)).exists() && file.canRead();
    }
    
    public static boolean fileCanWrite(final String path) {
        final File file;
        return !StringUtils.isNullOrEmpty(path) && (file = new File(path)).exists() && file.canWrite();
    }
    
    public static boolean isDirectory(final String path) {
        return !StringUtils.isNullOrEmpty(path) && new File(path).isDirectory();
    }
    
    public static boolean isFile(final String path) {
        return !StringUtils.isNullOrEmpty(path) && new File(path).isFile();
    }
    
    public static boolean isSymlink(final File file) {
        try {
            if (!StringUtils.safelyEquals(file.getAbsolutePath(), file.getCanonicalPath())) {
                return true;
            }
        }
        catch (final IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public static boolean createSymbolicLink(final String targetPath, final String linkPath) {
        if (StringUtils.isBlank(targetPath) || StringUtils.isBlank(linkPath)
                || Build.VERSION.SDK_INT < 26) return false;
        try {
            Path target = Paths.get(targetPath);
            Path link = Paths.get(linkPath);
            Files.deleteIfExists(link);
            Path symbolicLink = Files.createSymbolicLink(link, target);
            Debug.e(FileUtils.a, "symbolicLink path: " + symbolicLink, new Object[0]);
            return true;
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
    }
    
    public static boolean mkdirs(final String path) {
        return mkdirs(new File(path));
    }
    
    public static boolean mkdirs(final File file) {
        if (file.exists()) {
            return file.isDirectory();
        }
        return file.mkdirs();
    }
    
    public static void purgeDirectory(final File dir) {
        final File[] listFiles;
        if ((listFiles = dir.listFiles()) != null && listFiles.length > 0) {
            File[] listFiles2;
            for (int length = (listFiles2 = dir.listFiles()).length, i = 0; i < length; ++i) {
                final File file;
                if ((file = listFiles2[i]).isFile()) {
                    file.delete();
                }
            }
        }
    }
    
    public static String getAbsolutePath(final File file) {
        if (file == null) {
            return "";
        }
        return file.getAbsolutePath();
    }
    
    public static String getFileExtension(final String fileName) {
        if (StringUtils.isNullOrEmpty(fileName)) {
            return "";
        }
        return StringUtils.toLowerCase(FilenameUtils.getExtension(fileName));
    }
    
    public static String getFileExtensionUpperCase(final String fileName) {
        if (StringUtils.isNullOrEmpty(fileName)) {
            return "";
        }
        return StringUtils.toUpperCase(FilenameUtils.getExtension(fileName));
    }
    
    public static String getFileExtension(final File file) {
        return getFileExtension(file.getName());
    }
    
    public static boolean isPngExtension(final File file) {
        return org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)getAbsolutePath(file), (CharSequence)".png");
    }
    
    public static boolean isJpgExtension(final File file) {
        return isJpgExtension(getAbsolutePath(file));
    }
    
    public static boolean isJpgExtension(final String filePath) {
        return org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)filePath, (CharSequence)".jpg") || org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)filePath, (CharSequence)".jpeg");
    }
    
    public static boolean isJEB(final String fileName) {
        return org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".jeb");
    }
    
    public static boolean isWav(final String fileName) {
        return org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".wav");
    }
    
    public static void collectFiles(final String parentPath, final Set<String> extensionFilters, final boolean recursive, final Collection<String> fileList) {
        collectFiles(parentPath, extensionFilters, recursive, fileList, false);
    }
    
    public static void collectFiles(final String parentPath, final Set<String> extensionFilters, final boolean recursive, final Collection<String> fileList, final boolean checkNoMedia) {
        final File directory;
        final File[] listFiles;
        if ((listFiles = (directory = new File(parentPath)).listFiles()) == null || listFiles.length <= 0) {
            return;
        }
        if (checkNoMedia && containsNoMedia(directory)) {
            return;
        }
        for (int length = listFiles.length, i = 0; i < length; ++i) {
            final File file;
            if (!(file = listFiles[i]).isHidden()) {
                final File file2 = file;
                final String absolutePath = file2.getAbsolutePath();
                if (file2.isFile()) {
                    final String fileExtension = getFileExtension(absolutePath);
                    if (extensionFilters == null || extensionFilters.contains(fileExtension)) {
                        fileList.add(absolutePath);
                    }
                }
                else if (file.isDirectory() && recursive) {
                    collectFiles(absolutePath, extensionFilters, recursive, fileList, checkNoMedia);
                }
            }
        }
    }
    
    public static boolean isNoMediaFile(final File file) {
        return fileExist(file) && !EnvironmentUtil.isStorageRootDirectory(file.getAbsolutePath()) && ((file.isDirectory() && containsNoMedia(file)) || isNoMediaFile(file.getParentFile()));
    }
    
    public static boolean containsNoMedia(final File directory) {
        return directory != null && !directory.isFile() && fileExist(directory.getAbsoluteFile() + File.separator + ".nomedia");
    }
    
    public static boolean containsNoMedia(final File[] files) {
        if (files == null) {
            return false;
        }
        for (int length = files.length, i = 0; i < length; ++i) {
            if (files[i].getName().equalsIgnoreCase(".nomedia")) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isMacOsxPath(final String fileName) {
        return !StringUtils.isNullOrEmpty(fileName) && fileName.contains("__MACOSX");
    }
    
    public static void collectDirs(final String parentPath, final boolean recursive, final Collection<String> dirList) {
        final File[] listFiles;
        if ((listFiles = new File(parentPath).listFiles()) != null && listFiles.length > 0) {
            for (int length = listFiles.length, i = 0; i < length; ++i) {
                final File file;
                if (!(file = listFiles[i]).isHidden()) {
                    if (!file.isFile()) {
                        dirList.add(file.getAbsolutePath());
                        if (recursive) {
                            collectDirs(file.getAbsolutePath(), recursive, dirList);
                        }
                    }
                }
            }
        }
    }
    
    public static void collectFileTree(File rootFile, final List<File> flattenedFileList, final AtomicBoolean abortHolder) {
        if (!rootFile.exists()) {
            return;
        }
        final Comparator<File> c = new Comparator<File>() {
            public int compare(final File lhs, final File rhs) {
                return -lhs.getName().compareToIgnoreCase(rhs.getName());
            }
        };
        final Stack<File> stack;
        (stack = new Stack<File>()).push(rootFile);
        while (!stack.isEmpty()) {
            if (abortHolder.get()) {
                return;
            }
            flattenedFileList.add(rootFile = stack.pop());
            if (!rootFile.isDirectory()) {
                continue;
            }
            final File[] listFiles;
            if ((listFiles = rootFile.listFiles()) == null) {
                continue;
            }
            final File[] a = listFiles;
            Arrays.sort(a, c);
            for (int length = a.length, i = 0; i < length; ++i) {
                final File item;
                if (!(item = listFiles[i]).getName().equals(".") && !item.getName().equals("..")) {
                    stack.push(item);
                }
            }
        }
    }
    
    public static String getParent(final String path) {
        return new File(path).getParent();
    }
    
    @Nullable
    public static String getParentName(String path) {
        if ((path = getParent(path)) == null) {
            return null;
        }
        return getBaseName(path);
    }
    
    public static String getFileName(final String path) {
        if (StringUtils.isNullOrEmpty(path)) {
            return "";
        }
        return new File(path).getName();
    }
    
    public static String getBaseName(final File file) {
        if (file == null) {
            return "";
        }
        if (file.isDirectory()) {
            return file.getName();
        }
        return getFileBaseName(file.getName());
    }
    
    public static String getBaseName(final String path) {
        if (StringUtils.isNullOrEmpty(path)) {
            return "";
        }
        return getBaseName(new File(path));
    }
    
    public static String getFileBaseName(final String fileName) {
        if (StringUtils.isNullOrEmpty(fileName)) {
            return null;
        }
        final int lastIndex;
        if ((lastIndex = fileName.lastIndexOf(46)) < 0) {
            return fileName;
        }
        return fileName.substring(0, lastIndex);
    }
    
    public static String getLogPath(String path) {
        if (!Debug.getDebug() && !StringUtils.isNullOrEmpty(path) && !isDirectory(path)) {
            final String s = path;
            final String baseName = getBaseName(s);
            final String parent;
            String string;
            if (StringUtils.isNullOrEmpty(parent = getParent(s))) {
                string = "";
            }
            else {
                string = parent + File.separator;
            }
            if (StringUtils.isNullOrEmpty(path = getFileExtension(path))) {
                path = "";
            }
            else {
                path = "." + path;
            }
            return string + computeMD5(baseName) + path;
        }
        return path;
    }
    
    public static void closeQuietly(final Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            }
            catch (final Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void closeQuietly(final Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            }
            catch (final IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static <T extends Closeable> void closeList(final List<T> closeableList) {
        if (CollectionUtils.isNullOrEmpty(closeableList)) {
            return;
        }
        final Iterator<T> iterator = closeableList.iterator();
        while (iterator.hasNext()) {
            closeQuietly(iterator.next());
        }
    }
    
    public static <K, V extends Closeable> void closeMap(final Map<K, V> closeableMap) {
        final Iterator<Map.Entry<K, V>> iterator = closeableMap.entrySet().iterator();
        while (iterator.hasNext()) {
            closeQuietly(((Map.Entry<K, Closeable>)iterator.next()).getValue());
        }
    }
    
    public static void closeQuietly(final ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor != null) {
            try {
                parcelFileDescriptor.close();
            }
            catch (final IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static String canonicalPath(final String ref, String path) {
        final int lastIndex;
        if ((lastIndex = ref.lastIndexOf(47)) > 0 && path.indexOf(47) < 0) {
            path = ref.substring(0, lastIndex + 1) + path;
        }
        return path;
    }
    
    public static long getLastChangeTime(final File file) {
        return file.lastModified();
    }
    
    public static boolean isImageFile(String fileName) {
        return org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)(fileName = fileName.toLowerCase(Locale.getDefault())), (CharSequence)".bmp") || org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".jpg") || org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".jpeg") || org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".png") || org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".gif") || org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".webp") || org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".svg");
    }
    
    public static boolean isFontFile(String fileName) {
        return org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)(fileName = fileName.toLowerCase(Locale.getDefault())), (CharSequence)".ttf") || org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".otf") || org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".ttc");
    }
    
    public static boolean isGifFile(final String fileName) {
        return org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".gif");
    }
    
    public static boolean isPdfFile(final String fileName) {
        return org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".pdf");
    }
    
    public static boolean isEpubFile(final String fileName) {
        return org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".epub");
    }
    
    public static boolean isMobiFile(final String fileName) {
        return org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".mobi");
    }
    
    public static boolean isOffice(final String fileName) {
        return org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".docx") || org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".docm") || org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".xlsx") || org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".xlsm") || org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".pptx") || org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".pptm") || org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".ppsx");
    }
    
    public static boolean isTxtFile(final String fileName) {
        return org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".txt");
    }
    
    public static boolean isZipFile(final String fileName) {
        return org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".zip") || org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".cbz");
    }
    
    public static boolean isRarFile(final String fileName) {
        return org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".rar") || org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".cbr");
    }
    
    public static boolean isZipFileHeader(final String path) {
        return org.apache.commons.lang3.StringUtils.startsWithIgnoreCase((CharSequence)getFileHeader(path), (CharSequence)"504b0304");
    }
    
    public static boolean isXmlFile(final String fileName) {
        return org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".xml");
    }
    
    public static boolean isOpmlFile(final String fileName) {
        return org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)".opml");
    }
    
    public static boolean isCertFile(final String fileName) {
        return org.apache.commons.lang3.StringUtils.endsWithIgnoreCase((CharSequence)fileName, (CharSequence)"pem");
    }
    
    public static String getFileHeader(final String path) {
        try (FileInputStream input = new FileInputStream(path)) {
            byte[] header = new byte[4];
            input.read(header, 0, header.length);
            return hexToString(header);
        } catch (final Exception error) {
            error.printStackTrace();
            return "";
        }
    }
    
    public static String readContentOfFile(final File fileForRead) {
        Log.d(a, "readContentOfFile(file) path=" + String.valueOf(fileForRead));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(fileForRead), StandardCharsets.UTF_8))) {
            String separator = System.getProperty(LINE_SEPARATOR);
            StringBuilder content = new StringBuilder();
            boolean firstLine = true;
            String line;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                }
                else {
                    content.append(separator);
                }
                content.append(line);
            }
            return content.toString();
        }
        catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static boolean saveContentToFile(final String content, final File fileForSave) {
        try (FileOutputStream output = new FileOutputStream(fileForSave)) {
            output.write(content.getBytes("UTF-8"));
            return true;
        } catch (Exception failure) {
            failure.printStackTrace();
            return false;
        }
    }
    
    public static boolean saveContentWithSync(final String content, final File file) {
        try (FileOutputStream output = new FileOutputStream(file)) {
            output.write("0\n".getBytes());
            output.flush();
            output.write(("+" + content + "\n").getBytes());
            output.flush();
            output.getFD().sync();
            return true;
        } catch (Exception exception) {
            Log.e(FileUtils.a, "saveContentWithSync error: " + exception.getMessage());
            return false;
        }
    }
    
    public static boolean appendContentToFile(final String content, final File fileForSave) {
        boolean appendContentToFile;
        try {
            appendContentToFile = appendContentToFile(content.getBytes("utf-8"), fileForSave);
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            appendContentToFile = false;
        }
        return appendContentToFile;
    }
    
    public static boolean truncateFile(String filePath, final long size) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                FileChannel channel = randomAccessFile.getChannel()) {
            channel.truncate(size);
            return true;
        } catch (final Exception error) {
            error.printStackTrace();
            return false;
        }
    }
    
    public static boolean appendContentToFile(final String filePath, final byte[] appendData, final int size) {
        final File file = new File(filePath);
        ensureFileExists(filePath);
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                FileChannel channel = randomAccessFile.getChannel()) {
            channel.write(ByteBuffer.wrap(appendData, 0, size), getFileSize(filePath));
            return true;
        } catch (final Exception error) {
            error.printStackTrace();
            return false;
        }
    }
    
    public static boolean appendContentToFile(final byte[] appendData, final File fileForSave) {
        try (FileOutputStream output = new FileOutputStream(fileForSave, true)) {
            output.write(appendData);
            return true;
        } catch (Exception failure) {
            failure.printStackTrace();
            return false;
        }
    }
    
    public static byte[] readFileData(final String filePath, final long offset, final int length) {
        byte[] data = new byte[length];
        try (RandomAccessFile file = new RandomAccessFile(new File(filePath), "rw");
             FileChannel channel = file.getChannel()) {
            ByteBuffer mapped = channel.map(FileChannel.MapMode.READ_WRITE, offset, length);
            mapped.get(data);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        return data;
    }
    
    public static boolean saveContentToFile(final byte[] data, String filePath, final int offset, final int size) {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(new File(filePath), "rw");
                FileChannel channel = randomAccessFile.getChannel()) {
            channel.write(ByteBuffer.wrap(data, offset, size));
            return true;
        } catch (final Exception error) {
            error.printStackTrace();
            return false;
        }
    }
    
    public static boolean saveContentToFile(final byte[] data, final File fileForSave) {
        try (FileOutputStream output = new FileOutputStream(fileForSave)) {
            output.write(data);
            return true;
        }
        catch (Exception ignored) {
            return false;
        }
    }
    
    public static boolean saveBitmapToFile(final Bitmap bitmap, final File fileForSave, final Bitmap.CompressFormat format, final int quality) {
        File parent = fileForSave.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (OutputStream output = com.onyx.android.sdk.data.file.StreamProvider
                .getOutputStream(fileForSave.getAbsolutePath())) {
            bitmap.compress(format, quality, output);
            output.flush();
            return true;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
    
    public static boolean saveBitmapToMemoryFile(final Bitmap bitmap, final MemoryFile fileForSave, final Bitmap.CompressFormat format, final int quality) {
        try (OutputStream output = fileForSave.getOutputStream()) {
            bitmap.compress(format, quality, output);
            return true;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
    
    @Nullable
    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (uri == null) {
            return null;
        }
        try {
            return "content".equals(uri.getScheme())
                    ? (Build.VERSION.SDK_INT >= 23
                            ? c(context, uri)
                            : getRealFilePathFromUriByContentResolver(context, uri, "_data"))
                    : uri.getPath();
        } catch (Throwable error) {
            Debug.e(error);
            return null;
        }
    }
    
    @WorkerThread
    public static List<String> getFilePathFromUrisWithCopy(final Context context, final List<Uri> uris) {
        final ArrayList list = new ArrayList();
        final Iterator<Uri> iterator = uris.iterator();
        while (iterator.hasNext()) {
            final Uri uri;
            if ((uri = iterator.next()) == null) {
                continue;
            }
            final String realFilePathFromUri;
            if (fileCanRead(realFilePathFromUri = getRealFilePathFromUri(context, uri))) {
                list.add(realFilePathFromUri);
            }
            else {
                final String decodePath;
                if (!StringUtils.isNullOrEmpty(decodePath = SharePathDecoder.decodePath(uri))) {
                    list.add(decodePath);
                }
                final String a = FileUtils.a;
                final Object[] array2;
                final Object[] array = array2 = new Object[2];
                array[0] = realFilePathFromUri;
                array[1] = uri;
                Debug.i(a, "can't get readable filePath(%s) from uri(%s)", array2);
            }
        }
        return list;
    }
    
    public static String getDownloadFilePathFromName(String displayName) {
        displayName = buildValidFatFilename(displayName);
        return new File(EnvironmentUtil.getExternalStorageDownloadDirectory().getPath(), displayName).getPath();
    }
    
    public static boolean isContentSchemeFromUri(final Uri uri) {
        return uri != null && StringUtils.isEquals(uri.getScheme(), "content");
    }
    
    public static String getFileNameFromIntent(final Context context, final Intent intent) {
        String string;
        if (StringUtils.isNullOrEmpty(string = a(intent))) {
            string = getDisplayNameFromUri(context, intent.getData());
        }
        if (StringUtils.isNullOrEmpty(string)) {
            string = Optional.ofNullable(intent.getData()).map((Function<? super Uri, ? extends String>)Uri::getLastPathSegment).orElse(null);
        }
        if (StringUtils.isNullOrEmpty(string) && intent.getAction() == "android.intent.action.SEND") {
            Object stream = intent.getExtras() == null ? null : intent.getExtras().getParcelable("android.intent.extra.STREAM");
            string = stream instanceof Uri ? ((Uri) stream).getLastPathSegment() : null;
        }
        if (StringUtils.isNullOrEmpty(string)) {
            String extensionFromMimeType;
            if (StringUtils.isNullOrEmpty(extensionFromMimeType = MimeTypeUtils.getExtensionFromMimeType(intent.getType()))) {
                extensionFromMimeType = "txt";
            }
            string = System.currentTimeMillis() + "-tmp." + extensionFromMimeType;
        }
        return string;
    }
    
    private static String a(final Intent intent) {
        final Iterator<String> iterator = FileUtils.d.iterator();
        while (iterator.hasNext()) {
            final String s;
            if (intent.hasExtra(s = iterator.next())) {
                return intent.getStringExtra(s);
            }
        }
        return "";
    }
    
    public static String getDisplayNameFromUri(final Context context, final Uri uri) {
        return getRealFilePathFromUriByContentResolver(context, uri, "_display_name");
    }
    
    private static String c(final Context context, final Uri uri) {
        String str;
        if (StringUtils.containsIgnoreCase(uri.getAuthority(), "fileprovider")) {
            str = b(context, uri);
        }
        else {
            str = a(context, uri);
            Debug.e("getPathFromUri file path = " + str);
        }
        return str;
    }
    
    private static String a(final Context context, final Uri uri) {
        final String realFilePathFromUriByContentResolver;
        if (StringUtils.isNotBlank(realFilePathFromUriByContentResolver = getRealFilePathFromUriByContentResolver(context, uri, "_data"))) {
            return realFilePathFromUriByContentResolver;
        }
        return getDocumentPathFromExternalStorage(context, uri);
    }
    
    private static String b(final Context context, final Uri uri) {
        final String encodedPath;
        final String separator;
        final String[] split;
        final String[] array = split = (encodedPath = getEncodedPath(uri)).split(separator = File.separator);
        final String substring = encodedPath.substring(("/" + split[1]).length());
        final String lowerCase;
        final String s = lowerCase = StringUtils.toLowerCase(array[1]);
        s.hashCode();
        int n = -1;
        switch (s) {
            case "bluetooth": {
                n = 5;
                break;
            }
            case "download": {
                n = 4;
                break;
            }
            case "external_storage_root": {
                n = 3;
                break;
            }
            case "root": {
                n = 2;
                break;
            }
            case "nutstore_share_file": {
                n = 1;
                break;
            }
            case "external": {
                n = 0;
                break;
            }
            default:
                break;
        }
        switch (n) {
            default: {
                return a(encodedPath);
            }
            case 4: {
                return Device.currentDevice().getExternalStorageDirectory().getAbsolutePath() + separator + split[1] + substring;
            }
            case 2: {
                return substring;
            }
            case 1: {
                return b(substring);
            }
            case 0:
            case 3:
            case 5: {
                return Device.currentDevice().getExternalStorageDirectory().getAbsolutePath() + substring;
            }
        }
    }
    
    private static String b(final String relativePath) {
        if (Build.VERSION.SDK_INT <= 29) {
            return relativePath;
        }
        return null;
    }
    
    public static String getEncodedPath(Uri uri) {
        String result = null;
        try {
            result = URLDecoder.decode(uri.getEncodedPath(), "UTF-8");
        }
        catch (final UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    public static String decodeUrl(final String url) {
        try {
            return URLDecoder.decode(url, "UTF-8");
        }
        catch (final Exception ex) {
            ex.printStackTrace();
            return url;
        }
    }
    
    private static String a(final String encodedPath) {
        if (StringUtils.isNullOrEmpty(encodedPath)) {
            return encodedPath;
        }
        final List validRemovableSDCardDirectories = EnvironmentUtil.getValidRemovableSDCardDirectories();
        validRemovableSDCardDirectories.add(EnvironmentUtil.getExternalStorageDirectory());
        final Iterator iterator = validRemovableSDCardDirectories.iterator();
        while (iterator.hasNext()) {
            final int index;
            if ((index = encodedPath.indexOf(((File)iterator.next()).getAbsolutePath())) > 0) {
                return encodedPath.substring(index);
            }
        }
        Debug.i((Class)FileUtils.class, "can't get real path from encodePath:" + encodedPath, new Object[0]);
        return "";
    }
    
    public static String getDocumentPathFromExternalStorage(final Context context, final Uri uri) {
        if (uri == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT < 19) {
            return null;
        }
        if (!DocumentsContract.isDocumentUri(context, uri)) {
            return null;
        }
        if (!"com.android.externalstorage.documents".equals(uri.getAuthority())) {
            return null;
        }
        final String[] split;
        if ((split = DocumentsContract.getDocumentId(uri).split(":")).length < 2) {
            return null;
        }
        final String[] array = split;
        final String s = array[0];
        final String s2 = array[1];
        if ("primary".equals(s)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath().concat("/").concat(s2);
        }
        final Iterator<String> iterator = FileUtils.e.iterator();
        while (iterator.hasNext()) {
            final String concat;
            if (fileExist(concat = iterator.next().concat("/").concat(s).concat("/").concat(s2))) {
                return concat;
            }
        }
        return null;
    }
    
    public static String getRealFilePathFromUriByContentResolver(final Context context, final Uri uri, final String projectionName) {
        if (uri == null) {
            return "";
        }
        try (Cursor cursor = context.getContentResolver().query(
                uri, new String[] { projectionName }, null, null, null)) {
            if (cursor == null) {
                return "";
            }
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        catch (Exception exception) {
            Debug.e(exception);
            return "";
        }
    }
    
    public static String computeFileOrDirectoryMD5(final String path) {
        return isDirectory(path) ? computeMD5(path) : computeMD5Safely(path);
    }
    
    public static String computeMD5Safely(final String path) {
        String computeMD5 = null;
        try {
            computeMD5 = computeMD5(new File(path));
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
        return computeMD5;
    }
    
    public static String computeMD5(final File file) throws IOException, NoSuchAlgorithmException {
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        if (file.isFile()) {
            final byte[] digestBuffer = getDigestBuffer(file);
            final MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(digestBuffer);
            final byte[] digest;
            final byte[] array = digest = instance.digest();
            final char[] array3;
            final char[] array2 = array3 = new char[16];
            array2[0] = '0';
            array2[1] = '1';
            array2[2] = '2';
            array2[3] = '3';
            array2[4] = '4';
            array2[5] = '5';
            array2[6] = '6';
            array2[7] = '7';
            array2[8] = '8';
            array2[9] = '9';
            array2[10] = 'a';
            array2[11] = 'b';
            array2[12] = 'c';
            array2[13] = 'd';
            array2[14] = 'e';
            array2[15] = 'f';
            final char[] data = new char[array.length * 2];
            for (int i = 0; i < digest.length; ++i) {
                final char[] array4 = data;
                final char[] array5 = array3;
                final byte[] array6 = digest;
                final int n = i;
                final char[] array7 = data;
                final char[] array8 = array3;
                final byte[] array9 = digest;
                final int n2 = i;
                int n3 = n2 << 1;
                array7[n3] = array8[array9[n2] >> 4 & 0xF];
                ++n3;
                array4[n3] = array5[array6[n] & 0xF];
            }
            return String.valueOf(data);
        }
        throw new IllegalArgumentException();
    }
    
    public static byte[] getDigestBuffer(final File file) throws IOException {
        try (RandomAccessFile input = new RandomAccessFile(file, "r")) {
            long length = input.length();
            if (length <= 1536L) {
                byte[] data = new byte[(int) length];
                input.readFully(data);
                return data;
            }
            byte[] data = new byte[1536];
            input.seek(0L);
            input.readFully(data, 0, 512);
            input.seek(length / 2L - 256L);
            input.readFully(data, 512, 512);
            input.seek(length - 512L);
            input.readFully(data, 1024, 512);
            return data;
        }
    }
    
    public static String computeMD5(final String content) {
        if (StringUtils.isNullOrEmpty(content)) {
            return null;
        }
        return computeMD5(content.getBytes(Charset.defaultCharset()));
    }
    
    public static String computePartMD5(final byte[] data) {
        byte[] buffer;
        if (data.length < 1536) {
            buffer = data;
        }
        else {
            buffer = new byte[1536];
            for (int i = 0, n = data.length / 2 - 256, n2 = data.length - 512; i < 512; ++i, ++n, ++n2) {
                final byte[] array = buffer;
                final int n3 = i;
                final byte[] array2 = buffer;
                final int n4 = i;
                buffer[i] = data[i];
                array2[n4 + 512] = data[n];
                array[n3 + 1024] = data[n2];
            }
        }
        return computeMD5(buffer);
    }
    
    public static String computeMD5(final byte[] buffer) {
        if (buffer == null) {
            return null;
        }
        String hexToString = null;
        try {
            final MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(buffer, 0, buffer.length);
            hexToString = hexToString(instance.digest());
        }
        catch (final NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return hexToString;
    }
    
    public static List<String> computeSliceMD5(final String filePath, final long limit) throws Exception {
        final ArrayList list = new ArrayList();
        long n;
        for (long fileSize = getFileSize(filePath), offset = 0L; offset < fileSize; offset = n + limit) {
            n = offset;
            list.add(computeMD5(filePath, offset, limit));
        }
        return list;
    }
    
    public static String computeMD5(String filePath, long offset, final long limit) throws NoSuchAlgorithmException, IOException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        try (FileInputStream input = new FileInputStream(new File(filePath));
                FileChannel channel = input.getChannel()) {
            channel.position(offset);
            long remaining = limit;
            ByteBuffer buffer = ByteBuffer.allocate(65536);
            while (remaining > 0L) {
                buffer.clear();
                buffer.limit((int) Math.min(buffer.capacity(), remaining));
                int count = channel.read(buffer);
                if (count < 0) {
                    break;
                }
                digest.update(buffer.array(), 0, count);
                remaining -= count;
            }
        }
        return hexToString(digest.digest());
    }
    
    public static byte[] getBlock(String filePath, final long offset, final int limit) throws NoSuchAlgorithmException, IOException {
        int length = (int) Math.max(0L, Math.min(getFileSize(filePath) - offset, limit));
        ByteBuffer buffer = ByteBuffer.allocate(length);
        try (FileInputStream input = new FileInputStream(filePath);
                FileChannel channel = input.getChannel()) {
            channel.position(offset);
            while (buffer.hasRemaining() && channel.read(buffer) >= 0) {
                // Continue until the requested block is filled or EOF is reached.
            }
        }
        return buffer.array();
    }
    
    public static String computeFullMD5Checksum(final File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("MD5");
        try (FileInputStream input = new FileInputStream(file)) {
            byte[] buffer = new byte[65536];
            int count;
            while ((count = input.read(buffer)) != -1) {
                if (count > 0) {
                    digest.update(buffer, 0, count);
                }
            }
        }
        return hexToString(digest.digest());
    }
    
    public static String computeFullMD5Safely(final String filePath) {
        String computeFullMD5Checksum = null;
        try {
            computeFullMD5Checksum = computeFullMD5Checksum(new File(filePath));
        }
        catch (final Exception ex) {
            ex.printStackTrace();
        }
        return computeFullMD5Checksum;
    }
    
    public static String hexToString(final byte[] out) {
        final char[] array2;
        final char[] array = array2 = new char[16];
        array[0] = '0';
        array[1] = '1';
        array[2] = '2';
        array[3] = '3';
        array[4] = '4';
        array[5] = '5';
        array[6] = '6';
        array[7] = '7';
        array[8] = '8';
        array[9] = '9';
        array[10] = 'a';
        array[11] = 'b';
        array[12] = 'c';
        array[13] = 'd';
        array[14] = 'e';
        array[15] = 'f';
        final char[] data = new char[out.length * 2];
        for (int i = 0; i < out.length; ++i) {
            final char[] array3 = data;
            final char[] array4 = array2;
            final int n = i;
            final char[] array5 = data;
            final char[] array6 = array2;
            final int n2 = i;
            int n3 = n2 << 1;
            array5[n3] = array6[out[n2] >> 4 & 0xF];
            ++n3;
            array3[n3] = array4[out[n] & 0xF];
        }
        return String.valueOf(data);
    }
    
    public static boolean deleteFile(final String path) {
        return deleteFile(new File(path));
    }
    
    public static boolean deleteFile(final File file) {
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }
    
    public static boolean deleteFile(final String filePath, final boolean recursive) {
        return deleteFile(new File(filePath), recursive);
    }
    
    public static boolean deleteFile(final File file, final boolean recursive) {
        if (file.isFile()) {
            return deleteFile(file);
        }
        final File[] listFiles;
        if ((listFiles = file.listFiles()) != null && listFiles.length > 0) {
            for (final File file2 : listFiles) {
                if (recursive) {
                    deleteFile(file2, true);
                }
                else {
                    deleteFile(file2);
                }
            }
            return deleteFile(file);
        }
        return deleteFile(file);
    }
    
    public static boolean ensureFileExists(String path) {
        final File file;
        if ((file = new File(path)).exists()) {
            return true;
        }
        final int index;
        if ((index = path.indexOf(47, 1)) < 1) {
            return false;
        }
        path = path.substring(0, index);
        if (!new File(path).exists()) {
            return false;
        }
        final File parentFile;
        if (!(parentFile = file.getParentFile()).exists() && !parentFile.mkdirs()) {
            Log.e(FileUtils.a, "create folder failed: " + parentFile.getAbsolutePath());
            return false;
        }
        try {
            return file.createNewFile();
        }
        catch (final IOException ex) {
            Log.e(FileUtils.a, "File creation failed", (Throwable)ex);
            return false;
        }
    }
    
    public static void findFileByKey(final List<File> fileList, final String searchKey) {
        findFileByKey(fileList, EnvironmentUtil.getExternalStorageDirectory(), searchKey);
        findFileByKey(fileList, EnvironmentUtil.getRemovableSDCardDirectory(), searchKey);
    }
    
    public static void findFileByKey(final List<File> fileList, final File targetDir, final String searchKey) {
        if (!targetDir.canRead()) {
            return;
        }
        File[] listFiles;
        for (int length = (listFiles = targetDir.listFiles()).length, i = 0; i < length; ++i) {
            final File targetDir2;
            if (!(targetDir2 = listFiles[i]).isHidden()) {
                if (targetDir2.isDirectory()) {
                    if (targetDir2.getName().contains(searchKey)) {
                        fileList.add(targetDir2);
                    }
                    findFileByKey(fileList, targetDir2, searchKey);
                }
                if (targetDir2.isFile() && targetDir2.getName().contains(searchKey)) {
                    fileList.add(targetDir2);
                }
            }
        }
    }
    
    @Nullable
    public static String fixNotAllowFileName(final String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return null;
        }
        final int lastIndex;
        if ((lastIndex = fileName.lastIndexOf(".")) == -1) {
            return null;
        }
        return fixNotAllowFileName(fileName, lastIndex);
    }
    
    @Nullable
    public static String fixNotAllowFileName(String fileName, int dotIndex) {
        if (StringUtils.isBlank(fileName)) {
            return null;
        }
        final int n = dotIndex;
        String s = fileName.replaceAll("([*/^?|<>\r\n])", " ");
        if (n >= 0 && dotIndex < fileName.length()) {
            final String s2 = s;
            final String s3 = fileName;
            fileName = s.substring(dotIndex);
            s = s2.replace(fileName, s3.substring(dotIndex));
        }
        fileName = s.replace(":", "\uff1a");
        dotIndex = 0;
        while (fileName.contains("\"")) {
            if (dotIndex == 0) {
                fileName = fileName.replaceFirst("\"", "\u201c");
                dotIndex = 1;
            }
            else {
                fileName = fileName.replaceFirst("\"", "\u201d");
                dotIndex = 0;
            }
        }
        return fileName;
    }
    
    @SuppressLint({ "NewApi" })
    @Nullable
    public static String fixNotAllowFilePath(final String filePath) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        final String separator;
        String str;
        if (filePath.startsWith(separator = File.separator)) {
            str = separator;
        }
        else {
            str = "";
        }
        final String regex = separator;
        final StringBuilder sb = new StringBuilder(str);
        final List<String> collection = Arrays.stream(filePath.split(regex)).filter(StringUtils::isNotBlank).collect(Collectors.toList());
        final List<String> collection2 = collection.stream().map(node -> fixNotAllowFileName(node, node.lastIndexOf("."))).filter(StringUtils::isNotBlank).collect(Collectors.toList());
        if (CollectionUtils.getSize(collection) != CollectionUtils.getSize(collection2)) {
            return null;
        }
        for (int i = 0; i < collection2.size(); ++i) {
            final int n = i;
            final List list = collection2;
            sb.append((String)collection2.get(i));
            if (n != list.size() - 1) {
                sb.append(File.separator);
            }
        }
        return sb.toString();
    }
    
    public static String filterFileName(final String fileName) {
        if (StringUtils.isNullOrEmpty(fileName)) {
            return fileName;
        }
        return fileName.replaceAll("([*/^?|<>:\"\r\n])", " ");
    }
    
    public static String readContentOfFile(final String path) {
        Log.d(a, "readContentOfFile(path) path=" + path);
        if (StringUtils.isNullOrEmpty(path)) {
            Log.w(a, "readContentOfFile(path) received an empty path");
            return null;
        }
        try (FileInputStream input = new FileInputStream(new File(path));
             InputStreamReader streamReader = new InputStreamReader(input, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            Log.d(a, "readContentOfFile(path) chars=" + content.length());
            return content.toString();
        }
        catch (Exception error) {
            Log.w(a, "readContentOfFile(path) failed path=" + path, error);
            return null;
        }
    }
    
    public static String readContentOfInputStream(final FileInputStream input) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        } catch (final Exception error) {
            error.printStackTrace();
            return null;
        }
    }
    
    public static String readContentOfUri(Uri uri) {
        try (ParcelFileDescriptor descriptor = OnyxFileProviderUtil.openFileDescriptor(
                    ResManager.getAppContext(), uri, "r");
                FileInputStream input = new FileInputStream(descriptor.getFileDescriptor())) {
            return readContentOfInputStream(input);
        } catch (final Exception error) {
            error.printStackTrace();
            return null;
        }
    }
    
    public static String getFileNameFromUrl(final String url) {
        if (!StringUtils.isUrl(url)) {
            return null;
        }
        final int lastIndex;
        if ((lastIndex = url.lastIndexOf(47)) < 0) {
            return null;
        }
        return url.substring(lastIndex + 1, url.length());
    }
    
    public static String getFileSize(final long size) {
        final DecimalFormat decimalFormat = new DecimalFormat("###.##");
        if (size < 1024L) {
            return size + "B";
        }
        if (size < 1048576L) {
            return decimalFormat.format(size / 1024.0f) + "KB";
        }
        if (size < 1073741824L) {
            return decimalFormat.format(size / 1048576.0f) + "MB";
        }
        return decimalFormat.format(size / 1.07374182E9f) + "GB";
    }
    
    public static void transferFile(final String currentFilePath, final String newFilePath) throws Exception {
        final File file = new File(currentFilePath);
        final File file2 = new File(newFilePath);
        final FileChannel channel = new FileInputStream(file).getChannel();
        final FileChannel channel2 = new FileOutputStream(file2).getChannel();
        final FileChannel src = channel;
        safeTransfer(src, channel2);
        src.close();
        channel2.close();
    }
    
    public static boolean transferData(final InputStream is, final OutputStream os) {
        try {
            return IOUtils.copy(is, os) > 0;
        } catch (final IOException error) {
            error.printStackTrace();
            return false;
        } finally {
            closeQuietly(is);
            closeQuietly(os);
        }
    }
    
    public static long copy(final InputStream input, final OutputStream output, final Consumer<Long> bytesConsumer) throws Exception {
        return copyStream(input, output, bytesConsumer);
    }
    
    public static boolean compareFileMd5(final String file1, final String file2) throws IOException, NoSuchAlgorithmException {
        return computeFullMD5Checksum(new File(file1)).equals(computeFullMD5Checksum(new File(file2)));
    }
    
    public static boolean copyDirectory(final File src, final File dest) {
        try {
            com.onyx.android.sdk.commons.io.FileUtils.copyDirectory(src, dest);
            return true;
        }
        catch (final IOException ex) {
            Debug.e((Throwable)ex);
            return false;
        }
    }
    
    public static boolean isEmptyDirectory(String directoryPath) {
        if (!fileExist(directoryPath)) {
            return true;
        }
        try {
            final File file = new File(directoryPath);
            if (Build.VERSION.SDK_INT >= 26) {
                try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(file.toPath())) {
                    return !directoryStream.iterator().hasNext();
                }
            }
            return c(directoryPath);
        } catch (final IOException error) {
            error.printStackTrace();
            return false;
        }
    }
    
    private static boolean c(final String directory) {
        if (!fileExist(directory)) {
            return true;
        }
        if (!isDirectory(directory)) {
            return true;
        }
        final LinkedList<File> list;
        (list = new LinkedList<File>()).add(new File(directory));
        while (!list.isEmpty()) {
            final File[] listFiles;
            if ((listFiles = list.removeFirst().listFiles()) != null) {
                for (int length = listFiles.length, i = 0; i < length; ++i) {
                    final File e;
                    if (!(e = listFiles[i]).isDirectory()) {
                        return false;
                    }
                    list.add(e);
                }
            }
        }
        return true;
    }
    
    public static boolean copyAndTouchFile(final String srcPath, final String dstPath) {
        final File file;
        if ((file = new File(srcPath)).isDirectory()) {
            return false;
        }
        final File sourceFile = file;
        deleteFile(dstPath);
        if (!copyFile(sourceFile, new File(dstPath))) {
            return false;
        }
        touchFile(dstPath, file.lastModified());
        return true;
    }
    
    public static boolean touchFile(final String path, final long timestamp) {
        return new File(path).setLastModified(timestamp);
    }
    
    public static boolean copyFile(final File sourceFile, final File targetFile) {
        if (!ensureFileExists(targetFile.getAbsolutePath())) {
            return false;
        }
        try (FileInputStream input = new FileInputStream(sourceFile);
                FileOutputStream output = new FileOutputStream(targetFile);
                FileChannel source = input.getChannel();
                FileChannel target = output.getChannel()) {
            return safeTransfer(source, target);
        } catch (final Exception error) {
            error.printStackTrace();
            return false;
        }
    }
    
    public static String copyFile(final String srcFilePath) {
        final String s = "";
        final String fileName = getFileName(srcFilePath);
        final String baseName = getBaseName(srcFilePath);
        final int lastIndex;
        String combine;
        if ((lastIndex = srcFilePath.lastIndexOf(fileName)) >= 0) {
            int i = 0;
            do {
                ++i;
            } while (fileExist(combine = combine(srcFilePath.substring(0, lastIndex) + baseName + "(" + i + ")", getFileExtension(srcFilePath))));
            copyFile(new File(srcFilePath), new File(combine));
            MtpUtils.updateMtpDb(ResManager.getAppContext(), new File(combine));
        }
        else {
            combine = s;
        }
        return combine;
    }
    
    public static boolean copyAssetFile(Context context, final String assetPath, final File targetFile) {
        try {
            File parent = targetFile.getParentFile();
            if (parent != null && !parent.exists()) parent.mkdirs();
            try (InputStream input = context.getAssets().open(assetPath);
                 OutputStream output = new FileOutputStream(targetFile)) {
                return transferData(input, output);
            }
        } catch (Exception failure) {
            failure.printStackTrace();
            return false;
        }
    }
    
    public static void copyAssetsPath(final Context context, final String path, final String outPath) {
        final AssetManager assets = context.getAssets();
        try {
            final String[] list;
            if ((list = assets.list(path)).length == 0) {
                copyAssetFile(context, path, new File(outPath));
            }
            else {
                final File file;
                if (!(file = new File(outPath)).exists()) {
                    file.mkdirs();
                }
                for (String child : list) {
                    String separator = File.separator;
                    copyAssetsPath(context, path + separator + child,
                            outPath + separator + child);
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    
    public static long copyStream(final InputStream input, final OutputStream output, final Consumer<Long> listener) throws Exception {
        long l = 0L;
        final byte[] array = new byte[65536];
        while (true) {
            final int read = input.read(array);
            if (-1 == read) {
                break;
            }
            final long n = l;
            final int n2 = read;
            output.write(array, 0, read);
            l = n + n2;
            if (listener == null) {
                continue;
            }
            listener.accept(l);
        }
        return l;
    }
    
    public static void sortListByName(final List<File> fileList, final SortOrder sortOrder) {
        Collections.sort(fileList, new Comparator<File>() {
            public int compare(final File lhs, final File rhs) {
                final int booleanComparator;
                if ((booleanComparator = ComparatorUtils.booleanComparator(lhs.isDirectory(), rhs.isDirectory(), SortOrder.Desc)) == 0) {
                    return ComparatorUtils.stringComparator(lhs.getName(), rhs.getName(), sortOrder);
                }
                return booleanComparator;
            }
        });
    }
    
    public static void sortListByCreationTime(final List<File> fileList, final SortOrder sortOrder) {
        Collections.sort(fileList, new Comparator<File>() {
            public int compare(final File lhs, final File rhs) {
                final int booleanComparator;
                if ((booleanComparator = ComparatorUtils.booleanComparator(lhs.isDirectory(), rhs.isDirectory(), SortOrder.Desc)) == 0) {
                    return ComparatorUtils.longComparator(lhs.lastModified(), rhs.lastModified(), sortOrder);
                }
                return booleanComparator;
            }
        });
    }
    
    public static void sortListBySize(final List<File> fileList, final SortOrder sortOrder) {
        Collections.sort(fileList, new Comparator<File>() {
            public int compare(final File lhs, final File rhs) {
                final int booleanComparator;
                if ((booleanComparator = ComparatorUtils.booleanComparator(lhs.isDirectory(), rhs.isDirectory(), SortOrder.Desc)) == 0) {
                    return ComparatorUtils.longComparator(lhs.length(), rhs.length(), sortOrder);
                }
                return booleanComparator;
            }
        });
    }
    
    public static void sortListByFileType(final List<File> fileList, final SortOrder sortOrder) {
        Collections.sort(fileList, new Comparator<File>() {
            public int compare(final File lhs, final File rhs) {
                final int booleanComparator;
                if ((booleanComparator = ComparatorUtils.booleanComparator(lhs.isDirectory(), rhs.isDirectory(), SortOrder.Desc)) == 0) {
                    return ComparatorUtils.stringComparator(FileUtils.getFileExtension(lhs), FileUtils.getFileExtension(rhs), sortOrder);
                }
                return booleanComparator;
            }
        });
    }
    
    public static boolean onSameSDCard(final File a, final File b) {
        final String absolutePath = EnvironmentUtil.getExternalStorageDirectory().getAbsolutePath();
        final String absolutePath2 = a.getAbsolutePath();
        final String s = absolutePath;
        final String absolutePath3 = b.getAbsolutePath();
        final boolean contains = absolutePath2.contains(s);
        final boolean contains2 = absolutePath3.contains(absolutePath);
        if (!contains && !contains2) {
            return StringUtils.safelyEquals(EnvironmentUtil.getRemovableSDCardCid(ResManager.getAppContext(), absolutePath2), EnvironmentUtil.getRemovableSDCardCid(ResManager.getAppContext(), absolutePath3));
        }
        return contains && contains2;
    }
    
    public static void updateMtpDb(final Context context, final File file) {
        MediaScannerConnection.scanFile(context, new String[] { file.getAbsolutePath() }, (String[])null, (MediaScannerConnection.OnScanCompletedListener)new MediaScannerConnection.OnScanCompletedListener() {
            public void onScanCompleted(final String path, final Uri uri) {
                Debug.i((Class)FileUtils.class, "file " + path + " was scanned successfully: " + uri, new Object[0]);
            }
        });
    }
    
    public static String readableFileSize(final long size) {
        if (size <= 0L) {
            return "0";
        }
        final String[] units = { "B", "KB", "MB", "GB", "TB" };
        final double value = size;
        final int digitGroup = (int)(Math.log10(value) / Math.log10(1024.0));
        return new DecimalFormat("#,##0.#").format(
                value / Math.pow(1024.0, digitGroup)) + " " + units[digitGroup];
    }
    
    public static String getDisplayFileSize(final String filePath) {
        return getFileSize(getFileSize(filePath));
    }
    
    public static long getFileSize(final String filePath) {
        if (StringUtils.isNullOrEmpty(filePath)) {
            return 0L;
        }
        return getFileSize(new File(filePath));
    }
    
    public static long getFileSize(final File file) {
        if (Build.VERSION.SDK_INT >= 26) {
            return a(Paths.get(file.getAbsolutePath(), new String[0]));
        }
        return a(file);
    }
    
    private static long a(final File file) {
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
                        n3 = getFileSize(file2);
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
        AtomicLong total = new AtomicLong(0L);
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) {
                    total.addAndGet(attrs.size());
                    return FileVisitResult.CONTINUE;
                }
                
                @Override public FileVisitResult visitFileFailed(final Path file, final IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
                
                @Override public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) {
                    if (exc != null) {
                        Debug.d("had trouble traversing: " + dir + " (" + exc + ")");
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }
        catch (final IOException ex) {
            ex.printStackTrace();
        }
        return total.get();
    }
    
    public static void removeUnSupportFormatFiles(final Collection<String> originList, final Collection<String> extensionFilterList) {
        if (!CollectionUtils.isNullOrEmpty(originList) && !CollectionUtils.isNullOrEmpty(extensionFilterList)) {
            final Iterator<String> iterator = originList.iterator();
            while (iterator.hasNext()) {
                if (!extensionFilterList.contains(getFileExtension(StringUtils.safelyGetStr(iterator.next())))) {
                    iterator.remove();
                }
            }
        }
    }
    
    public static List<File> loadStorageFileList(final File targetDir, final boolean skipHiddenFile, final List<String> filterDirList) {
        final File[] listFiles;
        if (ArraysUtils.isNullOrEmpty(listFiles = targetDir.listFiles((dir, filename) -> {
            final File file2 = new File(dir, filename);
            if (CollectionUtils.safelyStringEqualsIgnoreCase(filterDirList, filename) && file2.isDirectory()) {
                return false;
            }
            else {
                return !skipHiddenFile || !file2.isHidden();
            }
        }))) {
            return new ArrayList<File>();
        }
        return new ArrayList<File>(Arrays.asList(listFiles));
    }
    
    public static void sortFileList(final List<File> fileList, final SortBy sortBy, final SortOrder sortOrder) {
        if (CollectionUtils.isNullOrEmpty(fileList)) {
            return;
        }
        final Benchmark benchmark = new Benchmark();
        switch (FileUtils$h.a[sortBy.ordinal()]) {
            case 4: {
                sortListBySize(fileList, sortOrder);
                break;
            }
            case 3: {
                sortListByFileType(fileList, sortOrder);
                break;
            }
            case 2: {
                sortListByCreationTime(fileList, sortOrder);
                break;
            }
            case 1: {
                sortListByName(fileList, sortOrder);
                break;
            }
        }
        Log.w(FileUtils.a, "Sort duration:" + benchmark.duration() + "ms");
    }
    
    public static boolean isStorageRoot(final File targetDirectory) {
        return targetDirectory == null || isStorageRoot(targetDirectory.getAbsolutePath());
    }
    
    public static boolean isStorageRoot(final String targetDirectory) {
        return StringUtils.isNullOrEmpty(targetDirectory) || StringUtils.safelyEquals(EnvironmentUtil.getStorageRootDirectory().getAbsolutePath(), targetDirectory);
    }
    
    public static boolean isStorageRoot(final String storageRootDirectory, final String targetDirectory) {
        return StringUtils.isNullOrEmpty(targetDirectory) || StringUtils.safelyEquals(storageRootDirectory, targetDirectory);
    }
    
    public static List<String> readStringListOfFile(final File fileForRead) {
        try {
            return com.onyx.android.sdk.commons.io.FileUtils.readLines(fileForRead, StandardCharsets.UTF_8);
        }
        catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static String getFileNameFromPath(final String path) {
        if (StringUtils.isNullOrEmpty(path)) {
            return "";
        }
        final int lastIndex;
        if ((lastIndex = path.lastIndexOf(47)) < 0) {
            return "";
        }
        return path.substring(lastIndex + 1, path.length());
    }
    
    public static String formatFileAbsolutePath(final String path, final String replacedPath) {
        if (StringUtils.isNullOrEmpty(path)) {
            return replacedPath;
        }
        return formatFileAbsolutePath(new File(path), replacedPath);
    }
    
    public static String formatFileAbsolutePath(final File file, final String replacedPath) {
        if (file == null) {
            return replacedPath;
        }
        return replaceRootPath(file, getRootDirectory(file), replacedPath);
    }
    
    public static File getRootDirectory(final File file) {
        final File file2 = null;
        final Iterator iterator = EnvironmentUtil.getValidRemovableSDCardDirectories().iterator();
        while (true) {
            while (iterator.hasNext()) {
                File externalStorageDirectory;
                if ((externalStorageDirectory = (File)iterator.next()) != null && file.getAbsolutePath().contains(externalStorageDirectory.getAbsolutePath())) {
                    if (externalStorageDirectory == null) {
                        externalStorageDirectory = EnvironmentUtil.getExternalStorageDirectory();
                    }
                    return externalStorageDirectory;
                }
            }
            File externalStorageDirectory = file2;
            continue;
        }
    }
    
    public static boolean isRootDirectoryRemovableSDCard(final String filePath) {
        if (StringUtils.isNullOrEmpty(filePath)) {
            return false;
        }
        final Iterator iterator = EnvironmentUtil.getValidRemovableSDCardDirectories().iterator();
        while (iterator.hasNext()) {
            final File file;
            if ((file = (File)iterator.next()) != null && filePath.contains(file.getAbsolutePath())) {
                return true;
            }
        }
        return false;
    }
    
    public static String replaceRootPath(final File targetDirectory, final File rootDirectory, final String replacedPath) {
        return targetDirectory.getAbsolutePath().replace(rootDirectory.getAbsolutePath(), replacedPath);
    }
    
    public static boolean isInSpeciallyDirectory(final List<String> list, final String filePath) {
        if (!StringUtils.isNullOrEmpty(filePath) && !CollectionUtils.isNullOrEmpty(list)) {
            final Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                final String str;
                if (StringUtils.isBlank(str = iterator.next())) {
                    continue;
                }
                if (filePath.equalsIgnoreCase(str)) {
                    return true;
                }
                final String separator;
                if (str.endsWith(separator = File.separator)) {
                    if (StringUtils.startsWithIgnoreCase(filePath, str)) {
                        return true;
                    }
                    continue;
                }
                else {
                    if (StringUtils.startsWithIgnoreCase(filePath, str + separator)) {
                        return true;
                    }
                    continue;
                }
            }
            return false;
        }
        return false;
    }
    
    public static String addSeparatorToDirectory(final String directoryPath) {
        if (StringUtils.isNullOrEmpty(directoryPath)) {
            return directoryPath;
        }
        final String separator;
        if (directoryPath.endsWith(separator = File.separator)) {
            return directoryPath;
        }
        return directoryPath + separator;
    }
    
    @NonNull
    public static LinkedList<File> listAllFiles(final String directory, final FilenameFilter filter) {
        final LinkedList<File> list = new LinkedList<File>();
        if (!fileExist(directory)) {
            return list;
        }
        if (!isDirectory(directory)) {
            return list;
        }
        final LinkedList<File> list2;
        (list2 = new LinkedList<File>()).add(new File(directory));
        while (!list2.isEmpty()) {
            File[] listFiles;
            for (int length = (listFiles = list2.removeFirst().listFiles(filter)).length, i = 0; i < length; ++i) {
                final File file;
                if ((file = listFiles[i]).isDirectory()) {
                    list2.add(file);
                }
                else {
                    list.add(file);
                }
            }
        }
        return list;
    }
    
    public static byte[] readBytesOfFile(final String filePath) {
        try {
            return com.onyx.android.sdk.commons.io.FileUtils.readFileToByteArray(new File(filePath));
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static byte[] readBytes(FileInputStream fin, final int off, final int len) {
        final byte[] b = new byte[len];
        try {
            fin.read(b, off, len);
        }
        catch (final IOException ex) {
            ex.printStackTrace();
        }
        return b;
    }
    
    public static List<String> readAssetsContent(final Context context, final String fileName) {
        try (InputStream input = context.getResources().getAssets().open(fileName)) {
            return IOUtils.readLines(input, StandardCharsets.UTF_8).stream()
                    .map(String::trim)
                    .collect(Collectors.toList());
        }
        catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    
    public static void skip(final FileInputStream fin, final long n) {
        try {
            fin.skip(n);
        }
        catch (final IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static String getCacheKey(final File file, final String... extraKeys) {
        if (!fileExist(file)) {
            return "";
        }
        String str = file.getAbsolutePath() + "-" + file.length() + "-" + file.lastModified();
        if (extraKeys != null && extraKeys.length > 0) {
            str += Arrays.asList(extraKeys).toString();
        }
        return str;
    }
    
    public static String combine(String filePath, final String fileExtension) {
        if (StringUtils.isNotBlank(filePath) && StringUtils.isNotBlank(fileExtension)) {
            filePath = filePath + "." + fileExtension;
        }
        return filePath;
    }
    
    public static String combinePath(String dirPath, final String name) {
        final String separator;
        if (dirPath.endsWith(separator = File.separator)) {
            final String s = dirPath;
            dirPath = s.substring(0, s.length() - 1);
        }
        return dirPath + separator + StringUtils.replaceFirst(name, separator, "");
    }
    
    public static String readContentFromFile(final String path) {
        try (FileInputStream input = new FileInputStream(new File(path))) {
            return StringUtils.join(IOUtils.readLines(input, StandardCharsets.UTF_8), "");
        }
        catch (IOException exception) {
            exception.printStackTrace();
            return "";
        }
    }
    
    public static void createAndOverridePermission(String dirName, String fileName, final boolean isNeedOverridePermission) throws IOException {
        final File file;
        if (!(file = new File(dirName)).exists()) {
            file.mkdirs();
        }
        final File file2;
        if (!(file2 = new File(fileName)).exists()) {
            file2.createNewFile();
        }
        if (isNeedOverridePermission) {
            fileName = "chmod 777 " + file.getAbsolutePath();
            dirName = "chmod 777 " + file2.getAbsolutePath();
            final Runtime runtime = Runtime.getRuntime();
            final String command = dirName;
            runtime.exec(fileName);
            runtime.exec(command);
        }
    }
    
    public static long getLastModified(final File file, final boolean correct) {
        long lastModified;
        if ((lastModified = file.lastModified()) == 0L) {
            lastModified = System.currentTimeMillis();
            if (correct) {
                file.setLastModified(lastModified);
            }
        }
        return lastModified;
    }
    
    @Deprecated
    public static boolean isVolumeReadOnly(final File file) {
        return Device.currentDevice().isVolumeReadOnly(ResManager.getAppContext(), file);
    }
    
    @Deprecated
    public static boolean isVolumeReadOnly(final String filePath) {
        return !StringUtils.isNullOrEmpty(filePath) && isVolumeReadOnly(new File(filePath));
    }
    
    @Nullable
    public static String getAbsoluteSanitizedPath(final String path) {
        final String[] sanitizePath;
        if ((sanitizePath = sanitizePath(path)).length == 0) {
            return null;
        }
        final String[] original = sanitizePath;
        final StringBuilder sb = new StringBuilder();
        String[] array;
        for (int length = (array = Arrays.copyOfRange(original, 1, original.length)).length, i = 0; i < length; ++i) {
            sb.append("/").append(array[i]);
        }
        return sb.toString();
    }
    
    @NonNull
    public static String[] sanitizePath(@Nullable final String path) {
        if (path == null) {
            return new String[0];
        }
        final String[] split;
        if ((split = path.split("/")).length == 0) {
            return new String[] { "" };
        }
        for (int i = 0; i < split.length; ++i) {
            split[i] = sanitizeDisplayName(split[i]);
        }
        return split;
    }
    
    @Nullable
    public static String sanitizeDisplayName(@Nullable final String name) {
        return sanitizeDisplayName(name, false);
    }
    
    @Nullable
    public static String sanitizeDisplayName(@Nullable final String name, final boolean rewriteHiddenFileName) {
        if (name == null) {
            return null;
        }
        if (rewriteHiddenFileName && name.startsWith(".")) {
            return "_" + name;
        }
        return buildValidFatFilename(name);
    }
    
    public static boolean isValidFatFilename(final String name) {
        return name != null && name.equals(buildValidFatFilename(name));
    }
    
    public static String buildValidFatFilename(final String name) {
        if (!StringUtils.isNullOrEmpty(name) && !".".equals(name) && !"..".equals(name)) {
            final StringBuilder sb = new StringBuilder(name.length());
            for (int i = 0; i < name.length(); ++i) {
                final char char1;
                if (a(char1 = name.charAt(i))) {
                    sb.append(char1);
                }
                else {
                    sb.append('_');
                }
            }
            final StringBuilder res = sb;
            a(res, 254);
            return res.toString();
        }
        return "(invalid)";
    }
    
    private static void a(final StringBuilder res, int maxBytes) {
        byte[] array;
        if ((array = res.toString().getBytes(StandardCharsets.UTF_8)).length > maxBytes) {
            for (maxBytes -= 3; array.length > maxBytes; array = res.toString().getBytes(StandardCharsets.UTF_8)) {
                res.deleteCharAt(res.length() / 2);
            }
            res.insert(res.length() / 2, "...");
        }
    }
    
    private static boolean a(final char c) {
        if (c >= '\0' && c <= '\u001f') {
            return false;
        }
        switch (c) {
            default: {
                return true;
            }
            case '\"':
            case '*':
            case '/':
            case ':':
            case '<':
            case '>':
            case '?':
            case '\\':
            case '|':
            case '\u007f': {
                return false;
            }
        }
    }
    
    public static boolean isParentEquals(final String originPath, final String destPath) {
        return StringUtils.safelyEquals(getParent(originPath), getParent(destPath));
    }
    
    public static boolean isFileRename(final String originPath, final String destPath) {
        return isParentEquals(originPath, destPath) && (StringUtils.safelyEquals(getFileName(originPath), getFileName(destPath)) ^ true);
    }
    
    public static boolean renameTo(final String sourcePath, final String destPath) {
        if (fileExist(sourcePath) && !StringUtils.isNullOrEmpty(destPath)) {
            final File dest;
            if (!fileExist((dest = new File(destPath)).getParentFile())) {
                mkdirs(dest.getParent());
            }
            return new File(sourcePath).renameTo(dest);
        }
        return false;
    }
    
    public static String newNameWithCurrentTime(final String name) {
        return combine(FilenameUtils.getBaseName(name) + "-" + System.currentTimeMillis(), FilenameUtils.getExtension(name));
    }
    
    public static <T> String createPath(final List<T> list, final io.reactivex.functions.Function<T, String> function) {
        final StringBuilder sb = new StringBuilder();
        final Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            final String fileName;
            if (StringUtils.isNullOrEmpty(fileName = RxUtils.applyItemSafety(function, iterator.next()))) {
                continue;
            }
            sb.append(File.separator).append(filterFileName(fileName));
        }
        return sb.toString();
    }
    
    public static boolean checkFileExistCaseSensitive(final String path) {
        final File file = new File(path);
        try {
            final String canonicalPath = file.getCanonicalPath();
            return file.exists() && StringUtils.safelyEquals(path, canonicalPath);
        }
        catch (final IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public static String buildParentDir(@NonNull final List<String> dirParams) {
        final StringBuilder obj = new StringBuilder();
        final Iterator<String> iterator = dirParams.iterator();
        while (iterator.hasNext()) {
            final StringBuilder sb = obj;
            final String fileName = iterator.next();
            if (!StringUtils.isNullOrEmpty(sb.toString())) {
                obj.append(File.separator);
            }
            obj.append(fixNotAllowFileName(fileName, -1));
        }
        final StringBuilder sb2 = obj;
        Debug.d((Class)FileUtils.class, "parentDir: " + (Object)obj, new Object[0]);
        return sb2.toString();
    }
    
    public static boolean safelyEqualsIgnoreCase(final String path1, final String path2) {
        return StringUtils.safelyEqualsIgnoreCase(path1, path2);
    }
    
    public static void listFd() {
        final File[] listFiles;
        if ((listFiles = new File("/proc/" + Process.myPid() + "/fd").listFiles()) == null) {
            return;
        }
        final File[] array = listFiles;
        final StringBuilder obj = new StringBuilder();
        for (int length = array.length, i = 0; i < length; ++i) {
            final StringBuilder sb = obj;
            final File file = listFiles[i];
            try {
                sb.append(Os.readlink(file.getAbsolutePath())).append("\n");
            }
            catch (final Exception obj2) {
                Debug.i(FileUtils.a, "listFd error=" + obj2, new Object[0]);
            }
        }
        Debug.i(FileUtils.a, "listFd=" + (Object)obj, new Object[0]);
    }
    
    public static String getValidFileName(final String baseFileName, final String extendedName) {
        final StringBuilder res = new StringBuilder();
        final int maxBytes = 254 - extendedName.getBytes(StandardCharsets.UTF_8).length;
        for (int i = 0; i < baseFileName.length(); ++i) {
            final char char1;
            if (a(char1 = baseFileName.charAt(i))) {
                res.append(char1);
            }
            else {
                res.append('_');
            }
        }
        final StringBuilder sb = res;
        a(res, maxBytes);
        return sb.append(extendedName).toString();
    }
    
    public static String contentToBase64(final String filePath) {
        try (FileInputStream input = new FileInputStream(filePath);
                ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[8192];
            int count;
            while ((count = input.read(buffer)) != -1) {
                output.write(buffer, 0, count);
            }
            return Base64.encodeToString(output.toByteArray(), 16);
        } catch (final IOException error) {
            Debug.e(error);
            return null;
        }
    }
    
    public static boolean isStorageAndroidDir(final File targetDirectory) {
        return targetDirectory != null && StringUtils.safelyEquals(FileUtils.ANDROID_DIR_PATH, targetDirectory.getAbsolutePath());
    }
    
    public static boolean safeTransfer(final FileChannel src, final FileChannel dst) {
        try {
            return safeTransfer(src, dst, 0L, src.size());
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
    
    public static boolean safeTransfer(final FileChannel src, final FileChannel dst, final long position, final long count) {
        long n = 0L;
        while (count > n) {
            try {
                final long n2 = n;
                final String a = FileUtils.a;
                final Locale root = Locale.ROOT;
                final String format = "safeTransfer position %d, left %d";
                try {
                    final Object[] array;
                    final Object[] args = array = new Object[2];
                    final long n3 = n;
                    final Object[] array2 = array;
                    final long n4 = n;
                    final int n5 = 0;
                    final long l = position + n4;
                    array2[n5] = l;
                    final int n6 = 1;
                    final long i = count - n3;
                    args[n6] = i;
                    Debug.i(a, String.format(root, format, args), new Object[0]);
                    n = n2 + src.transferTo(l, i, dst);
                }
                catch (final Exception ex) {
                    ex.printStackTrace();
                    return false;
                }
            }
            catch (final Exception ex2) {}
            break;
        }
        return true;
    }
    
    public static void split(File source, final File destination, final long startPosition, long endPosition) throws IOException {
        if (endPosition <= 0L) {
            endPosition = getFileSize(source);
        }
        if (startPosition < 0L || endPosition > source.length() || startPosition >= endPosition) {
            throw new IllegalArgumentException(
                    "Split file, invalid start/end position:" + startPosition + "," + endPosition);
        }
        try (RandomAccessFile input = new RandomAccessFile(source, "r");
                FileOutputStream output = new FileOutputStream(destination)) {
            input.seek(startPosition);
            long remaining = endPosition - startPosition;
            byte[] buffer = new byte[5120];
            while (remaining > 0L) {
                int count = input.read(buffer, 0, (int) Math.min(buffer.length, remaining));
                if (count < 0) {
                    break;
                }
                output.write(buffer, 0, count);
                remaining -= count;
            }
        }
    }
    
    public static void mergeFiles(File outputFile, List<File> inputFiles, boolean append) throws IOException {
        if (outputFile == null || CollectionUtils.isNullOrEmpty(inputFiles)) {
            return;
        }
        mkdirs(outputFile.getParent());
        try (FileOutputStream outputStream = new FileOutputStream(outputFile, append);
                FileChannel output = outputStream.getChannel()) {
            for (File inputFile : inputFiles) {
                try (FileInputStream inputStream = new FileInputStream(inputFile);
                        FileChannel input = inputStream.getChannel()) {
                    long position = 0L;
                    long size = input.size();
                    while (position < size) {
                        long transferred = input.transferTo(
                                position, Math.min(size - position, 5120L), output);
                        if (transferred <= 0L) {
                            break;
                        }
                        position += transferred;
                    }
                }
            }
        }
    }
    
    public static String getJdShopPinByDocPath(final String docPath) {
        final String replace = docPath.replace(Environment.getExternalStorageDirectory().getAbsolutePath(), "");
        final Matcher matcher = FileUtils.JD_SHOP_PATH_PATTERN.matcher(replace);
        if (org.apache.commons.lang3.StringUtils.startsWith((CharSequence)replace, (CharSequence)"/Shop") && matcher.find()) {
            return matcher.group();
        }
        return "";
    }
    
    public static List<File> safeListFiles(final File dir) {
        if (dir == null || !dir.exists() || !dir.canRead()) {
            return new ArrayList<File>();
        }
        final List<File> c;
        if (Build.VERSION.SDK_INT >= 26 && (c = c(dir)) != null) {
            return c;
        }
        return b(dir);
    }
    
    @RequiresApi(api = 26)
    private static List<File> c(final File dir) {
        try {
            final ArrayList<File> files = new ArrayList<>();
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(
                    Paths.get(dir.getAbsolutePath()))) {
                for (Path path : directoryStream) {
                    files.add(path.toFile());
                }
            }
            return files;
        } catch (final Exception error) {
            error.printStackTrace();
            return null;
        }
    }
    
    private static List<File> b(final File dir) {
        final ArrayList c = new ArrayList();
        try {
            final java.lang.Process exec = Runtime.getRuntime().exec(new String[] { "ls", dir.getAbsolutePath() });
            try {
                final InputStreamReader in = new InputStreamReader(exec.getInputStream());
                final BufferedReader bufferedReader = new BufferedReader(in);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (!StringUtils.isNullOrEmpty(line)) {
                        c.add(new File(dir, line));
                    }
                }
                exec.waitFor();
            }
            catch (final Exception ex) {
                ex.printStackTrace();
                final File[] listFiles;
                if ((listFiles = dir.listFiles()) == null) {
                    return c;
                }
                Collections.addAll(c, listFiles);
            }
        }
        catch (final Exception ex2) {
            ex2.printStackTrace();
        }
        return c;
    }
    
    public static boolean chmod777(final File file) {
        if (!fileExist(file)) {
            return false;
        }
        try {
            final boolean setReadable;
            final boolean b = setReadable = file.setReadable((boolean)(1 != 0), (boolean)(0 != 0));
            final boolean setWritable = file.setWritable(true, false);
            try {
                final boolean setExecutable = file.setExecutable(true, false);
                if (!b || !setWritable || !setExecutable) {
                    Log.w(FileUtils.a, "chmod777 partial success for: " + file.getAbsolutePath() + " [r=" + setReadable + ", w=" + setWritable + ", x=" + setExecutable + "]");
                }
                return setReadable && setWritable && setExecutable;
            }
            catch (final Exception ex) {
                Log.e(FileUtils.a, "chmod777 failed for: " + file.getAbsolutePath(), (Throwable)ex);
                return false;
            }
        }
        catch (final Exception ex2) {
            Log.e(FileUtils.a, "chmod777 failed for: " + file.getAbsolutePath(), ex2);
            return false;
        }
    }
    
    public static boolean chmod777(final String path) {
        return !StringUtils.isNullOrEmpty(path) && chmod777(new File(path));
    }
    
    static {
        FileUtils.d = Arrays.asList("com.microsoft.office.onenote.filename");
        FileUtils.e = Arrays.asList("/mnt/media_rw", "/storage");
        ANDROID_DIR_PATH = EnvironmentUtil.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Android";
        IMG_EXTENSION = new String[] { ".bmp", ".jpg", ".jpeg", ".png", ".gif", ".webp", ".svg", ".tif", ".tiff" };
        JD_SHOP_PATH_PATTERN = Pattern.compile("(?<=Shop/)[^/]+");
    }
}
