@file:Suppress(
    "AvoidMutableCollections",
    "AvoidVarsExceptWithDelegate",
    "LongMethod",
    "MagicNumber",
    "NoCallbacksInFunctions",
    "ReturnCount",
    "TooGenericExceptionCaught",
)

package com.onyx.android.sdk.recognition.internal

import android.content.Context
import com.onyx.android.sdk.recognition.RecognitionFailure
import com.onyx.android.sdk.recognition.diagnostics.RecognitionDiagnosticLevel
import com.onyx.android.sdk.recognition.diagnostics.RecognitionDiagnosticPhase
import com.onyx.android.sdk.recognition.diagnostics.RecognitionDiagnostics
import com.onyx.android.sdk.recognition.provider.CapabilityValidationState
import com.onyx.android.sdk.recognition.resources.HandwritingResources
import com.onyx.android.sdk.recognition.resources.OcrResources
import com.onyx.android.sdk.recognition.resources.RecognitionResourceSlot
import com.onyx.android.sdk.recognition.resources.RecognitionResourceSlotState
import com.onyx.android.sdk.recognition.resources.ResourceInstallPolicy
import com.onyx.android.sdk.recognition.resources.ResourceInstallReceipt
import com.onyx.android.sdk.recognition.resources.ResourceOperationHandle
import com.onyx.android.sdk.recognition.resources.ResourceOperationPhase
import com.onyx.android.sdk.recognition.resources.ResourceOperationProgress
import com.onyx.android.sdk.recognition.resources.ResourcePackInput
import com.onyx.android.sdk.recognition.resources.ResourcePackRevision
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.security.MessageDigest
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean
import java.util.zip.ZipFile
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.json.JSONArray
import org.json.JSONObject

