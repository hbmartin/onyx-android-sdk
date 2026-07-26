@file:Suppress(
    "AvoidMutableCollections",
    "AvoidVarsExceptWithDelegate",
    "NoCallbacksInFunctions",
    "TooGenericExceptionCaught",
)

package com.onyx.android.sdk.recognition.diagnostics

import android.util.Log
import com.onyx.android.sdk.recognition.provider.ProviderId
import java.nio.ByteBuffer
import java.security.MessageDigest
import java.util.ArrayDeque
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong

/** Diagnostic severity. */
enum class RecognitionDiagnosticLevel { DEBUG, INFO, WARNING, ERROR }

/** Recognition diagnostic phase. */
enum class RecognitionDiagnosticPhase {
    INITIALIZATION,
    DISCOVERY,
    QUEUE,
    PREPROCESSING,
    INFERENCE,
    POSTPROCESSING,
    RESOURCE,
    CLEANUP,
}

/**
 * Content-safe metadata emitted for SDK operations.
 *
 * @property sequence Process-local monotonic event number.
 * @property operation Stable operation name.
 * @property phase Operation phase.
 * @property level Diagnostic severity.
 * @property providerId Provider involved, when known.
 * @property durationNanos Elapsed monotonic duration, when known.
 * @property detail Content-safe detail that must not include recognition input or output.
 */
data class RecognitionDiagnosticMetadata(
    val sequence: Long,
    val operation: String,
    val phase: RecognitionDiagnosticPhase,
    val level: RecognitionDiagnosticLevel,
    val providerId: ProviderId? = null,
    val durationNanos: Long? = null,
    val detail: String? = null,
) {
    init {
        require(sequence > 0) { "sequence must be positive" }
        require(operation.isNotBlank()) { "operation must not be blank" }
        require(durationNanos == null || durationNanos >= 0) { "durationNanos must be non-negative" }
    }
}

/**
 * Optional typed raw diagnostic attachment.
 *
 * Raw attachments may contain sensitive ink, image, audio, or recognized output.
 * The host application owns consent, disclosure, secure transport, retention,
 * access control, and storage policy in debug and release builds.
 */
sealed interface RawRecognitionDiagnostic {
    /** Approximate retained bytes used for queue budgeting. */
    val estimatedBytes: Long

    /** Content-safe summary for Logcat when no host sink is configured. */
    val summary: String

    /** Updates a digest without forcing the SDK to invent a persistence format. */
    fun updateDigest(digest: MessageDigest)
}

/**
 * Raw immutable ink attachment represented as packed point channels.
 *
 * @param packedPoints Copied `[x, y, pressure, time]` tuples.
 */
class RawInkDiagnostic(packedPoints: DoubleArray) : RawRecognitionDiagnostic {
    private val values = packedPoints.copyOf()

    /** Returns a defensive copy of packed point data. */
    fun copyPackedPoints(): DoubleArray = values.copyOf()

    override val estimatedBytes: Long = values.size.toLong() * Double.SIZE_BYTES
    override val summary: String = "ink(points=${values.size / 4})"

    override fun updateDigest(digest: MessageDigest) {
        val buffer = ByteBuffer.allocate(Double.SIZE_BYTES)
        values.forEach { value ->
            buffer.clear()
            buffer.putDouble(value)
            digest.update(buffer.array())
        }
    }
}

/**
 * Raw image attachment with explicit dimensions and format.
 *
 * @param bytes Copied image bytes.
 */
class RawImageDiagnostic(
    bytes: ByteArray,
    /** Image width. */
    val width: Int,
    /** Image height. */
    val height: Int,
    /** Stable pixel or encoding format. */
    val format: String,
) : RawRecognitionDiagnostic {
    private val values = bytes.copyOf()

    init {
        require(width > 0 && height > 0) { "Image dimensions must be positive" }
        require(format.isNotBlank()) { "format must not be blank" }
    }

    /** Returns a defensive copy of image bytes. */
    fun copyBytes(): ByteArray = values.copyOf()

    override val estimatedBytes: Long = values.size.toLong()
    override val summary: String = "image(${width}x$height,$format,bytes=${values.size})"
    override fun updateDigest(digest: MessageDigest) = digest.update(values)
}

/**
 * Raw signed 16-bit PCM attachment.
 *
 * @param samples Copied interleaved PCM samples.
 */
class RawAudioDiagnostic(
    samples: ShortArray,
    /** Sample rate in hertz. */
    val sampleRateHz: Int,
    /** Interleaved channel count. */
    val channelCount: Int,
) : RawRecognitionDiagnostic {
    private val values = samples.copyOf()

    init {
        require(sampleRateHz > 0) { "sampleRateHz must be positive" }
        require(channelCount > 0) { "channelCount must be positive" }
    }

    /** Returns a defensive copy of PCM samples. */
    fun copySamples(): ShortArray = values.copyOf()

    override val estimatedBytes: Long = values.size.toLong() * Short.SIZE_BYTES
    override val summary: String =
        "audio(samples=${values.size},rate=$sampleRateHz,channels=$channelCount)"

    override fun updateDigest(digest: MessageDigest) {
        val buffer = ByteBuffer.allocate(Short.SIZE_BYTES)
        values.forEach { value ->
            buffer.clear()
            buffer.putShort(value)
            digest.update(buffer.array())
        }
    }
}

