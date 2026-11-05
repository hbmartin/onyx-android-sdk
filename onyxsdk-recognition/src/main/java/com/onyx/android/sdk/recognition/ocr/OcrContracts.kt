@file:Suppress("LongParameterList")

package com.onyx.android.sdk.recognition.ocr

import android.graphics.Bitmap
import com.onyx.android.sdk.recognition.model.CoordinateSpaceId
import com.onyx.android.sdk.recognition.model.RecognitionAffineTransform
import com.onyx.android.sdk.recognition.model.RecognitionContentType
import com.onyx.android.sdk.recognition.model.RecognitionDocument
import com.onyx.android.sdk.recognition.model.RecognitionProvenance
import com.onyx.android.sdk.recognition.model.RecognitionRevision
import com.onyx.android.sdk.recognition.provider.LanguageTag
import com.onyx.android.sdk.recognition.provider.ProviderId
import java.nio.ByteBuffer
import kotlin.time.Duration

/** OCR content identity used in revisions. */
enum class OcrContentType(
    override val revisionName: String,
) : RecognitionContentType {
    DOCUMENT_TEXT("ocr:document-text"),
}

/** Supported raw OCR pixel formats. */
enum class OcrPixelFormat { GRAYSCALE_8, RGB_888, RGBA_8888, YUV_420_888, NV21, JPEG, PNG, WEBP }

/**
 * Explicit image orientation relative to raw storage.
 *
 * @property rotationDegrees Clockwise rotation: 0, 90, 180, or 270.
 * @property mirroredHorizontally Whether visually oriented content is mirrored.
 */
data class OcrOrientation(
    val rotationDegrees: Int = 0,
    val mirroredHorizontally: Boolean = false,
) {
    init {
        require(rotationDegrees in setOf(0, 90, 180, 270)) {
            "rotationDegrees must be 0, 90, 180, or 270"
        }
    }
}

/**
 * Explicit geometry and memory layout of an OCR source.
 */
data class OcrImageLayout(
    val width: Int,
    val height: Int,
    val rowStride: Int,
    val pixelStride: Int,
    val format: OcrPixelFormat,
    val orientation: OcrOrientation,
    val rawCoordinateSpace: CoordinateSpaceId,
    val visualCoordinateSpace: CoordinateSpaceId,
    val rawToVisualTransform: RecognitionAffineTransform,
) {
    init {
        require(width > 0 && height > 0) { "Image dimensions must be positive" }
        require(rowStride > 0 && pixelStride > 0) { "Image strides must be positive" }
    }

    /** Pixel count with overflow-safe `Long` arithmetic. */
    val pixelCount: Long = width.toLong() * height.toLong()
}

/**
 * Read-only image plane.
 *
 * Snapshot planes own copied bytes. Borrowed planes retain the caller buffer until
 * recognition completes and return duplicate read-only views.
 */
class OcrImagePlane internal constructor(
    private val bytes: ByteBuffer,
    /** Row stride in bytes. */
    val rowStride: Int,
    /** Pixel stride in bytes. */
    val pixelStride: Int,
    /** Whether this plane borrows caller storage. */
    val borrowed: Boolean,
) {
    /** Returns an independent read-only view at position zero. */
    fun readOnlyBuffer(): ByteBuffer = bytes.asReadOnlyBuffer().apply { position(0) }
}

/** Ownership-explicit OCR source. */
sealed interface OcrInput {
    /** Explicit image layout. */
    val layout: OcrImageLayout

    /**
     * Bitmap pixels copied immediately at construction.
     */
    class CopiedBitmap private constructor(
        private val snapshot: Bitmap,
        override val layout: OcrImageLayout,
    ) : OcrInput {
        /** Returns a new mutable-independent bitmap copy for a provider. */
        fun copyBitmap(): Bitmap = requireNotNull(
            snapshot.copy(snapshot.config ?: Bitmap.Config.ARGB_8888, false),
        ) { "Bitmap copy failed" }

        /** Copies a bitmap and ignores later caller mutations. */
        companion object {
            /** Creates an owned bitmap input. */
            fun from(
                bitmap: Bitmap,
                rawCoordinateSpace: CoordinateSpaceId,
                visualCoordinateSpace: CoordinateSpaceId = rawCoordinateSpace,
                orientation: OcrOrientation = OcrOrientation(),
                rawToVisualTransform: RecognitionAffineTransform =
                    RecognitionAffineTransform.Identity,
            ): CopiedBitmap {
                require(!bitmap.isRecycled) { "bitmap must not be recycled" }
                val snapshot = requireNotNull(
                    bitmap.copy(bitmap.config ?: Bitmap.Config.ARGB_8888, false),
                ) { "Bitmap copy failed" }
                return CopiedBitmap(
                    snapshot = snapshot,
                    layout = OcrImageLayout(
                        width = bitmap.width,
                        height = bitmap.height,
                        rowStride = bitmap.rowBytes,
                        pixelStride = 4,
                        format = OcrPixelFormat.RGBA_8888,
                        orientation = orientation,
                        rawCoordinateSpace = rawCoordinateSpace,
                        visualCoordinateSpace = visualCoordinateSpace,
                        rawToVisualTransform = rawToVisualTransform,
                    ),
                )
            }
        }
    }

    /**
     * Encoded bytes copied immediately at construction.
     */
    class CopiedEncodedBytes(
        bytes: ByteArray,
        override val layout: OcrImageLayout,
    ) : OcrInput {
        private val snapshot = bytes.copyOf()

        init {
            require(layout.format in setOf(OcrPixelFormat.JPEG, OcrPixelFormat.PNG, OcrPixelFormat.WEBP)) {
                "Encoded input requires JPEG, PNG, or WEBP layout"
            }
            require(snapshot.isNotEmpty()) { "Encoded bytes must not be empty" }
        }

        /** Returns a defensive copy of encoded bytes. */
        fun copyBytes(): ByteArray = snapshot.copyOf()
    }

