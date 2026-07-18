@file:Suppress("LongParameterList", "MagicNumber")

package com.onyx.android.sdk.recognition.model

import com.onyx.android.sdk.recognition.provider.CapabilityValidationState
import com.onyx.android.sdk.recognition.provider.ComputeBackend
import com.onyx.android.sdk.recognition.provider.LanguageTag
import com.onyx.android.sdk.recognition.provider.ProviderId
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.security.MessageDigest

/** Typed identity implemented by each pipeline's content-type enum. */
interface RecognitionContentType {
    /** Stable canonical identity used by provenance and revision hashing. */
    val revisionName: String
}

/** Immutable finite 2D point. */
data class RecognitionPoint(val x: Double, val y: Double) {
    init {
        require(x.isFinite() && y.isFinite()) { "Point coordinates must be finite" }
    }
}

/** Immutable finite axis-aligned rectangle. */
data class RecognitionRect(
    val left: Double,
    val top: Double,
    val right: Double,
    val bottom: Double,
) {
    init {
        require(listOf(left, top, right, bottom).all(Double::isFinite)) {
            "Rectangle coordinates must be finite"
        }
        require(right >= left && bottom >= top) { "Rectangle must not be inverted" }
    }

    /** Rectangle width. */
    val width: Double get() = right - left

    /** Rectangle height. */
    val height: Double get() = bottom - top
}

/**
 * Stable identity of a 2D coordinate space.
 */
@JvmInline
value class CoordinateSpaceId(val value: String) {
    init {
        require(value.matches(Regex("^[a-z][a-z0-9.-]{1,95}$"))) {
            "Coordinate-space ID must be a lowercase ASCII identifier"
        }
    }
}

/**
 * Immutable affine transform `[a c tx; b d ty; 0 0 1]`.
 */
data class RecognitionAffineTransform(
    val a: Double,
    val b: Double,
    val c: Double,
    val d: Double,
    val tx: Double,
    val ty: Double,
) {
    init {
        require(listOf(a, b, c, d, tx, ty).all(Double::isFinite)) {
            "Transform values must be finite"
        }
    }

    /** Applies this transform to a point. */
    fun map(point: RecognitionPoint): RecognitionPoint = RecognitionPoint(
        x = a * point.x + c * point.y + tx,
        y = b * point.x + d * point.y + ty,
    )

    /** Identity transform. */
    companion object {
        /** Transform that leaves all coordinates unchanged. */
        val Identity: RecognitionAffineTransform =
            RecognitionAffineTransform(1.0, 0.0, 0.0, 1.0, 0.0, 0.0)
    }
}

/** Meaning of a provider-native confidence value. */
enum class ConfidenceKind {
    PROBABILITY,
    LOG_PROBABILITY,
    UNBOUNDED_SCORE,
    RANK_ONLY,
    PROVIDER_NATIVE,
}

/**
 * Provider-native confidence with no cross-provider normalization.
 */
data class RecognitionConfidence(
    val value: Double,
    val kind: ConfidenceKind,
) {
    init {
        require(value.isFinite()) { "Confidence value must be finite" }
        if (kind == ConfidenceKind.PROBABILITY) {
            require(value in 0.0..1.0) { "Probability confidence must be in 0..1" }
        }
    }
}

/**
 * Polygon and bounds in one explicit coordinate space.
 *
 * @param polygon Ordered polygon vertices; copied at construction.
 */
class RecognitionGeometry(
    /** Coordinate space containing this geometry. */
    val coordinateSpace: CoordinateSpaceId,
    /** Axis-aligned bounds. */
    val bounds: RecognitionRect,
    polygon: Collection<RecognitionPoint> = emptyList(),
) {
    /** Ordered polygon, or empty when the provider returned only bounds. */
    val polygon: List<RecognitionPoint> = polygon.toList()
}

/** Stable node identity within a recognition document. */
@JvmInline
value class RecognitionNodeId(val value: String) {
    init {
        require(value.isNotBlank()) { "Node ID must not be blank" }
    }
}

/**
 * Base contract for a typed immutable spatial-document node.
 */
sealed interface RecognitionNode {
    /** Node identity within the document. */
    val id: RecognitionNodeId

    /** Node geometry, when the concept is spatial. */
    val geometry: RecognitionGeometry?

    /** Provider-native confidence. */
    val confidence: RecognitionConfidence?

    /** Resolved language. */
    val language: LanguageTag?

    /** Typed child nodes. */
    val children: List<RecognitionNode>
}

