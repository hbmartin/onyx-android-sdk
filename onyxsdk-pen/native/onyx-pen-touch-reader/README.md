# Recovered Rust native reader

This crate is the source implementation of `libonyx_pen_touch_reader.so`. It
exports all 11 `RawInputReader` JNI functions and implements Linux pen-device
discovery, raw event polling, pen/eraser transitions, region filtering,
pressure normalization, and Java callback dispatch.

Run `cargo test --locked` for host state-machine validation. Android builds are
produced for four ABIs by `scripts/build-rust-android.sh`; device JNI lifecycle
coverage lives in `RawInputReaderDeviceTest`.
