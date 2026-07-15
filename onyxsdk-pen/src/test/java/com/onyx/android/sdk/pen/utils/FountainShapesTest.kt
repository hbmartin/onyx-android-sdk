package com.onyx.android.sdk.pen.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FountainShapesTest {
    @Test
    fun preservesLegacyAndNotableJvmOverloads() {
        val methods = FountainShapes::class.java.declaredMethods
            .filter { it.name == "createNeoPenV2" }
            .associateBy { it.parameterTypes.size }

        assertEquals(setOf(8, 9), methods.keys)
        assertFalse(java.lang.reflect.Modifier.isStatic(methods.getValue(8).modifiers))
        assertTrue(java.lang.reflect.Modifier.isStatic(methods.getValue(9).modifiers))
        assertEquals(
            11,
            FountainShapes::class.java.getDeclaredMethod(
                "createNeoPenV2\$default",
                FountainShapes::class.java,
                Float::class.javaPrimitiveType,
                Float::class.javaPrimitiveType,
                Float::class.javaPrimitiveType,
                Float::class.javaPrimitiveType,
                Float::class.javaPrimitiveType,
                Float::class.javaPrimitiveType,
                Float::class.javaObjectType,
                Boolean::class.javaPrimitiveType,
                Int::class.javaPrimitiveType,
                Any::class.java,
            ).parameterCount,
        )
    }
}
