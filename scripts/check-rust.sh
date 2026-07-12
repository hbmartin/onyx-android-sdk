#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")/.." && pwd)"
MODE="${1:?usage: check-rust.sh <fmt|test|clippy>}"

for crate in \
  "$ROOT/onyxsdk-pen/native/onyx-pen-touch-reader" \
  "$ROOT/onyxsdk-pen/native/onyx-neo-pen"; do
  case "$MODE" in
    fmt) cargo fmt --check --manifest-path "$crate/Cargo.toml" ;;
    test) cargo test --locked --manifest-path "$crate/Cargo.toml" ;;
    clippy) cargo clippy --locked --all-targets --manifest-path "$crate/Cargo.toml" -- -D warnings ;;
    *) echo "unknown mode: $MODE" >&2; exit 2 ;;
  esac
done
