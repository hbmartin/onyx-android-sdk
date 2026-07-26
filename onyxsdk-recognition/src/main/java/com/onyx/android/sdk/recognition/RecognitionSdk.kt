@file:Suppress(
    "AvoidMutableCollections",
    "AvoidVarsExceptWithDelegate",
    "LongMethod",
    "LongParameterList",
    "NoCallbacksInFunctions",
    "ReturnCount",
    "TooGenericExceptionCaught",
    "TooManyFunctions",
)

package com.onyx.android.sdk.recognition

import android.app.Application
import android.content.Context
import com.onyx.android.sdk.ktx.model.InkStroke
import com.onyx.android.sdk.recognition.diagnostics.RecognitionDiagnosticLevel
import com.onyx.android.sdk.recognition.diagnostics.RecognitionDiagnosticPhase
import com.onyx.android.sdk.recognition.diagnostics.RecognitionDiagnostics
import com.onyx.android.sdk.recognition.diagnostics.RecognitionDiagnosticsConfiguration
import com.onyx.android.sdk.recognition.hwr.HandwritingInkSnapshot
import com.onyx.android.sdk.recognition.hwr.HandwritingProvider
import com.onyx.android.sdk.recognition.hwr.HandwritingProviderSession
import com.onyx.android.sdk.recognition.hwr.HandwritingRecognizer
import com.onyx.android.sdk.recognition.hwr.HandwritingResult
import com.onyx.android.sdk.recognition.hwr.HandwritingSession
import com.onyx.android.sdk.recognition.hwr.HandwritingSessionOptions
import com.onyx.android.sdk.recognition.hwr.HandwritingSessionState
import com.onyx.android.sdk.recognition.internal.FileRecognitionResourceStore
import com.onyx.android.sdk.recognition.internal.SharedRecognitionLanes
import com.onyx.android.sdk.recognition.ocr.OcrProvider
import com.onyx.android.sdk.recognition.ocr.OcrRecognizer
import com.onyx.android.sdk.recognition.ocr.OcrRequest
import com.onyx.android.sdk.recognition.ocr.OcrResult
import com.onyx.android.sdk.recognition.ocr.OcrRoutingPolicy
import com.onyx.android.sdk.recognition.provider.BuiltInProviderIds
import com.onyx.android.sdk.recognition.provider.CapabilityDiscoveryState
import com.onyx.android.sdk.recognition.provider.CapabilityUnavailableReason
import com.onyx.android.sdk.recognition.provider.CapabilityValidationState
import com.onyx.android.sdk.recognition.provider.ComputeBackend
import com.onyx.android.sdk.recognition.provider.HandwritingProviderContext
import com.onyx.android.sdk.recognition.provider.HandwritingProviderFactory
import com.onyx.android.sdk.recognition.provider.LanguageTag
import com.onyx.android.sdk.recognition.provider.OcrProviderContext
import com.onyx.android.sdk.recognition.provider.OcrProviderFactory
import com.onyx.android.sdk.recognition.provider.ProviderCapability
import com.onyx.android.sdk.recognition.provider.ProviderId
import com.onyx.android.sdk.recognition.provider.ProviderInputOwnership
import com.onyx.android.sdk.recognition.provider.ProviderLimits
import com.onyx.android.sdk.recognition.provider.ProviderSpiCompatibility
import com.onyx.android.sdk.recognition.provider.RecognitionCapabilitySnapshot
import com.onyx.android.sdk.recognition.provider.RecognitionPipeline
import com.onyx.android.sdk.recognition.provider.RecognitionProviderDescriptor
import com.onyx.android.sdk.recognition.provider.SpeechProviderContext
import com.onyx.android.sdk.recognition.provider.SpeechProviderFactory
import com.onyx.android.sdk.recognition.resources.HandwritingResources
import com.onyx.android.sdk.recognition.resources.OcrResources
import com.onyx.android.sdk.recognition.speech.SpeechProvider
import com.onyx.android.sdk.recognition.speech.SpeechProviderSession
import com.onyx.android.sdk.recognition.speech.SpeechRecognizer
import com.onyx.android.sdk.recognition.speech.SpeechRequest
import com.onyx.android.sdk.recognition.speech.SpeechSession
import com.onyx.android.sdk.recognition.speech.SpeechSessionEvent
import com.onyx.android.sdk.recognition.speech.SpeechSessionState
import java.util.Locale
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.time.Duration

