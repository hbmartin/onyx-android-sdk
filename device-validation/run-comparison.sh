#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")" && pwd)"
RECOVERY_ROOT="$(cd "$ROOT/.." && pwd)"
ARTIFACTS_ROOT="$(cd "$RECOVERY_ROOT/.." && pwd)"
PACKAGE="com.onyx.recovery.validation"
SERIAL=""
SUITE="automated"
OUTPUT=""
DURATION=30000
INPUT_DEVICE="auto"

usage() {
  echo "usage: $0 [--serial SERIAL] [--suite automated|base|device|pen-replay|pen-live|neo-pen|guided] [--output DIR] [--duration-ms MS] [--input-device /dev/input/eventN]" >&2
}

while [[ $# -gt 0 ]]; do
  case "$1" in
    --serial) SERIAL="$2"; shift 2 ;;
    --suite) SUITE="$2"; shift 2 ;;
    --output) OUTPUT="$2"; shift 2 ;;
    --duration-ms) DURATION="$2"; shift 2 ;;
    --input-device) INPUT_DEVICE="$2"; shift 2 ;;
    --help|-h) usage; exit 0 ;;
    *) usage; exit 2 ;;
  esac
done

case "$SUITE" in automated|base|device|pen-replay|pen-live|neo-pen|guided) ;; *) usage; exit 2 ;; esac

if [[ "$SUITE" == "guided" && ! -t 0 ]]; then
  echo "the guided suite prompts an operator for verdicts and needs an interactive terminal" >&2
  exit 1
fi

SDK_ROOT="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-$HOME/Library/Android/sdk}}"
ADB="$SDK_ROOT/platform-tools/adb"
test -x "$ADB" || { echo "adb not found at $ADB" >&2; exit 1; }
export ANDROID_HOME="$SDK_ROOT" ANDROID_SDK_ROOT="$SDK_ROOT"

