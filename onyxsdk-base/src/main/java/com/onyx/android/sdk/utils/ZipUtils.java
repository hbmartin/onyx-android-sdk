package com.onyx.android.sdk.utils;

import android.util.Log;
import androidx.annotation.NonNull;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.EncryptionMethod;

public class ZipUtils {
    private static final int a = 2048;

    public ZipUtils() {}

    public static boolean compress(File[] files, File zipFile) {
        try (ZipOutputStream output = new ZipOutputStream(new FileOutputStream(zipFile))) {
            if (files != null) for (File file : files) a(output, file, "");
            return true;
        } catch (Exception failure) {
            Log.e("zip compress", "fail", failure);
            return false;
        }
    }

    private static void a(ZipOutputStream output, File source, String rootPath) throws Exception {
        String name = rootPath + source.getName();
        if (source.isDirectory()) {
            File[] children = source.listFiles();
            if (children == null || children.length == 0) {
                output.putNextEntry(new ZipEntry(name + '/'));
                output.closeEntry();
            } else {
                for (File child : children) a(output, child, name + '/');
            }
            return;
        }
        output.putNextEntry(new ZipEntry(name));
        try (InputStream input = new FileInputStream(source)) {
            byte[] buffer = new byte[a];
            int count;
            while ((count = input.read(buffer)) != -1) output.write(buffer, 0, count);
        }
        output.closeEntry();
    }

    public static boolean decompress(String zipFile, String dirLocation) {
        return decompressV2(zipFile, dirLocation, null);
    }

    public static boolean decompressV2(String zipFile, String dirLocation) {
        return decompressV2(zipFile, dirLocation, null);
    }

    public static boolean decompressV2(String zipFile, String dirLocation, List<String> unzipFiles) {
        File root = new File(dirLocation);
        try (ZipInputStream input = new ZipInputStream(new FileInputStream(zipFile))) {
            String rootPath = root.getCanonicalPath() + File.separator;
            byte[] buffer = new byte[a];
            ZipEntry entry;
            while ((entry = input.getNextEntry()) != null) {
                File target = new File(root, entry.getName());
                if (!target.getCanonicalPath().startsWith(rootPath)) throw new IOException("Invalid ZIP entry: " + entry.getName());
                if (entry.isDirectory()) {
                    target.mkdirs();
                } else {
                    if (unzipFiles != null) unzipFiles.add(entry.getName());
                    File parent = target.getParentFile();
                    if (parent != null) parent.mkdirs();
                    try (FileOutputStream output = new FileOutputStream(target)) {
                        int count;
                        while ((count = input.read(buffer)) != -1) output.write(buffer, 0, count);
                    }
                }
                input.closeEntry();
            }
            return true;
        } catch (Exception failure) {
            Log.e("zip Decompress", "fail", failure);
            return false;
        }
    }

    private static void a(String location, String dirName) {
        new File(location, dirName).mkdirs();
    }

    public static File zipFiles(File[] sourceFiles, String dstPath, String password) throws ZipException {
        return zipFiles(sourceFiles, new ZipFile(dstPath), password);
    }

    public static File zipFiles(File[] sourceFiles, ZipFile zipFile, String password) {
        try {
            ZipParameters parameters = parameters(password);
            if (sourceFiles != null) {
                for (File source : sourceFiles) {
                    if (source.isDirectory()) zipFile.addFolder(source, parameters);
                    else zipFile.addFile(source, parameters);
                }
            }
            return zipFile.getFile();
        } catch (ZipException failure) {
            Debug.w(a(failure));
            return null;
        }
    }

    public static File zipFolder(File srcFolder, String dstPath, String password) {
        try {
            ZipFile zipFile = new ZipFile(dstPath);
            zipFile.addFolder(srcFolder, parameters(password));
            return zipFile.getFile();
        } catch (ZipException failure) {
            Debug.w(a(failure));
            return null;
        }
    }

    private static ZipParameters parameters(String password) {
        ZipParameters parameters = new ZipParameters();
        if (StringUtils.isNotBlank(password)) {
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(EncryptionMethod.AES);
        }
        return parameters;
    }

    public static boolean unZip(File srcFile, String dstPath, String password) {
        try {
            ZipFile zipFile = new ZipFile(srcFile);
            a(password, zipFile);
            zipFile.extractAll(dstPath);
            return true;
        } catch (ZipException failure) {
            Debug.w(a(failure));
            return false;
        }
    }

    private static void a(String password, ZipFile zipFile) throws ZipException {
        if (zipFile.isEncrypted() && StringUtils.isNotBlank(password)) zipFile.setPassword(password.toCharArray());
    }

    public static String getNoteFileUnZipPath(String dirPath, String zipFilePath, String password) throws ZipException {
        ZipFile zipFile = new ZipFile(zipFilePath);
        a(password, zipFile);
        String fallback = dirPath + FileUtils.getBaseName(zipFilePath);
        List<FileHeader> headers = zipFile.getFileHeaders();
        if (CollectionUtils.isNullOrEmpty(headers)) return fallback;
        String name = headers.get(0).getFileName();
        if (StringUtils.isBlank(name)) return fallback;
        int separator = name.indexOf('/');
        return dirPath + (separator < 0 ? name : name.substring(0, separator));
    }

    private static ZipException a(@NonNull ZipException exception) {
        Throwable cause = exception.getCause();
        return cause instanceof ZipException && cause != exception ? a((ZipException) cause) : exception;
    }

    public static String readEntryString(java.util.zip.ZipFile zip, String entryName) throws IOException {
        byte[] data = readEntryData(zip, entryName);
        return data == null || data.length == 0 ? null : new String(data, StandardCharsets.UTF_8);
    }

    public static byte[] readEntryData(java.util.zip.ZipFile zip, String entryName) {
        ZipEntry entry = zip.getEntry(entryName);
        if (entry == null) return null;
        try (InputStream input = zip.getInputStream(entry); ByteArrayOutputStream output = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[a];
            int count;
            while ((count = input.read(buffer)) != -1) output.write(buffer, 0, count);
            return output.toByteArray();
        } catch (IOException failure) {
            Debug.w(failure);
            return null;
        }
    }

    public static boolean isZipFileByMagicNumber(String filepath) {
        if (StringUtils.isNullOrEmpty(filepath)) return false;
        File file = new File(filepath);
        if (!file.exists()) return false;
        try (FileInputStream input = new FileInputStream(file)) {
            byte[] signature = new byte[4];
            if (input.read(signature) < 4) return false;
            return signature[0] == 80 && signature[1] == 75
                    && (signature[2] == 3 || signature[2] == 5 || signature[2] == 7)
                    && (signature[3] == 4 || signature[3] == 6 || signature[3] == 8);
        } catch (IOException failure) {
            return false;
        }
    }
}