    /**
     * Read-only buffer copied before the request enters the process FIFO.
     */
    class SnapshotReadOnlyBuffer(
        buffer: ByteBuffer,
        override val layout: OcrImageLayout,
    ) : OcrInput {
        private val snapshot: ByteBuffer = copyBuffer(buffer)

        /** Returns an independent read-only view at position zero. */
        fun readOnlyBuffer(): ByteBuffer = snapshot.asReadOnlyBuffer().apply { position(0) }
    }

    /**
     * Caller buffer borrowed until the recognition call returns.
     */
    class BorrowedReadOnlyBufferUntilComplete(
        buffer: ByteBuffer,
        override val layout: OcrImageLayout,
    ) : OcrInput {
        private val borrowed = buffer.asReadOnlyBuffer()

        /** Returns a duplicate read-only view over caller-owned storage. */
        fun readOnlyBuffer(): ByteBuffer = borrowed.asReadOnlyBuffer()
    }

    /**
     * Image planes copied before the request enters the process FIFO.
     */
    class SnapshotImagePlanes(
        planes: List<ByteBuffer>,
        rowStrides: List<Int>,
        pixelStrides: List<Int>,
        override val layout: OcrImageLayout,
    ) : OcrInput {
        /** Copied read-only planes. */
        val planes: List<OcrImagePlane> =
            createPlanes(planes, rowStrides, pixelStrides, borrowed = false)
    }

    /**
     * Caller image planes borrowed until the recognition call returns.
     */
    class BorrowedImagePlanesUntilComplete(
        planes: List<ByteBuffer>,
        rowStrides: List<Int>,
        pixelStrides: List<Int>,
        override val layout: OcrImageLayout,
    ) : OcrInput {
        /** Borrowed read-only plane views. */
        val planes: List<OcrImagePlane> =
            createPlanes(planes, rowStrides, pixelStrides, borrowed = true)
    }

    private companion object {
        fun copyBuffer(buffer: ByteBuffer): ByteBuffer {
            val source = buffer.asReadOnlyBuffer()
            val copy = ByteBuffer.allocate(source.remaining())
            copy.put(source)
            copy.flip()
            return copy.asReadOnlyBuffer()
        }

        fun createPlanes(
            planes: List<ByteBuffer>,
            rowStrides: List<Int>,
            pixelStrides: List<Int>,
            borrowed: Boolean,
        ): List<OcrImagePlane> {
            require(planes.isNotEmpty()) { "At least one plane is required" }
            require(planes.size == rowStrides.size && planes.size == pixelStrides.size) {
                "Plane and stride lists must have equal sizes"
            }
            return planes.indices.map { index ->
                require(rowStrides[index] > 0 && pixelStrides[index] > 0) {
                    "Plane strides must be positive"
                }
                OcrImagePlane(
                    bytes = if (borrowed) planes[index].asReadOnlyBuffer() else copyBuffer(planes[index]),
                    rowStride = rowStrides[index],
                    pixelStride = pixelStrides[index],
                    borrowed = borrowed,
                )
            }
        }
    }
}

/** OCR provider routing policy. */
sealed interface OcrRoutingPolicy {
    /** Selects only among validated providers. */
    data object Auto : OcrRoutingPolicy

    /** Selects exactly one provider and never falls back. */
    data class Provider(val providerId: ProviderId) : OcrRoutingPolicy
}

/** Public OCR preprocessing control. */
enum class OcrPreprocessingPolicy { PROVIDER_DEFAULT, NONE }

/** Resolved writing direction. */
enum class OcrWritingDirection { HORIZONTAL_LTR, HORIZONTAL_RTL, VERTICAL }

/**
 * Immutable OCR request.
 *
 * @property input Ownership-explicit image input.
 * @property routing Automatic or explicit routing.
 * @property languageHints Preferred languages. Empty resolves application locales.
 * @property scriptHints Optional ISO 15924 script tags.
 * @property writingDirection Optional explicit reading-direction override.
 * @property preprocessing Public preprocessing policy.
 */
data class OcrRequest(
    val input: OcrInput,
    val routing: OcrRoutingPolicy = OcrRoutingPolicy.Auto,
    val languageHints: List<LanguageTag> = emptyList(),
    val scriptHints: List<String> = emptyList(),
    val writingDirection: OcrWritingDirection? = null,
    val preprocessing: OcrPreprocessingPolicy = OcrPreprocessingPolicy.PROVIDER_DEFAULT,
) {
    init {
        require(scriptHints.all { it.matches(Regex("^[A-Z][a-z]{3}$")) }) {
            "Script hints must be ISO 15924 tags"
        }
    }
}

/**
 * OCR result in raw and visually oriented coordinate systems.
 */
data class OcrResult(
    val text: String,
    val document: RecognitionDocument,
    val rawToVisualTransform: RecognitionAffineTransform,
    val writingDirection: OcrWritingDirection,
    val provenance: RecognitionProvenance,
) {
    /** Output-affecting recognition revision. */
    val revision: RecognitionRevision get() = provenance.revision
}

/** Provider runtime created during asynchronous OCR discovery. */
interface OcrProvider : AutoCloseable {
    /** Recognizes one ownership-explicit request. */
    suspend fun recognize(request: OcrRequest): Result<OcrResult>

    /** Requests idempotent non-blocking cleanup. */
    override fun close()
}

/** Idiomatic OCR facade. */
interface OcrRecognizer {
    /** Recognizes one image through automatic or explicit routing. */
    suspend fun recognize(
        request: OcrRequest,
        timeout: Duration? = null,
    ): Result<OcrResult>
}