/**
 * Immutable process initialization configuration.
 *
 * Provider collections and disabled IDs are copied at construction. Diagnostics,
 * raw logging, provider registration, and disabled built-ins are frozen by
 * [RecognitionSdk.initialize].
 */
class RecognitionConfiguration(
    handwritingProviders: Collection<HandwritingProviderFactory> = emptyList(),
    ocrProviders: Collection<OcrProviderFactory> = emptyList(),
    speechProviders: Collection<SpeechProviderFactory> = emptyList(),
    disabledBundledProviders: Collection<ProviderId> = emptySet(),
    /** Immutable diagnostics and raw-data policy. */
    val diagnostics: RecognitionDiagnosticsConfiguration = RecognitionDiagnosticsConfiguration(),
) {
    /** Explicit optional or custom handwriting factories. */
    val handwritingProviders: List<HandwritingProviderFactory> = handwritingProviders.toList()

    /** Explicit optional or custom OCR factories. */
    val ocrProviders: List<OcrProviderFactory> = ocrProviders.toList()

    /** Explicit optional or custom on-device speech factories. */
    val speechProviders: List<SpeechProviderFactory> = speechProviders.toList()

    /** Bundled providers suppressed during discovery. */
    val disabledBundledProviders: Set<ProviderId> = disabledBundledProviders.toSet()
}

/**
 * Process-wide recognition runtime.
 */
interface RecognitionRuntime {
    /** Live capability discovery and validation state. */
    val capabilities: StateFlow<RecognitionCapabilitySnapshot>

    /** Handwriting facade. */
    val handwriting: HandwritingRecognizer

    /** OCR facade. */
    val ocr: OcrRecognizer

    /** Local-only speech facade. */
    val speech: SpeechRecognizer

    /** Handwriting resource store. */
    val handwritingResources: HandwritingResources

    /** OCR resource store. */
    val ocrResources: OcrResources

    /** Waits for initial asynchronous discovery. */
    suspend fun awaitInitialDiscovery(): Result<RecognitionCapabilitySnapshot>

    /** Re-runs provider availability discovery. */
    suspend fun refreshCapabilities(): Result<RecognitionCapabilitySnapshot>

    /** Promotes or demotes a usable custom provider for automatic routing. */
    fun setCapabilityValidation(
        providerId: ProviderId,
        validation: CapabilityValidationState,
    ): Result<RecognitionCapabilitySnapshot>
}

/**
 * Single-entry process bootstrap.
 */
object RecognitionSdk {
    private val runtime = AtomicReference<RecognitionRuntime?>()

    /**
     * Validates and freezes configuration, then starts asynchronous discovery.
     *
     * This method is non-suspending and may be called exactly once per process.
     * Invalid programmer configuration throws. Operational initialization
     * failures are returned in [Result].
     */
    fun initialize(
        application: Application,
        configuration: RecognitionConfiguration,
    ): Result<RecognitionRuntime> {
        check(runtime.get() == null) { "RecognitionSdk.initialize may be called exactly once" }
        validateConfiguration(configuration)
        return try {
            val created = DefaultRecognitionRuntime(
                application.applicationContext,
                configuration,
            )
            check(runtime.compareAndSet(null, created)) {
                "RecognitionSdk.initialize may be called exactly once"
            }
            Result.success(created)
        } catch (failure: IllegalArgumentException) {
            throw failure
        } catch (failure: IllegalStateException) {
            throw failure
        } catch (failure: Throwable) {
            Result.failure(
                RecognitionFailure.InitializationFailed(
                    failure.message ?: failure.javaClass.simpleName,
                    failure,
                ),
            )
        }
    }

