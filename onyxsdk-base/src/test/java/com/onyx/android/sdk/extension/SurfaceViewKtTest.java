package com.onyx.android.sdk.extension;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.lang.reflect.Proxy;
import kotlin.Unit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.GraphicsMode;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 35)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
public class SurfaceViewKtTest {
    @Test
    public void nullSurfaceDoesNotLockOrInvokeBlock() {
        assertNull(SurfaceViewKt.lock(null, null, true));
        SurfaceViewKt.use(null, null, true, canvas -> {
            fail("block must not run");
            return Unit.INSTANCE;
        });
    }

    @Test
    public void useAlwaysPostsCanvasWhenBlockThrows() {
        RecordingSurface recording = new RecordingSurface();
        SurfaceView surfaceView = recording.createView();
        try {
            SurfaceViewKt.use(surfaceView, null, false, canvas -> {
                throw new IllegalStateException("expected test failure");
            });
            fail("block exception should propagate");
        }
        catch (IllegalStateException expected) {
            assertEquals("expected test failure", expected.getMessage());
        }
        finally {
            assertTrue(recording.softwareLocked);
            assertFalse(recording.hardwareLocked);
            assertEquals(1, recording.unlockCount);
            recording.recycle();
        }
    }

    @Test
    public void lockPassesDirtyRectToSoftwareCanvas() {
        RecordingSurface recording = new RecordingSurface();
        Rect dirty = new Rect(1, 2, 3, 4);
        try {
            assertSame(recording.canvas, SurfaceViewKt.lock(recording.createView(), dirty, false));
            assertEquals(dirty, recording.dirty);
        }
        finally {
            recording.recycle();
        }
    }

    @Test
    public void cleanUsesHardwareCanvasAndRequestedColor() {
        RecordingSurface recording = new RecordingSurface();
        try {
            SurfaceViewKt.clean(recording.createView(), Color.MAGENTA);

            assertTrue(recording.hardwareLocked);
            assertEquals(1, recording.unlockCount);
            assertEquals(Color.MAGENTA, recording.bitmap.getPixel(1, 1));
        }
        finally {
            recording.recycle();
        }
    }

    private static final class RecordingSurface {
        private final Bitmap bitmap = Bitmap.createBitmap(3, 3, Bitmap.Config.ARGB_8888);
        private final Canvas canvas = new Canvas(bitmap);
        private final SurfaceHolder holder;
        private boolean softwareLocked;
        private boolean hardwareLocked;
        private int unlockCount;
        private Rect dirty;

        private RecordingSurface() {
            holder = (SurfaceHolder) Proxy.newProxyInstance(
                    SurfaceHolder.class.getClassLoader(),
                    new Class<?>[] { SurfaceHolder.class },
                    (proxy, method, args) -> {
                        switch (method.getName()) {
                            case "lockHardwareCanvas":
                                hardwareLocked = true;
                                return canvas;
                            case "lockCanvas":
                                softwareLocked = true;
                                if (args != null && args.length == 1) {
                                    dirty = (Rect) args[0];
                                }
                                return canvas;
                            case "unlockCanvasAndPost":
                                unlockCount++;
                                return null;
                            case "getSurfaceFrame":
                                return new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                            case "isCreating":
                                return false;
                            default:
                                return null;
                        }
                    });
        }

        private SurfaceView createView() {
            return new SurfaceView(RuntimeEnvironment.getApplication()) {
                @Override
                public SurfaceHolder getHolder() {
                    return holder;
                }
            };
        }

        private void recycle() {
            bitmap.recycle();
        }
    }
}
