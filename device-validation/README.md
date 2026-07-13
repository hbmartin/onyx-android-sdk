# Non-root BOOX SDK validation

This standalone build compares supplied Onyx SDK artifacts with the source-built
recovery without adding reference binaries to the production build graph. It
uses the same debug application id for both variants and installs them
sequentially so package-scoped firmware behavior is comparable.

## Automated comparison

From the repository root, connect and authorize the device, then run:

```bash
device-validation/run-comparison.sh --artifacts-root /path/to/reference-artifacts \
  --neo-pen-reference /path/to/reference/neo-pen-library \
  --serial 2a9d2c67 --suite automated
```

Use `--output DIR` to select a result directory. The runner records device
inventory, an API-surface report, both JSONL result streams, `comparison.json`,
and `report.md`. Original SDK files are read only from the required,
explicitly supplied artifacts directory.
The automated suite also invokes the existing nine-pen-type native differential
against the hash-pinned, untracked `libneo_pen.so` reference.

## MMKV file compatibility

The repository includes an MMKV data/CRC fixture written by
`com.tencent:mmkv:1.0.19` on an ONYX NoteAir4C. The fixture contains string,
integer, boolean, float, and long values plus present and removed keys; its
manifest records the device provenance, expected values, byte sizes, and
SHA-256 checksums. It contains no legacy AAR or SDK code.

Run the compatibility gate after changing MMKV with:

```bash
device-validation/run-comparison.sh --artifacts-root /path/to/reference-artifacts \
  --serial DEVICE_SERIAL --suite mmkv-compat
```

Each variant stages a fresh copy of the legacy files, opens them through
`MMKVBuilder`, verifies every legacy value and default, and then exercises
1.3.14 writes and removal. The runner requires the reference and recovered
streams to compare cleanly. Regenerating the legacy fixture requires checking
out a revision that still resolves 1.0.19 and launching `mmkv-compat` with the
intent extra `--es mmkvMode write-fixture` before pulling the two files from
`files/mmkv/`.

For a classified JVM comparison that separates binary-breaking differences
from source-level generics, kotlin.Metadata, and harmless compiler artifacts,
run the descriptor-level audit (after `./gradlew assembleRecovered`):

```bash
python3 device-validation/classify_api_differences.py --module base \
  --artifacts-root /path/to/reference-artifacts --recovery-root . \
  --accepted-residuals device-validation/accepted-residuals/base.json \
  --output device-validation/base-classified.json --fail-on binary_breaking
```

It reads `javap -v` structures per class (including nested classes), compares
member (name, descriptor) sets, access flags, generic `Signature` attributes,
and the `kotlin.Metadata` annotation independently, and gives each class the
most severe of: `binary_breaking`, `extra_public_surface`, `source_generic`,
`kotlin_metadata`, `annotation_only`, `compiler_artifact`. The repaired base
surface is additionally pinned by the in-tree unit test
`RecoveredApiSurfaceRegressionTest`, which runs without any reference JAR.
The accepted-residual file names each javac-inexpressible difference exactly;
stale entries or any new unaccepted finding fail the audit.
It also records deliberately accepted removals listed in
[`docs/API_INCOMPATIBILITIES.md`](../docs/API_INCOMPATIBILITIES.md), including
the removed Retrofit-backed `OnyxOTAService.firmwareUpdate(String)` method.
`--fail-on` gates at the named severity and everything more severe, so audit
the pen module with `--fail-on extra_public_surface`: its accepted file lists
two intentional extra public classes, and under `--fail-on binary_breaking`
an unexpected new public class would never fail the gate.

The full surface/native audit expects the native API JAR under the explicit
artifacts root and the neo-pen reference passed with `--neo-pen-reference`.
They remain external to Gradle production dependencies and are never copied
into source control or the recovered release artifacts.

## Guided pen comparison

```bash
device-validation/run-comparison.sh --artifacts-root /path/to/reference-artifacts \
  --serial 2a9d2c67 --suite pen-live --duration-ms 30000
```

During each capture, draw light and heavy strokes in the guide, use tilt, the
side button, and the rear eraser if present. The app offers reversible limit,
exclude, single-region, and pause controls. The runner concurrently captures
the readable evdev stream, then replays the reference JNI callbacks through
both Java implementations for deterministic comparison.

The runner beeps and counts down before each capture window so the reference
and recovered runs start deliberately. The evdev stream is captured to a
device-side file under `/data/local/tmp` and pulled afterwards, so a stopped
host process cannot truncate it. A pen-live run aborts immediately — before
the recovered variant runs — if the reference capture recorded zero stylus
events, and each variant's capture fails the run if its own evdev log stayed
empty.

The stylus evdev node is auto-detected (the device advertising
`BTN_TOOL_PEN`); override with `--input-device /dev/input/eventN` if a device
exposes several pen nodes.

## Guided operator protocol

```bash
device-validation/run-comparison.sh --artifacts-root /path/to/reference-artifacts --suite guided
```

Twelve scenarios (DU/GU/GC refresh, scribble mode, region limits, ghosting
cleanup, pressure, tilt, both erasers, pause/resume, restart), each captured
on both builds with automated gates around an operator same/different verdict,
then a combined exact-replay parity phase. Operator instructions live in
`docs/GUIDED_VALIDATION.md`; the scenario catalog and gates in
`guided_scenarios.py`.

## Replay delivery health

Replay suites record a `replay_health` observation per run: every delivered
event must produce its JNI callback (drop count zero), semantic callbacks must
arrive, and per-event delivery latency must stay inside generous bounds
(p95 ≤ 50 ms, max ≤ 250 ms). `compare_results.py` classifies an unhealthy
side as `recovery_defect` even when both sides are symmetric. Raw latency
numbers are recorded under phase `metrics`, which is never compared across
variants. Note that `onPenUpRefresh` is exercised only by live captures — in
replay mode the reader quits before the pen-up refresh timer can fire — so
that callback path is deliberately validated by the live/guided suites, not
by replay.

## Fixtures

Raw `results/` runs stay untracked. Promote a finished run into the
git-tracked `fixtures/` directory with:

```bash
python3 device-validation/promote_fixture.py device-validation/results/<run-dir> \
  --name <fixture-name>
```

The script normalizes host-volatile fields, scrubs identifying tokens
(serials, MACs) from every promoted file, skips logcat, and regenerates each
`comparison.json` from the sanitized streams. `tests/test_fixtures.py`
re-compares every fixture and fails if the pinned counts stop reproducing.
The current `automated-prerepair-noteair4c-android13` fixture pins the
pre-repair evidence run, 75 api-surface defects included — promote a fresh
run after the next hardware session for a post-repair baseline.

## Host-side tests

```bash
python3 -m unittest discover device-validation/tests
```

covers the comparison taxonomy (`compare_results.py`), the guided-scenario
gates, and fixture reproduction — no device required.

## Safety boundary

The harness does not use root, inject input, hook processes, write system
partitions, change lights or radios, or call reset/reboot controls. Display and
pen changes are view/app-scoped and cleanup runs after each reversible probe.
Raw result directories and all APKs are ignored by Git.
