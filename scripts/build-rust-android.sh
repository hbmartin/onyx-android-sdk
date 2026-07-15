#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
NATIVE_ROOT="$ROOT/onyxsdk-pen/native"
MANIFEST="$NATIVE_ROOT/Cargo.toml"
OUTPUT_ROOT="${RUST_ANDROID_OUTPUT_DIR:-$ROOT/onyxsdk-pen/src/main/jniLibs}"
NDK_VERSION="${ANDROID_NDK_VERSION:-28.2.13676358}"
SDK_ROOT="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-}}"
if [[ -z "$SDK_ROOT" ]]; then
  echo "Set ANDROID_HOME or ANDROID_SDK_ROOT to the Android SDK directory" >&2
  exit 1
fi
NDK_ROOT="${ANDROID_NDK_HOME:-${ANDROID_NDK_ROOT:-$SDK_ROOT/ndk/$NDK_VERSION}}"
API="${ANDROID_API:-21}"
SHARED_LIBRARY_SUFFIX=".so"

if [[ ! -d "$NDK_ROOT/toolchains/llvm/prebuilt" ]]; then
  echo "Android NDK not found at $NDK_ROOT" >&2
  exit 1
fi

case "$(uname -s)" in
  Darwin) host_glob="darwin-*" ;;
  Linux) host_glob="linux-*" ;;
  *) echo "Unsupported NDK host: $(uname -s)" >&2; exit 1 ;;
esac

NDK_TOOLCHAIN=""
for candidate in "$NDK_ROOT"/toolchains/llvm/prebuilt/$host_glob/bin; do
  if [[ -d "$candidate" ]]; then
    NDK_TOOLCHAIN="$candidate"
    break
  fi
done
if [[ -z "$NDK_TOOLCHAIN" ]]; then
  echo "No $host_glob NDK toolchain found under $NDK_ROOT" >&2
  exit 1
fi

targets=(
  armv7-linux-androideabi
  aarch64-linux-android
  i686-linux-android
  x86_64-linux-android
)
RUST_TOOLCHAIN="$(cd "$ROOT" && rustup show active-toolchain | awk '{print $1}')"
rustup target add --toolchain "$RUST_TOOLCHAIN" "${targets[@]}"

export RUSTC="$(rustup which --toolchain "$RUST_TOOLCHAIN" rustc)"
export RUSTDOC="$(rustup which --toolchain "$RUST_TOOLCHAIN" rustdoc)"
export CARGO_TARGET_ARMV7_LINUX_ANDROIDEABI_LINKER="$NDK_TOOLCHAIN/armv7a-linux-androideabi${API}-clang"
export CARGO_TARGET_AARCH64_LINUX_ANDROID_LINKER="$NDK_TOOLCHAIN/aarch64-linux-android${API}-clang"
export CARGO_TARGET_I686_LINUX_ANDROID_LINKER="$NDK_TOOLCHAIN/i686-linux-android${API}-clang"
export CARGO_TARGET_X86_64_LINUX_ANDROID_LINKER="$NDK_TOOLCHAIN/x86_64-linux-android${API}-clang"

build_one() {
  local target="$1"
  local abi="$2"
  rustup run "$RUST_TOOLCHAIN" cargo build \
    --locked \
    --manifest-path "$MANIFEST" \
    --release \
    --target "$target"
  mkdir -p "$OUTPUT_ROOT/$abi"
  cp "$NATIVE_ROOT/target/$target/release/libonyx_pen_touch_reader$SHARED_LIBRARY_SUFFIX" \
    "$OUTPUT_ROOT/$abi/libonyx_pen_touch_reader$SHARED_LIBRARY_SUFFIX"
  cp "$NATIVE_ROOT/target/$target/release/libneopen_jni$SHARED_LIBRARY_SUFFIX" \
    "$OUTPUT_ROOT/$abi/libneopen_jni$SHARED_LIBRARY_SUFFIX"
  # The single modern binary exports both JNI namespaces. Keep the historical soname as an
  # application-compatibility alias for external penbrush AARs that still load "neo_pen".
  cp "$NATIVE_ROOT/target/$target/release/libneopen_jni$SHARED_LIBRARY_SUFFIX" \
    "$OUTPUT_ROOT/$abi/libneo_pen$SHARED_LIBRARY_SUFFIX"
}

build_one armv7-linux-androideabi armeabi-v7a
build_one aarch64-linux-android arm64-v8a
build_one i686-linux-android x86
build_one x86_64-linux-android x86_64

echo "Modern Rust pen libraries and legacy compatibility aliases are in $OUTPUT_ROOT"
