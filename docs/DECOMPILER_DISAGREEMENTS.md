# Decompiler disagreements

Decompiler output is evidence, not authority. Each disputed method was checked
against JVM instructions and exception tables before the main source was
edited.

## Device wrappers

CFR reconstructed the same pattern for all seven failed JADX sites:

```java
try {
    ReflectUtil.invokeMethodSafely(methodField, null, new Object[0]);
} catch (Exception exception) {
    exception.printStackTrace();
}
```

| Class | Method | Static `Method` field |
|---|---|---|
| `RK32XXDevice` | `penUp()` | `b0` |
| `RK32XXDevice` | `resetEpdPost()` | `m0` |
| `IMX6Device` | `resetEpdPost()` | `g0` |
| `RK33XXDevice` | `resetEpdPost()` | `g0` |
| `SDMDevice` | `penUp()` | `j0` |
| `SDMDevice` | `resetEpdPost()` | `u0` |
| `RK31XXDevice` | `resetEpdPost()` | `i0` |

The apparent redundancy is genuine. `ReflectUtil.invokeMethodSafely` already
handles reflective failures internally, but the outer SDK methods still have
their own `Exception` table. The recovery retains both layers.

## Base methods

CFR produced structurally broken local variables for
`FileUtils.readableFileSize`, and its aggressive exception reconstruction for
`RxUtils$d.run` was hard to reason about. JVM bytecode resolved both exactly.
The clean implementations now live directly in the production `FileUtils` and
`RxUtils` classes under `onyxsdk-base/src/main/java`.

## Genuine UnsupportedOperationException sites

Not every `UnsupportedOperationException` is a failed recovery. Kotlin emitted
three guards in `NeoPenRender` for unsupported default-argument super calls.
That behavior remains in the production `NeoPenRender` bridge and is tested
there with the exact message. Synthetic messages such as
`Method not decompiled` are forbidden in the nine repaired source sites.

## Wider diagnostics

JADX retained invalid inferred types in 95 files. CFR avoids `??` tokens but
often emits other non-Java constructs such as `void var1_2`; JD-Core and
Vineflower each omitted classes they could not reduce. The exact-name JADX tree
and cross-decompiler output are retained on the `backup` branch; only reviewed,
compilable classes are promoted into the source-native modules on this branch.
