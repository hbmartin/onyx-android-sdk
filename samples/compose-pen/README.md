# Kotlin/Compose pen-session sample

[`PenCanvasSample.kt`](PenCanvasSample.kt) demonstrates:

- `AndroidView` ownership of the firmware `SurfaceView`;
- lifecycle-bound session creation and deterministic cancellation cleanup;
- lossless completed-stroke collection and state hoisting;
- moving application work to `Dispatchers.Default` without changing event order;
- refresh dispatch through the injectable `DisplayController`; and
- replacement of production session/display implementations with SDK test
  doubles in previews and tests.

The snippet is intentionally app-namespace neutral so it can be copied into an
existing Compose application without introducing a sample application's
package name, theme, or dependency-version policy into the SDK build.
