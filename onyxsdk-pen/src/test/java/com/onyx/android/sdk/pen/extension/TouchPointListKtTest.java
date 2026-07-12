package com.onyx.android.sdk.pen.extension;

import static org.junit.Assert.assertEquals;

import com.onyx.android.sdk.data.note.TouchPoint;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class TouchPointListKtTest {
    @Test(timeout = 5000)
    public void filtersNeighborsCloserThanTheDrawRadius() {
        List<TouchPoint> points = Arrays.asList(
                new TouchPoint(0.0f, 0.0f),
                new TouchPoint(1.0f, 1.0f),
                new TouchPoint(50.0f, 50.0f));
        List<TouchPoint> filtered = TouchPointListKt.filterByGridSpacing(points, 10.0f);
        // (1,1) falls inside the 10px radius of the registered (0,0) grid
        // entry and must be rejected; the final point is always kept.
        assertEquals(2, filtered.size());
        assertEquals(0.0f, filtered.get(0).x, 0.0f);
        assertEquals(50.0f, filtered.get(1).x, 0.0f);
    }

    @Test(timeout = 5000)
    public void keepsPointsSpacedWiderThanTheDrawRadius() {
        List<TouchPoint> points = Arrays.asList(
                new TouchPoint(0.0f, 0.0f),
                new TouchPoint(100.0f, 0.0f),
                new TouchPoint(200.0f, 0.0f),
                new TouchPoint(300.0f, 0.0f));
        List<TouchPoint> filtered = TouchPointListKt.filterByGridSpacing(points, 10.0f);
        assertEquals(4, filtered.size());
    }

    @Test(timeout = 5000)
    public void terminatesOnRepeatedCoincidentPoints() {
        // Regression guard: the mis-reconstructed neighbor scan spun forever
        // on any list with at least two points.
        List<TouchPoint> points = Arrays.asList(
                new TouchPoint(5.0f, 5.0f),
                new TouchPoint(5.0f, 5.0f),
                new TouchPoint(5.0f, 5.0f));
        List<TouchPoint> filtered = TouchPointListKt.filterByGridSpacing(points, 8.0f);
        // The duplicate middle point is rejected; the trailing point is
        // always appended without a grid check.
        assertEquals(2, filtered.size());
    }
}
