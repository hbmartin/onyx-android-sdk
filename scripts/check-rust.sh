#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
MODE="${1:?usage: check-rust.sh <fmt|test|clippy>}"
MANIFEST="$ROOT/onyxsdk-pen/native/Cargo.toml"
TOOLCHAIN="$(cd "$ROOT" && rustup show active-toolchain | awk '{print $1}')"
CARGO=(rustup run "$TOOLCHAIN" cargo)

case "$MODE" in
  fmt) "${CARGO[@]}" fmt --all --check --manifest-path "$MANIFEST" ;;
  test) "${CARGO[@]}" test --locked --manifest-path "$MANIFEST" ;;
  clippy) "${CARGO[@]}" clippy --locked --all-targets --manifest-path "$MANIFEST" -- -D warnings ;;
  *) echo "unknown mode: $MODE" >&2; exit 2 ;;
esac
