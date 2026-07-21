# Note Air4 C firmware 4.2 investigation

This document records the firmware used to validate the recovered SDK behavior. The
firmware and decrypted images are investigation inputs, not repository dependencies,
and are intentionally kept outside the Git worktree.

The handwriting, OCR, and speech engines found in this firmware are catalogued
separately in
[Recognition paths in Note Air4 C firmware 4.2](RECOGNITION_FIRMWARE_EVIDENCE.md).

## Firmware identity and download

The supplied legacy API descriptor identifies this release as:

| Field | Value |
| --- | --- |
| Model | `NoteAir4C` |
| Firmware type | `release` |
| Build display ID | `2026-04-28_17-50_4.2-rel_04282_555977efe` |
| Build number | `8548` |
| Descriptor fingerprint | `Onyx/NoteAir4C/NoteAir4C:11/2026-04-28_17-50_4.2-rel_04282_555977efe/8548:user/release-keys` |
| Declared size | `1,983,772,531` bytes |
| Declared MD5 | `0cd82055053aa6787b5375757889e36e` |
| Direct URL | `http://firmware-us.boox.com/0cd82055053aa6787b5375757889e36e/update.upx` |

The exact binary path is therefore the value returned in `downloadUrlList`:

```text
/0cd82055053aa6787b5375757889e36e/update.upx
```

The observed legacy CDN path places the descriptor MD5 in the directory name. The
returned URL should remain authoritative; the pattern should not be assumed for a
different API generation or release.

The downloaded file is stored at:

```text
../recovery-work/firmware/NoteAir4C/2026-04-28_4.2_8548/update.upx
```

Its observed integrity values match the descriptor:

| File | Bytes | MD5 | SHA-256 |
| --- | ---: | --- | --- |
| `update.upx` | 1,983,772,531 | `0cd82055053aa6787b5375757889e36e` | `707aee450892e275a7356cfe0f08d0e734419a51790886a801e09af5f1433c89` |
| decrypted `update.zip` | 1,983,772,531 | `2ff799a7cccb87e6732627e44c96b066` | `dbee76c28a01e34e011f3ccd9b877a2131584b62211e84b72592d6b00737a3c1` |

## Decryption and OTA contents

