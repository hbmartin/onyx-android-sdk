@file:Suppress("LongParameterList")

package com.onyx.android.sdk.recognition.hwr

import com.onyx.android.sdk.ktx.model.InkPoint
import com.onyx.android.sdk.ktx.model.InkStroke
import com.onyx.android.sdk.ktx.model.InkTool
import com.onyx.android.sdk.recognition.model.RecognitionAffineTransform
import com.onyx.android.sdk.recognition.model.RecognitionContentType
import com.onyx.android.sdk.recognition.model.RecognitionDocument
import com.onyx.android.sdk.recognition.model.RecognitionProvenance
import com.onyx.android.sdk.recognition.model.RecognitionRect
import com.onyx.android.sdk.recognition.model.RecognitionRevision
import com.onyx.android.sdk.recognition.provider.LanguageTag
import com.onyx.android.sdk.recognition.provider.ProviderId
import kotlinx.coroutines.flow.StateFlow
import kotlin.time.Duration

/** Supported common handwriting content families. */
enum class HandwritingContentType(
    override val revisionName: String,
) : RecognitionContentType {
    TEXT("hwr:text"),
    MATH("hwr:math"),
    DIAGRAM("hwr:diagram"),
    SHAPE("hwr:shape"),
    RAW_CONTENT("hwr:raw-content"),
    GESTURE("hwr:gesture"),
    MIXED("hwr:mixed"),
}

/**
 * Immutable options pinned by a handwriting session.
 *
 * @property providerId Explicit provider; HWR never routes automatically.
 * @property contentType Requested content family.
 * @property languages Preferred BCP 47 languages. Empty resolves application locales.
 * @property documentTransform Optional ink-to-document-logical transform.
 */
data class HandwritingSessionOptions(
    val providerId: ProviderId,
    val contentType: HandwritingContentType,
    val languages: List<LanguageTag> = emptyList(),
    val documentTransform: RecognitionAffineTransform? = null,
)

/**
 * Fully copied handwriting point passed through the provider SPI.
 */
data class HandwritingInkPoint(
    val xPx: Float,
    val yPx: Float,
    val rawPressure: Int,
    val normalizedPressure: Float,
    val maxPressure: Int,
    val tiltX: Int,
    val tiltY: Int,
    val eventTimeNanos: Long,
    val sequence: Long,
    val tool: InkTool,
) {
    internal constructor(point: InkPoint) : this(
        xPx = point.xPx,
        yPx = point.yPx,
        rawPressure = point.rawPressure,
        normalizedPressure = point.normalizedPressure,
        maxPressure = point.maxPressure,
        tiltX = point.tiltX,
        tiltY = point.tiltY,
        eventTimeNanos = point.eventTimeNanos,
        sequence = point.sequence,
        tool = point.tool,
    )
}

/**
 * Fully copied stroke with SDK-recomputed bounds.
 *
 * @param points Copied point list.
 */
class HandwritingInkStroke(
    /** Physical tool recorded for the stroke. */
    val tool: InkTool,
    points: Collection<HandwritingInkPoint>,
    /** Whether input ended outside the raw-ink limit region. */
    val endedOutsideLimitRegion: Boolean,
    /** Bounds recomputed from [points]. */
    val bounds: RecognitionRect,
) {
    /** Ordered copied points. */
    val points: List<HandwritingInkPoint> = points.toList()
}

/**
 * Complete immutable stroke snapshot supplied for one provider call.
 *
 * @param strokes Complete ordered stroke list, copied at construction.
 */
class HandwritingInkSnapshot(strokes: Collection<HandwritingInkStroke>) {
    /** Complete ordered strokes for this call. */
    val strokes: List<HandwritingInkStroke> = strokes.toList()

    /** Total point count. */
    val pointCount: Long = strokes.sumOf { it.points.size.toLong() }

    init {
        require(strokes.isNotEmpty()) { "At least one stroke is required" }
        require(strokes.all { it.points.isNotEmpty() }) { "Strokes must not be empty" }
    }

