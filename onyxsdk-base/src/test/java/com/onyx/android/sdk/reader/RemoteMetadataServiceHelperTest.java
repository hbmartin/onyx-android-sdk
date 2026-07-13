package com.onyx.android.sdk.reader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import com.onyx.android.sdk.rx.RxRequest;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 35)
public class RemoteMetadataServiceHelperTest {
    @Test
    public void bindsInvokesCallbackUnbindsAndClearsState() {
        RecordingContext context = new RecordingContext(RuntimeEnvironment.getApplication());
        RxRequest request = new RxRequest() {
            @Override
            public void execute() {}
        }.setContext(context);
        AtomicBoolean callbackInvoked = new AtomicBoolean();
        RemoteMetadataServiceHelper helper = new RemoteMetadataServiceHelper(request, service -> {
            assertNotNull(service);
            callbackInvoked.set(true);
        });

        helper.executeRemoteService();

        assertTrue(context.bound);
        assertTrue(context.unbound);
        assertEquals(5, context.flags);
        assertEquals(RemoteMetadataServiceHelper.PACKAGE_NAME,
                context.intent.getComponent().getPackageName());
        assertEquals(RemoteMetadataServiceHelper.READER_METADATA_SERVICE_CLASS,
                context.intent.getComponent().getClassName());
        assertTrue(callbackInvoked.get());
        assertNull(helper.getConnection());
        assertNull(helper.getRemoteService());
    }

    @Test
    public void bindFailureIsContainedAndStateIsCleared() {
        RecordingContext context = new RecordingContext(RuntimeEnvironment.getApplication());
        context.failBinding = true;
        RxRequest request = new RxRequest() {
            @Override
            public void execute() {}
        }.setContext(context);
        AtomicBoolean callbackInvoked = new AtomicBoolean();
        RemoteMetadataServiceHelper helper = new RemoteMetadataServiceHelper(
                request, service -> callbackInvoked.set(true));

        helper.executeRemoteService();

        assertFalse(callbackInvoked.get());
        assertFalse(context.unbound);
        assertNull(helper.getConnection());
        assertNull(helper.getRemoteService());
    }

    @Test
    public void rejectedBindReturnsWithoutWaiting() {
        RecordingContext context = new RecordingContext(RuntimeEnvironment.getApplication());
        context.rejectBinding = true;
        RxRequest request = new RxRequest() {
            @Override
            public void execute() {}
        }.setContext(context);
        AtomicBoolean callbackInvoked = new AtomicBoolean();
        RemoteMetadataServiceHelper helper = new RemoteMetadataServiceHelper(
                request, service -> callbackInvoked.set(true));

        helper.executeRemoteService();

        assertFalse(callbackInvoked.get());
        assertFalse(context.unbound);
        assertNull(helper.getConnection());
        assertNull(helper.getRemoteService());
    }

    @Test
    public void callbackFailureStillUnbindsAndClearsState() {
        RecordingContext context = new RecordingContext(RuntimeEnvironment.getApplication());
        RxRequest request = new RxRequest() {
            @Override
            public void execute() {}
        }.setContext(context);
        RemoteMetadataServiceHelper helper = new RemoteMetadataServiceHelper(
                request, service -> { throw new IllegalStateException("callback failed"); });

        helper.executeRemoteService();

        assertTrue(context.unbound);
        assertNull(helper.getConnection());
        assertNull(helper.getRemoteService());
    }

    private static final class RecordingContext extends ContextWrapper {
        private boolean bound;
        private boolean unbound;
        private boolean failBinding;
        private boolean rejectBinding;
        private int flags;
        private Intent intent;

        RecordingContext(Context base) {
            super(base);
        }

        @Override
        public boolean bindService(Intent service, ServiceConnection connection, int flags) {
            if (failBinding) {
                throw new IllegalStateException("bind failed");
            }
            if (rejectBinding) {
                return false;
            }
            this.bound = true;
            this.flags = flags;
            this.intent = service;
            connection.onServiceConnected(service.getComponent(), new Binder());
            return true;
        }

        @Override
        public void unbindService(ServiceConnection connection) {
            this.unbound = true;
        }
    }
}
