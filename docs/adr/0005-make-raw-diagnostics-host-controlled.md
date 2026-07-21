---
status: accepted
---

# Make raw recognition diagnostics host-controlled

Emit content-free recognition metadata to Logcat by default and expose an optional
host sink. One immutable initialization flag permits typed raw ink, image, audio,
and result payloads, including in release builds; without a sink only summaries
and hashes reach Logcat. Diagnostics never delay recognition, use fixed
event/attachment/byte budgets, and drop raw data first. The host owns consent,
access, transport, storage, retention, and deletion because the SDK cannot infer
those product policies.
