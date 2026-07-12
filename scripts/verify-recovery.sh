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

if rg -n 'UnsupportedOperationException\("Method not decompiled|IllegalStateException\("Decompilation failed' \
    "$ROOT/onyxsdk-base/src/main/java/com/onyx/android/sdk/rx/RxUtils.java" \
    "$ROOT/onyxsdk-base/src/main/java/com/onyx/android/sdk/utils/FileUtils.java" \
    "$ROOT/onyxsdk-device/src/main/java/com/onyx/android/sdk/device/"{RK32XXDevice,IMX6Device,RK33XXDevice,SDMDevice,RK31XXDevice}.java; then
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
jar tf "$TMP/pen.jar" | rg '\.class$' | sort -u > "$TMP/pen-classes"
missing_pen_classes="$(comm -23 "$ROOT/scripts/native-contracts/pen-classes.txt" "$TMP/pen-classes" || true)"
test -z "$missing_pen_classes" || fail "pen AAR is missing reference classes: $missing_pen_classes"
echo "all AAR classes were compiled from recovered source"

if unzip -Z1 "$PEN_AAR" | rg -q 'onyxsdk-pen-native-classes\.jar|classes-original\.jar'; then
  fail "pen AAR contains a reference JAR"
fi

if rg -n '\?\?|\*\* GOTO|void var[0-9]|UnsupportedOperationException\("Method not decompiled|IllegalStateException\("Decompilation failed' \
    "$ROOT/onyxsdk-pen/src/main/java"; then
  fail "pen production source still contains decompiler-generated invalid or unfinished code"
fi

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

sha256() {
  if command -v shasum >/dev/null 2>&1; then
    shasum -a 256 "$1" | awk '{print $1}'
  else
    sha256sum "$1" | awk '{print $1}'
  fi
}
reference_neo_sha="4e902c1485ea660c5deb6a52693f95d113f862f448b57b6374c70404ba695f5e"

for abi in armeabi-v7a arm64-v8a x86 x86_64; do
  for native_contract in \
    "touch-reader:libonyx_pen_touch_reader.so" \
    "neo-pen:libneo_pen.so"; do
    contract="${native_contract%%:*}"
    library="${native_contract#*:}"
    expected="$ROOT/scripts/native-contracts/$contract.exports"
    rebuilt="$ROOT/onyxsdk-pen/src/main/jniLibs/$abi/$library"
    test -s "$expected" || fail "missing checked-in $contract export contract"
    test -s "$rebuilt" || fail "missing source-built $abi $library"
    "$LLVM_NM" -D --defined-only "$rebuilt" | awk '{print $3}' | rg '^Java_' | sort > "$TMP/$abi.$contract.exports"
    diff -u "$expected" "$TMP/$abi.$contract.exports" || fail "$abi $contract source contract differs"
    if "$LLVM_READELF" -d "$rebuilt" | rg -q 'libc\+\+_shared'; then
      fail "$abi $library unexpectedly depends on libc++_shared"
    fi
    unzip -p "$PEN_AAR" "jni/$abi/$library" > "$TMP/$abi.$contract.aar.so"
    test -s "$TMP/$abi.$contract.aar.so" || fail "$abi packaged $library is missing"
    "$LLVM_NM" -D --defined-only "$TMP/$abi.$contract.aar.so" | awk '{print $3}' | rg '^Java_' | sort > "$TMP/$abi.$contract.aar.exports"
    diff -u "$expected" "$TMP/$abi.$contract.aar.exports" || fail "$abi packaged $contract contract differs"
    if [[ "$abi" == "arm64-v8a" && "$library" == "libneo_pen.so" ]]; then
      test "$(sha256 "$rebuilt")" != "$reference_neo_sha" \
        || fail "source output was replaced by the supplied reference libneo_pen.so"
      test "$(sha256 "$TMP/$abi.$contract.aar.so")" != "$reference_neo_sha" \
        || fail "release AAR contains the supplied reference libneo_pen.so"
    fi
  done
  echo "$abi: both source-built Rust libraries match their JNI contracts"
done

echo "Source-only recovery verification passed."
