package com.onyx.recovery.validation;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

final class ResultRecorder implements AutoCloseable {
    private static final String TAG = "OnyxValidation";
    static final String MATCH = "match";
    static final String RECOVERY_DEFECT = "recovery_defect";
    static final String PLATFORM_VARIATION = "platform_variation";
    static final String PERMISSION_DENIED = "permission_denied";
    static final String UNSUPPORTED_HARDWARE = "unsupported_hardware";
    static final String HARNESS_ERROR = "harness_error";

    private final Writer writer;
    private final String variant;
    private long sequence;
    private boolean closed;

    ResultRecorder(Context context) throws IOException {
        // One writer for the app's lifetime, flushed per record: the runner
        // only pulls results after the done marker, but a flush per line keeps
        // partial output readable after a crash without reopening the file
        // for every event during replay.
        this(new BufferedWriter(new FileWriter(
                new File(context.getFilesDir(), "results.jsonl"), false)), BuildConfig.SDK_VARIANT);
    }

    ResultRecorder(Writer writer, String variant) {
        this.writer = writer;
        this.variant = variant;
    }

    synchronized void record(String suite, String caseId, String phase, String status,
                             Object inputs, Object result, Throwable error) {
        // onDestroy interrupts the worker without awaiting it; a probe still
        // running when the recorder closes must drop its record instead of
        // crashing the process on the closed writer.
        if (closed) {
            Log.w(TAG, "Dropping record after close: " + suite + "/" + caseId + "/" + phase);
            return;
        }
        JSONObject line = new JSONObject();
        try {
            line.put("sequence", sequence++);
            line.put("variant", variant);
            line.put("suite", suite);
            line.put("caseId", caseId);
            line.put("phase", phase);
            line.put("status", status);
            line.put("monotonicNs", SystemClock.elapsedRealtimeNanos());
            line.put("wallClockMs", System.currentTimeMillis());
            line.put("thread", Thread.currentThread().getName());
            line.put("inputs", jsonValue(inputs));
            line.put("output", jsonValue(result));
            if (error != null) {
                JSONObject exception = new JSONObject();
                exception.put("class", error.getClass().getName());
                exception.put("message", String.valueOf(error.getMessage()));
                line.put("exception", exception);
            }
            append(line);
            if (error == null) {
                Log.i(TAG, "RESULT " + line);
            } else {
                Log.e(TAG, "RESULT " + line, error);
            }
        } catch (JSONException | IOException e) {
            throw new IllegalStateException("Could not record validation result", e);
        }
    }

    void value(String suite, String caseId, Object inputs, Object result) {
        record(suite, caseId, "observation", MATCH, inputs, result, null);
    }

    void failure(String suite, String caseId, Throwable error) {
        String status = isPermissionDenied(error) ? PERMISSION_DENIED : HARNESS_ERROR;
        record(suite, caseId, "observation", status, null, null, error);
    }

    void unsupported(String suite, String caseId, Object result) {
        record(suite, caseId, "observation", UNSUPPORTED_HARDWARE, null, result, null);
    }

    void event(String caseId, Map<String, ?> values) {
        record("pen", caseId, "event", MATCH, null, values, null);
    }

    synchronized void markDone(Context context, String suite) throws IOException {
        // A done marker without its suite_complete record would let the
        // runner pull a stream that never says which suite finished.
        if (closed) throw new IOException("result recorder is closed");
        value("harness", "suite_complete", suite, true);
        Log.i(TAG, "Writing done marker for suite=" + suite);
        try (FileWriter writer = new FileWriter(new File(context.getFilesDir(), "done"), false)) {
            writer.write(suite);
            writer.write('\n');
        }
    }

    @Override
    public synchronized void close() throws IOException {
        if (closed) return;
        closed = true;
        Log.i(TAG, "Closing result stream after " + sequence + " records");
        writer.close();
    }

    private void append(JSONObject object) throws IOException {
        writer.write(object.toString());
        writer.write('\n');
        writer.flush();
    }

    private static boolean isPermissionDenied(Throwable error) {
        for (Throwable current = error; current != null; current = current.getCause()) {
            if (current instanceof SecurityException) return true;
            String message = current.getMessage();
            if (message != null && message.toLowerCase(Locale.ROOT).contains("permission denied")) return true;
        }
        return false;
    }

    static Map<String, Object> map(Object... pairs) {
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        for (int i = 0; i + 1 < pairs.length; i += 2) {
            result.put(String.valueOf(pairs[i]), pairs[i + 1]);
        }
        return result;
    }

    private static Object jsonValue(Object value) throws JSONException {
        if (value == null) return JSONObject.NULL;
        if (value instanceof JSONObject || value instanceof JSONArray || value instanceof Number
                || value instanceof Boolean || value instanceof String) return value;
        if (value instanceof Map<?, ?>) {
            JSONObject object = new JSONObject();
            for (Map.Entry<?, ?> entry : ((Map<?, ?>) value).entrySet()) {
                object.put(String.valueOf(entry.getKey()), jsonValue(entry.getValue()));
            }
            return object;
        }
        if (value instanceof Collection<?>) {
            JSONArray array = new JSONArray();
            for (Object item : (Collection<?>) value) array.put(jsonValue(item));
            return array;
        }
        if (value.getClass().isArray()) {
            JSONArray array = new JSONArray();
            for (int i = 0; i < Array.getLength(value); i++) {
                array.put(jsonValue(Array.get(value, i)));
            }
            return array;
        }
        return String.valueOf(value);
    }
}
