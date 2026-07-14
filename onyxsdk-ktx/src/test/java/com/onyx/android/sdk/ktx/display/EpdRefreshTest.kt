package com.onyx.android.sdk.ktx.display

import java.util.concurrent.Executors
import org.junit.Assert.assertNull
import org.junit.Test

class EpdRefreshTest {
    @Test
    fun rejectedFirmwareWaitSubmissionReturnsNull() {
        val executor = Executors.newSingleThreadExecutor()
        executor.shutdownNow()

        val future = submitFirmwareWaitOrNull(executor)

        assertNull(future)
    }
}
