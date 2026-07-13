package com.onyx.android.sdk.note;

import static org.junit.Assert.assertEquals;

import android.os.RemoteException;
import org.junit.Test;

public class NoteContentIntentCallbackHolderTest {
    @Test
    public void callbackIsConsumedOnlyOnce() throws Exception {
        CountingCallback callback = new CountingCallback(false);
        NoteContentIntentCallbackHolder holder = new NoteContentIntentCallbackHolder(callback);

        holder.read(null);
        holder.read(null);

        assertEquals(1, callback.calls);
    }

    @Test
    public void throwingCallbackIsStillCleared() throws Exception {
        CountingCallback callback = new CountingCallback(true);
        NoteContentIntentCallbackHolder holder = new NoteContentIntentCallbackHolder(callback);

        holder.read(null);
        holder.read(null);

        assertEquals(1, callback.calls);
    }

    private static final class CountingCallback extends NoteContentIntentCallback.Default {
        private final boolean shouldThrow;
        private int calls;

        private CountingCallback(boolean shouldThrow) {
            this.shouldThrow = shouldThrow;
        }

        @Override
        public void read(NoteContentIntentArgs args) throws RemoteException {
            calls++;
            if (shouldThrow) {
                throw new RemoteException("expected test failure");
            }
        }
    }
}