/** Document page node. */
data class PageNode(
    override val id: RecognitionNodeId,
    override val geometry: RecognitionGeometry,
    override val children: List<RecognitionNode> = emptyList(),
) : RecognitionNode {
    override val confidence: RecognitionConfidence? = null
    override val language: LanguageTag? = null
}

/** Layout block node. */
data class BlockNode(
    override val id: RecognitionNodeId,
    override val geometry: RecognitionGeometry,
    override val confidence: RecognitionConfidence? = null,
    override val language: LanguageTag? = null,
    override val children: List<RecognitionNode> = emptyList(),
) : RecognitionNode

/** Text line node. */
data class LineNode(
    override val id: RecognitionNodeId,
    val text: String,
    override val geometry: RecognitionGeometry,
    override val confidence: RecognitionConfidence? = null,
    override val language: LanguageTag? = null,
    override val children: List<RecognitionNode> = emptyList(),
) : RecognitionNode

/** Recognized word node. */
data class WordNode(
    override val id: RecognitionNodeId,
    val text: String,
    override val geometry: RecognitionGeometry,
    override val confidence: RecognitionConfidence? = null,
    override val language: LanguageTag? = null,
    override val children: List<RecognitionNode> = emptyList(),
) : RecognitionNode

/** Recognized symbol node. */
data class SymbolNode(
    override val id: RecognitionNodeId,
    val symbol: String,
    override val geometry: RecognitionGeometry,
    override val confidence: RecognitionConfidence? = null,
    override val language: LanguageTag? = null,
) : RecognitionNode {
    override val children: List<RecognitionNode> = emptyList()
}

/** Mathematical expression node. */
data class MathExpressionNode(
    override val id: RecognitionNodeId,
    val expression: String,
    override val geometry: RecognitionGeometry?,
    override val confidence: RecognitionConfidence? = null,
    override val children: List<RecognitionNode> = emptyList(),
) : RecognitionNode {
    override val language: LanguageTag? = null
}

/** Recognized geometric shape node. */
data class ShapeNode(
    override val id: RecognitionNodeId,
    val shapeName: String,
    override val geometry: RecognitionGeometry,
    override val confidence: RecognitionConfidence? = null,
) : RecognitionNode {
    override val language: LanguageTag? = null
    override val children: List<RecognitionNode> = emptyList()
}

/** Diagram entity node. */
data class DiagramNode(
    override val id: RecognitionNodeId,
    val label: String?,
    override val geometry: RecognitionGeometry,
    override val confidence: RecognitionConfidence? = null,
    override val children: List<RecognitionNode> = emptyList(),
) : RecognitionNode {
    override val language: LanguageTag? = null
}

/** Directed relationship between two diagram entities. */
data class DiagramEdgeNode(
    override val id: RecognitionNodeId,
    val source: RecognitionNodeId,
    val target: RecognitionNodeId,
    override val geometry: RecognitionGeometry?,
    override val confidence: RecognitionConfidence? = null,
) : RecognitionNode {
    override val language: LanguageTag? = null
    override val children: List<RecognitionNode> = emptyList()
}

/** Recognized editing or semantic gesture. */
data class GestureNode(
    override val id: RecognitionNodeId,
    val gestureName: String,
    override val geometry: RecognitionGeometry,
    override val confidence: RecognitionConfidence? = null,
) : RecognitionNode {
    override val language: LanguageTag? = null
    override val children: List<RecognitionNode> = emptyList()
}

/** Reference to an original input stroke by zero-based index. */
data class StrokeReferenceNode(
    override val id: RecognitionNodeId,
    val strokeIndex: Int,
    override val geometry: RecognitionGeometry,
) : RecognitionNode {
    init {
        require(strokeIndex >= 0) { "strokeIndex must be non-negative" }
    }

    override val confidence: RecognitionConfidence? = null
    override val language: LanguageTag? = null
    override val children: List<RecognitionNode> = emptyList()
}

/**
 * Normalized immutable spatial document returned by HWR and OCR.
 *
 * @param pages Ordered pages, copied at construction.
 */
class RecognitionDocument(pages: Collection<PageNode>) {
    /** Ordered pages. */
    val pages: List<PageNode> = pages.toList()

    init {
        require(pages.isNotEmpty()) { "Recognition document must contain at least one page" }
    }
}

/** Kind of output-affecting revision component. */
enum class RecognitionRevisionComponentKind {
    PROVIDER_RUNTIME,
    RESOURCE,
    DICTIONARY,
    PREPROCESSING,
    POSTPROCESSING,
    TRANSFORM,
    ROUTING,
    COMPUTE_BACKEND,
}