The publicly documented key material from
[Hagb/decryptBooxUpdateUpx](https://github.com/Hagb/decryptBooxUpdateUpx) successfully
decrypts this package as AES-128-CFB:

```text
key = CF26B9FD1C5F74A8170C24D389F9A92D
iv  = 3BC02F669B2C772CC3F9A583F1FC3263
```

A reproducible download and decrypt sequence is:

```sh
curl --fail --location \
  --output update.upx \
  http://firmware-us.boox.com/0cd82055053aa6787b5375757889e36e/update.upx

openssl enc -d -aes-128-cfb \
  -K CF26B9FD1C5F74A8170C24D389F9A92D \
  -iv 3BC02F669B2C772CC3F9A583F1FC3263 \
  -in update.upx -out update.zip
```

The result is a SignApk-signed A/B OTA ZIP containing seven entries:
`META-INF/com/android/metadata`, `metadata.pb`, `apex_info.pb`, `care_map.pb`,
`payload.bin`, `payload_properties.txt`, and `META-INF/com/android/otacert`.
`payload.bin` is `1,983,766,391` bytes. Its properties report a metadata size of
`141,025` bytes and a post-security-patch level of `2026-04-01`.

The embedded OTA metadata identifies `pre-device=NoteAir4C`, API level 33, and
post-build `ONYX/TabBoox/TabBoox:13/TKQ1.230615.001/GV2.027.SQ83A:user/release-keys`.
That Android 13 post-build identity differs from the Android 11-style fingerprint in
the legacy download descriptor; both values above are transcribed from their
respective sources.

The payload manifest contains these target partitions and uncompressed sizes:

| Partition | Bytes |
| --- | ---: |
| `abl` | 159,744 |
| `aop` | 180,224 |
| `boot` | 100,663,296 |
| `dtbo` | 8,388,608 |
| `modem` | 62,091,264 |
| `odm` | 1,060,864 |
| `product` | 649,891,840 |
| `recovery` | 100,663,296 |
| `system` | 3,133,366,272 |
| `system_ext` | 483,553,280 |
| `vbmeta` | 8,192 |
| `vbmeta_system` | 4,096 |
| `vendor` | 620,429,312 |
| `xbl` | 3,379,200 |

The dynamic partition group is `qti_dynamic_partitions`, with a declared maximum
size of `6,438,256,640` bytes and members `odm`, `product`, `system`, `system_ext`,
and `vendor`.

## Extraction and analysis evidence

The investigation used `payload-dumper-go` for OTA partitions, `debugfs` from
e2fsprogs for ext4 extraction, JADX 1.5.6 for framework bytecode, and Ghidra 12.1.2
for AArch64 native code. Ghidra completed the native analysis, so Binary Ninja was
not needed.

Extracted inputs are under:

```text
../recovery-evidence/firmware-noteair4c-4.2/artifacts/system/
```

The relevant inputs are:

- `framework/framework.jar` and `framework/services.jar`
- `bin/surfaceflinger`
- `lib64/libonyx_pen_touch_reader.so`
- `lib64/libonyx_epd_listener.so`
- `lib64/libneo_pen.so`, `libinputreader.so`, `libinputflinger.so`, and `libota_jni.so`

The retained analysis outputs are:

```text
../recovery-evidence/firmware-noteair4c-4.2/ghidra/pen-reader-all.c
../recovery-evidence/firmware-noteair4c-4.2/ghidra/pen-reader-native-api.c
../recovery-evidence/firmware-noteair4c-4.2/ghidra/pen-reader-inventory.tsv
../recovery-evidence/firmware-noteair4c-4.2/ghidra/epd-listener-all.c
../recovery-evidence/firmware-noteair4c-4.2/ghidra/epd-listener-native-api.c
../recovery-evidence/firmware-noteair4c-4.2/ghidra/epd-listener-inventory.tsv
../recovery-evidence/firmware-noteair4c-4.2/jadx/ViewUpdateHelper.java
../recovery-evidence/firmware-noteair4c-4.2/jadx/EpdEventListener.java
../recovery-evidence/firmware-noteair4c-4.2/jadx/OptimizationConstant.java
```

Ghidra recovered 447 functions from the pen reader and 185 from the EPD listener.
The firmware pen reader and the SDK 1.5.4 ARM64 pen reader are both 84,544 bytes but
have different raw SHA-256 hashes:

| Input | SHA-256 |
| --- | --- |
| SDK 1.5.4 reader | `35ec2d70062e01c92068c4a5814af4c3efd544ecd54d916b67274703ca8c53c1` |
| Firmware 4.2 reader | `a7a2e037265fd6486b02798b3855fcffb46e96b674eff5e59cb4564c0d42b067` |

Their differences are relocation/address material rather than recovered behavior.
After normalizing Ghidra addresses and input-specific headers, both decompilations
produce the same 288,732-byte canonical text with SHA-256
`6fc8d320e800310e57385500ceae63709c8b52d07daa3b5c981883cd409663a9`.
This makes the current firmware a strong independent confirmation of the older
reader behavior used by this project.

## Pen-reader behavior recovered from firmware

The native reader has four reader states: button/idle, drawing, tail erasing, and
side-button erasing. Side-button erase is enabled by default. A simultaneous
`BTN_STYLUS` and `BTN_TOUCH` selects side-button erasing and reports the shortcut
eraser flag separately from tail erasing.

Maximum pressure is queried from the input device's `ABS_PRESSURE` axis with
`EVIOCGABS`. The nonlinear pressure tool smooths pressure, divides it by the device
maximum, and applies this curve before scaling back to device pressure units:

```text
ratio < 0.15:  outputRatio = ratio * 4
otherwise:     outputRatio = min((ratio - 0.135)^0.2 + 0.17, 1)
```

For region checks, the stroke margin is half the configured stroke width:

- A limit rectangle accepts a point only when the entire stroke footprint is inside
  it: `x-margin >= left`, `x+margin <= right`, and likewise for Y.
- An exclusion rectangle rejects a point when any part of the stroke footprint
  overlaps it: `left <= x+margin`, `x-margin <= right`, and likewise for Y.
- Single-region mode (`1`) retains the first matching limit rectangle and continues
  only in that same rectangle. Multi-region mode (`0`) permits transitions among any
  configured limit rectangles.

These findings led to the following compatibility corrections in this repository:

- The Rust reader and the application-renderer fallback now use distinct containment
  and overlap predicates for limit and exclusion rectangles.
- The application fallback implements the recovered single/multi region selection.
- App-renderer side-button erasing now uses `BUTTON_STYLUS_PRIMARY`, remains fixed for
  the complete stroke, and reaches the existing `SIDE_ERASER` raw-input mapping.
- `TouchHelper.setFilterRepeatMovePoint` now calls the renderer method of the same
  name. The recovered 1.5.4 AAR called `setPenUpRefreshEnabled` here; this is treated
  as an original implementation typo rather than behavior to preserve.

## Framework Binder transactions

`android.onyx.ViewUpdateHelper` sends these calls to the `SurfaceFlinger` service
using interface token `android.ui.ISurfaceComposer`:

| Operation | Code | Request after interface token | Reply |
| --- | ---: | --- | --- |
| wait for update finished | `16711703` | none | none |
| handwriting repaint | `1048647` | boolean as `int`, then an absolute-screen `int[4]` region | none |
| save pen-attached framebuffer | `1049600` | none | none |
| get maximum touch pressure | `1048618` | none | `float` |
| get EPD width | `16711727` | none | `float` |
| get EPD height | `16711712` | none | `float` |
| get color type | `1048659` | none | `int` |

The default handwriting repaint overload writes `0` for its boolean flag. When a
`View` is supplied, the four rectangle coordinates are translated to absolute
screen coordinates before `Parcel.writeIntArray`.

## EPD event listener

The framework's optional `android.onyx.optimization.EpdEventListener` loads
`libonyx_epd_listener.so`. On API levels above 30 it reads JSON messages from
`/dev/onyx/listener`; older releases use `/dev/onyx/epdc-listener`. Native code opens
the FIFO and runs a poll/read loop, while Java dispatches parsed message types to
hidden callbacks for EPD updates, activity state, input events, native-surface
updates, split-screen dismissal, and screen-note state.

This appears to be an internal diagnostics/optimization channel and likely requires
system privileges and device-node access. It should not be made a public SDK
dependency without validation on a physical device.

## Validation boundary

The results above come from integrity checks, OTA metadata, JADX output, and static
native analysis. They do not establish that hidden Binder calls or FIFO access are
available to an ordinary third-party application. The behavioral changes are
covered by host-side Rust/JVM tests, but pressure, side-button, region transitions,
and privileged framework transactions should still be compared against a Note Air4
C running this exact build with the device-validation harness.
