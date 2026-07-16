package com.onyx.android.sdk.ktx.display

import java.util.concurrent.atomic.AtomicInteger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ScopedRefreshModeTest {
    @Test
    fun sameModeNestsAndClearsAfterFinalOwner() = runBlocking {
        val applied = AtomicInteger()
        val cleared = AtomicInteger()
        val owner = owner(applied, cleared, this)

        val first = owner.acquire(TransientUpdateMode.ANIMATION_QUALITY).getOrThrow()
        val second = owner.acquire(TransientUpdateMode.ANIMATION_QUALITY).getOrThrow()

        assertEquals(1, applied.get())
        assertEquals(2, second.receipt.nestingDepth)
        first.closeAndAwait().getOrThrow()
        assertEquals(0, cleared.get())
        second.closeAndAwait().getOrThrow()
        assertEquals(1, cleared.get())
    }

    @Test
    fun conflictingNestedModeIsRejectedWithoutChangingFirmware() = runBlocking {
        val applied = AtomicInteger()
        val cleared = AtomicInteger()
        val owner = owner(applied, cleared, this)
        val first = owner.acquire(TransientUpdateMode.ANIMATION).getOrThrow()

        val conflicting = owner.acquire(TransientUpdateMode.ANIMATION_QUALITY)

        assertTrue(conflicting.isFailure)
        assertEquals(1, applied.get())
        first.closeAndAwait().getOrThrow()
        assertEquals(1, cleared.get())
    }

    @Test
    fun tokenReleaseIsIdempotent() = runBlocking {
        val applied = AtomicInteger()
        val cleared = AtomicInteger()
        val token = owner(applied, cleared, this)
            .acquire(TransientUpdateMode.ANIMATION)
            .getOrThrow()

        token.closeAndAwait().getOrThrow()
        token.closeAndAwait().getOrThrow()

        assertEquals(1, applied.get())
        assertEquals(1, cleared.get())
    }

    @Test
    fun failedClearDoesNotRetainLogicalModeOwnership() = runBlocking {
        val appliedModes = mutableListOf<TransientUpdateMode>()
        val owner = ScopedRefreshModeOwner(
            applyMode = { mode ->
                appliedModes += mode
                Result.success(Unit)
            },
            clearMode = { Result.failure(IllegalStateException("firmware clear failed")) },
            asynchronousScope = this,
        )

        val first = owner.acquire(TransientUpdateMode.ANIMATION).getOrThrow()
        assertTrue(first.closeAndAwait().isFailure)
        val second = owner.acquire(TransientUpdateMode.ANIMATION_QUALITY).getOrThrow()

        assertEquals(
            listOf(TransientUpdateMode.ANIMATION, TransientUpdateMode.ANIMATION_QUALITY),
            appliedModes,
        )
        assertTrue(second.closeAndAwait().isFailure)
    }

    private fun owner(
        applied: AtomicInteger,
        cleared: AtomicInteger,
        asynchronousScope: CoroutineScope,
    ) = ScopedRefreshModeOwner(
        applyMode = {
            applied.incrementAndGet()
            Result.success(Unit)
        },
        clearMode = {
            cleared.incrementAndGet()
            Result.success(Unit)
        },
        asynchronousScope = asynchronousScope,
    )
}
