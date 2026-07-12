#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
TMP="$(mktemp -d)"
trap 'rm -rf "$TMP"' EXIT

fail() {
  echo "verification failed: $*" >&2
  exit 1
}

for removed in \
  "$ROOT/onyxsdk-base/binary-reference" \
  "$ROOT/onyxsdk-device/binary-reference" \
  "$ROOT/onyxsdk-pen/binary-reference" \
  "$ROOT/recovery-tests/fixtures"; do
  test ! -e "$removed" || fail "removed binary input returned: $removed"
done

tracked_binary="$({
  git -C "$ROOT" ls-files '*.jar' '*.aar' '*.so'
} | rg -v '^gradle/wrapper/gradle-wrapper\.jar$' || true)"
test -z "$tracked_binary" || fail "tracked original/generated binaries found: $tracked_binary"

if rg -n 'classes-original|binary-reference|from\(".*\.jar"\)|tasks\.register<Zip>' \
    "$ROOT"/*.gradle.kts "$ROOT"/onyxsdk-*/build.gradle.kts; then
  fail "binary injection build logic is still present"
fi

for package in base device pen; do
  case "$package" in
    base) expected=588 ;;
    device) expected=72 ;;
    pen) expected=45 ;;
  esac
  actual="$(find "$ROOT/recovery-evidence/decompilers/$package/jadx" -name '*.java' | wc -l | tr -d ' ')"
  test "$actual" -eq "$expected" || fail "$package JADX evidence count is $actual, expected $expected"
  echo "$package: $actual raw recovered sources retained as evidence"
done

if rg -n 'UnsupportedOperationException\("Method not decompiled|IllegalStateException\("Decompilation failed' \
    "$ROOT/recovery-evidence/decompilers/base/jadx/com/onyx/android/sdk/rx/RxUtils.java" \
    "$ROOT/recovery-evidence/decompilers/base/jadx/com/onyx/android/sdk/utils/FileUtils.java" \
    "$ROOT/recovery-evidence/decompilers/device/jadx/com/onyx/android/sdk/device/"{RK32XXDevice,IMX6Device,RK33XXDevice,SDMDevice,RK31XXDevice}.java; then
  fail "a repaired source site still contains a synthetic decompiler throw"
fi

BASE_AAR="$ROOT/onyxsdk-base/build/outputs/aar/onyxsdk-base-release.aar"
DEVICE_AAR="$ROOT/onyxsdk-device/build/outputs/aar/onyxsdk-device-release.aar"
PEN_AAR="$ROOT/onyxsdk-pen/build/outputs/aar/onyxsdk-pen-release.aar"
for aar in "$BASE_AAR" "$DEVICE_AAR" "$PEN_AAR"; do
  test -s "$aar" || fail "missing source-native AAR: $aar"
  unzip -Z1 "$aar" | rg -q '^AndroidManifest.xml$' || fail "$aar has no manifest"
  unzip -Z1 "$aar" | rg -q '^classes.jar$' || fail "$aar has no source-compiled classes.jar"
done

unzip -p "$BASE_AAR" classes.jar > "$TMP/base.jar"
unzip -p "$DEVICE_AAR" classes.jar > "$TMP/device.jar"
unzip -p "$PEN_AAR" classes.jar > "$TMP/pen.jar"
jar tf "$TMP/base.jar" | rg -q 'com/onyx/android/sdk/utils/FileUtils.class'
jar tf "$TMP/base.jar" | rg -q 'com/onyx/android/sdk/rx/RxUtils\$d.class'
jar tf "$TMP/device.jar" | rg -q 'com/onyx/android/sdk/device/a.class'
for class in RK32XXDevice IMX6Device RK33XXDevice SDMDevice RK31XXDevice; do
  jar tf "$TMP/device.jar" | rg -q "com/onyx/android/sdk/device/$class.class"
done
jar tf "$TMP/pen.jar" | rg -q 'com/onyx/android/sdk/pen/RawInputReader.class'
jar tf "$TMP/pen.jar" | rg -q 'com/onyx/android/sdk/recovered/pen/NativeContract.class'
echo "all AAR classes were compiled from recovered source"

if unzip -Z1 "$PEN_AAR" | rg -q 'libc\+\+_shared\.so'; then
  fail "pen AAR still contains libc++_shared.so"
fi

NDK_VERSION="${ANDROID_NDK_VERSION:-28.2.13676358}"
SDK_ROOT="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-$HOME/Library/Android/sdk}}"
NDK_ROOT="${ANDROID_NDK_HOME:-${ANDROID_NDK_ROOT:-$SDK_ROOT/ndk/$NDK_VERSION}}"
case "$(uname -s)" in
  Darwin) host_glob="darwin-*" ;;
  Linux) host_glob="linux-*" ;;
  *) fail "unsupported host for native validation" ;;
esac
LLVM_NM=""
LLVM_READELF=""
for bin in "$NDK_ROOT"/toolchains/llvm/prebuilt/$host_glob/bin; do
  if [[ -x "$bin/llvm-nm" ]]; then
    LLVM_NM="$bin/llvm-nm"
    LLVM_READELF="$bin/llvm-readelf"
    break
  fi
done
test -x "$LLVM_NM" || fail "NDK llvm-nm not found"

rg -o '"Java_[^"]+"' \
  "$ROOT/onyxsdk-pen/src/main/java/com/onyx/android/sdk/recovered/pen/NativeContract.java" \
  | tr -d '"' | sort > "$TMP/expected-exports"
test "$(wc -l < "$TMP/expected-exports" | tr -d ' ')" -eq 11 \
  || fail "source contract does not contain 11 JNI exports"

for abi in armeabi-v7a arm64-v8a x86 x86_64; do
  rebuilt="$ROOT/onyxsdk-pen/src/main/jniLibs/$abi/libonyx_pen_touch_reader.so"
  test -s "$rebuilt" || fail "missing source-built $abi JNI library"
  "$LLVM_NM" -D --defined-only "$rebuilt" | awk '{print $3}' | rg '^Java_' | sort > "$TMP/$abi.exports"
  diff -u "$TMP/expected-exports" "$TMP/$abi.exports" || fail "$abi JNI source contract differs"
  if "$LLVM_READELF" -d "$rebuilt" | rg -q 'libc\+\+_shared'; then
    fail "$abi Rust driver unexpectedly depends on libc++_shared"
  fi
  unzip -p "$PEN_AAR" "jni/$abi/libonyx_pen_touch_reader.so" > "$TMP/$abi.aar.so"
  "$LLVM_NM" -D --defined-only "$TMP/$abi.aar.so" | awk '{print $3}' | rg '^Java_' | sort > "$TMP/$abi.aar.exports"
  diff -u "$TMP/expected-exports" "$TMP/$abi.aar.exports" || fail "$abi packaged JNI contract differs"
  echo "$abi: source-built Rust driver exposes all 11 JNI functions"
done

echo "Source-only recovery verification passed."
