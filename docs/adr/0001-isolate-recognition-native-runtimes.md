---
status: accepted
---

# Isolate recognition native runtimes by pipeline

Expose one Kotlin recognition facade while placing HWR, OCR, and speech runtime
dependencies in separate core AARs. A fourth native-runtime artifact alone owns
the pinned `libc++_shared.so`; only those four artifacts may package native
libraries. All repository-owned native source is Rust, while licensed vendor
binaries remain allowed. This avoids a monolithic JNI surface, prevents optional
Kotlin extensions from acquiring native ownership, and makes duplicate or
incompatible C++ runtimes a publication error.