internal class FileRecognitionResourceStore(
    context: Context,
    private val diagnostics: RecognitionDiagnostics,
    private val activationLane: ProcessRecognitionLane,
) : HandwritingResources, OcrResources {
    private val appContext: Context = context.applicationContext
    private val root = File(context.filesDir, "onyx-recognition-resources")
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val mutex = Mutex()
    private val pins = ConcurrentHashMap<String, Int>()
    private val mutableSlots = MutableStateFlow<List<RecognitionResourceSlotState>>(emptyList())

    override val slots: StateFlow<List<RecognitionResourceSlotState>> = mutableSlots.asStateFlow()

    init {
        root.mkdirs()
        scope.launch {
            recoverInterruptedWork()
            refreshSlots()
        }
    }

    override fun install(
        input: ResourcePackInput,
        policy: ResourceInstallPolicy,
    ): ResourceOperationHandle {
        val operation = ResourceOperation(input, policy)
        operation.start()
        return operation
    }

    override suspend fun activate(
        slot: RecognitionResourceSlot,
    ): Result<ResourceInstallReceipt> = activationLane.submitControl("resource.activate") {
        mutex.withLock {
            val state = readSlot(slot)
            val candidate = state.prepared ?: return@withLock Result.failure(
                RecognitionFailure.ResourceOperationFailed("No prepared candidate for ${slot.value}"),
            )
            activateLocked(slot, candidate, ResourceInstallPolicy.ACTIVATE_AFTER_VERIFY)
        }
    }

    override suspend fun rollback(
        slot: RecognitionResourceSlot,
    ): Result<ResourceInstallReceipt> = activationLane.submitControl("resource.rollback") {
        mutex.withLock {
            val state = readSlot(slot)
            val previous = state.previous ?: return@withLock Result.failure(
                RecognitionFailure.ResourceOperationFailed("No rollback revision for ${slot.value}"),
            )
            val current = state.active
            writePointer(slot, ACTIVE_POINTER, previous.value)
            writePointer(slot, PREVIOUS_POINTER, current?.value)
            refreshSlotsLocked()
            Result.success(
                ResourceInstallReceipt(
                    slot,
                    previous,
                    ResourceInstallPolicy.ACTIVATE_AFTER_VERIFY,
                    activated = true,
                    validation = CapabilityValidationState.UNVERIFIED,
                ),
            )
        }
    }

    override suspend fun remove(
        slot: RecognitionResourceSlot,
        revision: ResourcePackRevision,
    ): Result<Unit> = mutex.withLock {
        val state = readSlot(slot)
        val protected = setOfNotNull(state.active, state.previous)
        val key = pinKey(slot, revision)
        if (revision in protected || (pins[key] ?: 0) > 0) {
            return@withLock Result.failure(
                RecognitionFailure.ResourceProtected("${slot.value}/${revision.value}"),
            )
        }
        val directory = revisionDirectory(slot, revision)
        if (state.prepared == revision) writePointer(slot, PREPARED_POINTER, null)
        directory.deleteRecursively()
        refreshSlotsLocked()
        Result.success(Unit)
    }

    internal fun pin(
        slot: RecognitionResourceSlot,
        revision: ResourcePackRevision,
    ): AutoCloseable {
        val key = pinKey(slot, revision)
        pins.compute(key) { _, count -> (count ?: 0) + 1 }
        val closed = AtomicBoolean()
        return AutoCloseable {
            if (closed.compareAndSet(false, true)) {
                pins.computeIfPresent(key) { _, count -> if (count <= 1) null else count - 1 }
            }
        }
    }

    private inner class ResourceOperation(
        private val input: ResourcePackInput,
        private val policy: ResourceInstallPolicy,
    ) : ResourceOperationHandle {
        private val id = UUID.randomUUID().toString()
        private val cancelled = AtomicBoolean()
        private val verified = AtomicBoolean()
        private val activationStarted = AtomicBoolean()
        private val result = CompletableDeferred<Result<ResourceInstallReceipt>>()
        private val mutableProgress = MutableStateFlow(
            ResourceOperationProgress(ResourceOperationPhase.QUEUED),
        )
        override val progress: StateFlow<ResourceOperationProgress> = mutableProgress.asStateFlow()

        fun start() {
            scope.launch {
                val completed = runCatching { execute() }.fold(
                    onSuccess = { it },
                    onFailure = { failure ->
                        diagnostics.emit(
                            "resource.install",
                            RecognitionDiagnosticPhase.RESOURCE,
                            RecognitionDiagnosticLevel.ERROR,
                            detail = failure.javaClass.simpleName,
                        )
                        Result.failure(
                            if (failure is RecognitionFailure) {
                                failure
                            } else {
                                RecognitionFailure.ResourceOperationFailed(
                                    failure.message ?: failure.javaClass.simpleName,
                                    failure,
                                )
                            },
                        )
                    },
                )
                if (completed.isFailure) {
                    mutableProgress.value = ResourceOperationProgress(
                        ResourceOperationPhase.FAILED,
                        detail = completed.exceptionOrNull()?.message,
                    )
                }
                result.complete(completed)
            }
        }

        override fun cancel() {
            cancelled.set(true)
        }

        override suspend fun await(): Result<ResourceInstallReceipt> = result.await()

        private suspend fun execute(): Result<ResourceInstallReceipt> {
            val staging = File(root, "staging/$id")
            staging.mkdirs()
            val archive = File(staging, "pack.zip")
            try {
                mutableProgress.value = ResourceOperationProgress(ResourceOperationPhase.STAGING)
                stageInput(input, archive, mutableProgress)
                if (cancelled.get()) return cancelBeforeVerification(staging)
                mutableProgress.value = ResourceOperationProgress(ResourceOperationPhase.VERIFYING)
                val verifiedPack = verifyAndExpand(archive, staging, mutableProgress)
                verified.set(true)
                val candidate = revisionDirectory(verifiedPack.slot, verifiedPack.revision)
                mutex.withLock {
                    candidate.parentFile?.mkdirs()
                    if (candidate.exists()) candidate.deleteRecursively()
                    atomicMove(verifiedPack.expandedDirectory, candidate)
                    writePointer(verifiedPack.slot, PREPARED_POINTER, verifiedPack.revision.value)
                    refreshSlotsLocked()
                }
                staging.deleteRecursively()
                mutableProgress.value = ResourceOperationProgress(ResourceOperationPhase.PREPARED)
                if (cancelled.get() || policy == ResourceInstallPolicy.PREPARE_ONLY) {
                    if (cancelled.get()) {
                        mutableProgress.value =
                            ResourceOperationProgress(ResourceOperationPhase.CANCELLED)
                    } else {
                        mutableProgress.value =
                            ResourceOperationProgress(ResourceOperationPhase.COMPLETED)
                    }
                    return Result.success(
                        ResourceInstallReceipt(
                            verifiedPack.slot,
                            verifiedPack.revision,
                            ResourceInstallPolicy.PREPARE_ONLY,
                            activated = false,
                            validation = CapabilityValidationState.UNVERIFIED,
                        ),
                    )
                }
                activationStarted.set(true)
                mutableProgress.value = ResourceOperationProgress(ResourceOperationPhase.ACTIVATING)
                val activation = activationLane.submitControl("resource.install.activate") {
                    mutex.withLock {
                        activateLocked(verifiedPack.slot, verifiedPack.revision, policy)
                    }
                }
                mutableProgress.value = ResourceOperationProgress(
                    if (activation.isSuccess) {
                        ResourceOperationPhase.COMPLETED
                    } else {
                        ResourceOperationPhase.FAILED
                    },
                )
                return activation
            } finally {
                if (!verified.get()) staging.deleteRecursively()
            }
        }

        private fun cancelBeforeVerification(staging: File): Result<ResourceInstallReceipt> {
            staging.deleteRecursively()
            mutableProgress.value = ResourceOperationProgress(ResourceOperationPhase.CANCELLED)
            return Result.failure(
                RecognitionFailure.ResourceOperationFailed("Resource installation cancelled"),
            )
        }
    }

    private fun stageInput(
        input: ResourcePackInput,
        destination: File,
        progress: MutableStateFlow<ResourceOperationProgress>,
    ) {
        val stream = when (input) {
            is ResourcePackInput.FileInput -> FileInputStream(input.file)
            is ResourcePackInput.ContentUri -> requireNotNull(
                appContext.contentResolver.openInputStream(input.uri),
            ) { "Content URI could not be opened" }
            is ResourcePackInput.Stream -> input.input
        }
        stream.use { source ->
            FileOutputStream(destination).use { output ->
                val buffer = ByteArray(COPY_BUFFER)
                var total = 0L
                while (true) {
                    val read = source.read(buffer)
                    if (read < 0) break
                    total += read
                    require(total <= MAX_EXPANDED_BYTES) { "Resource archive exceeds 4 GiB" }
                    output.write(buffer, 0, read)
                    progress.value = ResourceOperationProgress(
                        ResourceOperationPhase.STAGING,
                        bytesProcessed = total,
                    )
                }
            }
        }
    }

    private fun verifyAndExpand(
        archive: File,
        staging: File,
        progress: MutableStateFlow<ResourceOperationProgress>,
    ): VerifiedPack {
        val expanded = File(staging, "expanded")
        expanded.mkdirs()
        ZipFile(archive).use { zip ->
            require(zip.size() <= MAX_ENTRIES) { "Resource pack exceeds 4,096 entries" }
            val manifestEntry = requireNotNull(zip.getEntry(MANIFEST_NAME)) {
                "Resource pack has no $MANIFEST_NAME"
            }
            require(manifestEntry.size in 1..MAX_MANIFEST_BYTES) {
                "Resource manifest exceeds 1 MiB"
            }
            val manifestBytes = zip.getInputStream(manifestEntry).use(InputStream::readBytes)
            val manifestText = manifestBytes.toString(StandardCharsets.UTF_8)
            val manifestJson = JSONObject(manifestText)
            require(canonicalJson(manifestJson) == manifestText) {
                "Resource manifest is not canonical JSON"
            }
            val manifest = parseManifest(manifestJson)
            val expected = manifest.files.associateBy(ManifestFile::path)
            require(expected.size == manifest.files.size) { "Manifest contains duplicate paths" }
            val seen = mutableSetOf<String>()
            var totalExpanded = 0L
            var entries = 0
            val zipEntries = zip.entries()
            while (zipEntries.hasMoreElements()) {
                val entry = zipEntries.nextElement()
                if (entry.isDirectory || entry.name == MANIFEST_NAME) continue
                validatePackPath(entry.name)
                val expectedFile = requireNotNull(expected[entry.name]) {
                    "ZIP entry is absent from manifest: ${entry.name}"
                }
                require(entry.size == expectedFile.size) {
                    "ZIP size differs from manifest for ${entry.name}"
                }
                require(entry.size <= MAX_FILE_BYTES) { "Resource file exceeds 2 GiB" }
                if (entry.size > 0) {
                    require(entry.compressedSize > 0) {
                        "Compressed size is unavailable for ${entry.name}"
                    }
                    require(entry.size <= entry.compressedSize * MAX_EXPANSION_RATIO) {
                        "Resource file exceeds 100:1 expansion ratio"
                    }
                }
                totalExpanded += entry.size
                require(totalExpanded <= MAX_EXPANDED_BYTES) {
                    "Resource pack exceeds 4 GiB expanded"
                }
                val output = File(expanded, entry.name)
                output.parentFile?.mkdirs()
                val digest = MessageDigest.getInstance("SHA-256")
                zip.getInputStream(entry).use { source ->
                    FileOutputStream(output).use { sink ->
                        copyAndDigest(source, sink, digest)
                    }
                }
                require(digest.digest().hex() == expectedFile.sha256) {
                    "SHA-256 mismatch for ${entry.name}"
                }
                seen += entry.name
                entries += 1
                progress.value = ResourceOperationProgress(
                    ResourceOperationPhase.VERIFYING,
                    bytesProcessed = totalExpanded,
                    totalBytes = manifest.files.sumOf(ManifestFile::size),
                    entriesProcessed = entries,
                )
            }
            require(seen == expected.keys) { "Manifest lists files absent from ZIP" }
            File(expanded, MANIFEST_NAME).writeBytes(manifestBytes)
            return VerifiedPack(manifest.slot, manifest.revision, expanded)
        }
    }

    private fun activateLocked(
        slot: RecognitionResourceSlot,
        revision: ResourcePackRevision,
        policy: ResourceInstallPolicy,
    ): Result<ResourceInstallReceipt> {
        val candidate = revisionDirectory(slot, revision)
        if (!candidate.isDirectory) {
            return Result.failure(
                RecognitionFailure.ResourceOperationFailed(
                    "Prepared resource directory is missing for ${slot.value}/${revision.value}",
                ),
            )
        }
        val current = readPointer(slot, ACTIVE_POINTER)?.let(::ResourcePackRevision)
        return try {
            verifyExpandedCandidate(candidate)
            writePointer(slot, PREVIOUS_POINTER, current?.value)
            writePointer(slot, ACTIVE_POINTER, revision.value)
            writePointer(slot, PREPARED_POINTER, null)
            refreshSlotsLocked()
            Result.success(
                ResourceInstallReceipt(
                    slot,
                    revision,
                    policy,
                    activated = true,
                    validation = CapabilityValidationState.UNVERIFIED,
                ),
            )
        } catch (failure: Throwable) {
            current?.let { writePointer(slot, ACTIVE_POINTER, it.value) }
            File(slotDirectory(slot), "quarantine/${revision.value}").also { quarantine ->
                quarantine.parentFile?.mkdirs()
                if (candidate.exists()) atomicMove(candidate, quarantine)
            }
            writePointer(slot, PREPARED_POINTER, null)
            refreshSlotsLocked()
            Result.failure(
                RecognitionFailure.ResourceOperationFailed(
                    "Candidate failed activation verification",
                    failure,
                ),
            )
        }
    }

    private fun verifyExpandedCandidate(directory: File) {
        val manifestFile = File(directory, MANIFEST_NAME)
        val manifestText = manifestFile.readText(StandardCharsets.UTF_8)
        val manifest = parseManifest(JSONObject(manifestText))
        manifest.files.forEach { file ->
            val resource = File(directory, file.path)
            require(resource.isFile && resource.length() == file.size) {
                "Prepared resource is missing or changed: ${file.path}"
            }
            require(resource.inputStream().use(::sha256) == file.sha256) {
                "Prepared resource hash changed: ${file.path}"
            }
        }
    }

    private suspend fun recoverInterruptedWork() = mutex.withLock {
        File(root, "staging").listFiles()?.forEach(File::deleteRecursively)
        refreshSlotsLocked()
    }

    private suspend fun refreshSlots() = mutex.withLock { refreshSlotsLocked() }

    private fun refreshSlotsLocked() {
        val states = root.listFiles()
            .orEmpty()
            .filter { it.isDirectory && it.name != "staging" }
            .mapNotNull { directory ->
                runCatching { readSlot(RecognitionResourceSlot(directory.name)) }.getOrNull()
            }
            .sortedBy { it.slot.value }
        mutableSlots.value = states
    }

    private fun readSlot(slot: RecognitionResourceSlot): RecognitionResourceSlotState =
        RecognitionResourceSlotState(
            slot = slot,
            active = readPointer(slot, ACTIVE_POINTER)?.let(::ResourcePackRevision),
            previous = readPointer(slot, PREVIOUS_POINTER)?.let(::ResourcePackRevision),
            prepared = readPointer(slot, PREPARED_POINTER)?.let(::ResourcePackRevision),
        )

    private fun readPointer(slot: RecognitionResourceSlot, name: String): String? =
        File(slotDirectory(slot), name).takeIf(File::isFile)?.readText()?.trim()?.ifEmpty { null }

    private fun writePointer(slot: RecognitionResourceSlot, name: String, value: String?) {
        val directory = slotDirectory(slot)
        directory.mkdirs()
        val target = File(directory, name)
        if (value == null) {
            target.delete()
            return
        }
        val temporary = File(directory, "$name.tmp")
        temporary.writeText(value, StandardCharsets.UTF_8)
        atomicMove(temporary, target)
    }

    private fun slotDirectory(slot: RecognitionResourceSlot): File = File(root, slot.value)

    private fun revisionDirectory(
        slot: RecognitionResourceSlot,
        revision: ResourcePackRevision,
    ): File = File(slotDirectory(slot), "revisions/${revision.value}")

    private fun pinKey(slot: RecognitionResourceSlot, revision: ResourcePackRevision): String =
        "${slot.value}/${revision.value}"

    private fun parseManifest(json: JSONObject): PackManifest {
        require(json.getInt("schemaVersion") == 1) { "Unsupported resource manifest schema" }
        val filesJson = json.getJSONArray("files")
        require(filesJson.length() <= MAX_ENTRIES) { "Manifest exceeds 4,096 files" }
        val files = (0 until filesJson.length()).map { index ->
            val item = filesJson.getJSONObject(index)
            val path = item.getString("path")
            validatePackPath(path)
            val size = item.getLong("size")
            require(size in 0..MAX_FILE_BYTES) { "Invalid file size for $path" }
            val hash = item.getString("sha256")
            require(hash.matches(Regex("^[0-9a-f]{64}$"))) { "Invalid SHA-256 for $path" }
            ManifestFile(path, size, hash)
        }
        return PackManifest(
            slot = RecognitionResourceSlot(json.getString("slot")),
            revision = ResourcePackRevision(json.getString("revision")),
            files = files,
        )
    }

    private fun validatePackPath(path: String) {
        require(
            path.isNotBlank() &&
                !path.startsWith('/') &&
                '\\' !in path &&
                path.split('/').none { it.isBlank() || it == "." || it == ".." },
        ) { "Resource path is not normalized: $path" }
    }

    private fun canonicalJson(value: Any?): String = when (value) {
        null, JSONObject.NULL -> "null"
        is JSONObject -> value.keys().asSequence().toList().sorted().joinToString(
            prefix = "{",
            postfix = "}",
        ) { key -> "${JSONObject.quote(key)}:${canonicalJson(value.get(key))}" }
        is JSONArray -> (0 until value.length()).joinToString(prefix = "[", postfix = "]") {
            canonicalJson(value.get(it))
        }
        is String -> JSONObject.quote(value)
        is Boolean -> value.toString()
        is Number -> value.toString()
        else -> error("Unsupported canonical JSON value: ${value.javaClass.name}")
    }

    private fun copyAndDigest(
        input: InputStream,
        output: FileOutputStream,
        digest: MessageDigest,
    ) {
        BufferedInputStream(input).use { source ->
            val buffer = ByteArray(COPY_BUFFER)
            while (true) {
                val read = source.read(buffer)
                if (read < 0) break
                output.write(buffer, 0, read)
                digest.update(buffer, 0, read)
            }
        }
    }

    private fun sha256(input: InputStream): String {
        val digest = MessageDigest.getInstance("SHA-256")
        input.use { source ->
            val buffer = ByteArray(COPY_BUFFER)
            while (true) {
                val read = source.read(buffer)
                if (read < 0) break
                digest.update(buffer, 0, read)
            }
        }
        return digest.digest().hex()
    }

    private fun ByteArray.hex(): String = joinToString("") { byte -> "%02x".format(byte) }

    private fun atomicMove(source: File, target: File) {
        target.parentFile?.mkdirs()
        Files.move(
            source.toPath(),
            target.toPath(),
            StandardCopyOption.ATOMIC_MOVE,
            StandardCopyOption.REPLACE_EXISTING,
        )
    }

    private data class ManifestFile(val path: String, val size: Long, val sha256: String)
    private data class PackManifest(
        val slot: RecognitionResourceSlot,
        val revision: ResourcePackRevision,
        val files: List<ManifestFile>,
    )
    private data class VerifiedPack(
        val slot: RecognitionResourceSlot,
        val revision: ResourcePackRevision,
        val expandedDirectory: File,
    )

    private companion object {
        const val MANIFEST_NAME = "recognition-manifest.json"
        const val ACTIVE_POINTER = "active"
        const val PREVIOUS_POINTER = "previous"
        const val PREPARED_POINTER = "prepared"
        const val COPY_BUFFER = 64 * 1024
        const val MAX_MANIFEST_BYTES = 1024L * 1024L
        const val MAX_ENTRIES = 4096
        const val MAX_FILE_BYTES = 2L * 1024L * 1024L * 1024L
        const val MAX_EXPANDED_BYTES = 4L * 1024L * 1024L * 1024L
        const val MAX_EXPANSION_RATIO = 100L
    }
}
