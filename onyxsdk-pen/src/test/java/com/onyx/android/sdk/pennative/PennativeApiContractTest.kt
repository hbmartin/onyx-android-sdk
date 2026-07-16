package com.onyx.android.sdk.pennative

import android.graphics.Bitmap
import java.lang.reflect.Modifier
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertSame
import org.junit.Assert.assertTrue
import org.junit.Test

class PennativeApiContractTest {
    @Test
    fun penConfigMatchesThe104FieldAndDefaultContract() {
        val config = PenConfig()

        assertEquals(0, config.type)
        assertFalse(config.fastMode)
        assertEquals(0xff000000.toInt(), config.color)
        assertEquals(3f, config.width)
        assertEquals(0f, config.minWidth)
        assertEquals(0, config.rotateAngle)
        assertFalse(config.tiltEnabled)
        assertEquals(0f, config.tiltScale)
        assertFalse(config.directionEnabled)
        assertEquals(1f, config.maxTouchPressure)
        assertEquals(320f, config.dpi)
        assertEquals(1f, config.displayScaleX)
        assertEquals(1f, config.displayScaleY)
        assertEquals(1f, config.scalePrecision)
        assertEquals(0.25f, config.brushSpacing)
        assertEquals(PenConfig.NEOPEN_BRUSH_SHAPE_CIRCLE, config.brushShape)
        assertEquals(5f, config.brushRatio)
        assertEquals(0f, config.brushAngle)
        assertEquals(0f, config.pressureSensitivity)
        assertEquals(0f, config.velocitySensitivity)
        assertEquals(0f, config.velocityAmplifier)
        assertEquals(0f, config.velocityIgnoreThreshold)
        assertEquals(0f, config.velocityLowerBound)
        assertEquals(0f, config.velocityUpperBound)
        assertEquals(0.2f, config.smoothLevel)
        assertEquals(0f, config.startPointLimit)
        assertEquals(0f, config.startLengthLimit)
        assertEquals(0f, config.endVelocitySensitivity)
        assertEquals(0f, config.endThinningRate)
        assertEquals(0f, config.ignorePressure)

        val instanceFields = PenConfig::class.java.declaredFields.filterNot {
            Modifier.isStatic(it.modifiers)
        }
        assertEquals(30, instanceFields.size)
        assertTrue(instanceFields.all { Modifier.isPrivate(it.modifiers) })
        assertEquals(30, PenConfig::class.java.declaredMethods.count { it.name.startsWith("get") })
        val setters = PenConfig::class.java.declaredMethods.filter { it.name.startsWith("set") }
        assertEquals(30, setters.size)
        assertTrue(setters.all { it.returnType == Void.TYPE })
    }

    @Test
    fun neoPenNativePublishesOnlyThe104FacadeMethods() {
        val methods = NeoPenNative::class.java.declaredMethods
            .filter { Modifier.isPublic(it.modifiers) && !Modifier.isStatic(it.modifiers) }
            .map { it.name }
            .sorted()

        assertEquals(
            listOf(
                "createLogger",
                "createPen",
                "destroyPen",
                "onPenDown",
                "onPenMove",
                "onPenUp",
                "registerLogger",
                "setLogLevel",
            ),
            methods,
        )
    }

    @Test
    fun penInkValueObjectsMatchThe104Behavior() {
        val real = PenInk(floatArrayOf(1f, 2.5f), intArrayOf(2), emptyArray<Bitmap>())
        val prediction = PenInk(floatArrayOf(3f, 4.5f), intArrayOf(2), emptyArray<Bitmap>())
        assertEquals("PenInk: 2 -> [1.0,2.5]", real.toString())
        val direct = PenInkResult(real, prediction)
        val created = PenInkResult.create(real, prediction)
        assertSame(real, direct.realInk)
        assertSame(prediction, direct.predictionInk)
        assertSame(real, created.realInk)
        assertSame(prediction, created.predictionInk)
    }
}
