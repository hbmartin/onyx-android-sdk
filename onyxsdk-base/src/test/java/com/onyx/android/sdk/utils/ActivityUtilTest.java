package com.onyx.android.sdk.utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, sdk = 35)
public class ActivityUtilTest {
    @Test
    public void getActivitySafetyUnwrapsNestedActivityContext() {
        Activity activity = Robolectric.buildActivity(Activity.class).setup().get();
        Context wrapped = new ContextWrapper(new ContextWrapper(activity));

        assertSame(activity, ActivityUtil.getActivitySafety(wrapped));
    }

    @Test
    public void getActivitySafetyReturnsNullForNonActivityContext() {
        assertNull(ActivityUtil.getActivitySafety(RuntimeEnvironment.getApplication()));
    }

    @Test
    public void getLocationInActivityFallsBackToScreenCoordinatesWithoutActivity() {
        View view = new View(RuntimeEnvironment.getApplication());

        assertArrayEquals(
                ViewUtils.getLocationOnScreen(view), ViewUtils.getLocationInActivity(view));
    }

    @Test
    public void getActivitySafetyReturnsNullForSelfReferencingWrapper() {
        ContextWrapper wrapper = new ContextWrapper(RuntimeEnvironment.getApplication()) {
            @Override
            public Context getBaseContext() {
                return this;
            }
        };

        assertNull(ActivityUtil.getActivitySafety(wrapper));
    }
}
