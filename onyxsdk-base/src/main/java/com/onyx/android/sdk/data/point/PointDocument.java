package com.onyx.android.sdk.data.point;

import com.onyx.android.sdk.data.point.v1.PointDocumentLoaderV1;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.Debug;
import com.onyx.android.sdk.utils.FileUtils;
import com.onyx.android.sdk.utils.StringUtils;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class PointDocument {
    private static final int HEADER_SIZE = 76;
    private static final int UUID_SIZE = 36;

    private PointDocumentLoader a;
    private String b;
    private short c = 0;
    private short d = 1;
    private String e;
    private String f;
    private int g;
    private List<ShapeRepo> h;

    public PointDocument(String path) {
        this.b = path;
    }

    private void b() throws Exception {
        try (FileInputStream input = new FileInputStream(b)) {
            a(input);
            long size = getDocumentSize();
            if (size < 4) throw new IOException("Point document is too short");
            input.getChannel().position(size - 4);
            ByteBuffer xref = ByteBuffer.allocate(4);
            while (xref.hasRemaining() && input.getChannel().read(xref) >= 0) {}
            xref.flip();
            g = xref.getInt();
        }
    }

    private PointDocumentLoader a() {
        if (d == 1 && a == null) a = new PointDocumentLoaderV1();
        if (a != null) return a;
        throw new IllegalArgumentException("error version:" + d);
    }

    private void a(FileInputStream input) throws IOException {
        byte[] header = FileUtils.readBytes(input, 0, HEADER_SIZE);
        if (header == null || header.length < HEADER_SIZE) {
            throw new IOException("Invalid point document header");
        }
        try (DataInputStream data = new DataInputStream(new ByteArrayInputStream(header))) {
            c = data.readShort();
            d = data.readShort();
            byte[] uuid = new byte[UUID_SIZE];
            data.readFully(uuid);
            e = new String(uuid, StandardCharsets.UTF_8);
            data.readFully(uuid);
            f = new String(uuid, StandardCharsets.UTF_8);
        }
    }

    private void b(FileInputStream input) throws IOException {
        long size = getDocumentSize();
        input.getChannel().position(size - 4);
        ByteBuffer xref = ByteBuffer.allocate(4);
        input.getChannel().read(xref);
        g = xref.getInt(0);
    }

    private byte[] a(String uuid) {
        byte[] result = new byte[UUID_SIZE];
        Arrays.fill(result, (byte) ' ');
        if (uuid != null) {
            byte[] encoded = uuid.getBytes(StandardCharsets.UTF_8);
            System.arraycopy(encoded, 0, result, 0, Math.min(encoded.length, result.length));
        }
        return result;
    }

    public int getXref() {
        return g;
    }

    public PointDocument parse() throws Exception {
        return parse(0, Integer.MAX_VALUE);
    }

    public PointDocument parse(int start, int count) throws Exception {
        if (getDocumentSize() <= 0L) {
            Debug.e(getClass(), "error document size:" + getDocumentSize(), new Object[0]);
        }
        b();
        a().parse(this, start, count);
        return this;
    }

    public String getFilePath() {
        return b;
    }

    public PointDocument setShapeRepoList(List<ShapeRepo> shapeRepoList) {
        h = shapeRepoList;
        return this;
    }

    public long getDocumentSize() {
        return FileUtils.getFileSize(b);
    }

    public boolean save() throws Exception {
        if (CollectionUtils.isNullOrEmpty(h)) return false;
        FileUtils.ensureFileExists(b);
        a().save(this);
        return true;
    }

    public ByteBuffer addHeaderToBuf(ByteBuffer byteBuffer) {
        byteBuffer.putShort(c);
        byteBuffer.putShort(d);
        byteBuffer.put(a(e));
        byteBuffer.put(a(f));
        return byteBuffer;
    }

    public List<ShapeRepo> getShapeRepoList() {
        return h;
    }

    public String getPageUniqueId() {
        return StringUtils.trim(e);
    }

    public PointDocument setPageUniqueId(String pageUniqueId) {
        e = pageUniqueId;
        return this;
    }

    public String getRevisionId() {
        return StringUtils.trim(f);
    }

    public PointDocument setRevisionId(String revisionId) {
        f = revisionId;
        return this;
    }
}
