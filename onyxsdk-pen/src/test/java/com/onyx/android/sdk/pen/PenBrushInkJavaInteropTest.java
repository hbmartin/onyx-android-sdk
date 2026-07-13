package com.onyx.android.sdk.pen;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PenBrushInkJavaInteropTest {
    @Test
    public void integerConstructorAndAccessorsAreDirectlyCallableFromJava() {
        PenBrushInk ink = new PenBrushInk(10.0f, 20.0f, 255, 35, 200);

        assertEquals(255, ink.getSizeAsInt());
        assertEquals(35, ink.getAngle36AsInt());
        assertEquals(200, ink.getAlphaAsInt());
    }
}
