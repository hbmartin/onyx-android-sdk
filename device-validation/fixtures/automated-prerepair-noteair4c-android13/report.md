# Onyx SDK device comparison

Float tolerance: `0.001`

## Classifications

- `match`: 41
- `recovery_defect`: 75
- `missing_in_recovered`: 0
- `extra_in_recovered`: 0
- `platform_variation`: 3
- `permission_denied`: 1
- `unsupported_hardware`: 0
- `harness_error`: 0

## Public API surface

- `base`: `recovery_defect` — 0 missing classes, 75 changed class signatures, 0 recovery extensions
- `device`: `match` — 0 missing classes, 0 changed class signatures, 0 recovery extensions
- `pen`: `match` — 0 missing classes, 0 changed class signatures, 3 recovery extensions

Exact class names are recorded in `api-surface.json`.

## Non-matching cases

- `permission_denied` — `base/proc_input_access/observation` occurrence 0
