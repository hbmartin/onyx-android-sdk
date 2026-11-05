---
status: accepted
---

# Use host-sourced versioned resource packs

Accept caller-supplied ZIP packs with a canonical JSON manifest, bounded
extraction, and per-file lengths and SHA-256 values. These hashes detect
corruption but do not authenticate the source; trust remains a host responsibility.
Installation requires an explicit prepare-only or activate-after-verification
policy, preserves active and rollback revisions plus one prepared candidate, and
journals operations for re-verification and resume after process death.
Network-facing Digital Ink and SODA preparation live in optional
explicit-operation modules rather than the offline facade.