    private fun validateConfiguration(configuration: RecognitionConfiguration) {
        val registrations = buildList {
            addAll(configuration.handwritingProviders)
            addAll(configuration.ocrProviders)
            addAll(configuration.speechProviders)
        }
        val duplicates = registrations.groupingBy { it.descriptor.id }.eachCount()
            .filterValues { it > 1 }
            .keys
        require(duplicates.isEmpty()) { "Duplicate provider IDs: $duplicates" }
        require(registrations.none { it.descriptor.id in BuiltInProviderIds.all }) {
            "Custom factories must not replace bundled provider IDs"
        }
        require(configuration.disabledBundledProviders.all { it in BuiltInProviderIds.all }) {
            "Only bundled provider IDs may be disabled"
        }
        registrations.forEach { factory ->
            require(factory.compatibility.supports(HOST_SPI_MAJOR, HOST_SPI_MINOR)) {
                "Provider ${factory.descriptor.id} is incompatible with host SPI " +
                    "$HOST_SPI_MAJOR.$HOST_SPI_MINOR"
            }
        }
        configuration.handwritingProviders.forEach {
            require(it.descriptor.pipeline == RecognitionPipeline.HANDWRITING)
        }
        configuration.ocrProviders.forEach {
            require(it.descriptor.pipeline == RecognitionPipeline.OCR)
        }
        configuration.speechProviders.forEach {
            require(it.descriptor.pipeline == RecognitionPipeline.SPEECH)
        }
    }

    private const val HOST_SPI_MAJOR = 1
    private const val HOST_SPI_MINOR = 0
}

