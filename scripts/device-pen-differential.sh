#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
REFERENCE_LIBRARY="${1:?usage: device-pen-differential.sh <reference-libneo_pen.so> [device-serial] [notable-libneopen_jni.so]}"
SERIAL="${2:-${ANDROID_SERIAL:-}}"
NOTABLE_LIBRARY="${3:-}"
SDK_ROOT="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-}}"
if [[ -z "$SDK_ROOT" ]]; then
  echo "Set ANDROID_HOME or ANDROID_SDK_ROOT to the Android SDK directory" >&2
  exit 1
fi
ADB="$SDK_ROOT/platform-tools/adb"
EXPECTED_SHA256="4e902c1485ea660c5deb6a52693f95d113f862f448b57b6374c70404ba695f5e"
EXPECTED_NOTABLE_SHA256="a2c6910b7d3aa78e00505821113ead5876a11fe0f48f9abf004a19c5c53fc8c0"
EXPECTED_NOTABLE_RUNTIME_SHA256="f4e1e97c1943e60311e47e8b024d78f5b3b7229b3ccc65feb33af83d6025a670"
TMP="$(mktemp -d)"
TEST_APK="$ROOT/onyxsdk-pen/build/outputs/apk/androidTest/debug/onyxsdk-pen-debug-androidTest.apk"
TEST_PACKAGE="com.onyx.android.sdk.pen.test"
RUNNER="$TEST_PACKAGE/androidx.test.runner.AndroidJUnitRunner"
SNAPSHOT_TEST="com.onyx.android.sdk.pen.NeoPenNativeDeviceTest#writeBehaviorSnapshot"
NOTABLE_SNAPSHOT_TEST="com.onyx.android.sdk.pen.NotableNeoPenNativeDeviceTest#writeBehaviorSnapshot"

fail() {
  echo "pen differential failed: $*" >&2
  exit 1
}

sha256() {
  if command -v shasum >/dev/null 2>&1; then
    shasum -a 256 "$1" | awk '{print $1}'
  elif command -v sha256sum >/dev/null 2>&1; then
    sha256sum "$1" | awk '{print $1}'
  else
    fail "neither shasum nor sha256sum is available"
  fi
}

test -f "$REFERENCE_LIBRARY" || fail "reference libneo_pen.so does not exist: $REFERENCE_LIBRARY"
test -x "$ADB" || fail "adb not found: $ADB"
REFERENCE_LIBRARY="$(cd "$(dirname "$REFERENCE_LIBRARY")" && pwd)/$(basename "$REFERENCE_LIBRARY")"
actual_sha="$(sha256 "$REFERENCE_LIBRARY")"
test "$actual_sha" = "$EXPECTED_SHA256" \
  || fail "unexpected reference SHA-256: $actual_sha"
NOTABLE_GRADLE_ARG=()
if [[ -n "$NOTABLE_LIBRARY" ]]; then
  test -f "$NOTABLE_LIBRARY" \
    || fail "Notable libneopen_jni.so does not exist: $NOTABLE_LIBRARY"
  NOTABLE_LIBRARY="$(cd "$(dirname "$NOTABLE_LIBRARY")" && pwd)/$(basename "$NOTABLE_LIBRARY")"
  notable_sha="$(sha256 "$NOTABLE_LIBRARY")"
  test "$notable_sha" = "$EXPECTED_NOTABLE_SHA256" \
    || fail "unexpected Notable 0.2.3 SHA-256: $notable_sha"
  NOTABLE_RUNTIME="$(dirname "$NOTABLE_LIBRARY")/libc++_shared.so"
  test -f "$NOTABLE_RUNTIME" \
    || fail "extract Notable's libc++_shared.so beside libneopen_jni.so"
  notable_runtime_sha="$(sha256 "$NOTABLE_RUNTIME")"
  test "$notable_runtime_sha" = "$EXPECTED_NOTABLE_RUNTIME_SHA256" \
    || fail "unexpected Notable libc++_shared.so SHA-256: $notable_runtime_sha"
  NOTABLE_GRADLE_ARG=("-PpenNotableNeoSo=$NOTABLE_LIBRARY")
fi

if [[ -z "$SERIAL" ]]; then
  devices="$("$ADB" devices | awk 'NR > 1 && $2 == "device" {print $1}')"
  set -- $devices
  test "$#" -eq 1 \
    || fail "set ANDROID_SERIAL or pass a serial when zero/multiple devices are connected"
  SERIAL="$1"
