package com.onyx.android.sdk.data.point.v1;

import com.onyx.android.sdk.data.file.StreamProvider;
import com.onyx.android.sdk.data.point.PointDocument;
import com.onyx.android.sdk.data.point.PointDocumentLoader;
import com.onyx.android.sdk.data.point.PointDocumentUtils;
import com.onyx.android.sdk.data.point.ShapeRepo;
import com.onyx.android.sdk.data.point.TinyPoint;
import com.onyx.android.sdk.utils.ChannelUtils;
import com.onyx.android.sdk.utils.CollectionUtils;
import com.onyx.android.sdk.utils.Debug;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PointDocumentLoaderV1 implements PointDocumentLoader {
    private static final int HEADER_SIZE = 76;
    private static final int INDEX_ENTRY_SIZE = 44;
    private static final int POINT_SIZE = 16;
    private static final int LARGE_POINT_COUNT = 20000;
    private static final int CHUNK_BYTES = 0x5000000;

    private List<ShapeRepo> a(PointDocument document, FileChannel channel,
                              int start, int count) throws IOException {
        List<ShapeRepo> result = new ArrayList<>();
        for (int i = start; i < start + count; i++) {
            channel.position((long) document.getXref() + (long) i * INDEX_ENTRY_SIZE);
            ByteBuffer idBuffer = read(channel, 36);
            String id = new String(idBuffer.array(), StandardCharsets.UTF_8).trim();
            int offset = read(channel, 4).getInt(0);
            int length = read(channel, 4).getInt(0);
            result.add(new ShapeRepo().setShapeUniqueId(id).setOffset(offset).setLength(length));
        }
        return result;
    }

    private static ByteBuffer read(FileChannel channel, int size) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(size);
        while (buffer.hasRemaining()) {
            if (channel.read(buffer) < 0) throw new IOException("Unexpected end of point document");
        }
        return buffer;
    }

    private void a(FileChannel channel, List<ShapeRepo> repositories) throws IOException {
        for (ShapeRepo repository : repositories) {
            if ((repository.getLength() - 4) / POINT_SIZE > LARGE_POINT_COUNT) {
                a(channel, repository);
            } else {
                b(channel, repository);
            }
        }
    }

    private void b(FileChannel channel, ShapeRepo repository) throws IOException {
        channel.position(repository.getOffset());
        ByteBuffer buffer = read(channel, repository.getLength());
        try (DataInputStream data = new DataInputStream(
                new ByteArrayInputStream(buffer.array()))) {
            repository.setAttrA(data.readShort());
            repository.setAttrB(data.readShort());
            int count = (repository.getLength() - 4) / POINT_SIZE;
            List<TinyPoint> points = new ArrayList<>(count);
            for (int i = 0; i < count; i++) points.add(a(data));
            repository.setTinyPointList(points);
        }
    }

    private void a(FileChannel channel, ShapeRepo repository) throws IOException {
        channel.position(repository.getOffset());
        repository.setAttrA(ChannelUtils.readShort(channel));
        repository.setAttrB(ChannelUtils.readShort(channel));
        int dataBytes = repository.getLength() - 4;
        int pointCount = dataBytes / POINT_SIZE;
        int step = PointDocumentUtils.getPointCountBreakValue(pointCount);
        List<TinyPoint> points = new ArrayList<>();
        for (int consumed = 0; consumed < dataBytes; consumed += CHUNK_BYTES) {
            points.addAll(a(channel, Math.min(CHUNK_BYTES, dataBytes - consumed), step));
        }
        repository.setTinyPointList(points);
        if (step > 1) {
            Debug.w(getClass(), "large touch point count:" + pointCount
                    + ", render count: " + CollectionUtils.getSize(points), new Object[0]);
        }
    }

    private List<TinyPoint> a(FileChannel channel, int length, int step) throws IOException {
        ByteBuffer buffer = read(channel, length);
        List<TinyPoint> points = new ArrayList<>();
        try (DataInputStream data = new DataInputStream(
                new ByteArrayInputStream(buffer.array()))) {
            int count = length / POINT_SIZE;
            for (int i = 0; i < count; i += step) {
                if (step > 1 && i > 0) data.skipBytes((step - 1) * POINT_SIZE);
                points.add(a(data));
            }
        }
        return points;
    }

    private TinyPoint a(DataInputStream data) throws IOException {
        float x = data.readFloat();
        float y = data.readFloat();
        short size = data.readShort();
        short pressure = data.readShort();
        int time = data.readInt();
        return new TinyPoint(x, y, pressure, size, time);
    }

    private int a(List<ShapeRepo> repositories, ByteBuffer output, int offset) {
        for (ShapeRepo repository : repositories) {
            repository.setOffset(offset);
            int length = repository.getTinyPointList().size() * POINT_SIZE + 4;
            repository.setLength(length);
            output.putShort(repository.getAttrA());
            output.putShort(repository.getAttrB());
            for (TinyPoint point : repository.getTinyPointList()) {
                output.putFloat(point.getX());
                output.putFloat(point.getY());
                output.putShort(point.getSize());
                output.putShort(point.getPressure());
                output.putInt(point.getTime());
            }
            offset += length;
        }
        return offset;
    }

    private void b(List<ShapeRepo> repositories, ByteBuffer output, int xref) {
        for (ShapeRepo repository : repositories) {
            byte[] id = new byte[36];
            Arrays.fill(id, (byte) ' ');
            byte[] source = repository.getShapeUniqueId().getBytes(StandardCharsets.UTF_8);
            System.arraycopy(source, 0, id, 0, Math.min(id.length, source.length));
            output.put(id);
            output.putInt(repository.getOffset());
            output.putInt(repository.getLength());
        }
        output.putInt(xref);
    }

    @Override
    public void parse(PointDocument document) throws Exception {
        parse(document, 0, Integer.MAX_VALUE);
    }

    @Override
    public void parse(PointDocument document, int start, int count) throws Exception {
        try (FileInputStream input = new FileInputStream(document.getFilePath());
             FileChannel channel = input.getChannel()) {
            long indexBytes = document.getDocumentSize() - document.getXref() - 4L;
            if (indexBytes < 0 || indexBytes % INDEX_ENTRY_SIZE != 0L) {
                throw new Exception("error file data");
            }
            int total = (int) (indexBytes / INDEX_ENTRY_SIZE);
            int safeStart = Math.max(0, Math.min(start, total));
            int safeCount = Math.max(0, Math.min(count, total - safeStart));
            List<ShapeRepo> repositories = a(document, channel, safeStart, safeCount);
            a(channel, repositories);
            document.setShapeRepoList(repositories);
        }
    }

    @Override
    public void save(PointDocument document) throws Exception {
        List<ShapeRepo> repositories = document.getShapeRepoList();
        int pointBytes = 0;
        for (ShapeRepo repository : repositories) {
            pointBytes += repository.getTinyPointList().size() * POINT_SIZE + 4;
        }
        int indexBytes = repositories.size() * INDEX_ENTRY_SIZE;
        ByteBuffer output = ByteBuffer.allocate(HEADER_SIZE + pointBytes + indexBytes + 4);
        document.addHeaderToBuf(output);
        int xref = a(repositories, output, HEADER_SIZE);
        b(repositories, output, xref);
        output.flip();
        try (FileOutputStream stream = StreamProvider.getOutputStream(document.getFilePath());
             FileChannel channel = stream.getChannel()) {
            while (output.hasRemaining()) channel.write(output);
            channel.force(true);
        }
    }
}