/**
 * Typed output-affecting component included in a recognition revision.
 */
data class RecognitionRevisionComponent(
    val kind: RecognitionRevisionComponentKind,
    val identity: String,
) {
    init {
        require(identity.isNotBlank()) { "Revision component identity must not be blank" }
    }
}

/**
 * Stable recognition revision and canonical SHA-256 cache key.
 *
 * @param components Typed components, copied at construction.
 */
class RecognitionRevision private constructor(
    /** Revision schema version. */
    val schemaVersion: Int,
    components: Collection<RecognitionRevisionComponent>,
    /** Lowercase SHA-256 of the canonical length-prefixed component encoding. */
    val cacheKeySha256: String,
) {
    /** Output-affecting components in canonical order. */
    val components: List<RecognitionRevisionComponent> = components.toList()

    /** Creates canonical revisions. */
    companion object {
        /** Current canonical revision schema. */
        const val CURRENT_SCHEMA_VERSION: Int = 1

        /**
         * Creates a revision after sorting components by kind and identity.
         */
        fun create(
            components: Collection<RecognitionRevisionComponent>,
            schemaVersion: Int = CURRENT_SCHEMA_VERSION,
        ): RecognitionRevision {
            require(schemaVersion > 0) { "schemaVersion must be positive" }
            require(components.isNotEmpty()) { "components must not be empty" }
            val canonical = components.sortedWith(compareBy({ it.kind.name }, { it.identity }))
            val digest = MessageDigest.getInstance("SHA-256")
            updateLengthPrefixed(digest, schemaVersion.toString())
            canonical.forEach {
                updateLengthPrefixed(digest, it.kind.name)
                updateLengthPrefixed(digest, it.identity)
            }
            val key = digest.digest().joinToString("") { byte -> "%02x".format(byte) }
            return RecognitionRevision(schemaVersion, canonical, key)
        }

        private fun updateLengthPrefixed(digest: MessageDigest, value: String) {
            val bytes = value.toByteArray(StandardCharsets.UTF_8)
            digest.update(ByteBuffer.allocate(Int.SIZE_BYTES).putInt(bytes.size).array())
            digest.update(bytes)
        }
    }
}

/**
 * Exact resource or model identity used by an attempt.
 */
data class RecognitionResourceIdentity(
    val slot: String,
    val revision: String,
    val sha256: String,
) {
    init {
        require(slot.isNotBlank() && revision.isNotBlank()) { "Resource identity must not be blank" }
        require(sha256.matches(Regex("^[0-9a-f]{64}$"))) { "sha256 must be lowercase SHA-256" }
    }
}

/** Typed routing decision used by an attempt. */
sealed interface RecognitionRoutingDecision {
    /** Caller selected a provider explicitly. */
    data class Explicit(val providerId: ProviderId) : RecognitionRoutingDecision

    /** Automatic routing selected a validated provider. */
    data class Automatic(
        val providerId: ProviderId,
        val reason: AutomaticRoutingReason,
    ) : RecognitionRoutingDecision
}

/** Reason an automatic route selected its provider. */
enum class AutomaticRoutingReason { VALIDATED_LANGUAGE_COVERAGE, VALIDATED_SCRIPT_COVERAGE }

/**
 * Complete provenance retained by successful results and no-match failures.
 *
 * Collection properties are copied at construction.
 */
class RecognitionProvenance(
    /** Provider used by the attempt. */
    val providerId: ProviderId,
    /** Exact provider engine version. */
    val engineVersion: String,
    resources: Collection<RecognitionResourceIdentity>,
    languages: Collection<LanguageTag>,
    /** Requested content type. */
    val contentType: RecognitionContentType,
    /** Routing decision. */
    val routing: RecognitionRoutingDecision,
    /** Actual compute backend. */
    val computeBackend: ComputeBackend,
    /** Validation state at selection. */
    val validationState: CapabilityValidationState,
    /** Output revision. */
    val revision: RecognitionRevision,
) {
    /** Exact resources, models, and dictionaries used. */
    val resources: List<RecognitionResourceIdentity> = resources.toList()

    /** Resolved language tags. */
    val languages: List<LanguageTag> = languages.toList()

    init {
        require(engineVersion.isNotBlank()) { "engineVersion must not be blank" }
        require(validationState != CapabilityValidationState.UNAVAILABLE) {
            "An attempt cannot use an unavailable capability"
        }
    }
}
