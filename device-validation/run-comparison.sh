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
INPUT_DEVICE="/dev/input/event5"

usage() {
  echo "usage: $0 [--serial SERIAL] [--suite automated|base|device|pen-replay|pen-live|neo-pen] [--output DIR] [--duration-ms MS]" >&2
}

while [[ $# -gt 0 ]]; do
  case "$1" in
    --serial) SERIAL="$2"; shift 2 ;;
    --suite) SUITE="$2"; shift 2 ;;
    --output) OUTPUT="$2"; shift 2 ;;
    --duration-ms) DURATION="$2"; shift 2 ;;
    --help|-h) usage; exit 0 ;;
    *) usage; exit 2 ;;
  esac
done

case "$SUITE" in automated|base|device|pen-replay|pen-live|neo-pen) ;; *) usage; exit 2 ;; esac

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

run_variant() {
  local variant="$1" apk="$2" suite="$3" destination="$4" replay="${5:-}"
  echo "Running $variant $suite on $SERIAL"
  "${ADB_CMD[@]}" install -r -t "$apk" >/dev/null
  "${ADB_CMD[@]}" shell pm clear "$PACKAGE" >/dev/null
  "${ADB_CMD[@]}" logcat -c
  "${ADB_CMD[@]}" shell input keyevent 224 >/dev/null 2>&1 || true
  "${ADB_CMD[@]}" shell wm dismiss-keyguard >/dev/null 2>&1 || true
  if [[ -n "$replay" && -s "$replay" ]]; then
    "${ADB_CMD[@]}" push "$replay" /data/local/tmp/onyx-validation-replay.jsonl >/dev/null
    "${ADB_CMD[@]}" shell run-as "$PACKAGE" cp /data/local/tmp/onyx-validation-replay.jsonl files/replay.jsonl
  fi
  local event_pid=""
  if [[ "$suite" == "pen-live" ]]; then
    "${ADB_CMD[@]}" shell getevent -lt "$INPUT_DEVICE" > "$destination-getevent.txt" 2>&1 &
    event_pid=$!
  fi
  "${ADB_CMD[@]}" shell am start -W -n "$PACKAGE/.ValidationActivity" \
    --es suite "$suite" --el durationMs "$DURATION" > "$destination-am-start.txt"
  local timeout=90
  [[ "$suite" == "pen-live" ]] && timeout=$((DURATION / 1000 + 45))
  if ! wait_done "$timeout"; then
    echo "$variant $suite timed out" >&2
    "${ADB_CMD[@]}" logcat -d > "$destination-logcat.txt"
    [[ -z "$event_pid" ]] || kill "$event_pid" 2>/dev/null || true
    return 1
  fi
  "${ADB_CMD[@]}" exec-out run-as "$PACKAGE" cat files/results.jsonl > "$destination.jsonl"
  "${ADB_CMD[@]}" logcat -d > "$destination-logcat.txt"
  "${ADB_CMD[@]}" shell am force-stop "$PACKAGE"
  if [[ -n "$event_pid" ]]; then
    kill "$event_pid" 2>/dev/null || true
    wait "$event_pid" 2>/dev/null || true
  fi
}

run_variant reference "$REFERENCE_APK" "$SUITE" "$OUTPUT/reference"
REPLAY=""
if [[ "$SUITE" == "pen-live" ]]; then
  REPLAY="$OUTPUT/reference-replay-input.jsonl"
  python3 "$ROOT/extract_replay.py" "$OUTPUT/reference.jsonl" "$REPLAY" > "$OUTPUT/replay-event-count.txt"
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
  {
    echo
    echo "## Neo-pen native differential"
    echo
    echo 'Classification: `match` — all nine reference/candidate behavior snapshots and candidate instrumentation passed.'
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