private class DefaultRecognitionRuntime(
    private val context: Context,
    private val configuration: RecognitionConfiguration,
) : RecognitionRuntime {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val discoveryMutex = Mutex()
    private val initialDiscovery = CompletableDeferred<Result<RecognitionCapabilitySnapshot>>()
    private val diagnostics = RecognitionDiagnostics(configuration.diagnostics)
    private val resourceStore = FileRecognitionResourceStore(
        context,
        diagnostics,
        SharedRecognitionLanes.hwrAndOcr,
    )
    private val registrations = Registrations(configuration)
    private val mutableCapabilities = MutableStateFlow(
        RecognitionCapabilitySnapshot(CapabilityDiscoveryState.DISCOVERING, emptyList()),
    )
    private val providers = AtomicReference(ProviderSet())
    private val speechBusy = AtomicBoolean()

    override val capabilities: StateFlow<RecognitionCapabilitySnapshot> =
        mutableCapabilities.asStateFlow()
    override val handwritingResources: HandwritingResources = resourceStore
    override val ocrResources: OcrResources = resourceStore
    override val handwriting: HandwritingRecognizer = RuntimeHandwritingRecognizer()
    override val ocr: OcrRecognizer = RuntimeOcrRecognizer()
    override val speech: SpeechRecognizer = RuntimeSpeechRecognizer()

    init {
        scope.launch {
            val result = discover()
            initialDiscovery.complete(result)
        }
    }

    override suspend fun awaitInitialDiscovery(): Result<RecognitionCapabilitySnapshot> =
        initialDiscovery.await()

    override suspend fun refreshCapabilities(): Result<RecognitionCapabilitySnapshot> = discover()

    override fun setCapabilityValidation(
        providerId: ProviderId,
        validation: CapabilityValidationState,
    ): Result<RecognitionCapabilitySnapshot> {
        require(validation != CapabilityValidationState.UNAVAILABLE) {
            "Hosts may promote or demote only between VALIDATED and UNVERIFIED"
        }
        val current = mutableCapabilities.value
        val capability = current.capability(providerId) ?: return Result.failure(
            RecognitionFailure.ProviderUnavailable(providerId, "Provider is not registered"),
        )
        if (capability.validation == CapabilityValidationState.UNAVAILABLE) {
            return Result.failure(
                RecognitionFailure.ProviderUnavailable(providerId, capability.detail.orEmpty()),
            )
        }
        require(providerId !in BuiltInProviderIds.all) {
            "SDK-tested built-in validation state is not host-mutable"
        }
        val updated = RecognitionCapabilitySnapshot(
            current.discovery,
            current.providers.map {
                if (it.descriptor.id == providerId) it.copy(validation = validation) else it
            },
        )
        mutableCapabilities.value = updated
        return Result.success(updated)
    }

    private suspend fun discover(): Result<RecognitionCapabilitySnapshot> = discoveryMutex.withLock {
        val discoveredHwr = linkedMapOf<ProviderId, HandwritingProvider>()
        val discoveredOcr = linkedMapOf<ProviderId, OcrProvider>()
        val discoveredSpeech = linkedMapOf<ProviderId, SpeechProvider>()
        val states = mutableListOf<ProviderCapability>()
        registrations.handwriting.forEach { factory ->
            states += discoverFactory(
                factory.descriptor,
                factory.descriptor.id in configuration.disabledBundledProviders,
            ) {
                factory.create(
                    HandwritingProviderContext(context, resourceStore, diagnostics),
                ).onSuccess { discoveredHwr[factory.descriptor.id] = it }
            }
        }
        registrations.ocr.forEach { factory ->
            states += discoverFactory(
                factory.descriptor,
                factory.descriptor.id in configuration.disabledBundledProviders,
            ) {
                factory.create(
                    OcrProviderContext(context, resourceStore, diagnostics),
                ).onSuccess { discoveredOcr[factory.descriptor.id] = it }
            }
        }
        registrations.speech.forEach { factory ->
            states += discoverFactory(
                factory.descriptor,
                factory.descriptor.id in configuration.disabledBundledProviders,
            ) {
                factory.create(
                    SpeechProviderContext(context, diagnostics),
                ).onSuccess { discoveredSpeech[factory.descriptor.id] = it }
            }
        }
        providers.set(ProviderSet(discoveredHwr, discoveredOcr, discoveredSpeech))
        val snapshot = RecognitionCapabilitySnapshot(CapabilityDiscoveryState.COMPLETE, states)
        mutableCapabilities.value = snapshot
        diagnostics.emit(
            "recognition.discovery",
            RecognitionDiagnosticPhase.DISCOVERY,
            detail = "providers=${states.size}",
        )
        Result.success(snapshot)
    }

    private inline fun <T> discoverFactory(
        descriptor: RecognitionProviderDescriptor,
        disabled: Boolean,
        create: () -> Result<T>,
    ): ProviderCapability {
        if (disabled) {
            return ProviderCapability(
                descriptor,
                CapabilityValidationState.UNAVAILABLE,
                CapabilityUnavailableReason.DISABLED_BY_HOST,
                "Disabled by host configuration",
            )
        }
        val result = try {
            create()
        } catch (failure: Throwable) {
            Result.failure(failure)
        }
        return if (result.isSuccess) {
            ProviderCapability(
                descriptor,
                if (descriptor.id in BuiltInProviderIds.all) {
                    CapabilityValidationState.VALIDATED
                } else {
                    CapabilityValidationState.UNVERIFIED
                },
            )
        } else {
            val failure = result.exceptionOrNull()
            diagnostics.emit(
                "provider.discovery",
                RecognitionDiagnosticPhase.DISCOVERY,
                RecognitionDiagnosticLevel.WARNING,
                descriptor.id,
                detail = failure?.javaClass?.simpleName,
            )
            ProviderCapability(
                descriptor,
                CapabilityValidationState.UNAVAILABLE,
                if (descriptor.id in BuiltInProviderIds.all) {
                    CapabilityUnavailableReason.DEPENDENCY_MISSING
                } else {
                    CapabilityUnavailableReason.INITIALIZATION_FAILED
                },
                failure?.message,
            )
        }
    }

    private fun requireReady(): Result<Unit> =
        if (mutableCapabilities.value.discovery == CapabilityDiscoveryState.DISCOVERING) {
            Result.failure(RecognitionFailure.NotReady())
        } else {
            Result.success(Unit)
        }

    private fun providerCapability(providerId: ProviderId): Result<ProviderCapability> {
        val capability = mutableCapabilities.value.capability(providerId)
            ?: return Result.failure(
                RecognitionFailure.ProviderUnavailable(providerId, "Provider is not registered"),
            )
        if (capability.validation == CapabilityValidationState.UNAVAILABLE) {
            return Result.failure(
                RecognitionFailure.ProviderUnavailable(
                    providerId,
                    capability.detail ?: capability.unavailableReason.toString(),
                ),
            )
        }
        return Result.success(capability)
    }

    private inner class RuntimeHandwritingRecognizer : HandwritingRecognizer {
        override suspend fun openSession(
            options: HandwritingSessionOptions,
        ): Result<HandwritingSession> {
            requireReady().exceptionOrNull()?.let { return Result.failure(it) }
            val capability = providerCapability(options.providerId)
                .getOrElse { return Result.failure(it) }
            val provider = providers.get().handwriting[options.providerId]
                ?: return Result.failure(
                    RecognitionFailure.ProviderUnavailable(options.providerId, "Provider not loaded"),
                )
            val resolved = options.copy(
                languages = options.languages.ifEmpty { applicationLanguages() },
            )
            val opened = SharedRecognitionLanes.hwrAndOcr.submit(
                "hwr.open-session",
                timeout = null,
            ) {
                providerCall(options.providerId) { provider.openSession(resolved) }
            }
            return opened.map {
                RuntimeHandwritingSession(
                    options.providerId,
                    capability.descriptor.limits,
                    it,
                )
            }
        }

        override suspend fun recognize(
            strokes: List<InkStroke>,
            options: HandwritingSessionOptions,
            timeout: Duration?,
        ): Result<HandwritingResult> {
            val session = openSession(options).getOrElse { return Result.failure(it) }
            return try {
                session.recognize(strokes, timeout)
            } finally {
                session.closeAndAwait()
            }
        }
    }

    private class RuntimeHandwritingSession(
        private val providerId: ProviderId,
        private val limits: ProviderLimits,
        private val delegate: HandwritingProviderSession,
    ) : HandwritingSession {
        private val closed = AtomicBoolean()
        private val mutableState = MutableStateFlow(HandwritingSessionState.OPEN)
        override val state: StateFlow<HandwritingSessionState> = mutableState.asStateFlow()

        override suspend fun recognize(
            strokes: List<InkStroke>,
            timeout: Duration?,
        ): Result<HandwritingResult> {
            check(!closed.get()) { "Handwriting session is closed" }
            val snapshot = HandwritingInkSnapshot.copyOf(strokes)
            if (limits.maximumPoints > 0 && snapshot.pointCount > limits.maximumPoints) {
                return Result.failure(
                    RecognitionFailure.Unsupported(
                        "Point count ${snapshot.pointCount} exceeds provider limit " +
                            limits.maximumPoints,
                    ),
                )
            }
            return SharedRecognitionLanes.hwrAndOcr.submit("hwr.recognize", timeout) {
                providerCall(providerId) { delegate.recognize(snapshot) }
            }
        }

        override fun close() {
            if (closed.compareAndSet(false, true)) {
                mutableState.value = HandwritingSessionState.CLOSING
                delegate.close()
            }
        }

        override suspend fun closeAndAwait(): Result<Unit> {
            close()
            val result = providerCall(providerId) { delegate.closeAndAwait() }
            mutableState.value = if (result.isSuccess) {
                HandwritingSessionState.CLOSED
            } else {
                HandwritingSessionState.FAILED
            }
            return result
        }
    }

    private inner class RuntimeOcrRecognizer : OcrRecognizer {
        override suspend fun recognize(
            request: OcrRequest,
            timeout: Duration?,
        ): Result<OcrResult> {
            requireReady().exceptionOrNull()?.let { return Result.failure(it) }
            val providerId = when (val routing = request.routing) {
                is OcrRoutingPolicy.Provider -> routing.providerId
                OcrRoutingPolicy.Auto -> autoOcrProvider(request)
                    ?: return Result.failure(
                        RecognitionFailure.Unsupported(
                            "No validated OCR provider covers the requested language or script",
                        ),
                    )
            }
            val capability = providerCapability(providerId).getOrElse { return Result.failure(it) }
            if (
                request.routing == OcrRoutingPolicy.Auto &&
                capability.validation != CapabilityValidationState.VALIDATED
            ) {
                return Result.failure(
                    RecognitionFailure.ProviderUnavailable(
                        providerId,
                        "Automatic routing requires a validated capability",
                    ),
                )
            }
            val limits = capability.descriptor.limits
            if (
                limits.maximumPixels > 0 &&
                request.input.layout.pixelCount > limits.maximumPixels
            ) {
                return Result.failure(
                    RecognitionFailure.Unsupported("OCR input exceeds provider pixel limit"),
                )
            }
            val provider = providers.get().ocr[providerId] ?: return Result.failure(
                RecognitionFailure.ProviderUnavailable(providerId, "Provider not loaded"),
            )
            return SharedRecognitionLanes.hwrAndOcr.submit("ocr.recognize", timeout) {
                providerCall(providerId) { provider.recognize(request) }
            }
        }

        private fun autoOcrProvider(request: OcrRequest): ProviderId? {
            val validated = mutableCapabilities.value.providers.filter {
                it.descriptor.pipeline == RecognitionPipeline.OCR &&
                    it.validation == CapabilityValidationState.VALIDATED
            }
            val hints = request.languageHints.ifEmpty { applicationLanguages() }
            fun supports(capability: ProviderCapability): Boolean =
                capability.descriptor.supportedLanguages.isEmpty() ||
                    hints.all { hint ->
                        capability.descriptor.supportedLanguages.any {
                            it.canonical() == hint.canonical()
                        }
                    }
            return validated.firstOrNull {
                it.descriptor.id == BuiltInProviderIds.GOOGLE_MLKIT_TEXT_RECOGNITION_V2 &&
                    supports(it)
            }?.descriptor?.id ?: validated.firstOrNull(::supports)?.descriptor?.id
        }
    }

    private inner class RuntimeSpeechRecognizer : SpeechRecognizer {
        override suspend fun openSession(request: SpeechRequest): Result<SpeechSession> {
            requireReady().exceptionOrNull()?.let { return Result.failure(it) }
            if (!speechBusy.compareAndSet(false, true)) {
                return Result.failure(RecognitionFailure.SpeechBusy())
            }
            val capability = providerCapability(request.providerId)
                .getOrElse {
                    speechBusy.set(false)
                    return Result.failure(it)
                }
            val provider = providers.get().speech[request.providerId]
            if (provider == null) {
                speechBusy.set(false)
                return Result.failure(
                    RecognitionFailure.ProviderUnavailable(request.providerId, "Provider not loaded"),
                )
            }
            val resolved = request.copy(
                languages = request.languages.ifEmpty { applicationLanguages() },
            )
            val opened = providerCall(request.providerId) { provider.openSession(resolved) }
            if (opened.isFailure) speechBusy.set(false)
            return opened.map {
                RuntimeSpeechSession(request.providerId, it, speechBusy)
            }.also {
                diagnostics.emit(
                    "speech.open-session",
                    RecognitionDiagnosticPhase.INFERENCE,
                    providerId = capability.descriptor.id,
                )
            }
        }
    }

    private class RuntimeSpeechSession(
        private val providerId: ProviderId,
        private val delegate: SpeechProviderSession,
        private val busy: AtomicBoolean,
    ) : SpeechSession {
        private val closed = AtomicBoolean()
        override val events: Flow<SpeechSessionEvent> = delegate.events
        override val state: StateFlow<SpeechSessionState> = delegate.state

        override fun close() {
            if (closed.compareAndSet(false, true)) {
                delegate.close()
                busy.set(false)
            }
        }

        override suspend fun closeAndAwait(): Result<Unit> {
            close()
            return providerCall(providerId) { delegate.closeAndAwait() }
        }
    }

    private fun applicationLanguages(): List<LanguageTag> {
        val locales = context.resources.configuration.locales
        return (0 until locales.size()).map { index ->
            LanguageTag(locales[index].toLanguageTag())
        }.ifEmpty { listOf(LanguageTag(Locale.getDefault().toLanguageTag())) }
    }

    private data class ProviderSet(
        val handwriting: Map<ProviderId, HandwritingProvider> = emptyMap(),
        val ocr: Map<ProviderId, OcrProvider> = emptyMap(),
        val speech: Map<ProviderId, SpeechProvider> = emptyMap(),
    )

    private class Registrations(configuration: RecognitionConfiguration) {
        val handwriting: List<HandwritingProviderFactory> =
            bundledHandwritingFactories() + configuration.handwritingProviders
        val ocr: List<OcrProviderFactory> = bundledOcrFactories() + configuration.ocrProviders
        val speech: List<SpeechProviderFactory> =
            bundledSpeechFactories() + configuration.speechProviders
    }
}

