# Stroke compatibility extensions

The recovered SDK keeps all reference Java and JNI signatures and adds two
opt-in compatibility layers. Existing applications continue to use the same
renderer and BOOX framework reflection path by default.

## Reference-compatible fountain renderer

`NeoPenConfig.rendererVersion` defaults to
`NeoPenConfig.RENDERER_RECOVERED_V1`. Select
`NeoPenConfig.RENDERER_REFERENCE_COMPAT` to enable the configurable fountain
renderer:

```java
NeoPenConfig config = NeoPenRendererOptions.useReferenceCompatibleRenderer(
        new NeoPenConfig());
config.smoothLevel = 0.5f;
config.pressureSensitivity = 0.8f;
config.velocitySensitivity = 0.7f;
config.tiltEnabled = true;
NeoPen pen = NeoFountainPen.Companion.create(config);
```

The renderer consumes smoothing, pressure, velocity bounds/sensitivity,
velocity amplification, tilt, direction, brush shape/angle/ratio/spacing, DPI,
display scale, and precision. It preserves the three-float fountain record
format and shares the same Rust state machine between `NeoPenNative` and the
legacy `NeoPenWrapper` JNI APIs. Prediction is computed on a cloned state and
does not advance committed stroke history.

Version 2 is experimental and opt-in until a sufficiently broad BOOX
differential matrix establishes output parity. Version 1 remains the exact
default used by existing applications and the recovered-value fixtures.

## EPD stroke configuration

`EpdController` now forwards advanced vendor operations already implemented by
`BaseDevice`, including stroke parameters, pen-side-button control, and raw
brush/eraser drawing. `StrokeConfiguration` groups color, width, style, and
extra parameters for the framework-style apply operation.

Use the three `supports*` methods before exposing device-specific controls.
They report only methods that resolved as static, matching the SDK's null
receiver invocation contract.

## Stroke transport

The default transport remains `framework-reflection`; on BOOX firmware this is
the path that ultimately reaches SurfaceFlinger. Direct Binder transport is
never enabled automatically and the SDK contains no guessed transaction codes.

Applications with firmware-verified service metadata may opt in:

```java
StrokeTransportConfig transport = new StrokeTransportConfig(
        "SurfaceFlinger",
        "verified.interface.token",
        verifiedStartCode,
        verifiedAddPointCode,
        verifiedFinishCode,
        false);
boolean enabled = EpdController.useSurfaceFlingerStrokeTransport(transport);
```

If service resolution fails, or the Binder rejects the start transaction, the
entire stroke uses the framework/reflection transport. Once a start succeeds
over Binder, that route is retained for the stroke. A later Binder failure
aborts the remaining points instead of delivering an unmatched add/finish
sequence to the framework transport; Binder resolution is retried at the next
stroke boundary. Call `useFrameworkStrokeTransport()` to restore the default
explicitly.

Transaction codes, interface tokens, reply layout, and permissions vary by
firmware. Do not enable the Binder path with unverified values.
