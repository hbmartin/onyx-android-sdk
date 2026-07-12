# Recovered Rust native reader

This crate is the source implementation of the pen touch-reader Android shared library. It
exports all 11 `RawInputReader` JNI functions and implements Linux pen-device
discovery, raw event polling, pen/eraser transitions, region filtering,
pressure normalization, and Java callback dispatch.

Run `scripts/check-rust.sh test` from the repository root for host state-machine validation. Android builds are
produced for four ABIs by `scripts/build-rust-android.sh`; device JNI lifecycle
coverage lives in `RawInputReaderDeviceTest`.

The `fuzz/` package contains a `cargo-fuzz` target for arbitrary input-event,
configuration, region, pause, and reset sequences. Run it on a Unix-like host
with a nightly toolchain:

```bash
cd onyxsdk-pen/native/onyx-pen-touch-reader
cargo +nightly-2026-07-02 fuzz run pen_manager
```
