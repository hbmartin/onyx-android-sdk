package sample

import android.view.SurfaceView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.onyx.android.sdk.ktx.api.DefaultDisplayController
import com.onyx.android.sdk.ktx.api.DefaultPenSessionFactory
import com.onyx.android.sdk.ktx.api.DisplayController
import com.onyx.android.sdk.ktx.api.PenSessionFactory
import com.onyx.android.sdk.ktx.ink.RawInkSessionState
import com.onyx.android.sdk.ktx.model.EpdUpdateMode
import com.onyx.android.sdk.ktx.model.InkStroke
import com.onyx.android.sdk.ktx.model.LeasePolicy
import com.onyx.android.sdk.ktx.model.OnyxFailure
import com.onyx.android.sdk.ktx.model.RawInkConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.withContext

/**
 * Compose integration with hoisted durable strokes. Raw preview remains owned by the SurfaceView;
 * lifecycle stop/start and destruction are handled by RawInkSession.attach.
 */
@Composable
fun OnyxPenCanvas(
    committedRevision: Long,
    onStroke: (InkStroke) -> Unit,
    modifier: Modifier = Modifier,
    sessionFactory: PenSessionFactory = DefaultPenSessionFactory,
    displayController: DisplayController = DefaultDisplayController,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var surface by remember { mutableStateOf<SurfaceView?>(null) }
    var status by remember { mutableStateOf<RawInkSessionState?>(null) }

    LaunchedEffect(surface, lifecycleOwner, sessionFactory) {
        val ownedSurface = surface ?: return@LaunchedEffect
        val session = sessionFactory.attach(
            ownedSurface,
            lifecycleOwner,
            RawInkConfiguration(),
            LeasePolicy.FAIL_FAST,
        ).getOrElse {
            status = RawInkSessionState.Failed(
                it as? OnyxFailure ?: OnyxFailure.InvalidState(
                    "sample.attach",
                    null,
                    it.message ?: it.javaClass.simpleName,
                ),
            )
            return@LaunchedEffect
        }
        try {
            session.completedStrokes.collect { stroke ->
                withContext(Dispatchers.Default) { onStroke(stroke) }
            }
        } finally {
            withContext(NonCancellable) { session.closeAndAwait() }
        }
    }

    LaunchedEffect(surface, committedRevision, displayController) {
        surface?.let { view ->
            displayController.refresh(view, EpdUpdateMode.GU).getOrThrow()
        }
    }

    Box(modifier.fillMaxSize()) {
        AndroidView(
            factory = { context -> SurfaceView(context).also { surface = it } },
            modifier = Modifier.fillMaxSize(),
        )
        status?.let { Text(it.toString()) }
    }
}
