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

## Safety boundary

The harness does not use root, inject input, hook processes, write system
partitions, change lights or radios, or call reset/reboot controls. Display and
pen changes are view/app-scoped and cleanup runs after each reversible probe.
Raw result directories and all APKs are ignored by Git.
