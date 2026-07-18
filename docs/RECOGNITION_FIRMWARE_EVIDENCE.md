# Recognition paths in Note Air4 C firmware 4.2

<!-- markdownlint-disable MD013 MD060 -->

This document records the recognition engines and resource paths observed during the
Note Air4 C firmware 4.2 investigation. It separates firmware labels from actual
engines so that the recognition platform can use stable, truthful provider IDs.

The extracted firmware is analysis evidence only. Its native libraries, models,
dictionaries, certificates, and resources must not become repository files, build
inputs, fixtures, or publication contents.

See the broader
[firmware investigation](FIRMWARE_NOTEAIR4C_4_2.md) for firmware identity,
integrity, extraction, and validation boundaries.

## Recognition inventory

The firmware contains four distinct handwriting/OCR paths. Speech is a fifth,
separate path found in Google Speech Services rather than in the handwriting/OCR
provider set.

| Input | Firmware label | Actual engine | Platform provider ID |
| --- | --- | --- | --- |
| Bitmap/page image | `ONYX_OFFLINE` | ML Kit Vision Text Recognition | `google-mlkit-text-recognition-v2` |
| Bitmap/page image | `PADDLE` | PaddleOCR/Paddle Lite | `paddleocr-v1-2020` |
| Pen stroke sequence | `MyScript` | MyScript iink | `myscript-iink-v4` |
| Pen stroke sequence | built-in / `GoogleML` | ML Kit Digital Ink plus BOOX postprocessing | `google-mlkit-digital-ink-v1` |
| Microphone/audio | SODA path | Google on-device speech | `google-soda-v1` |

No handwriting engine is literally named `BOOX` in the observed engine enum. The UI
or service's built-in option maps to `GoogleML`. Firmware labels are therefore aliases,
not public engine identities.

## ML Kit OCR

`OnyxOfflineOCRProvider` calls ML Kit `TextRecognition`. This is the complete and
actively used firmware OCR path.

Observed characteristics:

- bundled Latin, Chinese, Japanese, Korean, and Devanagari models;
- Devanagari routing for Hindi, Marathi, and Nepali;
- blocks, lines, elements, symbols, and per-symbol bounding boxes;
- bitmap scaling, sharpening, gamma correction, and color filtering before inference;
- explicit offline classification that bypasses cloud quota/permission handling; and
- approximately 5.15 MiB of OCR model assets plus the shared ML Kit/TFLite runtime.

The platform design reproduces this through the supported ML Kit Text Recognition v2
API. Firmware preprocessing becomes a versioned provider profile rather than public
tuning knobs.

## Legacy Paddle OCR

The firmware's `PADDLE` path is an older Chinese-centric PaddleOCR pipeline:

| Resource | Observed size |
| --- | ---: |
| MobileNetV3 + DB detector | 4,468,263 bytes |
| MobileNetV3 + CRNN/LSTM/CTC recognizer | 4,732,940 bytes |
| 0°/180° orientation classifier | 755,197 bytes |
| Chinese/multilingual dictionary | 6,622 symbols |
| Total model/dictionary footprint | approximately 9.52 MiB |

The pipeline limits the maximum page dimension to 960 pixels, rounds dimensions down
to multiples of 32, and configures four CPU threads with `LITE_POWER_HIGH`.

The model files and dictionary are byte-identical to the September 2020
`ocr_v1_for_cpu` Android bundle. Relevant upstream snapshots are:

