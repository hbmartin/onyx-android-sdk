# Guided pen validation — operator instructions

The automated suites prove that API calls complete identically on both builds;
they cannot prove the physical waveform looks right on the e-ink panel. This
protocol puts a human in the loop for exactly that judgment, and wraps every
human step in automated gates so a "looks fine" can never mask an empty or
malformed capture.

## What you need

- The BOOX device connected over adb (USB or Wi-Fi), screen unlocked.
- Its stylus. One scenario (rear eraser) is skippable if your stylus has no
  rear eraser — the run records why instead of failing.
- 25–35 minutes. The run is interactive from start to finish.
- A terminal you can hear: an audible countdown marks the start of every
  capture window.

## Running it

```sh
cd device-validation
./run-comparison.sh --suite guided
```

Useful flags: `--serial` when several devices are attached, `--input-device`
if stylus auto-detection picks the wrong node (it looks for the evdev node
advertising `BTN_TOOL_PEN`).

## What happens, scenario by scenario

The run walks 12 scenarios in a fixed order: DU / GU / GC refresh modes,
hardware scribble mode, region-limited input, ghosting + GC cleanup, light vs
heavy pressure, tilt, side-button erase, rear eraser, pause/resume, and a
mid-capture reader restart. For each one:

1. The terminal prints the scenario's instructions and what to look for.
   Read them before pressing Enter — some scenarios script mid-capture
   actions (a self-pause, a full restart) that you should keep drawing
   through.
2. **Reference capture.** After the countdown, draw on the panel following
   the instructions for the whole window.
3. **Recovered capture.** Repeat the *same* drawing on the recovered build.
   The two captures never need to match stroke-for-stroke — you are not a
   robot — but performing the same gestures makes the visual comparison
   meaningful.
4. **Automated gates** run on each capture before you are asked anything:
   the raw evdev trace must be non-empty, the JNI trace must be non-empty
   with a valid state grammar, and scenario-specific checks must pass
   (pressure spread for the pressure scenario, tilt range for tilt, erase
   events for the two eraser scenarios, scheduled-action counts for
   pause/resume and restart). A failed gate offers a retry of that capture —
   it never silently continues.
5. **Your verdict.** You are asked whether the recovered build looked and
   behaved the same: ink latency, refresh flashing, ghosting, region
   clipping, eraser behavior. Answer honestly; a `different` verdict with a
   note is far more useful than a polite `same`. Verdicts land in
   `operator-verdicts.jsonl`.

## The replay parity phase

After the last scenario, every reference capture is concatenated and replayed
through both builds with no human involved. This is where exactness is
demanded: the two replay result streams must match record-for-record
(`--require-clean`), and each build's replay must be internally healthy —
every delivered event produced its JNI callback, semantic callbacks arrived,
and per-event delivery latency stayed inside bounds. Live captures tolerate
human variability; replay tolerates nothing.

## Reading the outcome

The final `report.md` lists, per scenario: your verdict, the comparison
counts, and any skip reasons; then the replay parity result; then a single
PASSED/FAILED outcome. The run exits non-zero on any `different` verdict or
parity failure.

If the run passes and you want to keep it as the canonical evidence, promote
it to a git-tracked fixture:

```sh
python3 promote_fixture.py results/<run-directory> --name pen-guided-<device>-<android>
```

## Practical tips

- Draw with intent: distinct strokes with pen lifts between them exercise
  the state machine better than one continuous scrawl.
- For the pressure scenario, genuinely feather the light strokes — the gate
  requires a spread of at least 1500 pressure units (of 4095).
- For tilt, roll the pen like you are shading with a pencil; the gate wants
  at least 10 units of tilt range.
- If a capture starts before you were ready, let it fail its gates (or draw
  nothing) and take the retry.
- Do not touch the on-screen mode buttons during guided runs; the scenarios
  configure everything themselves. (If you do, those records are excluded
  from comparison, but the visual result may confuse your own verdict.)
