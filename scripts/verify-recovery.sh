#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
TMP="$(mktemp -d)"
trap 'rm -rf "$TMP"' EXIT

fail() {
  echo "verification failed: $*" >&2
  exit 1
}

# rg exits 0 on match, 1 on no match, and 2 on error (e.g. a listed file is
# missing); a plain `if rg` treats an error like a clean scan, so every
# must-be-clean scan goes through this helper instead.
scan_must_be_clean() {
  local description="$1"
  shift
  local status=0
  rg -n "$@" && status=0 || status=$?
  if [[ "$status" -eq 0 ]]; then
    fail "$description"
  elif [[ "$status" -ne 1 ]]; then
    fail "scan could not run: $description"
  fi
}

for removed in \
  "$ROOT/onyxsdk-base/binary-reference" \
  "$ROOT/onyxsdk-device/binary-reference" \
  "$ROOT/onyxsdk-pen/binary-reference" \
  "$ROOT/recovery-tests/fixtures"; do
  test ! -e "$removed" || fail "removed binary input returned: $removed"
done

shared_library_suffix=".so"
tracked_binary="$({
  git -C "$ROOT" ls-files '*.jar' '*.aar' "*$shared_library_suffix"
} | rg -v '^gradle/wrapper/gradle-wrapper\.jar$' || true)"
test -z "$tracked_binary" || fail "tracked original/generated binaries found: $tracked_binary"

# Scans every Gradle script in the repository, including recovery-tests and
# the onyxsdk-base support modules.
scan_must_be_clean "binary injection build logic is still present" \
  'classes-original|binary-reference|from\(".*\.jar"\)|tasks\.register<Zip>' \
  --glob '*.gradle.kts' "$ROOT"

repaired_sources=(
  "$ROOT/onyxsdk-base/src/main/java/com/onyx/android/sdk/rx/RxUtils.java"
  "$ROOT/onyxsdk-base/src/main/java/com/onyx/android/sdk/utils/FileUtils.java"
  "$ROOT/onyxsdk-device/src/main/java/com/onyx/android/sdk/device/"{RK32XXDevice,IMX6Device,RK33XXDevice,SDMDevice,RK31XXDevice}.java
)
for source in "${repaired_sources[@]}"; do
  test -f "$source" || fail "expected repaired source missing: $source"
done
BASE_AAR="$ROOT/onyxsdk-base/build/outputs/aar/onyxsdk-base-release.aar"
DEVICE_AAR="$ROOT/onyxsdk-device/build/outputs/aar/onyxsdk-device-release.aar"
KTX_AAR="$ROOT/onyxsdk-ktx/build/outputs/aar/onyxsdk-ktx-release.aar"
PEN_AAR="$ROOT/onyxsdk-pen/build/outputs/aar/onyxsdk-pen-release.aar"
for aar in "$BASE_AAR" "$DEVICE_AAR" "$KTX_AAR" "$PEN_AAR"; do
  test -s "$aar" || fail "missing source-native AAR: $aar"
  unzip -Z1 "$aar" | rg '^AndroidManifest.xml$' >/dev/null || fail "$aar has no manifest"
  unzip -Z1 "$aar" | rg '^classes.jar$' >/dev/null || fail "$aar has no source-compiled classes.jar"
done

unzip -p "$BASE_AAR" classes.jar > "$TMP/base.jar"
unzip -p "$DEVICE_AAR" classes.jar > "$TMP/device.jar"
unzip -p "$KTX_AAR" classes.jar > "$TMP/ktx.jar"
unzip -p "$PEN_AAR" classes.jar > "$TMP/pen.jar"
jar tf "$TMP/base.jar" | rg 'com/onyx/android/sdk/utils/FileUtils.class' >/dev/null \
  || fail "base classes.jar is missing FileUtils"
jar tf "$TMP/base.jar" | rg 'com/onyx/android/sdk/rx/RxUtils\$d.class' >/dev/null \
  || fail "base classes.jar is missing RxUtils\$d"
jar tf "$TMP/device.jar" | rg 'com/onyx/android/sdk/device/a.class' >/dev/null \
  || fail "device classes.jar is missing the obfuscated class a"
for class in RK32XXDevice IMX6Device RK33XXDevice SDMDevice RK31XXDevice; do
  jar tf "$TMP/device.jar" | rg "com/onyx/android/sdk/device/$class.class" >/dev/null \
    || fail "device classes.jar is missing $class"
