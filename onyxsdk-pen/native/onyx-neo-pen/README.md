# Recovered Rust neo pen renderer

This crate builds `libneo_pen.so` for both SDK API generations. It exports the
seven handle-based `NeoPenNative` functions and seven static
`NeoPenWrapper` functions, implements all nine pen types, prediction, texture
bitmaps, lifecycle management, and legacy offline rendering.

Run `cargo test --locked` for host validation. Use
`scripts/device-pen-differential.sh` to compare the arm64 build with the
untracked reference on BOOX hardware.
