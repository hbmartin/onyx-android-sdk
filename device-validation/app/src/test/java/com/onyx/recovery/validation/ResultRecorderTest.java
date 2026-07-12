package com.onyx.recovery.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import java.io.IOException;
import java.io.Writer;

public final class ResultRecorderTest {
    @Test
    public void recordAfterCloseIsDropped() throws IOException {
        TrackingWriter writer = new TrackingWriter();
        ResultRecorder recorder = new ResultRecorder(writer, "test");

        recorder.close();
        recorder.record("base", "late_probe", "observation", ResultRecorder.MATCH,
                null, true, null);

        assertEquals(0, writer.writtenCharacters);
        assertEquals(1, writer.closeCalls);
    }

    @Test
    public void markDoneAfterCloseThrowsWithoutWriting() throws IOException {
        TrackingWriter writer = new TrackingWriter();
        ResultRecorder recorder = new ResultRecorder(writer, "test");
        recorder.close();

        IOException error = assertThrows(
                IOException.class,
                () -> recorder.markDone(null, "base")
        );

        assertEquals("result recorder is closed", error.getMessage());
        assertEquals(0, writer.writtenCharacters);
    }

    @Test
    public void markDoneSurfacesRecordWriteFailureAsIOException() {
        ResultRecorder recorder = new ResultRecorder(new FailingWriter(), "test");

        IOException error = assertThrows(
                IOException.class,
                () -> recorder.markDone(null, "base")
        );

        assertEquals("stream lost", error.getMessage());
    }

    @Test
    public void recordWrapsWriteFailureAsIllegalState() {
        ResultRecorder recorder = new ResultRecorder(new FailingWriter(), "test");

        assertThrows(
                IllegalStateException.class,
                () -> recorder.record("base", "probe", "observation", ResultRecorder.MATCH,
                        null, true, null)
        );
    }

    @Test
    public void repeatedCloseOnlyClosesWriterOnce() throws IOException {
        TrackingWriter writer = new TrackingWriter();
        ResultRecorder recorder = new ResultRecorder(writer, "test");

        recorder.close();
        recorder.close();

        assertEquals(1, writer.closeCalls);
    }

    private static final class FailingWriter extends Writer {
        @Override
        public void write(char[] buffer, int offset, int length) throws IOException {
            throw new IOException("stream lost");
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() {
        }
    }

    private static final class TrackingWriter extends Writer {
        private int writtenCharacters;
        private int closeCalls;

        @Override
        public void write(char[] buffer, int offset, int length) {
            writtenCharacters += length;
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() {
            closeCalls += 1;
        }
    }
}