private suspend inline fun <T> providerCall(
    providerId: ProviderId,
    call: () -> Result<T>,
): Result<T> = try {
    call()
} catch (cancelled: CancellationException) {
    throw cancelled
} catch (failure: Throwable) {
    Result.failure(RecognitionFailure.ProviderBug(providerId, failure))
}

private class UnavailableHandwritingFactory(
    override val descriptor: RecognitionProviderDescriptor,
) : HandwritingProviderFactory {
    override val compatibility: ProviderSpiCompatibility = ProviderSpiCompatibility(1, 0, 0)
    override fun create(context: HandwritingProviderContext): Result<HandwritingProvider> =
        Result.failure(
            RecognitionFailure.ProviderUnavailable(
                descriptor.id,
                "Licensed runtime or Google Digital Ink dependency is not packaged",
            ),
        )
}

private class UnavailableOcrFactory(
    override val descriptor: RecognitionProviderDescriptor,
) : OcrProviderFactory {
    override val compatibility: ProviderSpiCompatibility = ProviderSpiCompatibility(1, 0, 0)
    override fun create(context: OcrProviderContext): Result<OcrProvider> =
        Result.failure(
            RecognitionFailure.ProviderUnavailable(
                descriptor.id,
                "ML Kit or licensed Paddle runtime/model dependency is not packaged",
            ),
        )
}

