package com.onyx.android.sdk.ktx.model

/** Typed failures returned through Kotlin's [Result]. */
sealed class OnyxFailure(
    val operation: String,
    val diagnosticId: Long?,
    message: String,
    cause: Throwable? = null,
) : RuntimeException(message, cause) {
    class UnsupportedCapability(operation: String, diagnosticId: Long?, message: String) :
        OnyxFailure(operation, diagnosticId, message)

    class InvalidArgument(operation: String, message: String) :
        OnyxFailure(operation, null, message)

    class InvalidState(operation: String, diagnosticId: Long?, message: String) :
        OnyxFailure(operation, diagnosticId, message)

    class WrongThread(operation: String, diagnosticId: Long?, message: String) :
        OnyxFailure(operation, diagnosticId, message)

    class SurfaceUnavailable(operation: String, diagnosticId: Long?, message: String) :
        OnyxFailure(operation, diagnosticId, message)

    class SurfaceChanged(operation: String, diagnosticId: Long?, message: String) :
        OnyxFailure(operation, diagnosticId, message)

    class LeaseUnavailable(operation: String, diagnosticId: Long?, message: String) :
        OnyxFailure(operation, diagnosticId, message)

    class HiddenApiDenied(operation: String, diagnosticId: Long?, message: String, cause: Throwable? = null) :
        OnyxFailure(operation, diagnosticId, message, cause)

    class FirmwareRejected(operation: String, diagnosticId: Long?, message: String, cause: Throwable? = null) :
        OnyxFailure(operation, diagnosticId, message, cause)

    class TimedOut(operation: String, diagnosticId: Long?, message: String, cause: Throwable? = null) :
        OnyxFailure(operation, diagnosticId, message, cause)

    class EventOverflow(operation: String, diagnosticId: Long?, message: String) :
        OnyxFailure(operation, diagnosticId, message)

    class NativeRendererFailure(operation: String, diagnosticId: Long?, message: String, cause: Throwable? = null) :
        OnyxFailure(operation, diagnosticId, message, cause)
}