/**
 * Raw provider output retained as its typed in-memory object.
 *
 * The SDK does not serialize [value]. Providers supply a stable [digestMaterial]
 * solely for content hashing when no sink is installed.
 */
class RawProviderOutputDiagnostic<T : Any>(
    /** Typed provider output object. */
    val value: T,
    digestMaterial: ByteArray,
    /** Content-safe output type name. */
    val outputType: String,
    override val estimatedBytes: Long,
) : RawRecognitionDiagnostic {
    private val digestBytes = digestMaterial.copyOf()

    init {
        require(outputType.isNotBlank()) { "outputType must not be blank" }
        require(estimatedBytes >= 0) { "estimatedBytes must be non-negative" }
    }

    override val summary: String = "output(type=$outputType,estimatedBytes=$estimatedBytes)"
    override fun updateDigest(digest: MessageDigest) = digest.update(digestBytes)
}

/**
 * Monotonic diagnostic drop counters.
 *
 * @property metadata Number of metadata records dropped.
 * @property raw Number of raw attachments dropped.
 * @property rawBytes Approximate raw bytes dropped.
 */
data class RecognitionDiagnosticDropCounts(
    val metadata: Long,
    val raw: Long,
    val rawBytes: Long,
)

/**
 * Host diagnostic sink.
 *
 * Implementations run on a private SDK executor and must return promptly. Raw
 * callbacks occur only when the immutable process-wide raw flag is enabled.
 */
interface RecognitionDiagnosticSink {
    /** Receives content-safe metadata. */
    fun onMetadata(metadata: RecognitionDiagnosticMetadata)

    /** Receives a sensitive raw attachment. */
    fun onRaw(metadata: RecognitionDiagnosticMetadata, raw: RawRecognitionDiagnostic)

    /** Receives monotonic queue-drop counters. */
    fun onDrops(counts: RecognitionDiagnosticDropCounts)
}

/**
 * Immutable process-wide diagnostics policy.
 *
 * Raw diagnostics are permitted in release builds. When [rawDataEnabled] is true
 * but [sink] is null, only raw summaries and SHA-256 hashes reach Logcat. The host
 * owns user consent, disclosure, transport, retention, and storage policy.
 */
data class RecognitionDiagnosticsConfiguration(
    val rawDataEnabled: Boolean = false,
    val sink: RecognitionDiagnosticSink? = null,
    val logcatTag: String = "OnyxRecognition",
) {
    init {
        require(logcatTag.isNotBlank() && logcatTag.length <= 23) {
            "logcatTag must contain 1..23 characters"
        }
    }
}

/**
 * Non-blocking bounded diagnostics dispatcher.
 *
 * The dispatcher retains at most 256 metadata events, four raw attachments, and
 * 64 MiB of raw data or references. It drops raw first.
 */
