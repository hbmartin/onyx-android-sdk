---
status: accepted
---

# Version the public recognition provider SPI

Publish separate typed HWR, OCR, and speech factories behind stable
generation-bearing provider IDs. Registration is explicit and frozen during
process-wide initialization; factories declare one SPI major and a compatible
host-minor range, and duplicate or incompatible registrations are rejected.
Firmware labels remain documented aliases, and the privileged BOOX Binder service
is not a provider. This keeps stored provenance meaningful and lets third-party
engines integrate without exposing vendor or transport types.