    internal companion object {
        fun copyOf(strokes: List<InkStroke>): HandwritingInkSnapshot {
            require(strokes.isNotEmpty()) { "At least one stroke is required" }
            return HandwritingInkSnapshot(
                strokes.map { stroke ->
                    require(stroke.points.isNotEmpty()) { "Strokes must not be empty" }
                    val points = stroke.points.map(::HandwritingInkPoint)
                    val left = points.minOf { it.xPx }.toDouble()
                    val top = points.minOf { it.yPx }.toDouble()
                    val right = points.maxOf { it.xPx }.toDouble()
                    val bottom = points.maxOf { it.yPx }.toDouble()
                    HandwritingInkStroke(
                        tool = stroke.tool,
                        points = points,
                        endedOutsideLimitRegion = stroke.endedOutsideLimitRegion,
                        bounds = RecognitionRect(left, top, right, bottom),
                    )
                },
            )
        }
    }
}

/**
 * Common handwriting result with normalized document and exact attempt identity.
 */
sealed interface HandwritingResult {
    /** Normalized spatial document. */
    val document: RecognitionDocument

    /** Exact attempt provenance. */
    val provenance: RecognitionProvenance

    /** Output-affecting recognition revision. */
    val revision: RecognitionRevision get() = provenance.revision

    /** Recognized plain or rich text. */
    data class Text(
        val text: String,
        override val document: RecognitionDocument,
        override val provenance: RecognitionProvenance,
    ) : HandwritingResult

    /** Recognized mathematical expression. */
    data class Math(
        val expression: String,
        override val document: RecognitionDocument,
        override val provenance: RecognitionProvenance,
    ) : HandwritingResult

    /** Recognized diagram. */
    data class Diagram(
        override val document: RecognitionDocument,
        override val provenance: RecognitionProvenance,
    ) : HandwritingResult

    /** Recognized shape collection. */
    data class Shape(
        override val document: RecognitionDocument,
        override val provenance: RecognitionProvenance,
    ) : HandwritingResult

    /** Provider-independent raw spatial content. */
    data class RawContent(
        override val document: RecognitionDocument,
        override val provenance: RecognitionProvenance,
    ) : HandwritingResult

    /** Recognized gesture collection. */
    data class Gesture(
        override val document: RecognitionDocument,
        override val provenance: RecognitionProvenance,
    ) : HandwritingResult

    /** Mixed typed recognition content. */
    data class Mixed(
        override val document: RecognitionDocument,
        override val provenance: RecognitionProvenance,
    ) : HandwritingResult
}

/** Public handwriting-session lifecycle state. */
enum class HandwritingSessionState { OPEN, CLOSING, CLOSED, FAILED }

/**
 * Provider-owned handwriting session pinned to one provider and resource revision.
 */
interface HandwritingProviderSession : AutoCloseable {
    /** Recognizes one complete immutable ink snapshot. */
    suspend fun recognize(snapshot: HandwritingInkSnapshot): Result<HandwritingResult>

    /** Requests idempotent non-blocking cleanup. */
    override fun close()

    /** Waits for idempotent cleanup completion. */
    suspend fun closeAndAwait(): Result<Unit>
}

/**
 * Provider runtime created during asynchronous discovery.
 */
interface HandwritingProvider : AutoCloseable {
    /** Opens a pinned provider session. */
    suspend fun openSession(options: HandwritingSessionOptions): Result<HandwritingProviderSession>

    /** Requests idempotent non-blocking provider cleanup. */
    override fun close()
}

/** Idiomatic handwriting facade. */
interface HandwritingRecognizer {
    /** Opens a session pinned to the explicit provider and current resource revision. */
    suspend fun openSession(options: HandwritingSessionOptions): Result<HandwritingSession>

    /** Recognizes one complete stroke snapshot without retaining editor state. */
    suspend fun recognize(
        strokes: List<InkStroke>,
        options: HandwritingSessionOptions,
        timeout: Duration? = null,
    ): Result<HandwritingResult>
}

/**
 * Application-facing pinned handwriting session.
 */
interface HandwritingSession : AutoCloseable {
    /** Current session state. */
    val state: StateFlow<HandwritingSessionState>

    /** Recognizes one complete stroke snapshot. */
    suspend fun recognize(
        strokes: List<InkStroke>,
        timeout: Duration? = null,
    ): Result<HandwritingResult>

    /** Requests idempotent non-blocking cleanup. */
    override fun close()

    /** Waits for idempotent cleanup completion. */
    suspend fun closeAndAwait(): Result<Unit>
}
