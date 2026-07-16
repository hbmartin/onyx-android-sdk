package com.onyx.android.sdk.ktx.model

/**
 * Typed failure returned through Kotlin's [Result].
 *
 * @property operation Stable name of the operation that failed.
 * @property diagnosticId Identifier in [com.onyx.android.sdk.ktx.diagnostics.OnyxDiagnostics],
 * or `null` when no diagnostic was recorded.
 */
sealed class OnyxFailure(
    val operation: String,
    val diagnosticId: Long?,
    message: String,
    cause: Throwable? = null,
) : RuntimeException(message, cause) {
    /** A required capability is unsupported by the current device or firmware. */
    class UnsupportedCapability(operation: String, diagnosticId: Long?, message: String) :
        OnyxFailure(operation, diagnosticId, message)

    /** A caller supplied an invalid argument. */
    class InvalidArgument(operation: String, message: String) :
        OnyxFailure(operation, null, message)

    /** The operation is invalid for the current object or firmware state. */
    class InvalidState(operation: String, diagnosticId: Long?, message: String) :
        OnyxFailure(operation, diagnosticId, message)

    /** The operation was invoked from an unsupported thread. */
    class WrongThread(operation: String, diagnosticId: Long?, message: String) :
        OnyxFailure(operation, diagnosticId, message)

    /** A usable rendering surface was not available. */
    class SurfaceUnavailable(operation: String, diagnosticId: Long?, message: String) :
        OnyxFailure(operation, diagnosticId, message)

    /** The rendering surface generation changed during an operation. */
    class SurfaceChanged(operation: String, diagnosticId: Long?, message: String) :
        OnyxFailure(operation, diagnosticId, message)

    /** Another owner holds a required process-wide firmware lease. */
    class LeaseUnavailable(operation: String, diagnosticId: Long?, message: String) :
        OnyxFailure(operation, diagnosticId, message)

    /** Access to a required hidden Android API was denied. */
    class HiddenApiDenied(operation: String, diagnosticId: Long?, message: String, cause: Throwable? = null) :
        OnyxFailure(operation, diagnosticId, message, cause)

    /** Firmware rejected the operation or returned an error. */
    class FirmwareRejected(operation: String, diagnosticId: Long?, message: String, cause: Throwable? = null) :
        OnyxFailure(operation, diagnosticId, message, cause)

    /** The operation did not reach its completion boundary before its deadline. */
    class TimedOut(operation: String, diagnosticId: Long?, message: String, cause: Throwable? = null) :
        OnyxFailure(operation, diagnosticId, message, cause)

    /** A lossless event stream could not accept another event. */
    class EventOverflow(operation: String, diagnosticId: Long?, message: String) :
        OnyxFailure(operation, diagnosticId, message)

    /** The native ink renderer failed to create, process, or release a stroke. */
    class NativeRendererFailure(operation: String, diagnosticId: Long?, message: String, cause: Throwable? = null) :
        OnyxFailure(operation, diagnosticId, message, cause)
}