class RecognitionDiagnostics internal constructor(
    private val configuration: RecognitionDiagnosticsConfiguration,
) {
    private val ids = AtomicLong()
    private val droppedMetadata = AtomicLong()
    private val droppedRaw = AtomicLong()
    private val droppedRawBytes = AtomicLong()
    private val scheduled = AtomicBoolean()
    private val executor = Executors.newSingleThreadExecutor { runnable ->
        Thread(runnable, "onyx-recognition-diagnostics").apply { isDaemon = true }
    }
    private val queue = ArrayDeque<Envelope>()
    private var metadataCount = 0
    private var rawCount = 0
    private var rawBytes = 0L

    /**
     * Creates and enqueues one metadata record without waiting for delivery.
     */
    fun emit(
        operation: String,
        phase: RecognitionDiagnosticPhase,
        level: RecognitionDiagnosticLevel = RecognitionDiagnosticLevel.INFO,
        providerId: ProviderId? = null,
        durationNanos: Long? = null,
        detail: String? = null,
    ): RecognitionDiagnosticMetadata {
        val metadata = RecognitionDiagnosticMetadata(
            sequence = ids.incrementAndGet(),
            operation = operation,
            phase = phase,
            level = level,
            providerId = providerId,
            durationNanos = durationNanos,
            detail = detail,
        )
        offer(Envelope.Metadata(metadata))
        return metadata
    }

    /**
     * Enqueues a sensitive raw attachment when raw diagnostics are enabled.
     */
    fun emitRaw(
        metadata: RecognitionDiagnosticMetadata,
        raw: RawRecognitionDiagnostic,
    ) {
        if (!configuration.rawDataEnabled) return
        offer(Envelope.Raw(metadata, raw))
    }

    private fun offer(envelope: Envelope) {
        synchronized(queue) {
            when (envelope) {
                is Envelope.Metadata -> makeMetadataRoom()
                is Envelope.Raw -> {
                    if (
                        rawCount >= RAW_LIMIT ||
                        envelope.raw.estimatedBytes > RAW_BYTES_LIMIT ||
                        rawBytes + envelope.raw.estimatedBytes > RAW_BYTES_LIMIT
                    ) {
                        recordRawDrop(envelope.raw.estimatedBytes)
                        schedule()
                        return
                    }
                }
            }
            queue.addLast(envelope)
            when (envelope) {
                is Envelope.Metadata -> metadataCount += 1
                is Envelope.Raw -> {
                    rawCount += 1
                    rawBytes += envelope.raw.estimatedBytes
                }
            }
        }
        schedule()
    }

    private fun makeMetadataRoom() {
        if (metadataCount < METADATA_LIMIT) return
        val rawEnvelope = queue.firstOrNull { it is Envelope.Raw } as? Envelope.Raw
        if (rawEnvelope != null) {
            queue.remove(rawEnvelope)
            rawCount -= 1
            rawBytes -= rawEnvelope.raw.estimatedBytes
            recordRawDrop(rawEnvelope.raw.estimatedBytes)
        } else {
            val metadataEnvelope = queue.firstOrNull { it is Envelope.Metadata }
            if (metadataEnvelope != null) {
                queue.remove(metadataEnvelope)
                metadataCount -= 1
                droppedMetadata.incrementAndGet()
            }
        }
    }

    private fun schedule() {
        if (scheduled.compareAndSet(false, true)) executor.execute(::drain)
    }

    private fun drain() {
        while (true) {
            val envelope = synchronized(queue) {
                queue.pollFirst()?.also {
                    when (it) {
                        is Envelope.Metadata -> metadataCount -= 1
                        is Envelope.Raw -> {
                            rawCount -= 1
                            rawBytes -= it.raw.estimatedBytes
                        }
                    }
                }
            } ?: break
            try {
                deliver(envelope)
            } catch (failure: Throwable) {
                Log.e(configuration.logcatTag, "Diagnostic sink failed", failure)
            }
        }
        reportDrops()
        scheduled.set(false)
        synchronized(queue) {
            if (queue.isNotEmpty()) schedule()
        }
    }

    private fun deliver(envelope: Envelope) {
        when (envelope) {
            is Envelope.Metadata -> {
                log(envelope.value)
                configuration.sink?.onMetadata(envelope.value)
            }
            is Envelope.Raw -> {
                val sink = configuration.sink
                if (sink != null) {
                    sink.onRaw(envelope.metadata, envelope.raw)
                } else {
                    val digest = MessageDigest.getInstance("SHA-256")
                    envelope.raw.updateDigest(digest)
                    val hash = digest.digest().joinToString("") { byte -> "%02x".format(byte) }
                    Log.i(
                        configuration.logcatTag,
                        "raw#${envelope.metadata.sequence} ${envelope.raw.summary} sha256=$hash",
                    )
                }
            }
        }
    }

    private fun log(metadata: RecognitionDiagnosticMetadata) {
        val message = buildString {
            append(metadata.operation)
            append(" phase=")
            append(metadata.phase)
            metadata.providerId?.let {
                append(" provider=")
                append(it)
            }
            metadata.durationNanos?.let {
                append(" durationNanos=")
                append(it)
            }
            metadata.detail?.let {
                append(" detail=")
                append(it)
            }
        }
        when (metadata.level) {
            RecognitionDiagnosticLevel.DEBUG -> Log.d(configuration.logcatTag, message)
            RecognitionDiagnosticLevel.INFO -> Log.i(configuration.logcatTag, message)
            RecognitionDiagnosticLevel.WARNING -> Log.w(configuration.logcatTag, message)
            RecognitionDiagnosticLevel.ERROR -> Log.e(configuration.logcatTag, message)
        }
    }

    private fun recordRawDrop(bytes: Long) {
        droppedRaw.incrementAndGet()
        droppedRawBytes.addAndGet(bytes)
    }

    private fun reportDrops() {
        val counts = RecognitionDiagnosticDropCounts(
            metadata = droppedMetadata.get(),
            raw = droppedRaw.get(),
            rawBytes = droppedRawBytes.get(),
        )
        if (counts.metadata == 0L && counts.raw == 0L) return
        configuration.sink?.onDrops(counts)
        Log.w(
            configuration.logcatTag,
            "diagnosticDrops metadata=${counts.metadata} raw=${counts.raw} rawBytes=${counts.rawBytes}",
        )
    }

    private sealed interface Envelope {
        data class Metadata(val value: RecognitionDiagnosticMetadata) : Envelope
        data class Raw(
            val metadata: RecognitionDiagnosticMetadata,
            val raw: RawRecognitionDiagnostic,
        ) : Envelope
    }

    private companion object {
        const val METADATA_LIMIT = 256
        const val RAW_LIMIT = 4
        const val RAW_BYTES_LIMIT = 64L * 1024L * 1024L
    }
}