- [original PaddleOCR Lite deployment documentation](https://github.com/PaddlePaddle/PaddleOCR/blob/0a005fb1800795ffa62feb186a96c5ba1a58bfac/deploy/lite/readme_en.md);
- [original Android demo build configuration](https://github.com/PaddlePaddle/PaddleOCR/blob/54d562bfd91e714588a15b4adf7c9b1ef632115b/deploy/android_demo/app/build.gradle); and
- [current PaddleOCR Android Lite layout](https://github.com/PaddlePaddle/PaddleOCR/blob/main/deploy/lite/readme.md).

The examined firmware contains the Java provider and model resources but not the
required `libPaddleOCR.so` or its JNI symbols in any extracted partition. BOOX also
does not classify this provider as offline, so its service path passes through
quota/permission handling.

Consequently:

- the firmware ML Kit provider is the dependable offline OCR path;
- the firmware Paddle path is incomplete unless another package supplies its missing
  native runtime; and
- the Paddle assets are compatibility evidence, not a runtime the SDK can call.

The implementation plan reconstructs this provider using the official Paddle Lite
2.10 Java/JNI distribution and caller-provisioned licensed packs. It does not copy the
firmware models.

## MyScript iink

MyScript is the richer document-ink engine.

Observed behavior includes:

- on-screen editor, off-screen editor, and contextless modes;
- text, raw content, gesture, diagram, shape, math, and structured document parts;
- incremental recognition and import, erase, replace, undo, and redo;
- underline, scratch-out, strike-through, surround, insert, and join gestures;
- structured JIIX containing words, characters, item IDs, strokes, boxes, and document
  structure; and
- approximately 60.8 MiB of MyScript/iink native libraries.

System resources include English, Simplified Chinese, analyzer, math, multilingual,
and shape resources. Another 59 language configurations are present, with additional
resources available through BOOX-managed download paths.

The firmware performs an initial BOOX server entitlement check using device identity
data and then caches verification. This entitlement behavior is evidence about the
firmware integration, not a contract for the public platform. The planned provider
uses the officially licensed iink runtime and host-provisioned resources without
depending on BOOX entitlement or device identifiers.

## Built-in / GoogleML handwriting

The lighter built-in provider wraps ML Kit Digital Ink and BOOX postprocessing.

Observed behavior includes:

- `ML_COMMON`, which recognizes one content type;
- `ML_MIX`, which can run text, gesture, and shape recognition over the same ink;
- mappings for 70 text locales;
- gesture models specifically mapped for English and Simplified Chinese;
- candidate text strings rather than a persistent structured ink document;
- text models downloaded with ML Kit `RemoteModelManager`;
- only the approximately 1.7 MiB uncompressed `autodraw` shape model pre-seeded; and
- BOOX geometric verification/ranking over autodraw candidates for rectangles,
  triangles, polygons, arrows, ellipses, and related forms.

This path does not use the MyScript certificate/license route. The implementation plan
uses the supported ML Kit Digital Ink API and a clean-room implementation of the
observed BOOX ranking behavior. It does not call a BOOX Binder service.

## SODA speech path

The investigated Google Speech Services package contains on-device SODA, hybrid, and
network recognizer paths. Offline language packs are installed separately. Package
presence alone therefore does not establish that a usable on-device model is ready.

The public platform uses supported Android APIs to:

- check on-device recognition availability;
- create only an on-device recognizer;
- resolve supported languages and model readiness;
- trigger explicit preparation through the optional SODA preparation artifact where
  the API level permits it; and
- avoid all hybrid or network fallback.

On-device microphone recognition is gated at API 31. Caller-provided audio is
capability-probed and uses the API 33 audio-source contract for the bundled provider.
See Android's
[`SpeechRecognizer`](https://developer.android.com/reference/android/speech/SpeechRecognizer)
and
[`RecognizerIntent`](https://developer.android.com/reference/android/speech/RecognizerIntent)
references.

## Design implications

The evidence fixes these boundaries:

1. actual engine identities replace ambiguous firmware labels;
2. the four HWR/OCR paths remain separate providers rather than two overloaded modes;
3. MyScript owns structured/editable ink; Digital Ink owns lighter candidate-based
   recognition;
4. ML Kit is the primary validated OCR Auto route;
5. legacy Paddle and PP-OCRv3 are explicit, distinct providers;
6. speech is its own pipeline and concurrency lane;
7. BOOX Binder recognition is excluded; and
8. firmware-extracted assets remain evidence only.

## Evidence limits

These findings come from static firmware/package analysis and identified upstream
artifacts. They do not by themselves prove:

- ordinary-app access to privileged firmware services;
- redistribution rights for any extracted asset;
- output parity of a replacement provider;
- readiness of downloaded Digital Ink or SODA models;
- correctness across all declared language configurations; or
- stability across another BOOX firmware release.

The implementation must establish those claims through supported dependencies,
caller-provisioned resources, capability probes, differential corpora, and the device
matrix in the authoritative plan.

<!-- markdownlint-enable MD013 MD060 -->
