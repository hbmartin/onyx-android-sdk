package com.onyx.android.sdk.data.richtext.v1;

import com.onyx.android.sdk.data.richtext.RichTextResource;
import com.onyx.android.sdk.data.richtext.RichTextResourceLoader;
import com.onyx.android.sdk.utils.FileUtils;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

/** Reads and writes the version-one rich-text container. */
public class RichTextResourceLoaderV1 implements RichTextResourceLoader {
    private static final short c = 1;
    private static final short d = 4;
    private short a = 0;
    private short b = 1;

    private void b(RichTextResource resource) throws IOException {
        try (RandomAccessFile file = new RandomAccessFile(resource.getFilePath(), "rw")) {
            file.seek(0L);
            file.writeShort(a);
            file.writeShort(b);
        }
    }

    private void a(RichTextResource resource) throws Exception {
        try (DataInputStream input = new DataInputStream(new FileInputStream(resource.getFilePath()))) {
            a = input.readShort();
            b = input.readShort();
        }
    }

    @Override
    public void parse(RichTextResource resource) throws Exception {
        if (!FileUtils.fileExist(resource.getFilePath())) {
            return;
        }
        a(resource);
        try (RandomAccessFile file = new RandomAccessFile(resource.getFilePath(), "r")) {
            long payloadLength = file.length() - d;
            if (payloadLength <= 0L) {
                resource.setContent("");
                return;
            }
            if (payloadLength > Integer.MAX_VALUE) {
                throw new IOException("Rich-text payload is too large: " + payloadLength);
            }
            byte[] payload = new byte[(int) payloadLength];
            file.seek(d);
            file.readFully(payload);
            resource.setContent(new String(payload, StandardCharsets.UTF_8));
        }
    }

    @Override
    public boolean save(RichTextResource resource) throws Exception {
        b(resource);
        byte[] payload = resource.getContent().getBytes(StandardCharsets.UTF_8);
        try (RandomAccessFile file = new RandomAccessFile(resource.getFilePath(), "rw")) {
            file.setLength(d + payload.length);
            file.seek(d);
            file.write(payload);
        }
        return true;
    }
}
