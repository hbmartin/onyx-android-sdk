package com.onyx.android.sdk.utils;

import android.os.MemoryFile;
import android.os.ParcelFileDescriptor;
import androidx.annotation.Nullable;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class MemoryFileUtils {
    private static String a = "MemoryFileUtils";
    private static final String b = "getFileDescriptor";
    private static final int c = -1;

    @Nullable
    public static ParcelFileDescriptor createMemoryFile(byte[] contentBytes) {
        return createMemoryFile(null, contentBytes);
    }

    public static String readMemoryFile(ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor == null) {
            return null;
        }
        FileInputStream input = null;
        InputStreamReader inputReader = null;
        BufferedReader reader = null;
        try {
            Debug.d(a, "read fd = " + getFdSafely(parcelFileDescriptor), new Object[0]);
            input = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
            inputReader = new InputStreamReader(input);
            reader = new BufferedReader(inputReader);
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            return content.toString();
        } catch (IOException error) {
            error.printStackTrace();
            return null;
        } finally {
            FileUtils.closeQuietly(reader);
            FileUtils.closeQuietly(inputReader);
            FileUtils.closeQuietly(input);
            closeQuietly(parcelFileDescriptor);
        }
    }

    public static byte[] readByteArray(ParcelFileDescriptor parcelFileDescriptor) {
        if (parcelFileDescriptor == null) {
            return null;
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        FileInputStream input = null;
        BufferedInputStream bufferedInput = null;
        try {
            Debug.d(a, "read fd = " + getFdSafely(parcelFileDescriptor), new Object[0]);
            input = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
            bufferedInput = new BufferedInputStream(input);
            byte[] buffer = new byte[1024];
            int count;
            while ((count = bufferedInput.read(buffer)) != -1) {
                output.write(buffer, 0, count);
            }
        } catch (IOException error) {
            error.printStackTrace();
        } finally {
            FileUtils.closeQuietly(bufferedInput);
            FileUtils.closeQuietly(input);
            closeQuietly(parcelFileDescriptor);
        }
        return output.toByteArray();
    }

    public static void closeQuietly(ParcelFileDescriptor pfd) {
        if (pfd == null) {
            return;
        }
        Debug.d(a, "close fd = " + getFdSafely(pfd), new Object[0]);
        FileUtils.closeQuietly(pfd);
    }

    public static int getFdSafely(ParcelFileDescriptor pfd) {
        if (pfd == null) {
            return c;
        }
        try {
            return pfd.getFd();
        } catch (Throwable ignored) {
            return c;
        }
    }

    @Nullable
    public static ParcelFileDescriptor createMemoryFile(
            @Nullable String fileName, byte[] contentBytes) {
        ParcelFileDescriptor descriptor = null;
        MemoryFile memoryFile = null;
        try {
            memoryFile = new MemoryFile(fileName, contentBytes.length);
            memoryFile.writeBytes(contentBytes, 0, 0, contentBytes.length);
            FileDescriptor fileDescriptor = (FileDescriptor) ReflectUtil.invokeMethodSafely(
                    ReflectUtil.getDeclaredMethodSafely(MemoryFile.class, b),
                    memoryFile);
            descriptor = ParcelFileDescriptor.dup(fileDescriptor);
            Debug.d(a, "open fd = " + getFdSafely(descriptor), new Object[0]);
        } catch (IOException error) {
            Debug.e(error);
        } finally {
            if (memoryFile != null) {
                memoryFile.close();
            }
        }
        return descriptor;
    }
}
