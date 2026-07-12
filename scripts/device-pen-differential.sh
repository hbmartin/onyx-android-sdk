#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
REFERENCE_SO="${1:?usage: device-pen-differential.sh <reference-libneo_pen.so> [device-serial]}"
SERIAL="${2:-${ANDROID_SERIAL:-}}"
SDK_ROOT="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-$HOME/Library/Android/sdk}}"
ADB="$SDK_ROOT/platform-tools/adb"
EXPECTED_SHA256="4e902c1485ea660c5deb6a52693f95d113f862f448b57b6374c70404ba695f5e"
TMP="$(mktemp -d)"
TEST_APK="$ROOT/onyxsdk-pen/build/outputs/apk/androidTest/debug/onyxsdk-pen-debug-androidTest.apk"
TEST_PACKAGE="com.onyx.android.sdk.pen.test"
RUNNER="$TEST_PACKAGE/androidx.test.runner.AndroidJUnitRunner"
SNAPSHOT_TEST="com.onyx.android.sdk.pen.NeoPenNativeDeviceTest#writeBehaviorSnapshot"

fail() {
  echo "pen differential failed: $*" >&2
  exit 1
}

test -f "$REFERENCE_SO" || fail "reference library does not exist: $REFERENCE_SO"
test -x "$ADB" || fail "adb not found: $ADB"
REFERENCE_SO="$(cd "$(dirname "$REFERENCE_SO")" && pwd)/$(basename "$REFERENCE_SO")"
actual_sha="$(shasum -a 256 "$REFERENCE_SO" | awk '{print $1}')"
test "$actual_sha" = "$EXPECTED_SHA256" \
  || fail "unexpected reference SHA-256: $actual_sha"

if [[ -z "$SERIAL" ]]; then
  devices="$("$ADB" devices | awk 'NR > 1 && $2 == "device" {print $1}')"
  set -- $devices
  test "$#" -eq 1 \
    || fail "set ANDROID_SERIAL or pass a serial when zero/multiple devices are connected"
  SERIAL="$1"
fi

restore_candidate() {
  rm -rf "$TMP"
  ANDROID_HOME="$SDK_ROOT" ANDROID_SDK_ROOT="$SDK_ROOT" \
    "$ROOT/gradlew" -p "$ROOT" :onyxsdk-pen:assembleDebug >/dev/null 2>&1 || true
}
trap restore_candidate EXIT

capture_snapshot() {
  local destination="$1"
  "$ADB" -s "$SERIAL" install -r -t "$TEST_APK" >/dev/null
  "$ADB" -s "$SERIAL" shell am instrument -w \
    -e class "$SNAPSHOT_TEST" "$RUNNER" >"$TMP/instrument.txt"
  rg -q 'OK \(1 test\)' "$TMP/instrument.txt" \
    || fail "snapshot instrumentation failed: $(tr '\n' ' ' < "$TMP/instrument.txt")"
  "$ADB" -s "$SERIAL" exec-out run-as "$TEST_PACKAGE" \
    cat files/pen-snapshot.txt >"$destination"
  test -s "$destination" || fail "device returned an empty snapshot"
}

echo "Building and capturing the supplied reference library"
ANDROID_HOME="$SDK_ROOT" ANDROID_SDK_ROOT="$SDK_ROOT" \
  "$ROOT/gradlew" -p "$ROOT" :onyxsdk-pen:assembleDebugAndroidTest \
  -PpenReferenceNeoSo="$REFERENCE_SO" >/dev/null
capture_snapshot "$TMP/reference.txt"

echo "Building and capturing the Rust implementation"
ANDROID_HOME="$SDK_ROOT" ANDROID_SDK_ROOT="$SDK_ROOT" \
  "$ROOT/gradlew" -p "$ROOT" :onyxsdk-pen:assembleDebugAndroidTest >/dev/null
capture_snapshot "$TMP/candidate.txt"

python3 "$ROOT/scripts/compare-pen-snapshots.py" \
  "$TMP/reference.txt" "$TMP/candidate.txt"

echo "Running the complete candidate instrumentation suite"
ANDROID_HOME="$SDK_ROOT" ANDROID_SDK_ROOT="$SDK_ROOT" \
  "$ROOT/gradlew" -p "$ROOT" :onyxsdk-pen:connectedDebugAndroidTest >/dev/null
echo "Device pen differential and candidate instrumentation passed on $SERIAL."
