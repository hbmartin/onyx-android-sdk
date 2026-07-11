# Recovered Rust native reader

This crate is a behavioral reconstruction of `libonyx_pen_touch_reader.so` from
the four supplied Android binaries. It preserves the 11 JNI entry points,
Linux input-device discovery, raw event parsing, pen/eraser state transitions,
region filtering, pressure normalization, and the Java callback signature.

The supplied binaries were removed after analysis. The checked-in Java
`NativeContract`, Rust tests, and recovered state-machine behavior now define
the reproducible contract. This pure-Rust implementation has no C++ runtime
dependency.

Run `cargo test` for state-machine validation. Android builds are produced by
`scripts/build-rust-android.sh` from the repository root.