private class UnavailableSpeechFactory(
    override val descriptor: RecognitionProviderDescriptor,
) : SpeechProviderFactory {
    override val compatibility: ProviderSpiCompatibility = ProviderSpiCompatibility(1, 0, 0)
    override fun create(context: SpeechProviderContext): Result<SpeechProvider> =
        Result.failure(
            RecognitionFailure.ProviderUnavailable(
                descriptor.id,
                "On-device SODA provider adapter is not available on this build",
            ),
        )
}

private fun bundledHandwritingFactories(): List<HandwritingProviderFactory> = listOf(
    UnavailableHandwritingFactory(
        descriptor(
            BuiltInProviderIds.MYSCRIPT_IINK_V4,
            RecognitionPipeline.HANDWRITING,
            "MyScript iink",
            "4",
            ProviderLimits(maximumPoints = 1_000_000),
        ),
    ),
    UnavailableHandwritingFactory(
        descriptor(
            BuiltInProviderIds.GOOGLE_MLKIT_DIGITAL_INK_V1,
            RecognitionPipeline.HANDWRITING,
            "Google ML Kit Digital Ink",
            "1",
            ProviderLimits(maximumPoints = 1_000_000),
        ),
    ),
)

private fun bundledOcrFactories(): List<OcrProviderFactory> = listOf(
    UnavailableOcrFactory(
        descriptor(
            BuiltInProviderIds.GOOGLE_MLKIT_TEXT_RECOGNITION_V2,
            RecognitionPipeline.OCR,
            "Google ML Kit Text Recognition",
            "2",
            ProviderLimits(maximumPixels = 100_000_000),
        ),
    ),
    UnavailableOcrFactory(
        descriptor(
            BuiltInProviderIds.PADDLE_OCR_V1_2020,
            RecognitionPipeline.OCR,
            "Paddle OCR",
            "1-2020",
            ProviderLimits(maximumPixels = 100_000_000),
        ),
    ),
)

private fun bundledSpeechFactories(): List<SpeechProviderFactory> = listOf(
    UnavailableSpeechFactory(
        descriptor(
            BuiltInProviderIds.GOOGLE_SODA_V1,
            RecognitionPipeline.SPEECH,
            "Google On-device Speech",
            "1",
            ProviderLimits(maximumAudioFrames = Long.MAX_VALUE),
        ),
    ),
)

private fun descriptor(
    id: ProviderId,
    pipeline: RecognitionPipeline,
    name: String,
    version: String,
    limits: ProviderLimits,
): RecognitionProviderDescriptor = RecognitionProviderDescriptor(
    id = id,
    pipeline = pipeline,
    engineName = name,
    engineVersion = version,
    computeBackends = listOf(ComputeBackend.CPU),
    ownershipModes = listOf(
        ProviderInputOwnership.COPIED,
        ProviderInputOwnership.SNAPSHOT,
        ProviderInputOwnership.BORROW_UNTIL_COMPLETION,
    ),
    limits = limits,
)
