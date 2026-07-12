package com.onyx.android.sdk.media;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.FileUtils;
import com.onyx.android.sdk.utils.StringUtils;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class WavUtils {
    private static final String a = "baiduTTS";
    private static final String b = "audio_cache";

    public static String createTmpDir(Context context) {
        return createTmpDir(context, a);
    }

    public static String createTmpDir(Context context, String sampleDir) {
        String path = Environment.getExternalStorageDirectory().toString() + "/" + sampleDir;
        if (!FileUtils.mkdirs(path)) {
            path = context.getExternalFilesDir(sampleDir).getAbsolutePath();
            if (!FileUtils.mkdirs(path)) {
                throw new RuntimeException("create model resources dir failed :" + path);
            }
        }
        return path;
    }

    public static void copyFromAssets(AssetManager assets, String source, String dest) throws IOException {
        if (a(assets, source, dest)) {
            return;
        }
        try (InputStream input = assets.open(source);
             FileOutputStream output = new FileOutputStream(dest)) {
            byte[] buffer = new byte[1024];
            int count;
            while ((count = input.read(buffer, 0, buffer.length)) >= 0) {
                output.write(buffer, 0, count);
            }
        }
    }

    private static boolean a(AssetManager assets, String source, String dest) throws IOException {
        File target = new File(dest);
        return target.exists() && target.length() == a(assets, source);
    }

    private static long a(AssetManager assets, String source) throws IOException {
        try (InputStream input = assets.open(source)) {
            return input.available();
        }
    }

    public static String getCacheDirPath(Context context, String dir) {
        String relativeDir = StringUtils.isNullOrEmpty(dir)
                ? b
                : b + File.separator + dir;
        return context.getExternalCacheDir().getAbsoluteFile() + File.separator + relativeDir;
    }

    public static File getTempWavFile(Context context, String dir, String fileName) {
        String cacheDirPath = getCacheDirPath(context, dir);
        FileUtils.mkdirs(cacheDirPath);
        return new File(cacheDirPath, fileName + ".wav");
    }

    public static void clearTempWavFile(Context context, String dir) {
        clearTempWavFile(new File(getCacheDirPath(context, dir)));
    }

    public static void clearTempWavFile(File dir) {
        if (dir != null) {
            File[] files = dir.listFiles();
            if (files != null) {
                a(files);
            }
        }
    }

    public static byte[] addWavHeader(byte[] pmcSrc) {
        try {
            byte[] header = getWavHeaderData(pmcSrc.length);
            byte[] wav = new byte[header.length + pmcSrc.length];
            System.arraycopy(header, 0, wav, 0, header.length);
            System.arraycopy(pmcSrc, 0, wav, header.length, pmcSrc.length);
            return wav;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static byte[] addWavHeader(String srcPath) {
        try {
            byte[] header = getWavHeaderData((int) (FileUtils.getFileSize(srcPath) - WaveHeader.HEADER_LENGTH));
            byte[] source = FileUtils.readBytesOfFile(srcPath);
            byte[] wav = new byte[header.length + source.length];
            System.arraycopy(header, 0, wav, 0, header.length);
            System.arraycopy(source, 0, wav, header.length, source.length);
            return wav;
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static void writeWavHeader(File src) {
        try (RandomAccessFile file = new RandomAccessFile(src, "rw")) {
            byte[] header = getWavHeaderData((int) (file.getChannel().size() - WaveHeader.HEADER_LENGTH));
            file.getChannel().write(ByteBuffer.wrap(header, 0, header.length), 0L);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        Debug.d("Write wav header SUCCESS!");
    }

    public static byte[] getWavHeaderData(int pcmSize) throws IOException {
        WaveHeader header = new WaveHeader();
        header.fileLength = pcmSize + 36;
        header.FmtHdrLeth = 16;
        header.BitsPerSample = 16;
        header.Channels = 1;
        header.FormatTag = 1;
        header.SamplesPerSec = 16000;
        header.BlockAlign = (short) (header.Channels * header.BitsPerSample / 8);
        header.AvgBytesPerSec = header.BlockAlign * header.SamplesPerSec;
        header.DataHdrLeth = pcmSize;
        return header.getHeader();
    }

    private static void a(File[] files) {
        for (File file : files) {
            if (a(file)) {
                file.delete();
            }
        }
    }

    private static boolean a(File file) {
        return FileUtils.isWav(file.getAbsolutePath());
    }

    public static class WaveHeader {
        public static final int HEADER_LENGTH = 44;
        public final char[] fileID = {'R', 'I', 'F', 'F'};
        public int fileLength;
        public char[] wavTag = {'W', 'A', 'V', 'E'};
        public char[] FmtHdrID = {'f', 'm', 't', ' '};
        public int FmtHdrLeth;
        public short FormatTag;
        public short Channels;
        public int SamplesPerSec;
        public int AvgBytesPerSec;
        public short BlockAlign;
        public short BitsPerSample;
        public char[] DataHdrID = {'d', 'a', 't', 'a'};
        public int DataHdrLeth;

        public static boolean isWave(byte[] data) {
            if (data == null || data.length < HEADER_LENGTH) {
                return false;
            }
            WaveHeader header = new WaveHeader();
            for (int i = 0; i < header.fileID.length; i++) {
                if (data[i] != header.fileID[i]) {
                    return false;
                }
            }
            int wavOffset = header.fileID.length + 4;
            for (int i = 0; i < header.wavTag.length; i++) {
                if (data[wavOffset + i] != header.wavTag[i]) {
                    return false;
                }
            }
            return true;
        }

        private void b(ByteArrayOutputStream output, int value) throws IOException {
            output.write(new byte[] {
                (byte) value,
                (byte) (value >> 8)
            });
        }

        private void a(ByteArrayOutputStream output, int value) throws IOException {
            output.write(new byte[] {
                (byte) value,
                (byte) (value >> 8),
                (byte) (value >> 16),
                (byte) (value >> 24)
            });
        }

        private void a(ByteArrayOutputStream output, char[] id) {
            for (char value : id) {
                output.write(value);
            }
        }

        public byte[] getHeader() throws IOException {
            try (ByteArrayOutputStream output = new ByteArrayOutputStream()) {
                a(output, fileID);
                a(output, fileLength);
                a(output, wavTag);
                a(output, FmtHdrID);
                a(output, FmtHdrLeth);
                b(output, FormatTag);
                b(output, Channels);
                a(output, SamplesPerSec);
                a(output, AvgBytesPerSec);
                b(output, BlockAlign);
                b(output, BitsPerSample);
                a(output, DataHdrID);
                a(output, DataHdrLeth);
                output.flush();
                return output.toByteArray();
            }
        }
    }
}
