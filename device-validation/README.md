# Non-root BOOX SDK validation

This standalone build compares supplied Onyx SDK artifacts with the source-built
recovery without adding reference binaries to the production build graph. It
uses the same debug application id for both variants and installs them
sequentially so package-scoped firmware behavior is comparable.

## Automated comparison

Connect and authorize the device, then run:

```bash
./run-comparison.sh --serial 2a9d2c67 --suite automated
```

Use `--output DIR` to select a result directory. The runner records device
inventory, an API-surface report, both JSONL result streams, `comparison.json`,
and `report.md`. Original SDK files are read from the workspace parent by
default; override that with the `OnyxArtifactsRoot` Gradle property.
The automated suite also invokes the existing nine-pen-type native differential
against the hash-pinned, untracked `libneo_pen.so` reference.

For a classified JVM comparison that separates binary-breaking differences
from source-level generics, kotlin.Metadata, and harmless compiler artifacts,
run the descriptor-level audit (after `./gradlew assembleRecovered`):

```bash
python3 classify_api_differences.py --module base \
  --artifacts-root /path/to/onyx-artifacts --recovery-root .. \
  --accepted-residuals accepted-residuals/base.json \
  --output base-classified.json --fail-on binary_breaking
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

The full surface/native audit expects the existing untracked analysis inputs
`../onyxsdk-pen-native-classes.jar` and `../libneo_pen.so`. They remain external
to Gradle production dependencies and are never copied into source control or
the recovered release artifacts.

## Guided pen comparison

```bash
./run-comparison.sh --serial 2a9d2c67 --suite pen-live --duration-ms 30000
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
./run-comparison.sh --suite guided
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
python3 promote_fixture.py results/<run-dir> --name <fixture-name>
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
python3 -m unittest discover tests
```

covers the comparison taxonomy (`compare_results.py`), the guided-scenario
gates, and fixture reproduction — no device required.

## Safety boundary

The harness does not use root, inject input, hook processes, write system
partitions, change lights or radios, or call reset/reboot controls. Display and
pen changes are view/app-scoped and cleanup runs after each reversible probe.
Raw result directories and all APKs are ignored by Git.
