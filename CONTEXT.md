# Onyx Recognition

This glossary defines the shared language for local handwriting, optical character,
and speech recognition in the Onyx SDK.

## Platform

**Recognition Platform**:
The unified product surface through which applications use local handwriting, optical
character, and speech recognition.
_Avoid_: Recognition module, HWR module, OCR wrapper

**Recognition Pipeline**:
One input family and its recognition lifecycle: handwriting, optical character,
or speech recognition.
_Avoid_: Mode, backend type

**Handwriting Recognition (HWR)**:
Recognition of ordered vector pen strokes as text, math, shapes, diagrams, gestures,
or mixed ink content.
_Avoid_: OCR, handwriting OCR

**Optical Character Recognition (OCR)**:
Recognition of text and layout from raster pixels or encoded page images.
_Avoid_: HWR, image HWR

**Speech Recognition**:
Recognition of microphone or supplied audio as a time-based transcript.
_Avoid_: Voice activation, hotword detection

## Providers and capabilities

**Recognition Provider**:
A named engine integration that performs exactly one recognition pipeline.
_Avoid_: Backend, firmware mode

**Provider ID**:
The stable, generation-bearing identity of a recognition provider used in selection,
provenance, and revisions.
_Avoid_: Firmware label, display name

**Firmware Alias**:
A BOOX-facing label that names or groups an underlying recognition engine but is
not the engine's stable identity.
_Avoid_: Provider ID

**Recognition Capability**:
A provider's ability to recognize a particular content, language, script, input
form, or audio format with a specific resource revision.
_Avoid_: Feature flag, provider availability

**Capability Validation**:
The evidence state that distinguishes a tested capability from an installed but
untested capability or an unavailable one.
_Avoid_: Model presence, support guess

**Recognition Preparation**:
An explicit operation that makes a system-managed recognition model ready without
performing recognition.
_Avoid_: Recognition, implicit download

## Resources and identity

**Resource Pack**:
A versioned collection of models, dictionaries, or recognition resources for one
provider capability.
_Avoid_: Model folder, asset bundle

**Prepared Resource**:
A verified resource-pack revision retained for later activation.
_Avoid_: Installed model

**Active Resource**:
The resource-pack revision selected for new recognition sessions in one resource
slot.
_Avoid_: Latest model

**Recognition Revision**:
The stable identity of every output-affecting provider, resource, processing, routing,
transformation, and compute choice for a recognition attempt.
_Avoid_: App version, provider version

**Recognition Provenance**:
The descriptive account of which provider, resources, capabilities, and processing
choices produced a recognition attempt.
_Avoid_: Revision, diagnostic log

## Inputs and results

**Recognition Session**:
A bounded relationship that pins one provider configuration and its resource revision
across one or more recognition attempts.
_Avoid_: Editor, accumulated document

**Generic Recognition Document**:
The provider-neutral spatial hierarchy shared by handwriting and optical character
results.
_Avoid_: JIIX, transcript

**Speech Transcript**:
The provider-neutral time-based result of speech recognition, including alternatives
and optional segments or tokens.
_Avoid_: Generic Recognition Document

**JIIX Document**:
A lossless validated MyScript interchange document kept separate from the generic
recognition document.
_Avoid_: Generic JSON result

**No Match**:
A completed recognition attempt in which the engine could execute but produced no
acceptable recognized content.
_Avoid_: Empty success, provider unavailable

**Raw Diagnostic Payload**:
Recognition input or output content made available to a host diagnostic sink under
an explicit process-wide policy.
_Avoid_: Metadata, ordinary log
