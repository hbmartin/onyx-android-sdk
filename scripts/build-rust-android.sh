#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
READER_CRATE="$ROOT/onyxsdk-pen/native/onyx-pen-touch-reader"
NEO_CRATE="$ROOT/onyxsdk-pen/native/onyx-neo-pen"
OUTPUT_ROOT="${RUST_ANDROID_OUTPUT_DIR:-$ROOT/onyxsdk-pen/src/main/jniLibs}"
NDK_VERSION="${ANDROID_NDK_VERSION:-28.2.13676358}"
SDK_ROOT="${ANDROID_HOME:-${ANDROID_SDK_ROOT:-$HOME/Library/Android/sdk}}"
NDK_ROOT="${ANDROID_NDK_HOME:-${ANDROID_NDK_ROOT:-$SDK_ROOT/ndk/$NDK_VERSION}}"
API="${ANDROID_API:-21}"

if [[ ! -d "$NDK_ROOT/toolchains/llvm/prebuilt" ]]; then
  echo "Android NDK not found at $NDK_ROOT" >&2
  exit 1
fi

case "$(uname -s)" in
  Darwin) host_glob="darwin-*" ;;
  Linux) host_glob="linux-*" ;;
  *) echo "Unsupported NDK host: $(uname -s)" >&2; exit 1 ;;
esac

TOOLCHAIN=""
for candidate in "$NDK_ROOT"/toolchains/llvm/prebuilt/$host_glob/bin; do
  if [[ -d "$candidate" ]]; then
    TOOLCHAIN="$candidate"
    break
  fi
done
if [[ -z "$TOOLCHAIN" ]]; then
  echo "No $host_glob NDK toolchain found under $NDK_ROOT" >&2
  exit 1
fi

targets=(
  armv7-linux-androideabi
  aarch64-linux-android
  i686-linux-android
  x86_64-linux-android
)
rustup target add --toolchain stable "${targets[@]}"

export RUSTC="$(rustup which --toolchain stable rustc)"
export RUSTDOC="$(rustup which --toolchain stable rustdoc)"
export CARGO_TARGET_ARMV7_LINUX_ANDROIDEABI_LINKER="$TOOLCHAIN/armv7a-linux-androideabi${API}-clang"
export CARGO_TARGET_AARCH64_LINUX_ANDROID_LINKER="$TOOLCHAIN/aarch64-linux-android${API}-clang"
export CARGO_TARGET_I686_LINUX_ANDROID_LINKER="$TOOLCHAIN/i686-linux-android${API}-clang"
export CARGO_TARGET_X86_64_LINUX_ANDROID_LINKER="$TOOLCHAIN/x86_64-linux-android${API}-clang"

build_one() {
  local target="$1"
  local abi="$2"
  for crate in "$READER_CRATE" "$NEO_CRATE"; do
    rustup run stable cargo build \
      --locked \
      --manifest-path "$crate/Cargo.toml" \
      --release \
      --target "$target"
  done
  mkdir -p "$OUTPUT_ROOT/$abi"
  cp "$READER_CRATE/target/$target/release/libonyx_pen_touch_reader.so" \
    "$OUTPUT_ROOT/$abi/libonyx_pen_touch_reader.so"
  cp "$NEO_CRATE/target/$target/release/libneo_pen.so" \
    "$OUTPUT_ROOT/$abi/libneo_pen.so"
}

build_one armv7-linux-androideabi armeabi-v7a
build_one aarch64-linux-android arm64-v8a
build_one i686-linux-android x86
build_one x86_64-linux-android x86_64

echo "Recovered Rust pen libraries are in $OUTPUT_ROOT"