done
jar tf "$TMP/ktx.jar" | rg 'com/onyx/android/sdk/ktx/PixelSize.class' >/dev/null \
  || fail "Kotlin companion classes.jar is missing PixelSize"
jar tf "$TMP/pen.jar" | rg 'com/onyx/android/sdk/pen/RawInputReader.class' >/dev/null \
  || fail "pen classes.jar is missing RawInputReader"
# The contract list is byte-order sorted; force the same collation here so
# comm never sees "disorder" under a UTF-8 host locale.
jar tf "$TMP/pen.jar" | rg '\.class$' | LC_ALL=C sort -u > "$TMP/pen-classes"
missing_pen_classes="$(LC_ALL=C comm -23 "$ROOT/scripts/native-contracts/pen-classes.txt" "$TMP/pen-classes")"
test -z "$missing_pen_classes" || fail "pen AAR is missing reference classes: $missing_pen_classes"
echo "all AAR classes were compiled from recovered source"

for aar in "$BASE_AAR" "$DEVICE_AAR" "$KTX_AAR" "$PEN_AAR"; do
  listing="$(unzip -Z1 "$aar")" || fail "could not list contents of $aar"
  if printf '%s\n' "$listing" \
      | rg '^libs/.+\.jar$|onyxsdk-pen-native-classes\.jar|classes-original\.jar' >/dev/null; then
    fail "$aar contains an embedded or reference JAR"
  fi
done

scan_must_be_clean "pen production source still contains invalid or unfinished code" \
  '\?\?|\*\* GOTO|void var[0-9]' \
  "$ROOT/onyxsdk-pen/src/main/java"

if unzip -Z1 "$PEN_AAR" | rg 'libc\+\+_shared\.so' >/dev/null; then
  fail "pen AAR still contains libc++_shared.so"
fi

NDK_VERSION="${ANDROID_NDK_VERSION:-28.2.13676358}"
SDK_ROOT="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-}}"
test -n "$SDK_ROOT" || fail "set ANDROID_HOME or ANDROID_SDK_ROOT to the Android SDK directory"
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
    "touch-reader:libonyx_pen_touch_reader$shared_library_suffix" \
    "neo-pen:libneo_pen$shared_library_suffix"; do
    contract="${native_contract%%:*}"
    library="${native_contract#*:}"
    expected="$ROOT/scripts/native-contracts/$contract.exports"
    rebuilt="$ROOT/onyxsdk-pen/src/main/jniLibs/$abi/$library"
    test -s "$expected" || fail "missing checked-in $contract export contract"
    test -s "$rebuilt" || fail "missing source-built $abi $library"
    "$LLVM_NM" -D --defined-only "$rebuilt" | awk '{print $3}' | rg '^Java_' | sort > "$TMP/$abi.$contract.exports"
    diff -u "$expected" "$TMP/$abi.$contract.exports" || fail "$abi $contract source contract differs"
    if "$LLVM_READELF" -d "$rebuilt" | rg 'libc\+\+_shared' >/dev/null; then
      fail "$abi $library unexpectedly depends on libc++_shared"
    fi
    packaged_library="$TMP/$abi.$contract.aar$shared_library_suffix"
    unzip -p "$PEN_AAR" "jni/$abi/$library" > "$packaged_library"
    test -s "$packaged_library" || fail "$abi packaged $library is missing"
    "$LLVM_NM" -D --defined-only "$packaged_library" | awk '{print $3}' | rg '^Java_' | sort > "$TMP/$abi.$contract.aar.exports"
    diff -u "$expected" "$TMP/$abi.$contract.aar.exports" || fail "$abi packaged $contract contract differs"
    if [[ "$abi" == "arm64-v8a" && "$library" == "libneo_pen$shared_library_suffix" ]]; then
      test "$(sha256 "$rebuilt")" != "$reference_neo_sha" \
        || fail "source output was replaced by the supplied reference libneo_pen.so"
      test "$(sha256 "$packaged_library")" != "$reference_neo_sha" \
        || fail "release AAR contains the supplied reference libneo_pen.so"
    fi
  done
  echo "$abi: both source-built Rust libraries match their JNI contracts"
done

echo "Source-only recovery verification passed."
