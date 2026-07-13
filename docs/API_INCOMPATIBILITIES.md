# Accepted API incompatibilities

This file records deliberate differences from the original Onyx SDK public
surface. These differences are product decisions rather than incomplete
recovery work.

## Retrofit firmware update removal

Retrofit 2.1.0 and its transitive OkHttp client were removed to reduce the
external dependency surface. The SDK no longer performs the legacy
`firmware/update` network request.

Accepted compatibility effects:

- `OnyxOTAService.firmwareUpdate(String): retrofit2.Call<Firmware>` is removed.
- The Retrofit-specific public `FastJsonConverterFactory` class is removed.
- The Retrofit/OkHttp-specific public `ServiceFactory` class is removed.
- `FirmwareUpdateRequest.execute()` retains its JVM signature for callers but
  now throws `UnsupportedOperationException` instead of making a request.

Existing binaries linked to any removed class or method must be rebuilt.
Consumers that still need OTA discovery must provide their own HTTP client and
firmware service implementation.

The original-vs-recovered checker accepts the exact binary breaks through
`device-validation/accepted-residuals/base.json`. Contract and generated-test
checkers also exclude the removed `firmwareUpdate` descriptor explicitly; all
other API differences continue to fail their normal gates.

## Kotlin pen interoperability additions

`PenBrushInk` retains its original unsigned Kotlin constructor and mangled
accessors, and now also exposes an additive `(float, float, int, int, int)`
constructor plus unsigned integer accessors for Java. The recovered renderer
uses those methods directly instead of invoking the mangled members through
reflection for every brush stamp. It also implements `hashCode()` consistently
with its existing `equals()` method.

These additions do not remove or rename reference members. They are accepted by
`scripts/pen-api-additions.json` and the classified pen residuals.