fi

# The reference library is injected only into arm64-v8a; on any other ABI the
# device would load the Rust build twice and the differential would pass
# without ever executing the reference.
device_abi="$("$ADB" -s "$SERIAL" shell getprop ro.product.cpu.abi | tr -d '\r')"
test "$device_abi" = "arm64-v8a" \
  || fail "device $SERIAL is $device_abi; the reference comparison requires an arm64-v8a device"

restore_candidate() {
  local status=$?
  rm -rf "$TMP"
  local restore_log
  restore_log="$(mktemp)"
  if ANDROID_HOME="$SDK_ROOT" ANDROID_SDK_ROOT="$SDK_ROOT" \
    "$ROOT/gradlew" -p "$ROOT" :onyxsdk-pen:assembleDebug :onyxsdk-pen:assembleDebugAndroidTest \
    >"$restore_log" 2>&1; then
    rm -f "$restore_log"
  else
    echo "pen differential: candidate rebuild FAILED — build outputs may still contain the reference libneo_pen.so." >&2
    echo "re-run: ./gradlew :onyxsdk-pen:assembleDebug :onyxsdk-pen:assembleDebugAndroidTest (gradle log: $restore_log)" >&2
    exit 1
  fi
  exit "$status"
}
trap restore_candidate EXIT

capture_snapshot() {
  local destination="$1" test_name="${2:-$SNAPSHOT_TEST}" remote_name="${3:-pen-snapshot.txt}"
  "$ADB" -s "$SERIAL" install -r -t "$TEST_APK" >/dev/null
  "$ADB" -s "$SERIAL" shell am instrument -w \
    -e class "$test_name" "$RUNNER" >"$TMP/instrument.txt"
  rg -q 'OK \(1 test\)' "$TMP/instrument.txt" \
    || fail "snapshot instrumentation failed: $(tr '\n' ' ' < "$TMP/instrument.txt")"
  "$ADB" -s "$SERIAL" exec-out run-as "$TEST_PACKAGE" \
    cat "files/$remote_name" >"$destination"
  test -s "$destination" || fail "device returned an empty snapshot"
}

echo "Building and capturing the supplied reference libneo_pen.so"
ANDROID_HOME="$SDK_ROOT" ANDROID_SDK_ROOT="$SDK_ROOT" \
  "$ROOT/gradlew" -p "$ROOT" :onyxsdk-pen:assembleDebugAndroidTest \
  -PpenReferenceNeoSo="$REFERENCE_LIBRARY" \
  ${NOTABLE_GRADLE_ARG[@]+"${NOTABLE_GRADLE_ARG[@]}"} >/dev/null
capture_snapshot "$TMP/reference.txt"
if [[ -n "$NOTABLE_LIBRARY" ]]; then
  echo "Capturing the hash-pinned Notable 0.2.3 libneopen_jni.so"
  capture_snapshot "$TMP/notable.txt" "$NOTABLE_SNAPSHOT_TEST" "notable-pen-snapshot.txt"
fi

echo "Building and capturing the Rust implementation"
ANDROID_HOME="$SDK_ROOT" ANDROID_SDK_ROOT="$SDK_ROOT" \
  "$ROOT/gradlew" -p "$ROOT" :onyxsdk-pen:assembleDebugAndroidTest \
  ${NOTABLE_GRADLE_ARG[@]+"${NOTABLE_GRADLE_ARG[@]}"} >/dev/null
capture_snapshot "$TMP/candidate.txt"

python3 "$ROOT/scripts/compare-pen-snapshots.py" \
  "$TMP/reference.txt" "$TMP/candidate.txt"
if [[ -n "$NOTABLE_LIBRARY" ]]; then
  python3 "$ROOT/scripts/compare-pen-snapshots.py" \
    "$TMP/notable.txt" "$TMP/candidate.txt" --profile notable-0.2.3
fi

echo "Running the complete candidate instrumentation suite"
ANDROID_HOME="$SDK_ROOT" ANDROID_SDK_ROOT="$SDK_ROOT" \
  "$ROOT/gradlew" -p "$ROOT" :onyxsdk-pen:connectedDebugAndroidTest \
  ${NOTABLE_GRADLE_ARG[@]+"${NOTABLE_GRADLE_ARG[@]}"} >/dev/null
echo "Device pen differential and candidate instrumentation passed on $SERIAL."