if [[ -z "$SERIAL" ]]; then
  DEVICES=($("$ADB" devices | awk 'NR > 1 && $2 == "device" {print $1}'))
  [[ ${#DEVICES[@]} -eq 1 ]] || { echo "Pass --serial when zero or multiple devices are attached" >&2; exit 1; }
  SERIAL="${DEVICES[0]}"
fi
ADB_CMD=("$ADB" -s "$SERIAL")
"${ADB_CMD[@]}" get-state >/dev/null

# The stylus digitizer node differs across BOOX models; find the device that
# advertises a pen or stylus button instead of hardcoding an event index.
if [[ "$INPUT_DEVICE" == "auto" ]]; then
  INPUT_DEVICE="$("${ADB_CMD[@]}" shell getevent -lp 2>/dev/null | tr -d '\r' | awk '
    /^add device / { device = $NF }
    /BTN_TOOL_PEN|BTN_STYLUS/ { if (device != "") { print device; exit } }
  ')"
  if [[ -z "$INPUT_DEVICE" ]]; then
    echo "could not auto-detect a stylus input device (no node advertises BTN_TOOL_PEN or BTN_STYLUS);" >&2
    echo "pass --input-device /dev/input/eventN explicitly" >&2
    exit 1
  fi
  echo "Detected stylus input device: $INPUT_DEVICE"
fi

if [[ -z "$OUTPUT" ]]; then
  OUTPUT="$ROOT/results/$(date +%Y%m%d-%H%M%S)-$SUITE"
fi
mkdir -p "$OUTPUT/device"

echo "Building recovered SDK and validation APKs"
(cd "$RECOVERY_ROOT" && ./gradlew assembleRecovered \
  :onyxsdk-base:support:onyxsdk-baselite:assembleRelease \
  :onyxsdk-base:support:onyxsdk-commons-io:assembleRelease)
(cd "$ROOT" && ../gradlew -p . -POnyxArtifactsRoot="$ARTIFACTS_ROOT" \
  :app:assembleReferenceDebug :app:assembleRecoveredDebug)

python3 "$ROOT/api_surface.py" --artifacts-root "$ARTIFACTS_ROOT" \
  --recovery-root "$RECOVERY_ROOT" --output "$OUTPUT/api-surface.json"

"${ADB_CMD[@]}" shell getprop > "$OUTPUT/device/getprop.txt"
"${ADB_CMD[@]}" shell dumpsys input > "$OUTPUT/device/dumpsys-input.txt"
"${ADB_CMD[@]}" shell getevent -lp "$INPUT_DEVICE" > "$OUTPUT/device/getevent-capabilities.txt" 2>&1 || true
"${ADB_CMD[@]}" shell wm size > "$OUTPUT/device/display.txt"
"${ADB_CMD[@]}" shell wm density >> "$OUTPUT/device/display.txt"
"${ADB_CMD[@]}" shell id > "$OUTPUT/device/adb-shell-id.txt"

REFERENCE_APK="$ROOT/app/build/outputs/apk/reference/debug/app-reference-debug.apk"
RECOVERED_APK="$ROOT/app/build/outputs/apk/recovered/debug/app-recovered-debug.apk"

wait_done() {
  local timeout="$1"
  local elapsed=0
  while (( elapsed < timeout )); do
    if "${ADB_CMD[@]}" shell run-as "$PACKAGE" cat files/done 2>/dev/null | grep -q .; then
      return 0
    fi
    sleep 1
    elapsed=$((elapsed + 1))
  done
  return 1
}

# Audible + visible countdown so the person holding the stylus knows exactly
# when each timed capture starts.
countdown() {
  local label="$1" seconds="${2:-5}"
  echo "$label"
  for ((remaining = seconds; remaining > 0; remaining--)); do
    printf '\a  starting in %d...\n' "$remaining"
    sleep 1
  done
  printf '\a\aGO — draw now\n'
}

DEVICE_EVENT_CAPTURE=/data/local/tmp/onyx-validation-getevent.txt

# getevent runs and buffers entirely on the device; the capture file is pulled
# after the run so stopping the host process cannot truncate the stream.
start_event_capture() {
  "${ADB_CMD[@]}" shell "rm -f $DEVICE_EVENT_CAPTURE; \
    setsid getevent -lt $INPUT_DEVICE </dev/null >$DEVICE_EVENT_CAPTURE 2>&1 & echo \$!" | tr -d '\r'
}

stop_event_capture() {
  local device_pid="$1" destination="$2"
  "${ADB_CMD[@]}" shell "kill $device_pid" 2>/dev/null || true
  "${ADB_CMD[@]}" pull "$DEVICE_EVENT_CAPTURE" "$destination" >/dev/null
  "${ADB_CMD[@]}" shell "rm -f $DEVICE_EVENT_CAPTURE"
}

run_variant() {
  local variant="$1" apk="$2" suite="$3" destination="$4" replay="${5:-}" scenario="${6:-}" duration="${7:-$DURATION}"
  echo "Running $variant $suite${scenario:+ ($scenario)} on $SERIAL"
  "${ADB_CMD[@]}" install -r -t "$apk" >/dev/null
  "${ADB_CMD[@]}" shell pm clear "$PACKAGE" >/dev/null
  "${ADB_CMD[@]}" logcat -c
  "${ADB_CMD[@]}" shell input keyevent 224 >/dev/null 2>&1 || true
  "${ADB_CMD[@]}" shell wm dismiss-keyguard >/dev/null 2>&1 || true
  if [[ -n "$replay" && -s "$replay" ]]; then
    "${ADB_CMD[@]}" push "$replay" /data/local/tmp/onyx-validation-replay.jsonl >/dev/null
    # pm clear leaves an empty data dir on some firmware; do not assume files/ exists.
    "${ADB_CMD[@]}" shell "run-as $PACKAGE sh -c 'mkdir -p files && cp /data/local/tmp/onyx-validation-replay.jsonl files/replay.jsonl'"
  fi
  local event_pid=""
  if [[ "$suite" == "pen-live" ]]; then
    event_pid="$(start_event_capture)"
    countdown "Get ready to draw ${scenario:-the guided strokes} on the $variant build ($((duration / 1000))s capture)"
  fi
  local extras=(--es suite "$suite" --el durationMs "$duration")
  [[ -n "$scenario" ]] && extras+=(--es scenario "$scenario")
  "${ADB_CMD[@]}" shell am start -W -n "$PACKAGE/.ValidationActivity" \
    "${extras[@]}" > "$destination-am-start.txt"
  local timeout=90
  [[ "$suite" == "pen-live" ]] && timeout=$((duration / 1000 + 45))
  if ! wait_done "$timeout"; then
    echo "$variant $suite timed out" >&2
    "${ADB_CMD[@]}" logcat -d > "$destination-logcat.txt"
    [[ -z "$event_pid" ]] || stop_event_capture "$event_pid" "$destination-getevent.txt"
    return 1
  fi
  "${ADB_CMD[@]}" exec-out run-as "$PACKAGE" cat files/results.jsonl > "$destination.jsonl"
  "${ADB_CMD[@]}" logcat -d > "$destination-logcat.txt"
  "${ADB_CMD[@]}" shell am force-stop "$PACKAGE"
  if [[ -n "$event_pid" ]]; then
    stop_event_capture "$event_pid" "$destination-getevent.txt"
    if ! grep -q "EV_" "$destination-getevent.txt"; then
      echo "ERROR: no stylus events were recorded from $INPUT_DEVICE during the $variant pen-live run." >&2
      echo "Check that the stylus touched the panel during the capture window (raw log: $destination-getevent.txt)." >&2
      return 1
    fi
  fi
}

# Guided operator protocol: one scenario at a time, reference then recovered,
# automated gates around a human same/different verdict, and a combined
# exact-replay parity phase at the end. See docs/GUIDED_VALIDATION.md.
if [[ "$SUITE" == "guided" ]]; then
  capture_with_retry() {
    local variant="$1" apk="$2" scenario="$3" destination="$4" duration="$5"
    while true; do
      if run_variant "$variant" "$apk" pen-live "$destination" "" "$scenario" "$duration" \
          && python3 "$ROOT/guided_scenarios.py" check "$scenario" "$destination.jsonl"; then
        return 0
      fi
      printf '%s capture failed its gates. Retry it? [Y/n] ' "$variant"
      read -r reply
      [[ "$reply" == [nN]* ]] && return 1
    done
  }

  : > "$OUTPUT/operator-verdicts.jsonl"
  while IFS= read -r SCENARIO_ID; do
    SCENARIO_SECONDS="$(python3 "$ROOT/guided_scenarios.py" seconds "$SCENARIO_ID")"
    SCENARIO_MS=$((SCENARIO_SECONDS * 1000))
    echo
    python3 "$ROOT/guided_scenarios.py" brief "$SCENARIO_ID"
    if python3 "$ROOT/guided_scenarios.py" skippable "$SCENARIO_ID"; then
      printf 'This scenario needs specific stylus hardware. Run it? [Y/s to skip] '
      read -r reply
      if [[ "$reply" == [sS]* ]]; then
        printf 'Why is it skipped (e.g. "stylus has no rear eraser")? '
        read -r notes
        python3 "$ROOT/guided_scenarios.py" verdict --output-dir "$OUTPUT" \
          --scenario "$SCENARIO_ID" --verdict skipped --notes "$notes"
        continue
      fi
    else
      printf 'Press Enter when ready for the REFERENCE capture. '
      read -r _
    fi
    capture_with_retry reference "$REFERENCE_APK" "$SCENARIO_ID" "$OUTPUT/$SCENARIO_ID-reference" "$SCENARIO_MS" \
      || { echo "aborting: $SCENARIO_ID reference capture never passed its gates" >&2; exit 1; }
    printf 'Now repeat the SAME drawing on the RECOVERED build. Press Enter when ready. '
    read -r _
    capture_with_retry recovered "$RECOVERED_APK" "$SCENARIO_ID" "$OUTPUT/$SCENARIO_ID-recovered" "$SCENARIO_MS" \
      || { echo "aborting: $SCENARIO_ID recovered capture never passed its gates" >&2; exit 1; }
    mkdir -p "$OUTPUT/$SCENARIO_ID-comparison"
    if ! python3 "$ROOT/compare_results.py" "$OUTPUT/$SCENARIO_ID-reference.jsonl" \
        "$OUTPUT/$SCENARIO_ID-recovered.jsonl" \
        --output "$OUTPUT/$SCENARIO_ID-comparison" --live --require-clean; then
      echo "aborting: the $SCENARIO_ID captures diverged on operator-independent records" >&2
      exit 1
    fi
    printf 'Did the recovered build look and behave the same as the reference? [y/n] '
    read -r reply
    notes=""
    if [[ "$reply" == [yY]* ]]; then
      verdict=same
    else
      verdict=different
      printf 'Describe the difference: '
      read -r notes
    fi
    python3 "$ROOT/guided_scenarios.py" verdict --output-dir "$OUTPUT" \
      --scenario "$SCENARIO_ID" --verdict "$verdict" --notes "$notes"
  done < <(python3 "$ROOT/guided_scenarios.py" list)

  # Exact deterministic replay parity: every reference capture, concatenated
  # and replayed through both builds, must compare clean with no tolerance
  # for one-sided records.
  # Named so it can never match the *-reference.jsonl glob on a re-run.
  COMBINED="$OUTPUT/replay-source-combined.jsonl"
  cat "$OUTPUT"/*-reference.jsonl > "$COMBINED"
  REPLAY="$OUTPUT/reference-replay-input.jsonl"
  python3 "$ROOT/extract_replay.py" "$COMBINED" "$REPLAY" > "$OUTPUT/replay-event-count.txt"
  REPLAY_COUNT="$(cat "$OUTPUT/replay-event-count.txt")"
  if [[ "$REPLAY_COUNT" == "0" ]]; then
    echo "ERROR: no raw pen events across all reference captures; nothing to replay." >&2
    exit 1
  fi
  echo "Replaying $REPLAY_COUNT combined reference events through both builds"
  run_variant reference "$REFERENCE_APK" pen-replay "$OUTPUT/reference-replay" "$REPLAY"
  run_variant recovered "$RECOVERED_APK" pen-replay "$OUTPUT/recovered-replay" "$REPLAY"
  mkdir -p "$OUTPUT/replay-comparison"
  python3 "$ROOT/compare_results.py" "$OUTPUT/reference-replay.jsonl" "$OUTPUT/recovered-replay.jsonl" \
    --output "$OUTPUT/replay-comparison" --require-clean

  REPORT_STATUS=0
  python3 "$ROOT/guided_scenarios.py" report --output-dir "$OUTPUT" || REPORT_STATUS=$?
  echo "Report: $OUTPUT/report.md"
  exit "$REPORT_STATUS"
fi

run_variant reference "$REFERENCE_APK" "$SUITE" "$OUTPUT/reference"
REPLAY=""
if [[ "$SUITE" == "pen-live" ]]; then
  REPLAY="$OUTPUT/reference-replay-input.jsonl"
  python3 "$ROOT/extract_replay.py" "$OUTPUT/reference.jsonl" "$REPLAY" > "$OUTPUT/replay-event-count.txt"
  REPLAY_COUNT="$(cat "$OUTPUT/replay-event-count.txt")"
  if [[ "$REPLAY_COUNT" == "0" ]]; then
    echo "ERROR: the reference run captured zero raw JNI pen events; aborting before the recovered run." >&2
    echo "Every later phase would compare empty streams. Re-run and draw during the capture window." >&2
    exit 1
  fi
  echo "Reference run captured $REPLAY_COUNT raw pen events."
fi
run_variant recovered "$RECOVERED_APK" "$SUITE" "$OUTPUT/recovered"

if [[ "$SUITE" == "pen-live" ]]; then
  python3 "$ROOT/compare_results.py" "$OUTPUT/reference.jsonl" "$OUTPUT/recovered.jsonl" \
    --output "$OUTPUT" --live --api-surface "$OUTPUT/api-surface.json"
else
  python3 "$ROOT/compare_results.py" "$OUTPUT/reference.jsonl" "$OUTPUT/recovered.jsonl" \
    --output "$OUTPUT" --api-surface "$OUTPUT/api-surface.json"
fi

if [[ "$SUITE" == "automated" || "$SUITE" == "neo-pen" ]]; then
  echo "Running nine-type native neo-pen differential"
  "$RECOVERY_ROOT/scripts/device-pen-differential.sh" "$RECOVERY_ROOT/libneo_pen.so" "$SERIAL" \
    | tee "$OUTPUT/neo-pen-differential.txt"
  # Derive the report section from what the differential actually printed
  # instead of asserting specifics its exit status alone cannot support.
  SNAPSHOT_LINE="$(grep -m1 'snapshots' "$OUTPUT/neo-pen-differential.txt" || true)"
  RESULT_LINE="$(grep -m1 'passed' "$OUTPUT/neo-pen-differential.txt" || true)"
  if [[ -z "$SNAPSHOT_LINE" || -z "$RESULT_LINE" ]]; then
    echo "neo-pen differential exited zero but its summary lines are missing from the output" >&2
    exit 1
  fi
  {
    echo
    echo "## Neo-pen native differential"
    echo
    echo "Classification: \`match\` — $SNAPSHOT_LINE"
    echo
    echo "$RESULT_LINE"
  } >> "$OUTPUT/report.md"
fi

if [[ "$SUITE" == "pen-live" && -s "$REPLAY" ]]; then
  run_variant reference "$REFERENCE_APK" pen-replay "$OUTPUT/reference-replay" "$REPLAY"
  run_variant recovered "$RECOVERED_APK" pen-replay "$OUTPUT/recovered-replay" "$REPLAY"
  mkdir -p "$OUTPUT/replay-comparison"
  python3 "$ROOT/compare_results.py" "$OUTPUT/reference-replay.jsonl" "$OUTPUT/recovered-replay.jsonl" \
    --output "$OUTPUT/replay-comparison"
fi

echo "Report: $OUTPUT/report.md"
